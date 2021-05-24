<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Restaurant &#183; Manager</title>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/main_style.css">
    <script src="bootstrap/js/bootstrap.bundle.min.js"
            integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
            crossorigin="anonymous"></script>
</head>

<body>
<div class="container py-3">
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
                <span class="fs-4">Restaurant &#183; Manager</span>
            </a>

            <nav class="d-inline-flex mt-2 mt-md-0 ms-md-auto">
                <a class="me-3 py-2 text-dark text-decoration-none" href="about.jsp">About</a>
                <a class="me-3 py-2 text-dark text-decoration-none" href="delivery.jsp">Delivery</a>
                <a class="me-3 py-2 text-dark text-decoration-none" href="support.jsp">Support</a>
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
                                    <a class="dropdown-item" href="<c:url value="/login"/>"><strong>Sign in</strong></a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </ul>
                </div>
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
                        <button type="button" class="btn btn-outline-primary dropdown-toggle" data-bs-toggle="dropdown"
                                aria-expanded="false">
                            <c:out value="Sort (${sessionScope.sorting})"/>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item"
                                   href="<c:url value="/admin?sorting=default"/>">
                                By default</a></li>
                            <li><a class="dropdown-item"
                                   href="<c:url value="/admin?sorting=user id"/>">
                                By users</a></li>
                            <li><a class="dropdown-item"
                                   href="<c:url value="/admin?sorting=status"/>">
                                By status</a></li>
                            <li><a class="dropdown-item"
                                   href="<c:url value="/admin?sorting=date from old to new"/>">
                                By date (from old to new)</a></li>
                            <li><a class="dropdown-item"
                                   href="<c:url value="/admin?sorting=date from new to old"/>">
                                By date (from new to old)</a></li>
                        </ul>
                    </div>
                </div>

                <div class="col-12 col-sm-3">
                    <div class="card bg-light mb-3">
                        <div class="card-header bg-primary text-white text-uppercase">Filter by status</div>
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
                                            location.href = '/admin?filter=DEFAULT'
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
                                                    location.href = '/admin?filter=ACCEPTED'
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
                                                    location.href = '/admin?filter=CONFIRMED'
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
                                                    location.href = '/admin?filter=PREPARING'
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
                                                    location.href = '/admin?filter=DELIVERING'
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
                                                    location.href = '/admin?filter=COMPLETED'
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
                                                    location.href = '/admin?filter=REJECTED'
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
                                            location.href = '/admin?filter=UNCOMPLETED'
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
                                <div class="card border-primary mb-3" style="max-width: 900px;">
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
                                        <h5 class="card-title text-danger">Date and
                                            time: ${order.date.toLocalDateTime().toLocalTime()}
                                            &#183; ${order.date.toLocalDateTime().toLocalDate()}</h5>
                                        <hr>
                                        <h5 class="card-text">Address: ${order.address}</h5>
                                        <hr>
                                        <p class="card-text"><strong>Order:</strong> ${order.products}</p>
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
                                                <div class="btn-group dropup">
                                                    <button type="button" class="btn btn-primary dropdown-toggle"
                                                            data-bs-toggle="dropdown"
                                                            aria-expanded="false">
                                                        Change status
                                                    </button>

                                                    <ul class="dropdown-menu">
                                                        <li><a class="dropdown-item" href=<c:url
                                                                value="/admin?changeStatusFor=${order.id}&newStatus=ACCEPTED"/>>
                                                            ACCEPTED
                                                        </a></li>
                                                        <li><a class="dropdown-item" href=<c:url
                                                                value="/admin?changeStatusFor=${order.id}&newStatus=CONFIRMED"/>>
                                                            CONFIRMED
                                                        </a></li>
                                                        <li><a class="dropdown-item" href=<c:url
                                                                value="/admin?changeStatusFor=${order.id}&newStatus=PREPARING"/>>
                                                            PREPARING
                                                        </a></li>
                                                        <li><a class="dropdown-item" href=<c:url
                                                                value="/admin?changeStatusFor=${order.id}&newStatus=DELIVERING"/>>
                                                            DELIVERING
                                                        </a></li>
                                                        <li><a class="dropdown-item" href=<c:url
                                                                value="/admin?changeStatusFor=${order.id}&newStatus=COMPLETED"/>>
                                                            COMPLETED
                                                        </a></li>
                                                        <li><a class="dropdown-item" href=<c:url
                                                                value="/admin?changeStatusFor=${order.id}&newStatus=REJECTED"/>>
                                                            REJECTED
                                                        </a></li>
                                                    </ul>
                                                </div>
                                            </div>

                                            <div class="col text-end">
                                                <button type="button" class="btn btn-outline-danger mx-auto"
                                                        onclick="function remove() {
                                                                location.href = '/admin?removeOrder=${order.id}';
                                                                }remove()">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
                                                         fill="currentColor" class="bi bi-trash-fill"
                                                         viewBox="0 0 16 16">
                                                        <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z"></path>
                                                    </svg>
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
            <span class="text-muted"> &copy; 2021 Restaurant.</span>
        </div>
    </footer>
</div>
</body>
</html>