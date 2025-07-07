package com.bibliotheque.servlet;

import com.bibliotheque.service.LivreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/livre-delete-confirm")
public class LivreDeleteConfirmServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private LivreService livreService;
    
    @Override
    public void init() throws ServletException {
        livreService = new LivreService();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String idParam = request.getParameter("id");
            String confirmation = request.getParameter("confirmation");
            
            // Vérification de la confirmation
            if (!"true".equals(confirmation)) {
                response.sendRedirect(request.getContextPath() + "/livres?error=true&message=" + 
                                    java.net.URLEncoder.encode("Confirmation requise pour la suppression", "UTF-8"));
                return;
            }
            
            if (idParam == null || idParam.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/livres?error=true&message=" + 
                                    java.net.URLEncoder.encode("ID de livre manquant", "UTF-8"));
                return;
            }
            
            try {
                Long id = Long.parseLong(idParam);
                
                // Vérification de l'existence du livre
                Optional<com.bibliotheque.entity.Livre> livreOpt = livreService.findById(id);
                if (!livreOpt.isPresent()) {
                    response.sendRedirect(request.getContextPath() + "/livres?error=true&message=" + 
                                        java.net.URLEncoder.encode("Livre introuvable", "UTF-8"));
                    return;
                }
                
                // Vérification des exemplaires empruntés
                com.bibliotheque.entity.Livre livre = livreOpt.get();
                if (livre.getExemplaires().size() > 0) {
                    // Log de l'action pour audit
                    System.out.println("Suppression du livre " + livre.getIdentifiant() + 
                                     " avec " + livre.getExemplaires().size() + " exemplaires");
                }
                
                // Suppression du livre (cascade sur les exemplaires)
                livreService.delete(id);
                
                // Redirection avec message de succès
                response.sendRedirect(request.getContextPath() + "/livres?success=true&message=" + 
                                    java.net.URLEncoder.encode("Livre supprimé avec succès", "UTF-8"));
                
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/livres?error=true&message=" + 
                                    java.net.URLEncoder.encode("ID de livre invalide", "UTF-8"));
            }
            
        } catch (Exception e) {
            // Gestion des erreurs
            response.sendRedirect(request.getContextPath() + "/livres?error=true&message=" + 
                                java.net.URLEncoder.encode("Erreur lors de la suppression: " + e.getMessage(), "UTF-8"));
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirection vers la liste des livres
        response.sendRedirect(request.getContextPath() + "/livres");
    }
}