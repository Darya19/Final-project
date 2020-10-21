<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="enrollee" scope="session" class="com.epam.enrollee.model.entity.Enrollee"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Profile</title>
    <link rel="stylesheet" href="/bootstrap/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato:300,400,700">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
    <link rel="stylesheet" href="/bootstrap/fonts/simple-line-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/pikaday/1.6.1/css/pikaday.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
    <link rel="stylesheet" href="/bootstrap/css/smoothproducts.css">
</head>

<body>
<nav class="navbar navbar-light navbar-expand-lg fixed-top bg-white clean-navbar">
    <div class="container"><a class="navbar-brand logo" href="#">BELORUSSIAN NATIONAL UNIVERSITY</a>
        <button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span
                class="navbar-toggler-icon"></span></button>
        <div
                class="collapse navbar-collapse" id="navcol-1">
            <ul class="nav navbar-nav ml-auto"></ul>
            <button class="btn btn-primary" type="button">LOG OUT</button>
        </div>
    </div>
</nav>
<main class="page">
    <section class="main-block profile">
        <div class="container">
            <div class="main-information">
                <div class="text-info">
                <h1 class="text-center">${enrollee.userName} ${enrollee.lastName}</h1>
                </div>
                <div class="text-center">
                    <h6 class="text-muted">application status:</h6><h5>wait</h5>
                <h6 class="text-muted">identification number:</h6>
                <h5>by190</h5>
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
                          <h5 class="text-muted">Faculty information:</h5>
                                <div class="row">
                            <div class="col-4">
                                <div class="text-muted"> Chosen faculty:</div>
                            </div>
                                <div class="col-4">${faculty.facultyName}</div>
                                    <div class="col-2">
                                    <button class="btn btn-primary btn-sm" type="button">change</button>
                                </div>
                                </div>
                                </div>
                                <p></p>
                                <div class="row">
                                <div class="col-4">
                                    <div class="text-muted"> Chosen specialty:</div></div>
                                <div class="col-4">${faculty.facultyName}</div>
                                    <div class="col-2">
                                    <button class="btn btn-primary btn-sm" type="button">change</button>
                                    </div>
                                    </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="shadow p-3 mb-5 bg-white rounded">
                            <div class="subjects marks-info-card">
                                <h4 class="text-muted">Marks:</h4>
                                <h7>math</h7>
                                <div class="progress">
                                    <div class="progress-bar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"
                                         style="width: 50%;"><span class="sr-only">100%</span></div>
                                </div>
                                <h7>english</h7>
                                <div class="progress">
                                    <div class="progress-bar" aria-valuenow="90" aria-valuemin="0" aria-valuemax="100"
                                         style="width: 50%;"><span class="sr-only">90%</span></div>
                                </div>
                                <h7>science</h7>
                                <div class="progress">
                                    <div class="progress-bar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"
                                         style="width: 50%;"><span class="sr-only">80%</span></div>
                                </div>
                                <h7>certificate</h7>
                                <div class="progress">
                                    <div class="progress-bar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"
                                         style="width: 50%;"><span class="sr-only">80%</span>80%</div>
                                </div>
                                <h7>total</h7>
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
                    <li><a href="#">Faculty Information</a></li>
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
<script src="https://cdnjs.cloudflare.com/ajax/libs/pikaday/1.6.1/pikaday.min.js"></script>
<script src="/bootstrap/js/smoothproducts.min.js"></script>
<script src="/bootstrap/js/theme.js"></script>

</body>

</html>
