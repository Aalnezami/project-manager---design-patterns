package Factory_Q4;

import java.awt.Component;
import java.util.List;
import javax.swing.JOptionPane;

public class FabriqueRapportImport {

	public FabriqueRapportImport() {
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
