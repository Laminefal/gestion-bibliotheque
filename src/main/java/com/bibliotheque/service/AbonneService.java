package com.bibliotheque.service;

import com.bibliotheque.dao.AbonneDAO;
import com.bibliotheque.dao.impl.AbonneDAOImpl;
import com.bibliotheque.entity.Abonne;
import com.bibliotheque.entity.StatutAbonne;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AbonneService {
    private AbonneDAO abonneDAO;

    public AbonneService() {
        this.abonneDAO = new AbonneDAOImpl();
    }

    public List<Abonne> findAll() {
        List<Abonne> abonnes = abonneDAO.findAll();
        System.out.println("AbonneService.findAll() - Nombre d'abonnés trouvés : " + abonnes.size());
        for (Abonne abonne : abonnes) {
            System.out.println("  - " + abonne.getNom() + " " + abonne.getPrenom() + " (ID: " + abonne.getId() + ")");
        }
        return abonnes;
    }

    public Optional<Abonne> findById(Long id) {
        return abonneDAO.findById(id);
    }

    public Abonne save(Abonne abonne) {
        return abonneDAO.save(abonne);
    }

    public Abonne update(Abonne abonne) {
        return abonneDAO.update(abonne);
    }

    public void delete(Long id) {
        abonneDAO.deleteById(id);
    }

    public Optional<Abonne> findByNumeroAbonnement(String numeroAbonnement) {
        return abonneDAO.findByNumeroAbonnement(numeroAbonnement);
    }

    public Optional<Abonne> findByEmail(String email) {
        return abonneDAO.findByEmail(email);
    }

    public List<Abonne> findByStatut(String statut) {
        try {
            StatutAbonne statutEnum = StatutAbonne.valueOf(statut.toUpperCase());
            return abonneDAO.findByStatut(statut);
        } catch (IllegalArgumentException e) {
            // Si le statut n'est pas valide, retourner une liste vide
            return List.of();
        }
    }

    public List<Abonne> findActifs() {
        return abonneDAO.findActifs();
    }

    public List<Abonne> searchAbonnes(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return findAll();
        }
        
        String term = searchTerm.toLowerCase().trim();
        return findAll().stream()
                .filter(abonne -> 
                    abonne.getNom().toLowerCase().contains(term) ||
                    abonne.getPrenom().toLowerCase().contains(term) ||
                    abonne.getEmail().toLowerCase().contains(term) ||
                    abonne.getNumeroAbonnement().toLowerCase().contains(term) ||
                    abonne.getInstitution().toLowerCase().contains(term)
                )
                .collect(Collectors.toList());
    }

    public boolean existsByNumeroAbonnement(String numeroAbonnement) {
        return abonneDAO.existsByNumeroAbonnement(numeroAbonnement);
    }

    public boolean existsByEmail(String email) {
        return abonneDAO.existsByEmail(email);
    }

    public List<Abonne> findLastInscrits(int limit) {
        return abonneDAO.findLastInscrits(limit);
    }

    // Ajoute ici d'autres méthodes spécifiques si besoin, par exemple :
    // public Optional<Abonne> findByNumeroAbonnement(String numero) { ... }
}