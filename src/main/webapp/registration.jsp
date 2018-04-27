<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mine" uri="customTags" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${locale}"/>
<fmt:bundle basename="l10n">
    <fmt:message key="registration" var="registration"/>
    <fmt:message key="username" var="username"/>
    <fmt:message key="email" var="email"/>
    <fmt:message key="password" var="password"/>
    <fmt:message key="name" var="name"/>
    <fmt:message key="lastName" var="lastName"/>
    <fmt:message key="phoneNumber" var="phoneNumber"/>
    <fmt:message key="address" var="address"/>
    <fmt:message key="signUp" var="signUp"/>
</fmt:bundle>

<html>
<head>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<tag:header/>
<h2>${registration}:</h2>
<br><br>
<div class="productBody">
    <div class="inputForm">
<form action="/Controller" method=post>
    <input type="hidden" name="service" value="register">
    <input type="hidden" name="referrer" value="user.jsp">

    <table class="adminTable">
        <tr><td>${username}:</td><td><input type="text" pattern="[a-zA-Z0-9.-_]{3,}" name="username" size="25"
                       title="Username should be 3 or more characters long and contain only latin characters and numbers."
                       required></td></tr>
        <tr><td>${email}:</td><td><input type="text" pattern="[a-zA-Z0-9.-_]{1,}@[a-zA-Z.-]{2,}[.]{1}[a-zA-Z]{2,}" name="email" size="25"
                       title="Please enter correct e-mail address." required></td></tr>
        <tr><td>${password}:</td><td><input type="password" pattern="[a-zA-Z0-9.-_]{5,}" name="password" size="25"
                       title="Username should be 5 or more characters long and contain only latin characters and numbers."
                       required></td></tr>
        <tr><td>${name}:</td><td><input title="Name Pls" type="text" name="name" size="25"></td></tr>
        <tr><td>${lastName}:</td><td><input type="text" name="lastName" size="25"></td></tr>
        <tr><td>${phoneNumber}:</td><td><input type="text" name="phoneNumber" size="25"></td></tr>
        <tr><td>${address}:</td><td><input type="text" name="address" size="25"></td></tr>
    </table>
    <input type="submit" value="${signUp}">
</form>
    </div>
</div>
</body>
</html>
