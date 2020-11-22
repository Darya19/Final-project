<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.pagecontent"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="login.title"/></title>
</head>
<body>
<c:import url="/jsp/fragment/header.jsp"/>
<main class="page login-page">
    <section class="clean-block clean-form dark">
        <div class="container">
            <div class="block-heading">
                <h2 class="text-info"><fmt:message key="login.title"/></h2>
            </div>
            <form name="loginForm" method="POST" action="/projectServlet">
                <input type="hidden" name="command" value="login"/>
                <div class="form-group"><label for="email"><fmt:message key="login.email"/> </label>
                    <input class="form-control item" type="email" id="email" name="email"
                           autofocus required value="${parameters.get("email")}"></div>
                <div class="form-group"><label for="password"><fmt:message key="login.password"/> </label>
                    <input class="form-control" type="password" id="password" name="password"
                           value="${parameters.get("password")}" minlength="8" maxlength="40"
                           required pattern="[a-zA-Z0-9@#$%!]{8,40}">
                </div>
                <c:if test="${not empty parameters}">
                    <c:if test="${empty parameters['email'] or empty paramaters['password']}">
                        <small><label class="alert-danger"><fmt:message key="login.loginerror"/></label></small>
                    </c:if>
                </c:if>
                <button class="btn btn-primary btn-block" type="submit"><fmt:message key="login.loginbutton"/></button>
            </form>

        </div>
    </section>
</main>
<c:import url="//jsp//fragment//footer.jsp"/>
</body>
</html>