package com.bibliotheque.servlet;

import com.bibliotheque.entity.Livre;
import com.bibliotheque.service.LivreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/livre-export")
public class LivreExportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private LivreService livreService;
    
    @Override
    public void init() throws ServletException {
        livreService = new LivreService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String format = request.getParameter("format");
            if (format == null) {
                format = "csv";
            }
            
            List<Livre> livres = livreService.findAll();
            
            if ("csv".equalsIgnoreCase(format)) {
                exportCSV(response, livres);
            } else if ("txt".equalsIgnoreCase(format)) {
                exportTXT(response, livres);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Format non supporté");
            }
            
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                             "Erreur lors de l'export: " + e.getMessage());
        }
    }
    
    private void exportCSV(HttpServletResponse response, List<Livre> livres) throws IOException {
        response.setContentType("text/csv;charset=UTF-8");
        response.setHeader("Content-Disposition", 
                          "attachment; filename=\"livres_" + 
                          LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + 
                          ".csv\"");
        
        PrintWriter writer = response.getWriter();
        
        // En-tête CSV
        writer.println("ID,Identifiant,Titre,Auteurs,Année,Domaine,Niveau,ISBN,Éditeur,Pages,Actif,Exemplaires,Disponible");
        
        // Données
        for (Livre livre : livres) {
            writer.printf("\"%d\",\"%s\",\"%s\",\"%s\",\"%d\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%d\",\"%s\"%n",
                    livre.getId(),
                    livre.getIdentifiant(),
                    livre.getTitre().replace("\"", "\"\""),
                    livre.getAuteurs().replace("\"", "\"\""),
                    livre.getAnneePublication(),
                    livre.getDomaine(),
                    livre.getNiveauRequis(),
                    livre.getIsbn() != null ? livre.getIsbn() : "",
                    livre.getEditeur() != null ? livre.getEditeur().replace("\"", "\"\"") : "",
                    livre.getNombrePages() != null ? livre.getNombrePages().toString() : "",
                    livre.isActif() ? "Oui" : "Non",
                    livre.getExemplaires().size(),
                    livre.estDisponible() ? "Oui" : "Non"
            );
        }
        
        writer.flush();
    }
    
    private void exportTXT(HttpServletResponse response, List<Livre> livres) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Content-Disposition", 
                          "attachment; filename=\"livres_" + 
                          LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + 
                          ".txt\"");
        
        PrintWriter writer = response.getWriter();
        
        writer.println("CATALOGUE DES LIVRES");
        writer.println("===================");
        writer.println("Date d'export: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        writer.println("Nombre total de livres: " + livres.size());
        writer.println();
        
        for (Livre livre : livres) {
            writer.println("LIVRE #" + livre.getId());
            writer.println("Identifiant: " + livre.getIdentifiant());
            writer.println("Titre: " + livre.getTitre());
            writer.println("Auteurs: " + livre.getAuteurs());
            writer.println("Année: " + livre.getAnneePublication());
            writer.println("Domaine: " + livre.getDomaine());
            writer.println("Niveau: " + livre.getNiveauRequis());
            if (livre.getIsbn() != null) {
                writer.println("ISBN: " + livre.getIsbn());
            }
            if (livre.getEditeur() != null) {
                writer.println("Éditeur: " + livre.getEditeur());
            }
            if (livre.getNombrePages() != null) {
                writer.println("Pages: " + livre.getNombrePages());
            }
            writer.println("Statut: " + (livre.isActif() ? "Actif" : "Inactif"));
            writer.println("Exemplaires: " + livre.getExemplaires().size());
            writer.println("Disponible: " + (livre.estDisponible() ? "Oui" : "Non"));
            if (livre.getDescription() != null && !livre.getDescription().trim().isEmpty()) {
                writer.println("Description: " + livre.getDescription());
            }
            writer.println();
            writer.println("---");
            writer.println();
        }
        
        writer.flush();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirection vers GET
        doGet(request, response);
    }
}