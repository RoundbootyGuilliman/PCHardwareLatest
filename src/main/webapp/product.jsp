<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mine" uri="customTags" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${locale}"/>
<fmt:bundle basename="l10n">
    <fmt:message key="noReviews" var="noReviews"/>
    <fmt:message key="reviews" var="reviews"/>
    <fmt:message key="notLoggedReviews" var="notLoggedReviews"/>
    <fmt:message key="signIn" var="signIn"/>
    <fmt:message key="yourReview" var="yourReview"/>
</fmt:bundle>

<html>
<head>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<tag:header/>
<jsp:useBean id="product" class="epam.javalab22.pchardware.entity.Product" scope="request"/>
<div class="productBody">
<h2 align="center">
    ${product.name}
</h2>
<div class="productBig">
    <div class="productTop">
        <div class="bigImg">
            <img class="bigImage" src="resources/img/${product.img}"/>
        </div>
        <div class="centralTable">
            <table class="productTable">
                <c:forEach items="${product.mapOfCharacteristics}" var="entry">
                    <tr>
                        <td>${entry.key}</td>
                        <td>${entry.value}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="productPrice">
            <mine:price product="${product}"/>
        </div>
    </div>
    <div class="reviewArea">
    <c:choose>
        <c:when test="${empty requestScope.reviews}">
            <h3>${noReviews}</h3>
        </c:when>
        <c:otherwise>
            <h3>${reviews}:</h3>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${not empty pageContext.request.remoteUser}">
            <form action="/Controller" method="post">
                <input type="hidden" name="service" value="review"/>
                <input type="hidden" name="id" value="${product.id}"/>
                <input type="hidden" name="referrer" value="/Controller?service=product&id=${product.id}"/>
                <textarea name="review" rows="6" cols="100" placeholder="${yourReview}"></textarea><br>
                <input type="submit"/>
            </form>
        </c:when>
        <c:otherwise>
            <form action="/login.jsp" method="post">
                <input type="hidden" name="referrer" value="/Controller?service=product&id=${product.id}"/>
                ${notLoggedReviews}<p/><input type="submit" value="${signIn}">
            </form>
        </c:otherwise>
    </c:choose>
    <c:forEach var="review" items="${requestScope.reviews}">
        <div class="review">
            <div class="reviewName"><b>${review.username}:</b></div>
            <div class="reviewDate">${review.date}</div>
            <br><br>
            ${review.review}
        </div>
    </c:forEach>
    </div>
</div>
</div>
<tag:footer/>
</body>
</html>
