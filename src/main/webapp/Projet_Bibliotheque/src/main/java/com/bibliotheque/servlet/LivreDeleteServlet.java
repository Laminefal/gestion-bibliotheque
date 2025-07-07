package com.bibliotheque.servlet;

import com.bibliotheque.entity.Livre;
import com.bibliotheque.service.LivreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/livre-delete")
public class LivreDeleteServlet extends HttpServlet {
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
            String idParam = request.getParameter("id");
            
            if (idParam == null || idParam.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/livres?error=true&message=" + 
                                    java.net.URLEncoder.encode("ID de livre manquant", "UTF-8"));
                return;
            }
            
            try {
                Long id = Long.parseLong(idParam);
                Optional<Livre> livreOpt = livreService.findByIdWithExemplaires(id);
                
                if (livreOpt.isPresent()) {
                    Livre livre = livreOpt.get();
                    
                    request.setAttribute("livre", livre);
                    
                    // Calcul des statistiques pour l'affichage
                    int nombreExemplaires = livre.getExemplaires() != null ? livre.getExemplaires().size() : 0;
                    request.setAttribute("nombreExemplaires", nombreExemplaires);
                    
                } else {
                    request.setAttribute("error", "Livre introuvable avec l'ID: " + id);
                }
                
            } catch (NumberFormatException e) {
                request.setAttribute("error", "ID de livre invalide: " + idParam);
            }
            
            // Forward vers la page de confirmation
            request.getRequestDispatcher("/livre-delete.jsp").forward(request, response);
            
        } catch (Exception e) {
            // Gestion des erreurs
            e.printStackTrace(); // Pour le debug
            request.setAttribute("error", "Erreur lors du chargement: " + e.getMessage());
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