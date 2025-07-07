-- Script pour ajouter l'utilisateur biblio
-- À exécuter si la base de données existe déjà

USE bibliotheque;

-- Ajouter l'utilisateur biblio s'il n'existe pas
INSERT IGNORE INTO utilisateurs (nom_utilisateur, mot_de_passe, prenom, nom, email, role, photo) VALUES
('biblio', 'biblio123', 'Bibliothécaire', 'Test', 'biblio@bibliotheque.edu', 'BIBLIOTHECAIRE', 'default-avatar.png');

-- Ajouter les informations de bibliothécaire pour l'utilisateur biblio
INSERT IGNORE INTO bibliothecaires (utilisateur_id, adresse, date_recrutement, specialite, telephone) 
SELECT id, 'Adresse de test', '2024-01-01', 'Général', '01 00 00 00 00'
FROM utilisateurs 
WHERE nom_utilisateur = 'biblio' 
AND id NOT IN (SELECT utilisateur_id FROM bibliothecaires);

-- Afficher le résultat
SELECT 'Utilisateur biblio ajouté avec succès!' AS message;
SELECT nom_utilisateur, role, actif FROM utilisateurs WHERE nom_utilisateur IN ('admin', 'biblio', 'biblio1', 'biblio2'); 