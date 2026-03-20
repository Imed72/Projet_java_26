# Système de Gestion de Maintenance (Projet Java 26)

Ce projet est une application Java permettant de gérer des interventions de maintenance sur différents bâtiments, réalisées par des techniciens qualifiés.


Il s'agit d'une application de bureau Java permettant de piloter des interventions de maintenance technique. Il utilise le Pattern DAO (Data Access Object) pour assurer une séparation nette entre la logique métier et la base de données PostgreSQL.

La gestion des Techniciens se fait à travers le : Suivi des noms, qualifications et calcul automatique de la disponibilité réelle (basé sur les interventions en cours).

La Gestion des Bâtiments se fait à travers le : Répertoire des sites d'intervention avec leur localisation.

la Planification d'Interventions se fait à travers la : Création, modification, filtrage (par technicien ou bâtiment) et suppression d'interventions.

La Persistance des données se fait grace à : l'Utilisation de JDBC (Java DataBase Connectivity) pour une communication directe, sécurisée et performante avec la base de données PostgreSQL.

La Conteneurisation est utilisé et permet : le Déploiement simplifié via Docker et Docker Compose pour orchestrer l'application et la base de données dans des environnements isolés.

La Gestion de projet se fait à travers : l'Utilisation de Maven pour l'automatisation du cycle de vie (compilation, tests) et la gestion intelligente des dépendances via le fichier pom.xml.



Architecture du Projet
Le projet est structuré selon les standards Maven :

Installation et Lancement
1. Prérequis
Java JDK 17 ou supérieur.

Maven installé.

Docker installé.

PostgreSQL avec un utilisateur nommé admin et un mot de passe password123.

2. Configuration de la Base de Données
Le projet est configuré pour s'initialiser automatiquement. Il suffit de placer le fichier init.sql dans le dossier de montage Docker (ou de vérifier qu'il est bien à la racine) et de lancer : docker-compose up -d

3. Compilation et Exécution
Clonez le dépôt et lancez l'application :



### Fichiers utilisés



Voici les fichiers utilisés dans le projet :

Le fichier docker-compose.yml : Il permet de lancer en une seule commande le conteneur PostgreSQL. Il définit le nom de la base, l'utilisateur et le mot de passe.

Le fichier init.sql : Il le script de création des tables. Il est automatiquement lu par Docker au premier démarrage pour préparer la base de données (Schéma SQL).

Le fichier pom.xml : Ce fichier est lié à la Gestion du Projet avec Maven. Il liste toutes les bibliothèques externes nécessaires, notamment le Driver JDBC PostgreSQL, et définit la version de Java utilisée.

Les fichiers java utilisés dans le dossier model sont :  Batiment.java ; Intervention.java ;  Technicien.java. Ce sont des POJO (Plain Old Java Objects). Ils représentent les données sous forme d'objets Java. C'est l'image fidèle de tes tables SQL en code.


Les fichiers DAO.java utilisé dans le dossier dao : TechnicienDAO.java, BatimentDAO.java, InterventionDAO.java.  Ces fichiers contiennent les requêtes SQL et utilisent JDBC pour lire ou modifier la base de données. Ils font le pont entre  SQL et Java.

Les fichiers Viewjava :  BatimentView.java ; InterventionView.java ; TechnicienView.java. : Gère l'affichage des menus et des tableaux dans la console ou l'interface. leurs rôle est de présenter les données à l'utilisateur de manière lisible.

Le fichier MainApp.java : il contient la méthode main. C'est lui qui démarre l'application, initialise les DAO et lance l'affichage.


### Pour Lancer le Projet :


1. Cloner le dépôt

git clone https://github.com/Imed72/projet_java_26.git
cd projet_java_26


2. Lancer Docker

docker-compose up -d

Cette commande va lire ton docker-compose.yml, télécharger l'image PostgreSQL, et exécuter ton fichier init.sql pour créer les tables automatiquement.

3. Compiler le projet Maven

mvn clean install

Maven va télécharger le driver JDBC PostgreSQL et vérifier que ton code Java est correct


4. Exécuter l'application

mvn exec:java -Dexec.mainClass="projet_java_26.demo.MainApp"

Lance le programme principal.





