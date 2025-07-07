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
    <style>
        .card {
            border: none;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
        }
        .alert-warning {
            background-color: #fff3cd;
            border-color: #ffeaa7;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-6">
                <!-- En-tête -->
                <div class="text-center mb-4">
                    <i class="fas fa-exclamation-triangle fa-3x text-warning mb-3"></i>
                    <h2>Confirmation de suppression</h2>
                    <p class="lead text-muted">Veuillez confirmer cette action irréversible</p>
                </div>

                <!-- Message d'erreur si l'abonné n'existe pas -->
                <c:if test="${empty abonne}">
                    <div class="alert alert-danger text-center">
                        <i class="fas fa-times-circle fa-2x mb-3"></i>
                        <h4>Abonné introuvable</h4>
                        <p>L'abonné que vous souhaitez supprimer n'existe pas ou a déjà été supprimé.</p>
                        <a href="abonnes" class="btn btn-primary">
                            <i class="fas fa-arrow-left me-2"></i>Retour à la liste
                        </a>
                    </div>
                </c:if>

                <!-- Confirmation de suppression -->
                <c:if test="${not empty abonne}">
                    <div class="card">
                        <div class="card-body p-4">
                            <div class="alert alert-warning text-center mb-4">
                                <i class="fas fa-exclamation-triangle fa-2x mb-3"></i>
                                <h4 class="text-warning">Attention !</h4>
                                <p class="mb-0">Cette action est irréversible et supprimera définitivement l'abonné.</p>
                            </div>

                            <!-- Informations de l'abonné -->
                            <div class="row mb-4">
                                <div class="col-md-4 text-center">
                                    <div class="bg-light rounded-circle d-inline-flex align-items-center justify-content-center" 
                                         style="width: 80px; height: 80px;">
                                        <i class="fas fa-user fa-2x text-muted"></i>
                                    </div>
                                </div>
                                <div class="col-md-8">
                                    <h5 class="mb-2">
                                        <strong>${abonne.prenom} ${abonne.nom}</strong>
                                    </h5>
                                    <p class="mb-1">
                                        <i class="fas fa-id-card me-2 text-muted"></i>
                                        <strong>N° ${abonne.numeroAbonnement}</strong>
                                    </p>
                                    <p class="mb-1">
                                        <i class="fas fa-envelope me-2 text-muted"></i>
                                        ${abonne.email}
                                    </p>
                                    <p class="mb-1">
                                        <i class="fas fa-briefcase me-2 text-muted"></i>
                                        <span class="badge bg-primary">${abonne.statut}</span>
                                    </p>
                                    <p class="mb-0">
                                        <i class="fas fa-university me-2 text-muted"></i>
                                        ${abonne.institution}
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
                                        <strong>Emprunts en cours:</strong> 
                                        <span class="badge bg-${abonne.nombreEmpruntsEnCours > 0 ? 'danger' : 'success'}">
                                            ${abonne.nombreEmpruntsEnCours}
                                        </span>
                                    </li>
                                    <li>
                                        <strong>Date d'inscription:</strong> ${abonne.dateInscription}
                                    </li>
                                    <li>
                                        <strong>Statut:</strong> 
                                        <span class="badge bg-${abonne.actif ? 'success' : 'secondary'}">
                                            ${abonne.actif ? 'Actif' : 'Inactif'}
                                        </span>
                                    </li>
                                </ul>
                            </div>

                            <!-- Avertissement si emprunts en cours -->
                            <c:if test="${abonne.nombreEmpruntsEnCours > 0}">
                                <div class="alert alert-danger">
                                    <i class="fas fa-exclamation-triangle me-2"></i>
                                    <strong>Attention !</strong> Cet abonné a ${abonne.nombreEmpruntsEnCours} emprunt(s) en cours. 
                                    La suppression de l'abonné supprimera également tous ses emprunts.
                                </div>
                            </c:if>

                            <!-- Formulaire de confirmation -->
                            <form action="${pageContext.request.contextPath}/abonne-delete-confirm" method="post">
                                <input type="hidden" name="id" value="${abonne.id}">
                                
                                <div class="mb-3">
                                    <label for="confirmation" class="form-label">
                                        <strong>Confirmation requise</strong>
                                    </label>
                                    <div class="form-check">
                                        <input class="form-check-input" 
                                               type="checkbox" 
                                               id="confirmation" 
                                               name="confirmation" 
                                               value="true" 
                                               required>
                                        <label class="form-check-label" for="confirmation">
                                            Je confirme que je souhaite supprimer définitivement cet abonné
                                        </label>
                                    </div>
                                </div>

                                <!-- Boutons d'action -->
                                <div class="d-flex justify-content-between">
                                    <a href="abonnes" class="btn btn-outline-secondary">
                                        <i class="fas fa-times me-2"></i>Annuler
                                    </a>
                                    <button type="submit" 
                                            class="btn btn-danger" 
                                            id="deleteBtn" 
                                            disabled>
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
    <script>
        // Activer le bouton de suppression seulement si la confirmation est cochée
        document.getElementById('confirmation').addEventListener('change', function() {
            document.getElementById('deleteBtn').disabled = !this.checked;
        });

        // Confirmation supplémentaire avant soumission
        document.querySelector('form').addEventListener('submit', function(e) {
            if (!confirm('Êtes-vous absolument sûr de vouloir supprimer cet abonné ? Cette action ne peut pas être annulée.')) {
                e.preventDefault();
                return false;
            }
        });
    </script>
</body>
</html>