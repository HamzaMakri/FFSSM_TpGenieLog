package FFSSM;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/*
* Changements:
*
* CLASS => Moniteur // employeurActuel => Changer && par || et on enleve le !
*
* CLASS => LICENCE // Ajout du if else
*
* */

public class FFSSMJUnitTest {

    //LES ATTRIBUTS
    Moniteur hamza;
    Plongeur ikhlass;
    Plongeur maxence;
    Club club1;


    // BEFORE EACH POUR LES INSTANCES DE TEST
    @BeforeEach
    public void setUp() {

        hamza = new Moniteur("HM6920007", "MAKRI", "Hamza", "7 rue Robespierre 69200 Vénissieux", "0767474400", LocalDate.of(2000, 3,7), 76320);
        ikhlass = new Plongeur("IH75200874","HAJJAM", "Ikhlass", "12 rue Pasteur 81100 Castres ", "0765449800", LocalDate.of(2000, 12,11));
        maxence = new Plongeur("ML85202283","LEROY", "Maxence", "6 Grande rue 69000 Vernaison ", "07653986489", LocalDate.of(2000, 11,06));
        club1 = new Club(hamza,"Aquaman", "0346779800");

    }

    // ========================== TESTS PLONGEUR ==========================
    @Test
    public void testAjouteLicence() throws Exception {

        Licence licence1 = new Licence(hamza, "12GGE", LocalDate.of(2021, 12,2), club1);
        Licence licence2 = new Licence(hamza, "MR049", LocalDate.of(2021, 6,4), club1);
        Licence licence3 = new Licence(ikhlass, "08LOU", LocalDate.of(2020, 11,3), club1);

        assertThrows(Exception.class, () -> {
            hamza.derniereLicence();
        });

        hamza.ajouteLicence("12GGE", LocalDate.of(2021, 12,2), club1);

        assertEquals(hamza.derniereLicence(), licence1);

        hamza.ajouteLicence("MR049", LocalDate.of(2021, 6,4), club1);

        assertEquals(hamza.derniereLicence(), licence2);
    }

    // ========================== TESTS MONITEUR ==========================


    @Test
    public void testNouvelEmbauche() {
        Embauche emb1 = new Embauche(LocalDate.of(2020, 6,4), hamza, club1);
        Embauche emb2 = new Embauche(LocalDate.of(2020, 3,3), hamza, club1);



        ArrayList<Embauche> listEmbauches = new ArrayList<>();
        listEmbauches.add(emb1);
        listEmbauches.add(emb2);

        hamza.nouvelleEmbauche(club1, LocalDate.of(2020, 6,4));
        hamza.nouvelleEmbauche(club1, LocalDate.of(2020, 3,3));

        assertEquals(listEmbauches, hamza.emplois());
    }

    @Test
    public void testTerminerEmbauche() throws Exception {

        assertThrows(Exception.class, () -> {
            hamza.terminerEmbauche(LocalDate.now());
        });

        hamza.nouvelleEmbauche(club1, LocalDate.of(2020, 6,4));

        hamza.terminerEmbauche(LocalDate.now());

        assertTrue(hamza.emplois().get(hamza.emplois().size()-1).estTerminee());
    }

    @Test
    public void testEmployeurActuel() throws Exception {

        //TODO regler ça
        assertEquals(hamza.employeurActuel(), Optional.empty());  // On vérifie qu'un optional vide est renvoyé si la liste d'embauche est vide

        hamza.nouvelleEmbauche(club1, LocalDate.of(2020, 6,4));

        assertEquals(hamza.employeurActuel(), Optional.of(club1)); // On vérifie qu'on revoie bien le club quand on a une seule embauche en cours

        Club club2 = new Club(hamza,"GlouGlou", "0346798763");

        hamza.nouvelleEmbauche(club1, LocalDate.of(2020, 6,4));
        hamza.terminerEmbauche(LocalDate.now());
        hamza.nouvelleEmbauche(club2, LocalDate.now());

        assertEquals(hamza.employeurActuel(), Optional.of(club2)); // On vérifie que ça marche même avec plus d'une embauche

        hamza.terminerEmbauche(LocalDate.now());

        assertEquals(hamza.employeurActuel(), Optional.empty());  // On vérifie que s'il n'y a pas d'embauche en cours on renvoie un optional vide


    }

    // ========================== TESTS EMBAUCHE ==========================

    @Test
    public void testEstTerminee() throws Exception {

        // On a ajouté un emploi au moniteur, il a donc un emploi en cours, donc on check si estTerminee() return false
        hamza.nouvelleEmbauche(club1, LocalDate.of(2002, 3, 7));
        assertFalse(hamza.emplois().get(hamza.emplois().size()-1).estTerminee());


        // On a terminé l'embauche, on vérifie donc que estTerminee() retourne True
        hamza.terminerEmbauche(LocalDate.of(2021, 3, 7));
        assertTrue(hamza.emplois().get(hamza.emplois().size()-1).estTerminee());


        // On va ajouter une nouvelle embauche dans le club 2 au moniteur
        // Et la terminer d'une autre manière (càd avec la fonction terminer() de la class Embauche)
        // on vérifie donc que estTerminee() retourne True
        // et que la date de fin assignée est la bonne

        Club club2 = new Club(hamza,"GlouGlou", "0346798763");
        hamza.nouvelleEmbauche(club2, LocalDate.of(2002, 3, 7));
        hamza.emplois().get(hamza.emplois().size()-1).terminer(LocalDate.of(2021, 9, 2));

        assertTrue(hamza.emplois().get(hamza.emplois().size()-1).estTerminee());
        assertEquals(hamza.emplois().get(hamza.emplois().size()-1).getFin(),LocalDate.of(2021, 9, 2) );

    }

    // ==========================   TESTS CLUB   ==========================

    @Test
    public void testPlongeesNonConformes() throws Exception {

        Plongee plongee1 = new Plongee(new Site("Danlo", "Cmouillé"),hamza, LocalDate.of(2021,12,7),20,2);
        Plongee plongee2 = new Plongee(new Site("Soulo", "YaDPoisson"),hamza, LocalDate.of(2021,12,7),20,2);
        Plongee plongee3 = new Plongee(new Site("Ocean Aquatique", "CaTrempe"),hamza, LocalDate.of(2021,12,7),20,2);

        hamza.ajouteLicence("12GGE", LocalDate.of(2021, 12,2), club1);
        maxence.ajouteLicence("MR049", LocalDate.of(2021, 6,4), club1);
        ikhlass.ajouteLicence("08LOU", LocalDate.of(2020, 11,3), club1); // Cette licence est expirée

        plongee1.ajouteParticipant(hamza);
        plongee1.ajouteParticipant(maxence);

        plongee2.ajouteParticipant(maxence);
        plongee2.ajouteParticipant(ikhlass);  // Cette plongée n'est plus conforme

        plongee3.ajouteParticipant(ikhlass);  // Cette plongée n'est plus conforme
        plongee3.ajouteParticipant(hamza);

        HashSet<Plongee> plongeesNonConformes = new HashSet<>();

        club1.organisePlongee(plongee1);

        assertEquals(club1.plongeesNonConformes(), plongeesNonConformes);

        plongeesNonConformes.add(plongee2);
        club1.organisePlongee(plongee2);

        assertEquals(club1.plongeesNonConformes(), plongeesNonConformes);

        plongeesNonConformes.add(plongee3);
        club1.organisePlongee(plongee3);

        assertEquals(club1.plongeesNonConformes(), plongeesNonConformes);
    }

    // ========================== TESTS PLONGEE  ==========================

    @Test
    public void testEstConforme() throws Exception {
        Plongee plongee1 = new Plongee(new Site("Danlo", "Cmouillé"),hamza, LocalDate.of(2021,12,7),20,2);

        hamza.ajouteLicence("12GGE", LocalDate.of(2021, 12,2), club1);
        maxence.ajouteLicence("MR049", LocalDate.of(2021, 6,4), club1);
        ikhlass.ajouteLicence("08LOU", LocalDate.of(2020, 11,3), club1);

        plongee1.ajouteParticipant(hamza);
        plongee1.ajouteParticipant(maxence);

        ArrayList<Licence> listLicences = new ArrayList<>();

        listLicences.add(hamza.derniereLicence());
        listLicences.add(maxence.derniereLicence());

        assertTrue(plongee1.estConforme());  // On verifie que la plongée est conforme

        plongee1.ajouteParticipant(ikhlass);
        listLicences.add(ikhlass.derniereLicence());  // On ajoute une licence non conforme

        assertFalse(plongee1.estConforme());  // La plongée ne doit plus être conforme
        assertEquals(plongee1.getPalanquees(), listLicences); // On vérifie que les ajouts se sont bien fait

    }


    // ========================== TESTS LICENCE  ==========================

    @Test
    public void testEstValide() throws Exception {
        Licence l1 = new Licence(ikhlass, "LC873HE", LocalDate.of(2010, 1,1), club1);

        assertTrue(l1.estValide(LocalDate.of(2010, 1,3)));
        assertFalse(l1.estValide(LocalDate.of(2015, 1,1)));
        assertTrue(l1.estValide(LocalDate.of(2010, 1,1)));
    }

}



