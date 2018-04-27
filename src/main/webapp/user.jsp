<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mine" uri="customTags" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${locale}"/>
<fmt:bundle basename="l10n">
    <fmt:message key="personal" var="personal"/>
    <fmt:message key="username" var="username"/>
    <fmt:message key="email" var="email"/>
    <fmt:message key="name" var="name"/>
    <fmt:message key="lastName" var="lastName"/>
    <fmt:message key="phoneNumber" var="phoneNumber"/>
    <fmt:message key="address" var="address"/>
    <fmt:message key="edit" var="edit"/>
    <fmt:message key="save" var="save"/>
    <fmt:message key="order" var="orderMessage"/>
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
<h2>${personal}</h2>

<div class="userInfo">
    <jsp:useBean id="user" class="epam.javalab22.pchardware.entity.User" scope="request" />
    <c:choose>
    <c:when test="${param.edit eq 'true'}" >
        <form action="/Controller" method="post">
        <table class="userTable">
            <tr><td>${username}:</td><td><input type="text" name="username" value="${user.username}"></td></tr>
            <tr><td>${email}:</td><td><input type="text" name="email" value="${user.email}"></td></tr>
            <tr><td>${name}:</td><td><input type="text" name="name" value="${user.name}"></td></tr>
            <tr><td>${lastName}:</td><td><input type="text" name="lastName" value="${user.lastName}"></td></tr>
            <tr><td>${phoneNumber}:</td><td><input type="text" name="phoneNumber" value="${user.phoneNumber}"></td></tr>
            <tr><td>${address}:</td><td><input type="text" name="address" value="${user.address}"></td></tr>
        </table>
            <input type="hidden" name="service" value="edit"/>
            <input type="hidden" name="referrer" value="user.jsp"/>
            <input type="submit" value="${save}"/>
        </form>
    </c:when>
    <c:otherwise>
        <table class="userTable">
            <tr><td>${username}:</td><td>${user.username}</td></tr>
            <tr><td>${email}:</td><td>${user.email}</td></tr>
            <tr><td>${name}:</td><td>${user.name}</td></tr>
            <tr><td>${lastName}:</td><td>${user.lastName}</td></tr>
            <tr><td>${phoneNumber}:</td><td>${user.phoneNumber}</td></tr>
            <tr><td>${address}:</td><td>${user.address}</td></tr>
        </table>
        <form action="/user.jsp">
            <input type="hidden" name="edit" value="true"/>
            <input type="submit" value="${edit}"/>
        </form>
    </c:otherwise>
    </c:choose>
</div>

<div class="productArea">
    <c:forEach var="order" items="${listOfOrders}">
        <jsp:useBean id="order" class="epam.javalab22.pchardware.entity.Order" />
        <div class="order">
            <table class="adminTable">
                <tr><td><b><a href="order.jsp?id=${order.orderId}">${orderMessage} #${order.orderId}</a></b></td></tr>
                <tr><td>${total}:</td><td>${order.total}</td></tr>
                <tr><td>${status}:</td><td>${order.status}</td></tr>
                <tr><td>${date}:</td><td>${order.date}</td></tr>
            </table>
        </div>
    </c:forEach>
</div>
<tag:footer/>
</body>
</html>