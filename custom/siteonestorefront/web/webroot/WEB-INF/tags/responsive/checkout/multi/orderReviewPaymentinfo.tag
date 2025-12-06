<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.CartData" %>
<%@ attribute name="showDeliveryAddress" required="true" type="java.lang.Boolean" %>
<%@ attribute name="showTax" required="false" type="java.lang.Boolean" %>
<%@ attribute name="showTaxEstimate" required="false" type="java.lang.Boolean" %>
<%@ attribute name="fromPage" required="false" type="java.lang.String" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="multi-checkout" tagdir="/WEB-INF/tags/responsive/checkout/multi" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>

<%-- <multi-checkout:orderTotals cartData="${cartData}" showTaxEstimate="${showTaxEstimate}" showTax="${showTax}" /> --%>
<c:choose>
	<c:when test="${fromPage eq 'checkoutOrderSummary'}">
	<div class="col-xs-12 padding0">
		<div class=" payment-title-detail">
			<div class="col-xs-9 order-summary-title bold padding0">
				<h1 class="order-confirmation-page-title order-review-title-wrapper"><spring:theme code="checkout.multi.paymentMethod.text.review.payment.Details" /></h1>
			</div>
			<div class="col-xs-3 text-right padding0 order-exit-top-payment">
				<a class="add-edit-color add-edit-decoration" href="<c:url value="/checkout/multi/siteOne-checkout"/>"><spring:theme code="orderSummaryPage.edit" /></a>
			</div>
		</div>
	</div>
		<div class="row margin20">
			<div class="col-xs-12 add-border-radius add-border-grey ${isMixedCartEnabled? 'mixedcart-payment' : ''}">
				<div class="col-md-8 padding0">

				<div class="col-xs-12 col-sm-12 col-md-12  padding0 pad-left-order-payment">
			<div class="col-xs-12 col-sm-12 col-md-6 billing-address-wrapper">
			<div class="secondary-title font-space-title">

			

				<b> <spring:theme code="checkout.multi.paymentMethod.addPaymentDetails.billingAddress"/> </b>
			</div>
			<c:choose>
			
			<c:when test="${guestUsers eq 'guest'}">
			      <div>${cartData.b2bCustomerData.firstName}&nbsp;${cartData.b2bCustomerData.lastName}</div>
                 <div>${cartData.guestContactPerson.contactNumber}</div>
                 <div>${cartData.guestContactPerson.email} </div>
                 <div>${cartData.guestContactPerson.defaultAddress.line1}</div>
                 <div>${cartData.guestContactPerson.defaultAddress.line2}</div>
                <div>${cartData.guestContactPerson.defaultAddress.town}, ${cartData.guestContactPerson.defaultAddress.region.isocodeShort} &nbsp; ${cartData.guestContactPerson.defaultAddress.postalCode}</div>

				</c:when>
				<c:otherwise>
				
                <div>${cartData.b2bCustomerData.firstName}&nbsp;${cartData.b2bCustomerData.lastName}</div>
				<div>${cartData.b2bCustomerData.contactNumber}</div>
				<div> ${cartData.contactPerson.email} </div>
				<div>${cartData.billingAddress.line1}</div>
				<div>${cartData.billingAddress.line2}</div>
				<div>${cartData.billingAddress.town}, ${cartData.billingAddress.region.isocodeShort} &nbsp; ${cartData.billingAddress.postalCode}</div>
			
				
					
				</c:otherwise>
				</c:choose>

		</div>
		<div class="col-xs-12 col-sm-12 col-md-6 padding0 order-rev-paymt-wrapper">
			<div class="secondary-title payment-method-orderConfirmation font-space-title">
						<b> <spring:theme code="checkout.confirmation.payment.method"/> </b>
			</div>

			<div>
			
<c:choose>

				<c:when test="${paymentInfo.paymentType eq '3'}">
					<div class="col-xs-12 pad-lft-10 ">

					<img src="" alt="" />

					<span>
						<span class="bold">${paymentInfo.applicationLabel}</span>
						<span class="bold">XXXX-${paymentInfo.cardNumber}</span>
					</span>
</div>

			</c:when>
			
				<c:when test="${paymentInfo.paymentType eq '2'}">
				
					<div class="col-xs-12">

						<div class="marginBottom10">
							<b><spring:theme code="orderUnconsignedEntries.payAtBranch" /></b>
						</div>
						<div class="pickup-location-details">
							<div class="bold">
								<b class="m-r-15"><spring:theme code="orderUnconsignedEntries.branch" /></b>${cartData.pointOfService.address.town}
								&nbsp;${cartData.pointOfService.storeId}
							</div>

						</div>

					</div>

				</c:when>
				<c:when test="${cartData.siteOnePOAPaymentInfoData.paymentType eq '1'}">

					<div class="col-xs-12">

						<div class="marginBottom10">
							<b><spring:theme code="orderUnconsignedEntries.payOnAccount" /></b>
						</div>
					</div>

				</c:when>
				<c:otherwise>

					<div class="col-xs-12 pad-lft-10">
					<img src="" alt="" />

					<span>
						<span class="bold">${paymentInfo.applicationLabel}</span>
						<span class="bold">XXXX-${paymentInfo.cardNumber}</span>
					</span>
</div>

				</c:otherwise>
</c:choose>
			</div>

		</div>
			</div>
				</div>
				<div class="col-md-4 col-xs-12 padding0 order-summary-wrapper pad-left-order-payment ${isMixedCartEnabled? 'mixedcart-order-payment' : ''}">
					<cart:cartTotals cartData="${cartData}" showTax="true"/>
				</div>
				<c:if test="${cartData.isNationalShipping eq true && !cartData.isShippingFeeBranch}">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div class="shipping-info-box">
					<common:shippingIcon iconColor="#78a22f" width="71" height="48" />
					<div class="shipping-info-box-text"><spring:theme code="orderconfirmation.msg11"/><span class="hidden-sm hidden-xs"><br></span><spring:theme code="orderconfirmation.msg12"/>
					</div>
					</div>
				</div>
				</c:if>
			</div> 
		</div> 
	</c:when>
	<c:otherwise>
		<div class="col-xs-12 col-md-7 col-lg-6 row">
		 	<div class="cart-totals">
		     <cart:cartTotals cartData="${cartData}" showTax="true"/>
		    <%--  <cart:ajaxCartTotals/> --%>
		    </div>
		</div>
	</c:otherwise>
</c:choose>
