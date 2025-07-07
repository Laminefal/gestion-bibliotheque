package com.bibliotheque.servlet;

import com.bibliotheque.entity.Abonne;
import com.bibliotheque.entity.Exemplaire;
import com.bibliotheque.service.AbonneService;
import com.bibliotheque.service.ExemplaireService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/emprunt-form")
public class EmpruntFormServlet extends HttpServlet {
    private AbonneService abonneService = new AbonneService();
    private ExemplaireService exemplaireService = new ExemplaireService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Abonne> abonnes = abonneService.findAll();
        List<Exemplaire> exemplaires = exemplaireService.findDisponibles();
        
        System.out.println("Nombre d'abonnés trouvés : " + abonnes.size());
        System.out.println("Nombre d'exemplaires disponibles trouvés : " + exemplaires.size());

        request.setAttribute("abonnes", abonnes);
        request.setAttribute("exemplaires", exemplaires);

        request.getRequestDispatcher("/emprunt-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Rediriger vers la servlet de sauvegarde
        request.getRequestDispatcher("/emprunt-save").forward(request, response);
    }
}