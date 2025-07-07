-- Script pour ajouter la colonne photo à la table utilisateurs
-- À exécuter si la base de données existe déjà

USE bibliotheque;

-- Ajouter la colonne photo si elle n'existe pas
ALTER TABLE utilisateurs ADD COLUMN IF NOT EXISTS photo VARCHAR(255);

-- Mettre à jour les photos par défaut pour les utilisateurs existants
UPDATE utilisateurs SET photo = 'default-avatar.png' WHERE photo IS NULL;

-- Afficher le résultat
SELECT 'Colonne photo ajoutée avec succès!' AS message;
SELECT COUNT(*) AS nombre_utilisateurs_avec_photo FROM utilisateurs WHERE photo IS NOT NULL; 