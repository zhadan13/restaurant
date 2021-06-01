<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Diamond Restaurant &#183; Order</title>
    <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/payment_style.css">
    <script src="bootstrap/js/bootstrap.bundle.min.js"
            integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
            crossorigin="anonymous"></script>
    <style>
        <%@include file="bootstrap/css/bootstrap.min.css"%>
        <%@include file="css/payment_style.css"%>
    </style>
</head>

<body>
<div class="container py-3">
    <header>
        <div class="d-flex flex-column flex-md-row align-items-center pb-3 mb-4">
            <a href="" class="d-flex align-items-center text-light text-decoration-none">
                <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" fill="currentColor" class="bi bi-gem"
                     viewBox="0 0 16 16">
                    <title>Diamond Restaurant</title>
                    <path d="M3.1.7a.5.5 0 0 1 .4-.2h9a.5.5 0 0 1 .4.2l2.976 3.974c.149.185.156.45.01.644L8.4 15.3a.5.5 0 0 1-.8 0L.1 5.3a.5.5 0 0 1 0-.6l3-4zm11.386 3.785-1.806-2.41-.776 2.413 2.582-.003zm-3.633.004.961-2.989H4.186l.963 2.995 5.704-.006zM5.47 5.495 8 13.366l2.532-7.876-5.062.005zm-1.371-.999-.78-2.422-1.818 2.425 2.598-.003zM1.499 5.5l5.113 6.817-2.192-6.82L1.5 5.5zm7.889 6.817 5.123-6.83-2.928.002-2.195 6.828z"></path>
                </svg>
                <span class="fs-4">&nbsp;Diamond Restaurant &#183; Payment</span>
            </a>
        </div>
    </header>

    <main>
        <div class="container-fluid">
            <div class="row d-flex justify-content-center">
                <div class="col-sm-12">
                    <div class="card mx-auto">
                        <p class="heading text-uppercase text-muted">Payment details</p>
                        <form class="card-details" action="payment" method="post">
                            <div class="form-group mb-0">
                                <p class="text-warning mb-0">Card Number</p>
                                <label for="cno"></label>
                                <input type="text" name="cardNumber" placeholder="1234 5678 9012 3457" size="15"
                                       id="cno" minlength="16" maxlength="16"
                                       required pattern="^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14})$">
                                <img src="https://img.icons8.com/color/48/000000/visa.png" width="64px" height="60px"
                                     alt=""/>
                                <img src="https://img.icons8.com/color/48/000000/mastercard.png" width="64px"
                                     height="60px"
                                     alt=""/>
                            </div>
                            <div class="form-group">
                                <p class="text-warning mb-0">Cardholder's Name</p>
                                <label>
                                    <input type="text" name="name" placeholder="Name" size="16" required>
                                </label>
                            </div>
                            <div class="form-group pt-2">
                                <div class="row d-flex">
                                    <div class="col-sm-4">
                                        <p class="text-warning mb-0">Expiration date</p>
                                        <label for="exp"></label>
                                        <input type="text" name="expiration" placeholder="MM/YYYY" size="6" id="exp"
                                               minlength="7" maxlength="7"
                                               required pattern="^(0[1-9]|1[0-2])\/?([0-9]{4}|[0-9]{2})$">
                                    </div>
                                    <div class="col-sm-3">
                                        <p class="text-warning mb-0">CVV</p>
                                        <label>
                                            <input type="password" name="cvv" placeholder="&#9679;&#9679;&#9679;"
                                                   size="1" minlength="3" maxlength="3" required pattern="^[0-9]{3}$">
                                        </label>
                                    </div>
                                    <div class="row col-sm-4 pt-0" style="margin-left: 30px">
                                        <div class="col-lg-6">
                                            <button type="button" class="btn btn-danger" onclick="function cancel() {
                                              location.href = 'cancelOrder';
                                            }cancel()"
                                                    style="padding: 5px 10px 5px 10px">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                                     fill="currentColor" class="bi bi-x" viewBox="0 0 16 16">
                                                    <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"></path>
                                                </svg>
                                            </button>
                                        </div>
                                        <div class="col-lg-6">
                                            <button type="submit" class="btn btn-primary"
                                                    style="padding: 5px 10px 5px 10px">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                                     fill="currentColor" class="bi bi-check" viewBox="0 0 16 16">
                                                    <path d="M10.97 4.97a.75.75 0 0 1 1.07 1.05l-3.99 4.99a.75.75 0 0 1-1.08.02L4.324 8.384a.75.75 0 1 1 1.06-1.06l2.094 2.093 3.473-4.425a.267.267 0 0 1 .02-.022z"></path>
                                                </svg>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <footer class="footer mt-auto py-3 mx-auto text-center">
        <div class="container">
            <span class="text-light"> &copy; 2021 Diamond Restaurant.</span>
        </div>
    </footer>
</div>
</body>
</html>