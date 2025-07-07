<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Recherche de livres</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>Recherche de livres</h2>
    <form method="get" action="recherche.jsp" class="row g-3 mb-4">
        <div class="col-md-5">
            <input type="text" class="form-control" name="titre" placeholder="Titre du livre" value="${param.titre}">
        </div>
        <div class="col-md-5">
            <input type="text" class="form-control" name="auteur" placeholder="Auteur" value="${param.auteur}">
        </div>
        <div class="col-md-2">
            <button type="submit" class="btn btn-primary w-100">
                <i class="fas fa-search me-1"></i> Rechercher
            </button>
        </div>
    </form>

    <!-- Exemple de liste filtrée (à adapter selon ta logique) -->
    <c:if test="${not empty livres}">
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Titre</th>
                    <th>Auteur(s)</th>
                    <th>Année</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="livre" items="${livres}">
                    <c:if test="
                        (${empty param.titre or fn:contains(fn:toLowerCase(livre.titre), fn:toLowerCase(param.titre))}) and
                        (${empty param.auteur or fn:contains(fn:toLowerCase(livre.auteurs), fn:toLowerCase(param.auteur))})
                    ">
                        <tr>
                            <td>${livre.titre}</td>
                            <td>${livre.auteurs}</td>
                            <td>${livre.anneePublication}</td>
                        </tr>
                    </c:if>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty livres}">
        <div class="alert alert-info">Aucun livre trouvé.</div>
    </c:if>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>