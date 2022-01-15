<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jwdt" uri="jwdTags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="ru"/>
<fmt:setBundle basename="locale" var="loc"/>

<%@ page import="com.epam.jwd.dao.entity.Role" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>

    <title>Courses</title>
    <style>

        <%@include file="/WEB-INF/css/bootstrap.min.css" %>
        <%@include file="/WEB-INF/css/catalog.css" %>

        label {
            font-size: 120%;
        }

    </style>
</head>
<body>
<%@include file="header.jsp"%>
<main class="main">
    <c:choose>
        <c:when test="${sessionScope.account.role eq Role.ADMIN}">
            <form action="${pageContext.request.contextPath}/controller?command=create_course" method="post">
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#staticBackdrop">
                    Create course
                </button>

                <div class="modal fade" id="staticBackdrop" data-backdrop="static" data-keyboard="false" tabindex="-1"
                     aria-labelledby="staticBackdropLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">

                            <div class="modal-header">
                                <h5 class="modal-title" id="staticBackdropLabel">Create course</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>

                            <div class="modal-body">
                                <div class="form-group">
                                    <label for="title">Title</label>
                                    <input type="text" name="title" class="form-control" id="title"
                                           placeholder="Input title"
                                           aria-label="Title" aria-describedby="basic-addon1" required>
                                </div>

                                <div class="form-group">
                                    <label for="startDate">Beginning</label>
                                    <input type="date" name="startDate" class="form-control" id="startDate"
                                           placeholder="Input start date"
                                           aria-label="startDate" aria-describedby="basic-addon1" required>
                                </div>

                                <div class="form-group">
                                    <label for="endDate">Ending</label>
                                    <input type="date" name="endDate" class="form-control" id="endDate"
                                           placeholder="Input end date"
                                           aria-label="endDate" aria-describedby="basic-addon1" required>
                                </div>

                                <div class="form-group">
                                    <label for="teacher">Teacher</label>
                                    <select class="form-control" name="teacher" id="teacher" required>
                                        <option selected disabled>Select teacher</option>
                                        <c:forEach var="i" begin="0" end="${sessionScope.teachers.size()-1}">
                                            <option value="${sessionScope.teachers.get(i).id}">${sessionScope.teachers.get(i).firstName} ${sessionScope.teachers.get(i).lastName}</option>
                                        </c:forEach>

                                    </select>
                                </div>

                                <div class="form-group">
                                    <label for="description">Description</label>
                                    <textarea class="form-control" name="description" id="description" rows="3"></textarea>
                                </div>

                            </div>


                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary">Save</button>
                            </div>

                        </div>
                    </div>
                </div>
            </form>
        </c:when>
    </c:choose>

    <div class="container justify-content-center mt-50 mb-50">
        <div class="row">
            <div class="col-md-10">
                <c:forEach var="i" begin="0" end="${sessionScope.catalog.size()-1}">
                    <div class="card card-body mt-3">
                        <div class="media align-items-center align-items-lg-start text-center text-lg-left flex-column flex-lg-row">
                            <div class="media-body">
                                <h6 class="media-title font-weight-semibold">
                                    <a href=controller?command=catalog&course=${sessionScope.catalog.get(i).id}
                                       data-abc="true">
                                            ${sessionScope.catalog.get(i).title}</a>
                                </h6>

                                <p class="mb-3">${sessionScope.catalog.get(i).description}</p>
                                <ul class="list-inline list-inline-dotted mb-0">
                                    <li class="list-inline-item">${sessionScope.catalog.get(i).startDate}</li>
                                    <li class="list-inline-item">${sessionScope.catalog.get(i).endDate}</li>
                                    <li class="list-inline-item">
                                        Status: ${sessionScope.catalog.get(i).courseStatus}</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</main>


</body>
</html>