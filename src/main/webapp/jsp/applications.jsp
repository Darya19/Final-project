<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title><fmt:message key="applications.title"/></title>
</head>
<body>
<c:import url="//jsp//fragment//header.jsp"/>
<main class="page page-information">
    <section class="lean-block clean-testimonials dark">
        <div class="container">
            <div class="block-heading">
                <p></p>
                <h2 class="text-center"><fmt:message key="applications.title"/></h2>
                <div class="row align-items-center">
                    <div class="col-md-6"><fmt:message key="applications.specialtyname"/> ${specialty.specialtyName}</div>
                    <div class="col-md-6"><fmt:message key="applications.numberofseats"/>${specialty.numberOfSeats}</div>
            </div>
                <p></p>
            </div>
            <c:if test="${is_changed}">
                <small>
                    <label class="alert-danger"> <fmt:message key="applications.error"/></label>
                </small>
            </c:if>
            <p></p>
            </div>
            <c:if test="${is_changed}">
                <small>
                    <label class="alert-danger"> <fmt:message key="applications.acceptderror"/></label>
                </small>
            </c:if>
            <p></p>
            <table class="table">
                <thead class="thead-light">
                <tr>
                    <th scope="col"></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
               <ctg:application-pagination/>
            </table>
            <p></p>
            <div class="right-button">
                <button class="btn btn-primary btn-sm "
                        type="button"
                        onclick='location.href = "projectServlet?command=to_admin_specialties_page&faculty_id=${faculty_id}"'>
                    <fmt:message key="applications.backbutton"/>
                </button></div>
        </div>
    </section>
</main>
<c:import url="//jsp//fragment//footer.jsp"/>
</body>
</html>
