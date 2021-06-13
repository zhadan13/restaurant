<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" uri="http://localhost:8080/tags" %>
<%@ include file="WEB-INF/localization.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title} &#183; ${menu}</title>
    <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/index_style.css">
    <link rel="stylesheet" type="text/css" href="css/main_style.css">
    <link rel="stylesheet" type="text/css" href="css/button_style.css">
    <script src="bootstrap/js/bootstrap.bundle.min.js"
            integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
            crossorigin="anonymous"></script>
    <style>
        <%@ include file="bootstrap/css/bootstrap.min.css" %>
        <%@ include file="css/index_style.css" %>
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
                <span class="fs-4">&nbsp;${title} &#183; ${menu}</span>
            </a>

            <nav class="d-inline-flex mt-2 mt-md-0 ms-md-auto">
                <a class="me-3 py-2 text-dark text-decoration-none" href="">${menu}</a>
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
                        aria-expanded="false"><span class="button-span" style="font-size: 15px">${account}</span>
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
                        <h6 class="display-6">${our_menu}</h6>
                    </div>
                </div>

                <div class="bg-white rounded-3 my-3 mx-auto text-end">
                    <div class="btn-group">
                        <button type="button" class="btn btn-outline-purple dropdown-toggle" data-bs-toggle="dropdown"
                                aria-expanded="false">
                            <c:if test="${'DEFAULT'.equalsIgnoreCase(sessionScope.sorting)}">
                                <c:out value="${sort} (${sort_default})"/>
                            </c:if>
                            <c:if test="${'POPULARITY'.equalsIgnoreCase(sessionScope.sorting)}">
                                <c:out value="${sort} (${sort_popularity})"/>
                            </c:if>
                            <c:if test="${'CATEGORY'.equalsIgnoreCase(sessionScope.sorting)}">
                                <c:out value="${sort} (${sort_category})"/>
                            </c:if>
                            <c:if test="${'PRICE LOW TO HIGH'.equalsIgnoreCase(sessionScope.sorting)}">
                                <c:out value="${sort} (${sort_price})"/>
                            </c:if>
                            <c:if test="${'PRICE HIGH TO LOW'.equalsIgnoreCase(sessionScope.sorting)}">
                                <c:out value="${sort} (${sort_price_desc})"/>
                            </c:if>
                            <c:if test="${'NAME'.equalsIgnoreCase(sessionScope.sorting)}">
                                <c:out value="${sort} (${sort_name})"/>
                            </c:if>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item"
                                   href="home?sorting=default">
                                ${sort_default}</a></li>
                            <li><a class="dropdown-item"
                                   href="home?sorting=popularity">
                                ${sort_popularity}</a></li>
                            <li><a class="dropdown-item"
                                   href="home?sorting=category">
                                ${sort_category}</a></li>
                            <li><a class="dropdown-item"
                                   href="home?sorting=price low to high">
                                ${sort_price}</a></li>
                            <li><a class="dropdown-item"
                                   href="home?sorting=price high to low">
                                ${sort_price_desc}</a></li>
                            <li><a class="dropdown-item"
                                   href="home?sorting=name">
                                ${sort_name}</a></li>
                        </ul>
                    </div>
                </div>

                <div class="col-12 col-sm-3">
                    <div class="card bg-light mb-3">
                        <div class="card-header bg-gradient text-white text-uppercase">${categories}</div>
                        <ul class="list-group category_block">
                            <c:choose>
                                <c:when test="${'default'.equalsIgnoreCase(sessionScope.category)}">
                                    <button type="button" class="list-group-item list-group-item-action active"
                                            style="border-bottom: 1px solid #000000">${all_products}
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            style="border-bottom: 1px solid #000000" onclick="function fun() {
                                            location.href = 'home?category=default'
                                            }fun()">${all_products}
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'pizza'.equalsIgnoreCase(sessionScope.category)}">
                                    <button type="button" class="list-group-item list-group-item-action active">${pizza}
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = 'home?category=pizza'
                                                    }fun()">${pizza}
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'sushi'.equalsIgnoreCase(sessionScope.category)}">
                                    <button type="button" class="list-group-item list-group-item-action active">${sushi}
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = 'home?category=sushi'
                                                    }fun()">${sushi}
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'salad'.equalsIgnoreCase(sessionScope.category)}">
                                    <button type="button" class="list-group-item list-group-item-action active">${salad}
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = 'home?category=salad'
                                                    }fun()">${salad}
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'pasta'.equalsIgnoreCase(sessionScope.category)}">
                                    <button type="button" class="list-group-item list-group-item-action active">${pasta}
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = 'home?category=pasta'
                                                    }fun()">${pasta}
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'dessert'.equalsIgnoreCase(sessionScope.category)}">
                                    <button type="button"
                                            class="list-group-item list-group-item-action active">${dessert}
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = 'home?category=dessert'
                                                    }fun()">${dessert}
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'drinks'.equalsIgnoreCase(sessionScope.category)}">
                                    <button type="button"
                                            class="list-group-item list-group-item-action active">${drinks}
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="list-group-item list-group-item-action"
                                            onclick="function fun() {
                                                    location.href = 'home?category=drinks'
                                                    }fun()">${drinks}
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
                                <div class="card border-gradient mb-3" style="max-width: 900px;">
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
                                                            <c:out value="${volume}: ${product.weight} ${l}."/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:out value="${weight}: ${product.weight} ${g}."/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </span>
                                            </div>

                                            <div class="col d-flex justify-content-center">
                                                <span>${price}: ${product.price} ${uah}</span>
                                            </div>

                                            <div class="col d-flex justify-content-end">
                                                <button type="button" class="btn button border text-muted mx-1"
                                                        onclick="function add() {
                                                                location.href = 'home?addProduct=${product.id}'
                                                                }add()" style="font-size: 13px">
                                                    <span class="button-span">
                                                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                                                             fill="currentColor" class="bi bi-bag-plus"
                                                             viewBox="0 0 16 16">
                                                            <path fill-rule="evenodd"
                                                                  d="M8 7.5a.5.5 0 0 1 .5.5v1.5H10a.5.5 0 0 1 0 1H8.5V12a.5.5 0 0 1-1 0v-1.5H6a.5.5 0 0 1 0-1h1.5V8a.5.5 0 0 1 .5-.5z"></path>
                                                            <path d="M8 1a2.5 2.5 0 0 1 2.5 2.5V4h-5v-.5A2.5 2.5 0 0 1 8 1zm3.5 3v-.5a3.5 3.5 0 1 0-7 0V4H1v10a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V4h-3.5zM2 5h12v9a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1V5z"></path>
                                                        </svg>
                                                        <c:out value="${add_to_basket}"/>
                                                        <c:choose>
                                                            <c:when test="${sessionScope.productsInBucket.containsKey(product.id)}">
                                                                <div class="badge bg-purple">
                                                                    <c:out value="${sessionScope.productsInBucket.get(product.id)}"/>
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

                                                <button type="button"
                                                        class="btn button orange border text-danger mx-auto"
                                                        onclick="function remove() {
                                                                location.href = 'home?removeProduct=${product.id}';
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