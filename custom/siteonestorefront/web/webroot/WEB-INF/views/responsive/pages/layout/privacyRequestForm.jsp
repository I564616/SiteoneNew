<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="formElement"
	tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="sec"
		   uri="http://www.springframework.org/security/tags"%>
<script src="https://www.google.com/recaptcha/api.js"></script>
<sec:authorize access="hasAnyRole('ROLE_ANONYMOUS')">
<div class="global-alerts">	
		
		<div class="alert alert-info alert-dismissable">
					<button class="close" aria-hidden="true" data-dismiss="alert" type="button">×</button>
					<spring:theme code="privacyrequest.message.anonymous"/></div>
</div>
</sec:authorize>
<h1 class="headline"><spring:theme code="privacyrequest.headline"></spring:theme> </h1>
	<div class="col-md-12 marginTop35">
	<div class="row">
		<div class="col-md-7">
			<div class="row">
				<p class="bold-text store-specialty-heading"><spring:theme code="privacyrequest.form"/></p>
				<p class="small-font margin20"><span class="text-red">* </span><spring:theme code="require.fields.privacy"/></p>
		 <c:url value="/privacyrequest" var="privacyrequest"/>
 <form:form action="${privacyrequest}" method="POST" modelAttribute="siteoneCCPAForm" id="siteoneCCPAForm">
                  <div class="form-group select-grey">
		 			<form:label path="state"><spring:theme code="privacyrequest.state" /></form:label><span class="text-red"> *</span>
		 			  <br/>
		 			<form:select path="state" id="state" class="form-control" >
		 			<form:option value=""><spring:theme code="privacyrequest.state.select" /></form:option>
		 			<form:options items="${states}"></form:options>
		 			</form:select>
		 			<div class="cl"></div>
			 		<div class="has-error"><span id="errorcustomerstate"  class="help-block" style="font-weight:normal;"></span> </div>
	 				</div>
		 		<div class="form-group select-grey">
                  	 <form:label path="privacyRequestType"><spring:theme code="privacyrequest.type" /></form:label><span class="text-red"> *</span>
                   <br/>
                   <form:select path="privacyRequestType" class="form-control" id="privacyRequestType">
                   <form:option value=""><spring:theme code="contactUsForm.select" /></form:option>
                   <form:options items="${requestType}"></form:options>
                   </form:select>
                    <div class="has-error"><span id="errorselectrequest" class="help-block" style="font-weight:normal;"></span></div>
		 		</div>
		 		
		 		 <div class="form-group">
                      <form:label path="firstName"><spring:theme code="contactUsForm.firstname" /></form:label><span class="text-red"> *</span>
                       
                      <form:input path="firstName" id="firstName" class="form-control" />
                      
                      <div class="has-error"><span id="errorfirstname" class="help-block" style="font-weight:normal;"></span></div>
                      
                      </div>
                    <div class="form-group">
                      <form:label path="lastName"><spring:theme code="contactUsForm.lastname" /></form:label><span class="text-red"> *</span>
                      <div class="cl"></div>
                      <form:input path="lastName"  id="lastName" class="form-control"/>
                      <div class="has-error"><span id="errorlastname" class="help-block" style="font-weight:normal;"></span></div>
  
                  </div>
		 		<div class="form-group email-validate">
		 			<formElement:formInputBox idKey="emailaddress" labelKey="requestaccount.email" path="emailAddress" mandatory="true" inputCSS="form-control" autocomplete="off"/><span class="text-red"> *</span>
		
		 			<div class="cl"></div>
					<div><span id="errorEmailAddress"></span></div>
		 		</div>
		 		
		 			<spring:message code="contactUsForm.phoneNumber" var="phoneNumber"/>
                      <div class="form-group">
                      <formElement:formInputBox idKey="phonenumbercontact" labelKey="${phoneNumber}" path="phoneNumber" mandatory="true" inputCSS="form-control" />
                      <div class="cl"></div>
			 		<span id="errorphonenumber" class="text-red"></span> 
		 		</div>
		 		
		 		<div class="form-group">
		 			<form:label path="companyName"><spring:theme code="requestaccount.company.name" /></form:label>  
		 			<form:input idKey="ccpaCompanyName" path="companyName"  id="companyName-ccpa" class="form-control"/>
		 			<div class="cl"></div>
			 		<span id="errorCompanyName"  class="text-red"></span> 
		 		</div>
		 		
		 		<div class="form-group">
		 			<form:label path="accountNumber"><spring:theme code="contactUsForm.siteone.acc.no" /></form:label>  
		 			<form:input path="accountNumber" id="accountNumber" class="form-control"/>
		 			<div class="cl"></div>
		 			<span id="accountNumberError"  class="text-red"></span>
		 		</div>
		 		
		 		<div class="form-group">
		 		<spring:message code="privacy.address.placeholder" var="address1label"/>
		 			<form:label path="addressLine1"><spring:theme code="privacyrequest.address" /></form:label>
			     <br />
			       <form:input idKey="address-ccpa" path="addressLine1" id="addressLine1-ccpa" class="form-control" placeholder="${address1label}"/>
			     <span id="addressError" style="color: red;"></span> 
		 		</div>
		 		<div class="form-group">
		 		<spring:message code="privacy.address2.placeholder" var="address1label2"/>
		 			<form:input idKey="address2-ccpa" path="addressLine2" class="form-control" placeholder="${address1label2}"/>
			      <span id="addressError2" style="color: red;"></span> 
		 		</div>
		 		<div class="form-group">
		 		<spring:message code="requestaccount.city" var="city"/>
			 		<div class="row">
				 		<div class="col-md-5"><form:input idKey="customerCity" path="city" class="form-control" placeholder="${city}"/> 
				 		
			<div class="cl"></div> <span id="customerCityError" style="color: red;"></span> 
			</div>
			<div class="hidden-md hidden-lg"><br/></div>
			<spring:message code="address.state" var="state"/>
			<spring:message code="address.zipcode" var="zipcode"/>
				 		<div class="col-md-3"><input type="text"  class="form-control" placeholder="${state}"/> </div>
				 		<div class="hidden-md hidden-lg"><br/></div>
				 		<div class="col-md-4"><form:input idKey="customerZipCode" path="zipcode" class="form-control" placeholder="${zipcode}"/>
				 		
			<div class="cl"></div> <span id="customerZipCodeError" style="color: red;"></span></div>
			 		</div>
		 		</div>
		 		
		 		 <div id="recaptcha-border" class="recaptcha-error-border marginTop35">
        <div id="grecaptcha" class="g-recaptcha" data-callback="captcha_onclick" data-sitekey="${recaptchaPublicKey}"></div>
	     <input type="hidden" name="recaptcha" id="recaptchaValidator" />
	     <input type="hidden" id="recaptchaChallengeAnswered" value="${recaptchaChallengeAnswered}" />
	   
	     <div id="recaptcha-error" class="hidden" style="color: rgb(254, 3, 3); font-weight: normal;">
	      <spring:theme code="recaptcha.error"/> 
	     </div>
	      </div>
	      <div class="cl"></div>
		      <div class="row">
				 <div class="col-md-6 col-sm-6 col-xs-12 margin20"><input type="button" value="<spring:theme code="privacyrequestForm.sendEmail"/>" class="btn btn-primary btn-block privacyRequest"></div>
			 </div>
	      
		 	</form:form>	
		 		
	 		</div>
		 </div>
		 
		
		 </div>
	</div>