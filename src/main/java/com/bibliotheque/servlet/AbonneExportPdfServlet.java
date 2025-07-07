package com.bibliotheque.servlet;

import com.bibliotheque.entity.Abonne;
import com.bibliotheque.service.AbonneService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

@WebServlet("/admin/export-abonnes-pdf")
public class AbonneExportPdfServlet extends HttpServlet {
    private AbonneService abonneService = new AbonneService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Abonne> abonnes = abonneService.findAll();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=abonnes.pdf");

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            document.add(new Paragraph("Liste des abonnés"));
            PdfPTable table = new PdfPTable(5);
            table.addCell("Numéro");
            table.addCell("Nom");
            table.addCell("Prénom");
            table.addCell("Statut");
            table.addCell("Email");

            for (Abonne abonne : abonnes) {
                table.addCell(String.valueOf(abonne.getNumeroAbonnement()));
                table.addCell(abonne.getNom());
                table.addCell(abonne.getPrenom());
                table.addCell(abonne.getStatut().toString());
                table.addCell(abonne.getEmail());
            }
            document.add(table);
            document.close();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}