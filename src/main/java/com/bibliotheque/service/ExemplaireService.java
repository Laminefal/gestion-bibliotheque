package com.bibliotheque.service;

import com.bibliotheque.dao.ExemplaireDAO;
import com.bibliotheque.dao.impl.ExemplaireDAOImpl;
import com.bibliotheque.entity.Exemplaire;
import com.bibliotheque.entity.Livre;
import java.util.List;
import java.util.Optional;

public class ExemplaireService {
    private final ExemplaireDAO exemplaireDAO;

    public ExemplaireService() {
        this.exemplaireDAO = new ExemplaireDAOImpl();
    }

    public List<Exemplaire> findAll() {
        return exemplaireDAO.findAll();
    }

    public Optional<Exemplaire> findById(Long id) {
        return exemplaireDAO.findById(id);
    }

    public Exemplaire save(Exemplaire e) {
        return exemplaireDAO.save(e);
    }

    public Exemplaire update(Exemplaire e) {
        return exemplaireDAO.update(e);
    }

    public void delete(Long id) {
        exemplaireDAO.deleteById(id);
    }

    public List<Exemplaire> findByLivre(Livre livre) {
        return exemplaireDAO.findByLivre(livre);
    }

    // Méthodes de filtrage et recherche
    public List<Exemplaire> searchByCodeOrLivre(String searchTerm) {
        return exemplaireDAO.searchByCodeOrLivre(searchTerm);
    }

    public List<Exemplaire> findByEtat(String etat) {
        return exemplaireDAO.findByEtat(etat);
    }

    public List<Exemplaire> findDisponibles() {
        List<Exemplaire> exemplaires = exemplaireDAO.findDisponibles();
        System.out.println("ExemplaireService.findDisponibles() - Nombre d'exemplaires trouvés : " + exemplaires.size());
        for (Exemplaire exemplaire : exemplaires) {
            System.out.println("  - " + exemplaire.getCodeExemplaire() + " (ID: " + exemplaire.getId() + 
                              ", Livre: " + (exemplaire.getLivre() != null ? exemplaire.getLivre().getTitre() : "null") + ")");
        }
        return exemplaires;
    }

    public List<Exemplaire> findIndisponibles() {
        return exemplaireDAO.findIndisponibles();
    }

    public List<Exemplaire> findActifs() {
        return exemplaireDAO.findActifs();
    }

    public List<Exemplaire> findInactifs() {
        return exemplaireDAO.findInactifs();
    }

    // Statistiques
    public long count() {
        return exemplaireDAO.count();
    }

    public long countDisponibles() {
        return exemplaireDAO.countDisponibles();
    }

    public long countIndisponibles() {
        return exemplaireDAO.countIndisponibles();
    }

    public long countActifs() {
        return exemplaireDAO.countActifs();
    }
}