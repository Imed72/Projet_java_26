package projet_java_26.demo.dao;

import java.sql.*;
import java.util.*;
import java.time.LocalDate;
import projet_java_26.demo.model.Intervention;
import projet_java_26.demo.model.Technicien;
import projet_java_26.demo.model.Batiment;

public class InterventionDAO {
    // Configuration de la base de données
    // TODO: Mettre ces infos dans un fichier plus tard
    private final String databaseUrl = "jdbc:postgresql://localhost:5432/maintenance_db";
    private final String databaseUser = "admin";
    private final String databasePassword = "password123";

    public List<Intervention> listerTout() {
        List<Intervention> listeInterventions = new ArrayList<>();
       
        // Requête SQL pour récupérer toutes les interventions avec jointures
        // Note: On charge les techniciens et bâtiments associés directement
        String requeteSQL = "SELECT i.id as inter_id, i.date_intervention, i.type_intervention, i.statut, i.description, " +
                     "t.id as tech_id, t.nom as tech_nom, t.qualification as tech_qual, t.disponible as tech_dispo, " +
                     "b.id as bat_id, b.nom as bat_nom, b.localisation as bat_empl " +
                     "FROM interventions i " +
                     "JOIN techniciens t ON i.id_technicien = t.id " +
                     "JOIN batiments b ON i.id_batiment = b.id " +
                     "ORDER BY i.id";
       
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultats = null;
       
        try {
            connexion = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
            statement = connexion.createStatement();
            resultats = statement.executeQuery(requeteSQL);
           
            while (resultats.next()) {
                // Création des objets associés

                LocalDate date = resultats.getDate("date_intervention").toLocalDate();
                String type = resultats.getString("type_intervention");
                String statut = resultats.getString("statut");
                String description = resultats.getString("description");
               
                Technicien technicien = new Technicien(
                    resultats.getString("tech_nom"),
                    resultats.getString("tech_qual"),
                    resultats.getBoolean("tech_dispo")
                );
                technicien.setId(resultats.getInt("tech_id"));
               
                Batiment batiment = new Batiment(
                    resultats.getString("bat_nom"),
                    resultats.getString("bat_empl")
                );
                batiment.setId(resultats.getInt("bat_id"));
               
                Intervention intervention = new Intervention(description, date, type, statut, technicien, batiment);
                intervention.setId(resultats.getInt("inter_id"));
                listeInterventions.add(intervention);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des interventions");
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
       
        return listeInterventions;
    }

    public List<Intervention> listerParTechnicien(int idTechnicien) {
        List<Intervention> listeInterventions = new ArrayList<>();
       
        // Requête avec filtre technicien (style identique au listerTout)
        // Note: On peut aussi faire une requête plus simple et charger les 
        // techniciens/bâtiments séparément, 
        // mais là on garde tout en une seule requête pour la simplicité
        String requeteSQL = "SELECT i.id as inter_id, i.date_intervention, i.type_intervention, i.statut, i.description, " +
                     "t.id as tech_id, t.nom as tech_nom, t.qualification as tech_qual, t.disponible as tech_dispo, " +
                     "b.id as bat_id, b.nom as bat_nom, b.localisation as bat_empl " +
                     "FROM interventions i " +
                     "JOIN techniciens t ON i.id_technicien = t.id " +
                     "JOIN batiments b ON i.id_batiment = b.id " +
                     "WHERE i.id_technicien = " + idTechnicien + " ORDER BY i.id";
       
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultats = null;
       
        try {
            connexion = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
            statement = connexion.createStatement();
            resultats = statement.executeQuery(requeteSQL);
           
            while (resultats.next()) {
                // Même création que dans listerTout 
                LocalDate date = resultats.getDate("date_intervention").toLocalDate();
                String type = resultats.getString("type_intervention");
                String statut = resultats.getString("statut");
                String description = resultats.getString("description");
               
                Technicien technicien = new Technicien(
                    resultats.getString("tech_nom"),
                    resultats.getString("tech_qual"),
                    resultats.getBoolean("tech_dispo")
                );
                technicien.setId(resultats.getInt("tech_id"));
               
                Batiment batiment = new Batiment(
                    resultats.getString("bat_nom"),
                    resultats.getString("bat_empl")
                );
                batiment.setId(resultats.getInt("bat_id"));
               
                Intervention intervention = new Intervention(description, date, type, statut, technicien, batiment);
                intervention.setId(resultats.getInt("inter_id"));
                listeInterventions.add(intervention);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des interventions par technicien");
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
       
        return listeInterventions;
    }

    public List<Intervention> listerParBatiment(int idBatiment) {
        List<Intervention> listeInterventions = new ArrayList<>();
       
        // Requête avec filtre bâtiment (même style)
        String requeteSQL = "SELECT i.id as inter_id, i.date_intervention, i.type_intervention, i.statut, i.description, " +
                     "t.id as tech_id, t.nom as tech_nom, t.qualification as tech_qual, t.disponible as tech_dispo, " +
                     "b.id as bat_id, b.nom as bat_nom, b.localisation as bat_empl " +
                     "FROM interventions i " +
                     "JOIN techniciens t ON i.id_technicien = t.id " +
                     "JOIN batiments b ON i.id_batiment = b.id " +
                     "WHERE i.id_batiment = " + idBatiment + " ORDER BY i.id";
       
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultats = null;
       
        try {
            connexion = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
            statement = connexion.createStatement();
            resultats = statement.executeQuery(requeteSQL);
           
            while (resultats.next()) {
                // Même création que dans listerTout
                LocalDate date = resultats.getDate("date_intervention").toLocalDate();
                String type = resultats.getString("type_intervention");
                String statut = resultats.getString("statut");
                String description = resultats.getString("description");
               
                Technicien technicien = new Technicien(
                    resultats.getString("tech_nom"),
                    resultats.getString("tech_qual"),
                    resultats.getBoolean("tech_dispo")
                );
                technicien.setId(resultats.getInt("tech_id"));
               
                Batiment batiment = new Batiment(
                    resultats.getString("bat_nom"),
                    resultats.getString("bat_empl")
                );
                batiment.setId(resultats.getInt("bat_id"));
               
                Intervention intervention = new Intervention(description, date, type, statut, technicien, batiment);
                intervention.setId(resultats.getInt("inter_id"));
                listeInterventions.add(intervention);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des interventions par bâtiment");
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
       
        return listeInterventions;
    }

    public void planifier(Intervention intervention) {
        String insertSQL = "INSERT INTO interventions (date_intervention, type_intervention, statut, description, id_technicien, id_batiment) VALUES (?,?,?,?,?,?)";
       
        try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
           
            pstmt.setDate(1, java.sql.Date.valueOf(intervention.getDate()));
            pstmt.setString(2, intervention.getType());
            pstmt.setString(3, intervention.getStatut());
            pstmt.setString(4, intervention.getDescription());
            pstmt.setInt(5, intervention.getTechnicien().getId());
            pstmt.setInt(6, intervention.getBatiment().getId());
           
            int lignesAffectees = pstmt.executeUpdate();
           
            // Récupérer l'ID généré automatiquement
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int nouvelId = generatedKeys.getInt(1);
                intervention.setId(nouvelId);
            }
        } catch (SQLException e) {
            System.out.println("Problème lors de la planification de l'intervention");
            e.printStackTrace();
        }
    }

    public void modifier(Intervention inter) {
        String updateQuery = "UPDATE interventions SET date_intervention=?, type_intervention=?, statut=?, description=?, id_technicien=?, id_batiment=? WHERE id=?";
       
        Connection connection = null;
        PreparedStatement preparedStatement = null;
       
        try {
            connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
            preparedStatement = connection.prepareStatement(updateQuery);
           
            preparedStatement.setDate(1, java.sql.Date.valueOf(inter.getDate()));
            preparedStatement.setString(2, inter.getType());
            preparedStatement.setString(3, inter.getStatut());
            preparedStatement.setString(4, inter.getDescription());
            preparedStatement.setInt(5, inter.getTechnicien().getId());
            preparedStatement.setInt(6, inter.getBatiment().getId());
            preparedStatement.setInt(7, inter.getId());
           
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

    public void supprimer(int interventionId) {
        // Suppression d'une intervention par son ID
        try (Connection c = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
             PreparedStatement ps = c.prepareStatement("DELETE FROM interventions WHERE id=?")) {
            ps.setInt(1, interventionId);
            int deleted = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur suppression intervention ID: " + interventionId);
            e.printStackTrace();
        }
    }
}