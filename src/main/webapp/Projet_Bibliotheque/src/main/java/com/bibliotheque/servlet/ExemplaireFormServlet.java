package com.bibliotheque.servlet;

import com.bibliotheque.entity.Exemplaire;
import com.bibliotheque.entity.Livre;
import com.bibliotheque.entity.Domaine;
import com.bibliotheque.entity.Niveau;
import com.bibliotheque.service.ExemplaireService;
import com.bibliotheque.service.LivreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet({"/exemplaire-form", "/admin/exemplaire-form", "/bibliothecaire/exemplaire-form"})
public class ExemplaireFormServlet extends HttpServlet {
    private ExemplaireService exemplaireService;
    private LivreService livreService;

    @Override
    public void init() throws ServletException {
        exemplaireService = new ExemplaireService();
        livreService = new LivreService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                Optional<Exemplaire> exemplaireOpt = exemplaireService.findById(id);
                exemplaireOpt.ifPresent(ex -> request.setAttribute("exemplaire", ex));
            } catch (NumberFormatException e) {
                // id invalide, ignorer
            }
        }

        List<Livre> livres = livreService.findAll();
        System.out.println("Nombre de livres trouvés : " + livres.size());
        
        if (livres.isEmpty()) {
            System.out.println("Aucun livre trouvé, création de livres de test...");
            livreService.save(new Livre("L'Aventure ambiguë", "Cheikh Hamidou Kane", 1961, Domaine.LITTERATURE, Niveau.DEBUTANT, true, "9782070360289", "LIV001"));
            livreService.save(new Livre("Une si longue lettre", "Mariama Bâ", 1979, Domaine.LITTERATURE, Niveau.TOUS_NIVEAUX, true, "9782090316282", "LIV002"));
            livreService.save(new Livre("L'Étranger", "Albert Camus", 1942, Domaine.LITTERATURE, Niveau.INTERMEDIAIRE, true, "9782070360029", "LIV003"));
            livres = livreService.findAll();
            System.out.println("Livres créés, nombre total : " + livres.size());
        }

        request.setAttribute("livres", livres);
        request.getRequestDispatcher("/exemplaire-form.jsp").forward(request, response);
    }
}