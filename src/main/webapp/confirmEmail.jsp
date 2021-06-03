<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@include file="WEB-INF/localization.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title} &#183; ${email_confirm}</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/confirm_email_style.css">
    <script src="bootstrap/js/bootstrap.bundle.min.js"
            integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
            crossorigin="anonymous"></script>
    <style>
        <%@include file="bootstrap/css/bootstrap.min.css"%>
        <%@include file="css/confirm_email_style.css"%>
    </style>
</head>

<body onload="function closeTabAfterTimeout() {
                    window.setTimeout(window.close, 3000);
              }closeTabAfterTimeout()">
<div class="modal-dialog modal-confirm">
    <div class="modal-content">
        <div class="modal-header">
            <div class="icon-box">
                <i class="material-icons">&#xE876;</i>
            </div>
            <h4 class="modal-title w-100">${email_confirm_success}</h4>
        </div>
        <div class="modal-body">
            <p class="text-center">${email_confirm_success_info}</p>
        </div>
        <div class="modal-footer">
            <button class="btn btn-success btn-block" data-dismiss="modal" onclick="function closeTabAfterTimeout() {
                                                                                        window.setTimeout(window.close, 100);
                                                                                    }closeTabAfterTimeout()">OK
            </button>
        </div>
    </div>
</div>
</body>
</html>