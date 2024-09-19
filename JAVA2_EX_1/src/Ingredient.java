public class Ingredient {
    private final String nom;
    private double prix;

    public Ingredient(String nom, double prix) {
        this.nom = nom;
        this.prix = prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }

}
