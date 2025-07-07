package com.bibliotheque.servlet;

import com.bibliotheque.service.EmpruntService;
import com.bibliotheque.service.ExemplaireService;
import com.bibliotheque.entity.Emprunt;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet({"/emprunt-delete", "/admin/emprunt-delete", "/bibliothecaire/emprunt-delete"})
public class EmpruntDeleteServlet extends HttpServlet {
    private EmpruntService empruntService;
    private ExemplaireService exemplaireService;

    @Override
    public void init() throws ServletException {
        empruntService = new EmpruntService();
        exemplaireService = new ExemplaireService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                Optional<Emprunt> empruntOpt = empruntService.findById(id);
                
                if (empruntOpt.isPresent()) {
                    Emprunt emprunt = empruntOpt.get();
                    
                    // Si l'emprunt est en cours, remettre l'exemplaire disponible
                    if (emprunt.getDateRetourEffective() == null) {
                        emprunt.getExemplaire().setDisponible(true);
                        exemplaireService.update(emprunt.getExemplaire());
                    }
                    
                    // Supprimer l'emprunt
                    empruntService.delete(id);
                }
            } catch (NumberFormatException e) {
                // id invalide, ignorer
            }
        }
        response.sendRedirect(request.getContextPath() + "/emprunts");
    }
}