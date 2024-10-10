package be.alexandre_taillet.vinci.domaine;


import be.alexandre_taillet.vinci.Ingredient;
import be.alexandre_taillet.vinci.IngredientQuantifie;
import be.alexandre_taillet.vinci.Instruction;
import be.alexandre_taillet.vinci.Unite;

import java.time.Duration;
import java.util.*;

public class Plat  {


    private final String nom;
    private final int nbPersonnes;
    private final Difficulte niveauDeDifficulte;
    private final Cout cout;
    private Duration dureeEnMinutes;
    private final List<Instruction> recette = new ArrayList<>();
    private final Set<IngredientQuantifie> ingredients = new HashSet<>();
    private final Type type;

    public Plat(String nom, int nbPersonnes, Difficulte niveauDeDifficulte, Cout cout, Type type) {
        this.nom = nom;
        this.nbPersonnes = nbPersonnes;
        this.niveauDeDifficulte = niveauDeDifficulte;
        this.cout = cout;
        this.type = type;
        this.dureeEnMinutes = Duration.ofMinutes(0);
    }
    public void ajouterDuree(Instruction instruction){
        dureeEnMinutes = dureeEnMinutes.plus(instruction.getDureeEnMinutes());
    }

    public void retiterDuree(Instruction instruction){
        dureeEnMinutes = dureeEnMinutes.minus(instruction.getDureeEnMinutes());
    }
    public void insererInstruction(int position, Instruction instruction) {
        position--;
        System.out.println(instruction + " pos = "+ (recette.size()));
        if(position < 0 || position > recette.size()){
            throw new IllegalArgumentException("Position invalide");
        }
        recette.add(position,instruction);
        ajouterDuree(instruction);
    }

    public void ajouterInstruction(Instruction instruction) {
        System.out.println(instruction + " pos = "+ (recette.size()));
        recette.add(instruction);
        ajouterDuree(instruction);
    }

    public void remplacerInstruction(int position, Instruction instruction) {
        position--;
        if(position < 0 || position > recette.size()){
            throw new IllegalArgumentException("Position invalide");
        }

        System.out.println("Replace "+ recette.get(position) +" to "+ instruction);
        retiterDuree(recette.get(position));
        recette.set(position,instruction);
        ajouterDuree(instruction);
    }

    public void supprimerInstruction(int position) {
        if(position < 0 || position > recette.size()){
            throw new IllegalArgumentException("Position invalide");
        }
        retiterDuree(recette.get(position-1));
        recette.remove(position-1);

    }


    public Type getType() {
        return type;
    }

    public void ajouterIngredient(IngredientQuantifie ingredient) {
        ingredients.add(ingredient);
    }

    public void ajouterIngredient(Ingredient ingredient, int quantite, Unite unite) {
        ingredients.add(new IngredientQuantifie(ingredient, quantite, unite));
    }

    public void ajouterIngredient(Ingredient ingredient, int quantite) {
        ingredients.add(new IngredientQuantifie(ingredient, quantite, Unite.NEANT));
    }

    public void modifierIngredient(Ingredient ingredient, int quantite, Unite unite) {
        IngredientQuantifie ing = trouverIngredientQuantifie(ingredient);
        if (ing != null) {
            ingredients.remove(ing);
            ingredients.add(new IngredientQuantifie(ingredient, quantite, unite));
        }
    }

    public void supprimerIngredient(Ingredient ingredient) {
        IngredientQuantifie ing = trouverIngredientQuantifie(ingredient);
        if (ing != null) {
            ingredients.remove(ing);
        }
    }

    public IngredientQuantifie trouverIngredientQuantifie(Ingredient ingredient) {
        for (IngredientQuantifie ing : ingredients) {
            if (ing.getIngredient().equals(ingredient)) {
                return ing;
            }
        }
        return null;
    }

    public String getNom() {
        return nom;
    }

    public int getNbPersonnes() {
        return nbPersonnes;
    }

    public Difficulte getNiveauDeDifficulte() {
        return niveauDeDifficulte;
    }

    public Cout getCout() {
        return cout;
    }

    public Duration getDureeEnMinutes() {
        return dureeEnMinutes;
    }

    public List<Instruction> getRecette() {
        return recette;
    }

    public Set<IngredientQuantifie> getIngredients() {
        return ingredients;
    }

    public List<Instruction> instructions() {
        return Collections.unmodifiableList(recette);
    }
    @Override
    public String toString() {
        String hms = String.format("%d h %02d m", dureeEnMinutes.toHours(), dureeEnMinutes.toMinutes()%60);
        String res = this.nom + "\n\n";
        res += "Pour " + this.nbPersonnes + " personnes\n";
        res += "Difficulté : " + this.niveauDeDifficulte + "\n";
        res += "Coût : " + this.cout + "\n";
        res += "Durée : " + hms + " \n\n";
        res += "Ingrédients :\n";
        for (IngredientQuantifie ing : this.ingredients) {
            res += ing + "\n";
        }
        int i = 1;
        res += "\n";
        for (Instruction instruction : this.recette) {
            res += i++ + ". " + instruction + "\n";
        }
        return res;
    }
    public enum Cout {
        $,$$,$$$,$$$$;


        @Override
        public String toString() {
            return name().replace("$","€");
        }
    }

    public enum Difficulte {
        X,XX,XXX,XXXX,XXXXX;


        @Override
        public String toString() {
            return name().replace("X","*");
        }
    }

    public enum Type {
        ENTREE,PLAT,DESSERT;
    }


}
