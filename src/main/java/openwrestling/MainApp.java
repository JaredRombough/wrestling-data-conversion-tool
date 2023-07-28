package openwrestling;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import openwrestling.file.Import;
import openwrestling.model.controller.GameController;
import openwrestling.view.start.controller.ImportDialogController;
import openwrestling.view.utility.GameScreen;
import openwrestling.view.utility.ScreenCode;
import openwrestling.view.utility.ViewUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainApp extends Application {

    public static final String CONTACT = "OpenWrestling@gmail.com or /u/OpenWrestling on Reddit";
    public static final String VERSION = "0.5.1";

    private static final int WINDOW_MIN_WIDTH = 1600;
    private static final int WINDOW_MIN_HEIGHT = 900;
    private static final int PRE_RUN_DAYS = 0;
    private static final int DEMO_WORKER_IMAGES = 0;
    private final transient Logger logger;
    private final List<GameScreen> screens;
    @Getter
    private final ResourceBundle resx;
    @Getter
    private Stage primaryStage;
    private GameController gameController;
    private File picsFolder;
    private File logosFolder;
    private File dataFolder;

    @Getter
    private boolean randomGame;
    private GameScreen currentScreen;
    @Getter
    private double currentStageWidth;
    private double currentStageHeight;

    public MainApp() {
        this.screens = new ArrayList<>();


        logger = LogManager.getLogger(getClass());
        Configurator.setRootLevel(Level.DEBUG);
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.out.println("Caught " + e);
            System.out.println(ExceptionUtils.getStackTrace(e));
        });
        logger.log(Level.INFO, "Logger online. Running version " + VERSION);

        Locale locale = new Locale("en", "US");

        resx = ResourceBundle.getBundle("openwrestling.Language", locale);

        currentStageWidth = WINDOW_MIN_WIDTH;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        primaryStage = stage;
        primaryStage.setTitle("Open Wrestling " + VERSION);
        primaryStage.setMinWidth(WINDOW_MIN_WIDTH);
        primaryStage.setMinHeight(WINDOW_MIN_HEIGHT);
        //primaryStage.setResizable(false);

        ChangeListener<Number> stageHeightListener = ((observable, oldValue, newValue) -> {
            currentStageHeight = newValue.doubleValue();
            if (currentScreen != null) {
                updateLabels(currentScreen.code);
            }

        });
        ChangeListener<Number> stageWidthListener = ((observable, oldValue, newValue) -> {
            currentStageWidth = newValue.doubleValue();
            if (currentScreen != null) {

                updateLabels(currentScreen.code);
            }

        });

        primaryStage.widthProperty().addListener(stageWidthListener);
        primaryStage.heightProperty().addListener(stageHeightListener);

        showTitleScreen();

    }



    public void newImportGame(File dataFolder, File picsFolder, File logosFolder) throws Exception {
        File dbFile = ViewUtils.createDatabaseDialog(primaryStage);
        if (dbFile != null) {
            randomGame = false;
            this.dataFolder = dataFolder;
            this.picsFolder = picsFolder;
            this.logosFolder = logosFolder;

            Import importer = new Import();

            String error = "";
            try {

                error = importer.tryImport(dbFile, dataFolder);

                if (!error.isEmpty()) {

                    logger.log(Level.ERROR, error);

                    ViewUtils.generateAlert("Import error", "Resources could not be validated.", error).showAndWait();

                }
            } catch (Exception ex) {

                logger.log(Level.ERROR, error, ex);

                ViewUtils.generateAlert("Import error", "Resources could not be validated.", error + "\n" + ex.getMessage()).showAndWait();

                throw ex;
            }
        }
    }




    private void showTitleScreen() {

            Stage importPopup = new Stage();
            importPopup.setResizable(false);

            importPopup.initModality(Modality.APPLICATION_MODAL);
            importPopup.setTitle("New Import Game");

            GameScreen importDialog = ViewUtils.loadScreenFromFXML(ScreenCode.IMPORT_DIALOG, this, gameController);
            importDialog.controller.updateLabels();
            ((ImportDialogController) importDialog.controller).setStage(importPopup);

            Scene importScene = new Scene(importDialog.pane);

            importScene.getStylesheets().add("style.css");

            importPopup.setScene(importScene);

            importPopup.showAndWait();

        }




    public GameScreen show(ScreenCode code) {
        if (currentScreen != null) {
            currentScreen.controller.focusLost();
        }

        GameScreen screen = ViewUtils.getByCode(screens, code);
        currentScreen = screen;


        updateLabels();
        return screen;

    }

    public void show(ScreenCode code, Object obj) {
        GameScreen screen = show(code);
        screen.controller.setCurrent(obj);
    }


    /*
    calls the root layout to update the labels
    most/all controllers have a link to the main app
    so if they cause any change that needs to be reflected
    in labels outside of their screens it can be handled here
     */
    private void updateLabels() {
        logger.log(Level.DEBUG, "updateLabels");
        for (GameScreen screen : screens) {
            if (screen.code.alwaysUpdate()) {
                updateLabels(screen.code);
            }
        }
    }

    public void updateLabels(ScreenCode code) {
        GameScreen screen = ViewUtils.getByCode(screens, code);
        GameScreen root = ViewUtils.getByCode(screens, ScreenCode.ROOT);
        if (screen != null) {
            screen.controller.updateLabels();
            root.controller.updateLabels();
        }

    }




    /**
     * @return the picsFolder
     */
    public File getPicsFolder() {
        return (dataFolder == null) ? new File(System.getProperty("user.dir") + "/PICS/") : picsFolder;
    }

    /**
     * @return the logosFolder
     */
    public File getLogosFolder() {
        return (dataFolder == null) ? new File(System.getProperty("user.dir") + "/LOGOS/") : logosFolder;
    }

    /**
     * @return the dataFolder
     */
    public File getDataFolder() {

        return dataFolder == null ? new File(System.getProperty("user.dir") + "/DATA/") : dataFolder;
    }

}
