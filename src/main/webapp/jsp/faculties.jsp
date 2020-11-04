<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title><fmt:message key="faculties.title"/></title>
</head>
<body>
<c:import url="//jsp//fragment//header.jsp"/>
<main class="page testimonials">
    <section class="clean-block clean-testimonials dark">
        <div class="container">
            <div class="block-heading">
                <h2 class="text-info"><fmt:message key="faculties.listname"/> </h2>
            </div>
            <div class="row">
                <c:forEach var="faculty" items="${faculties}">
                <div class="col-lg-4">
                    <div class="card clean-testimonial-item border-0 rounded-0">
                        <div class="card-body">
                            <h3>${faculty.facultyName}</h3>
                            <p class="card-text">${faculty.facultyDescription}</p>
                            <h4 class="card-title">
                                <button class="btn btn-outline-primary btn-lg"
                                        type="button" onclick='location.href = "projectServlet?command=to_specialties_page&faculty_id=${faculty.facultyId}"'>
                                <fmt:message key="faculties.learnbutton"/> </button></h4>
                        </div>
                    </div>
                </div>
                    </c:forEach>
            </div>
        </div>
    </section>
</main>
<c:import url="//jsp//fragment//footer.jsp"/>
</body>
</html>
