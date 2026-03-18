package projet_java_26.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import projet_java_26.demo.dao.TechnicienDAO;
import projet_java_26.demo.dao.BatimentDAO;
import projet_java_26.demo.dao.InterventionDAO;

public class MainApp extends Application {

    private TechnicienDAO techDAO = new TechnicienDAO();
    private BatimentDAO batDAO = new BatimentDAO();
    private InterventionDAO interDAO = new InterventionDAO();

    @Override
    public void start(Stage stage) {
        // Création des vues
        TechnicienView techView = new TechnicienView(techDAO);
        BatimentView batView = new BatimentView(batDAO);
        InterventionView interView = new InterventionView(techDAO, batDAO, interDAO);

        // Partage des références des tables pour les mises à jour croisées
        interView.setTechTableView(techView.getTableView());
        interView.setBatTableView(batView.getTableView());

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(
                new Tab("Techniciens", techView.createView()),
                new Tab("Bâtiments", batView.createView()),
                new Tab("Interventions", interView.createView())
        );
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        stage.setScene(new Scene(tabPane, 900, 650));
        stage.setTitle("Système de Maintenance Professionnel");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}