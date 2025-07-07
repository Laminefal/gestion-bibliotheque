package com.bibliotheque.servlet;

import com.bibliotheque.entity.Abonne;
import com.bibliotheque.service.AbonneService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/abonne-form")
public class AbonneFormServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private AbonneService abonneService;
    
    @Override
    public void init() throws ServletException {
        abonneService = new AbonneService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String idParam = request.getParameter("id");
            
            if (idParam != null && !idParam.trim().isEmpty()) {
                // Mode édition
                Long id = Long.parseLong(idParam);
                var abonneOpt = abonneService.findById(id);
                
                if (abonneOpt.isPresent()) {
                    request.setAttribute("abonne", abonneOpt.get());
                    request.setAttribute("mode", "edit");
                } else {
                    request.setAttribute("error", "Abonné non trouvé");
                    response.sendRedirect(request.getContextPath() + "/abonnes");
                    return;
                }
            } else {
                // Mode création
                request.setAttribute("abonne", new Abonne());
                request.setAttribute("mode", "create");
            }
            
            // Forward vers la page JSP
            request.getRequestDispatcher("/abonn-form.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID d'abonné invalide");
            response.sendRedirect(request.getContextPath() + "/abonnes");
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