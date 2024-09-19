public class Client {
    private static int numeroSuivant = 1;
    private final int numero;
    private final String nom;
    private final String prenom;
    private final String telephone;
    private Commande commandeEnCours;

    public Client(String nom, String prenom, String telephone) {
        this.numero = numeroSuivant++;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

    public int getNumero() {
        return numero;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    @Override
    public String toString() {
        return "client n° " + numero + " (" + prenom  + " " + nom + ", telephone : " + telephone +")";
    }

    public Commande getCommandeEnCours() {
        return commandeEnCours;
    }

    public boolean enregistrerCommande(Commande commande) {
        if(commandeEnCours != null) {
            return false;
        }
        commandeEnCours = commande;
       return true;
    }

    public boolean cloturerCommandeEnCours() {
        if(commandeEnCours == null) {
            return false;
        }
        commandeEnCours = null;
        return true;
    }
}
