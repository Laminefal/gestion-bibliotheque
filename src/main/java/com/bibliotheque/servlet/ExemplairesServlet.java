package com.bibliotheque.servlet;

import com.bibliotheque.entity.Exemplaire;
import com.bibliotheque.entity.Livre;
import com.bibliotheque.entity.Domaine;
import com.bibliotheque.entity.Niveau;
import com.bibliotheque.service.ExemplaireService;
import com.bibliotheque.service.LivreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet({"/exemplaires", "/admin/exemplaires", "/bibliothecaire/exemplaires"})
public class ExemplairesServlet extends HttpServlet {
    private ExemplaireService exemplaireService;
    private LivreService livreService;

    @Override
    public void init() throws ServletException {
        exemplaireService = new ExemplaireService();
        livreService = new LivreService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupération des paramètres de filtrage
        String searchTerm = request.getParameter("search");
        String etatFilter = request.getParameter("etatFilter");
        String disponibilite = request.getParameter("disponibilite");
        String actif = request.getParameter("actif");

        List<Exemplaire> exemplaires;

        // Application des filtres
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            exemplaires = exemplaireService.searchByCodeOrLivre(searchTerm.trim());
        } else if (etatFilter != null && !etatFilter.trim().isEmpty()) {
            exemplaires = exemplaireService.findByEtat(etatFilter);
        } else if ("disponible".equals(disponibilite)) {
            exemplaires = exemplaireService.findDisponibles();
        } else if ("emprunte".equals(disponibilite)) {
            exemplaires = exemplaireService.findIndisponibles();
        } else if ("actif".equals(actif)) {
            exemplaires = exemplaireService.findActifs();
        } else if ("inactif".equals(actif)) {
            exemplaires = exemplaireService.findInactifs();
        } else {
            exemplaires = exemplaireService.findAll();
        }

        // Calcul des statistiques
        long totalExemplaires = exemplaireService.count();
        long statDisponibles = exemplaireService.countDisponibles();
        long statEmpruntes = exemplaireService.countIndisponibles();
        long statActifs = exemplaireService.countActifs();

        // Ajout des données à la requête
        request.setAttribute("exemplaires", exemplaires);
        request.setAttribute("totalExemplaires", totalExemplaires);
        request.setAttribute("statDisponibles", statDisponibles);
        request.setAttribute("statEmpruntes", statEmpruntes);
        request.setAttribute("statActifs", statActifs);

        // Conservation des paramètres de filtrage pour l'affichage
        request.setAttribute("searchTerm", searchTerm);
        request.setAttribute("etatFilter", etatFilter);
        request.setAttribute("disponibilite", disponibilite);
        request.setAttribute("actif", actif);

        request.getRequestDispatcher("/exemplaires.jsp").forward(request, response);
    }
}