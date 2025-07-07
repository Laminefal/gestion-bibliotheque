package com.bibliotheque.servlet;

import com.bibliotheque.entity.Bibliothecaire;
import com.bibliotheque.entity.Role;
import com.bibliotheque.service.BibliothecaireService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/admin/bibliothecaire-save")
public class BibliothecaireSaveServlet extends HttpServlet {
    private BibliothecaireService service = new BibliothecaireService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String idParam = req.getParameter("id");
        String nomUtilisateur = req.getParameter("nomUtilisateur");
        String motDePasse = req.getParameter("motDePasse");
        String prenom = req.getParameter("prenom");
        String nom = req.getParameter("nom");
        String email = req.getParameter("email");
        String adresse = req.getParameter("adresse");
        String specialite = req.getParameter("specialite");
        String telephone = req.getParameter("telephone");
        String dateRecrutementStr = req.getParameter("dateRecrutement");

        Bibliothecaire biblio = new Bibliothecaire();
        if (idParam != null && !idParam.isEmpty()) {
            biblio.setId(Long.parseLong(idParam));
        }
        biblio.setNomUtilisateur(nomUtilisateur);
        biblio.setMotDePasse(motDePasse);
        biblio.setPrenom(prenom);
        biblio.setNom(nom);
        biblio.setEmail(email);
        biblio.setAdresse(adresse);
        biblio.setSpecialite(specialite);
        biblio.setTelephone(telephone);
        biblio.setRole(Role.BIBLIOTHECAIRE);

        if (dateRecrutementStr != null && !dateRecrutementStr.isEmpty()) {
            biblio.setDateRecrutement(LocalDate.parse(dateRecrutementStr));
        } else {
            // Ici, tu peux soit lever une erreur, soit laisser la validation JPA refuser l'enregistrement
            biblio.setDateRecrutement(null);
        }

        if (service.emailExiste(email, biblio.getId())) {
            req.setAttribute("error", "Cet email est déjà utilisé.");
            // ... renvoyer vers le formulaire avec les valeurs remplies ...
            return;
        }

        service.save(biblio);
        resp.sendRedirect(req.getContextPath() + "/admin/bibliothecaires");
    }
}