package com.bibliotheque.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "administrateurs")
@PrimaryKeyJoinColumn(name = "utilisateur_id")
public class Administrateur extends Utilisateur {
    
    @NotBlank(message = "Le département est obligatoire")
    @Size(max = 100, message = "Le département ne peut pas dépasser 100 caractères")
    @Column(name = "departement", nullable = false)
    private String departement;
    
    @Column(name = "niveau_acces")
    private String niveauAcces;
    
    @Column(name = "date_nomination")
    private java.time.LocalDate dateNomination;
    
    // Constructeurs
    public Administrateur() {
        super();
        setRole(Role.ADMIN);
    }
    
    public Administrateur(String nomUtilisateur, String motDePasse, String prenom, String nom, 
                         String email, String departement) {
        super(nomUtilisateur, motDePasse, prenom, nom, email, Role.ADMIN);
        this.departement = departement;
        this.dateNomination = java.time.LocalDate.now();
    }
    
    // Getters et Setters
    public String getDepartement() {
        return departement;
    }
    
    public void setDepartement(String departement) {
        this.departement = departement;
    }
    
    public String getNiveauAcces() {
        return niveauAcces;
    }
    
    public void setNiveauAcces(String niveauAcces) {
        this.niveauAcces = niveauAcces;
    }
    
    public java.time.LocalDate getDateNomination() {
        return dateNomination;
    }
    
    public void setDateNomination(java.time.LocalDate dateNomination) {
        this.dateNomination = dateNomination;
    }
    
    @Override
    public String toString() {
        return "Administrateur{" +
                "id=" + getId() +
                ", nomUtilisateur='" + getNomUtilisateur() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", nom='" + getNom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", departement='" + departement + '\'' +
                ", niveauAcces='" + niveauAcces + '\'' +
                ", dateNomination=" + dateNomination +
                ", actif=" + isActif() +
                '}';
    }
}