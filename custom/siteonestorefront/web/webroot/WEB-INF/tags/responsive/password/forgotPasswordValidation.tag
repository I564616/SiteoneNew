<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user" %>
<div id="fgtPwdResponseSection">

<c:if test="${empty isValidCustomer && empty isInvalidEmail}">
   <div class="alert positive forgotten-password" id="validEmail" tabindex="0">
	<spring:theme code="account.confirmation.forgotten.password.link.sent"/>
	<button class="btn btn-primary btn-block signInOverlay" id="backToSignIn" type="button"><spring:theme code="forgotPasswordValidation.backTo" /></button>
   </div>
</c:if>


<%-- <c:if test ="${not empty isValidCustomer &&  isValidCustomer eq false}">  --%>
<!-- <!-- <div class="alert positive forgotten-password" id="inValidEmail" tabindex="0"> --> 
<%--  <user:forgottenPwd/>  --%>
<%-- <%-- 	<spring:theme code="account.forgotpassword.invalid.customer"/> --%> 
<!-- <!-- </div> --> 
<%-- </c:if> --%>

<%-- <c:if test ="${not empty isInvalidEmail &&  isInvalidEmail eq true}">  --%>
<!-- <!-- <div class="alert positive forgotten-password" id="inValidEmail" tabindex="0"> --> 
<%--  <user:forgottenPwd/>  --%>
<%-- <%-- 	<spring:theme code="forget.password.invalid.email.error"/> --%> 
<!-- <!-- </div> --> 
<%-- </c:if> --%>

</div>