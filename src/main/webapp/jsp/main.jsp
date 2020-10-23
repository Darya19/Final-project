<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main</title>
</head>
<body>
<c:import url="//jsp//fragment//header.jsp"/>
<h3>Welcome</h3>
<hr/>
${user}, hello!
<hr/>
<input type="hidden" name="command" value="log out">
<c:import url="//jsp//fragment//footer.jsp"/>
</body>
</html>
