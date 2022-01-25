<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="userName" var="userName"/>
<fmt:message bundle="${loc}" key="firstName" var="firstName"/>
<fmt:message bundle="${loc}" key="lastName" var="lastName"/>
<fmt:message bundle="${loc}" key="email" var="email"/>
<fmt:message bundle="${loc}" key="role" var="role"/>
<fmt:message bundle="${loc}" key="birthDate" var="birthDate"/>
<fmt:message bundle="${loc}" key="settings" var="settings"/>
<fmt:message bundle="${loc}" key="profile" var="profile"/>
<fmt:message bundle="${loc}" key="saveChanges" var="saveChanges"/>
<fmt:message bundle="${loc}" key="changePassword" var="changePassword"/>
<fmt:message bundle="${loc}" key="firstNameReq" var="firstNameReq"/>
<fmt:message bundle="${loc}" key="lastNameReq" var="lastNameReq"/>
<fmt:message bundle="${loc}" key="loginReq" var="loginReq"/>

<html>
<head>
    <title>${profile}</title>
    <style>
        <%@include file="/WEB-INF/css/bootstrap.min.css" %>
        <%@include file="/WEB-INF/css/editProfile.css" %>
    </style>
</head>
<body>
<%@include file="header.jsp"%>
<main class="main">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-12 col-lg-10 col-xl-8 mx-auto">
                <h2 class="h3 mb-4 page-title">${settings}</h2>
                <div class="my-4">
                    <ul class="nav nav-tabs mb-4" id="myTab" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" id="home" href="${pageContext.request.contextPath}/controller?command=show_edit_profile"
                               >${profile}</a>
                        </li>
                        <li>
                            <a class="nav-link active"  href="${pageContext.request.contextPath}/controller?command=show_password_page"
                               >${changePassword}</a>
                        </li>
                    </ul>
                    <form action="${pageContext.request.contextPath}/controller?command=edit_profile" method="post">
                        <div class="row mt-5 align-items-center">
                            <div class="col-md-3 text-center mb-5">
                                <div class="avatar avatar-xl">
                                    <img src="https://bootdey.com/img/Content/avatar/avatar1.png" alt="..."
                                         class="avatar-img rounded-circle"/>
                                </div>
                            </div>
                            <div class="col">
                                <div class="row align-items-center">
                                    <div class="col-md-7">
                                        <c:choose>
                                            <c:when test="${not empty sessionScope.account.firstName && not empty sessionScope.account.lastName}">
                                                <h2 class="font-weight-bold mb-0">${sessionScope.account.firstName} ${sessionScope.account.lastName}</h2>
                                                <h4 class="text-muted font-weight-normal">@${sessionScope.userName}</h4>
                                            </c:when>
                                            <c:otherwise>
                                                <h4 class="font-weight-normal">@${sessionScope.userName}</h4>
                                            </c:otherwise>
                                        </c:choose>

                                    </div>
                                </div>
                            </div>
                        </div>
                        <p style="font-size: 20px; color: red;">${requestScope.error}</p>
                        <hr class="my-4"/>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label for="firstname">${firstName}</label>
                                <input type="text" id="firstname" name="firstName" class="form-control"
                                <c:choose>
                                <c:when test="${not empty sessionScope.account.firstName}">
                                       value="${sessionScope.account.firstName}"
                                </c:when>
                                </c:choose>
                                       pattern="^([А-Я][а-яё]{2,20}|[A-Z][a-z]{2,20})$"
                                       title="${firstNameReq}">
                            </div>
                            <div class="form-group col-md-6">
                                <label for="lastname">${lastName}</label>
                                <input type="text" id="lastname" name="lastName" class="form-control"
                                <c:choose>
                                <c:when test="${not empty sessionScope.account.lastName}">
                                       value="${sessionScope.account.lastName}"
                                </c:when>
                                </c:choose>
                                       pattern="^([А-Я][а-яё]{2,20}|[A-Z][a-z]{2,20})$"
                                       title="${lastNameReq}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail4">${userName}</label>
                            <input type="text" name="userName" class="form-control" id="inputUsername"
                                   aria-label="inputEmail4" value="${sessionScope.user.login}" pattern="^[\w.-]{3,20}[0-9a-zA-Z]$"
                                   title="${loginReq}">
                        </div>
                        <div class="form-group">
                            <label for="inputEmail4">${email}</label>
                            <input type="email" name="email" class="form-control" id="inputEmail4"
                            <c:choose>
                            <c:when test="${not empty sessionScope.account.email}">
                                   value="${sessionScope.account.email}"
                            </c:when>
                            </c:choose>
                                   pattern="^([a-zA-Z0-9_-]+\.)*[a-zA-Z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$">
                        </div>
                        <div class="form-group">
                            <label for="inputAddress5">${birthDate}</label>
                            <input type="date" name="birthDate" class="form-control" id="inputAddress5"
                            <c:if test="${not empty sessionScope.account.birthDate}">
                                   value="${sessionScope.account.birthDate}"
                            </c:if>

                        </div>
                        <hr class="my-4"/>

                        <button type="submit" class="btn btn-primary">${saveChanges}</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>
<%@include file="footer.jsp"%>
</body>
</html>


