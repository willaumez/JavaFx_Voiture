package com.voiture.gestion_voitures.Controllers.User;

import com.voiture.gestion_voitures.Models.Model;
import com.voiture.gestion_voitures.Views.UserMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class UserMenuController implements Initializable {
    public Button cars_db_btn;
    public Button excel_file_btn;
    public Button json_file_btn;
    public Button txt_file_btn;
    public Button profile_btn;
    public Button logout_btn;
    public Button report_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners(){
        cars_db_btn.setOnAction(event -> onCarsDb());
        excel_file_btn.setOnAction(event -> onExcelFile());
        json_file_btn.setOnAction(event -> onJsonFile());
        txt_file_btn.setOnAction(event -> onTxtFile());
        logout_btn.setOnAction(event -> onLogout());
    }
    private void  onCarsDb(){
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(UserMenuOptions.CARS_DATABASE);
    }
    private void onExcelFile(){
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(UserMenuOptions.CARS_EXCEL_FILE);
    }

    private void onJsonFile(){
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(UserMenuOptions.CARS_JSON_FILE);
    }
    private void onTxtFile(){
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(UserMenuOptions.CARS_TXT_FILE);
    }
    private void onLogout(){
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(UserMenuOptions.LOGOUT);
    }


}
