<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accès interdit - Gestion de Bibliothèque</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
        }
        .error-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }
        .error-header {
            background: linear-gradient(135deg, #6f42c1 0%, #5a2d91 100%);
            color: white;
            padding: 40px;
            text-align: center;
        }
        .error-body {
            padding: 40px;
        }
        .btn-home {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            border-radius: 10px;
            padding: 12px 30px;
            font-weight: 600;
            transition: all 0.3s ease;
        }
        .btn-home:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="error-card">
                    <div class="error-header">
                        <i class="fas fa-ban fa-4x mb-3"></i>
                        <h1 class="display-4">403</h1>
                        <h3>Accès interdit</h3>
                        <p class="mb-0">Vous n'avez pas les permissions nécessaires pour accéder à cette page.</p>
                    </div>
                    
                    <div class="error-body text-center">
                        <p class="lead mb-4">
                            Désolé, vous n'avez pas les droits d'accès requis pour consulter cette page.
                            Veuillez vous connecter avec un compte ayant les permissions appropriées.
                        </p>
                        
                        <div class="row mb-4">
                            <div class="col-md-6">
                                <div class="card border-0 bg-light">
                                    <div class="card-body">
                                        <i class="fas fa-user-lock fa-2x text-warning mb-2"></i>
                                        <h6>Permissions</h6>
                                        <small class="text-muted">Vérifiez vos droits d'accès</small>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="card border-0 bg-light">
                                    <div class="card-body">
                                        <i class="fas fa-sign-in-alt fa-2x text-info mb-2"></i>
                                        <h6>Connexion</h6>
                                        <small class="text-muted">Connectez-vous avec un autre compte</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="d-grid gap-2 d-md-flex justify-content-md-center">
                            <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-primary btn-home">
                                <i class="fas fa-sign-in-alt me-2"></i>Se connecter
                            </a>
                            <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-outline-secondary">
                                <i class="fas fa-home me-2"></i>Retour à l'accueil
                            </a>
                        </div>
                        
                        <hr class="my-4">
                        
                        <div class="text-muted">
                            <small>
                                <i class="fas fa-info-circle me-1"></i>
                                Si vous pensez qu'il s'agit d'une erreur, contactez l'administrateur.
                            </small>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 