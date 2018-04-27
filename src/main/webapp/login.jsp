<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mine" uri="customTags" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${locale}"/>
<fmt:bundle basename="l10n">
    <fmt:message key="signInPls" var="signInPls"/>
    <fmt:message key="username" var="usernameLabel"/>
    <fmt:message key="password" var="passwordLabel"/>
    <fmt:message key="signIn" var="signIn"/>
</fmt:bundle>

<html>
<head>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<tag:header/>
<h2>${signInPls}:</h2>
<br><br>

<div class="productBody">
    <div class="inputForm">
        <h4>${loginError}</h4>
        <form action="/Controller" method=post>
            <input type="hidden" name="service" value="login">
            <input type="hidden" name="referrer" value="${not empty param.referrer ? param.referrer : 'user.jsp'}">
            <table class="adminTable">
                <tr><td>${usernameLabel}:</td><td><input type="text" name="username" size="25" value="${param.username}" required></td></tr>
                <tr><td>${passwordLabel}:</td><td><input type="password" size="25" name="password" required></td></tr>
            </table>
            <input type="submit" value="${signIn}">
        </form>
    </div>
</div>
</body>
</html>
