package openwrestling.view.utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import openwrestling.MainApp;
import openwrestling.model.controller.GameController;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class ViewUtils {

    public static GridPane gridPaneWithColumns(int columns) {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < columns; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100);
            gridPane.getColumnConstraints().add(colConst);
        }
        return gridPane;
    }

    public static GridPane gridPaneWithDimensions(int columns, int rows) {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < columns; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100);
            gridPane.getColumnConstraints().add(colConst);
        }

        for (int i = 0; i < rows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100);
            gridPane.getRowConstraints().add(rowConst);
        }
        gridPane.setGridLinesVisible(true);
        return gridPane;
    }

    public static void inititializeRegion(Region region) {
        region.setMinWidth(Control.USE_COMPUTED_SIZE);
        region.setMinHeight(Control.USE_COMPUTED_SIZE);
        region.setPrefWidth(Control.USE_COMPUTED_SIZE);
        region.setPrefHeight(Control.USE_COMPUTED_SIZE);
        region.setMaxHeight(Double.MAX_VALUE);
        region.setMaxWidth(Double.MAX_VALUE);
    }


    public static GameScreen loadScreenFromFXML(ScreenCode code, MainApp mainApp, GameController gameController) {
        Logger logger = LogManager.getLogger("ViewUtils loadScreenFromResource()");
        FXMLLoader loader = new FXMLLoader();
        GameScreen screen = new GameScreen();
        loader.setLocation(MainApp.class.getResource(code.resourcePath()));
        try {
            screen.pane = loader.load();
        } catch (IOException ex) {
            logger.log(Level.FATAL, String.format("Error loading Screen from %s", code.resourcePath()), ex);
        }

        screen.controller = loader.getController();
        screen.controller.setDependencies(mainApp, gameController);
        screen.code = code;
        return screen;
    }

    public static GameScreen getByCode(List<GameScreen> screens, ScreenCode code) {
        for (GameScreen screen : screens) {
            if (screen.code == code) {
                return screen;
            }
        }
        return null;
    }



    public static Alert generateAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("style.css");
        return alert;
    }

    public static File createDatabaseDialog(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.titleProperty().set("Select a name and location for the new game database");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Database File", "*.db")
        );
        return fileChooser.showSaveDialog(stage);
    }


    public static void anchorRegionToParent(AnchorPane parent, Region child) {

        parent.getChildren().add(child);

        AnchorPane.setTopAnchor(child, 0.0);
        AnchorPane.setRightAnchor(child, 0.0);
        AnchorPane.setLeftAnchor(child, 0.0);
        AnchorPane.setBottomAnchor(child, 0.0);
    }



    public static void updateSelectedButton(Button button, List<Button> buttons) {

        String selectedButtonClass = "selectedButton";

        buttons.stream().filter((b) -> (b.getStyleClass().contains(selectedButtonClass))).forEach((b) -> {
            b.getStyleClass().remove(selectedButtonClass);
        });

        button.getStyleClass().add(selectedButtonClass);

    }

    public static String intToStars(int rating) {
        int stars = rating / 20;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stars; i++) {
            sb.append("*");
        }

        int quarterStars = (rating / 5) % 4;

        if (quarterStars != 0) {
            if (quarterStars == 2) {
                sb.append("1/2");
            } else {
                sb.append(String.format("%d/4", quarterStars));
            }
        }
        return sb.toString();
    }



}
