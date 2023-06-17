package openwrestling.model.controller;

import openwrestling.PrintGameControllerSummary;
import openwrestling.database.Database;
import openwrestling.file.Import;
import openwrestling.model.controller.nextDay.PromotionSettingsManager;
import openwrestling.model.gameObjects.EventTemplate;
import openwrestling.model.segment.constants.EventFrequency;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class GameControllerTest {

    private String outputDirectoryName;
    private PromotionSettingsManager promotionSettingsManager;
    private GameController gameController;
    private GameController preTestState;
    private final boolean doImport = true;

    int TEST_DAYS = 365;

    @Before
    public void setUp() throws IOException {
        File sourceDirectory = new File("test import databases\\clean");

        if (doImport) {
            List<String> sourceImportDataDirectories = List.of("1993", "1997", "2003", "2019 February", "2021 March");
            sourceImportDataDirectories.forEach(importSourceDirectoryName -> {
                File importSourceDirectory = new File("test import databases\\" + importSourceDirectoryName + "\\DATA");

                Import importer = new Import();
                File dbFile = new File(sourceDirectory + "\\" + importSourceDirectoryName + ".db");
                try {
                    importer.tryImport(dbFile, importSourceDirectory);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("setup failed");
                }
            });
        }

        outputDirectoryName = new SimpleDateFormat("yyyy_MM_dd HH mm'.txt'").format(new Date());
        File destinationDirectory = new File("test import databases\\" + outputDirectoryName);
        destinationDirectory.mkdir();

        FileUtils.copyDirectory(sourceDirectory, destinationDirectory);

        promotionSettingsManager = new PromotionSettingsManager();
    }

    @After
    public void after() {
//        PrintGameControllerSummary printGameControllerSummary = new PrintGameControllerSummary();
//        printGameControllerSummary.printSummary(gameController, preTestState);
    }


    @Test
    public void nextDay_1993() {
        preTestState = initGameControllerFromTestDB("1993");
        gameController = initGameControllerFromTestDB("1993");
        int workerCount = 430;

        assertThat(gameController.getWorkerManager().getWorkers()).hasSize(workerCount);
//
//        for (int i = 0; i < TEST_DAYS; i++) {
//            //gameController.nextDay();
//        }
//
//        assertAfterState(workerCount);

    }


    @Test
    public void nextDay_1997() {
        preTestState = initGameControllerFromTestDB("1997");
        gameController = initGameControllerFromTestDB("1997");
        int workerCount = 898;

        assertThat(gameController.getWorkerManager().getWorkers()).hasSize(workerCount);
//
//        for (int i = 0; i < TEST_DAYS; i++) {
//            //gameController.nextDay();
//        }
//
//        assertAfterState(workerCount);

    }

    @Test
    public void nextDay_2003() {
        preTestState = initGameControllerFromTestDB("2003");
        gameController = initGameControllerFromTestDB("2003");
        int workerCount = 2413;

        assertThat(gameController.getWorkerManager().getWorkers()).hasSize(workerCount);

//        for (int i = 0; i < TEST_DAYS; i++) {
//            //gameController.nextDay();
//        }
//
//        assertAfterState(workerCount);

    }


    @Test
    public void nextDay_2019() {
        preTestState = initGameControllerFromTestDB("2019 February");
        gameController = initGameControllerFromTestDB("2019 February");
        int workerCount = 2156;

        assertThat(gameController.getWorkerManager().getWorkers()).hasSize(workerCount);
//
//        for (int i = 0; i < TEST_DAYS; i++) {
//            //gameController.nextDay();
//        }
//
//        assertAfterState(workerCount);

    }


    @Test
    public void nextDay_2021() {
        preTestState = initGameControllerFromTestDB("2021 March");
        gameController = initGameControllerFromTestDB("2021 March");
        int workerCount = 2556;

        assertThat(gameController.getWorkerManager().getWorkers()).hasSize(workerCount);

//        for (int i = 0; i < TEST_DAYS; i++) {
//            //gameController.nextDay();
//        }
//
//        assertAfterState(workerCount);
    }


    private void assertAfterState(int workerCount) {
        assertThat(gameController.getPromotionManager().getPromotions()).isNotEmpty();
        gameController.getPromotionManager().getPromotions().forEach(promotion -> {
            assertThat(promotion.getName()).isNotBlank();
            assertThat(promotion.getPromotionID()).isNotZero().isNotNull();

            List<EventTemplate> weeklyEventTemplates = gameController.getEventManager().getEventTemplates(promotion).stream().filter(eventTemplate -> eventTemplate.getEventFrequency().equals(EventFrequency.WEEKLY)).collect(Collectors.toList());
//            if (CollectionUtils.isNotEmpty(weeklyEventTemplates)) {
//                weeklyEventTemplates.forEach(eventTemplate ->
//                        assertThat(gameController.getFeudManager().getActiveFeuds(eventTemplate)).hasSizeBetween(4, 10)
//                );
//            } else {
//                assertThat(gameController.getFeudManager().getActiveFeuds(promotion)).hasSizeBetween(4, 10);
//            }

            assertThat(gameController.getWorkerManager().getRoster(promotion)).hasSizeGreaterThanOrEqualTo(promotionSettingsManager.getMinimumRosterSize(promotion));
        });


        assertThat(gameController.getWorkerManager().getWorkers()).hasSize(workerCount);
        gameController.getWorkerManager().getWorkers().forEach(worker -> {
            assertThat(worker.getName()).isNotBlank();
            assertThat(worker.getWorkerID()).isNotZero().isNotNull();
        });

        assertThat(gameController.getEventManager().getEvents()).isNotEmpty();
        gameController.getEventManager().getEvents().forEach(event -> {
            assertThat(event.getEventID()).isNotZero().isNotNull();
            assertThat(event.getName()).isNotBlank();
            //TODO some events are empty because of 2 events on same date
        });

        assertThat(gameController.getSegmentManager().getSegments()).isNotEmpty();
        gameController.getSegmentManager().getSegments().forEach(segment -> {
            assertThat(segment.getSegmentID()).isNotZero().isNotNull();
            assertThat(segment.getSegmentTeams()).hasSize(2);
            assertThat(segment.getSegmentTeams().get(0).getWorkers().size()).isNotZero();
            assertThat(segment.getSegmentTeams().get(0).getWorkers().size()).isEqualTo(segment.getSegmentTeams().get(0).getWorkers().size());
        });
    }

    private GameController initGameControllerFromTestDB(String dbName) {
        File importDatabase = new File("test import databases\\" + outputDirectoryName + "\\" + dbName + ".db");
        gameController = new GameController(new Database(importDatabase), false);

        gameController.loadGameDataFromDatabase();
        //gameController.initializeGameData();
        return gameController;
    }

}