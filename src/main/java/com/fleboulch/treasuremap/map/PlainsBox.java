package com.fleboulch.treasuremap.map;

import com.fleboulch.treasuremap.kernel.Domain;
import com.fleboulch.treasuremap.coordinates.Coordinates;

import java.util.Objects;

public class PlainsBox {

    private final Coordinates coordinates;

    protected PlainsBox(Coordinates coordinates) {
        this.coordinates = Domain.validateNotNull(coordinates, "Coordinates should not be null");
    }

    public boolean isInside(Dimension dimension) {
        return coordinates.hasValidCoordinates(dimension);
    }

    public BoxType getBoxType() {
        return BoxType.PLAINS;
    }

    public Coordinates coordinates() {
        return coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlainsBox plainsBox = (PlainsBox) o;
        return coordinates.equals(plainsBox.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }
}
