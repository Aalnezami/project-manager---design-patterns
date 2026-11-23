package GestionProjet;

import Visiteur_Q5.*;

public class Tache implements Visitable {
	private String intitule;
	private int duree;
	private boolean estTerminee;
	private Equipe equipeAffectee;

	
	public Tache(String nom, int duree, Equipe team) {
		this.intitule = nom;
		this.duree = duree;
		this.equipeAffectee = team;
		this.estTerminee = false;
	}
	
	/**********************************************************
	 * 
	 * @return
	 */
	public String getIntitule() {
		return this.intitule;
	}

	/**********************************************************
	 * 
	 * @return
	 */
	public int getDuree() {
		return this.duree;
	}

	/**********************************************************
	 * 
	 * @return
	 */
	public boolean estTerminee() {
		return this.estTerminee == true;
	}
	
	/**********************************************************
	 * 
	 * @return
	 */
	public Equipe getEquipe() {
		return this.equipeAffectee;
	}

	/**********************************************************
	 * 
	 * @param name
	 */
	public void setIntitule(String name) {
		this.intitule = name;
	}

	/**********************************************************
	 * 
	 * @param duree
	 */
	public void setDuree(int duree) {
		this.duree = duree;
	}

	/**********************************************************
	 * 
	 * @param etat
	 */
	public void majEtat(boolean etat) {
		this.estTerminee = etat;
	}
	
	/**********************************************************
	 * 
	 * @param team
	 */
	public void setEquipe(Equipe team) {
		this.equipeAffectee = team;
	}
	
	/**********************************************************
	 * 
	 */
	@Override
	public void applique(Visiteur visiteur) {
		visiteur.agitSur(this);
	}
}
