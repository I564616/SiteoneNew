<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="order" required="true" type="com.siteone.facade.MasterHybrisOrderData" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="containerCSS" required="false" type="java.lang.String" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="showDeliveryFee" value="true"/>

	<c:if test="${empty order.deliveryFee}">
		<c:set var="showDeliveryFee" value="false"/>
	</c:if>	

 <div class="row no-margin   border-grey white-bg flex-when-print payment-border-order-confirmation payment-box-mixedcart">

<div class="col-xs-12 col-sm-12 col-md-4">
	 <div class="orderfulfillments-title  payment-secondary-title">
			<spring:theme code="checkout.multi.paymentMethod.addPaymentDetails.billingAddress"/> 
		</div>
	 
	 
				  		<div>${masterHybrisOrderData.pickupContact.name}</div>
						<div>${masterHybrisOrderData.pickupContact.contactNumber} | ${masterHybrisOrderData.pickupContact.email} </div>
						<div>${order.billingAddress.line1}</div>
						<div>${order.billingAddress.line2}</div>
						<div>${order.billingAddress.town}, ${order.billingAddress.region.isocodeShort} &nbsp; ${order.billingAddress.postalCode}</div>
	 
	 </div>
		<div class="col-xs-12 col-sm-12 col-md-4">
		<div class="orderfulfillments-title  payment-secondary-title">
			<spring:theme code="checkout.confirmation.payment.method"/> 
		</div>
			
			<c:choose>
					<c:when test="${order.siteOnePaymentInfoData.paymentType eq '3'}">
						<div>
							<img src="" alt="" />
							<span>
							<span class="bold">${order.siteOnePaymentInfoData.applicationLabel}</span>
							<span class="bold">XXXX-${order.siteOnePaymentInfoData.cardNumber}</span>
						</span>
						</div>
					</c:when>
					<c:when test="${order.siteOnePOAPaymentInfoData.paymentType eq '1'}">
						<div>
							<div class="marginBottom10">
								 <spring:theme code="orderUnconsignedEntries.payOnAccount" /> 
							</div>
						</div>
					</c:when>
					<c:when test="${order.siteOnePaymentInfoData.paymentType eq '2'}">
						<div>
							<div class="marginBottom10">
								 <spring:theme code="orderUnconsignedEntries.payAtBranch" /> 
							</div>
							 
						</div>
					</c:when>
					<c:otherwise>
						<div>
							<div class="marginBottom10">
								<b><spring:theme code="orderUnconsignedEntries.payAtBranch" /></b>
							</div>
							 
						</div>
					</c:otherwise>
				</c:choose>
	</div>	 
<div class="col-xs-12 col-sm-12 col-md-4">
<div class="orderfulfillments-title col-md-12  payment-secondary-title">
			<spring:theme code="order.order.totals"/> 
		</div>

<div class="orderTotal">
		<c:if test="${order.totalDiscountAmount.value > 0}">
			<div class="col-xs-6">
				<div class="subtotals__item--state-discount">
					<spring:theme code="text.account.order.discount"/>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="text-right subtotals__item--state-discount">
					<ycommerce:testId code="orderTotal_discount_label">
						-<format:price priceData="${order.totalDiscountAmount}" displayFreeForZero="false" />
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
						<span class="bold">$ <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${order.subtotal}"/>   </span>
					</ycommerce:testId>
				</div>
			</div>
			<c:if test="${not empty order.deliveryFee}">
			<div class="col-xs-6">
				<spring:theme code="text.account.order.deliveryfee"/>
			</div>
			<div class="col-xs-6">
				<div class="text-right">
					<ycommerce:testId code="orderTotal_subTotal_label">
						<span class="bold">$ ${order.deliveryFee}</span>
					</ycommerce:testId>
				</div>
			</div>
			</c:if>
			<c:if test="${not empty order.shippingFee}">
			<div class="col-xs-6">
				<spring:theme code="shipping.cost.text"/>
			</div>
			<div class="col-xs-6">
				<div class="text-right">
					<ycommerce:testId code="orderTotal_subTotal_label">
						<span class="bold">$  ${order.shippingFee}</span>
					</ycommerce:testId>
				</div>
			</div>
			</c:if>
			 

			 
				<div class="col-xs-6">
					<spring:theme code="text.account.order.netTax"/>
				</div>
				<div class="col-xs-6">
					<div class="text-right">
						<span class="bold">$ <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${order.tax}"/></span>
					</div>
				</div>
			 

			<div class="col-xs-6 cart-totals-left mb-padding grand-totall black-title"><spring:theme code="basket.page.totals.total"/></div>
			<div class="col-xs-6 grand-total text-right">
			<div class="col-md-12">
				<span class="bold">$  <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${order.total}"/></span>
				</div>
			</div>
		</div>
	 

</div>
 </div>
<div class="cl"></div>
</div>