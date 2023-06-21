package openwrestling.model.controller;

import openwrestling.PrintGameControllerSummary;
import openwrestling.database.Database;
import openwrestling.file.Import;
import openwrestling.model.controller.nextDay.PromotionSettingsManager;
import openwrestling.model.gameObjects.EventTemplate;
import openwrestling.model.gameObjects.Worker;
import openwrestling.model.segment.constants.EventFrequency;
import openwrestling.model.segment.constants.Gender;
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

        outputDirectoryName = new SimpleDateFormat("yyyy_MM_dd HH mm").format(new Date());
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

        Worker firstWorker = gameController.getWorkerManager().getWorkers().get(0);

        assertThat(firstWorker.getName()).startsWith("C");
        assertThat(firstWorker.getName()).hasSize(13);
        assertThat(firstWorker.getWorkerID()).isEqualTo(1);
        assertThat(firstWorker.getShortName()).startsWith("J");
        assertThat(firstWorker.getShortName()).hasSize(7);
        assertThat(firstWorker.getImageFileName()).isEqualTo("None");
        assertThat(firstWorker.getImportKey()).isEqualTo(256);
        assertThat(firstWorker.getStriking()).isEqualTo(80);
        assertThat(firstWorker.getFlying()).isEqualTo(90);
        assertThat(firstWorker.getWrestling()).isEqualTo(95);
        assertThat(firstWorker.getCharisma()).isEqualTo(100);
        assertThat(firstWorker.getBehaviour()).isEqualTo(90);
        assertThat(firstWorker.getPopularity()).isEqualTo(30);
        assertThat(firstWorker.getAge()).isEqualTo(22);
        assertThat(firstWorker.getGender()).isEqualTo(Gender.MALE);
        assertThat(firstWorker.isFullTime()).isEqualTo(true);
        assertThat(firstWorker.isMainRoster()).isEqualTo(true);
        assertThat(firstWorker.getManager()).isEqualTo(null);

        assertThat(firstWorker.isHighSpots()).isFalse();
        assertThat(firstWorker.isShooting()).isFalse();
        assertThat(firstWorker.isFonzFactor()).isFalse();
        assertThat(firstWorker.isSuperstarLook()).isTrue();
        assertThat(firstWorker.isDiva()).isFalse();
        assertThat(firstWorker.isMenacing()).isFalse();
        assertThat(firstWorker.isAnnouncer()).isTrue();
        assertThat(firstWorker.isBooker()).isFalse();
        assertThat(firstWorker.isTrainer()).isTrue();

        assertThat(firstWorker.getBirthMonth()).isEqualTo(11);
        assertThat(firstWorker.getWeight()).isEqualTo("H");

        assertThat(firstWorker.isSpeaks()).isTrue();

        assertThat(firstWorker.getNationality()).isEqualTo("Other");

        assertThat(firstWorker.getWage()).isEqualTo(50000);

        assertThat(firstWorker.getPrimaryFinisherName()).startsWith("L");
        assertThat(firstWorker.getPrimaryFinisherName()).hasSize(10);
        assertThat(firstWorker.getPrimaryFinisherType()).isEqualTo("Ground");
        assertThat(firstWorker.getSecondaryFinisherName()).startsWith("L");
        assertThat(firstWorker.getSecondaryFinisherName()).hasSize(10);
        assertThat(firstWorker.getSecondaryFinisherName()).isNotEqualTo(firstWorker.getPrimaryFinisherName());
        assertThat(firstWorker.getSecondaryFinisherType()).isEqualTo("Submission");

        Worker workerWithManager = gameController.getWorkerManager().getWorker(341);

        assertThat(workerWithManager.getName()).startsWith("G");
        assertThat(workerWithManager.getName()).hasSize(14);
        assertThat(workerWithManager.getWorkerID()).isEqualTo(341);
        assertThat(workerWithManager.getShortName()).startsWith("G");
        assertThat(workerWithManager.getShortName()).hasSize(8);
        assertThat(workerWithManager.getImageFileName()).isEqualTo("None");
        assertThat(workerWithManager.getImportKey()).isEqualTo(8707);
        assertThat(workerWithManager.getStriking()).isEqualTo(8);
        assertThat(workerWithManager.getFlying()).isEqualTo(0);
        assertThat(workerWithManager.getWrestling()).isEqualTo(0);
        assertThat(workerWithManager.getCharisma()).isEqualTo(80);
        assertThat(workerWithManager.getBehaviour()).isEqualTo(86);
        assertThat(workerWithManager.getPopularity()).isEqualTo(75);
        assertThat(workerWithManager.getAge()).isEqualTo(41);
        assertThat(workerWithManager.getGender()).isEqualTo(Gender.MALE);
        assertThat(workerWithManager.isFullTime()).isEqualTo(true);
        assertThat(workerWithManager.isMainRoster()).isEqualTo(true);
        assertThat(workerWithManager.getManager().getWorkerID()).isEqualTo(246);
    }


    @Test
    public void nextDay_1997() {
        preTestState = initGameControllerFromTestDB("1997");
        gameController = initGameControllerFromTestDB("1997");
        int workerCount = 898;

        assertThat(gameController.getWorkerManager().getWorkers()).hasSize(workerCount);

    }

    @Test
    public void nextDay_2003() {
        preTestState = initGameControllerFromTestDB("2003");
        gameController = initGameControllerFromTestDB("2003");
        int workerCount = 2413;

        assertThat(gameController.getWorkerManager().getWorkers()).hasSize(workerCount);


    }


    @Test
    public void nextDay_2019() {
        preTestState = initGameControllerFromTestDB("2019 February");
        gameController = initGameControllerFromTestDB("2019 February");
        int workerCount = 2156;

        assertThat(gameController.getWorkerManager().getWorkers()).hasSize(workerCount);

    }


    @Test
    public void nextDay_2021() {
        preTestState = initGameControllerFromTestDB("2021 March");
        gameController = initGameControllerFromTestDB("2021 March");
        int workerCount = 2556;

        assertThat(gameController.getWorkerManager().getWorkers()).hasSize(workerCount);


    }


    private GameController initGameControllerFromTestDB(String dbName) {
        File importDatabase = new File("test import databases\\" + outputDirectoryName + "\\" + dbName + ".db");
        gameController = new GameController(new Database(importDatabase), false);

        gameController.loadGameDataFromDatabase();
        //gameController.initializeGameData();
        return gameController;
    }

}