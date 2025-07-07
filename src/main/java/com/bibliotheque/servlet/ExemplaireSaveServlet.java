package com.bibliotheque.servlet;

import com.bibliotheque.entity.Exemplaire;
import com.bibliotheque.entity.Livre;
import com.bibliotheque.service.ExemplaireService;
import com.bibliotheque.service.LivreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet({"/exemplaire-save", "/admin/exemplaire-save", "/bibliothecaire/exemplaire-save"})
public class ExemplaireSaveServlet extends HttpServlet {
    private ExemplaireService exemplaireService;
    private LivreService livreService;

    @Override
    public void init() throws ServletException {
        exemplaireService = new ExemplaireService();
        livreService = new LivreService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idParam = request.getParameter("id");
            String codeExemplaire = request.getParameter("codeExemplaire");
            String livreIdParam = request.getParameter("livreId");
            String etat = request.getParameter("etat");
            String localisation = request.getParameter("localisation");
            String disponibleParam = request.getParameter("disponible");
            String actifParam = request.getParameter("actif");

            if (codeExemplaire == null || codeExemplaire.trim().isEmpty() ||
                livreIdParam == null || livreIdParam.trim().isEmpty()) {
                throw new IllegalArgumentException("Le code exemplaire et le livre sont obligatoires");
            }

            Exemplaire exemplaires;
            if (idParam != null && !idParam.trim().isEmpty()) {
                Long id = Long.parseLong(idParam);
                Optional<Exemplaire> exemplaireOpt = exemplaireService.findById(id);
                if (exemplaireOpt.isEmpty()) {
                    throw new IllegalArgumentException("Exemplaire non trouvé");
                }
                exemplaires = exemplaireOpt.get();
            } else {
                exemplaires = new Exemplaire();
            }

            Long livreId = Long.parseLong(livreIdParam);
            Optional<Livre> livreOpt = livreService.findById(livreId);
            if (livreOpt.isEmpty()) {
                throw new IllegalArgumentException("Livre non trouvé");
            }

            exemplaires.setCodeExemplaire(codeExemplaire.trim());
            exemplaires.setLivre(livreOpt.get());
            exemplaires.setEtat(etat != null ? etat : "Bon");
            exemplaires.setLocalisation(localisation);
            exemplaires.setDisponible("on".equals(disponibleParam));
            exemplaires.setActif("on".equals(actifParam));

            if (idParam != null && !idParam.trim().isEmpty()) {
                exemplaireService.update(exemplaires);
            } else {
                exemplaireService.save(exemplaires);
            }

            response.sendRedirect(request.getContextPath() + "/exemplaires");

        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de la sauvegarde : " + e.getMessage());
            request.getRequestDispatcher("/exemplaire-form.jsp").forward(request, response);
        }
    }
}