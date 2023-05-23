package com.voiture.gestion_voitures.Controllers.Admin;

import com.sun.javafx.scene.control.DoubleField;
import com.sun.javafx.scene.control.IntegerField;
import com.voiture.gestion_voitures.Models.Model;
import com.voiture.gestion_voitures.Models.Voiture;
import com.voiture.gestion_voitures.Services.VoitureService;
import com.voiture.gestion_voitures.Views.AdminMenuOptions;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCarController implements Initializable {


    public TextField marqueTextField;
    public TextField modeleTextField;
    public TextField matriculationTextField;
    public TextField anneeTextField;
    public TextField prixTextField;
    public TextField consommationTextField;
    public TextField puissanceTextField;
    public Button returnToDataBase;
    public Button add_car_db;
    private VoitureService voitureService = new VoitureService();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        returnToDataBase.setOnAction(event -> setView());
    }

    public void setView(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.DATABASE);
    }

    public void handleAddCarButton(ActionEvent actionEvent) {
        Voiture voiture = new Voiture();

        voiture.setMarque(marqueTextField.getText());
        voiture.setModele(modeleTextField.getText());
        voiture.setMatriculation(matriculationTextField.getText());
        voiture.setAnnee(Integer.parseInt(anneeTextField.getText()));
        voiture.setPrix(Double.parseDouble(prixTextField.getText()));
        voiture.setConsommation(Double.parseDouble(consommationTextField.getText()));
        voiture.setPuissance(Integer.parseInt(puissanceTextField.getText()));
        voitureService.save(voiture);
        setView();

    }


}
