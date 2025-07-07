package com.bibliotheque.service;

import com.bibliotheque.dao.LivreDAO;
import com.bibliotheque.dao.impl.LivreDAOImpl;
import com.bibliotheque.entity.Livre;
import com.bibliotheque.entity.Domaine;
import com.bibliotheque.entity.Niveau;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LivreService {
    private LivreDAO livreDAO;

    public LivreService() {
        this.livreDAO = new LivreDAOImpl();
    }

    /* Méthodes CRUD unifiées */
    public Livre save(Livre livre) {
        return livre.getId() == null ? livreDAO.save(livre) : livreDAO.update(livre);
    }

    public void delete(Long id) {
        livreDAO.deleteById(id);
    }

    /* Méthodes de recherche */
    public List<Livre> findAll() {
        List<Livre> livres = livreDAO.findAll();
        System.out.println("LivreService.findAll() - Nombre de livres trouvés : " + livres.size());
        for (Livre livre : livres) {
            System.out.println("  - " + livre.getTitre() + " (ID: " + livre.getId() + ")");
        }
        return livres;
    }

    public Optional<Livre> findById(Long id) {
        return livreDAO.findById(id);
    }

    /* Méthodes spécifiques */
    public Optional<Livre> findByIdentifiant(String identifiant) {
        return livreDAO.findByIdentifiant(identifiant);
    }

    public Optional<Livre> findByIsbn(String isbn) {
        return livreDAO.findByIsbn(isbn);
    }

    public List<Livre> findByDomaine(Domaine domaine) {
        return livreDAO.findByDomaine(domaine);
    }

    public List<Livre> findByNiveau(Niveau niveau) {
        return livreDAO.findByNiveau(niveau);
    }

    public List<Livre> findByDomaineAndNiveau(Domaine domaine, Niveau niveau) {
        return livreDAO.findByDomaineAndNiveau(domaine, niveau);
    }

    public List<Livre> findActifs() {
        return livreDAO.findActifs();
    }

    public List<Livre> findByAnneePublication(Integer annee) {
        return livreDAO.findByAnneePublication(annee);
    }

    public List<Livre> searchByTitre(String titre) {
        return livreDAO.searchByTitre(titre);
    }

    public List<Livre> searchByAuteur(String auteur) {
        return livreDAO.searchByAuteur(auteur);
    }

    public List<Livre> searchByTitreOrAuteur(String searchTerm) {
        List<Livre> result = searchByTitre(searchTerm);
        result.addAll(searchByAuteur(searchTerm));
        return result.stream().distinct().collect(Collectors.toList());
    }

    public List<Livre> findDisponibles() {
        return livreDAO.findDisponibles();
    }

    public List<Livre> findIndisponibles() {
        return livreDAO.findAll().stream()
                .filter(livre -> !livre.estDisponible())
                .collect(Collectors.toList());
    }

    public boolean existsByIdentifiant(String identifiant) {
        return livreDAO.existsByIdentifiant(identifiant);
    }

    public boolean existsByIsbn(String isbn) {
        return livreDAO.existsByIsbn(isbn);
    }

    public boolean existsById(Long id) {
        return livreDAO.existsById(id);
    }

    public long count() {
        return livreDAO.count();
    }

    /* Méthode pour charger un livre avec ses exemplaires */
    public Optional<Livre> findByIdWithExemplaires(Long id) {
        Optional<Livre> livreOpt = livreDAO.findById(id);
        if (livreOpt.isPresent()) {
            Livre livre = livreOpt.get();
            // Forcer le chargement des exemplaires
            if (livre.getExemplaires() != null) {
                livre.getExemplaires().size();
            }
        }
        return livreOpt;
    }
}