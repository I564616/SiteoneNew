<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="productCode" required="true" type="java.lang.String" %>
<%@ attribute name="variantProduct" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ taglib prefix="productItem" tagdir="/WEB-INF/tags/responsive/product/cardView" %>
<%@ taglib prefix="listviewComponent" tagdir="/WEB-INF/tags/responsive/product/listView" %>
<%@ taglib prefix="listviewVariantComponent" tagdir="/WEB-INF/tags/responsive/product/listView/variants" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>


<c:if test="${(!variantProduct.multidimensional || variantProduct.variantCount ge 1) && variantProduct.isRegulateditem}">
	    <c:set var="isProductSellable" value="false" />
	    <c:forEach items="${variantProduct.regulatoryStates}" var="regulatoryStates">
		    <c:if test="${regulatoryStates eq sessionStore.address.region.isocodeShort}">
			    <c:set var="isProductSellable" value="true" />
		    </c:if>
	    </c:forEach>
    </c:if>
    <c:set var="hardscapeMoreOnWayMsg" value="false"/>
    <c:if test="${!isAnonymous and variantProduct.isEligibleForBackorder eq true and variantProduct.inventoryCheck eq true and fn:escapeXml(variantProduct.categories[1].name)== 'Manufactured Hardscape Products'}">
	    <c:set var="hardscapeMoreOnWayMsg" value="true"/>
    </c:if>
    <c:set var="sessionStoreDisplayName"  value="${not empty sessionNearbyStores[0].displayName ? sessionNearbyStores[0].displayName : sessionNearbyStores[0].name }"/>
    <c:set var="isBackorderAndShippable"  value="false" />
    <c:set var="isHomeOrNearbyStockAvailable" value="false"/>
    <c:set var="isMyStoreProduct" value="false" />
    <c:forEach items="${variantProduct.stores}" var="store">
	    <c:if test="${store eq variantProduct.nearestStore.storeId}">
		    <c:set var="isMyStoreProduct" value="true" />
		    <c:set var="isHomeOrNearbyStockAvailable" value="true"/>
	    </c:if>
    </c:forEach>
    <c:if test="${variantProduct.isSellableInventoryHit eq true}">
	    <c:set var="isMyStoreProduct" value="true" />
    </c:if>
    <c:forEach items="${variantProduct.stores}" var="store">
	    <c:if test="${(isMyStoreProduct eq false && (variantProduct.isShippable && !empty sessionStore.hubStores && store eq sessionStore.hubStores[0]))}">
		    <c:set var="isMyStoreProduct" value="true" />
		    <c:set var="onlyHubStoreAvailability"  value="true" />
	    </c:if>
	    <c:if test="${variantProduct.isSellableInventoryHit eq true && !isHomeOrNearbyStockAvailable && (variantProduct.isShippable && !empty sessionStore.hubStores && store eq sessionStore.hubStores[0])}">
		    <c:set var="isBackorderAndShippable"  value="true" />
	    </c:if>
    </c:forEach>
    <c:set var="inventoryFlag" value="${variantProduct.inventoryFlag}" />
    <c:if test="${empty variantProduct.inventoryFlag}">
	    <c:choose>
		    <c:when test="${isHomeOrNearbyStockAvailable eq true}">
			    <c:set var="inventoryFlag" value="false" />
		    </c:when>
		    <c:otherwise>
			    <c:set var="inventoryFlag" value="true" />
		    </c:otherwise>
	    </c:choose>
    </c:if>
    <c:choose>
        <c:when test="${variantProduct.stockAvailableOnlyHubStore eq true}">
            <c:set var="variantStockLevel" value="${variantProduct.hubStoreStockLevel}" />
        </c:when>
        <c:otherwise>
            <c:set var="variantStockLevel" value="${variantProduct.stock.stockLevel}" />
        </c:otherwise>
    </c:choose>
    <c:if test="${variantProduct.isForceInStock eq true}">
        <c:set var="variantStockLevel" value="9999" />
    </c:if>

    <c:choose>
        <c:when test="${variantProduct.hideList eq true}">
            <c:if test="${variantProduct.hideCSP eq true}">
                <c:set var="disableVariantatc" value="disabled" />
            </c:if>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${!isAnonymous && variantProduct.hideCSP eq true}">
                    <c:set var="disableVariantatc" value="disabled" />
                </c:when>
                <c:otherwise>
                    <c:set var="disableVariantatc" value="" />
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>
    <c:set var="nonTransferrable" value="false" />
    <input type="hidden" name="variantProduct.isTransferrable" value="${variantProduct.isTransferrable}" />
    <input type="hidden" name="isHomeOrNearbyStockAvailable" value="${isHomeOrNearbyStockAvailable}" />
    <input type="hidden" name="variantProduct.nearestStore.storeId" value="${variantProduct.nearestStore.storeId}" />
    <input type="hidden" name="sessionStore.storeId" value="${sessionStore.storeId}" />
    <input type="hidden" name="isMyStoreProduct" value="${isMyStoreProduct}" />
    <c:if test="${variantProduct.isTransferrable eq false && isHomeOrNearbyStockAvailable eq true && variantProduct.nearestStore.storeId ne sessionStore.storeId}">
        <c:set var="nonTransferrable" value="true" />
    </c:if>
    <c:forEach items="${variantProduct.stores}" var="store">
        <c:if test="${(isMyStoreProduct eq false && (variantProduct.isShippable && !empty sessionStore.hubStores && store eq sessionStore.hubStores[0])) || (variantProduct.isShippable && !empty sessionStore.hubStores && store eq sessionStore.hubStores[0])}">
            <c:set var="nonTransferrable" value="false" />
            <input type="hidden" name="nonTransferrable" value="${nonTransferrable}"/>
        </c:if>
    </c:forEach>
    <c:set var="isPriceAvailable" value="false" />

    <div class="row variant-item product-list-view" data-product-code="${productCode}" data-variant-code="${variantProduct.code}">
        <c:if test="${!isAnonymous}">
            <div class="checkbox-container">
            <input type="checkbox" class="custom-checkboxplpvariant custom-checkboxlistplp" />
            </div>
        </c:if>
        <input id="requestQuoteButtonDesc" type="hidden" value='${variantProduct.productShortDesc}'>
        <input type="hidden" name="plp-item-code" class="plp-item-code" value="${variantProduct.code}" />
        <input type="hidden" name="plp-item-number" class="plp-item-number" value="${variantProduct.itemNumber}" id="requestQuoteButtonItemnumber" />
        <input type="hidden" name="productInventoryUOMID" class="productInventoryUOMID_${variantProduct.code}" value="${variantProduct.sellableUoms[0].inventoryUOMID}" />
        <div class="variant-detail-section col-md-3 p-l-0">
            <c:set var="variantOptionsObj" value="${variantProduct.variantProductOptions}" />
            <c:if test="${not empty variantOptionsObj}">
                <c:set var="splitValues" value="${fn:split(variantOptionsObj, '|')}" />
                <div class="variant-itemSize"><span class="heading-label">${splitValues[0]}: </span>${splitValues[1]}</div>
            </c:if>
            <div class="variant-itemNumber"><span class="heading-label">Item: </span>${variantProduct.itemNumber}</div>
        </div>
        <div class="col-md-6 p-l-0">
            <div class="row flex-center">
                <div class="variant-qty-section col-md-3 p-l-0 p-r-5">
                    <listviewVariantComponent:productListViewVariantQty product="${variantProduct}" />
                </div>
                <div class="variant-pricing-section col-md-3 p-r-0 p-l-5">
                    <c:choose>
                        <c:when test="${isAnonymous}">
                            <c:choose>
                                <c:when test="${variantProduct.hideList eq true and variantProduct.hideCSP eq true}">
                                    <c:set var="callForPricingFlag" value="SET" />
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${variantProduct.hideList ne true and (variantProduct.price ne null || not empty variantProduct.priceRange.minPrice)}">
                                            <c:choose>
                                                <c:when test="${variantProduct.inventoryFlag eq true}">
                                                    <c:set var="callForPricingFlag" value="SET" />
                                                </c:when>
                                                <c:otherwise>
                                                    <product:productListerItemPricePLP product="${variantProduct}" />
                                                    <c:set var="isPriceAvailable" value="true" />
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="callForPricingFlag" value="SET" />
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${variantProduct.hideList ne true}">
                                    <c:choose>
                                        <c:when test="${variantProduct.hideCSP ne true}">
                                            <c:choose>
                                                <c:when test="${variantProduct.inventoryFlag}">
                                                    <c:set var="callForPricingFlag" value="SET" />
                                                </c:when>
                                                <c:when test="${empty variantProduct.customerPrice || variantProduct.customerPrice.value eq '' || variantProduct.customerPrice.value eq '0.0' || variantProduct.customerPrice.value == 0}">
                                                    <c:set var="callForPricingFlag" value="SET" />
                                                </c:when>
                                                <c:otherwise>
                                                    <product:ProductCSPListerItemsPLP product="${variantProduct}" />
                                                    <c:set var="isPriceAvailable" value="true" />
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="callForPricingFlag" value="SET" />
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${variantProduct.hideCSP ne true}">
                                            <c:choose>
                                                <c:when test="${variantProduct.inventoryFlag}">
                                                    <c:set var="callForPricingFlag" value="SET" />
                                                </c:when>
                                                <c:when test="${empty variantProduct.customerPrice || variantProduct.customerPrice.value eq '' || variantProduct.customerPrice.value eq '0.0' || variantProduct.customerPrice.value == 0}">
                                                    <c:set var="callForPricingFlag" value="SET" />
                                                </c:when>
                                                <c:otherwise>
                                                    <product:ProductCSPListerItemsPLP product="${variantProduct}" />
                                                    <c:set var="isPriceAvailable" value="true" />
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="callForPricingFlag" value="SET" />
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </div>
                <c:set var="variantProductnearestStoreStockLevel" value="${variantProduct.nearestStoreStockLevel}" />
                <c:if test="${not empty variantProduct.sellableUoms}">
                    <c:if test="${not empty variantProduct.sellableUoms[0].inventoryMultiplier && variantProduct.sellableUoms[0].inventoryMultiplier > 1}">
                        <c:set var="tempValue" value="${Math.floor(variantProduct.nearestStoreStockLevel/variantProduct.sellableUoms[0].inventoryMultiplier)}" />
                        <c:set var="variantProductnearestStoreStockLevel">
                            <fmt:formatNumber value="${tempValue}" type="number" maxFractionDigits="0" />
                        </c:set>
                    </c:if>
                </c:if>
                <div class="variant-stock-section col-md-6">
                    <c:set var="isNotInStock" value="false" />
                    <c:set var="hideSubmitFlag" value="false" />
                    <c:if test="${!variantProduct.multidimensional || variantProduct.variantCount ge 1}">
                        <c:if test="${!variantProduct.isProductDiscontinued}">
                            <c:choose>
                                <c:when test="${variantProduct.isStockAvailable}">
                                    <c:choose>
                                        <c:when test="${onlyHubStoreAvailability}">
                                            <c:choose>
                                                <c:when test="${variantProduct.nearestStore.storeId eq sessionStore.storeId && (variantProduct.isStockAvailable eq true) && variantProduct.isShippable}">
                                                    <c:set var="isNotInStock" value="false" />
                                                    <span id="stock13" class="stock-section-instock"
                                                        data-available-qty="${variantProductnearestStoreStockLevel}">
                                                        <span class="bg-color">${variantProductnearestStoreStockLevel}</span>
                                                        <spring:theme code="stock.section.in.stock" />
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="isNotInStock" value="true" />
                                                    <span id="stock14" class="stock-section-instock"
                                                        data-available-qty="${variantProductnearestStoreStockLevel}">
                                                        <span class="bg-color">${variantProductnearestStoreStockLevel}</span>
                                                        <spring:theme code="stock.section.in.stock" />&nbsp;
                                                        <span class="shipping-only">
                                                            <spring:theme code="stock.section.for.shipping.only" />
                                                           <input type="hidden" data-shippingonly="shippingonly" id="shipping_${variantProduct.code}">
                                                        </span>
                                                    </span>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:when test="${isBackorderAndShippable}">
                                            <span id="stock15" class="stock-section-backorder">
                                                <span class="bg-color">-</span>
                                                <spring:theme code="stock.section.backorder" />
                                            </span>
                                        </c:when>
                                        <c:when test="${variantProduct.nearestStore.storeId eq sessionStore.storeId}">
                                            <c:choose>
                                                <c:when test="${not empty isMixedCartEnabled ? isMixedCartEnabled ne true : sessionScope.isMixedCartEnabled ne true}">
                                                    <span id="stock16" class="stock-section-instock"
                                                        data-available-qty="${variantProductnearestStoreStockLevel}">
                                                        <span class="bg-color">${variantProductnearestStoreStockLevel}</span>
                                                        <spring:theme code="stock.section.in.stock" />
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${variantProduct.hideList eq true && variantProduct.hideCSP eq true}">
                                                            <span id="stock17" class="stock-section-instock"
                                                                data-available-qty="${variantProductnearestStoreStockLevel}">
                                                                <span class="bg-color">${variantProductnearestStoreStockLevel}</span>
                                                                <spring:theme code="stock.section.in.stock" />
                                                            </span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span id="stock18" class="stock-section-instock"
                                                                data-available-qty="${variantProductnearestStoreStockLevel}">
                                                                <span class="bg-color">${variantProductnearestStoreStockLevel}</span>
                                                                <spring:theme code="stock.section.in.stock" />
                                                            </span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${!onlyHubStoreAvailability && isHomeOrNearbyStockAvailable}">
                                                <c:choose>
                                                    <c:when test="${not empty isMixedCartEnabled ? isMixedCartEnabled ne true : sessionScope.isMixedCartEnabled ne true}">
                                                        <span id="stock19" class="stock-section-instock-nearby"
                                                            data-available-qty="${variantProductnearestStoreStockLevel}">
                                                            <span class="bg-color">${variantProductnearestStoreStockLevel}</span>
                                                            <span>
                                                                <span class="nearbylbl"><spring:theme code="stock.section.in.stock2" /></span><br>
                                                                <span class="branchIdCls"><a onclick="ACC.mystores.changebranchPLPPopupFn(${variantProduct.code},${productCode},true);">Branch #${variantProduct.nearestStore.storeId}</a></span>
                                                            </span>
                                                            <!--<a class="nearby" href=""><spring:theme code="stock.section.nearby" /></a>-->
                                                        </span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${variantProduct.hideList eq true && variantProduct.hideCSP eq true}">
                                                                <span id="stock20" class="stock-section-instock-nearby"
                                                                    data-available-qty="${variantProductnearestStoreStockLevel}">
                                                                    <span class="bg-color">${variantProductnearestStoreStockLevel}</span>
                                                                    <span>
                                                                        <span class="nearbylbl"><spring:theme code="stock.section.in.stock2" /></span><br>
                                                                        <span class="branchIdCls"><a onclick="ACC.mystores.changebranchPLPPopupFn(${variantProduct.code},${productCode},true);">Branch #${variantProduct.nearestStore.storeId}</a></span>
                                                                    </span>
                                                                    <!--<a class="nearby" href=""><spring:theme code="stock.section.nearby" /></a>-->
                                                                </span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span id="stock21" class="stock-section-instock-nearby"
                                                                    data-available-qty="${variantProductnearestStoreStockLevel}">
                                                                    <span class="bg-color">${variantProductnearestStoreStockLevel}</span>
                                                                    <span>
                                                                        <span class="nearbylbl"><spring:theme code="stock.section.in.stock2" /></span><br>
                                                                        <span class="branchIdCls"><a onclick="ACC.mystores.changebranchPLPPopupFn(${variantProduct.code},${productCode},true);">Branch #${variantProduct.nearestStore.storeId}</a></span>
                                                                    </span>
                                                                    <!--<a class="nearby" href=""><spring:theme code="stock.section.nearby" /></a>-->
                                                                </span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:when test="${!isHomeOrNearbyStockAvailable && (variantProduct.isSellableInventoryHit eq true) && !onlyHubStoreAvailability}">
                                    <c:choose>
                                        <c:when test="${(not empty isMixedCartEnabled ? isMixedCartEnabled ne true : sessionScope.isMixedCartEnabled ne true) and variantProduct.isForceInStock eq true}">
                                            <span id="stock22" class="stock-section-moreontheway">
                                                <span class="bg-color"><common:plpNewCheck height="13" width="13"/></span>
                                                <span class="m-l-5"><spring:theme code="stock.section.more.on.the.way" /></span>
                                                <span class="js-info-tootip blueLink" rel="custom-tooltip">
                                                    <span class="tooltip-content hide">
                                                        <spring:theme code="AvailableToOrder.msg.tooltip" />
                                                    </span>
                                                    <common:headerIcon iconName="plpinfotooltip" width="13" height="13" viewBox="0 0 13 13" />
                                                </span>
                                            </span>
                                        </c:when>
                                        <c:when test="${not empty isMixedCartEnabled ? isMixedCartEnabled ne true : sessionScope.isMixedCartEnabled ne true}">
                                            <c:choose>
                                                <c:when test="${hardscapeMoreOnWayMsg eq true}">
                                                    <span id="stock23" class="stock-section-moreontheway">
                                                        <span class="bg-color"><common:plpNewCheck height="13" width="13"/></span>
                                                        <span class="m-l-5"><spring:theme code="stock.section.more.on.the.way" /></span>
                                                        <span class="js-info-tootip blueLink" rel="custom-tooltip">
                                                            <span class="tooltip-content hide">
                                                                <spring:theme code="AvailableToOrder.msg.tooltip" />
                                                            </span>
                                                            <common:headerIcon iconName="plpinfotooltip" width="13" height="13" viewBox="0 0 13 13" />
                                                        </span>
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span id="stock24" class="stock-section-backorder">
                                                        <span class="bg-color">-</span>
                                                        <spring:theme code="stock.section.backorder" />
                                                    </span>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${hideList eq true && hideCSP eq true}">
                                                    <span id="stock25" class="stock-section-backorder">
                                                        <span class="bg-color">-</span>
                                                        <spring:theme code="stock.section.backorder" />
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span id="stock26" class="stock-section-backorder">
                                                        <span class="bg-color">-</span>
                                                        <spring:theme code="stock.section.backorder" />
                                                    </span>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:when test="${(variantProduct.isStockAvailable eq true)}">
                                    <c:choose>
                                        <c:when test="${variantProduct.nearestStore.storeId eq sessionStore.storeId}">
                                            <span id="stock27" class="stock-section-instock"
                                                data-available-qty="${variantProductnearestStoreStockLevel}">
                                                <span class="bg-color">${variantProductnearestStoreStockLevel}</span>
                                                <spring:theme code="stock.section.in.stock" />
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <span id="stock28" class="stock-section-instock-nearby"
                                                data-available-qty="${variantProductnearestStoreStockLevel}">
                                                <span class="bg-color">${variantProductnearestStoreStockLevel}</span>
                                                <span>
                                                    <span class="nearbylbl"><spring:theme code="stock.section.in.stock2" /></span><br>
                                                    <span class="branchIdCls"><a onclick="ACC.mystores.changebranchPLPPopupFn(${variantProduct.code},${productCode},true);">Branch #${variantProduct.nearestStore.storeId}</a></span>
                                                </span>
                                                <!--<a class="nearby" href=""><spring:theme code="stock.section.nearby" /></a>-->
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="isNotInStock" value="true" />
                                    <c:choose>
                                        <c:when test="${variantProduct.isHardscapeProduct eq true}">
                                            <c:set var="hideSubmitFlag" value="true" />
                                            <div id="stock29" class="stock-section-notavailableonline-listview">
													<span class="text-default">
														<spring:theme code="stock.section.not.available" /></br>
														<span class="check-branch" data-value="${variantProduct.code}" onclick="ACC.mystores.changebranchPLPPopupFn(${variantProduct.code},${productCode},false);"><spring:theme code="stock.section.check.branches" /></span>
													</span>
											</div>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${not empty isMixedCartEnabled ? isMixedCartEnabled ne true : sessionScope.isMixedCartEnabled ne true}">
                                                    <c:set var="hideSubmitFlag" value="true" />
                                                    <div id="stock30" class="stock-section-notavailableonline-listview">
														<span class="text-default">
															<spring:theme code="stock.section.not.available" /></br>
															<span class="check-branch" data-value="${variantProduct.code}" onclick="ACC.mystores.changebranchPLPPopupFn(${variantProduct.code},${productCode},false);"><spring:theme code="stock.section.check.branches" /></span>
														</span>
													</div>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${hideList eq true && hideCSP eq true}">
                                                            <c:set var="hideSubmitFlag" value="true" />
                                                            <div id="stock31" class="stock-section-notavailableonline-listview">
																<span class="text-default">
																	<spring:theme code="stock.section.not.available" /></br>
																	<span class="check-branch" data-value="${variantProduct.code}" onclick="ACC.mystores.changebranchPLPPopupFn(${variantProduct.code},${productCode},false);"><spring:theme code="stock.section.check.branches" /></span>
																</span>
															</div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:set var="hideSubmitFlag" value="true" />
                                                            <div id="stock32" class="stock-section-notavailableonline-listview">
																<span class="text-default">
																	<spring:theme code="stock.section.not.available" /></br>
																	<span class="check-branch" data-value="${variantProduct.code}" onclick="ACC.mystores.changebranchPLPPopupFn(${variantProduct.code},${productCode},false);"><spring:theme code="stock.section.check.branches" /></span>
																</span>
															</div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="col-md-3 p-l-0">
            <div class="row flex-center">
                <div class="variant-list-section col-md-6 p-l-0">
                    <c:choose>
                        <c:when test="${isAnonymous}">
                            <div class="${(((not empty variantProduct.price && variantProduct.price.value ne '0.0')|| not empty variantProduct.priceRange.minPrice)) and (hideList ne true) ? '':'login-center'} login-price-plp">
                                <c:choose>
                                    <c:when test="${hideSubmitFlag}">
                                        <a href="<c:url value=" /login" />" class="logInToSeeYourPrice signInOverlay">
                                        <spring:theme code="plp.list.login.to.requestAQuote" />
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${variantProduct.inventoryFlag ne true }">
                                            <c:choose>
                                                <c:when test="${hideCSP ne true}">
                                                    <a href="<c:url value=" /login" />" class="logInToSeeYourPrice signInOverlay">
                                                    <spring:theme code="text.product.logInToSeeYourPrice" /></a>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="callForPricingFlag" value="SET" />
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="plp-wish-list-container wishlistAddProLink-wrapper ${isAnonymous ? 'signInOverlay': ''}">
                                <productItem:addToSaveList product="${variantProduct}" wishlist="${allWishlist}" wishlistName="${wishlistName}"/>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <c:choose>
                    <c:when test="${(not empty quotesFeatureSwitch ? quotesFeatureSwitch : sessionScope.quotesFeatureSwitch) and (hideSubmitFlag eq true) and (!isAnonymous)}">
                        <div class="col-md-6 padding0 btnStatusSection">
                            <button class="col-md-12 btn btn-primary requestQuoteBtn" 
                            data-product-description="${fn:escapeXml(variantProduct.productShortDesc)}"
                            onclick="ACC.savedlist.requestQuotePopupplp(this,'variant-item', '${variantProduct.code}')">
                                Get a Quote
                            </button>
                        </div>
                    </c:when>
                    <c:when test="${isAnonymous && hideSubmitFlag}">
                        <div class="col-md-6 p-l-0 p-r-0 btnStatusSection">
                        </div>
                    </c:when>
                    <c:when test="${callForPricingFlag eq 'SET'}">
                        <div class="col-md-6 p-l-0 p-r-0 btnStatusSection">
                            <p class="callBranchForPrice">
                                <spring:theme code="text.product.callbranch" />
                            </p>
                        </div>
                    </c:when>
                    <c:when test="${nonTransferrable eq true}">
                        <div onclick="ACC.mystores.changebranchPLPPopupFn(${variantProduct.code},${productCode},true);" class="col-md-6 p-l-0 p-r-0 btnStatusSection cursor-pointer-plp">
                            <p class="changeBranchSection">
                                <spring:theme code="plp.changeBranch" />
                            </p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${(isAnonymous and (variantProduct.hideList eq true or empty variantProduct.price) and variantProduct.hideCSP ne true)}">
                                <div class="variant-login_to_buy-section col-md-6 p-l-0 btnStatusSection">
                                    <c:if test="${!hideSubmitFlag}">
                                        <button type="submit" data-prod-code="${variantProduct.code}"
                                            class="btn btn-primary btn-block variant-login-to-buy_atc js-login-to-buy ${currentLanguage.isocode eq 'es' ? 'atc-spanish-text-vplp' : ''}" onclick="ACC.productVariant.handleVariantAddToCart(event, this);">
                                        <spring:theme code="login.to.buy" />
                                        </button>
                                    </c:if>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="variant-atc-wrapper-section col-md-6 p-l-0 p-r-0 btnStatusSection">
                                    <c:if test="${!hideSubmitFlag}">
                                        <c:url value="/cart/add" var="addToCartUrl" />
                                        <form:form method="post" id="addToCartForm" class="add_to_cart_form"
                                            action="${addToCartUrl}">
                                            <input type="hidden" maxlength="3" size="1" id="qty" name="qty"
                                                class="qty js-qty-selector-input qty-hidden-variant" value="1">
                                            <input type="hidden" name="productCodePost" value="${variantProduct.code}" />
                                            <input type="hidden" name="storeId"
                                                value="${variantProduct.nearestStore.storeId}" />
                                            <input type="hidden" id="isCouponEnabled" name="isCouponEnabled" value="false">
                                            <c:set var="newuominventoryuomid" value="" />
                                            <c:forEach items="${variantProduct.sellableUoms}" var="sellableUom2">
                                                <c:if test="${variantProduct.hideUom eq true}">
                                                    <c:set var="newuominventoryuomid" value="${sellableUom2.inventoryUOMID}" />
                                                </c:if>
                                            </c:forEach>
                                            <input type="hidden" id="inventoryUomId" name="inventoryUomId"
                                                value="${variantProduct.sellableUoms[0].inventoryUOMID}">
                                            <input type="hidden" id="promoProductCode" name="promoProductCode" value="">
                                            <button type="submit" id="showAddtoCart" ${disableVariantatc}
                                                class="btn btn-primary variant-login-to-buy_atc js-atc-${variantProduct.code} plpspanishtest ${currentLanguage.isocode eq 'es' ? 'atc-spanish-text-vplp' : ''}"
                                                data-available-qty="${variantStockLevel}" onclick="ACC.productVariant.handleVariantAddToCart(event, this);">
                                                <spring:theme code="basket.add.to.basket" />
                                            </button>
                                        </form:form>
                                    </c:if>
                                </div>
                                <input type="hidden" name="getAQuoteFlagForB2BUser" class="getAQuoteFlagForB2BUser" value="${(not empty quotesFeatureSwitch ? quotesFeatureSwitch : sessionScope.quotesFeatureSwitch) and (hideSubmitFlag eq false) and (!isAnonymous)}" />
                                <div class="getAQuoteSection col-md-6 p-l-0 p-r-0 btnStatusSection hidden">
                                    <button class="col-md-12 btn btn-primary requestQuoteBtn"
                                    data-product-description="${fn:escapeXml(variantProduct.productShortDesc)}"
                                   onclick="ACC.savedlist.requestQuotePopupplp(this,'variant-item','${variantProduct.code}')">
                                        Get a Quote
                                    </button>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
