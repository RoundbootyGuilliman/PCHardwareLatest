<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mine" uri="customTags" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${locale}"/>
<fmt:bundle basename="l10n">
    <fmt:message key="price" var="price"/>
    <fmt:message key="max" var="max"/>
    <fmt:message key="min" var="min"/>
    <fmt:message key="vendor" var="vendorMessage"/>
</fmt:bundle>

<html>
<head>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<tag:header/>
<p/>
<div class="sideBar">
    <form action="/Controller" method="post">
        <input type="hidden" name="service" value="search"/>
        <input type="hidden" name="searchParams"/>
        <input type="hidden" name="type" value="${param.type}"/>
        <fieldset>
            <legend>${price}</legend>
            ${max}: <input type="number" name="maxPrice" min="10000" max="400000" step="10000" value="${param.maxPrice}"/><br>
            ${min}: <input type="number" name="minPrice" min="0" max="390000" step="10000" value="${param.minPrice}"/><br>
        </fieldset>
        <fieldset>
            <legend>${vendorMessage}</legend>
            <c:forEach var="vendor" items="${setOfVendors}">
                ${vendor} <input type="checkbox" name="${vendor}" ${param[vendor] eq 'on' ? 'checked' : '' }/><br>
            </c:forEach>
        </fieldset>
        <input type="submit"/>
    </form>
</div>
<div class="productArea">
<c:forEach var="product" items="${listOfProducts}">
    <mine:complexProduct product="${product}">
        <mine:price product="${product}"/>
    </mine:complexProduct>
</c:forEach>
</div>
<tag:footer/>
</body>
</html>

