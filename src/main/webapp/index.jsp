<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="mine" uri="customTags"%>

<html>
<head>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>
<tag:header/>

<c:forEach var="product" items="${listOfProducts}">
    <mine:simpleProduct product="${product}"/>
</c:forEach>
<tag:footer/>
</body></html>