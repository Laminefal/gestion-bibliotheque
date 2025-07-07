package com.bibliotheque.dao.impl;

import com.bibliotheque.dao.AbonneDAO;
import com.bibliotheque.entity.Abonne;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class AbonneDAOImpl extends GenericDAOImpl<Abonne, Long> implements AbonneDAO {

    public AbonneDAOImpl() {
        super(Abonne.class);
    }

    @Override
    public Optional<Abonne> findByNumeroAbonnement(String numeroAbonnement) {
        EntityManager em = em();
        try {
            List<Abonne> result = em.createQuery("SELECT a FROM Abonne a WHERE a.numeroAbonnement = :numero", Abonne.class)
                    .setParameter("numero", numeroAbonnement)
                    .getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Abonne> findByEmail(String email) {
        EntityManager em = em();
        try {
            List<Abonne> result = em.createQuery("SELECT a FROM Abonne a WHERE a.email = :email", Abonne.class)
                    .setParameter("email", email)
                    .getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        } finally {
            em.close();
        }
    }

    @Override
    public List<Abonne> findByStatut(String statut) {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT a FROM Abonne a WHERE a.statut = :statut", Abonne.class)
                    .setParameter("statut", statut)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Abonne> findActifs() {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT a FROM Abonne a WHERE a.actif = true", Abonne.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existsByNumeroAbonnement(String numeroAbonnement) {
        return findByNumeroAbonnement(numeroAbonnement).isPresent();
    }

    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }

    public List<Abonne> findLastInscrits(int limit) {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT a FROM Abonne a ORDER BY a.dateInscription DESC", Abonne.class)
                     .setMaxResults(limit)
                     .getResultList();
        } finally {
            em.close();
        }
    }
}