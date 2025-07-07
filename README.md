# Système de Gestion de Bibliothèque Universitaire

## Description

Ce projet est une application web de gestion de bibliothèque universitaire développée avec Jakarta EE et MySQL. Elle permet aux bibliothécaires et administrateurs de gérer les abonnés, les livres, les exemplaires et les emprunts.

## Fonctionnalités

### Pour l'Administrateur
- Inscription et désinscription des bibliothécaires
- Mise à jour des informations des bibliothécaires
- État de la bibliothèque (statistiques complètes)
- Gestion des utilisateurs

### Pour les Bibliothécaires
- Gestion des abonnés (inscription, modification, désinscription)
- Gestion des livres (ajout, modification, suppression)
- Gestion des exemplaires
- Gestion des emprunts et retours
- Recherche avancée de livres
- Visualisation des statistiques

## Technologies utilisées

- **Backend**: Jakarta EE 10, JPA/Hibernate 6
- **Base de données**: MySQL 8.0
- **Frontend**: JSP, Bootstrap 5, Font Awesome
- **Serveur**: Apache Tomcat 10
- **Build**: Maven

## Structure du projet

```
Projet_Bibliotheque/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/bibliotheque/
│   │   │       ├── entity/          # Entités JPA
│   │   │       ├── dao/             # Data Access Objects
│   │   │       ├── service/         # Services métier
│   │   │       ├── servlet/         # Servlets
│   │   │       └── filter/          # Filtres
│   │   ├── resources/
│   │   │   └── META-INF/
│   │   │       └── persistence.xml  # Configuration JPA
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── web.xml          # Configuration web
│   │       │   └── error/           # Pages d'erreur
│   │       ├── admin/               # Pages administrateur
│   │       ├── bibliothecaire/      # Pages bibliothécaire
│   │       ├── index.jsp            # Page d'accueil
│   │       └── login.jsp            # Page de connexion
├── database/
│   └── init.sql                     # Script d'initialisation BD
├── pom.xml                          # Configuration Maven
└── README.md                        # Ce fichier
```

## Installation et configuration

### Prérequis
- Java 17 ou supérieur
- Maven 3.6 ou supérieur
- MySQL 8.0 ou supérieur
- Apache Tomcat 10

### Étapes d'installation

1. **Cloner le projet**
   ```bash
   git clone <url-du-projet>
   cd Projet_Bibliotheque
   ```

2. **Configurer la base de données**
   - Créer une base de données MySQL nommée `bibliotheque`
   - Exécuter le script `database/init.sql`
   - Modifier les paramètres de connexion dans `src/main/resources/META-INF/persistence.xml` si nécessaire

3. **Compiler le projet**
   ```bash
   mvn clean compile
   ```

4. **Créer le fichier WAR**
   ```bash
   mvn package
   ```

5. **Déployer sur Tomcat**
   - Copier le fichier `target/gestion-bibliotheque.war` dans le dossier `webapps` de Tomcat
   - Démarrer Tomcat
   - Accéder à l'application via `http://localhost:8080/gestion-bibliotheque`

### Comptes de test

**Administrateur:**
- Nom d'utilisateur: `admin`
- Mot de passe: `admin123`

**Bibliothécaire:**
- Nom d'utilisateur: `biblio1`
- Mot de passe: `biblio123`

## Fonctionnalités détaillées

### Gestion des Abonnés
- Inscription avec attribution automatique d'un numéro d'abonnement
- Modification des informations personnelles
- Désactivation (suppression logique)
- Recherche par nom, numéro d'abonnement ou institution
- Gestion des statuts (Étudiant, Enseignant, Autre)

### Gestion des Livres
- Ajout de livres avec toutes les informations requises
- Gestion des exemplaires multiples
- Recherche avancée par titre, auteur, domaine, niveau
- Catégorisation par domaine et niveau requis
- Gestion des ISBN et éditeurs

### Gestion des Emprunts
- Enregistrement des emprunts avec dates
- Gestion des retours avec date effective
- Prolongation des emprunts (limite de 2 prolongations)
- Détection automatique des retards
- Limitation à 5 emprunts simultanés par abonné

### Sécurité
- Authentification obligatoire
- Gestion des rôles (Admin/Bibliothécaire)
- Filtres de sécurité sur les URLs
- Protection contre les accès non autorisés

### Interface utilisateur
- Design moderne avec Bootstrap 5
- Interface responsive
- Navigation intuitive
- Messages d'erreur et de succès
- Pages d'erreur personnalisées (404, 500, 403)

## Base de données

### Tables principales
- `utilisateurs` : Gestion des comptes utilisateurs
- `bibliothecaires` : Informations spécifiques aux bibliothécaires
- `abonnes` : Gestion des abonnés
- `livres` : Catalogue des livres
- `exemplaires` : Exemplaires physiques des livres
- `emprunts` : Historique des emprunts

### Relations
- Un bibliothécaire hérite d'un utilisateur
- Un livre peut avoir plusieurs exemplaires
- Un abonné peut avoir plusieurs emprunts
- Un exemplaire peut être emprunté plusieurs fois

## Fonctionnalités bonus (à implémenter)

- Import/Export CSV des abonnés et livres
- Export PDF des listes d'abonnés
- Notifications automatiques par email
- Statistiques avancées avec graphiques
- API REST pour intégration externe

## Développement

### Ajout de nouvelles fonctionnalités
1. Créer les entités JPA si nécessaire
2. Implémenter les DAOs correspondants
3. Créer les services métier
4. Développer les servlets pour les actions
5. Créer les pages JSP pour l'interface

### Tests
- Tests unitaires des services
- Tests d'intégration des DAOs
- Tests fonctionnels de l'interface

## Support

Pour toute question ou problème :
- Créer une issue sur le repository
- Contacter l'équipe de développement

## Licence

Ce projet est développé dans le cadre d'un projet académique.

## Auteurs

- [Votre nom]
- [Nom de votre binôme]

## Version

Version 1.0.0 - Juin 2024 