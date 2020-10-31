<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title>Profile</title>
</head>

<body>
<c:import url="//jsp//fragment//header.jsp"/>
<main class="page">
    <section class="main-block profile">
        <div class="container">
            <div class="main-information">
                <div class="text-info">
                    <h1 class="text-center">${enrollee.firstName} ${enrollee.middleName} ${enrollee.lastName}</h1>
                </div>
                <div class="text-center">
                    <h6 class="text-muted"><fmt:message key="profile.applicationstatus"/></h6><h4>${enrollee.applicationStatus}</h4>
                    <h6 class="text-muted"><fmt:message key="profile.personalnumber"/></h6><h5>${passport.personalNumber}</h5>
                    <h6 class="text-muted"><fmt:message key="profile.passportseriesandnumber"/></h6><h5>${passport.passportSeriesAndNumber}</h5>
                </div>
            </div>
        </div>
    </section>
    <p></p>
    <section class="faulty-block profile">
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
                                    <button class="btn btn-primary btn-sm" type="button"><fmt:message key="profile.edit"/></button>
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
                                <button class="btn btn-primary btn-sm" type="button"><fmt:message key="profile.edit"/></button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="shadow p-3 mb-5 bg-white rounded">
                        <div class="subjects marks-info-card">
                            <h4 class="text-muted"><fmt:message key="profile.results"/></h4>
                             <c:forEach items="${enrollee_register}" var="register">
                            <h7>${register.key.subjectName}</h7>
                            <div class="progress">
                                <div class="progress-bar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"
                                     style="width: 50%;"><span class="sr-only">${register.value}</span>${register.value}
                                </div>
                            </div>
                             </c:forEach>
                            <h7><fmt:message key="profile.total"/></h7>
                            <div class="progress">
                                <div class="progress-bar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"
                                     style="width: 50%;"><span class="sr-only">80%</span></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
<c:import url="//jsp//fragment//footer.jsp"/>
</body>

</html>
