<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
<c:import url="//jsp//fragment//header.jsp"/>
    <main class="page login-page">
        <section class="clean-block clean-form dark">
            <div class="container">
                <div class="block-heading">
                    <h2 class="text-info">Log In</h2>
                </div>
                <form name="loginForm" method="POST" action="projectServlet">
                    <input type="hidden" name="command" value="login"/>
                    <div class="form-group"><label for="email">Email</label><input class="form-control item"
                                                                                   type="email" id="email" name="email"></div>
                    <div class="form-group"><label for="password">Password</label><input class="form-control"
                                                                                         type="password" id="password" name="password"></div>
                    <button class="btn btn-primary btn-block" type="submit">Log In</button>
                </form>
                <p>${error_login_massage}</p>
            </div>
        </section>
    </main>
<c:import url="//jsp//fragment//footer.jsp"/>
</body>
</html>



