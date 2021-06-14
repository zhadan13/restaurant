<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" uri="http://localhost:8080/tags" %>
<%@ include file="WEB-INF/localization.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title} &#183; ${cart}</title>
    <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/index_style.css">
    <link rel="stylesheet" type="text/css" href="css/cart_style.css">
    <link rel="stylesheet" type="text/css" href="css/button_style.css">
    <script src="bootstrap/js/bootstrap.bundle.min.js"
            integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
            crossorigin="anonymous"></script>
    <style>
        <%@ include file="bootstrap/css/bootstrap.min.css" %>
        <%@ include file="css/index_style.css" %>
        <%@ include file="css/cart_style.css" %>
        <%@ include file="css/button_style.css" %>
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
                    <title>${title}</title>
                    <path d="M3.1.7a.5.5 0 0 1 .4-.2h9a.5.5 0 0 1 .4.2l2.976 3.974c.149.185.156.45.01.644L8.4 15.3a.5.5 0 0 1-.8 0L.1 5.3a.5.5 0 0 1 0-.6l3-4zm11.386 3.785-1.806-2.41-.776 2.413 2.582-.003zm-3.633.004.961-2.989H4.186l.963 2.995 5.704-.006zM5.47 5.495 8 13.366l2.532-7.876-5.062.005zm-1.371-.999-.78-2.422-1.818 2.425 2.598-.003zM1.499 5.5l5.113 6.817-2.192-6.82L1.5 5.5zm7.889 6.817 5.123-6.83-2.928.002-2.195 6.828z"></path>
                </svg>
                <span class="fs-4">&nbsp; ${title} &#183; ${_order}</span>
            </a>

            <nav class="d-inline-flex mt-2 mt-md-0 ms-md-auto">
                <a class="me-3 py-2 text-dark text-decoration-none" href="home">${menu}</a>
                <a class="me-3 py-2 text-dark text-decoration-none" href="about.jsp">${about}</a>
                <a class="me-3 py-2 text-dark text-decoration-none" href="delivery.jsp">${delivery}</a>
                <a class="me-3 py-2 text-dark text-decoration-none" href="support.jsp">${support}</a>
                <div class="me-3">
                    <button type="button" class="btn button border text-muted" onclick="function cart() {
                      location.href = 'cart';
                    }cart()" style="border-radius: 10px; font-size: 15px">
                        <span class="button-span">
                            <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor"
                                 class="bi bi-basket"
                                 viewBox="0 0 16 16">
                                <title>${basket}</title>
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
                <tags:account/>
            </nav>
        </div>
    </header>

    <main>
        <div class="container py-3">
            <c:choose>
                <c:when test="${sessionScope.bucket == null || sessionScope.bucket.size() == 0}">
                    <div class="bg-light rounded-3 mx-auto text-center">
                        <div class="container-fluid py-2 mb-2">
                            <h6 class="display-6">${cart_empty}</h6>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="bg-light rounded-3 mx-auto text-center">
                        <div class="container-fluid py-2 mb-2">
                            <h6 class="display-6">${cart}</h6>
                        </div>
                    </div>

                    <div class="row">
                        <aside class="col-lg-9">
                            <div class="card">
                                <div class="table-responsive">
                                    <table class="table table-borderless table-shopping-cart">
                                        <thead class="text-muted">
                                        <tr class="text-uppercase">
                                            <th scope="col">${product}</th>
                                            <th scope="col">${quantity}</th>
                                            <th scope="col">${price}</th>
                                            <th scope="col">${price_total}</th>
                                            <th scope="col" class="text-right d-none d-md-block"></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${sessionScope.bucket}" var="product">
                                            <tr>
                                                <td class="text-uppercase"><strong>${product.key.name}</strong></td>
                                                <td class="text-uppercase">
                                                    <strong>${product.value}</strong>
                                                </td>
                                                <td>
                                                    <div class="price-wrap">
                                                        <var class="price">${product.key.price} ${uah}</var>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="price-wrap">
                                                        <var class="price">${product.key.price * product.value}
                                                                ${uah}</var>
                                                    </div>
                                                </td>
                                                <td class="text-right d-none d-md-block">
                                                    <a href="<c:url value="cart?removeProduct=${product.key.id}"/>"
                                                       class="btn button orange border text-danger" data-abc="true"
                                                       style="font-size: 12px">
                                                        <span class="button-span">${remove}</span>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </aside>

                        <aside class="col-lg-3">
                            <div class="card">
                                <div class="card-body">
                                    <dl class="dlist-align">
                                        <dt>${price_total}:&nbsp;&nbsp;</dt>
                                        <dd class="text-right ml-3">${sessionScope.totalPrice} ${uah}</dd>
                                    </dl>
                                    <dl class="dlist-align">
                                        <dt>${delivery}:&nbsp;&nbsp;</dt>
                                        <c:choose>
                                            <c:when test="${sessionScope.deliveryPrice != null && sessionScope.deliveryPrice != 0}">
                                                <dd class="text-right ml-3">${sessionScope.deliveryPrice} ${uah}</dd>
                                            </c:when>
                                            <c:otherwise>
                                                <dd class="text-right ml-3">${free}</dd>
                                            </c:otherwise>
                                        </c:choose>
                                    </dl>
                                    <hr>
                                    <dl class="dlist-align">
                                        <dt>${total}:&nbsp;&nbsp;</dt>
                                        <dd class="text-right ml-3" style="color: #735beb">
                                            <strong>${sessionScope.totalPrice + sessionScope.deliveryPrice} ${uah}</strong>
                                        </dd>
                                    </dl>
                                    <hr>
                                    <div style="text-align: center">
                                        <a href="order" class="btn button orange border text-dark" data-abc="true"
                                           style="font-size: 13px">
                                            <span class="button-span">${make_purchase}</span>
                                        </a>
                                        <a href="home" class="btn button border text-dark mt-2" data-abc="true"
                                           style="font-size: 13px">
                                            <span class="button-span">${continue_shopping}</span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </aside>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </main>

    <footer class="footer mt-auto py-3 bg-light border-top mx-auto text-center">
        <div class="container">
            <div class="row">
                <div class="col"></div>

                <div class="col text-center">
                    <div class="container">
                        <span class="text-muted"> &copy; 2021 ${title}.</span>
                    </div>
                </div>

                <div class="col text-end">
                    <button type="button" class="btn button orange border text-muted mx-auto" data-bs-toggle="dropdown"
                            aria-expanded="false" style="font-size: 13px">
                        <span class="button-span">${language}</span>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="?language=en">
                            ${english} (English)
                        </a></li>
                        <li><a class="dropdown-item" href="?language=ru">
                            ${russian} (Russian)
                        </a></li>
                    </ul>
                </div>
            </div>
        </div>
    </footer>
</div>
</body>
</html>