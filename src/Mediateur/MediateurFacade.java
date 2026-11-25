package Mediateur;

import java.util.ArrayList;
import java.util.List;
import java.awt.Component;
import java.time.LocalDate;

import VerificationProjet_Q2.*;
import Factory_Q1.*;
import Factory_Q4.*;
import GestionProjet.*;
import Observateur_Q6.*;
import Visiteur_Q5.*;

public class MediateurFacade {
	private List<Projet> projets = new ArrayList<Projet>();
	private FabriqueProjet factory = new FabriqueProjet();
	private VerificationProjet verificateur = new VerificationProjet();
	private FabriqueProjetJsonReader jsonReader = new FabriqueProjetJsonReader();
	private List<Observateur> observateurs = new ArrayList<>();
	private Projet projetCourant;
	private List<String> rapportImport = new ArrayList<>();
	private FabriqueRapportImport FRI = new FabriqueRapportImport();
	private int dernierTotalImport = 0;
	private int dernierNbImportes = 0;

	public MediateurFacade(List<Projet> prjs, FabriqueProjet FP, VerificationProjet VP, FabriqueProjetJsonReader FPJR,
			List<Observateur> LO) {
		this.projets = prjs;
		this.factory = FP;
		this.verificateur = VP;
		this.jsonReader = FPJR;
		this.observateurs = LO;
	}

	/**
	 * 
	 * @return
	 */
	public int getDernierTotalImport() {
		return dernierTotalImport;
	}

	/**
	 * 
	 * @return
	 */
	public int getDernierNbImportes() {
		return dernierNbImportes;
	}

	/**********************************************************
	 * Ajouter un projet.
	 * 
	 * @param nom
	 * @param debut
	 * @param nomEquipe
	 * @param nbPersonnes
	 */
	public void ajouterProjet(String nom, LocalDate debut, String nomEquipe, int nbPersonnes) {
		if (rechercherProjet(nom) != null) {
			throw new IllegalArgumentException("Erreur : Un projet portant le nom " + nom + " existe déjà.");
		}

		Projet prj = factory.creerProjetStandard(nom, debut, nomEquipe, nbPersonnes);
		List<ErreurProjet> erreurs = verificateur.verifierProjet(prj);
		if (!erreurs.isEmpty()) {
			throw new IllegalArgumentException("Erreur de validation du projet : " + erreurs);
		}
		projets.add(prj);
		this.notifierObservateurs();
	}

	/**********************************************************
	 * Lire les informations d'un projet.
	 * 
	 * @param nom nom du projet a rechercher
	 * @return un projet
	 */
	public Projet rechercherProjet(String nom) {
		for (Projet p : projets) {
			if (p.getNom().equals(nom)) {
				return p;
			}
		}
		return null;
	}

	/**********************************************************
	 * Renvoyer la liste des projets.
	 * 
	 * @return
	 */
	public List<Projet> getTousLesProjets() {
		return projets;
	}

	/**********************************************************
	 * Supprimer un projet.
	 * 
	 * @param nom
	 * @return
	 */
	public boolean supprimerProjet(String nom) {
		Projet p = rechercherProjet(nom);
		if (p != null) {
			projets.remove(p);
			this.projetCourant = null;
			this.notifierObservateurs();
			return true;
		}
		return false;
	}

	/**********************************************************
	 * Modifier le nom d'un projet.
	 * 
	 * @param ancienNom
	 * @param nouveauNom
	 */
	public void modifierNomProjet(String ancienNom, String nouveauNom) {
		if (nouveauNom == null || nouveauNom.trim().isEmpty()) {
			throw new IllegalArgumentException("Le nom ne peut pas être vide.");
		}

		Projet prjOld = rechercherProjet(ancienNom);
		Projet prjNew = rechercherProjet(nouveauNom);

		if (prjOld == null) {
			throw new IllegalArgumentException("Projet introuvable.");
		}

		if (prjNew != null) {
			throw new IllegalArgumentException("Ce nom de projet est déjà utilisé.");
		}

		prjOld.setNom(nouveauNom);
		this.notifierObservateurs();
	}

	/**********************************************************
	 * Modifier la date de début d'un projet.
	 * 
	 * @param nom
	 * @param noouvelleDate
	 */
	public void modifierDateDebutProjet(String nom, LocalDate noouvelleDate) {
		Projet prj = rechercherProjet(nom);

		if (prj == null) {
			throw new IllegalArgumentException("Projet introuvable.");
		}

		LocalDate ancienneDate = prj.getDateDebut();
		prj.setDateDebut(noouvelleDate);

		List<ErreurProjet> erreurs = verificateur.verifierProjet(prj);
		if (!erreurs.isEmpty()) {
			prj.setDateDebut(ancienneDate);
			throw new IllegalArgumentException("Impossible d'ajouter cette tâche : " + erreurs);
		}

		this.notifierObservateurs();
	}

	/**********************************************************
	 * Modifier la date de fin d'un projet.
	 * 
	 * @param nom
	 * @param noouvelleDate
	 */
	public void modifierDateFinProjet(String nom, LocalDate noouvelleDate) {
		Projet prj = rechercherProjet(nom);

		if (prj == null) {
			throw new IllegalArgumentException("Projet introuvable.");
		}

		LocalDate ancienneDate = prj.getDateDebut();
		prj.setDateFin(noouvelleDate);

		List<ErreurProjet> erreurs = verificateur.verifierProjet(prj);
		if (!erreurs.isEmpty()) {
			prj.setDateFin(ancienneDate);
			throw new IllegalArgumentException("Impossible d'ajouter cette tâche : " + erreurs);
		}

		notifierObservateurs();
	}

	/**********************************************************
	 * Ajouter une tache au projet.
	 * 
	 * @param nom
	 * @param nouvelleTache
	 */
	public void ajouterTacheAuProjet(String nom, int duree, Equipe eq) {
		Projet prj = this.getProjetCourant();
		if (prj == null) {
			throw new IllegalArgumentException("Projet introuvable.");
		}
		
		Tache nouvelleTache = new Tache(nom, duree, eq);
		prj.ajouterTache(nouvelleTache);

		List<ErreurProjet> erreurs = verificateur.verifierProjet(prj);
		if (!erreurs.isEmpty()) {
			prj.getTaches().remove(nouvelleTache);
			throw new IllegalArgumentException("Impossible d'ajouter cette tâche : " + erreurs);
		}

		notifierObservateurs();
	}

	/**********************************************************
	 * Retirer une tache du projet.
	 * 
	 * @param nom
	 * @param tacheARetirer
	 */
	public void retirerTachesDuProjet(String nomTache) {
		Projet prj = this.getProjetCourant();
		if (prj == null) {
			throw new IllegalArgumentException("Projet introuvable.");
		}
		Tache t = prj.getTacheByName(nomTache);
		prj.getTaches().remove(t);
		notifierObservateurs();
	}

	/**********************************************************
	 * Ajouter une équipe au projet.
	 * 
	 * @param nom
	 * @param nouvelEqipe
	 */
	public void ajouterEquipeAuProjet(String nom, Equipe nouvelEqipe) {
		Projet prj = rechercherProjet(nom);
		if (prj == null) {
			throw new IllegalArgumentException("Projet introuvable.");
		}

		prj.ajouterEquipe(nouvelEqipe);
		List<ErreurProjet> erreurs = verificateur.verifierProjet(prj);
		if (!erreurs.isEmpty()) {
			prj.getTeams().remove(nouvelEqipe);
			throw new IllegalArgumentException("Impossible d'ajouter cette équipe : " + erreurs);
		}

		notifierObservateurs();
	}

	/**********************************************************
	 * Retirer une équipe du projet.
	 * 
	 * @param nom
	 * @param equipeARetirer
	 */
	public void retirerEquipeDuProjet(String nom, Equipe equipeARetirer) {
		Projet prj = rechercherProjet(nom);
		if (prj == null) {
			throw new IllegalArgumentException("Projet introuvable.");
		}

		prj.getTeams().remove(equipeARetirer);
		notifierObservateurs();
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getRapportImport() {
		return rapportImport;
	}

	/**********************************************************
	 * Importer des projets depuis un fichier JSON.
	 * 
	 * @param cheminFichier
	 */
	public void importerProjetsDepuisJSONFIle(String cheminFichier, Component parent) {
		rapportImport.clear();
		List<Projet> projetsImportes = jsonReader.creerProjetsDepuisJSONFile(cheminFichier);
		rapportImport.addAll(jsonReader.getRapportErreurs());
		int nbAjoute = 0;
		
		boolean ajoute = false;
		for (Projet p : projetsImportes) {
			if (rechercherProjet(p.getNom()) != null) {
				rapportImport.add("Projet '" + p.getNom() + "' ignoré : existe déjà.");
				continue;
			}

			List<ErreurProjet> erreurs = verificateur.verifierProjet(p);
			if (!erreurs.isEmpty()) {
				rapportImport.add("Projet '" + p.getNom() + "' rejeté : " + erreurs);
				continue;
			}

			projets.add(p);
			ajoute = true;
			nbAjoute++;
		}

		if (ajoute) {
			this.notifierObservateurs();
		}
		this.dernierTotalImport = projetsImportes.size() + jsonReader.getRapportErreurs().size();
		this.dernierNbImportes = nbAjoute;

		FRI.afficherRapport(parent, this.dernierNbImportes, this.dernierTotalImport, rapportImport);
	}

	/**********************************************************
	 * Un affichage hiérarchique des projets, équipes et tâches.
	 */
	public void afficherProjetHierarchiquement() {
		VisiteurAffichageHierarchique visiteur = new VisiteurAffichageHierarchique();
		for (Projet p : projets) {
			p.applique(visiteur);
		}
	}

	/**********************************************************
	 * Un affichage à plat des tâches avec leur projet, leur équipe et leur état.
	 */
	public void afficherLesTachesAPlat() {
		VisiteurAffichagePlatTaches visiteur = new VisiteurAffichagePlatTaches();
		for (Projet p : projets) {
			p.applique(visiteur);
		}
	}

	/**********************************************************
	 * 
	 */
	public void ajouterObservateur(Observateur o) {
		observateurs.add(o);
	}

	/**********************************************************
	 * 
	 */
	public void retirerObservateur(Observateur o) {
		observateurs.remove(o);
	}

	/**********************************************************
	 * 
	 */
	public void notifierObservateurs() {
		for (Observateur o : observateurs) {
			o.actualiser();
		}
	}

	public void selectionnerProjetCourant(String nomProjet) {
		this.projetCourant = rechercherProjet(nomProjet);
		this.notifierObservateurs();
	}

	public Projet getProjetCourant() {
		return this.projetCourant;
	}
}
