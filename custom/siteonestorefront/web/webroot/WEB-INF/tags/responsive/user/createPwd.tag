<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>

<div class="create-password">
	<div class="description"><spring:theme code="createPwd.description"/></div>
	<form:form method="post" modelAttribute="createPwdForm">
		<div class="control-group">
			<ycommerce:testId code="login_createPasswordEmail_input">
				<formElement:formInputBox idKey="createPwd.email" labelKey="createPwd.email" path="email" mandatory="true"/>
			</ycommerce:testId>
			<ycommerce:testId code="login_createPasswordSubmit_button">
				<button class="btn btn-primary btn-block" type="submit">
					<spring:theme code="createPwd.title"/>
				</button>
			</ycommerce:testId>
		</div>
	</form:form>
</div>
