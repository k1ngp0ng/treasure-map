package com.fleboulch.treasuremap.explorer.domain;

import com.fleboulch.treasuremap.kernel.domain.Domain;
import com.fleboulch.treasuremap.map.domain.*;
import com.fleboulch.treasuremap.shared.coordinates.domain.Coordinates;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Explorer {

    // Add an UUID

    private final Name name;
    private final Coordinates coordinates;
    private final Orientation orientation;
    private final Movements movements;
    private int nbCollectedTreasures = 0;

    private Explorer(Name name, Coordinates coordinates, Orientation orientation, Movements movements) {
        this.name = Domain.validateNotNull(name, "Name should be not null");
        this.coordinates = Domain.validateNotNull(coordinates, "Coordinates should be not null");

        this.orientation = Domain.validateNotNull(orientation, "Orientation should be not null");
        this.movements = Domain.validateNotNull(movements, "Movements should be not null");
    }

    public static Explorer of(Name name, Coordinates coordinates, Orientation orientation, String rawMovements) {
        Domain.validateNotNull(rawMovements, "Raw movements should not be null");
        return new Explorer(name, coordinates, orientation, buildMovements(rawMovements));
    }

    public boolean isOnMountain(TreasureMap treasureMap) {
        PlainsBox plainsBox = currentBox(treasureMap);

        if (plainsBox instanceof MountainBox) {
            throw new InvalidCurrentPositionException(name.value(), coordinates);
        } else {
            return false;
        }
    }

    public boolean canPerformMovements() {
        return !movements().movementTypes().isEmpty();
    }

    public boolean isOnTreasure(TreasureMap treasureMap) {
        PlainsBox plainsBox = currentBox(treasureMap);

        return (plainsBox instanceof TreasureBox);
    }

    public boolean isOnPlains(TreasureMap treasureMap) {
        PlainsBox plainsBox = currentBox(treasureMap);

        return !(plainsBox instanceof TreasureBox) && !(plainsBox instanceof MountainBox);
    }

    private PlainsBox currentBox(TreasureMap treasureMap) {
        return treasureMap.from(coordinates);
    }

    private static Movements buildMovements(String rawMovements) {
        return new Movements(buildMovementTypeList(rawMovements));
    }

    private static List<MovementType> buildMovementTypeList(String rawMovements) {
        return rawMovements.chars()
                .mapToObj(explorerIndex -> toMovementType(explorerIndex, rawMovements))
                .collect(Collectors.toList());
    }

    private static MovementType toMovementType(int indexMovement, String rawMovements) {
        char movementAsChar = (char) indexMovement;
        return MovementType.valueOf(String.valueOf(movementAsChar));
    }

    public boolean hasValidCoordinates(Dimension dimension) {
        return coordinates.hasValidCoordinates(dimension);
    }

    public Name name() {
        return name;
    }

    public Coordinates coordinates() {
        return coordinates;
    }

    public Orientation orientation() {
        return orientation;
    }

    public Movements movements() {
        return movements;
    }

    public int nbCollectedTreasures() {
        return nbCollectedTreasures;
    }

    @Override
    public String toString() {
        return "Explorer{" +
                "name=" + name +
                ", coordinates=" + coordinates +
                ", orientation=" + orientation +
                ", movements=" + movements +
                ", nbCollectedTreasures=" + nbCollectedTreasures +
                '}';
    }

    public Explorer turn(MovementType direction) {
        if (Objects.equals(direction, MovementType.D)) {
            switch (orientation.orientationType()) {
                case S:
                    return buildExplorerAfterAction(OrientationType.W);
                case N:
                    return buildExplorerAfterAction(OrientationType.E);
                case E:
                    return buildExplorerAfterAction(OrientationType.S);
                case W:
                    return buildExplorerAfterAction(OrientationType.N);
            }
        }
        throw new RuntimeException("");
    }

    private Explorer buildExplorerAfterAction(OrientationType newOrientationType) {
        Movements movementsAfterAction = popMovement();
        return new Explorer(name, coordinates, new Orientation(newOrientationType), movementsAfterAction);
    }

    public Explorer goForward() {
        switch (orientation.orientationType()) {
            case S:
                return goForwardSouth();
            case N:
                return goForwardNorth();
            case E:
                return goForwardEast();
            case W:
                return goForwardWest();
            default:
                throw new RuntimeException("Unknown orientation"); // replace with custom exception
        }
    }

    private Explorer goForwardWest() {
        Coordinates newCoordinates = coordinates.goForwardWest();
        Movements movementsAfterAction = popMovement();
        return new Explorer(name, newCoordinates, orientation, movementsAfterAction);
    }

    private Explorer goForwardNorth() {
        Coordinates newCoordinates = coordinates.goForwardNorth();
        Movements movementsAfterAction = popMovement();
        return new Explorer(name, newCoordinates, orientation, movementsAfterAction);
    }

    private Explorer goForwardEast() {
        Coordinates newCoordinates = coordinates.goForwardEast();
        Movements movementsAfterAction = popMovement();
        return new Explorer(name, newCoordinates, orientation, movementsAfterAction);
    }

    private Explorer goForwardSouth() {
        Coordinates newCoordinates = coordinates.goForwardSouth();
        Movements movementsAfterAction = popMovement();
        return new Explorer(name, newCoordinates, orientation, movementsAfterAction);
    }

    private Movements popMovement() {
        return movements().popMovement();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Explorer explorer = (Explorer) o;
        return nbCollectedTreasures == explorer.nbCollectedTreasures &&
                name.equals(explorer.name) &&
                coordinates.equals(explorer.coordinates) &&
                orientation.equals(explorer.orientation) &&
                movements.equals(explorer.movements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, orientation, movements, nbCollectedTreasures);
    }
}
