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
    <script src="${pageContext.request.contextPath}/js/admin-add-movie.js"></script>
    <link href="${pageContext.request.contextPath}/css/movie-add-style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/bootstrap-select.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/bootstrap-select.min.js"></script>
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
        <div id="message"></div>
        <div class="col-md-10">
            <div class="thumbnail">
                <form id="movieForm">
                    <div class="form-group">
                        <label for="inputNameEn"><fmt:message bundle="${loc}" key="name.en"/> </label>
                        <input type="text" class="form-control" id="inputNameEn" name="nameEn"  placeholder="<fmt:message bundle="${loc}" key="enter.name.en"/> ">
                    </div>
                    <div class="form-group">
                        <label for="inputNameRu"><fmt:message bundle="${loc}" key="name.ru"/> </label>
                        <input type="text" class="form-control" id="inputNameRu" name="nameRu"  placeholder="<fmt:message bundle="${loc}" key="enter.name.ru"/> ">
                    </div>
                    <div class="form-group">
                        <label for="inputYear"><fmt:message bundle="${loc}" key="release.year"/> </label>
                        <input type="text" class="form-control" id="inputYear" name="releaseYear"  placeholder="<fmt:message bundle="${loc}" key="enter.release.year"/> ">
                    </div>
                    <div class="form-group">
                        <label for="inputDescrEn"><fmt:message bundle="${loc}" key="description.en"/> </label>
                        <textarea class="form-control" id="inputDescrEn" name="descriptionEn" rows="3"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="inputDescrRu"><fmt:message bundle="${loc}" key="description.ru"/> </label>
                        <textarea class="form-control" id="inputDescrRu" name="descriptionRu" rows="3"></textarea>
                    </div>
                </form>
                <button id="addMovieBtn" class="btn btn-primary"><fmt:message bundle="${loc}" key="add.movie"/> </button>
            </div>
            <div class="menu">
                <h2><fmt:message bundle="${loc}" key="second.step"/></h2>
                <ul class="nav navbar-nav ul-w">
                    <li class="panel panel-default li-w" id="country-li">
                        <a data-toggle="collapse" href="#dropdown-add-country" id="addCountry">
                            <fmt:message bundle="${loc}" key="add.country"/>  <span class="caret"></span>
                        </a>
                        <div id="dropdown-add-country" class="panel-collapse collapse">
                            <div class="panel-body">
                                <select class="selectpicker" multiple title="" data-style="btn-default" data-size="4">
                                </select>
                                <button id="addCountryBtn" class="btn btn-primary"><fmt:message bundle="${loc}" key="save"/> </button>
                            </div>
                        </div>
                    </li>
                    <li class="panel panel-default li-w" id="genre-li">
                        <a data-toggle="collapse" href="#dropdown-add-genre" id="addGenre">
                            <fmt:message bundle="${loc}" key="add.genre"/>  <span class="caret"></span>
                        </a>
                        <div id="dropdown-add-genre" class="panel-collapse collapse">
                            <div class="panel-body">
                                <select class="selectpicker" multiple title="" data-style="btn-default" data-size="4">
                                </select>
                                <button id="addGenreBtn" class="btn btn-primary"><fmt:message bundle="${loc}" key="save"/> </button>
                            </div>
                        </div>
                    </li>
                    <li class="panel panel-default li-w" id="actor-li">
                        <a data-toggle="collapse" href="#dropdown-add-actor" id="addActor">
                             <fmt:message bundle="${loc}" key="add.actor"/> <span class="caret"></span>
                        </a>
                        <div id="dropdown-add-actor" class="panel-collapse collapse">
                            <div class="panel-body">
                                <select class="selectpicker" multiple title="" data-style="btn-default" data-size="4">
                                </select>
                                <button id="addActorBtn" class="btn btn-primary"><fmt:message bundle="${loc}" key="save"/> </button>
                            </div>
                        </div>
                    </li>
                </ul>
                <button id="3rdStep" class="btn btn-primary th-next"><fmt:message bundle="${loc}" key="next.step"/></button>
            </div>
            <div class="img-form">
                <h2><fmt:message bundle="${loc}" key="third.step"/></h2>
                <a href="Controller?command=redirect&redirectPage=successAdd" id="skip-3-step" class="btn btn-primary">
                <fmt:message bundle="${loc}" key="skip.this.step"/> </a>
            </div>
        </div>
    </div>
</div>
<c:import url="template/footer.jsp"/>
</body>
</html>
