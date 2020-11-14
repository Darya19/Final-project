<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
<c:choose>
<c:when test="${not empty faculty}">
    <title><fmt:message key="editfaculty.edittitle"/></title>
</c:when>
    <c:otherwise>
        <title><fmt:message key="editfaculty.addtitle"/></title>
    </c:otherwise>
</c:choose>
</head>
<body>
<c:import url="//jsp//fragment//header.jsp"/>
<main class="page registration-page">
    <section class="clean-block clean-form dark">
        <div class="container">
            <div class="block-heading">
                <c:choose>
                    <c:when test="${not empty faculty}">
                        <h2 class="text-info"><fmt:message key="editfaculty.edittitle"/></h2>
                    </c:when>
                    <c:otherwise>
                        <h2 class="text-info"><fmt:message key="editfaculty.addtitle"/></h2>
                    </c:otherwise>
                </c:choose>
            </div>
            <form name="AddFacultyForm" method="POST" action="projectServlet">
                <c:choose>
                <c:when test="${not empty faculty}">
                <input type="hidden" name="command" value="edit_faculty"/>
                    <input type="hidden" name="faculty_id" value="${faculty.facultyId}">
                </c:when>
                    <c:otherwise>
                        <input type="hidden" name="command" value="add_faculty"/>
                    </c:otherwise>
                </c:choose>
                <div class="form-group">
                    <label for="faculty_name"><fmt:message key="editfaculty.facultyname"/></label>
                    <input class="form-control item" type="text" id="faculty_name" name="faculty_name"
                           autofocus value="${faculty.facultyName}"
                           required pattern="[a-zA-ZА-Яа-я\s]{2,40}">
                    <c:if test="${not empty parameters and empty parameters['faculty_name'] }">
                        <small><label class="alert-danger"> <fmt:message key="editfaculty.facultyerror"/></label></small>
                    </c:if>
                </div>

                <div class="form-group"><label for="faculty_description"><fmt:message key="editfaculty.description"/>
                </label>
                    <textarea class="form-control item" type="text" id="faculty_description" name="faculty_description"
                              required placeholder="write here" cols="30" rows="5"> ${faculty.facultyDescription}
                    </textarea>
                </div>
                <c:choose>
                    <c:when test="${not empty faculty}">
                <button class="btn btn-primary btn-block" type="submit"><fmt:message
                        key="editfaculty.editbutton"/></button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-primary btn-block" type="submit"><fmt:message
                                key="editfaculty.addbutton"/></button>
                    </c:otherwise>
                </c:choose>
            </form>
        </div>
    </section>
</main>
<c:import url="//jsp//fragment//footer.jsp"/>
</body>
</html>
