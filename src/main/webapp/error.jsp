<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mine" uri="customTags" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${locale}"/>
<fmt:bundle basename="l10n">
    <fmt:message key="error" var="error"/>
    <fmt:message key="goBack" var="goBack"/>
</fmt:bundle>

<html>
<head>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<tag:header/>
<h2>
    ${error}<br>
    ${message}<br>
    ${goBack}
</h2>
</body>
</html>
