<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="userName" var="userName"/>
<fmt:message bundle="${loc}" key="firstName" var="firstName"/>
<fmt:message bundle="${loc}" key="lastName" var="lastName"/>
<fmt:message bundle="${loc}" key="birthDate" var="birthDate"/>
<fmt:message bundle="${loc}" key="settings" var="settings"/>
<fmt:message bundle="${loc}" key="profile" var="profile"/>
<fmt:message bundle="${loc}" key="saveChanges" var="saveChanges"/>
<fmt:message bundle="${loc}" key="oldPassword" var="oldPassword"/>
<fmt:message bundle="${loc}" key="newPassword" var="newPassword"/>
<fmt:message bundle="${loc}" key="confirmPassword" var="confirmPassword"/>
<fmt:message bundle="${loc}" key="passwordReq" var="passwordReq"/>
<fmt:message bundle="${loc}" key="passReq" var="passReq"/>
<fmt:message bundle="${loc}" key="passToCreate" var="passToCreate"/>
<fmt:message bundle="${loc}" key="minChar" var="minChar"/>
<fmt:message bundle="${loc}" key="oneLUl" var="oneLUl"/>
<fmt:message bundle="${loc}" key="oneNumber" var="oneNumber"/>
<fmt:message bundle="${loc}" key="samePass" var="samePass"/>
<fmt:message bundle="${loc}" key="changePassword" var="changePassword"/>

<html>
<head>
    <title>${profile}</title>
    <style>
        <%@include file="/WEB-INF/css/bootstrap.min.css" %>
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
                               aria-controls="changePass" aria-selected="false">${changePassword}</a>
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
                                    <label for="inputOldPassword">${oldPassword}</label>
                                    <input name="oldPass" type="password" class="form-control" id="inputOldPassword"/>
                                </div>
                                <div class="form-group">
                                    <label for="inputNewPassword">${newPassword}</label>
                                    <input name="newPass" type="password" class="form-control" id="inputNewPassword"
                                           required pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,20}"
                                           title="${passwordReq}"/>
                                </div>
                                <div class="form-group">
                                    <label for="confirmNewPassword">${confirmPassword}</label>
                                    <input name="confirmNewPass" type="password" class="form-control" id="confirmNewPassword"
                                           required pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,20}"
                                           title="${passwordReq}"/>
                                </div>
                            </div>

                            <div class="col-md-6">
                                <p class="mb-2">${passReq}</p>
                                <p class="small text-muted mb-2">${passToCreate}:</p>
                                <ul class="small text-muted pl-4 mb-0">
                                    <li>${minChar}</li>
                                    <li>${oneLUl}</li>
                                    <li>${oneNumber}</li>
                                    <li>${samePass}</li>
                                </ul>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary">${saveChanges}</button>
                    </form>

                    <hr class="my-4"/>
                </div>
            </div>
        </div>
    </div>
</main>
<%@include file="footer.jsp" %>
</body>
</html>