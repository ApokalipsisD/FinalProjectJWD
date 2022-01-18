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

        /*body {*/
        /*    margin: 20px;*/
        /*}*/

        /*.input-container {*/
        /*    margin-bottom: 15px;*/
        /*    max-width: 300px;*/
        /*}*/

        /*.input-container[data-error] .form-control {*/
        /*    border-color: #c92432;*/
        /*    color: #c92432;*/
        /*    background-color: #ffffff;*/
        /*}*/

        /*.input-container[data-error]::after {*/
        /*    content: attr(data-error);*/
        /*    font-size: 0.85em;*/
        /*    color: #c92432;*/
        /*    display: block;*/
        /*    margin: 10px 0;*/
        /*}*/

        /*.form-control {*/
        /*    display: block;*/
        /*    width: 100%;*/
        /*    padding: 12px;*/
        /*    border: 1px solid #dddd;*/
        /*    border-radius: 4px;*/
        /*}*/

    </style>

</head>
<body class="text-center">
<%@include file="header.jsp" %>
<main class="main">
<%--    <c:choose>--%>
<%--        <c:when test="${not empty requestScope.error}">--%>
<%--            <p>${requestScope.error}</p>--%>
<%--            <a href="${pageContext.request.contextPath}/controller?command=show_main_page">Try again</a>--%>
<%--        </c:when>--%>
<%--        <c:otherwise>--%>
            <div class="container">
                <div class="card card-container">
                    <img id="profile-img" class="profile-img-card" src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"/>
                    <form action="${pageContext.request.contextPath}/controller?command=sign_up_command" method="post"
                          class="form-signin">
                        <h4 style="margin-bottom: 20px;">Sign Up</h4>
                        <p style="color: red;">${error}</p>
                        <div class="input-container">

                            <input name="login" id="login" type="text" class="form-control" placeholder="Login"
                                   aria-label=Login" aria-describedby="basic-addon1" required
                                   pattern="^[\w.-]{3,20}[0-9a-zA-Z]$"
                                   title="Login must be greater than 3 and less than 20 and must not contain inaccessible characters"
                                <%--                                   oninput="setCustomValidity('Username must be greater than 3 and less than 20 and must not contain inaccessible characters')"--%>
                                <%--                                   oninvalid="this.setCustomValidity('Please enter username')"--%>
                            >

                        </div>


                        <div class="input-container">
                            <input type="password" name="password" id="inputPassword" class="form-control"
                                   placeholder="Password"
                                <%--                                   oninvalid="this.setCustomValidity('Please enter password')"--%>
                                   required pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,}"
                                   title="Password must contain at least one number, one lowercase and one uppercase letter, min password length 8">
                        </div>


                            <%--                <div id="passwordHelpBlock" class="form-text text-muted">--%>
                            <%--                    Ваш пароль должен состоять из 8-20 символов, содержать минимум одну прописную букву и цифры и не должен содержать пробелов.--%>
                            <%--                </div>--%>
                        <div class="input-container">
                            <input type="password" name="repeat_password" id="inputRepeatPassword" class="form-control"
                                   placeholder="Repeat password" required
                                   pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,}" title="Repeat password"
                            >
                                <%--                                   oninvalid="this.setCustomValidity('Please repeat password')"--%>
                                <%--                                   oninput="setCustomValidity('no')"/>--%>
                        </div>

                            <%--                        <div id="remember" class="checkbox">--%>
                            <%--                            <label>--%>
                            <%--                                <input type="checkbox" value="remember-me"> Remember me--%>
                            <%--                            </label>--%>
                            <%--                        </div>--%>
                        <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Sign Up</button>
                    </form>
                    <a href="${pageContext.request.contextPath}/controller?command=show_login" class="forgot-password">
                        Already have account?
                    </a>
                </div>
            </div>

<%--        </c:otherwise>--%>
<%--    </c:choose>--%>
<%--    <c:choose>--%>
<%--        <c:when test="${not empty error}">--%>
<%--            <p style="color: red;">${error}</p>--%>
<%--        </c:when>--%>

<%--        <c:when test="${not empty message}">--%>
<%--            <p style="color: red;">${message}</p>--%>
<%--        </c:when>--%>
<%--    </c:choose>--%>
</main>
<%--<script>--%>
<%--    document.querySelectorAll('.input-container[data-error] .form-control').forEach(inpEl=>{--%>
<%--        inpEl.addEventListener('input', ()=>inpEl.parentElement.removeAttribute('data-error'));--%>
<%--    })--%>
<%--</script>--%>
<%@include file="footer.jsp" %>
</body>
</html>