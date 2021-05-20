<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Restaurant &#183; Order</title>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/payment_style.css">
    <script src="bootstrap/js/bootstrap.bundle.min.js"
            integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
            crossorigin="anonymous"></script>
</head>

<body>
<div class="container py-3">
    <header>
        <div class="d-flex flex-column flex-md-row align-items-center pb-3 mb-4">
            <a href="" class="d-flex align-items-center text-dark text-decoration-none">
                <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" class="me-2" viewBox="0 0 118 94"
                     role="img">
                    <title>Restaurant</title>
                    <path fill-rule="evenodd" clip-rule="evenodd"
                          d="M24.509 0c-6.733 0-11.715 5.893-11.492 12.284.214 6.14-.064 14.092-2.066 20.577C8.943 39.365 5.547 43.485 0 44.014v5.972c5.547.529 8.943 4.649 10.951 11.153 2.002 6.485 2.28 14.437 2.066 20.577C12.794 88.106 17.776 94 24.51 94H93.5c6.733 0 11.714-5.893 11.491-12.284-.214-6.14.064-14.092 2.066-20.577 2.009-6.504 5.396-10.624 10.943-11.153v-5.972c-5.547-.529-8.934-4.649-10.943-11.153-2.002-6.484-2.28-14.437-2.066-20.577C105.214 5.894 100.233 0 93.5 0H24.508zM80 57.863C80 66.663 73.436 72 62.543 72H44a2 2 0 01-2-2V24a2 2 0 012-2h18.437c9.083 0 15.044 4.92 15.044 12.474 0 5.302-4.01 10.049-9.119 10.88v.277C75.317 46.394 80 51.21 80 57.863zM60.521 28.34H49.948v14.934h8.905c6.884 0 10.68-2.772 10.68-7.727 0-4.643-3.264-7.207-9.012-7.207zM49.948 49.2v16.458H60.91c7.167 0 10.964-2.876 10.964-8.281 0-5.406-3.903-8.178-11.425-8.178H49.948z"
                          fill="currentColor"></path>
                </svg>
                <span class="fs-4">Restaurant &#183; Payment</span>
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
                                              location.href = '/cancelOrder';
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
            <span class="text-muted"> &copy; 2021 Restaurant.</span>
        </div>
    </footer>
</div>
</body>
</html>