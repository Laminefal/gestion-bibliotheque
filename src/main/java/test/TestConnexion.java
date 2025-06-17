package test;

import entities.Bibliothecaire;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestConnexion {
    public static void main(String[] args) {
        System.out.println("Début du test de connexion...");
        
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try {
            // 1. Création de l'EntityManagerFactory
            emf = Persistence.createEntityManagerFactory("bibliothequePU");
            System.out.println("✅ EntityManagerFactory créé avec succès");
            
            // 2. Création de l'EntityManager
            em = emf.createEntityManager();
            System.out.println("✅ EntityManager créé avec succès");
            
            // 3. Test de requête
            Bibliothecaire b = em.find(Bibliothecaire.class, 1);
            System.out.println("Résultat de la requête : " + (b != null ? b.getNom() : "Aucun résultat"));
            
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du test :");
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }
}