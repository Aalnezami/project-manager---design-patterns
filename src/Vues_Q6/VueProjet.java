package Vues_Q6;

import javax.swing.*;
import java.awt.*;
import Facade_Q3.GestionProjets;
import Observateur_Q6.Observateur;
import GestionProjet.Projet;
import Visiteur_Q5.*;

public class VueProjet extends JFrame implements Observateur {
	private GestionProjets modele;
	private JTextArea zoneDetails;
	private Projet projetActuel;

	public VueProjet(GestionProjets model) {
		this.modele = model;
		this.modele.attacher(this);

		setTitle("Details du projet sélectionné");
		setSize(350, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		zoneDetails = new JTextArea();
        zoneDetails.setEditable(false);
        zoneDetails.setFont(new Font("SansSerif", Font.PLAIN, 13));
        zoneDetails.setMargin(new Insets(10, 10, 10, 10));
        add(new JScrollPane(zoneDetails), BorderLayout.CENTER);

		actualiser();
		setVisible(true);
	}

	/**********************************************************
	 * 
	 * @param prj
	 */
	public void setProjetActuel(Projet prj) {
		this.projetActuel = prj;
	}
	
	/**********************************************************
	 * 
	 */
	@Override
	public void actualiser() {
		Projet prj = modele.getProjetCourant();
		if (prj == null) {
            projetActuel = null;
            zoneDetails.setText("Aucun projet sélectionné!");
            return;
        }
		
		projetActuel = prj;
		VisiteurAffichageHierarchique visiteur = new VisiteurAffichageHierarchique();
        prj.applique(visiteur);
        zoneDetails.setText(visiteur.getResultat());
	}
}
