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
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/admin-page-style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/admin-page.js"></script>
    <link href="${pageContext.request.contextPath}/css/morris.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/morris.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/raphael.min.js"></script>
    <link href="${pageContext.request.contextPath}/css/bootstrap-editable.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/bootstrap-editable.min.js"></script>

</head>
<body>
<c:import url="template/admin-header.jsp"/>
<div class="container">
    <div id="message"></div>
    <div class="row">
        <div class="col-sm-11">
            <label class="label label-success"><fmt:message bundle="${loc}" key="statistics.user.reg"/> </label>
            <div id="user-chart"></div>
        </div>

    </div>
    <div class="row">
        <div class="col-sm-11">
            <label class="label label-success"><fmt:message bundle="${loc}" key="statistics.movies"/></label>
            <div id="review-chart"></div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-5">
            <label class="label label-success"><fmt:message bundle="${loc}" key="statistics.genre"/></label>
            <div id="genre-chart"></div>
        </div>
        <div class="col-sm-6">
            <ul class="list-inline list-group">
                <c:forEach items="${requestScope.genres}" var="genre">
                    <li class="list-group-item">
                        ${genre.name}
                        <p class="id-genre">${genre.id}</p>
                        <span class="btn-label btn-danger delete-genre">
                            <i class="glyphicon glyphicon-remove"></i>
                        </span>
                        <span class="btn-label btn-primary edit-genre">
                            <i class="glyphicon glyphicon-edit"></i>
                        </span>
                    </li>
                </c:forEach>
            </ul>
            <div id="add-content">
                <button id="add-genre" class="btn-success"><fmt:message bundle="${loc}" key="add.genre"/></button>
                <form id="add-genre-form">
                    <div class="form-group name-form-group">
                        <label for="nameRu"><fmt:message bundle="${loc}" key="genre.name.ru"/> </label>
                        <input type="text" class="form-control" id="nameRu" name="nameRu"/>
                    </div>
                    <div class="form-group name-form-group">
                        <label for="nameEn"><fmt:message bundle="${loc}" key="genre.name.en"/> </label>
                        <input type="text" class="form-control" id="nameEn" name="nameEn"/>
                    </div>
                </form>
                <button id="save-add-genre" class="btn-success"><fmt:message bundle="${loc}" key="save"/></button>
            </div>
        </div>
    </div>
</div>

<c:import url="template/footer.jsp"/>
</body>
</html>
