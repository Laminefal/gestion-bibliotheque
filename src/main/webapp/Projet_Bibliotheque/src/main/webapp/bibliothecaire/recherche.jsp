<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Recherche - Bibliothécaire</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .sidebar {
            min-height: 100vh;
            background: linear-gradient(135deg, #28a745 0%, #20c997 100%);
        }
        .sidebar .nav-link {
            color: rgba(255, 255, 255, 0.8);
            padding: 12px 20px;
            border-radius: 8px;
            margin: 2px 0;
            transition: all 0.3s ease;
        }
        .sidebar .nav-link:hover,
        .sidebar .nav-link.active {
            color: white;
            background: rgba(255, 255, 255, 0.1);
        }
        .main-content {
            background-color: #f8f9fa;
            min-height: 100vh;
        }
        .search-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .result-card {
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
        }
        .result-card:hover {
            transform: translateY(-2px);
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <nav class="col-md-3 col-lg-2 d-md-block sidebar collapse">
                <div class="position-sticky pt-3">
                    <div class="text-center mb-4">
                        <i class="fas fa-user-tie fa-2x text-white mb-2"></i>
                        <h5 class="text-white">Bibliothécaire</h5>
                        <small class="text-white-50">${sessionScope.nomComplet}</small>
                    </div>
                    
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/bibliothecaire/dashboard.jsp">
                                <i class="fas fa-tachometer-alt me-2"></i>Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/abonnes">
                                <i class="fas fa-users me-2"></i>Gestion Abonnés
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/livres">
                                <i class="fas fa-book me-2"></i>Gestion Livres
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/emprunts">
                                <i class="fas fa-exchange-alt me-2"></i>Emprunts/Retours
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/bibliothecaire/recherche.jsp">
                                <i class="fas fa-search me-2"></i>Recherche
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/bibliothecaire/statistiques">
                                <i class="fas fa-chart-bar me-2"></i>Statistiques
                            </a>
                        </li>
                        <hr class="text-white-50">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/logout">
                                <i class="fas fa-sign-out-alt me-2"></i>Déconnexion
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>

            <!-- Main content -->
            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 main-content">
                <!-- Top navbar -->
                <nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm mb-4">
                    <div class="container-fluid">
                        <span class="navbar-brand">
                            <i class="fas fa-search me-2"></i>Recherche
                        </span>
                        <div class="navbar-nav ms-auto">
                            <span class="navbar-text me-3">
                                <i class="fas fa-clock me-1"></i>
                                <span id="current-time"></span>
                            </span>
                        </div>
                    </div>
                </nav>

                <!-- Search form -->
                <div class="row mb-4">
                    <div class="col-12">
                        <div class="search-card">
                            <div class="card-body">
                                <h5 class="card-title mb-4">
                                    <i class="fas fa-search me-2"></i>Recherche avancée
                                </h5>
                                
                                <form action="${pageContext.request.contextPath}/livre-search" method="get">
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label for="q" class="form-label">Terme de recherche</label>
                                            <input type="text" class="form-control" id="q" name="q" 
                                                   placeholder="Titre, auteur, ISBN..." value="${searchTerm}">
                                        </div>
                                        <div class="col-md-3 mb-3">
                                            <label for="domaine" class="form-label">Domaine</label>
                                            <select class="form-select" id="domaine" name="domaine">
                                                <option value="">Tous les domaines</option>
                                                <option value="INFORMATIQUE" ${domaineFilter == 'INFORMATIQUE' ? 'selected' : ''}>Informatique</option>
                                                <option value="LITTERATURE" ${domaineFilter == 'LITTERATURE' ? 'selected' : ''}>Littérature</option>
                                                <option value="SCIENCES" ${domaineFilter == 'SCIENCES' ? 'selected' : ''}>Sciences</option>
                                                <option value="HISTOIRE" ${domaineFilter == 'HISTOIRE' ? 'selected' : ''}>Histoire</option>
                                                <option value="PHILOSOPHIE" ${domaineFilter == 'PHILOSOPHIE' ? 'selected' : ''}>Philosophie</option>
                                                <option value="ART" ${domaineFilter == 'ART' ? 'selected' : ''}>Art</option>
                                            </select>
                                        </div>
                                        <div class="col-md-3 mb-3">
                                            <label for="niveau" class="form-label">Niveau</label>
                                            <select class="form-select" id="niveau" name="niveau">
                                                <option value="">Tous les niveaux</option>
                                                <option value="DEBUTANT" ${niveauFilter == 'DEBUTANT' ? 'selected' : ''}>Débutant</option>
                                                <option value="INTERMEDIAIRE" ${niveauFilter == 'INTERMEDIAIRE' ? 'selected' : ''}>Intermédiaire</option>
                                                <option value="AVANCE" ${niveauFilter == 'AVANCE' ? 'selected' : ''}>Avancé</option>
                                                <option value="EXPERT" ${niveauFilter == 'EXPERT' ? 'selected' : ''}>Expert</option>
                                            </select>
                                        </div>
                                    </div>
                                    
                                    <div class="row">
                                        <div class="col-md-3 mb-3">
                                            <label for="annee" class="form-label">Année de publication</label>
                                            <input type="number" class="form-control" id="annee" name="annee" 
                                                   placeholder="Ex: 2020" value="${anneeFilter}">
                                        </div>
                                        <div class="col-md-3 mb-3">
                                            <label for="disponibilite" class="form-label">Disponibilité</label>
                                            <select class="form-select" id="disponibilite" name="disponibilite">
                                                <option value="">Tous</option>
                                                <option value="disponible" ${disponibiliteFilter == 'disponible' ? 'selected' : ''}>Disponible</option>
                                                <option value="emprunte" ${disponibiliteFilter == 'emprunte' ? 'selected' : ''}>Emprunté</option>
                                            </select>
                                        </div>
                                        <div class="col-md-6 mb-3 d-flex align-items-end">
                                            <button type="submit" class="btn btn-primary me-2">
                                                <i class="fas fa-search me-2"></i>Rechercher
                                            </button>
                                            <a href="${pageContext.request.contextPath}/bibliothecaire/recherche.jsp" class="btn btn-outline-secondary">
                                                <i class="fas fa-undo me-2"></i>Réinitialiser
                                            </a>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Results -->
                <c:if test="${not empty livres}">
                    <div class="row mb-4">
                        <div class="col-12">
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <h5>
                                    <i class="fas fa-list me-2"></i>Résultats de la recherche
                                </h5>
                                <span class="badge bg-primary">${nombreResultats} résultat(s)</span>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <c:forEach var="livre" items="${livres}">
                            <div class="col-md-6 col-lg-4 mb-4">
                                <div class="result-card">
                                    <div class="card-body">
                                        <div class="d-flex justify-content-between align-items-start mb-2">
                                            <h6 class="card-title mb-1">${livre.titre}</h6>
                                            <span class="badge ${livre.estDisponible() ? 'bg-success' : 'bg-warning'}">
                                                ${livre.estDisponible() ? 'Disponible' : 'Emprunté'}
                                            </span>
                                        </div>
                                        <p class="card-text text-muted mb-2">
                                            <i class="fas fa-user me-1"></i>${livre.auteur}
                                        </p>
                                        <p class="card-text small mb-2">
                                            <i class="fas fa-tag me-1"></i>${livre.domaine.nom} | 
                                            <i class="fas fa-star me-1"></i>${livre.niveauRequis}
                                        </p>
                                        <p class="card-text small mb-3">
                                            <i class="fas fa-calendar me-1"></i>${livre.anneePublication} | 
                                            <i class="fas fa-barcode me-1"></i>${livre.isbn}
                                        </p>
                                        <div class="d-flex justify-content-between">
                                            <a href="${pageContext.request.contextPath}/livre-form?id=${livre.id}" 
                                               class="btn btn-sm btn-outline-primary">
                                                <i class="fas fa-edit me-1"></i>Modifier
                                            </a>
                                            <a href="${pageContext.request.contextPath}/exemplaires?livreId=${livre.id}" 
                                               class="btn btn-sm btn-outline-info">
                                                <i class="fas fa-copy me-1"></i>Exemplaires
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>

                <!-- No results message -->
                <c:if test="${not empty searchTerm && empty livres}">
                    <div class="row">
                        <div class="col-12">
                            <div class="alert alert-info text-center">
                                <i class="fas fa-info-circle me-2"></i>
                                Aucun livre trouvé pour votre recherche.
                            </div>
                        </div>
                    </div>
                </c:if>

                <!-- Error message -->
                <c:if test="${not empty error}">
                    <div class="row">
                        <div class="col-12">
                            <div class="alert alert-danger">
                                <i class="fas fa-exclamation-triangle me-2"></i>
                                ${error}
                            </div>
                        </div>
                    </div>
                </c:if>
            </main>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Afficher l'heure actuelle
        function updateTime() {
            const now = new Date();
            const timeString = now.toLocaleTimeString('fr-FR');
            document.getElementById('current-time').textContent = timeString;
        }
        
        updateTime();
        setInterval(updateTime, 1000);
    </script>
</body>
</html> 