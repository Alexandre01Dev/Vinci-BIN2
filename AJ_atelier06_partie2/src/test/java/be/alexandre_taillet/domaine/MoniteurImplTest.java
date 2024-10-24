package be.alexandre_taillet.domaine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoniteurImplTest {
    MoniteurImpl moniteur;
    Sport sport;

    @BeforeEach
    void setUp() {
        moniteur = new MoniteurImpl("Test");
        sport = Mockito.mock(Sport.class);
        Mockito.when(sport.contientMoniteur(moniteur)).thenReturn(true);
    }

    @Test
    void TestMoniteurTC1() {
        Stage stage = Mockito.mock(Stage.class);
        Mockito.when(stage.getMoniteur()).thenReturn(moniteur);
        Mockito.when(stage.getNumeroDeSemaine()).thenReturn(1);
        Mockito.when(stage.getSport()).thenReturn(sport);
        assertAll(() -> assertTrue(moniteur.ajouterStage(stage)),
                () -> assertEquals(1, moniteur.stages().size())
        );
    }

    @Test
    void TestMoniteurTC2() {
        amenerALEtat(1, moniteur);
        Stage stage = mockStageParDefaut(2);
        assertAll(() -> assertTrue(moniteur.ajouterStage(stage)),
                () -> assertEquals(2, moniteur.stages().size()));

    }

    @Test
    void TestMoniteurTC3() {
        amenerALEtat(2,moniteur);

        Stage stage = mockStageParDefaut(3);
        assertAll(() -> assertTrue(moniteur.ajouterStage(stage)),
                () -> assertEquals(3, moniteur.stages().size()));

    }

    @Test
    void TestMoniteurTC4() {
        amenerALEtat(3,moniteur);

        Stage stage = mockStageParDefaut(4);
        assertAll(() -> assertTrue(moniteur.ajouterStage(stage)),
                () -> assertEquals(4, moniteur.stages().size()));

    }

    @Test
    void TestMoniteurTC5() {
        amenerALEtat(3,moniteur);

        Stage stage = mockStageParDefaut(4);
        assertAll(() -> assertTrue(moniteur.ajouterStage(stage)),
                () -> assertEquals(4, moniteur.stages().size()),
                () -> assertFalse(moniteur.ajouterStage(stage)),
                () -> assertEquals(4, moniteur.stages().size()));

    }
    @Test
    void TestMoniteurTC6() {
        amenerALEtat(4,moniteur);

        Stage stage = mockStageParDefaut(4);
        assertAll(
                () -> assertFalse(moniteur.contientStage(stage)),
                () -> assertFalse(moniteur.estLibre(4)),
                () -> assertFalse(moniteur.ajouterStage(stage)),
                () -> assertEquals(4, moniteur.stages().size())
        );
    }

    @Test
    void TestMoniteurTC7() {
        amenerALEtat(4,moniteur);
        Moniteur nouveauMoniteur;
        Stage stage = mockStageParDefaut(5, nouveauMoniteur = new MoniteurImpl("Dimitri"));
        assertAll(
                () -> assertFalse(stage.getMoniteur().equals(moniteur)),
                () -> assertFalse(moniteur.ajouterStage(stage)),
                () -> assertEquals(4, moniteur.stages().size())
        );
    }

    @Test
    void TestMoniteurTC8() {
        amenerALEtat(4,moniteur);
        Stage stage = mockStageParDefaut(5, null);
        assertAll(
                () -> assertTrue(moniteur.ajouterStage(stage)),
                () -> assertEquals(5, moniteur.stages().size())
        );
    }

    @Test
    void TestMoniteurTC9() {
        amenerALEtat(4,moniteur);
        Sport nouveauSport = Mockito.mock(Sport.class);
        Mockito.when(nouveauSport.contientMoniteur(moniteur)).thenReturn(false);

        Stage stage = mockStageParDefaut(5, moniteur,nouveauSport);
        assertAll(
                () -> assertFalse(nouveauSport.contientMoniteur(moniteur)),
                () -> assertFalse(moniteur.ajouterStage(stage)),
                () -> assertEquals(4, moniteur.stages().size())
        );
    }




    private Stage mockStageParDefaut(int etat){
        return mockStageParDefaut(etat,moniteur,sport);
    }

    private Stage mockStageParDefaut(int etat,Moniteur moniteur){
        return mockStageParDefaut(etat,moniteur,sport);
    }
    private Stage mockStageParDefaut(int etat,Moniteur moniteur,Sport sport){
        Stage stage = Mockito.mock(Stage.class);
        Mockito.when(stage.getMoniteur()).thenReturn(moniteur);
        Mockito.when(stage.getNumeroDeSemaine()).thenReturn(etat);
        Mockito.when(stage.getSport()).thenReturn(sport);
        return stage;
    }
    private boolean amenerALEtat(List<Stage> stages, Moniteur moniteur){
        for (Stage o : stages) {

            if(!moniteur.ajouterStage(o))
                return false;
        }
        return true;
    }

    private boolean amenerALEtat(int etats, Moniteur moniteur){
        return amenerALEtat(creerStages(etats),moniteur);
    }


    private List<Stage> creerStages(int etats) {
        List<Stage> stages = new ArrayList<>();
        for (int i = 1; i < etats+1; i++) {
           Stage stage = Mockito.mock(Stage.class);
           Mockito.when(stage.getSport()).thenReturn(sport);
           Mockito.when(stage.getNumeroDeSemaine()).thenReturn(i);
           stages.add(stage);
        }
        return stages;
    }


}