package com.fleboulch.treasuremap.application.domain;

import com.fleboulch.treasuremap.explorer.Explorer;
import com.fleboulch.treasuremap.explorer.Name;
import com.fleboulch.treasuremap.kernel.Domain;
import com.fleboulch.treasuremap.map.TreasureMap;
import com.fleboulch.treasuremap.coordinates.Coordinates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HistoryTreasureQuest {

    private TreasureMap treasureMap;
    private Map<Name, List<Explorer>> historyMovementsPerExplorer = new HashMap<>();

    private HistoryTreasureQuest(TreasureMap treasureMap, List<Explorer> explorers) {
        this.treasureMap = Domain.validateNotNull(treasureMap, "Treasure map should not be null");
        this.historyMovementsPerExplorer = buildMapPerExplorer(explorers);
    }

    private Map<Name, List<Explorer>> buildMapPerExplorer(List<Explorer> explorers) {
        return explorers.stream()
                .collect(Collectors.toMap(
                        Explorer::name,
                        List::of
                        )
                );
    }

    public void removeOneTreasure(Coordinates coordinates) {
        treasureMap = treasureMap.removeOneTreasure(coordinates);
    }

    public static HistoryTreasureQuest of(TreasureQuest treasureQuest) {
        Domain.validateNotNull(treasureQuest, "History quest should have a not null treasure quest");

        return new HistoryTreasureQuest(treasureQuest.treasureMap(), treasureQuest.explorers().explorers());
    }

    public void registerMove(Explorer explorerAfterAction) {
        checkValidExplorer(explorerAfterAction);

        Name explorerName = explorerAfterAction.name();
        List<Explorer> explorerHistory = historyMovementsPerExplorer.get(explorerName);

        historyMovementsPerExplorer.compute(
                explorerName,
                (e, f) -> new ArrayList<>(explorerHistory)).add(explorerAfterAction);

    }

    public Explorer getLastState(Name explorerName) {
        List<Explorer> explorersHistory = historyMovementsPerExplorer.get(explorerName);
        return explorersHistory.get(explorersHistory.size() - 1);
    }

    private void checkValidExplorer(Explorer explorer) {
        if (!historyMovementsPerExplorer.containsKey(explorer.name())) {
            throw new ExplorerIsNotAllowedToDoThisQuestException(explorer);
        }
    }

    public TreasureMap treasureMap() {
        return treasureMap;
    }

    public Map<Name, List<Explorer>> historyMovementsPerExplorer() {
        return historyMovementsPerExplorer;
    }
}
