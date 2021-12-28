package dev.haedhutner.towns.service;

import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.TownsConfig;
import dev.haedhutner.towns.facade.TownFacade;
import dev.haedhutner.towns.facade.TownRaidFacade;
import dev.haedhutner.towns.facade.TownsMessagingFacade;
import dev.haedhutner.towns.model.RaidPoint;
import dev.haedhutner.towns.model.entity.Town;
import dev.haedhutner.towns.util.MathUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.boss.BossBarColors;
import org.spongepowered.api.boss.BossBarOverlays;
import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Singleton
public class TownRaidService {
    @Inject
    TownsConfig config;

    @Inject
    TownService townService;

    @Inject
    TownRaidFacade townRaidFacade;

    @Inject
    TownFacade townFacade;

    @Inject
    TownsMessagingFacade townsMsg;

    Map<UUID, RaidPoint> activeRaids = new ConcurrentHashMap<>();

    public TownRaidService() {

    }

    private void removeEntityFromWorld(World world, UUID entityUUID) {
        Optional<Entity> entity = world.getEntity(entityUUID);
        entity.ifPresent(Entity::remove);
    }

    public void removeRaidPoint(UUID id) {
        RaidPoint point = activeRaids.get(id);
        World world = point.getPointTransform().getExtent();

        //First remove players from BossBar
        ServerBossBar bossBar = point.getRaidBossBar();
        bossBar.removePlayers(bossBar.getPlayers());

        //Next Remove RaidPoint Entity
        removeEntityFromWorld(world, id);

        //Finally, Remove Particles
        point.getParticleUUIDs().forEach(uuid -> removeEntityFromWorld(world, uuid));

        activeRaids.remove(id);
    }

    public RaidPoint getRaidPoint(UUID id) {
        return activeRaids.get(id);
    }

    public boolean isIdRaidEntity(UUID entityId) {
        return activeRaids.containsKey(entityId);
    }

    public Optional<RaidPoint> getTownRaidPoint(Town town) {
        return activeRaids.values().stream().filter(point -> point.getRaidingTown().equals(town)).findFirst();
    }

    public void createRaidPointEntry(Transform<World> transform, Town town, Town targetTown, UUID entityId, Set<UUID> particleEffects) {
        ServerBossBar raidBar = ServerBossBar.builder()
                .name(Text.of("Hired Mage | Time Left: ", townRaidFacade.formatDurationLeft(LocalDateTime.now(), config.RAID.RAID_DURATION)))
                .overlay(BossBarOverlays.PROGRESS)
                .color(BossBarColors.RED)
                .percent(1.0f)
                .build();

        RaidPoint point = new RaidPoint(LocalDateTime.now(), transform, entityId, town, targetTown, particleEffects, raidBar);
        townService.setTownLastRaidCreationDate(town, LocalDateTime.now());
        activeRaids.put(entityId, point);
    }

    public boolean isTownRaidActive(Town town) {
        return getTownRaidPoint(town).isPresent();
    }

    public boolean isLocationTaken(Location<World> location) {
        double raidDistance = Math.pow(config.RAID.RAID_DISTANCE_BETWEEN_POINTS, 2);
        return activeRaids.values().stream()
                .anyMatch(point -> MathUtils.getDistanceBetweenPointsSquared(location.getPosition(), point.getPointTransform().getPosition()) < raidDistance);
    }

    public boolean hasCooldownPeriodPassed(Town town) {
        Optional<LocalDateTime> raidCreationDate = town.getLastRaidCreationDate();
        return raidCreationDate.map(dateTime -> Duration.between(dateTime, LocalDateTime.now()).compareTo(config.RAID.RAID_COOLDOWN) >= 0).orElse(true);
    }

    public boolean hasDurationPassed(Town town) {
        Optional<LocalDateTime> raidCreationDate = town.getLastRaidCreationDate();
        return raidCreationDate.map(dateTime -> Duration.between(dateTime, LocalDateTime.now()).compareTo(config.RAID.RAID_DURATION) >= 0).orElse(true);
    }

    public void initRaidTimer() {
        Task.Builder taxTimer = Task.builder();
        taxTimer.interval(2, TimeUnit.SECONDS)
                .execute(RaidTimerTask())
                .submit(HunterTowns.getInstance());
    }

    public double getRaidHealth(World world, RaidPoint point) {
        Optional<Entity> entity = world.getEntity(point.getRaidPointUUID());
        if (entity.isPresent()) {
            return entity.map(value -> Math.round(value.get(Keys.HEALTH).orElse(0.00))).get();
        }
        return 0;
    }

    private void updateBossBarPlayers(UUID pointId) {
        RaidPoint point = activeRaids.get(pointId);
        Location<World> location = point.getPointTransform().getLocation();
        Set<Player> playersInRadius = location.getExtent().getNearbyEntities(location.getPosition(), config.RAID.RAID_BOSS_BAR_DISTANCE).stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity).collect(Collectors.toSet());

        Collection<Player> bossBarPlayers = point.getRaidBossBar().getPlayers();
        ServerBossBar bossBar = point.getRaidBossBar();

        // Remove players that are no longer in radius
        Set<Player> removePlayers = bossBarPlayers.stream().filter(player -> !playersInRadius.contains(player)).collect(Collectors.toSet());
        bossBar.removePlayers(removePlayers);

        // Add players that were not previously in radius
        Set<Player> addPlayers = playersInRadius.stream().filter(player -> !bossBarPlayers.contains(player)).collect(Collectors.toSet());
        bossBar.addPlayers(addPlayers);
    }

    private void updateBossBar(UUID pointId) {
        //Update BossBar health with current entity health
        RaidPoint point = activeRaids.get(pointId);
        double raidHealth = getRaidHealth(point.getPointTransform().getExtent(), point);
        double raidPercentage = (raidHealth / config.RAID.RAID_HEALTH) * 100;
        point.getRaidBossBar().setName(Text.of("Hired Mage | Time Left: ", townRaidFacade.formatDurationLeft(point.getCreationTime(), config.RAID.RAID_DURATION)));
        point.getRaidBossBar().setPercent((float) (raidPercentage / 100));

        //Update who should be seeing boss bar
        updateBossBarPlayers(pointId);
    }

    private Runnable RaidTimerTask() {
        return () -> {
            Set<UUID> pointsToRemove = new HashSet<>();
            activeRaids.forEach((uuid, point) -> {
                updateBossBar(uuid);
                if (hasDurationPassed(point.getRaidingTown())) {
                    pointsToRemove.add(uuid);
                    townFacade.getOnlineTownMembers(point.getRaidingTown()).forEach(player -> {
                        townsMsg.info(player, Text.of("Your raid point has expired!"));
                    });
                }
            });
            pointsToRemove.forEach(this::removeRaidPoint);
        };
    }
}
