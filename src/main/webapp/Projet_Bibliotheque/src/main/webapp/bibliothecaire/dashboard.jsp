<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Bibliothécaire - Gestion de Bibliothèque</title>
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
                            <a class="nav-link active" href="${pageContext.request.contextPath}/bibliothecaire/dashboard">
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
                            <i class="fas fa-tachometer-alt me-2"></i>Dashboard Bibliothécaire
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
                        <div class="card border-0 bg-success text-white">
                            <div class="card-body">
                                <div class="row align-items-center">
                                    <div class="col-md-8">
                                        <h4 class="card-title">
                                            <i class="fas fa-book-open me-2"></i>
                                            Bienvenue dans votre espace bibliothécaire
                                        </h4>
                                        <p class="card-text mb-0">
                                            Gérez les abonnés, les livres et les emprunts depuis ce tableau de bord.
                                        </p>
                                    </div>
                                    <div class="col-md-4 text-md-end">
                                        <i class="fas fa-user-tie fa-3x opacity-50"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Statistics cards -->
                <div class="row mb-4">
                    <div class="col-xl-3 col-md-6 mb-4">
                        <a href="${pageContext.request.contextPath}/abonnes" style="text-decoration:none; color:inherit;">
                            <div class="stat-card hover-shadow">
                                <div class="card-body">
                                    <div class="row align-items-center">
                                        <div class="col">
                                            <h6 class="text-muted mb-2">Abonnés Actifs</h6>
                                            <h4 class="text-primary mb-0">${abonnesActifsCount}</h4>
                                        </div>
                                        <div class="col-auto">
                                            <i class="fas fa-users fa-2x text-primary opacity-50"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>
                    
                    <div class="col-xl-3 col-md-6 mb-4">
                        <a href="${pageContext.request.contextPath}/livres" style="text-decoration:none; color:inherit;">
                            <div class="stat-card hover-shadow">
                                <div class="card-body">
                                    <div class="row align-items-center">
                                        <div class="col">
                                            <h6 class="text-muted mb-2">Livres Disponibles</h6>
                                            <h4 class="text-success mb-0">${livresDisponiblesCount}</h4>
                                        </div>
                                        <div class="col-auto">
                                            <i class="fas fa-book fa-2x text-success opacity-50"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>
                    
                    <div class="col-xl-3 col-md-6 mb-4">
                        <a href="${pageContext.request.contextPath}/emprunts" style="text-decoration:none; color:inherit;">
                            <div class="stat-card hover-shadow">
                                <div class="card-body">
                                    <div class="row align-items-center">
                                        <div class="col">
                                            <h6 class="text-muted mb-2">Emprunts En Cours</h6>
                                            <h4 class="text-warning mb-0">${empruntsEnCoursCount}</h4>
                                        </div>
                                        <div class="col-auto">
                                            <i class="fas fa-exchange-alt fa-2x text-warning opacity-50"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>
                    
                    <div class="col-xl-3 col-md-6 mb-4">
                        <a href="${pageContext.request.contextPath}/emprunts" style="text-decoration:none; color:inherit;">
                            <div class="stat-card hover-shadow">
                                <div class="card-body">
                                    <div class="row align-items-center">
                                        <div class="col">
                                            <h6 class="text-muted mb-2">Retards</h6>
                                            <h4 class="text-danger mb-0">${retardsCount}</h4>
                                        </div>
                                        <div class="col-auto">
                                            <i class="fas fa-exclamation-triangle fa-2x text-danger opacity-50"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>
                </div>

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
                                        <a href="${pageContext.request.contextPath}/abonne-form" 
                                           class="btn btn-outline-primary w-100">
                                            <i class="fas fa-user-plus me-2"></i>Nouvel Abonné
                                        </a>
                                    </div>
                                    <div class="col-md-3 mb-3">
                                        <a href="${pageContext.request.contextPath}/livre-form" 
                                           class="btn btn-outline-success w-100">
                                            <i class="fas fa-book-medical me-2"></i>Nouveau Livre
                                        </a>
                                    </div>
                                    <div class="col-md-3 mb-3">
                                        <a href="${pageContext.request.contextPath}/emprunt-form" 
                                           class="btn btn-outline-warning w-100">
                                            <i class="fas fa-hand-holding me-2"></i>Nouvel Emprunt
                                        </a>
                                    </div>
                                    <div class="col-md-3 mb-3">
                                        <a href="${pageContext.request.contextPath}/livre-search" 
                                           class="btn btn-outline-info w-100">
                                            <i class="fas fa-search me-2"></i>Rechercher
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Recent activity and alerts -->
                <div class="row">
                    <div class="col-md-6 mb-4">
                        <div class="card border-0 shadow-sm">
                            <div class="card-header bg-white">
                                <h5 class="mb-0">
                                    <i class="fas fa-clock me-2"></i>Activité récente
                                </h5>
                            </div>
                            <div class="card-body">
                                <div class="list-group list-group-flush">
                                    <div class="list-group-item border-0 px-0">
                                        <div class="d-flex align-items-center">
                                            <i class="fas fa-hand-holding text-success me-3"></i>
                                            <div>
                                                <h6 class="mb-1">Emprunt enregistré</h6>
                                                <small class="text-muted">Alice Durand - Java pour les débutants</small>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="list-group-item border-0 px-0">
                                        <div class="d-flex align-items-center">
                                            <i class="fas fa-undo text-primary me-3"></i>
                                            <div>
                                                <h6 class="mb-1">Retour enregistré</h6>
                                                <small class="text-muted">Bob Leroy - Algorithmes et structures</small>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="list-group-item border-0 px-0">
                                        <div class="d-flex align-items-center">
                                            <i class="fas fa-user-plus text-info me-3"></i>
                                            <div>
                                                <h6 class="mb-1">Nouvel abonné</h6>
                                                <small class="text-muted">Claire Moreau - Étudiante</small>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6 mb-4">
                        <div class="card border-0 shadow-sm">
                            <div class="card-header bg-white">
                                <h5 class="mb-0">
                                    <i class="fas fa-exclamation-triangle me-2"></i>Alertes et retards
                                </h5>
                            </div>
                            <div class="card-body">
                                <div class="list-group list-group-flush">
                                    <div class="list-group-item border-0 px-0">
                                        <div class="d-flex align-items-center">
                                            <i class="fas fa-exclamation-circle text-warning me-3"></i>
                                            <div>
                                                <h6 class="mb-1">Retard de 3 jours</h6>
                                                <small class="text-muted">David Petit - Physique quantique</small>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="list-group-item border-0 px-0">
                                        <div class="d-flex align-items-center">
                                            <i class="fas fa-exclamation-circle text-warning me-3"></i>
                                            <div>
                                                <h6 class="mb-1">Retard de 1 jour</h6>
                                                <small class="text-muted">Emma Rousseau - Histoire de France</small>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="list-group-item border-0 px-0">
                                        <div class="d-flex align-items-center">
                                            <i class="fas fa-info-circle text-info me-3"></i>
                                            <div>
                                                <h6 class="mb-1">5 emprunts à retourner aujourd'hui</h6>
                                                <small class="text-muted">Action recommandée</small>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Quick search -->
                <div class="row">
                    <div class="col-12">
                        <div class="card border-0 shadow-sm">
                            <div class="card-header bg-white">
                                <h5 class="mb-0">
                                    <i class="fas fa-search me-2"></i>Recherche rapide
                                </h5>
                            </div>
                            <div class="card-body">
                                <form action="${pageContext.request.contextPath}/livre-search" method="get">
                                    <div class="row">
                                        <div class="col-md-4 mb-3">
                                            <input type="text" class="form-control" name="titre" 
                                                   placeholder="Rechercher par titre...">
                                        </div>
                                        <div class="col-md-4 mb-3">
                                            <input type="text" class="form-control" name="auteur" 
                                                   placeholder="Rechercher par auteur...">
                                        </div>
                                        <div class="col-md-4 mb-3">
                                            <button type="submit" class="btn btn-primary w-100">
                                                <i class="fas fa-search me-2"></i>Rechercher
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
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