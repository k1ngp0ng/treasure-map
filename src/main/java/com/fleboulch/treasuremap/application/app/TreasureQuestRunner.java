package com.fleboulch.treasuremap.application.app;

import com.fleboulch.treasuremap.application.domain.ExplorerOrchestrator;
import com.fleboulch.treasuremap.application.domain.TreasureQuest;
import com.fleboulch.treasuremap.explorer.domain.Explorer;
import com.fleboulch.treasuremap.explorer.domain.MovementType;
import com.fleboulch.treasuremap.explorer.domain.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TreasureQuestRunner {

    private final Logger log = LoggerFactory.getLogger(TreasureQuestRunner.class);

    public TreasureQuestRunner() {
    }

    public TreasureQuest start(TreasureQuest treasureQuest) {
        log.info("Quest is starting");
        log.info("{}", treasureQuest.treasureMap());
        log.info("Initial start for explorer 1: {}", treasureQuest.explorers().explorers().get(0));

        ExplorerOrchestrator explorerOrchestrator = new ExplorerOrchestrator(treasureQuest.explorers());

        List<TreasureQuest> quests = explorerOrchestrator.explorerNames().stream()
                .map(explorerName -> saveAction(explorerName, treasureQuest))
//                .reduce((l, r) -> r);
                .collect(Collectors.toList());

        TreasureQuest finalQuest = quests.get(quests.size() - 1);

        log.info("Final position {}", finalQuest.historyMovements().get(finalQuest.historyMovements().size() -1));
        log.info("Quest is finished");
        return finalQuest;
    }

    private TreasureQuest saveAction(Name explorerName, TreasureQuest treasureQuest) {
        Explorer currentExplorer = treasureQuest.getLastState(explorerName);

        executeAction(currentExplorer, treasureQuest);

        return treasureQuest;
    }

    private void executeAction(Explorer currentExplorer, TreasureQuest treasureQuest) {
        MovementType movementType = currentExplorer.nextMovement();
        switch (movementType) {
            case A:
                treasureQuest.goForwardAction(currentExplorer);
                break;
            case D:
                treasureQuest.turn(currentExplorer, MovementType.D);
                break;
            case G:
                treasureQuest.turn(currentExplorer, MovementType.G);
                break;
            default:
                throw new IllegalArgumentException("Unknown movement type"); // should never occured
        }

        treasureQuest.popMovementFor();

    }

}
