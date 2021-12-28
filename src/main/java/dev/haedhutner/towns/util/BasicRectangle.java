package dev.haedhutner.towns.util;

import com.flowpowered.math.vector.Vector2i;
import com.flowpowered.math.vector.Vector3i;

import java.util.Objects;

public class BasicRectangle implements Rectangle {
    private Vector2i topLeft;
    private Vector2i bottomRight;

    public BasicRectangle() {
    }

    public BasicRectangle(Vector2i topLeft, Vector2i bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    @Override
    public Vector2i getTopLeftCorner() {
        return topLeft;
    }

    @Override
    public Vector2i getBottomRightCorner() {
        return bottomRight;
    }

    @Override
    public void setTopLeftCorner(Vector3i point) {
        topLeft = point.toVector2(true);
    }

    @Override
    public void setBottomRightCorner(Vector3i point) {
        bottomRight = point.toVector2(true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicRectangle that = (BasicRectangle) o;
        return Objects.equals(topLeft, that.topLeft) &&
                Objects.equals(bottomRight, that.bottomRight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topLeft, bottomRight);
    }

    @Override
    public String toString() {
        return "BasicRectangle{" +
                "topLeft=" + topLeft +
                ", bottomRight=" + bottomRight +
                '}';
    }
}