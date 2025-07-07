<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des livres</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">Liste des livres</h2>
    
    <!-- Messages de succès/erreur -->
    <c:if test="${param.success == 'true'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="fas fa-check-circle me-2"></i>${param.message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>
    
    <c:if test="${param.error == 'true'}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="fas fa-exclamation-triangle me-2"></i>${param.message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>
    
    <c:if test="${not empty livres}">
        <div class="table-responsive">
            <table class="table table-striped table-hover align-middle">
                <thead class="table-dark">
                    <tr>
                        <th>Identifiant</th>
                        <th>Titre</th>
                        <th>Auteurs</th>
                        <th>Année</th>
                        <th>Domaine</th>
                        <th>Niveau</th>
                        <th>ISBN</th>
                        <th>Exemplaires</th>
                        <th>Actif</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="livre" items="${livres}">
                    <tr>
                        <td>${livre.identifiant}</td>
                        <td>${livre.titre}</td>
                        <td>${livre.auteurs}</td>
                        <td>${livre.anneePublication}</td>
                        <td>${livre.domaine}</td>
                        <td>${livre.niveauRequis}</td>
                        <td>${livre.isbn}</td>
                        <td>
                            <i class="fas fa-copy me-1"></i>${fn:length(livre.exemplaires)} exemplaire(s)
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${livre.actif}">
                                    <span class="badge bg-success">Oui</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-danger">Non</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${sessionScope.role ne 'ADMIN'}">
                                <div class="btn-group" role="group">
                                    <a href="${pageContext.request.contextPath}/livre-form?id=${livre.id}" 
                                       class="btn btn-sm btn-primary" 
                                       title="Modifier">
                                        <i class="fas fa-edit"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/livre-delete?id=${livre.id}" 
                                       class="btn btn-sm btn-danger" 
                                       title="Supprimer"
                                       onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce livre ?')">
                                        <i class="fas fa-trash"></i>
                                    </a>
                                </div>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
    <c:if test="${empty livres}">
        <div class="alert alert-info">Aucun livre trouvé.</div>
    </c:if>
    <c:if test="${sessionScope.role ne 'ADMIN'}">
        <a href="${pageContext.request.contextPath}/livre-form" class="btn btn-success mt-3">
            <i class="fas fa-plus"></i> Ajouter un livre
        </a>
    </c:if>
    <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-secondary mt-3">Retour à l'accueil</a>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>