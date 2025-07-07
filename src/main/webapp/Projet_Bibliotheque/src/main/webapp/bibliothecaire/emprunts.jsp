<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Emprunts - Bibliothécaire</title>
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
        .table thead th { 
            position: sticky; 
            top: 0; 
            background: #e9f7ef; 
            z-index: 1; 
        }
        .table td, .table th { 
            vertical-align: middle; 
        }
        .btn-group .btn { 
            margin-right: 0.2rem; 
        }
        .table-responsive { 
            box-shadow: 0 2px 8px rgba(0,0,0,0.07); 
            border-radius: 10px; 
            background: #fff; 
        }
        .badge { 
            font-size: 0.95em; 
        }
        .stat-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
        }
        .stat-card:hover {
            transform: translateY(-5px);
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
                            <a class="nav-link active" href="${pageContext.request.contextPath}/emprunts">
                                <i class="fas fa-exchange-alt me-2"></i>Emprunts/Retours
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/bibliothecaire/recherche.jsp">
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
                            <i class="fas fa-exchange-alt me-2"></i>Gestion des Emprunts
                        </span>
                        <div class="navbar-nav ms-auto">
                            <span class="navbar-text me-3">
                                <i class="fas fa-clock me-1"></i>
                                <span id="current-time"></span>
                            </span>
                        </div>
                    </div>
                </nav>

                <!-- Messages de notification -->
                <c:if test="${param.success == 'true'}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <i class="fas fa-check-circle me-2"></i>
                        <strong>Succès !</strong> ${param.message}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <c:if test="${param.error == 'true'}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="fas fa-exclamation-triangle me-2"></i>
                        <strong>Erreur !</strong> ${param.message}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <!-- Barre d'actions -->
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <div>
                        <h5 class="mb-0">
                            <i class="fas fa-list me-2"></i>Liste des emprunts
                            <span class="badge bg-secondary ms-2">${emprunts.size()}</span>
                        </h5>
                    </div>
                    <div>
                        <a href="${pageContext.request.contextPath}/emprunt-form" class="btn btn-success">
                            <i class="fas fa-plus me-2"></i>Nouvel emprunt
                        </a>
                    </div>
                </div>

                <!-- Tableau des emprunts -->
                <div class="table-responsive">
                    <table class="table table-striped table-hover align-middle">
                        <thead class="table-success">
                            <tr>
                                <th>Abonné</th>
                                <th>Exemplaire</th>
                                <th>Livre</th>
                                <th>Date emprunt</th>
                                <th>Date retour prévue</th>
                                <th>Date retour effective</th>
                                <th>Statut</th>
                                <th style="min-width:110px;">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty emprunts}">
                                    <tr>
                                        <td colspan="8" class="text-center py-4">
                                            <i class="fas fa-inbox fa-2x text-muted mb-3"></i>
                                            <p class="text-muted">Aucun emprunt trouvé</p>
                                            <a href="${pageContext.request.contextPath}/emprunt-form" class="btn btn-sm btn-success">
                                                <i class="fas fa-plus me-1"></i>Créer le premier emprunt
                                            </a>
                                        </td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="emprunt" items="${emprunts}">
                                        <tr>
                                            <td>
                                                <strong>${emprunt.abonne.prenom} ${emprunt.abonne.nom}</strong>
                                                <br>
                                                <small class="text-muted">${emprunt.abonne.numeroAbonnement}</small>
                                            </td>
                                            <td>
                                                <span class="badge bg-light text-dark">${emprunt.exemplaire.codeExemplaire}</span>
                                            </td>
                                            <td>
                                                <strong>${emprunt.exemplaire.livre.titre}</strong>
                                                <br>
                                                <small class="text-muted">${emprunt.exemplaire.livre.auteurs}</small>
                                            </td>
                                            <td>${emprunt.dateEmpruntFormatted}</td>
                                            <td>${emprunt.dateRetourPrevueFormatted}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty emprunt.dateRetourEffective}">
                                                        ${emprunt.dateRetourEffectiveFormatted}
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="text-muted">-</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${emprunt.statut == 'EN_COURS'}">
                                                        <span class="badge bg-warning">En cours</span>
                                                    </c:when>
                                                    <c:when test="${emprunt.statut == 'RETOURNE'}">
                                                        <span class="badge bg-success">Retourné</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-secondary">${emprunt.statut}</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <div class="btn-group" role="group">
                                                    <a href="${pageContext.request.contextPath}/emprunt-form?id=${emprunt.id}" 
                                                       class="btn btn-sm btn-outline-primary" 
                                                       title="Modifier">
                                                        <i class="fas fa-edit"></i>
                                                    </a>
                                                    <form action="${pageContext.request.contextPath}/emprunt-delete" method="post" style="display:inline;">
                                                        <input type="hidden" name="id" value="${emprunt.id}">
                                                        <button type="submit" 
                                                                class="btn btn-sm btn-outline-danger" 
                                                                onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet emprunt ?');" 
                                                                title="Supprimer">
                                                            <i class="fas fa-trash"></i>
                                                        </button>
                                                    </form>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>

                <!-- Statistiques -->
                <div class="row mt-4">
                    <div class="col-md-4">
                        <div class="stat-card">
                            <div class="card-body text-center">
                                <i class="fas fa-exchange-alt fa-2x text-primary mb-2"></i>
                                <h5>Total Emprunts</h5>
                                <h3 class="text-primary">${emprunts.size()}</h3>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="stat-card">
                            <div class="card-body text-center">
                                <i class="fas fa-clock fa-2x text-warning mb-2"></i>
                                <h5>En Cours</h5>
                                <h3 class="text-warning">${emprunts.stream().filter(e -> e.statut == 'EN_COURS').count()}</h3>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="stat-card">
                            <div class="card-body text-center">
                                <i class="fas fa-check-circle fa-2x text-success mb-2"></i>
                                <h5>Retournés</h5>
                                <h3 class="text-success">${emprunts.stream().filter(e -> e.statut == 'RETOURNE').count()}</h3>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Affichage de l'heure actuelle
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