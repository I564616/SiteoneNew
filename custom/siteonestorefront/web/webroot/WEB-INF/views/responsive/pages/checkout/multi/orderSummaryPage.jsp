<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="multi-checkout" tagdir="/WEB-INF/tags/responsive/checkout/multi"%>
<%@ taglib prefix="multi-checkouts" tagdir="/WEB-INF/tags/responsive/checkout/multi"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<spring:url value="/checkout/multi/order-summary/placeOrder" var="placeOrderUrl"/>
<spring:url value="/checkout/multi/order-summary/voucher/apply" var="applyVoucherAction"/>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<spring:url value="/checkout/multi/order-summary/voucher/remove" var="removeVoucherAction"/>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="multiCheckout" tagdir="/WEB-INF/tags/responsive/checkout/multi" %>


<template:page pageTitle="${pageTitle}" hideHeaderLinks="true" showCheckoutSteps="true">
	<c:set var="isSplitMixedPickupBranchReview" value="false" />
	<c:if test="${not empty isSplitMixedCartEnabledBranch && isSplitMixedCartEnabledBranch ne null}" >
		<c:set var="isSplitMixedPickupBranchCheckout" value="${isSplitMixedCartEnabledBranch}" />
	</c:if>
	<c:if test="${not empty cartData.isShippingHubOnly && cartData.isShippingHubOnly ne null}" >
		<c:set var="isSplitCartShippingHubOnly" value="${cartData.isShippingHubOnly}"/>
	</c:if>
	<c:if test="${isSplitMixedPickupBranchCheckout && isSplitCartShippingHubOnly && (cartData.orderType eq 'PICKUP' ||  cartData.orderType eq 'DELIVERY')}" >
		<c:set var="isSplitMixedPickupBranchReview" value="true"/>
	</c:if>
	
	<input class="isSplitMixedPickupBranchReview" type="hidden" value='${isSplitMixedPickupBranchReview}'>

<c:if test="${cartData.orderType eq 'DELIVERY'}">
<div class="global-alerts homeOwner-msg hidden">
<div class="alert alert-info alert-dismissable"><button class="close" type="button" data-dismiss="alert" aria-hidden="true">x</button><spring:theme code="delivery.enable.condition.message"/></div>
</div>
</c:if>

<div class="row order-review-row-wrapper ${isMixedCartEnabled? 'mixedcart-review-order' : ''}">
    <div class="col-md-12">
		<div class="col-md-7 col-sm-6 p-l-0 p-t-40 p-t-20-xs text-default">
			<p class="f-s-22-xs-px f-s-32 font-Geogrotesque m-b-0 p-b-10 p-b-5-xs">
				<span><spring:theme code="checkout.multi.review.your.order"/></span>
			</p>
			<p class="font-size-14 font-small-xs bold">
				<spring:theme code="checkout.multi.review.your.order.note" />
			</p>
			<a class="choose-payment-header-link order-review-title-link " href="<c:url value="
				/checkout/multi/siteOne-checkout" />">
			<spring:theme code="checkout.multi.review.your.order.title" /></a>
		</div>
    <div class="order-info-wrapper-data row hidden-sm hidden-lg hidden-md"> 
	<div class="col-xs-12  marginBottom10">
	<div class="marginTopBVottom20 order-number-name">
		<div class="row">
		<div  class="col-xs-4 col-sm-4 bold order-info-text-wrapper padding0"><spring:theme code="orderUnconsignedEntries.orderDateandtime" /></div>
		<div  class="col-xs-8 col-sm-8 marginBottom10 order-info-dateandtime-wrapper">
					<fmt:formatDate var="fmtDate" value="${cartData.orderDate}" pattern="MMM dd,YYYY hh:mm:ss a"/>
					<input type="hidden" id="requestedDateinUTC"  value="${fmtDate}"/>
					<span id="requestedDateInLocal"><fmt:formatDate value="${cartData.orderDate}" pattern="MMM dd,YYYY hh:mma" /></span>
						
		</div>
		</div>
		<div class="row">
		<div  class="col-xs-4 col-sm-4 col-md-2 bold order-info-text-wrapper padding0"><spring:theme code="orderUnconsignedEntries.poNumber" /></div>
		<div  class="col-xs-8 col-sm-8 col-md-10 marginBottom10 order-info-dateandtime-wrapper ">
					<c:choose>
						<c:when test="${not empty cartData.purchaseOrderNumber}">
							${cartData.purchaseOrderNumber}<br>
						</c:when>
						<c:otherwise>NA</c:otherwise>
					</c:choose>
		</div>
		</div>
		<c:if test="${isSplitMixedPickupBranchReview}">
			<c:choose>
				<c:when test="${guestUsers eq 'guest'}">

				</c:when>
				<c:otherwise>
					<div class="row">
						<div  class="col-xs-4 col-sm-4 col-md-2 bold order-info-text-wrapper padding0"><spring:theme code="orderSummaryPage.acc.num"/></div>
						<div  class="col-xs-8 col-sm-8 col-md-10 marginBottom10 order-info-dateandtime-wrapper">
									${sessionShipTo.displayId}
						</div>
						</div>
				</c:otherwise>
			</c:choose>
		
			<div class="row">
				<div  class="col-xs-4 col-sm-4 col-md-2 bold order-info-text-wrapper padding0"><spring:theme code="orderSummaryPage.acc.ordercontact"/></div>
				<div  class="col-xs-8 col-sm-8 col-md-10 marginBottom10 order-info-dateandtime-wrapper ">
					<c:choose>
						<c:when test="${guestUsers eq 'guest'}">
							<span>
								${cartData.b2bCustomerData.firstName}&nbsp;${cartData.b2bCustomerData.lastName}
							</span>
							<span>|</span>
							<c:choose>  
								<c:when test="${not empty cartData.guestContactPerson.contactNumber}">
									<a class="tel-phone" href="tel:${cartData.guestContactPerson.contactNumber}">${cartData.guestContactPerson.contactNumber}</a>
								</c:when>
								<c:otherwise>
									<spring:theme code="orderSummaryPage.na" />
								</c:otherwise>
				  			</c:choose>
							<span>|</span>
							<span>${cartData.guestContactPerson.email}</span>
						</c:when>
						<c:otherwise>
							<span>
								${cartData.contactPerson.name}
							</span>
							<span>|</span>
							<c:choose>  
								<c:when test="${not empty cartData.contactPerson.contactNumber}">
									<a class="tel-phone" href="tel:${cartData.contactPerson.contactNumber}">${cartData.contactPerson.contactNumber}</a>
								</c:when>
								<c:otherwise>
									<spring:theme code="orderSummaryPage.na" />
								</c:otherwise>
				  			</c:choose>
							<span>|</span>
							<span>${cartData.contactPerson.email}</span>
						</c:otherwise>
					</c:choose>

				</div>
				</div>
		</c:if>
		
	</div>
	</div> 
	</div>
    <div class="row col-sm-6 col-md-5 button-payment palceOrder-button-summary">
	     	<form:form action="${placeOrderUrl}" id="placeOrderForm1" modelAttribute="placeOrderForm">
		        <button id="placeOrder" type="button" class="btn btn-primary btn-place-order btn-block button-payment-val ${cartData.b2bCustomerData.needsOrderApproval eq true ?'orderapproval_btn':''}">
		            <c:choose>
						<c:when test="${cartData.b2bCustomerData.needsOrderApproval eq true}">
							<spring:theme code="checkout.summary.submitForApproval" text="Place Order"/>
						</c:when>
						<c:otherwise>
		            		<spring:theme code="checkout.summary.placeOrder" text="Place Order"/>
						</c:otherwise>
					</c:choose>
		        </button>
	        </form:form> 
     	</div>
    </div>
    <%-- <multi-checkouts:checkoutSummaryPoNumber/> --%>
<div class="order-info-wrapper-data row hidden-xs"> 
<div class="col-xs-12  marginBottom10 no-padding-md">

<div class="col-xs-12 marginTopBVottom20 order-number-name">
	<div class="row">
	<div  class="col-sm-4 col-md-1 w-10p bold order-info-text-wrapper"><spring:theme code="orderUnconsignedEntries.orderDateandtime" /></div>
	<div  class="col-sm-8 col-md-10 marginBottom10 order-info-dateandtime-wrapper">
				<fmt:formatDate var="fmtDate" value="${cartData.orderDate}" pattern="MMM dd,YYYY hh:mm:ss a"/>
				<input type="hidden" id="requestedDateinUTC"  value="${fmtDate}"/>
				<span id="requestedDateInLocal"><fmt:formatDate value="${cartData.orderDate}" pattern="MMM dd,YYYY hh:mma" /></span>
					
	</div>
	</div>
	<div class="row">
	<div  class="col-sm-4 col-md-1 w-10p bold order-info-text-wrapper"><spring:theme code="orderUnconsignedEntries.poNumber" /></div>
	<div  class="col-sm-8 col-md-10 marginBottom10 order-info-dateandtime-wrapper ">
				<c:choose>
					<c:when test="${not empty cartData.purchaseOrderNumber}">
						${cartData.purchaseOrderNumber}<br>
					</c:when>
					<c:otherwise>NA</c:otherwise>
				</c:choose>
	</div>
	</div>
	<c:if test="${isSplitMixedPickupBranchReview}">
		<div class="row">
			<div  class="col-sm-4 col-md-1 w-10p bold order-info-text-wrapper p-r-0-imp"><spring:theme code="orderSummaryPage.acc.num"/></div>
			<div  class="col-sm-8 col-md-9 marginBottom10 order-info-dateandtime-wrapper ">
						${sessionShipTo.displayId}
			</div>
			</div>
			<div class="row">
				<div  class="col-sm-4 col-md-1 w-10p bold order-info-text-wrapper"><spring:theme code="orderSummaryPage.acc.ordercontact"/></div>
				<div  class="col-sm-8 col-md-9 marginBottom10 order-info-dateandtime-wrapper ">
					<c:choose>
						<c:when test="${guestUsers eq 'guest'}">
							<span>
								${cartData.b2bCustomerData.firstName}&nbsp;${cartData.b2bCustomerData.lastName}
							</span>
							<span>|</span>
							<c:choose>  
								<c:when test="${not empty cartData.guestContactPerson.contactNumber}">
									<a class="tel-phone" href="tel:${cartData.guestContactPerson.contactNumber}">${cartData.guestContactPerson.contactNumber}</a>
								</c:when>
								<c:otherwise>
									<spring:theme code="orderSummaryPage.na" />
								</c:otherwise>
				  			</c:choose>
							<span>|</span>
							<span>${cartData.guestContactPerson.email}</span>
						</c:when>
						<c:otherwise>
							<span>
								${cartData.contactPerson.name}
							</span>
							<span>|</span>
							<c:choose>  
								<c:when test="${not empty cartData.contactPerson.contactNumber}">
									<a class="tel-phone" href="tel:${cartData.contactPerson.contactNumber}">${cartData.contactPerson.contactNumber}</a>
								</c:when>
								<c:otherwise>
									<spring:theme code="orderSummaryPage.na" />
								</c:otherwise>
				  			</c:choose>
							<span>|</span>
							<span>${cartData.contactPerson.email}</span>
						</c:otherwise>
					</c:choose>

				</div>
				</div>
		</c:if>
</div>
</div> 
</div>
   <!-- <c:if test="${cartData.orderType eq 'DELIVERY'}"> 
    <div class="col-xs-12 icon-delivery-charges-order message-delivery-order">
		<spring:theme code="delivery.charges.notification" /><br>
	</div>

			     </c:if> --> 
			
        <%-- <multi-checkout:checkoutSteps checkoutSteps="${checkoutSteps}" progressBarId="${progressBarId}">
			<ycommerce:testId code="checkoutStepTwo"> --%>
			<div class="cl"></div>
<c:if test="${isMixedCartEnabled ne true}">
<multiCheckout:reviewOrderNonMixedCart/>
</c:if>
<c:if test="${isMixedCartEnabled eq true}">
<multiCheckout:reviewOrderMixedCart/>
</c:if>
</div>


</template:page>
