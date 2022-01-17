<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
Something went wrong.

    <p>${sessionScope.error}</p>
${error}
<%@include file="footer.jsp"%>
</body>
</html>
