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
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/user-info-style.css" rel="stylesheet">
</head>
<body>
<c:import url="template/admin-header.jsp"/>
<div class="container-fluid text-center wrapper">
    <h4><fmt:message bundle="${loc}" key="edit"/></h4>
    <form class="form-horizontal" action="Controller?command=edit-user&userId=${user.id}" method="post">
        <div class="form-group">
            <label class="control-label col-xs-3" for="login"><fmt:message bundle="${loc}" key="login"/> </label>
            <div class="col-xs-9">
                <input type="text" class="form-control" id="login" name="login" value="${user.login}" >
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-3" for="lastName"><fmt:message bundle="${loc}" key="lastname"/> </label>
            <div class="col-xs-9">
                <input type="text" class="form-control" id="lastName" name="lastName" value="${user.lastName}" >
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-3" for="firstName"><fmt:message bundle="${loc}" key="firstname"/> </label>
            <div class="col-xs-9">
                <input type="text" class="form-control" id="firstName" name="firstName" value="${user.firstName}">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-3" for="inputEmail"><fmt:message bundle="${loc}" key="email"/> </label>
            <div class="col-xs-9">
                <input type="email" class="form-control" id="inputEmail" name="email" value="${user.email}">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-3" for="dateRegister"><fmt:message bundle="${loc}" key="date.register"/> </label>
            <div class="col-xs-9">
                <input type="datetime" class="form-control" id="dateRegister" name="dateRegister" value="${user.dateRegister}">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-3" for="status"><fmt:message bundle="${loc}" key="status"/> </label>
            <div class="col-xs-9">
                <input type="text" class="form-control" id="status" name="status" value="${user.status}">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-3" for="isAdmin"><fmt:message bundle="${loc}" key="is.admin"/> </label>
            <div class="col-xs-9">
                <c:choose>
                    <c:when test="${user.isAdmin()}">
                        <input type="text" class="form-control" id="isAdmin" name="isAdmin" value="<fmt:message bundle="${loc}" key="yes"/> ">
                    </c:when>
                    <c:otherwise>
                        <input type="text" class="form-control" id="isAdmin" name="isAdmin" value="<fmt:message bundle="${loc}" key="no"/> ">
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-3" for="isBanned"><fmt:message bundle="${loc}" key="is.banned"/> </label>
            <div class="col-xs-9">
                <c:choose>
                    <c:when test="${user.isBanned()}">
                        <input type="text" class="form-control" id="isBanned" name="isBanned" value="<fmt:message bundle="${loc}" key="yes"/> ">
                    </c:when>
                    <c:otherwise>
                        <input type="text" class="form-control" id="isBanned" name="isBanned" value="<fmt:message bundle="${loc}" key="no"/> ">
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <br />
        <div class="form-group">
            <div class="col-xs-offset-3 col-xs-9">
                <input type="submit" class="btn btn-primary" value="<fmt:message bundle="${loc}" key="save"/>">
            </div>
        </div>
    </form>
</div>
<c:import url="template/footer.jsp"/>
</body>
</html>
