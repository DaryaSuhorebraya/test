<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="pgn" uri="/WEB-INF/tld/pagination.tld" %>
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
    <link href="${pageContext.request.contextPath}/css/movie-list-style.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/admin-movies.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/user-movies.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.dotdotdot.min.js" type="text/javascript"></script>
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
<p></p>
<div id="message"></div>
<div class="container">
    <div class="well well-sm">
        <strong> <fmt:message bundle="${loc}" key="movies"/> </strong>
        <div class="btn-group">
            <a href="#" id="list" class="btn btn-default btn-sm">
                <span class="glyphicon glyphicon-th-list"></span><fmt:message bundle="${loc}" key="list"/>
            </a>
            <a href="#" id="grid" class="btn btn-default btn-sm">
                <span class="glyphicon glyphicon-th"></span><fmt:message bundle="${loc}" key="grid"/>
            </a>
        </div>

        <c:if test='${sessionScope.role eq "admin"}'>
            <a href="Controller?command=redirect&redirectPage=addMovie" id="addMovie" class="btn btn-default btn-sm pull-right">
            <span class="glyphicon glyphicon-plus"></span><fmt:message bundle="${loc}" key="add.movie"/>
            </a>
        </c:if>
    </div>

    <div id="products" class="row list-group">
<c:if test="${requestScope.movies != null}">
        <c:forEach var="movie" items="${requestScope.movies}">
            <div class="item  col-xs-4 col-lg-4 col-md-4">
                <div class="thumbnail" >
                    <p id="id">${movie.id}</p>
                    <c:if test='${sessionScope.role eq "admin"}'>
                        <button class="btn btn-labeled delete-movie">
                            <span class="btn-label" >
                                <i class="glyphicon glyphicon-minus"></i>
                            </span>
                        </button>
                    </c:if>
                    <a href="Controller?command=view-movie&movieId=${movie.id}" class="btn btn-info">
                        <fmt:message bundle="${loc}" key="view"/>
                </a>
                    <img class="group list-group-image" src="./${movie.posterPath}" alt="" />
                    <div class="caption">
                        <h4 class="group inner list-group-item-heading title">${movie.title} &nbsp;(${movie.releaseYear})</h4>
                        <h4 class="group inner list-group-item-heading">${movie.rating}</h4>
                        <p class="group inner list-group-item-text">${movie.description}</p>
                        <div class="row">
                            <div class="col-xs-12 col-md-6">
                                <p class="lead">${movie.releaseYear}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </c:if>
    </div>
    <pgn:paginate uri="Controller?command=view-all-movies" currentPage="${curPageNumber}"
                  pageCount="3"
                  next="&raquo;" previous="&laquo;" />
</div>
<c:import url="template/footer.jsp"/>
</body>
</html>
