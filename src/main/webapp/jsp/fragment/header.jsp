<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <link rel="stylesheet" href="/bootstrap/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
    <link rel="stylesheet" href="/bootstrap/css/smoothproducts.css">
</head>
<body>
<nav class="navbar navbar-light navbar-expand-lg fixed-top bg-white clean-navbar">
    <div class="container"><a class="navbar-brand logo" href="projectServlet?command=to_main_page">
        <em><fmt:message key="header.bnu"/></em></a>
        <button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1">
            <span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon">
        </span></button>
        <div class="collapse navbar-collapse" id="navcol-1">
<c:choose>
    <c:when test="${role eq 'USER'}" >
            <ul class="nav navbar-nav ml-auto">
                <li class="nav-item" role="presentation"><a class="nav-link active" href="projectServlet?command=to_faculties_page"> <fmt:message key="header.facultiesbutton"/></a></li>
                <li class="nav-item" role="presentation"><a class="nav-link active" href="projectServlet?command=logout"> <fmt:message key="header.logoutbutton"/></a></li>
            </ul>
    </c:when>
    <c:when test="${role eq 'ADMIN'}" >
        <ul class="nav navbar-nav ml-auto">
            <li class="nav-item" role="presentation"><a class="nav-link active" href="projectServlet?command=to_admin_faculties_page"> <fmt:message key="header.facultiesbutton"/></a></li>
            <li class="nav-item" role="presentation"><a class="nav-link active" href="projectServlet?command=logout"> <fmt:message key="header.logoutbutton"/></a></li>
        </ul>
    </c:when>
            <c:otherwise>
                </ul>
    <ul class="nav navbar-nav ml-auto">
                <li class="nav-item" role="presentation"><a class="nav-link active" href="projectServlet?command=to_faculties_page"> <fmt:message key="header.facultiesbutton"/></a></li>
                <li class="nav-item" role="presentation"><a class="nav-link active" href="projectServlet?command=to_login_page"> <fmt:message key="header.loginbutton"/></a></li>
                <li class="nav-item" role="presentation"><a class="nav-link" href="projectServlet?command=to_register_page"> <fmt:message key="header.registerbutton"/></a></li>
    </ul>
            </c:otherwise>
        </c:choose>
        </div>
    </div>
</nav>
</body>
</html>
