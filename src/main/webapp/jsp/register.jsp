<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<c:import url="//jsp//fragment//header.jsp"/>
<main class="page registration-page">
    <section class="clean-block clean-form dark">
        <div class="container">
            <div class="block-heading">
                <h2 class="text-info">Registration</h2>
            </div>
            <<form name="RegistrationForm" method="POST" action="projectServlet">
            <input type="hidden" name="command" value="register"/>
                <div class="form-group"><label for="name">First Name</label><input class="form-control item" type="text" id="name"name="name"></div>
                <div class="form-group"><label for="name">Last Name</label><input class="form-control item" type="text" id="last name"name="last name"></div>
                <div class="form-group"><label for="name">Surname</label><input class="form-control item" type="text" id="surname" name="surname"></div>
                <div class="form-group"><label for="name">Passport</label><input class="form-control item" type="text" id="passport" name="passport"></div>
                <div class="form-group"><label for="name">Personal number</label><input class="form-control item" type="text" id="personal number" name="personal number"></div>
                <div class="form-group"><label for="name">Country</label><input class="form-control item" type="text" id="country" name="country"></div>
                <div class="form-group"><label for="name">City</label><input class="form-control item" type="text" id="city" name="city"></div>
                <div class="form-row">
                    <div class="col-7">
                        <label for="street">Street</label>

                        <input type="text" class="form-control" id="street" name="street">
                    </div>
                    <div class="col">
                        <label for="house">House</label>
                        <input type="text" class="form-control" id="house" name="house">
                    </div>
                    <div class="col">
                        <label for="flat">Flat</label>
                        <input type="text" class="form-control" id="flat" name="flat">
                    </div>
                </div>
                <p></p>
                <h6>Phone number</h6>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon1">+375</span>
                    </div>
                    <input type="text" class="form-control" placeholder="(29)1234567" aria-label="phone number" aria-describedby="basic-addon1" id="phone number" name="phone number">
                </div>
                <h6>Faculty</h6>
                <div class="input-group mb-3">
                    <select class="custom-select" id="inputGroupSelect01" name="chosen faculty">
                        <option selected>Choose faculty</option>
                        <option value="1">Information technologies faculty</option>
                        <option value="2">Historical faculty</option>
                        <option value="3">Philological</option>
                    </select>
                </div>
                <h6>Specialty</h6>
                    <div class="input-group mb-3">
                        <select class="custom-select" id="inputGroupSelect02" name="chosen specialty">
                            <option selected>Choose specialty</option>
                            <option value="1">information systems and technologies</option>
                            <option value="2">Computer security</option>
                            <option value="3">Documentation</option>
                            <option value="4">History</option>
                            <option value="5">Russian philology</option>
                            <option value="6">Belorussian philology</option>
                        </select>
                    </div>
                <h5>Tests:</h5>
                <div class="form-row">
                    <div class="col-7">
                        <select class="custom-select" id="subject1" name="subject1">
                            <option selected>Choose subject</option>
                            <option value="1">Math</option>
                            <option value="2">Physics</option>
                            <option value="3">The World History</option>
                            <option value="4">History of Belarus</option>
                            <option value="5">Russian language</option>
                            <option value="6">Belorussian language</option>
                            <option value="7">Russian literature</option>
                        </select>
                    </div>
                    <div class="col">
                        <input type="text" class="form-control" placeholder="mark"id="mark1" name="mark1">
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-7">
                        <select class="custom-select" id="subject2" name="subject2">
                            <option selected>Choose subject</option>
                            <option value="1">Math</option>
                            <option value="2">Physics</option>
                            <option value="3">The World History</option>
                            <option value="4">History of Belarus</option>
                            <option value="5">Russian language</option>
                            <option value="6">Belorussian language</option>
                            <option value="7">Russian literature</option>
                        </select>
                    </div>
                    <div class="col">
                        <input type="text" class="form-control" placeholder="mark" id="mark2" name="mark2">
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-7">
                        <select class="custom-select" id="subject3" name="subject3">
                            <option selected>Choose subject</option>
                            <option value="1">Math</option>
                            <option value="2">Physics</option>
                            <option value="3">The World History</option>
                            <option value="4">History of Belarus</option>
                            <option value="5">Russian language</option>
                            <option value="6">Belorussian language</option>
                            <option value="7">Russian literature</option>
                        </select>
                    </div>
                    <div class="col">
                        <input type="text" class="form-control" placeholder="mark" id="mark3" name="mark3">
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-7">
                        <p>Certificate</p></div>
                    <div class="col">
                        <input type="text" class="form-control" placeholder="mark" id="certificate mark" name="certificate mark">
                    </div>
                </div>
                <div class="form-group"><label for="password">Password</label><input class="form-control item" type="password" id="password"></div>
                <div class="form-group"><label for="password">Repeat password</label><input class="form-control item" type="password" id="repeat password"></div>
                <div class="form-group"><label for="email">Email</label><input class="form-control item" type="email" id="email">
                </div><button class="btn btn-primary btn-block" type="submit">Sign Up</button></form>
        </div>
    </section>
</main>
<c:import url="//jsp//fragment//footer.jsp"/>
</body>
</html>
