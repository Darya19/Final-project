<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title><fmt:message key="error.title"/></title>
</head>
<body>
<c:import url="/jsp/fragment/header.jsp"/>
<main class="page landing-page">
    <section class="">
        <img src="/img/err404.jpg" alt=""/>
        <div class="text">
            <h5 class="text-center" style="color: blue"><c:out value="${pageContext.errorData.statusCode}:
                ${pageContext.exception}"/></h5>
        </div>
    </section>
</main>
<c:import url="/jsp/fragment/footer.jsp"/>
</body>
</html>