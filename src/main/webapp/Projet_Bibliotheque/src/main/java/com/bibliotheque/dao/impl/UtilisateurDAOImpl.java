package com.bibliotheque.dao.impl;

import com.bibliotheque.dao.UtilisateurDAO;
import com.bibliotheque.entity.Role;
import com.bibliotheque.entity.Utilisateur;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class UtilisateurDAOImpl extends GenericDAOImpl<Utilisateur, Long> implements UtilisateurDAO {

    public UtilisateurDAOImpl() {
        super(Utilisateur.class);
    }

    @Override
    public Optional<Utilisateur> findByNomUtilisateur(String nomUtilisateur) {
        EntityManager em = em();
        try {
            String jpql = "SELECT u FROM Utilisateur u WHERE u.nomUtilisateur = :nomUtilisateur";
            List<Utilisateur> results = em.createQuery(jpql, Utilisateur.class)
                    .setParameter("nomUtilisateur", nomUtilisateur)
                    .getResultList();
            
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche par nom d'utilisateur: " + nomUtilisateur, e);
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Utilisateur> findByEmail(String email) {
        EntityManager em = em();
        try {
            String jpql = "SELECT u FROM Utilisateur u WHERE u.email = :email";
            List<Utilisateur> results = em.createQuery(jpql, Utilisateur.class)
                    .setParameter("email", email)
                    .getResultList();
            
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche par email: " + email, e);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existsByNomUtilisateur(String nomUtilisateur) {
        try {
            return findByNomUtilisateur(nomUtilisateur).isPresent();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la vérification de l'existence du nom d'utilisateur: " + nomUtilisateur, e);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        try {
            return findByEmail(email).isPresent();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la vérification de l'existence de l'email: " + email, e);
        }
    }

    @Override
    public List<Utilisateur> findByRole(Role role) {
        EntityManager em = em();
        try {
            String jpql = "SELECT u FROM Utilisateur u WHERE u.role = :role";
            return em.createQuery(jpql, Utilisateur.class)
                    .setParameter("role", role)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche par rôle: " + role, e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Utilisateur> findActifs() {
        EntityManager em = em();
        try {
            String jpql = "SELECT u FROM Utilisateur u WHERE u.actif = true ORDER BY u.nom, u.prenom";
            return em.createQuery(jpql, Utilisateur.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des utilisateurs actifs", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Utilisateur> authenticate(String nomUtilisateur, String motDePasse) {
        EntityManager em = em();
        try {
            System.out.println("=== Tentative d'authentification pour: " + nomUtilisateur + " ===");
            
            // Vérifier si l'EntityManager est disponible
            if (em == null) {
                System.err.println("=== ERREUR: EntityManager est null ===");
                throw new RuntimeException("EntityManager non disponible");
            }
            
            System.out.println("=== EntityManager disponible, exécution de la requête ===");
            
            // Utiliser une requête JPQL directe
            String jpql = "SELECT u FROM Utilisateur u WHERE u.nomUtilisateur = :nomUtilisateur AND u.motDePasse = :motDePasse AND u.actif = true";
            List<Utilisateur> results = em.createQuery(jpql, Utilisateur.class)
                    .setParameter("nomUtilisateur", nomUtilisateur)
                    .setParameter("motDePasse", motDePasse)
                    .getResultList();
            
            System.out.println("=== Nombre d'utilisateurs trouvés: " + results.size() + " ===");
            
            if (!results.isEmpty()) {
                Utilisateur utilisateur = results.get(0);
                System.out.println("=== Utilisateur authentifié: " + utilisateur.getNomUtilisateur() + " (Role: " + utilisateur.getRole() + ") ===");
                return Optional.of(utilisateur);
            }
            
            System.out.println("=== Aucun utilisateur trouvé avec ces identifiants ===");
            return Optional.empty();
            
        } catch (Exception e) {
            System.err.println("=== Erreur lors de l'authentification: " + e.getMessage() + " ===");
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'authentification pour l'utilisateur: " + nomUtilisateur, e);
        } finally {
            em.close();
        }
    }
    
    // Méthode de test pour vérifier l'EntityManager
    public void testEntityManager() {
        EntityManager em = em();
        try {
            System.out.println("=== Test de l'EntityManager ===");
            if (em == null) {
                System.err.println("=== ERREUR: EntityManager est null ===");
                return;
            }
            
            System.out.println("=== EntityManager disponible ===");
            
            // Test simple de requête
            String jpql = "SELECT COUNT(u) FROM Utilisateur u";
            Long count = em.createQuery(jpql, Long.class).getSingleResult();
            System.out.println("=== Nombre total d'utilisateurs: " + count + " ===");
            
        } catch (Exception e) {
            System.err.println("=== Erreur lors du test de l'EntityManager: " + e.getMessage() + " ===");
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
} 