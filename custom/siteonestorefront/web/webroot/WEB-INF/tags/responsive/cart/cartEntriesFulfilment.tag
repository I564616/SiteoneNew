<%@ attribute name="entry" required="true" type="de.hybris.platform.commercefacades.order.data.OrderEntryData" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="grid" tagdir="/WEB-INF/tags/responsive/grid" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>

<c:set var="isSplitMixedPickupBranchFE" value="false" />
<c:if test="${not empty isSplitMixedCartEnabledBranch && isSplitMixedCartEnabledBranch ne null}" >
	<c:set value="${isSplitMixedCartEnabledBranch}" var="isSplitMixedPickupBranchFE"></c:set>
</c:if>
<c:set var="isSplitCart" value="${isSplitMixedPickupBranchFE}"/>

<c:if test="${!isSplitCart}">
  <div class="row cart-item-icon-container">
    <c:if test="${entry.product.isDeliverable eq false}">
      <div class="flex-center text-orange font-GeogrotesqueSemiBold hidden-xs hidden-sm font13-md marginBottom10 pickupOnly-error-entry" data-analyticerror="Available for pick-up only">
        <span class="m-t-5"><common:exclamation-circle width="20" height="20"/></span>
        <span class="pad-lft-10 pickup_onlyError"><spring:theme code="cart.pickuponly.text"/></span>
      </div>
    </c:if>

    <div class="flex-center text-orange font-size-14 m-b-5 bold hidden-xs hidden-sm hidden national-shipping-element" data-analyticerror="${entry.product.stockAvailableOnlyHubStore? 'Available for shipping only' : 'Pick-Up/Delivery Only' }">
      <c:if test="${entry.product.stockAvailableOnlyHubStore}">
        <common:exclamation-circle width="15" height="15"/>
        <span class="p-l-5"><span class="shipping_onlyError"><spring:theme code="text.cart.problem.ship"/></span>
        </span>
      </c:if>
    </div>
    <div class="cl"></div>
    <input type="hidden" name="segmentshipping" value="${segmentLevelShippingEnabled}"/>
    <c:choose>
      <c:when test="${entry.product.outOfStockImage || entry.product.stockAvailableOnlyHubStore || (entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true)}">
        <div class="col-md-4 col-xs-12 cart-item-col"><div class="cart-item-icon"><common:pickUpIcon height="18" width="22" iconColor="#ccc" /></div></div>
      </c:when>
      <c:otherwise>
        <div class="col-md-4 col-xs-12 cart-item-col"><div class="cart-item-icon"><common:pickUpIcon height="18" width="22" iconColor="#000" /></div></div>
      </c:otherwise>
    </c:choose>
    <c:choose>
      <c:when test="${entry.product.isEligibleForDelivery && entry.product.isDeliverable && !entry.product.stockAvailableOnlyHubStore && (!(entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true))}">
        <div class="col-md-4 col-xs-12 cart-item-col"><div class="cart-item-icon"><common:deliveryIcon height="18" width="37" iconColor="#000"/></div></div>
      </c:when>
      <c:otherwise>
        <div class="col-md-4 col-xs-12 cart-item-col"><div class="cart-item-icon"><common:deliveryIcon height="18" width="37" iconColor="#ccc"/></div></div>
      </c:otherwise>
    </c:choose>	
    <input name='segmentshippingenable' type="hidden" value="${((entry.product.isShippable || entry.product.stockAvailableOnlyHubStore) && (!(entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true))) && segmentLevelShippingEnabled}"/>
    <c:choose>
      <c:when test="${((entry.product.isShippable || entry.product.stockAvailableOnlyHubStore) && (!(entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true))) && segmentLevelShippingEnabled}">
        <div class="col-md-4 col-xs-12 cart-item-col"><div class="cart-item-icon"><common:parcelIcon height="18" width="26" iconColor="#000"/></div></div>
      </c:when>
      <c:otherwise>
        <div class="col-md-4 col-xs-12 cart-item-col"><div class="cart-item-icon"><common:parcelIcon height="18" width="26" iconColor="#ccc"/></div></div>
      </c:otherwise> 
    </c:choose>
  </div>
</c:if>
<input name="entry.product.isDeliverable" type="hidden" value="${entry.product.isDeliverable}">
<c:if test="${isSplitCart}">
    <div class="row split-cart-item-icon-container m-t-25">
      <div class="split-cart-item-icon-items f-s-12 flex font-Arial justify-between justify-center-xs m-l-10">
        <!-- Split Pickup -->
        <c:choose>
          <c:when test="${entry.product.outOfStockImage || entry.product.stockAvailableOnlyHubStore || (entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true)}">
            <div class="p-r-15 m-l-5 text-grey pickup-disable"><span class="m-r-5"><common:tick color="#ccc" /></span><spring:theme code="text.account.order.type.display.PICKUP" /></div>
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${entry.product.isDeliverable eq false}">
               <div class="p-r-10 p-r-15-xs m-l-5 pickup-active pickup-only-split text-orange"><span class="m-r-5"><common:tick color="#EF8700" /></span><spring:theme code="splitcart.pickuponly.text" /></div>
              </c:when>
              <c:otherwise>
                <div class="p-r-10 p-r-15-xs m-l-5 pickup-active"><span class="m-r-5"><common:tick color="#414244" /></span><spring:theme code="text.account.order.type.display.PICKUP" /></div>
              </c:otherwise> 
            </c:choose>
          </c:otherwise>
        </c:choose>
        <div class="b-r-gray m-r-15"></div>
         <!--Split Delivery -->
        <c:choose>
          <c:when test="${entry.product.isEligibleForDelivery && entry.product.isDeliverable && !entry.product.stockAvailableOnlyHubStore && (!(entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true))}">
            <div class="p-r-10 p-r-15-xs delivery-active"><span class="m-r-5"><common:tick  color="#414244" /></span><spring:theme code="text.account.order.type.display.DELIVERY" /></div>
          </c:when>
          <c:otherwise>
            <div class="p-r-10 p-r-15-xs text-grey delivery-disable"><span class="m-r-5"><common:tick color="#ccc" /></span><spring:theme code="text.account.order.type.display.DELIVERY" /></div>
          </c:otherwise>
        </c:choose>
        <div class="b-r-gray m-r-15"></div>
         <!--Split Shipping -->
        <c:choose>
          <c:when test="${((entry.product.isShippable || entry.product.stockAvailableOnlyHubStore) && (!(entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true))) && segmentLevelShippingEnabled}">
              <div class="shipping-active"><span class="m-r-5"><common:tick  color="#414244" /></span><spring:theme code="text.account.order.type.display.SHIPPING" /></div>
          </c:when>
          <c:otherwise>
            <div class="text-grey shipping-disable"><span class="m-r-5"><common:tick color="#ccc" /></span><spring:theme code="text.account.order.type.display.SHIPPING" /></div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
</c:if>