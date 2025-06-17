package com.biblio.web;

import entities.Bibliothecaire;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.io.IOException;

@WebServlet(name = "AuthServlet", urlPatterns = {"/auth/login"})
public class AuthServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bibliothequePU");
        EntityManager em = emf.createEntityManager();
        
        try {
            Bibliothecaire bibliothecaire = em.createQuery(
                "SELECT b FROM Bibliothecaire b WHERE b.login = :login", Bibliothecaire.class)
                .setParameter("login", login)
                .getSingleResult();
            
            if(bibliothecaire != null && bibliothecaire.getPassword().equals(password)) {
                HttpSession session = request.getSession();
                session.setAttribute("user", bibliothecaire);
                response.sendRedirect(request.getContextPath() + "/auth/dashboard.jsp");
            } else {
                request.setAttribute("error", "Identifiants incorrects");
                request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
            }
        } catch(Exception e) {
            request.setAttribute("error", "Erreur d'authentification : " + e.getMessage());
            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
        } finally {
            if(em != null && em.isOpen()) {
                em.close();
            }
            if(emf != null && emf.isOpen()) {
                emf.close();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}