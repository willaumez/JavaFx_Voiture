package com.voiture.gestion_voitures.Controllers.Admin;

import com.voiture.gestion_voitures.Models.Model;
import com.voiture.gestion_voitures.Models.Voiture;
import com.voiture.gestion_voitures.Services.VoitureService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.geometry.Pos;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class DatabaseController implements Initializable {

    public BorderPane admin_parent= new BorderPane();

    public Label login_date;
    @FXML
    public Button add_car_db;
    @FXML
    public TableView<Voiture> table_view;
    @FXML
    public TableColumn<Voiture, Integer> idColumn;
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
    @FXML
    public TableColumn<Voiture, Void> actionsColumn;
    @FXML
    public Button edit_table_btn;
    public Button loading;

    private VoitureService voitureService = new VoitureService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table_view.getItems().clear();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        marqueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarque()));
        modeleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModele()));
        matriculationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMatriculation()));
        anneeColumn.setCellValueFactory(new PropertyValueFactory<>("annee"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        consommationColumn.setCellValueFactory(new PropertyValueFactory<>("consommation"));
        puissanceColumn.setCellValueFactory(new PropertyValueFactory<>("puissance"));
        actionsColumn.setCellFactory(createActionCellFactory());

        table_view.setEditable(true); // Définir le TableView comme éditable
        edit_table_btn.setOnAction(this::handleEditTableButton);

        add_car_db.setOnAction(this::handleAddCarButton);

        //login date
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"));
        login_date.setText(currentDateTime);

        loadDataIntoTableView();


    }

    private void loadDataIntoTableView() {
        List<Voiture> voitureList = voitureService.findAll();
        table_view.getItems().clear();
        table_view.getItems().addAll(voitureList);
    }

    private Callback<TableColumn<Voiture, Void>, TableCell<Voiture, Void>> createActionCellFactory() {
        return new Callback<TableColumn<Voiture, Void>, TableCell<Voiture, Void>>() {
            @Override
            public TableCell<Voiture, Void> call(TableColumn<Voiture, Void> param) {
                return new TableCell<>() {
                    private final Button deleteButton = new Button();

                    {
                        // Créer les icônes FontAwesomeFX correspondantes
                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);

                        // Appliquer un style CSS aux boutons pour qu'ils ressemblent à des icônes
                        deleteButton.getStyleClass().addAll("icon-button", "delete-button");

                        // Ajouter les icônes aux boutons
                        deleteButton.setGraphic(deleteIcon);


                        deleteButton.setOnAction((ActionEvent event) -> {
                            Voiture voiture = getTableView().getItems().get(getIndex());
                            System.out.println("Voiture delete----" + voiture);
                            voitureService.remove(voiture);

                            // Mettre à jour la liste des données du TableView
                            ObservableList<Voiture> voitureList = getTableView().getItems();
                            voitureList.remove(voiture);

                            // Réaffecter la liste mise à jour au TableView
                            getTableView().setItems(voitureList);
                            // Appeler votre fonction de suppression ici avec l'objet "voiture"
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttonsContainer = new HBox(deleteButton);
                            buttonsContainer.setAlignment(Pos.CENTER); // Aligner le contenu au centre
                            buttonsContainer.setSpacing(17);
                            setGraphic(buttonsContainer);
                        }
                    }
                };
            }
        };
    }

    //Edit car
    @FXML
    private void handleEditTableButton(ActionEvent event) {
        Voiture voiture = table_view.getSelectionModel().getSelectedItem();
        if (voiture != null) {
            // Rendre les cellules éditables
            marqueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            modeleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            matriculationColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            anneeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            prixColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            consommationColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            puissanceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));


            // Activer l'édition pour chaque colonne
            marqueColumn.setOnEditCommit((TableColumn.CellEditEvent<Voiture, String> t) -> {
                voiture.setMarque(t.getNewValue());
                voitureService.update(voiture);
            });

            modeleColumn.setOnEditCommit((TableColumn.CellEditEvent<Voiture, String> t) -> {
                voiture.setModele(t.getNewValue());
                voitureService.update(voiture);
            });

            matriculationColumn.setOnEditCommit((TableColumn.CellEditEvent<Voiture, String> t) -> {
                voiture.setMatriculation(t.getNewValue());
                voitureService.update(voiture);
            });

            anneeColumn.setOnEditCommit((TableColumn.CellEditEvent<Voiture, Integer> t) -> {
                voiture.setAnnee(t.getNewValue());
                voitureService.update(voiture);
            });

            prixColumn.setOnEditCommit((TableColumn.CellEditEvent<Voiture, Double> t) -> {
                voiture.setPrix(t.getNewValue());
                voitureService.update(voiture);
            });

            consommationColumn.setOnEditCommit((TableColumn.CellEditEvent<Voiture, Double> t) -> {
                voiture.setConsommation(t.getNewValue());
                voitureService.update(voiture);
            });

            puissanceColumn.setOnEditCommit((TableColumn.CellEditEvent<Voiture, Integer> t) -> {
                voiture.setPuissance(t.getNewValue());
                voitureService.update(voiture);
            });

            // Activer l'édition des cellules pour chaque colonne
            marqueColumn.setEditable(true);
            modeleColumn.setEditable(true);
            matriculationColumn.setEditable(true);
            anneeColumn.setEditable(true);
            prixColumn.setEditable(true);
            consommationColumn.setEditable(true);
            puissanceColumn.setEditable(true);

            // Mettre à jour la ligne du TableView pour permettre l'édition
            loadDataIntoTableView();
            table_view.refresh();
        }
    }


    //Add car
    @FXML
    private void handleAddCarButton(ActionEvent event) {

        AdminController.setAdmin_parent_To_Add();

       /* // Créer une nouvelle instance de voiture avec des valeurs par défaut
        Voiture newVoiture = new Voiture();
        newVoiture.setMarque("-----");
        newVoiture.setModele("-----");
        newVoiture.setMatriculation("-----");
        newVoiture.setAnnee(0); // Valeur par défaut pour l'année
        newVoiture.setPrix(0.0); // Valeur par défaut pour le prix
        newVoiture.setConsommation(0.0); // Valeur par défaut pour la consommation
        newVoiture.setPuissance(0); // Valeur par défaut pour la puissance

        // Ajouter la nouvelle voiture à la liste des données du TableView
        table_view.getItems().add(newVoiture);

        // Enregistrer la nouvelle voiture dans la base de données
        voitureService.save(newVoiture);
        table_view.refresh();*/
    }


    @FXML
    public void loading(ActionEvent actionEvent) {
        loadDataIntoTableView();
    }
}
