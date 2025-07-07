package com.bibliotheque.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "emprunts")
public class Emprunt {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "L'abonné est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "abonne_id", nullable = false)
    private Abonne abonne;
    
    @NotNull(message = "L'exemplaire est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exemplaire_id", nullable = false)
    private Exemplaire exemplaire;
    
    @NotNull(message = "Le bibliothécaire est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bibliothecaire_id", nullable = false)
    private Bibliothecaire bibliothecaire;
    
    @NotNull(message = "La date d'emprunt est obligatoire")
    @Column(name = "date_emprunt", nullable = false)
    private LocalDateTime dateEmprunt;
    
    @NotNull(message = "La date de retour prévue est obligatoire")
    @Column(name = "date_retour_prevue", nullable = false)
    private LocalDate dateRetourPrevue;
    
    @Column(name = "date_retour_effective")
    private LocalDateTime dateRetourEffective;
    
    @Column(name = "prolongations")
    private int prolongations = 0;
    
    @Column(name = "commentaires")
    private String commentaires;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private StatutEmprunt statut = StatutEmprunt.EN_COURS;
    
    // Constructeurs
    public Emprunt() {
        this.dateEmprunt = LocalDateTime.now();
    }
    
    public Emprunt(Abonne abonne, Exemplaire exemplaire, Bibliothecaire bibliothecaire, 
                   LocalDate dateRetourPrevue) {
        this();
        this.abonne = abonne;
        this.exemplaire = exemplaire;
        this.bibliothecaire = bibliothecaire;
        this.dateRetourPrevue = dateRetourPrevue;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Abonne getAbonne() {
        return abonne;
    }
    
    public void setAbonne(Abonne abonne) {
        this.abonne = abonne;
    }
    
    public Exemplaire getExemplaire() {
        return exemplaire;
    }
    
    public void setExemplaire(Exemplaire exemplaire) {
        this.exemplaire = exemplaire;
    }
    
    public Bibliothecaire getBibliothecaire() {
        return bibliothecaire;
    }
    
    public void setBibliothecaire(Bibliothecaire bibliothecaire) {
        this.bibliothecaire = bibliothecaire;
    }
    
    public LocalDateTime getDateEmprunt() {
        return dateEmprunt;
    }
    
    public void setDateEmprunt(LocalDateTime dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }
    
    public LocalDate getDateRetourPrevue() {
        return dateRetourPrevue;
    }
    
    public void setDateRetourPrevue(LocalDate dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }
    
    public LocalDateTime getDateRetourEffective() {
        return dateRetourEffective;
    }
    
    public void setDateRetourEffective(LocalDateTime dateRetourEffective) {
        this.dateRetourEffective = dateRetourEffective;
    }
    
    public int getProlongations() {
        return prolongations;
    }
    
    public void setProlongations(int prolongations) {
        this.prolongations = prolongations;
    }
    
    public String getCommentaires() {
        return commentaires;
    }
    
    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }
    
    public StatutEmprunt getStatut() {
        return statut;
    }
    
    public void setStatut(StatutEmprunt statut) {
        this.statut = statut;
    }
    
    // Méthodes utilitaires
    public boolean estEnRetard() {
        return dateRetourEffective == null && 
               LocalDate.now().isAfter(dateRetourPrevue);
    }
    
    public boolean estTermine() {
        return dateRetourEffective != null;
    }
    
    public long getJoursDeRetard() {
        if (dateRetourEffective != null) {
            return java.time.temporal.ChronoUnit.DAYS.between(
                dateRetourPrevue, dateRetourEffective.toLocalDate());
        } else if (estEnRetard()) {
            return java.time.temporal.ChronoUnit.DAYS.between(
                dateRetourPrevue, LocalDate.now());
        }
        return 0;
    }
    
    public boolean peutEtreProlonge() {
        return !estTermine() && !estEnRetard() && prolongations < 2;
    }
    
    public void prolonger() {
        if (peutEtreProlonge()) {
            this.dateRetourPrevue = this.dateRetourPrevue.plusDays(15);
            this.prolongations++;
        }
    }
    
    public void retourner() {
        this.dateRetourEffective = LocalDateTime.now();
        this.statut = StatutEmprunt.RETOURNE;
        this.exemplaire.setDisponible(true);
    }
    
    @Override
    public String toString() {
        return "Emprunt{" +
                "id=" + id +
                ", abonne=" + (abonne != null ? abonne.getNomComplet() : "null") +
                ", exemplaire=" + (exemplaire != null ? exemplaire.getCodeExemplaire() : "null") +
                ", dateEmprunt=" + dateEmprunt +
                ", dateRetourPrevue=" + dateRetourPrevue +
                ", dateRetourEffective=" + dateRetourEffective +
                ", statut=" + statut +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprunt emprunt = (Emprunt) o;
        return id != null && id.equals(emprunt.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    // Méthodes utilitaires pour affichage formaté des dates dans la JSP
    public String getDateEmpruntFormatted() {
        if (dateEmprunt == null) return "";
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateEmprunt.format(formatter);
    }

    public String getDateRetourPrevueFormatted() {
        if (dateRetourPrevue == null) return "";
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateRetourPrevue.format(formatter);
    }

    public String getDateRetourEffectiveFormatted() {
        if (dateRetourEffective == null) return "";
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateRetourEffective.format(formatter);
    }
}