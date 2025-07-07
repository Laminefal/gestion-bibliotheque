package com.bibliotheque.servlet;

import com.bibliotheque.entity.Utilisateur;
import com.bibliotheque.service.AuthenticationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    
    private AuthenticationService authenticationService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        authenticationService = new AuthenticationService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Rediriger vers la page de connexion
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nomUtilisateur = request.getParameter("nomUtilisateur");
        String motDePasse = request.getParameter("motDePasse");
        
        // Validation des paramètres
        if (nomUtilisateur == null || motDePasse == null || 
            nomUtilisateur.trim().isEmpty() || motDePasse.trim().isEmpty()) {
            
            request.setAttribute("error", "Le nom d'utilisateur et le mot de passe sont obligatoires");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        
        try {
            // Test de l'EntityManager
            System.out.println("=== Test de l'EntityManager dans LoginServlet ===");
            if (authenticationService != null) {
                System.out.println("=== AuthenticationService disponible ===");
            } else {
                System.err.println("=== ERREUR: AuthenticationService est null ===");
            }
            
            // Authentification
            Optional<Utilisateur> utilisateurOpt = authenticationService.authenticate(
                nomUtilisateur.trim(), motDePasse);
            
            if (utilisateurOpt.isPresent()) {
                Utilisateur utilisateur = utilisateurOpt.get();
                
                // Créer la session
                HttpSession session = request.getSession();
                session.setAttribute("utilisateur", utilisateur);
                session.setAttribute("nomUtilisateur", utilisateur.getNomUtilisateur());
                session.setAttribute("role", utilisateur.getRole());
                session.setAttribute("nomComplet", utilisateur.getNomComplet());
                session.setAttribute("photo", utilisateur.getPhoto());
                
                // Redirection selon le rôle
                if (utilisateur.getRole() == com.bibliotheque.entity.Role.ADMIN) {
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
                } else {
                    response.sendRedirect(request.getContextPath() + "/bibliothecaire/dashboard");
                }
                
            } else {
                // Échec de l'authentification
                request.setAttribute("error", "Nom d'utilisateur ou mot de passe incorrect. Utilisez les comptes de test : admin/admin123 ou biblio/biblio123");
                request.setAttribute("nomUtilisateur", nomUtilisateur);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            // Erreur lors de l'authentification
            request.setAttribute("error", "Erreur lors de la connexion: " + e.getMessage() + ". Utilisez les comptes de test : admin/admin123 ou biblio/biblio123");
            request.setAttribute("nomUtilisateur", nomUtilisateur);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
} 