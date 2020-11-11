<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <c:choose>
        <c:when test="${not empty specialty}">
            <title><fmt:message key="editspecialty.edittitle"/></title>
        </c:when>
        <c:otherwise>
            <title><fmt:message key="editspecialty.addtitle"/></title>
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
                    <c:when test="${not empty specialty}">
                        <h2 class="text-info"><fmt:message key="editspecialty.edittitle"/></h2>
                    </c:when>
                    <c:otherwise>
                        <h2 class="text-info"><fmt:message key="editspecialty.addtitle"/></h2>
                    </c:otherwise>
                </c:choose>
            </div>
            <form name="AddFacultyForm" method="POST" action="projectServlet">
                <c:choose>
                    <c:when test="${not empty specialty}">
                        <input type="hidden" name="command" value="edit_specialty"/>
                        <input type="hidden" name="specialty_id" value="${specialty.specialtyId}">
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="command" value="add_specialty"/>
                    </c:otherwise>
                </c:choose>
                <div class="form-group">
                    <label for="specialty_name"><fmt:message key="editspecialty.specialtyname"/></label>
                    <input class="form-control item" type="text" id="specialty_name" name="specialty_name"
                           autofocus value="${specialty.specialtyName}"
                           required pattern="[a-zA-ZА-Яа-я\s]{2,40}">
                    <c:if test="${not empty parameters and empty parameters['specialty_name'] }">
                        <small><label class="alert-danger"> <fmt:message key="editspecialty.editerror"/></label></small>
                    </c:if>
                </div>
                <div class="form-group">
                    <label for="number_of_seats"><fmt:message key="editspecialty.numberoseats"/></label>
                    <input class="form-control item" type="text" id="number_of_seats" name="number_of_seats"
                           autofocus value="${specialty.numberOfSeats}"
                           required pattern="[0-9]{1,6}">
                    <c:if test="${not empty parameters and empty parameters['number_of_seats'] }">
                        <small><label class="alert-danger"> <fmt:message key="editspecialty.editerror"/></label></small>
                    </c:if>
                </div>

                <c:choose>
                    <c:when test="${not empty specialty}">
                        <button class="btn btn-primary btn-block" type="submit"><fmt:message
                                key="editspecialty.editbutton"/></button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-primary btn-block" type="submit"><fmt:message
                                key="editspecialty.addbutton"/></button>
                    </c:otherwise>
                </c:choose>
            </form>
        </div>
    </section>
</main>
<c:import url="//jsp//fragment//footer.jsp"/>
</body>
</html>
