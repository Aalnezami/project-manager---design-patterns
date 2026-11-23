package GestionProjet;

import Visiteur_Q5.*;

public class Equipe implements Visitable {
	private String nomTeam;
	private int nombre;

	public Equipe(String nom, int nombre) {
		this.nomTeam = nom;
		this.nombre = nombre;
	}

	/**********************************************************
	 * 
	 * @return
	 */
	public String getNom() {
		return this.nomTeam;
	}

	/**********************************************************
	 * 
	 * @return
	 */
	public int getNombre() {
		return this.nombre;
	}

	/**********************************************************
	 * 
	 * @param name
	 */
	public void setNom(String name) {
		this.nomTeam = name;
	}

	/**********************************************************
	 * 
	 * @param nb
	 */
	public void setNombre(int nb) {
		this.nombre = nb;
	}

	/**********************************************************
	 * 
	 */
	@Override
	public void applique(Visiteur visiteur) {
		visiteur.agitSur(this);
	}
}
