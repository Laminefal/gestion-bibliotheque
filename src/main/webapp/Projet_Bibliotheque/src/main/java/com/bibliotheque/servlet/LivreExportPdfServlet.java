package com.bibliotheque.servlet;

import com.bibliotheque.entity.Livre;
import com.bibliotheque.service.LivreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

@WebServlet("/admin/export-livres-pdf")
public class LivreExportPdfServlet extends HttpServlet {
    private LivreService livreService = new LivreService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Livre> livres = livreService.findAll();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=livres.pdf");

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            document.add(new Paragraph("Catalogue des livres"));
            PdfPTable table = new PdfPTable(13);
            table.addCell("ID");
            table.addCell("Identifiant");
            table.addCell("Titre");
            table.addCell("Auteurs");
            table.addCell("Année");
            table.addCell("Domaine");
            table.addCell("Niveau");
            table.addCell("ISBN");
            table.addCell("Éditeur");
            table.addCell("Pages");
            table.addCell("Actif");
            table.addCell("Exemplaires");
            table.addCell("Disponible");

            for (Livre livre : livres) {
                table.addCell(String.valueOf(livre.getId()));
                table.addCell(livre.getIdentifiant());
                table.addCell(livre.getTitre());
                table.addCell(livre.getAuteurs());
                table.addCell(livre.getAnneePublication() != null ? livre.getAnneePublication().toString() : "");
                table.addCell(livre.getDomaine() != null ? livre.getDomaine().name() : "");
                table.addCell(livre.getNiveauRequis() != null ? livre.getNiveauRequis().name() : "");
                table.addCell(livre.getIsbn() != null ? livre.getIsbn() : "");
                table.addCell(livre.getEditeur() != null ? livre.getEditeur() : "");
                table.addCell(livre.getNombrePages() != null ? livre.getNombrePages().toString() : "");
                table.addCell(livre.isActif() ? "Oui" : "Non");
                table.addCell(String.valueOf(livre.getExemplaires() != null ? livre.getExemplaires().size() : 0));
                table.addCell(livre.estDisponible() ? "Oui" : "Non");
            }
            document.add(table);
            document.close();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
