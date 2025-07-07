package com.bibliotheque.filter;

import com.bibliotheque.entity.Utilisateur;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AuthenticationFilter")
public class AuthenticationFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialisation du filtre
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        
        // Pages publiques qui ne nécessitent pas d'authentification
        if (isPublicPage(requestURI, contextPath)) {
            chain.doFilter(request, response);
            return;
        }
        
        // Vérifier si l'utilisateur est connecté
        if (session == null || session.getAttribute("utilisateur") == null) {
            // Rediriger vers la page de connexion
            httpResponse.sendRedirect(contextPath + "/login.jsp");
            return;
        }
        
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        
        // Vérifier les droits d'accès selon le rôle
        if (requestURI.contains("/admin/") && !isAdmin(utilisateur)) {
            // Accès refusé pour les pages admin
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès refusé");
            return;
        }
        
        if (requestURI.contains("/bibliothecaire/") && !isBibliothecaire(utilisateur)) {
            // Accès refusé pour les pages bibliothécaire
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès refusé");
            return;
        }
        
        // L'utilisateur est authentifié et autorisé
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        // Nettoyage du filtre
    }
    
    /**
     * Vérifie si une page est publique
     * @param requestURI l'URI de la requête
     * @param contextPath le chemin du contexte
     * @return true si la page est publique, false sinon
     */
    private boolean isPublicPage(String requestURI, String contextPath) {
        String relativePath = requestURI.substring(contextPath.length());
        
        return relativePath.equals("/") ||
               relativePath.equals("/index.jsp") ||
               relativePath.equals("/login.jsp") ||
               relativePath.equals("/login") ||
               relativePath.equals("/logout") ||
               relativePath.startsWith("/resources/") ||
               relativePath.startsWith("/css/") ||
               relativePath.startsWith("/js/") ||
               relativePath.startsWith("/images/") ||
               relativePath.endsWith(".css") ||
               relativePath.endsWith(".js") ||
               relativePath.endsWith(".png") ||
               relativePath.endsWith(".jpg") ||
               relativePath.endsWith(".jpeg") ||
               relativePath.endsWith(".gif") ||
               relativePath.endsWith(".ico");
    }
    
    /**
     * Vérifie si l'utilisateur est administrateur
     * @param utilisateur l'utilisateur
     * @return true si l'utilisateur est administrateur, false sinon
     */
    private boolean isAdmin(Utilisateur utilisateur) {
        return utilisateur != null && 
               utilisateur.getRole() == com.bibliotheque.entity.Role.ADMIN &&
               utilisateur.isActif();
    }
    
    /**
     * Vérifie si l'utilisateur est bibliothécaire
     * @param utilisateur l'utilisateur
     * @return true si l'utilisateur est bibliothécaire, false sinon
     */
    private boolean isBibliothecaire(Utilisateur utilisateur) {
        return utilisateur != null && 
               (utilisateur.getRole() == com.bibliotheque.entity.Role.BIBLIOTHECAIRE ||
                utilisateur.getRole() == com.bibliotheque.entity.Role.ADMIN) &&
               utilisateur.isActif();
    }
} 