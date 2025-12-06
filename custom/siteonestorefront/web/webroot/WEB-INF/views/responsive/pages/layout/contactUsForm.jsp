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
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<div class="visible-xs visible-sm hidden-md hidden-lg margin40"></div>
<input type="hidden" class="currentBaseStoreId" value="${currentBaseStoreId eq 'siteone' ? 'US' : 'CA' }"/>
<div class="row">
<script src="https://www.google.com/recaptcha/api.js"></script>
<p class="store-specialty-heading email-us-contact bold-text"><spring:theme code="contactUsForm.email.us" /></p>
<p class="margin20"><spring:theme code="contactUsForm.please.fill" />.</p>
 <div class="col-md-9 row">
<input type="hidden" id="contactusHardscapes" value="${hardscapesContactUs}" />
 <c:choose>
 <c:when test="${hardscapesContactUs eq false}">
 <div id="contactUsForm">
 <c:url value="/contactus" var="contactus"/>
 <form:form action="${contactus}" method="POST" modelAttribute="siteOneContactUsForm" id="siteOneContactUsForm" class="contactus-form">
 
 <div class="form-group select-grey">
 <form:label path="reasonForContact"><spring:theme code="contactUsForm.reason" /></form:label>
 <br/>
 <form:select path="reasonForContact" class="form-control" id="reasonForContact">
 <form:option value=""><spring:theme code="contactUsForm.select" /></form:option>
 <form:options items="${reasonForContact}"></form:options>
 </form:select>
  <div class="has-error"><span id="errorselectreason" class="help-block" style="font-weight:normal;"></span></div>
 
 </div>
  <div class="form-group">
 <form:label path="firstName"><spring:theme code="contactUsForm.firstname" /></form:label>
  
 <form:input path="firstName" id="firstName" class="form-control text-capitalize" />

 <div class="has-error"><span id="errorfirstname" class="help-block" style="font-weight:normal;"></span></div>
 
 </div>
  <div class="form-group">
 <form:label path="lastName"><spring:theme code="contactUsForm.lastname" /></form:label>
 <br/>
 <form:input path="lastName"  id="lastName" class="form-control text-capitalize"/>
 <div class="has-error"><span id="errorlastname" class="help-block" style="font-weight:normal;"></span></div>
  
 </div>
 
  <div class="form-group">
 <form:label path="email" for="contactUsFormEmail"><spring:theme code="contactUsForm.email.address" /></form:label>
 <br/>
 <form:input path="email"  id="contactUsFormEmail" class="form-control" autocomplete="off"/>
  <div class="has-error"><span id="erroremail" class="help-block" style="font-weight:normal;"></span></div>
 
</div>

<spring:message code="contactUsForm.phoneNumber" var="phoneNumber"/>
 <div class="form-group">
 <formElement:formInputBox idKey="phonenumbercontact" labelKey="${phoneNumber}" path="phoneNumber" mandatory="true" inputCSS="form-control" />
 <%-- <form:label path="phoneNumber">Phone number</form:label>
 <br/>
 <form:input path="phoneNumber" id="phonenumbercontact" class="form-control"/> --%>
 
 <div class="has-error"><span id="errorphonenumber" class="help-block" style="font-weight:normal;"></span></div>
 
</div>

 <div class="form-group">
 <form:label path="streetAddress"><spring:theme code="contactUsForm.street.address" /></form:label>
 <br/>
 <form:input path="streetAddress"  id="streetAddress" class="form-control"/>
  <div class="has-error"><span id="errorStreetAddress" class="help-block" style="font-weight:normal;"></span></div>
 
</div>

<div class="form-group">
 <form:label path="customerCity"><spring:theme code="contactUsForm.customerCity" /></form:label>
 <br/>
 <form:input path="customerCity"  id="contact-us-city" class="form-control text-capitalize"/>
 <div class="has-error"><span id="errorCustomerCity" class="help-block" style="font-weight:normal;"></span></div>
  
 </div>
 

<div class="form-group select-grey ${currentBaseStoreId eq 'siteone' ? '' : 'hidden' }">
 <form:label path="customerState"><spring:theme code="contactUsForm.customerState" /></form:label>
 <br/>
 <form:select path="customerState" class="form-control" id="customerState">
 <form:option value=""><spring:theme code="contactUsForm.select" /></form:option>
 <form:options items="${states}"></form:options>
 </form:select>
  <div class="has-error"><span id="errorcustomerstate" class="help-block" style="font-weight:normal;"></span></div>
 
 </div>
 <div class="form-group ${currentBaseStoreId eq 'siteone' ? '' : 'hidden' }">
 <form:label path="projectZipcode"><spring:theme code="contactUsForm.zipcode" /></form:label>
 <br/>
 <form:input path="projectZipcode"  id="projectZipcode" class="form-control"/>
 <div class="has-error"><span id="errorprojectZipcode" class="help-block" style="font-weight:normal;"></span></div>
 </div>

 <div class="form-group">
  <form:label path="customerNumber"><spring:theme code="contactUsForm.siteone.acc.no" /></form:label>
 <br/>
 <form:input path="customerNumber" id="customerNumber" class="form-control"/>
 </div>

 <div class="form-group">
 <p class="black-title"><spring:theme code="contactUsForm.text" />:</p>
 <div class="cl"></div>
 <div class="row" role="radiogroup" aria-label="radiogroup-typeOfCustomer">
 <div class="label-column2">
  <div class="label-highlight">
 <span class="colored-radio"><spring:message code="contactUsForm.contractor" var="contractor" /> <form:radiobutton path="typeOfCustomer" name="typeOfCustomer" value="${contractor}" checked="checked" class="typeOfCustomer"/></span> &nbsp;<label for="typeOfCustomer1" style='color:#5a5b5d'><spring:theme code="contactUsForm.contractor" /></label>
 </div>
 </div>
 <div class="cl hidden-md hidden-lg"><br/></div>
 <div class="label-column2">
 <div class="label-highlight">
 <span class="colored-radio"><spring:message code="contactUsForm.homeowner" var="homeowner" /><form:radiobutton path="typeOfCustomer" name="typeOfCustomer" value="${homeowner}" class="typeOfCustomer"/></span> &nbsp;<label for="typeOfCustomer2" style='color:#5a5b5d'><spring:theme code="contactUsForm.homeowner" /></label>
 </div>
 </div>
  </div>
   <div class="cl"></div>
   </div>
     <div class="has-error"><span id="errorcustomertype" class="help-block" style="font-weight:normal;"></span></div>
    <div class="form-group">
 <form:label path="message"><spring:theme code="contactUsForm.message" /></form:label>
  <div class="cl"></div>
 <form:textarea path="message" id="message" class="form-control" maxlength="1000"/> 
 <input type="hidden" id="contactUsMessage" value="1000">
 
  <span id="message_feedback" style="color:#999;"></span>
  <div class="cl"></div>
  <div class="has-error"><span id="errormessage" class="help-block" style="font-weight:normal;"></span></div>
 
 </div>
  <div class="cl"></div>
 <div id="recaptcha-border" class="recaptcha-error-border">
        <div id="grecaptcha" class="g-recaptcha" data-callback="captcha_onclick" data-sitekey="${recaptchaPublicKey}"></div>
	     <input type="hidden" name="recaptcha" id="recaptchaValidator" />
	     <input type="hidden" id="recaptchaChallengeAnswered" value="${recaptchaChallengeAnswered}" />
	   
	     <div id="recaptcha-error" class="hidden" style="color: rgb(254, 3, 3); font-weight: normal;">
	      <spring:theme code="recaptcha.error"/> 
	     </div>
	      </div>
 
<div class="row">
 <div class="col-md-5 col-sm-6 col-xs-12"><input type="button" value="<spring:theme code="contactUsForm.sendEmail"/>" class="btn btn-primary btn-block contactUs"></div>
 </div>
  </form:form>
    <div class="cl"></div>
  </div>
  <br/><br/>
  <spring:theme code="text.corporate.email" arguments="${siteoneSupportEmail}"/>
  <div class="cl"></div>
  </c:when>
  <c:otherwise>
       <div id="contactUsForm">
       <c:choose>
            <c:when test="${hardscapesContactUs eq true}">
 				<c:url value="/contactus/hardscapes" var="contactusHardscapes"/>
 			</c:when>
	        <c:when test="${lightingEmailContactUs eq true}">
	         	<c:url value="/contactus/lighting" var="contactusLighting"/> 
		    </c:when>
		    <c:when test="${drainageContactUs eq true}">
		    	<c:url value="/contactus/drainage" var="contactusDrainage"/>
		    </c:when>
	   </c:choose>
 <form:form action="${contactus}" method="POST" modelAttribute="siteOneContactUsForm" id="siteOneContactUsForm" class="hardscape-form">
 <div class="form-group select-grey">
 <form:label path="reasonForContact"><spring:theme code="contactUsForm.reason" /></form:label>
 <br/>
 <form:select path="reasonForContact" class="form-control" id="reasonForContact">
 <form:options items="${reasonForContact}"></form:options>
 </form:select>
  <div class="has-error"><span id="errorselectreason" class="help-block" style="font-weight:normal;"></span></div>
 
 </div>
  <div class="form-group">
 <form:label path="firstName"><spring:theme code="contactUsForm.firstname" /></form:label>
  
 <form:input path="firstName" id="firstName" class="form-control text-capitalize" />

 <div class="has-error"><span id="errorfirstname" class="help-block" style="font-weight:normal;"></span></div>
 
 </div>
  <div class="form-group">
 <form:label path="lastName"><spring:theme code="contactUsForm.lastname" /></form:label>
 <br/>
 <form:input path="lastName"  id="lastName" class="form-control text-capitalize"/>
 <div class="has-error"><span id="errorlastname" class="help-block" style="font-weight:normal;"></span></div>
  
 </div>
 
  <div class="form-group">
 <form:label path="email" for="contactUsFormEmail"><spring:theme code="contactUsForm.email.address" /></form:label>
 <br/>
 <form:input path="email"  id="contactUsFormEmail" class="form-control" autocomplete="off"/>
  <div class="has-error"><span id="erroremail" class="help-block" style="font-weight:normal;"></span></div>
 
</div>

<spring:message code="contactUsForm.phoneNumber" var="phoneNumber"/>
 <div class="form-group">
 <formElement:formInputBox idKey="phonenumbercontact" labelKey="${phoneNumber}" path="phoneNumber" mandatory="true" inputCSS="form-control" />
 <%-- <form:label path="phoneNumber">Phone number</form:label>
 <br/>
 <form:input path="phoneNumber" id="phonenumbercontact" class="form-control"/> --%>
 
 <div class="has-error"><span id="errorphonenumber" class="help-block" style="font-weight:normal;"></span></div>
 
</div>

<div class="form-group">
 <form:label path="streetAddress"><spring:theme code="contactUsForm.street.address" /></form:label>
 <br/>
 <form:input path="streetAddress"  id="streetAddress" class="form-control"/>
  <div class="has-error"><span id="errorStreetAddress" class="help-block" style="font-weight:normal;"></span></div>
 
</div>

<div class="form-group">
 <form:label path="customerCity"><spring:theme code="contactUsForm.customerCity" /></form:label>
 <br/>
 <form:input path="customerCity"  id="contact-us-city" class="form-control text-capitalize"/>
 <div class="has-error"><span id="errorCustomerCity" class="help-block" style="font-weight:normal;"></span></div>
  
 </div>

<div class="form-group select-grey">
 <form:label path="customerState"><spring:theme code="contactUsForm.customerState" /></form:label>
 <br/>
 <form:select path="customerState" class="form-control" id="customerState">
 <form:option value=""><spring:theme code="contactUsForm.select" /></form:option>
 <form:options items="${states}"></form:options>
 </form:select>
  <div class="has-error"><span id="errorcustomerstate" class="help-block" style="font-weight:normal;"></span></div>
 
 </div>
 <div class="form-group">
 <form:label path="projectZipcode"><spring:theme code="contactUsForm.projectzipcode" /></form:label>
 <br/>
 <form:input path="projectZipcode"  id="projectZipcode" class="form-control"/>
 <div class="has-error"><span id="errorprojectZipcode" class="help-block" style="font-weight:normal;"></span></div>
 </div>
 <div class="form-group">
 <form:label path="projectStartDate"><spring:theme code="contactUsForm.project.startdate" /></form:label>
 <br/>
 <div class="row">
 <div class="col-md-11 col-xs-10"><form:input path="projectStartDate"  id="projectStartDate" class="form-control projectDatepicker"/></div>
  <div class="pointer padding5"><span class="icon-calendar"></span></div>
 </div>
 </div>
 <input type="hidden" class="lightingEmailContactUs" value="${lightingEmailContactUs}"/>
 <div class="form-group select-grey">
 <form:label path="inPhaseOf"><spring:theme code="contactUsForm.phaseOf" /></form:label>
 <br/>
 <form:select path="inPhaseOf" class="form-control" id="inPhaseOf">
 <form:option value=""><spring:theme code="contactUsForm.select" /></form:option>
 <form:options items="${phaseOf}"></form:options>
 </form:select>
 
 </div>
 <div class="form-group select-grey">
 <form:label path="myProject"><spring:theme code="contactUsForm.project" /></form:label>
 <br/>
 <form:select path="myProject" class="form-control" id="myProject">
 <form:option value=""><spring:theme code="contactUsForm.select" /></form:option>
 <form:options items="${project}"></form:options>
 </form:select>
 
 </div>
 
 <div class="form-group select-grey">
 <form:label path="myBudget"><spring:theme code="contactUsForm.budget" /></form:label>
 <br/>
 <form:select path="myBudget" class="form-control" id="myBudget">
 <form:option value=""><spring:theme code="contactUsForm.select"/></form:option>
 <form:options items="${budget}"></form:options>
 </form:select>
 
 </div>
 
 <div class="form-group select-grey">
 <form:label path="typeOfCustomer"><spring:theme code="contactUsForm.identity" /></form:label>
 <br/>
 <form:select path="typeOfCustomer" class="form-control" id="typeOfCustomer">
 <form:option value=""><spring:theme code="contactUsForm.select" /></form:option>
 <form:options items="${identity}"></form:options>
 </form:select>
 
 </div>
 
<div class="form-group contractor-mycompany-name" style=" display: none;" data-contractorname="<spring:theme code='contactUsForm.contractor.name' />"  data-companyname="<spring:theme code='contactUsForm.company.name' />">
  <form:label path="identityName"></form:label>
  <form:input path="identityName" id="identityName" class="form-control"/>
</div>

 <div class="form-group">
  <form:label path="customerNumber"><spring:theme code="contactUsForm.siteone.acc.no" /></form:label>
 <br/>
 <form:input path="customerNumber" id="customerNumber" class="form-control"/>
 </div>

    <div class="form-group">
 <form:label path="message"><spring:theme code="contactUsForm.message" /></form:label>
  <div class="cl"></div>
 <form:textarea path="message" id="message" class="form-control" maxlength="1000"/> 
 <input type="hidden" id="contactUsMessage" value="1000">
 
  <span id="message_feedback" style="color:#999;"></span>
  <div class="cl"></div>
  <div class="has-error"><span id="errormessage" class="help-block" style="font-weight:normal;"></span></div>
 
 </div>
  <div class="cl"></div>
 <div id="recaptcha-border" class="recaptcha-error-border">
        <div id="grecaptcha" class="g-recaptcha" data-callback="captcha_onclick" data-sitekey="${recaptchaPublicKey}"></div>
	     <input type="hidden" name="recaptcha" id="recaptchaValidator" />
	     <input type="hidden" id="recaptchaChallengeAnswered" value="${recaptchaChallengeAnswered}" />
	   
	     <div id="recaptcha-error" class="hidden" style="color: rgb(254, 3, 3); font-weight: normal;">
	      <spring:theme code="recaptcha.error"/> 
	     </div>
	      </div>
 
<div class="row">
 <div class="col-md-5 col-sm-6 col-xs-12"><input type="button" value="<spring:theme code="contactUsForm.sendEmail"/>" class="btn btn-primary btn-block contactUs"></div>
 </div>
  </form:form>
    <div class="cl"></div>
  </div>
    <br/><br/>
  
  <c:choose>
            <c:when test="${hardscapesContactUs eq true}">
 				<spring:theme code="text.corporate.email.hardscapes"/>
 			</c:when>
	        <c:when test="${lightingEmailContactUs eq true}">
	         	<spring:theme code="text.corporate.email.lighting"/>
		    </c:when>
		    <c:when test="${drainageContactUs eq true}">
		    	<spring:theme code="text.corporate.email.drainage"/>
		    </c:when>
	   </c:choose>
  <div class="cl"></div>
 </c:otherwise>
  </c:choose>
</div>
 </div>