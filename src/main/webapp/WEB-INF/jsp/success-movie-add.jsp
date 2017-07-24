<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="localization" var="loc"/>
<html>
<head>
    <title>ProFilm</title>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body>

<c:if test="${sessionScope.role != null}">
    <c:choose>
        <c:when test="${sessionScope.get('role') eq 'admin'}">
            <c:import url="template/admin-header.jsp"/>
        </c:when>
        <c:otherwise>
            <c:import url="template/user-header.jsp"/>
        </c:otherwise>
    </c:choose>
</c:if>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="content" style="height: 80%">
                <h2 style="text-align: center"><fmt:message bundle="${loc}" key="new.movie.add"/></h2>
                <p class="text-center"><fmt:message bundle="${loc}" key="add.new.movie"/>:
                    <a
                        href="Controller?command=redirect&redirectPage=addMovie"><fmt:message bundle="${loc}" key="add.new.movie"/>
                </a></p>
                <p class="text-center"><fmt:message bundle="${loc}" key="go.to.main.page"/>:
                    <a href="Controller?command=admin-page"><fmt:message bundle="${loc}" key="home"/></a></p>
            </div>
        </div>
    </div>
</div>
<c:import url="template/footer.jsp"/>
</body>
</html>
