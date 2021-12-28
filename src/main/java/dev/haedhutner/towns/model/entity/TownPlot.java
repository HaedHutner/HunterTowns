package dev.haedhutner.towns.model.entity;

import dev.haedhutner.towns.persistence.converter.TextConverter;
import org.spongepowered.api.text.Text;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(schema = "atherystowns", name = "TownPlot")
public class TownPlot extends Plot {

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "town_id")
    private Town town;

    @Convert(converter = TextConverter.class)
    private Text name;

    @ManyToOne(fetch = FetchType.EAGER)
    private Resident owner;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            schema = "atherystowns",
            name = "TownPlot_permissions",
            joinColumns = @JoinColumn(name = "townplot_id"),
            inverseJoinColumns = @JoinColumn(name = "townplot_permission_id")
    )
    private Set<TownPlotPermission> permissions;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            schema = "atherystowns",
            name = "TownPlot_cuboidPlots",
            joinColumns = @JoinColumn(name = "townplot_id"),
            inverseJoinColumns = @JoinColumn(name = "cuboid_id")
    )
    private Set<TownPlot> cuboidPlots = new HashSet<>();

    @OneToOne
    private TownPlot parentPlot;

    private boolean isCuboid;

    @OneToOne
    private RentInfo rentInfo;

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public Text getName() {
        return name;
    }

    public void setName(Text name) {
        this.name = name;
    }

    public Resident getOwner() {
        return owner;
    }

    public void setOwner(Resident owner) {
        this.owner = owner;
    }

    public Set<TownPlotPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<TownPlotPermission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean isCuboid() {
        return isCuboid;
    }

    public void setCuboid(boolean cuboid) {
        isCuboid = cuboid;
    }

    public Set<TownPlot> getCuboidPlots() {
        return cuboidPlots;
    }

    public void addCuboidPlot(TownPlot cuboidPlot) {
        this.cuboidPlots.add(cuboidPlot);
    }

    public void removeCuboidPlot(TownPlot cuboidPlot) {
        this.cuboidPlots.remove(cuboidPlot);
    }

    public TownPlot getParentPlot() {
        return parentPlot;
    }

    public void setParentPlot(TownPlot parentPlot) {
        this.parentPlot = parentPlot;
    }

    public Optional<RentInfo> getRentInfo() {
        return Optional.ofNullable(rentInfo);
    }

    public void setRentInfo(RentInfo rentInfo) {
        this.rentInfo = rentInfo;
    }
}
