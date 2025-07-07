<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des emprunts</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body { background: #f8f9fa; }
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
        <h2 class="page-title"><i class="fas fa-book-reader me-2"></i>Liste des emprunts</h2>
        <c:if test="${sessionScope.role ne 'ADMIN'}">
            <a href="${pageContext.request.contextPath}/emprunt-form" class="btn btn-success">
                <i class="fas fa-plus me-2"></i>Nouvel emprunt
            </a>
        </c:if>
    </div>
    <c:if test="${not empty emprunts}">
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
                <c:forEach var="emprunt" items="${emprunts}">
                    <tr>
                        <td>${emprunt.abonne.prenom} ${emprunt.abonne.nom}</td>
                        <td>${emprunt.exemplaire.codeExemplaire}</td>
                        <td>${emprunt.exemplaire.livre.titre}</td>
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
                            <span class="badge ${emprunt.statut == 'EN_COURS' ? 'bg-warning' : 'bg-success'}">
                                ${emprunt.statut}
                            </span>
                        </td>
                        <td>
                            <c:if test="${sessionScope.role ne 'ADMIN'}">
                                <div class="btn-group" role="group">
                                    <a href="${pageContext.request.contextPath}/emprunt-form?id=${emprunt.id}" class="btn btn-sm btn-outline-primary" title="Modifier">
                                        <i class="fas fa-edit"></i>
                                    </a>
                                    <form action="${pageContext.request.contextPath}/emprunt-delete" method="post" style="display:inline;">
                                        <input type="hidden" name="id" value="${emprunt.id}">
                                        <button type="submit" class="btn btn-sm btn-outline-danger" onclick="return confirm('Supprimer cet emprunt ?');" title="Supprimer">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </form>
                                </div>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
    <c:if test="${empty emprunts}">
        <div class="alert alert-info">Aucun emprunt trouvé.</div>
    </c:if>
    <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-secondary mt-3">Retour à l'accueil</a>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>