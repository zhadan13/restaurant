<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../localization.jsp" %>

<button type="button" class="btn button border text-muted" data-bs-toggle="dropdown" aria-expanded="false">
    <span class="button-span" style="font-size: 15px">${account}</span>
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