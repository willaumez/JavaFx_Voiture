package com.voiture.gestion_voitures.Controllers.Admin;

import com.voiture.gestion_voitures.Models.Model;
import com.voiture.gestion_voitures.Views.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button database_btn;
    public Button excelFile_btn;
    public Button jsonFile_btn;
    public Button txtFile_btn;
    public Button logout_btn;
    private Button add_car_db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners(){
        database_btn.setOnAction(event -> onDatabase());
        excelFile_btn.setOnAction(event -> onExcelFile());
        jsonFile_btn.setOnAction(event -> onJsonFile());
        txtFile_btn.setOnAction(event -> onTxtFile());
        logout_btn.setOnAction(event -> onLogout());
    }

    private void onDatabase(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.DATABASE);
    }
    private void onExcelFile(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.EXCEL_FILE);
    }
    private void onJsonFile(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.JSON_FILE);
    }
    private void onTxtFile(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.TXT_FILE);
    }
    private void onLogout(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.LOGOUT);
    }

}
