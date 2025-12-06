<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="productItem" tagdir="/WEB-INF/tags/responsive/product/cardView" %>
<%@ taglib prefix="listviewComponent" tagdir="/WEB-INF/tags/responsive/product/listView" %>
<%@ taglib prefix="productItemVariant" tagdir="/WEB-INF/tags/responsive/product/cardView/variants" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ attribute name="loop" type="java.lang.String" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="analytics" tagdir="/WEB-INF/tags/shared/analytics" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<analytics:GA4HiddenFields product="${product}"/>

<c:set var="productQuantity_p" value="1"/>
<c:if test="${product.orderQuantityInterval eq null || product.orderQuantityInterval eq '0' || product.orderQuantityInterval lt '0'  || product.orderQuantityInterval eq ''}">
	<c:set var="productQuantity_p" value="1" />
</c:if>
<c:if test="${product.orderQuantityInterval ne null && product.orderQuantityInterval ne '0' && product.orderQuantityInterval gt '0' && product.orderQuantityInterval ne ''}">
	<c:set var="productQuantity_p" value="${product.orderQuantityInterval}" />
</c:if>
<c:if test="${product.minOrderQuantity ne null && product.minOrderQuantity ne '0' && product.minOrderQuantity ne '' && empty product.sellableUoms[0].parentInventoryUOMID}">
	<c:set var="productQuantity_p" value="${product.minOrderQuantity}" />
</c:if>

 <c:set var="notPurchasable" value="false" />
<input type="hidden" class="isSellableForB2BUnitInputPDP" value="${product.isSellableForB2BUnit}" />
<c:if test="${not empty product.isSellableForB2BUnit && product.isSellableForB2BUnit ne null && product.isSellableForB2BUnit eq false}">
	<c:set var="notPurchasable" value="true" />
</c:if>

<c:set var="regulatedAndNotSellableProduct" value="false" />
<c:if test="${not empty product.isRegulatedAndNotSellable && product.isRegulatedAndNotSellable ne null && product.isRegulatedAndNotSellable ne false}">
	<c:set var="regulatedAndNotSellableProduct" value="true" />
</c:if>
 
<spring:theme code="text.addToCart" var="addToCartText"/>
<c:url value="${product.url}" var="productUrl"/>
<c:set value="${not empty product.potentialPromotions}" var="hasPromotion"/>
<c:set var="hideList" value="${product.hideList}"/>
<c:set var="hideCSP" value="${product.hideCSP}"/>
<c:set var="strikeListPrice" value="false"/>
<c:set var="sessionStoreDisplayName"  value="${not empty sessionNearbyStores[0].displayName ? sessionNearbyStores[0].displayName : sessionNearbyStores[0].name }"/>
<c:set var="sellableUomListLen" value="${fn:length(product.sellableUoms)}" />
<c:set var="onlyHubStoreAvailability"  value="false" />
<c:set var="isBackorderAndShippable"  value="false" />
<c:set var="isHomeOrNearbyStockAvailable" value="false"/>
<c:set var="isMyStoreProduct" value="false" />
<c:set var="showAskAnExpertButton" value="false" />
<c:forEach items="${product.stores}" var="store">
	<c:if test="${store eq product.nearestStore.storeId}">
		<c:set var="isMyStoreProduct" value="true" />
		<c:set var="isHomeOrNearbyStockAvailable" value="true"/>
	</c:if>
</c:forEach>
<c:set var="quoteUomMeasure" value="${false}" />
<c:set var="quoteUomMeasureDesc" value="${false}" />
<c:set var="entryFound" value="${false}" />
<c:forEach items="${product.sellableUoms}" var="sellableUom1">
	<c:if test="${entryFound eq false}">
		<c:set var="entryFound" value="${true}" />
		<c:set var="quoteUomMeasureDesc" value="${quoteUom.inventoryUOMDesc}" />
		<c:set var="quoteUomMeasure" value="${quoteUom.measure}" />
	</c:if>
</c:forEach>
<c:if test="${product.singleUom eq true}">
	<c:set var="quoteUomMeasureDesc" value="${product.singleUomDescription}"/>
	<c:set var="quoteUomMeasure" value="${product.singleUomMeasure}"/>
</c:if>
<input class="quoteUom-MeasureDesc" type="hidden" value='${quoteUomMeasureDesc}'>
<input class="quoteUom-Measure" type="hidden" value='${quoteUomMeasure}'>
<input class="quoteUom-CustomerPrice" type="hidden" value='${not empty product.customerPrice ? product.customerPrice.value : '0.00'}'>
<input class="quoteUom-Price" type="hidden" value='${not empty product.price ? product.price.value : '0.00'}'>
<input class="quoteUom-code" type="hidden" value='${product.code}'>
<input type="hidden" id="hardscapeisSellable-plp" name="hardscapeisSellable-plp" value="${product.isSellable}"/>
<input type="hidden" id="hardscape-outofstock" name="hardscape-outofstock" value="${!product.isStockAvailable}"/>
<input type="hidden" class="item-isForceInStock" name="item-isForceInStock" value="${not empty product.isForceInStock and product.isForceInStock ne null ? product.isForceInStock : 1}"/>
<span class="hidden item-outOfStockInvHitForceStockMsg">${not empty outOfStockInvHitForceStockMsg and outOfStockInvHitForceStockMsg ne null ? outOfStockInvHitForceStockMsg : 2}"</span>
<span class="hidden item-outOfStockInvHitMsg">${not empty outOfStockInvHitMsg and outOfStockInvHitMsg ne null ? outOfStockInvHitMsg : 3}</span>
<c:if test="${product.isSellableInventoryHit eq true}">
	<c:set var="isMyStoreProduct" value="true" />
</c:if>
<c:if test="${product.askAnExpertEnable eq true}">
	<c:set var="showAskAnExpertButton" value="true" />
</c:if>
<c:choose>
	<c:when test="${showAskAnExpertButton && !product.isStockAvailable && !(!isHomeOrNearbyStockAvailable && (product.isSellableInventoryHit eq true) && !onlyHubStoreAvailability)}">
		<c:set var="hideHardscapeplp" value="hide" />
	</c:when>
	<c:otherwise>
		<c:set var="hideHardscapeplp" value="" />
	</c:otherwise>
</c:choose>
<c:forEach items="${product.stores}" var="store">
	<c:if test="${(isMyStoreProduct eq false && (product.isShippable && !empty sessionStore.hubStores && store eq sessionStore.hubStores[0]))}">
		<c:set var="isMyStoreProduct" value="true" />
		<c:set var="onlyHubStoreAvailability"  value="true" />
	</c:if>
	<c:if test="${product.isSellableInventoryHit eq true && !isHomeOrNearbyStockAvailable && (product.isShippable && !empty sessionStore.hubStores && store eq sessionStore.hubStores[0])}">
		<c:set var="isBackorderAndShippable"  value="true" />
	</c:if>
</c:forEach>
<c:set var="inventoryFlag" value="${product.inventoryFlag}" />
<c:if test="${empty product.inventoryFlag}">
	<c:choose>
		<c:when test="${isHomeOrNearbyStockAvailable eq true}">
			<c:set var="inventoryFlag" value="false" />
		</c:when>
		<c:otherwise>
			<c:set var="inventoryFlag" value="true" />
		</c:otherwise>
	</c:choose>
</c:if>
<c:if test="${(not empty product.customerPrice && product.customerPrice.value ne '0.0') && ((not empty product.price && product.price.value ne '0.0') || not empty product.priceRange.minPrice)}">
	<c:set var="strikeListPrice" value="true"/>
</c:if>
<c:choose>
	<c:when test="${empty cookie['gls'] && sessionStore.storeId eq '172'}">
		<c:set var="contactNo" value="<%=de.hybris.platform.util.Config.getParameter(\"siteOne.Customer.Service.Number\")%>"/>
	</c:when>
	<c:otherwise>
		<c:set var="contactNo" value="${sessionStore.address.phone}"/>
	</c:otherwise>
</c:choose>
<c:if test="${(!product.multidimensional || product.variantCount ge 1) && product.isRegulateditem}">
	<c:set var="isProductSellable" value="false" />
	<c:forEach items="${product.regulatoryStates}" var="regulatoryStates">
		<c:if test="${regulatoryStates eq sessionStore.address.region.isocodeShort}">
			<c:set var="isProductSellable" value="true" />
		</c:if>
	</c:forEach>
</c:if>
<c:set var="hardscapeMoreOnWayMsg" value="false"/>
<c:if test="${!isAnonymous and product.isEligibleForBackorder eq true and product.inventoryCheck eq true and (fn:escapeXml(product.categories[1].name)== 'Manufactured Hardscape Products'  || fn:escapeXml(product.categories[1].name) == 'Productos de Paisajismo Manufacturados' || fn:escapeXml(product.categories[1].name) == 'Natural Stone' || fn:escapeXml(product.categories[1].name) == 'Piedra Natural' || fn:escapeXml(product.categories[1].name) == 'Outdoor Living' || fn:escapeXml(product.categories[1].name) == 'Vida al Aire Libre')}">
	<c:set var="hardscapeMoreOnWayMsg" value="true"/>
</c:if>
<input type="hidden" value="${hardscapeMoreOnWayMsg}" class="hardscapeMoreOnWayMsg" />
<input type="hidden" name="product.categories[0].name" value="${fn:escapeXml(product.categories[0].name)}" />
<input type="hidden" name="product.level1Category" value="${fn:escapeXml(product.level1Category)}" />
<c:set var="isHardscapeProdFound" value="false" />
<c:forEach items="${product.categories}" var="categoryObj">
	<c:if test="${categoryObj.name eq 'Hardscapes & Outdoor Living' || categoryObj.name eq 'Materiales duros & Vida al Aire Libre'}">
		<c:set var="isHardscapeProdFound" value="true" />
	</c:if>
</c:forEach>
<c:if test="${isHardscapeProdFound eq true}">
	<input type="hidden" class="hardscapeProd_${product.code}" name="isHardscapeProduct" value="true" />
	<input type="hidden" class="hardscapeForceStock_${product.code}" value="${product.isForceInStock}" />
</c:if>
<c:set var="optionsCount" value="1" />
<c:choose>
	<c:when test="${product.multidimensional}">
		<c:if test="${product.variantCount > 1}">
			<c:set var="optionsCount" value="${product.variantCount}" />
		</c:if>
	</c:when>
	<c:otherwise>
		<c:if test="${not empty product.sellableUoms}">
			<c:set var="optionsCount" value="${fn:length(product.sellableUoms)}" />
		</c:if>
	</c:otherwise>
</c:choose>
<c:set var="callForPricingFlag" value="" />
<div class="cl"></div>
<ycommerce:testId code="product_wholeProduct">
	<c:if test="${product.multidimensional}">
		<div id="cardview-variants-list-${product.code}">
			<c:set var="variantdataavailability" value="false" />
			<c:forEach items="${baseVariantMap[product.code]}" var="variantProduct" varStatus="loop" begin="0" end="0">
	            <c:set var="variantdataavailability" value="true" />
	        </c:forEach>
			<c:if test="${variantdataavailability == 'true'}">
				<c:set var="variantshowhide" value="true" />
				<c:forEach items="${baseVariantMap[product.code]}" var="variantProduct" varStatus="loop">
					<div class="row compare-and-atl flex-center-md productVariantSection-${product.code}-${variantProduct.code} a1 ${variantshowhide == true ? '' : 'hidden'}">
						<div class="col-xs-6 col-md-6">
							<c:if test="${!isAnonymous}">
								<div class="checkbox-container hidden-sm hidden-xs">
									<input type="checkbox" class="custom-checkbox" />
									<span class="checkbox-label">Select</span>
								</div>
							</c:if>
						</div>
						<div class="col-xs-6 col-md-6 plp-wish-list-container wishlistAddProLink-wrapper signInOverlay">
							<productItem:addToSaveList product="${variantProduct}" wishlist="${allWishlist}" wishlistName="${wishlistName}" />
						</div>
					</div>
				<c:set var="variantshowhide" value="false" />
	        	</c:forEach>
			</c:if>
		</div>	
	</c:if>
	<c:if test="${(!product.multidimensional || product.variantCount == 1)}">
		<div class="row compare-and-atl flex-center-md ${product.sellableUomsCount >= 1 ?'multiplpuomatlist_' : ''}${product.code}">
			<div class="col-xs-6 col-md-6">
				<c:if test="${!isAnonymous}">
					<div class="checkbox-container hidden-sm hidden-xs">
						<input type="checkbox" class="custom-checkbox" />
						<span class="checkbox-label">Select</span>
					</div>
				</c:if>
			</div>
			<div class="col-xs-6 col-md-6 plp-wish-list-container wishlistAddProLink-wrapper signInOverlay">
				<productItem:addToSaveList product="${product}" wishlist="${allWishlist}" wishlistName="${wishlistName}"/>
			</div>
		</div>
	</c:if>
	<div class="row p-t-15-xs">
		<c:set var="isProductSellableclipcoupon" value="${product.nearestStore.storeId eq sessionStore.storeId}" />
		<div class="col-md-12 col-xs-5">
			<a class="thumb" href="${productUrl}" title="${fn:escapeXml(product.name)}">
				<product:productPrimaryImage product="${product}" format="product"/>
				<c:if test="${(!product.multidimensional || product.variantCount ge 1) && not empty product.productPromotion && (not empty product.price && product.price.value ne '0.0')}">
					<div class="on-sale-tag"><span class="glyphicon glyphicon-usd glyphicon-border"></span>On Sale</div></c:if>
				<c:if test="${product.isRegulateditem}">
					<div class="regulated-tag" style="bottom: 2px;" title="Restrictions on sale or use may apply to this item"><spring:theme code="productListerGridItem.regulated" /></div></c:if>
			</a>
			<c:if test="${product.couponEnabled eq true}">
             	<div class="clipCouponplp"><common:clipCouponPLP></common:clipCouponPLP></div>
         	</c:if>
		</div>
		<input type="hidden" value="${product.itemNumber}" id="productitemNumber" />
		<div class="col-md-12 col-xs-7 item-number-section">
			<span class="item-number-format"><c:out value="${product.itemNumber}" /></span>
			<span class="divider">|</span>
			<a href="${productUrl}"><span class="option-count">${optionsCount} Option${optionsCount gt 1 ? 's' : ''}</span></a>
			<ycommerce:testId code="product_productName">
				<div class="productName-wrapper">
					<input type="hidden" value="${product.code}" class="productCodeValue" />
					<spring:url value="/p/showNearbyOverlay/?code=" var="nearbyOverlayUrl" htmlEscape="false" />
					<a class="name linktracking-product" href="${productUrl}">
						<span>
							<c:choose>
								<c:when test="${fn:length(product.productShortDesc) > 85}">
									<c:out value="${fn:substring(product.productShortDesc, 0, 85)}..." />
								</c:when>
								<c:otherwise>
									<c:out value="${product.productShortDesc}" />
								</c:otherwise>
							</c:choose>
						</span>
					</a>
				</div>
			</ycommerce:testId>
		</div>
		<div class="col-xs-12 hidden-md hidden-lg text-align-right mob-addtolist-align">
			<span class="plpAddtoListPopupMobileLink plpAddToListMobile-${product.code} removesignInlink signInOverlay" onclick="ACC.productDetail.plpMobileAddList(${product.code})">Add to List</span>		
		</div>
		<div class="cl"></div>
		<div class="addtolistBorderLine hidden-md hidden-lg"></div>
	</div>
<c:choose>	
	<c:when test="${product.multidimensional}">
		<input type="hidden" class="variant-codes-${product.code}" value="${product.variantCodes}" />
		<input type="hidden" class="variant-codes" value="${product.variantCodes}" />
		<input type="hidden" class="product-data" value="${product}" />
		<input type="hidden" class="product-data-variant-count-${product.code}" value="${product.variantCount}" />
		<input type="hidden" class="product-data-variant-redirect-${product.code}" value="${productUrl}" />
	<div id="cardview-variants-${product.code}">
		<c:set var="variantdataavailability" value="false" />
		<c:forEach items="${baseVariantMap[product.code]}" var="variantProduct" varStatus="loop" begin="0" end="0">
            <c:set var="variantdataavailability" value="true" />
        </c:forEach>
		<c:if test="${variantdataavailability == 'true'}">
			<productItemVariant:productCardViewVariant product="${product}" />
		</c:if>
		<c:if test="${variantdataavailability == 'false' && product.variantCount eq 1}">
			<productItemVariant:productCardViewVariant product="${product}" />
		</c:if>
	</div>	
	</c:when>
	<c:otherwise>
	<div class="cardstocksection">
		<input type="hidden" name="plp-item-code" class="plp-item-code" value="${product.code}" />
		<input type="hidden" name="plp-item-number" class="plp-item-number" value="${product.itemNumber}" />
		<input type="hidden" name="productInventoryUOMID" class="productInventoryUOMID_${product.code}" value="${product.sellableUoms[0].inventoryUOMID}" />
		<productItem:productCardViewStockData product="${product}" isProductSellable="${isProductSellable}" sessionStoreDisplayName="${sessionStoreDisplayName}" isBackorderAndShippable="${isBackorderAndShippable}" 
						isMyStoreProduct="${isMyStoreProduct}" onlyHubStoreAvailability="${onlyHubStoreAvailability}" isHomeOrNearbyStockAvailable="${isHomeOrNearbyStockAvailable}" hardscapeMoreOnWayMsg="${hardscapeMoreOnWayMsg}"/>
	</div>
<c:set var="moreontheway" value="false"/>
<c:set var="backorder" value="false"/>
<c:if test="${!isHomeOrNearbyStockAvailable && (product.isSellableInventoryHit eq true) && !onlyHubStoreAvailability}">
		<c:choose>
			<c:when test="${isMixedCartEnabled ne true and product.isForceInStock eq true}">		
				<c:set var="moreontheway" value="true"/>		
			</c:when>
			<c:when test="${isMixedCartEnabled ne true}">
				<c:choose>
						<c:when test="${hardscapeMoreOnWayMsg eq true}">
							<c:set var="moreontheway" value="true"/>	
						</c:when>
						<c:otherwise>
							<c:set var="backorder" value="true"/>	
						</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
						<c:set var="backorder" value="true"/>
			</c:otherwise>
		</c:choose>
</c:if>
<input id="moreontheway" type="hidden" value='${moreontheway}'>
<input id="backorder" type="hidden" value='${backorder}'>
<input id="isBackorderAndShippable" type="hidden" value='${isBackorderAndShippable}'>
	<div id="plp-commonerror-${product.code}" class="plp-commonerror-section hidden">
		<common:plpwarning_icon /><span class="plp-commonerror"><spring:theme code="text.valid.quantity" /></span>
	</div>
	<div id="plp-commonshippingonlyqtyerror-${product.code}" class="plp-commonshippingonlyqtyerror hidden">
		<common:plpwarning_icon /><span class="plp-commonerror"><spring:theme code="text.valid.quantity" /></span>
	</div>
	<div class="plp-warning_info_${product.code} plp-commonerror-section hidden">
		<common:plpwarning_icon /><span class="plp-commonerror"><spring:theme code="text.plp.expect.delay.for.fullorder" /></span>
	</div>
	
	<div class="row plpcard-price-section ${product.sellableUomsCount >= 1 ?'multipleuom-price-update_' : ''}${product.code} ">
		<div class="${strikeListPrice ? 'strike-list-price ' : ''}details col-xs-12 col-md-12 ">
			<c:if test="${not empty product.potentialPromotions}">
				<div class="promo ${hideHardscapeplp}"> 
					<c:forEach items="${product.potentialPromotions}" var="promotion">
						${promotion.description}
					</c:forEach>
				</div>
			</c:if>
			<c:if test="${!product.multidimensional || product.variantCount ge 1}">
				<sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')">
					<div class="row flex-center ${hideHardscapeplp}">
						<!-- donemno  -->
						<div class="col-xs-6 flex-half-width logged-in-your-price-section ${hardscapeMoreOnWayMsg? ' hidden ': ''}">
							<c:choose>
								<c:when test="${(product.variantCount ge 1 && (product.inventoryFlag ne true || (!(empty product.inventoryFlag)))) || (inventoryFlag ne true) }">
									<c:choose>
										<c:when test="${hideCSP ne true}">
											<product:productNonAnonymousCSP product="${product}" isMyStoreProduct="${isMyStoreProduct}" displayFlag="false" />
											<c:choose>
												<c:when test="${product.variantCount ge 1}">
													<!-- <div id="cspEnabled${product.code}" style="display: none">
													</div> -->
													<c:set var="callForPricingFlag" value="UPDATE" />
												</c:when>
												<c:otherwise>
													<c:if test="${(empty product.customerPrice || product.customerPrice.value eq '0.0')}">
														<c:set var="callForPricingFlag" value="SET" />
													</c:if>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:set var="callForPricingFlag" value="SET" />
											<!-- <p class="callBranchForPrice">
												<spring:theme code="text.product.callbranchforpricing" />
											</p> -->
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${empty product.inventoryFlag && hideCSP ne true && hideList ne true}">
											<product:productNonAnonymousCSP product="${product}" isMyStoreProduct="${isMyStoreProduct}" displayFlag="false" />
											<c:choose>
												<c:when test="${product.variantCount ge 1}">
													<!-- <div id="cspEnabled${product.code}" style="display: none">
													</div> -->
													<c:set var="callForPricingFlag" value="UPDATE" />
												</c:when>
												<c:otherwise>
													<c:if test="${(empty product.customerPrice || product.customerPrice.value eq '0.0')}">
														<c:set var="callForPricingFlag" value="SET" />
													</c:if>
												</c:otherwise>
											</c:choose>
											<ycommerce:testId code="product_productPrice">
												<span id="promotionlabel_${loop}"></span>
											</ycommerce:testId>
										</c:when>
										<c:otherwise>
											<c:set var="callForPricingFlag" value="SET" />
											<!-- <p class="callBranchForPrice">
												<spring:theme code="text.product.callbranchforpricing" />
											</p> -->
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</div>
						<ycommerce:testId code="product_productPrice">
							<div class="col-xs-6 flex-half-width text-align-right logged-in-retail-price-section  ${hardscapeMoreOnWayMsg? ' hidden ': ''}"><!-- doneabc -->
								<!-- <c:if test="${(not empty product.price && product.price.value ne '0.0')|| not empty product.priceRange.minPrice}">
									<c:if test="${product.inventoryFlag ne true}">
										<c:if test="${hideList ne true}">
											<c:if test="${product.price.value gt product.customerPrice.value}">
												<h5 class="fontBold ${product.multidimensional && product.variantCount ge 1 && isRetailBranchPrice && !isAnonymous ? 'hide' : ''}">
													<spring:theme code="text.product.siteOnelistprice.PLP"/>
												</h5>
											</c:if>
										</c:if>
									</c:if>
								</c:if> -->
								<c:if test="${(not empty product.price && product.price.value ne '0.0') || not empty product.priceRange.minPrice}">
									<div class="productPriceWrapper">
										<span class="price-container h5">
											<span class="black-title">
												<span id="salePrice${loop}" class="discount sales-price"></span>
											</span>
											<span id="basePrice${loop}">
												<c:choose>
													<c:when test="${product.inventoryFlag eq true && product.isSellableInventoryHit ne true} ">
														<c:set var="callForPricingFlag" value="SET" />	
													<!-- <p class="callBranchForPrice">
															<spring:theme code="text.product.callbranchforpricing" />
														</p> -->
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${hideList ne true && product.inventoryFlag ne true}">
																<c:if test="${product.price.value gt product.customerPrice.value}">
																	<product:productListerItemPricePLP product="${product}" />
																</c:if>
															</c:when>
															<c:otherwise>
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											</span>
										</span>
									</div>
								</c:if>
								<div class="csperror display-none" id="cspError${loop}"></div>
							</div>
						</ycommerce:testId>
						<c:if test="${(!product.multidimensional || product.variantCount ge 1) && not empty product.productPromotion}">
						<div class="product-promotion-wrapper col-xs-12">
								<input id="productPromotion${loop}" type="hidden" data-price="${product.price.value}"
									value="${product.productPromotion}">
						</div>
					</c:if>
						<c:if test="${product.variantOptions ne null}">
							<div class="variant-option-wrapper col-xs-12">
								<c:if test="${(product.sellableUomsCount >=1 && sellableUomListLen!=1) && (not empty product.price || not empty product.priceRange.minPrice) && (product.inventoryFlag eq false) && (hideList ne true)}">
									<p class="infoTextForMultipleProducts">Multiple sizes available</p>
								</c:if>
								<c:forEach var="variantOption" items="${product.variantOptions}">
									<c:forEach items="${variantOption.variantOptionQualifiers}" var="variantOptionQualifier">
										<c:if test="${variantOptionQualifier.qualifier eq 'rollupProperty'}">
											<c:set var="rollupProperty" value="${variantOptionQualifier.value}"/>
										</c:if>
										<c:if test="${variantOptionQualifier.qualifier eq 'thumbnail'}">
											<c:set var="imageUrl" value="${variantOptionQualifier.value}"/>
										</c:if>
										<c:if test="${variantOptionQualifier.qualifier eq rollupProperty}">
											<c:set var="variantName" value="${variantOptionQualifier.value}"/>
										</c:if>
									</c:forEach>
									<img style="width: 32px; height: 32px;" src="${imageUrl}" title="${variantName}" alt="${variantName}"/>
								</c:forEach>
								<div class="cl"></div>
							</div>
						</c:if>
						<div class="cl"></div>
					</div>
				</sec:authorize>
			</c:if>
			<c:if test="${!product.multidimensional || product.variantCount ge 1}">
				<sec:authorize access="hasAnyRole('ROLE_ANONYMOUS')">
					<c:choose>
						<c:when test="${null != cookie['j_username'] && cookie['j_username'].value != ''}"> <%--softlogin new code to show price for session timeout--%>
								<!-- donemno  -->
								<div class="row ${hideHardscapeplp}">
									<c:if test="${product.inventoryFlag ne true }">
										<c:choose>
											<c:when test="${hideCSP ne true}">
												<c:choose>
													<c:when test="${product.variantCount gt 1}">
														<div class="customerSpecificPrice-wrapper col-xs-12">
															<a href="#" onclick="return false;" id="customerSpecificPriceLink${product.code}"
																class="customerSpecificPriceLink" data-index="${loop}"
																data-productcode="${product.code}"
																data-is-sellable="${product.isSellableInventoryHit}"
																data-is-regulateditem="${product.isRegulateditem}"
																data-is-orderingaccount="${isOrderingAccount}"
																data-is-storeproduct="${isMyStoreProduct}"
																data-is-productsellable="${isProductSellable}"
																data-is-licensed="${sessionStore.isLicensed}">
																<spring:theme code="text.product.listing.Price" />
															</a>
															<input type="hidden" id="customerSpecificPriceValue${loop}" />
															<div class="check_price" id="customerSpecificPriceSection${product.code}"
																style="display: none">
																<h5 class="fontBold">
																	<spring:theme code="text.product.your.price.PLP" />
																</h5>
																<div class="cl"></div>
																<span id="cspSalePrice${loop}" class="discount sales-price"></span>
																<c:choose>
																	<c:when test="${(not empty product.sellableUomsCount)}">
																		<span id="customerSpecificPrice${product.code}"></span><sup> /
																		${product.sellableUomDesc}</sup>
																	</c:when>
																	<c:otherwise>
																		<span id="customerSpecificPrice${product.code}"></span>
																	</c:otherwise>
																</c:choose>
																<div class="cl"></div>
																<span id="description${loop}"></span>
															</div>
														</div>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when
																test="${(not empty product.customerPrice && product.customerPrice.value ne '0.0')}">
																<div id="mycspDiv${product.code}">
																	<input type="hidden" value="${product.code}" class="productcspCode" />
																	<input type="hidden" value="${product.isStockAvailable}" class="isStockAvailablecsp${product.code}" />
																	<input type="hidden" value="${product.price.value}" class="productRetailPrice${product.code}" />
																	<input type="hidden" value="${product.customerPrice.value}" class="csp${product.code}" />
																	<input type="hidden" value="${product.isRegulateditem}" class="isRegulateditemcsp${product.code}" />
																	<input type="hidden" value="${isMyStoreProduct}" class="isMyStoreProduct${product.code}" />
																	<input type="hidden" value="${sessionStore.isLicensed}" class="isLicensed${product.code}" />
																	<input type="hidden" value="${isOrderingAccount}" class="isOrderingAccount${product.code}" />
																	<input type="hidden" value="${product.isSellableInventoryHit}" class="isSellableInventoryHit${product.code}" />
																	<div class="customerSpecificPrice-wrapper col-xs-12">
																		<div class="check_price">
																			<h5 class="fontBold">
																				<spring:theme code="text.product.your.price.PLP" />
																			</h5>
																			<div class="cl"></div>
																			<product:ProductCSPListerItemsPLP product="${product}"/>
																			<div class="cl"></div>
																			<span id="description${loop}"></span>
																		</div>
																	</div>
																</div>
															</c:when>
															<c:otherwise>
																<c:set var="callForPricingFlag" value="SET" />
																<!-- <p class="callBranchForPrice">
																	<spring:theme code="text.product.callbranchforpricing" />
																</p> -->
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<c:set var="callForPricingFlag" value="SET" />
												<!-- <p class="callBranchForPrice">
													<spring:theme code="text.product.callbranchforpricing" />
												</p> -->
											</c:otherwise>
										</c:choose>
									</c:if>
								</div>
						</c:when>
						<c:otherwise> <%--softlogin new code anonymous--%>
						<input class="isretailbranchpricelabel" type="hidden" value='${!product.isStockAvailable && !(!isHomeOrNearbyStockAvailable && (product.isSellableInventoryHit eq true) && !onlyHubStoreAvailability)}'>
						<c:set var="isRetailPriceforNotAvalProd" value="${!product.isStockAvailable && !(!isHomeOrNearbyStockAvailable && (product.isSellableInventoryHit eq true) && !onlyHubStoreAvailability)}"/>
							<div class="row">
								<div class="col-md-12 col-sm-12 col-xs-12 flex-center">
									<div class="plp-full-flex ${hideHardscapeplp}">
										<ycommerce:testId code="product_productPrice">
												<c:choose>
													<c:when test="${product.multidimensional && product.variantCount ge 1 && isRetailBranchPrice}">
														<c:if test="${!(!product.isStockAvailable && !(!isHomeOrNearbyStockAvailable && (product.isSellableInventoryHit eq true) && !onlyHubStoreAvailability))}">
														<c:if test="${hideList ne true}">
															<div class="retailPrice-wrapper">
																<a href="#" onclick="return false;" id="retailPriceLink${product.code}" class="retailPriceLink" data-index="${loop}"
																	data-productcode="${product.code}" data-is-sellable="${product.isSellableInventoryHit}"
																	data-is-regulateditem="${product.isRegulateditem}" data-is-orderingaccount="${isOrderingAccount}"
																	data-is-storeproduct="${isMyStoreProduct}" data-is-productsellable="${isProductSellable}"
																	data-is-licensed="${sessionStore.isLicensed}">
																	<spring:theme code="text.product.listing.retail.price" />
																</a>
																<input type="hidden" id="retailPriceValue${loop}" />
																<!-- <div id="retailPriceEnabled${product.code}" style="display: none">
																		<p class="callBranchForPrice">
																			<spring:theme code="text.product.callbranchforpricing" />
																		</p>
																</div> -->
																<c:set var="callForPricingFlag" value="UPDATE" />
																<div class="check_price" id="retailPriceSection${product.code}" style="display: none">
																	<h5 class="fontBold">
																		<spring:theme code="text.product.siteOnelistprice.PLP" />
																	</h5>
																	<div class="cl"></div>
																	<span id="retailPriceSalePrice${loop}" class="discount sales-price"></span>
																	<c:choose>
																		<c:when test="${(not empty product.sellableUomsCount)}">
																			<span id="retailPrice${product.code}"></span> / ${product.sellableUomDesc}
																		</c:when>
																		<c:otherwise>
																			<span id="retailPrice${product.code}"></span>
																		</c:otherwise>
																	</c:choose>
																	<div class="cl"></div>
																	<span id="description${loop}"></span>
																</div>
															</div>
														</c:if>
														</c:if>
													</c:when>
													<c:otherwise>
														<div class="retail-price-anonymous ${(((not empty product.price && product.price.value ne '0.0')|| not empty product.priceRange.minPrice)  and ((hideList ne true )||(hideList eq true and product.inventoryFlag eq true)))?'':'hide'}">
															<c:if test="${(not empty product.price && product.price.value ne '0.0') || not empty product.priceRange.minPrice}">
																<c:choose>
																	<c:when test="${product.inventoryFlag eq true}">
																		<div class="productPriceWrapper">
																			<span class="price-container h5">
																				<span class="black-title">
																					<span id="salePrice${loop}" class="discount sales-price"></span>
																				</span>
																				<!-- <span id="basePrice${loop}">
																						 <p class="callBranchForPrice">
																							<spring:theme code="text.product.callbranchforpricing" />
																						</p>
																					</span> -->
																				<c:set var="callForPricingFlag" value="BASEPRICE_UPDATE" />
																			</span>
																		</div>
																	</c:when>
																	<c:otherwise>
																		<c:if test="${hideList ne true}">
																			<span class="plp-price-total-title h5">
																				<spring:theme code="text.product.siteOnelistprice.PLP" />
																			</span>
																		</c:if>
																		<div class="productPriceWrapper">
																			<span class="price-container h5">
																				<span class="black-title">
																					<span id="salePrice${loop}" class="discount sales-price"></span>
																				</span>
																				<span id="basePrice${loop}">
																					<c:if test="${hideList ne true}">
																						<product:productListerItemPricePLP product="${product}" />
																					</c:if>
																				</span>
																			</span>
																		</div>
																	</c:otherwise>
																</c:choose>
															</c:if>
															<div class="csperror display-none" id="cspError${loop}"></div>
														</div>
													</c:otherwise>
												</c:choose>
										</ycommerce:testId>
										<div class="${(((not empty product.price && product.price.value ne '0.0')|| not empty product.priceRange.minPrice) || (isRetailBranchPrice eq true && !isRetailPriceforNotAvalProd)) and (hideList ne true) ? '':'login-left'} login-price-plp">
											<c:if test="${product.inventoryFlag ne true }">
												<c:choose>
													<c:when test="${hideCSP ne true}">
														<a href="<c:url value=" /login" />" class="logInToSeeYourPrice signInOverlay">
														<spring:theme code="text.product.logInToSeeYourPrice" /></a>
													</c:when>
													<c:otherwise>
														<c:set var="callForPricingFlag" value="SET" />
														<!-- <p class="callBranchForPrice">
															<spring:theme code="text.product.callbranchforpricing" />
														</p> -->
													</c:otherwise>
												</c:choose>
											</c:if>
										</div>
									</div>
								</div>
							</div>
							<div class="product-promotion-wrapper">
								<c:if test="${(!product.multidimensional || product.variantCount ge 1) && not empty product.productPromotion}">
									<input id="productPromotion${loop}" type="hidden" data-price="${product.price.value}" value="${product.productPromotion}">
								</c:if>
							</div>
							<c:if test="${product.variantOptions ne null}">
								<div class="variant-option-wrapper">
									<c:if test="${(product.sellableUomsCount >=1 && sellableUomListLen!=1) && (not empty product.price || not empty product.priceRange.minPrice) && (product.inventoryFlag eq false) && (hideList ne true)}">
										<p class="infoTextForMultipleProducts">Multiple sizes available</p>
									</c:if>
									<c:forEach var="variantOption" items="${product.variantOptions}">
										<c:forEach items="${variantOption.variantOptionQualifiers}" var="variantOptionQualifier">
											<c:if test="${variantOptionQualifier.qualifier eq 'rollupProperty'}">
												<c:set var="rollupProperty" value="${variantOptionQualifier.value}"/>
											</c:if>
											<c:if test="${variantOptionQualifier.qualifier eq 'thumbnail'}">
												<c:set var="imageUrl" value="${variantOptionQualifier.value}"/>
											</c:if>
											<c:if test="${variantOptionQualifier.qualifier eq rollupProperty}">
												<c:set var="variantName" value="${variantOptionQualifier.value}"/>
											</c:if>
										</c:forEach>
										<img style="width: 32px; height: 32px;" src="${imageUrl}" title="${variantName}" alt="${variantName}"/>
									</c:forEach>
								</div>
							</c:if>
						</c:otherwise> <%--//anonymous no price--%>
					</c:choose><%--/anonymous no price--%>
				</sec:authorize>
			</c:if>
		</div>
	</div>
<div class="row">
<c:set var="nonTransferrable" value="false" />
<c:if test="${product.isTransferrable eq false && isHomeOrNearbyStockAvailable eq true && product.nearestStore.storeId ne sessionStore.storeId}">
	<c:set var="nonTransferrable" value="true" />
</c:if>
<c:forEach items="${product.stores}" var="store">
	<c:if test="${(isMyStoreProduct eq false && (product.isShippable && !empty sessionStore.hubStores && store eq sessionStore.hubStores[0])) || (product.isShippable && !empty sessionStore.hubStores && store eq sessionStore.hubStores[0])}">
		<c:set var="nonTransferrable" value="false" />
	</c:if>
</c:forEach>
</div>


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
		<c:if test="${!product.isRegulateditem   && !product.isProductDiscontinued}">
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
 <!-- Is not in stock variable end -->
<!--Multiple UOM PLP section Start-->
<input type="hidden" value="${!isAnonymous}" class="plp-login-muom" />
<c:set var="plploginmuom" value="${!isAnonymous}" />
<c:set var="mulUomPlpCnt" value="0" />
<c:forEach items="${product.sellableUoms}" var="sellableUom">
<c:set var="mulUomPlpCnt" value="${mulUomPlpCnt+1}" />
</c:forEach>

<c:set var="mulUomPlp" value="false" />
<c:if test="${not empty product.sellableUoms && hideuomSelect ne true}">
       <c:set var="mulUomPlp" value="true" />
</c:if>
<c:set var="hideuomSelect" value="false" />
<c:if test="${product.hideUom eq true}">
       <c:set var="hideuomSelect" value="true" />
</c:if>
<c:set var="hidelistCheck" value="false" />
<c:if test="${isAnonymous and hideList}">
	<c:set var="hidelistCheck" value="true" />
</c:if>
<input type="hidden" class="mulUomPlpCnt-plpr" value="${mulUomPlpCnt}" />
<input type="hidden" class="mulUomPlp-plpr" value="${mulUomPlp}" />
<input type="hidden" class="hideuomSelect-plpr" value="${hideuomSelect}" />
<input type="hidden" class="hidelistCheck-plpr" value="${hidelistCheck}" />
<input type="hidden" class="isNotInStock-plpr" value="${isNotInStock}" />
<input type="hidden" class="isAnonymous-plpr" value="${isAnonymous}" />
<input type="hidden" class="hideCSP-plpr" value="${hideCSP}" />

<c:if test="${mulUomPlp && mulUomPlpCnt > 1 && !isNotInStock && !hidelistCheck && (isAnonymous || (!isAnonymous && !hideCSP))}">
<c:forEach items="${product.sellableUoms}" var="sellableUom" begin="0" end="0">
	<c:set var="MulUomValue" value="${sellableUom.inventoryUOMDesc}" />
	<c:set var="inventoryMul">&nbsp;(<fmt:formatNumber value="${sellableUom.inventoryMultiplier}" maxFractionDigits="0"/>)</c:set>
							
</c:forEach>

<div class="row flex-center multipleuom-plp multipleuom-plp_${product.code} m-b-5">
	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="btn-group col-md-12 col-sm-12 col-xs-12 padding0 uom-wrapper-plp" role="group" aria-label="...">
			<button type="button" id="js-btn-muom" class="col-md-3 col-sm-3 col-xs-3 js-plp-uom-btn  js-btn-muom btn btn-dark-gray flex-center font-Arial transition-3s uom-mesg-wrapper" onclick="ACC.global.toggleOffElemsplp(this, '.popup-box-muom' , 'active','${product.code}')">
				UOM
			</button>
			<button type="button" id="js-btn-muom" class="col-md-9 col-sm-9 col-xs-9 js-plp-uom-btn  js-btn-muom btn btn-dark-gray flex-center font-Arial transition-3s uom-text-wrapper custom-dropdown-button" onclick="ACC.global.toggleOffElemsplp(this, '.popup-box-muom' , 'active','${product.code}')">
				<span title="${MulUomValue} ${inventoryMul}">${MulUomValue} ${inventoryMul}</span>
			</button>
			<div class="row f-s-15 font-Arial popup-box-muom popup-box-muom_${product.code} ">
				<div class="col-md-12 col-sm-12 col-xs-12 padding0">
						<c:set var="baseUOM" value="true" />
						<c:forEach items="${product.sellableUoms}" var="sellableUom" begin="0" end="0">
							<c:set var="plpselectedUOM" value="${sellableUom.inventoryUOMID}" />
						</c:forEach>
						<input type="hidden" value="${plpselectedUOM}" id="plpselectedUOM_${product.code}" />
						<c:forEach items="${product.sellableUoms}" var="sellableUom1">
							<c:set var="inventoryMul1">&nbsp;(<fmt:formatNumber value="${sellableUom1.inventoryMultiplier}" maxFractionDigits="0"/>)</c:set>
							<div onclick="ACC.global.multipleUomTotalPriceUpdaterPLP(this,'${product.code}','${plploginmuom}','${sellableUom1.inventoryUOMDesc}','cardView')"
							    data-productcode="${product.code}"
								data-uom="${sellableUom1.inventoryUOMDesc}${inventoryMul1}" 
								data-inventoryid="${sellableUom1.inventoryUOMID}"
								data-inventorymultiplier="${sellableUom1.inventoryMultiplier}"
								data-qty="1"  data-baseuom="${baseUOM}" data-desccode="${sellableUom1.inventoryUOMDesc}"
								data-stockdata="${product.nearestStoreStockLevel}"  
								class="list-group-item flex-center transition-3s uom-dropdown-option qtyprice-${sellableUom1.inventoryUOMID}-${product.code}">
							<div class="icon-box m-r-10"></div>${sellableUom1.inventoryUOMDesc}${inventoryMul1}
						</div>
						<c:set var="baseUOM" value="false" />
						</c:forEach>	
				</div>
			</div>
		</div>
	</div>
</div>
</c:if>
<!--Multiple UOM PLP section End-->
<input type="hidden" class="hardscapeMoreOnWayMsg-plpr" value="${hardscapeMoreOnWayMsg}" />
<c:set var="plpRequestQuote" value="${isNotInStock && quotesFeatureSwitch && !product.multidimensional  && !backorder && !moreontheway}" />
<input type="hidden" value="${isNotInStock && quotesFeatureSwitch && !product.multidimensional  && !backorder && !moreontheway}" class="plpRequestQuote" />
<input type="hidden" value="${callForPricingFlag}" class="callForPricingFlag" />
<!--Regulated Product - Stock Message - Flags - Start-->
<c:if test="${(not product.multidimensional || product.variantCount == 1)}">
<input type="hidden" value="${product.isRegulateditem}" id="regulated${product.code}" />
<c:if test="${product.isRegulateditem}">
			<c:set var="addIsProductSellable" value="false" />
	        <c:if test="${product.isRegulateditem}">
	            <c:forEach items="${product.regulatoryStates}" var="regulatoryStates">
	                <c:if test="${regulatoryStates eq sessionStore.address.region.isocodeShort}">
	                    <c:set var="addIsProductSellable" value="true" />
	                </c:if>
	            </c:forEach>
	        </c:if>
	        <c:set var="addIsMyStoreProduct" value="false" />
	        <c:forEach items="${product.stores}" var="store">
	            <c:if test="${store eq product.nearestStore.storeId}">
	                <c:set var="addIsMyStoreProduct" value="true" />
	            </c:if>
	        </c:forEach>
	        <c:if test="${product.isSellableInventoryHit eq true}">
	            <c:set var="addIsMyStoreProduct" value="true" />
	        </c:if>
	        <c:forEach items="${product.stores}" var="store">
	            <c:if
	                test="${addIsMyStoreProduct eq false && (product.isShippable && !empty sessionStore.hubStores && store eq sessionStore.hubStores[0])}">
	                <c:set var="addIsMyStoreProduct" value="true" />
	            </c:if>
	        </c:forEach>
            <input type="hidden" value="${addIsProductSellable}" id="isProductSellable${product.code}" />
            <input type="hidden" value="${sessionStore.isLicensed}" id="isLicensed${product.code}" />
            <input type="hidden" value="${addIsMyStoreProduct}" id="isMyStoreProduct${product.code}" />
            <input type="hidden" id="isRup${product.code}" />
</c:if>
</c:if>
<!--End-->
<c:choose>
	<c:when test="${isAnonymous && isNotInStock && (!product.multidimensional) && !backorder && !moreontheway}">
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12">
				<p class="loginToRequestAQuote">
					<a href="<c:url value=" /login" />" class="signInOverlay">
					<spring:theme code="plp.login.to.requestAQuote" />
					</a>
				</p>
			</div>
		</div>
	</c:when>
	<c:when test="${callForPricingFlag eq 'SET' && !plpRequestQuote}">
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<p class="callBranchForPrice">
					<spring:theme code="text.product.callbranchforpricing" />
				</p>
			</div>
		</div>
	</c:when>
	<c:when test="${callForPricingFlag eq 'BASEPRICE_UPDATE' && !plpRequestQuote}">
		<div class="row" id="basePrice${loop}">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<p class="callBranchForPrice">
					<spring:theme code="text.product.callbranchforpricing" />
				</p>
			</div>
		</div>
	</c:when>
	<c:when test="${callForPricingFlag eq 'UPDATE' && !plpRequestQuote}">
			<div class="row" id="callForPriceEnabled${product.code}" style="display: none;">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<p class="callBranchForPrice">
						<spring:theme code="text.product.callbranchforpricing" />
					</p>
				</div>
			</div>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${isNotInStock && quotesFeatureSwitch && !product.multidimensional  && !backorder && !moreontheway}">
				<div class="row">
					<div class="col-md-12 col-xs-12 m-t-5 m-t-5-xs p-b-5">
						<button class="col-md-12 col-xs-12 btn btn-primary requestQuoteBtn"
						data-product-description="${fn:escapeXml(product.productShortDesc)}"
						onclick="ACC.savedlist.requestQuotePopupplp(this,'product-item','${product.code}');ACC.adobelinktracking.requestQuotePLP(this,'','','${cmsPage.name}','true')">
						<spring:theme code="request.quote.popup.request.text" />
						</button>
					</div>
				</div>
			</c:when>
			<c:when test="${hardscapeMoreOnWayMsg}">
				<div class="row">
					<div class="col-md-12 col-xs-12 m-t-5 m-t-5-xs p-b-5">
						<button class="col-md-12 col-xs-12 btn btn-primary requestQuoteBtn"
						data-product-description="${fn:escapeXml(product.productShortDesc)}"
						onclick="ACC.savedlist.requestQuotePopupplp(this,'product-item','${product.code}');ACC.adobelinktracking.requestQuotePLP(this,'','','${cmsPage.name}','true')">
							<spring:theme code="request.quote.popup.request.text" />
						</button>
					</div>
				</div>
			</c:when>
			<c:when test="${nonTransferrable eq true && !(product.variantCount > 1)}">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 change-branch-section-${product.code}">
						<div class="card-change-branch-section flex-center justify-center change-contact-branch-section cursor-pointer-plp ${notPurchasable? ' hide ': ' '}">
							<div onclick="ACC.mystores.changebranchPLPPopupFn(${product.code},${product.code},true);"
								class="call-branch-icon-text text-center">
								<spring:theme code="plp.changeBranch.toAddToCart" />
							</div>
						</div>
						<input type="hidden" name="getAQuoteFlagForB2BUser" class="getAQuoteFlagForB2BUser" value="${quotesFeatureSwitch and !isAnonymous}" />
						<div class="changeBranchGetAQuoteSection col-md-12 col-xs-12 padding0 hidden">
							<button class="col-md-12 col-xs-12 btn btn-primary requestQuoteBtn m-t-5"
							data-product-description="${fn:escapeXml(product.productShortDesc)}"
								onclick="ACC.savedlist.requestQuotePopupplp(this,'product-item','${product.code}');ACC.adobelinktracking.requestQuotePLP(this,'','','${cmsPage.name}','true');event.preventDefault();">
								<spring:theme code="request.quote.popup.request.text" />
							</button>
						</div>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<input type="hidden" class="qty-section-productcode" name="qty-section-productcode" value="${product.code}" />
				<c:set var="product" value="${product}" scope="request" />
				<c:set var="addToCartText" value="${addToCartText}" scope="request" />
				<c:set var="addToCartUrl" value="${addToCartUrl}" scope="request" />
				<c:set var="isGrid" value="true" scope="request" />
				<div class="addtocart plp-ordermultiplesaddtocart ${notPurchasable? ' hide ': ' '}" style="display:grid">
					<c:if test="${cmsPage.uid eq 'siteOnePromotionSearchPage'}">
						<cms:component uid="ListAddToCartAction" />
						<cms:component uid="ListOrderFormAction" />
					</c:if>
					<div class="actions-container-for-${component.uid} <c:if test=" ${ycommerce:checkIfPickupEnabledForStore()
						and product.availableForPickup}">
						pickup-in-store-available</c:if>">
						<productItem:productCardViewCart product="${product}" />
					</div>
				</div>
				<div class="cardview-errorpart-${product.code}">
					<c:if test="${productQuantity_p ne '1' and !(not product.multidimensional and (empty product.price) and hideCSP ne true)}">
						<div class="plp-message1-${product.code} plp-infoMessage ${product.orderQuantityInterval ne null && product.orderQuantityInterval ne '0' && product.orderQuantityInterval gt '0' && product.orderQuantityInterval ne '' ? '' : 'hidden'}">
							<spring:theme code="text.minimum.value" />&nbsp;${product.orderQuantityInterval}
						</div>
						<div class="plp-message2-${product.code} plp-errorMessage hidden">
							<spring:theme code="text.plp.error.message1" />&nbsp;${product.orderQuantityInterval}
						</div>
					</c:if>
					<c:if test="${product.eeee eq true and !(not product.multidimensional and (hideList eq true || empty product.price ) and hideCSP ne true)}">
						<div class="plp-message3-${product.code} plp-errorMessage hidden">
							<spring:theme code="PLP.EEE.item.branch.on.hand.message1"
								arguments="${product.nearestStoreStockLevel}" />
						</div>
					</c:if>
					<c:if test="${product.minOrderQuantity ne null && product.minOrderQuantity ne '0' && product.minOrderQuantity ne '' }">
						<div class="plp-message4-${product.code} plp-errorMessage hidden">
							<spring:theme code="text.plp.error.min.quantity.message" />&nbsp;${product.minOrderQuantity}
						</div>
						<div class="plp-message5-${product.code} plp-infoMessage">
							<spring:theme code="text.plp.error.min.quantity.message" />&nbsp;${product.minOrderQuantity}
						</div>
					</c:if>
				</div>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>
</c:otherwise>
</c:choose>	
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
<input type="hidden" class="plp-isNotInStock" value="${isNotInStock}" />
<input type="hidden" class="plp-isShippableNotInStock" value="${isShippableNotInStock}" />
<input type="hidden" class="plp-branch-pickupAvailable" value="${isBranchPickupAvailable}" />
<input type="hidden" class="plp-branch-deliveryAvailable" value="${isBranchDeliveryAvailable}" />
<input type="hidden" class="plp-branch-shippingAvailable" value="${isBranchShippingAvailable}" />
<input type="hidden" class="plp-branch-segmentLevelShippingEnabled" value="${segmentLevelShippingEnabled}" />
<input type="hidden" class="plp-fulfilment" value="${(isShippableNotInStock && ((product.variantCount eq 1) || (!product.multidimensional)))}" />

<div class="row m-t-5">
	<div class="col-xs-12 col-sm-12 col-md-12 plp-fulfillment-section ${(isShippableNotInStock && ((product.variantCount eq 1) || (!product.multidimensional))) ? 'hide':''} ${notPurchasable? ' hide ': ' '}">
		<div class="plp_icon_details no-margin flex-center row">
			<div class="col-xs-4 plp_fulfillment_item">
				<c:set var="pickUpFlag" value="${(nonTransferrable || isNotInStock || !isBranchPickupAvailable || regulatedAndNotSellableProduct)}" />
 				<c:set var="pickUpFlag1" value="${(isNotInStock || !isBranchPickupAvailable)}" />
 				<input type="hidden" class="plp-pickUpFlag" value="${pickUpFlag}" />
 				<input type="hidden" class="plp-pickUpFlag1" value="${pickUpFlag1}" />
				<common:checkIcon height="12" width="10" iconColor="${pickUpFlag ? '#CCC': '#414244'}" />
				<span class="m-l-5 m-r-5 f-s-12 f-w-400 ${pickUpFlag ? 'text-gray' : 'text-gray-1'}"><spring:theme code="text.product.fullfilment.pickup" /></span>
			</div>
			<div class="col-xs-4 plp_fulfillment_item">
				<c:set var="deliveryFlag"
             value="${(nonTransferrable || isNotInStock || !product.isDeliverable || !isBranchDeliveryAvailable || regulatedAndNotSellableProduct)}" />
             <c:set var="deliveryFlag1"
             value="${(isNotInStock || !product.isDeliverable || !isBranchDeliveryAvailable)}" />
         <input type="hidden" class="plp-deliveryFlag" value="${deliveryFlag}" />
         <input type="hidden" class="plp-deliveryFlag1" value="${deliveryFlag1}" />
				<common:checkIcon height="12" width="10" iconColor="${deliveryFlag ? '#CCC': '#414244'}" />
				<span class="m-l-5 f-s-12 f-w-400 ${deliveryFlag ? 'text-gray' : 'text-gray-1'}"><spring:theme code="text.product.fullfilment.delivery" /></span>
			</div>
			<div class="col-xs-4 plp_fulfillment_item">
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
</div>
</ycommerce:testId>
<common:nearbyOverlay/>
