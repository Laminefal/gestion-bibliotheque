package com.bibliotheque.servlet;

import com.bibliotheque.entity.Abonne;
import com.bibliotheque.entity.StatutAbonne;
import com.bibliotheque.service.AbonneService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/AbonneImportCsvServlet")
@MultipartConfig
public class AbonneImportCsvServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AbonneService abonneService = new AbonneService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("file");
        List<String> errors = new ArrayList<>();
        int imported = 0;
        if (filePart != null && filePart.getSize() > 0) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                boolean first = true;
                while ((line = reader.readLine()) != null) {
                    if (first) { first = false; continue; } // skip header
                    String[] tokens = line.split(",");
                    if (tokens.length < 7) { errors.add("Ligne ignorée: " + line); continue; }
                    try {
                        Abonne abonne = new Abonne();
                        abonne.setNumeroAbonnement(tokens[0].trim());
                        abonne.setNom(tokens[1].trim());
                        abonne.setPrenom(tokens[2].trim());
                        abonne.setEmail(tokens[3].trim());
                        abonne.setStatut(StatutAbonne.valueOf(tokens[4].trim().toUpperCase()));
                        abonne.setInstitution(tokens[5].trim());
                        abonne.setTelephone(tokens.length > 6 ? tokens[6].trim() : "");
                        abonne.setAdresse(tokens.length > 7 ? tokens[7].trim() : "");
                        if (tokens.length > 8 && !tokens[8].trim().isEmpty()) {
                            abonne.setDateInscription(LocalDateTime.parse(tokens[8].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        }
                        abonne.setActif(tokens.length > 9 ? "Oui".equalsIgnoreCase(tokens[9].trim()) : true);
                        abonneService.save(abonne);
                        imported++;
                    } catch (Exception e) {
                        errors.add("Erreur ligne: " + line + " => " + e.getMessage());
                    }
                }
            }
        }
        request.setAttribute("message", imported + " abonnés importés. " + (errors.isEmpty() ? "" : ("Erreurs: " + errors)));
        request.getRequestDispatcher("/admin/rapports.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/admin/rapports.jsp");
    }
}
