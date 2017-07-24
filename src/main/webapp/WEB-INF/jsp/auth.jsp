<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization" var="loc"/>
<div id="myModal"  class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header text-center">
                <button class="close" type="button" data-dismiss="modal">×</button>
                <h4 class="modal-title">Авторизация</h4>
            </div>

            <div class="modal-body text-center">
                <div class="tab-content">
                    <div class="tab-pane active" id="profile">
                        <form action= "Controller" method="get" role="form">
                            <input type="hidden" name="command" value="login">
                            <div class="row">
                                <div class="input-group">
						<span class="input-group-addon">
						<span class="glyphicon glyphicon-user"></span>
						</span>
                                    <input type="text" name="login" class="form-control" id="login" placeholder="${username}" required />
                                </div>
                            </div>
                            <p></p>
                            <div class="row">
                                <div class="input-group">
						<span class="input-group-addon">
						<span class="glyphicon glyphicon-lock"></span>
						</span>
                                    <input type="password" name="password" class="form-control" id="password" placeholder="${password}" required autofocus />
                                </div>
                            </div>
                            <p></p>
                            У вас нет аккаунта? <a href="#">Регистрация</a>
                            <p></p>
                            <div class="row">
                                <button type="submit" class="btn btn-labeled btn-success">
                                    <span class="btn-label"><i class="glyphicon glyphicon-ok"></i></span>Войти</button>
                                <button type="button" class="btn btn-labeled btn-danger">
                                    <span class="btn-label"><i class="glyphicon glyphicon-remove"></i></span>Выход</button>

                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>