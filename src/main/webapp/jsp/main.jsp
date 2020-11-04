<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title><fmt:message key="main.title"/></title>
</head>
<body>
<c:import url="//jsp//fragment//header.jsp"/>
<main class="page landing-page">
    <section class="clean-block clean-hero" style="background-image: url(/img/picture.jpg);color:rgba(9, 162, 255, 0.85);">
        <div class="text">
            <p><fmt:message key="main.velcome" /></p>
            <h2><fmt:message key="main.bnu"/> </h2>
            <button class="btn btn-outline-light btn-lg" type="button" onclick='location.href = "projectServlet?command=to_faculties_page"'><fmt:message key="main.facultybutton"/></button>
    </div>
    </section>
    <section class="clean-block clean-info dark">
        <div class="container">
            <div class="block-heading">
                <h2 class="text-info"><fmt:message key="main.educationtitle"/> </h2>
                <p><fmt:message key="main.educationtext"/> </p>
            </div>
            <div class="row align-items-center">
                <div class="col-md-6"><img class="img-thumbnail" src="/img/picture2.jpg"></div>
                <div class="col-md-6">
                    <p></p>
                    <div class="getting-started-info">
                        <p><fmt:message key="main.description"/> </p>
                    </div><button class="btn btn-outline-primary btn-lg" type="button" onclick='location.href = "projectServlet?command=to_register_page"'><fmt:message key="main.registerbutton"/> </button></div>
            </div>
        </div>
    </section>
</main>
<c:import url="//jsp//fragment//footer.jsp"/>
</body>
</html>
