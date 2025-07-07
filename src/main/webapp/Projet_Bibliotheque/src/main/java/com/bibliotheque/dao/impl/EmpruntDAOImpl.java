package com.bibliotheque.dao.impl;

import com.bibliotheque.dao.EmpruntDAO;
import com.bibliotheque.entity.Emprunt;
import com.bibliotheque.entity.Abonne;
import com.bibliotheque.entity.Exemplaire;
import com.bibliotheque.entity.StatutEmprunt;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class EmpruntDAOImpl extends GenericDAOImpl<Emprunt, Long> implements EmpruntDAO {

    public EmpruntDAOImpl() {
        super(Emprunt.class);
    }

    @Override
    public List<Emprunt> findAll() {
        EntityManager em = em();
        try {
            // Utiliser JOIN FETCH pour charger les entités liées
            return em.createQuery("SELECT DISTINCT e FROM Emprunt e " +
                    "LEFT JOIN FETCH e.abonne " +
                    "LEFT JOIN FETCH e.exemplaire " +
                    "LEFT JOIN FETCH e.exemplaire.livre " +
                    "LEFT JOIN FETCH e.bibliothecaire", Emprunt.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Emprunt> findByAbonne(Abonne abonne) {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT DISTINCT e FROM Emprunt e " +
                    "LEFT JOIN FETCH e.abonne " +
                    "LEFT JOIN FETCH e.exemplaire " +
                    "LEFT JOIN FETCH e.exemplaire.livre " +
                    "LEFT JOIN FETCH e.bibliothecaire " +
                    "WHERE e.abonne = :abonne", Emprunt.class)
                    .setParameter("abonne", abonne)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Emprunt> findEnCoursByAbonne(Abonne abonne) {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT DISTINCT e FROM Emprunt e " +
                    "LEFT JOIN FETCH e.abonne " +
                    "LEFT JOIN FETCH e.exemplaire " +
                    "LEFT JOIN FETCH e.exemplaire.livre " +
                    "LEFT JOIN FETCH e.bibliothecaire " +
                    "WHERE e.abonne = :abonne AND e.statut = :statut", Emprunt.class)
                    .setParameter("abonne", abonne)
                    .setParameter("statut", StatutEmprunt.EN_COURS)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Emprunt> findByExemplaire(Exemplaire exemplaire) {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT DISTINCT e FROM Emprunt e " +
                    "LEFT JOIN FETCH e.abonne " +
                    "LEFT JOIN FETCH e.exemplaire " +
                    "LEFT JOIN FETCH e.exemplaire.livre " +
                    "LEFT JOIN FETCH e.bibliothecaire " +
                    "WHERE e.exemplaire = :exemplaire", Emprunt.class)
                    .setParameter("exemplaire", exemplaire)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Emprunt findEnCoursByExemplaire(Exemplaire exemplaire) {
        EntityManager em = em();
        try {
            List<Emprunt> result = em.createQuery("SELECT DISTINCT e FROM Emprunt e " +
                    "LEFT JOIN FETCH e.abonne " +
                    "LEFT JOIN FETCH e.exemplaire " +
                    "LEFT JOIN FETCH e.exemplaire.livre " +
                    "LEFT JOIN FETCH e.bibliothecaire " +
                    "WHERE e.exemplaire = :exemplaire AND e.statut = :statut", Emprunt.class)
                    .setParameter("exemplaire", exemplaire)
                    .setParameter("statut", StatutEmprunt.EN_COURS)
                    .getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Emprunt> findByStatut(StatutEmprunt statut) {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT DISTINCT e FROM Emprunt e " +
                    "LEFT JOIN FETCH e.abonne " +
                    "LEFT JOIN FETCH e.exemplaire " +
                    "LEFT JOIN FETCH e.exemplaire.livre " +
                    "LEFT JOIN FETCH e.bibliothecaire " +
                    "WHERE e.statut = :statut", Emprunt.class)
                    .setParameter("statut", statut)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Emprunt> findEnCours() {
        return findByStatut(StatutEmprunt.EN_COURS);
    }

    @Override
    public List<Emprunt> findEnRetard() {
        return findByStatut(StatutEmprunt.EN_RETARD);
    }

    @Override
    public List<Emprunt> findByDateRetourPrevue(LocalDate dateRetourPrevue) {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT DISTINCT e FROM Emprunt e " +
                    "LEFT JOIN FETCH e.abonne " +
                    "LEFT JOIN FETCH e.exemplaire " +
                    "LEFT JOIN FETCH e.exemplaire.livre " +
                    "LEFT JOIN FETCH e.bibliothecaire " +
                    "WHERE e.dateRetourPrevue = :dateRetourPrevue", Emprunt.class)
                    .setParameter("dateRetourPrevue", dateRetourPrevue)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Emprunt> findByDateEmpruntBetween(LocalDate dateDebut, LocalDate dateFin) {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT DISTINCT e FROM Emprunt e " +
                    "LEFT JOIN FETCH e.abonne " +
                    "LEFT JOIN FETCH e.exemplaire " +
                    "LEFT JOIN FETCH e.exemplaire.livre " +
                    "LEFT JOIN FETCH e.bibliothecaire " +
                    "WHERE e.dateEmprunt >= :dateDebut AND e.dateEmprunt <= :dateFin", Emprunt.class)
                    .setParameter("dateDebut", dateDebut.atStartOfDay())
                    .setParameter("dateFin", dateFin.atTime(23,59,59))
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Emprunt> findByDateRetourEffectiveBetween(LocalDate dateDebut, LocalDate dateFin) {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT DISTINCT e FROM Emprunt e " +
                    "LEFT JOIN FETCH e.abonne " +
                    "LEFT JOIN FETCH e.exemplaire " +
                    "LEFT JOIN FETCH e.exemplaire.livre " +
                    "LEFT JOIN FETCH e.bibliothecaire " +
                    "WHERE e.dateRetourEffective >= :dateDebut AND e.dateRetourEffective <= :dateFin", Emprunt.class)
                    .setParameter("dateDebut", dateDebut.atStartOfDay())
                    .setParameter("dateFin", dateFin.atTime(23,59,59))
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long countEnCoursByAbonne(Abonne abonne) {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT COUNT(e) FROM Emprunt e WHERE e.abonne = :abonne AND e.statut = :statut", Long.class)
                    .setParameter("abonne", abonne)
                    .setParameter("statut", StatutEmprunt.EN_COURS)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public long countEnRetard() {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT COUNT(e) FROM Emprunt e WHERE e.statut = :statut", Long.class)
                    .setParameter("statut", StatutEmprunt.EN_RETARD)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Emprunt> findLastEmprunts(int limit) {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT DISTINCT e FROM Emprunt e " +
                    "LEFT JOIN FETCH e.abonne " +
                    "LEFT JOIN FETCH e.exemplaire " +
                    "LEFT JOIN FETCH e.exemplaire.livre " +
                    "LEFT JOIN FETCH e.bibliothecaire " +
                    "ORDER BY e.dateEmprunt DESC", Emprunt.class)
                    .setMaxResults(limit)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}