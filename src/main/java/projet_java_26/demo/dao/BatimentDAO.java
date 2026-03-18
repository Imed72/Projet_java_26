package projet_java_26.demo.dao;

import java.sql.*;
import java.util.*;
import projet_java_26.demo.model.Batiment;

public class BatimentDAO {
    // Configuration de la base de données
    // TODO: Mettre ces infos dans un fichier de config plus tard
    private final String databaseUrl = "jdbc:postgresql://localhost:5432/maintenance_db";
    private final String databaseUser = "admin";
    private final String databasePassword = "password123";

    public List<Batiment> listerTout() {
        List<Batiment> listeBatiments = new ArrayList<>();
       
        // Requête SQL pour récupérer tous les bâtiments
        String requeteSQL = "SELECT * FROM batiments ORDER BY id";
       
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultats = null;
       
        try {
            connexion = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
            statement = connexion.createStatement();
            resultats = statement.executeQuery(requeteSQL);
           
            while (resultats.next()) {
                // Création d'un nouveau bâtiment avec les données de la BD
                String nom = resultats.getString("nom");
                String localisation = resultats.getString("localisation");
               
                Batiment batiment = new Batiment(nom, localisation);
                batiment.setId(resultats.getInt("id"));
                listeBatiments.add(batiment);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des bâtiments");
            e.printStackTrace();
        } finally {
            // Fermeture des ressources
            try {
                if (resultats != null) resultats.close();
                if (statement != null) statement.close();
                if (connexion != null) connexion.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
       
        return listeBatiments;
    }

    public void ajouter(Batiment batiment) {
        String insertSQL = "INSERT INTO batiments (nom, emplacement) VALUES (?,?)";
       
        try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
           
            pstmt.setString(1, batiment.getNom());
            pstmt.setString(2, batiment.getLocalisation());
           
            int lignesAffectees = pstmt.executeUpdate();
           
            // Récupérer l'ID généré automatiquement
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int nouvelId = generatedKeys.getInt(1);
                batiment.setId(nouvelId);
            }
        } catch (SQLException e) {
            System.out.println("Problème lors de l'ajout du bâtiment");
            e.printStackTrace();
        }
    }

    public void modifier(Batiment bat) {
        String updateQuery = "UPDATE batiments SET nom=?, emplacement=? WHERE id=?";
       
        Connection connection = null;
        PreparedStatement preparedStatement = null;
       
        try {
            connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
            preparedStatement = connection.prepareStatement(updateQuery);
           
            preparedStatement.setString(1, bat.getNom());
            preparedStatement.setString(2, bat.getLocalisation());
            preparedStatement.setInt(3, bat.getId());
           
            int rowsUpdated = preparedStatement.executeUpdate();
           
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void supprimer(int batimentId) {
        // Suppression d'un bâtiment par son ID
        try (Connection c = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
             PreparedStatement ps = c.prepareStatement("DELETE FROM batiments WHERE id=?")) {
            ps.setInt(1, batimentId);
            int deleted = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur suppression bâtiment ID: " + batimentId);
            e.printStackTrace();
        }
    }
}