package be.alexandre_taillet.vinci;

import java.time.Duration;

public class Instruction {
    private String description;
    private Duration dureeEnMinutes;

    public Instruction(String description, int dureeEnMinutes) {
        this.description = description;
        this.dureeEnMinutes = Duration.ofMinutes(dureeEnMinutes);
    }

    public String getDescription() {
        return description;
    }

    public Duration getDureeEnMinutes() {
        return dureeEnMinutes;
    }

    public void setDureeEnMinutes(int dureeEnMinutes) {
        this.dureeEnMinutes = Duration.ofMinutes(dureeEnMinutes);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        //(00:15) Couper les légumes
        // format : (HH:mm) description
        int minutes = (int) dureeEnMinutes.toMinutes();

        return "(" +  String.format("%d:%02d", minutes / 60, minutes%60) +")"+ description;
    }
}
