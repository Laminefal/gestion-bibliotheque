<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Suppression du bibliothécaire</title>
</head>
<body>
    <h2>Suppression du bibliothécaire</h2>
    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>
    <a href="${pageContext.request.contextPath}/admin/bibliothecaires">Retour à la liste des bibliothécaires</a>
</body>
</html>
