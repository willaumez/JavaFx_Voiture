package com.voiture.gestion_voitures.Controllers.Admin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.voiture.gestion_voitures.Models.Voiture;
import com.voiture.gestion_voitures.Services.VoitureService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TextFileController implements Initializable {
    @FXML
    public Button data_to_text;
    @FXML
    public Button data_from_text;
    @FXML
    public Button save_txt_file;
    @FXML
    private TextArea txt_text_area;

    private VoitureService voitureService = new VoitureService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTextArea();
    }

    public void updateTextArea(){
        String elements = voitureService.readTxt();
        txt_text_area.setText(elements);
    }

    @FXML
    public void saveTxtToFile() {
        String txt_file = txt_text_area.getText();
        voitureService.txt_save(txt_file);
    }

    @FXML
    public void handleDataToTxt(ActionEvent actionEvent) {
        try {
            voitureService.dataToTxt();
            updateTextArea();
            System.out.println("Importation Données Reussi.");
        } catch (Exception e) {
            System.out.println("Erreur importation Données .");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDataFromTxt(ActionEvent actionEvent) {
        try {
            String[] lines = txt_text_area.getText().split("\n");
            voitureService.dataFromTxt(lines);
            updateTextArea();
            System.out.println("Exportation Données Reussi.");
        } catch (Exception e) {
            System.out.println("Erreur Exportation Données .");
            e.printStackTrace();
        }
    }
}
