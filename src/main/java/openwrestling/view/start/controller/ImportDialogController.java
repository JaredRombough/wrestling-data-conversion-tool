package openwrestling.view.start.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import openwrestling.view.utility.interfaces.ControllerBase;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class ImportDialogController extends ControllerBase implements Initializable {



    @FXML
    private Button dataPathButton;

    @FXML
    private Button startGameButton;



    @FXML
    private Text dataPathLabel;

    private File picsPath;
    private File logosPath;
    private File dataPath;

    private Stage stage;

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {


            if (event.getSource() == dataPathButton) {
            dataPath = chooseDirectory(dataPath);
        } else if (event.getSource() == startGameButton) {
            stage.close();
            mainApp.newImportGame(dataPath, picsPath, logosPath);
        }
        updateLabels();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private File chooseDirectory(File original) {

        DirectoryChooser dc = new DirectoryChooser();
        dc.titleProperty().set("Select Folder Containing Scenario .DAT Files");

        File importFolder = dc.showDialog(mainApp.getPrimaryStage());

        if (importFolder == null) {
            importFolder = original;

        }

        return importFolder;
    }

    @Override
    public void updateLabels() {

        dataPathLabel.setText(dataPath.toString());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        picsPath = new File(System.getProperty("user.dir") + "/PICS/");
        logosPath = new File(System.getProperty("user.dir") + "/LOGOS/");
        dataPath = new File(System.getProperty("user.dir") + "/DATA/");
    }
}
