package com.bibliotheque.servlet;

import com.bibliotheque.entity.Livre;
import com.bibliotheque.entity.Domaine;
import com.bibliotheque.entity.Niveau;
import com.bibliotheque.service.LivreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet({"/livre-save", "/admin/livre-save", "/bibliothecaire/livre-save"})
public class LivreSaveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private LivreService livreService;
    
    @Override
    public void init() throws ServletException {
        livreService = new LivreService();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Récupération des paramètres
            String idParam = request.getParameter("id");
            String identifiant = request.getParameter("identifiant");
            String titre = request.getParameter("titre");
            String auteurs = request.getParameter("auteurs");
            String anneeParam = request.getParameter("anneePublication");
            String domaineParam = request.getParameter("domaine");
            String niveauParam = request.getParameter("niveauRequis");
            String isbn = request.getParameter("isbn");
            String editeur = request.getParameter("editeur");
            String description = request.getParameter("description");
            String nombrePagesParam = request.getParameter("nombrePages");
            String actifParam = request.getParameter("actif");
            
            // Validation des champs obligatoires
            if (identifiant == null || identifiant.trim().isEmpty()) {
                throw new IllegalArgumentException("L'identifiant est obligatoire");
            }
            if (titre == null || titre.trim().isEmpty()) {
                throw new IllegalArgumentException("Le titre est obligatoire");
            }
            if (auteurs == null || auteurs.trim().isEmpty()) {
                throw new IllegalArgumentException("Les auteurs sont obligatoires");
            }
            if (anneeParam == null || anneeParam.trim().isEmpty()) {
                throw new IllegalArgumentException("L'année de publication est obligatoire");
            }
            if (domaineParam == null || domaineParam.trim().isEmpty()) {
                throw new IllegalArgumentException("Le domaine est obligatoire");
            }
            if (niveauParam == null || niveauParam.trim().isEmpty()) {
                throw new IllegalArgumentException("Le niveau requis est obligatoire");
            }
            
            // Conversion des paramètres
            Integer anneePublication = Integer.parseInt(anneeParam);
            Domaine domaine = Domaine.valueOf(domaineParam);
            Niveau niveauRequis = Niveau.valueOf(niveauParam);
            Integer nombrePages = (nombrePagesParam != null && !nombrePagesParam.trim().isEmpty()) 
                ? Integer.parseInt(nombrePagesParam) : null;
            boolean actif = actifParam != null;
            
            Livre livre;
            boolean isEdit = false;
            
            if (idParam != null && !idParam.trim().isEmpty()) {
                // Mode édition
                Long id = Long.parseLong(idParam);
                var livreOpt = livreService.findById(id);
                
                if (livreOpt.isPresent()) {
                    livre = livreOpt.get();
                    isEdit = true;
                } else {
                    throw new IllegalArgumentException("Livre non trouvé avec l'ID: " + id);
                }
            } else {
                // Mode création
                livre = new Livre();
                livre.setDateAjout(LocalDateTime.now());
            }
            
            // Mise à jour des propriétés
            livre.setIdentifiant(identifiant.trim());
            livre.setTitre(titre.trim());
            livre.setAuteurs(auteurs.trim());
            livre.setAnneePublication(anneePublication);
            livre.setDomaine(domaine);
            livre.setNiveauRequis(niveauRequis);
            livre.setIsbn(isbn != null ? isbn.trim() : null);
            livre.setEditeur(editeur != null ? editeur.trim() : null);
            livre.setDescription(description != null ? description.trim() : null);
            livre.setNombrePages(nombrePages);
            livre.setActif(actif);
            
            // Vérification de l'unicité de l'identifiant
            if (!isEdit && livreService.existsByIdentifiant(identifiant)) {
                throw new IllegalArgumentException("Un livre avec cet identifiant existe déjà");
            }
            
            // Vérification de l'unicité de l'ISBN si fourni
            if (isbn != null && !isbn.trim().isEmpty() && livreService.existsByIsbn(isbn)) {
                throw new IllegalArgumentException("Un livre avec cet ISBN existe déjà");
            }
            
            // Sauvegarde
            if (isEdit) {
                livreService.save(livre);
            } else {
                livreService.save(livre);
            }
            
            // Redirection avec message de succès
            String message = isEdit ? "Livre modifié avec succès" : "Livre ajouté avec succès";
            response.sendRedirect(request.getContextPath() + "/livres?success=" + 
                                java.net.URLEncoder.encode(message, "UTF-8"));
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Format de nombre invalide: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/error/500.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/error/500.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de la sauvegarde: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/error/500.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirection vers la liste des livres
        response.sendRedirect(request.getContextPath() + "/livres");
    }
}