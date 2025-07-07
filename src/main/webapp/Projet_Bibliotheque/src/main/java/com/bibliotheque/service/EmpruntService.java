package com.bibliotheque.service;

import com.bibliotheque.dao.EmpruntDAO;
import com.bibliotheque.dao.impl.EmpruntDAOImpl;
import com.bibliotheque.entity.Emprunt;
import com.bibliotheque.entity.Abonne;
import com.bibliotheque.entity.Exemplaire;
import com.bibliotheque.entity.StatutEmprunt;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class EmpruntService {
    private EmpruntDAO empruntDAO;

    public EmpruntService() {
        this.empruntDAO = new EmpruntDAOImpl();
    }

    public List<Emprunt> findAll() {
        return empruntDAO.findAll();
    }

    public Optional<Emprunt> findById(Long id) {
        return empruntDAO.findById(id);
    }

    public Emprunt save(Emprunt e) {
        return empruntDAO.save(e);
    }

    public Emprunt update(Emprunt e) {
        return empruntDAO.update(e);
    }

    public void delete(Long id) {
        empruntDAO.deleteById(id);
    }

    public List<Emprunt> findByAbonne(Abonne abonne) {
        return empruntDAO.findByAbonne(abonne);
    }

    public List<Emprunt> findEnCoursByAbonne(Abonne abonne) {
        return empruntDAO.findEnCoursByAbonne(abonne);
    }

    public List<Emprunt> findByExemplaire(Exemplaire exemplaire) {
        return empruntDAO.findByExemplaire(exemplaire);
    }

    public Emprunt findEnCoursByExemplaire(Exemplaire exemplaire) {
        return empruntDAO.findEnCoursByExemplaire(exemplaire);
    }

    public List<Emprunt> findByStatut(StatutEmprunt statut) {
        return empruntDAO.findByStatut(statut);
    }

    public List<Emprunt> findEnCours() {
        return empruntDAO.findEnCours();
    }

    public List<Emprunt> findEnRetard() {
        return empruntDAO.findEnRetard();
    }

    public List<Emprunt> findByDateRetourPrevue(LocalDate dateRetourPrevue) {
        return empruntDAO.findByDateRetourPrevue(dateRetourPrevue);
    }

    public List<Emprunt> findByDateEmpruntBetween(LocalDate dateDebut, LocalDate dateFin) {
        return empruntDAO.findByDateEmpruntBetween(dateDebut, dateFin);
    }

    public List<Emprunt> findByDateRetourEffectiveBetween(LocalDate dateDebut, LocalDate dateFin) {
        return empruntDAO.findByDateRetourEffectiveBetween(dateDebut, dateFin);
    }

    public long countEnCoursByAbonne(Abonne abonne) {
        return empruntDAO.countEnCoursByAbonne(abonne);
    }

    public long countEnRetard() {
        return empruntDAO.countEnRetard();
    }

    public List<Emprunt> findLastEmprunts(int limit) {
        return empruntDAO.findLastEmprunts(limit);
    }

    // Méthode métier pour retourner un emprunt
    public Emprunt retournerEmprunt(Emprunt emprunt) {
        emprunt.retourner();
        return empruntDAO.update(emprunt);
    }
}