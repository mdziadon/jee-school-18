<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <table border="1">
        <tr>
            <th>Excercise id</th>
            <th>User id</th>
            <th>Sedcription</th>
        </tr>
        <c:forEach items="${solutions}" var="solution">
            <tr>
                <td>${solution.exerciseId}</td>
                <td>${solution.userId}</td>
                <td>${solution.description}</td>
            </tr>
        </c:forEach>

    </table>
</body>
</html>
