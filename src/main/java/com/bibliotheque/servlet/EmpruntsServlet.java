package com.bibliotheque.servlet;

import com.bibliotheque.entity.Emprunt;
import com.bibliotheque.service.EmpruntService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet({"/emprunts", "/admin/emprunts", "/bibliothecaire/emprunts"})
public class EmpruntsServlet extends HttpServlet {
    private EmpruntService empruntService;

    @Override
    public void init() throws ServletException {
        empruntService = new EmpruntService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Emprunt> emprunts = empruntService.findAll();
        request.setAttribute("emprunts", emprunts);
        
        // Déterminer le contexte et forward vers la page JSP appropriée
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/bibliothecaire/")) {
            // Contexte bibliothécaire - utiliser la page JSP dans le répertoire bibliothecaire
            request.getRequestDispatcher("/bibliothecaire/emprunts.jsp").forward(request, response);
        } else {
            // Contexte général - utiliser la page JSP principale
            request.getRequestDispatcher("/emprunts.jsp").forward(request, response);
        }
    }
}
