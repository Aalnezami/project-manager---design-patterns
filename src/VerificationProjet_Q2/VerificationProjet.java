package VerificationProjet_Q2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import GestionProjet.*;

public class VerificationProjet {

	public VerificationProjet() {
	}

	/**********************************************************
	 * 
	 * @param prj
	 * @return
	 */
	public List<ErreurProjet> verifierProjet(Projet prj) {
		List<ErreurProjet> erreurs = new ArrayList<ErreurProjet>();

		verifierNom(prj, erreurs);
		verifierDates(prj, erreurs);
		verifierDureesTaches(prj, erreurs);
		verifierEquipesDistinctes(prj, erreurs);
		verifierAffectationTaches(prj, erreurs);

		return erreurs;
	}

	/**********************************************************
	 * 
	 * @param prj
	 * @return
	 */
	public boolean estValide(Projet prj) {
		return verifierProjet(prj).isEmpty();
	}

	/**********************************************************
	 * 
	 * @param prj
	 * @param erreurs
	 */
	public void verifierNom(Projet prj, List<ErreurProjet> erreurs) {
		if (prj.getNom() == null || prj.getNom().trim().isEmpty()) {
			erreurs.add(ErreurProjet.NOM_VIDE);
		}
	}

	/**********************************************************
	 * 
	 * @param prj
	 * @param erreurs
	 */
	public void verifierDates(Projet prj, List<ErreurProjet> erreurs) {
		if (prj.getDateDebut() != null && prj.getDateFIn() != null && prj.getDateFIn().isBefore(prj.getDateDebut())) {
			erreurs.add(ErreurProjet.DATES_INCOHERENTES);
		}
	}

	/**********************************************************
	 * 
	 * @param prj
	 * @param erreurs
	 */
	public void verifierDureesTaches(Projet prj, List<ErreurProjet> erreurs) {
		for (Tache t : prj.getTaches()) {
			if (t.getDuree() <= 0) {
				erreurs.add(ErreurProjet.DUREE_NON_POSITIVE);
				break;
			}
		}
	}

	/**********************************************************
	 * 
	 * @param prj
	 * @param erreurs
	 */
	public void verifierEquipesDistinctes(Projet prj, List<ErreurProjet> erreurs) {
		Set<String> nomsEquipes = new HashSet<>();
		for (Equipe e : prj.getTeams()) {
			if (!nomsEquipes.add(e.getNom())) {
				erreurs.add(ErreurProjet.NOMS_EQUIPES_NON_DISTINCTS);
				break;
			}
		}
	}

	/**********************************************************
	 * 
	 * @param prj
	 * @param erreurs
	 */
	private void verifierAffectationTaches(Projet prj, List<ErreurProjet> erreurs) {
		for (Tache t : prj.getTaches()) {
			if (!prj.getTeams().contains(t.getEquipe())) {
				erreurs.add(ErreurProjet.TACHE_HORS_PROJET);
				break;
			}
		}
	}
}
