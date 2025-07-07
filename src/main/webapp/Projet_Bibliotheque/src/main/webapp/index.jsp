<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion de Bibliothèque Universitaire</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .hero-section {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 100px 0;
        }
        .feature-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
            height: 100%;
        }
        .feature-card:hover {
            transform: translateY(-5px);
        }
        .navbar-brand {
            font-weight: bold;
            font-size: 1.5rem;
        }
        .btn-custom {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            color: white;
            padding: 12px 30px;
            border-radius: 25px;
            font-weight: 600;
            transition: all 0.3s ease;
        }
        .btn-custom:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
            color: white;
        }
        .stats-section {
            background-color: #f8f9fa;
            padding: 60px 0;
        }
        .stat-item {
            text-align: center;
            padding: 20px;
        }
        .stat-number {
            font-size: 2.5rem;
            font-weight: bold;
            color: #667eea;
        }
        .stat-label {
            color: #6c757d;
            font-size: 1.1rem;
        }
        .hover-shadow:hover {
            box-shadow: 0 8px 24px rgba(86,171,47,0.15);
            transform: translateY(-4px) scale(1.03);
            transition: all 0.2s;
        }
    </style>
</head>
<body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                <i class="fas fa-book-open me-2"></i>Bibliothèque Universitaire
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="#features">Fonctionnalités</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#about">À propos</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/login.jsp">
                            <i class="fas fa-sign-in-alt me-1"></i>Connexion
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Hero Section -->
    <section class="hero-section">
        <div class="container text-center">
            <h1 class="display-4 mb-4">
                <i class="fas fa-graduation-cap me-3"></i>
                Gestion de Bibliothèque Universitaire
            </h1>
            <p class="lead mb-5">
                Une solution complète pour la gestion des abonnés, des livres et des emprunts 
                dans votre bibliothèque universitaire.
            </p>
            <div class="row justify-content-center">
                <div class="col-md-4 mb-3">
                    <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-custom btn-lg w-100">
                        <i class="fas fa-sign-in-alt me-2"></i>Se connecter
                    </a>
                </div>
                <div class="col-md-4 mb-3">
                    <a href="#features" class="btn btn-outline-light btn-lg w-100">
                        <i class="fas fa-info-circle me-2"></i>En savoir plus
                    </a>
                </div>
            </div>
        </div>
    </section>

    <!-- Features Section -->
    <section id="features" class="py-5">
        <div class="container">
            <h2 class="text-center mb-5">
                <i class="fas fa-star me-2"></i>Fonctionnalités principales
            </h2>
            <div class="row g-4">
                <div class="col-md-4">
                    <a href="${pageContext.request.contextPath}/login.jsp" style="text-decoration:none; color:inherit;">
                        <div class="card p-4 text-center shadow-sm hover-shadow">
                            <i class="fas fa-users fa-3x mb-3 text-primary"></i>
                            <h3>Gestion des Abonnés</h3>
                            <p>Inscription, modification et suivi des abonnés...</p>
                        </div>
                    </a>
                </div>
                <div class="col-md-4">
                    <a href="${pageContext.request.contextPath}/login.jsp" style="text-decoration:none; color:inherit;">
                        <div class="card p-4 text-center shadow-sm hover-shadow">
                            <i class="fas fa-book fa-3x mb-3 text-success"></i>
                            <h3>Gestion des Livres</h3>
                            <p>Catalogue complet avec recherche avancée...</p>
                        </div>
                    </a>
                </div>
                <div class="col-md-4">
                    <a href="${pageContext.request.contextPath}/login.jsp" style="text-decoration:none; color:inherit;">
                        <div class="card p-4 text-center shadow-sm hover-shadow">
                            <i class="fas fa-exchange-alt fa-3x mb-3 text-warning"></i>
                            <h3>Emprunts et Retours</h3>
                            <p>Suivi complet des emprunts avec dates de retour...</p>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </section>

    <!-- Stats Section -->
    <section class="stats-section">
        <div class="container">
            <h2 class="text-center mb-5">
                <i class="fas fa-chart-bar me-2"></i>Statistiques de la bibliothèque
            </h2>
            <div class="row">
                <div class="col-md-3">
                    <div class="stat-item">
                        <div class="stat-number">${nbAbonnes}</div>
                        <div class="stat-label">Abonnés actifs</div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="stat-item">
                        <div class="stat-number">${nbLivres}</div>
                        <div class="stat-label">Livres disponibles</div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="stat-item">
                        <div class="stat-number">${nbExemplaires}</div>
                        <div class="stat-label">Exemplaires</div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="stat-item">
                        <div class="stat-number">${nbEmprunts}</div>
                        <div class="stat-label">Emprunts en cours</div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- About Section -->
    <section id="about" class="py-5">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-md-6">
                    <h2 class="mb-4">
                        <i class="fas fa-info-circle me-2"></i>À propos du projet
                    </h2>
                    <p class="lead">
                        Cette application de gestion de bibliothèque universitaire a été développée 
                        dans le cadre d'un projet académique utilisant les technologies Jakarta EE.
                    </p>
                    <p>
                        L'application permet aux bibliothécaires de gérer efficacement :
                    </p>
                    <ul class="list-unstyled">
                        <li><i class="fas fa-check text-success me-2"></i>Les abonnés et leurs informations</li>
                        <li><i class="fas fa-check text-success me-2"></i>Le catalogue des livres</li>
                        <li><i class="fas fa-check text-success me-2"></i>Les exemplaires disponibles</li>
                        <li><i class="fas fa-check text-success me-2"></i>Les emprunts et retours</li>
                        <li><i class="fas fa-check text-success me-2"></i>Les statistiques et rapports</li>
                    </ul>
                </div>
                <div class="col-md-6 text-center">
                    <i class="fas fa-university fa-8x text-primary opacity-50"></i>
                </div>
            </div>
        </div>
    </section>

    <!-- Footer -->
    <footer class="bg-dark text-white py-4">
        <div class="container text-center">
            <p class="mb-0">
                <i class="fas fa-copyright me-1"></i>
                2025 Gestion de Bibliothèque Universitaire - Projet Jakarta EE
            </p>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>