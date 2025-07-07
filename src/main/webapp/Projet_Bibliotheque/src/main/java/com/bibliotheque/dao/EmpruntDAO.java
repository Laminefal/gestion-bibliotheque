package com.bibliotheque.dao;

import com.bibliotheque.entity.Emprunt;
import com.bibliotheque.entity.Abonne;
import com.bibliotheque.entity.Exemplaire;
import com.bibliotheque.entity.StatutEmprunt;
import java.time.LocalDate;
import java.util.List;

public interface EmpruntDAO extends GenericDAO<Emprunt, Long> {
    List<Emprunt> findByAbonne(Abonne abonne);
    List<Emprunt> findEnCoursByAbonne(Abonne abonne);
    List<Emprunt> findByExemplaire(Exemplaire exemplaire);
    Emprunt findEnCoursByExemplaire(Exemplaire exemplaire);
    List<Emprunt> findByStatut(StatutEmprunt statut);
    List<Emprunt> findEnCours();
    List<Emprunt> findEnRetard();
    List<Emprunt> findByDateRetourPrevue(LocalDate dateRetourPrevue);
    List<Emprunt> findByDateEmpruntBetween(LocalDate dateDebut, LocalDate dateFin);
    List<Emprunt> findByDateRetourEffectiveBetween(LocalDate dateDebut, LocalDate dateFin);
    long countEnCoursByAbonne(Abonne abonne);
    long countEnRetard();
    List<Emprunt> findLastEmprunts(int limit);
}