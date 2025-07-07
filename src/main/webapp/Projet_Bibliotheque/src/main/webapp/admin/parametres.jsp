<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Paramètres - Admin</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <!-- Bootstrap + Font Awesome -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    
    <style>
        body {
            background-color: #f5f6fa;
            font-family: 'Segoe UI', sans-serif;
        }

        .sidebar {
            background: linear-gradient(135deg, #56ab2f 0%, #a8e063 100%);
            min-height: 100vh;
            padding-top: 30px;
        }

        .sidebar .nav-link {
            color: rgba(255,255,255,0.8);
            padding: 12px 20px;
            border-radius: 8px;
            margin: 5px 0;
            font-size: 16px;
            transition: all 0.3s;
        }

        .sidebar .nav-link:hover,
        .sidebar .nav-link.active {
            color: white;
            background: rgba(255,255,255,0.1);
            font-weight: bold;
        }

        .sidebar i {
            margin-right: 10px;
        }

        .sidebar hr {
            border-color: rgba(255, 255, 255, 0.2);
        }

        .profile-photo {
            width: 80px;
            height: 80px;
            border-radius: 50%;
            object-fit: cover;
            border: 3px solid rgba(255,255,255,0.3);
        }

        .profile-photo-default {
            width: 80px;
            height: 80px;
            border-radius: 50%;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 30px;
            border: 3px solid rgba(255,255,255,0.3);
        }

        .card {
            border-radius: 12px;
            border: none;
            box-shadow: 0 4px 6px rgba(0,0,0,0.07);
        }

        .card h2 {
            font-size: 24px;
            font-weight: 600;
            margin-bottom: 20px;
        }

        .form-control:focus {
            border-color: #56ab2f;
            box-shadow: 0 0 0 0.2rem rgba(86, 171, 47, 0.25);
        }

        .btn-success {
            background: linear-gradient(135deg, #56ab2f 0%, #a8e063 100%);
            border: none;
        }

        .btn-success:hover {
            background: linear-gradient(135deg, #4a9a26 0%, #97d55a 100%);
        }

        .photo-preview {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            object-fit: cover;
            border: 3px solid #dee2e6;
            margin-top: 10px;
        }

        .upload-area {
            border: 2px dashed #dee2e6;
            border-radius: 8px;
            padding: 20px;
            text-align: center;
            transition: all 0.3s;
            cursor: pointer;
        }

        .upload-area:hover {
            border-color: #56ab2f;
            background-color: rgba(86, 171, 47, 0.05);
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <!-- Sidebar -->
        <nav class="col-md-3 col-lg-2 d-md-block sidebar">
            <div class="text-center mb-4">
                <c:choose>
                    <c:when test="${not empty sessionScope.photo and sessionScope.photo != 'default-avatar.png'}">
                        <img src="${pageContext.request.contextPath}/uploads/photos/${sessionScope.photo}" 
                             alt="Photo de profil" 
                             class="profile-photo mb-2"
                             onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';">
                        <div class="profile-photo-default mb-2" style="display: none;">
                            <i class="fas fa-user-shield"></i>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="profile-photo-default mb-2">
                            <i class="fas fa-user-shield"></i>
                        </div>
                    </c:otherwise>
                </c:choose>
                <h5 class="text-white mt-2">${sessionScope.nomComplet}</h5>
                <small class="text-white-50">Administrateur</small>
            </div>
            <ul class="nav flex-column px-3">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/dashboard.jsp">
                        <i class="fas fa-tachometer-alt"></i> Tableau de bord
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/bibliothecaires.jsp">
                        <i class="fas fa-users"></i> Bibliothécaires
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/statistiques">
                        <i class="fas fa-chart-line"></i> Statistiques
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="${pageContext.request.contextPath}/admin/parametres.jsp">
                        <i class="fas fa-cog"></i> Paramètres
                    </a>
                </li>
                <hr>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/logout">
                        <i class="fas fa-sign-out-alt"></i> Déconnexion
                    </a>
                </li>
            </ul>
        </nav>

        <!-- Main Content -->
        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-5 py-4">
            <div class="card shadow-sm p-4">
                <h2><i class="fas fa-cog me-2"></i>Paramètres du compte</h2>
                
                <!-- Section Photo de profil -->
                <div class="row mb-4">
                    <div class="col-md-6">
                        <h5><i class="fas fa-camera me-2"></i>Photo de profil</h5>
                        <div class="upload-area" onclick="document.getElementById('photoInput').click();">
                            <i class="fas fa-cloud-upload-alt fa-2x text-muted mb-2"></i>
                            <p class="text-muted">Cliquez pour sélectionner une photo</p>
                            <small class="text-muted">Formats acceptés: JPG, PNG, GIF (max 5MB)</small>
                        </div>
                        <input type="file" id="photoInput" name="photo" accept="image/*" style="display: none;" onchange="previewPhoto(this)">
                        
                        <div id="photoPreview" class="mt-3" style="display: none;">
                            <img id="previewImg" class="photo-preview" alt="Aperçu">
                            <button type="button" class="btn btn-sm btn-outline-danger mt-2" onclick="removePhoto()">
                                <i class="fas fa-trash me-1"></i>Supprimer
                            </button>
                        </div>
                        
                        <c:if test="${not empty sessionScope.photo and sessionScope.photo != 'default-avatar.png'}">
                            <div class="mt-3">
                                <p class="text-muted mb-2">Photo actuelle:</p>
                                <img src="${pageContext.request.contextPath}/uploads/photos/${sessionScope.photo}" 
                                     alt="Photo actuelle" class="photo-preview">
                            </div>
                        </c:if>
                    </div>
                </div>

                <!-- Section Informations personnelles -->
                <div class="row">
                    <div class="col-md-6">
                        <h5><i class="fas fa-user me-2"></i>Informations personnelles</h5>
                        <form method="post" action="${pageContext.request.contextPath}/AdminParamServlet">
                            <div class="mb-3">
                                <label for="prenom" class="form-label">Prénom</label>
                                <input type="text" class="form-control" id="prenom" name="prenom" value="${sessionScope.utilisateur.prenom}" required>
                            </div>
                            <div class="mb-3">
                                <label for="nom" class="form-label">Nom</label>
                                <input type="text" class="form-control" id="nom" name="nom" value="${sessionScope.utilisateur.nom}" required>
                            </div>
                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" name="email" value="${sessionScope.utilisateur.email}" required>
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label">Nouveau mot de passe</label>
                                <input type="password" class="form-control" id="password" name="password" placeholder="Laissez vide pour ne pas changer">
                            </div>
                            <button type="submit" class="btn btn-success">
                                <i class="fas fa-save me-2"></i>Enregistrer les modifications
                            </button>
                        </form>
                    </div>
                </div>

                <c:if test="${not empty message}">
                    <div class="alert alert-info mt-3">${message}</div>
                </c:if>
            </div>
        </main>
    </div>
</div>

<!-- JS Bootstrap -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
function previewPhoto(input) {
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('previewImg').src = e.target.result;
            document.getElementById('photoPreview').style.display = 'block';
        };
        reader.readAsDataURL(input.files[0]);
        
        // Upload automatique de la photo
        uploadPhoto(input.files[0]);
    }
}

function removePhoto() {
    document.getElementById('photoInput').value = '';
    document.getElementById('photoPreview').style.display = 'none';
}

function uploadPhoto(file) {
    const formData = new FormData();
    formData.append('photo', file);
    
    fetch('${pageContext.request.contextPath}/upload-photo', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            // Recharger la page pour mettre à jour l'affichage
            setTimeout(() => {
                location.reload();
            }, 1000);
        } else {
            alert('Erreur lors de l\'upload: ' + data.error);
        }
    })
    .catch(error => {
        console.error('Erreur:', error);
        alert('Erreur lors de l\'upload de la photo');
    });
}
</script>
</body>
</html>
