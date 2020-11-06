<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title><fmt:message key="adminfaculties.title"/></title>
</head>
<body>
<c:import url="//jsp//fragment//header.jsp"/>
<main class="page page-information">
    <section class="lean-block clean-testimonials dark">
        <div class="container">
            <div class="block-heading">
                <h2 class="text-center"><fmt:message key="adminfaculties.title"/></h2>
            </div>
            <c:if test="${has_application}">
                <small>
                    <label class="alert-danger"> <fmt:message key="adminfaculties.deletederror"/></label>
                </small>
            </c:if>
            <table class="table">
                <caption><fmt:message key="adminfaculties.note"/></caption>
                <thead class="thead-light">
                <tr>
                    <th scope="col"></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${faculties}" var="faculty">
                    <tr>
                        <th scope="row">${faculty.facultyId}</th>
                        <td>${faculty.facultyName}</td>
                        <td>
                            <button class="btn btn-outline-primary btn-sm"
                                    type="button"
                                    onclick='location.href = "projectServlet?command=to_specialties_page&faculty_id=${faculty.facultyId}"'>
                                <fmt:message key="adminfaculties.showbutton"/>
                            </button>
                        </td>
                        <td>
                            <div>
                                <button class="btn btn-outline-primary btn-sm"
                                        type="button"
                                        onclick='location.href = "projectServlet?command=delete_faculty&faculty_id=${faculty.facultyId}"'>
                                    <fmt:message key="adminfaculties.deletebutton"/></button>
                                </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="row align-items-center">
                <div class="col-md-10">
            <button class="btn btn-outline-primary btn-sm"
                    type="button"
                    onclick='location.href = "projectServlet?command=to_specialties_page&faculty_id=${faculty.facultyId}"'>
                <fmt:message key="adminfaculties.showbutton"/>
            </button></div>
                <div class="col-md-10">
            <button class="btn btn-outline-primary btn-sm"
                    type="button"
                    onclick='location.href = "projectServlet?command=to_specialties_page&faculty_id=${faculty.facultyId}"'>
                <fmt:message key="adminfaculties.showbutton"/>
            </button></div>
        </div>
    </section>
</main>
<c:import url="//jsp//fragment//footer.jsp"/>
</body>
</html>
