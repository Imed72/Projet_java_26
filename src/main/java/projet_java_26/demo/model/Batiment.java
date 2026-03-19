package projet_java_26.demo.model;

/**
 * Classe reprsentant un btiment du complexe
 * Contient les informations de base sur les lieux d'intervention
 */
public class Batiment {

    private int id;
    private String nom;
    private String localisation; // Adresse ou emplacement prcis

    // Constructeur par défaut - nécessaire pour certains frameworks
    // et pour permettre la création d'instances sans paramètres
    public Batiment() {
        // Constructeur vide pour permettre la création d'instances sans paramètres
    }

    // Constructeur qu'on utilise normalement
    public Batiment(String nom, String localisation) {
        this.nom = nom;
        this.localisation = localisation;
    }

    // Getters et Setters classiques

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    // Methode toString pour afficher les infos de base
    @Override
    public String toString() {
        return nom + " - " + localisation;
    }
}