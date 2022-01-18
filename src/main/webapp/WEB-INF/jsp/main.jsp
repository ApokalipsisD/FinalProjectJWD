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
    <style>
        .under{
            margin-top: 3%;
            margin-bottom: 3%;
            border-radius: 0.5rem;
            background: #fff;
            padding: 90px 3% 3%;
        }
    </style>
</head>
<body>
<%@include file="header.jsp"%>

<main class="main">
    <jwdt:welcomeText/>
    <jwdt:currentTime/>
    <c:choose>
        <c:when test="${not empty error}">
            <p style="color: red;">${error}</p>
        </c:when>

        <c:when test="${not empty message}">
            <p style="color: red;">${message}</p>
        </c:when>
    </c:choose>
</main>


<%--<br>--%>
<%--<c:choose>--%>
<%--    <c:when test="${empty sessionScope.user}">--%>
<%--        <a href="${pageContext.request.contextPath}/controller?command=show_sign_up">SignUp page</a><br>--%>
<%--        <a href="${pageContext.request.contextPath}/controller?command=show_login">Login page</a>--%>
<%--    </c:when>--%>
<%--    <c:otherwise>--%>
<%--        <a href="${pageContext.request.contextPath}/controller?command=logout">Logout</a>--%>
<%--        <a href="${pageContext.request.contextPath}/controller?command=show_profile_page">Profile</a>--%>
<%--        <a href="${pageContext.request.contextPath}/controller?command=show_courses">Catalog    </a>--%>
<%--    </c:otherwise>--%>
<%--</c:choose>--%>

<%--${message}--%>
<%@include file="footer.jsp"%>
</body>
</html>