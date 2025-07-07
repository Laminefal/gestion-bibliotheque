package com.bibliotheque.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "CharacterEncodingFilter", urlPatterns = "/*")
public class CharacterEncodingFilter implements Filter {
    
    private String encoding;
    private boolean forceEncoding;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.encoding = filterConfig.getInitParameter("encoding");
        if (this.encoding == null) {
            this.encoding = "UTF-8";
        }
        
        String forceEncodingParam = filterConfig.getInitParameter("forceEncoding");
        this.forceEncoding = forceEncodingParam != null && Boolean.parseBoolean(forceEncodingParam);
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // Définir l'encodage de la requête
        if (this.encoding != null && (this.forceEncoding || request.getCharacterEncoding() == null)) {
            request.setCharacterEncoding(this.encoding);
        }
        
        // Définir l'encodage de la réponse
        if (this.encoding != null && (this.forceEncoding || response.getCharacterEncoding() == null)) {
            response.setCharacterEncoding(this.encoding);
        }
        
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        // Nettoyage du filtre
    }
} 