<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="order" required="true" type="de.hybris.platform.commercefacades.order.data.AbstractOrderData" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="containerCSS" required="false" type="java.lang.String" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<c:set var="showDeliveryFee" value="true"/>
<fmt:setLocale value="en_US" scope="session"/>
<c:set var="unitpriceFormattedDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.unitprice.formattedDigits\")%>" />
<c:set var="unitpriceDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.unitprice.digits\")%>" />
<c:if test="${not empty order.guestContactPerson && (order.orderType eq 'DELIVERY' || order.orderType eq 'STORE_DELIVERY')}">
	<c:if test="${empty order.freight}">
		<c:set var="showDeliveryFee" value="false"/>
	</c:if>	
</c:if>

<c:set var="isSplitMixedPickupBranchOrder" value="false" />
<c:if test="${not empty isSplitMixedCartEnabledBranch && isSplitMixedCartEnabledBranch ne null}" >
	<c:set var="isSplitMixedPickupBranchCheckout" value="${isSplitMixedCartEnabledBranch}" />
</c:if>
<c:set var="shippingProductCount" value="${order.shippingOnlyEntries}" />
<c:set var="splitCartOrderShippingCount" value="${fn:length(shippingProductCount)}" />
<c:if test="${splitCartOrderShippingCount gt 0 }" >
	<c:set var="isSplitCartShippingHubOnly" value="true"/>
</c:if>
<c:if test="${isSplitMixedPickupBranchCheckout && isSplitCartShippingHubOnly && (order.orderType eq 'PICKUP' ||  order.orderType eq 'DELIVERY')}" >
	<c:set var="isSplitMixedPickupBranchOrder" value="true"/>
</c:if>
<input class="ordertype" type="hidden" value='${order.orderType}'>
<input class="isSplitMixedCartEnabledBranch" type="hidden" value='${isSplitMixedCartEnabledBranch}'>
<input class="isSplitMixedPickupBranchCheckout" type="hidden" value='${isSplitMixedPickupBranchCheckout}'>
<input class="isSplitMixedPickupBranchOrder" type="hidden" value='${isSplitMixedPickupBranchOrder}'>
<input class="isSplitCartShippingHubOnly" type="hidden" value='${isSplitCartShippingHubOnly}'>
<span class="hidden">
	${order.guestContactPerson} : order.guestContactPerson</br>
	${order.orderType} : order.orderType</br>
	${order.freight} : order.freight
</span>
<c:choose>
	<c:when test="${isMixedCartEnabled eq true}">
		<c:set var="paymentSecTitle" value="payment-secondary-title"/>
		<c:set var="paymentBox" value="payment-box-mixedcart"/>
	</c:when>
	<c:otherwise>
			<c:set var="paymentSecTitle" value="" />
			<c:set var="paymentBox" value=""/>
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${cmsPage.uid eq 'order-approval-details'}">
		<c:set var="hideSection" value="hidden" />
		<c:set var="billingSection" value="padding-30 order-approval-payment print-border-top-0 print-border-right-0 print-border-left-0 print-p-t-0 print-p-r-0 print-p-l-0"/>
		<c:set var="NoPadding" value="padding0" />
		<c:set var="approvalOrderPaymentSection" value="yes"/>
	</c:when>
	<c:otherwise>
		<c:set var="hideSection" value="" />
		<c:set var="billingSection" value="" />
		<c:set var="NoPadding" value="" />
		<c:set var="approvalOrderPaymentSection" value=""/>
	</c:otherwise>
</c:choose>
<div class="orderTotal p-a-0-imp ${hideSection}">
	<c:if test = "${cmsPage.uid ne 'order' and cmsPage.uid ne 'orderConfirmationPage'  and cmsPage.uid ne 'order-approval-details'}">
		<c:if test="${order.totalDiscounts.value > 0}">
			<div class="col-xs-6">
				<div class="subtotals__item--state-discount">
					<spring:theme code="text.account.order.discount"/>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="text-right subtotals__item--state-discount">
					<ycommerce:testId code="orderTotal_discount_label">
						-<format:price priceData="${order.totalDiscounts}" displayFreeForZero="false" />
					</ycommerce:testId>
				</div>
			</div>
		</c:if>
		<div class="row">
			<div class="col-xs-6">
				<spring:theme code="text.account.order.subtotal"/>
			</div>
			<div class="col-xs-6">
				<div class="text-right">
					<ycommerce:testId code="orderTotal_subTotal_label">
						<format:price priceData="${order.subTotal}" />
					</ycommerce:testId>
				</div>
			</div>
			<div class="col-xs-6">
				<spring:theme code="text.account.order.deliveryfee"/>
			</div>
			<div class="col-xs-6">
				<div class="text-right">
					<ycommerce:testId code="orderTotal_subTotal_label">
						<format:price priceData="${order.deliveryCost}" />
					</ycommerce:testId>
				</div>
			</div>
			<c:if test="${order.quoteDiscounts.value > 0}">
				<div class="col-xs-6 cart-totals-left discount">
					<spring:theme code="basket.page.quote.discounts" />
				</div>
				<div class="col-xs-6 cart-totals-right text-right discount">
					<ycommerce:testId code="Quote_Quote_Savings">
						<format:price priceData="${order.quoteDiscounts}" />
					</ycommerce:testId>
				</div>
			</c:if>
			<c:if test="${order.net}">
				<div class="col-xs-6">
					<spring:theme code="text.account.order.netTax"/>
				</div>
				<div class="col-xs-6">
					<div class="text-right">
						<format:price priceData="${order.totalTax}" />
					</div>
				</div>
			</c:if>

			<div class="col-xs-6 cart-totals-left mb-padding grand-total black-title"><spring:theme code="basket.page.totals.total"/></div>
			<div class="col-xs-6 grand-total">

				<c:choose>
					<c:when test="${order.net}">
						<div class="">
							<div class="text-right totals">
								<format:price priceData="${order.totalPriceWithTax}" />
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="text-right">
							<ycommerce:testId code="orderTotal_totalPrice_label">
								<format:price priceData="${order.totalPrice}" />
							</ycommerce:testId>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</c:if>
	<c:if test = "${cmsPage.uid eq 'order'}">
		<div class="row hide">
			<div class="col-md-7">
			</div>
			<div class="col-md-5 col-xs-12 order-totalsSummary">
				<c:if test="${order.totalDiscounts.value > 0}">
					<div class="col-xs-6 col-md-4 discount-title-wrapper">
						<div class="subtotals__item--state-discount totalText-title promotion-discount">
							<spring:theme code="text.account.order.discount"/>
						</div>
					</div>
					<div class="col-md-4 hidden-sm hidden-xs">
						&nbsp;
					</div>
					<div class="col-xs-6 col-md-4 grant-total_value">
						<div class="text-right subtotals__item--state-discount orderDetailsDiscount totalText-title">
							<ycommerce:testId code="orderTotal_discount_label">
								-$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.totalDiscounts.value}" minFractionDigits="2"  maxFractionDigits="2" />
							</ycommerce:testId>
						</div>
					</div>
				</c:if>

				<!-- Custom Attribute for order level discount and populated from order feed. OOB attribute is not used to avoid miscalculation -->
				<c:if test="${cmsPage.uid eq 'order'}">
					<c:if test="${not empty order.totalDiscountAmount && order.totalDiscountAmount.value > 0}">
						<div class="col-xs-6 col-md-4 discount-title-wrapper">
							<div class="subtotals__item--state-discount totalText-title promotion-discount">
								<spring:theme code="text.account.order.discount"/>
							</div>
						</div>
						<div class="col-md-4 hidden-sm hidden-xs">
							&nbsp;
						</div>
						<div class="col-xs-6 col-md-4 grant-total_value">
							<div class="text-right subtotals__item--state-discount orderDetailsDiscount totalText-title">
								<ycommerce:testId code="orderTotal_discount_label">
									-$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.totalDiscountAmount.value}" minFractionDigits="2"  maxFractionDigits="2" />
								</ycommerce:testId>
							</div>
						</div>
					</c:if>
				</c:if>

				<div class="col-xs-6 col-md-4 totalText-title">
					<spring:theme code="text.account.order.subtotal"/>
				</div>
				<div class="col-md-4 hidden-sm hidden-xs">
					&nbsp;
				</div>
				<div class="col-xs-6 col-md-4 grant-total_value">
					<div class="text-right">
						<ycommerce:testId code="orderTotal_subTotal_label">
							$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.subTotal.value}" minFractionDigits="2"  maxFractionDigits="2" />
						</ycommerce:testId>
					</div>
				</div>

				<c:if test="${cmsPage.uid eq 'order'}">
					<c:if test="${not empty order.freight}">
						<div class="col-xs-6 col-md-4 totalText-title">
							<spring:theme code="order.page.freight" />
						</div>
						<div class="col-md-4 hidden-sm hidden-xs">&nbsp;</div>
						<div class="col-xs-6 col-md-4 grant-total_value">
							<div class="text-right"><span class="black-title b-price">${order.freight}</span></div>
						</div>
					</c:if>
				</c:if>

				<c:if test="${order.quoteDiscounts.value > 0}">
					<div class="col-xs-6 col-md-4 cart-totals-left discount totalText-title">
						<spring:theme code="basket.page.quote.discounts" />
					</div>
					<div class="col-md-4 hidden-sm hidden-xs">
						&nbsp;
					</div>
					<div class="col-xs-6 col-md-4 cart-totals-right text-right discount grant-total_value">
						<ycommerce:testId code="Quote_Quote_Savings">
							$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.quoteDiscounts.value}" minFractionDigits="2"  maxFractionDigits="2" />
						</ycommerce:testId>
					</div>
				</c:if>
				<c:if test = "${cmsPage.uid eq 'orderConfirmationPage'}">
					<c:if test="${order.net}">
						<div class="col-xs-6 col-md-4 totalText-title">
							<spring:theme code="text.account.order.netTax"/>
						</div>
						<div class="col-md-4 hidden-sm hidden-xs">
							&nbsp;
						</div>
						<div class="col-xs-6 col-md-4 grant-total_value">
							<div class="text-right">
								$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.totalTax.value}" minFractionDigits="2"  maxFractionDigits="2" />
							</div>
						</div>
					</c:if>

					<div class="col-xs-6 col-md-4 cart-totals-left black-title totalText-title"><spring:theme code="basket.page.totals.total"/></div>
					<div class="col-md-4 hidden-sm hidden-xs">
						&nbsp;
					</div>
					<div class="col-xs-6 col-md-4 grand-total">
						<c:choose>
							<c:when test="${order.net}">
								<div class="text-right totals">
									$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.totalPriceWithTax.value}" minFractionDigits="2"  maxFractionDigits="2" />
								</div>
							</c:when>
							<c:otherwise>
								<div class="text-right">
									<ycommerce:testId code="orderTotal_totalPrice_label">
										$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.totalPrice.value}" minFractionDigits="2"  maxFractionDigits="2" />
									</ycommerce:testId>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</c:if>
				<c:if test = "${cmsPage.uid eq 'order'}">
					<div class="col-xs-6 col-md-4 totalText-title">
						<spring:theme code="text.account.order.netTax"/>
					</div>
					<div class="col-md-4 hidden-sm hidden-xs">
						&nbsp;
					</div>
					<div class="col-xs-6 col-md-4 grant-total_value">
						<div class="text-right">
							$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.totalTax.value}" minFractionDigits="2"  maxFractionDigits="2" />
						</div>
					</div>
					<div class="col-xs-6 col-md-4 cart-totals-left black-title totalText-title"><spring:theme code="basket.page.totals.total"/></div>
					<div class="col-md-4 hidden-sm hidden-xs">
						&nbsp;
					</div>
					<div class="col-xs-6 col-md-4 grand-total">
						<c:choose>
							<c:when test="${order.net}">
								<div class="text-right totals">
									$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.totalPriceWithTax.value}" minFractionDigits="2"  maxFractionDigits="2" />
								</div>
							</c:when>
							<c:otherwise>
								<div class="text-right">
									<ycommerce:testId code="orderTotal_totalPrice_label">
										$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.totalPrice.value}" minFractionDigits="2"  maxFractionDigits="2" />
									</ycommerce:testId>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</c:if>
			</div>
		</div>
	</c:if>


	<c:if test="${order.totalDiscountsWithQuoteDiscounts.value > 0}">
		<div class="account-orderdetail-orderTotalDiscount-section">
			<ycommerce:testId code="order_totalDiscount_label">
				<div class="order-savings__info text-right">
					<spring:theme code="text.account.order.totalSavings" argumentSeparator=";"
								  arguments="${order.totalDiscountsWithQuoteDiscounts.formattedValue}"/>
				</div>
			</ycommerce:testId>
		</div>
	</c:if>


</div>
<!-- Order confirmation Payment Details section -->
<c:if test = "${cmsPage.uid eq 'orderConfirmationPage' || cmsPage.uid eq 'order' || cmsPage.uid eq 'order-approval-details'}">
	<c:if test="${isMixedCartEnabled eq true}">
		<c:set var="hasPickup" value="false" />
		<c:set var="hasDelivery" value="false" />
		<c:set var="hasShipping" value="false" />
		<c:forEach items="${order.deliveryModeAndBranchOrderGroups}" var="groupData" varStatus="status">
			<c:if test="${groupData.deliveryMode.code eq 'pickup'}">	
				<c:set var="hasPickup" value="true" />
			</c:if>
			<c:if test="${groupData.deliveryMode.code eq 'standard-net'}">	
				<c:set var="hasDelivery" value="true" />
			</c:if>
			<c:if test="${groupData.deliveryMode.code eq 'free-standard-shipping'}">	
				<c:set var="hasShipping" value="true" />
			</c:if>
			<span class="hidden">
				${groupData.deliveryMode.code} : groupData.deliveryMode.code</br>
				${hasPickup} : hasPickup</br>
				${hasDelivery} : hasDelivery</br>
				${hasShipping} : hasShipping
			</span>
		</c:forEach>
 	</c:if>
	<span class="hidden">
		${order.deliveryModeAndBranchOrderGroups} : order.deliveryModeAndBranchOrderGroups</br>
		${cmsPage.uid} : cmsPage.uid</br>
		${isMixedCartEnabled} : isMixedCartEnabled
	</span>
 	<div class="cl"></div>
 	<div class="row no-margin ${billingSection} print-font-arial flex-when-print payment-border-order-confirmation ${paymentBox}">
	<div class="col-xs-12 col-sm-12 col-md-5 f-s-15 f-s-11-xs-px p-a-10-xs p-a-10-sm print-p-a-0 bg-light-dark-xs bg-light-dark-sm ${approvalOrderPaymentSection==''?'':'print-p-l-0'} addPaymentDetails-billingAddress-print">
		<!-- payment-method -->
		<div class="row no-margin-xs no-margin-sm">
			<div class="col-xs-5 p-l-0 f-w-700 text-uppercase-xs text-uppercase-sm print-text-darkgray ">
				<spring:theme code="text.account.paymentType"/><span class="hidden-md hidden-lg">:</span>
			</div>
			<c:choose>
				<c:when test="${order.siteOnePaymentInfoData.paymentType eq '3'}">
					<div class="col-xs-7 p-r-0 p-b-5-xs p-b-5-sm text-default ">
						1${order.siteOnePaymentInfoData.applicationLabel}
					</div>
					<div class="col-xs-5 p-l-0 f-w-700 text-uppercase-xs text-uppercase-sm print-text-darkgray ">
						<spring:theme code="orderUnconsignedEntries.cardNumber"/><span class="hidden-md hidden-lg">:</span>
					</div>
					<div class="col-xs-7 p-r-0 p-b-5-xs p-b-5-sm text-default">
						XXXX-${order.siteOnePaymentInfoData.cardNumber}
					</div>
				</c:when>
				<c:when test="${order.siteOnePOAPaymentInfoData.paymentType eq '1'}">
					<div class="col-xs-7 p-r-0 p-b-5-xs p-b-5-sm text-default ">
						<spring:theme code="orderUnconsignedEntries.payOnAccount" />
					</div>
				</c:when>
				<c:when test="${order.siteOnePaymentInfoData.paymentType eq '2'}">
					<div class="col-xs-7 p-r-0 p-b-5-xs p-b-5-sm text-default">
						<spring:theme code="orderUnconsignedEntries.payAtBranch" /> 
					</div>
					<c:if test="${isMixedCartEnabled eq false}">
						<div class="col-xs-5 p-l-0 f-w-700 text-uppercase-xs text-uppercase-sm print-text-darkgray">
							<spring:theme code="orderUnconsignedEntries.branch" /><span class="hidden-md hidden-lg">:</span>
						</div>
						<div class="col-xs-7 p-r-0 p-b-5-xs p-b-5-sm text-default">
							${order.pointOfService.address.town}&nbsp;${order.pointOfService.storeId}
						</div>
					</c:if>
				</c:when>
				<c:otherwise>
					<div class="col-xs-7 p-r-0 p-b-5-xs p-b-5-sm text-default">
						<spring:theme code="orderUnconsignedEntries.payAtBranch" />
					</div>
					<c:if test="${isMixedCartEnabled eq false}">
						<div class="col-xs-5 p-l-0 f-w-700 text-uppercase-xs text-uppercase-sm">
							<spring:theme code="orderUnconsignedEntries.branch" /><span class="hidden-md hidden-lg">:</span>
						</div>
						<div class="col-xs-7 p-r-0 p-b-5-xs p-b-5-sm text-default">
							${order.pointOfService.address.town}&nbsp;${order.pointOfService.storeId}
						</div>
					</c:if>
				</c:otherwise>
			</c:choose>
		</div>
		<!-- ./payment-method -->
		<!-- billingAddress -->
		<div class="row no-margin-xs no-margin-sm">
			<div class="col-xs-5 p-l-0 f-w-700 text-uppercase-xs text-uppercase-sm">
				<spring:theme code="checkout.multi.paymentMethod.addPaymentDetails.billingAddress"/><span class="hidden-md hidden-lg">:</span>
			</div>
			<div class="col-xs-7 p-r-0 p-b-5-xs p-b-5-sm text-default">
				<c:choose>
					<c:when test="${order.guestCustomer eq true }">
						<div>${order.b2bCustomerData.firstName}&nbsp;${order.b2bCustomerData.lastName}</div>
						<div>${order.guestContactPerson.contactNumber} | ${order.guestContactPerson.email} </div>
						<div>${order.guestContactPerson.defaultAddress.line1}</div>
						<div>${order.guestContactPerson.defaultAddress.line2}</div>
						<div>${order.guestContactPerson.defaultAddress.town}, ${order.guestContactPerson.defaultAddress.region.isocodeShort} &nbsp; ${order.guestContactPerson.defaultAddress.postalCode}</div>
					</c:when>
					<c:otherwise>
						<div>${order.b2bCustomerData.firstName}&nbsp;${order.b2bCustomerData.lastName}</div>
						<div>${order.b2bCustomerData.contactNumber} | ${order.contactPerson.email} </div>
						<div>${order.billingAddress.line1}</div>
						<div>${order.billingAddress.line2}</div>
						<div>${order.billingAddress.town}, ${order.billingAddress.region.isocodeShort} &nbsp; ${order.billingAddress.postalCode}</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<!-- ./billingAddress -->
	</div>
	<div class="col-xs-12 m-y-20 m-b-0-xs m-b-0-sm font-Geogrotesque text-default f-s-20 f-s-16-xs-px f-s-16-sm-px padding0 payment-detailHeader-print hidden-md hidden-lg">
		<spring:theme code="orderconfirmation.checkout.multi.paymentMethod.text.paymentdetails" />
	</div>
	<!-- order total footer -->
	<div class="col-xs-12 col-sm-12 col-md-5 col-md-offset-2 padding0 f-s-15 f-s-11-xs-px p-a-10-xs p-a-10-sm bg-light-dark-xs bg-light-dark-sm m-b-25-xs m-b-25-sm order-summary-print print-hidden">
			<div class="row no-margin-xs no-margin-sm subtotalclass">
				<div class="col-xs-5 f-w-700 text-uppercase-xs text-uppercase-sm mb-padding print-f-s-15 print-text-darkgray"><spring:theme code="order.form.subtotal"/><span class="hidden-md hidden-lg">:</span></div>
				<div class="col-xs-7 text-default p-b-5-xs p-b-5-sm">
					<ycommerce:testId code="orderTotal_subTotal_label">
					 <span class="black-title b-price add_price">
					 	<fmt:setLocale value="en_US" scope="session"/>
						$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.subTotal.value}" minFractionDigits="2"  maxFractionDigits="2" />
					 </span>
					</ycommerce:testId>
				</div>
			</div>
			<span class="hidden">
				${cmsPage.uid} : cmsPage.uid</br>
				${orderData.orderType} : orderData.orderType</br>
				${hasDelivery} : hasDelivery</br>
				${order.freight} : order.freight</br>
				${order.orderType} : order.orderType</br>
				${showDeliveryFee} : showDeliveryFee</br>
				${order.orderingAccount.exemptDeliveryFee} : order.orderingAccount.exemptDeliveryFee
			</span>
			<c:if test="${cmsPage.uid eq 'orderConfirmationPage' and ((orderData.orderType eq 'DELIVERY') or hasDelivery) and (empty order.freight)}">
				<c:set var="showDeliveryFee" value="false"/>
			</c:if>
			<c:if test="${((order.orderType eq 'DELIVERY' || order.orderType eq 'STORE_DELIVERY') || hasDelivery) && ((showDeliveryFee && (not empty order.freight)) or (order.orderingAccount.exemptDeliveryFee))}">
			<div class="row no-margin-xs no-margin-sm deliveryfeeclass">
				<div class="col-xs-5 f-w-700 text-uppercase-xs text-uppercase-sm mb-padding print-hidden ${NoPadding}">
						<spring:theme code="order.form.deliveryfee"/><span class="hidden-md hidden-lg">:</span>
				</div>
				<div class="col-xs-7 text-default p-b-5-xs p-b-5-sm black-title print-hidden">
					<ycommerce:testId code="orderTotal_subTotal_label">
						<c:if test="${cmsPage.uid ne 'order' && (order.orderType eq 'DELIVERY' || order.orderType eq 'STORE_DELIVERY')}">
						<span class="black-title b-price add_price" data-deliveryCost="${order.deliveryCost}">
						<c:choose>
							<c:when test="${not empty order.deliveryCost && order.deliveryCost.value ne '0.0'}">
								$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.deliveryCost.value}" minFractionDigits="2"  maxFractionDigits="2" />							 
							</c:when>
							<c:otherwise>
								$0.00
							</c:otherwise>
						</c:choose>
						</span>
						</c:if>
						<c:if test="${hasDelivery || cmsPage.uid eq 'order'}">
						<input type="hidden" class="order-deliveryFreight" value="${order.deliveryFreight}"/>
						<c:choose>
							<c:when test="${not empty order.deliveryFreight && order.deliveryFreight ne '0.0'}">
								<span class="black-title b-price add_price" data-deliveryFreight="${order.deliveryFreight}">
								$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.deliveryFreight}" minFractionDigits="2"  maxFractionDigits="2" />
								</span>
							</c:when>
							<c:otherwise>
								$0.00
							</c:otherwise>
						</c:choose>
						</c:if>
					</ycommerce:testId>
				</div>
			</div>
			</c:if>
			<span class="hidden">
				${order.isNationalShipping} : order.isNationalShipping</br>
				${cmsPage.uid} : cmsPage.uid</br>
				${order.shippingFreight} : order.shippingFreight</br>
				${order.orderType} : order.orderType</br>
				${hasShipping} : hasShipping
			</span>
			<c:if test="${(order.isNationalShipping eq false && cmsPage.uid eq 'orderConfirmationPage') || (cmsPage.uid ne 'order-approval-details' || cmsPage.uid ne 'order')}">
			<c:if test="${order.orderType eq 'SHIPPING' || order.orderType eq 'PARCEL_SHIPPING' || hasShipping || isSplitMixedPickupBranchOrder}">
				<div class="row no-margin-xs no-margin-sm parcelshippingfeeclass">
					<div class="col-xs-5 f-w-700 text-uppercase-xs text-uppercase-sm mb-padding ${NoPadding}">
						<spring:theme code="order.form.parcelshippingfee"/><span class="hidden-md hidden-lg">:</span>
					</div>
					<div class="col-xs-7 text-default p-b-5-xs p-b-5-sm">
						<ycommerce:testId code="orderTotal_subTotal_label">
						<c:if test="${cmsPage.uid ne 'order' && (order.orderType eq 'SHIPPING' || order.orderType eq 'PARCEL_SHIPPING')}">
							<c:choose>
								<c:when test="${not empty order.deliveryCost && order.deliveryCost.value ne '0.0'}">
								 <span class="black-title b-price add_price" data-deliveryCost="${order.deliveryCost}">
									 $<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.deliveryCost.value}" minFractionDigits="2"  maxFractionDigits="2" />
								 </span>
								</c:when>
								<c:otherwise>
									 <spring:theme code="text.shipping.free"/>
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${hasShipping || cmsPage.uid eq 'order' || isSplitMixedPickupBranchOrder}">
							<input type="hidden" class="order-shippingFreight" value="${order.shippingFreight}"/>
							<c:choose>
								<c:when test="${not empty order.shippingFreight && order.shippingFreight ne '0.00'}">
								 <span class="black-title b-price add_price" data-shippingFreight="${order.shippingFreight}">
									 $<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.shippingFreight}" minFractionDigits="2"  maxFractionDigits="2" />
								 </span>
								</c:when>
								<c:otherwise>
									 <spring:theme code="text.shipping.free"/>
								</c:otherwise>
							</c:choose>
						</c:if>
						</ycommerce:testId>
					</div>
				</div>
			</c:if>
			</c:if>
			<div class="row no-margin-xs no-margin-sm hide">
				<div class="col-xs-5 f-w-700 text-uppercase-xs text-uppercase-sm mb-padding"><spring:theme code="basket.page.shipping"/><span class="hidden-md hidden-lg">:</span></div>
				<div class="col-xs-7 text-default p-b-5-xs p-b-5-sm confirmation-tax-value"></div>
			</div>
			<div class="row no-margin-xs no-margin-sm totaltaxclass ${NoPadding} print-m-b-0">
				<div class="col-xs-5 f-w-700 text-uppercase-xs text-uppercase-sm mb-padding print-text-darkgray ${NoPadding}">
					<spring:theme code="order.form.Sales" />
					<span class="hidden-md hidden-lg">:</span>
				</div>
				<div class="col-xs-7 text-default p-b-5-xs p-b-5-sm confirmation-tax-value">
					$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.totalTax.value}" minFractionDigits="2"  maxFractionDigits="2" />
				</div>
			</div>
			<c:if test="${not empty order.totalDiscounts && order.totalDiscounts.value ne '0.0'}">
			<div class="row no-margin-xs no-margin-sm promotionappliedclass ${NoPadding} print-m-b-0">
				<div class="col-xs-5 f-w-700 text-uppercase-xs text-uppercase-sm confirmation-promo-title  mb-padding  ${NoPadding}"><spring:theme code="basket.validation.couponNotValid.promotion"/><span class="hidden-md hidden-lg">:</span></div>
				<div class="col-xs-7 text-default p-b-5-xs p-b-5-sm confirmation-promo-value">
					<c:choose>
						<c:when test="${not empty order.totalDiscounts && order.totalDiscounts.value ne '0.00'}">
							<ycommerce:testId code="orderTotal_discount_label  mb-padding">
								-$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.totalDiscounts.value}" minFractionDigits="2"  maxFractionDigits="2" />
							</ycommerce:testId>
						</c:when>
						<c:otherwise>
							$0.00
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			</c:if>
			<div class="row no-margin-xs no-margin-sm finalordertotalclass ${NoPadding}">
				<div class="col-xs-5 f-w-700 text-uppercase-xs text-uppercase-sm mb-padding print-text-darkgray ${NoPadding}"><spring:theme code="text.account.orderHistory.page.sort.byAmount"/><span class="hidden-md hidden-lg">:</span></div>
				<div class="col-xs-7 text-default p-b-5-xs p-b-5-sm confirmation-total-value bold">
					<ycommerce:testId code="orderTotal_totalPrice_label">
						$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.totalPriceWithTax.value}" minFractionDigits="2"  maxFractionDigits="2" />
					</ycommerce:testId>
				</div>
			</div>
		<input type="hidden" class="confirmation-showDeliveryFeeMessage" value="${orderData.showDeliveryFeeMessage}"/>
		<input type="hidden" class="confirmation-isfreight" value="${empty order.freight}"/>
        <input type="hidden" class="confirmation-freight" value="${order.freight}"/>
        <input type="hidden" class="confirmation-isMixedCartEnabled" value="${isMixedCartEnabled}"/>
        <input type="hidden" class="confirmation-isInDeliveryFeePilot" value="${orderData.isInDeliveryFeePilot}"/>
        <input type="hidden" class="confirmation-exemptDeliveryFee" value="${orderData.orderingAccount.exemptDeliveryFee}"/>
		<input type="hidden" class="confirmation-o-exemptDeliveryFee" value="${order.orderingAccount.exemptDeliveryFee}"/>

			<c:if test="${cmsPage.uid eq 'orderConfirmationPage' and ((((orderData.orderType eq 'DELIVERY') or hasDelivery) and ((empty order.freight) or currentBaseStoreId eq 'siteoneCA')) and (!order.orderingAccount.exemptDeliveryFee or currentBaseStoreId eq 'siteoneCA'))}">
		       	<div class="cl"></div>
		       		<div class="col-md-12 no-padding-lft-xs">
		       		<div class="delivery-fee-msg marginTop20">
				       	<div class="col-md-12">
					       	<div class="delivery-fee-box">
						       	<div class="bold"><spring:theme code="deliveryFeeTitle" /></div>
						       	<p>	<spring:theme code="deliveryFeeMSg" /></p>
					       	</div>
				       	</div>
		       	</div>
		       	</div>
	       	</c:if>
			
		</div>
		<div class="col-xs-12 col-sm-12 col-md-5 padding0 f-s-15 f-s-11-xs-px p-a-10-xs p-a-10-sm bg-light-dark-xs bg-light-dark-sm m-b-25-xs print-p-a-0 hidden print-visible">
			<div class="row no-margin-xs no-margin-sm subtotalclass">
				<div class="col-xs-5 f-w-700 text-uppercase-xs text-uppercase-sm mb-padding print-f-s-15 print-text-darkgray"><spring:theme code="order.form.subtotal"/><span class="hidden-md hidden-lg">:</span></div>
				<div class="col-xs-7 text-default p-b-5-xs p-b-5-sm">
					<ycommerce:testId code="orderTotal_subTotal_label">
					 <span class="black-title b-price add_price">
					 	<fmt:setLocale value="en_US" scope="session"/>
						$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.subTotal.value}" minFractionDigits="2"  maxFractionDigits="2" />
					 </span>
					</ycommerce:testId>
				</div>
			</div>
			<span class="hidden">
				${cmsPage.uid} : cmsPage.uid</br>
				${orderData.orderType} : orderData.orderType</br>
				${hasDelivery} : hasDelivery</br>
				${order.freight} : order.freight</br>
				${order.orderType} : order.orderType</br>
				${showDeliveryFee} : showDeliveryFee</br>
				${order.orderingAccount.exemptDeliveryFee} : order.orderingAccount.exemptDeliveryFee
			</span>
			<c:if test="${cmsPage.uid eq 'orderConfirmationPage' and ((orderData.orderType eq 'DELIVERY') or hasDelivery) and (empty order.freight)}">
				<c:set var="showDeliveryFee" value="false"/>
			</c:if>
			<c:if test="${((order.orderType eq 'DELIVERY' || order.orderType eq 'STORE_DELIVERY') || hasDelivery) && ((showDeliveryFee && (not empty order.freight)) or (order.orderingAccount.exemptDeliveryFee))}">
			<div class="row no-margin-xs no-margin-sm deliveryfeeclass">
				<div class="col-xs-5 f-w-700 text-uppercase-xs text-uppercase-sm mb-padding print-hidden ${NoPadding}">
						<spring:theme code="order.form.deliveryfee"/><span class="hidden-md hidden-lg">:</span>
				</div>
				<div class="col-xs-7 text-default p-b-5-xs p-b-5-sm black-title print-hidden">
					<ycommerce:testId code="orderTotal_subTotal_label">
						<c:if test="${cmsPage.uid ne 'order' && (order.orderType eq 'DELIVERY' || order.orderType eq 'STORE_DELIVERY')}">
						<span class="black-title b-price add_price" data-deliveryCost="${order.deliveryCost}">
						<c:choose>
							<c:when test="${not empty order.deliveryCost && order.deliveryCost.value ne '0.0'}">
								$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.deliveryCost.value}" minFractionDigits="2"  maxFractionDigits="2" />							 
							</c:when>
							<c:otherwise>
								$0.00
							</c:otherwise>
						</c:choose>
						</span>
						</c:if>
						<c:if test="${hasDelivery || cmsPage.uid eq 'order'}">
						<input type="hidden" class="order-deliveryFreight" value="${order.deliveryFreight}"/>
						<c:choose>
							<c:when test="${not empty order.deliveryFreight && order.deliveryFreight ne '0.0'}">
								<span class="black-title b-price add_price" data-deliveryFreight="${order.deliveryFreight}">
								$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.deliveryFreight}" minFractionDigits="2"  maxFractionDigits="2" />
								</span>
							</c:when>
							<c:otherwise>
								$0.00
							</c:otherwise>
						</c:choose>
						</c:if>
					</ycommerce:testId>
				</div>
			</div>
			</c:if>
			<span class="hidden">
				${order.isNationalShipping} : order.isNationalShipping</br>
				${cmsPage.uid} : cmsPage.uid</br>
				${order.shippingFreight} : order.shippingFreight</br>
				${order.orderType} : order.orderType</br>
				${hasShipping} : hasShipping
			</span>
			<c:if test="${(order.isNationalShipping eq false && cmsPage.uid eq 'orderConfirmationPage') || (cmsPage.uid ne 'order-approval-details' || cmsPage.uid ne 'order')}">
			<c:if test="${order.orderType eq 'SHIPPING' || order.orderType eq 'PARCEL_SHIPPING' || hasShipping || isSplitMixedPickupBranchOrder}">
				<div class="row no-margin-xs no-margin-sm parcelshippingfeeclass">
					<div class="col-xs-5 f-w-700 text-uppercase-xs text-uppercase-sm mb-padding ${NoPadding}">
						<spring:theme code="order.form.parcelshippingfee"/><span class="hidden-md hidden-lg">:</span>
					</div>
					<div class="col-xs-7 text-default p-b-5-xs p-b-5-sm">
						<ycommerce:testId code="orderTotal_subTotal_label">
						<c:if test="${cmsPage.uid ne 'order' && (order.orderType eq 'SHIPPING' || order.orderType eq 'PARCEL_SHIPPING')}">
							<c:choose>
								<c:when test="${not empty order.deliveryCost && order.deliveryCost.value ne '0.0'}">
								 <span class="black-title b-price add_price" data-deliveryCost="${order.deliveryCost}">
									 $<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.deliveryCost.value}" minFractionDigits="2"  maxFractionDigits="2" />
								 </span>
								</c:when>
								<c:otherwise>
									 <spring:theme code="text.shipping.free"/>
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${hasShipping || cmsPage.uid eq 'order' || isSplitMixedPickupBranchOrder}">
							<input type="hidden" class="order-shippingFreight" value="${order.shippingFreight}"/>
							<c:choose>
								<c:when test="${not empty order.shippingFreight && order.shippingFreight ne '0.00'}">
								 <span class="black-title b-price add_price" data-shippingFreight="${order.shippingFreight}">
									 $<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.shippingFreight}" minFractionDigits="2"  maxFractionDigits="2" />
								 </span>
								</c:when>
								<c:otherwise>
									 <spring:theme code="text.shipping.free"/>
								</c:otherwise>
							</c:choose>
						</c:if>
						</ycommerce:testId>
					</div>
				</div>
			</c:if>
			</c:if>
			<div class="row no-margin-xs no-margin-sm hide">
				<div class="col-xs-5 f-w-700 text-uppercase-xs text-uppercase-sm mb-padding"><spring:theme code="basket.page.shipping"/><span class="hidden-md hidden-lg">:</span></div>
				<div class="col-xs-7 text-default p-b-5-xs p-b-5-sm confirmation-tax-value"></div>
			</div>
			<div class="row no-margin-xs no-margin-sm totaltaxclass ${NoPadding} print-m-b-0">
				<div class="col-xs-5 f-w-700 text-uppercase-xs text-uppercase-sm mb-padding print-text-darkgray ${NoPadding}">
					<spring:theme code="order.form.Sales" />
					<span class="hidden-md hidden-lg">:</span>
				</div>
				<div class="col-xs-7 text-default p-b-5-xs p-b-5-sm confirmation-tax-value">
					$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.totalTax.value}" minFractionDigits="2"  maxFractionDigits="2" />
				</div>
			</div>
			<c:if test="${not empty order.totalDiscounts && order.totalDiscounts.value ne '0.0'}">
			<div class="row no-margin-xs no-margin-sm promotionappliedclass ${NoPadding} print-m-b-0">
				<div class="col-xs-5 f-w-700 text-uppercase-xs text-uppercase-sm confirmation-promo-title  mb-padding  ${NoPadding}"><spring:theme code="basket.validation.couponNotValid.promotion"/><span class="hidden-md hidden-lg">:</span></div>
				<div class="col-xs-7 text-default p-b-5-xs p-b-5-sm confirmation-promo-value">
					<c:choose>
						<c:when test="${not empty order.totalDiscounts && order.totalDiscounts.value ne '0.00'}">
							<ycommerce:testId code="orderTotal_discount_label  mb-padding">
								-$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.totalDiscounts.value}" minFractionDigits="2"  maxFractionDigits="2" />
							</ycommerce:testId>
						</c:when>
						<c:otherwise>
							$0.00
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			</c:if>
			<div class="row no-margin-xs no-margin-sm finalordertotalclass ${NoPadding}">
				<div class="col-xs-5 f-w-700 text-uppercase-xs text-uppercase-sm mb-padding print-text-darkgray ${NoPadding}"><spring:theme code="text.account.orderHistory.page.sort.byAmount"/><span class="hidden-md hidden-lg">:</span></div>
				<div class="col-xs-7 text-default p-b-5-xs p-b-5-sm confirmation-total-value bold">
					<ycommerce:testId code="orderTotal_totalPrice_label">
						$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.totalPriceWithTax.value}" minFractionDigits="2"  maxFractionDigits="2" />
					</ycommerce:testId>
				</div>
			</div>
		<input type="hidden" class="confirmation-showDeliveryFeeMessage" value="${orderData.showDeliveryFeeMessage}"/>
		<input type="hidden" class="confirmation-isfreight" value="${empty order.freight}"/>
        <input type="hidden" class="confirmation-freight" value="${order.freight}"/>
        <input type="hidden" class="confirmation-isMixedCartEnabled" value="${isMixedCartEnabled}"/>
        <input type="hidden" class="confirmation-isInDeliveryFeePilot" value="${orderData.isInDeliveryFeePilot}"/>
        <input type="hidden" class="confirmation-exemptDeliveryFee" value="${orderData.orderingAccount.exemptDeliveryFee}"/>
		<input type="hidden" class="confirmation-o-exemptDeliveryFee" value="${order.orderingAccount.exemptDeliveryFee}"/>

			<c:if test="${cmsPage.uid eq 'orderConfirmationPage' and ((((orderData.orderType eq 'DELIVERY') or hasDelivery) and ((empty order.freight) or currentBaseStoreId eq 'siteoneCA')) and (!order.orderingAccount.exemptDeliveryFee or currentBaseStoreId eq 'siteoneCA'))}">
		       	<div class="cl"></div>
		       		<div class="col-md-12 no-padding-lft-xs">
		       		<div class="delivery-fee-msg marginTop20">
				       	<div class="col-md-12">
					       	<div class="delivery-fee-box">
						       	<div class="bold"><spring:theme code="deliveryFeeTitle" /></div>
						       	<p>	<spring:theme code="deliveryFeeMSg" /></p>
					       	</div>
				       	</div>
		       	</div>
		       	</div>
	       	</c:if>
			
		</div>
		<c:if test="${order.isNationalShipping eq true and cmsPage.uid eq 'orderConfirmationPage' and order.isShippingFeeBranch eq false}">
		<div class=" text-center col-md-12 col-sm-12 col-xs-12">
			<div class="shipping-info-box">
			<common:shippingIcon iconColor="#78a22f" width="71" height="48" />
			<div class="shipping-info-box-text"><spring:theme code="orderconfirmation.msg11"/><span class="hidden-sm hidden-xs"><br></span><spring:theme code="orderconfirmation.msg12"/>
			</div>
			</div>
		</div>
		</c:if>
		<!-- ./order total footer -->
	</div>
</c:if>
<c:if test = "${cmsPage.uid eq 'orderConfirmationPage' || cmsPage.uid eq 'order-approval-details'}">
	<br>
	<div class="col-xs-12 marginBottom20 ${approvalOrderPaymentSection==''?'':'print-m-a-0 '} questions-order ${NoPadding}">
		<div class="col-md-8 margintop20 hidden-xs hidden-sm padding0 xs-center"><strong> <spring:theme code="checkout.multi.questions.order"/></strong><br> <spring:theme code="checkout.multi.questions.call"/><a href="mailto:customersupport@siteone.com"><spring:theme code="search.no.results.helpContactEmailId" /></a></div>
		<div class="${approvalOrderPaymentSection==''?'':'print-hidden'}"><order:buttonsInConfirmationPage/></div>
		<div class="col-md-8 marginTop35 hidden-lg hidden-md ${approvalOrderPaymentSection==''?'':'print-m-t-5 print-p-l-0 print-f-s-10 print-text-left'} confirmation-deleviry-link xs-center"><strong> <spring:theme code="checkout.multi.questions.order"/></strong><br> <spring:theme code="checkout.multi.questions.call"/><a href="mailto:customersupport@siteone.com" class="${approvalOrderPaymentSection==''?'':'print-text-blue'}"><spring:theme code="search.no.results.helpContactEmailId" /></a></div>
		<br>

	</div>
</c:if>

<c:if test="${cmsPage.uid eq 'orderConfirmationPage' and (orderData.orderType eq 'DELIVERY' or orderData.orderType eq 'PICKUP')}">

	<div class="thankyou-message-delivery-reg bold margintop40 text-default">
		<div class="thankyou-message hidden"><spring:theme code="checkout.multi.paymentMethod.text.paymentdetails.order" /></div>
		<div class="margin20 thankyou-message hidden"><spring:theme code="checkout.multi.paymentdetails.order.confirmation" /></div>
		<c:if test="${orderData.orderType eq 'DELIVERY' and not empty orderData.deliveryCost}">
		<div class="thankyou-message">
			<spring:theme code="text.account.order.regulatedItems.disclaimerTextAdditionalDelivery"/>
		</div>
		</c:if>
		<div class="thankyou-message p-t-10"><spring:theme code="checkout.multi.paymentdetails.order.confirmation.description" /></div>
	</div>
</c:if>