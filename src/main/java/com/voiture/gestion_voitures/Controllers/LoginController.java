package com.voiture.gestion_voitures.Controllers;

import com.voiture.gestion_voitures.Models.Model;
import com.voiture.gestion_voitures.Models.UserRole;
import com.voiture.gestion_voitures.Models.Users;
import com.voiture.gestion_voitures.Services.VoitureService;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label address_lbl;
    public TextField address_fld;
    public Label password_lbl;
    public TextField password_fld;
    public Button login_btn;
    public Label error_lbl;

    private VoitureService voitureService = new VoitureService();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*acc_selector.setItems(FXCollections.observableArrayList(AccountType.ADMIN, AccountType.USER));
        acc_selector.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
        acc_selector.valueProperty().addListener(observable -> Model.getInstance().getViewFactory().setLoginAccountType(acc_selector.getValue()));*/
        login_btn.setOnAction(event -> onLogin());
    }


    private void onLogin() {
        Stage stage = (Stage) error_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);

        String address = address_fld.getText();
        String password = password_fld.getText();

        Users user = voitureService.getUser(address);

        if (user != null){
            if (user.getPassword().equals(password)){

                if (user.getRole() == UserRole.USER) {
                    Model.getInstance().getViewFactory().showUserWindow();
                } else {
                    Model.getInstance().getViewFactory().showAdminWindow();
                }
            }else {
                error_lbl.setText("Password incorrect!");
                Model.getInstance().getViewFactory().showLoginWindow();
            }
        }else {
            error_lbl.setText("User not found!");
            Model.getInstance().getViewFactory().showLoginWindow();
        }
    }


}
