<%--
  Created by IntelliJ IDEA.
  User: CiteflaZe
  Date: 08-Nov-19
  Time: 2:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Something Went Wrong</title>
    <link href="https://fonts.googleapis.com/css?family=Josefin+Sans:400,700" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="css/errorPage.css"/>

    <style>
        <%@ include file ="css/errorPage.css"%>
    </style>


</head>
<body>
<div id="notfound">
    <div class="notfound">
        <div class="notfound-404">
            <h1>O<span>OP</span>S</h1>
        </div>
        <p><fmt:message key="error.message"/></p>
        <a href="<c:out value="/"/>"><fmt:message key="error.homepage"/> </a>
    </div>
</div>
</body>
</html>

