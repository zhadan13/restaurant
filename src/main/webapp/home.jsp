<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Restaurant &#183; Menu</title>
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
                <span class="fs-4">Restaurant &#183; Menu</span>
            </a>

            <nav class="d-inline-flex mt-2 mt-md-0 ms-md-auto">
                <a class="me-3 py-2 text-dark text-decoration-none" href="">Menu</a>
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
                        <h6 class="display-6">Our menu</h6>
                    </div>
                </div>

                <div class="bg-white rounded-3 my-3 mx-auto text-end">
                    <div class="btn-group">
                        <button type="button" class="btn btn-outline-success dropdown-toggle" data-bs-toggle="dropdown"
                                aria-expanded="false">
                            <c:out value="Sort (${sessionScope.sorting})"/>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item"
                                   href="<c:url value="/home?sorting=default"/>">
                                By default</a></li>
                            <li><a class="dropdown-item"
                                   href="<c:url value="/home?sorting=popularity"/>">
                                By popularity</a></li>
                            <li><a class="dropdown-item"
                                   href="<c:url value="/home?sorting=category"/>">
                                By category</a></li>
                            <li><a class="dropdown-item"
                                   href="<c:url value="/home?sorting=price low to high"/>">
                                By price (Low to High)</a></li>
                            <li><a class="dropdown-item"
                                   href="<c:url value="/home?sorting=price high to low"/>">
                                By price (High to Low)</a></li>
                            <li><a class="dropdown-item"
                                   href="<c:url value="/home?sorting=name"/>">
                                By name</a></li>
                        </ul>
                    </div>
                </div>

                <div class="col-12 col-sm-3">
                    <div class="card bg-light mb-3">
                        <div class="card-header bg-success text-white text-uppercase">Categories</div>
                        <ul class="list-group category_block">
                            <c:choose>
                                <c:when test="${'default'.equalsIgnoreCase(sessionScope.category)}">
                                    <button type="button" class="list-group-item list-group-item-action active"
                                            style="border-bottom: 1px solid #000000">All products
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            style="border-bottom: 1px solid #000000" onclick="function fun() {
                                            location.href = '/home?category=default'
                                            }fun()">All products
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'pizza'.equalsIgnoreCase(sessionScope.category)}">
                                    <button type="button" class="list-group-item list-group-item-action active">Pizza
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = '/home?category=pizza'
                                                    }fun()">Pizza
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'sushi'.equalsIgnoreCase(sessionScope.category)}">
                                    <button type="button" class="list-group-item list-group-item-action active">Sushi
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = '/home?category=sushi'
                                                    }fun()">Sushi
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'salad'.equalsIgnoreCase(sessionScope.category)}">
                                    <button type="button" class="list-group-item list-group-item-action active">Salad
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = '/home?category=salad'
                                                    }fun()">Salad
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'pasta'.equalsIgnoreCase(sessionScope.category)}">
                                    <button type="button" class="list-group-item list-group-item-action active">Pasta
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = '/home?category=pasta'
                                                    }fun()">Pasta
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'dessert'.equalsIgnoreCase(sessionScope.category)}">
                                    <button type="button" class="list-group-item list-group-item-action active">Dessert
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = '/home?category=dessert'
                                                    }fun()">Dessert
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'drinks'.equalsIgnoreCase(sessionScope.category)}">
                                    <button type="button" class="list-group-item list-group-item-action active">Drinks
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = '/home?category=drinks'
                                                    }fun()">Drinks
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </ul>
                    </div>
                </div>

                <div class="col">
                    <div class="row">
                        <c:forEach items="${sessionScope.products}" var="product">
                            <div class="col-12">
                                <div class="card border-success mb-3" style="max-width: 900px;">
                                    <div class="card-header">
                                        <strong>${product.category}</strong>
                                    </div>
                                    <div class="card-body text-dark">
                                        <h5 class="card-title">${product.name}</h5>
                                        <p class="card-text">${product.details}</p>
                                    </div>
                                    <div class="card-footer">
                                        <div class="row">
                                            <div class="col">
                                                <span>
                                                    <c:choose>
                                                        <c:when test="${'drinks'.equalsIgnoreCase(product.category)}">
                                                            <c:out value="Volume: ${product.weight} L."/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:out value="Weight: ${product.weight} g."/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </span>
                                            </div>

                                            <div class="col d-flex justify-content-center">
                                                <span>Price: ${product.price} UAH</span>
                                            </div>

                                            <div class="col d-flex justify-content-end">
                                                <button type="button" class="btn btn-outline-dark mx-auto"
                                                        onclick="function add() {
                                                                location.href = '/home?addProduct=${product.id}'
                                                                }add()">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                                                         fill="currentColor" class="bi bi-bag-plus" viewBox="0 0 16 16">
                                                        <path fill-rule="evenodd"
                                                              d="M8 7.5a.5.5 0 0 1 .5.5v1.5H10a.5.5 0 0 1 0 1H8.5V12a.5.5 0 0 1-1 0v-1.5H6a.5.5 0 0 1 0-1h1.5V8a.5.5 0 0 1 .5-.5z"></path>
                                                        <path d="M8 1a2.5 2.5 0 0 1 2.5 2.5V4h-5v-.5A2.5 2.5 0 0 1 8 1zm3.5 3v-.5a3.5 3.5 0 1 0-7 0V4H1v10a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V4h-3.5zM2 5h12v9a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1V5z"></path>
                                                    </svg>
                                                    <span>Add to bucket</span>
                                                    <c:choose>
                                                        <c:when test="${sessionScope.productsInBucket.containsKey(product.id)}">
                                                            <span class="badge bg-success">
                                                                <c:out value="${sessionScope.productsInBucket.get(product.id)}"/>
                                                            </span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge bg-secondary">
                                                                <c:out value="0"/>
                                                            </span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </button>

                                                <button type="button" class="btn btn-outline-danger mx-auto"
                                                        onclick="function remove() {
                                                                location.href = '/home?removeProduct=${product.id}';
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