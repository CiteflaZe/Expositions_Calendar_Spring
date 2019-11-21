<%--
  Created by IntelliJ IDEA.
  User: CiteflaZe
  Date: 14-Nov-19
  Time: 8:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>
<html>
<head>

    <title>User Pagination</title>
</head>
<body>
<nav aria-label="Navigation for countries">
    <ul class="pagination">
        <c:if test="${currentPage ne 1}">
            <li class="page-item"><a class="page-link"
                                     href="user?command=${command}&currentPage=${currentPage-1}&rowCount=${rowCount}"><fmt:message key="pagination.prev"/></a>
            </li>
        </c:if>

        <c:if test="${numberOfPages ne 1}">
            <c:forEach begin="1" end="${numberOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="page-item active"><a class="page-link">
                                ${i} <span class="sr-only">(current)</span></a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link"
                                                 href="user?command=${command}&currentPage=${i}&rowCount=${rowCount}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </c:if>

        <c:if test="${currentPage lt numberOfPages}">
            <li class="page-item"><a class="page-link"
                                     href="user?command=${command}&currentPage=${currentPage+1}&rowCount=${rowCount}"><fmt:message key="pagination.next"/></a>
            </li>
        </c:if>
    </ul>
</nav>
</body>
</html>
