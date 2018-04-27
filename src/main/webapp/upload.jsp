<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mine" uri="customTags" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${locale}"/>
<fmt:bundle basename="l10n">
    <fmt:message key="fillProduct" var="fillProduct"/>
    <fmt:message key="productName" var="productName"/>
    <fmt:message key="price" var="price"/>
    <fmt:message key="type" var="type"/>
    <fmt:message key="mainboard" var="mainboard"/>
    <fmt:message key="ram" var="ram"/>
    <fmt:message key="vendor" var="vendor"/>
    <fmt:message key="image" var="image"/>
    <fmt:message key="chars1" var="chars1"/>
    <fmt:message key="chars2" var="chars2"/>
    <fmt:message key="charPlaceholder" var="charPlaceholder"/>
    <fmt:message key="addProduct" var="addProduct"/>
    <fmt:message key="hdd" var="hdd"/>
    <fmt:message key="power" var="power"/>
</fmt:bundle>

<html>
<head>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<tag:header/>
<h2>${fillProduct}</h2>
<div class="productBody">
    <div class="inputForm">
    <form action="/Controller" method="post" enctype="multipart/form-data">
    <input type="hidden" name="service" value="addProduct"/>
    <input type="hidden" name="referrer" value="Controller?service=admin&admin=Products"/>
        <table class="adminTable" style="font-size: 14pt;">
            <tr><td>${productName}:</td><td><input type="text" name="name" required/></td></tr>
            <tr><td>${price}:</td><td><input type="number" name="price" required/></td></tr>
            <tr><td>${type}:</td><td><select name="type" required>
            <option value="CPU">CPU</option>
            <option value="GPU">GPU</option>
            <option value="Motherboard">${mainboard}</option>
            <option value="Memory">${ram}</option>
            <option value="Power">${power}</option>
            </select></td></tr>
            <tr><td>${vendor}:</td><td><select name="Vendor" required>
                <option value="Intel">Intel</option>
                <option value="AMD">AMD</option>
                <option value="ASUS">ASUS</option>
                <option value="MSI">MSI</option>
                <option value="ASRock">ASRock</option>
                <option value="GIGABYTE">GIGABYTE</option>
                <option value="Palit">Palit</option>
                <option value="Inno3D">Inno3D</option>
                <option value="EVGA">EVGA</option>
                <option value="Kingston">Kingston</option>
                <option value="Corsair">Corsair</option>
            </select></td></tr>
            <tr><td>${image}:</td><td><input type="file" name="fileName" accept="image/*" required/></td></tr>
            <tr><td>${chars1}:</td><td><textarea name="charsEng" placeholder="${charPlaceholder}" cols="30" rows="10"></textarea></td></tr>
            <tr><td>${chars2}:</td><td><textarea name="charsRus" placeholder="${charPlaceholder}" cols="30" rows="10"></textarea></td></tr>
        </table>


    <input type="submit" value="${addProduct}">
    </form>
    </div>
</div>
</body>
</html>
