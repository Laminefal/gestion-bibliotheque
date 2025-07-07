package com.bibliotheque.entity;

public enum Domaine {
    INFORMATIQUE("Informatique"),
    MATHEMATIQUES("Mathématiques"),
    PHYSIQUE("Physique"),
    CHIMIE("Chimie"),
    BIOLOGIE("Biologie"),
    MEDECINE("Médecine"),
    DROIT("Droit"),
    ECONOMIE("Économie"),
    GESTION("Gestion"),
    LITTERATURE("Littérature"),
    HISTOIRE("Histoire"),
    PHILOSOPHIE("Philosophie"),
    LANGUES("Langues"),
    ARTS("Arts"),
    SPORTS("Sports"),
    AUTRE("Autre");
    
    private final String libelle;
    
    Domaine(String libelle) {
        this.libelle = libelle;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public String getNom() {
        return this.name();
    }
    
    @Override
    public String toString() {
        return libelle;
    }
}