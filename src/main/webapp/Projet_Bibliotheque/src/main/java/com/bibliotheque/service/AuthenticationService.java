package com.bibliotheque.service;

import com.bibliotheque.entity.Utilisateur;
import com.bibliotheque.dao.UtilisateurDAO;
import com.bibliotheque.dao.impl.UtilisateurDAOImpl;
import java.util.Optional;

public class AuthenticationService {
    
    private UtilisateurDAO utilisateurDAO;
    
    public AuthenticationService() {
        this.utilisateurDAO = new UtilisateurDAOImpl();
    }
    
    /**
     * Authentifie un utilisateur
     * @param nomUtilisateur le nom d'utilisateur
     * @param motDePasse le mot de passe
     * @return l'utilisateur authentifié ou null
     */
    public Optional<Utilisateur> authenticate(String nomUtilisateur, String motDePasse) {
        if (nomUtilisateur == null || motDePasse == null || 
            nomUtilisateur.trim().isEmpty() || motDePasse.trim().isEmpty()) {
            return Optional.empty();
        }
        
        return utilisateurDAO.authenticate(nomUtilisateur.trim(), motDePasse);
    }
    
    /**
     * Vérifie si un utilisateur est administrateur
     * @param utilisateur l'utilisateur
     * @return true si l'utilisateur est administrateur, false sinon
     */
    public boolean isAdmin(Utilisateur utilisateur) {
        return utilisateur != null && 
               utilisateur.getRole() == com.bibliotheque.entity.Role.ADMIN;
    }
    
    /**
     * Vérifie si un utilisateur est bibliothécaire
     * @param utilisateur l'utilisateur
     * @return true si l'utilisateur est bibliothécaire, false sinon
     */
    public boolean isBibliothecaire(Utilisateur utilisateur) {
        return utilisateur != null && 
               utilisateur.getRole() == com.bibliotheque.entity.Role.BIBLIOTHECAIRE;
    }
    
    /**
     * Vérifie si un utilisateur a les droits d'accès
     * @param utilisateur l'utilisateur
     * @return true si l'utilisateur a les droits, false sinon
     */
    public boolean hasAccess(Utilisateur utilisateur) {
        return utilisateur != null && utilisateur.isActif();
    }
    
    /**
     * Trouve un utilisateur par son nom d'utilisateur
     * @param nomUtilisateur le nom d'utilisateur
     * @return l'utilisateur trouvé ou null
     */
    public Optional<Utilisateur> findByNomUtilisateur(String nomUtilisateur) {
        if (nomUtilisateur == null || nomUtilisateur.trim().isEmpty()) {
            return Optional.empty();
        }
        
        return utilisateurDAO.findByNomUtilisateur(nomUtilisateur.trim());
    }
    
    /**
     * Vérifie si un nom d'utilisateur existe
     * @param nomUtilisateur le nom d'utilisateur
     * @return true si le nom d'utilisateur existe, false sinon
     */
    public boolean existsByNomUtilisateur(String nomUtilisateur) {
        if (nomUtilisateur == null || nomUtilisateur.trim().isEmpty()) {
            return false;
        }
        
        return utilisateurDAO.existsByNomUtilisateur(nomUtilisateur.trim());
    }
    
    /**
     * Vérifie si un email existe
     * @param email l'email
     * @return true si l'email existe, false sinon
     */
    public boolean existsByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        return utilisateurDAO.existsByEmail(email.trim());
    }
} 