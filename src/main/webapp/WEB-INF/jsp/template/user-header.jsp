<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="localization" var="loc"/>
<fmt:message bundle="${loc}" key="username" var="username"/>
<fmt:message bundle="${loc}" key="password" var="password"/>
<link href="${pageContext.request.contextPath}/css/header-style.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/bootstrapValidator.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/bootstrap-submenu.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
<script src="${pageContext.request.contextPath}/js/auth-validation.js"></script>
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
                <li class="active"><a href="Controller?cmd=welcome-page"><fmt:message bundle="${loc}" key="home"/> </a></li>
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

<div id="myModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header text-center">
                <button class="close" type="button" data-dismiss="modal">× </button>
                <h4 class="modal-title"><fmt:message bundle="${loc}" key="auth"/> </h4>
            </div>

            <div class="modal-body text-center">
                <div class="tab-content">
                    <div class="tab-pane active" id="profile">
                        <form action= "Controller" method="get" role="form" id="authForm">
                            <input type="hidden" name="command" value="login">
                            <div class="row">
                            <div class="form-group auth-form-group" id="login-div">
						        <span class="input-group-addon">
						        <span class="glyphicon glyphicon-user"></span>
						        </span>
                                <input type="text" name="login" class="form-control" id="login" placeholder="${username}" required />
                            </div>
                        </div>
                            <p id="login-p"></p>
                            <div class="row" >
                                <div class="form-group auth-form-group" id="pass-div">
						<span class="input-group-addon">
						<span class="glyphicon glyphicon-lock"></span>
						</span>
                                    <input type="password" name="password" class="form-control" id="password" placeholder="${password}" required autofocus />
                                </div>
                            </div>
                            <p id="pass-p"></p>
                            <fmt:message bundle="${loc}" key="register.question"/>
                            <a href="Controller?command=redirect&redirectPage=registration"><fmt:message bundle="${loc}" key="register"/> </a>
                            <p></p>
                            <div class="row">
                                <button type="submit" class="btn btn-labeled btn-success">
                                    <span class="btn-label"><i class="glyphicon glyphicon-ok"></i></span>&nbsp<fmt:message bundle="${loc}" key="log.in"/> </button>
                                <button type="button" data-dismiss="modal" class="btn btn-labeled btn-danger">
                                    <span class="btn-label"><i class="glyphicon glyphicon-remove"></i></span>&nbsp<fmt:message bundle="${loc}" key="close"/> </button>

                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
