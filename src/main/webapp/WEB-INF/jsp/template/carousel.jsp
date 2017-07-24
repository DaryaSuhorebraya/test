<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="localization" var="loc"/>
<fmt:message bundle="${loc}" key="username" var="username"/>
<fmt:message bundle="${loc}" key="password" var="password"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row" id="carousel">
    <div class="col-md-12">
        <div class="carousel slide" id="carousel-259970">
            <ol class="carousel-indicators">
                <li data-slide-to="0" data-target="#carousel-259970" class="active">
                </li>
                <li data-slide-to="1" data-target="#carousel-259970">
                </li>
                <li data-slide-to="2" data-target="#carousel-259970" >
                </li>
            </ol>
            <div class="carousel-inner">
                <div class="item">
                    <img id="first" alt="Carousel Bootstrap First" src="images/main/after_earth4.jpg">
                    <div class="carousel-caption">
                    </div>
                </div>
                <div class="item active left">
                    <img id="second" alt="Carousel Bootstrap Second" src="images/main/inception_banner2.jpg">
                </div>
                <div class="item next left">
                    <img alt="Carousel Bootstrap Third" src="images/main/johnwick_poster2.jpg">
                </div>
            </div> <a class="left carousel-control" href="#carousel-259970" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a> <a class="right carousel-control" href="#carousel-259970" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
        </div>
    </div>
</div>

