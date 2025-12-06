<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="cartData" type="de.hybris.platform.commercefacades.order.data.CartData" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ attribute name="entries" required="true" type="java.util.List" %>
<%@ attribute name="productTitle" required="true" type="java.lang.String" %>
<%@ attribute name="storeDetails" required="false" type="java.lang.String" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="PickupProductCount" value="${cartData.pickupAndDeliveryEntries}" />
<c:set var="SplitCartPickUpCount" value="${fn:length(PickupProductCount)}" />
<c:set var="ShippingProductCount" value="${cartData.shippingOnlyEntries}" />
<c:set var="SplitCartShippingCount" value="${fn:length(ShippingProductCount)}" />

<c:set var="isSplitCartCheckoutItem" value="false" />
<c:if test="${not empty isSplitMixedCartEnabledBranch && isSplitMixedCartEnabledBranch ne null && orderType eq 'PICKUP'}" >
	<c:set var="isSplitCartCheckoutItem" value="${isSplitMixedCartEnabledBranch}" />
</c:if>
<c:set var="splitCartShippingOnly" value="false" />
<c:if test="${cartData.shippingOnlyEntries[0].fullfilledStoreType == 'HubStore' && orderType eq 'PICKUP'}" >
	<c:set var="splitCartShippingOnly" value="true" />
</c:if>
<c:set var="splitCartPickupProduct" value="false" />
<c:if test="${storeDetails == 'splitPickup' &&  (orderType eq 'PICKUP' || orderType eq 'DELIVERY' )}" >
	<c:set var="splitCartPickupProduct" value="true" />
</c:if>
<c:set var="splitCartShippingProduct" value="false" />
<c:if test="${storeDetails == 'splitShipping' && (orderType eq 'PICKUP' || orderType eq 'DELIVERY' )}" >
	<c:set var="splitCartShippingProduct" value="true" />
</c:if>

<div class="pdp_whitebox col-md-12 checkout-productadded ${splitCartPickupProduct ? 'hidden-xs' : 'Demo'} ${isMixedCartEnabled? 'mixedcart-leftblock': ''}">
	<div class="row">
		<div class="col-md-10 col-xs-10"> <h3 class="headline3"><spring:theme code="${productTitle}"/><span class="${splitCartPickupProduct ? '' : splitCartShippingProduct ? '' : 'hide'}"> [${splitCartPickupProduct ? SplitCartPickUpCount : splitCartShippingProduct ? SplitCartShippingCount : ''}]</span></h3></div>
		<div class="col-md-2 col-xs-2 text-right ${splitCartPickupProduct ? 'hide-pickup-product' : splitCartShippingProduct ? 'hide-shipping-product' : 'hide-products'} pointer">
		 <span class="glyphicon glyphicon-plus green-title hidden-md hidden-lg"></span> 
		 <span class="glyphicon glyphicon-minus green-title hidden-xs hidden-sm"></span> 
		 </div>
		</div>
	<div class="cl"></div>
	<div  class="${splitCartPickupProduct ? 'checkout-pickup-product' : splitCartShippingProduct ? 'checkout-shipping-product' : 'checkout-product'} collapsed">
	 <c:if test="${isMixedCartEnabled ne true}">
	<div class="gray-bg margin-top-20 message-center">
	<c:if test="${orderType eq 'PICKUP' && !splitCartShippingProduct}" > 
	<svg xmlns="http://www.w3.org/2000/svg" width="26" height="20" viewBox="0 0 21.776 18">
	<g transform="translate(-472.37 -141.187)">
		<path fill="#000" d="M493.942,157.84a.828.828,0,0,0-.6-.254h-.926V146.795h1.007a.625.625,0,0,0,.543-.932L491.5,141.5a.623.623,0,0,0-.544-.317h-15.4a.622.622,0,0,0-.543.317l-2.466,4.358a.625.625,0,0,0,.543.932H474.1v10.792h-.927a.8.8,0,1,0,0,1.6h20.174a.792.792,0,0,0,.8-.8A.731.731,0,0,0,493.942,157.84Zm-17.767-15.047H490.34l1.267,2.4h-16.7Zm-.487,14.794c0-1.47,0-7.337,0-10.792h7l-5.6,7.675a.124.124,0,0,0-.006.138l1.837,2.979Zm9.829-.048a.12.12,0,0,1-.085.048h-1.255a.119.119,0,0,1-.1-.1l-.463-5.509a.124.124,0,0,0-.123-.109h-.412a.125.125,0,0,0-.1.052l-1.94,2.753a.124.124,0,0,0-.005.135l.28.466a.125.125,0,0,0,.172.041l0,0,.8-.52a.123.123,0,0,1,.19.117l-.1,2.574a.12.12,0,0,1-.1.1h-1.206a.118.118,0,0,1-.085-.048l-1.711-2.834a.125.125,0,0,1,.006-.137l3.866-5.289a.123.123,0,0,1,.172-.027.116.116,0,0,1,.027.027l3.866,5.289a.124.124,0,0,1,.006.137Zm2.132.048,1.792-2.98a.124.124,0,0,0-.006-.137l-5.6-7.675h7v10.792Z"></path>
	</g>
	</svg>
	<p class="bold"> <spring:theme code="text.pickup.title"/></p>
	</c:if>
	
	
	<c:if test="${orderType eq 'PARCEL_SHIPPING' || (orderType eq 'PICKUP' && splitCartShippingProduct)}" > 
	<svg xmlns="http://www.w3.org/2000/svg" width="28" height="20" viewBox="0 0 26.257 18">
	<g transform="translate(-424.7 -141.187)">
		<path fill="#000" d="M438.422,144.623a.811.811,0,0,0-.467.736v9.66a.809.809,0,0,0,.467.735l7.357,3.37a.906.906,0,0,0,.677,0l7.349-3.366a.811.811,0,0,0,.466-.735v-9.66a.812.812,0,0,0-.467-.736l-7.357-3.37a.936.936,0,0,0-.342-.066.821.821,0,0,0-.328.067Zm6.891,12.492-5.729-2.607v-7.877l5.729,2.607Zm7.329-2.607-5.729,2.607v-7.877l5.729-2.607Zm-6.529-11.6,5.39,2.479-5.39,2.479-5.39-2.479Z" transform="translate(-3.314)"></path>
		<g transform="translate(424.7 145.401)">
			<path fill="#000" d="M432.755,146.8h-2.724a.8.8,0,1,0,0,1.6h2.724a.792.792,0,0,0,.8-.8.724.724,0,0,0-.208-.541A.823.823,0,0,0,432.755,146.8Z" transform="translate(-425.833 -146.805)"></path>
			<path fill="#000" d="M432.194,152.122h-4.409a.8.8,0,1,0,0,1.6h4.437a.792.792,0,0,0,.8-.8A.815.815,0,0,0,432.194,152.122Z" transform="translate(-425.271 -148.134)"></path>
			<path fill="#000" d="M431.623,157.4H425.5a.8.8,0,0,0,0,1.6h6.122a.792.792,0,0,0,.8-.8.731.731,0,0,0-.2-.547A.828.828,0,0,0,431.623,157.4Z" transform="translate(-424.7 -149.454)"></path>
		</g>
	</g>
	</svg>
	<p class="bold"> <spring:theme code="text.parcel.title"/></p>
	</c:if>

	<c:if test="${orderType eq 'DELIVERY' && !splitCartShippingProduct }" > 
	<svg xmlns="http://www.w3.org/2000/svg" width="34" height="16" viewBox="0 0 36.398 18"><g transform="translate(-362.938 -141.335)"><g transform="translate(362.938 145.424)"><path d="M370.982,146.8h-2.715a.8.8,0,0,0,0,1.6h2.715a.789.789,0,0,0,.8-.8.72.72,0,0,0-.206-.54A.823.823,0,0,0,370.982,146.8Z" transform="translate(-364.082 -146.805)"/><path d="M370.414,152.122H366.02a.8.8,0,1,0,0,1.6h4.423a.789.789,0,0,0,.8-.8A.812.812,0,0,0,370.414,152.122Z" transform="translate(-363.515 -148.147)"/><path d="M369.837,157.4h-6.1a.8.8,0,1,0,0,1.6h6.1a.789.789,0,0,0,.8-.8.723.723,0,0,0-.2-.545A.824.824,0,0,0,369.837,157.4Z" transform="translate(-362.938 -149.48)"/></g><path d="M402.25,149.428l-2.919-4.864a1.933,1.933,0,0,0-1.562-.884h-4.489v-.968a1.38,1.38,0,0,0-1.378-1.378H377.49a1.379,1.379,0,0,0-1.377,1.378v12.9a1.381,1.381,0,0,0,1.383,1.377h1.064a3.137,3.137,0,0,0,6.071,0h9.516a3.137,3.137,0,0,0,6.071,0h1.064a1.363,1.363,0,0,0,1.382-1.374v-4.7A3.208,3.208,0,0,0,402.25,149.428Zm-5.455,1.289c0-.552,0-2.17,0-3.125.2.007.505.009.958.01l1.557,0,1.581,2.634a1.683,1.683,0,0,1,.164.475Zm-.206-4.691a1.371,1.371,0,0,0-1.372,1.366v3.54a1.373,1.373,0,0,0,1.375,1.366h4.49v3.113c-.183,0-.486,0-.864,0a3.137,3.137,0,0,0-6.071,0h-.865V145.262h4.489a.416.416,0,0,1,.206.117l.389.648Zm.592,8.618a1.555,1.555,0,1,1-1.555,1.555A1.556,1.556,0,0,1,397.181,154.644ZM391.7,142.916v12.492H384.63a3.137,3.137,0,0,0-6.071,0h-.86c0-1.015,0-5.644,0-9.021v-3.471Zm-10.1,11.728a1.555,1.555,0,1,1-1.555,1.555A1.556,1.556,0,0,1,381.595,154.644Z" transform="translate(-3.327)"/></g>
	</svg>
	<p class="bold"> <spring:theme code="text.delivery.title"/></p>
	</c:if>
	<c:if test="${orderType eq 'DELIVERY' && splitCartShippingProduct }" > 
	<svg xmlns="http://www.w3.org/2000/svg" width="28" height="20" viewBox="0 0 26.257 18">
	<g transform="translate(-424.7 -141.187)">
		<path fill="#000" d="M438.422,144.623a.811.811,0,0,0-.467.736v9.66a.809.809,0,0,0,.467.735l7.357,3.37a.906.906,0,0,0,.677,0l7.349-3.366a.811.811,0,0,0,.466-.735v-9.66a.812.812,0,0,0-.467-.736l-7.357-3.37a.936.936,0,0,0-.342-.066.821.821,0,0,0-.328.067Zm6.891,12.492-5.729-2.607v-7.877l5.729,2.607Zm7.329-2.607-5.729,2.607v-7.877l5.729-2.607Zm-6.529-11.6,5.39,2.479-5.39,2.479-5.39-2.479Z" transform="translate(-3.314)"></path>
		<g transform="translate(424.7 145.401)">
			<path fill="#000" d="M432.755,146.8h-2.724a.8.8,0,1,0,0,1.6h2.724a.792.792,0,0,0,.8-.8.724.724,0,0,0-.208-.541A.823.823,0,0,0,432.755,146.8Z" transform="translate(-425.833 -146.805)"></path>
			<path fill="#000" d="M432.194,152.122h-4.409a.8.8,0,1,0,0,1.6h4.437a.792.792,0,0,0,.8-.8A.815.815,0,0,0,432.194,152.122Z" transform="translate(-425.271 -148.134)"></path>
			<path fill="#000" d="M431.623,157.4H425.5a.8.8,0,0,0,0,1.6h6.122a.792.792,0,0,0,.8-.8.731.731,0,0,0-.2-.547A.828.828,0,0,0,431.623,157.4Z" transform="translate(-424.7 -149.454)"></path>
		</g>
	</g>
	</svg>
	<p class="bold"><spring:theme code="text.product.fullfilment.shipping"/></p>
	</c:if>
	
	<div class="cl"></div>
	</div>
	<c:if test="${(orderType eq 'PICKUP' || orderType eq 'DELIVERY' || !cartData.isShippingFeeBranch) && (orderType eq 'PICKUP' && !splitCartShippingProduct)}"> 
        <div class="row margin-top-20">
	        <c:url value="/store/${fn:escapeXml(pointOfService.storeId)}" var="checkoutstore"/>
	        <div class="col-md-7 col-xs-6"><a href="${checkoutstore}">${pointOfService.name}</a></div>
	        <div class="col-md-5 col-xs-6 text-right padding-LeftZero"><a class="tel-phone" href="tel:${pointOfService.address.phone}">${pointOfService.address.phone}</a></div>
        </div>
    </c:if>
	<div class="checkout-product-details margin-top-20">
	
	
	
	<jsp:useBean id="list" class="java.util.ArrayList"/>
	<jsp:useBean id="list1" class="java.util.ArrayList"/>
	<jsp:useBean id="list2" class="java.util.ArrayList"/>

<c:forEach items="${entries}" var="entry1" varStatus="loop">
	<c:choose>
		<c:when test="${entry1.product.inStockImage}">
			<c:choose>
			<c:when test="${entry1.product.isStockInNearbyBranch}">
			<c:set var="stockNearbyEnteries" value="${list1.add(entry1)}"/>
			</c:when>
			<c:otherwise>
			<c:set var="stockEnteries" value="${list.add(entry1)}"/>
			</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
		<c:set var="backorderEnteries" value="${list2.add(entry1)}"/>
		</c:otherwise>
	</c:choose>
</c:forEach>

	<c:if test="${not empty list}">
 	<c:forEach items="${list}" var="entry" varStatus="loop">
	 	<storepickup:checkoutProductEntry entry="${entry}"/>
	</c:forEach>
	</c:if>
	
	
	<c:if test="${not empty list1}">
	 <c:forEach items="${list1}" var="entry" varStatus="loop">
	 	<storepickup:checkoutProductEntry entry="${entry}"/>
	</c:forEach>
	</c:if>
	
	
	<c:if test="${not empty list2}">
	 <c:forEach items="${list2}" var="entry" varStatus="loop">
	 	<storepickup:checkoutProductEntry entry="${entry}"/>
	</c:forEach>
	</c:if>
	 
	</div>
	</c:if>
		<c:if test="${isMixedCartEnabled}">
			<div class="checkout-product-details mixedcart-ordersummary marginTop10">
				<c:set var="shippingOrder" value="false" />
				<c:set var="orderShow" value="1" />
				  <c:forEach items="${cartData.deliveryModeAndBranchOrderGroups}" var="groupData" varStatus="status">
				  	<c:if test="${groupData.deliveryMode.code eq 'pickup'}">
							<c:set var="orderShow" value="1" />
					</c:if>
					<c:if test="${groupData.deliveryMode.code eq 'standard-net'}">
						<c:set var="orderShow" value="5000" />
					</c:if>
					<c:if test="${groupData.deliveryMode.code eq 'free-standard-shipping'}">
						<c:set var="orderShow" value="10000" />
					</c:if>
				     <div class="${(groupData.deliveryMode.code eq 'free-standard-shipping')? 'mixed-shipping-order' : '' }" data-boxorder="${status.index+1}" data-boxmode="${groupData.deliveryMode.code}" style="order:${orderShow + status.index};">
						<storepickup:checkoutOrderSummaryProduct cartData="${cartData}" groupData="${groupData}" index="${status.index}" showHead="true" shippingFound="${shippingOrder}" />
				     	<c:if test="${orderShow eq 10000}">
							<c:set var="shippingOrder" value="true" />
						</c:if>
					</div>
				</c:forEach>
			</div>
		</c:if>
	</div>
	</div>





	 



