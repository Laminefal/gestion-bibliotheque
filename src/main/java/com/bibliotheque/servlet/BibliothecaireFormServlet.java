package com.bibliotheque.servlet;

import com.bibliotheque.entity.Bibliothecaire;
import com.bibliotheque.service.BibliothecaireService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/bibliothecaire-form")
public class BibliothecaireFormServlet extends HttpServlet {
    private BibliothecaireService service = new BibliothecaireService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        Bibliothecaire biblio = null;
        if (idParam != null && !idParam.isEmpty()) {
            biblio = service.findById(Long.parseLong(idParam));
        }
        req.setAttribute("bibliothecaire", biblio);
        req.getRequestDispatcher("/admin/bibliothecaire-form.jsp").forward(req, resp);
    }
}