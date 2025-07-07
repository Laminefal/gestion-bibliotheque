package com.bibliotheque.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "bibliothecaires")
@PrimaryKeyJoinColumn(name = "utilisateur_id")
public class Bibliothecaire extends Utilisateur {
    
    @NotBlank(message = "L'adresse est obligatoire")
    @Column(name = "adresse", nullable = false, columnDefinition = "TEXT")
    private String adresse;
    
    @NotNull(message = "La date de recrutement est obligatoire")
    @Column(name = "date_recrutement", nullable = false)
    private LocalDate dateRecrutement;
    
    @Column(name = "specialite")
    private String specialite;
    
    @Column(name = "telephone")
    private String telephone;
    
    // Constructeurs
    public Bibliothecaire() {
        super();
        setRole(Role.BIBLIOTHECAIRE);
    }
    
    public Bibliothecaire(String nomUtilisateur, String motDePasse, String prenom, String nom, 
                         String email, String adresse, LocalDate dateRecrutement) {
        super(nomUtilisateur, motDePasse, prenom, nom, email, Role.BIBLIOTHECAIRE);
        this.adresse = adresse;
        this.dateRecrutement = dateRecrutement;
    }
    
    // Getters et Setters
    public String getAdresse() {
        return adresse;
    }
    
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    public LocalDate getDateRecrutement() {
        return dateRecrutement;
    }
    
    public void setDateRecrutement(LocalDate dateRecrutement) {
        this.dateRecrutement = dateRecrutement;
    }
    
    public String getSpecialite() {
        return specialite;
    }
    
    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    @Override
    public String toString() {
        return "Bibliothecaire{" +
                "id=" + getId() +
                ", nomUtilisateur='" + getNomUtilisateur() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", nom='" + getNom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", adresse='" + adresse + '\'' +
                ", dateRecrutement=" + dateRecrutement +
                ", specialite='" + specialite + '\'' +
                ", telephone='" + telephone + '\'' +
                ", actif=" + isActif() +
                '}';
    }
} 