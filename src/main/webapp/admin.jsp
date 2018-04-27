<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mine" uri="customTags" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${locale}"/>
<fmt:bundle basename="l10n">
    <fmt:message key="administration" var="administration"/>
    <fmt:message key="order" var="orderMessage"/>
    <fmt:message key="username" var="username"/>
    <fmt:message key="total" var="total"/>
    <fmt:message key="date" var="date"/>
    <fmt:message key="changeStatus" var="changeStatus"/>
    <fmt:message key="addProduct" var="addProduct"/>
    <fmt:message key="deleteProduct" var="deleteProduct"/>
    <fmt:message key="deleteUser" var="deleteUser"/>
    <fmt:message key="email" var="email"/>
    <fmt:message key="name" var="name"/>
    <fmt:message key="lastName" var="lastName"/>
    <fmt:message key="phoneNumber" var="phoneNumber"/>
    <fmt:message key="address" var="address"/>
    <fmt:message key="orders" var="orders"/>
    <fmt:message key="products" var="products"/>
    <fmt:message key="users" var="users"/>
</fmt:bundle>

<html>
<head>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<tag:header/>
<h2>${administration}</h2>

<div class="sideBar">
    <form action="/Controller" method="post">
        <input type="hidden" name="service" value="admin">
        <button class="adminButton" type="submit" name="admin" value="Orders" ${param.admin eq 'Orders' ? 'disabled' : '' }>${orders}</button>
        <button class="adminButton" type="submit" name="admin" value="Products" ${param.admin eq 'Products' ? 'disabled' : '' }>${products}</button>
        <button class="adminButton" type="submit" name="admin" value="Users" ${param.admin eq 'Users' ? 'disabled' : '' }>${users}</button>
    </form>
</div>

<div class="productArea">
    <c:choose>
        <c:when test="${param.admin eq 'Orders'}">
            <c:forEach var="order" items="${listOfOrders}">
                <jsp:useBean id="order" class="epam.javalab22.pchardware.entity.Order" />
                <div class="adminList">
                    <div class="adminLeft">
                        <table class="adminTable">
                            <tr><td><b><a href="order.jsp?id=${order.orderId}">${orderMessage} #${order.orderId}</a></b></td></tr>
                            <tr><td>${username}:</td><td>${order.username}</td></tr>
                            <tr><td>${total}:</td><td>${order.total}</td></tr>
                            <tr><td>${date}:</td><td>${order.date}</td></tr>
                        </table>
                    </div>
                    <div class="adminRight">
                        <form action="/Controller" method="post">
                            <input type="hidden" name="service" value="orderStatus">
                            <input type="hidden" name="referrer" value="Controller?service=admin&admin=Orders">
                            <input type="hidden" name="id" value="${order.orderId}">
                            <select name="status">
                                <option value="" selected disabled hidden>${order.status}</option>
                                <option value="Pending">Pending</option>
                                <option value="Confirmed">Confirmed</option>
                                <option value="Shipped">Shipped</option>
                                <option value="Completed">Completed</option>
                            </select><br>
                            <input type="submit" value="${changeStatus}"/>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:when test="${param.admin eq 'Products'}">
            <div class="adminList" style="text-align: center;">
                <form action="/upload.jsp">
                    <input type="submit" value="${addProduct}"/>
                </form>
            </div>
            <c:forEach var="product" items="${listOfProducts}">
                <jsp:useBean id="product" class="epam.javalab22.pchardware.entity.Product" />
        <div class="adminList">
            <div class="adminLeft">
                <b><a href="Controller?service=product&id=${product.id}">${product.name}</a></b>
                <table class="adminTable">
                    <tr><td>ID#${product.id}</td><td>${product.type}</td><td><mine:currency figure="${product.price}"/></td></tr>
                </table>
                </div>
                <div class="adminRight">
                    <form action="/Controller" method="post">
                        <input type="hidden" name="service" value="deleteProduct">
                        <input type="hidden" name="id" value="${product.id}">
                        <input type="hidden" name="referrer" value="Controller?service=admin&admin=Products">
                        <input type="submit" value="${deleteProduct}"/>
                    </form>
                </div>
                </div>
            </c:forEach>
        </c:when>
        <c:when test="${param.admin eq 'Users'}">
            <c:forEach var="user" items="${listOfUsers}">
                <jsp:useBean id="user" class="epam.javalab22.pchardware.entity.User" />
                <div class="adminList">
                    <div class="adminLeft">
                        <table class="adminTable">
                            <tr><td>${username}:</td><td><b>${user.username}</b></td></tr>
                            <tr><td>${email}:</td><td>${user.email}</td></tr>
                            <tr><td>${name}:</td><td>${user.name}</td></tr>
                            <tr><td>${lastName}:</td><td>${user.lastName}</td></tr>
                            <tr><td>${phoneNumber}:</td><td>${user.phoneNumber}</td></tr>
                            <tr><td>${address}:</td><td>${user.address}</td></tr>
                        </table>
                    </div>
                    <c:if test="${user.username != 'admin'}">
                        <div class="adminRight">
                            <form action="/Controller" method="post">
                                <input type="hidden" name="service" value="deleteUser">
                                <input type="hidden" name="username" value="${user.username}">
                                <input type="hidden" name="referrer" value="Controller?service=admin&admin=Users">
                                <input type="submit" value="${deleteUser}"/>
                            </form>
                        </div>
                    </c:if>
                </div>
            </c:forEach>
        </c:when>
    </c:choose>

</div>
<tag:footer/>
</body>
</html>