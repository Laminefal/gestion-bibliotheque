package com.bibliotheque.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "livres")
public class Livre {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "L'identifiant du livre est obligatoire")
    @Size(max = 50, message = "L'identifiant ne peut pas dépasser 50 caractères")
    @Column(name = "identifiant", unique = true, nullable = false)
    private String identifiant;
    
    @NotBlank(message = "Le titre est obligatoire")
    @Size(max = 500, message = "Le titre ne peut pas dépasser 500 caractères")
    @Column(name = "titre", nullable = false)
    private String titre;
    
    @NotBlank(message = "Les auteurs sont obligatoires")
    @Size(max = 300, message = "Les auteurs ne peuvent pas dépasser 300 caractères")
    @Column(name = "auteurs", nullable = false)
    private String auteurs;
    
    @NotNull(message = "L'année de publication est obligatoire")
    @Positive(message = "L'année de publication doit être positive")
    @Column(name = "annee_publication", nullable = false)
    private Integer anneePublication;
    
    @NotNull(message = "Le domaine est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(name = "domaine", nullable = false)
    private Domaine domaine;
    
    @NotNull(message = "Le niveau requis est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(name = "niveau_requis", nullable = false)
    private Niveau niveauRequis;
    
    @Column(name = "isbn")
    private String isbn;
    
    @Column(name = "editeur")
    private String editeur;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "nombre_pages")
    private Integer nombrePages;
    
    @Column(name = "date_ajout")
    private LocalDateTime dateAjout;
    
    @Column(name = "actif")
    private boolean actif = true;
    
    @OneToMany(mappedBy = "livre", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Exemplaire> exemplaires = new ArrayList<>();
    
    // Constructeur par défaut
    public Livre() {
        this.dateAjout = LocalDateTime.now();
    }
    
    // Constructeur complet pour l'insertion automatique
    public Livre(String titre, String auteurs, Integer anneePublication, Domaine domaine, Niveau niveauRequis, boolean actif, String isbn, String identifiant) {
        this(); // initialise dateAjout
        this.titre = titre;
        this.auteurs = auteurs;
        this.anneePublication = anneePublication;
        this.domaine = domaine;
        this.niveauRequis = niveauRequis;
        this.actif = actif;
        this.isbn = isbn;
        this.identifiant = identifiant;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getIdentifiant() {
        return identifiant;
    }
    
    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }
    
    public String getTitre() {
        return titre;
    }
    
    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    public String getAuteurs() {
        return auteurs;
    }
    
    public void setAuteurs(String auteurs) {
        this.auteurs = auteurs;
    }
    
    public Integer getAnneePublication() {
        return anneePublication;
    }
    
    public void setAnneePublication(Integer anneePublication) {
        this.anneePublication = anneePublication;
    }
    
    public Domaine getDomaine() {
        return domaine;
    }
    
    public void setDomaine(Domaine domaine) {
        this.domaine = domaine;
    }
    
    public Niveau getNiveauRequis() {
        return niveauRequis;
    }
    
    public void setNiveauRequis(Niveau niveauRequis) {
        this.niveauRequis = niveauRequis;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public String getEditeur() {
        return editeur;
    }
    
    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getNombrePages() {
        return nombrePages;
    }
    
    public void setNombrePages(Integer nombrePages) {
        this.nombrePages = nombrePages;
    }
    
    public LocalDateTime getDateAjout() {
        return dateAjout;
    }
    
    public void setDateAjout(LocalDateTime dateAjout) {
        this.dateAjout = dateAjout;
    }
    
    public boolean isActif() {
        return actif;
    }
    
    public void setActif(boolean actif) {
        this.actif = actif;
    }
    
    public List<Exemplaire> getExemplaires() {
        return exemplaires;
    }
    
    public void setExemplaires(List<Exemplaire> exemplaires) {
        this.exemplaires = exemplaires;
    }
    
    // Méthodes utilitaires
    public int getNombreExemplairesDisponibles() {
        if (exemplaires == null) return 0;
        return (int) exemplaires.stream().filter(Exemplaire::isDisponible).count();
    }
    
    public boolean estDisponible() {
        return isActif() && getNombreExemplairesDisponibles() > 0;
    }
    
    @Override
    public String toString() {
        return "Livre{" +
                "id=" + id +
                ", identifiant='" + identifiant + '\'' +
                ", titre='" + titre + '\'' +
                ", auteurs='" + auteurs + '\'' +
                ", anneePublication=" + anneePublication +
                ", domaine=" + domaine +
                ", niveauRequis=" + niveauRequis +
                ", actif=" + actif +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livre livre = (Livre) o;
        return id != null && id.equals(livre.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
} 