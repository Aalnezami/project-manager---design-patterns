package Facade_Q3;

import java.util.ArrayList;
import java.util.List;
import java.awt.Component;
import java.time.LocalDate;

import VerificationProjet_Q2.*;
import Factory_Q1.*;
import Factory_Q4.FabriqueProjetJsonReader;
import GestionProjet.*;
import Observateur_Q6.*;
import Mediateur.*;

public class GestionProjets implements Observable {

	private List<Projet> projets = new ArrayList<Projet>();
	private FabriqueProjet factory = new FabriqueProjet();
	private VerificationProjet verificateur = new VerificationProjet();
	private FabriqueProjetJsonReader jsonReader = new FabriqueProjetJsonReader();
	private List<Observateur> observateurs = new ArrayList<>();
	private MediateurFacade MF = new MediateurFacade(this.projets, this.factory, this.verificateur, this.jsonReader,
			this.observateurs);

	/**********************************************************
	 * Ajouter un projet.
	 * 
	 * @param nom
	 * @param debut
	 * @param nomEquipe
	 * @param nbPersonnes
	 * @return
	 */
	public void ajouterProjet(String nom, LocalDate debut, String nomEquipe, int nbPersonnes) {
		MF.ajouterProjet(nom, debut, nomEquipe, nbPersonnes);
	}

	/**********************************************************
	 * Lire les informations d'un projet.
	 * 
	 * @param nom nom du projet a rechercher
	 * @return un projet
	 */
	public Projet rechercherProjet(String nom) {
		return MF.rechercherProjet(nom);
	}

	/**********************************************************
	 * Supprimer un projet.
	 * 
	 * @param nom
	 * @return
	 */
	public boolean supprimerProjet(String nom) {
		return MF.supprimerProjet(nom);
	}

	/**********************************************************
	 * Modifier le nom d'un projet.
	 * 
	 * @param ancienNom
	 * @param nouveauNom
	 */
	public void modifierNomProjet(String ancienNom, String nouveauNom) {
		MF.modifierNomProjet(ancienNom, nouveauNom);
	}

	/**********************************************************
	 * Modifier la date de début d'un projet.
	 * 
	 * @param nom
	 * @param noouvelleDate
	 */
	public void modifierDateDebutProjet(String nom, LocalDate nouvelleDate) {
		MF.modifierDateDebutProjet(nom, nouvelleDate);
	}

	/**********************************************************
	 * Modifier la date de fin d'un projet.
	 * 
	 * @param nom
	 * @param noouvelleDate
	 */
	public void modifierDateFinProjet(String nom, LocalDate nouvelleDate) {
		MF.modifierDateFinProjet(nom, nouvelleDate);
	}

	/**********************************************************
	 * Ajouter une tache au projet.
	 * 
	 * @param nom
	 * @param nouvelleTache
	 */
	public void ajouterTacheAuProjet(String nom, Tache nouvelleTache) {
		MF.ajouterTacheAuProjet(nom, nouvelleTache);
	}

	/**********************************************************
	 * Retirer une tache du projet.
	 * 
	 * @param nom
	 * @param tacheARetirer
	 */
	public void retirerTachesDuProjet(String nom, Tache tacheARetirer) {
		MF.retirerTachesDuProjet(nom, tacheARetirer);
	}

	/**********************************************************
	 * Ajouter une équipe au projet.
	 * 
	 * @param nom
	 * @param nouvelEqipe
	 */
	public void ajouterEquipeAuProjet(String nom, Equipe nouvelEquipe) {
		MF.ajouterEquipeAuProjet(nom, nouvelEquipe);
	}

	/**********************************************************
	 * Retirer une équipe du projet.
	 * 
	 * @param nom
	 * @param equipeARetirer
	 */
	public void retirerEquipeDuProjet(String nom, Equipe equipeARetirer) {
		MF.retirerEquipeDuProjet(nom, equipeARetirer);
	}

	/**********************************************************
	 * Importer des projets depuis un fichier JSON.
	 * 
	 * @param cheminFichier
	 */
	public void importerProjetsDepuisJSONFIle(String cheminFichier, Component parent) {
		MF.importerProjetsDepuisJSONFIle(cheminFichier, parent);
	}

	/**********************************************************
	 * Un affichage hiérarchique des projets, équipes et tâches.
	 */
	public void afficherProjetHierarchiquement() {
		MF.afficherProjetHierarchiquement();
	}

	/**********************************************************
	 * Un affichage à plat des tâches avec leur projet, leur équipe et leur état.
	 */
	public void afficherLesTachesAPlat() {
		MF.afficherLesTachesAPlat();
	}

	/**********************************************************
	 * 
	 */
	@Override
	public void attacher(Observateur o) {
		MF.ajouterObservateur(o);
	}

	/**********************************************************
	 * 
	 */
	@Override
	public void detacher(Observateur o) {
		MF.retirerObservateur(o);
	}

	/**********************************************************
	 * 
	 */
	@Override
	public void notifierObservateurs() {
		MF.notifierObservateurs();
	}

	/**********************************************************
	 * Renvoyer la liste des projets.
	 * 
	 * @return
	 */
	public List<Projet> getTousLesProjets() {
		return MF.getTousLesProjets();
	}
	
	public void selectionnerProjetCourant(String nomProjet) {
		MF.selectionnerProjetCourant(nomProjet);
	}
	
	public Projet getProjetCourant() {
		return MF.getProjetCourant();
	}
}
