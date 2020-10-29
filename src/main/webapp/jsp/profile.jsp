<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="enrollee" scope="session" class="com.epam.enrollee.model.entity.Enrollee"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
</head>

<body>
<c:import url="//jsp//fragment//header.jsp"/>
<main class="page">
    <section class="main-block profile">
        <div class="container">
            <div class="main-information">
                <div class="text-info">
                    <h1 class="text-center">${enrollee.firstName} ${enrollee.lastName}</h1>
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
                                <div class="text-muted"> Chosen specialty:</div>
                            </div>
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
                                     style="width: 50%;"><span class="sr-only">80%</span>80%
                                </div>
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
<c:import url="//jsp//fragment//footer.jsp"/>
</body>

</html>
