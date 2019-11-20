<%--
  Created by IntelliJ IDEA.
  User: CiteflaZe
  Date: 12-Nov-19
  Time: 11:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <title>User side bar</title>

    <link href="css/sidebar.css" rel="stylesheet"/>
    <link href="css/bootstrap.min.css" rel="stylesheet"/>
    <script src="js/bootstrap.min.js"></script>

    <style>
        <%@ include file ="css/sidebar.css"%>
    </style>
</head>
<body>
<nav id="sidebar">
    <ul class="list-unstyled components">
        <p><fmt:message key="sidebar.menu"/> </p>
        <li>
            <a href="?command=showExpositions&currentPage=1&rowCount=15"><fmt:message key="user.sidebar.showExpos"/></a>
        </li>
        <li>
            <a href="?command=showTickets"><fmt:message key="user.sidebar.showTickets"/></a>
        </li>
    </ul>
</nav>
</body>
</html>
