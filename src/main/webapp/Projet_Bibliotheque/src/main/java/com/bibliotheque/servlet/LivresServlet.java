package com.bibliotheque.servlet;

import com.bibliotheque.entity.Livre;
import com.bibliotheque.entity.Domaine;
import com.bibliotheque.entity.Niveau;
import com.bibliotheque.service.LivreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet({"/livres", "/admin/livres", "/bibliothecaire/livres"})
public class LivresServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private LivreService livreService;
    
    @Override
    public void init() throws ServletException {
        livreService = new LivreService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Récupération des paramètres de filtrage
            String searchTerm = request.getParameter("search");
            String domaineParam = request.getParameter("domaineFilter");
            String niveauParam = request.getParameter("niveauFilter");
            String disponibiliteParam = request.getParameter("disponibilite");
            String anneeParam = request.getParameter("annee");
            
            List<Livre> livres;
            
            // Application des filtres
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                // Recherche par titre ou auteur
                livres = livreService.searchByTitreOrAuteur(searchTerm);
            } else if (domaineParam != null && !domaineParam.trim().isEmpty()) {
                try {
                    Domaine domaine = Domaine.valueOf(domaineParam);
                    if (niveauParam != null && !niveauParam.trim().isEmpty()) {
                        try {
                            Niveau niveau = Niveau.valueOf(niveauParam);
                            livres = livreService.findByDomaineAndNiveau(domaine, niveau);
                        } catch (IllegalArgumentException e) {
                            livres = livreService.findByDomaine(domaine);
                        }
                    } else {
                        livres = livreService.findByDomaine(domaine);
                    }
                } catch (IllegalArgumentException e) {
                    livres = livreService.findAll();
                }
            } else if (niveauParam != null && !niveauParam.trim().isEmpty()) {
                try {
                    Niveau niveau = Niveau.valueOf(niveauParam);
                    livres = livreService.findByNiveau(niveau);
                } catch (IllegalArgumentException e) {
                    livres = livreService.findAll();
                }
            } else if (anneeParam != null && !anneeParam.trim().isEmpty()) {
                try {
                    Integer annee = Integer.parseInt(anneeParam);
                    livres = livreService.findByAnneePublication(annee);
                } catch (NumberFormatException e) {
                    livres = livreService.findAll();
                }
            } else if ("disponible".equals(disponibiliteParam)) {
                livres = livreService.findDisponibles();
            } else if ("emprunte".equals(disponibiliteParam)) {
                livres = livreService.findIndisponibles();
            } else if ("actif".equals(disponibiliteParam)) {
                livres = livreService.findActifs();
            } else {
                livres = livreService.findAll();
            }
            
            // Ajout des données à la requête
            request.setAttribute("livres", livres);
            request.setAttribute("totalLivres", livres.size());
            
            // Calcul des statistiques simplifiées (sans accéder aux exemplaires)
            long actifs = livres.stream().filter(Livre::isActif).count();
            
            request.setAttribute("statDisponibles", actifs); // Temporairement, on utilise les actifs
            request.setAttribute("statTotalExemplaires", 0); // À calculer différemment si nécessaire
            request.setAttribute("statActifs", actifs);
            
            // Conservation des paramètres de filtrage pour l'affichage
            request.setAttribute("searchTerm", searchTerm);
            request.setAttribute("domaineFilter", domaineParam);
            request.setAttribute("niveauFilter", niveauParam);
            request.setAttribute("disponibiliteFilter", disponibiliteParam);
            request.setAttribute("anneeFilter", anneeParam);
            
            // Déterminer le contexte et forward vers la page JSP appropriée
            String requestURI = request.getRequestURI();
            if (requestURI.contains("/bibliothecaire/")) {
                // Contexte bibliothécaire - utiliser la page JSP dans le répertoire bibliothecaire
                request.getRequestDispatcher("/bibliothecaire/livres.jsp").forward(request, response);
            } else {
                // Contexte général - utiliser la page JSP principale
                request.getRequestDispatcher("/livres.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            // Gestion des erreurs avec plus de détails
            e.printStackTrace(); // Pour le debug
            request.setAttribute("error", "Erreur lors du chargement des livres: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/error/500.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirection vers GET pour éviter la duplication de soumission
        doGet(request, response);
    }
}