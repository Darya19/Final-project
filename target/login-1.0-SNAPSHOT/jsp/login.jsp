<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Login - Brand</title>
    <link rel="stylesheet" href="/bootstrap/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
    <link rel="stylesheet" href="/bootstrap/css/smoothproducts.css">
</head>
<body>
<nav class="navbar navbar-light navbar-expand-lg fixed-top bg-white clean-navbar">
    <div class="container"><a class="navbar-brand logo" href="#"><em>NATIONAL UNIVERSITY</em></a><button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div
                class="collapse navbar-collapse" id="navcol-1">
            <ul class="nav navbar-nav ml-auto"></ul>
        </div>
    </div>
</nav>
    <main class="page login-page">
        <section class="clean-block clean-form dark">
            <div class="container">
                <div class="block-heading">
                    <h2 class="text-info">Log In</h2>
                </div>
                <form name="loginForm" method="POST" action="projectServlet">
                    <input type="hidden" name="command" value="login"/>
                    <div class="form-group"><label for="email">Email</label><input class="form-control item"
                                                                                   type="email" id="email"></div>
                    <div class="form-group"><label for="password">Password</label><input class="form-control"
                                                                                         type="password" id="password"></div>
                    <button class="btn btn-primary btn-block" type="submit">Log In</button>
                </form>
            </div>
        </section>
    </main>
    <footer class="page-footer dark">
        <div class="container">
            <div class="row">
                <div class="col-sm-3">
                    <h5>Get started</h5>
                    <ul>
                        <li><a href="#">Home</a></li>
                        <li><a href="#">Sign up</a></li>
                    </ul>
                </div>
                <div class="col-sm-3">
                    <h5>About us</h5>
                    <ul>
                        <li><a href="#">&nbsp;Faculty Information</a></li>
                        <li><a href="#">Contact us</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="footer-copyright">
            <p>© 2020 BNU</p>
        </div>
    </footer>
    <script src="/bootstrap/js/jquery.min.js"></script>
    <script src="/bootstrap/bootstrap/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.js"></script>
    <script src="/bootstrap/js/smoothproducts.min.js"></script>
    <script src="/bootstrap/js/theme.js"></script>
</body>
</html>



