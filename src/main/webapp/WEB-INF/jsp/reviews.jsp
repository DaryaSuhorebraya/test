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
    <link href="${pageContext.request.contextPath}/css/reviews-style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/star-rating.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/star-rating.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/reviews.js"></script>
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


<div class="container">
    <div class="row">
        <div id="message"></div>
        <div class="col-sm-8">
            <div class="review-block">
                <c:if test="${requestScope.reviews != null}">
                    <c:forEach var="review" items="${requestScope.reviews}">
                        <div class="row">
                            <div class="col-sm-3">
                                <div class="user-span"><span class="glyphicon glyphicon-user"></span></div>
                                <div class="review-block-name"><a href="#">${review.userLogin}</a></div>
                                <div class="review-block-date">${review.publishDate}</div>
                            </div>
                            <div class="col-sm-9">
                                <c:if test='${sessionScope.role eq "admin"}'>
                                    <p class="id-movie">${review.idMovie}</p>
                                    <p class="id-user">${review.idUser}</p>
                                    <span class="btn-label delete-review">
                                        <i class="glyphicon glyphicon-remove"></i>
                                    </span>
                                </c:if>
                                <h3><a class="movie-title"
                                       href="Controller?command=view-movie&movieId=${review.idMovie}">${review.movieTitle}</a>
                                </h3>
                                <div class="review-block-rate">
                                    <input class="input-stars" data-size="xs" value="${review.rating}" data-min="0"
                                           data-max="10" data-step="1" class="rating rating-loading">
                                </div>
                                <div class="review-block-title">${review.title}</div>
                                <div class="review-block-description">${review.review}</div>
                            </div>
                        </div>
                        <hr/>
                    </c:forEach>
                </c:if>

            </div>
        </div>
    </div>

</div>
<c:import url="template/footer.jsp"/>
</body>
</html>
