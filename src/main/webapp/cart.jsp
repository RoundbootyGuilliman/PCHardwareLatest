<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mine" uri="customTags" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${locale}"/>
<fmt:bundle basename="l10n">
    <fmt:message key="cart" var="cart"/>
    <fmt:message key="total" var="totalMessage"/>
    <fmt:message key="checkout" var="checkout"/>
    <fmt:message key="notLogged" var="notLogged"/>
    <fmt:message key="signIn" var="signIn"/>
</fmt:bundle>

<html>
<head>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<tag:header/>
<h2>${cart}</h2>
<div class="sideBar">
    <c:set var="total" value="${0}"/>
    <c:forEach var="entry" items="${shoppingCart}">
        <c:set var="total" value="${total + entry.key.price * entry.value}" />
    </c:forEach>
    ${totalMessage}: <mine:currency figure="${total}"/><p/>
    <c:choose>
        <c:when test="${not empty pageContext.request.remoteUser}">
            <c:if test="${not empty shoppingCart}">
                <form method="post" action="/Controller">
                    <input type="hidden" name="service" value="checkout"/>
                    <input type="hidden" name="referrer" value="user.jsp"/>
                    <button type="submit" value="fuck">${checkout}</button>
                </form>
            </c:if>
        </c:when>
        <c:otherwise>
            <form action="/login.jsp" method="post">
                <input type="hidden" name="referrer" value="/cart.jsp"/>
                ${notLogged}<p/><input type="submit" value="${signIn}">
            </form>
        </c:otherwise>
    </c:choose>
</div>
<div class="productArea">
<c:forEach var="entry" items="${shoppingCart}">
    <mine:complexProduct product="${entry.key}">
        <mine:price product="${entry.key}"/>
    </mine:complexProduct>
</c:forEach>
</div>
<tag:footer/>
</body>
</html>