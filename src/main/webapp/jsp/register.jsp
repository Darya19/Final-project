<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title><fmt:message key="register.title"/></title>
</head>
<body>
<c:import url="//jsp//fragment//header.jsp"/>
<main class="page registration-page">
    <section class="clean-block clean-form dark">
        <div class="container">
            <div class="block-heading">
                <h2 class="text-info"><fmt:message key="register.formname"/></h2>
            </div>
            <form name="RegistrationForm" method="POST" action="projectServlet">
                <input type="hidden" name="command" value="register"/>
                <div class="form-group"><label for="first_name"><fmt:message key="register.firstname"/> </label>
                    <input class="form-control item" type="text" id="first_name" name="first_name"
                           autofocus required value="${parameters.get("first_name")}">
                    <c:if test="${not empty parameters and empty parameters['first_name'] }">
                        <small><label class="alert-danger"> <fmt:message key="register.registererror"/></label></small>
                    </c:if>
                </div>

                <div class="form-group"><label for="last_name"><fmt:message key="register.lastname"/> </label>
                    <input class="form-control item" type="text" id="last_name" name="last_name"
                           required value="${parameters.get("last_name")}">
                    <c:if test="${not empty parameters and empty parameters['last_name'] }">
                        <small><label class="alert-danger"> <fmt:message key="register.registererror"/></label></small>
                    </c:if>
                </div>
                <div class="form-group"><label for="middle_name"><fmt:message key="register.middlename"/> </label>
                    <input class="form-control item" type="text" id="middle_name" name="middle_name"
                           required value="${parameters.get("middle_name")}">
                    <c:if test="${not empty parameters and empty parameters['middle_name'] }">
                        <small><label class="alert-danger"> <fmt:message key="register.registererror"/></label></small>
                    </c:if>
                </div>
                <div class="form-group"><label for="passport_series_and_number"><fmt:message key="register.passport"/> </label>
                    <input class="form-control item" type="text" id="passport_series_and_number" name="passport_series_and_number"
                           required value="${parameters.get("passport_series_and_number")}">
                    <c:if test="${not empty parameters and empty parameters['passport_series_and_number'] }">
                        <small><label class="alert-danger"> <fmt:message key="register.registererror"/></label></small>
                    </c:if>
                </div>
                <div class="form-group"><label for="personal_number"><fmt:message
                        key="register.personalnumber"/> </label>
                    <input class="form-control item" type="text" id="personal_number" name="personal_number"
                           required value="${parameters.get("personal_number")}">
                    <c:if test="${not empty parameters and empty parameters['personal_number'] }">
                        <small><label class="alert-danger"> <fmt:message key="register.registererror"/></label></small>
                    </c:if></div>

                <h6><fmt:message key="register.faculty"/></h6>
                <div class="input-group mb-3">
                    <select class="custom-select" id="faculty_id" name="faculty_id">
                        <option selected value="0">choose faculty</option>
                        <c:forEach items="${faculties}" var="faculty">
                            <option value="${faculty.facultyId}">${faculty.facultyName}</option>
                        </c:forEach>
                    </select>
                </div>
                <h6><fmt:message key="register.specialty"/></h6>
                <div class="input-group mb-3">
                    <select class="custom-select" id="specialty_id" name="specialty_id">
                        <option selected value="0">choose specialty</option>
                        <c:forEach var="specialty" items="${specialties}">
                            <option value="${specialty.specialtyId}">${specialty.specialtyName}</option>
                        </c:forEach>
                    </select>
                    <c:if test="${not empty parameters and empty parameters['specialty_id'] }">
                        <small><label class="alert-danger"> <fmt:message key="register.wrongfaculty"/></label></small>
                    </c:if>
                </div>
                <h5><fmt:message key="register.tests"/></h5>
                <div class="form-row">
                    <div class="col-7">
                        <select class="custom-select" id="subject_1" name="subject_id_1">
                            <option selected value="0">choose subject</option>
                            <c:forEach var="subject" items="${subjects}">
                                <option value="${subject.subjectId}">${subject.subjectName}</option>
                            </c:forEach>
                        </select>

                    </div>
                    <div class="col">
                        <input type="text" class="form-control" placeholder="mark" id="mark_1" name="mark_1">
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-7">
                        <select class="custom-select" id="subject_2" name="subject_id_2">
                            <option selected value="0">choose subject</option>
                            <c:forEach var="subject" items="${subjects}">
                                <option value="${subject.subjectId}">${subject.subjectName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col">
                        <input type="text" class="form-control" placeholder="mark" id="mark_2" name="mark_2">
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-7">
                        <select class="custom-select" id="subject_3" name="subject_id_3">
                            <option selected value="0">choose subject</option>
                            <c:forEach var="subject" items="${subjects}">
                                <option value="${subject.subjectId}">${subject.subjectName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col">
                        <input type="text" class="form-control" placeholder="mark" id="mark_3" name="mark_3">
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-7">
                        <select class="custom-select" id="subject_4" name="subject_id_4">
                            <option selected value="0">choose subject</option>
                            <c:forEach var="subject" items="${subjects}">
                                <option value="${subject.subjectId}">${subject.subjectName}</option>
                            </c:forEach>
                        </select>
                        <c:if test="${not empty parameters and empty parameters['subject_id_4'] }">
                            <small><label class="alert-danger"> <fmt:message key="register.wrongsubject"/></label></small>
                        </c:if>
                    </div>
                    <div class="col">
                        <input type="text" class="form-control" placeholder="mark" id="mark_4"
                               name="mark_4">
                    </div>
                </div>
                <div class="form-group"><label for="password"><fmt:message key="register.password"/> </label>
                    <input class="form-control item" type="password" id="password" name="password"
                           required value="${parameters.get("password")}"></div>
                <div class="form-group"><label for="password"><fmt:message key="register.repeatedpassword"/></label>
                    <input class="form-control item" type="password" id="repeated_password" name="repeated_password"
                           required value="${parameters.get("repeated_password")}">
                    <c:if test="${not empty parameters and empty parameters['repeated_password'] }">
                        <small><label class="alert-danger"> <fmt:message key="register.wrongpassword"/></label></small>
                    </c:if>
                </div>
                <div class="form-group"><label for="email"><fmt:message key="register.email"/></label>
                    <input class="form-control item" type="email" id="email" name="email"
                           required value="${parameters.get("email")}">
                    <c:if test="${not empty parameters and empty parameters['email'] }">
                        <small><label class="alert-danger"> <fmt:message key="register.wrongemail"/></label></small>
                    </c:if>
                </div>
                <button class="btn btn-primary btn-block" type="submit"><fmt:message key="register.submitbutton"/></button>
            </form>
        </div>
    </section>
</main>
<c:import url="//jsp//fragment//footer.jsp"/>
</body>
</html>
