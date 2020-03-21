package com.fleboulch.treasuremap.map;

import com.fleboulch.treasuremap.coordinates.Coordinates;

public class MountainBox extends PlainsBox {

    public MountainBox(Coordinates coordinates) {
        super(coordinates);
    }

    public BoxType getBoxType() {
        return BoxType.MOUNTAIN;
    }

    @Override
    public String toString() {
        return String.format("M - %s", coordinates());
    }
}
