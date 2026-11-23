package Vues_Q6;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import Facade_Q3.GestionProjets;
import Observateur_Q6.Observateur;
import GestionProjet.*;
import Controleur_Q7.*;

public class VueListeProjets extends JFrame implements Observateur {
	private GestionProjets modele;
	private DefaultListModel<String> listModel;
	private JList<String> listProjets;
	private ControleurProjets controleur;
	private boolean actualisationEnCours = false;

	public VueListeProjets(GestionProjets modele, ControleurProjets controleur) {
		this.modele = modele;
		this.controleur = controleur;
		this.modele.attacher(this); // s'abonne au modèle

		setTitle("Gestion des Projets - Architecture Logicielle");
		setSize(750, 450);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		// barre de menu en haut
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFichier = new JMenu("Fichier");
		JMenu menuProjet = new JMenu("Gestion Projet");
		JMenu menuTaches = new JMenu("Taches");
		JMenu menuEquipes = new JMenu("Equipes");

		JMenuItem itemImporter = new JMenuItem("Importer JSON...");
		JMenuItem itemSauvegarder = new JMenuItem("Sauvegarder");
		JMenuItem itemQuitter = new JMenuItem("Quitter");

		JMenuItem itemAjouterPrj = new JMenuItem("+ Ajouter");
		JMenuItem itemSupprimerPrj = new JMenuItem("- Supprimer");
		JMenuItem itemModifNom = new JMenuItem("Modifier le nom");
		JMenuItem itemModifDateDebut = new JMenuItem("Modifier la date de début");
		JMenuItem itemModifDateFin = new JMenuItem("Modifier la date de fin");

		JMenuItem itemAjouterTache = new JMenuItem("+ Ajouter une tache");
		JMenuItem itemSupprimerTache = new JMenuItem("- Supprimer une tache");
		JMenuItem itemModifierTache = new JMenuItem(" Modifier une tache");

		menuFichier.add(itemImporter);
		menuFichier.add(itemSauvegarder);
		menuFichier.addSeparator();
		menuFichier.add(itemQuitter);

		menuProjet.add(itemAjouterPrj);
		menuProjet.add(itemSupprimerPrj);
		menuProjet.add(itemModifNom);
		menuProjet.add(itemModifDateDebut);
		menuProjet.add(itemModifDateFin);

		menuTaches.add(itemAjouterTache);
		menuTaches.add(itemSupprimerTache);
		menuTaches.add(itemModifierTache);

		// barre des boutons
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);

		JButton btnAjouter = new JButton("Ajouter un projet");
		JButton btnSupprimer = new JButton("Supprimer un projet");

		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(btnAjouter);
		toolBar.addSeparator();
		toolBar.add(btnSupprimer);
		toolBar.add(Box.createHorizontalGlue());

		add(toolBar, BorderLayout.NORTH);

		// liste des projets
		listModel = new DefaultListModel<>();
		listProjets = new JList<>(listModel);
		listProjets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(new JScrollPane(listProjets), BorderLayout.CENTER);

		// gestion des évenements
		// Ajouter un projet
		btnAjouter.addActionListener(e -> {
			String nom = JOptionPane.showInputDialog(this, "Nom du projet :");
			if (nom != null && !nom.trim().isEmpty()) {
				try {
					controleur.ajouterProjet(nom, LocalDate.now(), "Équipe par défaut", 3);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		itemAjouterPrj.addActionListener(e -> {
			String nom = JOptionPane.showInputDialog(this, "Nom du projet :");
			if (nom != null && !nom.trim().isEmpty()) {
				try {
					controleur.ajouterProjet(nom, LocalDate.now(), "Équipe par défaut", 3);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// Supprimer un projet
		btnSupprimer.addActionListener(e -> {
			String selected = listProjets.getSelectedValue();
			if (selected != null) {
				int confirmation = JOptionPane.showConfirmDialog(this,
						"Voulez-vous supprimer le projet : " + selected + " ?", "Confirmation",
						JOptionPane.YES_NO_OPTION);
				if (confirmation == JOptionPane.YES_OPTION) {
					try {
						controleur.supprimerProjet(selected);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Veuillez sélectionner un projet dans la liste.");
			}
		});

		itemSupprimerPrj.addActionListener(e -> {
			String selected = listProjets.getSelectedValue();
			if (selected != null) {
				int confirmation = JOptionPane.showConfirmDialog(this,
						"Voulez-vous supprimer le projet : " + selected + " ?", "Confirmation",
						JOptionPane.YES_NO_OPTION);
				if (confirmation == JOptionPane.YES_OPTION) {
					try {
						controleur.supprimerProjet(selected);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Veuillez sélectionner un projet dans la liste.");
			}
		});

		// modifier le nom
		itemModifNom.addActionListener(e -> {
			String selected = listProjets.getSelectedValue();
			if (selected != null) {
				String nouveauNom = JOptionPane.showInputDialog(this, "Nouveau nom pour '" + selected + "' :");
				if (nouveauNom != null && !nouveauNom.trim().isEmpty()) {
					try {
						controleur.modifierNomProjet(selected, nouveauNom);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Veuillez sélectionner un projet.");
			}
		});

		// modifier la date de début
        itemModifDateDebut.addActionListener(e -> {
            String selected = listProjets.getSelectedValue();
            if (selected != null) {
                String dateStr = JOptionPane.showInputDialog(this, "Nouvelle date de début (YYYY-MM-DD) :");
                if (dateStr != null && !dateStr.trim().isEmpty()) {
                    try {
                        LocalDate nouvelleDate = LocalDate.parse(dateStr);
                        controleur.modifierDateDebutProjet(selected, nouvelleDate);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un projet.");
            }
        });

		/**********************************************************
		 * gestion des taches, afficher la liste des taches d'un prj pour pouvoir en
		 * supprimer ou ajouter ou modifier
		 */
		itemAjouterTache.addActionListener(e -> {
			String selected = listProjets.getSelectedValue();
			if (selected == null) {
				JOptionPane.showMessageDialog(this, "Veuillez sélectionner un projet dans la liste.");
				return;
			}
			Projet prj = controleur.rechercherProjet(selected);
			if (prj == null) {
				JOptionPane.showMessageDialog(this, "Le projet sélectionné est introuvable.");
				return;
			}

			JTextField champTitre = new JTextField();
			JTextField champDur = new JTextField();
			Object[] champs = { "Intitulé :", champTitre, "Durée (jours) :", champDur };

			int res = JOptionPane.showConfirmDialog(this, champs, "Nouvelle tâche", JOptionPane.OK_CANCEL_OPTION);
			String titre = champTitre.getText().trim();
			String dur = champDur.getText().trim();

			if (res == JOptionPane.OK_OPTION && !titre.isEmpty() && !dur.isEmpty()) {
				try {
					Equipe eq = prj.getTeams().isEmpty() ? null : prj.getTeams().get(0);
					//controleur.ajouterTacheAuProjet(prj.getNom(), new Tache(titre, Integer.parseInt(dur), eq));
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
//        btnGererTaches.addActionListener(e -> {
//            String selected = listProjets.getSelectedValue();
//            if (selected == null) {
//                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un projet dans la liste.");
//                return;
//            }
//            Projet prj = modele.rechercherProjet(selected);
//            if (prj == null) {
//                JOptionPane.showMessageDialog(this, "Le projet sélectionné est introuvable.");
//                return;
//            }
//
//            JDialog d = new JDialog(this, "Tâches - " + prj.getNom(), true);
//            d.setSize(450, 280);
//            d.setLocationRelativeTo(this);
//            d.setLayout(new BorderLayout());
//
//            DefaultListModel<String> tm = new DefaultListModel<>();
//            JList<String> liste = new JList<>(tm);
//            Runnable refresh = () -> {
//                tm.clear();
//                for (Tache t : prj.getTaches()) {
//                    tm.addElement(t.getIntitule() + " (" + t.getDuree() + "j)");
//                }
//            };
//            refresh.run();
//            d.add(new JScrollPane(liste), BorderLayout.CENTER);
//
//            JButton btnAdd = new JButton("Ajouter");
//            JButton btnEdit = new JButton("Modifier");
//            JButton btnDel = new JButton("Supprimer");
//
//            btnAdd.addActionListener(ev -> {
//                String titre = JOptionPane.showInputDialog(d, "Intitulé :");
//                String dur = JOptionPane.showInputDialog(d, "Durée (jours) :");
//                if (titre != null && !titre.trim().isEmpty() && dur != null) {
//                    try {
//                        GestionProjet.Equipe eq = prj.getTeams().isEmpty() ? null : prj.getTeams().get(0);
//                        modele.ajouterTacheAuProjet(prj.getNom(), new GestionProjet.Tache(titre.trim(), Integer.parseInt(dur.trim()), eq));
//                        refresh.run();
//                    } catch (Exception ex) {
//                        JOptionPane.showMessageDialog(d, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//            });
//
//            btnEdit.addActionListener(ev -> {
//                int idx = liste.getSelectedIndex();
//                if (idx < 0) return;
//                GestionProjet.Tache t = prj.getTaches().get(idx);
//                String titre = JOptionPane.showInputDialog(d, "Nouvel intitulé :", t.getIntitule());
//                String dur = JOptionPane.showInputDialog(d, "Nouvelle durée :", t.getDuree());
//                if (titre != null && !titre.trim().isEmpty() && dur != null) {
//                    try {
//                        t.setIntitule(titre.trim());
//                        t.setDuree(Integer.parseInt(dur.trim()));
//                        modele.notifierObservateurs();
//                        refresh.run();
//                    } catch (Exception ex) {
//                        JOptionPane.showMessageDialog(d, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//            });
//
//            btnDel.addActionListener(ev -> {
//                int idx = liste.getSelectedIndex();
//                if (idx < 0) return;
//                modele.retirerTachesDuProjet(prj.getNom(), prj.getTaches().get(idx));
//                refresh.run();
//            });
//
//            JPanel bas = new JPanel();
//            bas.add(btnAdd);
//            bas.add(btnEdit);
//            bas.add(btnDel);
//            d.add(bas, BorderLayout.SOUTH);
//            d.setVisible(true);
//        });

//        // Ajouter une tâche
//        btnGererTaches.addActionListener(e -> {
//            String selected = listProjets.getSelectedValue();
//            if (selected != null) {
//                String intituleTache = JOptionPane.showInputDialog(this, "Intitulé de la nouvelle tâche :");
//                if (intituleTache != null && !intituleTache.trim().isEmpty()) {
//                    String dureeStr = JOptionPane.showInputDialog(this, "Durée de la tâche (en jours) :");
//                    try {
//                        int duree = Integer.parseInt(dureeStr);
//                        controleur.ajouterTacheAuProjet(selected, intituleTache, duree);
//                    } catch (Exception ex) {
//                        JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la tâche : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//            } else {
//                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un projet.");
//            }
//        });

		// Ajouter une équipe
//        btnGererEquipes.addActionListener(e -> {
//            String selected = listProjets.getSelectedValue();
//            if (selected != null) {
//                String nomEquipe = JOptionPane.showInputDialog(this, "Nom de l'équipe à ajouter :");
//                if (nomEquipe != null && !nomEquipe.trim().isEmpty()) {
//                    String nbStr = JOptionPane.showInputDialog(this, "Nombre de personnes dans l'équipe :");
//                    try {
//                        int nb = Integer.parseInt(nbStr);
//                        controleur.demanderAjoutEquipe(selected, nomEquipe, nb);
//                    } catch (Exception ex) {
//                        JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'équipe : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//            } else {
//                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un projet.");
//            }
//        });
		
		itemImporter.addActionListener(e -> {
		    JFileChooser chooser = new JFileChooser();
		    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
		        String chemin = chooser.getSelectedFile().getAbsolutePath();
		        try {
		            controleur.importerProjetsDepuisJSONFIle(chemin, this);
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur d'import", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});

		listProjets.addListSelectionListener(e -> {
			if (actualisationEnCours || e.getValueIsAdjusting()) {
		        return;
		    }
		    String selected = listProjets.getSelectedValue();
		    if (selected != null) {
		        controleur.selectionnerProjetCourant(selected);
		    }
		});

		// chargement initial
		actualiser();
		setVisible(true);
		menuBar.add(menuFichier);
		menuBar.add(menuProjet);
		menuBar.add(menuEquipes);
		menuBar.add(menuTaches);
		setJMenuBar(menuBar);
	}

	@Override
	public void actualiser() {
	    actualisationEnCours = true;
	    try {
	        String selected = listProjets.getSelectedValue();
	        listModel.clear();
	        for (Projet p : controleur.getTousLesProjets()) {
	            listModel.addElement(p.getNom());
	        }
	        if (selected != null && listModel.contains(selected)) {
	            listProjets.setSelectedValue(selected, false);
	        }
	    } finally {
	        actualisationEnCours = false;
	    }
	}
}