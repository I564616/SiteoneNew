<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user" %>

<c:url value="/checkout/j_spring_security_check" var="loginAndCheckoutActionUrl" />

<c:url value="/login/checkout/auth" var="ssoLoginActionUrl" />
<user:login actionNameKey="checkout.login.loginAndCheckout" action="${loginAndCheckoutActionUrl}"/>

