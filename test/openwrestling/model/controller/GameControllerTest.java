package openwrestling.model.controller;

import openwrestling.database.Database;
import openwrestling.file.Import;
import openwrestling.model.controller.nextDay.PromotionSettingsManager;
import openwrestling.model.gameObjects.Contract;
import openwrestling.model.gameObjects.Promotion;
import openwrestling.model.gameObjects.Worker;
import openwrestling.model.segment.constants.Gender;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
            List<String> sourceImportDataDirectories = List.of(
                 //   "1993",
         //           "1997"
           //         ,
                //    "2003"
                //    ,
                 //   "2019 February",
                    "2021 March"
            );
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

        firstWorker1993();
        workerWithManager1993();

    }

    private void firstWorker1993() {
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

        List<Contract> contracts = gameController.getContractManager().getContracts(firstWorker);
        assertThat(contracts.size()).isEqualTo(1);
        Contract contract = contracts.get(0);
        assertThat(contract.getPushLevel()).isEqualTo(4);
        assertThat(contract.getDisposition()).isEqualTo("F");


    }

    private void workerWithManager1993() {
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

        List<Contract> contracts = gameController.getContractManager().getContracts(workerWithManager);
        assertThat(contracts.size()).isEqualTo(1);
        Contract contract = contracts.get(0);
        assertThat(contract.getPushLevel()).isEqualTo(2);
        assertThat(contract.getDisposition()).isEqualTo("H");
    }


    @Test
    public void nextDay_1997() {
        preTestState = initGameControllerFromTestDB("1997");
        gameController = initGameControllerFromTestDB("1997");
        int workerCount = 898;

        assertThat(gameController.getWorkerManager().getWorkers()).hasSize(workerCount);

        Worker firstWorker = gameController.getWorkerManager().getWorkers().get(0);

        assertThat(firstWorker.getName()).startsWith("C");
        assertThat(firstWorker.getName()).hasSize(13);
        assertThat(firstWorker.getWorkerID()).isEqualTo(1);
        assertThat(firstWorker.getShortName()).startsWith("J");
        assertThat(firstWorker.getShortName()).hasSize(7);
        assertThat(firstWorker.getImageFileName()).startsWith("C");
        assertThat(firstWorker.getImageFileName()).endsWith(".jpg");
        assertThat(firstWorker.getImageFileName()).hasSize(17);
        assertThat(firstWorker.getImportKey()).isEqualTo(256);
        assertThat(firstWorker.getStriking()).isEqualTo(68);
        assertThat(firstWorker.getFlying()).isEqualTo(88);
        assertThat(firstWorker.getWrestling()).isEqualTo(84);
        assertThat(firstWorker.getStiffness()).isEqualTo(54);
        assertThat(firstWorker.getSelling()).isEqualTo(93);
        assertThat(firstWorker.getCharisma()).isEqualTo(93);
        assertThat(firstWorker.getBehaviour()).isEqualTo(95);
        assertThat(firstWorker.getAttitude()).isEqualTo(95);
        assertThat(firstWorker.getPopularity()).isEqualTo(45);
        assertThat(firstWorker.getAge()).isEqualTo(27);
        assertThat(firstWorker.getGender()).isEqualTo(Gender.MALE);
        assertThat(firstWorker.isFullTime()).isEqualTo(true);
        assertThat(firstWorker.isMainRoster()).isEqualTo(true);
        assertThat(firstWorker.getManager()).isEqualTo(null);

        assertThat(firstWorker.isHighSpots()).isFalse();
        assertThat(firstWorker.isShooting()).isFalse();
        assertThat(firstWorker.isFonzFactor()).isTrue();
        assertThat(firstWorker.isSuperstarLook()).isTrue();
        assertThat(firstWorker.isDiva()).isFalse();
        assertThat(firstWorker.isMenacing()).isFalse();
        assertThat(firstWorker.isAnnouncer()).isTrue();
        assertThat(firstWorker.isBooker()).isFalse();
        assertThat(firstWorker.isTrainer()).isFalse();

        assertThat(firstWorker.getBirthMonth()).isEqualTo(11);
        assertThat(firstWorker.getWeight()).isEqualTo("L");

        assertThat(firstWorker.isSpeaks()).isTrue();

        assertThat(firstWorker.getNationality()).isEqualTo("American");

        assertThat(firstWorker.getWage()).isEqualTo(25000);

        assertThat(firstWorker.getPrimaryFinisherName()).startsWith("L");
        assertThat(firstWorker.getPrimaryFinisherName()).hasSize(10);
        assertThat(firstWorker.getPrimaryFinisherType()).isEqualTo("Submission");
        assertThat(firstWorker.getSecondaryFinisherName()).startsWith("L");
        assertThat(firstWorker.getSecondaryFinisherName()).hasSize(10);
        assertThat(firstWorker.getSecondaryFinisherName()).isNotEqualTo(firstWorker.getPrimaryFinisherName());
        assertThat(firstWorker.getSecondaryFinisherType()).isEqualTo("Ground");

    }

    @Test
    public void nextDay_2003() {
        preTestState = initGameControllerFromTestDB("2003");
        gameController = initGameControllerFromTestDB("2003");
        int workerCount = 2413;

        assertThat(gameController.getWorkerManager().getWorkers()).hasSize(workerCount);

        Worker firstWorker = gameController.getWorkerManager().getWorkers().get(0);

        assertThat(firstWorker.getName()).startsWith("C");
        assertThat(firstWorker.getName()).hasSize(13);
        assertThat(firstWorker.getWorkerID()).isEqualTo(1);
        assertThat(firstWorker.getShortName()).startsWith("J");
        assertThat(firstWorker.getShortName()).hasSize(7);
        assertThat(firstWorker.getImageFileName()).isEqualTo("None");
        assertThat(firstWorker.getImportKey()).isEqualTo(256);
        assertThat(firstWorker.getStriking()).isEqualTo(70);
        assertThat(firstWorker.getFlying()).isEqualTo(88);
        assertThat(firstWorker.getWrestling()).isEqualTo(92);
        assertThat(firstWorker.getStiffness()).isEqualTo(60);
        assertThat(firstWorker.getSelling()).isEqualTo(90);
        assertThat(firstWorker.getCharisma()).isEqualTo(100);
        assertThat(firstWorker.getBehaviour()).isEqualTo(100);
        assertThat(firstWorker.getAttitude()).isEqualTo(100);
        assertThat(firstWorker.getPopularity()).isEqualTo(97);
        assertThat(firstWorker.getAge()).isEqualTo(32);
        assertThat(firstWorker.getGender()).isEqualTo(Gender.MALE);
        assertThat(firstWorker.isFullTime()).isEqualTo(true);
        assertThat(firstWorker.isMainRoster()).isEqualTo(true);
        assertThat(firstWorker.getManager()).isEqualTo(null);

        assertThat(firstWorker.isHighSpots()).isFalse();
        assertThat(firstWorker.isShooting()).isFalse();
        assertThat(firstWorker.isFonzFactor()).isTrue();
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

        assertThat(firstWorker.getWage()).isEqualTo(190000);

        assertThat(firstWorker.getPrimaryFinisherName()).startsWith("L");
        assertThat(firstWorker.getPrimaryFinisherName()).hasSize(10);
        assertThat(firstWorker.getPrimaryFinisherType()).isEqualTo("Ground");
        assertThat(firstWorker.getSecondaryFinisherName()).startsWith("W");
        assertThat(firstWorker.getSecondaryFinisherName()).hasSize(16);
        assertThat(firstWorker.getSecondaryFinisherName()).isNotEqualTo(firstWorker.getPrimaryFinisherName());
        assertThat(firstWorker.getSecondaryFinisherType()).isEqualTo("Submission");

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

        femaleWrestlerTest2021();

        Worker worker = gameController.getWorkerManager().getWorker(339);

        assertThat(worker.getName()).startsWith("K");
        assertThat(worker.getName()).hasSize(11);
        assertThat(worker.getWorkerID()).isEqualTo(339);
        assertThat(worker.getShortName()).startsWith("O");
        assertThat(worker.getShortName()).hasSize(5);
        assertThat(worker.getImageFileName()).startsWith("K");
        assertThat(worker.getImageFileName()).endsWith(".jpg");
        assertThat(worker.getImageFileName()).hasSize(15);
        assertThat(worker.getImportKey()).isEqualTo(43272);
        assertThat(worker.getStriking()).isEqualTo(78);
        assertThat(worker.getFlying()).isEqualTo(86);
        assertThat(worker.getWrestling()).isEqualTo(87);
        assertThat(worker.getStiffness()).isEqualTo(54);
        assertThat(worker.getSelling()).isEqualTo(91);
        assertThat(worker.getCharisma()).isEqualTo(92);
        assertThat(worker.getBehaviour()).isEqualTo(87);
        assertThat(worker.getAttitude()).isEqualTo(91);
        assertThat(worker.getPopularity()).isEqualTo(86);
        assertThat(worker.getAge()).isEqualTo(37);
        assertThat(worker.getGender()).isEqualTo(Gender.MALE);
        assertThat(worker.isFullTime()).isEqualTo(true);
        assertThat(worker.isMainRoster()).isEqualTo(true);

        assertThat(worker.getManager().getWorkerID()).isEqualTo(2521);
        Worker manager = gameController.getWorkerManager().getWorker(2521);
        assertThat(manager.getName()).startsWith("D");

        assertThat(worker.isHighSpots()).isTrue();
        assertThat(worker.isShooting()).isFalse();
        assertThat(worker.isFonzFactor()).isTrue();
        assertThat(worker.isSuperstarLook()).isTrue();
        assertThat(worker.isDiva()).isFalse();
        assertThat(worker.isMenacing()).isFalse();
        assertThat(worker.isAnnouncer()).isFalse();
        assertThat(worker.isBooker()).isFalse();
        assertThat(worker.isTrainer()).isTrue();

        assertThat(worker.getBirthMonth()).isEqualTo(10);
        assertThat(worker.getWeight()).isEqualTo("L");

        assertThat(worker.isSpeaks()).isTrue();

        assertThat(worker.getNationality()).isEqualTo("Canadian");

        assertThat(worker.getWage()).isEqualTo(45000);

        assertThat(worker.getPrimaryFinisherName()).startsWith("O");
        assertThat(worker.getPrimaryFinisherName()).hasSize(17);
        assertThat(worker.getPrimaryFinisherType()).isEqualTo("Impact");
        assertThat(worker.getSecondaryFinisherName()).startsWith("V");
        assertThat(worker.getSecondaryFinisherName()).hasSize(9);
        assertThat(worker.getSecondaryFinisherType()).isEqualTo("Impact");

        List<Contract> contracts = gameController.getContractManager().getContracts(worker);
        assertThat(contracts.size()).isEqualTo(3);
        Contract contract1 = contracts.stream().filter(contract -> contract.getPromotion().getName().startsWith("Al")).findFirst().orElseThrow();

        Promotion promotion1 = gameController.getPromotionManager().getPromotion(contract1.getPromotion().getPromotionID());
        assertThat(promotion1.getName()).startsWith("Al");

        assertThat(contract1.getPushLevel()).isEqualTo(1);
        assertThat(contract1.getDisposition()).isEqualTo("H");
        assertThat(contract1.getManagerID()).isEqualTo(2521);
        assertThat(contract1.getGimmickID()).isEqualTo(53);
        assertThat(contract1.isUnsackable()).isFalse();
        assertThat(contract1.isCreativeControl()).isFalse();


        Contract contract2 = contracts.stream().filter(contract -> contract.getPromotion().getName().startsWith("Lu")).findFirst().orElseThrow();

        Promotion promotion2 = gameController.getPromotionManager().getPromotion(contract2.getPromotion().getPromotionID());
        assertThat(promotion2.getName()).startsWith("Lu");

        assertThat(contract2.getPushLevel()).isEqualTo(1);
        assertThat(contract2.getDisposition()).isEqualTo("H");
        assertThat(contract2.getGimmickID()).isEqualTo(35);
        assertThat(contract2.isUnsackable()).isFalse();
        assertThat(contract2.isCreativeControl()).isFalse();


        Contract contract3 = contracts.stream().filter(contract -> contract.getPromotion().getName().startsWith("Im")).findFirst().orElseThrow();

        Promotion promotion3 = gameController.getPromotionManager().getPromotion(contract3.getPromotion().getPromotionID());
        assertThat(promotion3.getName()).startsWith("Im");

        assertThat(contract3.getPushLevel()).isEqualTo(1);
        assertThat(contract3.getDisposition()).isEqualTo("H");
        assertThat(contract3.getGimmickID()).isEqualTo(11);
        assertThat(contract3.isUnsackable()).isFalse();
        assertThat(contract3.isCreativeControl()).isFalse();


        workerWithSpecialContract();
    }

    void workerWithSpecialContract() {
        Worker worker = gameController.getWorkerManager().getWorker(2);

        assertThat(worker.getName()).startsWith("T");
        assertThat(worker.getName()).hasSize(8);


        List<Contract> contracts = gameController.getContractManager().getContracts(worker);
        assertThat(contracts.size()).isEqualTo(1);
        Contract contract = contracts.get(0);

        assertThat(contract.isCreativeControl()).isTrue();
        assertThat(contract.isUnsackable()).isTrue();
        assertThat(contract.getPushLevel()).isEqualTo(25);
        assertThat(contract.getDisposition()).isEqualTo("T");
        assertThat(contract.getGimmickID()).isEqualTo(66);
    }

    void femaleWrestlerTest2021() {
        Worker worker = gameController.getWorkerManager().getWorker(1274);

        assertThat(worker.getName()).startsWith("D");
        assertThat(worker.getName()).hasSize(15);
        assertThat(worker.getWorkerID()).isEqualTo(1274);
        assertThat(worker.getShortName()).startsWith("D");
        assertThat(worker.getShortName()).hasSize(6);
        assertThat(worker.getImageFileName()).startsWith("D");
        assertThat(worker.getImageFileName()).endsWith(".jpg");
        assertThat(worker.getImageFileName()).hasSize(18);
        assertThat(worker.getImportKey()).isEqualTo(44292);
        assertThat(worker.getStriking()).isEqualTo(47);
        assertThat(worker.getFlying()).isEqualTo(62);
        assertThat(worker.getWrestling()).isEqualTo(67);
        assertThat(worker.getStiffness()).isEqualTo(33);
        assertThat(worker.getSelling()).isEqualTo(72);
        assertThat(worker.getCharisma()).isEqualTo(72);
        assertThat(worker.getBehaviour()).isEqualTo(85);
        assertThat(worker.getAttitude()).isEqualTo(85);
        assertThat(worker.getPopularity()).isEqualTo(50);
        assertThat(worker.getAge()).isEqualTo(26);
        assertThat(worker.getGender()).isEqualTo(Gender.FEMALE);
        assertThat(worker.isFullTime()).isEqualTo(true);
        assertThat(worker.isMainRoster()).isEqualTo(true);

        assertThat(worker.getManager()).isNull();


        assertThat(worker.isHighSpots()).isFalse();
        assertThat(worker.isShooting()).isFalse();
        assertThat(worker.isFonzFactor()).isFalse();
        assertThat(worker.isSuperstarLook()).isTrue();
        assertThat(worker.isDiva()).isTrue();
        assertThat(worker.isMenacing()).isFalse();
        assertThat(worker.isAnnouncer()).isFalse();
        assertThat(worker.isBooker()).isFalse();
        assertThat(worker.isTrainer()).isFalse();

        assertThat(worker.getBirthMonth()).isEqualTo(6);
        assertThat(worker.getWeight()).isEqualTo("L");

        assertThat(worker.isSpeaks()).isTrue();

        assertThat(worker.getNationality()).isEqualTo("American");

        assertThat(worker.getWage()).isEqualTo(20000);

        assertThat(worker.getPrimaryFinisherName()).startsWith("C");
        assertThat(worker.getPrimaryFinisherType()).isEqualTo("Impact");
        assertThat(worker.getSecondaryFinisherName()).startsWith("V");
        assertThat(worker.getSecondaryFinisherType()).isEqualTo("Submission");
    }


    private GameController initGameControllerFromTestDB(String dbName) {
        File importDatabase = new File("test import databases\\" + outputDirectoryName + "\\" + dbName + ".db");
        gameController = new GameController(new Database(importDatabase), false);

        gameController.loadGameDataFromDatabase();
        //gameController.initializeGameData();
        return gameController;
    }

}