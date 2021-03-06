package com.fleboulch.treasuremap.application.infra;

import com.fleboulch.treasuremap.application.app.TreasureQuestRunner;
import com.fleboulch.treasuremap.application.domain.HistoryTreasureQuest;
import com.fleboulch.treasuremap.application.domain.TreasureQuest;
import com.fleboulch.treasuremap.application.infra.utils.CsvConverter;
import com.fleboulch.treasuremap.application.infra.utils.CsvWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InputReader {

    private final Logger log = LoggerFactory.getLogger(InputReader.class);

    private final TreasureQuestRunner treasureQuestRunner;

    public InputReader(TreasureQuestRunner treasureQuestRunner) {
        this.treasureQuestRunner = treasureQuestRunner;
    }

    public List<String> process(String filePath, String description) {

        TreasureQuest treasureQuest = buildTreasureQuestFromCsv(filePath);
        HistoryTreasureQuest historyQuest = treasureQuestRunner.start(treasureQuest);

        return csvOutput(historyQuest, description);

    }

    private TreasureQuest buildTreasureQuestFromCsv(String filePath) {
        List<String> configurationsWithoutComments = CsvConverter.toConfigurationList(filePath);
        return ApplicationFactory.toDomain(configurationsWithoutComments);
    }

    private List<String> csvOutput(HistoryTreasureQuest historyQuest, String description) {
        List<String> exposition = ApplicationFactory.toExposition(historyQuest);
        String outputPathName = CsvWriter.csvOutput(exposition, description);
        log.info("'{}' output has been generated into '{}'", description, outputPathName);

        return exposition;
    }

}
