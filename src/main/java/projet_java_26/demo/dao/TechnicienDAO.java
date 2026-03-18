package projet_java_26.demo.dao;

import java.sql.*;
import java.util.*;
import projet_java_26.demo.model.Technicien;

public class TechnicienDAO {
    // Configuration de la base de données
    // TODO: Mettre ces infos dans un fichier de config plus tard
    private final String databaseUrl = "jdbc:postgresql://localhost:5432/maintenance_db";
    private final String databaseUser = "admin";
    private final String databasePassword = "password123";

    public List<Technicien> listerTout() {
        List<Technicien> listeTechniciens = new ArrayList<>();
        
        // Requête SQL pour récupérer tous les techniciens
        // Note: La disponibilité est calculée automatiquement en vérifiant s'il y a des interventions en cours
        String requeteSQL = "SELECT t.*, CASE WHEN EXISTS (SELECT 1 FROM interventions WHERE id_technicien = t.id AND statut = 'En cours') " +
                     "THEN false ELSE true END as dispo_reel FROM techniciens t ORDER BY t.id";
        
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultats = null;
        
        try {
            connexion = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
            statement = connexion.createStatement();
            resultats = statement.executeQuery(requeteSQL);
            
            while (resultats.next()) {
                // Création d'un nouveau technicien avec les données de la BD
                String nom = resultats.getString("nom");
                String qualification = resultats.getString("qualification");
                boolean disponibilite = resultats.getBoolean("dispo_reel");
                
                Technicien technicien = new Technicien(nom, qualification, disponibilite);
                technicien.setId(resultats.getInt("id"));
                listeTechniciens.add(technicien);
            }
        } catch (SQLException e) { 
            System.out.println("Erreur lors de la récupération des techniciens");
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
        
        return listeTechniciens;
    }

    public void ajouter(Technicien technicien) {
        String insertSQL = "INSERT INTO techniciens (nom, qualification, disponible) VALUES (?,?,?)";
        
        try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, technicien.getNom());
            pstmt.setString(2, technicien.getQualification());
            pstmt.setBoolean(3, technicien.isDisponible());
            
            int lignesAffectees = pstmt.executeUpdate();
            
            // Récupérer l'ID généré automatiquement
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int nouvelId = generatedKeys.getInt(1);
                technicien.setId(nouvelId);
            }
        } catch (SQLException e) { 
            System.out.println("Problème lors de l'ajout du technicien");
            e.printStackTrace(); 
        }
    }

    public void modifier(Technicien tech) {
        String updateQuery = "UPDATE techniciens SET nom=?, qualification=?, disponible=? WHERE id=?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
            preparedStatement = connection.prepareStatement(updateQuery);
            
            preparedStatement.setString(1, tech.getNom());
            preparedStatement.setString(2, tech.getQualification());
            preparedStatement.setBoolean(3, tech.isDisponible());
            preparedStatement.setInt(4, tech.getId());
            
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

    public void supprimer(int technicienId) {
        // Suppression d'un technicien par son ID
        try (Connection c = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
             PreparedStatement ps = c.prepareStatement("DELETE FROM techniciens WHERE id=?")) {
            ps.setInt(1, technicienId);
            int deleted = ps.executeUpdate();
        } catch (SQLException e) { 
            System.err.println("Erreur suppression technicien ID: " + technicienId);
            e.printStackTrace(); 
        }
    }
}