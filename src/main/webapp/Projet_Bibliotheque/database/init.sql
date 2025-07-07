-- Script d'initialisation de la base de données pour la gestion de bibliothèque (CORRIGÉ)
-- Création de la base de données
CREATE DATABASE IF NOT EXISTS bibliotheque CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE bibliotheque;

-- Suppression des tables existantes (si elles existent)
DROP TABLE IF EXISTS emprunts;
DROP TABLE IF EXISTS exemplaires;
DROP TABLE IF EXISTS livres;
DROP TABLE IF EXISTS abonnes;
DROP TABLE IF EXISTS bibliothecaires;
DROP TABLE IF EXISTS utilisateurs;

-- Table des utilisateurs (classe parent)
CREATE TABLE utilisateurs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom_utilisateur VARCHAR(50) UNIQUE NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role ENUM('ADMIN', 'BIBLIOTHECAIRE') NOT NULL,
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP,
    actif BOOLEAN DEFAULT TRUE,
    photo VARCHAR(255)
);

-- Table des bibliothécaires (hérite de utilisateurs)
CREATE TABLE bibliothecaires (
    utilisateur_id BIGINT PRIMARY KEY,
    adresse TEXT NOT NULL,
    date_recrutement DATE NOT NULL,
    specialite VARCHAR(100),
    telephone VARCHAR(20),
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id) ON DELETE CASCADE
);

-- Table des abonnés
CREATE TABLE abonnes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_abonnement VARCHAR(20) UNIQUE NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    nom VARCHAR(100) NOT NULL,
    statut ENUM('ETUDIANT', 'ENSEIGNANT', 'PERSONNEL') NOT NULL,
    institution VARCHAR(200) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telephone VARCHAR(20),
    adresse TEXT,
    date_inscription DATETIME DEFAULT CURRENT_TIMESTAMP,
    actif BOOLEAN DEFAULT TRUE
);

-- Table des livres
CREATE TABLE livres (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    identifiant VARCHAR(50) UNIQUE NOT NULL,
    titre VARCHAR(500) NOT NULL,
    auteurs VARCHAR(300) NOT NULL,
    annee_publication INT NOT NULL,
    domaine ENUM('INFORMATIQUE', 'MATHEMATIQUES', 'PHYSIQUE', 'CHIMIE', 'BIOLOGIE', 
                 'MEDECINE', 'DROIT', 'ECONOMIE', 'GESTION', 'LITTERATURE', 'HISTOIRE', 
                 'PHILOSOPHIE', 'LANGUES', 'ARTS', 'SPORTS', 'AUTRE') NOT NULL,
    niveau_requis ENUM('DEBUTANT', 'INTERMEDIAIRE', 'AVANCE', 'EXPERT', 'TOUS_NIVEAUX') NOT NULL,
    isbn VARCHAR(20),
    editeur VARCHAR(200),
    description TEXT,
    nombre_pages INT,
    date_ajout DATETIME DEFAULT CURRENT_TIMESTAMP,
    actif BOOLEAN DEFAULT TRUE
);

-- Table des exemplaires
CREATE TABLE exemplaires (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code_exemplaire VARCHAR(50) UNIQUE NOT NULL,
    livre_id BIGINT NOT NULL,
    etat VARCHAR(50) DEFAULT 'Bon',
    localisation VARCHAR(100),
    date_acquisition DATETIME DEFAULT CURRENT_TIMESTAMP,
    disponible BOOLEAN DEFAULT TRUE,
    actif BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (livre_id) REFERENCES livres(id) ON DELETE CASCADE
);

-- Table des emprunts
CREATE TABLE emprunts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    abonne_id BIGINT NOT NULL,
    exemplaire_id BIGINT NOT NULL,
    bibliothecaire_id BIGINT NOT NULL,
    date_emprunt DATETIME DEFAULT CURRENT_TIMESTAMP,
    date_retour_prevue DATE NOT NULL,
    date_retour_effective DATETIME NULL,
    prolongations INT DEFAULT 0,
    commentaires TEXT,
    statut ENUM('EN_COURS', 'RETOURNE', 'EN_RETARD', 'PERDU') DEFAULT 'EN_COURS',
    FOREIGN KEY (abonne_id) REFERENCES abonnes(id) ON DELETE CASCADE,
    FOREIGN KEY (exemplaire_id) REFERENCES exemplaires(id) ON DELETE CASCADE,
    FOREIGN KEY (bibliothecaire_id) REFERENCES bibliothecaires(utilisateur_id) ON DELETE CASCADE
);

-- Insertion des données de test

-- Utilisateurs de test
INSERT INTO utilisateurs (nom_utilisateur, mot_de_passe, prenom, nom, email, role) VALUES
('admin', 'admin123', 'Administrateur', 'Système', 'admin@bibliotheque.edu', 'ADMIN'),
('biblio1', 'biblio123', 'Marie', 'Dupont', 'marie.dupont@bibliotheque.edu', 'BIBLIOTHECAIRE'),
('biblio2', 'biblio123', 'Jean', 'Martin', 'jean.martin@bibliotheque.edu', 'BIBLIOTHECAIRE');

-- Bibliothécaires de test
INSERT INTO bibliothecaires (utilisateur_id, adresse, date_recrutement, specialite, telephone) VALUES
(2, '123 Rue de la Bibliothèque, 75001 Paris', '2020-01-15', 'Informatique', '01 23 45 67 89'),
(3, '456 Avenue des Livres, 75002 Paris', '2021-03-20', 'Littérature', '01 98 76 54 32');

-- Abonnés de test
INSERT INTO abonnes (numero_abonnement, prenom, nom, statut, institution, email, telephone) VALUES
('AB001', 'Alice', 'Durand', 'ETUDIANT', 'Université Paris-Sorbonne', 'alice.durand@student.edu', '06 12 34 56 78'),
('AB002', 'Bob', 'Leroy', 'ENSEIGNANT', 'Université Paris-Diderot', 'bob.leroy@univ.edu', '06 98 76 54 32'),
('AB003', 'Claire', 'Moreau', 'ETUDIANT', 'École Centrale Paris', 'claire.moreau@student.edu', '06 11 22 33 44'),
('AB004', 'David', 'Petit', 'PERSONNEL', 'CNRS', 'david.petit@cnrs.fr', '06 55 66 77 88'),
('AB005', 'Emma', 'Rousseau', 'ETUDIANT', 'Sciences Po Paris', 'emma.rousseau@student.edu', '06 99 88 77 66');

-- Livres de test
INSERT INTO livres (identifiant, titre, auteurs, annee_publication, domaine, niveau_requis, isbn, editeur, description, nombre_pages) VALUES
('LIV001', 'Java pour les débutants', 'John Smith, Sarah Johnson', 2020, 'INFORMATIQUE', 'DEBUTANT', '978-1234567890', 'Éditions Programmation', 'Guide complet pour apprendre Java', 350),
('LIV002', 'Algorithmes et structures de données', 'Michael Brown', 2019, 'INFORMATIQUE', 'INTERMEDIAIRE', '978-0987654321', 'Tech Books', 'Manuel avancé sur les algorithmes', 450),
('LIV003', 'Calcul différentiel et intégral', 'Pierre Laplace', 2018, 'MATHEMATIQUES', 'AVANCE', '978-1122334455', 'Mathématiques Éditions', 'Cours complet de calcul', 600),
('LIV004', 'Physique quantique', 'Marie Curie', 2021, 'PHYSIQUE', 'EXPERT', '978-5566778899', 'Science Press', 'Introduction à la physique quantique', 400),
('LIV005', 'Histoire de France', 'Jean Historien', 2017, 'HISTOIRE', 'TOUS_NIVEAUX', '978-9988776655', 'Histoire Éditions', 'Histoire complète de la France', 800),
('LIV006', 'Économie moderne', 'Paul Économiste', 2022, 'ECONOMIE', 'INTERMEDIAIRE', '978-4433221100', 'Économie Press', 'Principes de l''économie moderne', 500),
('LIV007', 'Programmation Python', 'Anna Programmatrice', 2021, 'INFORMATIQUE', 'DEBUTANT', '978-6677889900', 'Code Books', 'Apprendre Python facilement', 300),
('LIV008', 'Chimie organique', 'Louis Chimiste', 2020, 'CHIMIE', 'AVANCE', '978-2233445566', 'Chimie Éditions', 'Cours de chimie organique', 550);

-- Exemplaires de test
INSERT INTO exemplaires (code_exemplaire, livre_id, etat, localisation) VALUES
('EX001', 1, 'Bon', 'Rayon A - Informatique'),
('EX002', 1, 'Bon', 'Rayon A - Informatique'),
('EX003', 2, 'Bon', 'Rayon A - Informatique'),
('EX004', 3, 'Moyen', 'Rayon B - Mathématiques'),
('EX005', 4, 'Bon', 'Rayon C - Physique'),
('EX006', 5, 'Bon', 'Rayon D - Histoire'),
('EX007', 6, 'Bon', 'Rayon E - Économie'),
('EX008', 7, 'Bon', 'Rayon A - Informatique'),
('EX009', 8, 'Bon', 'Rayon F - Chimie'),
('EX010', 1, 'Bon', 'Rayon A - Informatique');

-- Emprunts de test
INSERT INTO emprunts (abonne_id, exemplaire_id, bibliothecaire_id, date_emprunt, date_retour_prevue, statut) VALUES
(1, 1, 2, '2024-01-15 10:00:00', '2024-02-15', 'EN_COURS'),
(2, 3, 2, '2024-01-10 14:30:00', '2024-02-10', 'EN_COURS'),
(3, 5, 3, '2024-01-05 09:15:00', '2024-02-05', 'EN_COURS');

-- Emprunt retourné
INSERT INTO emprunts (abonne_id, exemplaire_id, bibliothecaire_id, date_emprunt, date_retour_prevue, date_retour_effective, statut) VALUES
(4, 7, 2, '2023-12-01 11:00:00', '2024-01-01', '2023-12-28 16:45:00', 'RETOURNE');

-- Création des index pour optimiser les performances (avec des tailles appropriées)
CREATE INDEX idx_abonnes_numero ON abonnes(numero_abonnement);
CREATE INDEX idx_abonnes_email ON abonnes(email(50));
CREATE INDEX idx_abonnes_statut ON abonnes(statut);
CREATE INDEX idx_abonnes_actif ON abonnes(actif);

CREATE INDEX idx_livres_identifiant ON livres(identifiant);
CREATE INDEX idx_livres_isbn ON livres(isbn);
CREATE INDEX idx_livres_domaine ON livres(domaine);
CREATE INDEX idx_livres_niveau ON livres(niveau_requis);
CREATE INDEX idx_livres_actif ON livres(actif);

CREATE INDEX idx_exemplaires_code ON exemplaires(code_exemplaire);
CREATE INDEX idx_exemplaires_livre ON exemplaires(livre_id);
CREATE INDEX idx_exemplaires_disponible ON exemplaires(disponible);
CREATE INDEX idx_exemplaires_actif ON exemplaires(actif);

CREATE INDEX idx_emprunts_abonne ON emprunts(abonne_id);
CREATE INDEX idx_emprunts_exemplaire ON emprunts(exemplaire_id);
CREATE INDEX idx_emprunts_bibliothecaire ON emprunts(bibliothecaire_id);
CREATE INDEX idx_emprunts_date_retour ON emprunts(date_retour_prevue);
CREATE INDEX idx_emprunts_statut ON emprunts(statut);

CREATE INDEX idx_utilisateurs_nom_utilisateur ON utilisateurs(nom_utilisateur);
CREATE INDEX idx_utilisateurs_email ON utilisateurs(email(50));
CREATE INDEX idx_utilisateurs_role ON utilisateurs(role);
CREATE INDEX idx_utilisateurs_actif ON utilisateurs(actif);

-- Affichage des statistiques
SELECT 'Base de données initialisée avec succès!' AS message;
SELECT COUNT(*) AS nombre_utilisateurs FROM utilisateurs;
SELECT COUNT(*) AS nombre_abonnes FROM abonnes;
SELECT COUNT(*) AS nombre_livres FROM livres;
SELECT COUNT(*) AS nombre_exemplaires FROM exemplaires;
SELECT COUNT(*) AS nombre_emprunts FROM emprunts;