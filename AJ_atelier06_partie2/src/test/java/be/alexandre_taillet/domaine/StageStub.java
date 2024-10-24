package be.alexandre_taillet.domaine;

import java.util.Set;

public class StageStub implements Stage {
    final Sport sport;
    final int numeroDeSemaine;
    final Moniteur moniteur;

    public StageStub(Sport sportStub, int numeroDeSemaine, Moniteur moniteur) {
        this.sport = sportStub;
        this.numeroDeSemaine = numeroDeSemaine;
        this.moniteur = moniteur;
    }


    @Override
    public String getIntitule() {
        return null;
    }

    @Override
    public String getLieu() {
        return null;
    }

    @Override
    public int getNumeroDeSemaine() {
        return numeroDeSemaine;
    }

    @Override
    public Sport getSport() {
        return sport;
    }

    @Override
    public boolean enregistrerMoniteur(Moniteur moniteur) {
        return false;
    }

    @Override
    public boolean supprimerMoniteur() {
        return false;
    }

    @Override
    public Moniteur getMoniteur() {
        return moniteur;
    }

    @Override
    public boolean ajouterEnfant(Enfant enfant) {
        return false;
    }

    @Override
    public boolean supprimerEnfant(Enfant enfant) {
        return false;
    }

    @Override
    public boolean contientEnfant(Enfant enfant) {
        return false;
    }

    @Override
    public int nombreDEnfants() {
        return 0;
    }

    @Override
    public Set<Enfant> enfants() {
        return null;
    }
}
