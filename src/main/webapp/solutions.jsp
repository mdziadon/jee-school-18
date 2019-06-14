<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%@include file="fragments/header.jspf"%>

<a href="/solutions/add">Dodaj nowe rozwiązanie</a>

<br><br>

    <table border="1">
        <tr>
            <th>Excercise id</th>
            <th>User id</th>
            <th>Sedcription</th>
            <th>Actions</th>
        </tr>
        <c:forEach items="${solutions}" var="solution">
            <tr>
                <td>${solution.exerciseId}</td>
                <td>${solution.userId}</td>
                <td>${solution.description}</td>
                <td>
                    <a href="/solutions/update?id=${solution.id}">Modyfikuj</a>
                    <a href="/solutions/delete?id=${solution.id}">Usuń</a>
                </td>
            </tr>
        </c:forEach>

    </table>

<%@include file="fragments/footer.jspf"%>

</body>
</html>
