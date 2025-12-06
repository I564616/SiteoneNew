<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="password" tagdir="/WEB-INF/tags/responsive/password" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>


<div class="forgotten-password">

<div class='headline'><span class='headline-text'><spring:theme code="js.forgottenpassword.error"/></span></div>
	<div class="description"><spring:theme code="forgottenPwd.description"/></div>
	<form:form method="post" modelAttribute="forgottenPwdForm">
		<div class="control-group">
			<div id="forgotPwdEmailError" class="marginBottom20"></div>
			<ycommerce:testId code="login_forgotPasswordEmail_input">
				<formElement:formInputBox idKey="forgottenPwd.email" inputCSS="js-forgotpwd-input" labelKey="" path="email" mandatory="true"/>
			</ycommerce:testId>
<%-- 			<c:if test ="${not empty isValidCustomer &&  isValidCustomer eq false}"> --%>
<%-- 	           <spring:theme code="account.forgotpassword.invalid.customer"/> --%>
<%-- 	        </c:if> --%>
	        <c:if test ="${not empty isLinkCreated &&  isLinkCreated eq false}">
	           <spring:theme code="account.resetpassword.invalid.customer"/>
	        </c:if>
			<ycommerce:testId code="login_forgotPasswordSubmit_button">
				<button class="btn btn-primary btn-block js-forgotpwd-submit" type="submit">
					<spring:theme code="forgottenPwd.submit"/>
				</button>
				<div class="margin20"><u><a href="<c:url value="/login"/>" class="signInOverlay" id="backToSignIn"><spring:theme code="forgottenPwd.backToSignIn" /> &#8594;</a></u></div>
			</ycommerce:testId>
		</div>
	</form:form>
	
	

	<%-- <c:if test ="${not empty isInvalidEmail &&  isInvalidEmail eq true}">
	 <spring:theme code="forget.password.invalid.email.error"/>
	</c:if> --%>
</div>
