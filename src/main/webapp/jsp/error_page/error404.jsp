<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Error page</title>
</head>
<body>
<c:import url="//jsp//fragment//header.jsp"/>
Request from ${pageContext.errorData.requestURI} is failed
<br/>
Servlet name: ${pageContext.errorData.servletName}
<br/>
Status code: ${pageContext.errorData.statusCode}
<br/>
Exception: ${pageContext.exception}
<br/>
Message from exception: ${pageContext.exception.message}
<c:import url="//jsp//fragment//footer.jsp"/>
</body>
</html>
