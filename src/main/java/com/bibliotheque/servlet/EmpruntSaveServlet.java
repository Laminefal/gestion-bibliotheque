package com.bibliotheque.servlet;

import com.bibliotheque.entity.Emprunt;
import com.bibliotheque.entity.Abonne;
import com.bibliotheque.entity.Exemplaire;
import com.bibliotheque.entity.Bibliothecaire;
import com.bibliotheque.service.EmpruntService;
import com.bibliotheque.service.AbonneService;
import com.bibliotheque.service.ExemplaireService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@WebServlet({"/emprunt-save", "/admin/emprunt-save", "/bibliothecaire/emprunt-save"})
public class EmpruntSaveServlet extends HttpServlet {
    private EmpruntService empruntService;
    private AbonneService abonneService;
    private ExemplaireService exemplaireService;

    @Override
    public void init() throws ServletException {
        empruntService = new EmpruntService();
        abonneService = new AbonneService();
        exemplaireService = new ExemplaireService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String abonneIdParam = request.getParameter("abonneId");
            String exemplaireIdParam = request.getParameter("exemplaireId");
            String dateRetourPrevueParam = request.getParameter("dateRetourPrevue");

            // Validation des paramètres obligatoires
            if (abonneIdParam == null || abonneIdParam.trim().isEmpty() ||
                exemplaireIdParam == null || exemplaireIdParam.trim().isEmpty() ||
                dateRetourPrevueParam == null || dateRetourPrevueParam.trim().isEmpty()) {
                throw new IllegalArgumentException("Tous les champs sont obligatoires");
            }

            // Récupérer l'abonné
            Long abonneId = Long.parseLong(abonneIdParam);
            Optional<Abonne> abonneOpt = abonneService.findById(abonneId);
            if (abonneOpt.isEmpty()) {
                throw new IllegalArgumentException("Abonné non trouvé");
            }
            Abonne abonne = abonneOpt.get();

            // Récupérer l'exemplaire
            Long exemplaireId = Long.parseLong(exemplaireIdParam);
            Optional<Exemplaire> exemplaireOpt = exemplaireService.findById(exemplaireId);
            if (exemplaireOpt.isEmpty()) {
                throw new IllegalArgumentException("Exemplaire non trouvé");
            }
            Exemplaire exemplaire = exemplaireOpt.get();

            // Vérifier que l'exemplaire est disponible
            if (!exemplaire.isDisponible()) {
                throw new IllegalArgumentException("L'exemplaire n'est pas disponible");
            }

            // Récupérer le bibliothécaire connecté
            HttpSession session = request.getSession();
            Bibliothecaire bibliothecaire = (Bibliothecaire) session.getAttribute("utilisateur");
            if (bibliothecaire == null) {
                throw new IllegalArgumentException("Bibliothécaire non connecté");
            }

            // Parser la date de retour prévue
            LocalDate dateRetourPrevue = LocalDate.parse(dateRetourPrevueParam);

            // Créer l'emprunt
            Emprunt emprunt = new Emprunt(abonne, exemplaire, bibliothecaire, dateRetourPrevue);
            
            // Marquer l'exemplaire comme non disponible
            exemplaire.setDisponible(false);
            exemplaireService.update(exemplaire);

            // Sauvegarder l'emprunt
            empruntService.save(emprunt);

            response.sendRedirect(request.getContextPath() + "/emprunts");

        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de la création de l'emprunt : " + e.getMessage());
            request.getRequestDispatcher("/emprunt-form.jsp").forward(request, response);
        }
    }
}