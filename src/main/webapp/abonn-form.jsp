<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty abonne ? 'Nouvel abonné' : 'Modifier abonné'} - Bibliothèque</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .form-label {
            font-weight: 600;
        }
        .required::after {
            content: " *";
            color: red;
        }
        .card {
            border: none;
            box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
        }
    </style>
</head>
<body class="bg-light">
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <!-- En-tête -->
                <div class="text-center mb-4">
                    <i class="fas fa-user-edit fa-3x text-primary mb-3"></i>
                    <h2>${empty abonne ? 'Nouvel abonné' : 'Modifier l\'abonné'}</h2>
                    <p class="lead text-muted">
                        ${empty abonne ? 'Ajouter un nouvel abonné à la bibliothèque' : 'Modifier les informations de l\'abonné'}
                    </p>
                    <a href="abonnes" class="btn btn-outline-secondary">
                        <i class="fas fa-arrow-left me-2"></i>Retour à la liste
                    </a>
                </div>

                <!-- Messages d'erreur -->
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="fas fa-exclamation-triangle me-2"></i>
                        <strong>Erreur !</strong> ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <!-- Formulaire -->
                <div class="card">
                    <div class="card-body p-4">
                        <form action="${pageContext.request.contextPath}/abonne-save" method="post" id="abonneForm">
                            <!-- ID caché pour la modification -->
                            <c:if test="${not empty abonnes.id}">
                                <input type="hidden" name="id" value="${abonnes.id}">
                            </c:if>

                            <div class="row">
                                <!-- Informations personnelles -->
                                <div class="col-md-6">
                                    <h5 class="mb-3 text-primary">
                                        <i class="fas fa-user me-2"></i>Informations personnelles
                                    </h5>
                                    
                                    <div class="mb-3">
                                        <label for="numeroAbonnement" class="form-label required">Numéro d'abonnement</label>
                                        <input type="text" 
                                               class="form-control" 
                                               id="numeroAbonnement" 
                                               name="numeroAbonnement" 
                                               value="${abonnes.numeroAbonnement}"
                                               required
                                               pattern="[A-Z0-9]{6,10}"
                                               title="Le numéro doit contenir 6 à 10 caractères alphanumériques"
                                               ${not empty abonnes.id ? 'readonly' : ''}>
                                        <div class="form-text">Format: 6 à 10 caractères alphanumériques</div>
                                    </div>

                                    <div class="mb-3">
                                        <label for="prenom" class="form-label required">Prénom</label>
                                        <input type="text" 
                                               class="form-control" 
                                               id="prenom" 
                                               name="prenom" 
                                               value="${abonnes.prenom}"
                                               required
                                               minlength="2"
                                               maxlength="50">
                                    </div>

                                    <div class="mb-3">
                                        <label for="nom" class="form-label required">Nom</label>
                                        <input type="text" 
                                               class="form-control" 
                                               id="nom" 
                                               name="nom" 
                                               value="${abonnes.nom}"
                                               required
                                               minlength="2"
                                               maxlength="50">
                                    </div>

                                    <div class="mb-3">
                                        <label for="email" class="form-label required">Email</label>
                                        <input type="email" 
                                               class="form-control" 
                                               id="email" 
                                               name="email" 
                                               value="${abonnes.email}"
                                               required>
                                    </div>

                                    <div class="mb-3">
                                        <label for="telephone" class="form-label">Téléphone</label>
                                        <input type="tel" 
                                               class="form-control" 
                                               id="telephone" 
                                               name="telephone" 
                                               value="${abonnes.telephone}"
                                               pattern="[0-9+\-\s\(\)]{10,15}"
                                               title="Format: 10 à 15 chiffres avec espaces, tirets ou parenthèses">
                                    </div>
                                </div>

                                <!-- Informations professionnelles -->
                                <div class="col-md-6">
                                    <h5 class="mb-3 text-primary">
                                        <i class="fas fa-briefcase me-2"></i>Informations professionnelles
                                    </h5>

                                    <div class="mb-3">
                                        <label for="statut" class="form-label required">Statut</label>
                                        <select class="form-select" id="statut" name="statut" required>
                                            <option value="">Sélectionner un statut</option>
                                            <option value="ETUDIANT" ${abonnes.statut == 'ETUDIANT' ? 'selected' : ''}>
                                                Étudiant
                                            </option>
                                            <option value="ENSEIGNANT" ${abonnes.statut == 'ENSEIGNANT' ? 'selected' : ''}>
                                                Enseignant
                                            </option>
                                            <option value="PERSONNEL" ${abonnes.statut == 'PERSONNEL' ? 'selected' : ''}>
                                                Personnel administratif
                                            </option>
                                        </select>
                                    </div>

                                    <div class="mb-3">
                                        <label for="institution" class="form-label required">Institution de rattachement</label>
                                        <input type="text" 
                                               class="form-control" 
                                               id="institution" 
                                               name="institution" 
                                               value="${abonnes.institution}"
                                               required
                                               maxlength="200">
                                    </div>

                                    <div class="mb-3">
                                        <label for="adresse" class="form-label">Adresse</label>
                                        <textarea class="form-control" 
                                                  id="adresse" 
                                                  name="adresse" 
                                                  rows="3"
                                                  maxlength="500">${abonnes.adresse}</textarea>
                                    </div>

                                    <div class="mb-3">
                                        <div class="form-check">
                                            <input class="form-check-input" 
                                                   type="checkbox" 
                                                   id="actif" 
                                                   name="actif" 
                                                   value="true"
                                                   ${abonnes.actif ? 'checked' : ''}>
                                            <label class="form-check-label" for="actif">
                                                Abonné actif
                                            </label>
                                        </div>
                                        <div class="form-text">Un abonné inactif ne peut pas emprunter de livres</div>
                                    </div>
                                </div>
                            </div>

                            <!-- Informations complémentaires -->
                            <c:if test="${not empty abonnes.id}">
                                <hr class="my-4">
                                <div class="row">
                                    <div class="col-md-6">
                                        <h6 class="text-muted">
                                            <i class="fas fa-info-circle me-2"></i>Informations système
                                        </h6>
                                        <p class="mb-1">
                                            <strong>ID:</strong> ${abonnes.id}
                                        </p>
                                        <p class="mb-1">
                                            <strong>Date d'inscription:</strong> 
                                            ${abonne.dateInscription}
                                        </p>
                                        <p class="mb-1">
                                            <strong>Emprunts en cours:</strong> 
                                            <span class="badge bg-info">${abonnes.nombreEmpruntsEnCours}</span>
                                        </p>
                                    </div>
                                    <div class="col-md-6">
                                        <h6 class="text-muted">
                                            <i class="fas fa-book me-2"></i>Statut d'emprunt
                                        </h6>
                                        <c:choose>
                                            <c:when test="${abonnes.peutEmprunter()}">
                                                <p class="text-success mb-1">
                                                    <i class="fas fa-check-circle me-1"></i>
                                                    Peut emprunter des livres
                                                </p>
                                            </c:when>
                                            <c:otherwise>
                                                <p class="text-danger mb-1">
                                                    <i class="fas fa-times-circle me-1"></i>
                                                    Ne peut pas emprunter
                                                </p>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </c:if>

                            <!-- Boutons d'action -->
                            <hr class="my-4">
                            <div class="d-flex justify-content-between">
                                <a href="abonnes" class="btn btn-outline-secondary">
                                    <i class="fas fa-times me-2"></i>Annuler
                                </a>
                                <div>
                                    <button type="reset" class="btn btn-outline-warning me-2">
                                        <i class="fas fa-undo me-2"></i>Réinitialiser
                                    </button>
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-save me-2"></i>
                                        ${empty abonnes ? 'Créer l\'abonné' : 'Mettre à jour'}
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Validation côté client
        document.getElementById('abonn-Form').addEventListener('submit', function(e) {
            const email = document.getElementById('email').value;
            const numeroAbonnement = document.getElementById('numeroAbonnement').value;
            
            // Validation email
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email)) {
                alert('Veuillez entrer une adresse email valide');
                e.preventDefault();
                return false;
            }
            
            // Validation numéro d'abonnement
            const numeroRegex = /^[A-Z0-9]{6,10}$/;
            if (!numeroRegex.test(numeroAbonnement)) {
                alert('Le numéro d\'abonnement doit contenir 6 à 10 caractères alphanumériques');
                e.preventDefault();
                return false;
            }
        });
    </script>
</body>
</html>