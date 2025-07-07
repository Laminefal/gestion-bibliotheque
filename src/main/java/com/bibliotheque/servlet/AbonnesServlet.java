package com.bibliotheque.servlet;

import com.bibliotheque.entity.Abonne;
import com.bibliotheque.service.AbonneService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet({"/abonnes", "/bibliothecaire/abonnes"})
public class AbonnesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AbonneService abonneService;
       
    public AbonnesServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        System.out.println("=== AbonnesServlet initialisé avec le mapping /abonnes ===");
        abonneService = new AbonneService();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("=== AbonnesServlet.doGet() appelé ===");
		
		try {
			// Récupération des paramètres de pagination et filtrage
			String statutFilter = request.getParameter("statut");
			String searchTerm = request.getParameter("search");
			
			List<Abonne> abonnes;
			
			// Application des filtres
			if (statutFilter != null && !statutFilter.trim().isEmpty()) {
				abonnes = abonneService.findByStatut(statutFilter);
			} else if (searchTerm != null && !searchTerm.trim().isEmpty()) {
				abonnes = abonneService.searchAbonnes(searchTerm);
			} else {
				abonnes = abonneService.findAll();
			}
			
			// Ajout des données à la requête
			request.setAttribute("abonnes", abonnes);
			request.setAttribute("totalAbonnes", abonnes.size());
			
			// Calcul des statistiques
			long etudiants = abonnes.stream().filter(a -> "ETUDIANT".equals(a.getStatut().name())).count();
			long enseignants = abonnes.stream().filter(a -> "ENSEIGNANT".equals(a.getStatut().name())).count();
			long personnel = abonnes.stream().filter(a -> "PERSONNEL".equals(a.getStatut().name())).count();
			
			request.setAttribute("statEtudiants", etudiants);
			request.setAttribute("statEnseignants", enseignants);
			request.setAttribute("statPersonnel", personnel);
			
			// Déterminer le contexte et forward vers la page JSP appropriée
			String requestURI = request.getRequestURI();
			if (requestURI.contains("/bibliothecaire/")) {
				// Contexte bibliothécaire - utiliser la page JSP dans le répertoire bibliothecaire
				request.getRequestDispatcher("/bibliothecaire/abonnes.jsp").forward(request, response);
			} else {
				// Contexte général - utiliser la page JSP principale
				request.getRequestDispatcher("/abonnes.jsp").forward(request, response);
			}
			
		} catch (Exception e) {
			// Gestion des erreurs
			request.setAttribute("error", "Erreur lors du chargement des abonnés: " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/error/500.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Redirection vers GET pour éviter la duplication de soumission
		doGet(request, response);
	}
}