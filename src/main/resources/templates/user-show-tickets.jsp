<%--
  Created by IntelliJ IDEA.
  User: CiteflaZe
  Date: 14-Nov-19
  Time: 8:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"
          integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
    <title>Tickets</title>
</head>
<body>
<c:import url="header.jsp"/>
<div class="wrapper">
    <c:import url="user-sidebar.jsp"/>
    <div id="content">
        <table class="table table-striped table-responsive-md btn-table">
            <thead class="thead-dark">
            <tr>
                <th scope="col"><fmt:message key="user.showTickets.expoTitle"/></th>
                <th scope="col"><fmt:message key="user.showTickets.hall"/></th>
                <th scope="col"><fmt:message key="user.showTickets.ticketAmount"/></th>
                <th scope="col"><fmt:message key="user.showTickets.date"/></th>
                <th scope="col"><fmt:message key="user.showTickets.download"/></th>
            </tr>
            </thead>

            <tbody>
            <c:forEach begin="0" end="${tickets.size()-1}" var="i">
                <tr>
                    <td>${tickets.get(i).getExposition().getTitle()}</td>
                    <td>${tickets.get(i).getHall().getName()}</td>
                    <td>${ticketAmount.get(i)}</td>
                    <td>${tickets.get(i).getValidDate()}</td>
                    <td>
                        <form action="user" method="get">
                            <input type="hidden" name="command" value="download"/>
                            <input type="hidden" name="paymentId" value="${tickets.get(i).getPayment().getId()}"/>
                            <button type="submit" class="btn btn-success"><i class="fa fa-download" aria-hidden="true"></i></button>
                        </form>
                    </td>

                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>
</div>
</body>
</html>
