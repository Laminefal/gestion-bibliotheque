package com.bibliotheque.servlet;

import com.bibliotheque.entity.Abonne;
import com.bibliotheque.entity.Exemplaire;
import com.bibliotheque.entity.Livre;
import com.bibliotheque.service.AbonneService;
import com.bibliotheque.service.ExemplaireService;
import com.bibliotheque.service.LivreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/test-data")
public class TestDataServlet extends HttpServlet {
    private LivreService livreService = new LivreService();
    private AbonneService abonneService = new AbonneService();
    private ExemplaireService exemplaireService = new ExemplaireService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html><head><title>Test des données</title></head><body>");
        out.println("<h1>Test de la base de données</h1>");
        
        try {
            // Test des livres
            List<Livre> livres = livreService.findAll();
            out.println("<h2>Livres (" + livres.size() + ")</h2>");
            if (livres.isEmpty()) {
                out.println("<p>Aucun livre trouvé</p>");
            } else {
                out.println("<ul>");
                for (Livre livre : livres) {
                    out.println("<li>" + livre.getTitre() + " - " + livre.getAuteurs() + "</li>");
                }
                out.println("</ul>");
            }
            
            // Test des abonnés
            List<Abonne> abonnes = abonneService.findAll();
            out.println("<h2>Abonnés (" + abonnes.size() + ")</h2>");
            if (abonnes.isEmpty()) {
                out.println("<p>Aucun abonné trouvé</p>");
            } else {
                out.println("<ul>");
                for (Abonne abonne : abonnes) {
                    out.println("<li>" + abonne.getNom() + " " + abonne.getPrenom() + " - " + abonne.getNumeroAbonnement() + "</li>");
                }
                out.println("</ul>");
            }
            
            // Test des exemplaires
            List<Exemplaire> exemplaires = exemplaireService.findAll();
            out.println("<h2>Exemplaires (" + exemplaires.size() + ")</h2>");
            if (exemplaires.isEmpty()) {
                out.println("<p>Aucun exemplaire trouvé</p>");
            } else {
                out.println("<ul>");
                for (Exemplaire exemplaire : exemplaires) {
                    out.println("<li>" + exemplaire.getCodeExemplaire() + " - " + 
                               (exemplaire.getLivre() != null ? exemplaire.getLivre().getTitre() : "Livre null") + "</li>");
                }
                out.println("</ul>");
            }
            
            // Test des exemplaires disponibles
            List<Exemplaire> exemplairesDisponibles = exemplaireService.findDisponibles();
            out.println("<h2>Exemplaires disponibles (" + exemplairesDisponibles.size() + ")</h2>");
            if (exemplairesDisponibles.isEmpty()) {
                out.println("<p>Aucun exemplaire disponible trouvé</p>");
            } else {
                out.println("<ul>");
                for (Exemplaire exemplaire : exemplairesDisponibles) {
                    out.println("<li>" + exemplaire.getCodeExemplaire() + " - " + 
                               (exemplaire.getLivre() != null ? exemplaire.getLivre().getTitre() : "Livre null") + "</li>");
                }
                out.println("</ul>");
            }
            
        } catch (Exception e) {
            out.println("<h2>Erreur</h2>");
            out.println("<p>Erreur lors du test : " + e.getMessage() + "</p>");
            e.printStackTrace(out);
        }
        
        out.println("</body></html>");
    }
}