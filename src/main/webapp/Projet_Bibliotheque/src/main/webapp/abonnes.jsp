<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Abonnés - Bibliothèque</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .table th {
            background-color: #f8f9fa;
            border-top: none;
        }
        .badge {
            font-size: 0.8em;
        }
        .btn-sm {
            margin: 0 2px;
        }
    </style>
</head>
<body>
    <div class="container py-5">
        <!-- En-tête -->
        <div class="text-center mb-4">
            <i class="fas fa-users fa-3x text-primary mb-3"></i>
            <h2>Gestion des Abonnés</h2>
            <p class="lead text-muted">Administration des abonnés de la bibliothèque</p>
            <a href="index.jsp" class="btn btn-outline-primary mt-3">
                <i class="fas fa-home me-2"></i>Retour à l'accueil
            </a>
        </div>

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
                    <i class="fas fa-list me-2"></i>Liste des abonnés
                    <span class="badge bg-secondary ms-2">${abonnes.size()}</span>
                </h5>
            </div>
            <div>
                <c:if test="${sessionScope.role ne 'ADMIN'}">
                    <a href="abonne-form" class="btn btn-primary">
                        <i class="fas fa-user-plus me-2"></i>Nouvel abonné
                    </a>
                </c:if>
            </div>
        </div>

        <!-- Tableau des abonnés -->
        <div class="card shadow-sm">
            <div class="card-body p-0">
                <div class="table-responsive">
                    <table class="table table-hover mb-0">
                        <thead>
                            <tr>
                                <th width="5%">#</th>
                                <th width="12%">Numéro</th>
                                <th width="15%">Nom</th>
                                <th width="15%">Prénom</th>
                                <th width="20%">Email</th>
                                <th width="10%">Statut</th>
                                <th width="15%">Institution</th>
                                <th width="8%">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty abonnes}">
                                    <tr>
                                        <td colspan="8" class="text-center py-4">
                                            <i class="fas fa-inbox fa-2x text-muted mb-3"></i>
                                            <p class="text-muted">Aucun abonné trouvé</p>
                                            <a href="abonne-form" class="btn btn-sm btn-primary">
                                                <i class="fas fa-plus me-1"></i>Ajouter le premier abonné
                                            </a>
                                        </td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="abonne" items="${abonnes}">
                                        <tr>
                                            <td>${abonne.id}</td>
                                            <td>
                                                <span class="badge bg-light text-dark">${abonne.numeroAbonnement}</span>
                                            </td>
                                            <td><strong>${abonne.nom}</strong></td>
                                            <td>${abonne.prenom}</td>
                                            <td>
                                                <a href="mailto:${abonne.email}" class="text-decoration-none">
                                                    <i class="fas fa-envelope me-1"></i>${abonne.email}
                                                </a>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${abonne.statut == 'ETUDIANT'}">
                                                        <span class="badge bg-primary">Étudiant</span>
                                                    </c:when>
                                                    <c:when test="${abonne.statut == 'ENSEIGNANT'}">
                                                        <span class="badge bg-success">Enseignant</span>
                                                    </c:when>
                                                    <c:when test="${abonne.statut == 'PERSONNEL'}">
                                                        <span class="badge bg-info">Personnel</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-secondary">${abonne.statut}</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <small class="text-muted">${abonne.institution}</small>
                                            </td>
                                            <td>
                                                <c:if test="${sessionScope.role ne 'ADMIN'}">
                                                    <div class="btn-group" role="group">
                                                        <a href="abonne-form?id=${abonne.id}" 
                                                           class="btn btn-sm btn-outline-warning" 
                                                           title="Modifier">
                                                            <i class="fas fa-edit"></i>
                                                        </a>
                                                        <a href="abonne-delete?id=${abonne.id}" 
                                                           class="btn btn-sm btn-outline-danger" 
                                                           title="Supprimer"
                                                           onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet abonné ?');">
                                                            <i class="fas fa-trash"></i>
                                                        </a>
                                                    </div>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- Statistiques -->
        <div class="row mt-4">
            <div class="col-md-3">
                <div class="card bg-primary text-white">
                    <div class="card-body text-center">
                        <i class="fas fa-users fa-2x mb-2"></i>
                        <h5>Total</h5>
                        <h3>${abonnes.size()}</h3>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card bg-success text-white">
                    <div class="card-body text-center">
                        <i class="fas fa-user-graduate fa-2x mb-2"></i>
                        <h5>Étudiants</h5>
                        <h3>${abonnes.stream().filter(a -> a.statut == 'ETUDIANT').count()}</h3>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card bg-info text-white">
                    <div class="card-body text-center">
                        <i class="fas fa-chalkboard-teacher fa-2x mb-2"></i>
                        <h5>Enseignants</h5>
                        <h3>${abonnes.stream().filter(a -> a.statut == 'ENSEIGNANT').count()}</h3>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card bg-warning text-white">
                    <div class="card-body text-center">
                        <i class="fas fa-user-tie fa-2x mb-2"></i>
                        <h5>Personnel</h5>
                        <h3>${abonnes.stream().filter(a -> a.statut == 'PERSONNEL').count()}</h3>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>