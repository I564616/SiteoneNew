<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.CartData"%>
<%@ attribute name="groupData" required="true" type="de.hybris.platform.commercefacades.order.data.OrderEntryGroupData"%>
<%@ attribute name="index" required="true" type="java.lang.Integer"%>
<%@ attribute name="showPotentialPromotions" required="false" type="java.lang.Boolean"%>
<%@ attribute name="showHead" required="false" type="java.lang.Boolean"%>
<%@ attribute name="shippingFound" type="java.lang.Boolean"%>
<%@ attribute name="fromPage" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="multi-checkout" tagdir="/WEB-INF/tags/responsive/checkout/multi"%>

<c:set var="hasPickup" value="false" />
<c:set var="hasDelivery" value="false" />
<c:set var="hasShipping" value="false" />
<c:if test="${groupData.deliveryMode.code eq 'pickup'}">
	<c:set var="hasPickup" value="true" />
</c:if>
<c:if test="${groupData.deliveryMode.code eq 'standard-net'}">
	<c:set var="hasDelivery" value="true" />
</c:if>
<c:if test="${groupData.deliveryMode.code eq 'free-standard-shipping'}">
	<c:set var="hasShipping" value="true" />
</c:if>
<!-- Checking Modes -->
<c:if test="${hasPickup}">
	<div class="mixedcart-icon-header">
		<common:pickUpIcon height="18" width="30" iconColor="#000" />
		<p class="bold black-title">
			<spring:theme code="mixcart.order.pickup" />
		</p>
	</div>
	<!-- store name details -->
	<div class="row paddingTopB10">
		<c:url value="/store/${fn:escapeXml(pointOfService.storeId)}" var="checkoutstore" />
		<div class="col-md-7 col-xs-6">
			<a href="${checkoutstore}">${groupData.pointOfService.name}</a>
		</div>
		<div class="col-md-5 col-xs-6 text-right padding-LeftZero">
			<a class="tel-phone" href="tel:${groupData.pointOfService.address.phone}">${groupData.pointOfService.address.phone}</a>
		</div>
	</div>
</c:if>
<c:if test="${hasDelivery}">
	<div class="mixedcart-icon-header">
		<common:deliveryIcon height="18" width="30" iconColor="#000" />
		<p class="bold black-title">
			<spring:theme code="mixcart.order.delivery" />
		</p>
	</div>
	<!-- store name details -->
	<div class="row paddingTopB10">
		<c:url value="/store/${fn:escapeXml(pointOfService.storeId)}" var="checkoutstore" />
		<div class="col-md-7 col-xs-6">
			<a href="${checkoutstore}">${groupData.pointOfService.name}</a>
		</div>
		<div class="col-md-5 col-xs-6 text-right padding-LeftZero">
			<a class="tel-phone" href="tel:${groupData.pointOfService.address.phone}">${groupData.pointOfService.address.phone}</a>
		</div>
	</div>
</c:if>
<c:if test="${hasShipping && !shippingFound}">
	<div class="mixedcart-icon-header">
		<common:parcelIcon height="18" width="30" iconColor="#000" />
		<p class="bold black-title">
			<spring:theme code="mixcart.order.shipping" />
		</p>
	</div>
</c:if>

<c:forEach items="${groupData.entries}" var="entry">
	<c:if test="${hasPickup}">
		<!-- product details -->
		<div class="checkout-product-sec">
			<div class="product-thumb col-xs-3 padding0">
				<product:productPrimaryImage product="${entry.product}" format="product" />
			</div>
			<div class="col-xs-9 padding-rightZero">
				<p class="black-title bold-text">${fn:escapeXml(entry.product.name)}</p>
				<div class="row">
					<div class="col-xs-5 padding-rightZero">
						<span><spring:theme code="product.product.details.future.qty" />: ${entry.quantity}</span>
					</div>
					<div class="col-xs-7 text-right">
						<span class="b-price add_price">
							<format:price priceData="${entry.basePrice}" displayFreeForZero="true" />
						</span>
					</div>
				</div>
			</div>
			<div class="cl"></div>
		</div>
	</c:if>
	<c:if test="${hasDelivery}">
		<!-- product details -->
		<div class="checkout-product-sec">
			<div class="product-thumb col-xs-3 padding0">
				<product:productPrimaryImage product="${entry.product}" format="product" />
			</div>
			<div class="col-xs-9 padding-rightZero">
				<p class="black-title bold-text">${fn:escapeXml(entry.product.name)}</p>
				<div class="row">
					<div class="col-xs-5 padding-rightZero">
						<span><spring:theme code="product.product.details.future.qty" />: ${entry.quantity}</span>
					</div>
					<div class="col-xs-7 text-right">
						<format:price priceData="${entry.basePrice}" displayFreeForZero="true" />
					</div>
				</div>
			</div>
			<div class="cl"></div>
		</div>
	</c:if>
	<c:if test="${hasShipping}">
		<!-- product details -->
		<div class="checkout-product-sec">
			<div class="product-thumb col-xs-3 padding0">
				<product:productPrimaryImage product="${entry.product}" format="product" />
			</div>
			<div class="col-xs-9 padding-rightZero">
				<p class="black-title bold-text">${fn:escapeXml(entry.product.name)}</p>
				<div class="row">
					<div class="col-xs-5 padding-rightZero">
						<span><spring:theme code="product.product.details.future.qty" />: ${entry.quantity}</span>
					</div>
					<div class="col-xs-7 text-right">
						<span class="b-price add_price">
							<format:price priceData="${entry.basePrice}" displayFreeForZero="true" />
						</span>
					</div>
				</div>
			</div>
			<div class="cl"></div>
		</div>
	</c:if>
</c:forEach>