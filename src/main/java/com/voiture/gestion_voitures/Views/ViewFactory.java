package com.voiture.gestion_voitures.Views;

import com.voiture.gestion_voitures.Controllers.Admin.AdminController;
import com.voiture.gestion_voitures.Controllers.User.UserController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {
    //login

    //User-Views
    private final ObjectProperty<UserMenuOptions> userSelectedMenuItem;
    private AnchorPane carsDBView;
    private AnchorPane carsExcelFileView;
    private AnchorPane carsJsonFileView;
    private AnchorPane carsTxtFileView;

    //Admin-View
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
    private AnchorPane dataBaseView;
    private AnchorPane excelFileView;
    private AnchorPane jsonFileView;
    private AnchorPane txtFileView;

    private AnchorPane addCarView;



    public ViewFactory(){
        this.userSelectedMenuItem = new SimpleObjectProperty<>();
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
    }


    // User View Section //
    public ObjectProperty<UserMenuOptions> getUserSelectedMenuItem() {
        return userSelectedMenuItem;
    }

    public AnchorPane getCarsDBView(){
        if (carsDBView == null){
            try{
                carsDBView = new FXMLLoader(getClass().getResource("/Fxml/user/CarsDB.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return carsDBView;
    }

    public AnchorPane getCarsExcelFile() {
        if (carsExcelFileView == null){
            try {
                carsExcelFileView = new FXMLLoader(getClass().getResource("/Fxml/user/CarsExcelFile.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return carsExcelFileView;
    }

    public AnchorPane getCarsJsonFile() {
        if (carsJsonFileView == null){
            try {
                carsJsonFileView = new FXMLLoader(getClass().getResource("/Fxml/user/CarsJsonFile.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return carsJsonFileView;
    }

    public AnchorPane getCarsTxtFileView() {
        if (carsTxtFileView == null){
            try {
                carsTxtFileView = new FXMLLoader(getClass().getResource("/Fxml/user/CarsTxtFile.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return carsTxtFileView;
    }

    public void showLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/login.fxml"));
        createStage(loader);
    }


    public void showUserWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/user/User.fxml"));
        UserController userController = new UserController();
        loader.setController(userController);
        createStage(loader);

    }

    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        }catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/icon.png"))));
        stage.setResizable(false);
        stage.setTitle("My Cars");
        stage.show();
    }
    public void closeStage(Stage stage){
        stage.close();
    }





    //Admin View Section//
    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }
    public void showAdminWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);
        createStage(loader);
    }

    public AnchorPane getDataBaseView() {
        if (dataBaseView == null){
            try {
                dataBaseView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Database.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return dataBaseView;
    }

    public AnchorPane getExcelFileView() {
        if (excelFileView == null){
            try {
                excelFileView = new FXMLLoader(getClass().getResource("/Fxml/Admin/ExcelFile.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return excelFileView;
    }

    public AnchorPane getJsonFileView() {
        if (jsonFileView == null){
            try {
                jsonFileView = new FXMLLoader(getClass().getResource("/Fxml/Admin/JsonFile.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return jsonFileView;
    }

    public AnchorPane getTxtFileView() {
        if (txtFileView == null){
            try {
                txtFileView = new FXMLLoader(getClass().getResource("/Fxml/Admin/TextFile.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return txtFileView;
    }


    public AnchorPane getAddCarView(){
        if (addCarView == null){
            try {
                addCarView = new FXMLLoader(getClass().getResource("/Fxml/Admin/AddCar.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return addCarView;
    }

    /*public AnchorPane getAddCarView() {
        AnchorPane addCarView = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/AddCar.fxml"));
            addCarView = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addCarView;
    }*/



}
