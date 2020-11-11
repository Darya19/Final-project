<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title>${faculty.facultyName}</title>
</head>
<body>
<c:import url="//jsp//fragment//header.jsp"/>
<main class="page faq-page">
    <section class="clean-block clean-faq dark">
        <div class="container">
            <div class="block-heading">
                <h2 class="text-info">${faculty.facultyName}</h2>
                <p>${faculty.facultyDescription}</p>
            </div>
            <div class="block-content">
                <c:forEach items="${specialties}" var="specialty">
                <div class="faq-item">
                    <div class="question"><h4>${specialty.specialtyName}</h4></div>
                    <h6><fmt:message key="specialties.recruitment"/> ${specialty.recruitment}</h6>
                    <h6><fmt:message key="specialties.seats"/> ${specialty.numberOfSeats}</h6>
                    <div class="answer">
                        <c:if test="${empty enrollee and specialty.recruitment == 'OPENED'}">
                        <button class="btn btn-outline-primary btn-lg"
                                type="button" onclick='location.href = "projectServlet?command=to_register_page"'>
                        <fmt:message key="specialties.registerbutton"/> </button>
                        </c:if>
                    </div>
                </c:forEach>
            </div>
        </div>
            <p></p>
                <div class="col-md-10 right-button">
                    <button class="btn btn-outline-primary btn-sm"
                            type="button"
                            onclick='location.href = "projectServlet?command=to_faculties_page"'>
                        <fmt:message key="specialties.backbutton"/>
                    </button></div>
        </div>
    </section>
</main>
<c:import url="//jsp//fragment//footer.jsp"/>
</body>
</html>
