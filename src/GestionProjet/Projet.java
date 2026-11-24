package GestionProjet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Visiteur_Q5.*;

public class Projet implements Visitable {
	private String nom;
	private LocalDate dateDebut;
	private LocalDate dateFin;
	private List<Equipe> lesEquipes = new ArrayList<>();
	private List<Tache> lesTaches = new ArrayList<>();

	public Projet(String nom, LocalDate debut, LocalDate fin) {
		this.nom = nom;
		this.dateDebut = debut;
		this.dateFin = fin;
	}

	/**********************************************************
	 * 
	 * @return
	 */
	public String getNom() {
		return this.nom;
	}

	/**********************************************************
	 * 
	 * @return
	 */
	public LocalDate getDateDebut() {
		return this.dateDebut;
	}

	/**********************************************************
	 * 
	 * @return
	 */
	public LocalDate getDateFIn() {
		return this.dateFin;
	}

	/**********************************************************
	 * 
	 * @return
	 */
	public List<Equipe> getTeams() {
		return this.lesEquipes;
	}

	/**********************************************************
	 * 
	 * @return
	 */
	public List<Tache> getTaches() {
		return this.lesTaches;
	}

	/**********************************************************
	 * 
	 * @param name
	 */
	public void setNom(String name) {
		this.nom = name;
	}

	/**********************************************************
	 * 
	 * @param date
	 */
	public void setDateDebut(LocalDate date) {
		this.dateDebut = date;
	}

	/**********************************************************
	 * 
	 * @param date
	 */
	public void setDateFin(LocalDate date) {
		this.dateFin = date;
	}

	/**********************************************************
	 * 
	 * @param team
	 */
	public void ajouterEquipe(Equipe team) {
		this.lesEquipes.add(team);
	}

	/**********************************************************
	 * 
	 * @param tache
	 */
	public void ajouterTache(Tache tache) {
		this.lesTaches.add(tache);
	}
	
	/**********************************************************
	 * 
	 * @param nomTache
	 * @return
	 */
	public Tache getTacheByName(String nomTache) {
		for(Tache t : this.getTaches()) {
			if(t.getIntitule().equals(nomTache)) {
				return t;
			}
		}
		return null;
	}
	
	/**********************************************************
	 * 
	 */
	@Override
	public void applique(Visiteur visiteur) {
		visiteur.agitSur(this);
		// visiter les equipes du projet
		for(Equipe e : this.getTeams()) {
			e.applique(visiteur);
		}
		// visiter les taches du projet
		for(Tache t : this.getTaches()) {
			t.applique(visiteur);
		}
	}
}
