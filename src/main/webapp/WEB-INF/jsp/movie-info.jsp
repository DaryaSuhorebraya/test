<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

    <link href="${pageContext.request.contextPath}/css/bootstrap-editable.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/bootstrap-editable.min.js"></script>
    <link href="${pageContext.request.contextPath}/css/bootstrap-select.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/bootstrap-select.min.js"></script>
    <link href="${pageContext.request.contextPath}/css/star-rating.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/star-rating.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/admin-movie-info.js"></script>
    <link href="${pageContext.request.contextPath}/css/movie-info-style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/user-movie-info.js"></script>
</head>
<body>
<c:choose>
    <c:when test="${sessionScope.get('role') eq 'admin'}">
        <c:import url="template/admin-header.jsp"/>
    </c:when>
    <c:otherwise>
        <c:import url="template/user-header.jsp"/>
    </c:otherwise>
</c:choose>

<div class="container">
    <div class="row">
        <div id="message"></div>
        <div class="col-md-10">
            <div class="thumbnail">
                <div class="row main">
                    <img class="img-responsive" src="./${movie.posterPath}" alt="">
                    <c:if test='${sessionScope.role eq "admin"}'>
                        <button class="btn btn-labeled edit" id="img-edit">
                            <span class="glyphicon glyphicon-edit"></span>
                        </button>
                        <form method="post" action="UploadServlet?command=upload-movie-poster&movieId=${movie.id}"
                              enctype='multipart/form-data' class="form-img">
                            <input name='data' type='file'>
                            <input type="submit" value="<fmt:message bundle="${loc}" key="save"/>">
                        </form>
                    </c:if>
                    <p id="movie-id">${movie.id}</p>
                    <ul class="list-group">
                        <li class="list-group-item">
                            <fmt:message bundle="${loc}" key="release.year"/>:<a
                                id="year-text"> ${movie.releaseYear}</a>
                            <c:if test='${sessionScope.role eq "admin"}'>
                                <button class="btn btn-labeled edit" id="year-edit">
                                    <span class="glyphicon glyphicon-edit"></span>
                                </button>
                                <a id='save-edit-year' type='button' class='btn btn-primary'> <span
                                        class="glyphicon glyphicon-ok"></span></a>
                                <a id='cancel-edit-year' type='button' class='btn btn-primary'> <span
                                        class="glyphicon glyphicon-remove"></span></a>
                            </c:if>
                        </li>
                        <li class="list-group-item"><fmt:message bundle="${loc}" key="country"/>:
                            <c:forEach var="country" items="${requestScope.countries}">
                                <a href="Controller?command=view-movies-by-country-name&countryName=${country.name}"
                                   id="country-text">
                                        ${country.name}</a>
                                <a type='button' class='btn btn-primary btn-xs delete-country'> <span
                                        class="glyphicon glyphicon-remove"></span></a>
                            </c:forEach>
                            <c:if test='${sessionScope.role eq "admin"}'>
                                <div id="select-country">
                                    <select class="selectpicker form-control input-sm" title=""
                                            data-style="input-sm btn-default" data-size="4" data-live-search="true">
                                    </select>
                                </div>
                                <a id='plus-country' type='button' class='btn btn-primary btn-xs'> <span
                                        class="glyphicon glyphicon-plus"></span></a>
                                <a id='save-edit-country' type='button' class='btn btn-xs btn-primary'> <span
                                        class="glyphicon glyphicon-ok"></span></a>
                                <a id='cancel-edit-country' type='button' class='btn btn-primary'> <span
                                        class="glyphicon glyphicon-remove"></span></a>
                                <button class="btn btn-labeled edit" id="country-edit">
                                    <span class="glyphicon glyphicon-edit"></span>
                                </button>
                            </c:if>
                        </li>
                        <li class="list-group-item"><fmt:message bundle="${loc}" key="genre"/>:
                            <c:forEach var="genre" items="${requestScope.genres}">
                                <a href="Controller?command=view-movies-by-genre-name&genreName=${genre.name}">${genre.name}
                                </a>
                                <a type='button' class='btn btn-primary btn-xs delete-genre'> <span
                                        class="glyphicon glyphicon-remove"></span></a>
                            </c:forEach>
                            <c:if test='${sessionScope.role eq "admin"}'>
                                <div id="select-genre">
                                    <select class="selectpicker form-control input-sm" title=""
                                            data-style="input-sm btn-default" data-size="4" data-live-search="true">
                                    </select>
                                </div>
                                <a id='plus-genre' type='button' class='btn btn-primary btn-xs'> <span
                                        class="glyphicon glyphicon-plus"></span></a>
                                <a id='save-edit-genre' type='button' class='btn btn-xs btn-primary'> <span
                                        class="glyphicon glyphicon-ok"></span></a>
                                <a id='cancel-edit-genre' type='button' class='btn btn-primary'> <span
                                        class="glyphicon glyphicon-remove"></span></a>
                                <button class="btn btn-labeled edit" id="genre-edit">
                                    <span class="glyphicon glyphicon-edit"></span>
                                </button>
                            </c:if>
                        </li>
                        <li class="list-group-item"><fmt:message bundle="${loc}" key="actors"/>:
                            <c:forEach var="actor" items="${requestScope.actors}">
                                <a href="Controller?command=view-movies-by-actor-initial&actorFName=${actor.firstName}&actorLName=${actor.lastName}">
                                        ${actor.firstName} &nbsp;${actor.lastName}</a>
                                <a type='button' class='btn btn-primary btn-xs delete-actor'>
                                    <span class="glyphicon glyphicon-remove"></span>
                                </a>
                            </c:forEach>
                            <c:if test='${sessionScope.role eq "admin"}'>
                                <div id="select-actor">
                                    <select class="selectpicker form-control input-sm" title=""
                                            data-style="input-sm btn-default" data-size="4" data-live-search="true">
                                    </select>
                                </div>
                                <a id='plus-actor' type='button' class='btn btn-primary btn-xs'> <span
                                        class="glyphicon glyphicon-plus"></span></a>
                                <a id='save-edit-actor' type='button' class='btn btn-xs btn-primary'> <span
                                        class="glyphicon glyphicon-ok"></span></a>
                                <a id='cancel-edit-actor' type='button' class='btn btn-primary'> <span
                                        class="glyphicon glyphicon-remove"></span></a>
                                <button class="btn btn-labeled edit" id="actor-edit">
                                    <span class="glyphicon glyphicon-edit"></span>
                                </button>
                            </c:if>
                        </li>
                    </ul>
                </div>
                <div class="caption-full">
                    <h4 class="h4-title" id="title-text">${movie.title}</h4>
                    <c:if test='${sessionScope.role eq "admin"}'>
                        <button class="btn btn-labeled edit" id="title-edit">
                            <span class="glyphicon glyphicon-edit"></span>
                        </button>
                        <a id='save-edit-title' type='button' class='btn btn-primary'> <span
                                class="glyphicon glyphicon-ok"></span></a>
                        <a id='cancel-edit-title' type='button' class='btn btn-primary'> <span
                                class="glyphicon glyphicon-remove"></span></a>

                    </c:if>
                    <div class="ratings">
                        <p class="p-rating">
                            <input id="input-stars" data-size="xs" value=${movie.rating} data-min="0" data-max="10"
                                   data-step="1" class="rating rating-loading">
                        </p>
                        <h4 class="h4-rating">${movie.rating}</h4>
                        <c:choose>
                           <c:when test="${requestScope.get('rating') !=null}">
                            <h4 class="h4-user-rating">(
                                <fmt:message bundle="${loc}" key="your.rating"/> ${rating.mark} </h4>
                            <span class="btn-label delete-rating">
                                <i class="glyphicon glyphicon-remove"></i>
                            </span>
                            <h4 class="h4-user-rating">)</h4>
                            </c:when>
                            <c:otherwise>
                                <a class="btn btn-success rate"><fmt:message bundle="${loc}" key="rate"/> </a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <p id="description-text">${movie.description}</p>
                    <c:if test='${sessionScope.role eq "admin"}'>
                        <button class="btn btn-labeled edit pull-right" id="description-edit">
                            <span class="glyphicon glyphicon-edit"></span>
                        </button>
                        <a id='save-edit-description' type='button' class='btn btn-primary'> <span
                                class="glyphicon glyphicon-ok"></span></a>
                        <a id='cancel-edit-description' type='button' class='btn btn-primary'> <span
                                class="glyphicon glyphicon-remove"></span></a>
                    </c:if>
                </div>
            </div>
            <c:if test="${sessionScope.userId != null}">
                <p id="user-id">${sessionScope.userId}</p>
            </c:if>
            <div class="well">
                <div class="add-review-block">
                    <div class="text-right">
                        <a class="btn btn-success leave-review"><fmt:message bundle="${loc}" key="leave.review"/> </a>
                    </div>
                    <div class="add-review">
                        <div class="add-star-rating">
                            <input data-size="xs" data-min="0" data-max="10"
                                   data-step="1" class="rating-loading add-rating">
                            <button class="btn btn-primary btn-rate"><fmt:message bundle="${loc}" key="rate"/></button>
                        </div>
                        <form id="form-add-review">
                            <div class="form-group">
                                <label for="title"><fmt:message bundle="${loc}" key="header"/> </label>
                                <input type="text" class="form-control" id="title" name="title" placeholder=" ">
                            </div>
                            <div class="form-group">
                                <label for="review"><fmt:message bundle="${loc}" key="revie"/> </label>
                                <textarea class="form-control" id="review" name="review" rows="3" required></textarea>
                            </div>
                        </form>
                        <a class="btn btn-success" id="save-review"><fmt:message bundle="${loc}"
                                                                                 key="leave.review"/> </a>
                    </div>
                </div>
                <hr>
                <c:forEach var="review" items="${requestScope.reviews}">
                    <div class="row review">
                        <div class="col-md-12">
                            <c:if test='${sessionScope.role eq "admin"}'>
                            <span class="btn-label delete-review">
                                <i class="glyphicon glyphicon-remove"></i>
                            </span>
                            </c:if>
                            <div class="review-block-rate">
                                <input class="user-stars rating rating-loading" data-size="xs" value="${review.rating}"
                                       data-min="0" data-max="10" data-step="1">
                            </div>
                                <span class="user-login">
                                <a href="Controller?command=view-reviews-by-user-id&userId=${review.idUser}">
                                        ${review.userLogin}
                                </a></span>
                            <h4 class="review-title">${review.title}</h4>
                            <span class="pull-right">${review.publishDate}</span>
                            <p class="review-text">${review.review}</p>
                        </div>
                    </div>
                    <hr>
                </c:forEach>

            </div>
        </div>
    </div>
</div>
<c:import url="template/footer.jsp"/>
</body>
</html>
