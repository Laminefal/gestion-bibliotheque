<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nouvel emprunt</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">Nouvel emprunt</h2>
    
    <!-- Debug info -->
    <div class="alert alert-info">
        Nombre d'abonnés : ${fn:length(abonnes)}<br>
        Nombre d'exemplaires : ${fn:length(exemplaires)}
    </div>
    
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    
    <form action="${pageContext.request.contextPath}/emprunt-save" method="post">
        <div class="mb-3">
            <label for="abonneId" class="form-label">Abonné</label>
            <select class="form-select" id="abonneId" name="abonneId" required>
                <option value="">Sélectionner un abonné</option>
                <c:forEach var="abonne" items="${abonnes}">
                    <option value="${abonne.id}">
                        ${abonne.numeroAbonnement} - ${abonne.prenom} ${abonne.nom}
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="mb-3">
            <label for="exemplaireId" class="form-label">Exemplaire</label>
            <select class="form-select" id="exemplaireId" name="exemplaireId" required>
                <option value="">Sélectionner un exemplaire</option>
                <c:forEach var="ex" items="${exemplaires}">
                    <option value="${ex.id}">
                        ${ex.codeExemplaire} - ${ex.livre.titre}
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="mb-3">
            <label for="dateRetourPrevue" class="form-label">Date de retour prévue</label>
            <input type="date" class="form-control" id="dateRetourPrevue" name="dateRetourPrevue" required>
        </div>
        <div class="d-flex gap-2">
            <button type="submit" class="btn btn-primary">Enregistrer l'emprunt</button>
            <a href="${pageContext.request.contextPath}/emprunts" class="btn btn-secondary">Annuler</a>
        </div>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>