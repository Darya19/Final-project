<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
</head>
<body>
<footer class="page-footer dark">
    <div class="container">
        <div class="row">
            <div class="col-sm-4">
                <h5><fmt:message key="footer.start"/></h5>
                <ul>
                    <li><a href="projectServlet?command=to_main_page"><fmt:message key="footer.home"/></a></li>
                    <p></p>
                        <li><a href="projectServlet?command=to_login_page"><fmt:message key="footer.login"/></a>
                        </li>
                </ul>
            </div>
            <div class="col-sm-4">
                <h5><fmt:message key="footer.resources"/></h5>
                <ul>
                    <li><a href="projectServlet?command=to_faculties_page"><fmt:message key="footer.faculties"/></a>
                    </li>
                    <p></p>
                    <c:if test="${role eq 'USER'}">
                        <li><a href="projectServlet?command=to_profile_page"><fmt:message key="footer.profile"/></a>
                        </li>
                    </c:if>
                </ul>
            </div>
            <div class="col-sm-4">
                <h5><fmt:message key="footer.contact"/></h5>
                <ul>
                    <li><a><fmt:message key="footer.address"/></a></li>
                </ul>
                <ul>
                    <li><a><fmt:message key="footer.phonenumber"/></a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="footer-copyright">
        <p><fmt:message key="footer.end"/></p>
    </div>
</footer>
<script src="/bootstrap/js/jquery.min.js"></script>
<script src="/bootstrap/bootstrap/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.js"></script>
<script src="/bootstrap/js/smoothproducts.min.js"></script>
<script src="/bootstrap/js/theme.js"></script>

</body>
</html>
