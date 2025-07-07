package com.bibliotheque.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "exemplaires")
public class Exemplaire {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le code exemplaire est obligatoire")
    @Size(max = 50, message = "Le code exemplaire ne peut pas dépasser 50 caractères")
    @Column(name = "code_exemplaire", unique = true, nullable = false)
    private String codeExemplaire;
    
    @NotNull(message = "Le livre est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livre_id", nullable = false)
    private Livre livre;
    
    @Column(name = "etat")
    private String etat = "Bon"; // Bon, Moyen, Mauvais
    
    @Column(name = "localisation")
    private String localisation;
    
    @Column(name = "date_acquisition")
    private LocalDateTime dateAcquisition;
    
    @Column(name = "disponible")
    private boolean disponible = true;
    
    @Column(name = "actif")
    private boolean actif = true;
    
    @OneToMany(mappedBy = "exemplaire", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Emprunt> emprunts = new ArrayList<>();
    
    // Constructeurs
    public Exemplaire() {
        this.dateAcquisition = LocalDateTime.now();
    }
    
    public Exemplaire(String codeExemplaire, Livre livre) {
        this();
        this.codeExemplaire = codeExemplaire;
        this.livre = livre;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCodeExemplaire() {
        return codeExemplaire;
    }
    
    public void setCodeExemplaire(String codeExemplaire) {
        this.codeExemplaire = codeExemplaire;
    }
    
    public Livre getLivre() {
        return livre;
    }
    
    public void setLivre(Livre livre) {
        this.livre = livre;
    }
    
    public String getEtat() {
        return etat;
    }
    
    public void setEtat(String etat) {
        this.etat = etat;
    }
    
    public String getLocalisation() {
        return localisation;
    }
    
    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }
    
    public LocalDateTime getDateAcquisition() {
        return dateAcquisition;
    }
    
    public void setDateAcquisition(LocalDateTime dateAcquisition) {
        this.dateAcquisition = dateAcquisition;
    }
    
    public boolean isDisponible() {
        return disponible;
    }
    
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
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
    public boolean peutEtreEmprunte() {
        return actif && disponible;
    }
    
    public Emprunt getEmpruntEnCours() {
        return emprunts.stream()
                .filter(e -> e.getDateRetourEffective() == null)
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public String toString() {
        return "Exemplaire{" +
                "id=" + id +
                ", codeExemplaire='" + codeExemplaire + '\'' +
                ", livre=" + (livre != null ? livre.getTitre() : "null") +
                ", etat='" + etat + '\'' +
                ", localisation='" + localisation + '\'' +
                ", disponible=" + disponible +
                ", actif=" + actif +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exemplaire that = (Exemplaire) o;
        return id != null && id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    // Méthode utilitaire pour affichage formaté de la date dans la JSP
    public String getDateAcquisitionFormatted() {
        if (dateAcquisition == null) return "";
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateAcquisition.format(formatter);
    }
}