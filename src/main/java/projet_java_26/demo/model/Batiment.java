package projet_java_26.demo.model;

/**
 * Classe reprÃ©sentant un bÃ¢timent du complexe
 * Contient les informations de base sur les lieux d'intervention
 */
public class Batiment {

    private int id;
    private String nom;
    private String localisation; // Adresse ou emplacement prÃ©cis

    // Constructeur par dÃ©faut - nÃ©cessaire pour certains frameworks
    public Batiment() {
        // vide pour l'instant
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

    // MÃ©thode toString pour afficher les infos de base
    @Override
    public String toString() {
        return nom + " - " + localisation;
    }
}