<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.CartData" %>
<%@ attribute name="groupData" required="true" type="de.hybris.platform.commercefacades.order.data.OrderEntryGroupData" %>
<%@ attribute name="index" required="true" type="java.lang.Integer" %>
<%@ attribute name="showPotentialPromotions" required="false" type="java.lang.Boolean" %>
<%@ attribute name="showHead" required="false" type="java.lang.Boolean" %>
<%@ attribute name="fromPage" required="false" type="java.lang.String" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="multi-checkout" tagdir="/WEB-INF/tags/responsive/checkout/multi" %>
 <div class="cl"></div>
 <div class="row">
<div class="checkout-order-summary-list ${(isMixedCartEnabled)? 'checkout-orderlist-mixedcart' : '' }">
 <c:forEach items="${groupData.entries}" var="entry">	
	<c:url value="${entry.product.url}" var="productUrl"/>
		<div class="checkout-order-summary-list-items flex-center-md checkout-product-details">
			<div class="checkout-product-thumb col-md-1 col-xs-3">
				<product:productPrimaryImage product="${entry.product}" format="product" />
			</div>
			
			<div class="details col-md-5 col-xs-9 padleft0">
				<div class="name title black-title pad-xs-bot-15"> ${fn:escapeXml(entry.product.name)} </div>
				</div>
				<div class="cl hidden-md hidden-lg"></div>
				<div class="col-md-5 col-xs-9 mb-white-border inventory-msg">
				     <c:set var="requestedQty" value="${entry.quantity}"/>
    <c:set var="homeStoreQty" value="${entry.product.pickupHomeStoreInfo.onHandQuantity}"/>
    <c:set var="nearbyStoresQty" value="${entry.product.pickupNearbyStoreInfo.onHandQuantity}"/>
    <c:set var="deliveryStoresQty" value="${entry.product.deliveryStoreInfo.onHandQuantity}"/>
    <c:set var="shippingStoresQty" value="${entry.product.shippingStoreInfo.onHandQuantity}"/>
    <c:set var="hubStoresQty" value="${entry.product.hubStoresAvailableQty}"/>
    <c:set var="showAvailableMessage" value="hidden"/>
  	<c:set var="onHandQty" value=""/>
    <c:set var="remainingQty" value=""/>
    <c:choose>
    	<c:when test="${(entry.defaultFulfillmentType eq 'pickuphome') and (requestedQty gt homeStoreQty) and (entry.product.isEligibleForBackorder ne true)}">
    		<c:set var="showAvailableMessage" value=""/>
    		<c:set var="onHandQty" value="${homeStoreQty}"/>
    		<c:set var="remainingQty" value="${requestedQty - homeStoreQty}"/>
    	</c:when>
    	<c:when test="${(entry.defaultFulfillmentType eq 'pickupnearby') and (requestedQty gt nearbyStoresQty) and (entry.product.isEligibleForBackorder ne true)}">
    		<c:set var="showAvailableMessage" value=""/>
    		<c:set var="onHandQty" value="${nearbyStoresQty}"/>
    		<c:set var="remainingQty" value="${requestedQty - nearbyStoresQty}"/>
    	</c:when>
    	<c:when test="${(entry.defaultFulfillmentType eq 'delivery') and (requestedQty gt deliveryStoresQty) and (entry.product.isEligibleForBackorder ne true)}">
    		<c:set var="showAvailableMessage" value=""/>
    		<c:set var="onHandQty" value="${deliveryStoresQty}"/>
    		<c:set var="remainingQty" value="${requestedQty - deliveryStoresQty}"/>
    	</c:when>
    	<c:when test="${(entry.defaultFulfillmentType eq 'shipping') and (requestedQty gt shippingStoresQty) and (entry.product.isEligibleForBackorder ne true)}">
    		<c:set var="showAvailableMessage" value=""/>
    		<c:set var="onHandQty" value="${shippingStoresQty}"/>
    		<c:set var="remainingQty" value="${requestedQty - shippingStoresQty}"/>
    	</c:when>
    </c:choose>
   
       <c:if test="${(entry.product.productType ne 'Nursery') and (onHandQty gt 0)}">
                       
                    <div class="js-availablility-msg-mixed-cart ${showAvailableMessage}" 
                    data-requestedqty="${requestedQty}"
                    data-homestoreqty="${homeStoreQty}"
                    data-nearbystoreqty="${nearbyStoresQty}"
                    data-deliverystoreqty="${deliveryStoresQty}"
                    data-shippingstoresqty="${shippingStoresQty}"
                    data-isbackordarable="${entry.product.isEligibleForBackorder}"
                    >
                        <div class="flex-center cart-availability-backorder-msg" >
                            <common:exclamationCircle />
                            <div class="pad-lft-10  pad-rgt-10">
	                            <spring:theme code="cart.more.quantity.than.available1"/>
	                            <span class="js-onhand-qty">${onHandQty}</span>
	                            <spring:theme code="cart.more.quantity.than.available2"/>
	                            <span class="js-remaining-qty">${remainingQty}</span>
	                            <spring:theme code="cart.more.quantity.than.available3"/>                                   
							</div>
							 
	                        </div>
                   		</div>                                     
                
            </c:if> 
                  
        <!-- Nearest Backorderable at Iten Level -->
			
			<div class="${entry.product.isEligibleForBackorder ? '':'hidden'}">
			<div class="flex-center font-small-xs cart-delivery-threshold-message">
				<common:exclamationCircle />
					<div class="pad-lft-10 pad-rgt-10 cart-threshold-message">
						<div class="delivery-threshold-text">
							<spring:theme code="cart.backorder.message" arguments="${not empty entry.product.nearestBackorderableStore ? entry.product.nearestBackorderableStore.name : entry.product.pickupHomeStoreInfo.storeName}" />
						</div>				
						 
						<c:set var="quantityBoxDisable" value="false" />
						
					</div>
					</div>
					</div>
				
		<!-- Nearest Backorderable at Iten Level Ends-->
		
				
				</div>
				<div class="checkout-product-qty col-md-1 col-xs-3 ${(isMixedCartEnabled)? 'paddingtop20' : '' } padleft0 black-title"><span class="title">${entry.quantity} </span> <div class="cl hidden-xs"></div> 
				<span class="text-uppercase qty-text"><spring:theme code="product.product.details.future.qty"/></span>
				</div>
				<div class="cl"></div> 

		</div>
</c:forEach> 


</div>

<c:if test="${groupData.deliveryMode.code eq 'pickup'}">
<div class="checkout-branch-msg-wrapper ${(isMixedCartEnabled)? 'checkout-orderlist-mixedcart' : '' }">
	<div class="checkout-branch-msg"> <spring:theme code="mixcart.branchMsg.pickip1"/> <strong> ${groupData.pointOfService.name} </strong> 
	<span class="line-br"> <spring:theme code="mixcart.branchMsg.pickip2"/> </span>
	</div>
</div>
 </c:if>
 <c:if test="${groupData.deliveryMode.code eq 'standard-net'}">
 <div class="checkout-branch-msg-wrapper ${(isMixedCartEnabled)? 'checkout-orderlist-mixedcart' : '' }">
 <c:choose>
 	<c:when test="${cartData.showDeliveryFeeMessage eq true}">
 	<div class="checkout-branch-msg">  <strong>${groupData.pointOfService.name}</strong> <spring:theme code="mixcart.branchMsg.deliveryFee"/></div>
	</c:when>
 
	 <c:otherwise>
		 <div class="checkout-branch-msg">  <strong>${groupData.pointOfService.name}</strong> <spring:theme code="mixcart.branchMsg.delivery"/></div>
	 </c:otherwise>
 </c:choose>
	</div>

 </c:if>
 <c:if test="${groupData.deliveryMode.code eq 'free-standard-shipping'}">
 <div class="checkout-branch-msg-wrapper ${(isMixedCartEnabled)? 'checkout-orderlist-mixedcart' : '' }">
	<div class="checkout-branch-msg ${(isMixedCartEnabled)? 'checkout-shipping-msg' : '' }"> <spring:theme code="mixcart.branchMsg.shipping"/>
	</div>
</div>
 </c:if>
 </div>
