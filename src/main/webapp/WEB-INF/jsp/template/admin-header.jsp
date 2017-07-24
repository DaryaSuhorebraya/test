<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="localization" var="loc"/>
<fmt:message bundle="${loc}" key="username" var="username"/>
<fmt:message bundle="${loc}" key="password" var="password"/>
<link href="css/header-style.css" rel="stylesheet">
<link href="css/bootstrap-submenu.min.css" rel="stylesheet">
<header>
    <div class="navbar navbar-inverse">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">

            <ul class="nav navbar-nav">
                <li class="active"><a href="Controller?command=admin-page"><fmt:message bundle="${loc}" key="home"/> </a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <fmt:message bundle="${loc}" key="movies"/> <strong class="caret"></strong></a>
                    <ul class=" dropdown-menu">
                        <li class="menu-item dropdown dropdown-submenu"> <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <fmt:message bundle="${loc}" key="genre"/> </a>
                            <ul class="dropdown-menu">
                                <c:if test="${sessionScope.get('language') eq 'ru_RU'}">
                                    <c:if test="${applicationScope.genresRu != null}">
                                        <c:forEach items="${applicationScope.genresRu}" var="genre">
                                            <li class="menu-item "><a href="Controller?command=view-movies-by-genre&genreId=${genre.id}">${genre.name} </a></li>
                                        </c:forEach>
                                    </c:if>
                                </c:if>
                                <c:if test="${sessionScope.get('language') eq 'en_EN' }">
                                    <c:if test="${applicationScope.genresEn != null}">
                                        <c:forEach items="${applicationScope.genresEn}" var="genre">
                                            <li class="menu-item "><a href="Controller?command=view-movies-by-genre&genreId=${genre.id}">${genre.name} </a></li>
                                        </c:forEach>
                                    </c:if>
                                </c:if>
                            </ul>
                        </li>
                        <li class="menu-item dropdown dropdown-submenu"> <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <fmt:message bundle="${loc}" key="country"/> </a>
                            <ul class="dropdown-menu">
                                <c:if test="${sessionScope.get('language') eq 'ru_RU' }">
                                    <c:if test="${applicationScope.countriesRu != null}">
                                        <c:forEach items="${applicationScope.countriesRu}" var="country">
                                            <li class="menu-item "><a href="Controller?command=view-movies-by-country&countryCode=${country.code}">${country.name} </a></li>
                                        </c:forEach>
                                    </c:if>
                                </c:if>
                                <c:if test="${sessionScope.get('language') eq 'en_EN'}">
                                    <c:if test="${applicationScope.countriesEn != null}">
                                        <c:forEach items="${applicationScope.countriesEn}" var="country">
                                            <li class="menu-item "><a href="Controller?command=view-movies-by-country&countryCode=${country.code}">${country.name} </a></li>
                                        </c:forEach>
                                    </c:if>
                                </c:if>
                            </ul>
                        </li>
                        <li><a href="Controller?command=view-newest-movies"><fmt:message bundle="${loc}" key="newest"/> </a></li>
                        <li><a href=""><fmt:message bundle="${loc}" key="popular"/> </a></li>
                        <li><a href="Controller?command=view-all-movies"><fmt:message bundle="${loc}" key="all"/> </a></li>

                    </ul>
                </li>
                <li><a href="Controller?command=view-all-actors"><fmt:message bundle="${loc}" key="actors"/> </a></li>
                <li><a href="Controller?command=view-top-movies"><fmt:message bundle="${loc}" key="top"/> </a></li>
                <li><a href="Controller?command=view-all-reviews-order-by-date"><fmt:message bundle="${loc}" key="review"/> </a></li>
                <li><a href="Controller?command=view-all-users"><fmt:message bundle="${loc}" key="users"/> </a></li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <c:if test="${sessionScope.get('user') !=null}">
                    <li class="dropdown">
                        <a href="#" id="user-login">${user.login}</a> </li>
                </c:if>
                <c:if test="${sessionScope.get('language') eq 'ru_RU' || sessionScope.get('language')==null}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            Русский<strong class="caret"></strong></a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="Controller?command=change-language&language=en">English</a>
                            </li>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${sessionScope.get('language') eq 'en_EN'}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            English
                            <strong class="caret"></strong></a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="Controller?command=change-language&language=ru">Русский</a>
                            </li>
                        </ul>
                    </li>
                </c:if>
                <c:choose>
                    <c:when test="${sessionScope.userId== null}">
                        <a href="#" class="btn btn-info btn-lg " data-toggle="modal" data-target="#myModal">
                    <span class="glyphicon glyphicon-log-in">
                    </span> <fmt:message bundle="${loc}" key="log.in"/>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="Controller?command=logout" class="btn btn-info btn-lg ">
                    <span class="glyphicon glyphicon-log-out">
                    </span>  <fmt:message bundle="${loc}" key="log.out"/>
                        </a>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</header>
