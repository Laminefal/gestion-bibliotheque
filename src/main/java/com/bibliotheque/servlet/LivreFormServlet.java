package com.bibliotheque.servlet;

import com.bibliotheque.entity.Livre;
import com.bibliotheque.service.LivreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/livre-form")
public class LivreFormServlet extends HttpServlet {
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
            
            if (idParam != null && !idParam.trim().isEmpty()) {
                // Mode édition
                Long id = Long.parseLong(idParam);
                var livreOpt = livreService.findById(id);
                
                if (livreOpt.isPresent()) {
                    request.setAttribute("livre", livreOpt.get());
                    request.setAttribute("mode", "edit");
                } else {
                    request.setAttribute("error", "Livre non trouvé");
                    response.sendRedirect(request.getContextPath() + "/livres");
                    return;
                }
            } else {
                // Mode création
                request.setAttribute("livre", new Livre());
                request.setAttribute("mode", "create");
            }
            
            // Forward vers la page JSP
            request.getRequestDispatcher("/livre-form.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID de livre invalide");
            response.sendRedirect(request.getContextPath() + "/livres");
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors du chargement du formulaire: " + e.getMessage());
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