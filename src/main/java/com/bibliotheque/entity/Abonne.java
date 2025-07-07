package com.bibliotheque.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "abonnes")
public class Abonne {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le numéro d'abonnement est obligatoire")
    @Size(max = 20, message = "Le numéro d'abonnement ne peut pas dépasser 20 caractères")
    @Column(name = "numero_abonnement", unique = true, nullable = false)
    private String numeroAbonnement;
    
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 100, message = "Le prénom ne peut pas dépasser 100 caractères")
    @Column(name = "prenom", nullable = false)
    private String prenom;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    @Column(name = "nom", nullable = false)
    private String nom;
    
    @NotNull(message = "Le statut est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutAbonne statut;
    
    @NotBlank(message = "L'institution de rattachement est obligatoire")
    @Size(max = 200, message = "L'institution ne peut pas dépasser 200 caractères")
    @Column(name = "institution", nullable = false)
    private String institution;
    
    @Email(message = "L'adresse email doit être valide")
    @NotBlank(message = "L'adresse email est obligatoire")
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    
    @Column(name = "telephone")
    private String telephone;
    
    @Column(name = "adresse", columnDefinition = "TEXT")
    private String adresse;
    
    @Column(name = "date_inscription")
    private LocalDateTime dateInscription;
    
    @Column(name = "actif")
    private boolean actif = true;
    
    @OneToMany(mappedBy = "abonne", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Emprunt> emprunts = new ArrayList<>();
    
    // Constructeurs
    public Abonne() {
        this.dateInscription = LocalDateTime.now();
    }
    
    public Abonne(String numeroAbonnement, String prenom, String nom, StatutAbonne statut, 
                  String institution, String email) {
        this();
        this.numeroAbonnement = numeroAbonnement;
        this.prenom = prenom;
        this.nom = nom;
        this.statut = statut;
        this.institution = institution;
        this.email = email;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNumeroAbonnement() {
        return numeroAbonnement;
    }
    
    public void setNumeroAbonnement(String numeroAbonnement) {
        this.numeroAbonnement = numeroAbonnement;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public StatutAbonne getStatut() {
        return statut;
    }
    
    public void setStatut(StatutAbonne statut) {
        this.statut = statut;
    }
    
    public String getInstitution() {
        return institution;
    }
    
    public void setInstitution(String institution) {
        this.institution = institution;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public String getAdresse() {
        return adresse;
    }
    
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    public LocalDateTime getDateInscription() {
        return dateInscription;
    }
    
    public void setDateInscription(LocalDateTime dateInscription) {
        this.dateInscription = dateInscription;
    }
    
    public boolean isActif() {
        return actif;
    }
    
    public void setActif(boolean actif) {
        this.actif = actif;
    }
    
    public List<Emprunt> getEmprunts() {
        return emprunts;
    }
    
    public void setEmprunts(List<Emprunt> emprunts) {
        this.emprunts = emprunts;
    }
    
    // Méthodes utilitaires
    public String getNomComplet() {
        return prenom + " " + nom;
    }
    
    public int getNombreEmpruntsEnCours() {
        return (int) emprunts.stream()
                .filter(e -> e.getDateRetourEffective() == null)
                .count();
    }
    
    public boolean peutEmprunter() {
        return actif && getNombreEmpruntsEnCours() < 5; // Limite de 5 emprunts
    }
    
    @Override
    public String toString() {
        return "Abonne{" +
                "id=" + id +
                ", numeroAbonnement='" + numeroAbonnement + '\'' +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", statut=" + statut +
                ", institution='" + institution + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", actif=" + actif +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Abonne abonne = (Abonne) o;
        return id != null && id.equals(abonne.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
} 