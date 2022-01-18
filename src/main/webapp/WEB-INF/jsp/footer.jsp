<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jwdt" uri="jwdTags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="ru"/>
<fmt:setBundle basename="locale" var="loc"/>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<%--    <title>Main Page</title>--%>
<style>
    /** {*/
    /*    margin: 0;*/
    /*    padding: 0;*/
    /*}*/
    html,
    body {
        height: 100%;
    }
    main {
        min-height: 100%;
        padding-bottom:100px;
    }

    footer {
        min-height: 60px;
        height: 60px;
        margin-top: -60px;;
    }
</style>

</head>
<body>
<footer class="footer bg-dark text-center text-white">

<%--    <div class="container p-4 pb-0">--%>
<%--        <!-- Section: Social media -->--%>
<%--        <section class="mb-4">--%>
<%--            <!-- Facebook -->--%>
<%--            <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button"--%>
<%--            ><i class="fab fa-facebook-f"></i--%>
<%--            ></a>--%>

<%--            <!-- Twitter -->--%>
<%--            <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button"--%>
<%--            ><i class="fab fa-twitter"></i--%>
<%--            ></a>--%>

<%--            <!-- Google -->--%>
<%--            <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button"--%>
<%--            ><i class="fab fa-google"></i--%>
<%--            ></a>--%>

<%--            <!-- Instagram -->--%>
<%--            <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button"--%>
<%--            ><i class="fab fa-instagram"></i--%>
<%--            ></a>--%>

<%--            <!-- Linkedin -->--%>
<%--            <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button"--%>
<%--            ><i class="fab fa-linkedin-in"></i--%>
<%--            ></a>--%>

<%--            <!-- Github -->--%>
<%--            <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button"--%>
<%--            ><i class="fab fa-github"></i--%>
<%--            ></a>--%>
<%--        </section>--%>
<%--        <!-- Section: Social media -->--%>
<%--    </div>--%>



    <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0.2);">
        © 2022 Copyright:
        <a class="text-white" href="https://www.youtube.com/watch?v=G1IbRujko-A&t=164s&ab_channel=10Hours">Site name</a>
    </div>

</footer>
</body>
</html>