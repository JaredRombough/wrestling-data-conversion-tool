package openwrestling.view.utility.interfaces;

import javafx.fxml.Initializable;
import openwrestling.MainApp;
import openwrestling.model.controller.GameController;

import java.util.ResourceBundle;

public abstract class ControllerBase implements Initializable {

    public MainApp mainApp;
    public GameController gameController;

    public void setDependencies(MainApp mainApp, GameController gameController) {
        this.gameController = gameController;
        this.mainApp = mainApp;
    }

    public void setCurrent(Object obj) {};

    public void updateLabels() {};
    
    public void focusLost() {};


}
