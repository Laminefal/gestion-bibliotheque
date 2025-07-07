package com.bibliotheque.servlet;

import com.bibliotheque.entity.Bibliothecaire;
import com.bibliotheque.service.BibliothecaireService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/bibliothecaires")
public class BibliothecairesServlet extends HttpServlet {
    private BibliothecaireService service = new BibliothecaireService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Bibliothecaire> list = service.findAll();
        System.out.println("Nombre de bibliothécaires trouvés : " + list.size());
        req.setAttribute("bibliothecaires", list);
        req.getRequestDispatcher("/admin/bibliothecaires.jsp").forward(req, resp);
    }
}