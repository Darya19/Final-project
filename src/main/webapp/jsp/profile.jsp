<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title><fmt:message key="profile.title"/></title>
</head>

<body>
<c:import url="/jsp/fragment/header.jsp"/>
<main class="page">
    <section class="main-block profile">
        <div class="container">
            <div class="main-information">
                <div class="text-info">
                    <p></p>
                    <p></p>
                    <h1 class="text-center">${enrollee.firstName} ${enrollee.middleName} ${enrollee.lastName}</h1>
                </div>
                <div class="text-center">
                    <h6 class="text-muted"><fmt:message key="profile.applicationstatus"/></h6>
                    <h4>${enrollee.applicationStatus}</h4>
                    <h6 class="text-muted"><fmt:message key="profile.personalnumber"/></h6>
                    <h5>${passport.personalNumber}</h5>
                    <h6 class="text-muted"><fmt:message key="profile.passportseriesandnumber"/></h6>
                    <h5>${passport.passportSeriesAndNumber}</h5>
                    <button class="btn btn-outline-primary btn-sm" type="button"
                            onclick='location.href = "projectServlet?command=to_edit_profile_page&edit_part=edit_personal_information"'>
                        <fmt:message key="profile.edit"/></button>
                </div>
            </div>
        </div>
    </section>
    <p></p>
    <section class="faculty-block profile">
        <div class="container">
            <div class="row">
                <div class="col-md-8">
                    <div class="shadow p-3 mb-5 bg-white rounded"><span class="border-bottom border-light"></span>
                        <div class="faculty info">
                            <h5 class="text-muted"><fmt:message key="profile.facultyinformation"/></h5>
                            <div class="row">
                                <div class="col-4">
                                    <div class="text-muted"><fmt:message key="profile.chosenfaculty"/></div>
                                </div>
                                <div class="col-4">${faculty.facultyName}</div>
                                <div class="col-2">

                                </div>
                            </div>
                        </div>
                        <p></p>
                        <div class="row">
                            <div class="col-4">
                                <div class="text-muted"><fmt:message key="profile.chosenspecialty"/></div>
                            </div>
                            <div class="col-4">${specialty.specialtyName}</div>
                            <div class="col-2">
                            </div>
                        </div>
                        <c:if test="${enrollee.applicationStatus eq 'ARCHIVED'}">
                            <button class="btn btn-outline-primary btn-sm" type="button"
                                    onclick='location.href = "projectServlet?command=to_register_page&type=add_application"'>
                                <fmt:message key="profile.add"/></button>
                        </c:if>
                        <c:if test="${enrollee.applicationStatus eq 'CONSIDERED'}">
                            <button class="btn btn-outline-primary btn-sm" type="button"
                                    onclick='location.href = "projectServlet?command=to_edit_profile_page&edit_part=edit_specialty"'>
                                <fmt:message key="profile.edit"/></button>
                        </c:if>

                    </div>
                </div>
                <div class="col-md-3">
                    <div class="shadow p-3 mb-5 bg-white rounded">
                        <div class="subjects marks-info-card">
                            <h4 class="text-muted"><fmt:message key="profile.results"/></h4>
                            <c:forEach items="${register.testsSubjectsAndMarks}" var="register">
                                <h7>${register.key.subjectName}</h7>
                                <div class="progress">
                                    <div class="progress-bar" aria-valuenow="${register.value}" aria-valuemin="0"
                                         aria-valuemax="100"
                                         style="width: ${register.value}%;"><span
                                            class="sr-only"></span>${register.value}
                                    </div>
                                </div>
                            </c:forEach>
                            <h7><fmt:message key="profile.total"/></h7>
                            <div class="progress">
                                <div class="progress-bar" aria-valuenow="${register.averageMark}" aria-valuemin="0"
                                     aria-valuemax="100"
                                     style="width: ${register.averageMark}%;"><span
                                        class="sr-only"></span>${register.averageMark}
                                </div>
                            </div>
                        </div>
                        <p></p>
                        <c:if test="${enrollee.applicationStatus eq 'CONSIDERED'}">
                            <button class="btn btn-outline-primary btn-sm" type="button"
                                    onclick='location.href = "projectServlet?command=to_edit_profile_page&edit_part=edit_marks"'>
                                <fmt:message key="profile.edit"/></button>
                        </c:if>
                    </div>
                </div>
            </div>
            <ul>
                <li class="nav-item" role="presentation"><a class="nav-link active"
                                                            href="projectServlet?command=delete_profile"> <fmt:message
                        key="profile.deleteprofile"/></a></li>
            </ul>
        </div>
    </section>
</main>
<c:import url="/jsp/fragment/footer.jsp"/>
</body>

</html>
