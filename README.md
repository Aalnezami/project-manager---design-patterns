# project-manager---design-patterns
## Gestion de Projets — Architecture Logicielle & Design Patterns

Application desktop en **Java (Swing)** conçue pour mettre en pratique les principes d'architecture logicielle et les patrons de conception classiques dans un cadre strict de séparation des responsabilités.

## Architecture & Design Patterns

* **Façade & Médiateur (`GestionProjets` / `MediateurFacade`)** : centralisation de la coordination métier, gestion du cycle de vie des projets, sélection du projet courant et notification des vues sans exposer la complexité interne aux appelants.
* **Observateur (`Observable` / `Observateur`, `VueListeProjets`, `VueProjet`)** : synchronisation automatique des vues via `notifierObservateurs()` et `actualiser()`, sans couplage direct entre le contrôleur et les vues.
* **Visiteur (`Element` / `VisiteurElement`, `Projet`, `VisiteurAffichageHierarchique`, `VisiteurAffichagePlatTaches`)** : parcours et représentation des structures hiérarchiques (projets, équipes, tâches) découplés des classes métier.
* **Factory & Import JSON (`FabriqueProjet`, `FabriqueProjetJsonReader`)** : instanciation contrôlée des projets et lecture tolérante aux erreurs d'un fichier JSON avec génération d'un rapport d'erreurs non bloquant.
* **Validation sans exceptions bloquantes (`VerificationProjet`, `ErreurProjet`)** : contrôle des règles métier et des cohérences de dates via des codes d'erreur explicites et restauration d'état en cas d'échec.
* **MVC (`ControleurProjets`)** : le contrôleur agit uniquement sur le modèle et délègue la mise à jour graphique au mécanisme d'observation.

## Fonctionnalités

* Création, modification (nom, dates), suppression et sélection de projets.
* Gestion des équipes et des tâches associées.
* Importation de projets depuis un fichier JSON avec rapport détaillé (projets importés vs. anomalies détectées).
* Affichage hiérarchique et vue détaillée synchronisée du projet courant.

## Stack technique

* **Langage** : Java
* **Interface graphique** : Swing (`JFrame`, `JList`, `JToolBar`, `JOptionPane`, `JFileChooser`)
* **Données** : JSON (`org.json`)
