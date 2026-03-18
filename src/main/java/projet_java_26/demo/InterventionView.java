package projet_java_26.demo;

import javafx.collections.FXCollections;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import projet_java_26.demo.dao.*;
import projet_java_26.demo.model.*;
import projet_java_26.demo.util.AlertUtil;

public class InterventionView {
    private final TechnicienDAO tDao;
    private final BatimentDAO bDao;
    private final InterventionDAO iDao;
    
    private TableView<Intervention> table = new TableView<>();
    private TableView<Technicien> viewTech;
    private TableView<Batiment> viewBat;

    public InterventionView(TechnicienDAO t, BatimentDAO b, InterventionDAO i) {
        this.tDao = t; this.bDao = b; this.iDao = i;
    }

    public void setTechTableView(TableView<Technicien> tv) { this.viewTech = tv; }
    public void setBatTableView(TableView<Batiment> bv) { this.viewBat = bv; }

    public VBox createView() {
        // Setup des colonnes principales
        String[] colonnes = {"date", "type", "statut", "description"};
        for (String label : colonnes) {
            TableColumn<Intervention, Object> col = new TableColumn<>(label.toUpperCase());
            col.setCellValueFactory(new PropertyValueFactory<>(label));
            table.getColumns().add(col);
        }

        // Mapping manuel pour les objets liés
        TableColumn<Intervention, String> colTech = new TableColumn<>("TECHNICIEN");
        colTech.setCellValueFactory(it -> new SimpleStringProperty(it.getValue().getTechnicien().getNom()));
        
        TableColumn<Intervention, String> colBat = new TableColumn<>("BÂTIMENT");
        colBat.setCellValueFactory(it -> new SimpleStringProperty(it.getValue().getBatiment().getNom()));
        
        table.getColumns().addAll(colTech, colBat);

        // Barre de filtres
        ComboBox<Technicien> fTech = new ComboBox<>(FXCollections.observableArrayList(tDao.listerTout()));
        fTech.setPromptText("Filtrer Technicien");
        fTech.setOnAction(e -> {
            if (fTech.getValue() != null) 
                table.setItems(FXCollections.observableArrayList(iDao.listerParTechnicien(fTech.getValue().getId())));
        });

        ComboBox<Batiment> fBat = new ComboBox<>(FXCollections.observableArrayList(bDao.listerTout()));
        fBat.setPromptText("Filtrer Bâtiment");
        fBat.setOnAction(e -> {
            if (fBat.getValue() != null)
                table.setItems(FXCollections.observableArrayList(iDao.listerParBatiment(fBat.getValue().getId())));
        });

        Button btnReset = new Button("Voir Tout");
        btnReset.setOnAction(e -> { refreshUI(); fTech.setValue(null); fBat.setValue(null); });

        // Boutons d'action
        Button btnAdd = new Button("Programmer");
        Button btnModif = new Button("Modifier");
        Button btnDone = new Button("Clôturer");
        Button btnSup = new Button("Supprimer");

        btnAdd.setOnAction(e -> openDialog(null));
        
        btnModif.setOnAction(e -> {
            Intervention sel = table.getSelectionModel().getSelectedItem();
            if (sel != null) openDialog(sel);
            else AlertUtil.showAlert("Attention", "Veuillez sélectionner une intervention.");
        });

        btnDone.setOnAction(e -> {
            Intervention sel = table.getSelectionModel().getSelectedItem();
            if (sel != null) {
                sel.setStatut("Terminée");
                iDao.modifier(sel);
                refreshUI();
            } else AlertUtil.showAlert("Attention", "Aucune intervention sélectionnée.");
        });

        btnSup.setOnAction(e -> {
            Intervention sel = table.getSelectionModel().getSelectedItem();
            if (sel == null) {
                AlertUtil.showAlert("Attention", "Rien à supprimer.");
                return;
            }
            ButtonType o = new ButtonType("Confirmer", ButtonBar.ButtonData.OK_DONE);
            ButtonType n = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert al = new Alert(Alert.AlertType.CONFIRMATION, "Supprimer l'intervention ?", o, n);
            al.showAndWait().ifPresent(r -> {
                if (r == o) { iDao.supprimer(sel.getId()); refreshUI(); }
            });
        });

        refreshUI();
        
        HBox top = new HBox(10, new Label("Filtres:"), fTech, fBat, btnReset);
        HBox bottom = new HBox(10, btnAdd, btnModif, btnDone, btnSup);
        
        VBox root = new VBox(10, top, table, bottom);
        root.setStyle("-fx-padding: 15;");
        return root;
    }

    private void openDialog(Intervention base) {
        Dialog<Intervention> win = new Dialog<>();
        win.setTitle(base == null ? "Nouvelle Intervention" : "Édition");

        ButtonType save = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        ButtonType back = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        win.getDialogPane().getButtonTypes().addAll(save, back);

        TextField txtDesc = new TextField(base != null ? base.getDescription() : "");
        DatePicker pkrDate = new DatePicker(base != null ? base.getDate() : java.time.LocalDate.now());
        
        ComboBox<String> cbStatut = new ComboBox<>(FXCollections.observableArrayList("Planifiée", "En cours", "Terminée"));
        cbStatut.setValue(base != null ? base.getStatut() : "Planifiée");

        ComboBox<Technicien> cbTech = new ComboBox<>(FXCollections.observableArrayList(tDao.listerTout()));
        ComboBox<Batiment> cbBat = new ComboBox<>(FXCollections.observableArrayList(bDao.listerTout()));
        
        if (base != null) {
            cbTech.setValue(base.getTechnicien());
            cbBat.setValue(base.getBatiment());
        }

        win.getDialogPane().setContent(new VBox(10, 
            new Label("Description"), txtDesc, 
            new Label("Date"), pkrDate, 
            new Label("État"), cbStatut, 
            new Label("Technicien"), cbTech, 
            new Label("Bâtiment"), cbBat
        ));

        win.setResultConverter(b -> {
            if (b != save) return null;
            Intervention i = (base == null) ? new Intervention(txtDesc.getText(), pkrDate.getValue(), "Maintenance", cbStatut.getValue(), cbTech.getValue(), cbBat.getValue()) : base;
            i.setDescription(txtDesc.getText());
            i.setDate(pkrDate.getValue());
            i.setStatut(cbStatut.getValue());
            i.setTechnicien(cbTech.getValue());
            i.setBatiment(cbBat.getValue());
            return i;
        });

        win.showAndWait().ifPresent(res -> {
            if (base == null) iDao.planifier(res); 
            else iDao.modifier(res);
            refreshUI();
        });
    }

    private void refreshUI() {
        table.setItems(FXCollections.observableArrayList(iDao.listerTout()));
        if (viewTech != null) viewTech.setItems(FXCollections.observableArrayList(tDao.listerTout()));
        if (viewBat != null) viewBat.setItems(FXCollections.observableArrayList(bDao.listerTout()));
    }

    public TableView<Intervention> getTableView() { return table; }
}