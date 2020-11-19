<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title><fmt:message key="editprofile.title"/></title>
</head>
<body>
<c:import url="/jsp/fragment/header.jsp"/>
<main class="page edit-profile-page">
    <section class="clean-block clean-form dark">
        <div class="container">
            <c:if test="${edit_part eq 'edit_specialty'}">
                <div class="block-heading">
                    <h2 class="text-info"><fmt:message key="editprofile.editspecialty"/></h2>
                </div>
                <form name="EditionSpecialtyForm" method="POST" action="projectServlet">
                    <input type="hidden" name="command" value="edit_enrollee_specialty"/>
                    <div class="input-group mb-3">
                        <select class="custom-select" id="specialty_id" name="specialty_id">
                            <option selected value=""><fmt:message key="editprofile.editspecialty"/></option>
                            <c:forEach var="specialty" items="${specialties}">
                                <option value="${specialty.specialtyId}">${specialty.specialtyName}</option>
                            </c:forEach>
                        </select>
                        <c:if test="${not empty parameters}">
                            <small><label class="alert-danger"> <fmt:message
                                    key="register.registererror"/></label></small>
                        </c:if>
                    </div>
                    <div class="form-group">
                        <button class="btn btn-primary btn-block" type="submit"><fmt:message
                                key="editprofile.edit"/></button>
                    </div>
                </form>
            </c:if>
            <c:if test="${edit_part eq 'edit_marks'}">
                <div class="block-heading">
                    <h2 class="text-info"><fmt:message key="editprofile.editmarks"/></h2>
                </div>
                <form name="EditionTestsResultForm" method="POST" action="projectServlet">
                    <input type="hidden" name="command" value="edit_marks"/>
                    <div class="form-row">
                        <c:forEach items="${register.testsSubjectsAndMarks}" var="register">
                            <div class="col-7">
                                <input class="form-control" type="text" placeholder="${register.key.subjectName}"
                                       readonly>
                            </div>

                            <div class="col">
                                <input type="text" class="form-control" placeholder="mark" id="mark_4"
                                       name="${register.key.subjectId}" required value="${register.value}">
                            </div>
                        </c:forEach>
                        <c:if test="${not empty parameters}">
                            <small><label class="alert-danger"> <fmt:message
                                    key="editprofile.editerror"/></label></small>
                        </c:if></div>
                    <div class="form-group">
                        <button class="btn btn-primary btn-block" type="submit"><fmt:message
                                key="editprofile.edit"/></button>
                    </div>
                </form>
            </c:if>
            <c:if test="${edit_part eq 'edit_personal_information'}">
                <div class="block-heading">
                    <h2 class="text-info"><fmt:message key="editprofile.editpersonalinformation"/></h2>
                </div>
                <form name="EditionPersonalNumberForm" method="POST" action="projectServlet">
                    <input type="hidden" name="command" value="edit_personal_information"/>
                    <div><fmt:message key="editprofile.firstname"/>
                        <input class="form-control" type="text"
                               id="first_name" name="first_name" required
                               value="${enrollee.firstName}">
                    </div>
                    <div><fmt:message key="editprofile.lastname"/>
                        <input class="form-control" type="text"
                               id="last_name" name="last_name" required
                               value="${enrollee.lastName}">
                    </div>
                    <div><fmt:message key="editprofile.middlename"/>
                        <input class="form-control" type="text"
                               id="middle_name" name="middle_name"
                               required value="${enrollee.middleName}"></div>
                    <div><fmt:message key="editprofile.passport"/>
                        <input class="form-control" type="text"
                               id="passport_series_and_number"
                               name="passport_series_and_number" required
                               value="${passport.passportSeriesAndNumber}"></div>
                    <div><fmt:message key="editprofile.personalnumber"/>
                        <input class="form-control" type="text"
                               id="personal_number"
                               name="personal_number" required
                               value="${passport.personalNumber}">
                        <c:if test="${not empty parameters}">
                            <small><label class="alert-danger"> <fmt:message
                                    key="editprofile.editerror"/></label></small>
                        </c:if></div>
                    <div class="form-group">
                        <button class="btn btn-primary btn-block" type="submit"><fmt:message
                                key="editprofile.edit"/></button>
                    </div>
                </form>
            </c:if>
        </div>
    </section>
</main>
<c:import url="/jsp/fragment/footer.jsp"/>
</body>
</html>