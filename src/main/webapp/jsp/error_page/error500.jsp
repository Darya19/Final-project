<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title>error500</title>
</head>
<body>
<c:import url="//jsp//fragment//header.jsp"/>
<main class="page-information">
    <section class="lean-block clean-testimonials dark">
        <div class="container">
        <img class="img-thumbnail" src="/img/error500.jpg">
        <br/>
        Status code: ${pageContext.errorData.statusCode}
        <br/>
        Exception: ${pageContext.exception}
        <br/>
        Message from exception: ${pageContext.exception.message}
        </div>
    </section>
</main>
<c:import url="//jsp//fragment//footer.jsp"/>
</body>
</html>
