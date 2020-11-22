<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title><fmt:message key="adminspecialties.title"/></title>
</head>
<body>
<c:import url="/jsp/fragment/header.jsp"/>
<main class="page page-information">
    <section class="lean-block clean-testimonials dark">
        <div class="container">
            <div class="block-heading">
                <p></p>
                <h2 class="text-center"><fmt:message key="adminspecialties.title"/></h2>
            </div>
            <c:if test="${has_application}">
                <small>
                    <label class="alert-danger"> <fmt:message key="adminspecialties.deletederror"/></label>
                </small>
            </c:if>
            <p></p>
            <div class="col-md-10 right-button">
                <button class="btn btn-outline-primary btn-sm"
                        type="button"
                        onclick='location.href = "${pageContext.request.contextPath}/projectServlet?command=to_edit_specialty_page"'>
                    <fmt:message key="adminspecialties.addbutton"/>
                </button>
            </div>
            <table class="table">
                <caption><fmt:message key="adminspecialties.note"/></caption>
                <thead class="thead-light">
                <tr>
                    <th scope="col"></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${specialties}" var="specialty">
                    <tr>
                        <td>${specialty.specialtyName}</td>
                        <td>${specialty.recruitment}
                            <button class="btn btn-outline-primary btn-sm"
                                    type="button"
                                    onclick='location.href = "${pageContext.request.contextPath}/projectServlet?command=change_recruitment&specialty_id=${specialty.specialtyId}&recruitment=${specialty.recruitment}"'>
                                <fmt:message key="adminspecialties.recrutmentbutton"/>
                            </button>
                        </td>
                        <c:if test="${specialty.recruitment eq ('OPENED')}">
                            <td>
                                <button class="btn btn-outline-primary btn-sm"
                                        type="button"
                                        onclick='location.href = "${pageContext.request.contextPath}/projectServlet?command=to_applications_page&specialty_id=${specialty.specialtyId}"'>
                                    <fmt:message key="adminspecialties.showbutton"/>
                                </button>
                            </td>

                            <td>
                                <div>
                                    <button class="btn btn-outline-primary btn-sm"
                                            type="button"
                                            onclick='location.href = "${pageContext.request.contextPath}/projectServlet?command=to_edit_specialty_page&specialty_id=${specialty.specialtyId}"'>
                                        <fmt:message key="adminspecialties.editbutton"/></button>
                                </div>
                            </td>
                        </c:if>
                        <td>
                            <div>
                                <button class="btn btn-outline-primary btn-sm"
                                        type="button"
                                        onclick='location.href = "${pageContext.request.contextPath}/projectServlet?command=delete_specialty&specialty_id=${specialty.specialtyId}"'>
                                    <fmt:message key="adminspecialties.deletebutton"/></button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <p></p>
            <div class="col-md-10 right-button">
                <button class="btn btn-primary btn-sm "
                        type="button"
                        onclick='location.href = "${pageContext.request.contextPath}/projectServlet?command=to_admin_faculties_page"'>
                    <fmt:message key="adminspecialties.backbutton"/>
                </button>
            </div>
        </div>
    </section>
</main>
<c:import url="/jsp/fragment/footer.jsp"/>
</body>
</html>