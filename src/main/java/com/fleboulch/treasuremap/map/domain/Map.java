package com.fleboulch.treasuremap.map.domain;

import com.fleboulch.treasuremap.kernel.domain.Domain;

import java.util.List;

public class Map {

    private final Dimension dimension;
    private final List<TreasureBox> treasureBoxes;
    private final List<MountainBox> mountainBoxes;

    public Map(Dimension dimension, List<TreasureBox> treasureBoxes, List<MountainBox> mountainBoxes) {
        this.dimension = Domain.validateNotNull(dimension, "A map should have a dimension");
        checkValidTreasures(treasureBoxes);
        this.treasureBoxes = Domain.validateNotNull(treasureBoxes, "A map should not have null treasures");
        this.mountainBoxes = Domain.validateNotNull(mountainBoxes, "A map should not have null mountains");
    }

    private void checkValidTreasures(List<TreasureBox> treasureBoxes) {
        treasureBoxes.forEach(this::checkValidTreasure);
    }

    private void checkValidTreasure(TreasureBox treasureBox) {
//        if (treasureBox.)
    }

    public Dimension dimension() {
        return dimension;
    }

    public List<TreasureBox> treasureBoxes() {
        return treasureBoxes;
    }

    public List<MountainBox> mountainBoxes() {
        return mountainBoxes;
    }
}
