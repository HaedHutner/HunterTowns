package com.atherys.towns.model;

import com.atherys.towns.model.entity.Town;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.world.World;

import java.time.LocalDateTime;
import java.util.UUID;

public class RaidPoint {

    private Transform<World> pointTransform;

    private LocalDateTime creationTime;

    private UUID raidPointUUID;

    private double health;

    private Town raidingTown;

    public RaidPoint(LocalDateTime creationTime, Transform<World> location, double health, UUID entityId, Town town) {
        this.pointTransform = location;
        this.creationTime = creationTime;
        this.health = health;
        this.raidPointUUID = entityId;
        this.raidingTown = town;
    }

    public Transform<World> getPointTransform() {
        return this.pointTransform;
    }

    public void setPointTransform(Transform<World> pointTransform) {
        this.pointTransform = pointTransform;
    }

    public LocalDateTime getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public double getHealth() {
        return this.health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public UUID getRaidPointUUID() {
        return this.raidPointUUID;
    }

    public void setRaidPointUUID(UUID raidPointUUID) {
        this.raidPointUUID = raidPointUUID;
    }

    public Town getRaidingTown() {
        return this.raidingTown;
    }

    public void setRaidingTown(Town town) {
        this.raidingTown = town;
    }
}
