<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" uri="http://localhost:8080/tags" %>
<%@ include file="WEB-INF/localization.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title} &#183; ${order_confirmed}</title>
    <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/index_style.css">
    <link rel="stylesheet" type="text/css" href="css/order_style.css">
    <link rel="stylesheet" type="text/css" href="css/button_style.css">
    <script src="bootstrap/js/bootstrap.bundle.min.js"
            integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
            crossorigin="anonymous"></script>
    <style>
        <%@include file="bootstrap/css/bootstrap.min.css"%>
        <%@include file="css/index_style.css"%>
        <%@include file="css/order_style.css"%>
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
                    <title>${title}</title>
                    <path d="M3.1.7a.5.5 0 0 1 .4-.2h9a.5.5 0 0 1 .4.2l2.976 3.974c.149.185.156.45.01.644L8.4 15.3a.5.5 0 0 1-.8 0L.1 5.3a.5.5 0 0 1 0-.6l3-4zm11.386 3.785-1.806-2.41-.776 2.413 2.582-.003zm-3.633.004.961-2.989H4.186l.963 2.995 5.704-.006zM5.47 5.495 8 13.366l2.532-7.876-5.062.005zm-1.371-.999-.78-2.422-1.818 2.425 2.598-.003zM1.499 5.5l5.113 6.817-2.192-6.82L1.5 5.5zm7.889 6.817 5.123-6.83-2.928.002-2.195 6.828z"></path>
                </svg>
                <span class="fs-4">&nbsp;${title}</span>
            </a>

            <nav class="d-inline-flex mt-2 mt-md-0 ms-md-auto">
                <a class="me-3 py-2 text-dark text-decoration-none" href="<c:url value="/home"/>">${menu}</a>
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
                <button type="button" class="btn button border text-muted" data-bs-toggle="dropdown"
                        aria-expanded="false"><span class="button-span" style="font-size: 16px">${account}</span>
                </button>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="">
                        <c:choose>
                            <c:when test="${sessionScope.user != null}">
                                <strong>
                                    <c:out value="${account_logged} ${sessionScope.user.name}"/>
                                </strong>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${account_not_logged}"/>
                            </c:otherwise>
                        </c:choose>
                    </a></li>
                    <li>
                        <c:choose>
                            <c:when test="${sessionScope.user != null}">
                                <strong>
                                    <a class="dropdown-item" href="account">${account}</a>
                                </strong>
                            </c:when>
                            <c:otherwise>
                                <a class="dropdown-item disabled" href="" tabindex="-1"
                                   aria-disabled="true">${account}</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>
                    <li>
                        <c:choose>
                            <c:when test="${sessionScope.user != null}">
                                <a class="dropdown-item" href="signOut"><strong>${account_sign_out}</strong></a>
                            </c:when>
                            <c:otherwise>
                                <a class="dropdown-item" href="login"><strong>${account_sign_in}</strong></a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </ul>
            </nav>
        </div>
    </header>

    <main>
        <div class="container py-3">
            <div class="bg-light rounded-3 mx-auto text-center">
                <div class="container-fluid py-2 mb-2">
                    <h6 class="display-6">${thanks_for_order}</h6>
                </div>
            </div>

            <div class="page payment-page">
                <section class="payment-form dark">
                    <div class="container">
                        <form>
                            <div class="products">
                                <h3 class="title text-muted">${order_details}</h3>
                                <c:forEach items="${requestScope.order.products}" var="product">
                                    <div class="item">
                                        <span class="price">${product.key.price * product.value} ${uah}</span>
                                        <p class="item-name">${product.key.name}</p>
                                        <p class="item-description">${price}: ${product.key.price} ${uah} &#183;
                                                ${quantity}: ${product.value}</p>
                                    </div>
                                </c:forEach>
                                <c:choose>
                                    <c:when test="${sessionScope.deliveryPrice != null && sessionScope.deliveryPrice != 0}">
                                        <div class="total">${delivery}<span
                                                class="price">${sessionScope.deliveryPrice} ${uah}</span></div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="total">${delivery}<span class="price">${free}</span></div>
                                    </c:otherwise>
                                </c:choose>
                                <div class="total">${total}<span class="price">${requestScope.order.cost} ${uah}</span>
                                </div>
                            </div>

                            <div class="input-details">
                                <h3 class="title text-muted">${delivery_details}</h3>
                                <div class="col-sm-auto">
                                    <label>${order_number}:&nbsp;</label>
                                    <strong>
                                        <c:out value="${requestScope.order.id}"/>
                                    </strong>
                                </div>
                                <div class="col-sm-auto">
                                    <label>${order_status}:&nbsp;</label>
                                    <strong>
                                        <c:if test="${'ACCEPTED'.equals(requestScope.order.status.name())}">
                                            <c:out value="${accepted}"/>
                                        </c:if>
                                        <c:if test="${'CONFIRMED'.equals(requestScope.order.status.name())}">
                                            <c:out value="${confirmed}"/>
                                        </c:if>
                                        <c:if test="${'PREPARING'.equals(requestScope.order.status.name())}">
                                            <c:out value="${previous}"/>
                                        </c:if>
                                        <c:if test="${'DELIVERING'.equals(requestScope.order.status.name())}">
                                            <c:out value="${delivering}"/>
                                        </c:if>
                                        <c:if test="${'COMPLETED'.equals(requestScope.order.status.name())}">
                                            <c:out value="${completed}"/>
                                        </c:if>
                                        <c:if test="${'REJECTED'.equals(requestScope.order.status.name())}">
                                            <c:out value="${rejected}"/>
                                        </c:if>
                                    </strong>
                                </div>
                                <div class="col-sm-auto">
                                    <label>${address}:&nbsp;</label>
                                    <strong>
                                        <c:out value="${requestScope.order.address}"/>
                                    </strong>
                                </div>
                                <div class="col-sm-auto">
                                    <label>${date_time}:&nbsp;</label>
                                    <strong>
                                        <tags:dateFormatTag dateTime="${requestScope.order.date}"/>
                                    </strong>
                                </div>
                                <div class="col-sm-auto">
                                    <label>${payment}:&nbsp;</label>
                                    <strong>
                                        <c:if test="${'CASH'.equals(requestScope.order.payment.name())}">
                                            <c:out value="${cash}"/>
                                        </c:if>
                                        <c:if test="${'CARD_ONLINE'.equals(requestScope.order.payment.name())}">
                                            <c:out value="${card_online}"/>
                                        </c:if>
                                        <c:if test="${'CARD_OFFLINE'.equals(requestScope.order.payment.name())}">
                                            <c:out value="${card_offline}"/>
                                        </c:if>
                                    </strong>
                                </div>
                            </div>
                        </form>
                    </div>
                </section>
            </div>
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