<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="password" tagdir="/WEB-INF/tags/responsive/password" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>


<div class="forgotten-password forgotten-pass">
<div class="row">
<div class="col-md-6 col-xs-12">
	

	<div class="description margin20"><spring:theme code="forgottenPwd.description"/></div>
	<div class="col-md-7 col-xs-12">
	<div class="row">
	<form:form method="post" modelAttribute="forgottenPwdForm" action="<c:url value="/login/pw/request"/>">
	<div class="row">
		<div class="control-group">
			<ycommerce:testId code="login_forgotPasswordEmail_input">
				<formElement:formInputBox idKey="forgottenPwd-emailNew" labelKey="" path="email" mandatory="true" placeholder="<spring:theme code='account.forgottenPwd.customer.email'/>"/>
			</ycommerce:testId>
	        <c:if test ="${not empty isLinkCreated &&  isLinkCreated eq false}">
	           <spring:theme code="account.resetpassword.invalid.customer"/>
	        </c:if>
	        <div class="help-block" style="display:none;"><b>Please enter an email address.</b></div>
			<ycommerce:testId code="login_forgotPasswordSubmit_button">
				<a class="btn btn-primary forgotPass">
					<spring:theme code="forgottenPwd.submit"/>
				</a>
				<div class="margin20"><u><a class="signInOverlay" href="<c:url value="/login"/>" id="backToSignIn"><spring:theme code="forgottenPwd.backToSignIn"/>&#8594;</a></u></div>
			</ycommerce:testId>
		</div>
	</div>	
	</form:form>
	</div>
	</div>
		</div>
	</div>	
</div>


<div id="fgtPwdResponseSection" style="display:none;">

<c:if test="${empty isValidCustomer && empty isInvalidEmail}">
   <div class="alert positive forgotten-password" id="validEmail" tabindex="0">
	<spring:theme code="account.confirmation.forgotten.password.link.sent"/>
	<a class="btn btn-primary btn-block" href="<c:url value="/login"/>">Back to Sign In</a>
   </div>
</c:if>

</div>
