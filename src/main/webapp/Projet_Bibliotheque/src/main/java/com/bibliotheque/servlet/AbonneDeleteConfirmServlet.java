package com.bibliotheque.servlet;

import com.bibliotheque.entity.Abonne;
import com.bibliotheque.service.AbonneService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/abonne-delete.jsp")
public class AbonneDeleteConfirmServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AbonneService abonneService;

    @Override
    public void init() throws ServletException {
        abonneService = new AbonneService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                Optional<Abonne> abonneOpt = abonneService.findById(id);
                abonneOpt.ifPresent(a -> request.setAttribute("abonne", a));
            } catch (NumberFormatException e) {
                // id invalide, ignorer
            }
        }
        request.getRequestDispatcher("/abonne-delete.jsp").forward(request, response);
    }
}