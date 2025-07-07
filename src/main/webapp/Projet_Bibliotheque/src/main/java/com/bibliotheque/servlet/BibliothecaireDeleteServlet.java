package com.bibliotheque.servlet;

import com.bibliotheque.service.BibliothecaireService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/bibliothecaire-delete")
public class BibliothecaireDeleteServlet extends HttpServlet {
    private BibliothecaireService service = new BibliothecaireService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            service.delete(Long.parseLong(idParam));
        }
        resp.sendRedirect(req.getContextPath() + "/admin/bibliothecaires");
    }
}