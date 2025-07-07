package com.bibliotheque.servlet;

import com.bibliotheque.entity.Livre;
import com.bibliotheque.entity.Domaine;
import com.bibliotheque.entity.Niveau;
import com.bibliotheque.service.LivreService;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/LivreImportCsvServlet")
@MultipartConfig
public class LivreImportCsvServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LivreService livreService = new LivreService();

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
                    if (tokens.length < 6) { errors.add("Ligne ignorée: " + line); continue; }
                    try {
                        Livre livre = new Livre();
                        livre.setIdentifiant(tokens[0].trim());
                        livre.setTitre(tokens[1].trim());
                        livre.setAuteurs(tokens[2].trim());
                        livre.setAnneePublication(Integer.parseInt(tokens[3].trim()));
                        livre.setDomaine(Domaine.valueOf(tokens[4].trim().toUpperCase()));
                        livre.setNiveauRequis(Niveau.valueOf(tokens[5].trim().toUpperCase()));
                        if (tokens.length > 6) livre.setIsbn(tokens[6].trim());
                        if (tokens.length > 7) livre.setEditeur(tokens[7].trim());
                        if (tokens.length > 8 && !tokens[8].trim().isEmpty()) livre.setDescription(tokens[8].trim());
                        if (tokens.length > 9 && !tokens[9].trim().isEmpty()) livre.setNombrePages(Integer.parseInt(tokens[9].trim()));
                        livre.setActif(tokens.length > 10 ? "Oui".equalsIgnoreCase(tokens[10].trim()) : true);
                        livreService.save(livre);
                        imported++;
                    } catch (Exception e) {
                        errors.add("Erreur ligne: " + line + " => " + e.getMessage());
                    }
                }
            }
        }
        request.setAttribute("message", imported + " livres importés. " + (errors.isEmpty() ? "" : ("Erreurs: " + errors)));
        request.getRequestDispatcher("/admin/rapports.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/admin/rapports.jsp");
    }
}
