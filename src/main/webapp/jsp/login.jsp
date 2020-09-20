<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 03.09.2020
  Time: 0:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form name="loginForm" method="POST" action="projectServlet">
    <input type="hidden" name="command" value="login" />
    Login:<br/>
    <input type="text" name="login" value=""/>
    <br/>Password:<br/>
    <input type="password" name="password" value=""/>
    <br/>
    ${errorLoginMessage}
    <br/>
    ${wrongAction}
    <br/>
    <input type="submit" value="Log in"/>
</form><hr/>
</body>
</html>
