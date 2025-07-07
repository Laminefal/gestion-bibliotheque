package com.bibliotheque.dao;

import com.bibliotheque.entity.Exemplaire;
import com.bibliotheque.entity.Livre;
import java.util.List;

public interface ExemplaireDAO extends GenericDAO<Exemplaire, Long> {
    List<Exemplaire> findByLivre(Livre livre);
    List<Exemplaire> findDisponiblesByLivre(Livre livre);
    List<Exemplaire> findAllDisponibles();
    Exemplaire findByCodeExemplaire(String codeExemplaire);
    
    // Méthodes de filtrage
    List<Exemplaire> searchByCodeOrLivre(String searchTerm);
    List<Exemplaire> findByEtat(String etat);
    List<Exemplaire> findDisponibles();
    List<Exemplaire> findIndisponibles();
    List<Exemplaire> findActifs();
    List<Exemplaire> findInactifs();
    
    // Méthodes de statistiques
    long countDisponibles();
    long countIndisponibles();
    long countActifs();
}