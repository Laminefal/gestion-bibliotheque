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
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/livre-stats")
public class LivreStatsServlet extends HttpServlet {
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
            List<Livre> tousLesLivres = livreService.findAll();
            
            // Statistiques générales
            int totalLivres = tousLesLivres.size();
            int livresActifs = (int) tousLesLivres.stream().filter(Livre::isActif).count();
            int livresDisponibles = (int) tousLesLivres.stream().filter(Livre::estDisponible).count();
            int totalExemplaires = tousLesLivres.stream().mapToInt(l -> l.getExemplaires().size()).sum();
            
            // Statistiques par domaine
            Map<Domaine, Long> statsParDomaine = tousLesLivres.stream()
                    .collect(Collectors.groupingBy(Livre::getDomaine, Collectors.counting()));
            
            // Statistiques par niveau
            Map<Niveau, Long> statsParNiveau = tousLesLivres.stream()
                    .collect(Collectors.groupingBy(Livre::getNiveauRequis, Collectors.counting()));
            
            // Statistiques par année
            Map<Integer, Long> statsParAnnee = tousLesLivres.stream()
                    .collect(Collectors.groupingBy(Livre::getAnneePublication, Collectors.counting()));
            
            // Livres les plus récents
            List<Livre> livresRecents = tousLesLivres.stream()
                    .sorted((l1, l2) -> l2.getAnneePublication().compareTo(l1.getAnneePublication()))
                    .limit(10)
                    .collect(Collectors.toList());
            
            // Livres avec le plus d'exemplaires
            List<Livre> livresPopulaires = tousLesLivres.stream()
                    .sorted((l1, l2) -> Integer.compare(l2.getExemplaires().size(), l1.getExemplaires().size()))
                    .limit(10)
                    .collect(Collectors.toList());
            
            // Ajout des statistiques à la requête
            request.setAttribute("totalLivres", totalLivres);
            request.setAttribute("livresActifs", livresActifs);
            request.setAttribute("livresDisponibles", livresDisponibles);
            request.setAttribute("totalExemplaires", totalExemplaires);
            request.setAttribute("statsParDomaine", statsParDomaine);
            request.setAttribute("statsParNiveau", statsParNiveau);
            request.setAttribute("statsParAnnee", statsParAnnee);
            request.setAttribute("livresRecents", livresRecents);
            request.setAttribute("livresPopulaires", livresPopulaires);
            
            // Calcul des pourcentages
            double pourcentageActifs = totalLivres > 0 ? (double) livresActifs / totalLivres * 100 : 0;
            double pourcentageDisponibles = totalLivres > 0 ? (double) livresDisponibles / totalLivres * 100 : 0;
            
            request.setAttribute("pourcentageActifs", Math.round(pourcentageActifs * 100.0) / 100.0);
            request.setAttribute("pourcentageDisponibles", Math.round(pourcentageDisponibles * 100.0) / 100.0);
            
            // Forward vers la page de statistiques
            request.getRequestDispatcher("/livre-stats.jsp").forward(request, response);
            
        } catch (Exception e) {
            // Gestion des erreurs
            request.setAttribute("error", "Erreur lors du calcul des statistiques: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/error/500.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirection vers GET
        doGet(request, response);
    }
}