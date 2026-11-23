package Factory_Q1;

import java.time.LocalDate;
import GestionProjet.*;

public class FabriqueProjet {
    private static int nombreProjetsCrees = 0;

    public FabriqueProjet() {
    }

    /**********************************************************
     * 
     * @param nomProjet
     * @param debut
     * @param nomEquipe
     * @param nbPersonnes
     * @return
     */
    public Projet creerProjetStandard(String nomProjet, LocalDate debut, String nomEquipe, int nbPersonnes) {
        
        // création de l'équipe unique
        Equipe equipe = new Equipe(nomEquipe, nbPersonnes);
        
        // création des 3 tâches standard, 1 semaine chacune et non terminées
        Tache spec = new Tache("spec (déafult)", 7, equipe);
        Tache dev = new Tache("dev (déafult)", 7, equipe);
        Tache tests = new Tache("tests (déafult)", 7, equipe);

        // calcul de la date de fin
        LocalDate fin = debut.plusWeeks(3);
        Projet prj = new Projet(nomProjet, debut, fin);

        // association
        prj.ajouterEquipe(equipe);
        prj.ajouterTache(spec);
        prj.ajouterTache(dev);
        prj.ajouterTache(tests);

        // incrémentation du compteur
        FabriqueProjet.nombreProjetsCrees++;
        
        return prj;
    }

    /**********************************************************
     * 
     * @return
     */
    public static int getNombresProjetsCrees() {
        return FabriqueProjet.nombreProjetsCrees;
    }
}