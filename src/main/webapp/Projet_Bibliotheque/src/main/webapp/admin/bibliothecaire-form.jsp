<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulaire Bibliothécaire</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow-sm">
                    <div class="card-header bg-primary text-white">
                        <h4 class="mb-0">
                            <c:choose>
                                <c:when test="${not empty bibliothecaire}">
                                    Modifier un bibliothécaire
                                </c:when>
                                <c:otherwise>
                                    Ajouter un bibliothécaire
                                </c:otherwise>
                            </c:choose>
                        </h4>
                    </div>
                    <div class="card-body">
                        <form method="post" action="${pageContext.request.contextPath}/admin/bibliothecaire-save">
                            <c:if test="${not empty bibliothecaire}">
                                <input type="hidden" name="id" value="${bibliothecaire.id}" />
                            </c:if>
                            
                            <c:if test="${empty bibliothecaire}">
                                <div class="mb-3">
                                    <label for="nomUtilisateur" class="form-label">Nom d'utilisateur</label>
                                    <input type="text" class="form-control" id="nomUtilisateur" name="nomUtilisateur" value="${bibliothecaire.nomUtilisateur}" required>
                                </div>
                            </c:if>
                            
                            <div class="mb-3">
                                <label for="prenom" class="form-label">Prénom</label>
                                <input type="text" class="form-control" id="prenom" name="prenom" value="${bibliothecaire.prenom}" required>
                            </div>
                            <div class="mb-3">
                                <label for="nom" class="form-label">Nom</label>
                                <input type="text" class="form-control" id="nom" name="nom" value="${bibliothecaire.nom}" required>
                            </div>
                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" name="email" value="${bibliothecaire.email}" required>
                            </div>
                            <div class="mb-3">
                                <label for="adresse" class="form-label">Adresse</label>
                                <textarea class="form-control" id="adresse" name="adresse" rows="3" required>${bibliothecaire.adresse}</textarea>
                            </div>
                            <div class="mb-3">
                                <label for="dateRecrutement" class="form-label">Date de recrutement</label>
                                <input type="date" class="form-control" id="dateRecrutement" name="dateRecrutement" value="${bibliothecaire.dateRecrutement}" required>
                            </div>
                            <div class="mb-3">
                                <label for="specialite" class="form-label">Spécialité</label>
                                <input type="text" class="form-control" id="specialite" name="specialite" value="${bibliothecaire.specialite}">
                            </div>
                            <div class="mb-3">
                                <label for="telephone" class="form-label">Téléphone</label>
                                <input type="tel" class="form-control" id="telephone" name="telephone" value="${bibliothecaire.telephone}">
                            </div>
                            <c:if test="${empty bibliothecaire}">
                                <div class="mb-3">
                                    <label for="motDePasse" class="form-label">Mot de passe</label>
                                    <input type="password" class="form-control" id="motDePasse" name="motDePasse" required>
                                </div>
                            </c:if>
                            <div class="d-flex gap-2">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save me-2"></i>Enregistrer
                                </button>
                                <a href="${pageContext.request.contextPath}/admin/bibliothecaires" class="btn btn-secondary">
                                    <i class="fas fa-times me-2"></i>Annuler
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
