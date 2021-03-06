package com.fleboulch.treasuremap.map;

import com.fleboulch.treasuremap.explorer.Explorer;
import com.fleboulch.treasuremap.kernel.Domain;
import com.fleboulch.treasuremap.coordinates.Coordinates;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class TreasureMap {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    private final Dimension dimension;
    private final List<MountainBox> mountainBoxes;
    private final List<TreasureBox> treasureBoxes;

    public TreasureMap(Dimension dimension, List<MountainBox> mountainBoxes, List<TreasureBox> treasureBoxes) {
        this.dimension = Domain.validateNotNull(dimension, "A map should have a dimension");

        Domain.validateNotNull(mountainBoxes, "Treasure map should have not null mountains");
        Domain.validateNotNull(treasureBoxes, "Treasure map should have not null treasures");

        checkValidBoxes(mountainBoxes, treasureBoxes);
        this.mountainBoxes = mountainBoxes;
        this.treasureBoxes = treasureBoxes;
    }

    // TODO: need to refacto this method
    public Optional<PlainsBox> from(Coordinates coordinates) {
        if (coordinates.hasValidCoordinates(dimension)) {
            Optional<MountainBox> mountain = findMountainBoxByCoordinates(coordinates);

            if (mountain.isPresent()) {
                return Optional.of(mountain.get());
            }

            Optional<TreasureBox> treasure = findTreasureBoxByCoordinates(coordinates);

            if (treasure.isPresent()) {
                return Optional.of(treasure.get());
            }

            return Optional.of(new PlainsBox(coordinates));

        } else {
            return Optional.empty();
        }
    }

    public boolean explorerIsOnMountain(Explorer currentExplorer) {
        Coordinates coordinates = currentExplorer.coordinates();

        if (findMountainBoxByCoordinates(coordinates).isPresent()) {
            throw new InvalidCurrentPositionException(currentExplorer.name().value(), coordinates);
        } else {
            return false;
        }
    }

    private Optional<MountainBox> findMountainBoxByCoordinates(Coordinates coordinates) {
        return mountainBoxes.stream()
                .filter(mountainBox -> Objects.equals(mountainBox.coordinates(), coordinates))
                .findFirst();
    }

    private Optional<TreasureBox> findTreasureBoxByCoordinates(Coordinates coordinates) {
        return treasureBoxes.stream()
                .filter(treasureBox -> Objects.equals(treasureBox.coordinates(), coordinates))
                .findFirst();
    }

    public TreasureMap removeOneTreasure(Coordinates coordinates) {
        Optional<TreasureBox> optionalTreasureBoxToUpdate = findTreasureBoxByCoordinates(coordinates);
        if (optionalTreasureBoxToUpdate.isPresent()) {
            TreasureBox treasureBoxToUpdate = optionalTreasureBoxToUpdate.get();

            List<TreasureBox> treasures = treasureBoxes.stream()
                    .map(treasureBox -> Objects.equals(treasureBox, treasureBoxToUpdate) ? treasureBox.decrementNbTreasures() : treasureBox)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            return new TreasureMap(dimension, mountainBoxes, treasures);
        }
        throw new IllegalArgumentException("These coordinates does not represent a treasure box");
    }

    private void checkValidBoxes(List<MountainBox> mountainBoxes, List<TreasureBox> treasureBoxes) {
        mountainBoxes.forEach(this::checkValidBox);
        treasureBoxes.forEach(this::checkValidBox);
    }

    private void checkValidBox(PlainsBox box) {
        if (!box.isInside(dimension)) {
            throw new BoxIsOutOfMapException(box, dimension);
        }
    }

    public Dimension dimension() {
        return dimension;
    }

    public List<MountainBox> mountainBoxes() {
        return mountainBoxes;
    }

    public List<TreasureBox> treasureBoxes() {
        return treasureBoxes;
    }

    @Override
    public String toString() {
        String mountain = mountainBoxes.stream()
                .map(MountainBox::toString)
                .collect(Collectors.joining(LINE_SEPARATOR));

        String treasures = treasureBoxes.stream()
                .map(TreasureBox::toString)
                .collect(Collectors.joining(LINE_SEPARATOR));

        return String.format("C - %s - %s %n%s%n%s",
                dimension.width(),
                dimension.height(),
                mountain,
                treasures
        );
    }
}
