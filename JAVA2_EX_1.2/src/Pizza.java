import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public abstract class Pizza implements Iterable<Ingredient> {
    public static final  double PRIX_BASE = 5.0;
    private String titre;
    private String description;
    private List<Ingredient> ingredients;

    public Pizza(String titre, String description, List<Ingredient> ingredients) {
        this.titre = titre;
        this.description = description;
        this.ingredients = new ArrayList<>();
        for (Ingredient ingredient : ingredients){
            if(this.ingredients.contains(ingredient)){
                throw new IllegalArgumentException("Il ne peut pas y avoir deux fois le même ingrédient dans une pizza");
            }
            this.ingredients.add(ingredient);
        }
    }

    public Pizza(String titre, String description) {
        this.titre = titre;
        this.description = description;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public boolean ajouter(Ingredient ingredient) {
        if(ingredients.contains(ingredient)){
            throw new IllegalArgumentException("L'ingrédient " + ingredient.getNom() + " est déjà présent dans la pizza");
        }
        return ingredients.add(ingredient);
    }

    public boolean supprimer(Ingredient ingredient) {
        if(!ingredients.contains(ingredient)){
            throw new IllegalArgumentException("L'ingrédient " + ingredient.getNom() + " n'est pas présent dans la pizza");
        }
        return ingredients.remove(ingredient);
    }

    public double calculerPrix() {
        double prix = PRIX_BASE;
        for (Ingredient ingredient : ingredients) {
            prix += ingredient.getPrix();
        }
        return prix;
    }

    public Iterator<Ingredient> iterator() {
        return ingredients.iterator();
    }

    @Override
    public String toString() {
        String infos = titre + "\n" + description + "\nIngrédients : ";
        for (Ingredient ingredient : ingredients){
            infos +="\n" + ingredient.getNom();
        }
        infos +="\nprix : " + calculerPrix() + " euros";
        return infos;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Pizza that = (Pizza) object;
        return Objects.equals(titre, that.titre);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(titre);
    }
}
