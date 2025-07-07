package com.bibliotheque.servlet;

import com.bibliotheque.service.ExemplaireService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/exemplaire-delete", "/admin/exemplaire-delete", "/bibliothecaire/exemplaire-delete"})
public class ExemplaireDeleteServlet extends HttpServlet {
    private ExemplaireService exemplaireService;

    @Override
    public void init() throws ServletException {
        exemplaireService = new ExemplaireService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                exemplaireService.delete(id);
            } catch (NumberFormatException e) {
                // id invalide, ignorer
            }
        }
        response.sendRedirect(request.getContextPath() + "/exemplaires");
    }
}