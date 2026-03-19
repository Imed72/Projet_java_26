package projet_java_26.demo;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import projet_java_26.demo.dao.TechnicienDAO;
import projet_java_26.demo.model.Technicien;
import projet_java_26.demo.util.AlertUtil;


// Vue pour gérer les techniciens - affichage et operations




public class TechnicienView {
    private TechnicienDAO dao;

    private TableView<Technicien> table = new TableView<>();

    public TechnicienView(TechnicienDAO dao) { 
        this.dao = dao; 
    }

    public VBox createView() {

        // Configuration des colonnes - ID, Nom, Qualification
        // Note: Je pourrais utiliser un Map ici mais l'array est plus simple pour ce cas


        String[] columnNames = {"id", "nom", "qualification"};
        for (String columnName : columnNames) {

            TableColumn<Technicien, Object> column = new TableColumn<>(columnName.toUpperCase());
            column.setCellValueFactory(new PropertyValueFactory<>(columnName));
            table.getColumns().add(column);
        }


        // Colonne spéciale pour la disponibilité - affiche "Oui" ou "Non" au lieu de true/false
        TableColumn<Technicien, Boolean> disponibiliteColumn = new TableColumn<>("DISPONIBLE");
        
        disponibiliteColumn.setCellValueFactory(new PropertyValueFactory<>("disponible"));
        disponibiliteColumn.setCellFactory(column -> new TableCell<Technicien, Boolean>() {
        
            @Override
            protected void updateItem(Boolean disponible, boolean empty) {
                super.updateItem(disponible, empty);

                // Affichage personnalisé pour la disponibilité
                if (empty || disponible == null) {
                    setText(null);
                } else {
                    setText(disponible ? "Oui" : "Non");
                }
            }
        });

        table.getColumns().add(disponibiliteColumn);

        refresh();  // Charger les données initiales

        // Boutons d'action
        Button ajouterBtn = new Button("Ajouter");
        Button modifierBtn = new Button("Modifier");

        Button supprimerBtn = new Button("Supprimer");

        ajouterBtn.setOnAction(event -> openDialog(null));
        modifierBtn.setOnAction(event -> {

            Technicien selectedTech = table.getSelectionModel().getSelectedItem();
            if (selectedTech != null) {
                openDialog(selectedTech);
            }

            else {
                AlertUtil.showAlert("Aucune sélection", "Veuillez sélectionner un technicien.");
            }
        });
        
        supprimerBtn.setOnAction(event -> {
            Technicien selectedTech = table.getSelectionModel().getSelectedItem();
            if (selectedTech != null) {

                dao.supprimer(selectedTech.getId());
                refresh();  // Rafraîchir la table après suppression

            } else {
                AlertUtil.showAlert("Aucune sélection", "Veuillez sélectionner un technicien.");
            }
        });

        HBox buttonBar = new HBox(10, ajouterBtn, modifierBtn, supprimerBtn);
        return new VBox(10, table, buttonBar);
    }

    private void openDialog(Technicien technicien) {
        Dialog<Technicien> dialog = new Dialog<>();

        // Titre différent selon si on ajoute ou modifie
        if (technicien == null) {
            dialog.setTitle("Nouveau technicien");
        } else {
            dialog.setTitle("Modifier technicien");
        }
        
        ButtonType validerButton = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(validerButton, new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE));




        // Champs de saisie



        TextField nomField = new TextField();
        if (technicien != null) {
            nomField.setText(technicien.getNom());
        }


        nomField.setPromptText("Nom");

        TextField qualificationField = new TextField();
        if (technicien != null) {
            qualificationField.setText(technicien.getQualification());
        }
        qualificationField.setPromptText("Qualification");


        CheckBox disponibleCheckbox = new CheckBox("Disponible");
        // Par défaut, un nouveau technicien est disponible
        if (technicien != null) {
            disponibleCheckbox.setSelected(technicien.isDisponible());
        } else {
            disponibleCheckbox.setSelected(true);
        }

        VBox dialogContent = new VBox(10, new Label("Nom:"), nomField, new Label("Qualification:"), qualificationField, disponibleCheckbox);
        dialog.getDialogPane().setContent(dialogContent);

        dialog.setResultConverter(buttonType -> {

            if (buttonType == validerButton) {
                // Validation basique des champs
                if (nomField.getText().isEmpty() || qualificationField.getText().isEmpty()) {
                    AlertUtil.showAlert("Champ vide", "Le nom et la qualification sont requis.");
                    return null;
                }



                Technicien result;
                if (technicien == null) {
                    result = new Technicien(nomField.getText(), qualificationField.getText(), disponibleCheckbox.isSelected());
                } else {
                    result = technicien;
                }
                
                result.setNom(nomField.getText());
                result.setQualification(qualificationField.getText());

                result.setDisponible(disponibleCheckbox.isSelected());
                return result;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(resultat -> {

            if (technicien == null) {
                dao.ajouter(resultat);
            }


            else {
                dao.modifier(resultat);
            }
            refresh();  // Mettre à jour la table
        });
    }

    private void refresh() {
        // Recharger toutes les données depuis la base
        table.setItems(FXCollections.observableArrayList(dao.listerTout()));
    }

    
    public TableView<Technicien> getTableView() { 
        return table; 
    }
}