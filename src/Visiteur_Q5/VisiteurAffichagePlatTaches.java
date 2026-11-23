package Visiteur_Q5;

import GestionProjet.*;

public class VisiteurAffichagePlatTaches implements Visiteur {
	private String projetCourantNom = "";

	/**********************************************************
	 * 
	 */
	@Override
	public void agitSur(Projet projet) {
		this.projetCourantNom = projet.getNom();
	}

	/**********************************************************
	 * 
	 */
	@Override
	public void agitSur(Equipe equipe) {
	}

	/**********************************************************
	 * 
	 */
	@Override
	public void agitSur(Tache tache) {
		String etat = tache.estTerminee() ? "Terminée" : "En cours";
		String equipeNom = (tache.getEquipe() != null) ? tache.getEquipe().getNom() : "Aucune";
		System.out.println("- [Tâche] " + tache.getIntitule() 
			+ " | Projet : " + projetCourantNom 
			+ " | Équipe : " + equipeNom 
			+ " | État : " + etat 
			+ " | Durée : " + tache.getDuree() + "j");
	}
}
