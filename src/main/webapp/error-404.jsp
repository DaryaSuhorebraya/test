<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="localization" var="loc"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><fmt:message bundle="${loc}" key="error"/> </title>
    <link href="css/image-style.css" rel="stylesheet">
</head>
<body style="background-color: white;">
<div id="error-404">
    <img src="./images/error/404.jpg" style="margin-left: auto;margin-right: auto;display: block;">
</div>
<h1 style="text-align: center;margin-top: -20px;font-family: Verdana;">
    <fmt:message bundle="${loc}" key="error.404"/>
</h1>
    <h2 style="text-align: center;font-family: Verdana;">
        <a href="Controller?cmd=welcome-page"  style="color: black;"><fmt:message bundle="${loc}" key="home"/> </a>
    </h2>
</body>
</html>

