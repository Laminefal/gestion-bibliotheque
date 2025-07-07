package com.bibliotheque.servlet;

import com.bibliotheque.entity.Utilisateur;
import com.bibliotheque.service.AuthenticationService;
import com.bibliotheque.dao.impl.UtilisateurDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@WebServlet("/upload-photo")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 5,   // 5 MB
    maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
public class PhotoUploadServlet extends HttpServlet {
    
    private AuthenticationService authenticationService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        authenticationService = new AuthenticationService();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Non autorisé");
            return;
        }
        
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        
        try {
            Part filePart = request.getPart("photo");
            if (filePart == null || filePart.getSize() == 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Aucun fichier sélectionné");
                return;
            }
            
            // Vérifier le type de fichier
            String contentType = filePart.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Le fichier doit être une image");
                return;
            }
            
            // Générer un nom de fichier unique
            String originalFileName = filePart.getSubmittedFileName();
            String fileExtension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            String newFileName = UUID.randomUUID().toString() + fileExtension;
            
            // Créer le dossier d'upload s'il n'existe pas
            String uploadPath = getServletContext().getRealPath("/uploads/photos");
            Path uploadDir = Paths.get(uploadPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            
            // Sauvegarder le fichier
            Path filePath = uploadDir.resolve(newFileName);
            Files.copy(filePart.getInputStream(), filePath);
            
            // Mettre à jour la base de données
            utilisateur.setPhoto(newFileName);
            UtilisateurDAOImpl utilisateurDAO = new UtilisateurDAOImpl();
            utilisateurDAO.update(utilisateur);
            
            // Mettre à jour la session
            session.setAttribute("photo", newFileName);
            
            // Réponse JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"success\": true, \"filename\": \"" + newFileName + "\"}");
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"success\": false, \"error\": \"" + e.getMessage() + "\"}");
        }
    }
} 