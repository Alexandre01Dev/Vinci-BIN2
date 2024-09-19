import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Commande implements Iterable<LigneDeCommande>{
    private static int numeroSuivant = 1;
    private final int numero;
    private final Client client;
    private LocalDateTime date;
    private List<LigneDeCommande> lignesCommande;

    public Commande(Client client) {
        if(!client.enregistrerCommande(this)) {
            throw new IllegalArgumentException("impossible de créer une commande pour un client ayant encore une commande en cours");
        }
        this.numero = numeroSuivant++;
        this.client = client;
        this.date = LocalDateTime.now();
        this.lignesCommande = new ArrayList<>();
    }

    @Override
    public Iterator<LigneDeCommande> iterator() {
        return lignesCommande.iterator();
    }

    public int getNumero() {
        return numero;
    }

    public Client getClient() {
        return client;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public boolean ajouter(Pizza pizza, int quantite) {
        if(client.getCommandeEnCours() != this){
            return false;
        }

        for (LigneDeCommande ligne : lignesCommande) {
            if(ligne.getPizza().equals(pizza)){
                ligne.setQuantite(ligne.getQuantite() + quantite);
                return true;
            }
        }
        return lignesCommande.add(new LigneDeCommande(pizza, quantite));
    }

    public boolean ajouter(Pizza pizza) {
        return ajouter(pizza, 1);
    }

    public double calculerMontantTotal() {
        double montantTotal = 0;
        for(LigneDeCommande ligne : lignesCommande) {
            montantTotal += ligne.calculerPrixTotal();
        }
        return montantTotal;
    }

    public String detailler(){
        StringBuilder sb = new StringBuilder();
        for (LigneDeCommande ligneDeCommande : lignesCommande) {
            sb.append(ligneDeCommande).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        DateTimeFormatter formater = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        String encours = "";
        if (client.getCommandeEnCours() == this)
            encours = " (en cours)";
        return "Commande n° " + numero + encours + " du " + client + "\ndate : " + formater.format(date);
    }
}
