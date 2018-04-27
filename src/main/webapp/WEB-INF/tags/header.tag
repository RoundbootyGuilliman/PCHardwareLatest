<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mine" uri="customTags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:bundle basename="l10n">
    <fmt:message key="administration" var="administration"/>
    <fmt:message key="mainPage" var="mainPage"/>
    <fmt:message key="components" var="components"/>
    <fmt:message key="cpu" var="cpu"/>
    <fmt:message key="gpu" var="gpu"/>
    <fmt:message key="mainboard" var="mainboard"/>
    <fmt:message key="ram" var="ram"/>
    <fmt:message key="power" var="power"/>
    <fmt:message key="logout" var="logout"/>
    <fmt:message key="signIn" var="signIn"/>
    <fmt:message key="signUp" var="signUp"/>
    <fmt:message key="cart" var="cart"/>
</fmt:bundle>

<header><h1>Glorious PC Hardware</h1></header>
<nav>
    <div class="container">
        <a href="/index.jsp">${mainPage}</a>

        <div class="dropdown">
            <button class="dropbtn">${components}</button>
            <div class="dropdown-content">
                <a href="/Controller?service=search&type=CPU">${cpu}</a>
                <a href="/Controller?service=search&type=GPU">${gpu}</a>
                <a href="/Controller?service=search&type=Motherboard">${mainboard}</a>
                <a href="/Controller?service=search&type=Memory">${ram}</a>
                <a href="/Controller?service=search&type=Power">${power}</a>
            </div>
        </div>
        <c:choose>
            <c:when test="${sessionScope.locale.toString() eq 'ru_KZ'}">
                <a class="locale" href="/Controller?service=changeLocale&locale=en"><img src="resources/img/SH.png"/></a>
            </c:when>
            <c:otherwise>
                <a class="locale" href="/Controller?service=changeLocale&locale=ru"><img src="resources/img/RU.png"/></a>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${not empty pageContext.request.remoteUser}">
                <a class="rightMenu" href="/Controller?service=logout">${logout}</a>
                <a class="rightMenu" href="/user.jsp">${pageContext.request.remoteUser}</a>
                <a class="rightMenu" href="/cart.jsp">${cart}</a>
            </c:when>
            <c:otherwise>
                <a class="rightMenu" href="/login.jsp">${signIn}</a>
                <a class="rightMenu" href="/registration.jsp">${signUp}</a>
                <a class="rightMenu" href="/cart.jsp">${cart}</a>
            </c:otherwise>
        </c:choose>
        <c:if test="${pageContext.request.isUserInRole('Admin')}">
            <a class="rightMenu" href="/Controller?service=admin&admin=Orders">${administration}</a>
        </c:if>
    </div>
</nav>