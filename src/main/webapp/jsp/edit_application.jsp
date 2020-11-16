<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title><fmt:message key="editapplication.title"/></title>
</head>
<body>
    <c:import url="//jsp//fragment//header.jsp"/>
    <main class="page registration-page">
    <section class="clean-block clean-form dark">
    <div class="container">
    <div class="block-heading">
    <h2 class="text-info"><fmt:message key="editapplication.title"/></h2>
    </div>
    <form name="ApplicationForm" method="POST" action="projectServlet">
    <input type="hidden" name="command" value="new_application"/>
    <h6><fmt:message key="editapplication.faculty"/></h6>
    <div class="input-group mb-3">
        <select class="custom-select" id="faculty_id" name="faculty_id">
            <option selected value=""><fmt:message key="editapplication.choosefaculty"/></option>
            <c:forEach items="${faculties}" var="faculty">
                <option value="${faculty.facultyId}">${faculty.facultyName}</option>
            </c:forEach>
        </select>
    </div>
    <h6><fmt:message key="editapplication.specialty"/></h6>
    <div class="input-group mb-3">
        <select class="custom-select" id="specialty_id" name="specialty_id">
            <option selected value=""><fmt:message key="editapplication.choosespecialty"/></option>
            <c:forEach var="specialty" items="${specialties}">
                <option value="${specialty.specialtyId}">${specialty.specialtyName}</option>
            </c:forEach>
        </select>
        <c:if test="${not empty parameters and empty parameters['specialty_id'] }">
            <small><label class="alert-danger"> <fmt:message key="editapplication.wrongfaculty"/></label></small>
        </c:if>
    </div>
    <h5><fmt:message key="editapplication.tests"/></h5>
    <div class="form-row">
        <div class="col-7">
            <select class="custom-select" id="subject_1" name="subject_id_1">
                <option selected value=""><fmt:message key="editapplication.choosesubject"/></option>
                <c:forEach var="subject" items="${subjects}">
                    <option value="${subject.subjectId}">${subject.subjectName}</option>
                </c:forEach>
            </select>

        </div>
        <div class="col">
            <input type="text" class="form-control" placeholder="mark" id="mark_1" name="mark_1"
                   required pattern="[0-9]{1,3}" max="100" min="0">
        </div>
    </div>
    <div class="form-row">
        <div class="col-7">
            <select class="custom-select" id="subject_2" name="subject_id_2">
                <option selected value="0"><fmt:message key="editapplication.choosesubject"/></option>
                <c:forEach var="subject" items="${subjects}">
                    <option value="${subject.subjectId}">${subject.subjectName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col">
            <input type="text" class="form-control" placeholder="mark" id="mark_2" name="mark_2"
                   required pattern="[0-9]{1,3}" max="100" min="0">
        </div>
    </div>
    <div class="form-row">
        <div class="col-7">
            <select class="custom-select" id="subject_3" name="subject_id_3">
                <option selected value="0"><fmt:message key="editapplication.choosesubject"/></option>
                <c:forEach var="subject" items="${subjects}">
                    <option value="${subject.subjectId}">${subject.subjectName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col">
            <input type="text" class="form-control" placeholder="mark" id="mark_3" name="mark_3"
                   required pattern="[0-9]{1,3}" max="100" min="0">
        </div>
    </div>
    <div class="form-row">
        <div class="col-7">
            <select class="custom-select" id="subject_4" name="subject_id_4">
                <option selected value="0"><fmt:message key="editapplication.choosesubject"/></option>
                <c:forEach var="subject" items="${subjects}">
                    <option value="${subject.subjectId}">${subject.subjectName}</option>
                </c:forEach>
            </select>
            <c:if test="${not empty parameters and empty parameters['subject_id_4'] }">
                <small><label class="alert-danger"> <fmt:message
                        key="editapplication.wrongsubject"/></label></small>
            </c:if>
        </div>
        <div class="col">
            <input type="text" class="form-control" placeholder="mark" id="mark_4" name="mark_4"
                   required pattern="[0-9]{1,3}" max="100" min="0">
        </div>
    </div>
        <p></p>
        <p></p>
        <button class="btn btn-primary btn-block" type="submit"><fmt:message
                key="editapplication.submitbutton"/></button>
    </form>
    </div>
    </section>
    </main>
    <c:import url="//jsp//fragment//footer.jsp"/>
</body>
</html>
