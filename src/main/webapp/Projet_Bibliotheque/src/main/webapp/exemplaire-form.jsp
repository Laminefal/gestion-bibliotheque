<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Formulaire Exemplaire</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">
        <c:choose>
            <c:when test="${not empty exemplaire}">Modifier un exemplaire</c:when>
            <c:otherwise>Ajouter un exemplaire</c:otherwise>
        </c:choose>
    </h2>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/exemplaire-save">
        <c:if test="${not empty exemplaire}">
            <input type="hidden" name="id" value="${exemplaire.id}" />
        </c:if>

        <div class="mb-3">
            <label for="codeExemplaire" class="form-label">Code exemplaire</label>
            <input type="text" class="form-control" id="codeExemplaire" name="codeExemplaire"
                   value="${exemplaire.codeExemplaire}" required maxlength="50">
        </div>

        <div class="mb-3">
            <label for="livreId" class="form-label">Livre à choisir</label>
            <select class="form-select" id="livreId" name="livreId" required>
                <option value="">Sélectionnez un livre</option>
                <c:forEach var="livre" items="${livres}">
                    <option value="${livre.id}"
                        ${not empty exemplaire && exemplaire.livre.id == livre.id ? 'selected' : ''}>
                        ${livre.titre} - ${livre.auteurs}
                        [
                        <c:choose>
                            <c:when test="${livre.niveauRequis == 'DEBUTANT'}">Débutant</c:when>
                            <c:when test="${livre.niveauRequis == 'INTERMEDIAIRE'}">Intermédiaire</c:when>
                            <c:when test="${livre.niveauRequis == 'AVANCE'}">Avancé</c:when>
                            <c:when test="${livre.niveauRequis == 'EXPERT'}">Expert</c:when>
                            <c:when test="${livre.niveauRequis == 'TOUS_NIVEAUX'}">Tous niveaux</c:when>
                            <c:otherwise>${livre.niveauRequis}</c:otherwise>
                        </c:choose>
                        ]
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3">
            <label for="etat" class="form-label">État</label>
            <select class="form-select" id="etat" name="etat">
                <option value="Bon" ${exemplaire.etat == 'Bon' ? 'selected' : ''}>Bon</option>
                <option value="Moyen" ${exemplaire.etat == 'Moyen' ? 'selected' : ''}>Moyen</option>
                <option value="Mauvais" ${exemplaire.etat == 'Mauvais' ? 'selected' : ''}>Mauvais</option>
            </select>
        </div>

        <div class="mb-3">
            <label for="localisation" class="form-label">Localisation</label>
            <input type="text" class="form-control" id="localisation" name="localisation"
                   value="${exemplaire.localisation}">
        </div>

        <div class="form-check mb-2">
            <input class="form-check-input" type="checkbox" id="disponible" name="disponible"
                   <c:if test="${empty exemplaire || exemplaire.disponible}">checked</c:if>>
            <label class="form-check-label" for="disponible">Disponible</label>
        </div>

        <div class="form-check mb-3">
            <input class="form-check-input" type="checkbox" id="actif" name="actif"
                   <c:if test="${empty exemplaire || exemplaire.actif}">checked</c:if>>
            <label class="form-check-label" for="actif">Actif</label>
        </div>

        <button type="submit" class="btn btn-success">Enregistrer</button>
        <a href="${pageContext.request.contextPath}/exemplaires" class="btn btn-secondary">Annuler</a>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>