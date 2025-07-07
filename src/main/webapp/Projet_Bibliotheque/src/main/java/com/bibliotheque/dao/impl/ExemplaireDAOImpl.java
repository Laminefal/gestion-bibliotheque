package com.bibliotheque.dao.impl;

import com.bibliotheque.dao.ExemplaireDAO;
import com.bibliotheque.entity.Exemplaire;
import com.bibliotheque.entity.Livre;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class ExemplaireDAOImpl extends GenericDAOImpl<Exemplaire, Long> implements ExemplaireDAO {

    public ExemplaireDAOImpl() {
        super(Exemplaire.class);
    }

    @Override
    public List<Exemplaire> findByLivre(Livre livre) {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT e FROM Exemplaire e WHERE e.livre = :livre", Exemplaire.class)
                    .setParameter("livre", livre)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Exemplaire> findDisponiblesByLivre(Livre livre) {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT e FROM Exemplaire e WHERE e.livre = :livre AND e.disponible = true AND e.actif = true", Exemplaire.class)
                    .setParameter("livre", livre)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Exemplaire> findAllDisponibles() {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT e FROM Exemplaire e WHERE e.disponible = true AND e.actif = true", Exemplaire.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Exemplaire findByCodeExemplaire(String codeExemplaire) {
        EntityManager em = em();
        try {
            List<Exemplaire> result = em.createQuery("SELECT e FROM Exemplaire e WHERE e.codeExemplaire = :code", Exemplaire.class)
                    .setParameter("code", codeExemplaire)
                    .getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Exemplaire> searchByCodeOrLivre(String searchTerm) {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT e FROM Exemplaire e WHERE LOWER(e.codeExemplaire) LIKE :search OR LOWER(e.livre.titre) LIKE :search", Exemplaire.class)
                    .setParameter("search", "%" + searchTerm.toLowerCase() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Exemplaire> findByEtat(String etat) {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT e FROM Exemplaire e WHERE e.etat = :etat", Exemplaire.class)
                    .setParameter("etat", etat)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Exemplaire> findDisponibles() {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT e FROM Exemplaire e LEFT JOIN FETCH e.livre WHERE e.disponible = true", Exemplaire.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Exemplaire> findIndisponibles() {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT e FROM Exemplaire e LEFT JOIN FETCH e.livre WHERE e.disponible = false", Exemplaire.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Exemplaire> findActifs() {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT e FROM Exemplaire e WHERE e.actif = true", Exemplaire.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Exemplaire> findInactifs() {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT e FROM Exemplaire e WHERE e.actif = false", Exemplaire.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long countDisponibles() {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT COUNT(e) FROM Exemplaire e WHERE e.disponible = true", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public long countIndisponibles() {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT COUNT(e) FROM Exemplaire e WHERE e.disponible = false", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public long countActifs() {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT COUNT(e) FROM Exemplaire e WHERE e.actif = true", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
}