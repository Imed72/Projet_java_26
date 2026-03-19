package projet_java_26.demo.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertUtil {

    // Méthode générique pour les avertissements 
    // head : titre de l'alerte, msg : message à afficher
    public static void showAlert(String head, String msg) {
        Alert a = new Alert(AlertType.WARNING);
        a.setTitle("Alerte");
        a.setHeaderText(head);
        a.setContentText(msg);
        a.showAndWait();
    }

    //  une méthode pour les erreurs fatales
    // msg : message à afficher
    public static void showError(String msg) {
        Alert a = new Alert(AlertType.ERROR);
        a.setTitle("Erreur");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}