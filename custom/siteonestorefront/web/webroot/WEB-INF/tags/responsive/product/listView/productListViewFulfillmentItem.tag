<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="nonTransferrable" type="java.lang.String" %>
<%@ attribute name="isNotInStock" type="java.lang.String" %>
<%@ attribute name="regulatedAndNotSellableProduct" type="java.lang.String" %>
<%@ attribute name="onlyHubStoreAvailability" type="java.lang.String" %>
<%@ attribute name="isMyStoreProduct" type="java.lang.String" %>
<%@ attribute name="isProductSellable" type="java.lang.String" %>
<%@ attribute name="moreontheway" type="java.lang.String" %>
<%@ attribute name="backorder" type="java.lang.String" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<!-- Is not in stock variable start -->
<c:set var="isNotInStock" value="false"/>
<c:set var="isShippableNotInStock" value="false"/>
<c:if test="${!product.multidimensional || product.variantCount ge 1 }">
    <c:if test="${product.isRegulateditem}">
        <c:if test="${!isMyStoreProduct && !onlyHubStoreAvailability}">
        <c:set var="isNotInStock" value="true"/>
        <c:set var="isShippableNotInStock" value="true"/>
        </c:if>
        <c:if test="${isProductSellable && onlyHubStoreAvailability}">
            <c:set var="isNotInStock" value="true"/>
            <c:set var="isShippableNotInStock" value="true"/>
        </c:if>
    </c:if>
    <c:if test="${!product.isRegulateditem && !product.isProductDiscontinued}">
        <c:choose>
            <c:when test="${product.isStockAvailable}">
                <c:choose>
                    <c:when test="${onlyHubStoreAvailability}">
                        <c:set var="isNotInStock" value="true"/>
                        <c:set var="isShippableNotInStock" value="true"/>
                    </c:when>
                </c:choose>
            </c:when>
            <c:otherwise>
                <c:if test="${!moreontheway && !backorder}">
                    <c:set var="isNotInStock" value="true"/>
                    <c:set var="isShippableNotInStock" value="true"/>
                </c:if>
            </c:otherwise>
        </c:choose>
    </c:if>
</c:if>
<c:if test="${onlyHubStoreAvailability}">
    <c:set var="isNotInStock" value="false"/>
</c:if>
<input type="hidden" class="plp-isNotInStock" value="${isNotInStock}" />
<input type="hidden" class="plp-isShippableNotInStock" value="${isShippableNotInStock}" />
<!-- Is not in stock variable end -->
<c:set value="true" var="isBranchPickupAvailable"></c:set>
<c:set value="true" var="isBranchDeliveryAvailable"></c:set>
<c:set value="true" var="isBranchShippingAvailable"></c:set>
<c:if test="${not empty sessionStore.pickupfullfillment && sessionStore.pickupfullfillment ne null}">
    <c:set value="${sessionStore.pickupfullfillment}" var="isBranchPickupAvailable"></c:set>
</c:if>
<c:if test="${not empty sessionStore.deliveryfullfillment && sessionStore.deliveryfullfillment ne null}">
    <c:set value="${sessionStore.deliveryfullfillment}" var="isBranchDeliveryAvailable"></c:set>
</c:if>
<c:if test="${not empty sessionStore.shippingfullfillment && sessionStore.shippingfullfillment ne null}">
    <c:set value="${sessionStore.shippingfullfillment}" var="isBranchShippingAvailable"></c:set>
</c:if>
<input type="hidden" class="plp-branch-pickupAvailable" value="${isBranchPickupAvailable}" />
<input type="hidden" class="plp-branch-deliveryAvailable" value="${isBranchDeliveryAvailable}" />
<input type="hidden" class="plp-branch-shippingAvailable" value="${isBranchShippingAvailable}" />
<input type="hidden" class="plp-branch-segmentLevelShippingEnabled" value="${segmentLevelShippingEnabled}" />
<input type="hidden" class="plp-fulfilment" value="${(isShippableNotInStock && ((product.variantCount eq 1) || (!product.multidimensional)))}" />
<div class="col-xs-12 col-sm-12 col-md-12 ${(isShippableNotInStock && ((product.variantCount eq 1) || (!product.multidimensional))) ? 'hide':''} ${notPurchasable? ' hide ': ' '}">
    <div class="plp_icon_details no-margin flex-center justify-center row">
        <div class="col-xs-4 plp_icon_details__data_lv">
            <c:set var="pickUpFlag" value="${(nonTransferrable || isNotInStock || !isBranchPickupAvailable || regulatedAndNotSellableProduct)}" />
            <c:set var="pickUpFlag1" value="${(isNotInStock || !isBranchPickupAvailable)}" />
            <input type="hidden" class="plp-pickUpFlag" value="${pickUpFlag}" />
            <input type="hidden" class="plp-pickUpFlag1" value="${pickUpFlag1}" />
            <common:checkIcon height="12" width="10" iconColor="${pickUpFlag ? '#CCC': '#414244'}" />
            <span class="m-l-5 m-r-5 f-s-12 f-w-400 ${pickUpFlag ? 'text-gray' : 'text-gray-1'}"><spring:theme code="text.product.fullfilment.pickup" /></span>
        </div>
        <div class=" col-xs-4 plp_icon_details__data_lv">
            <c:set var="deliveryFlag"
            value="${(nonTransferrable || isNotInStock || !product.isDeliverable || !isBranchDeliveryAvailable || regulatedAndNotSellableProduct)}" />
            <c:set var="deliveryFlag1"
            value="${(isNotInStock || !product.isDeliverable || !isBranchDeliveryAvailable)}" />
        <input type="hidden" class="plp-deliveryFlag" value="${deliveryFlag}" />
        <input type="hidden" class="plp-deliveryFlag1" value="${deliveryFlag1}" />
            <common:checkIcon height="12" width="10" iconColor="${deliveryFlag ? '#CCC': '#414244'}" />
            <span class="m-l-5 f-s-12 f-w-400 ${deliveryFlag ? 'text-gray' : 'text-gray-1'}"><spring:theme code="text.product.fullfilment.delivery" /></span>
        </div>
        <div class="col-xs-4 plp_icon_details__data_lv">
            <c:set var="shippingFlag"
            value="${((isBranchShippingAvailable && (product.isShippable || onlyHubStoreAvailability)) && segmentLevelShippingEnabled && !regulatedAndNotSellableProduct)}" />
            <c:set var="shippingFlag1"
            value="${((isBranchDeliveryAvailable && (product.isShippable || onlyHubStoreAvailability)) && segmentLevelShippingEnabled)}" />
        <input type="hidden" class="plp-shippingFlag" value="${shippingFlag}" />
        <input type="hidden" class="plp-shippingFlag1" value="${shippingFlag1}" />
            <common:checkIcon height="12" width="10" iconColor="${shippingFlag ? '#414244': '#CCC'}" />
            <span class="m-l-5 m-r-5 f-s-12 f-w-400 ${shippingFlag ? 'text-gray-1' : 'text-gray'}"><spring:theme code="text.product.fullfilment.shipping" /></span>
        </div>
    </div>
</div>