<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="js/bootstrap.min.js"></script>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/cover.css" rel="stylesheet">

    <style>
        body{
            background:
                    linear-gradient(
                            rgba(0, 0, 0, 0.7),
                            rgba(0, 0, 0, 0.7)
                    ),
                    url("img/bg.jpg");
            -webkit-background-size: cover;
            background-size: cover;
        }
    </style>

    <title>Exposition Calendar</title>
</head>

<body class="text-center">
<div class="cover-container d-flex h-100 p-3 mx-auto flex-column">
    <header class="masthead mb-auto">
        <div class="inner">
            <h3 class="masthead-brand"><fmt:message key="index.headerName"/></h3>
            <nav class="nav nav-masthead justify-content-center">
                <a class="nav-link" href="?locale=en">English</a>
                <a class="nav-link" href="?locale=ru">Русский</a>
                <a class="nav-link" href="login?command=loginForm"><fmt:message key="signIn.submit"/></a>
            </nav>
        </div>
    </header>
    <main role="main" class="inner cover">
        <h1 class="cover-heading"><fmt:message key="index.brandName"/></h1>
        <p class="lead"><fmt:message key="index.text"/> </p>
        <p class="lead">
            <a href="register?command=registerForm" class="btn btn-lg btn-secondary"><fmt:message key="index.register"/> </a>
        </p>
    </main>

    <footer class="mastfoot mt-auto">
        <div class="inner">
            <p><fmt:message key="index.footer"/> </p>
        </div>
    </footer>
</div>

</body>
</html>
