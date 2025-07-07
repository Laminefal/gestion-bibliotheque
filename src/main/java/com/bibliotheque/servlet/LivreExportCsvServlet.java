package com.bibliotheque.servlet;

import com.bibliotheque.entity.Livre;
import com.bibliotheque.service.LivreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/admin/export-livres-csv")
public class LivreExportCsvServlet extends HttpServlet {
    private LivreService livreService = new LivreService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Livre> livres = livreService.findAll();

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=livres.csv");

        PrintWriter writer = response.getWriter();
        writer.println("id,titre,auteurs,annee,domaine,niveau");
        for (Livre livre : livres) {
            writer.printf("%s,%s,%s,%d,%s,%s\n",
                livre.getId(),
                livre.getTitre(),
                livre.getAuteurs(),
                livre.getAnneePublication(),
                livre.getDomaine() != null ? livre.getDomaine().getNom() : "",
                livre.getNiveauRequis() != null ? livre.getNiveauRequis().name() : ""
            );
        }
        writer.flush();
        writer.close();
    }
}