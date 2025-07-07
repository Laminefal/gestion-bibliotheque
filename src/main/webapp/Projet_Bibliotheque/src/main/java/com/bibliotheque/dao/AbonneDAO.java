package com.bibliotheque.dao;

import com.bibliotheque.entity.Abonne;
import java.util.List;
import java.util.Optional;

public interface AbonneDAO extends GenericDAO<Abonne, Long> {
    Optional<Abonne> findByNumeroAbonnement(String numeroAbonnement);
    Optional<Abonne> findByEmail(String email);
    List<Abonne> findByStatut(String statut);
    List<Abonne> findActifs();
    boolean existsByNumeroAbonnement(String numeroAbonnement);
    boolean existsByEmail(String email);
    List<Abonne> findLastInscrits(int limit);
}