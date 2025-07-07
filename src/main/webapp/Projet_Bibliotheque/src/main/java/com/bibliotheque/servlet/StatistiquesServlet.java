package com.bibliotheque.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.bibliotheque.entity.*;
import com.bibliotheque.service.*;

@WebServlet({"/admin/statistiques", "/bibliothecaire/statistiques"})
public class StatistiquesServlet extends HttpServlet {

    private AbonneService abonneService = new AbonneService();
    private LivreService livreService = new LivreService();
    private EmpruntService empruntService = new EmpruntService();
    private BibliothecaireService bibliothecaireService = new BibliothecaireService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Statistiques générales
            List<Abonne> abonnes = abonneService.findAll();
            List<Livre> livres = livreService.findAll();
            List<Emprunt> emprunts = empruntService.findAll();
            List<Bibliothecaire> bibliothecaires = bibliothecaireService.findAll();

            int abonnesCount = abonnes.size();
            int livresCount = livres.size();
            int empruntsActifsCount = (int) emprunts.stream()
                    .filter(e -> e.getStatut() == StatutEmprunt.EN_COURS)
                    .count();
            int bibliothecairesCount = bibliothecaires.size();

            // Statistiques par domaine
            Map<String, Long> statsParDomaine = livres.stream()
                    .collect(Collectors.groupingBy(
                        l -> l.getDomaine() != null ? l.getDomaine().getNom() : "Inconnu",
                        Collectors.counting()
                    ));

            // Statistiques par niveau
            Map<String, Long> statsParNiveau = livres.stream()
                    .collect(Collectors.groupingBy(
                        l -> l.getNiveauRequis() != null ? l.getNiveauRequis().name() : "Inconnu",
                        Collectors.counting()
                    ));

            // Emprunts par mois (format AAAA-MM)
            Map<String, Long> empruntsParMois = emprunts.stream()
                    .filter(e -> e.getDateEmprunt() != null)
                    .collect(Collectors.groupingBy(
                        e -> e.getDateEmprunt().getYear() + "-" +
                             String.format("%02d", e.getDateEmprunt().getMonthValue()),
                        Collectors.counting()
                    ));

            // Ajout des données à la requête
            request.setAttribute("abonnesCount", abonnesCount);
            request.setAttribute("livresCount", livresCount);
            request.setAttribute("empruntsActifsCount", empruntsActifsCount);
            request.setAttribute("bibliothecairesCount", bibliothecairesCount);
            request.setAttribute("statsParDomaine", statsParDomaine);
            request.setAttribute("statsParNiveau", statsParNiveau);
            request.setAttribute("empruntsParMois", empruntsParMois);

            // Détecter si c'est pour admin ou bibliothécaire
            String requestURI = request.getRequestURI();
            String targetPage;
            
            if (requestURI.contains("/bibliothecaire/")) {
                targetPage = "/bibliothecaire/statistiques.jsp";
            } else {
                targetPage = "/admin/statistiques.jsp";
            }
            
            // Forward vers la page des statistiques appropriée
            request.getRequestDispatcher(targetPage).forward(request, response);

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