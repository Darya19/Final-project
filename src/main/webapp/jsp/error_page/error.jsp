<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title><fmt:message key="incorrectdata.title"/> </title>
</head>
<body>
<c:import url="//jsp//fragment//header.jsp"/>
<main class="page landing-page">
    <section class="">
        <img src="/img/error.jpg" alt=""/>
        <div class="text">
            <h5 class="text-center" ><fmt:message key="incorrectdata.note"/> </h5>
        </div>
    </section>
</main>
<c:import url="//jsp//fragment//footer.jsp"/>
</body>
</html>