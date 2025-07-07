package com.bibliotheque.servlet;

import com.bibliotheque.dao.UtilisateurDAO;
import com.bibliotheque.dao.impl.UtilisateurDAOImpl;
import com.bibliotheque.entity.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import jakarta.servlet.annotation.MultipartConfig;

@WebServlet("/AdminParamServlet")
@MultipartConfig
public class AdminParamServlet extends HttpServlet {
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAOImpl();
    private static final String UPLOAD_DIR = "/uploads";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        Utilisateur admin = (Utilisateur) session.getAttribute("utilisateur");
        String prenom = request.getParameter("prenom");
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String message;
        try {
            admin.setPrenom(prenom);
            admin.setNom(nom);
            admin.setEmail(email);
            if (password != null && !password.trim().isEmpty()) {
                admin.setMotDePasse(password); // À adapter si hashage !
            }
            // Gestion de la photo
            Part photoPart = request.getPart("photo");
            if (photoPart != null && photoPart.getSize() > 0 && photoPart.getSubmittedFileName() != null && !photoPart.getSubmittedFileName().isEmpty()) {
                String fileName = Paths.get(photoPart.getSubmittedFileName()).getFileName().toString();
                String uploadPath = request.getServletContext().getRealPath(UPLOAD_DIR);
                Files.createDirectories(Paths.get(uploadPath));
                String filePath = uploadPath + java.io.File.separator + fileName;
                photoPart.write(filePath);
                admin.setPhoto(UPLOAD_DIR + "/" + fileName);
            }
            utilisateurDAO.update(admin);
            session.setAttribute("utilisateur", admin);
            session.setAttribute("nomComplet", admin.getNomComplet());
            message = "Profil mis à jour avec succès.";
        } catch (Exception e) {
            message = "Erreur lors de la mise à jour : " + e.getMessage();
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher("/admin/parametres.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/admin/parametres.jsp");
    }
}
