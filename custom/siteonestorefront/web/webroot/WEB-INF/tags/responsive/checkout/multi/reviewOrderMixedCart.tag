<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="multi-checkout"
	tagdir="/WEB-INF/tags/responsive/checkout/multi"%>
<%@ taglib prefix="multi-checkouts"
	tagdir="/WEB-INF/tags/responsive/checkout/multi"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<spring:url value="/checkout/multi/order-summary/placeOrder"
	var="placeOrderUrl" />
<spring:url value="/checkout/multi/order-summary/voucher/apply"
	var="applyVoucherAction" />
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<spring:url value="/checkout/multi/order-summary/voucher/remove"
	var="removeVoucherAction" />
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order"%>
<%@ taglib prefix="multiCheckout"
	tagdir="/WEB-INF/tags/responsive/checkout/multi"%>

<input type="hidden" id="currentCartId" name="currentCartId"
	value="${cartData.b2bCustomerData.currentCartId}">
<input type="hidden" id="recentCartIds" name="recentCartIds"
	value="${cartData.b2bCustomerData.recentCartIds}">
<input type="hidden" id="anonymousCartId" name="anonymousCartId"
	value="${cartData.code}">
<input type="hidden" id="orderType" name="orderType"
	value="${cartData.orderType}">
<c:set var="subtotalLimit" value="${cartData.deliveryEligibilityThreshold}"/>
<input type="hidden" class="subtotalLimit" value="${subtotalLimit}" />
<c:set var="homeOwnerCode"
	value="<%=de.hybris.platform.util.Config.getParameter(\"homeOwner.trade.class.code\")%>" />
<input type="hidden" class="homeOwnerCode" value="${homeOwnerCode}" />
<input type="hidden" class="trade-class"
	value="${cartData.orderingAccount.tradeClass}" />
<input type="hidden" class="subTotal-class"
	value="${cartData.subTotal.value}" />
	
	<c:set var="hasPickup" value="false" />
	<c:set var="hasDelivery" value="false" />
	<c:set var="hasShipping" value="false" />
	
 	<c:forEach items="${cartData.deliveryModeAndBranchOrderGroups}" var="groupData" varStatus="status">	
 	<c:if test="${groupData.deliveryMode.code eq 'pickup'}">	
 		<c:set var="hasPickup" value="true" />
 		<c:set var="pickupEntries" value="${groupData.entries}"/>
 	</c:if>
 	<c:if test="${groupData.deliveryMode.code eq 'standard-net'}">	
 		<c:set var="hasDelivery" value="true" />
 		<c:set var="deliveryEntries" value="${groupData.entries}"/>
 	</c:if>
 	<c:if test="${groupData.deliveryMode.code eq 'free-standard-shipping'}">	
 		<c:set var="hasShipping" value="true" />
 		<c:set var="shippingEntries" value="${groupData.entries}"/>
 	</c:if>
 	
 </c:forEach>
<div class="checkout-review">
	<c:if test="${hasPickup}">
	<multi-checkout:mixedCartPickupOrderReview />
	</c:if>
	<c:if test="${hasDelivery}">
	<multi-checkout:mixedCartDeliveryOrderReview />
	</c:if>
	<c:if test="${hasShipping}">
		<multi-checkout:mixedCartShippingOrderReview/>
	</c:if>

	<div class="row">
		<div class="col-xs-12">
			<multi-checkout:orderReviewPaymentinfo cartData="${cartData}"
				showDeliveryAddress="true" showTaxEstimate="true" showTax="true"
				fromPage="checkoutOrderSummary" />
		</div>
	</div>



	<div
		class="col-md-12 promoApply hidden-lg hidden-sm hidden-xs hidden-md">
		<div class="col-xs-12">
			<div class="title-bar">
				<div class="col-xs-12">
					<h2>
						<spring:theme code="orderSummaryPage.promotion.code" />
					</h2>
				</div>
			</div>
		</div>
		<div class="col-xs-12 promotion-code-wrapper">
			<c:set var="containerClass">
				<c:choose>
					<c:when test="${not empty errorMsg}">
						<spring:theme code="orderSummaryPage.error" />
					</c:when>
					<c:when test="${not empty successMsg}">
						<spring:theme code="orderSummaryPage.success" />
					</c:when>
					<c:otherwise></c:otherwise>
				</c:choose>
			</c:set>
			<c:forEach items="${cartData.appliedProductPromotions}"
				var="promotion">
				<c:set var="promotionApplied" value="true" />
			</c:forEach>
			<div class="form-group js-voucher-respond ${containerClass}">
				<spring:theme code="text.checkoutvoucher.apply.input.placeholder"
					var="voucherInputPlaceholder" />
				<label for="js-voucher-code-text"><spring:theme
						code="orderSummaryPage.have.prom.code" /></label> <input type="hidden"
					class="voucherDisplay" value="checkout" />
				<form:form id="applyVoucherForm_checkout" class="applyVoucherForm"
					action="${applyVoucherAction}" method="post"
					modelAttribute="voucherForm">

					<form:input
						cssClass="js-voucher-code cart-voucher__input form-control input-sm js-voucher-code-text"
						name="voucher-code" id="js-voucher-code-text" maxlength="100"
						placeholder="${voucherInputPlaceholder}" path="voucherCode"
						disabled="${disableUpdate}" />
					<c:if test="${not disableUpdate}">

						<button type="button" id="js-voucher-apply-btn"
							class="btn btn-default btn-small cart-voucher__btn js-voucher-apply-btn">
							<spring:theme code="text.checkoutvoucher.apply.button.label" />
						</button>
					</c:if>
				</form:form>
				<c:if test="${not empty errorMsg || not empty successMsg}">
					<div
						class="js-voucher-validation-container help-block cart-voucher__help-block">
						${errorMsg} ${successMsg}</div>
				</c:if>
			</div>
		</div>
	</div>
	<div class="cl"></div>

	<div
		class="checkout-couponcode hidden-lg hidden-sm hidden-xs hidden-md">

		<ul id="js-applied-vouchers"
			class="selected_product_ids clearfix voucher-list row">
			<c:forEach items="${cartData.appliedVouchers}" var="voucher"
				varStatus="loop">
				<c:set var="hideVoucher" value="hide" />
				<c:if test="${not empty showVoucherList}">
					<c:forEach items="${showVoucherList}" var="showVoucher">
						<c:if test="${voucher eq showVoucher}">
							<c:set var="hideVoucher" value="" />
						</c:if>
					</c:forEach>
				</c:if>
				<li class="voucher-list__item ${hideVoucher}"><form:form
						id="removeVoucherForm${loop.index}_${voucherDisplay}"
						action="${removeVoucherAction}" method="post"
						modelAttribute="voucherForm">
						<span class="js-release-voucher voucher-list__item-box"
							id="voucher-code-${voucher}"> ${voucher} <form:input
								hidden="hidden" id="voucherCode_${voucherDisplay}"
								value="${voucher}" path="voucherCode" /> <span
							class="glyphicon glyphicon-remove js-release-voucher-remove-btn voucher-list__item-remove"></span>
						</span>
					</form:form></li>

			</c:forEach>
		</ul>
	</div>
	<div class="col-md-12 order-summary-call padding0">
		<div class="col-md-8 hidden-xs hidden-sm paddingTopB10">
			<strong> <spring:theme code="checkout.multi.questions.order" /></strong>
			<spring:theme code="checkout.multi.questions.call" />
			<a href="mailto:customersupport@siteone.com"><spring:theme
					code="search.no.results.helpContactEmailId" /></a>
		</div>
		<div class="row col-sm-12 col-md-4 padding0 palceOrder-button-summary">
			<form:form action="${placeOrderUrl}" id="placeOrderForm1"
				modelAttribute="placeOrderForm">
				<button id="placeOrder" type="button"
					class="btn btn-primary btn-place-order btn-block button-payment-val">
					<spring:theme code="checkout.summary.placeOrder" text="Place Order" />
				</button>
			</form:form>
		</div>
		<div class="col-md-8 hidden-md hidden-lg button-payment-bottom">
			<strong> <spring:theme code="checkout.multi.questions.order" /></strong><br>
			<spring:theme code="checkout.multi.questions.call" />
			<a href="mailto:customersupport@siteone.com"><spring:theme
					code="search.no.results.helpContactEmailId" /></a>
		</div>
	</div>
	
</div>
