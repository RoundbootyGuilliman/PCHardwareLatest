<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mine" uri="customTags" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${locale}"/>
<fmt:bundle basename="l10n">
    <fmt:message key="order" var="orderMessage"/>
    <fmt:message key="username" var="username"/>
    <fmt:message key="total" var="total"/>
    <fmt:message key="date" var="date"/>
    <fmt:message key="status" var="status"/>
</fmt:bundle>

<html>
<head>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<tag:header/>

<c:forEach var="order" items="${listOfOrders}">
    <c:if test="${order.orderId eq param.id}">
    <jsp:useBean id="order" class="epam.javalab22.pchardware.entity.Order" />
        <div class="sideBar" style="width: auto;">${orderMessage} #${order.orderId}<br>
            <table class="userTable">
                <tr><td>${username}:</td><td>${order.username}</td></tr>
                <tr><td>${total}:</td><td>${order.total}</td></tr>
                <tr><td>${status}:</td><td>${order.status}</td></tr>
                <tr><td>${date}:</td><td>${order.date}</td></tr>
            </table>
        </div>

        <c:forEach var="entry" items="${order.mapOfProducts}">
            <mine:simpleProduct product="${entry.key}"/>
        </c:forEach>
    </c:if>
</c:forEach>
<tag:footer/>
</body>
</html>