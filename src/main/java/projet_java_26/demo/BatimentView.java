package projet_java_26.demo;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import projet_java_26.demo.dao.BatimentDAO;
import projet_java_26.demo.model.Batiment;
import projet_java_26.demo.util.AlertUtil;

public class BatimentView {
    private final BatimentDAO batimentDAO;
    private TableView<Batiment> tableauBatiments = new TableView<>();

    public BatimentView(BatimentDAO dao) { 
        this.batimentDAO = dao; 
    }

    public VBox createView() {
        // Configuration des colonnes du tableau
        // Note: On pourrait optimiser ça mais bon, ça marche
        TableColumn<Batiment, Object> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn<Batiment, Object> colNom = new TableColumn<>("NOM");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        
        TableColumn<Batiment, Object> colLocalisation = new TableColumn<>("LOCALISATION");
        colLocalisation.setCellValueFactory(new PropertyValueFactory<>("localisation"));
        
        tableauBatiments.getColumns().add(colId);
        tableauBatiments.getColumns().add(colNom);
        tableauBatiments.getColumns().add(colLocalisation);
        
        // Charger les données initiales
        rafraichirDonnees();

        // Création des boutons d'action
        Button boutonAjouter = new Button("Ajouter");
        Button boutonModifier = new Button("Modifier");
        Button boutonSupprimer = new Button("Supprimer");

        // Action pour ajouter un nouveau bâtiment
        boutonAjouter.setOnAction(e -> {
            afficherFormulaire(null);
        });

        // Action pour modifier un bâtiment existant
        boutonModifier.setOnAction(e -> {
            Batiment batimentSelectionne = tableauBatiments.getSelectionModel().getSelectedItem();
            if (batimentSelectionne != null) {
                afficherFormulaire(batimentSelectionne);
            } else {
                AlertUtil.showAlert("Attention", "Sélectionnez un bâtiment d'abord.");
            }
        });

        // Action pour supprimer un bâtiment
        boutonSupprimer.setOnAction(e -> {
            Batiment batimentSelectionne = tableauBatiments.getSelectionModel().getSelectedItem();
            if (batimentSelectionne == null) {
                AlertUtil.showAlert("Attention", "Aucun élément sélectionné.");
                return;
            }
            
            // Demander confirmation avant suppression
            ButtonType boutonOui = new ButtonType("Confirmer", ButtonBar.ButtonData.OK_DONE);
            ButtonType boutonNon = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alerteConfirmation = new Alert(Alert.AlertType.CONFIRMATION, 
                "Êtes-vous sûr de vouloir supprimer ce bâtiment ?", boutonOui, boutonNon);
            
            alerteConfirmation.showAndWait().ifPresent(reponse -> {
                if (reponse == boutonOui) {
                    batimentDAO.supprimer(batimentSelectionne.getId());
                    rafraichirDonnees();  // Recharger la liste après suppression
                }
            });
        });

        // Organisation de la mise en page
        HBox barreOutils = new HBox(10, boutonAjouter, boutonModifier, boutonSupprimer);
        VBox conteneurPrincipal = new VBox(15, tableauBatiments, barreOutils);
        conteneurPrincipal.setStyle("-fx-padding: 15;");
        
        return conteneurPrincipal;
    }

    private void afficherFormulaire(Batiment batimentAModifier) {
        Dialog<Batiment> dialogue = new Dialog<>();
        
        // Titre du dialogue selon le contexte
        if (batimentAModifier == null) {
            dialogue.setTitle("Ajout d'un nouveau bâtiment");
        } else {
            dialogue.setTitle("Modification du bâtiment");
        }

        ButtonType boutonSauvegarder = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        ButtonType boutonAnnuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialogue.getDialogPane().getButtonTypes().addAll(boutonSauvegarder, boutonAnnuler);

        // Champs de saisie
        TextField champNom = new TextField();
        TextField champLocalisation = new TextField();
        
        // Pré-remplir les champs si on modifie un bâtiment existant
        if (batimentAModifier != null) {
            champNom.setText(batimentAModifier.getNom());
            champLocalisation.setText(batimentAModifier.getLocalisation());
        }

        // Construction du formulaire
        VBox formulaire = new VBox(10);
        formulaire.getChildren().addAll(
            new Label("Nom du bâtiment :"), 
            champNom, 
            new Label("Localisation :"), 
            champLocalisation
        );
        dialogue.getDialogPane().setContent(formulaire);

        // Traitement du résultat
        dialogue.setResultConverter(boutonClique -> {
            if (boutonClique == boutonSauvegarder) {
                // Validation basique des champs
                String nomSaisi = champNom.getText();
                String localisationSaisie = champLocalisation.getText();
                
                if (nomSaisi.isBlank() || localisationSaisie.isBlank()) {
                    AlertUtil.showAlert("Erreur", "Les champs ne peuvent pas être vides.");
                    return null;
                }
                
                Batiment batimentResultat;
                if (batimentAModifier == null) {
                    // Création d'un nouveau bâtiment
                    batimentResultat = new Batiment(nomSaisi, localisationSaisie);
                } else {
                    // Modification du bâtiment existant
                    batimentResultat = batimentAModifier;
                    batimentResultat.setNom(nomSaisi);
                    batimentResultat.setLocalisation(localisationSaisie);
                }
                
                return batimentResultat;
            }
            return null;
        });

        // Afficher le dialogue et traiter le résultat
        dialogue.showAndWait().ifPresent(batimentResultat -> {
            if (batimentAModifier == null) {
                batimentDAO.ajouter(batimentResultat);
            } else {
                batimentDAO.modifier(batimentResultat);
            }
            rafraichirDonnees();  // Actualiser l'affichage
        });
    }

    // Méthode pour recharger les données du tableau
    private void rafraichirDonnees() {
        tableauBatiments.setItems(FXCollections.observableArrayList(batimentDAO.listerTout()));
    }

    // Getter pour accéder au tableau depuis l'extérieur si besoin
    public TableView<Batiment> getTableView() { 
        return tableauBatiments; 
    }
}