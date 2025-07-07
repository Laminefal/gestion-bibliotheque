<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Exemplaires - Bibliothèque</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- FontAwesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f8f9fa; }
        .table thead th { position: sticky; top: 0; background: #e9f7ef; z-index: 1; }
        .table td, .table th { vertical-align: middle; }
        .btn-group .btn { margin-right: 0.2rem; }
        .table-responsive { box-shadow: 0 2px 8px rgba(0,0,0,0.07); border-radius: 10px; background: #fff; }
        .page-title { font-weight: bold; color: #198754; margin-bottom: 1.5rem; }
        .badge { font-size: 0.95em; }
    </style>
</head>
<body>
<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="page-title"><i class="fas fa-copy me-2"></i>Gestion des Exemplaires</h2>
        <a href="${pageContext.request.contextPath}/exemplaire-form" class="btn btn-success">
            <i class="fas fa-plus me-2"></i>Ajouter un exemplaire
        </a>
    </div>

    <!-- Messages -->
    <c:if test="${not empty success}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="fas fa-check-circle me-2"></i>${success}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="fas fa-exclamation-triangle me-2"></i>${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <!-- Tableau des exemplaires -->
    <div class="table-responsive">
        <table class="table table-striped table-hover align-middle">
            <thead class="table-success">
                <tr>
                    <th>Code</th>
                    <th>Livre</th>
                    <th>Auteurs</th>
                    <th>État</th>
                    <th>Localisation</th>
                    <th>Date d'acquisition</th>
                    <th>Disponibilité</th>
                    <th>Statut</th>
                    <th style="min-width:110px;">Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="exemplaire" items="${exemplaires}">
                    <tr>
                        <td>${exemplaire.codeExemplaire}</td>
                        <td>${exemplaire.livre.titre}</td>
                        <td>${exemplaire.livre.auteurs}</td>
                        <td>${exemplaire.etat}</td>
                        <td>${exemplaire.localisation}</td>
                        <td>${exemplaire.dateAcquisitionFormatted}</td>
                        <td>
                            <span class="badge ${exemplaire.disponible ? 'bg-success' : 'bg-warning'}">
                                ${exemplaire.disponible ? 'Disponible' : 'Indisponible'}
                            </span>
                        </td>
                        <td>
                            <span class="badge ${exemplaire.actif ? 'bg-success' : 'bg-secondary'}">
                                ${exemplaire.actif ? 'Actif' : 'Inactif'}
                            </span>
                        </td>
                        <td>
                            <div class="btn-group" role="group">
                                <a href="${pageContext.request.contextPath}/exemplaire-form?id=${exemplaire.id}" class="btn btn-sm btn-outline-primary" title="Modifier">
                                    <i class="fas fa-edit"></i>
                                </a>
                                <a href="${pageContext.request.contextPath}/exemplaire-delete?id=${exemplaire.id}" class="btn btn-sm btn-outline-danger"
                                   onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet exemplaire ?')" title="Supprimer">
                                    <i class="fas fa-trash"></i>
                                </a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Message si aucun exemplaire -->
    <c:if test="${empty exemplaires}">
        <div class="text-center py-5">
            <i class="fas fa-copy fa-4x text-muted mb-3"></i>
            <h4 class="text-muted">Aucun exemplaire trouvé</h4>
            <p class="text-muted">Aucun exemplaire ne correspond à vos critères de recherche.</p>
            <a href="${pageContext.request.contextPath}/exemplaire-form" class="btn btn-success">
                <i class="fas fa-plus me-2"></i>Ajouter le premier exemplaire
            </a>
        </div>
    </c:if>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>