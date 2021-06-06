<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" uri="http://localhost:8080/tags" %>
<%@ include file="WEB-INF/localization.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title} &#183; ${manager}</title>
    <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/main_style.css">
    <link rel="stylesheet" type="text/css" href="css/button_style.css">
    <script src="bootstrap/js/bootstrap.bundle.min.js"
            integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
            crossorigin="anonymous"></script>
    <style>
        <%@ include file="bootstrap/css/bootstrap.min.css" %>
        <%@ include file="css/main_style.css" %>
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
                <span class="fs-4">&nbsp;${title} &#183; ${manager}</span>
            </a>

            <nav class="d-inline-flex mt-2 mt-md-0 ms-md-auto">
                <c:choose>
                    <c:when test="${!'MANAGER'.equalsIgnoreCase(sessionScope.user.role.name())}">
                        <a class="me-3 py-2 text-dark text-decoration-none" href="home">${menu}</a>
                    </c:when>
                    <c:otherwise>
                        <a class="me-3 py-2 text-dark text-decoration-none" href="home"><strong>${manager_page}
                        </strong></a>
                    </c:otherwise>
                </c:choose>
                <a class="me-3 py-2 text-dark text-decoration-none" href="about.jsp">${about}</a>
                <a class="me-3 py-2 text-dark text-decoration-none" href="delivery.jsp">${delivery}</a>
                <a class="me-3 py-2 text-dark text-decoration-none" href="support.jsp">${support}</a>
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
        <div class="container">
            <div class="row">
                <div class="bg-light rounded-3 mx-auto text-center">
                    <div class="container-fluid py-2 mb-2">
                        <h6 class="display-6">${orders}</h6>
                    </div>
                </div>

                <div class="bg-white rounded-3 my-3 mx-auto text-end">
                    <div class="btn-group">
                        <button type="button" class="btn btn-outline-purple dropdown-toggle" data-bs-toggle="dropdown"
                                aria-expanded="false">
                            <c:if test="${'DEFAULT'.equalsIgnoreCase(sessionScope.sorting)}">
                                <c:out value="${sort} (${sort_default})"/>
                            </c:if>
                            <c:if test="${'USER ID'.equalsIgnoreCase(sessionScope.sorting)}">
                                <c:out value="${sort} (${sort_users})"/>
                            </c:if>
                            <c:if test="${'STATUS'.equalsIgnoreCase(sessionScope.sorting)}">
                                <c:out value="${sort} (${sort_status})"/>
                            </c:if>
                            <c:if test="${'DATE OLD TO NEW'.equalsIgnoreCase(sessionScope.sorting)}">
                                <c:out value="${sort} (${sort_date})"/>
                            </c:if>
                            <c:if test="${'DATE NEW TO OLD'.equalsIgnoreCase(sessionScope.sorting)}">
                                <c:out value="${sort} (${sort_date_desc})"/>
                            </c:if>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item"
                                   href="admin?sorting=default">
                                ${sort_default}</a></li>
                            <li><a class="dropdown-item"
                                   href="admin?sorting=user id">
                                ${sort_users}</a></li>
                            <li><a class="dropdown-item"
                                   href="admin?sorting=status">
                                ${sort_status}</a></li>
                            <li><a class="dropdown-item"
                                   href="admin?sorting=date old to new">
                                ${sort_date}</a></li>
                            <li><a class="dropdown-item"
                                   href="admin?sorting=date new to old">
                                ${sort_date_desc}</a></li>
                        </ul>
                    </div>
                </div>

                <div class="col-12 col-sm-3">
                    <div class="card bg-light mb-3">
                        <div class="card-header bg-gradient text-white text-uppercase">${filter_by_status}</div>
                        <ul class="list-group filter_block">
                            <c:choose>
                                <c:when test="${'default'.equalsIgnoreCase(sessionScope.filter)}">
                                    <button type="button" class="list-group-item list-group-item-action active"
                                            style="border-bottom: 1px solid #000000">${any_status}
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            style="border-bottom: 1px solid #000000" onclick="function fun() {
                                            location.href = 'admin?filter=default'
                                            }fun()">${any_status}
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'ACCEPTED'.equalsIgnoreCase(sessionScope.filter)}">
                                    <button type="button"
                                            class="list-group-item list-group-item-action active">${accepted}
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = 'admin?filter=accepted'
                                                    }fun()">${accepted}
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'CONFIRMED'.equalsIgnoreCase(sessionScope.filter)}">
                                    <button type="button" class="list-group-item list-group-item-action active">
                                            ${confirmed}
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = 'admin?filter=confirmed'
                                                    }fun()">${confirmed}
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'PREPARING'.equalsIgnoreCase(sessionScope.filter)}">
                                    <button type="button" class="list-group-item list-group-item-action active">
                                            ${preparing}
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = 'admin?filter=preparing'
                                                    }fun()">${preparing}
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'DELIVERING'.equalsIgnoreCase(sessionScope.filter)}">
                                    <button type="button" class="list-group-item list-group-item-action active">
                                            ${delivering}
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = 'admin?filter=delivering'
                                                    }fun()">${delivering}
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'COMPLETED'.equalsIgnoreCase(sessionScope.filter)}">
                                    <button type="button" class="list-group-item list-group-item-action active">
                                            ${completed}
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = 'admin?filter=completed'
                                                    }fun()">${completed}
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'REJECTED'.equalsIgnoreCase(sessionScope.filter)}">
                                    <button type="button"
                                            class="list-group-item list-group-item-action active">${rejected}
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = 'admin?filter=rejected'
                                                    }fun()">${rejected}
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'UNCOMPLETED'.equalsIgnoreCase(sessionScope.filter)}">
                                    <button type="button" class="list-group-item list-group-item-action active"
                                            style="border-top: 1px solid #000000">${uncompleted}
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            style="border-top: 1px solid #000000" onclick="function fun() {
                                            location.href = 'admin?filter=uncompleted'
                                            }fun()">${uncompleted}
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
                                                <strong>${order_number}: ${order.id}</strong>
                                            </div>
                                            <div class="col text-end">
                                                <strong>${_user} ID: ${order.userId}</strong>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="card-body text-dark">
                                        <h5 class="card-title text-danger">
                                                ${date_time}: <tags:dateFormatTag dateTime="${order.date}"/>
                                        </h5>
                                        <hr>
                                        <h5 class="card-text">${address}: ${order.address}</h5>
                                        <hr>
                                        <p class="card-text">
                                            <strong>${_order}:</strong>
                                            <c:forEach items="${order.products}" var="product">
                                                <c:out value="${product.key}"/>
                                                <strong><c:out value=" = ${product.value}"/></strong><br>
                                            </c:forEach>
                                        </p>
                                    </div>

                                    <div class="card-footer">
                                        <div class="row">
                                            <div class="col">
                                                <h5 class="card-title text-danger">${status}:
                                                    <c:if test="${'ACCEPTED'.equals(order.status.name())}">
                                                        <c:out value="${accepted}"/>
                                                    </c:if>
                                                    <c:if test="${'CONFIRMED'.equals(order.status.name())}">
                                                        <c:out value="${confirmed}"/>
                                                    </c:if>
                                                    <c:if test="${'PREPARING'.equals(order.status.name())}">
                                                        <c:out value="${preparing}"/>
                                                    </c:if>
                                                    <c:if test="${'DELIVERING'.equals(order.status.name())}">
                                                        <c:out value="${delivering}"/>
                                                    </c:if>
                                                    <c:if test="${'COMPLETED'.equals(order.status.name())}">
                                                        <c:out value="${completed}"/>
                                                    </c:if>
                                                    <c:if test="${'REJECTED'.equals(order.status.name())}">
                                                        <c:out value="${rejected}"/>
                                                    </c:if>
                                                </h5>
                                                <div class="col">
                                                    <span>${cost}: ${order.cost} ${uah}</span>
                                                    <br>
                                                    <span>${payment}:
                                                        <c:if test="${'CASH'.equals(order.payment.name())}">
                                                            <c:out value="${cash}"/>
                                                        </c:if>
                                                        <c:if test="${'CARD_ONLINE'.equals(order.payment.name())}">
                                                            <c:out value="${card_online}"/>
                                                        </c:if>
                                                        <c:if test="${'CARD_OFFLINE'.equals(order.payment.name())}">
                                                            <c:out value="${card_offline}"/>
                                                        </c:if>
                                                    </span>
                                                </div>
                                            </div>

                                            <div class="col text-center">
                                                <button type="button" class="btn button border text-muted mx-auto"
                                                        data-bs-toggle="dropdown"
                                                        aria-expanded="false" style="font-size: 13px">
                                                    <span class="button-span">${change_status}</span>
                                                </button>

                                                <ul class="dropdown-menu">
                                                    <li><a class="dropdown-item"
                                                           href="admin?changeStatusFor=${order.id}&newStatus=ACCEPTED">
                                                            ${accepted}
                                                    </a></li>
                                                    <li><a class="dropdown-item"
                                                           href="admin?changeStatusFor=${order.id}&newStatus=CONFIRMED">
                                                            ${confirmed}
                                                    </a></li>
                                                    <li><a class="dropdown-item"
                                                           href="admin?changeStatusFor=${order.id}&newStatus=PREPARING">
                                                            ${preparing}
                                                    </a></li>
                                                    <li><a class="dropdown-item"
                                                           href="admin?changeStatusFor=${order.id}&newStatus=DELIVERING">
                                                            ${delivering}
                                                    </a></li>
                                                    <li><a class="dropdown-item"
                                                           href="admin?changeStatusFor=${order.id}&newStatus=COMPLETED">
                                                            ${completed}
                                                    </a></li>
                                                    <li><a class="dropdown-item"
                                                           href="admin?changeStatusFor=${order.id}&newStatus=REJECTED">
                                                            ${rejected}
                                                    </a></li>
                                                </ul>
                                            </div>

                                            <div class="col text-end">
                                                <form action="admin" method="post">
                                                    <input type="hidden" name="removeOrder"
                                                           value="${order.id}"/>
                                                    <button type="submit"
                                                            class="btn button orange border text-danger mx-auto"
                                                            style="font-size: 13px">
                                                    <span class="button-span">
                                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
                                                             fill="currentColor" class="bi bi-trash-fill"
                                                             viewBox="0 0 16 16">
                                                            <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z"></path>
                                                        </svg>
                                                    </span>
                                                    </button>
                                                </form>
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
                           class="prev">${previous}
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li><a class="prev-disabled">${previous}</a></li>
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
                           class="next">${next}
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li><a class="next-disabled">${next}</a></li>
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