package com.bibliotheque.dao.impl;

import com.bibliotheque.dao.LivreDAO;
import com.bibliotheque.entity.Livre;
import com.bibliotheque.entity.Domaine;
import com.bibliotheque.entity.Niveau;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class LivreDAOImpl extends GenericDAOImpl<Livre, Long> implements LivreDAO {

    public LivreDAOImpl() {
        super(Livre.class);
    }

    @Override
    public Livre save(Livre livre) {
        EntityManager em = em();
        try {
            em.persist(livre);
            return livre;
        } finally {
            em.close();
        }
    }

    @Override
    public Livre update(Livre livre) {
        EntityManager em = em();
        try {
            return em.merge(livre);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Livre livre) {
        EntityManager em = em();
        try {
            Livre attached = em.find(Livre.class, livre.getId());
            if (attached != null) em.remove(attached);
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteById(Long id) {
        EntityManager em = em();
        try {
            Livre livre = em.find(Livre.class, id);
            if (livre != null) em.remove(livre);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existsById(Long id) {
        EntityManager em = em();
        try {
            return em.find(Livre.class, id) != null;
        } finally {
            em.close();
        }
    }

    @Override
    public long count() {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT COUNT(l) FROM Livre l", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Livre> findById(Long id) {
        EntityManager em = em();
        try {
            return Optional.ofNullable(em.find(Livre.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Livre> findByIdentifiant(String identifiant) {
        EntityManager em = em();
        try {
            List<Livre> result = em.createQuery("SELECT l FROM Livre l WHERE l.identifiant = :identifiant", Livre.class)
                    .setParameter("identifiant", identifiant)
                    .getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existsByIdentifiant(String identifiant) {
        EntityManager em = em();
        try {
            Long count = em.createQuery("SELECT COUNT(l) FROM Livre l WHERE l.identifiant = :identifiant", Long.class)
                    .setParameter("identifiant", identifiant)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Livre> findByIsbn(String isbn) {
        EntityManager em = em();
        try {
            List<Livre> result = em.createQuery("SELECT l FROM Livre l WHERE l.isbn = :isbn", Livre.class)
                    .setParameter("isbn", isbn)
                    .getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        EntityManager em = em();
        try {
            Long count = em.createQuery("SELECT COUNT(l) FROM Livre l WHERE l.isbn = :isbn", Long.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Livre> findAll() {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT l FROM Livre l", Livre.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Livre> findByAnneePublication(Integer annee) {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT l FROM Livre l WHERE l.anneePublication = :annee", Livre.class)
                    .setParameter("annee", annee)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Livre> findByDomaine(Domaine domaine) {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT l FROM Livre l WHERE l.domaine = :domaine", Livre.class)
                    .setParameter("domaine", domaine)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Livre> findByNiveau(Niveau niveau) {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT l FROM Livre l WHERE l.niveauRequis = :niveau", Livre.class)
                    .setParameter("niveau", niveau)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Livre> findByDomaineAndNiveau(Domaine domaine, Niveau niveau) {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT l FROM Livre l WHERE l.domaine = :domaine AND l.niveauRequis = :niveau", Livre.class)
                    .setParameter("domaine", domaine)
                    .setParameter("niveau", niveau)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Livre> findActifs() {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT l FROM Livre l WHERE l.actif = true", Livre.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Livre> findDisponibles() {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT l FROM Livre l WHERE l.actif = true AND EXISTS (SELECT e FROM Exemplaire e WHERE e.livre = l AND e.disponible = true AND e.actif = true)", Livre.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Livre> searchByTitre(String titre) {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT l FROM Livre l WHERE l.titre LIKE :titre", Livre.class)
                    .setParameter("titre", "%" + titre + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Livre> searchByAuteur(String auteur) {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT l FROM Livre l WHERE l.auteurs LIKE :auteur", Livre.class)
                    .setParameter("auteur", "%" + auteur + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}