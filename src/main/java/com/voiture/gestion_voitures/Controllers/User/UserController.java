package com.voiture.gestion_voitures.Controllers.User;

import com.voiture.gestion_voitures.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    public BorderPane user_parent;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            switch (newVal){
                case CARS_EXCEL_FILE -> user_parent.setCenter(Model .getInstance().getViewFactory().getCarsExcelFile());
                case CARS_JSON_FILE -> user_parent.setCenter(Model .getInstance().getViewFactory().getCarsJsonFile());
                case CARS_TXT_FILE -> user_parent.setCenter(Model .getInstance().getViewFactory().getCarsTxtFileView());
                case LOGOUT -> {
                    Stage stage = (Stage) user_parent.getScene().getWindow();
                    Model.getInstance().getViewFactory().closeStage(stage);
                    Model.getInstance().getViewFactory().showLoginWindow();
                }
                default -> user_parent.setCenter(Model.getInstance().getViewFactory().getCarsDBView());
            }
        });
    }
}
