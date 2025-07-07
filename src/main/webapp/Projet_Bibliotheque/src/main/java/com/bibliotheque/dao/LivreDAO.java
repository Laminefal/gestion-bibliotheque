package com.bibliotheque.dao;

import com.bibliotheque.entity.Livre;
import com.bibliotheque.entity.Domaine;
import com.bibliotheque.entity.Niveau;

import java.util.List;
import java.util.Optional;

public interface LivreDAO {
    Livre save(Livre livre);
    Livre update(Livre livre);
    void delete(Livre livre);
    void deleteById(Long id);
    boolean existsById(Long id);
    long count();

    Optional<Livre> findById(Long id);
    Optional<Livre> findByIdentifiant(String identifiant);
    boolean existsByIdentifiant(String identifiant);

    Optional<Livre> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);

    List<Livre> findAll();
    List<Livre> findByAnneePublication(Integer annee);
    List<Livre> findByDomaine(Domaine domaine);
    List<Livre> findByNiveau(Niveau niveau);
    List<Livre> findByDomaineAndNiveau(Domaine domaine, Niveau niveau);
    List<Livre> findActifs();
    List<Livre> findDisponibles();
    List<Livre> searchByTitre(String titre);
    List<Livre> searchByAuteur(String auteur);
}