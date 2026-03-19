package projet_java_26.demo.model;

import java.time.LocalDate;

/**
 * Classe représentant une intervention technique
 * Gère les différents types d'interventions sur les bâtiments
 */
public class Intervention {

    private int id;
    private String description;
    private LocalDate date;
    private String type;      // peut êtr
    private String statut;    // Planifiée / En cours / Terminée

    // Liens vers les autres objets
    private Technicien technicien;
    private Batiment batiment;

    // Constructeur par défaut - nécessaire pour certains frameworks
    public Intervention() {
        // vide pour l'instant
    }

    // Constructeur qu'on utilise normalement
    public Intervention(String description, LocalDate date, String type, String statut,
                        Technicien technicien, Batiment batiment) {
        this.description = description;
        this.date = date;
        this.type = type;
        this.statut = statut;
        this.technicien = technicien;
        this.batiment = batiment;
    }

    // Getters et Setters classiques

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Technicien getTechnicien() {
        return technicien;
    }

    public void setTechnicien(Technicien technicien) {
        this.technicien = technicien;
    }

    public Batiment getBatiment() {
        return batiment;
    }

    public void setBatiment(Batiment batiment) {
        this.batiment = batiment;
    }

    // Méthode toString pour afficher les infos de base
    @Override
    public String toString() {
        return "Intervention " + id + " - " + description;
    }
}