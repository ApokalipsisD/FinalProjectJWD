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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Bitter:400,700">
    <style>
        <%@include file="/WEB-INF/css/header.css" %>
    </style>

</head>
<body>

<div>
    <div class="header-dark ">
        <nav class="navbar navbar-dark navbar-expand-md navigation-clean-search ">
            <div class="container  ">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/controller?command=show_main">Company Name</a>
                <div class="collapse navbar-collapse align-items-center justify-content-md-between " id="navcol-1">
                    <c:choose>
                        <c:when test="${empty sessionScope.user}">
                            <div class="collapse navbar-collapse align-items-center justify-content-md-end">
                                <span class="navbar-text"><a href="${pageContext.request.contextPath}/controller?command=show_login" class="login">Log In</a></span>
                                <a class="btn btn-light action-button" role="button" href="${pageContext.request.contextPath}/controller?command=show_sign_up">Sign Up</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <ul class="nav navbar-nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
<%--                                <li><a href="#" class="nav-link px-2 link-secondary">Home</a></li>--%>
                                <li><a href="${pageContext.request.contextPath}/controller?command=show_courses"
                                       class="nav-link px-2 link-dark">Catalog</a></li>
                                <li><a href="${pageContext.request.contextPath}/controller?command=show_my_courses" class="nav-link px-2 link-dark">My courses</a></li>
                                <c:if test="${sessionScope.account.role eq Role.TEACHER}">
                                    <li><a href="${pageContext.request.contextPath}/controller?command=show_teacher_courses" class="nav-link px-2 link-dark">Teacher courses</a></li>
                                </c:if>
                                <li><a href="${pageContext.request.contextPath}/controller?command=show_profile_page"
                                       class="nav-link px-2 link-dark">Profile</a></li>
                                    <%--                                <li><a href="#" class="nav-link px-2 link-dark">About</a></li>--%>
                            </ul>
                            <div>
                                <span class="navbar-text"><a href="${pageContext.request.contextPath}/controller?command=logout"
                                        class="login">Log Out</a></span>
                            </div>
                        </c:otherwise>
                    </c:choose>

                    <%--                    <ul class="nav navbar-nav">--%>
                    <%--                        <li class="nav-item" role="presentation"><a class="nav-link" href="#">Link</a></li>--%>
                    <%--                        <li class="dropdown"><a class="dropdown-toggle nav-link dropdown-toggle" data-toggle="dropdown" aria-expanded="false" href="#">Dropdown </a>--%>
                    <%--                            <div class="dropdown-menu" role="menu"><a class="dropdown-item" role="presentation" href="#">First Item</a><a class="dropdown-item" role="presentation" href="#">Second Item</a><a class="dropdown-item" role="presentation" href="#">Third Item</a></div>--%>
                    <%--                        </li>--%>
                    <%--                    </ul>--%>
                    <%--                    <form class="form-inline mr-auto" target="_self">--%>
                    <%--                        <div class="form-group">--%>
                    <%--                            <label for="search-field">--%>
                    <%--                                <i class="fa fa-search"></i>--%>
                    <%--                            </label><input class="form-control search-field" type="search" name="search" id="search-field"></div>--%>
                    <%--                    </form>--%>
                    <%--                    <div>--%>
                    <%--                        <span class="navbar-text">--%>
                    <%--                        <a href="#" class="login">Log In</a>--%>
                    <%--                    </span>--%>
                    <%--                        <a class="btn btn-light action-button" role="button" href="#">Sign Up</a>--%>
                    <%--                    </div>--%>

                </div>
            </div>
        </nav>

    </div>
</div>


<%--<div class="container">--%>
<%--    &lt;%&ndash;<div class="header d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom ">&ndash;%&gt;--%>
<%--    <header class="header d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">--%>
<%--        &lt;%&ndash;    <div class="container d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">&ndash;%&gt;--%>
<%--        <a class="navbar-brand " href="#">--%>
<%--            <img src="/assets/brand/bootstrap-solid.svg" width="30" height="30" class="d-inline-block align-top" alt="">--%>
<%--            Bootstrap--%>
<%--        </a>--%>
<%--        &lt;%&ndash;        <a href="/" class="d-flex align-items-center col-md-3 mb-2 mb-md-0 text-dark text-decoration-none">&ndash;%&gt;--%>
<%--        &lt;%&ndash;            <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap"><use xlink:href="#bootstrap"></use></svg>&ndash;%&gt;--%>
<%--        &lt;%&ndash;        </a>&ndash;%&gt;--%>

<%--        <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">--%>
<%--            <li><a href="#" class="nav-link px-2 link-secondary">Home</a></li>--%>
<%--            <li><a href="#" class="nav-link px-2 link-dark">Features</a></li>--%>
<%--            <li><a href="#" class="nav-link px-2 link-dark">Pricing</a></li>--%>
<%--            <li><a href="#" class="nav-link px-2 link-dark">FAQs</a></li>--%>
<%--            <li><a href="#" class="nav-link px-2 link-dark">About</a></li>--%>
<%--        </ul>--%>

<%--        <div class="col-md-3 text-end">--%>
<%--            <button type="button" class="btn btn-outline-primary me-2">Login</button>--%>
<%--            <button type="button" class="btn btn-primary">Sign-up</button>--%>
<%--        </div>--%>
<%--    </header>--%>
<%--</div>--%>


</body>
</html>
