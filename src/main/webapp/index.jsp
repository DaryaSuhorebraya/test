<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="localization" var="loc"/>
<!DOCTYPE html>
<html lang="${sessionScope.language}">
<head>
    <title>ProFilm</title>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/css/image-style.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/css/bootstrap-select.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/bootstrap-select.min.js"></script>
    <link href="${pageContext.request.contextPath}/css/main-page-style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery.dotdotdot.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/main-page.js" type="text/javascript"></script>
</head>
<body>
<c:import url="WEB-INF/jsp/template/user-header.jsp"/>
<c:import url="WEB-INF/jsp/template/carousel.jsp"/>
<div class="container">
    <section class="review-container">
        <div class="container">
            <div>
                <h2 class="title"><fmt:message bundle="${loc}" key="movies"/></h2>
                <div class="pull-right button-main">
                    <a href="Controller?command=view-all-movies"><fmt:message bundle="${loc}" key="movies"/></a>
                </div>
            </div>

            <div class="row">
                <div class="gallery">
                <c:forEach var="movie" items="${requestScope.movies}">
                    <div class="col-md-3">
                        <a href="#">
                            <img src="./${movie.posterPath}" alt="Poster of movie ${movie.title}" class="item-image img-thumbnail">
                        </a>
                        <h3 class="movie-caption"><a href="#">${movie.title}</a></h3>
                    </div>
                </c:forEach>
                    </div>
            </div>

            <div>
                <h2 class="title"><fmt:message bundle="${loc}" key="latest.review"/></h2>
                <div class="pull-right button-main">
                    <a href="Controller?command=view-all-reviews-order-by-date"><fmt:message bundle="${loc}" key="view.all.reviews"/></a>
                </div>
            </div>
            <div class="row">
                <c:forEach var="review" items="${requestScope.reviews}">
                    <div class="col-md-4">
                        <blockquote class="quote-box ">
                            <p class="quotation-mark">“</p>
                            <h4 class="movie-title">
                                <a href="Controller?command=view-movie&movieId=${review.idMovie}">${review.movieTitle}</a>
                            </h4>
                            <p class="quote-text">${review.review}</p>
                            <hr>
                            <div class="blog-post-actions">
                                <p class="blog-post-bottom pull-left">
                                    <a href="Controller?command=view-reviews-by-user-id&userId=${review.idUser}">${review.userLogin}</a>
                                </p>
                            </div>
                        </blockquote>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>
</div>

<c:import url="WEB-INF/jsp/template/footer.jsp"/>
</body>
</html>