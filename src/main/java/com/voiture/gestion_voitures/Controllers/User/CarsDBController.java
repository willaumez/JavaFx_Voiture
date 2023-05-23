package com.voiture.gestion_voitures.Controllers.User;

import com.voiture.gestion_voitures.Models.Voiture;
import com.voiture.gestion_voitures.Services.VoitureService;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class CarsDBController implements Initializable {
    @FXML
    public Label login_date;
    @FXML
    public Text user_name;
    @FXML
    public TableView<Voiture> table_view;
    @FXML
    public TableColumn<Voiture, String> marqueColumn;
    @FXML
    public TableColumn<Voiture, String> modeleColumn;
    @FXML
    public TableColumn<Voiture, Integer> anneeColumn;
    @FXML
    public TableColumn<Voiture, Double> prixColumn;
    @FXML
    public TableColumn<Voiture, String> matriculationColumn;
    @FXML
    public TableColumn<Voiture, Double> consommationColumn;
    @FXML
    public TableColumn<Voiture, Integer> puissanceColumn;

    private VoitureService voitureService = new VoitureService();

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        marqueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarque()));
        modeleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModele()));
        matriculationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMatriculation()));
        anneeColumn.setCellValueFactory(new PropertyValueFactory<>("annee"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        consommationColumn.setCellValueFactory(new PropertyValueFactory<>("consommation"));
        puissanceColumn.setCellValueFactory(new PropertyValueFactory<>("puissance"));

        List<Voiture> voitureList = voitureService.findAll();
        table_view.getItems().addAll(voitureList);

        //login date
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"));
        login_date.setText(currentDateTime);
    }
}
