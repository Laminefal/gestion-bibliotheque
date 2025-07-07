package com.bibliotheque.servlet;

import com.bibliotheque.entity.Abonne;
import com.bibliotheque.service.AbonneService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/admin/export-abonnes-csv")
public class AbonneExportCsvServlet extends HttpServlet {
    private AbonneService abonneService = new AbonneService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Abonne> abonnes = abonneService.findAll();

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=abonnes.csv");

        PrintWriter writer = response.getWriter();
        writer.println("id,numeroAbonnement,nom,prenom,email,statut,institution,telephone,adresse,dateInscription,actif");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (Abonne abonne : abonnes) {
            writer.printf("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                abonne.getId(),
                abonne.getNumeroAbonnement(),
                abonne.getNom(),
                abonne.getPrenom(),
                abonne.getEmail(),
                abonne.getStatut() != null ? abonne.getStatut().name() : "",
                abonne.getInstitution(),
                abonne.getTelephone() != null ? abonne.getTelephone() : "",
                abonne.getAdresse() != null ? abonne.getAdresse().replace("\n", " ").replace(",", ";") : "",
                abonne.getDateInscription() != null ? abonne.getDateInscription().format(dtf) : "",
                abonne.isActif() ? "Oui" : "Non"
            );
        }
        writer.flush();
        writer.close();
    }
}
