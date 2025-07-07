package com.bibliotheque.servlet;

import com.bibliotheque.entity.StatutEmprunt;
import com.bibliotheque.service.AbonneService;
import com.bibliotheque.service.LivreService;
import com.bibliotheque.service.EmpruntService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet({"/bibliothecaire/dashboard"})
public class DashboardServlet extends HttpServlet {

    private AbonneService abonneService = new AbonneService();
    private LivreService livreService = new LivreService();
    private EmpruntService empruntService = new EmpruntService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Calcul des statistiques pour le dashboard
            List<com.bibliotheque.entity.Abonne> abonnes = abonneService.findActifs();
            List<com.bibliotheque.entity.Livre> livres = livreService.findDisponibles();
            List<com.bibliotheque.entity.Emprunt> empruntsEnCours = empruntService.findEnCours();
            List<com.bibliotheque.entity.Emprunt> retards = empruntService.findEnRetard();

            // Ajout des données à la requête
            request.setAttribute("abonnesActifsCount", abonnes.size());
            request.setAttribute("livresDisponiblesCount", livres.size());
            request.setAttribute("empruntsEnCoursCount", empruntsEnCours.size());
            request.setAttribute("retardsCount", retards.size());

            // Forward vers la page du dashboard
            request.getRequestDispatcher("/bibliothecaire/dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors du calcul des statistiques: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/error/500.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
} 