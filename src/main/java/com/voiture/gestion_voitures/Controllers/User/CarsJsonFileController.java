package com.voiture.gestion_voitures.Controllers.User;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.voiture.gestion_voitures.Models.Voiture;
import com.voiture.gestion_voitures.Services.VoitureService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class CarsJsonFileController implements Initializable {
    @FXML
    public TextArea json_text_area;

    private VoitureService voitureService = new VoitureService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Voiture> voitures = voitureService.readJson();

        // Créez une instance de Gson avec une indentation pour un formatage lisible
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Convertissez la liste d'objets en JSON formaté
        String json = gson.toJson(voitures);

        // Affichez le JSON dans le TextArea
        json_text_area.setText(json);
    }
}
