<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>


<div class="unlock-user">
<div class="headlineUnlock"><spring:theme code="unlockUser.title"/></div>
<div class="unlock-user-popup">
	<div class="description"><spring:theme code="unlockUser.description"/></div>
	<br>
	<form:form method="post" modelAttribute="siteOneUnlockUserForm">
		<div class="control-group" align="left">
			<ycommerce:testId code="login_unlockUser_input">
				<formElement:formInputBox idKey="unlockUser.email" labelKey="unlockUser.email" path="email" placeholder="Email address" mandatory="true"/>
			</ycommerce:testId>
			<ycommerce:testId code="login_unlockUserSubmit_button">
				<button class="btn btn-primary btn-block" type="submit">
					<spring:theme code="unlockUser.submit"/>
				</button>
				<div class="margin20"><p class="signInOverlay blueLink"><u><spring:theme code="unlockUser.backTo" /> &#8594; </u></p></div>
			</ycommerce:testId>
		</div>
	</form:form>
</div>
</div>