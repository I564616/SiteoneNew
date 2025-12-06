<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<div id="unlockUserResponseSection">

<c:if test="${empty isValidCustomer && empty isInvalidEmail}">
   <div class="alert positive unlock-user" id="validEmail" tabindex="0">
   <div class='headline text-center'><span class='headline-text'><b><spring:theme code="account.user.unlock.email.sent"/></b></span></div>
	<spring:theme code="account.user.unlock.email.confirmation"/><br><br>
	<button class="btn btn-primary btn-block signInOverlay" type="button"><spring:theme code="unlockUser.backTo"/></button>
   </div>
</c:if>


<%-- <c:if test ="${not empty isValidCustomer &&  isValidCustomer eq false}">  --%>
<!-- <div class="alert positive unlock-user" id="inValidEmail" tabindex="0"> -->
<%-- 	<spring:theme code="account.forgotpassword.invalid.customer"/> --%>
<!-- </div> -->
<%-- </c:if> --%>

<%-- <c:if test ="${not empty isInvalidEmail &&  isInvalidEmail eq true}">  --%>
<!-- <div class="alert positive forgotten-password" id="inValidEmail" tabindex="0"> -->
<%-- 	<spring:theme code="forget.password.invalid.email.error"/> --%>
<!-- </div> -->
<%-- </c:if> --%>
</div>