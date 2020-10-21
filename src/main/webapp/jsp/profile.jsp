<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="enrollee" scope="session" class="com.epam.enrollee.model.entity.Enrollee"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Profile</title>
    <link rel="stylesheet" href="/bootstrap/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
    <link rel="stylesheet" href="/bootstrap/fonts/simple-line-icons.min.css">
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
                <h2 class="text-info">${enrollee.userName} ${enrollee.lastName}</h2>
                <p>application status: <strong>${enrollee.applicationStatus}</strong></p>
                <p>identification number: <strong>${enrollee.identificationNumber}</strong></p>
            </div>
        </div>
    </section>
    <section class="faulty-block profile">
        <div class="container">
            <div class="faculty-information">
                <div class="heading">
                    <h2 class="text-center">Faculty information</h2>
                </div>
                <div class="item">
                    <div class="row">
                        <div class="col-md-6">
                            <h3>Chosen faculty</h3>
                            <h4 class="organization">Amazing Co.</h4>
                        </div>
                        <div class="col-md-4"><span class="faculty">${faculty}</span></div>
                    </div>
                </div>
                <div class="item">
                    <div class="row">
                        <div class="col-4">
                            <h3>Chosen specialty</h3>
                        </div>
                        <div class="col-md-4"><span class="specialty">${specialty}</span></div>
                    </div>
                </div>
            </div>
            <div class="group">
                <div class="row">
                    <div class="col-md-6">
                        <div class="subjects marks-info-card">
                            <h2>Marks</h2>
                            <h3>${subject1}</h3>
                            <div class="progress">
                                <div class="progress-bar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"
                                     style="width: 100%;"><span class="sr-only">100%</span></div>
                            </div>
                            <h3>${subject2}</h3>
                            <div class="progress">
                                <div class="progress-bar" aria-valuenow="90" aria-valuemin="0" aria-valuemax="100"
                                     style="width: 90%;"><span class="sr-only">90%</span></div>
                            </div>
                            <h3>${subject3}</h3>
                            <div class="progress">
                                <div class="progress-bar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"
                                     style="width: 80%;"><span class="sr-only">80%</span></div>
                            </div>
                            <h3>certificate</h3>
                            <div class="progress">
                                <div class="progress-bar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"
                                     style="width: 80%;"><span class="sr-only">80%</span></div>
                            </div>
                            <h3>total</h3>
                            <div class="progress">
                                <div class="progress-bar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"
                                     style="width: 80%;"><span class="sr-only">80%</span></div>
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
<script src="/bootstrap/js/smoothproducts.min.js"></script>
<script src="/bootstrap/js/theme.js"></script>
</body>

</html>
