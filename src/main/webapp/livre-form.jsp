<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${mode == 'edit' ? 'Modifier' : 'Ajouter'} un livre</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .btn-custom {
            padding: 10px 20px;
            font-weight: 500;
        }
        .required::after {
            content: " *";
            color: red;
        }
        .form-label {
            font-weight: 500;
            color: #495057;
        }
        .card {
            box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
            border: 1px solid rgba(0, 0, 0, 0.125);
        }
    </style>
</head>
<body class="bg-light">
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <nav class="col-md-3 col-lg-2 d-md-block bg-dark sidebar collapse">
                <div class="position-sticky pt-3">
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link text-white" href="${pageContext.request.contextPath}/index.jsp">
                                <i class="fas fa-home me-2"></i>Accueil
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-white" href="${pageContext.request.contextPath}/livres">
                                <i class="fas fa-book me-2"></i>Gestion des livres
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-white" href="${pageContext.request.contextPath}/abonnes">
                                <i class="fas fa-users me-2"></i>Gestion des abonnés
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-white" href="${pageContext.request.contextPath}/emprunts">
                                <i class="fas fa-exchange-alt me-2"></i>Gestion des emprunts
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>

            <!-- Main content -->
            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">
                        <i class="fas fa-${mode == 'edit' ? 'edit' : 'plus'} me-2"></i>
                        ${mode == 'edit' ? 'Modifier' : 'Ajouter'} un livre
                    </h1>
                </div>

                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-body">
                                <c:if test="${not empty error}">
                                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                        <i class="fas fa-exclamation-triangle me-2"></i>${error}
                                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                    </div>
                                </c:if>

                                <form class="needs-validation" action="${pageContext.request.contextPath}/livre-save" method="post" novalidate>
                                    <c:if test="${mode == 'edit'}">
                                        <input type="hidden" name="id" value="${livre.id}">
                                    </c:if>
                                    
                                    <div class="row">
                                        <!-- Left Column -->
                                        <div class="col-md-6">
                                            <h5 class="mb-4">
                                                <i class="fas fa-info-circle me-2"></i>Informations générales
                                            </h5>
                                            
                                            <div class="mb-3">
                                                <label for="identifiant" class="form-label required">Identifiant</label>
                                                <input type="text" 
                                                       class="form-control" 
                                                       id="identifiant" 
                                                       name="identifiant" 
                                                       value="${livre.identifiant}"
                                                       placeholder="LIV-2024-001"
                                                       required>
                                                <div class="invalid-feedback">
                                                    Veuillez saisir un identifiant.
                                                </div>
                                            </div>
                                            
                                            <div class="mb-3">
                                                <label for="titre" class="form-label required">Titre</label>
                                                <input type="text" 
                                                       class="form-control" 
                                                       id="titre" 
                                                       name="titre" 
                                                       value="${livre.titre}"
                                                       placeholder="Titre du livre"
                                                       required>
                                                <div class="invalid-feedback">
                                                    Veuillez saisir le titre du livre.
                                                </div>
                                            </div>
                                            
                                            <div class="mb-3">
                                                <label for="auteurs" class="form-label required">Auteurs</label>
                                                <input type="text" 
                                                       class="form-control" 
                                                       id="auteurs" 
                                                       name="auteurs" 
                                                       value="${livre.auteurs}"
                                                       placeholder="Nom(s) de(s) l'auteur(s)"
                                                       required>
                                                <div class="invalid-feedback">
                                                    Veuillez saisir le(s) nom(s) de(s) l'auteur(s).
                                                </div>
                                            </div>
                                            
                                            <div class="mb-3">
                                                <label for="anneePublication" class="form-label required">Année de publication</label>
                                                <input type="number" 
                                                       class="form-control" 
                                                       id="anneePublication" 
                                                       name="anneePublication" 
                                                       value="${livre.anneePublication}"
                                                       min="1900" 
                                                       max="2025"
                                                       placeholder="2024"
                                                       required>
                                                <div class="invalid-feedback">
                                                    Veuillez saisir une année valide.
                                                </div>
                                            </div>
                                        </div>
                                        
                                        <!-- Right Column -->
                                        <div class="col-md-6">
                                            <h5 class="mb-4">
                                                <i class="fas fa-tags me-2"></i>Classification
                                            </h5>
                                            
                                            <div class="mb-3">
                                                <label for="domaine" class="form-label required">Domaine</label>
                                                <select class="form-select" id="domaine" name="domaine" required>
                                                    <option value="">Sélectionnez un domaine</option>
                                                    <option value="INFORMATIQUE" ${livre.domaine == 'INFORMATIQUE' ? 'selected' : ''}>Informatique</option>
                                                    <option value="MATHEMATIQUES" ${livre.domaine == 'MATHEMATIQUES' ? 'selected' : ''}>Mathématiques</option>
                                                    <option value="PHYSIQUE" ${livre.domaine == 'PHYSIQUE' ? 'selected' : ''}>Physique</option>
                                                    <option value="CHIMIE" ${livre.domaine == 'CHIMIE' ? 'selected' : ''}>Chimie</option>
                                                    <option value="BIOLOGIE" ${livre.domaine == 'BIOLOGIE' ? 'selected' : ''}>Biologie</option>
                                                    <option value="MEDECINE" ${livre.domaine == 'MEDECINE' ? 'selected' : ''}>Médecine</option>
                                                    <option value="DROIT" ${livre.domaine == 'DROIT' ? 'selected' : ''}>Droit</option>
                                                    <option value="ECONOMIE" ${livre.domaine == 'ECONOMIE' ? 'selected' : ''}>Économie</option>
                                                    <option value="GESTION" ${livre.domaine == 'GESTION' ? 'selected' : ''}>Gestion</option>
                                                    <option value="LITTERATURE" ${livre.domaine == 'LITTERATURE' ? 'selected' : ''}>Littérature</option>
                                                    <option value="HISTOIRE" ${livre.domaine == 'HISTOIRE' ? 'selected' : ''}>Histoire</option>
                                                    <option value="PHILOSOPHIE" ${livre.domaine == 'PHILOSOPHIE' ? 'selected' : ''}>Philosophie</option>
                                                    <option value="LANGUES" ${livre.domaine == 'LANGUES' ? 'selected' : ''}>Langues</option>
                                                    <option value="ARTS" ${livre.domaine == 'ARTS' ? 'selected' : ''}>Arts</option>
                                                    <option value="SPORTS" ${livre.domaine == 'SPORTS' ? 'selected' : ''}>Sports</option>
                                                    <option value="AUTRE" ${livre.domaine == 'AUTRE' ? 'selected' : ''}>Autre</option>
                                                </select>
                                                <div class="invalid-feedback">
                                                    Veuillez sélectionner un domaine.
                                                </div>
                                            </div>
                                            
                                            <div class="mb-3">
                                                <label for="niveauRequis" class="form-label required">Niveau requis</label>
                                                <select class="form-select" id="niveauRequis" name="niveauRequis" required>
                                                    <option value="">Sélectionnez un niveau</option>
                                                    <option value="DEBUTANT" ${livre.niveauRequis == 'DEBUTANT' ? 'selected' : ''}>Débutant</option>
                                                    <option value="INTERMEDIAIRE" ${livre.niveauRequis == 'INTERMEDIAIRE' ? 'selected' : ''}>Intermédiaire</option>
                                                    <option value="AVANCE" ${livre.niveauRequis == 'AVANCE' ? 'selected' : ''}>Avancé</option>
                                                    <option value="EXPERT" ${livre.niveauRequis == 'EXPERT' ? 'selected' : ''}>Expert</option>
                                                    <option value="TOUS_NIVEAUX" ${livre.niveauRequis == 'TOUS_NIVEAUX' ? 'selected' : ''}>Tous niveaux</option>
                                                </select>
                                                <div class="invalid-feedback">
                                                    Veuillez sélectionner un niveau.
                                                </div>
                                            </div>
                                            
                                            <div class="mb-3">
                                                <label for="isbn" class="form-label">ISBN</label>
                                                <input type="text" 
                                                       class="form-control" 
                                                       id="isbn" 
                                                       name="isbn" 
                                                       value="${livre.isbn}"
                                                       placeholder="978-0-000000-0-0">
                                                <div class="form-text">
                                                    Format: 978-0-000000-0-0 (optionnel)
                                                </div>
                                            </div>
                                            
                                            <!-- Le nombre d'exemplaires sera géré séparément dans la gestion des exemplaires -->
                                            
                                            <div class="mb-3">
                                                <div class="form-check">
                                                    <input class="form-check-input" 
                                                           type="checkbox" 
                                                           id="actif" 
                                                           name="actif" 
                                                           value="true"
                                                           ${livre.actif ? 'checked' : ''}>
                                                    <label class="form-check-label" for="actif">
                                                        Livre actif (disponible pour emprunt)
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <!-- Description -->
                                    <div class="mb-4">
                                        <label for="description" class="form-label">Description</label>
                                        <textarea class="form-control" 
                                                  id="description" 
                                                  name="description" 
                                                  rows="4"
                                                  placeholder="Description du livre (optionnel)">${livre.description}</textarea>
                                    </div>
                                    
                                    <!-- Action Buttons -->
                                    <div class="d-flex justify-content-between">
                                        <a href="${pageContext.request.contextPath}/livres" class="btn btn-outline-secondary btn-custom">
                                            <i class="fas fa-times me-2"></i>Annuler
                                        </a>
                                        <button type="submit" class="btn btn-success btn-custom">
                                            <i class="fas fa-${mode == 'edit' ? 'save' : 'plus'} me-2"></i>
                                            ${mode == 'edit' ? 'Enregistrer' : 'Ajouter'} le livre
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Form validation
        (function() {
            'use strict';
            window.addEventListener('load', function() {
                var forms = document.getElementsByClassName('needs-validation');
                var validation = Array.prototype.filter.call(forms, function(form) {
                    form.addEventListener('submit', function(event) {
                        if (form.checkValidity() === false) {
                            event.preventDefault();
                            event.stopPropagation();
                        }
                        form.classList.add('was-validated');
                    }, false);
                });
            }, false);
        })();
        
        // Auto-focus on first field
        document.addEventListener('DOMContentLoaded', function() {
            document.getElementById('identifiant').focus();
        });
        
        // Auto-generate identifier if empty
        document.addEventListener('DOMContentLoaded', function() {
            const identifiantField = document.getElementById('identifiant');
            const titreField = document.getElementById('titre');
            
            titreField.addEventListener('blur', function() {
                if (!identifiantField.value && titreField.value) {
                    const titre = titreField.value.substring(0, 3).toUpperCase();
                    const year = new Date().getFullYear();
                    const random = Math.floor(Math.random() * 1000).toString().padStart(3, '0');
                    identifiantField.value = `${titre}-${year}-${random}`;
                }
            });
        });
    </script>
</body>
</html>