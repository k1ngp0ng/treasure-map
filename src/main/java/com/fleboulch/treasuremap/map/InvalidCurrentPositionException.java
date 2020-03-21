package com.fleboulch.treasuremap.map;

import com.fleboulch.treasuremap.coordinates.Coordinates;

public class InvalidCurrentPositionException extends RuntimeException {

    public InvalidCurrentPositionException(String explorerName, Coordinates explorerCoordinates) {
        super(String.format("%s cannot be on box %s because it's a mountain", explorerName, explorerCoordinates));
    }
}
