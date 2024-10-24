package be.alexandre_taillet.domaine.be.alexandre01.domaine;

import be.alexandre_taillet.domaine.Moniteur;
import be.alexandre_taillet.domaine.MoniteurImpl;
import be.alexandre_taillet.domaine.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoniteurImplTest {
    private MoniteurImpl moniteur;
    private SportStub sport;
    private StageStub stage;
    private StageStub stage2;
    private StageStub stage3;
    private StageStub stage4;
    @BeforeEach
    void setUp() {
         moniteur = new MoniteurImpl("test");
        sport = new SportStub(true);
        stage = new StageStub(sport,1,moniteur);
        stage2 = new StageStub(sport,2,moniteur);
        stage3 = new StageStub(sport,3, moniteur);

    }

    @Test
    void TestMoniteurTC1() {
        assertTrue(moniteur.ajouterStage(stage));
        assertEquals(1, moniteur.stages().size());
        System.out.println("Le stage est ajouté");
    }

    @Test
    void TestMoniteurTC2() {
        assertTrue(amenerALEtat(2));
        assertEquals(2, moniteur.stages().size());
        System.out.println("Le stage est ajouté");
    }

    @Test
    void TestMoniteurTC3() {
        assertTrue(amenerALEtat(3));
        assertEquals(3, moniteur.stages().size());
        System.out.println("Le stage est ajouté");
    }

    @Test
    void TestMoniteurTC4() {
        assertTrue(amenerALEtat(4));
        assertEquals(4, moniteur.stages().size());
        System.out.println("Le stage est ajouté");
    }

    @Test
    void TestMoniteurTC5() {
        List<StageStub> stageStubs = creerStages(4);
        amenerALEtat(stageStubs);

        moniteur.supprimerStage(stageStubs.get(0));
        assertFalse(moniteur.contientStage(stageStubs.get(0)));
        assertEquals(3, moniteur.stages().size());
        System.out.println("Le stage n'est plus présent");
    }

    @Test
    void TestMoniteurTC6() {
        List<StageStub> stageStubs = creerStages(3);
        amenerALEtat(stageStubs);

        moniteur.supprimerStage(stageStubs.get(0));
        assertFalse(moniteur.contientStage(stageStubs.get(0)));
        assertEquals(2, moniteur.stages().size());
        System.out.println("Le stage n'est plus présent");
    }

    @Test
    void TestMoniteurTC7() {
        List<StageStub> stageStubs = creerStages(2);
        amenerALEtat(stageStubs);

        moniteur.supprimerStage(stageStubs.get(0));
        assertFalse(moniteur.contientStage(stageStubs.get(0)));
        assertEquals(1, moniteur.stages().size());
        System.out.println("Le stage n'est plus présent");
    }
    @Test
    void TestMoniteurTC8() {
        List<StageStub> stageStubs = creerStages(1);
        amenerALEtat(stageStubs);

        moniteur.supprimerStage(stageStubs.get(0));
        assertTrue(moniteur.stages().isEmpty());
        System.out.println("Il n'y a plus aucun stage");
    }

    @Test
    void TestMoniteurTC9() {
        List<StageStub> stageStubs = creerStages(4);
        amenerALEtat(stageStubs);


        assertTrue( moniteur.contientStage(stageStubs.get(0)));
        assertFalse(moniteur.ajouterStage(stageStubs.get(0)));
        assertEquals(4, moniteur.stages().size());
        System.out.println("stage non ajouté");
    }

    @Test
    void TestMoniteurTC10() {
        List<StageStub> stageStubs = creerStages(4);
        amenerALEtat(stageStubs);
        StageStub nouveauStage = new StageStub(sport,1,moniteur);
        assertFalse(moniteur.estLibre(nouveauStage.numeroDeSemaine));
        assertFalse(moniteur.ajouterStage(stageStubs.get(0)));
        assertEquals(4, moniteur.stages().size());
        System.out.println("stage non ajouté");
    }

    @Test
    void TestMoniteurTC11() {
        List<StageStub> stageStubs = creerStages(4);
        amenerALEtat(stageStubs);
        StageStub nouveauStage = new StageStub(sport,1,moniteur);
        assertFalse(moniteur.contientStage(nouveauStage));
        assertFalse(moniteur.supprimerStage(nouveauStage));
        assertEquals(4, moniteur.stages().size());
        System.out.println("Tous les stages sont présents rien n'a été supprimé");
    }

    @Test
    void TestMoniteurTC12() {
        List<StageStub> stageStubs = creerStages(4);
        amenerALEtat(stageStubs);
        StageStub nouveauStage = new StageStub(sport,6,new MoniteurImpl("Jean Christophe"));
        assertFalse(nouveauStage.moniteur == null || moniteur == nouveauStage.getMoniteur());
        assertFalse(moniteur.ajouterStage(nouveauStage));
        assertEquals(4, moniteur.stages().size());
        System.out.println("stage non ajouté");
    }


    @Test
    void TestMoniteurTC13() {
        List<StageStub> stageStubs = creerStages(4);
        amenerALEtat(stageStubs);
        StageStub nouveauStage = new StageStub(sport,7,moniteur);
        assertTrue(nouveauStage.moniteur == null || moniteur == nouveauStage.getMoniteur());
        assertTrue(moniteur.ajouterStage(nouveauStage));
        assertEquals(5, moniteur.stages().size());
        System.out.println("stage ajouté");
    }

    @Test
    void TestMoniteurTC14() {
        List<StageStub> stageStubs = creerStages(4);
        amenerALEtat(stageStubs);
        SportStub nouveauSport = new SportStub(false);
        StageStub nouveauStage = new StageStub(nouveauSport,7,null);
        assertFalse(nouveauSport.contientMoniteur(moniteur));
        assertFalse(moniteur.ajouterStage(nouveauStage));
        assertEquals(4, moniteur.stages().size());
        System.out.println("stage non ajouté");
    }


    private boolean amenerALEtat(int etat){
       return amenerALEtat(creerStages(etat));
    }

    private boolean amenerALEtat(List<StageStub> stageStubs){
        for (StageStub o : stageStubs) {
            if(!moniteur.ajouterStage(o))
                return false;
        }
        return true;
    }

    private List<StageStub> creerStages(int etat){
        List<StageStub> list = new ArrayList<>();
        for (int i = 1; i < etat+1; i++) {
            list.add(new StageStub(sport,i,moniteur));
        }
        return list;
    }
}