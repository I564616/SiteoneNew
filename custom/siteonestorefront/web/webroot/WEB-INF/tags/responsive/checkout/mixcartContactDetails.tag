<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="address" tagdir="/WEB-INF/tags/responsive/address"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="multiCheckout" 	tagdir="/WEB-INF/tags/responsive/checkout/multi"%>


				<div class="contact-section marginTop35">
				<div class="center-content contact-info-content">
				<div class="row">
				
				<c:if test="${isGuestUser eq false}">
				<c:choose>
				<c:when test="${contactPerson ne null && contactPerson ne '' && addressData ne null && addressData ne '' }">
				
				<div class="checkout-form-data contactForm_guest-checkout marginBottom20">
					<div class="row">
							<div class="col-xs-12 col-sm-6 ${(isMixedCartEnabled)? 'cont-details' : '' }">
									
		
		
		<span class="contact-details">
	   				
	   				</span>
		</div>
		
		
		<div class="col-xs-12 col-sm-6 md-right">
		
		
		<span id="addPhoneMessage" style="display: none;"><br><a class='contact-add-phoneNumber'  data-link='<c:url value='/checkout/multi/add-phone'/>' href='#' data-cbox-title='<spring:theme code='checkout.addphone.title'/> '><spring:theme code="choosePickupDeliveryMethodPage.add.phone.num" /></a><BR><br><spring:theme code="checkout.addphone.message"/></span>
	  		 		<br>
	  		 		<span id="errorPhoneNo"></span>
	  		        <input type="hidden" id="isContactRequirePhoneNumber"/>
                    <input type="hidden" id="isAddPhoneNumberUsed"/>	  	
                    				
	      			<span class="contactError" id="errorContact"></span>
		</div></div> 
		</div>
				</c:when>
				<c:otherwise>
	<div class="col-md-12 checkout-form-data contactForm_guest-checkout">
		<div class="row">
		<div class="col-xs-12 col-sm-6 ">
		
		
		
		<span class="contact-details">
	   				
	   				</span>
		</div>
		
		
		<div class="col-xs-12 col-sm-5 ">
		
		
		<a id="deliveryChangeContact" class="delivery--content changeContact" href="#" ><spring:theme code="choosePickupDeliveryMethodPage.change.contact" />&#8594;</a>
	   					<a id="pickUpChangeContact" class="changeContact pickup--content" href="#" ><spring:theme code="choosePickupDeliveryMethodPage.change.contact" />&#8594;</a>
		<span id="addPhoneMessage" style="display: none;"><br><a class='contact-add-phoneNumber'  data-link='<c:url value='/checkout/multi/add-phone'/>' href='#' data-cbox-title='<spring:theme code='checkout.addphone.title'/> '><spring:theme code="choosePickupDeliveryMethodPage.add.phone.num" /></a><BR><br><spring:theme code="checkout.addphone.message"/></span>
	  		 		<br>
	  		 		<span id="errorPhoneNo"></span>
	  		        <input type="hidden" id="isContactRequirePhoneNumber"/>
                    <input type="hidden" id="isAddPhoneNumberUsed"/>	  	
                    				
	      			<span class="contactError" id="errorContact"></span>
			
		</div></div></div>
		 </c:otherwise>
		
		</c:choose>
		</c:if>
		
		
		<c:if test="${isGuestUser eq true}">
		<c:choose>
	<c:when test="${contactPerson ne null && contactPerson ne '' && addressData ne null && addressData ne '' }">
		 
				
				<div class="row">
	<div class="contactcheckout-mixed topBottom-border contactmix-section mixedcart-contact-section" style="display: none;">
					<multiCheckout:contactInformationGuestUser/>
			</div>	 
<div id="signinId" style="display: none">  <common:signInoverlay/> </div> 
	<div class="cl"></div>

</div>

	
	<c:set value="${contactPerson.firstName}" var="contactPersonfirstName"></c:set>
	<c:set value="${contactPerson.lastName}" var="contactPersonlastName"></c:set>
	<c:set value="${addressData.companyName}" var="contactPersoncompanyName"></c:set>
	<c:set value="${contactPerson.contactNumber}" var="contactPersoncontactNumber"></c:set>
	<c:set value="${contactPerson.displayUid}" var="contactPersondisplayUid"></c:set>
	<c:set value="${addressData.line1}" var="addressDataline1"></c:set>
	<c:set value="${addressData.line2}" var="addressDataline2"></c:set>
	<c:set value="${addressData.town}" var="addressDatatown"></c:set>
	<c:set value="${addressData.district}" var="addressDatadistrict"></c:set>
	<c:set value="${addressData.postalCode}" var="addressDatapostalCode"></c:set>
	
	
	
	<input class="contactPersonfirstName" type="hidden" value='${contactPersonfirstName}'>
	<input class="contactPersonlastName" type="hidden" value='${contactPersonlastName}'>
	<input class="contactPersoncompanyName" type="hidden" value='${contactPersoncompanyName}'>
	<input class="contactPersoncontactNumber" type="hidden" value='${contactPersoncontactNumber}'>
	<input class="contactPersondisplayUid" type="hidden" value='${contactPersondisplayUid}'>
	<input class="addressDataline1" type="hidden" value='${addressDataline1}'>
	<input class="addressDataline2" type="hidden" value='${addressDataline2}'>
	
	<input class=addressDatatown type="hidden" value='${addressDatatown}'>
	<input class="addressDatadistrict" type="hidden" value='${addressDatadistrict}'>
	<input class="addressDatapostalCode" type="hidden" value='${addressDatapostalCode}'>
		<div class="md-11 col-md-offset-1 checkout-form-data contactForm_guest-checkout saved-mixedcart-contact-details" style="display:none;">
		<div class="row">
		<div class="col-xs-12 col-sm-6 ">
		<span id="mixedcart-data" >${contactPerson.firstName}</span>
		<span id="mixedcart-lastNamedata" >${contactPerson.lastName}</span>
		
		<div id="mixedcart-phonedata" >${contactPerson.contactNumber}</div>
		
		<div id="mixedcart-emaildata" >${contactPerson.displayUid}</div>
		</div>
		<div class="col-xs-12 col-sm-5 ">
		
		<div id="mixedcart-addressdata" >${addressData.line1}</div>
		<div id="mixedcart-addressdat1" >${addressData.line2}</div>
	
		<span id="mixedcart-citydata" >${addressData.town}</span>,
		
		<span id="mixedcart-statedata" >${addressData.district}</span>
		<span id="mixedcart-zipdata" >${addressData.postalCode}</span>
		</div>
		</div>
		</div>
		</c:when>
		<c:otherwise>
				
				<div class="row">
		<div class="padding-bottom-15 mixedcart-contact-section">
					<multiCheckout:contactInformationGuestUser/>
			</div>	 
<div id="signinId" style="display: none">  <common:signInoverlay/> </div> 
	<div class="cl"></div>

</div>		
<div class="checkout-form-data contactForm_guest-checkout saved-mixedcart-contact-details" style="display:none;">
		<div class="row">
		<div class="col-xs-12 col-sm-6 ">
		<span id="mixedcart-data" ></span>
		<span id="mixedcart-lastNamedata" ></span>
		
		<div id="mixedcart-phonedata" ></div>
		
		<div id="mixedcart-emaildata" ></div>
		</div>
		<div class="col-xs-12 col-sm-5 ">
		
		<div id="mixedcart-addressdata" ></div>
		<div id="mixedcart-addressdat1" ></div>
	
		<span id="mixedcart-citydata" ></span>
		
		<span id="mixedcart-statedata" ></span>
		<span id="mixedcart-zipdata" ></span>
		</div>
		</div>
		</div>
		</c:otherwise>	
		</c:choose>
		
		</c:if>
		
			
				
				</div>
				
				
		</div>		
</div>