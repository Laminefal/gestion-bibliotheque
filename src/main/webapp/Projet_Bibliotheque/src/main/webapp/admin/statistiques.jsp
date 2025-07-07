<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    ObjectMapper mapper = new ObjectMapper();
    String statsParDomaineJson = mapper.writeValueAsString(request.getAttribute("statsParDomaine"));
    String statsParNiveauJson = mapper.writeValueAsString(request.getAttribute("statsParNiveau"));
    String empruntsParMoisJson = mapper.writeValueAsString(request.getAttribute("empruntsParMois"));
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Statistiques - Administration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f8f9fa; }
        .sidebar {  background: linear-gradient(135deg, #56ab2f 0%, #a8e063 100%) !important; }
        .sidebar .nav-link { color: rgba(255,255,255,0.8); transition: all 0.3s; }
        .sidebar .nav-link:hover, .sidebar .nav-link.active { 
            color: white; background: rgba(255,255,255,0.1); border-radius: 8px; 
        }
        .card { border-radius: 12px; box-shadow: 0 4px 6px rgba(0,0,0,0.07); }
        .chart-container { position: relative; height: 300px; margin: 20px 0; }
        .stats-card { transition: transform 0.2s; }
        .stats-card:hover { transform: translateY(-2px); }
        .stats-card.primary { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; }
        .stats-card.success { background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%); color: white; }
        .stats-card.warning { background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%); color: white; }
        .stats-card.info { background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%); color: white; }
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
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/dashboard.jsp">
                                <i class="fas fa-tachometer-alt me-2"></i>Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/bibliothecaires.jsp">
                                <i class="fas fa-users me-2"></i>Gestion Bibliothécaires
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/admin/statistiques">
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
                <div class="d-flex justify-content-between align-items-center pt-3 pb-2 mb-4 border-bottom">
                    <h2 class="h4"><i class="fas fa-chart-bar me-2"></i>Statistiques de la Bibliothèque</h2>
                </div>

                <!-- Cartes de statistiques principales -->
                <div class="row mb-4">
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card stats-card primary border-0">
                            <div class="card-body text-center">
                                <i class="fas fa-users fa-2x mb-2 opacity-75"></i>
                                <h6 class="mb-2">Total Abonnés</h6>
                                <h3 class="mb-0">${abonnesCount}</h3>
                                <small class="opacity-75">Utilisateurs actifs</small>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card stats-card success border-0">
                            <div class="card-body text-center">
                                <i class="fas fa-book fa-2x mb-2 opacity-75"></i>
                                <h6 class="mb-2">Total Livres</h6>
                                <h3 class="mb-0">${livresCount}</h3>
                                <small class="opacity-75">Titres disponibles</small>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card stats-card warning border-0">
                            <div class="card-body text-center">
                                <i class="fas fa-exchange-alt fa-2x mb-2 opacity-75"></i>
                                <h6 class="mb-2">Emprunts Actifs</h6>
                                <h3 class="mb-0">${empruntsActifsCount}</h3>
                                <small class="opacity-75">En cours</small>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card stats-card info border-0">
                            <div class="card-body text-center">
                                <i class="fas fa-user-tie fa-2x mb-2 opacity-75"></i>
                                <h6 class="mb-2">Bibliothécaires</h6>
                                <h3 class="mb-0">${bibliothecairesCount}</h3>
                                <small class="opacity-75">Personnel</small>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Graphiques -->
                <div class="row">
                    <!-- Graphique en barres - Livres par domaine -->
                    <div class="col-lg-6 mb-4">
                        <div class="card border-0 shadow-sm">
                            <div class="card-header bg-white">
                                <h5 class="mb-0"><i class="fas fa-chart-bar me-2"></i>Livres par Domaine</h5>
                            </div>
                            <div class="card-body">
                                <div class="chart-container">
                                    <canvas id="domaineChart"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Graphique en barres - Livres par niveau -->
                    <div class="col-lg-6 mb-4">
                        <div class="card border-0 shadow-sm">
                            <div class="card-header bg-white">
                                <h5 class="mb-0"><i class="fas fa-chart-bar me-2"></i>Livres par Niveau</h5>
                            </div>
                            <div class="card-body">
                                <div class="chart-container">
                                    <canvas id="niveauChart"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Graphique en barres - Emprunts par mois -->
                <div class="row">
                    <div class="col-12 mb-4">
                        <div class="card border-0 shadow-sm">
                            <div class="card-header bg-white">
                                <h5 class="mb-0"><i class="fas fa-chart-line me-2"></i>Évolution des Emprunts</h5>
                            </div>
                            <div class="card-body">
                                <div class="chart-container" style="height: 400px;">
                                    <canvas id="empruntsChart"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Tableaux de statistiques détaillées -->
                <div class="row mt-4">
    <div class="col-md-6 mb-4">
        <div class="card p-3 shadow-sm h-100">
            <div class="section-title mb-3"><i class="fas fa-table me-2"></i>Statistiques par Niveau</div>
            <div class="table-responsive">
                <table class="table table-striped table-hover align-middle mb-0">
                    <thead class="table-success">
                        <tr>
                            <th>Niveau</th>
                            <th>Nombre</th>
                            <th>Pourcentage</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="entry" items="${statsParNiveau}">
                            <tr>
                                <td class="fw-semibold">${entry.key}</td>
                                <td>${entry.value}</td>
                                <td>
                                    <span class="badge bg-info text-dark">
                                        <fmt:formatNumber value="${(entry.value * 100.0) / livresCount}" maxFractionDigits="1"/>%
                                    </span>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="col-md-6 mb-4">
        <div class="card p-3 shadow-sm h-100">
            <div class="section-title mb-3"><i class="fas fa-table me-2"></i>Statistiques par Domaine</div>
            <div class="table-responsive">
                <table class="table table-striped table-hover align-middle mb-0">
                    <thead class="table-success">
                        <tr>
                            <th>Domaine</th>
                            <th>Nombre</th>
                            <th>Pourcentage</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="entry" items="${statsParDomaine}">
                            <tr>
                                <td class="fw-semibold">${entry.key}</td>
                                <td>${entry.value}</td>
                                <td>
                                    <span class="badge bg-info text-dark">
                                        <fmt:formatNumber value="${(entry.value * 100.0) / livresCount}" maxFractionDigits="1"/>%
                                    </span>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
            </main>
        </div>
    </div>

    <!-- Chart.js -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
const statsParDomaine = <%= statsParDomaineJson %>;
const statsParNiveau = <%= statsParNiveauJson %>;
const empruntsParMois = <%= empruntsParMoisJson %>;

const domaineLabels = Object.keys(statsParDomaine);
const domaineValues = Object.values(statsParDomaine);

const niveauLabels = Object.keys(statsParNiveau);
const niveauValues = Object.values(statsParNiveau);

new Chart(document.getElementById('domaineChart'), {
    type: 'bar',
    data: {
        labels: domaineLabels,
        datasets: [{
            label: 'Livres par domaine',
            data: domaineValues,
            backgroundColor: 'rgba(54, 162, 235, 0.7)'
        }]
    },
    options: {
        responsive: true,
        plugins: {
            legend: { display: false }
        },
        scales: {
            y: { beginAtZero: true }
        }
    }
});

new Chart(document.getElementById('niveauChart'), {
    type: 'bar',
    data: {
        labels: niveauLabels,
        datasets: [{
            label: 'Livres par niveau',
            data: niveauValues,
            backgroundColor: 'rgba(255, 99, 132, 0.7)'
        }]
    },
    options: {
        responsive: true,
        plugins: {
            legend: { display: false }
        },
        scales: {
            y: { beginAtZero: true }
        }
    }
});

const empruntsLabels = Object.keys(empruntsParMois);
const empruntsValues = Object.values(empruntsParMois);

new Chart(document.getElementById('empruntsChart'), {
    type: 'line',
    data: {
        labels: empruntsLabels,
        datasets: [{
            label: 'Emprunts par mois',
            data: empruntsValues,
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 2,
            fill: true,
            tension: 0.3,
            pointRadius: 5,
            pointHoverRadius: 7
        }]
    },
    options: {
        responsive: true,
        plugins: {
            legend: { display: true }
        },
        scales: {
            y: { beginAtZero: true }
        }
    }
});
</script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
       