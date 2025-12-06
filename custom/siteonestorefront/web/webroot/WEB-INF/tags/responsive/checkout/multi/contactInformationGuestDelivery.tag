<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="multiCheckout"
	tagdir="/WEB-INF/tags/responsive/checkout/multi"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="multi-checkout"
	tagdir="/WEB-INF/tags/responsive/checkout/multi"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement"
	tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="address" tagdir="/WEB-INF/tags/responsive/address"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="multiCheckout"
	tagdir="/WEB-INF/tags/responsive/checkout/multi"%>
<spring:url value="/" var="homelink" htmlEscape="false"/>
<div class="col-md-12">
	<!-- <form:form  id="SiteOneGCDeliveryForm" modelAttribute="SiteOneGCContactForm" action="${homelink}checkout/multi/saveAlternateContactDetails" method="POST"> --> 
		        	<div id="mixedcart-deliveryContactForm" class="deliveryContact-form" style="display:none">	
		        	<div>
					<div class="col-md-12 col-xs-12 contactForm_mixedcart_delivery">
			     		<div class="col-md-12"><span class="addAddressError" ></span></div>
			     		<form:hidden path="deliveryMode" id="deliveryMode" value="standard-net" />
			     		<div class="col-md-12 padding0">
			     		<div class="col-md-6 ">
			     		<formElement:formInputBox idKey="checkoutdeliveryfirstName" labelKey="profile.firstName"  path="firstName" placeholder="profile.firstName" inputCSS="form-control" mandatory="true" />
						<div><span id="errordeliveryFirstName"></span></div> </div>
						
				
			     		<div class="col-md-6">
			     		<formElement:formInputBox idKey="checkoutdeliverylastName" labelKey="profile.lastName" path="lastName" placeholder="profile.lastName" inputCSS="form-control" mandatory="true" />
						<span id="errordeliveryLastName"></span></div>
						</div>
			     		<div class="cl"></div>
			    	     		
			     		<div class="col-md-12">
			     		<formElement:formInputBox idKey="checkoutdelivery.companyName" labelKey="leadForm.companyName" path="companyName" placeholder="optional.text" inputCSS="form-control"/>
			     		</div>
			     		<div class="cl"></div>
						<div class="col-md-12 padding0">
						<div class="col-md-6">
			     		<formElement:formInputBox idKey="checkoutdeliveryphone" labelKey="leadForm.phone" path="phone" maxlength="10" placeholder="leadForm.phone" inputCSS="form-control" mandatory="true"/>
			     		<span id="errordeliveryPhoneNumber"></span></div>
			     		
			     		
			     		<div class="col-md-6">
			     		<formElement:formInputBox idKey="checkoutdeliveryemail" labelKey="homeOwnerComponent.email" path="email" inputCSS="form-control" placeholder="homeOwnerComponent.email"  mandatory="true" />
						<span id="errordeliveryEmailAddress"></span></div></div>

			     		<div class="col-md-12">
	  		 			<formElement:formInputBox idKey="checkoutdeliveryaddressLine1" labelKey="homeOwnerComponent.address" path="addressLine1" mandatory="true" placeholder="homeOwnerComponent.addressLimit" maxlength="50" inputCSS="form-control"/>
						<span id="errordeliveryAddressLine1"></span></div>
			     		<div class="cl"></div>

			     		<div class="col-md-12">
  						<formElement:formInputBox idKey="checkoutdelivery.addressLine2" labelKey="leadForm.address.lin.gc" path="addressLine2" placeholder="leadForm.address.lin.gcLimit" maxlength="50" inputCSS="form-control" mandatory="true" />
						<span class="errordeliveryPostCode"></span></div>
			     		<div class="cl"></div>
						
						<div class="col-md-12 padding0">
						<div class="col-md-5">
  						<formElement:formInputBox idKey="checkoutdeliverycity" labelKey="requestaccount.city" path="city" placeholder="requestaccount.cityLimit" maxlength="50" inputCSS="form-control" mandatory="true" />
						<span id="errordeliveryCity"></span></div>
			     		
						
						<div class="custom_dropdown col-md-4">
						<formElement:customRegionSelectBox idKey="checkoutdeliverystate" labelKey="requestaccount.state" selectCSSClass="form-control" path="state" mandatory="true" skipBlank="false" skipBlankMessageKey="request.form.select.empty" items="${states}"  />
						
						<span id="errordeliveryState"></span>
						</div>
					
			     		
			     		<div class="col-md-3">
  						<formElement:formInputBox idKey="checkoutdeliveryzip" labelKey="leadForm.zip.gc" path="zip" inputCSS="form-control" placeholder="leadForm.zip.gc"  mandatory="true" maxlength="10" />
						<span id="errordeliveryZipcode"></span></div>
			     		</div>
			     		<div class="cl"></div>
						
						 <div class="col-md-12"><button type="button" class="btn btn-primary col-md-4 col-xs- 12 adddeliveryFormSubmitMixedCart"><spring:theme code="choosePickupDeliveryMethodPage.continue.to.payment" /></button></div>
		
				      </div>
				      </div>
				      <div class="cl"></div>
				      </div>
				    <!-- </form:form> -->  
				      
</div>