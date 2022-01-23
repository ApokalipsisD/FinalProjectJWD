<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jwdt" uri="jwdTags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="catalog" var="catalog"/>
<fmt:message bundle="${loc}" key="profile" var="profile"/>
<fmt:message bundle="${loc}" key="login" var="login"/>
<fmt:message bundle="${loc}" key="signUp" var="signUp"/>


<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Main Page</title>
</head>
<body>
<%@include file="header.jsp"%>

<main class="main">

        <div class="container col-md-5">
            <h1 style="text-align: center; font-size: 60px; padding-bottom: 20px; padding-top: 20px">Company name</h1>
            <h3 style="text-align: center"><jwdt:welcomeText/></h3>
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <div class="row justify-content-between" style="margin-top: 50px">
                        <div class="card card-body col-md-5 col-sm-5 col-lg-5">
                            <div class="card-body align-items-center justify-content-center"
                                 style="height: 150px; justify-content: center; text-align: center">
                                <p style="text-align: center; font-size: 30px">${catalog}</p>
                                <a class="btn btn-primary btn-lg"
                                   href="${pageContext.request.contextPath}/controller?command=show_courses"
                                   role="button" style="align-content: center; align-items: center" type="submit">${catalog}</a>
                            </div>
                        </div>
                        <div class="card card-body col-md-5 col-sm-5 col-lg-5">
                            <div class="card-body align-items-center justify-content-center"
                                 style="height: 150px; justify-content: center; text-align: center">
                                <p style="text-align: center; font-size: 30px">${profile}</p>
                                <a class="btn btn-primary btn-lg"
                                   href="${pageContext.request.contextPath}/controller?command=show_profile_page"
                                   role="button" style="align-content: center; align-items: center" type="submit">${profile}</a>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="row justify-content-between" style="margin-top: 50px">
                        <div class="card card-body col-md-5 col-sm-5 col-lg-5">
                            <div class="card-body align-items-center justify-content-center"
                                 style="height: 150px; justify-content: center; text-align: center">
                                <p style="text-align: center; font-size: 30px">${login}</p>
                                <a class="btn btn-primary btn-lg"
                                   href="${pageContext.request.contextPath}/controller?command=show_login"
                                   role="button" style="align-content: center; align-items: center" type="submit">${login}</a>
                            </div>
                        </div>
                        <div class="card card-body col-md-5 col-sm-5 col-lg-5">
                            <div class="card-body align-items-center justify-content-center"
                                 style="height: 150px; justify-content: center; text-align: center">
                                <p style="text-align: center; font-size: 30px">${signUp}</p>
                                <a class="btn btn-primary btn-lg"
                                   href="${pageContext.request.contextPath}/controller?command=show_sign_up"
                                   role="button" style="align-content: center; align-items: center" type="submit">${signUp}</a>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>

        </div>

</main>

<%@include file="footer.jsp"%>
</body>
</html>