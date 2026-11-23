package Controleur_Q7;

import Facade_Q3.*;

import java.awt.Component;
import java.time.LocalDate;
import java.util.List;

import GestionProjet.*;
import Vues_Q6.*;

public class ControleurProjets {
	private GestionProjets modele;

	/**********************************************************
	 * 
	 * @param modele
	 */
	public ControleurProjets(GestionProjets modele) {
		this.modele = modele;
	}

	/**********************************************************
	 * 
	 * @param nom
	 * @param debut
	 * @param nomEquipe
	 * @param nbPersonnes
	 */
	public void ajouterProjet(String nom, LocalDate debut, String nomEquipe, int nbPersonnes) {
		try {
			modele.ajouterProjet(nom, debut, nomEquipe, nbPersonnes);
		} catch (Exception e) {
			throw new RuntimeException("Erreur contrôleur (Ajout) : " + e.getMessage());
		}
	}

	/**********************************************************
	 * 
	 * @param nom
	 */
	public void supprimerProjet(String nom) {
		try {
			boolean supprime = modele.supprimerProjet(nom);
			if (!supprime) {
				throw new IllegalArgumentException("Projet introuvable pour la suppression.");
			}
		} catch (Exception e) {
			throw new RuntimeException("Erreur contrôleur (Suppression) : " + e.getMessage());
		}
	}

	/**********************************************************
	 * 
	 * @param ancienNom
	 * @param nouveauNom
	 */
	public void modifierNomProjet(String ancienNom, String nouveauNom) {
		try {
			modele.modifierNomProjet(ancienNom, nouveauNom);
		} catch (Exception e) {
			throw new RuntimeException("Erreur contrôleur (Modification) : " + e.getMessage());
		}
	}

	/**********************************************************
	 * 
	 * @param nomProjet
	 * @param nouvelleDate
	 */
	public void modifierDateDebutProjet(String nomProjet, LocalDate nouvelleDate) {
		try {
			modele.modifierDateDebutProjet(nomProjet, nouvelleDate);
		} catch (Exception e) {
			throw new RuntimeException("Erreur contrôleur (Modification) : " + e.getMessage());
		}
	}

	/**********************************************************
	 * 
	 */
	public void ajouterTacheAuProjet() {

	}
	
	/**********************************************************
	 * Importer des projets depuis un fichier JSON.
	 * 
	 * @param cheminFichier
	 */
	public void importerProjetsDepuisJSONFIle(String cheminFichier, Component parent) {
		modele.importerProjetsDepuisJSONFIle(cheminFichier, parent);
	}

	/**********************************************************
	 * 
	 * @param nomProjet
	 */
	public void selectionnerProjetCourant(String nomProjet) {
		modele.selectionnerProjetCourant(nomProjet);
	}

	public Projet rechercherProjet(String prj) {
		return this.modele.rechercherProjet(prj);
	}
	
	public List<Projet> getTousLesProjets() {
		return modele.getTousLesProjets();
	}
}