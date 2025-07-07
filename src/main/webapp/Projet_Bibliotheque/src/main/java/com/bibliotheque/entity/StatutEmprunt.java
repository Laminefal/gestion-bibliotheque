package com.bibliotheque.entity;

public enum StatutEmprunt {
    EN_COURS("En cours"),
    RETOURNE("Retourné"),
    EN_RETARD("En retard"),
    PERDU("Perdu");
    
    private final String libelle;
    
    StatutEmprunt(String libelle) {
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