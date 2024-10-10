package be.alexandre_taillet.vinci.domaine;

import java.util.*;

public class Livre {
    private Comparator<Plat> comparator = new Comparator<Plat>() {
            @Override
            public int compare(Plat o1, Plat o2) {
                int difficulty = o1.getNiveauDeDifficulte().compareTo(o2.getNiveauDeDifficulte());
                if(difficulty == 0){
                    return o1.getNom().compareTo(o2.getNom());
                }
                return difficulty;
            }
    };
    private final Map<Plat.Type,SortedSet<Plat>> plats = new HashMap<>();


    public boolean ajouterPlat(Plat plat){
        plats.putIfAbsent(plat.getType(), new TreeSet<>(comparator));
        return plats.get(plat.getType()).add(plat);
    }

    public boolean supprimerPlat(Plat plat){
        return plats.get(plat.getType()).remove(plat);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Plat.Type type : plats.keySet()){
            String camelCaseType = type.name().charAt(0) + type.name().substring(1).toLowerCase();
            sb.append(camelCaseType);
            sb.append("\n");
            sb.append("=====\n");
            for (Plat plat : plats.get(type)){
                sb.append(plat.getNom()).append("\n");
            }
        }
        return sb.toString();
    }
}
