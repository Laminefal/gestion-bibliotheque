package com.bibliotheque.entity;

public enum StatutAbonne {
    ETUDIANT("Étudiant"),
    ENSEIGNANT("Enseignant"),
    PERSONNEL("Personnel");
    
    private final String libelle;
    
    StatutAbonne(String libelle) {
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