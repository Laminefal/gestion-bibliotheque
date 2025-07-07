<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmation de suppression - Bibliothèque</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <!-- En-tête -->
                <div class="text-center mb-4">
                    <i class="fas fa-exclamation-triangle fa-3x text-warning mb-3"></i>
                    <h2>Confirmation de suppression</h2>
                    <p class="lead text-muted">Veuillez confirmer cette action irréversible</p>
                </div>

                <!-- Message d'erreur si le livre n'existe pas -->
                <c:if test="${empty livre}">
                    <div class="alert alert-danger text-center">
                        <i class="fas fa-times-circle fa-2x mb-3"></i>
                        <h4>Livre introuvable</h4>
                        <p>Le livre que vous souhaitez supprimer n'existe pas ou a déjà été supprimé.</p>
                        <a href="livres" class="btn btn-primary">
                            <i class="fas fa-arrow-left me-2"></i>Retour au catalogue
                        </a>
                    </div>
                </c:if>

                <!-- Confirmation de suppression -->
                <c:if test="${not empty livre}">
                    <div class="card">
                        <div class="card-body p-4">
                            <div class="alert alert-warning text-center mb-4">
                                <i class="fas fa-exclamation-triangle fa-2x mb-3"></i>
                                <h4 class="text-warning">Attention !</h4>
                                <p class="mb-0">Cette action est irréversible et supprimera définitivement le livre et tous ses exemplaires.</p>
                            </div>

                            <!-- Informations du livre -->
                            <div class="row mb-4">
                                <div class="col-md-8">
                                    <h4 class="mb-2">
                                        <strong>${livre.titre}</strong>
                                    </h4>
                                    <p class="mb-2">
                                        <i class="fas fa-user me-2 text-muted"></i>
                                        <strong>${livre.auteurs}</strong>
                                    </p>
                                    <p class="mb-2">
                                        <i class="fas fa-id-card me-2 text-muted"></i>
                                        <strong>ID: ${livre.identifiant}</strong>
                                    </p>
                                    <p class="mb-2">
                                        <i class="fas fa-calendar me-2 text-muted"></i>
                                        ${livre.anneePublication}
                                    </p>
                                    <p class="mb-1">
                                        <i class="fas fa-tags me-2 text-muted"></i>
                                        <span class="badge bg-primary">${livre.domaine}</span>
                                        <span class="badge bg-secondary">${livre.niveauRequis}</span>
                                    </p>
                                </div>
                            </div>

                            <!-- Vérifications importantes -->
                            <div class="alert alert-info">
                                <h6 class="alert-heading">
                                    <i class="fas fa-info-circle me-2"></i>Vérifications importantes
                                </h6>
                                <ul class="mb-0">
                                    <li>
                                        <strong>Exemplaires:</strong> 
                                        <span class="badge bg-info">${livre.exemplaires.size()}</span>
                                    </li>
                                    <li>
                                        <strong>Date d'ajout:</strong> ${livre.dateAjout}
                                    </li>
                                    <li>
                                        <strong>Statut:</strong> 
                                        <span class="badge bg-${livre.actif ? 'success' : 'secondary'}">
                                            ${livre.actif ? 'Actif' : 'Inactif'}
                                        </span>
                                    </li>
                                </ul>
                            </div>

                            <!-- Avertissement si exemplaires existants -->
                            <c:if test="${livre.exemplaires.size() > 0}">
                                <div class="alert alert-danger">
                                    <i class="fas fa-exclamation-triangle me-2"></i>
                                    <strong>Attention !</strong> Ce livre possède ${livre.exemplaires.size()} exemplaire(s). 
                                    La suppression du livre supprimera également tous ses exemplaires et leurs emprunts associés.
                                </div>
                            </c:if>

                            <!-- Formulaire de confirmation SIMPLIFIÉ -->
                            <form action="${pageContext.request.contextPath}/livre-delete-confirm" method="post">
                                <input type="hidden" name="id" value="${livre.id}">
                                <input type="hidden" name="confirmation" value="true">
                                
                                <div class="mb-3">
                                    <div class="form-check">
                                        <input class="form-check-input" 
                                               type="checkbox" 
                                               id="confirmation" 
                                               required>
                                        <label class="form-check-label" for="confirmation">
                                            Je confirme que je souhaite supprimer définitivement ce livre et tous ses exemplaires
                                        </label>
                                    </div>
                                </div>

                                <!-- Boutons d'action -->
                                <div class="d-flex justify-content-between">
                                    <a href="livres" class="btn btn-outline-secondary">
                                        <i class="fas fa-times me-2"></i>Annuler
                                    </a>
                                    <button type="submit" class="btn btn-danger">
                                        <i class="fas fa-trash me-2"></i>Supprimer définitivement
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>