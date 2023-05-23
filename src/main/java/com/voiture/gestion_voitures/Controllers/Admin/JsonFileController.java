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

public class JsonFileController implements Initializable {
    @FXML
    public Button data_to_json;
    @FXML
    public Button data_from_json;
    @FXML
    public TextArea json_text_area;
    @FXML
    public Button save_json_file;
    private final VoitureService voitureService = new VoitureService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateJsonTextArea();
    }

    public void updateJsonTextArea(){
        List<Voiture> voitures = voitureService.readJson();

        // Créez une instance de Gson avec une indentation pour un formatage lisible
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Convertissez la liste d'objets en JSON formaté
        String json = gson.toJson(voitures);

        // Affichez le JSON dans le TextArea
        json_text_area.setText(json);
    }

    @FXML
    private void saveJsonToFile() {
        String json_file = json_text_area.getText();
        voitureService.json_save(json_file);
        updateJsonTextArea();
    }

    @FXML
    public void handleDataToJson(ActionEvent actionEvent) {
        try {
            voitureService.dataToJson();
            updateJsonTextArea();
            System.out.println("Importation Données Reussi.");
        } catch (Exception e) {
            System.out.println("Erreur importation Données .");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDataFromJson(ActionEvent actionEvent) {
        try {
            voitureService.dataFromJson();
            updateJsonTextArea();
            System.out.println("Exportation Données Reussi.");
        } catch (Exception e) {
            System.out.println("Erreur Exportation Données .");
            e.printStackTrace();
        }
    }
}
