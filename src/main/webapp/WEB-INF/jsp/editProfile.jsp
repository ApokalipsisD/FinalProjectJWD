<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

<html>
<head>
    <title>Profile</title>
    <%--    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" />--%>
    <style>
        <%@include file="/WEB-INF/css/bootstrap.min.css" %>
<%--                <%@include file="/WEB-INF/css/jquery-1.10.2.min.js"%>--%>
<%--                <%@include file="/WEB-INF/css/bootstrap.bundle.min.js"%>--%>
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
                            <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab"
                               aria-controls="home" aria-selected="false">${profile}</a>
                        </li>
                    </ul>
                    <%--                <form action="${pageContext.request.contextPath}/controller?command=edit_profile" method="post">--%>
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
                        <hr class="my-4"/>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label for="firstname">${firstName}</label>
                                <input type="text" id="firstname" name="firstName" class="form-control"
                                <c:choose>
                                <c:when test="${not empty sessionScope.account.firstName}">
                                       value="${sessionScope.account.firstName}"
                                </c:when>
                                </c:choose>>
                            </div>
                            <div class="form-group col-md-6">
                                <label for="lastname">${lastName}</label>
                                <input type="text" id="lastname" name="lastName" class="form-control"
                                <c:choose>
                                <c:when test="${not empty sessionScope.account.lastName}">
                                       value="${sessionScope.account.lastName}"
                                </c:when>
                                </c:choose>>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail4">${userName}</label>
                            <input type="text" name="userName" class="form-control" id="inputUsername"
                                   value="${sessionScope.user.login}">
                        </div>
                        <div class="form-group">
                            <label for="inputEmail4">${email}</label>
                            <input type="email" name="email" class="form-control" id="inputEmail4"
                            <c:choose>
                            <c:when test="${not empty sessionScope.account.email}">
                                   value="${sessionScope.account.email}"
                            </c:when>
                            </c:choose>>
                        </div>
                        <div class="form-group">
                            <label for="inputAddress5">${birthDate}</label>
                            <input type="date" name="birthDate" class="form-control" id="inputAddress5"
                            <c:choose>
                            <c:when test="${not empty sessionScope.account.birthDate}">
                                   value="${sessionScope.account.birthDate}"
                            </c:when>
                            </c:choose>>
                        </div>
                        <hr class="my-4"/>
                        <%--                    <div class="row mb-4">--%>
                        <%--                        <div class="col-md-6">--%>
                        <%--                            <div class="form-group">--%>
                        <%--                                <label for="inputPassword4">Old Password</label>--%>
                        <%--                                <input type="password" class="form-control" id="inputPassword5" />--%>
                        <%--                            </div>--%>
                        <%--                            <div class="form-group">--%>
                        <%--                                <label for="inputPassword5">New Password</label>--%>
                        <%--                                <input type="password" class="form-control" id="inputPassword5" />--%>
                        <%--                            </div>--%>
                        <%--                            <div class="form-group">--%>
                        <%--                                <label for="inputPassword6">Confirm Password</label>--%>
                        <%--                                <input type="password" class="form-control" id="inputPassword6" />--%>
                        <%--                            </div>--%>
                        <%--                        </div>--%>
                        <%--                        <div class="col-md-6">--%>
                        <%--                            <p class="mb-2">Password requirements</p>--%>
                        <%--                            <p class="small text-muted mb-2">To create a new password, you have to meet all of the following requirements:</p>--%>
                        <%--                            <ul class="small text-muted pl-4 mb-0">--%>
                        <%--                                <li>Minimum 8 character</li>--%>
                        <%--                                <li>At least one special character</li>--%>
                        <%--                                <li>At least one number</li>--%>
                        <%--                                <li>Can’t be the same as a previous password</li>--%>
                        <%--                            </ul>--%>
                        <%--                        </div>--%>
                        <%--                    </div>--%>
                        <button type="submit" class="btn btn-primary">${saveChanges}</button>
                    </form>
                    <form action="${pageContext.request.contextPath}/controller?command=change_password" method="post">
                        <button type="submit" class="btn btn-primary">${changePassword}</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>

</body>
</html>


