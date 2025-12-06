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
	<form:form  id="SiteOneGCContactForm" modelAttribute="SiteOneGCContactForm" action="${homelink}checkout/multi/siteOne-checkout/saveContactDetails" method="POST">
			     	<div class="row">
			     		<div class="col-md-12 col-xs-12 contactForm_guest-checkout no-padding-md ">
			     		<div class="col-md-12"><span class="addAddressError" ></span></div>
			     		<div class="col-md-12 no-padding-md">
			     		<div class="col-md-6 ">
			     		<formElement:formInputBox idKey="checkoutContactfirstName" labelKey="profile.firstName"  path="firstName" placeholder="profile.firstName" inputCSS="form-control" mandatory="true" />
						<div><span id="errorFirstName"></span></div> </div>
						
				
			     		<div class="col-md-6">
			     		<formElement:formInputBox idKey="checkoutContactlastName" labelKey="profile.lastName" path="lastName" placeholder="profile.lastName" inputCSS="form-control" mandatory="true" />
						<span id="errorLastName"></span></div>
						</div>
			     		<div class="cl"></div>
			    	     		
			     		<div class="col-md-12 no-padding-md">
			     		<div class="col-md-12">
			     		<formElement:formInputBox idKey="checkoutContact.companyName" labelKey="leadForm.companyName" path="companyName" placeholder="Optional" inputCSS="form-control"/>
			     		</div>
			     		</div>
			     		<div class="cl"></div>
						<div class="col-md-12 no-padding-md">
						<div class="col-md-6">
			     		<formElement:formInputBox idKey="checkoutContactphone" labelKey="leadForm.phone" path="phone" maxlength="10" placeholder="leadForm.phone" inputCSS="form-control" mandatory="true"/>
			     		<span id="errorPhoneNumber"></span></div>
			     		
			     		
			     		<div class="col-md-6">
			     		<formElement:formInputBox idKey="checkoutContactemail" labelKey="homeOwnerComponent.email" path="email" inputCSS="form-control" placeholder="homeOwnerComponent.email"  mandatory="true" />
						<span id="errorEmailAddress"></span></div></div>
						
						
						
						<div class="col-md-12 ">
						<div class="exitEmail-guestCheckout exitEmail-guestCheckout-formerror">
						<spring:theme code="text.email.add.error" /></div>
						<div class="exitEmail-guestCheckout exitEmail-guestCheckout-form">
						<label><b><spring:theme code="checkout.multi.paymentMethod.email"/></b></label>
						<p><spring:theme code="checkout.multi.paymentMethod.email.user"/></p>
						
					</div>
						<div class="col-md-12 exitEmail-guestCheckout-form no-padding-md"><button class="btn btn-primary col-md-12 hidden-xs hidden-sm  signInOverlay exitEmail-guestCheckout-email"><spring:theme code="checkout.multi.paymentMethod.email.sign"/> &nbsp; <svg xmlns="http://www.w3.org/2000/svg" width="14.364" height="14" viewBox="0 0 14.364 14"><defs><style>.a{fill:#fff;}</style></defs><path class="a" d="M6.107,38.588l.712-.712a.766.766,0,0,1,1.087,0l6.232,6.229a.766.766,0,0,1,0,1.087L7.906,51.424a.766.766,0,0,1-1.087,0l-.712-.712a.77.77,0,0,1,.013-1.1l3.863-3.68H.769A.768.768,0,0,1,0,45.163V44.137a.768.768,0,0,1,.769-.769H9.983L6.12,39.687A.765.765,0,0,1,6.107,38.588Z" transform="translate(0 -37.65)"/></svg></span></button>
						<button class="btn btn-primary col-md-12 hidden-md hidden-lg signInOverlay exitEmail-guestCheckout-email"><spring:theme code="checkout.multi.paymentMethod.email.signin.text"/> </br><spring:theme code="checkout.multi.paymentMethod.email.signin"/> &nbsp; <svg xmlns="http://www.w3.org/2000/svg" width="14.364" height="14" viewBox="0 0 14.364 14"><defs><style>.a{fill:#fff;}</style></defs><path class="a" d="M6.107,38.588l.712-.712a.766.766,0,0,1,1.087,0l6.232,6.229a.766.766,0,0,1,0,1.087L7.906,51.424a.766.766,0,0,1-1.087,0l-.712-.712a.77.77,0,0,1,.013-1.1l3.863-3.68H.769A.768.768,0,0,1,0,45.163V44.137a.768.768,0,0,1,.769-.769H9.983L6.12,39.687A.765.765,0,0,1,6.107,38.588Z" transform="translate(0 -37.65)"/></svg></span></button></div>
													

				     	   
						</div>
			     	

			     		<div class="col-md-12 no-padding-md">
			     		<div class="col-md-12">
	  		 			<formElement:formInputBox idKey="checkoutContactaddressLine1" labelKey="homeOwnerComponent.address" path="addressLine1" mandatory="true" placeholder="homeOwnerComponent.addressLimit" maxlength="50" inputCSS="form-control"/>
						<span id="errorAddressLine1"></span>
						</div>
						</div>
			     		<div class="cl"></div>

			     		<div class="col-md-12 no-padding-md">
			     		<div class="col-md-12">
  						<formElement:formInputBox idKey="checkoutContact.addressLine2" labelKey="leadForm.address.lin.gc" path="addressLine2" placeholder="leadForm.address.lin.gcLimit" maxlength="50" inputCSS="form-control" mandatory="true" />
						<span class="errorPostCode"></span></div>
						</div>
			     		<div class="cl"></div>
						
						<div class="col-md-12 no-padding-md">
						<div class="col-md-6">
  						<formElement:formInputBox idKey="checkoutContactcity" labelKey="requestaccount.city" path="city" placeholder="requestaccount.cityLimit" maxlength="50" inputCSS="form-control" mandatory="true" />
						<span id="errorCity"></span></div>
			     		
						
						<div class="custom_dropdown col-md-2">
		<formElement:customRegionSelectBox idKey="checkoutContactstate" labelKey="${currentBaseStoreId eq 'siteone' ? 'requestaccount.state' : 'homeOwnerComponent.province' }" selectCSSClass="form-control" path="state" mandatory="true" skipBlank="false" skipBlankMessageKey="request.form.select.empty" items="${states}"  />
		
		<span id="errorState"></span>
		</div>
					
			     		
			     		<div class="col-md-4">
  						<formElement:formInputBox idKey="checkoutContactzip" labelKey="${currentBaseStoreId eq 'siteone' ? 'leadForm.zip.gc' : 'text.postcode.homeowner' }" path="zip" inputCSS="form-control" placeholder="${currentBaseStoreId eq 'siteone' ? 'leadForm.zip.gc' : 'text.postcode.homeowner' }"  mandatory="true" maxlength="10" />
						<span id="errorZipcode"></span></div>
			     		</div>
			     		<c:if test="${isMixedCartEnabled eq false}">
			     		<c:if test="${orderType eq 'PARCEL_SHIPPING'}">
			     		<div class="cl"></div>
						<div class="col-md-12 colored"><div class="pad-xs-lft-15 pad-sm-lft-15 contactinput"><formElement:formCheckbox idKey="isSameaddressforParcelShip"
							labelKey="use.ParcelShipping.address"
							path="isSameaddressforParcelShip" inputCSS="add-address-left-input"
							labelCSS="add-address-left-label" mandatory="true" /></div>
					</div>
			     	</c:if>
			     	<c:if test="${orderType eq 'DELIVERY'}">
			     		<div class="cl"></div>
						<div class="col-md-12 colored">
						<div class="pad-xs-lft-15 pad-sm-lft-15 contactinput">
						<formElement:formCheckbox idKey="isSameaddressforParcelShip"
							labelKey="use.localShipping.address"
							path="isSameaddressforParcelShip" inputCSS="add-address-left-input"
							labelCSS="add-address-left-label" mandatory="true" /></div>
						</div>
			     	</c:if>
			     		</c:if>
			     		<c:if test="${isMixedCartEnabled eq true}">
			     		<div class="cl"></div>
			     		<div class="mixedcart_checkbox marginTop10">
			     			<div class="col-md-12 colored mixedcart-sameaddress">
			     				<formElement:formCheckbox idKey="isSameaddressforDelivery" labelKey="use.localShipping.address" path="isSameaddressforDelivery" inputCSS="add-address-left-input mixedcart-sameaddress-delivery" labelCSS="add-address-left-label" mandatory="true" />
							</div>
							<div class="col-md-12 colored mixedcart-sameaddress">
								<formElement:formCheckbox idKey="isSameaddressforParcelShip" labelKey="use.ParcelShipping.address" path="isSameaddressforParcelShip" inputCSS="add-address-left-input mixedcart-sameaddress-shipping" labelCSS="add-address-left-label" mandatory="true" />
							</div>
							</div>
			     		</c:if>
			     		<div class="cl"></div>
			     		<div id="recaptcha-border" class="recaptcha-error-border col-md-6 col-xs-12 margin20">
        <div id="grecaptcha" class="g-recaptcha" data-callback="captcha_onclick" data-sitekey="${recaptchaPublicKey}"></div>
	     <input type="hidden" name="recaptcha" id="recaptchaValidator" />
	     <input type="hidden" id="recaptchaChallengeAnswered" value="${recaptchaChallengeAnswered}" />
	   
	     <div id="recaptcha-error" class="hidden" style="color: rgb(254, 3, 3); font-weight: normal;">
	      <spring:theme code="recaptcha.error"/> 
	     </div>
	      </div>
	    <div class="cl"></div>
			     		<div class="col-md-4  col-xs- 12">
				     		<c:if test="${isMixedCartEnabled}">
				     			<button type="button" class="btn btn-primary btn-block addContactFormSubmitMixCart"><spring:theme code="choosePickupDeliveryMethodPage.continue.to.payment" /></button>
				     		</c:if>
				     		<c:if test="${!isMixedCartEnabled}">
				     			<button type="button" class="btn btn-primary btn-block addContactFormSubmit"><spring:theme code="choosePickupDeliveryMethodPage.continue.to.payment" /></button>
				     		</c:if>
			     		</div>
			     		</div>
			     				   
			     		
			     		
		     	</div>	

</form:form>
