<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="localization" var="loc"/>
<html lang="${sessionScope.language}">
<head>
    <title>ProFilm</title>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/bootstrapValidator.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/registration-style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/registration.js"></script>
</head>
<body>
<c:import url="template/user-header.jsp"/>
<div class="container-fluid text-center wrapper">
    <h4><fmt:message bundle="${loc}" key="register"/></h4>
    <form class="form-horizontal" action="Controller?command=registration" method="post" id="registerForm">
        <div class="form-group">
            <label class="control-label col-xs-3" for="login"><fmt:message bundle="${loc}" key="login"/> </label>
            <div class="col-xs-9">
                <input type="text" class="form-control" id="login" name="login" required placeholder="<fmt:message bundle="${loc}" key="login"/>" >
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-3" for="lastName"><fmt:message bundle="${loc}" key="lastname"/> </label>
            <div class="col-xs-9">
                <input type="text" class="form-control" id="lastName" name="lastName" placeholder="<fmt:message bundle="${loc}" key="lastname"/>" >
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-3" for="firstName"><fmt:message bundle="${loc}" key="firstname"/> </label>
            <div class="col-xs-9">
                <input type="text" class="form-control" id="firstName" name="firstName" placeholder="<fmt:message bundle="${loc}" key="firstname"/> ">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-3" for="inputEmail"><fmt:message bundle="${loc}" key="email"/> </label>
            <div class="col-xs-9">
                <input type="email" class="form-control" id="inputEmail" name="email" placeholder="<fmt:message bundle="${loc}" key="email"/> ">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-3" for="inputPassword"><fmt:message bundle="${loc}" key="password"/> </label>
            <div class="col-xs-9">
                <input type="password" class="form-control" id="inputPassword" name="password" placeholder="<fmt:message bundle="${loc}" key="password"/> ">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-3" for="confirmPassword"><fmt:message bundle="${loc}" key="confirmPassword"/> </label>
            <div class="col-xs-9">
                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="<fmt:message bundle="${loc}" key="confirmPassword"/> ">
            </div>
        </div>

        <br />
        <div class="form-group buttons">
            <div class="col-xs-offset-3 col-xs-9">
                <input type="submit" class="btn btn-primary" value="<fmt:message bundle="${loc}" key="register"/>">
                <input type="reset" class="btn btn-default" value="<fmt:message bundle="${loc}" key="reset"/>">
            </div>
        </div>
    </form>
</div>
<c:import url="template/footer.jsp"/>
</body>
</html>
