<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jwdt" uri="jwdTags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="ru"/>
<fmt:setBundle basename="locale" var="loc"/>


<%@ page import="com.epam.jwd.dao.entity.Role" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Main Page</title>
</head>
<body>
<jwdt:welcomeText/>

<br>
<c:choose>
    <c:when test="${empty sessionScope.user}">
        <a href="${pageContext.request.contextPath}/controller?command=show_sign_up">SignUp page</a><br>
        <a href="${pageContext.request.contextPath}/controller?command=show_login">Login page</a>
    </c:when>
    <c:otherwise>
        <a href="${pageContext.request.contextPath}/controller?command=logout">Logout</a>
        <a href="${pageContext.request.contextPath}/controller?command=show_profile_page">Profile</a>
        <a href="${pageContext.request.contextPath}/controller?command=show_courses">Courses</a>
    </c:otherwise>
</c:choose>
<jwdt:currentTime/>
<%--${message}--%>
</body>
</html>