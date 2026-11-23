package Factory_Q4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import GestionProjet.*;

public class FabriqueProjetJsonReader {
	private List<String> rapportErreurs = new ArrayList<>();

	public FabriqueProjetJsonReader() {
	}

	public List<String> getRapportErreurs() {
		return rapportErreurs;
	}

	public List<Projet> creerProjetsDepuisJSONFile(String cheminFichier) {
		rapportErreurs.clear();
		List<Projet> projetsImportes = new ArrayList<>();

		String contenuJson;
		try {
			contenuJson = new String(Files.readAllBytes(Paths.get(cheminFichier)));
		} catch (IOException e) {
			rapportErreurs.add("Lecture impossible (" + cheminFichier + ") : " + e.getMessage());
			return projetsImportes;
		}

		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(contenuJson);
		} catch (Exception e) {
			rapportErreurs.add("JSON racine invalide : " + e.getMessage());
			return projetsImportes;
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				JSONObject jsonProjet = jsonArray.getJSONObject(i);

				String nom = jsonProjet.getString("nom");
				LocalDate dateDebut = LocalDate.parse(jsonProjet.getString("dateDebut"));
				LocalDate dateFin = LocalDate.parse(jsonProjet.getString("dateFin"));

				Projet projet = new Projet(nom, dateDebut, dateFin);

				JSONArray jsonEquipes = jsonProjet.optJSONArray("equipes");
				List<Equipe> equipesProjet = new ArrayList<>();
				if (jsonEquipes != null) {
					for (int j = 0; j < jsonEquipes.length(); j++) {
						JSONObject jsonEquipe = jsonEquipes.getJSONObject(j);
						Equipe equipe = new Equipe(jsonEquipe.getString("nom"), jsonEquipe.getInt("nombre"));
						equipesProjet.add(equipe);
						projet.ajouterEquipe(equipe);
					}
				}

				JSONArray jsonTaches = jsonProjet.optJSONArray("taches");
				boolean projetInvalide = false;
				if (jsonTaches != null) {
					for (int k = 0; k < jsonTaches.length(); k++) {
						JSONObject jsonTache = jsonTaches.getJSONObject(k);
						String intitule = jsonTache.getString("intitule");
						int duree = jsonTache.getInt("duree");
						String equipeNom = jsonTache.getString("equipeNom");

						Equipe equipeTrouvee = null;
						for (Equipe e : equipesProjet) {
							if (e.getNom().equals(equipeNom)) {
								equipeTrouvee = e;
								break;
							}
						}

						if (equipeTrouvee == null) {
							rapportErreurs.add("Projet '" + nom + "' ignoré : équipe '" + equipeNom
									+ "' introuvable pour la tâche '" + intitule + "'.");
							projetInvalide = true;
							break;
						}

						Tache tache = new Tache(intitule, duree, equipeTrouvee);
						if (jsonTache.has("estTerminee")) {
							tache.majEtat(jsonTache.getBoolean("estTerminee"));
						}
						projet.ajouterTache(tache);
					}
				}

				if (!projetInvalide) {
					projetsImportes.add(projet);
				}

			} catch (Exception e) {
				rapportErreurs.add("Élément JSON #" + i + " ignoré : " + e.getMessage());
			}
		}

		return projetsImportes;
	}
}