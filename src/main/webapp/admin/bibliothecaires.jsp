<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Bibliothécaires - Administration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f8f9fa;
        }
        .sidebar {
            min-height: 100vh;
            background-color: #0d6efd;
        }
        .sidebar .nav-link {
            color: #fff;
            font-weight: 500;
            transition: background 0.3s;
        }
        .sidebar .nav-link:hover,
        .sidebar .nav-link.active {
            background-color: #0b5ed7;
            color: #fff;
        }
        .sidebar h5, .sidebar small {
            color: #fff;
        }
        .main-content {
            padding-top: 20px;
        }
        .table thead {
            background-color: #e9ecef;
        }
        .table-hover tbody tr:hover {
            background-color: #f1f3f5;
        }
        .btn-primary {
            background-color: #0d6efd;
            border: none;
        }
        .btn-primary:hover {
            background-color: #0b5ed7;
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <nav class="col-md-3 col-lg-2 d-md-block sidebar collapse">
                <div class="position-sticky pt-3 px-3">
                    <div class="text-center mb-4">
                        <i class="fas fa-user-shield fa-3x mb-2"></i>
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
                            <a class="nav-link active" href="${pageContext.request.contextPath}/admin/bibliothecaires.jsp">
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
                        <hr class="text-white">
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
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2 class="h4"><i class="fas fa-users me-2 text-primary"></i>Gestion des Bibliothécaires</h2>
                    <a href="${pageContext.request.contextPath}/admin/bibliothecaire-form" class="btn btn-primary shadow-sm">
                        <i class="fas fa-user-plus me-2"></i>Ajouter un bibliothécaire
                    </a>
                </div>

                <div class="card shadow-sm border-0">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-hover align-middle">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Nom</th>
                                        <th>Prénom</th>
                                        <th>Email</th>
                                        <th>Date de recrutement</th>
                                        <th>Adresse</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:if test="${empty bibliothecaires}">
                                        <tr>
                                            <td colspan="7" class="text-center text-muted">Aucun bibliothécaire trouvé.</td>
                                        </tr>
                                    </c:if>
                                    <c:forEach var="b" items="${bibliothecaires}">
                                        <tr>
                                            <td>${b.id}</td>
                                            <td>${b.nom}</td>
                                            <td>${b.prenom}</td>
                                            <td>${b.email}</td>
                                            <td>${b.dateRecrutement}</td>
                                            <td>${b.adresse}</td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/admin/bibliothecaire-form?id=${b.id}" class="btn btn-sm btn-warning">
                                                    <i class="fas fa-edit"></i>
                                                </a>
                                                <a href="${pageContext.request.contextPath}/admin/bibliothecaire-delete?id=${b.id}" class="btn btn-sm btn-danger" onclick="return confirm('Supprimer ce bibliothécaire ?');">
                                                    <i class="fas fa-trash"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
