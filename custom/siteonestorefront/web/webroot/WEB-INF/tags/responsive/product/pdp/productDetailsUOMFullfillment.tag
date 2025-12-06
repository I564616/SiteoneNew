<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="b2b-product" tagdir="/WEB-INF/tags/addons/siteoneacceleratoraddon/responsive/product"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="productDetails" tagdir="/WEB-INF/tags/responsive/product/pdp"%>
<%@ attribute name="nonTransferrable" type="java.lang.String"%>
<%@ attribute name="regulatedAndNotSellableProduct" type="java.lang.String"%>
<%@ attribute name="multipleuompdpredesign" type="java.lang.String"%>
<%@ attribute name="multipleuompdpredesigncnt" type="java.lang.String"%>
<%@ attribute name="hardscapeMoreOnWayMsg" type="java.lang.String"%>

<input type="hidden" value="${regulatedAndNotSellableProduct}" id="regulatedAndNotSellableProduct-PDUOMFullfillment"/>
<input type="hidden" value="${multipleuompdpredesign}" id="multipleuompdpredesign-PDUOMFullfillment"/>
<input type="hidden" value="${multipleuompdpredesigncnt}" id="multipleuompdpredesigncnt--PDUOMFullfillment"/>
<input type="hidden" value="${nonTransferrable}" id="nonTransferrable--PDUOMFullfillment"/>
<input type="hidden" value="${hardscapeMoreOnWayMsg}" id="hardscapeMoreOnWayMsg--PDUOMFullfillment"/>
<div class="pdp-uom-fullfillment-section">
	<div class="pdp-uom-fullfillment">
		<div class="no-margin">
			<c:if test="${isMixedCartEnabled eq false}">
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
				<input type="hidden" class="branch-pickupAvailable-pdp" value="${isBranchPickupAvailable}" />
				<input type="hidden" class="branch-deliveryAvailable-pdp" value="${isBranchDeliveryAvailable}" />
				<input type="hidden" class="branch-shippingAvailable-pdp" value="${isBranchShippingAvailable}" />
				<c:set value="false" var="isDeliveryOnlyBranchPdp" />
				<c:if test="${!isBranchPickupAvailable && isBranchDeliveryAvailable && !isBranchShippingAvailable}">
					<c:set value="true" var="isDeliveryOnlyBranchPdp" />
				</c:if>
				<input type="hidden" class="isDeliveryOnlyBranchPdp" value="${isDeliveryOnlyBranchPdp}" />
				<c:set var="disablePickup" value="${product.outOfStockImage || product.stockAvailableOnlyHubStore || nonTransferrable}" />
				<c:if test="${isDeliveryOnlyBranchPdp || regulatedAndNotSellableProduct == true}">
					<c:set value="true" var="disablePickup" />
				</c:if>
				<div class="pdp-fullfillment margin">
					<div class="js-info-tootip" rel="pdp-tooltip">
						<div class="tooltip-content hide">
							<common:FulfillmentTooltipContent fulfillment="pickUp" />
						</div>
						<div class="tooltip-icon">
							<div class="pdp-fullfillment-text">
								<common:checkIcon height="7" width="9" iconColor="${disablePickup? '#CCCCCC': '#414244'}" /> &nbsp;
								<div class="${disablePickup? 'text-grey': 'text-normal' }"><spring:theme code="pdp.new.text.fulfillment.pickup"/></div>
							</div>
						</div>
					</div>
				</div>
				<c:set var="disableDelivery" value="${product.outOfStockImage || product.stockAvailableOnlyHubStore || !product.isDeliverable || nonTransferrable}" />
				<c:if test="${isBranchDeliveryAvailable == false || regulatedAndNotSellableProduct == true}">
					<c:set var="disableDelivery" value="true" />
				</c:if>
				<div class="pdp-fullfillment">
					<div class="js-info-tootip" rel="pdp-tooltip">
						<div class="tooltip-content hide">
							<common:FulfillmentTooltipContent fulfillment="delivery" />
						</div>
						<div class="tooltip-icon">
							<div class="pdp-fullfillment-text">
								<common:checkIcon height="7" width="9" iconColor="${disableDelivery? '#CCCCCC': '#414244'}" /> &nbsp;
								<div class="${disableDelivery? 'text-grey': 'text-normal' }"><spring:theme code="pdp.new.text.fulfillment.delivery"/></div>
							</div>
						</div>
					</div>
				</div>
				<input type="hidden" class="productsimpleproduct-segmentLevelShippingEnabled" value="${segmentLevelShippingEnabled}" />
				<div class="pdp-fullfillment" data-isNationalShippingPDP="${product.isNationalShippingPDP}" data-isBranchShippingAvailable="${isBranchShippingAvailable}"
					data-isShippable="${product.isShippable}" data-stockAvailableOnlyHubStore="${product.stockAvailableOnlyHubStore}"
					data-nonTransferrable="${nonTransferrable}" data-isnonTransferrable="${product.isTransferrable eq false && product.isStockInNearbyBranch eq true}"
					data-isTransferrable="${product.isTransferrable}" data-isStockInNearbyBranch="${product.isStockInNearbyBranch}"
					data-shippingfullfillment="${sessionStore.shippingfullfillment}" data-enableShipping="${((product.isShippable || product.stockAvailableOnlyHubStore) && (!nonTransferrable))}">
					<c:set var="enableShipping" value="${(((product.isShippable || product.stockAvailableOnlyHubStore) && (!nonTransferrable)) && segmentLevelShippingEnabled)}" />
					<c:if test="${isBranchShippingAvailable == false || isDeliveryOnlyBranchPdp==true || segmentLevelShippingEnabled == false || regulatedAndNotSellableProduct == true}">
						<c:set var="enableShipping" value="false" />
					</c:if>
					<div class="js-info-tootip ${product.isNationalShippingPDP == true and isBranchShippingAvailable == false? ' hidden' : ''}" rel="pdp-tooltip">
						<div class="tooltip-content hide">
							<common:FulfillmentTooltipContent fulfillment="parcel" />
						</div>
						<div class="tooltip-icon">
							<div class="pdp-fullfillment-text">
								<common:checkIcon height="7" width="9" iconColor="${enableShipping? '#414244': '#CCCCCC'}" /> 	&nbsp;
								<div class="${enableShipping? 'text-normal': 'text-grey' }"><spring:theme code="pdp.new.text.fulfillment.shipping"/></div>
							</div>
						</div>
					</div>
				</div>
				<div class="pdp-uom-fullfillment-branch-data hidden-xs">
					<div class="pdp-uom-fullfillment-stock-section ${multipleuompdpredesign && multipleuompdpredesigncnt > 1 ? 'hidden':''}">
						<productDetails:productDetailsUOMStockInfo hardscapeMoreOnWayMsg="${hardscapeMoreOnWayMsg}" />
						<span class="flex ${multipleuompdpredesign && multipleuompdpredesigncnt > 1 ? '':'hidden'}">
							<common:headerIcon iconName="marker" iconFill="#77A12E" width="11" height="15" viewBox="0 0 15 18" /> &nbsp;
								${sessionStore.name}
						</span>
				</div>
				<button class="pdp-uom-fullfillment-checkotherbranch pdp-store-link hidden-xs">
					<spring:theme code="pdp.new.text.check.other.branches" />
				</button>
			</c:if>
		</div>
	</div>
	<div
		class="hidden-sm hidden-md hidden-lg pdp-uom-fullfillment-stock-section-mobile">
		<div class="pdp-uom-fullfillment-branch-data">
			<div class="pdp-uom-fullfillment-stock-section">
				<productDetails:productDetailsUOMStockInfo hardscapeMoreOnWayMsg="${hardscapeMoreOnWayMsg}" />
				<span class="hidden flex"> <common:headerIcon iconName="marker" iconFill="#77A12E" width="11" height="15" viewBox="0 0 15 18" /> &nbsp;
					${sessionStore.name}
				</span>
		</div>
		<button class="pdp-uom-fullfillment-checkotherbranch pdp-store-link">
			<spring:theme code="pdp.new.text.check.other.branches" />
		</button>
	</div>
</div>
