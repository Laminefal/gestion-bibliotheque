package com.bibliotheque.servlet;

import com.bibliotheque.entity.Abonne;
import com.bibliotheque.entity.Exemplaire;
import com.bibliotheque.entity.Livre;
import com.bibliotheque.service.AbonneService;
import com.bibliotheque.service.ExemplaireService;
import com.bibliotheque.service.LivreService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/diagnostic")
public class DiagnosticServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html><head><title>Diagnostic complet</title></head><body>");
        out.println("<h1>Diagnostic complet de l'application</h1>");
        
        try {
            // Test de la connexion JPA
            out.println("<h2>1. Test de la connexion JPA</h2>");
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("bibliothequePU");
            EntityManager em = emf.createEntityManager();
            out.println("<p>✓ EntityManagerFactory créé avec succès</p>");
            out.println("<p>✓ EntityManager créé avec succès</p>");
            
            // Test direct des entités
            out.println("<h2>2. Test direct des entités</h2>");
            
            // Test des livres
            List<Livre> livresDirect = em.createQuery("SELECT l FROM Livre l", Livre.class).getResultList();
            out.println("<p>Livres (requête directe) : " + livresDirect.size() + "</p>");
            for (Livre livre : livresDirect) {
                out.println("<p>  - " + livre.getTitre() + " (ID: " + livre.getId() + ")</p>");
            }
            
            // Test des abonnés
            List<Abonne> abonnesDirect = em.createQuery("SELECT a FROM Abonne a", Abonne.class).getResultList();
            out.println("<p>Abonnés (requête directe) : " + abonnesDirect.size() + "</p>");
            for (Abonne abonne : abonnesDirect) {
                out.println("<p>  - " + abonne.getNom() + " " + abonne.getPrenom() + " (ID: " + abonne.getId() + ")</p>");
            }
            
            // Test des exemplaires
            List<Exemplaire> exemplairesDirect = em.createQuery("SELECT e FROM Exemplaire e LEFT JOIN FETCH e.livre", Exemplaire.class).getResultList();
            out.println("<p>Exemplaires (requête directe) : " + exemplairesDirect.size() + "</p>");
            for (Exemplaire exemplaire : exemplairesDirect) {
                out.println("<p>  - " + exemplaire.getCodeExemplaire() + " (ID: " + exemplaire.getId() + 
                           ", Livre: " + (exemplaire.getLivre() != null ? exemplaire.getLivre().getTitre() : "null") + ")</p>");
            }
            
            // Test des services
            out.println("<h2>3. Test des services</h2>");
            
            LivreService livreService = new LivreService();
            AbonneService abonneService = new AbonneService();
            ExemplaireService exemplaireService = new ExemplaireService();
            
            List<Livre> livresService = livreService.findAll();
            out.println("<p>Livres (via service) : " + livresService.size() + "</p>");
            
            List<Abonne> abonnesService = abonneService.findAll();
            out.println("<p>Abonnés (via service) : " + abonnesService.size() + "</p>");
            
            List<Exemplaire> exemplairesService = exemplaireService.findDisponibles();
            out.println("<p>Exemplaires disponibles (via service) : " + exemplairesService.size() + "</p>");
            
            em.close();
            emf.close();
            out.println("<p>✓ EntityManager et EntityManagerFactory fermés</p>");
            
        } catch (Exception e) {
            out.println("<h2>❌ Erreur</h2>");
            out.println("<p>Erreur lors du diagnostic : " + e.getMessage() + "</p>");
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        }
        
        out.println("</body></html>");
    }
} 