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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <title>Course</title>
    <style>

        <%@include file="/WEB-INF/css/bootstrap.min.css" %>
        <%--        <%@include file="/WEB-INF/css/catalog.css" %>--%>
        .main .drop{
            border-color: yellow;
            background-color: yellow;
            color: black;
        }

    </style>

</head>
<body>
<%@include file="header.jsp"%>
<main class="main">
    <div class="jumbotron">
        <div class="container">
            <h1>${title}</h1>
            <p>${description}</p>
            <p>Start: ${startDate} End: ${endDate} Status: ${status}</p>
            <p>Teacher: ${teacher.firstName} ${teacher.lastName}</p>
            <c:choose>
                <c:when test="${sessionScope.account ne teacher}">
                    <c:choose>
                        <c:when test="${record eq true}">
                            <a class="drop btn btn-primary btn-lg" href="controller?command=drop_course&id=${id}"
                               role="button" type="submit">Drop</a>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-primary btn-lg" href="controller?command=join_course&id=${id}"
                               role="button" type="submit">Join</a>
                        </c:otherwise>
                    </c:choose>
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${sessionScope.account.role eq Role.ADMIN or sessionScope.account eq teacher}">
                    <a class="btn btn-primary btn-lg" href="#" data-toggle="modal" data-target="#staticBackdrop1"
                       role="button">Change</a>
                    <a class="btn btn-primary btn-lg" href="#" data-toggle="modal" data-target="#deleteCourse"
                       role="button">Delete</a>
                    <c:if test="${not empty studentsOnCourse}">
                        <div class="users">
                            <p>Users:</p>
                            <ul class="list-group">
                                <c:forEach var="i" begin="0" end="${studentsOnCourse.size()-1}">
                                    <li class="list-group-item">${studentsOnCourse.get(i).login}
                                        <button type="button" class="btn btn-primary pull-right">Review</button>
                                    </li>
                                </c:forEach>
                                    <%--                            <li class="list-group-item">Cras justo odio</li>--%>
                                    <%--                            <li class="list-group-item">Dapibus ac facilisis in</li>--%>
                                    <%--                            <li class="list-group-item">Morbi leo risus</li>--%>
                                    <%--                            <li class="list-group-item">Porta ac consectetur ac</li>--%>
                                    <%--                            <li class="list-group-item">Vestibulum at eros</li>--%>
                            </ul>
                        </div>
                    </c:if>
                </c:when>
            </c:choose>

            <input type="hidden" name="id" value="${id}">

            <form action="${pageContext.request.contextPath}/controller?command=change_course" method="post">
                <div class="modal fade" id="staticBackdrop1" data-backdrop="static" data-keyboard="false"
                     tabindex="-1" aria-labelledby="staticBackdropLabel1" aria-hidden="true">

                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="staticBackdropLabel1">Change course</h5>
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>

                            <div class="modal-body">
                                <div class="form-group">
                                    <label for="title1">Title</label>
                                    <input type="text" name="title" class="form-control"
                                           id="title1"
                                           aria-label="Title"
                                           aria-describedby="basic-addon1" value=${title} required>
                                </div>
                                <input type="hidden" name="id" value="${id}">
                                <div class="form-group">
                                    <label for="startDate1">Beginning</label>
                                    <input type="date" name="startDate" class="form-control"
                                           id="startDate1" placeholder="Input start date"
                                           value=${startDate}
                                                   aria-label="startDate"
                                           aria-describedby="basic-addon1" required>
                                </div>

                                <div class="form-group">
                                    <label for="endDate1">Ending</label>
                                    <input type="date" name="endDate" class="form-control"
                                           id="endDate1" placeholder="Input end date"
                                           value=${endDate} aria-label="endDate"
                                           aria-describedby="basic-addon1" required>
                                </div>

                                <div class="form-group">
                                    <label for="teacher1">Teacher</label>
                                    <select class="form-control" name="teacher" id="teacher1">
                                        <%--                                            <option selected>Select teacher</option>--%>
                                        <c:forEach var="i" begin="0" end="${sessionScope.teachers.size()-1}">
                                            <c:choose>
                                                <c:when test="${sessionScope.teachers.get(i) eq teacher}">
                                                    <option selected value="${sessionScope.teachers.get(i).id}">${teacher.firstName} ${teacher.lastName}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${sessionScope.teachers.get(i).id}">${sessionScope.teachers.get(i).firstName} ${sessionScope.teachers.get(i).lastName}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </div>


                                <div class="form-group">
                                    <label for="description1">Description</label>
                                    <textarea class="form-control" name="description" id="description1"
                                              rows="3">${description}</textarea>
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

            <form action="${pageContext.request.contextPath}/controller?command=delete_course" method="post">
                <div class="modal fade" tabindex="-1" id="deleteCourse">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Delete course</h5>
                                <input type="hidden" name="id" value="${id}">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <p>Are you sure you want to delete this course?</p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary">Delete course</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>

        </div>
    </div>

    <%--<div class="container">--%>
    <%--    <!-- Example row of columns -->--%>
    <%--    <div class="row">--%>
    <%--        <div class="col-md-4">--%>
    <%--            <h2>Heading</h2>--%>
    <%--            <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>--%>
    <%--            <p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>--%>
    <%--        </div>--%>
    <%--        <div class="col-md-4">--%>
    <%--            <h2>Heading</h2>--%>
    <%--            <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>--%>
    <%--            <p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>--%>
    <%--        </div>--%>
    <%--        <div class="col-md-4">--%>
    <%--            <h2>Heading</h2>--%>
    <%--            <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.</p>--%>
    <%--            <p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>--%>
    <%--        </div>--%>
    <%--    </div>--%>

    <hr>
</main>



</body>
</html>