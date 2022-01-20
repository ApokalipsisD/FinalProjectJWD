<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jwdt" uri="jwdTags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="ru"/>
<fmt:setBundle basename="locale" var="loc"/>

<%@ page import="com.epam.jwd.dao.entity.Role" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">

    <title>My Courses</title>
    <style>

        <%@include file="/WEB-INF/css/bootstrap.min.css" %>
        <%--        <%@include file="/WEB-INF/css/catalog.css" %>--%>

    </style>

</head>
<body>
<%@include file="header.jsp"%>
<main class="main">
    <div class="container justify-content-center mt-50 mb-50">
        <div class="row">
            <div class="col-md-10">
                <c:choose>
                    <c:when test="${not empty sessionScope.myCourses}">
                        <c:forEach var="i" begin="0" end="${sessionScope.myCourses.size()-1}">
                            <div class="card card-body mt-3">
                                <div class="media align-items-center align-items-lg-start text-center text-lg-left flex-column flex-lg-row">
                                    <div class="media-body">
                                        <h6 class="media-title font-weight-semibold">
                                            <a href=controller?command=catalog&course=${sessionScope.myCourses.get(i).id}
                                               data-abc="true">
                                                    ${sessionScope.myCourses.get(i).title}</a>
                                        </h6>

                                        <p class="mb-3">${sessionScope.myCourses.get(i).description}</p>
                                        <ul class="list-inline list-inline-dotted mb-0">
                                            <li class="list-inline-item">${sessionScope.myCourses.get(i).startDate}</li>
                                            <li class="list-inline-item">${sessionScope.myCourses.get(i).endDate}</li>
                                            <li class="list-inline-item">
                                                Status: ${sessionScope.myCourses.get(i).courseStatus}</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <h2 style="text-align: center; padding-top: 100px; padding-left: 100px">You don't have any courses yet</h2>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>
    </div>
</main>
<%@include file="footer.jsp"%>
</body>
</html>