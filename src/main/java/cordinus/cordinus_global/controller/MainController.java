package cordinus.cordinus_global.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


//This file is responsible for loading data applicable to Main
//Password Validation
//and Navigation
public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

//Password Validation//
    //https://www.youtube.com/watch?v=1jiuM-gNyBc
    //https://www.youtube.com/watch?v=wII6ufsn82c


    //for now use next button

    //Language Translation set by radio button, and connected to Language bundle
    //if translate is selected may have to set up screens just for french translation

    //Time Display according to time alerts videos

//Main Menu Navigation//

//Exit//


}