<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Yriy Petrashenko">

    <title><fmt:message key="order.details.title"/></title>

    <link href="${pageContext.request.contextPath}/css/agency.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/vendor/fontawesome-free/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Kaushan+Script' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700' rel='stylesheet' type='text/css'>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">

</head>

<body id="page-top">

<!-- Navigation -->
<c:import url="menu.jsp"/>


<section class="page-section" id="services">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <h2 class="section-heading text-uppercase"><fmt:message key="admin.order.details.title.body"/></h2>
                <h3 class="section-subheading text-muted"><fmt:message key="admin.order.details.body.subtitle"/></h3>

                <c:if test="${not empty sessionScope.successMessage}">
                    <p class="text-success" ><fmt:message key="${sessionScope.successMessage}"/></p>
                </c:if>
                <c:remove var="successMessage" scope="session" />

                <table class="table table-striped profile">
                    <tbody>
                    <tr>
                        <td ><span class="profile-header"><fmt:message key="user.orders.table.id"/></span></td>
                        <td>${order.id}</td>
                    </tr>
                    <tr>
                        <td><span class="profile-header"><fmt:message key="user.orders.table.date"/></span></td>
                        <td><tags:localDateTime date="${order.dateTime}" /></td>
                    </tr>
                    <tr>
                        <td><span class="profile-header"><fmt:message key="all.orders.table.client.name"/></span></td>
                        <td>${order.client.name} ${order.client.surname}</td>
                    </tr>
                    <tr>
                        <td><span class="profile-header"><fmt:message key="all.orders.table.client.email"/></span></td>
                        <td>${order.client.email}</td>
                    </tr>
                    <tr>
                        <td><span class="profile-header"><fmt:message key="all.orders.table.client.phone"/></span></td>
                        <td>${order.client.phone}</td>
                    </tr>
                    <tr>
                        <td><span class="profile-header"><fmt:message key="all.orders.table.master.name"/></span></td>
                        <td>${order.master.name} ${order.master.surname}</td>
                    </tr>
                    <tr>
                        <td><span class="profile-header"><fmt:message key="user.orders.table.instrument.brand"/></span></td>
                        <td>${order.instrument.brand}</td>
                    </tr>
                    <tr>
                        <td><span class="profile-header"><fmt:message key="user.orders.table.instrument.model"/></span></td>
                        <td>${order.instrument.model}</td>
                    </tr>
                    <tr>
                        <td><span class="profile-header"><fmt:message key="user.orders.table.instrument.year"/></span></td>
                        <td>${order.instrument.manufactureYear}</td>
                    </tr>
                    <tr>
                        <td><span class="profile-header"><fmt:message key="user.orders.table.service"/></span></td>
                        <td>${order.service}</td>
                    </tr>
                    <tr>
                        <td><span class="profile-header"><fmt:message key="user.orders.table.price"/></span></td>
                        <c:choose>
                            <c:when test="${order.price eq 0.0}">
                                <td> </td>
                            </c:when>
                            <c:otherwise>
                                <td>$<fmt:formatNumber value = "${order.price}" type = "number" maxFractionDigits = "2" minFractionDigits="2"/></td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    <tr>
                        <td><span class="profile-header"><fmt:message key="user.orders.table.status"/></span></td>
                        <td><fmt:message key="${order.status.localeDescription}"/></td>
                    </tr>

                    <c:if test="${order.status.name() eq 'REJECTED'}">
                        <tr>
                            <td><span class="profile-header"><fmt:message key="user.orders.table.rejection.reason"/></span></td>
                            <td>${order.rejectionReason}</td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
                <c:if test="${order.status.name() eq 'NEW'}">
                    <div class="row text-center">
                        <div class="limiter">
                            <div class="container-login100">
                                <div class="wrap-login100 p-t-50 p-b-90">
                                    <form class="login100-form validate-form flex-sb flex-w" action="/admin/accept-order" method="post">
                                        <input type="hidden" name="orderId" value="${order.id}">

                                        <div class="wrap-input100 validate-input m-b-16 order-details-input" data-validate = "Price is required">
                                            <input class="input100" type="text"  name="price" placeholder=<fmt:message key="user.orders.table.price"/>  required
                                            pattern="^[0-9]+(\.[0-9]+)?$">
                                            <span class="focus-input100"></span>

                                        </div>
                                        <div class="container-login100-form-btn m-t-17 order-details-button">
                                            <button class="login100-form-btn" type="submit" >
                                                <fmt:message key="accept.button"/>
                                            </button>
                                        </div>

                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row text-center">
                        <div class="limiter">
                            <div class="container-login100">
                                <div class="wrap-login100 p-t-50 p-b-90">
                                    <form class="login100-form validate-form flex-sb flex-w" action="/admin/reject-order" method="post">
                                        <input type="hidden" name="orderId" value="${order.id}">

                                        <div class="wrap-input100 validate-input m-b-16 order-details-input" data-validate = "Reason is required">
                                            <input class="input100" type="text"  name="reason" placeholder=<fmt:message key="admin.order.details.reason"/>  required >
                                            <span class="focus-input100"></span>

                                        </div>
                                        <div class="container-login100-form-btn m-t-17 order-details-button">
                                            <button class="login100-form-btn" type="submit" >
                                                <fmt:message key="reject.button"/>
                                            </button>
                                        </div>

                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>

            </div>
        </div>
    </div>
</section>

<!-- Footer -->
<footer class="bg-light footer">
    <div class="container">
        <div class="row align-items-center">
            <div class="col-md-4">
                <span class="copyright">Copyright &copy; Guitar Service 2020</span>
            </div>
            <div class="col-md-4">
                <img src="${pageContext.request.contextPath}/img/brands.jpg">
            </div>
            <div class="col-md-4">
                <ul class="list-inline quicklinks">

                </ul>
            </div>
        </div>
    </div>
</footer>



<!-- Bootstrap core JavaScript -->
<script src="${pageContext.request.contextPath}/vendor/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Plugin JavaScript -->
<script src="${pageContext.request.contextPath}/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Contact form JavaScript -->
<script src="${pageContext.request.contextPath}/js/jqBootstrapValidation.js"></script>
<script src="${pageContext.request.contextPath}/js/contact_me.js"></script>


</body>

</html>