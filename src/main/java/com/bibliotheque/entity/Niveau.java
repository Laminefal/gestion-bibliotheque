package com.bibliotheque.entity;

public enum Niveau {
    DEBUTANT("Débutant"),
    INTERMEDIAIRE("Intermédiaire"),
    AVANCE("Avancé"),
    EXPERT("Expert"),
    TOUS_NIVEAUX("Tous niveaux");
    
    private final String libelle;
    
    Niveau(String libelle) {
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