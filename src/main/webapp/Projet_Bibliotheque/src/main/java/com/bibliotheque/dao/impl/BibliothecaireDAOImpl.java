package com.bibliotheque.dao.impl;

import com.bibliotheque.dao.BibliothecaireDAO;
import com.bibliotheque.entity.Bibliothecaire;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class BibliothecaireDAOImpl implements BibliothecaireDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bibliothequePU");

    @Override
    public List<Bibliothecaire> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Bibliothecaire> list = em.createQuery("SELECT b FROM Bibliothecaire b", Bibliothecaire.class).getResultList();
        em.close();
        return list;
    }

    @Override
    public Bibliothecaire findById(Long id) {
        EntityManager em = emf.createEntityManager();
        Bibliothecaire b = em.find(Bibliothecaire.class, id);
        em.close();
        return b;
    }

    @Override
    public void save(Bibliothecaire biblio) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        if (biblio.getId() == null) {
            em.persist(biblio);
        } else {
            em.merge(biblio);
        }
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Bibliothecaire b = em.find(Bibliothecaire.class, id);
        if (b != null) em.remove(b);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public boolean emailExiste(String email, Long idExclu) {
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT COUNT(u) FROM Utilisateur u WHERE u.email = :email";
        if (idExclu != null) {
            jpql += " AND u.id <> :idExclu";
        }
        var q = em.createQuery(jpql, Long.class);
        q.setParameter("email", email);
        if (idExclu != null) q.setParameter("idExclu", idExclu);
        Long count = q.getSingleResult();
        em.close();
        return count > 0;
    }
}