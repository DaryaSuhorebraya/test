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
    <link href="${pageContext.request.contextPath}/css/top-movie-style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/css/star-rating.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/star-rating.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/user-top-movies.js"></script>
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
        <div class="col-sm-9">
            <table class="table">
                <thead>
                    <tr>
                    <th></th>
                    <th><fmt:message bundle="${loc}" key="rank"/>&<fmt:message bundle="${loc}" key="title"/></th>
                    <th id="rating" class="rating-content">
                        <fmt:message bundle="${loc}" key="rating"/>
                        <a href="#" class="btn btn-default btn-xs">
                        <span class="glyphicon glyphicon-sort-by-order"></span>
                    </a>
                        <a href="#" class="btn btn-default btn-xs">
                            <span class="glyphicon glyphicon-sort-by-order-alt"></span>
                        </a>
                    </th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${sessionScope.userId != null}">
                        <p id="user-id">${sessionScope.userId}</p>
                    </c:if>

                    <c:forEach var="movie" items="${requestScope.movies}" varStatus="loop">
                        <tr class="movie-tr">
                            <td class="td-img"><img class="img-top" src="./${movie.posterPath}" alt="" /></td>
                            <td>${loop.index+1}.&nbsp;
                                <a class="movie-a" href="Controller?command=view-movie&movieId=${movie.id}"> ${movie.title}</a>
                            </td>
                            <td class="rating">
                                <div class="rating-content">
                                    ${movie.rating}
                                    <a><span class="glyphicon glyphicon-star single-star"></span></a>
                                </div>
                                <div class="star-rating">
                                    <input data-size="xs" data-min="0" data-max="10"
                                       data-step="1" class="rating-loading input-stars">
                                    <button class="btn btn-primary btn-rate" value="<fmt:message bundle="${loc}" key="rate"/>"><fmt:message bundle="${loc}" key="rate"/></button>
                                    <button class="btn btn-info btn-unrate" ><fmt:message bundle="${loc}" key="rate"/></button>
                                    <p class="movie-id">${movie.id}</p>
                                </div>
                                <button class="btn btn-info btn-unrate" ><fmt:message bundle="${loc}" key="remove.rating"/></button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<c:import url="template/footer.jsp"/>
</body>
</html>
