package com.fleboulch.treasuremap.map;

import com.fleboulch.treasuremap.kernel.Domain;
import com.fleboulch.treasuremap.coordinates.Coordinates;

import java.util.Objects;

public class TreasureBox extends PlainsBox {

    private final int nbTreasures;

    public TreasureBox(Coordinates coordinates, int nbTreasures) {
        super(coordinates);
        this.nbTreasures = Domain.validatePositive(nbTreasures, "The number of treasures should be positive");
    }

    public BoxType getBoxType() {
        return BoxType.TREASURE;
    }

    public TreasureBox decrementNbTreasures() {
        if (nbTreasures == 1) {
            return null;
        }
        return new TreasureBox(coordinates(), nbTreasures - 1);
    }

    public int nbTreasures() {
        return nbTreasures;
    }

    @Override
    public String toString() {
        return String.format("T - %s - %s", coordinates(), nbTreasures);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TreasureBox that = (TreasureBox) o;
        return nbTreasures == that.nbTreasures;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nbTreasures);
    }
}
