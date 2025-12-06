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
 
<div class="cl"></div>
<div class="col-md-6 col-sm-12 col-xs-12">
<script src="https://www.google.com/recaptcha/api.js"></script>
<div class="cl"></div>
		  <br/>
		 		<!-- Customer information section -->
		 		 <div class="cl item-sec-border col-md-10"></div>
		 		 	<br/>
		 		 	<div class="cl"></div>
		  <br/><p></p>
		 		 	

<c:url value="/homeowner/form" var="homeowner"></c:url>
	<form:form action="${homeowner}" method="POST"
		modelAttribute="siteOneHomeOwnerForm">
		 <div class="col-md-8 col-sm-12 col-xs-12 row">
		 
<p class="store-specialty-heading bold-text"><spring:theme code="text.customer.information"/></p> 
 	<div class="margin20">
		 	<div class="form-group">
			<form:label path="firstName"><spring:theme code="leadForm.firstName"/></form:label>
			<br />
			<form:input idKey="firstName" path="firstName" class="form-control" />
			<div class="cl"></div>
			<span id="errorfirstname" style="color: red;"></span>
			 </div>


			<div class="form-group">
			<form:label path="lastName"><spring:theme code="leadForm.lastName"/></form:label>
			<br />
			<form:input idKey="lastName" path="lastName" class="form-control" />
			<div class="cl"></div>
			 <span id="errorlastname" style="color: red;"></span>  
			</div>
			
			
			<div class="form-group">
			<form:label path="emailAddr"><spring:theme code="homeOwnerComponent.email" /></form:label>
			<br />
			<form:input idKey="emailAddr" path="emailAddr" class="form-control" autocomplete="off"/>
			<div class="cl"></div>
			<span id="erroremailaddr" style="color: red;"></span>  
			</div>
			
			<div class="form-group">

			<formElement:formInputBox idKey="homeownerphone" labelKey="leadForm.phone" path="phone" mandatory="true" inputCSS="form-control" />
			<%-- <form:label path="phone"><spring:theme code="leadForm.phone"/></form:label>
			<br />
			<form:input idKey="homeownerphone" path="phone" class="form-control"/> --%>
			<div class="cl"></div>
			 <span id="errorhomeownerphone" style="color: red;"></span>
			</div>
			
			<div class="form-group">			
			<form:label path="address"><spring:theme code="homeOwnerComponent.address" /></form:label>
			<br />
			<form:input idKey="address" path="address"  class="form-control"/>
			<div class="cl"></div>
			 <span id="erroraddress" style="color: red;"></span> 
			</div>
			
			
			
			
			<div class="form-group">
			<form:label path="customerCity"><spring:theme code="homeOwnerComponent.city" /></form:label>
			<br />
			<form:input idKey="customerCity" path="customerCity" class="form-control"/>
			<div class="cl"></div>
			 <span id="errorcustomercity" style="color: red;"></span>  
			</div>
			
			<div class="form-group select-grey ">
			<form:label path="customerState"><spring:theme code="${currentBaseStoreId eq 'siteone' ? 'homeOwnerComponent.state' : 'homeOwnerComponent.province' }" /></form:label>
			<br />
			<form:select idKey="customerState" path="customerState" class="form-control"
				items="${states}" />
			<div class="cl"></div>
			 <span id="errorcustomerstate" style="color: red;"></span> 
			</div>


			<div class="form-group ">
			<form:label path="customerZipCode"><spring:theme code="${currentBaseStoreId eq 'siteone' ? 'text.zipcode.homeowner' : 'text.postcode.homeowner' }"/></form:label>
			<br />
			<form:input idKey="customerZipCode" path="customerZipCode" class="form-control"/>
			<div class="cl"></div>
			 <span id="errorcustomerzipcode" style="color: red;"></span>  
			
			</div>
	
	
	
	
	
	
	
	
	
               </div>
		 </div>
		 <div class="cl"></div>
		  <br/>
		 		<!-- Request information section -->
		 		 <div class="cl item-sec-border col-md-10"></div>
		 		 	<br/>
		 		 	<div class="cl"></div>
		  <br/><p></p>
		  	 <div class="col-md-8 col-sm-12 col-xs-12 row">
		  	 <p class="store-specialty-heading bold-text"><spring:theme code="text.request.information"/></p>
			<br/>
			<div class="form-group select-grey">
				<label for="bestTimeToCall"><spring:theme code="text.best.time.contractor.call"/></label>
			<form:select idKey="bestTimeToCall" path="bestTimeToCall" items="${bestTimeToCall}" class="form-control"/>
			</div>
				 
		 
		    <div class="form-group select-grey">
			 <label for="serviceType"><spring:theme code="text.service.type.needed"/></label>
			<form:select idKey="serviceType" path="serviceType" items="${serviceType}" class="form-control"/>
			<div class="cl"></div>
			<span id="errorsrevicetype"></span>
            </div>
            
		    <div class="form-group select-grey">
			 <label for="referalsNo"><spring:theme code="text.how.many.referral"/></label>
			 <form:select idKey="referalsNo" path="referalsNo" 	items="${referralNo}" class="form-control"/>
                <span id="errorreferalno"></span>
            </div>
		  	 
		  	 </div>
		  
		 <p class="cl"></p>
			<p class="black-title"> <spring:theme code="text.please.select.looking.for"/></p> 

           	<div class="col-md-8 col-sm-12 col-xs-12 row" role="group" aria-label="Interested In"> 
		 
		    <div class="form-group">

			 <span class="colored homeowner-checkbox pull-left"><spring:message code="homeownercomponent.landscaping" var="landscaping" /><form:checkbox path="lookingFor" value="${landscaping}" /> </span><label for="lookingFor1"><spring:theme code="homeownercomponent.landscaping" /></label> 

            </div>	 
		  <div class="cl"></div>
		    <div class="form-group">
          <span class="colored homeowner-checkbox pull-left"><spring:message code="homeownercomponent.lawn.maintenance" var="maintenance" /> <form:checkbox path="lookingFor" value="${maintenance}" /> </span><label for="lookingFor2"><spring:theme code="homeownercomponent.lawn.maintenance" /></label>
       </div>	 
		 	 <div class="cl"></div>
		    <div class="form-group">

			 <span class="colored homeowner-checkbox pull-left"><spring:message code="homeownercomponent.sprinkler" var="sprinkler" /><form:checkbox path="lookingFor"	value="${sprinkler}" /></span><label for="lookingFor3"><spring:theme code="homeownercomponent.sprinkler" /></label>
            </div>	 
		 	 <div class="cl"></div>
		 
		 	<div class="form-group">
			 <span class="colored homeowner-checkbox pull-left"><spring:message code="homeownercomponent.lighting" var="lighting" /><form:checkbox path="lookingFor" value="${lighting}" /></span> <label for="lookingFor4"><spring:theme code="homeownercomponent.lighting" /></label>
            </div>	 
		     <div class="cl"></div>
		 
		 	<div class="form-group">
			 <span class="colored homeowner-checkbox pull-left"><spring:message code="homeownercomponent.kitchen" var="kitchen" /> <form:checkbox path="lookingFor" value="${kitchen}" /></span> <label for="lookingFor5"><spring:theme code="homeownercomponent.kitchen" /></label>
            </div>	 
		 		 <div class="cl"></div> 
			<div class="form-group">
			 <span class="colored homeowner-checkbox pull-left"><spring:message code="homeownercomponent.text5" var="paverwalls" /><form:checkbox path="lookingFor"  value="${paverwalls}" /> </span><label for="lookingFor6"><spring:theme code="homeownercomponent.text5" /></label>
            </div>	
             <div class="cl"></div>
            <div class="form-group"> 
		 	<label for="lookingForOthers"><spring:theme code="homeownercomponent.other" />:</label> 
		 	<input class="form-control"	id="lookingForOthers" name="lookingForOthers" type="text" />
		 	</div>
		 	<br/>
		 	
        <div id="recaptcha-border" class="recaptcha-error-border">
        <div id="grecaptcha" class="g-recaptcha" data-callback="captcha_onclick" data-sitekey="${recaptchaPublicKey}"></div>
	     <input type="hidden" name="recaptcha" id="recaptchaValidator" />
	     <input type="hidden" id="recaptchaChallengeAnswered" value="${recaptchaChallengeAnswered}" />
	   
	     <div id="recaptcha-error" class="hidden" style="color: rgb(254, 3, 3); font-weight: normal;">
	      <spring:theme code="recaptcha.error"/> 
	     </div>
	      </div>
		 	<br/>	<br/>
			 <div class="col-md-6 row col-xs-12"> 
			 <button type="submit" class="homeowner btn btn-primary btn-block" 	id="homeowner" ><spring:theme code="homeownercomponent.submit" /></button>
			 </div>
		 
		 
		 
</div>
	<br/> <br/> <br/>	 

	</form:form>

</div>

<div class="col-md-8" >
	${component.bottomPara}
</div>
 
<div class="cl"></div>
<div class="cl"></div>