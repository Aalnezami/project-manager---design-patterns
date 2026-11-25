package Factory_Q4;

import java.awt.Component;
import java.util.List;
import javax.swing.JOptionPane;
import VerificationProjet_Q2.*;

import VerificationProjet_Q2.ErreurProjet;

public class FabriqueRapportImport {

	public FabriqueRapportImport() {
	}

	public String expliquerErreur(ErreurProjet erreur) {
		if (erreur == null) {
			return "Erreur non précisée.";
		}
		return switch (erreur) {
			case NOM_VIDE -> "Le nom du projet est vide.";
			case DATES_INCOHERENTES -> "La date de fin est antérieure à la date de début.";
			case DUREE_NON_POSITIVE -> "La durée d'une tâche doit être strictement positive.";
			case NOMS_EQUIPES_NON_DISTINCTS -> "Deux équipes ou plus ont le même nom.";
			case TACHE_HORS_PROJET -> "Une tâche référence une équipe inexistante ou hors du projet.";
		};
	}
	
	public void afficherRapport(Component parent, int nbImportes, int nbTotal, List<String> erreurs) {
		if (erreurs == null || erreurs.isEmpty()) {
			JOptionPane.showMessageDialog(parent,
					"Import réussi : " + nbImportes + " projet(s) importé(s) sur " + nbTotal + ".", "Import JSON",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(nbImportes).append(" projet(s) importé(s) sur ").append(nbTotal).append(".\n\n");
		sb.append("Veuillez consulter le rapport d'erreur :\n");
		for (String err : erreurs) {
			sb.append("- ").append(err).append("\n");
		}

		JOptionPane.showMessageDialog(parent, sb.toString(), "Rapport d'erreur d'import", JOptionPane.WARNING_MESSAGE);
	}
}
