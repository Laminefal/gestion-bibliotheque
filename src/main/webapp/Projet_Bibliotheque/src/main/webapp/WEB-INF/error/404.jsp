<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Page non trouvée - Gestion Bibliothèque</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .error-page {
            min-height: 100vh;
            display: flex;
            align-items: center;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }
        .error-card {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
        }
        .error-icon {
            font-size: 8rem;
            color: #dc3545;
            margin-bottom: 2rem;
        }
        .error-code {
            font-size: 6rem;
            font-weight: bold;
            color: #6c757d;
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>
    <div class="error-page">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8 col-lg-6">
                    <div class="error-card p-5 text-center">
                        <div class="error-icon">
                            <i class="fas fa-exclamation-triangle"></i>
                        </div>
                        <div class="error-code">404</div>
                        <h2 class="mb-4">Page non trouvée</h2>
                        <p class="lead mb-4">
                            La page que vous recherchez n'existe pas ou a été déplacée.
                        </p>
                        <div class="alert alert-info" role="alert">
                            <i class="fas fa-info-circle me-2"></i>
                            <strong>URL demandée :</strong> ${pageContext.request.requestURL}
                        </div>
                        <div class="d-grid gap-2 d-md-flex justify-content-md-center">
                            <a href="${pageContext.request.contextPath}/" class="btn btn-primary btn-lg">
                                <i class="fas fa-home me-2"></i>Retour à l'accueil
                            </a>
                            <a href="javascript:history.back()" class="btn btn-outline-secondary btn-lg">
                                <i class="fas fa-arrow-left me-2"></i>Page précédente
                            </a>
                        </div>
                        <hr class="my-4">
                        <div class="row text-start">
                            <div class="col-md-6">
                                <h5><i class="fas fa-lightbulb me-2"></i>Suggestions :</h5>
                                <ul class="list-unstyled">
                                    <li><i class="fas fa-check text-success me-2"></i>Vérifiez l'URL</li>
                                    <li><i class="fas fa-check text-success me-2"></i>Assurez-vous que l'adresse est correcte</li>
                                    <li><i class="fas fa-check text-success me-2"></i>Naviguez depuis la page principale</li>
                                </ul>
                            </div>
                            <div class="col-md-6">
                                <h5><i class="fas fa-link me-2"></i>Liens utiles :</h5>
                                <ul class="list-unstyled">
                                    <li><a href="${pageContext.request.contextPath}/livres" class="text-decoration-none">
                                        <i class="fas fa-book me-2"></i>Gestion des livres
                                    </a></li>
                                    <li><a href="${pageContext.request.contextPath}/abonnes" class="text-decoration-none">
                                        <i class="fas fa-users me-2"></i>Gestion des abonnés
                                    </a></li>
                                    <li><a href="${pageContext.request.contextPath}/emprunts" class="text-decoration-none">
                                        <i class="fas fa-exchange-alt me-2"></i>Gestion des emprunts
                                    </a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 