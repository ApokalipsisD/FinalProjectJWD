<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="userName" var="userName"/>
<fmt:message bundle="${loc}" key="firstName" var="firstName"/>
<fmt:message bundle="${loc}" key="lastName" var="lastName"/>
<fmt:message bundle="${loc}" key="email" var="email"/>
<fmt:message bundle="${loc}" key="role" var="role"/>
<fmt:message bundle="${loc}" key="birthDate" var="birthDate"/>
<fmt:message bundle="${loc}" key="edit" var="edit"/>
<fmt:message bundle="${loc}" key="delete" var="delete"/>


<html>
<head>
    <title>Profile</title>
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"/>
    <style>
        <%@include file="/WEB-INF/css/bootstrap.min.css" %>
    </style>

    <style>
        body {
            margin-top: 20px;
            background: #f5f5f5;
        }

        .ui-w-100 {
            width: 100px !important;
            height: auto;
        }

        .card {
            background-clip: padding-box;
            box-shadow: 0 1px 4px rgba(24, 28, 33, 0.012);
        }

        .user-view-table td:first-child {
            width: 9rem;
        }

        .user-view-table td {
            padding-right: 0;
            padding-left: 0;
            border: 0;
        }

        .text-light {
            color: #babbbc !important;
        }

        .card .row-bordered > [class*=" col-"]::after {
            border-color: rgba(24, 28, 33, 0.075);
        }

        .text-xlarge {
            font-size: 170% !important;
        }
    </style>

</head>
<body>
<%--    <form action="${pageContext.request.contextPath}/controller?command=profile" method="post">--%>
<div class="container bootdey flex-grow-1 container-p-y">
    <div class="media align-items-center py-3 mb-3">
        <img src="https://bootdey.com/img/Content/avatar/avatar1.png" alt=""
             class="d-block ui-w-100 rounded-circle">
        <div class="media-body ml-4">
            <c:choose>
                <c:when test="${not empty sessionScope.account.firstName && not empty sessionScope.account.lastName}">
                <h4 class="font-weight-bold mb-0">${sessionScope.account.firstName} ${sessionScope.account.lastName}
                    <span class="text-muted font-weight-normal">@${sessionScope.userName}</span> </h4>
                </c:when>
                <c:otherwise>
                    <h4 class="font-weight-normal">@${sessionScope.userName}</h4>
                </c:otherwise>
            </c:choose>

<%--            <h4 class="font-weight-bold mb-0">John Doe <span--%>
<%--                    class="text-muted font-weight-normal">@${sessionScope.userName}</span>--%>
<%--            </h4>--%>
            <a href="controller?command=show_edit_profile" class="btn btn-primary btn-sm">${edit}</a>
            <a href="javascript:void(0)" class="btn btn-default btn-sm">${delete}</a>
        </div>
    </div>

    <div class="card mb-4">
        <div class="card-body">
            <table class="table user-view-table m-0">
                <tbody>
                <tr>
                    <td>${userName}:</td>
                    <c:choose>
                        <c:when test="${not empty sessionScope.user.login}">
                            <td>${sessionScope.user.login}</td>
                        </c:when>
                        <c:otherwise>
                            <td>-</td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                <tr>
                    <td>${firstName}:</td>
                    <c:choose>
                        <c:when test="${not empty sessionScope.account.firstName}">
                            <td>${sessionScope.account.firstName}</td>
                        </c:when>
                        <c:otherwise>
                            <td>-</td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                <tr>
                    <td>${lastName}:</td>
                    <c:choose>
                        <c:when test="${not empty sessionScope.account.lastName}">
                            <td>${sessionScope.account.lastName}</td>
                        </c:when>
                        <c:otherwise>
                            <td>-</td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                <tr>
                    <td>${email}:</td>
                    <c:choose>
                        <c:when test="${not empty sessionScope.account.email}">
                            <td>${sessionScope.account.email}</td>
                        </c:when>
                        <c:otherwise>
                            <td>-</td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                <tr>
                    <td>${role}:</td>
                    <td>${sessionScope.account.role}</td>
                </tr>
                <tr>
                    <td>${birthDate}:</td>
                    <c:choose>
                        <c:when test="${not empty sessionScope.account.birthDate}">
                            <td>${sessionScope.account.birthDate}</td>
                        </c:when>
                        <c:otherwise>
                            <td>-</td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>