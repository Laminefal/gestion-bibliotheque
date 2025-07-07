package com.bibliotheque.dao;

import com.bibliotheque.entity.Utilisateur;
import com.bibliotheque.entity.Role;
import java.util.List;
import java.util.Optional;

public interface UtilisateurDAO extends GenericDAO<Utilisateur, Long> {
    
    /**
     * Trouve un utilisateur par son nom d'utilisateur
     * @param nomUtilisateur le nom d'utilisateur
     * @return l'utilisateur trouvé ou null
     */
    Optional<Utilisateur> findByNomUtilisateur(String nomUtilisateur);
    
    /**
     * Trouve un utilisateur par son email
     * @param email l'email
     * @return l'utilisateur trouvé ou null
     */
    Optional<Utilisateur> findByEmail(String email);
    
    /**
     * Trouve tous les utilisateurs par rôle
     * @param role le rôle
     * @return la liste des utilisateurs
     */
    List<Utilisateur> findByRole(Role role);
    
    /**
     * Trouve tous les utilisateurs actifs
     * @return la liste des utilisateurs actifs
     */
    List<Utilisateur> findActifs();
    
    /**
     * Authentifie un utilisateur
     * @param nomUtilisateur le nom d'utilisateur
     * @param motDePasse le mot de passe
     * @return l'utilisateur authentifié ou null
     */
    Optional<Utilisateur> authenticate(String nomUtilisateur, String motDePasse);
    
    /**
     * Vérifie si un nom d'utilisateur existe
     * @param nomUtilisateur le nom d'utilisateur
     * @return true si le nom d'utilisateur existe, false sinon
     */
    boolean existsByNomUtilisateur(String nomUtilisateur);
    
    /**
     * Vérifie si un email existe
     * @param email l'email
     * @return true si l'email existe, false sinon
     */
    boolean existsByEmail(String email);
} 