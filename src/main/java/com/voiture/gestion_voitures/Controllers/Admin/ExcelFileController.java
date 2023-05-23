package com.voiture.gestion_voitures.Controllers.Admin;

import com.voiture.gestion_voitures.Models.Voiture;
import com.voiture.gestion_voitures.Services.VoitureService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ExcelFileController implements Initializable {
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
    @FXML
    public TableColumn<Voiture, Integer> idColumn;
    @FXML
    public TableColumn<Voiture, Void> actionsColumn;
    @FXML
    public Button data_to_excel;
    @FXML
    public Button data_from_excel;
    public Button edit_excel_btn;
    public Button add_excel_db;

    private VoitureService voitureService = new VoitureService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        marqueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarque()));
        modeleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModele()));
        matriculationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMatriculation()));
        anneeColumn.setCellValueFactory(new PropertyValueFactory<>("annee"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        consommationColumn.setCellValueFactory(new PropertyValueFactory<>("consommation"));
        puissanceColumn.setCellValueFactory(new PropertyValueFactory<>("puissance"));
        actionsColumn.setCellFactory(createActionCellFactory());

        table_view.setEditable(true); // Définir le TableView comme éditable
        edit_excel_btn.setOnAction(this::handleEditTableButton);
        add_excel_db.setOnAction(this::handleAddCarButton);

        insertDataTable();
    }

    public void insertDataTable(){
        List<Voiture> voitureList = VoitureService.readExcel();
        // Ajouter le numéro de ligne au modèle de données
        for (int i = 0; i < voitureList.size(); i++) {
            Voiture voiture = voitureList.get(i);
            voiture.setId(i + 1);
        }
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        // Réinitialiser la table_view
        table_view.getItems().clear();
        // Ajouter le numéro de ligne au modèle de données
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
                            voitureService.deleteIntoExcel(voiture);

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
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setMarque(t.getNewValue());
                voitureService.updateExcel(t.getNewValue(), t.getTablePosition().getRow()+1, 0);
            });

            modeleColumn.setOnEditCommit((TableColumn.CellEditEvent<Voiture, String> t) -> {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setModele(t.getNewValue());
                voitureService.updateExcel(t.getNewValue(), t.getTablePosition().getRow()+1, 1);
            });

            matriculationColumn.setOnEditCommit((TableColumn.CellEditEvent<Voiture, String> t) -> {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setMatriculation(t.getNewValue());
                voitureService.updateExcel(t.getNewValue(), t.getTablePosition().getRow()+1, 2);
            });

            anneeColumn.setOnEditCommit((TableColumn.CellEditEvent<Voiture, Integer> t) -> {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setAnnee(t.getNewValue());
                voitureService.updateExcel(t.getNewValue().toString(), t.getTablePosition().getRow()+1, 3);
            });

            prixColumn.setOnEditCommit((TableColumn.CellEditEvent<Voiture, Double> t) -> {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setPrix(t.getNewValue());
                voitureService.updateExcel(t.getNewValue().toString(), t.getTablePosition().getRow()+1, 4);
            });

            consommationColumn.setOnEditCommit((TableColumn.CellEditEvent<Voiture, Double> t) -> {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setConsommation(t.getNewValue());
                voitureService.updateExcel(t.getNewValue().toString(), t.getTablePosition().getRow()+1, 5);
            });

            puissanceColumn.setOnEditCommit((TableColumn.CellEditEvent<Voiture, Integer> t) -> {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setPuissance(t.getNewValue());
                voitureService.updateExcel(t.getNewValue().toString(), t.getTablePosition().getRow()+1, 6);
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
            insertDataTable();
            table_view.refresh();
        }
    }

    @FXML
    private void handleAddCarButton(ActionEvent event) {
        // Créer une nouvelle instance de voiture avec des valeurs par défaut
        Voiture newVoiture = new Voiture();
        newVoiture.setMarque("-----");
        newVoiture.setModele("-----");
        newVoiture.setMatriculation("-----");
        newVoiture.setAnnee(0); // Valeur par défaut pour l'année
        newVoiture.setPrix(0.0); // Valeur par défaut pour le prix
        newVoiture.setConsommation(0.0); // Valeur par défaut pour la consommation
        newVoiture.setPuissance(0); // Valeur par défaut pour la puissance

        // Enregistrer la nouvelle voiture dans le fichier Excel
        voitureService.saveExcel(newVoiture);
        insertDataTable();
        table_view.refresh();
    }


    //Export - Import
    @FXML
    private void handleDataToExcel(ActionEvent event) {
        try {
            voitureService.dataToExcel();
            insertDataTable();
            table_view.refresh();
            System.out.println("Données importées avec succès.");
        } catch (Exception e) {
            System.out.println("Erreur importation Données .");
            e.printStackTrace();
        }
    }
    @FXML
    private void handleDataFromExcel(ActionEvent event) {
        try {
            List<Voiture> voitures = table_view.getItems();
            voitureService.dataFromExcel(voitures);
            table_view.refresh();
            System.out.println("Exportation Données  avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur Exportation Exporter.");

        }
    }



}
