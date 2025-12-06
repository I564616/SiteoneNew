<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="b2b-order" tagdir="/WEB-INF/tags/addons/siteoneacceleratoraddon/responsive/order" %>
<template:page pageTitle="${pageTitle}">
		 
		<link rel="stylesheet" type="text/css" media="all" href="${themeResourcePath}/css/jquery-editable-select.min.css" />
		<spring:htmlEscape defaultHtmlEscape="true" />

<h1 class="headline">
   <spring:theme code="text.account.order.order"/> # ${masterHybrisOrderData.masterOrderNumber}
 </h1>


			

<c:if test="${not empty masterHybrisOrderData.pickupEntries}">
<div class="row marginTop35">
<div class="title-bar no-background-color col-md-12">
				<div class="col-xs-12 padding0 order-confirmation-page-title">
					<spring:theme code="orderunconsignedentries.branchPickUpDetails.mixedcart" />
				</div>
			</div>
			</div>
<div class="col-md-12 fulfillment-border-box marginTop20">
	<div class="row">
		<div class="col-md-4">
			<div class="orderfulfillments-title bold"><spring:theme code="orderUnconsignedEntries.pickUpContact" /></div>
			<div>${masterHybrisOrderData.pickupContact.name}</div>
			<div><a class="tel-phone" href="tel:${masterHybrisOrderData.pickupContact.contactNumber}">${masterHybrisOrderData.pickupContact.contactNumber}</a></div>
			<div><a href="mailto:${masterHybrisOrderData.pickupContact.email}">${masterHybrisOrderData.pickupContact.email}</a></div>
		</div>
	
	
		<div class="col-md-8">
			<div class="orderfulfillments-title bold"><spring:theme code="mixcart.pickup.instruction" /></div>
				<div>
						<c:choose>
								<c:when test="${not empty masterHybrisOrderData.pickupInstructions}">
									${masterHybrisOrderData.pickupInstructions}
								</c:when>
								<c:otherwise>NA</c:otherwise>
							</c:choose>
				</div>
		</div>
	
	</div>

<div class="cl"></div>
</div>
<div class="cl"></div>
<c:forEach var="entry" items="${masterHybrisOrderData.pickupEntries}">
				  <c:forEach var="consignmentEntry" items="${entry.value}">
					  <div class="row marginTop35">
						<div class="title-bar no-background-color col-md-12">
										<div class="col-xs-12 padding0 order-confirmation-page-title">
										<c:set var="counter" value="${counter+1}"/>
										<c:set var="storeNo" value="${entry.key}" />
										<c:set var="storeName" value="${fn:split(storeNo, '|')}" />
											<spring:theme code="orderSummaryPage.pickup.from" /> #${counter}: <span class="store-details">${storeName[0]}</span>
										</div>
										<div class="store-data">${storeName[1]} | ${storeName[2]} | ${storeName[3]} | ${storeName[4]}</div>
									</div>
									</div>
					  
					  
					  
							<div class="fulfillment-border-box marginTop20 marginbottomp30">
							<div class="fullfillment-products"><order:orderEntryDetailsMasterOrder orderEntry="${consignmentEntry.orderEntry}" itemIndex="${loop.index}"/>
							<div class="cl"></div>
							</div>
							<div class="cl"></div>
							</div>
				</c:forEach>
</c:forEach>
</c:if>	
		
	<div class="cl"></div>		
<c:if test="${not empty masterHybrisOrderData.deliveryEntries}">
<div class="row marginTop35">
<div class="title-bar no-background-color col-md-12">
				<div class="col-xs-12 padding0 order-confirmation-page-title">
					<spring:theme code="orderunconsignedentries.deliveryDetails.mixedcart.title" />
				</div>
			</div>
			</div>
<div class="col-md-12 fulfillment-border-box marginTop20">
	<div class="row">
		<div class="col-md-4">
			<div class="orderfulfillments-title bold"><spring:theme code="choosePickupDeliveryMethodPage.Delivery.contact"/></div>
			<div>${masterHybrisOrderData.deliveryContact.name}</div>
			<div><a class="tel-phone" href="tel:${masterHybrisOrderData.deliveryContact.contactNumber}">${masterHybrisOrderData.deliveryContact.contactNumber}</a></div>
			<div><a href="mailto:${masterHybrisOrderData.deliveryContact.email}">${masterHybrisOrderData.deliveryContact.email}</a></div>
		</div>
	<div class="col-md-4">
	<div class="orderfulfillments-title bold"><spring:theme code="orderUnconsignedEntries.DeliveryInfo"/></div>
			 
						<div>${masterHybrisOrderData.deliveryAddress.line1}</div>
						<div>${masterHybrisOrderData.deliveryAddress.line2}</div>
						<div>${masterHybrisOrderData.deliveryAddress.district}, ${masterHybrisOrderData.deliveryAddress.region.isocodeShort} &nbsp; ${masterHybrisOrderData.deliveryAddress.postalCode}</div>
	</div>
	
		<div class="col-md-4">
			<div class="orderfulfillments-title bold"><spring:theme code="orderunconsignedentries.deliveryInstruction"/></div>
				<div>
						<c:choose>
								<c:when test="${not empty masterHybrisOrderData.deliveryInstructions}">
									${masterHybrisOrderData.deliveryInstructions}
								</c:when>
								<c:otherwise>NA</c:otherwise>
							</c:choose>
				</div>
		</div>
	
	</div>

<div class="cl"></div>
</div>
<div class="cl"></div>
			<c:forEach var="entry" items="${masterHybrisOrderData.deliveryEntries}">
				  <c:forEach var="consignmentEntry" items="${entry.value}">
					  <div class="row marginTop35">
						<div class="title-bar no-background-color col-md-12">
						<c:set var="storeNoDelivery" value="${entry.key}" />
										<c:set var="storeNameDelivery" value="${fn:split(storeNoDelivery, '|')}" />
										<div class="col-xs-12 padding0 order-confirmation-page-title">
										 
											<spring:theme code="orderunconsignedentries.deliveryfrom.mixedcart" /> : <span class="store-details">${storeNameDelivery[0]}</span>
										</div>
										 
									</div>
									</div>
					  
					  
					  
							<div class="fulfillment-border-box marginTop20 marginbottomp30">
							<div class="fullfillment-products"><order:orderEntryDetailsMasterOrder orderEntry="${consignmentEntry.orderEntry}" itemIndex="${loop.index}"/>
							<div class="cl"></div>
							</div>
							<div class="cl"></div>
							</div>
				</c:forEach>
</c:forEach>

			
</c:if>		
	

<c:if test="${not empty masterHybrisOrderData.shippingEntries}">
			<div class="row marginTop35">
			<div class="title-bar no-background-color col-md-12">
							<div class="col-xs-12 padding0 order-confirmation-page-title">
								<spring:theme code="orderunconsignedentries.ShippingDetails.mixedcart.title" />
							</div>
						</div>
			</div>
		<div class="col-md-12 fulfillment-border-box marginTop35">
		<div class="row">
			<div class="col-md-4">
				<div class="orderfulfillments-title bold"><spring:theme code="orderSummaryPage.parcel.shipping.to.contact.mixedcart" /></div>
				<div>${masterHybrisOrderData.shippingContact.name}</div>
				<div><a class="tel-phone" href="tel:${masterHybrisOrderData.shippingContact.contactNumber}">${masterHybrisOrderData.shippingContact.contactNumber}</a></div>
				<div><a href="mailto:${masterHybrisOrderData.shippingContact.email}">${masterHybrisOrderData.shippingContact.email}</a></div>
			</div>
			
			<div class="col-md-4">
	<div class="orderfulfillments-title bold"><spring:theme code="orderunconsignedentries.ShippingDetails.mixedcart.title" /></div>
			 
						<div>${masterHybrisOrderData.shippingAddress.line1}</div>
						<div>${masterHybrisOrderData.shippingAddress.line2}</div>
						<div>${masterHybrisOrderData.shippingAddress.district}, ${masterHybrisOrderData.shippingAddress.region.isocodeShort} &nbsp; ${masterHybrisOrderData.shippingAddress.postalCode}</div>
	</div>
	
		<div class="col-md-4">
			<div class="orderfulfillments-title bold"><spring:theme code="orderunconsignedentries.shippingUpInstruction.mixedcart"/></div>
				<div>
						<c:choose>
								<c:when test="${not empty masterHybrisOrderData.shippingInstructions}">
									${masterHybrisOrderData.shippingInstructions}
								</c:when>
								<c:otherwise>NA</c:otherwise>
							</c:choose>
				</div>
				</div>
			</div>

		<div class="cl"></div>
		</div>
<div class="cl"></div>
			 <c:forEach var="entry" items="${masterHybrisOrderData.shippingEntries}">
					   
							<div class="fulfillment-border-box marginTop20 marginbottomp30">
							<div class="fullfillment-products"><order:orderEntryDetailsMasterOrder orderEntry="${entry.orderEntry}" itemIndex="${loop.index}"/>
							<div class="cl"></div>
							</div>
							<div class="cl"></div>
							</div>
				</c:forEach>
</c:if>  	
	
<div class="row marginTop35">
			<div class="title-bar no-background-color col-md-12">
							<div class="col-xs-12 padding0 order-confirmation-page-title">
								<spring:theme code="paymentMethod.header"/>
							</div>
						</div>
			</div>	
 
<div class="marginTop35">
<order:orderTotalsItemMasterOrder order="${masterHybrisOrderData}" />
</div>
      
</template:page>