package projet_java_26.demo.model;

/**
 * Classe reprsentant un technicien de maintenance
 * Gere les informations personnelles et les comptences
 */
public class Technicien {

    private int id;
    private String nom;
    private String qualification; // Spcialité du technicien
    private boolean disponible;   // true si libre pour une mission

    // Constructeur par dfaut - ncessaire pour certains frameworks
    public Technicien() {
        // vide pour l'instant
    }

    // Constructeur qu'on utilise normalement
    public Technicien(String nom, String qualification, boolean disponible) {
        this.nom = nom;
        this.qualification = qualification;
        this.disponible = disponible;
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

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // Mthode toString pour afficher les infos de base
    @Override
    public String toString() {
        return nom + " (" + qualification + ")";
    }
}