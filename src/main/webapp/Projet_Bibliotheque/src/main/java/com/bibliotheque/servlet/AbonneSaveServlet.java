package com.bibliotheque.servlet;

import com.bibliotheque.entity.Abonne;
import com.bibliotheque.entity.StatutAbonne;
import com.bibliotheque.service.AbonneService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/abonne-save")
public class AbonneSaveServlet extends HttpServlet {
    private AbonneService abonneService;

    @Override
    public void init() throws ServletException {
        abonneService = new AbonneService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String numeroAbonnement = request.getParameter("numeroAbonnement");
            String prenom = request.getParameter("prenom");
            String nom = request.getParameter("nom");
            String email = request.getParameter("email");
            String institution = request.getParameter("institution");
            String telephone = request.getParameter("telephone");
            String adresse = request.getParameter("adresse");
            String statut = request.getParameter("statut");

            // Validation côté serveur
            if (numeroAbonnement == null || numeroAbonnement.trim().isEmpty() ||
                prenom == null || prenom.trim().isEmpty() ||
                nom == null || nom.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                institution == null || institution.trim().isEmpty() ||
                statut == null || statut.trim().isEmpty()) {
                throw new IllegalArgumentException("Tous les champs obligatoires doivent être remplis.");
            }

            Abonne abonnes = new Abonne();
            abonnes.setNumeroAbonnement(numeroAbonnement.trim());
            abonnes.setPrenom(prenom.trim());
            abonnes.setNom(nom.trim());
            abonnes.setEmail(email.trim());
            abonnes.setInstitution(institution.trim());
            abonnes.setTelephone(telephone != null ? telephone.trim() : null);
            abonnes.setAdresse(adresse != null ? adresse.trim() : null);
            abonnes.setStatut(StatutAbonne.valueOf(statut));

            abonneService.save(abonnes);

            response.sendRedirect(request.getContextPath() + "/abonnes");
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de la sauvegarde : " + e.getMessage());
            // Pour garder les valeurs déjà saisies
            Abonne abonnes = new Abonne();
            abonnes.setNumeroAbonnement(request.getParameter("numeroAbonnement"));
            abonnes.setPrenom(request.getParameter("prenom"));
            abonnes.setNom(request.getParameter("nom"));
            abonnes.setEmail(request.getParameter("email"));
            abonnes.setInstitution(request.getParameter("institution"));
            abonnes.setTelephone(request.getParameter("telephone"));
            abonnes.setAdresse(request.getParameter("adresse"));
            request.setAttribute("abonne", abonnes);
            request.getRequestDispatcher("abonn-form.jsp").forward(request, response);
        }
    }
}