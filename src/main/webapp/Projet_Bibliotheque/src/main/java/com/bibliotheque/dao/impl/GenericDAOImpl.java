package com.bibliotheque.dao.impl;

import com.bibliotheque.dao.GenericDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class GenericDAOImpl<T, ID> implements GenericDAO<T, ID> {

    private static EntityManagerFactory emf;
    private final Class<T> entityClass;

    public GenericDAOImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("bibliothequePU");
        }
    }

    // Méthode pour accéder à l'EntityManager
    protected EntityManager em() {
        return emf.createEntityManager();
    }

    @Override
    public T save(T entity) {
        EntityManager em = em();
        em.getTransaction().begin();
        try {
            Object id = getId(entity);
            if (id == null) {
                em.persist(entity);
            } else {
                entity = em.merge(entity);
            }
            em.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur lors de la sauvegarde de l'entité", e);
        } finally {
            em.close();
        }
    }

    // Méthode utilitaire pour obtenir l'ID d'une entité via réflexion
    private Object getId(T entity) {
        try {
            return entity.getClass().getMethod("getId").invoke(entity);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public T update(T entity) {
        EntityManager em = em();
        em.getTransaction().begin();
        try {
            T updatedEntity = em.merge(entity);
            em.getTransaction().commit();
            return updatedEntity;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur lors de la mise à jour de l'entité", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        EntityManager em = em();
        try {
            T entity = em.find(entityClass, id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de l'entité par ID", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<T> findAll() {
        EntityManager em = em();
        try {
            String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
            return em.createQuery(jpql, entityClass).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de toutes les entités", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(T entity) {
        EntityManager em = em();
        em.getTransaction().begin();
        try {
            em.remove(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur lors de la suppression de l'entité", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteById(ID id) {
        EntityManager em = em();
        em.getTransaction().begin();
        try {
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur lors de la suppression de l'entité par ID", e);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existsById(ID id) {
        EntityManager em = em();
        try {
            return em.find(entityClass, id) != null;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la vérification de l'existence de l'entité", e);
        } finally {
            em.close();
        }
    }

    @Override
    public long count() {
        EntityManager em = em();
        try {
            String jpql = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e";
            return em.createQuery(jpql, Long.class).getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du comptage des entités", e);
        } finally {
            em.close();
        }
    }

    // Méthode pour fermer l'EntityManagerFactory
    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    // Méthode pour vérifier si l'EntityManagerFactory est ouvert
    public static boolean isEntityManagerFactoryOpen() {
        return emf != null && emf.isOpen();
    }

    // Méthode pour obtenir la classe de l'entité
    public Class<T> getEntityClass() {
        return entityClass;
    }
}