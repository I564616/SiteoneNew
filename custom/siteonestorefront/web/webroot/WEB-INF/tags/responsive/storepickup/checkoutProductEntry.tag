<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="cartData" type="de.hybris.platform.commercefacades.order.data.CartData" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ attribute name="entry" required="true" type="de.hybris.platform.commercefacades.order.data.OrderEntryData" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<c:set var="isPickupDateRequired" value="false" />
<c:set var="isChecked" value="${entry.bigBagInfo.isChecked ne null && not empty entry.bigBagInfo.isChecked ? entry.bigBagInfo.isChecked : false}" />
	<div class="checkout-product-sec" data-quantity="${entry.quantity}" data-homeStoreAvailableQty="${entry.product.homeStoreAvailableQty}" data-nearbyStoresAvailableQty="${entry.product.nearbyStoresAvailableQty}" data-hubStoresAvailableQty="${entry.product.hubStoresAvailableQty}" data-hubStoreNumber="${hubStoreId}" data-isRUPTrainingSku="${entry.product.isRUPTrainingSku}" data-isRegulatoryLicenseCheckFailed="${entry.product.isRegulatoryLicenseCheckFailed}" data-isEligibleForBackorder="${entry.product.isEligibleForBackorder}" data-productType="${entry.product.productType}" data-isForceInStock="${entry.product.isForceInStock}" data-inStockImage="${entry.product.inStockImage}" data-cartentry="${entry.entryNumber}" data-isDeliverable="${entry.product.isDeliverable}" data-isShippable="${entry.product.isShippable}" data-stockAvailableOnlyHubStore="${entry.product.stockAvailableOnlyHubStore}" data-nontransferableFlag="${(entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true)?true:false}" data-outOfStockImage="${entry.product.outOfStockImage}" data-isTransferrable="${entry.product.isTransferrable}" data-isStockInNearbyBranch="${entry.product.isStockInNearbyBranch}" data-allFulfillmentEnabled="${(entry.product.isDeliverable && entry.product.isShippable && (!entry.product.stockAvailableOnlyHubStore))?true:false}" data-pickupenabled="${(entry.product.outOfStockImage || entry.product.stockAvailableOnlyHubStore || (entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true))?false: true}" data-deliveryenabled="${(entry.product.isEligibleForDelivery && entry.product.isDeliverable && !entry.product.stockAvailableOnlyHubStore && (!(entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true)))?true: false}" data-shippingenabled="${((entry.product.isShippable || entry.product.stockAvailableOnlyHubStore) && (!(entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true)))?true: false}">
	<!--
	1 - nearby
	2 - reqty>homeStoreQty
	3 - isEligibleForBackorder
	4 - entry.product.isForceInStock
	-->
		<c:if test="${entry.product.homeStoreAvailableQty eq 0 or entry.quantity > entry.product.homeStoreAvailableQty}" > 
			<c:set var="isPickupDateRequired" value="true" />
		</c:if>
		<input class="isPickupDateRequired" value="${isPickupDateRequired}" type="hidden"/> 
		<div class="product-thumb col-md-3 col-xs-3 padding0"><product:productPrimaryImage product="${entry.product}" format="product" /></div>
		<div class="checkout-product-info col-md-9 col-xs-9">
		<p class="black-title bold-text">${fn:escapeXml(entry.product.name)}</p>
		<div class="row">
		<div class="col-md-6 col-xs-6"><span><spring:theme code="text.account.savedCart.qty"/>: ${entry.quantity}</span></div>
		<div class="col-md-6 col-xs-6 text-right padding-rightZero"><span> <format:price priceData="${entry.totalPrice}" displayFreeForZero="true" /></span></div>
		</div>
		<c:if test="${isChecked eq true}">
		<div class="m-t-10 p-l-15 row">
		<div class="b-t-grey-2 p-b-10"></div>
        <div class="col-md-6 col-xs-6 p-l-0"><span>Big Bags</span></div>
        <div class="col-md-6 col-xs-6 text-right padding-rightZero"><span> 
        <span class="black-title b-price add_price atc-price-analytics">
        $${entry.bigBagInfo.totalPrice}</span>
    	</span></div>
        </div>
        </c:if>
		</div>
		<div class="cl"></div>
	</div>
	
	 





	 


