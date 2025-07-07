<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Suppression d'un exemplaire</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4 text-danger">Confirmer la suppression de l'exemplaire</h2>
    <c:if test="${not empty exemplaires}">
        <div class="alert alert-warning">
            <strong>Attention :</strong> Vous êtes sur le point de supprimer l'exemplaire suivant :
            <ul>
                <li><strong>Code :</strong> ${exemplaires.codeExemplaire}</li>
                <li><strong>Livre :</strong> ${exemplaires.livre.titre}</li>
                <li><strong>État :</strong> ${exemplaires.etat}</li>
            </ul>
            Cette action est irréversible.
        </div>
        <form action="${pageContext.request.contextPath}/exemplaire-delete" method="post">
            <input type="hidden" name="id" value="${exemplaires.id}">
            <button type="submit" class="btn btn-danger">Confirmer la suppression</button>
            <a href="${pageContext.request.contextPath}/exemplaires" class="btn btn-secondary">Annuler</a>
        </form>
    </c:if>
    <c:if test="${empty exemplaires}">
        <div class="alert alert-info">Aucun exemplaire à supprimer.</div>
        <a href="${pageContext.request.contextPath}/exemplaires" class="btn btn-secondary">Retour à la liste</a>
    </c:if>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
