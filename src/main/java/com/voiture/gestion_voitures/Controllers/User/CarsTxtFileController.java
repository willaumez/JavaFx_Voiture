package com.voiture.gestion_voitures.Controllers.User;

import com.voiture.gestion_voitures.Services.VoitureService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class CarsTxtFileController implements Initializable {

    @FXML
    private TextArea txt_text_area;

    private VoitureService voitureService = new VoitureService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String elements = voitureService.readTxt();
        txt_text_area.setText(elements);
    }
}
