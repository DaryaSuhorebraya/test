<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="pgn" uri="/WEB-INF/tld/pagination.tld" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="localization" var="loc"/>
<html lang="${sessionScope.language}">
<head>
    <title>ProFilm</title>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/actors-style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/admin-actors.js"></script>
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
            <c:if test="${requestScope.actors != null}">
                <c:forEach var="actor" items="${requestScope.actors}">
                    <div class="row">
                        <c:if test='${sessionScope.role eq "admin"}'>
                                    <span class="btn btn-label delete-actor">
                                        <i class="glyphicon glyphicon-remove"></i>
                                    </span>
                        </c:if>
                        <p class="actor-id">${actor.id}</p>
                        <div class="col-sm-3">
                            <img class="img-responsive" src="./${actor.imagePath}" alt="">
                        </div>
                        <c:if test='${sessionScope.role eq "admin"}'>
                                    <span class="btn btn-label img-edit btn-xs pull-left">
                                        <i class="glyphicon glyphicon-edit"></i>
                                    </span>
                        </c:if>
                        <div class="col-sm-8 content">
                            <h3 class="initial">${actor.firstName}&nbsp;${actor.lastName}</h3>
                            <c:if test='${sessionScope.role eq "admin"}'>
                                <button type="button" class="btn btn-labeled btn-xs open-modal-edit" data-toggle="modal"
                                        data-target="#edit-modal">
                                    <span class="btn-label">
                                        <i class="glyphicon glyphicon-edit"></i>
                                    </span>
                                </button>
                            </c:if>
                            <ul>
                                <c:forEach var="movie" items="${actor.movieList}">
                                    <li><a href="Controller?command=view-movie&movieId=${movie.id}">${movie.title}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                        <c:if test='${sessionScope.role eq "admin"}'>
                            <form method="post"
                                  action="UploadServlet?command=upload-actor-image&actorId=${actor.id}"
                                  enctype='multipart/form-data' class="form-img">
                                <!-- <span class="btn btn-success fileinput-button">
                                 <span>Select file</span>-->
                                <input name='data' type='file'>
                                <!-- </span>-->
                                <input type="submit" value="<fmt:message bundle="${loc}" key="save"/>">
                            </form>
                                    <span class="btn btn-label cancel-img">
                                        <i class="glyphicon glyphicon-remove"></i>
                                    </span>
                        </c:if>
                    </div>
                    <hr/>

                </c:forEach>
            </c:if>
            <div class="modal fade" id="edit-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog modal-dialog-actor" role="document">
                    <div class="modal-content">
                        <div class="modal-header modal-header-actor">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title modal-title-actor" id="myModalLabel"><fmt:message bundle="${loc}"
                                                                                                     key="edit.actor"/></h4>
                        </div>
                        <div class="modal-body">
                            <form id="actorForm">
                                <div class="form-group">
                                    <label for="inputFirstName"><fmt:message bundle="${loc}" key="firstname"/> </label>
                                    <input type="text" class="form-control" id="inputFirstName" name="firstName"/>
                                </div>
                                <div class="form-group">
                                    <label for="inputLastName"><fmt:message bundle="${loc}" key="lastname"/> </label>
                                    <input type="text" class="form-control" id="inputLastName" name="lastName"/>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer modal-footer-actor">
                            <button type="button" class="btn btn-primary save-edit-actor"><fmt:message bundle="${loc}"
                                                                                                       key="save"/></button>
                            <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message
                                    bundle="${loc}" key="close"/></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-1">
            <c:if test='${sessionScope.role eq "admin"}'>
                <button type="button" class="btn btn-primary" data-toggle="modal"
                        data-target="#add-modal" id="add-actor"><fmt:message bundle="${loc}" key="add.actor"/>
                </button>
            </c:if>
            <div class="modal fade" id="add-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title"><fmt:message bundle="${loc}" key="adding.actor"/></h4>
                        </div>
                        <div class="modal-body">
                            <form id="actorAddForm">
                                <div class="form-group">
                                    <label for="firstNameEn"><fmt:message bundle="${loc}" key="first.name.en"/> </label>
                                    <input type="text" class="form-control" id="firstNameEn" name="firstNameEn"/>
                                </div>
                                <div class="form-group">
                                    <label for="lastNameEn"><fmt:message bundle="${loc}" key="last.name.en"/> </label>
                                    <input type="text" class="form-control" id="lastNameEn" name="lastNameEn"/>
                                </div>
                                <div class="form-group">
                                    <label for="firstNameRu"><fmt:message bundle="${loc}" key="first.name.ru"/> </label>
                                    <input type="text" class="form-control" id="firstNameRu" name="firstNameRu"/>
                                </div>
                                <div class="form-group">
                                    <label for="lastNameRu"><fmt:message bundle="${loc}" key="last.name.ru"/> </label>
                                    <input type="text" class="form-control" id="lastNameRu" name="lastNameRu"/>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer modal-footer-actor">
                            <button type="button" class="btn btn-primary" id="apply-add-actor"><fmt:message
                                    bundle="${loc}" key="apply"/></button>
                            <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message
                                    bundle="${loc}" key="close"/></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <pgn:paginate uri="Controller?command=view-all-actors" currentPage="${curPageNumber}"
                  pageCount="3"
                  next="&raquo;" previous="&laquo;" />
</div>
<c:import url="template/footer.jsp"/>
</body>
</html>
