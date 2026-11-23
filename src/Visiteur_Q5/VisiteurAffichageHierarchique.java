package Visiteur_Q5;

import GestionProjet.*;

public class VisiteurAffichageHierarchique implements Visiteur {

	private StringBuilder sb = new StringBuilder();

	/**********************************************************
	 * 
	 * @return
	 */
	public String getResultat() {
		return sb.toString();
	}

	/**********************************************************
	 * 
	 */
	@Override
	public void agitSur(Projet projet) {
		sb.append("Projet : ").append(projet.getNom()).append("\nDébut : ").append(projet.getDateDebut())
				.append(" | Fin : ").append(projet.getDateFIn()).append("\n");
	}

	/**********************************************************
	 * 
	 */
	@Override
	public void agitSur(Equipe equipe) {
		sb.append("  - Équipe : ").append(equipe.getNom()).append(" (").append(equipe.getNombre()).append(" pers.)\n");
	}

	/**********************************************************
	 * 
	 */
	@Override
	public void agitSur(Tache tache) {
		String etat = tache.estTerminee() ? "Terminée" : "En cours";
		String eq = (tache.getEquipe() != null) ? tache.getEquipe().getNom() : "Aucune";
		sb.append("  - Tâche : ").append(tache.getIntitule()).append(" | ").append(tache.getDuree()).append("j")
				.append(" | ").append(etat).append(" | Équipe : ").append(eq).append("\n");
	}
}
