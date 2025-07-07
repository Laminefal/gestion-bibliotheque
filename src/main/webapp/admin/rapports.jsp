<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rapports - Administration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .sidebar {
            min-height: 100vh;
            padding-top: 2rem;
            background-color: #1e1e2f;
        }
        .sidebar .nav-link {
            color: #ccc;
        }
        .sidebar .nav-link.active, .sidebar .nav-link:hover {
            background-color: #343a40;
            color: #fff;
        }
        .main-content {
            padding: 2rem;
        }
        .card {
            border-radius: 1rem;
        }
        .btn {
            margin-right: 10px;
            margin-bottom: 10px;
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
                <div class="text-center text-white mb-4">
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
                    <h5>Administrateur</h5>
                    <small>${sessionScope.nomComplet}</small>
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
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/statistiques">
                            <i class="fas fa-chart-bar me-2"></i>Statistiques
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/parametres.jsp">
                            <i class="fas fa-cog me-2"></i>Paramètres
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/admin/rapports.jsp">
                            <i class="fas fa-file-alt me-2"></i>Rapports
                        </a>
                    </li>
                    <hr class="text-white-50">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout">
                            <i class="fas fa-sign-out-alt me-2"></i>Déconnexion
                        </a>
                    </li>
                </ul>
            </nav>

            <!-- Main Content -->
            <main class="col-md-9 ms-sm-auto col-lg-10 main-content">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2><i class="fas fa-file-alt me-2"></i>Rapports</h2>
                </div>

                <div class="card shadow-sm border-0 p-4">
                    <h5 class="mb-4">Générer ou télécharger des rapports</h5>

                    <!-- Abonnés -->
                    <div class="mb-4">
                        <h6><i class="fas fa-user me-2 text-primary"></i>Abonnés</h6>
                        <a href="${pageContext.request.contextPath}/admin/export-abonnes-pdf" class="btn btn-outline-danger">
                            <i class="fas fa-file-pdf"></i> PDF
                        </a>
                        <a href="${pageContext.request.contextPath}/admin/export-abonnes-csv" class="btn btn-outline-success">
                            <i class="fas fa-file-csv"></i> CSV
                        </a>
                        <form action="${pageContext.request.contextPath}/AbonneImportCsvServlet" method="post" enctype="multipart/form-data" class="d-inline-block mt-2">
                            <input type="file" name="file" accept=".csv" class="form-control d-inline-block w-auto" required>
                            <button type="submit" class="btn btn-outline-warning ms-2"><i class="fas fa-upload me-1"></i>Importer</button>
                        </form>
                    </div>

                    <!-- Livres -->
                    <div class="mb-4">
                        <h6><i class="fas fa-book me-2 text-success"></i>Livres</h6>
                        <a href="${pageContext.request.contextPath}/admin/export-livres-pdf" class="btn btn-outline-danger">
                            <i class="fas fa-file-pdf"></i> PDF
                        </a>
                        <a href="${pageContext.request.contextPath}/admin/export-livres-csv" class="btn btn-outline-success">
                            <i class="fas fa-file-csv"></i> CSV
                        </a>
                        <form action="${pageContext.request.contextPath}/LivreImportCsvServlet" method="post" enctype="multipart/form-data" class="d-inline-block mt-2">
                            <input type="file" name="file" accept=".csv" class="form-control d-inline-block w-auto" required>
                            <button type="submit" class="btn btn-outline-warning ms-2"><i class="fas fa-upload me-1"></i>Importer</button>
                        </form>
                    </div>

                    <p class="text-muted mt-3">Ajoutez d'autres fonctionnalités d'import/export au besoin.</p>
                </div>
            </main>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
