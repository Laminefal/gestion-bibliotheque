package com.bibliotheque.servlet;

import com.bibliotheque.service.AbonneService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/abonne-delete")
public class AbonneDeleteServlet extends HttpServlet {
    private AbonneService abonneService;

    @Override
    public void init() throws ServletException {
        abonneService = new AbonneService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                abonneService.delete(id);
            } catch (NumberFormatException e) {
                // id invalide, ignorer
            }
        }
        response.sendRedirect("abonnes");
    }
}
