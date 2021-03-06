package com.fleboulch.treasuremap.application.domain;

import com.fleboulch.treasuremap.explorer.Explorer;
import com.fleboulch.treasuremap.kernel.Domain;
import com.fleboulch.treasuremap.map.Dimension;
import com.fleboulch.treasuremap.map.TreasureMap;

import java.util.List;

public class TreasureQuest {

    private final TreasureMap treasureMap;
    private final Explorers explorers;

    public TreasureQuest(TreasureMap treasureMap, Explorers explorers) {
        this.treasureMap = Domain.validateNotNull(treasureMap, "Quest should have a not null treasure map");
        Domain.validateNotNull(explorers, "Quest should have not null explorers");
        validateStartingCoordinatesFor(explorers.explorers());
        this.explorers = explorers;
    }

    private void validateStartingCoordinatesFor(List<Explorer> explorers) {
        explorers.forEach(this::validateStartingCoordinatesFor);
    }

    private void validateStartingCoordinatesFor(Explorer explorer) {
        Dimension dimension = treasureMap.dimension();
        if (!explorer.hasValidCoordinates(dimension)) {
            throw new ExplorerIsOutOfMapException(explorer, dimension);
        }
        treasureMap.explorerIsOnMountain(explorer);
    }

    public TreasureMap treasureMap() {
        return treasureMap;
    }

    public Explorers explorers() {
        return explorers;
    }

    @Override
    public String toString() {
        return "TreasureQuest{" +
                "treasureMap= \n" + treasureMap +
                "explorers= \n" + explorers +
                '}';
    }
}
