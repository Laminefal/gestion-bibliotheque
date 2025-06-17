package entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Bibliothecaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String prenom;
    private String nom;

    @Temporal(TemporalType.DATE)
    private Date dateRecrutement;

    private String adresse;
    private String email;

    @Column(unique = true, nullable = false, length = 50)
    private String login;

    @Column(nullable = false, length = 255)
    private String password;

    // Constructeur par défaut obligatoire pour JPA
    public Bibliothecaire() {
    }

    // Constructeur pratique pour les tests
    public Bibliothecaire(String prenom, String nom, String login, String password) {
        this.prenom = prenom;
        this.nom = nom;
        this.login = login;
        this.password = password;
        this.dateRecrutement = new Date(); // Date courante par défaut
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDateRecrutement() {
        return dateRecrutement;
    }

    public void setDateRecrutement(Date dateRecrutement) {
        this.dateRecrutement = dateRecrutement;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Méthode pratique pour vérifier le mot de passe
    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    @Override
    public String toString() {
        return "Bibliothecaire [id=" + id + ", prenom=" + prenom + ", nom=" + nom + ", login=" + login + "]";
    }
}