<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="userName" var="userName"/>

<html>
<head>
    <title>Registration</title>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <style>
        <%--        <%@include file="/WEB-INF/css/bootstrap.bundle.min.js"%>--%>
        <%@include file="/WEB-INF/css/bootstrap.min.css" %>
        <%@include file="/WEB-INF/css/signUp.css" %>
        <%@include file="/WEB-INF/js/signUp.js" %>
    </style>

</head>
<body class="text-center">
<%@include file="header.jsp"%>
<main class="main">
    <c:choose>
        <c:when test="${not empty requestScope.error}">
            <p>${requestScope.error}</p>
            <a href="${pageContext.request.contextPath}/controller?command=show_main_page">Try again</a>
        </c:when>
        <c:otherwise>
            <%--<form action="${pageContext.request.contextPath}/controller?command=sign_up_command" method="post">--%>
            <div class="container">
                <div class="card card-container">
                    <!-- <img class="profile-img-card" src="//lh3.googleusercontent.com/-6V8xOA6M7BA/AAAAAAAAAAI/AAAAAAAAAAA/rzlHcD0KYwo/photo.jpg?sz=120" alt="" /> -->
                    <img id="profile-img" class="profile-img-card" src="//ssl.gstatic.com/accounts/ui/avatar_2x.png" />
                    <p id="profile-name" class="profile-name-card"></p>
                    <form action="${pageContext.request.contextPath}/controller?command=sign_up_command" method="post" class="form-signin">
                        <span id="reauth-email" class="reauth-email"></span>

                        <input name="login" id="login" type="text" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="basic-addon1" required><span id="errmsg"></span>
                            <%--                <input  name="quantity" id="quantity" /> --%>
                            <%--                <label for="validationCustomUsername" class="">Имя пользователя</label>--%>
                            <%--                <div class="input-group has-validation">--%>
                            <%--                    <span class="" id="inputGroupPrepend">@</span>--%>
                            <%--                    <input type="text" class="" id="validationCustomUsername" aria-describedby="inputGroupPrepend" required>--%>
                            <%--                    <div class="invalid-feedback">--%>
                            <%--                        Пожалуйста, выберите имя пользователя.--%>
                            <%--                    </div>--%>
                            <%--                </div>--%>
                        <div>${errors}</div>

                        <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
                            <%--                <div id="passwordHelpBlock" class="form-text text-muted">--%>
                            <%--                    Ваш пароль должен состоять из 8-20 символов, содержать минимум одну прописную букву и цифры и не должен содержать пробелов.--%>
                            <%--                </div>--%>
                        <input type="password" name="repeat_password" id="inputRepeatPassword" class="form-control" placeholder="Repeat password" required>
                        <div id="remember" class="checkbox">
                            <label>
                                <input type="checkbox" value="remember-me"> Remember me
                            </label>
                        </div>
                        <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Sign in</button>
                    </form><!-- /form -->
                    <a href="#" class="forgot-password">
                        Forgot the password?
                    </a>
                </div><!-- /card-container -->
            </div><!-- /container -->

            <%--			<table style="with: 50%">--%>
            <%--                <tr>--%>
            <%--                    <td>Login</td>--%>
            <%--                    <td><input type="text" name="login" /></td>--%>
            <%--                </tr>--%>
            <%--                <tr>--%>
            <%--                    <td>Password</td>--%>
            <%--                    <td><input type="text" name="password" /></td>--%>
            <%--                </tr>--%>
            <%--                <tr>--%>
            <%--                    <td>Repeat password</td>--%>
            <%--                    <td><input type="text" name="repeat_password" /></td>--%>
            <%--                </tr>--%>
            <%--            </table>--%>
            <%--            <input type="submit" value="Submit" /></form>--%>
        </c:otherwise>
    </c:choose>
</main>
<%@include file="footer.jsp"%>
</body>
</html>