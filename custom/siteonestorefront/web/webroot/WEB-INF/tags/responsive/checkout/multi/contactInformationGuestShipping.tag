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
	<div id="mixedcart-shipmentForm" class="shipping-mixedcart-form marginTop35" style="display:none;">	
		<!-- <form:form  id="SiteOneGCShippingForm" modelAttribute="SiteOneGCContactForm" action="${homelink}checkout/multi/saveAlternateContactDetails" method="POST"> -->	
			<div class="row col-md-12 col-xs-12 contactForm_parcel-shipping padding0">
			     		<div class="col-md-12"><span class="addAddressError" ></span></div>
			     		<div class="col-md-12 padding0">
			     		<div class="col-md-6 ">
			     		<form:hidden path="deliveryMode" id="deliveryMode" value="shipping" />
			     		<formElement:formInputBox idKey="checkoutShippingfirstName" labelKey="profile.firstName"  path="firstName" placeholder="profile.firstName" inputCSS="form-control" mandatory="true" />
						<div><span id="errorShippingFirstName"></span></div> </div>
						
				
			     		<div class="col-md-6">
			     		<formElement:formInputBox idKey="checkoutShippinglastName" labelKey="profile.lastName" path="lastName" placeholder="profile.lastName" inputCSS="form-control" mandatory="true" />
						<span id="errorShippingLastName"></span></div>
						</div>
			     		<div class="cl"></div>
			    	     		
			     		<div class="col-md-12">
			     		<formElement:formInputBox idKey="checkoutShipping.companyName" labelKey="leadForm.companyName" path="companyName" placeholder="optional.text" inputCSS="form-control"/>
			     		</div>
			     		<div class="cl"></div>
						<div class="col-md-12 padding0">
						<div class="col-md-6">
			     		<formElement:formInputBox idKey="checkoutShippingphone" labelKey="leadForm.phone" path="phone" maxlength="10" placeholder="leadForm.phone" inputCSS="form-control" mandatory="true"/>
			     		<span id="errorShippingPhoneNumber"></span></div>
			     		
			     		
			     		<div class="col-md-6">
			     		<formElement:formInputBox idKey="checkoutShippingemail" labelKey="homeOwnerComponent.email" path="email" inputCSS="form-control" placeholder="homeOwnerComponent.email"  mandatory="true" />
						<span id="errorShippingEmailAddress"></span></div></div>
						
												
						
						<div class="col-md-12">
	  		 			<formElement:formInputBox idKey="checkoutShippingaddressLine1" labelKey="homeOwnerComponent.address" path="addressLine1" mandatory="true" placeholder="homeOwnerComponent.addressLimit" maxlength="50" inputCSS="form-control"/>
						<span id="errorShippingAddressLine1"></span></div>
			     		<div class="cl"></div>

			     		<div class="col-md-12">
  						<formElement:formInputBox idKey="checkoutShipping.addressLine2" labelKey="leadForm.address.lin.gc" path="addressLine2" placeholder="leadForm.address.lin.gcLimit" maxlength="50" inputCSS="form-control" mandatory="true" />
						<span class="errorShippingPostCode"></span></div>
			     		<div class="cl"></div>
						
						<div class="col-md-12 padding0">
						<div class="col-md-6">
  						<formElement:formInputBox idKey="checkoutShippingcity" labelKey="requestaccount.city" path="city" placeholder="requestaccount.cityLimit" maxlength="50" inputCSS="form-control" mandatory="true" />
						<span id="errorShippingCity"></span></div>
			     		
						
						<div class="custom_dropdown col-md-2">
							<formElement:customRegionSelectBox idKey="checkoutShippingstate" labelKey="requestaccount.state" selectCSSClass="form-control" path="state" mandatory="true" skipBlank="false" skipBlankMessageKey="request.form.select.empty" items="${states}"  />
						</div>
					
			     		
			     		<div class="col-md-4">
  						<formElement:formInputBox idKey="checkoutShippingzip" labelKey="leadForm.zip.gc" path="zip" inputCSS="form-control" placeholder="leadForm.zip.gc"  mandatory="true" maxlength="10" />
						<span id="errorShippingZipcode"></span></div>
			     		</div>
			     		<div class="col-xs-12">
			     			<span id="errorShippingState"></span>
			     		</div>
			     		<div class="cl"></div>
						
		
	      </div>
	       <div class="cl"></div>
	      <div class="col-md-4 col-xs-12 padding0"><button type="button" class="btn btn-primary btn-block  mixedcart-addShippingFormSubmit"> <spring:theme code="choosePickupDeliveryMethodPage.continue.to.payment" /></button></div>
	      <!-- </form:form> -->
	      </div> 
</div>