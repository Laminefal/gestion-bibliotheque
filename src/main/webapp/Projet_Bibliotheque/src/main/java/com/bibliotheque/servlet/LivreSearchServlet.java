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
import java.util.stream.Collectors;

@WebServlet("/livre-search")
public class LivreSearchServlet extends HttpServlet {
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
            // Récupération des paramètres de recherche
            String searchTerm = request.getParameter("q");
            String domaineParam = request.getParameter("domaine");
            String niveauParam = request.getParameter("niveau");
            String anneeParam = request.getParameter("annee");
            String disponibiliteParam = request.getParameter("disponibilite");
            
            List<Livre> resultats;
            
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                // Recherche par terme
                resultats = livreService.searchByTitreOrAuteur(searchTerm);
            } else {
                // Recherche par critères
                resultats = livreService.findAll();
            }
            
            // Application des filtres supplémentaires
            if (domaineParam != null && !domaineParam.trim().isEmpty()) {
                try {
                    Domaine domaine = Domaine.valueOf(domaineParam);
                    resultats = resultats.stream()
                            .filter(l -> l.getDomaine() == domaine)
                            .collect(Collectors.toList());
                } catch (IllegalArgumentException e) {
                    // Ignorer le filtre invalide
                }
            }
            
            if (niveauParam != null && !niveauParam.trim().isEmpty()) {
                try {
                    Niveau niveau = Niveau.valueOf(niveauParam);
                    resultats = resultats.stream()
                            .filter(l -> l.getNiveauRequis() == niveau)
                            .collect(Collectors.toList());
                } catch (IllegalArgumentException e) {
                    // Ignorer le filtre invalide
                }
            }
            
            if (anneeParam != null && !anneeParam.trim().isEmpty()) {
                try {
                    Integer annee = Integer.parseInt(anneeParam);
                    resultats = resultats.stream()
                            .filter(l -> l.getAnneePublication().equals(annee))
                            .collect(Collectors.toList());
                } catch (NumberFormatException e) {
                    // Ignorer le filtre invalide
                }
            }
            
            if ("disponible".equals(disponibiliteParam)) {
                resultats = resultats.stream()
                        .filter(Livre::estDisponible)
                        .collect(Collectors.toList());
            } else if ("emprunte".equals(disponibiliteParam)) {
                resultats = resultats.stream()
                        .filter(l -> !l.estDisponible())
                        .collect(Collectors.toList());
            }
            
            // Ajout des résultats à la requête
            request.setAttribute("livres", resultats);
            request.setAttribute("nombreResultats", resultats.size());
            request.setAttribute("searchTerm", searchTerm);
            request.setAttribute("domaineFilter", domaineParam);
            request.setAttribute("niveauFilter", niveauParam);
            request.setAttribute("anneeFilter", anneeParam);
            request.setAttribute("disponibiliteFilter", disponibiliteParam);
            
            // Forward vers la page de résultats
            request.getRequestDispatcher("/livres.jsp").forward(request, response);
            
        } catch (Exception e) {
            // Gestion des erreurs
            request.setAttribute("error", "Erreur lors de la recherche: " + e.getMessage());
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