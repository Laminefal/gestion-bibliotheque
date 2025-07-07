package com.bibliotheque.entity;

public enum Role {
    ADMIN("Administrateur"),
    BIBLIOTHECAIRE("Bibliothécaire");
    
    private final String libelle;
    
    Role(String libelle) {
        this.libelle = libelle;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    @Override
    public String toString() {
        return libelle;
    }
} 