<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title>Registration</title>
</head>
<body>
<h1>Registration</h1>
        <c:choose>
            <c:when test="${not empty requestScope.error}">
                <p>${requestScope.error}</p>
                <a href="${pageContext.request.contextPath}/controller?command=show_main_page">Try again</a>
            </c:when>
            <c:otherwise>
            <form action="${pageContext.request.contextPath}/controller?command=sign_up_command" method="post">
			<table style="with: 50%">
                <tr>
                    <td>Login</td>
                    <td><input type="text" name="login" /></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="text" name="password" /></td>
                </tr>
                <tr>
                    <td>Repeat password</td>
                    <td><input type="text" name="repeat_password" /></td>
                </tr>
            </table>
            <input type="submit" value="Submit" /></form>
            </c:otherwise>
        </c:choose>
</body>
</html>