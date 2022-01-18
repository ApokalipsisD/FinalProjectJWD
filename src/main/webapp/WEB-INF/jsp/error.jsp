<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<%@include file="header.jsp" %>
<main class="main">
    Something went wrong.
    <p>${sessionScope.error}</p>
    ${error}
</main>
<%@include file="footer.jsp" %>
</body>
</html>
