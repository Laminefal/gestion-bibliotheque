<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.biblio.web.entities.Bibliothecaire" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Tableau de Bord</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; }
        .header { background: #4CAF50; color: white; padding: 10px 20px; margin-bottom: 20px; }
        .menu { background: #f1f1f1; padding: 15px; }
        .menu a { display: block; padding: 8px 0; text-decoration: none; color: #333; }
        .menu a:hover { color: #4CAF50; }
        .logout { margin-top: 20px; }
    </style>
</head>
<body>
    <div class="header">
        <h1>Tableau de Bord</h1>
        <%
            Bibliothecaire user = (Bibliothecaire) session.getAttribute("user");
            out.print("Connecté en tant que : <strong>" + user.getPrenom() + " " + user.getNom() + "</strong>");
        %>
    </div>
    
    <div class="menu">
        <h3>Menu Principal</h3>
        <a href="${pageContext.request.contextPath}/gestion/abonnes">Gestion des Abonnés</a>
        <a href="${pageContext.request.contextPath}/gestion/livres">Gestion des Livres</a>
        <a href="${pageContext.request.contextPath}/gestion/emprunts">Gestion des Emprunts</a>
        
        <div class="logout">
            <a href="${pageContext.request.contextPath}/auth/logout">Déconnexion</a>
        </div>
    </div>
</body>
</html>