# Système de Gestion de Maintenance (Projet Java 26)

Ce projet est une application Java permettant de gérer des interventions de maintenance sur différents bâtiments, réalisées par des techniciens qualifiés.


Système de Gestion de Maintenance Industrielle
Ce projet est une application de bureau Java permettant de piloter des interventions de maintenance technique. Il utilise le Pattern DAO (Data Access Object) pour assurer une séparation nette entre la logique métier et la base de données PostgreSQL.

Fonctionnalités
Gestion des Techniciens : Suivi des noms, qualifications et calcul automatique de la disponibilité réelle (basé sur les interventions en cours).

Gestion des Bâtiments : Répertoire des sites d'intervention avec leur localisation.

Planification d'Interventions : Création, modification, filtrage (par technicien ou bâtiment) et suppression d'interventions.

Architecture Robuste : Utilisation de JDBC pour une communication directe et performante avec la base de données.

Architecture du Projet
Le projet est structuré selon les standards Maven :

Installation et Lancement
1. Prérequis
Java JDK 17 ou supérieur.

Maven installé.

PostgreSQL avec un utilisateur nommé admin et un mot de passe password123.

2. Configuration de la Base de Données
Exécutez le script suivant dans votre terminal PostgreSQL ou via un outil comme pgAdmin :

3. Compilation et Exécution
Clonez le dépôt et lancez l'application :

 Configuration Technique
Si vous souhaitez modifier les identifiants de connexion à la base de données, éditez les fichiers dans src/main/java/projet_java_26/demo/dao/ :



Prochaines étapes suggérées :
Modifier TON_PSEUDO : Remplace les liens par ton vrai nom d'utilisateur GitHub.

Ajouter des captures d'écran : Si tu as une interface graphique ou une console qui affiche des tableaux, prends une capture d'écran et ajoute-la au dossier images/ de ton repo pour l'inclure dans le README.