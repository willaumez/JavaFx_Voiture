package com.voiture.gestion_voitures.Controllers.Admin;

import com.voiture.gestion_voitures.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public BorderPane admin_parent;
    public static BorderPane getAdmin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((observableValue, oldValue, newValue) -> {
            switch (newValue){
                case EXCEL_FILE -> admin_parent.setCenter(Model.getInstance().getViewFactory().getExcelFileView());
                case JSON_FILE -> admin_parent.setCenter(Model.getInstance().getViewFactory().getJsonFileView());
                case TXT_FILE -> admin_parent.setCenter(Model.getInstance().getViewFactory().getTxtFileView());
                case LOGOUT -> {
                    Stage stage = (Stage) admin_parent.getScene().getWindow();
                    Model.getInstance().getViewFactory().closeStage(stage);
                    Model.getInstance().getViewFactory().showLoginWindow();
                }
                default -> admin_parent.setCenter(Model.getInstance().getViewFactory().getDataBaseView());
            }
        });

        getAdmin = admin_parent;
    }

    public static void setAdmin_parent_To_Add(){
        getAdmin.setCenter(Model.getInstance().getViewFactory().getAddCarView());
    }


}
