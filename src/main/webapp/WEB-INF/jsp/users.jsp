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
    <script src="${pageContext.request.contextPath}/js/admin-users.js"></script>
    <link href="${pageContext.request.contextPath}/css/users-style.css" rel="stylesheet">
</head>
<body>
<c:import url="template/admin-header.jsp"/>
<div class="container">
    <h4 class="caption"><fmt:message bundle="${loc}" key="users"/> </h4>
    <div class="row">
        <div id="message"></div>
        <div class="col-md-10">
            <div class="table-responsive">
                <table class="table table-striped table-hover">
    <thead>
    <tr>
        <th>id</th>
        <th><fmt:message bundle="${loc}" key="firstname"/> </th>
        <th><fmt:message bundle="${loc}" key="lastname"/></th>
        <th><fmt:message bundle="${loc}" key="login"/></th>
        <th><fmt:message bundle="${loc}" key="date.register"/></th>
        <th><fmt:message bundle="${loc}" key="email"/></th>
        <th><fmt:message bundle="${loc}" key="status"/></th>
        <th><fmt:message bundle="${loc}" key="is.admin"/></th>
        <th><fmt:message bundle="${loc}" key="is.banned"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${requestScope.users}">
        <tr>

            <td><c:out value="${user.id}"/> </td>
            <td><c:out value="${user.firstName}"/></td>
            <td><c:out value="${user.lastName}"/></td>
            <td><c:out value="${user.login}"/></td>
            <td><c:out value="${user.dateRegister}"/></td>
            <td><c:out value="${user.email}"/></td>
            <td><c:out value="${user.status}"/></td>

            <c:choose>
                <c:when test="${user.isAdmin()}">
                    <td><fmt:message bundle="${loc}" key="yes"/></td>
                </c:when>
                <c:otherwise>
                    <td><fmt:message bundle="${loc}" key="no"/></td>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${user.isBanned()}">
                    <td><fmt:message bundle="${loc}" key="yes"/></td>
                </c:when>
                <c:otherwise>
                    <td><fmt:message bundle="${loc}" key="no"/></td>
                </c:otherwise>
            </c:choose>
        </tr>
    </c:forEach>
    </tbody>
</table>
            </div>
        </div>

        <div class="col-md-2">
            <div class="sidebar-nav-fixed pull-right affix">
                <div class="well">
                    <ul class="nav ">
                        <li class="nav-header" id="select-user"><fmt:message bundle="${loc}" key="select.a.user"/></li>
                        <li><a id="for-ban" type="button" class="ban"></a>
                        </li>
                        <li><a id="edit"><fmt:message bundle="${loc}" key="edit"/></a>
                        </li>
                        <li><a id="delete"><fmt:message bundle="${loc}" key="delete"/></a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        </div>
    </div>
<c:import url="template/footer.jsp"/>
</body>
</html>
