package dev.haedhutner.towns.service;

import dev.haedhutner.towns.TownsConfig;
import dev.haedhutner.towns.api.permission.TownsPermissionContext;
import dev.haedhutner.towns.api.permission.world.WorldPermission;
import dev.haedhutner.towns.model.PlotSelection;
import dev.haedhutner.towns.model.entity.*;
import dev.haedhutner.towns.model.entity.*;
import dev.haedhutner.towns.persistence.NationPlotRepository;
import dev.haedhutner.towns.persistence.TownPlotRepository;
import dev.haedhutner.towns.util.MathUtils;
import com.flowpowered.math.vector.Vector2i;
import com.flowpowered.math.vector.Vector3i;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.AABB;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class PlotService {
    @Inject
    TownsConfig config;

    @Inject
    TownPlotRepository townPlotRepository;

    @Inject
    NationPlotRepository nationPlotRepository;

    @Inject
    ResidentService residentService;

    PlotService() {
    }

    public boolean isLocationWithinPlot(Location<World> location, Plot plot) {
        if (plot.isCuboid()) {
            return plot.asAABB().contains(location.getPosition());
        }

        return MathUtils.pointInRectangle(location.getPosition(), plot);
    }

    public static Set<Vector2i> getChunksOverlappedByPlot(Plot plot) {
        Vector3i southWestCorner = plot.getSouthWestCorner();
        Vector3i northEastCorner = plot.getNorthEastCorner();

        Set<Vector2i> chunkCoordinates = new HashSet<>();

        // Calculate min corner and max corner chunks
        // South West Corner Chunk
        Vector2i southWestChunk = Vector2i.from(southWestCorner.getX() >> 4, southWestCorner.getZ() >> 4);
        // North East Corner Chunk
        Vector2i northEastChunk = Vector2i.from(northEastCorner.getX() >> 4, northEastCorner.getZ() >> 4);

        for (int x = southWestChunk.getX(); x <= northEastChunk.getX(); x++) {
            for (int y = northEastChunk.getY(); y <= southWestChunk.getY(); y++) {
                chunkCoordinates.add(Vector2i.from(x, y));
            }
        }
        return chunkCoordinates;
    }

    public TownPlot createTownPlotFromSelection(PlotSelection selection) {
        TownPlot plot = new TownPlot();
        MathUtils.populateRectangleFromTwoCorners(plot, selection.getPointAVector(), selection.getPointBVector());
        plot.setCuboid(selection.isCuboid());
        return plot;
    }

    public boolean townPlotIntersectAnyOthers(TownPlot plot) {
        for (Vector2i chunkCoordinate : getChunksOverlappedByPlot(plot)) {
            for (TownPlot other : townPlotRepository.getPlotsIntersectingChunk(chunkCoordinate)) {
                if (MathUtils.overlaps(plot, other)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Optional<TownPlot> getTownPlotContainingPlot(TownPlot plot, Town town) {
        for (Vector2i chunkCoordinate : getChunksOverlappedByPlot(plot)) {
            for (TownPlot other : townPlotRepository.getPlotsIntersectingChunk(chunkCoordinate)) {
                if (!other.isCuboid() && MathUtils.contains(other, plot) && other.getTown() == town) {
                    return Optional.of(other);
                }
            }
        }
        return Optional.empty();
    }

    public boolean aabbIntersectAnyCuboidPlots(AABB aabb, TownPlot containing) {
        return containing.getCuboidPlots().stream()
                .anyMatch(plot -> aabb.intersects(plot.asAABB()));
    }

    public Optional<TownPlot> getTownPlotByLocation(Location<World> location) {
        for (TownPlot plot : townPlotRepository.getPlotsIntersectingChunk(location.getChunkPosition())) {
            if (isLocationWithinPlot(location, plot)) {
                return Optional.of(plot);
            }
        }

        return Optional.empty();
    }

    public void setTownPlotName(TownPlot plot, Text newName) {
        plot.setName(newName);
        townPlotRepository.saveOne(plot);
    }

    public void setTownPlotOwner(TownPlot plot, Resident owner) {
        plot.setOwner(owner);
        plot.setPermissions(getDefaultPlotPermissions());

        townPlotRepository.saveOne(plot);
    }

    public boolean townPlotBordersTown(Town town, TownPlot plot) {
        for (TownPlot townPlot : town.getPlots()) {
            if (MathUtils.borders(plot, townPlot)) {
                return true;
            }
        }
        return false;
    }

    public boolean townPlotIntersectsTown(Town town, TownPlot plot) {
        for (TownPlot townPlot : town.getPlots()) {
            if (!MathUtils.overlaps(townPlot, plot)) {
                return true;
            }
        }

        return false;
    }

    public NationPlot createNationPlotFromSelection(PlotSelection selection) {
        NationPlot plot = new NationPlot();
        MathUtils.populateRectangleFromTwoCorners(plot, selection.getPointAVector(), selection.getPointBVector());
        return plot;
    }

    public Set<NationPlot> getNationPlotsByLocation(Location<World> location) {
        return nationPlotRepository.getAll().stream().filter(plot -> isLocationWithinPlot(location, plot))
                .collect(Collectors.toSet());
    }

    public Optional<NationPlot> getNationPlotsByTownPlot(TownPlot tPlot) {
        return nationPlotRepository.getAll().stream().filter(plot ->
                MathUtils.overlaps(tPlot, plot)).findFirst();
    }

    public boolean permissionAlreadyExistsInContext(TownsPermissionContext context, TownPlot plot, WorldPermission permission) {
        return plot.getPermissions().stream().anyMatch(p -> p.getContext().equals(context) && p.getWorldPermission().equals(permission));
    }

    public void addPlotPermission(TownPlot plot, TownsPermissionContext type, WorldPermission permission) {
        TownPlotPermission townPlotPermission = new TownPlotPermission();
        townPlotPermission.setContext(type);
        townPlotPermission.setWorldPermission(permission);

        plot.getPermissions().add(townPlotPermission);

        townPlotRepository.saveOne(plot);
    }

    public void removePlotPermission(TownPlot plot, TownsPermissionContext type, WorldPermission permission) {
        plot.getPermissions().removeIf(p -> p.getWorldPermission().equals(permission) && p.getContext().equals(type));

        townPlotRepository.saveOne(plot);
    }

    public Set<TownPlotPermission> getDefaultPlotPermissions() {
        Set<TownPlotPermission> defaultTownPlotPermissions = new HashSet<>();

        config.TOWN.DEFAULT_PLOT_PERMISSIONS.forEach(config -> {
            TownPlotPermission townPlotPermission = new TownPlotPermission();
            townPlotPermission.setContext(config.getContext());
            townPlotPermission.setWorldPermission(config.getWorldPermission());
            defaultTownPlotPermissions.add(townPlotPermission);
        });

        return defaultTownPlotPermissions;
    }

    public Optional<TownPlot> getClosestTownPlot(Plot plot) {
        int centerX = (plot.getNorthEastCorner().getX() + plot.getSouthWestCorner().getX()) / 2;
        int centerZ = (plot.getNorthEastCorner().getY() + plot.getSouthWestCorner().getY()) / 2;

        TownPlot closest = null;
        double distance = 0;
        for (TownPlot p : townPlotRepository.getAll()) {
            if (p.equals(plot)) {
                continue;
            }

            double newDistance = MathUtils.getDistanceToPlotSquared(Vector2i.from(centerX, centerZ), p);
            if (closest == null || newDistance < distance) {
                closest = p;
                distance = newDistance;
            }
        }

        return Optional.ofNullable(closest);
    }
}
