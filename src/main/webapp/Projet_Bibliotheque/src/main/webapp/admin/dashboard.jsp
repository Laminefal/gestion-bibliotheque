<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Administrateur - Gestion de Bibliothèque</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
   <style>
    .sidebar {
        min-height: 100vh;
        background: linear-gradient(135deg, #56ab2f 0%, #a8e063 100%);
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
    .stat-card {
        background: white;
        border-radius: 15px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        transition: transform 0.3s ease;
    }
    .stat-card:hover {
        transform: translateY(-5px);
    }
    .navbar-brand {
        font-weight: bold;
    }
    .profile-photo {
        border-radius: 50%;
        object-fit: cover;
        border: 2px solid rgba(255,255,255,0.3);
    }
    .profile-photo-default {
        border-radius: 50%;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        display: flex;
        align-items: center;
        justify-content: center;
        color: white;
        border: 2px solid rgba(255,255,255,0.3);
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
                        <c:choose>
                            <c:when test="${not empty sessionScope.photo and sessionScope.photo != 'default-avatar.png'}">
                                <img src="${pageContext.request.contextPath}/uploads/photos/${sessionScope.photo}" 
                                     alt="Photo de profil" 
                                     class="profile-photo mb-2"
                                     style="width: 80px; height: 80px;"
                                     onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';">
                                <div class="profile-photo-default mb-2" style="display: none; width: 80px; height: 80px; font-size: 30px;">
                                    <i class="fas fa-user-shield"></i>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="profile-photo-default mb-2" style="width: 80px; height: 80px; font-size: 30px;">
                                    <i class="fas fa-user-shield"></i>
                                </div>
                            </c:otherwise>
                        </c:choose>
                        <h5 class="text-white">Administrateur</h5>
                        <small class="text-white-50">${sessionScope.nomComplet}</small>
                    </div>
                    
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/admin/dashboard.jsp">
                                <i class="fas fa-tachometer-alt me-2"></i>Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/bibliothecaires.jsp">
                                <i class="fas fa-users me-2"></i>Gestion Bibliothécaires
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/statistiques">
                                <i class="fas fa-chart-bar me-2"></i>Statistiques
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/parametres.jsp">
                                <i class="fas fa-cog me-2"></i>Paramètres
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
                            <i class="fas fa-tachometer-alt me-2"></i>Dashboard Administrateur
                        </span>
                        <div class="navbar-nav ms-auto">
                            <span class="navbar-text me-3">
                                <i class="fas fa-clock me-1"></i>
                                <span id="current-time"></span>
                            </span>
                        </div>
                    </div>
                </nav>

                <!-- Welcome section -->
                <div class="row mb-4">
                    <div class="col-12">
                        <div class="card border-0 bg-primary text-white">
                            <div class="card-body">
                                <div class="row align-items-center">
                                    <div class="col-md-8">
                                        <h4 class="card-title">
                                            <i class="fas fa-book-open me-2"></i>
                                            Bienvenue dans votre espace administrateur
                                        </h4>
                                        <p class="card-text mb-0">
                                            Gérez votre bibliothèque universitaire depuis ce tableau de bord centralisé.
                                        </p>
                                    </div>
                                    <div class="col-md-4 text-md-end">
                                        <i class="fas fa-shield-alt fa-3x opacity-50"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Bloc unique pour l'admin : Gestion des Bibliothécaires -->
                <div class="row mb-4">
                  <div class="col-md-3 mb-4">
                    <a href="${pageContext.request.contextPath}/abonnes" style="text-decoration:none; color:inherit;">
                      <div class="card p-4 text-center shadow-sm hover-shadow">
                        <i class="fas fa-users fa-3x mb-3 text-primary"></i>
                        <h4 class="mb-2">Gestion des Abonnés</h4>
                        <p class="mb-1">Inscription, modification et suivi des abonnés.</p>
                        <span class="fw-bold">${abonnesCount} abonnés</span>
                      </div>
                    </a>
                  </div>
                  <div class="col-md-3 mb-4">
                    <a href="${pageContext.request.contextPath}/livres" style="text-decoration:none; color:inherit;">
                      <div class="card p-4 text-center shadow-sm hover-shadow">
                        <i class="fas fa-book fa-3x mb-3 text-success"></i>
                        <h4 class="mb-2">Gestion des Livres</h4>
                        <p class="mb-1">Catalogue, recherche et gestion des livres.</p>
                        <span class="fw-bold">${livresCount} livres</span>
                      </div>
                    </a>
                  </div>
                  <div class="col-md-3 mb-4">
                    <a href="${pageContext.request.contextPath}/emprunts" style="text-decoration:none; color:inherit;">
                      <div class="card p-4 text-center shadow-sm hover-shadow">
                        <i class="fas fa-exchange-alt fa-3x mb-3 text-warning"></i>
                        <h4 class="mb-2">Emprunts et Retours</h4>
                        <p class="mb-1">Suivi complet des emprunts et retours.</p>
                        <span class="fw-bold">${empruntsActifsCount} en cours</span>
                      </div>
                    </a>
                  </div>
                  <div class="col-md-3 mb-4">
                    <a href="${pageContext.request.contextPath}/admin/bibliothecaires.jsp" style="text-decoration:none; color:inherit;">
                      <div class="card p-4 text-center shadow-sm hover-shadow">
                        <i class="fas fa-user-tie fa-3x mb-3 text-info"></i>
                        <h4 class="mb-2">Gestion des Bibliothécaires</h4>
                        <p class="mb-1">Ajout, modification et suivi des bibliothécaires.</p>
                        <span class="fw-bold">${bibliothecairesCount} bibliothécaires</span>
                      </div>
                    </a>
                  </div>
                </div>
                <!-- Fin du bloc unique -->

                <!-- Quick actions -->
                <div class="row mb-4">
                    <div class="col-12">
                        <div class="card border-0 shadow-sm">
                            <div class="card-header bg-white">
                                <h5 class="mb-0">
                                    <i class="fas fa-bolt me-2"></i>Actions rapides
                                </h5>
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-3 mb-3">
                                        <a href="${pageContext.request.contextPath}/admin/bibliothecaires.jsp" 
                                           class="btn btn-outline-primary w-100">
                                            <i class="fas fa-user-plus me-2"></i>Ajouter Bibliothécaire
                                        </a>
                                    </div>
                                    <div class="col-md-3 mb-3">
                                        <a href="${pageContext.request.contextPath}/admin/statistiques" 
                                           class="btn btn-outline-success w-100">
                                            <i class="fas fa-chart-line me-2"></i>Voir Statistiques
                                        </a>
                                    </div>
                                    <div class="col-md-3 mb-3">
                                        <a href="${pageContext.request.contextPath}/admin/rapports.jsp" 
                                           class="btn btn-outline-warning w-100">
                                            <i class="fas fa-file-alt me-2"></i>Générer Rapports
                                        </a>
                                    </div>
                                    <div class="col-md-3 mb-3">
                                        <a href="${pageContext.request.contextPath}/admin/parametres.jsp" 
                                           class="btn btn-outline-info w-100">
                                            <i class="fas fa-cog me-2"></i>Paramètres
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
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