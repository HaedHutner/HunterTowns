package dev.haedhutner.towns.model.entity;

import dev.haedhutner.core.db.Identifiable;
import dev.haedhutner.towns.persistence.converter.Vector3iConverter;
import dev.haedhutner.towns.util.Rectangle;
import com.flowpowered.math.vector.Vector2i;
import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.util.AABB;

import javax.annotation.Nonnull;
import javax.persistence.*;

@MappedSuperclass
public class Plot implements Rectangle, Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Convert(converter = Vector3iConverter.class)
    private Vector3i swCorner;

    @Convert(converter = Vector3iConverter.class)
    private Vector3i neCorner;

    @Version
    private int version;

    @Nonnull
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vector3i getSouthWestCorner() {
        return swCorner;
    }

    @Override
    public Vector2i getTopLeftCorner() {
        return swCorner.toVector2(true);
    }

    public void setSouthWestCorner(Vector3i swCorner) {
        this.swCorner = swCorner;
    }

    @Override
    public void setTopLeftCorner(Vector3i point) {
        this.swCorner = point;
    }

    public Vector3i getNorthEastCorner() {
        return neCorner;
    }

    @Override
    public Vector2i getBottomRightCorner() {
        return neCorner.toVector2(true);
    }

    public void setNorthEastCorner(Vector3i neCorner) {
        this.neCorner = neCorner;
    }

    @Override
    public void setBottomRightCorner(Vector3i point) {
        this.neCorner = point;
    }

    public boolean isCuboid() {
        return true;
    }

    public AABB asAABB() {
        return new AABB(neCorner, swCorner);
    }

    protected int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
