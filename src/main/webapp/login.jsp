<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Restaurant &#183; Login</title>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/login_style.css">
    <script src="bootstrap/js/bootstrap.bundle.min.js"
            integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
            crossorigin="anonymous"></script>
    <style>
        <%@include file="bootstrap/css/bootstrap.min.css"%>
        <%@include file="css/login_style.css"%>
    </style>
</head>

<body>
<div class="py-3">
    <header>
        <div class="d-flex flex-column flex-md-row align-items-center pb-3 mb-4 border-bottom">
            <a href="<c:url value="/"/>" class="d-flex align-items-center text-dark text-decoration-none">
                <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" class="me-2" viewBox="0 0 118 94"
                     role="img">
                    <title>Restaurant</title>
                    <path fill-rule="evenodd" clip-rule="evenodd"
                          d="M24.509 0c-6.733 0-11.715 5.893-11.492 12.284.214 6.14-.064 14.092-2.066 20.577C8.943 39.365 5.547 43.485 0 44.014v5.972c5.547.529 8.943 4.649 10.951 11.153 2.002 6.485 2.28 14.437 2.066 20.577C12.794 88.106 17.776 94 24.51 94H93.5c6.733 0 11.714-5.893 11.491-12.284-.214-6.14.064-14.092 2.066-20.577 2.009-6.504 5.396-10.624 10.943-11.153v-5.972c-5.547-.529-8.934-4.649-10.943-11.153-2.002-6.484-2.28-14.437-2.066-20.577C105.214 5.894 100.233 0 93.5 0H24.508zM80 57.863C80 66.663 73.436 72 62.543 72H44a2 2 0 01-2-2V24a2 2 0 012-2h18.437c9.083 0 15.044 4.92 15.044 12.474 0 5.302-4.01 10.049-9.119 10.88v.277C75.317 46.394 80 51.21 80 57.863zM60.521 28.34H49.948v14.934h8.905c6.884 0 10.68-2.772 10.68-7.727 0-4.643-3.264-7.207-9.012-7.207zM49.948 49.2v16.458H60.91c7.167 0 10.964-2.876 10.964-8.281 0-5.406-3.903-8.178-11.425-8.178H49.948z"
                          fill="currentColor"></path>
                </svg>
                <span class="fs-4">Restaurant</span>
            </a>

            <nav class="d-inline-flex mt-2 mt-md-0 ms-md-auto">
                <a class="me-3 py-2 text-dark text-decoration-none" href="<c:url value="/home"/>">Menu</a>
                <a class="me-3 py-2 text-dark text-decoration-none" href="about.jsp">About</a>
                <a class="me-3 py-2 text-dark text-decoration-none" href="delivery.jsp">Delivery</a>
                <a class="me-3 py-2 text-dark text-decoration-none" href="support.jsp">Support</a>
                <div class="me-3 text-dark text-decoration-none">
                    <button type="button" class="btn btn-outline-primary" onclick="function cart() {
                      location.href = '/cart';
                    }cart()">
                        <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="black" class="bi bi-basket"
                             viewBox="0 0 16 16">
                            <title>Basket</title>
                            <path d="M5.757 1.071a.5.5 0 0 1 .172.686L3.383 6h9.234L10.07 1.757a.5.5 0 1 1 .858-.514L13.783 6H15a1 1 0 0 1 1 1v1a1 1 0 0 1-1 1v4.5a2.5 2.5 0 0 1-2.5 2.5h-9A2.5 2.5 0 0 1 1 13.5V9a1 1 0 0 1-1-1V7a1 1 0 0 1 1-1h1.217L5.07 1.243a.5.5 0 0 1 .686-.172zM2 9v4.5A1.5 1.5 0 0 0 3.5 15h9a1.5 1.5 0 0 0 1.5-1.5V9H2zM1 7v1h14V7H1zm3 3a.5.5 0 0 1 .5.5v3a.5.5 0 0 1-1 0v-3A.5.5 0 0 1 4 10zm2 0a.5.5 0 0 1 .5.5v3a.5.5 0 0 1-1 0v-3A.5.5 0 0 1 6 10zm2 0a.5.5 0 0 1 .5.5v3a.5.5 0 0 1-1 0v-3A.5.5 0 0 1 8 10zm2 0a.5.5 0 0 1 .5.5v3a.5.5 0 0 1-1 0v-3a.5.5 0 0 1 .5-.5zm2 0a.5.5 0 0 1 .5.5v3a.5.5 0 0 1-1 0v-3a.5.5 0 0 1 .5-.5z"></path>
                        </svg>
                        <c:choose>
                            <c:when test="${sessionScope.productsInBucket != null && sessionScope.productsInBucket.size() > 0}">
                                <span class="badge bg-success">
                                    <c:out value="${sessionScope.productsInBucket.values().stream().reduce(0, (x, y) -> x + y)}"/>
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge bg-secondary">
                                    <c:out value="0"/>
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </button>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn btn-primary dropdown-toggle" data-bs-toggle="dropdown"
                            aria-expanded="false">
                        Account
                    </button>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="">
                            <c:choose>
                                <c:when test="${sessionScope.user != null}">
                                    <strong>
                                        <c:out value="You are logged as ${sessionScope.user.name}"/>
                                    </strong>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="You are not logged"/>
                                </c:otherwise>
                            </c:choose>
                        </a></li>
                        <li>
                            <c:choose>
                                <c:when test="${sessionScope.user != null}">
                                    <strong>
                                        <a class="dropdown-item" href="<c:url value="/account"/>">Account</a>
                                    </strong>
                                </c:when>
                                <c:otherwise>
                                    <a class="dropdown-item disabled" href="" tabindex="-1"
                                       aria-disabled="true">Account</a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                        <li>
                            <hr class="dropdown-divider">
                        </li>
                        <li>
                            <c:choose>
                                <c:when test="${sessionScope.user != null}">
                                    <a class="dropdown-item" href="<c:url value="/signOut"/>"><strong>Sign out</strong></a>
                                </c:when>
                                <c:otherwise>
                                    <a class="dropdown-item" href=""><strong>Sign in</strong></a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
    </header>

    <main>
        <div class="bg-light rounded-3 mx-auto text-center">
            <div class="container-fluid py-2 mb-2">
                <h6 class="display-6">Please login or create an account</h6>
            </div>
        </div>

        <div class="container" id="container">
            <div class="form-container sign-up-container">
                <form action="register" method="post">
                    <h2>Create Account</h2>
                    <label>
                        <input type="text" placeholder="Name" name="name" required/>
                    </label>
                    <label>
                        <input type="text" placeholder="Phone number" name="phoneNumber" required minlength="10"/>
                    </label>
                    <label>
                        <input type="email" placeholder="Email" name="email" required minlength="6"/>
                    </label>
                    <label>
                        <input type="password" placeholder="Password" name="password" required minlength="8"/>
                    </label>
                    <button type="submit">Sign Up</button>
                </form>
            </div>
            <div class="form-container sign-in-container">
                <form action="login" method="post">
                    <h2>Sign in</h2>
                    <label>
                        <input type="email" placeholder="Email" name="email" required minlength="6"/>
                    </label>
                    <label>
                        <input type="password" placeholder="Password" name="password" required minlength="8"/>
                    </label>
                    <button type="submit">Sign In</button>
                </form>
            </div>

            <div class="overlay-container">
                <div class="overlay">
                    <div class="overlay-panel overlay-left">
                        <h1>Welcome Back!</h1>
                        <p>To continue, please login with your personal details</p>
                        <button class="ghost" id="signIn">Sign In</button>
                    </div>
                    <div class="overlay-panel overlay-right">
                        <h1>Hello, Friend!</h1>
                        <p>Enter your personal details and start journey with us.</p>
                        <p><em>You need to confirm your email address after registration.</em></p>
                        <button class="ghost" id="signUp">Sign Up</button>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <footer class="footer mt-4 py-3 bg-light border-top text-center">
        <span class="text-muted"> &copy; 2021 Restaurant.</span>
    </footer>
</div>
</body>

<script>
    const signUpButton = document.getElementById('signUp');
    const signInButton = document.getElementById('signIn');
    const container = document.getElementById('container');

    signUpButton.addEventListener('click', () => {
        container.classList.add("right-panel-active");
    });

    signInButton.addEventListener('click', () => {
        container.classList.remove("right-panel-active");
    });
</script>
</html>