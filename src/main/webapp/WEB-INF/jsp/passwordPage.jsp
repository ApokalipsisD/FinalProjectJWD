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
<%@include file="header.jsp" %>
<main class="main">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-12 col-lg-10 col-xl-8 mx-auto">
                <h2 class="h3 mb-4 page-title">${settings}</h2>
                <div class="my-4">
                    <ul class="nav nav-tabs mb-4" id="myTab" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" id="home" data-toggle="tab"
                               href="${pageContext.request.contextPath}/controller?command=show_edit_profile" role="tab"
                               aria-controls="home" aria-selected="false">${profile}</a>
                        </li>
                        <li>
                            <a class="nav-link active" id="changePass" data-toggle="tab"
                               href="${pageContext.request.contextPath}/controller?command=show_password_page"
                               role="tab"
                               aria-controls="changePass" aria-selected="false">Change Password</a>
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
                    </form>
                    <p style="font-size: 20px; color: red;">${requestScope.error}</p>
                    <hr class="my-4"/>
                    <form action="${pageContext.request.contextPath}/controller?command=change_password" method="post">
                        <div class="row mb-4">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="inputOldPassword">Old Password</label>
                                    <input name="oldPass" type="password" class="form-control" id="inputOldPassword"/>
                                </div>
                                <div class="form-group">
                                    <label for="inputNewPassword">New Password</label>
                                    <input name="newPass" type="password" class="form-control" id="inputNewPassword"
                                           required pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,20}"
                                           title="Password must contain at least one number, one lowercase and one uppercase letter, min password length 8"/>
                                </div>
                                <div class="form-group">
                                    <label for="confirmNewPassword">Confirm Password</label>
                                    <input name="confirmNewPass" type="password" class="form-control" id="confirmNewPassword"
                                           required pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,20}"
                                           title="Password must contain at least one number, one lowercase and one uppercase letter, min password length 8"/>
                                </div>
                            </div>

                            <div class="col-md-6">
                                <p class="mb-2">Password requirements</p>
                                <p class="small text-muted mb-2">To create a new password, you have to meet all of the
                                    following requirements:</p>
                                <ul class="small text-muted pl-4 mb-0">
                                    <li>Minimum 8 character</li>
                                    <li>At least one lowercase and one uppercase letter</li>
                                    <li>At least one number</li>
                                    <li>Can’t be the same as a previous password</li>
                                </ul>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary">${saveChanges}</button>
                    </form>

                    <hr class="my-4"/>


                    <%--                        <button type="button" class="btn btn-primary" href="/controller?command=change_password">${changePassword}</button>--%>


                </div>
            </div>
        </div>
    </div>

    <%--    <c:choose>--%>
    <%--        <c:when test="${not empty error}">--%>
    <%--            <p style="color: red;">${error}</p>--%>
    <%--        </c:when>--%>

    <%--        <c:when test="${not empty message}">--%>
    <%--            <p style="color: red;">${message}</p>--%>
    <%--        </c:when>--%>
    <%--    </c:choose>--%>

</main>
<%@include file="footer.jsp" %>
</body>
</html>