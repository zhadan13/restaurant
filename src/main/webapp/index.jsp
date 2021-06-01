<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" uri="http://localhost:8080/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Diamond Restaurant</title>
    <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/index_style.css">
    <link rel="stylesheet" type="text/css" href="css/button_style.css">
    <script src="bootstrap/js/bootstrap.bundle.min.js"
            integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
            crossorigin="anonymous"></script>
    <style>
        <%@include file="bootstrap/css/bootstrap.min.css"%>
        <%@include file="css/index_style.css"%>
        <%@include file="css/button_style.css"%>
    </style>
</head>

<body>
<div class="container py-3">
    <header>
        <div class="d-flex flex-column flex-md-row align-items-center pb-3 mb-4 border-bottom">
            <a href="" class="d-flex align-items-center text-dark text-decoration-none">
                <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" fill="currentColor" class="bi bi-gem"
                     viewBox="0 0 16 16">
                    <title>Diamond Restaurant</title>
                    <path d="M3.1.7a.5.5 0 0 1 .4-.2h9a.5.5 0 0 1 .4.2l2.976 3.974c.149.185.156.45.01.644L8.4 15.3a.5.5 0 0 1-.8 0L.1 5.3a.5.5 0 0 1 0-.6l3-4zm11.386 3.785-1.806-2.41-.776 2.413 2.582-.003zm-3.633.004.961-2.989H4.186l.963 2.995 5.704-.006zM5.47 5.495 8 13.366l2.532-7.876-5.062.005zm-1.371-.999-.78-2.422-1.818 2.425 2.598-.003zM1.499 5.5l5.113 6.817-2.192-6.82L1.5 5.5zm7.889 6.817 5.123-6.83-2.928.002-2.195 6.828z"></path>
                </svg>
                <c:choose>
                    <c:when test="${!'MANAGER'.equalsIgnoreCase(sessionScope.user.role.name())}">
                        <span class="fs-4">&nbsp; Diamond Restaurant</span>
                    </c:when>
                    <c:otherwise>
                        <span class="fs-4">&nbsp; Diamond Restaurant &#183; Manager</span>
                    </c:otherwise>
                </c:choose>
            </a>

            <nav class="d-inline-flex mt-2 mt-md-0 ms-md-auto">
                <c:choose>
                    <c:when test="${!'MANAGER'.equalsIgnoreCase(sessionScope.user.role.name())}">
                        <a class="me-3 py-2 text-dark text-decoration-none" href="home">Menu</a>
                    </c:when>
                    <c:otherwise>
                        <a class="me-3 py-2 text-dark text-decoration-none" href="home"><strong>Manager
                            page</strong></a>
                    </c:otherwise>
                </c:choose>
                <a class="me-3 py-2 text-dark text-decoration-none" href="about.jsp">About</a>
                <a class="me-3 py-2 text-dark text-decoration-none" href="delivery.jsp">Delivery</a>
                <a class="me-3 py-2 text-dark text-decoration-none" href="support.jsp">Support</a>
                <div class="me-3">
                    <button type="button" class="btn button border text-muted" onclick="function cart() {
                      location.href = 'cart';
                    }cart()" style="border-radius: 10px; font-size: 15px">
                        <span class="button-span">
                            <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor"
                                 class="bi bi-basket"
                                 viewBox="0 0 16 16">
                                <title>Basket</title>
                                <path d="M5.757 1.071a.5.5 0 0 1 .172.686L3.383 6h9.234L10.07 1.757a.5.5 0 1 1 .858-.514L13.783 6H15a1 1 0 0 1 1 1v1a1 1 0 0 1-1 1v4.5a2.5 2.5 0 0 1-2.5 2.5h-9A2.5 2.5 0 0 1 1 13.5V9a1 1 0 0 1-1-1V7a1 1 0 0 1 1-1h1.217L5.07 1.243a.5.5 0 0 1 .686-.172zM2 9v4.5A1.5 1.5 0 0 0 3.5 15h9a1.5 1.5 0 0 0 1.5-1.5V9H2zM1 7v1h14V7H1zm3 3a.5.5 0 0 1 .5.5v3a.5.5 0 0 1-1 0v-3A.5.5 0 0 1 4 10zm2 0a.5.5 0 0 1 .5.5v3a.5.5 0 0 1-1 0v-3A.5.5 0 0 1 6 10zm2 0a.5.5 0 0 1 .5.5v3a.5.5 0 0 1-1 0v-3A.5.5 0 0 1 8 10zm2 0a.5.5 0 0 1 .5.5v3a.5.5 0 0 1-1 0v-3a.5.5 0 0 1 .5-.5zm2 0a.5.5 0 0 1 .5.5v3a.5.5 0 0 1-1 0v-3a.5.5 0 0 1 .5-.5z"></path>
                            </svg>
                            <c:choose>
                                <c:when test="${sessionScope.productsInBucket != null && sessionScope.productsInBucket.size() > 0}">
                                    <div class="badge bg-purple">
                                        <tags:countProducts bucket="${sessionScope.productsInBucket}"/>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="badge bg-gradient">
                                        <c:out value="0"/>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </button>
                </div>
                <button type="button" class="btn button border text-muted" data-bs-toggle="dropdown"
                        aria-expanded="false"><span class="button-span" style="font-size: 16px">Account</span>
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
                                    <a class="dropdown-item" href="account">Account</a>
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
                                <a class="dropdown-item" href="signOut"><strong>Sign out</strong></a>
                            </c:when>
                            <c:otherwise>
                                <a class="dropdown-item" href="login"><strong>Sign in</strong></a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </ul>
            </nav>
        </div>
    </header>

    <main>
        <div class="p-5 mb-4 bg-light rounded-3 mx-auto text-center">
            <div class="container-fluid py-5">
                <h1 class="display-5 fw-bold">Welcome to Diamond</h1>
                <p class="col-md-8 fs-4 mx-auto text-center"></p><br>
                <c:choose>
                    <c:when test="${!'MANAGER'.equalsIgnoreCase(sessionScope.user.role.name())}">
                        <a class="btn button border text-muted" href="home"><span class="button-span">Menu</span></a>
                    </c:when>
                    <c:otherwise>
                        <a class="btn button border text-muted" href="admin"><span
                                class="button-span">Manager page</span></a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </main>

    <footer class="footer mt-auto py-3 bg-light border-top mx-auto text-center">
        <div class="container">
            <div class="row">
                <div class="col"></div>

                <div class="col text-center">
                    <div class="container">
                        <span class="text-muted"> &copy; 2021 Diamond Restaurant.</span>
                    </div>
                </div>

                <div class="col text-end">
                    <button type="button" class="btn button orange border text-muted mx-auto" data-bs-toggle="dropdown"
                            aria-expanded="false" style="font-size: 13px">
                        <span class="button-span">Change language</span>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="">
                            ENGLISH
                        </a></li>
                        <li><a class="dropdown-item" href="">
                            RUSSIAN
                        </a></li>
                        <li><a class="dropdown-item" href="">
                            ANOTHER ONE
                        </a></li>
                    </ul>
                </div>
            </div>
        </div>
    </footer>
</div>
</body>
</html>