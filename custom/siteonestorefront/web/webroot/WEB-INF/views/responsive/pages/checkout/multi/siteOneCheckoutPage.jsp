<%@ page trimDirectiveWhitespaces="true"%>
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
<c:set var="parcelData" value='${pageName}'></c:set>

<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="checkout" tagdir="/WEB-INF/tags/responsive/checkout" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<spring:url value="/" var="homelink" htmlEscape="false"/>
<fmt:setLocale value="en_US" scope="session" />
<c:set value="false" var="isNationalShipping"></c:set>
<input class="isguestuser" type="hidden" value='${isGuestUser}'>
<input type="hidden" class="currentBaseStoreId" value="${currentBaseStoreId eq 'siteoneCA' ? 'CA' : 'US'}"/>
<template:page pageTitle="${pageTitle}" hideHeaderLinks="true"  showCheckoutSteps="true">
<input class="allCustomers" type="hidden" value='${ycommerce:contructJSONForCustomers(customers)}'>
<input class="currentCustomer" type="hidden" value='${ycommerce:contructJSONForCustomer(contactPerson)}'>

<c:set var="SplitMixedPickupBranchCheckout" value="false" />
<c:if test="${not empty isSplitMixedCartEnabledBranch && isSplitMixedCartEnabledBranch ne null}" >
	<c:set var="SplitMixedPickupBranchCheckout" value="${isSplitMixedCartEnabledBranch}" />
</c:if>
<c:if test="${not empty cartData.isShippingHubOnly && cartData.isShippingHubOnly ne null}" >
	<c:set var="isSplitCartShippingHubOnly" value="${cartData.isShippingHubOnly}"/>
</c:if>
<c:set var="isSplitCartCheckoutPickupFE" value="false"/>
<c:if test="${SplitMixedPickupBranchCheckout && isSplitCartShippingHubOnly && (orderType eq 'PICKUP' || orderType eq 'DELIVERY')}" >
	<c:set var="isSplitCartCheckoutPickupFE" value="true"/>
</c:if>
<c:set var="isSplitCartCheckoutPickup" value="${isSplitCartCheckoutPickupFE}"/>
<input class="isSplitCartCheckoutPickup" type="hidden" value='${isSplitCartCheckoutPickupFE}'>

<c:if test="${not empty cartData.bigBagInstruction && cartData.bigBagInstruction ne null}" >
	<c:set var="bigBagInstruction" value="${cartData.bigBagInstruction}"/>
	<input class="isbigBagInstruction" type="hidden" value='${bigBagInstruction}'>
</c:if>
<input type="hidden" class="siteoneHolidays" value="${siteoneHolidays}">
<script src="https://www.google.com/recaptcha/api.js"></script>	
<input type="hidden" class="sessionAddressId" value="${pointOfService.address.id}">
	<input class="openingSchedule" type="hidden" value='${ycommerce:contructJSONForOpeningSchedule(pointOfService.openingHours)}'> 
	<input class="allCustomers" type="hidden" value='${ycommerce:contructJSONForCustomers(customers)}'>
	<input class="currentCustomer" type="hidden" value='${ycommerce:contructJSONForCustomer(currentCustomer)}'>
	<input class="deliveryAddresses" type="hidden" value='${ycommerce:contructJSONForaddresses(deliveryAddresses)}'>
	<input id="poNumberRequired" type="hidden" value="${cartData.orderingAccount.isPONumberRequired}">
	<input id="poRegex" type="hidden" value="${cartData.orderingAccount.poRegex}">
	<input type="hidden" value="${sessionStore.address.region.isocodeShort}"  id="california_location"/>
	<input type="hidden" value="${pointOfService.address.region.isocodeShort}"  id="pickup_location"/> <c:url value="/checkout/multi/order-type/submit" var="orderSubmit"/>
	<input type="hidden" value="${addressData.district}" id="delivery_location"/>
	<c:set var="timeLimit" value="<%=de.hybris.platform.util.Config.getParameter(\"timeLimit.morning.checkout\")%>"/>
	<input type="hidden" value="${timeLimit}" id="timeLimit"/>
	<c:set var="deliveryTimeLimit" value="<%=de.hybris.platform.util.Config.getParameter(\"delivery.timeLimit.morning.checkout\")%>"/>
	<input type="hidden" value="${deliveryTimeLimit}" id="deliveryTimeLimit"/>
	<c:set var="afternoonTimeLimit" value="<%=de.hybris.platform.util.Config.getParameter(\"timeLimit.afternoon.checkout\")%>"/>
	<input type="hidden" value="${afternoonTimeLimit}" id="afternoonTimeLimit"/>
	<input type="hidden" id="isPOValidated" name="isPOValidated" value="true" >
	<input id="sessionStore" type="hidden" value="${sessionStore.storeId}"> 
	<c:set var="ddcUrl" value="<%=de.hybris.platform.util.Config.getParameter(\"kount.ddc.url\")%>"/>
	<c:set var="merchantId" value="<%=de.hybris.platform.util.Config.getParameter(\"payments.fraud.client.id\")%>"/>
	<c:set var="subtotalLimit" value="${cartData.deliveryEligibilityThreshold}"/>
	<input type="hidden" class="subtotalLimit" value="${subtotalLimit}"/>
	<input type="hidden" class="homeOwnerCode" value="${homeOwnerCode}"/>
	<input type="hidden" class="trade-class" value="${cartData.orderingAccount.tradeClass}"/>
	<input type="hidden" class="subTotal-class" value="${cartData.subTotal.value}"/>
	<c:set var="isBulkDelivery" value="${not empty isBulkDeliveryBranch ? isBulkDeliveryBranch : 'false'}"/>
	<input type="hidden" id="bulkdelivery" value="${isBulkDelivery}" />
	<input type="hidden" id="orderType" value="${orderType}"/>
	<input type="hidden" id="isGuestUser" value="${isGuestUser}"/>
	<input type="hidden" id="currentCartId" name="currentCartId" value="${cartData.b2bCustomerData.currentCartId}">
	<input type="hidden" id="recentCartIds" name="recentCartIds" value="${cartData.b2bCustomerData.recentCartIds}">
	<input type="hidden" id="anonymousCartId" name="anonymousCartId" value="${cartData.code}">
	<c:set var="showAllDayOption" value="false"/>
	<c:if test="${orderType eq 'DELIVERY' &&  isBulkDelivery eq true}">
		<c:set var="showAllDayOption" value="true"/>
	</c:if>
	<input type="hidden" name="showAllDayOption" id="showAllDayOptions" value="${showAllDayOption}"/>
	<input type="hidden" name="isGuestCCDisbaled" id="isGuestCCDisbaled" value="${isCreditPaymentBlocked}"/>
<c:set var="homeOwnerCode" value="<%=de.hybris.platform.util.Config.getParameter(\"homeOwner.trade.class.code\")%>"/>
<div class="col-md-9 no-padding-xs">
<div class="headline checkout-page-title">
		<c:if test="${isGuestUser eq false}">
			<spring:theme code="contact.checkout.title"/>
		</c:if>
		<c:if test="${isGuestUser eq true}">
		<spring:theme code="contact.checkout.gc.title"/>
		</c:if>
		</div>
		
		<div class="hidden-md hidden-lg ${isSplitCartCheckoutPickupFE ? 'hidden-xs hidden-sm' : ''}">
<div class="col-md-4 col-xs-12 col-sm-12 page-choosePickupDeliveryMethodNewPage padding0">
		<div class="col-sm-12 col-md-12 col-xs-12 pick_delivery_summary padding-rightZero">
			<c:if test="${orderType eq 'PICKUP'}">
				<storepickup:checkoutProductDetails productTitle="checkout.orderConfirmation.yourItems" entries="${cartData.entries}"/> 
			</c:if>
			<c:if test="${orderType eq 'DELIVERY'}">
				<storepickup:checkoutProductDetails productTitle="text.account.order.title.deliveryItems" entries="${cartData.entries}"/> 
			</c:if>
			</div>
			</div>
</div>
<div class="cl"></div>
<c:if test="${isMixedCartEnabled ne true}">
<div class="col-md-12 contact-data-box  ${isSplitCartCheckoutPickupFE ? ' m-b-0-imp border-bottom-none-imp b-b-lr-none b-b-rr-none ' :''}">

		<div class="col-md-12 padding0">
		
				
			
	<c:if test="${isGuestUser eq false}">
	<div class="title-bar order-confirmation-page-bottom row">
	<div class="numberCircle-div"></div>
		<div class="title-bar order-confirmation-page-bottom">
					<div class="col-xs-8 order-summary-title-gc ">
						
						<h1 class="order-confirmation-page-title">
					<c:if test="${!isSplitCartCheckoutPickup}">
						<c:if test="${orderType eq 'PICKUP'}">
						 <span class=""><spring:theme code="use.pickUp.title" /></span>
						 </c:if>
						 <c:if test="${orderType eq 'DELIVERY'}">
						  <span class="" ><spring:theme code="use.delivery.title" /></span>
						  </c:if>
						  <c:if test="${orderType eq 'PARCEL_SHIPPING'}">
						   <span class="" ><spring:theme code="use.parcelShipping.title" /></span>
						</c:if>
					</c:if>
					<c:if test="${isSplitCartCheckoutPickup}">
						<spring:theme code="checkout.summary.yourcontactdetails" />
					</c:if>
						</h1>
					</div>
					<div class="col-md-3 col-xs-1 text-align-right split-cart-contact-edit-info hidden">
						<a id="split-cart-contact-edit" href="#"><spring:theme code="checkout.summary.edit"/></a>
					</div>
				</div>
				</div>
				<div class="cl"></div>
				<br>
				<c:choose>
				<c:when test="${contactPerson ne null && contactPerson ne '' && addressData ne null && addressData ne '' }">
				
				<div class="col-md-11 col-md-offset-1 checkout-form-data contactForm_guest-checkout">
					<div class="row">
							<div class="col-xs-12 col-sm-6">
									
		
		
		<span class="contact-details">
	   				
	   				</span>
		</div>
		
		
		<div class="col-xs-12 col-sm-6 md-right">
		
		
		<a id="deliveryChangeContact" class="delivery--content changeContact" href="#" ><spring:theme code="choosePickupDeliveryMethodPage.change.contact" />&#8594;</a>
	   					<a id="pickUpChangeContact" class="changeContact pickup--content" href="#" ><spring:theme code="choosePickupDeliveryMethodPage.change.contact" />&#8594;</a>
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
		
		
		</div>
		<div class="cl"></div>
		 
		<c:if test="${isGuestUser eq true}">
		<c:choose>
	<c:when test="${contactPerson ne null && contactPerson ne '' && addressData ne null && addressData ne '' }">
		<div class="title-bar order-confirmation-page-bottom row">
		<div class="numberCircle-div"></div>
		<div class="title-bar order-confirmation-page-bottom">
					<div class="col-xs-8 order-summary-title-gc ">
						<h1 class="order-confirmation-page-title"><c:if test="${orderType eq 'PICKUP'}">
						 <span class="" ><spring:theme code="use.pickUp.title" /></span>
						 </c:if>
						 <c:if test="${orderType eq 'DELIVERY'}">
						  <span class="" ><spring:theme code="use.delivery.title" /></span>
						  </c:if>
						  <c:if test="${orderType eq 'PARCEL_SHIPPING'}">
						   <span class="" ><spring:theme code="use.parcelShipping.title" /></span>
						</c:if></h1>
					</div>
					
					
					<div class="col-xs-3 text-right edit-contact-information_guest">
					
						<a class="add-edit-color edit-contact-information_guest " href="#"><spring:theme code="checkout.multi.deliveryAddress.edit"/></a>
					</div>
					
				</div>
				</div>
				<br>
				
				<div class="row">
	<div class="contactcheckout topBottom-border" style="display: none">
					<multiCheckout:contactInformationGuestUser/>
			</div>	 
<div id="signinId" style="display: none">  <common:signInoverlay/> </div> 
	<div class="cl"></div>

</div>

	
	<c:set value="${contactPerson.firstName}" var="contactPersonfirstName"></c:set>
	<c:set value="${contactPerson.lastName}" var="contactPersonlastName"></c:set>
	
	<c:set value="${contactPerson.contactNumber}" var="contactPersoncontactNumber"></c:set>
	<c:set value="${contactPerson.displayUid}" var="contactPersondisplayUid"></c:set>
	<c:set value="${addressData.line1}" var="addressDataline1"></c:set>
	<c:set value="${addressData.line2}" var="addressDataline2"></c:set>
	<c:set value="${addressData.town}" var="addressDatatown"></c:set>
	<c:set value="${addressData.district}" var="addressDatadistrict"></c:set>
	<c:set value="${addressData.postalCode}" var="addressDatapostalCode"></c:set>
	

	
	
	<input class="contactPersonfirstName" type="hidden" value='${contactPersonfirstName}'>
	<input class="contactPersonlastName" type="hidden" value='${contactPersonlastName}'>
	<input class="contactPersoncontactNumber" type="hidden" value='${contactPersoncontactNumber}'>
	<input class="contactPersondisplayUid" type="hidden" value='${contactPersondisplayUid}'>
	<input class="addressDataline1" type="hidden" value='${addressDataline1}'>
	<input class="addressDataline2" type="hidden" value='${addressDataline2}'>
	
	<input class=addressDatatown type="hidden" value='${addressDatatown}'>
	<input class="addressDatadistrict" type="hidden" value='${addressDatadistrict}'>
	<input class="addressDatapostalCode" type="hidden" value='${addressDatapostalCode}'>
		<div class="md-11 col-md-offset-1 checkout-form-data contactForm_guest-checkout">
		<div class="row">
		<div class="col-xs-12 col-sm-6 ">
		<span id="data" >${contactPerson.firstName}</span>
		<span id="lastNamedata" >${contactPerson.lastName}</span>
		
		<div id="phonedata" >${contactPerson.contactNumber}</div>
		
		<div id="emaildata" >${contactPerson.displayUid}</div>
		</div>
		<div class="col-xs-12 col-sm-5 ">
		
		<div id="addressdata" >${addressData.line1}</div>
		<div id="addressdat1" >${addressData.line2}</div>
	
		<span id="citydata" >${addressData.town}</span>,
		
		<span id="statedata" >${addressData.district}</span>
		<span id="zipdata" >${addressData.postalCode}</span>
		</div>
		</div>
		</div>
		</c:when>
		<c:otherwise>
		<div class="title-bar order-confirmation-page-bottom row">
		<div class="numberCircle-div"></div>
		<div class="title-bar order-confirmation-page-bottom">
					<div class="col-md-9 col-xs-8 order-summary-title-gc ">
						<h1 class="order-confirmation-page-title"><c:if test="${orderType eq 'PICKUP'}">
						 <span class="" ><spring:theme code="use.pickUp.title" /></span>
						 </c:if>
						 <c:if test="${orderType eq 'DELIVERY'}">
						  <span class="" ><spring:theme code="use.delivery.title" /></span>
						  </c:if>
						  <c:if test="${orderType eq 'PARCEL_SHIPPING'}">
						   <span class="" ><spring:theme code="use.parcelShipping.title" /></span>
						</c:if></h1>
					</div>
					
					
					<div class="col-md-2 col-sm-3 col-xs-2 text-right edit-contact-information">
						<a class="add-edit-color edit-contact-information " href="#"><spring:theme code="checkout.multi.deliveryAddress.edit"/></a>
					</div>
					
				</div>
				</div>
				<br>
				
				<div class="row">
		<div class="contactcheckout topBottom-border">
					<multiCheckout:contactInformationGuestUser/>
			</div>	 
<div id="signinId" style="display: none">  <common:signInoverlay/> </div> 
	<div class="cl"></div>

</div>		
<div class="col-md-11 col-md-offset-1 checkout-form-data contactForm_guest-checkout">
		<div class="row">
		<div class="col-xs-12 col-sm-6 ">
		<span id="data" ></span>
		<span id="lastNamedata" ></span>
		
		<div id="phonedata" ></div>
		
		<div id="emaildata" ></div>
		</div>
		<div class="col-xs-12 col-sm-5 ">
		
		<div id="addressdata" ></div>
		<div id="addressdat1" ></div>
	
		<span id="citydata" ></span>
		
		<span id="statedata" ></span>
		<span id="zipdata" ></span>
		</div>
		</div>
		</div>
		</c:otherwise>	
		</c:choose>
		
		</c:if>
		
			

	</div>
<c:if test="${isSplitCartCheckoutPickup}">
	<div class="col-md-12 bg-white p-y-45 splitPoNum">
		<div class="row">
			<div class="col-md-8">
				<form:form modelAttribute="siteOneOrderTypeForm">
					<checkout:pONumberForm/>
				</form:form>
			</div>
		</div>
	</div>
</c:if>
	<div class="step-body kaxsdc" data-event="load">
		<c:if test="${isSplitCartCheckoutPickup}">
			<div class="col-md-12 marginTop30 p-l-0 splitPickupOrder">
				<div class="bg-gray padding-20 shippingLabel text-align-center text-white">
					<c:if test="${orderType eq 'PICKUP'}">
					<spring:theme code="checkout.summary.pickuporder" />
					</c:if>
					<c:if test="${orderType eq 'DELIVERY'}">
						<spring:theme code="checkout.summary.deliveryorder" />
					</c:if>
				</div>
			</div>
		</c:if>
	<div class="col-md-12 contact-data-box">

		 
		<div class="title-bar order-confirmation-page-bottom row">
		<div class="numberCircle-div"></div>
					<div class="col-xs-7 col-sm-7  col-md-8 order-summary-title">
						<h1 class="order-confirmation-page-title">
						 <span class="pickup-title" style="display:none;"><spring:theme code="branch.pickUp.gc.title" /></span>
						<span class="delivery-title" style="display:none;"><spring:theme code="branch.delivery.gc.title" /></span>
						<c:if test="${orderType eq 'PARCEL_SHIPPING' }" > 
							<h1 class="order-confirmation-page-title order-confirmation-page-gc"> <spring:theme code="shipping.title.text" /></h1>
						 </c:if>
						</h1>
				</div>
					<c:if test="${orderType eq 'PICKUP' }" > 
					<div class="text-right col-md-3 col-xs-3 edit-pickup-information" style="display:none;">
					<span class="edit-link">
						<a id="PickUp_Edit" href="#"><spring:theme code="checkout.multi.deliveryAddress.edit"/></a>
					   </span>
					</div>
					</c:if>
					<c:if test="${orderType eq 'DELIVERY'}" > 
					<div id="delivery_fee" class="col-md-3  text-right pull-right delivery-fee">
					<input type="hidden" class="cart-isfreight" value="${empty cartData.freight}"/>
					<input type="hidden" class="cart-freight" value="${cartData.freight}"/>
					<c:if test="${not empty cartData.freight}" >
					<span class="delivery-fee-title"><spring:theme code="delivery.fee.text" /></span>
					<span id="deliveryfees" class="headline3">&#36;0.00 </span> 
					</c:if>
	               <div class="text-right edit-delivery-information link-edit" style="display:none;">
					<span class="edit-link">
						<a id="Delivery_Edit" href="#"><spring:theme code="checkout.multi.deliveryAddress.edit"/></a>
					   </span>
					</div>
					</div>
					</c:if>
			      	<c:if test="${orderType eq 'PARCEL_SHIPPING' && (cartData.isTampaBranch || cartData.isLABranch || cartData.isShippingFeeBranch) }" >
					<div class="col-md-3  text-right pull-right delivery-fee">
					<div id="shipping_fee">
					<span class="delivery-fee-title"><spring:theme code="flat.rate.text"/></span>
					<span class="headline3">
					<c:choose>
						<c:when test="${not empty flatRateShippingFee && flatRateShippingFee ne '0.0'}">
							&#36;${flatRateShippingFee}
						</c:when>
						<c:otherwise>
							 <spring:theme code="text.shipping.free"/>
						</c:otherwise>
					</c:choose>
					</span>
					</div>
					<div class="text-right  link-edit edit-shipping-information" style="display:none;">
					<span class="edit-link">
						<a id="Shipping_Edit" href="#"><spring:theme code="checkout.multi.deliveryAddress.edit"/></a>
					   </span>
					</div>
					
					</div>
					</c:if>
					
					
					
				</div>
				
	<form:form id="siteOneOrderTypeForm" modelAttribute="siteOneOrderTypeForm" action="${homelink}checkout/multi/siteOne-checkout/saveBranchPickupDetails" method="POST">
		<%-- <ycommerce:testId code="checkoutStepOne"> --%>
		<c:if test="${orderType eq 'PICKUP' || orderType eq 'DELIVERY'}" > 
	    <div class="step-body-form col-sm-12 col-md-12 col-xs-12 page-choosePickupDeliveryMethodPage">
	    <div class="row">
	        <form:hidden path="orderType" value="${orderType}" />
	      <div class="delivery-chargesMsg">
			     <spring:theme code="delivery.charges.apply" /><br>
			     </div>
			    
			     <div class="icon-delivery-charges message-delivery choose-pickup-msgdelivery hidden">
			      <spring:theme code="delivery.charges.notification" /><br>
			   </div>
			    
			    <div class="pickupDelivery-mainContent">
			    <c:if test="${orderType eq 'PICKUP'}">
			    <div class=" pickup-delivery-table row marginTop35">
	        <div class="topBottom-border">
	        <div class="row">
		        <div id="pickup" class="pickup--content">
		       		<form:hidden path="storeId" value="${sessionStore.storeId}" />
		       		<div id="pickup_Location_Div" class="col-md-4">
		       		<p class="bold-text black-title"> <spring:theme code="choosePickupDeliveryMethodPage.pickup.location" />:</p>
		       		<span class="hidden-md hidden-lg pull-right direction-link"><a id="getDirection" href="" data-url="${pointOfService.address.line1},${pointOfService.address.line2},${pointOfService.address.town},${pointOfService.address.region.isocodeShort},${pointOfService.address.postalCode}"><spring:theme code="choosePickupDeliveryMethodPage.get.direction" /></a></span>
		       		</div>
		       		<div class="col-md-5">
		         		<span class="pickup-store">
		         		<span class="pickup-store-detail">
		           			<strong>${pointOfService.name}</strong><br><strong><c:if test="${not empty pointOfService.title}">${pointOfService.title}<br></c:if></strong>${pointOfService.address.line1}<br>${pointOfService.address.town},&nbsp;${pointOfService.address.region.isocodeShort}&nbsp;${pointOfService.address.postalCode}</span><br><a class="tel-phone" href="tel:${pointOfService.address.phone}">${pointOfService.address.phone}</a>
		           			</span> 
		           	 </div>
		           	 <div class="col-md-3 text-right">
		           	 <div class="hidden-xs hidden-sm direction-link">
		           	 <a id="getDirection" href="" data-url="${pointOfService.address.line1},${pointOfService.address.line2},${pointOfService.address.town},${pointOfService.address.region.isocodeShort},${pointOfService.address.postalCode}"><spring:theme code="choosePickupDeliveryMethodPage.get.direction" /></a>
		           	 </div>
		           	 </div>
		           	 
		           	 <div id="pickup_Details" class="col-md-6 col-xs-12 bold-text hidden bg-danger">
		           	 <span id="requestedDateData"></span> 	&#921; <span id="requestedMeridianData"></span>
		           	 </div>
		        </div>
		         
		         <div class="cl"></div>
		         </div>
	      			</div>
	      			</div>
	      			</c:if>
	      			<c:if test="${orderType eq 'DELIVERY'}">
	      			
	      			<div id="delivery_Details">
	      			<div class="row marginTop35">
	      			<div class="topBottom-border">
	      			<div class="row">
	      			<div class="col-xs-12 col-md-2"><p class="bold-text black-title"><spring:theme code="orderSummaryPage.delivery.to"/>:</p></div>
	      			<div class="col-xs-12 col-md-3">
		      			<div id="delivery_line1"></div>
						<div id="delivery_line2"></div>	
						<span id="delivery_city"></span>,<span id="delivery_state"></span>
						<div id="delivery_postalcode"></div>				
	      			</div>
	      			<div id="requestedDeliveryDate" class="col-xs-12 col-md-7 bold-text hidden bg-danger"></div>
	      			
	      			<div class="cl"></div>
	      			</div>
	      			</div>
	      			</div>
	      			</div>
	      			</c:if>
	      			<div id="delivery" class="delivery--content row marginTop35">
	      			
	      			 <div id="delivery_content1" class="topBottom-border">
		        
		        	<div class="row">
		        	<div class="hidden-md hidden-lg col-md-12 marginBottom20"><p class="bold-text black-title"><spring:theme code="delivery.option.text"/>:</p></div>
		        	<c:if test="${orderType eq 'DELIVERY'}">
		        	<form:hidden path="storeId" value="${sessionStore.storeId}" />
		        	<c:if test="${isGuestUser eq true}">
		        	<%-- <form:hidden path="deliveryAddress" value="${addressData}"/>  --%>
		       		 <form:hidden path="contactId" value="${siteOneOrderTypeForm.contactId}" />
			        <form:hidden path="addressId" id="addressId" value="${siteOneOrderTypeForm.addressId}" />
		        	
		         <div class="col-xs-12 col-md-8 float-div">
		         <div class="lift-sec">
		        	<div class="col-md-12 message-center">
		        		<span class="colored">
		        		<input id="isSameAsContactInfo" name="isSameAsContactInfo" type="checkbox" checked/>
		        		</span>
		        		<label><spring:theme code="parcel.shipping.checkbox.label" /></label>
		        	</div>
		        	<div class="cl"></div>
		        	</div>
		        	<div class="row marginTop20 pad-xs-lft-30 m-t-10-xs">
		        	<div class="col-xs-12 delivery-Newaddress"></div>
			     	<div class="col-xs-12 delivery-Newregion"></div>
			     	
		        	<div class="col-xs-12" id="deliveryaddressdata" >${addressData.line1}</div>
					<div class="col-xs-12" id="deliveryaddressdat1" >${addressData.line2}</div>
				
					<span class="col-xs-12" id="deliverycitydata" >${addressData.town}</span>
					
					<span class="col-xs-12" id="deliverystatedata" >${addressData.district}</span>
					<span class="col-xs-12"  id="deliveryzipdata" >${addressData.postalCode}</span>
				    </div>
	      
		        	
		         </div>
		         </c:if>
		         </c:if>
		         <c:if test="${isGuestUser eq false }">
		         
		         <div class="col-xs-12 col-md-8 float-div">
		        	<label for="deliveryAddress"><strong> <spring:theme code="choosePickupDeliveryMethodPage.delivery.location" />:</strong></label><br>
			        	<form:hidden path="contactId" value="${siteOneOrderTypeForm.contactId}" />
			        	<form:hidden path="addressId" id="addressId" value="${siteOneOrderTypeForm.addressId}" />
			        	<form:errors path="addressId"/>
			        	<select id="deliveryAddress" placeholder="select">
			        		<option value="selectDefault"><spring:theme code="choosePickupDeliveryMethodPage.select.delivery.location" /></option>
				   		  	<c:forEach var="item" items= "${deliveryAddresses}" varStatus="status">
				   		  	<c:set var="nickname" value=""/>
				   		  	<c:if test="${not empty item.projectName}">
				   		  		<c:set var="nickname" value="${item.projectName}:"/>
				   		  	</c:if>
				   		  		<c:choose>
				   		  			<c:when test="${not empty siteOneOrderTypeForm.addressId && siteOneOrderTypeForm.addressId != '' && siteOneOrderTypeForm.addressId == item.id}">
				   		  				<option value ="${item.id}" ${status.index == 0 ? 'selected':''}> ${nickname}&nbsp;${item.line1},&nbsp;${item.region.isocodeShort}&nbsp;${item.postalCode}</option>
				   		  			</c:when>
				   		  			<c:otherwise>
				   		  				<option value ="${item.id}" ${status.index == 0 ? 'selected':''}>${nickname}&nbsp;${item.line1},&nbsp;${item.region.isocodeShort}&nbsp;${item.postalCode}</option>
				   		  			</c:otherwise>
				   		  		</c:choose>
				   		    </c:forEach>
				      	</select>
				      	<br>
				      	<span class="deliveryError" id="errorDeliveryAddressRadio"></span>
			     		<div class="delivery-address"></div>
			     		<div class="delivery-region hidden"></div>
			     		<div class="delivery-phone"></div>
			     		<sec:authorize var="isAdmin" access="hasAnyRole('ROLE_B2BADMINGROUP')"/>
                       <c:if test="${isAdmin or enableAddDeliveryAddress}">
			     		<br/>
			     			<div><a href="#" class="newaddress btn btn-block btn-address"><span class="glyphicon glyphicon-plus address-icon"></span> 
			     			<strong><spring:theme code="choosePickupDeliveryMethodPage.add.new.del.location" /></strong></a></div>
			     		</c:if>
			     	</div>
			     	</c:if>
			     	<div id="addAddressSuccess"></div>
			     	<div class="col-xs-12 col-md-4 icon-text-sec">
			     	<p class="hidden-xs hidden-sm"><span class="bold-text black-title"><spring:theme code="delivery.location.text"/>:</span></p>
		        	<div class="checkout-info-icons">
		        	<div>
					<svg xmlns="http://www.w3.org/2000/svg" width="24" height="23.899" viewBox="0 0 24 23.899"><defs><style>.icon-7{fill:#78a22f;}</style></defs>
					<path class="icon-7" d="M408.138,321.446l-8.883-2.914-7.5,3.387V341.9l7.625-3.445,8.892,2.914,7.482-3.388V318Zm-7.691-.184,6.639,2.178v15.2l-6.639-2.176Zm-6.472,2.092,4.25-1.918v15.1l-4.25,1.92Zm19.556,13.194-4.222,1.912v-15.1l4.222-1.911Z" transform="translate(-391.752 -318)"/>
					</svg>
							<p><spring:theme code="delivery.msg1.text"/></p>	
			       </div>
		       	 </div>
		        
		        </div>
			     	<div class="cl"></div>
			     	</div>
			     </div>
		         <div class="cl"></div>
		        </div>
		       <div class="cl"></div>
		       <div class="row">
		       <div id="pickup-delivery"  class="common-content-pickupDelivery pickupDelivery_desktop topBottom-border">
		        	
	  				<div class="row">
	  				<div class="col-xs-12 col-md-4 pick-up-details icon-text-sec">
			       			<p class="bold-text black-title"><spring:theme code="choosePickupDeliveryMethodPage.pickup.info" />:</p>
			       			<div class="hidden-xs hidden-sm margin-top-20 font-size-14">
			       				<storepickup:pickupInfomationIcons/>
			       			</div>
		        		</div>
		        		 
		        		<div class="col-xs-12 col-md-4 delivery-details no-padding-xs icon-text-sec">
		        		<div class="col-md-12 marginBottom20 no-padding-md"><p class="bold-text black-title"><spring:theme code="choosePickupDeliveryMethodPage.delivery.info" />:</p></div>
		        		<div class="hidden-xs hidden-sm margin-top-20">
			       				<storepickup:deliveryInfomationIcons/>
			       			</div>
		        		</div>
		        		
		        		<div class="col-xs-12 col-sm-8 font-size-14 font-small-xs">
							<div class="flex-center m-b-15 bold-text text-orange show-pickupDate-message" style="display: none;">
								<span class=""><common:exclamationCircle /></span>
								<span class="m-l-15"><spring:theme code="choosePickupDeliveryMethodPage.request.pickup.date.message" /></span>
							</div>
		        			<div id="pickupinfo">
			       			<div class="dateTimeHeader bold-text black-title"><spring:theme code="choosePickupDeliveryMethodPage.request.pickup.date" /></div>
			       			</div>
			       			<div id="deliveryinfo" class="delivery--content hidden bg-danger" style="display: none;">
			       			<div class="dateTimeHeader bold-text black-title"><spring:theme code="choosePickupDeliveryMethodPage.request.delivery.date" /></div>
			       			</div>
			       			<div class="info-details m-b-25">
			        			<div class="date-time-pickupDelivery">
			        			<div class="row">
			        				<fmt:formatDate var="fmtDate" value="${siteOneOrderTypeForm.requestedDate}" pattern="MM/dd/YYYY hh:mm:ss a"/>
			        				<form:hidden path="requestedDate" id="requestedDate" value="${fmtDate}"/>
			        				<spring:message code="choosePickupDeliveryMethodPage.select.date" var="dataVar"/>
			        				<div class="${showAllDayOption ? 'col-md-5 col-sm-12 col-xs-12' : 'col-md-6 col-sm-12 col-xs-12' }">
			        				<span class="input-dateWrapper">
			        				<form:input type="text" path="date" placeholder="${dataVar}" id="date" readonly="true"/>
			        				</span>
			        				
			        				</div>
				        			 <div class="${showAllDayOption ? 'col-md-7 col-sm-12 col-xs-12' : 'col-md-6 col-sm-12 col-xs-12'}">
		       		              	<div class="requested-time row">
			        	              	<c:forEach items="${meridianTime}" var="siteOneMeridianTime">
											<input type="hidden" name="meridiancode" value="${siteOneMeridianTime.code}"/>
											<c:choose>
												<c:when test="${siteOneMeridianTime.code eq 'AM'}">
													<div class="${showAllDayOption ? 'col-md-4 col-xs-4 col-sm-4' : 'col-md-6 col-xs-6 col-sm-6'}">
														<div class="row">
															<div class="radio-wrapper">
																<div class="colored-radio"><form:radiobutton path="requestedMeridian" id="${siteOneMeridianTime.code}" value="${siteOneMeridianTime.code}"  /></div>
																	<label for="${siteOneMeridianTime.code}">
																		<spring:theme code="choosePickupDeliveryMethodPage.morning" />
																	</label>
															</div>
														</div>
													</div>
												</c:when>
												<c:when test="${siteOneMeridianTime.code eq 'PM'}">
													<div class="${showAllDayOption ? 'col-md-4 col-xs-4 col-sm-4' : 'col-md-6 col-xs-6 col-sm-6'}">
														<div class="row">
															<div class="radio-wrapper">
																<div class="colored-radio"><form:radiobutton path="requestedMeridian" id="${siteOneMeridianTime.code}" value="${siteOneMeridianTime.code}"  /></div>
																	<label for="${siteOneMeridianTime.code}">
																		<spring:theme code="choosePickupDeliveryMethodPage.afternoon" />
																	</label>
															</div>
														</div>
													</div>
												</c:when>
												<c:when test="${siteOneMeridianTime.code eq 'ANYTIME'}">
													<div class="${showAllDayOption ? 'col-md-4 col-xs-4 col-sm-4' : 'col-md-6 col-xs-6 col-sm-6 hide'}">
														<div class="row">
															<div class="radio-wrapper">
																<div class="colored-radio"><form:radiobutton path="requestedMeridian" id="${siteOneMeridianTime.code}" value="${siteOneMeridianTime.code}"  /></div>
																	<label for="${siteOneMeridianTime.code}">
																		<spring:theme code="choosePickupDeliveryMethodPage.allday" />
																	</label>
															</div>
														</div>
													</div>
												</c:when>
												<c:otherwise>
													${siteOneMeridianTime.code}
												</c:otherwise>
											</c:choose>
			                          	</c:forEach>
				                  	</div>
				                  	</div>
				                  	</div>
				                  	<c:if test="${orderType eq 'DELIVERY'}">
				                  	<div class="lift-sec charges-sec marginBottom10 hidden">
							        	<div class="col-md-8 col-xs-8 message-center">
							        	<span class="colored">
							        		<form:checkbox value="" path="expediteDelivery" id="expediteDelivery" class="form-control" />
							        		</span>
							        		<label for="expediteDelivery"> <spring:theme code="expedited.delivery.text"/></label>
							        	</div>
							        	<div class="col-md-5 col-xs-5 text-right">
							        	<em><spring:theme code="charges.apply.text"/></em>
							        	</div>
							         <div class="cl"></div>
							        </div>
							        </c:if>
			        			</div>
			        			 	<form:errors path="requestedMeridian"/>
				                  	<div class="dateError" id="errorDate"></div>
			        				
				        			<div class="timeError" id="errorMeridianRadio"></div>
			        		</div>
							<c:if test="${orderType eq 'DELIVERY'}">
		        				<div class="col-md-12 border-radius-10 bg-lightgreen padding-20 font-arial font-size-14 m-b-25">
									<div class="flex-center">
										<common:globalIcon iconName="checkout-alert-clock" width="24" height="24" viewBox="0 0 25 25"/>
										<div class="green-title bold m-l-10"><spring:theme code="checkout.deliveryinfo.title" /></div>
									</div>
									<div class="bold m-t-15 m-b-15"><spring:theme code="checkout.deliveryinfo.heading" />&nbsp;<span class="underline-text"><spring:theme code="checkout.deliveryinfo.heading2" /></span></div>
									<div><spring:theme code="checkout.deliveryinfo.alertinfo" /></div>
								</div>
							</c:if>
		        		<div class="col-md-12 margin-top-20 delivery-info hidden">
		        		<div class="row">
		        		<div class="message-center marginBottom20">
							<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><defs><style>.delivery-info-icon{fill:#78a22f;}</style></defs><g transform="translate(-1.875 -1.875)">
							<path class="delivery-info-icon" d="M50.4,30.35H46.817v-4.39a1.169,1.169,0,0,0-2.338,0v5.559a1.181,1.181,0,0,0,1.169,1.169h4.779A1.164,1.164,0,0,0,51.6,31.519,1.2,1.2,0,0,0,50.4,30.35Z" transform="translate(-31.981 -17.203)"/>
							<path class="delivery-info-icon" d="M13.875,1.875a12,12,0,1,0,12,12A12.005,12.005,0,0,0,13.875,1.875Zm0,21.662a9.662,9.662,0,1,1,9.662-9.662A9.677,9.677,0,0,1,13.875,23.537Z"/></g></svg>
						<p class="green-title">Lorem Ipsum is simply dummy text of the printing.</p>
		        		</div>
		        		<p>It was popularised in the 1960s with the release of Letraset sheets containing
						 Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum</p>
		        		</div>
						 </div>
						<div class="col-md-12">
						<div class="row">
						<div class="col-xs-12 col-sm-12 optional-message-wrapper padding0">
								<div id="storeInstrn" class="common-content-pickupDelivery" >
			        			 	<label for="specialinstruction" class="m-b-0">
				        			 	<c:choose>
											<c:when test="${showAllDayOption}">
												<spring:theme code="checkout.branch.bulk.delivery.msg" />
											</c:when>
											<c:when test="${!((cartData.orderingAccount.tradeClass eq homeOwnerCode) || (isGuestUser eq true)) eq true}">
					        			 		<spring:theme code="checkout.branch.msg" />
					        			 	</c:when>
											<c:otherwise>
												<spring:theme code="choosePickupDeliveryMethodPage.msg.branch" />:
											</c:otherwise>
			        			 	</c:choose>
			        			 	</label>
			        			 	<br>
			        			 	
			        			</div>
								<spring:theme code="choosePickupDeliveryMethodPage.checkout1" var="textplaceholdercheckout1"/>
								<spring:theme code="choosePickupDeliveryMethodPage.text3" var="textplaceholdertext3"/>
								<spring:theme code="choosePickupDeliveryMethodPage.text4" var="textplaceholdertext4"/>
								<spring:theme code="choosePickupDeliveryMethodPage.text5" var="textplaceholdertext5"/>
			        			<spring:message code="optional.text" var="textplaceholder"/>
								<spring:theme code="checkout.branch.bulk.delivery.placeholder" var="textplaceholdertext6"/>
								<c:choose>
									<c:when test="${showAllDayOption}">
										<form:textarea path="specialInstruction" id="specialinstruction" class="form-control m-t-0-xs-imp" rows="0" cols="40" maxlength="250" placeholder="${textplaceholdertext6}" value="Hello" />
									</c:when>
									<c:when test="${!((cartData.orderingAccount.tradeClass eq homeOwnerCode) || (isGuestUser eq true)) eq true}">
										<form:textarea path="specialInstruction" id="specialinstruction" class="form-control m-t-0-xs-imp" rows="0" cols="40" maxlength="250" placeholder="${textplaceholdercheckout1}${textplaceholdertext4}${textplaceholdertext5}"/>
									 </c:when>
									 <c:otherwise>
										<form:textarea path="specialInstruction" id="specialinstruction" class="form-control m-t-0-xs-imp" rows="0" cols="40" maxlength="250" placeholder="${textplaceholdertext3}${textplaceholdertext4}${textplaceholdertext5}"/>
									 </c:otherwise>
							 </c:choose>
							<p class="small-text" ><span id="wordcountstaticmessage"><spring:theme code="choosePickupDeliveryMethodPage.text6" /></span></p>
				        	<p class="small-text" ><span id="remainingwordcount" hidden></span></p>
				        	<span id="errorSpecialinstruction" ></span>
			        	</div>
			        	</div>
					</div>
					
					
					<div id="termsPODiv" class="col-md-12 margin-top-20">
		   	<div class="row">
		   	<div class="common-content-pickupDelivery col-md-12 no-padding-md">
				<c:if test="${!isSplitCartCheckoutPickup}">
					<checkout:pONumberForm/>
				</c:if>
      		</div>
      		<div class="cl"></div>
      		<div class="termsAndConditions-div col-md-12 margin30 common-content-pickupDelivery no-padding-md hidden bg-danger">
	      		<label>
	      		<input type="checkbox" name="termsAndConditions"  id="termsAndConditions">
	      		<span class="cr"><em class="cr-icon glyphicon glyphicon-ok"></em></span>
	      		<spring:theme code="choosePickupDeliveryMethodPage.text7" /> <a id="termsAndConditionsPopup" href="#" ><u><spring:theme code="choosePickupDeliveryMethodPage.text8" /></u></a>.
	      		<span class="termsCheckError" id="errorTermsAndConditionsCheckBox"></span>
	      		</label>
      		</div>
			<c:if test="${!isSplitCartCheckoutPickup}">
      		<div class="payment_cbtn col-md-5 col-xs-12 col-sm-6 no-padding-md margin-top-20">
      		<button type="button" class="btn btn-block btn-primary orderTypeFormSubmit bold-text"><spring:theme code="choosePickupDeliveryMethodPage.continue.to.payment" /></button>
		    </div>
			</c:if>
		    </div>
		    <div class="cl"></div>
		    <div class="hidden-md hidden-lg m-t-25 pick-up-details">
			       				<storepickup:pickupInfomationIcons/>
			       			</div>
			       			<div class="hidden-md hidden-lg m-t-25 delivery-details">
			       				<storepickup:deliveryInfomationIcons/>
			       			</div>
		    </div>
		       	</div>
		       	</div>
		       	</div>
		       	 <div class="cl"></div>
		   	</div>
		    
		   	
		    
		    
		    
		    
		    <form:hidden id="kountSessionId" path="kountSessionId" value="" />
		    
		    
		    </div>
		      
						 
						</div>
		   </div>
		   </c:if>
		  
		   </form:form>
		<c:if test="${isGuestUser eq true}">
			<div class="cl"></div>
			<form:form id="SiteOneGCDeliveryForm" modelAttribute="SiteOneGCContactForm" action="${homelink}checkout/multi/saveAlternateContactDetails" method="POST" class="pad-lft-15 p-r-15 no-padding-xs">
				<div id="deliveryContactForm" class="row deliveryContact-form" style="display:none">
					<div class="col-md-12"><span class="addAddressError" ></span></div>
					<div class="col-md-6 ">
						<formElement:formInputBox idKey="checkoutdeliveryfirstName" labelKey="First Name" path="firstName" placeholder="First Name" inputCSS="form-control" mandatory="true" />
						<span id="errordeliveryFirstName"></span>
					</div>
					<div class="col-md-6">
						<formElement:formInputBox idKey="checkoutdeliverylastName" labelKey="Last Name" path="lastName" placeholder="Last Name" inputCSS="form-control" mandatory="true" />
						<span id="errordeliveryLastName"></span>
					</div>
					<div class="col-md-12">
						<formElement:formInputBox idKey="checkoutdelivery.companyName" labelKey="Company Name" path="companyName" placeholder="Optional" inputCSS="form-control" />
					</div>
					<div class="col-md-6">
						<formElement:formInputBox idKey="checkoutdeliveryphone" labelKey="Phone Number" path="phone" maxlength="10" placeholder="Phone Number" inputCSS="form-control" mandatory="true" />
						<span id="errordeliveryPhoneNumber"></span>
					</div>
					<div class="col-md-6">
						<formElement:formInputBox idKey="checkoutdeliveryemail" labelKey="Email" path="email" inputCSS="form-control" placeholder="Email" mandatory="true" />
						<span id="errorDeliveryEmailAddress"></span>
					</div>
					<div class="col-md-12">
						<formElement:formInputBox idKey="checkoutdeliveryaddressLine1" labelKey="Address" path="addressLine1" mandatory="true" placeholder="Address (Maximum of 50 characters)" maxlength="50" inputCSS="form-control" />
						<span id="errordeliveryAddressLine1"></span>
					</div>
					<div class="col-md-12">
						<formElement:formInputBox idKey="checkoutdelivery.addressLine2" labelKey="Apartment, Suite, Building, Etc. (Maximum of 50 characters)" maxlength="50" path="addressLine2" placeholder="Apartment, Suite, Building, Etc." inputCSS="form-control" mandatory="true" />
					</div>
					<div class="col-md-5">
						<formElement:formInputBox idKey="checkoutdeliverycity" labelKey="City" path="city" placeholder="City (Maximum of 50 characters)" maxlength="50" inputCSS="form-control" mandatory="true" />
					</div>
					<div class="custom_dropdown col-md-4">
						<formElement:customRegionSelectBox idKey="checkoutDeliverystate" labelKey="State" selectCSSClass="form-control" path="state" mandatory="true" skipBlank="false" skipBlankMessageKey="request.form.select.empty" items="${states}" />
						<span id="errordeliveryState"></span>
					</div>
					<div class="col-md-3">
						<formElement:formInputBox idKey="checkoutdeliveryzip" labelKey="Zip" path="zip" inputCSS="form-control" placeholder="Zip" mandatory="true" maxlength="10" />
						<span id="errordeliveryZipcode"></span>
					</div>
					<div class="cl"></div>
					<div class="col-md-4"><button type="button" class="btn btn-primary btn-block m-b-15 adddeliveryFormSubmit"> Continue</button></div>
				</div>
			</form:form>
		</c:if>
		   
	<c:if test="${orderType eq 'PARCEL_SHIPPING' }" > 
		<c:if test="${!cartData.isTampaBranch && !cartData.isLABranch}" >
			<c:set value="true" var="isNationalShipping"></c:set>
		</c:if>
		<input class="isNationalShipping" type="hidden" value='${isNationalShipping}'>
	<div id="parcelShipping_div" class="row marginTop35">
	<!-- Checkout Shipping Address Block -->
	<form:form  id="SiteOneGCMainShippingForm" modelAttribute="siteOneOrderTypeForm" action="${homelink}checkout/multi/saveFulfilmentDetails" method="POST">
		<form:hidden path="storeId" value="${sessionStore.storeId}"/>
		<form:hidden path="orderType" value="${orderType}" />
		<%--  <form:hidden path="deliveryAddress" value="${siteOneOrderTypeForm.deliveryAddresses}"/> --%>
	  	<div id="shipping_Details">
	  		<div class="topBottom-border">
	  			<div class="row">
	      			<div class="col-xs-12 col-md-2"><p class="bold-text black-title"><spring:theme code="checkout.pickup.items.to.be.shipped"/></p></div>
	      			<div class="col-xs-12 col-md-8">
						<div class="row">
							<c:if test="${isGuestUser eq true }">
								<div class="col-xs-12 col-sm-6">
									<span id="shipping_fname" ></span>
									<span id="shipping_lname" ></span>
									<div id="shipping_phone" ></div>
									<div id="shipping_email" ></div>
								</div>
							</c:if>
							<div class="col-xs-12 col-sm-6">
								<div id="shipping_line1" ></div>
								<div id="shipping_line2" ></div>
								<span id="shipping_city" ></span>,
								<span id="shipping_state" ></span>
								<span id="shipping_zipcode" ></span>
							</div>
						</div>
	      			</div>
	      			<div class="cl"></div>
	      		</div>
	      	</div>
		</div>
		<div id="shipment_div2" class="col-xs-12 no-padding-md">
			<checkout:shippingFormsSplit/>
		</div>
		<div class="cl"></div>
		<div class="col-md-offset-4 col-md-7 marginBottom30 shipping-ponumber">
			<c:choose>
				<c:when test="${cartData.orderingAccount.isPONumberRequired}">
					<strong><spring:theme code="PO.required.text" /> :</strong>
					<div><form:input type="text" onkeypress="ACC.checkout.poValueCheckonKeyPress(event)" oninput="ACC.checkout.poValueCheck(event)" path="PurchaseOrderNumber" class="form-control" id="poNumberReq" maxlength="30" /></div>
					<span id="errorPONumberRequired"></span>
					<form:errors path="PurchaseOrderNumber" />
				</c:when>
				<c:otherwise>
					<label for="PurchaseOrderNumber"><strong><spring:theme code="choosePickupDeliveryMethodPage.po.num" /></strong></label>
					<spring:message code="optional.text" var="Poplaceholder" />
					<form:input type="text" onkeypress="ACC.checkout.poValueCheckonKeyPress(event)" oninput="ACC.checkout.poValueCheck(event)" class="form-control" path="PurchaseOrderNumber" maxlength="30" placeholder="${Poplaceholder}" />
				</c:otherwise>
			</c:choose>
		</div>
		<div class="col-md-12 no-padding-md">
			<div id="shipping_div" class="row topBottom-border">
				<div class="col-md-3 padding0 ${isNationalShipping? 'hidden': ''}">
					<svg xmlns="http://www.w3.org/2000/svg" width="43.761" height="30" viewBox="0 0 43.761 30"><defs><style>.icon1{fill:#78a22f;}</style></defs><g transform="translate(-424.7 -141.187)"><path class="icon1" d="M438.733,146.913a1.351,1.351,0,0,0-.778,1.226v16.1a1.348,1.348,0,0,0,.779,1.225L451,171.081a1.51,1.51,0,0,0,1.128-.006l12.247-5.61a1.351,1.351,0,0,0,.777-1.225v-16.1a1.354,1.354,0,0,0-.779-1.226L452.108,141.3a1.56,1.56,0,0,0-.57-.11,1.368,1.368,0,0,0-.546.111Zm11.485,20.82-9.549-4.345V150.261l9.549,4.345Zm12.215-4.345-9.548,4.345V154.606l9.548-4.345Zm-10.881-19.334,8.984,4.131-8.984,4.131-8.984-4.131Z" transform="translate(3.314 0)"/><g transform="translate(424.7 148.21)"><path class="icon1" d="M435.1,146.8h-4.54a1.334,1.334,0,1,0,0,2.667h4.54a1.32,1.32,0,0,0,1.334-1.334,1.207,1.207,0,0,0-.346-.9A1.372,1.372,0,0,0,435.1,146.8Z" transform="translate(-423.567 -146.805)"/><path class="icon1" d="M435.667,152.122h-7.349a1.334,1.334,0,0,0,0,2.668h7.4a1.319,1.319,0,0,0,1.334-1.334A1.358,1.358,0,0,0,435.667,152.122Z" transform="translate(-424.129 -145.476)"/><path class="icon1" d="M436.238,157.4h-10.2a1.334,1.334,0,0,0,0,2.667h10.2a1.32,1.32,0,0,0,1.334-1.334,1.218,1.218,0,0,0-.338-.911A1.379,1.379,0,0,0,436.238,157.4Z" transform="translate(-424.7 -144.156)"/></g></g></svg>
					<p class="shipping-info-text marginBottom20 m-b-30-xs"> <spring:theme code="parcel.info.text1" /></p>
				</div>
				<div class="col-md-3 p-l-0 no-padding-xs ${isNationalShipping? 'hidden': ''}">
					<svg xmlns="http://www.w3.org/2000/svg" width="56.405" height="24" viewBox="0 0 56.405 24"><defs><style>.icon2{fill:#78a22f;}</style></defs><g transform="translate(-363.381 -95.993)"><path class="icon2" d="M404.023,109.2H397.76a1.452,1.452,0,0,0,0,2.9h6.3a1.45,1.45,0,0,0,1.44-1.458A1.477,1.477,0,0,0,404.023,109.2Z" transform="translate(-6.631 -2.659)"/><path class="icon2" d="M429.64,96.449a1.5,1.5,0,0,0-1.073-.456H415.643c-.018,0-.034,0-.052.006a1.344,1.344,0,0,0-.942.372,1.492,1.492,0,0,0-.346.517c-.012.029-.018.059-.028.089a1.563,1.563,0,0,0-.054.18,1.48,1.48,0,0,0-.028.28l0,.01v21.1a1.453,1.453,0,0,0,1.454,1.45,1.485,1.485,0,0,0,1.452-1.45V107.549h7.935a1.443,1.443,0,0,0,1.44-1.434,1.362,1.362,0,0,0-.375-1.014,1.5,1.5,0,0,0-1.073-.455H417.1V98.9h11.478a1.441,1.441,0,0,0,1.44-1.434A1.362,1.362,0,0,0,429.64,96.449Z" transform="translate(-10.231 0)"/><path class="icon2" d="M383.64,96.09a1.358,1.358,0,0,0-.458-.1c-.007,0-.014,0-.022,0h-.012a1.383,1.383,0,0,0-1.015.376,1.429,1.429,0,0,0-.138.164l-8,8-8.115-8.115a1.425,1.425,0,0,0-.724-.4h0a1.406,1.406,0,0,0-.289-.033h-.009a1.379,1.379,0,0,0-1.015.376,1.49,1.49,0,0,0-.368.593,1.254,1.254,0,0,0-.055.178c0,.015,0,.03-.006.046a1.359,1.359,0,0,0-.026.206c0,.01,0,.018,0,.028s0,.016,0,.024v21.1a1.451,1.451,0,0,0,1.452,1.45h0a1.455,1.455,0,0,0,1.451-1.45v-17.6l6.67,6.67,0,0,0,0a1.455,1.455,0,0,0,2.051,0l6.661-6.661v17.584a1.451,1.451,0,0,0,1.452,1.45h0a1.456,1.456,0,0,0,1.451-1.45V97.45A1.441,1.441,0,0,0,383.64,96.09Z" transform="translate(0 0)"/></g></svg>
					<p class="shipping-info-text marginBottom20 m-b-30-xs"> <spring:theme code="parcel.info.text2" /></p>
				</div>
				<div class="col-md-3 p-l-0 no-padding-xs ${isNationalShipping? 'hidden': ''}">
					<svg xmlns="http://www.w3.org/2000/svg" width="30.149" height="30" viewBox="0 0 30.149 30"><defs><style>.icon3{fill:#78a22f;}</style></defs><g transform="translate(-457.644 -94.094)"><path class="icon3" d="M480.206,106.995v-5.318a1.4,1.4,0,0,0-.228-.737l-4.636-6.348a1.424,1.424,0,0,0-1.043-.5H463.551a1.438,1.438,0,0,0-1.064.524l-4.6,6.307a1.137,1.137,0,0,0-.24.753V114.6a1.277,1.277,0,0,0,1.291,1.26H470.17a8.814,8.814,0,1,0,10.037-8.864Zm-9.989-10.35h3.413l2.754,3.771h-6.167Zm-6,0h3.417v3.771h-6.2Zm-3.989,16.693v-10.4h7.411v3.312a1.334,1.334,0,0,0,2.667,0v-3.312h7.318V107a8.769,8.769,0,0,0-7.161,6.336Zm18.75,8.327a6.143,6.143,0,1,1,6.295-6.141A6.227,6.227,0,0,1,478.977,121.666Z" transform="translate(0 0)"/><path class="icon3" d="M485.743,118.13h-1.49V116a1.261,1.261,0,0,0-2.521,0v3.361a1.246,1.246,0,0,0,1.26,1.229h2.751a1.23,1.23,0,1,0,0-2.459Z" transform="translate(-4.015 -3.866)"/></g></svg>
					<p class="shipping-info-text marginBottom20 m-b-30-xs"><spring:theme code="parcel.info.text3" /></p>
				</div>
				<div class="col-md-3 padding0 ${isNationalShipping? 'hidden': ''}">
					<svg xmlns="http://www.w3.org/2000/svg" width="54.02" height="30" viewBox="0 0 54.02 30"><defs><style>.icon4{fill:#78a22f;}</style></defs><g transform="translate(-271.041 -197.386)"><path class="icon4" d="M304.008,203.113a1.349,1.349,0,0,0-.777,1.226v16.1a1.348,1.348,0,0,0,.779,1.225l12.261,5.617a1.512,1.512,0,0,0,1.128-.007l12.25-5.61a1.352,1.352,0,0,0,.777-1.225v-16.1a1.351,1.351,0,0,0-.778-1.226L317.387,197.5a1.537,1.537,0,0,0-.57-.11,1.361,1.361,0,0,0-.546.111Zm11.485,20.82-9.549-4.345V206.46l9.549,4.345Zm12.214-4.345-9.547,4.345V210.805l9.547-4.345Zm-10.88-19.333,8.983,4.132-8.983,4.132-8.984-4.132Z" transform="translate(-5.365 0)"/><g transform="translate(281.299 204.404)"><path class="icon4" d="M296.019,205.808h-4.54a1.333,1.333,0,1,0,0,2.667h4.544a1.321,1.321,0,0,0,1.333-1.307v-.027a1.208,1.208,0,0,0-.347-.9A1.369,1.369,0,0,0,296.019,205.808Z" transform="translate(-284.482 -205.808)"/><path class="icon4" d="M294.886,221.706h-10.2a1.333,1.333,0,0,0,0,2.667h10.2a1.321,1.321,0,0,0,1.333-1.307v-.027a1.218,1.218,0,0,0-.337-.912A1.378,1.378,0,0,0,294.886,221.706Z" transform="translate(-283.35 -208.458)"/></g><g transform="translate(271.041 209.051)"><path class="icon4" d="M273.551,213.932l.239-1.138h3.089l.3-1.41h-4.751L271.041,218H272.7l.555-2.663h3.03l.294-1.41Z" transform="translate(-271.041 -211.384)"/><path class="icon4" d="M282.514,215.655a3.223,3.223,0,0,0-.257-.369,2.268,2.268,0,0,0,1.216-.641A2.049,2.049,0,0,0,284,213.2a1.938,1.938,0,0,0-.273-1.048,1.445,1.445,0,0,0-.735-.614,3.48,3.48,0,0,0-1.179-.159h-2.927L277.5,218h1.661l.542-2.591h.378a1.022,1.022,0,0,1,.535.091,1.77,1.77,0,0,1,.5.739c.321.738.537,1.288.647,1.633l.04.128h1.784l-.1-.249A15.861,15.861,0,0,0,282.514,215.655Zm-2.266-2.861h1.268a1.509,1.509,0,0,1,.643.075.384.384,0,0,1,.191.357.747.747,0,0,1-.158.448.914.914,0,0,1-.446.324,4.483,4.483,0,0,1-1.314.113h-.459Z" transform="translate(-272.117 -211.384)"/><path class="icon4" d="M290.514,215.261l.294-1.41h-3.239l.222-1.057h3.35l.294-1.41h-5.011L285.042,218H290.3l.3-1.41h-3.636l.308-1.334Z" transform="translate(-273.375 -211.384)"/><path class="icon4" d="M298.137,212.794l.294-1.41H293.42L292.038,218H297.3l.3-1.41h-3.636l.308-1.334h3.242l.294-1.41h-3.239l.222-1.057Z" transform="translate(-274.541 -211.384)"/></g></g></svg>
					<p class="shipping-info-text marginBottom20 no-margin-xs"><spring:theme code="parcel.info.text4" arguments="${cartData.freeShippingThreshold}"/></p>
				</div>
				<div id="shipping_btn" class="payment_cbtn col-md-4 col-xs-12 col-sm-4 padding0 margin-top-20">
					<button type="button" class="btn btn-primary btn-block addShippingMainFormSubmit"><spring:theme code="choosePickupDeliveryMethodPage.continue.to.payment" /></button>
				</div>
		   		<div class="cl"></div>
			</div>
		</div>
	</form:form>
	<!-- ./Checkout Shipping Address Block --> 
	<c:if test="${isGuestUser eq true }">
		<form:form id="SiteOneGCShippingForm" modelAttribute="SiteOneGCContactForm" action="${homelink}checkout/multi/saveAlternateContactDetails" method="POST">
			<div id="shipmentForm" class="row contactForm_parcel-shipping sipping-form" style="display:none">
				<div class="col-md-12"><span class="addAddressError" ></span></div>
				<div class="col-md-6 ">
					<formElement:formInputBox idKey="checkoutShippingfirstName" labelKey="First Name" path="firstName" placeholder="First Name" inputCSS="form-control" mandatory="true" />
					<span id="errorShippingFirstName"></span>
				</div>
				<div class="col-md-6">
					<formElement:formInputBox idKey="checkoutShippinglastName" labelKey="Last Name" path="lastName" placeholder="Last Name" inputCSS="form-control" mandatory="true" />
					<span id="errorShippingLastName"></span>
				</div>
				<div class="col-md-12">
					<formElement:formInputBox idKey="checkoutShipping.companyName" labelKey="Company Name" path="companyName" placeholder="Optional" inputCSS="form-control" />
				</div>
				<div class="col-md-6">
					<formElement:formInputBox idKey="checkoutShippingphone" labelKey="Phone Number" path="phone" maxlength="10" placeholder="Phone Number" inputCSS="form-control" mandatory="true" />
					<span id="errorShippingPhoneNumber"></span>
				</div>
				<div class="col-md-6">
					<formElement:formInputBox idKey="checkoutShippingemail" labelKey="Email" path="email" inputCSS="form-control" placeholder="Email" mandatory="true" />
					<span id="errorShippingEmailAddress"></span>
				</div>
				<div class="col-md-12">
					<formElement:formInputBox idKey="checkoutShippingaddressLine1" labelKey="Address" path="addressLine1" mandatory="true" placeholder="Address (Maximum of 50 characters)" maxlength="50" inputCSS="form-control" />
					<span id="errorShippingAddressLine1"></span>
				</div>
				<div class="col-md-12">
					<formElement:formInputBox idKey="checkoutShipping.addressLine2" labelKey="Apartment, Suite, Building, Etc." path="addressLine2" placeholder="Apartment, Suite, Building, Etc. (Maximum of 50 characters)" maxlength="50" inputCSS="form-control" mandatory="true" />
				</div>
				<div class="col-md-5">
					<formElement:formInputBox idKey="checkoutShippingcity" labelKey="City" path="city" placeholder="City (Maximum of 50 characters)" maxlength="50" inputCSS="form-control" mandatory="true" />
				</div>
				<div class="custom_dropdown col-md-4">
					<formElement:customRegionSelectBox idKey="checkoutShippingstate" labelKey="State" selectCSSClass="form-control" path="state" mandatory="true" skipBlank="false" skipBlankMessageKey="request.form.select.empty" items="${states}" />
					<span id="errorShippingState"></span>
				</div>
				<div class="col-md-3">
					<formElement:formInputBox idKey="checkoutShippingzip" labelKey="Zip" path="zip" inputCSS="form-control" placeholder="Zip" mandatory="true" maxlength="10" />
					<span id="errorShippingZipcode"></span>
				</div>
				<div class="cl"></div>
				<div class="col-md-4"><button type="button" class="btn btn-primary btn-block m-b-15 addShippingFormSubmit"> <spring:theme code="choosePickupDeliveryMethodPage.continue.to.payment" /></button></div>
			</div>
		</form:form>
	</c:if>
	</div>
	  	</c:if>

	
		    
		 
			<%-- </ycommerce:testId> --%>
			<div class="cl"></div>
			
	</div>
	</div>
	</c:if>
	
	
	
	
	<!-- MixedCart scenario -->
	<c:if test="${isMixedCartEnabled eq true}">
	<div class="step-body kaxsdc mixedcart-checkout" data-event="load">
	<c:set var="hasPickup" value="false" />
	<c:set var="hasDelivery" value="false" />
	<c:set var="hasShipping" value="false" />
 	<c:forEach items="${cartData.deliveryModeAndBranchOrderGroups}" var="groupData" varStatus="status">
 	<c:if test="${groupData.deliveryMode.code eq 'pickup'}">	
 		<c:set var="hasPickup" value="true" />
 	</c:if>
 	<c:if test="${groupData.deliveryMode.code eq 'standard-net'}">	
 		<c:set var="hasDelivery" value="true" />
 	</c:if>
 	<c:if test="${groupData.deliveryMode.code eq 'free-standard-shipping'}">	
 		<c:set var="hasShipping" value="true" />
 	</c:if>
 	
 </c:forEach>
 
 
 
 
 <!-- Contact Block Starts -->
 <div class="contact-box mixedcart-contact-box">
	<div class="contact-data-box col-md-12">
<div class="title-bar order-confirmation-page-bottom row ${isMixedCartEnabled? 'title-mobile-view': ' '}">
		<div class="numberCircle-div"></div>
					<div class="col-xs-7 col-sm-7  col-md-8 order-summary-title">
						<h1 class="order-confirmation-page-title">
						<spring:theme code="checkout.contact-title"/>
						</h1>
				</div>
					<div class="col-xs-3 text-right ">
						<c:if test="${isGuestUser}">
							<span class="edit-mixedcart-contact-information edit-btn-acco" style="display:none;"><spring:theme code="mixcart.contact.edit" /></span>
						</c:if>
						<c:if test="${isGuestUser eq false}">
							<span id="pickUpChangeContact" class="mixCartChangeContact edit-btn-acco"><spring:theme code="mixcart.contact.edit" /></span>
						</c:if>
					</div> 
				</div>
					<checkout:mixcartContactDetails/> 
					</div>
				</div>
 
 <!-- Contact Block ends -->
 <form:form id="siteOneOrderTypeForm" modelAttribute="siteOneOrderTypeForm" action="${homelink}checkout/multi/siteOne-checkout/saveBranchPickupDetails" method="POST">
	         <form:hidden path="storeId" value="${sessionStore.storeId}" />
	 		<div class="cl"></div>
	 		<c:if test="${hasPickup}">
				<div class="pickup-box">
					<checkout:pickupfulfillment/>
				</div>
			</c:if>
			<div class="cl"></div>
			<c:if test="${hasDelivery}">
				<div class="delivery-box">
					<checkout:deliveryfulfillment/>
				</div>
			</c:if>
			<div class="cl"></div>
			<c:if test="${hasShipping}">
				<div class="shipping-box">
					<checkout:shippingfulfillment/>
				</div>
			</c:if>
	
	</form:form>
 
 </div>
 
	
	</c:if>
<c:if test="${isSplitCartCheckoutPickup}">
	<div class="col-md-12 marginTop30 p-l-0 splitShippingOrder">
		<div class="bg-gray padding-20 shippingLabel text-align-center text-white"><spring:theme code="checkout.summary.shippingorder" /></div>
	</div>
	<div class="bg-white col-md-12 marginbottom30 contact-data-box splitShipping">
		<div class="row">
			<div class="splitShippingContent">
				<div class="col-md-1 numberCircle-div m-r-10"></div>
				<div class="col-md-11 title-bar order-confirmation-page-bottom row">
					<div class="row">
						<div class="col-md-4 col-xs-6 col-sm-8 order-summary-title">
							<h1 class="order-confirmation-page-title">
							<span class="pickup-title" style=""><spring:theme code="shipping.title.text" /></span>
							</h1>
						</div>
						<div class="col-md-8 col-xs-1 text-right text-right edit-split-shipping-information p-l-40-xs hidden">
							<!-- <span class="edit-link"> -->
								<a id="split-shipping_Edit" href="#"><spring:theme code="checkout.summary.edit"/></a>
							<!-- </span> -->
						</div>
						<div class="col-md-8 col-xs-12 text-right split-shipping-right">
							<div class="f-s-12 flex flex-center font-arial justify-flex-end text-gray split-shipping-fee">Shipping Fee&nbsp;<span class="f-s-26 f-w-500 font-geogrotesque text-default">
							<c:choose>
							<c:when test="${not empty flatRateShippingFee && flatRateShippingFee ne '0.0'}">
								&#36;${flatRateShippingFee}
							</c:when>
							<c:otherwise>
								<spring:theme code="text.shipping.free"/>
							</c:otherwise>
							</c:choose></span> 
							</div>
						</div>
					</div>
				</div>
			</div>
			<form:form  id="SiteOneGCMainShippingForm" modelAttribute="siteOneOrderTypeForm" action="${homelink}checkout/multi/saveFulfilmentDetails" method="POST">
				<form:hidden path="storeId" value="${sessionStore.storeId}"/>
				<form:hidden path="orderType" value="${orderType}" />
				<div class="split-shipping-Details hidden">
					<div class="split-shipping-address">
						<div class="row">
							<div class="col-md-12">
								<div class="delivery-address"></div>
							</div>
							<div class="cl"></div>
						</div>
					</div>
				</div>
				<div class="cls">
					<checkout:shippingFormsSplit/>
				</div>
			</form:form>
		</div>
	</div>
	<c:if test="${orderType eq 'DELIVERY'}">
		<div class="payment_cbtn col-md-3 col-xs-12 col-sm-6 no-padding-md margin-top-20 m-b-40">
			<button type="button" class="btn btn-block btn-primary bold-text splitShippingPickupSubmit" onclick="ACC.checkout.continueCheckoutProcess()"><spring:theme code="choosePickupDeliveryMethodPage.continue.to.payment" /></button>
		</div>
	</c:if>
</c:if>	
<c:if test="${isGuestUser eq true}">
<div class="col-md-12  padding0">
<multiCheckout:paymentMethodPage/>
</div>
</c:if>


	
<c:if test="${isGuestUser eq false}">
<div class="col-md-12  padding0">
<multiCheckout:paymentMethodPageB2BUser/>
</div>
</c:if>
</div>
	 
	<c:if test="${!isSplitCartCheckoutPickupFE}">
		<div class="col-md-3 col-xs-12 page-choosePickupDeliveryMethodPage ${isMixedCartEnabled? 'mixedcart-leftbar padding0': 'no-padding-xs padding-rightZero'}">
			<div class="col-xs-12 pick_delivery_summary padding-rightZero no-padding-lft-xs">
				<span class="hidden-xs hidden-sm"><storepickup:checkoutProductDetails productTitle="checkout.orderConfirmation.yourItems" entries="${cartData.entries}"/></span>
				<storepickup:checkoutOrderSummary/>
			</div>
		</div>
	</c:if>
	<c:if test="${isSplitCartCheckoutPickupFE}">
	<div class="col-md-3 col-xs-12 page-choosePickupDeliveryMethodPage no-padding-xs padding-rightZero">
			<div class="col-xs-12 pick_delivery_summary padding-rightZero no-padding-lft-xs">
				<c:if test="${orderType eq 'PICKUP'}">
					<span class="hidden-xs hidden-sm"><storepickup:checkoutProductDetails storeDetails="splitPickup" productTitle="checkout.orderConfirmation.splitPickup" entries="${cartData.pickupAndDeliveryEntries}"/></span>
				</c:if>
				<c:if test="${orderType eq 'DELIVERY'}">
					<span class="hidden-xs hidden-sm"><storepickup:checkoutProductDetails storeDetails="splitPickup" productTitle="text.account.order.title.deliveryItems" entries="${cartData.pickupAndDeliveryEntries}"/></span>
				</c:if>
				<span class="hidden-xs hidden-sm"><storepickup:checkoutProductDetails storeDetails="splitShipping" productTitle="checkout.orderConfirmation.splitShipping" entries="${cartData.shippingOnlyEntries}"/></span>
				<storepickup:checkoutOrderSummary/>
			</div>
		</div>
	</c:if>
	<sec:authorize var="isAdmin" access="hasAnyRole('ROLE_B2BADMINGROUP')"/>
      <c:if test="${isAdmin or enableAddDeliveryAddress}">
	 <div id="addresscheckout" style="display:none"><common:addressCheckout></common:addressCheckout></div>
	</c:if>
	
	<div class="declinePickupError hidden">
	  <p class="bold-text f-s-16 f-w-600 text-align-center text-red">We couldn't process your payment right now</p>
	  <p class="bold-text f-s-14 f-w-400 text-align-center text-red">For your security, we've temporarily paused further payment attempts after several unsuccessful tries. Please continue with your order using a different Payment Option in Checkout.</p>
	 </div>
	 <div class="declineShippingError hidden">
	  <p class="bold-text f-s-16 f-w-600 text-align-center text-red">We couldn't process your payment right now</p>
	  <p class="bold-text f-s-14 f-w-400 text-align-center text-red">For your security, we've temporarily paused further payment attempts after several unsuccessful tries. Please visit your nearest SiteOne branch to continue with your order.</p>
	 </div>
	<div class="declineCcGuestError hidden">
	  <p class="bold-text f-s-16 f-w-600 text-align-center text-red"><spring:theme code="checkout.guestCcDeclined.heading" /></p>
	  <p class="bold-text f-s-14 f-w-400 text-align-center text-red"><spring:theme code="checkout.guestCcDeclined.message" /></p>
	 </div>
	
	<script type='text/javascript' src='${ddcUrl}${merchantId}'> </script>
	<script type='text/javascript'>
	$(document).ready(function () {
	if (!sessionStorage.MercSessId)
	{
        var client=new ka.ClientSDK();
        
     	// The auto load looks for an element with the 'kaxsdc' class and
        // data-event equal to a DOM event (load in this case). Data collection begins
        // when that event fires on that element--immediately in this example
        client.autoLoadEvents();
 
       // OPTIONAL
        client.setupCallback(
            {
                // fires when collection has finished - this example would not enable the 
                // login button until collection has completed
                'collect-end':
                    function(params) 
                    {
                		debugger
                		if (typeof(Storage) !== "undefined") 
                		{
                			sessionStorage.MercSessId = params.MercSessId;
                			$("#kountSessionId").val(sessionStorage.MercSessId);
              			}
                	}
            });
        // END OPTIONAL SECTION
        // The auto load looks for an element with the 'kaxsdc' class and
        // data-event equal to a DOM event (load in this case). Data collection begins
        // when that event fires on that element--immediately in this example
        client.autoLoadEvents();
	}
	$("#kountSessionId").val(sessionStorage.MercSessId);
    });
    </script>	       	 
</template:page>