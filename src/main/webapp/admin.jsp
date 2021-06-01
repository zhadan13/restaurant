<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" uri="http://localhost:8080/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Diamond Restaurant &#183; Manager</title>
    <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/main_style.css">
    <link rel="stylesheet" type="text/css" href="css/button_style.css">
    <script src="bootstrap/js/bootstrap.bundle.min.js"
            integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
            crossorigin="anonymous"></script>
    <style>
        <%@include file="bootstrap/css/bootstrap.min.css"%>
        <%@include file="css/main_style.css"%>
        <%@include file="css/button_style.css"%>
    </style>
</head>

<body>
<div class="container py-3">
    <header>
        <div class="d-flex flex-column flex-md-row align-items-center pb-3 mb-4 border-bottom">
            <a href="${applicationScope.APPLICATION_NAME}"
               class="d-flex align-items-center text-dark text-decoration-none">
                <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" fill="currentColor" class="bi bi-gem"
                     viewBox="0 0 16 16">
                    <title>Diamond Restaurant</title>
                    <path d="M3.1.7a.5.5 0 0 1 .4-.2h9a.5.5 0 0 1 .4.2l2.976 3.974c.149.185.156.45.01.644L8.4 15.3a.5.5 0 0 1-.8 0L.1 5.3a.5.5 0 0 1 0-.6l3-4zm11.386 3.785-1.806-2.41-.776 2.413 2.582-.003zm-3.633.004.961-2.989H4.186l.963 2.995 5.704-.006zM5.47 5.495 8 13.366l2.532-7.876-5.062.005zm-1.371-.999-.78-2.422-1.818 2.425 2.598-.003zM1.499 5.5l5.113 6.817-2.192-6.82L1.5 5.5zm7.889 6.817 5.123-6.83-2.928.002-2.195 6.828z"></path>
                </svg>
                <span class="fs-4">&nbsp;Diamond Restaurant &#183; Manager</span>
            </a>

            <nav class="d-inline-flex mt-2 mt-md-0 ms-md-auto">
                <a class="me-3 py-2 text-dark text-decoration-none" href="about.jsp">About</a>
                <a class="me-3 py-2 text-dark text-decoration-none" href="delivery.jsp">Delivery</a>
                <a class="me-3 py-2 text-dark text-decoration-none" href="support.jsp">Support</a>
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
        <div class="container">
            <div class="row">
                <div class="bg-light rounded-3 mx-auto text-center">
                    <div class="container-fluid py-2 mb-2">
                        <h6 class="display-6">Orders</h6>
                    </div>
                </div>

                <div class="bg-white rounded-3 my-3 mx-auto text-end">
                    <div class="btn-group">
                        <button type="button" class="btn btn-outline-purple dropdown-toggle" data-bs-toggle="dropdown"
                                aria-expanded="false">
                            <c:out value="Sort (${sessionScope.sorting})"/>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item"
                                   href="admin?sorting=default">
                                By default</a></li>
                            <li><a class="dropdown-item"
                                   href="admin?sorting=user id">
                                By users</a></li>
                            <li><a class="dropdown-item"
                                   href="admin?sorting=status">
                                By status</a></li>
                            <li><a class="dropdown-item"
                                   href="admin?sorting=date old to new">
                                By date (Old to New)</a></li>
                            <li><a class="dropdown-item"
                                   href="admin?sorting=date new to old">
                                By date (New to Old)</a></li>
                        </ul>
                    </div>
                </div>

                <div class="col-12 col-sm-3">
                    <div class="card bg-light mb-3">
                        <div class="card-header bg-gradient text-white text-uppercase">Filter by status</div>
                        <ul class="list-group filter_block">
                            <c:choose>
                                <c:when test="${'default'.equalsIgnoreCase(sessionScope.filter)}">
                                    <button type="button" class="list-group-item list-group-item-action active"
                                            style="border-bottom: 1px solid #000000">Any status
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            style="border-bottom: 1px solid #000000" onclick="function fun() {
                                            location.href = 'admin?filter=default'
                                            }fun()">Any status
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'ACCEPTED'.equalsIgnoreCase(sessionScope.filter)}">
                                    <button type="button" class="list-group-item list-group-item-action active">ACCEPTED
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = 'admin?filter=accepted'
                                                    }fun()">ACCEPTED
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'CONFIRMED'.equalsIgnoreCase(sessionScope.filter)}">
                                    <button type="button" class="list-group-item list-group-item-action active">
                                        CONFIRMED
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = 'admin?filter=confirmed'
                                                    }fun()">CONFIRMED
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'PREPARING'.equalsIgnoreCase(sessionScope.filter)}">
                                    <button type="button" class="list-group-item list-group-item-action active">
                                        PREPARING
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = 'admin?filter=preparing'
                                                    }fun()">PREPARING
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'DELIVERING'.equalsIgnoreCase(sessionScope.filter)}">
                                    <button type="button" class="list-group-item list-group-item-action active">
                                        DELIVERING
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = 'admin?filter=delivering'
                                                    }fun()">DELIVERING
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'COMPLETED'.equalsIgnoreCase(sessionScope.filter)}">
                                    <button type="button" class="list-group-item list-group-item-action active">
                                        COMPLETED
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = 'admin?filter=completed'
                                                    }fun()">COMPLETED
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'REJECTED'.equalsIgnoreCase(sessionScope.filter)}">
                                    <button type="button" class="list-group-item list-group-item-action active">REJECTED
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = 'admin?filter=rejected'
                                                    }fun()">REJECTED
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'UNCOMPLETED'.equalsIgnoreCase(sessionScope.filter)}">
                                    <button type="button" class="list-group-item list-group-item-action active"
                                            style="border-top: 1px solid #000000">UNCOMPLETED
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            style="border-top: 1px solid #000000" onclick="function fun() {
                                            location.href = 'admin?filter=uncompleted'
                                            }fun()">UNCOMPLETED
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </ul>
                    </div>
                </div>

                <div class="col">
                    <div class="row">
                        <c:forEach items="${sessionScope.orders}" var="order">
                            <div class="col-12">
                                <div class="card border-gradient mb-3" style="max-width: 900px;">
                                    <div class="card-header">
                                        <div class="row">
                                            <div class="col">
                                                <strong>Order ID: ${order.id}</strong>
                                            </div>
                                            <div class="col text-end">
                                                <strong>User ID: ${order.userId}</strong>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="card-body text-dark">
                                        <h5 class="card-title text-danger">
                                            Date and Time: <tags:dateFormatTag dateTime="${order.date}"/>
                                        </h5>
                                        <hr>
                                        <h5 class="card-text">Address: ${order.address}</h5>
                                        <hr>
                                        <p class="card-text">
                                            <strong>Order:</strong>
                                            <c:forEach items="${order.products}" var="product">
                                                <c:out value="${product.key}"/>
                                                <strong><c:out value=" = ${product.value}"/></strong><br>
                                            </c:forEach>
                                        </p>
                                    </div>

                                    <div class="card-footer">
                                        <div class="row">
                                            <div class="col">
                                                <h5 class="card-title text-danger">Status: ${order.status}</h5>
                                                <div class="col">
                                                    <span>Cost: ${order.cost} UAH</span>
                                                    <br>
                                                    <span>Payment: ${order.payment}</span>
                                                </div>
                                            </div>

                                            <div class="col text-center">
                                                <button type="button" class="btn button border text-muted mx-auto"
                                                        data-bs-toggle="dropdown"
                                                        aria-expanded="false" style="font-size: 13px">
                                                    <span class="button-span">Change status</span>
                                                </button>

                                                <ul class="dropdown-menu">
                                                    <li><a class="dropdown-item"
                                                           href="admin?changeStatusFor=${order.id}&newStatus=ACCEPTED">
                                                        ACCEPTED
                                                    </a></li>
                                                    <li><a class="dropdown-item"
                                                           href="admin?changeStatusFor=${order.id}&newStatus=CONFIRMED">
                                                        CONFIRMED
                                                    </a></li>
                                                    <li><a class="dropdown-item"
                                                           href="admin?changeStatusFor=${order.id}&newStatus=PREPARING">
                                                        PREPARING
                                                    </a></li>
                                                    <li><a class="dropdown-item"
                                                           href="admin?changeStatusFor=${order.id}&newStatus=DELIVERING">
                                                        DELIVERING
                                                    </a></li>
                                                    <li><a class="dropdown-item"
                                                           href="admin?changeStatusFor=${order.id}&newStatus=COMPLETED">
                                                        COMPLETED
                                                    </a></li>
                                                    <li><a class="dropdown-item"
                                                           href="admin?changeStatusFor=${order.id}&newStatus=REJECTED">
                                                        REJECTED
                                                    </a></li>
                                                </ul>
                                            </div>

                                            <div class="col text-end">
                                                <button type="button"
                                                        class="btn button orange border text-danger mx-auto"
                                                        onclick="function remove() {
                                                                location.href = 'admin?removeOrder=${order.id}';
                                                                }remove()" style="font-size: 13px">
                                                    <span class="button-span">
                                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
                                                             fill="currentColor" class="bi bi-trash-fill"
                                                             viewBox="0 0 16 16">
                                                            <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z"></path>
                                                        </svg>
                                                    </span>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <div class="pagination_rounded">
        <ul>
            <c:choose>
                <c:when test="${requestScope.pageIndex != 1}">
                    <li>
                        <a href="home?elementsPerPage=${requestScope.elementsPerPage}&pageIndex=${requestScope.pageIndex - 1}"
                           class="prev">Previous
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li><a class="prev-disabled">Previous</a></li>
                </c:otherwise>
            </c:choose>

            <c:forEach begin="1" end="${requestScope.numberOfPages}" var="index">
                <c:choose>
                    <c:when test="${requestScope.pageIndex == index}">
                        <li><a>${index}</a></li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <a href="home?elementsPerPage=${requestScope.elementsPerPage}&pageIndex=${index}">${index}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:choose>
                <c:when test="${requestScope.pageIndex != requestScope.numberOfPages}">
                    <li>
                        <a href="home?elementsPerPage=${requestScope.elementsPerPage}&pageIndex=${requestScope.pageIndex + 1}"
                           class="next">Next
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li><a class="next-disabled">Next</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>

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