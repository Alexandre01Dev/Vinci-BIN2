package be.alexandre_taillet.vinci;


public enum Unite {

    GRAMME("gr"),
    KILOGRAMME("kg"),
    LITRE("l"),
    MILLILITRE("ml"),
    CENTILITRE("cl"),
    DECILITRE("dl"),
    CUILLER_A_CAFE("cc"),
    CUILLER_A_THE("cs"),
    CUILLER_A_DESSERT("cd"),
    PINCEE("pincée"),
    UN_PEU("peu"),
    NEANT("");

    private final String abbreviation;

    Unite(String abbreviation){
        this.abbreviation = abbreviation;
    }

    @Override
    public String toString() {
        return abbreviation;
    }
}
