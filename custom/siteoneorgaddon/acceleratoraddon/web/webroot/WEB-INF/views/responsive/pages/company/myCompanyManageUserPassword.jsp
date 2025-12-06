<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:htmlEscape defaultHtmlEscape="true" />
<%-- <spring:url value="/my-company/organization-management/manage-users/resetpassword" var="resetpasswordUrl">
	<spring:param name="unit" value="${unit}" />
    <spring:param name="user" value="${customerResetPasswordForm.uid}" />
</spring:url> --%>
<c:set var="resetpasswordUrl" value="/my-company/organization-management/manage-users/resetpassword?unit=${unit}&user=${customerResetPasswordForm.uid}"/>

<template:page pageTitle="${pageTitle}">
    <div class="account-section">
        <div class="col-md-6">
            <div class="row">
                    <h1 class="headline"><spring:theme code="text.account.profile.updatePasswordForm" /></h1>
                </div>
            </div>
            <div class="cl"></div>
        <div class="row">
            <div class="col-md-4">
                 
                    <br/>
                        <form:form action="${resetpasswordUrl}" method="post" modelAttribute="customerResetPasswordForm" autocomplete="off">
                            <form:input type="hidden" name="uid" path="uid" id="uid" />
                            <formElement:formPasswordBox idKey="profile-newPassword" labelKey="profile.newPassword" labelCSS="control-label" path="newPassword" inputCSS="form-control text password strength" mandatory="true" />
                            <formElement:formPasswordBox idKey="profile.checkNewPassword" labelKey="profile.checkNewPassword" labelCSS="control-label" path="checkNewPassword" inputCSS="form-control text password" mandatory="true" />
                            <br/>
                            <div class="accountActions col-md-6 col-xs-12">
                                <button class="btn btn-primary btn-block">
                                    <spring:theme code="text.account.profile.updatePasswordForm" />
                                </button>
                            </div>
                        </form:form>
                     
                
            </div>
        </div>
    </div>
</template:page>