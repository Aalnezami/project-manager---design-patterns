package Main;

import Facade_Q3.GestionProjets;
import Controleur_Q7.*;
import Vues_Q6.*;

public class Main {
    public static void main(String[] args) {
        // creation du Modèle - Façade
        GestionProjets modele = new GestionProjets();

        // creation du Contrôleur relié au Modèle
        ControleurProjets controleur = new ControleurProjets(modele);

        new VueListeProjets(modele, controleur);
        new VueProjet(modele, controleur);
    }
}