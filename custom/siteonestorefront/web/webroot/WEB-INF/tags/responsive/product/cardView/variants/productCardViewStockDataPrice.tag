<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="productCode" required="true" type="java.lang.String" %>
<%@ attribute name="simpleVariant" required="false" type="java.lang.String" %>
<%@ attribute name="variantShowHide" type="java.lang.String" %>
<%@ attribute name="variantSimple" type="java.lang.String" %>
<%@ attribute name="variantProduct" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ taglib prefix="productItem" tagdir="/WEB-INF/tags/responsive/product/cardView" %>
<%@ taglib prefix="productItemVariant" tagdir="/WEB-INF/tags/responsive/product/cardView/variants" %>
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
    <c:if test="${variantProduct.isTransferrable eq false && isHomeOrNearbyStockAvailable eq true && variantProduct.nearestStore.storeId ne sessionStore.storeId}">
        <c:set var="nonTransferrable" value="true" />
    </c:if>
    <c:forEach items="${variantProduct.stores}" var="store">
        <c:if test="${(isMyStoreProduct eq false && (variantProduct.isShippable && !empty sessionStore.hubStores && store eq sessionStore.hubStores[0])) || (variantProduct.isShippable && !empty sessionStore.hubStores && store eq sessionStore.hubStores[0])}">
            <c:set var="nonTransferrable" value="false" />
        </c:if>
    </c:forEach>
<c:set var="isYourPriceShown" value="false" />
<c:set var="stockMessageShown" value="0" />
<c:set var="stockCountShown" value="0" />
<c:set var="stockLocationData" value="" />
<input type="hidden" name="plp-selected-desc" value="${variantShowHide ? variantProduct.productShortDesc: ''}" id="requestQuoteButtonDesc" />
<input type="hidden" name="plp-selected-itemno" value="${variantShowHide ? variantProduct.itemNumber: ''}" id="requestQuoteButtonItemnumber" />
<input type="hidden" name="plp-item-code" class="plp-item-code" value="${variantShowHide ? variantProduct.code : ''}" />
<input type="hidden" name="plp-item-number" class="plp-item-number" value="${variantShowHide ? variantProduct.itemNumber : ''}" />
<input type="hidden" name="productInventoryUOMID" class="productInventoryUOMID_${variantProduct.code}" value="${variantProduct.sellableUoms[0].inventoryUOMID}" />
<div class="productVariantSection-${productCode}-${variantProduct.code} card-variant-item ${variantShowHide == true ? '' : 'hidden'}">
    <div class="variantstock">
        <c:set var="variantProductnearestStoreStockLevel" value="${variantProduct.nearestStoreStockLevel}" />
        <c:if test="${not empty variantProduct.sellableUoms}">
            <c:if test="${not empty variantProduct.sellableUoms[0].inventoryMultiplier && variantProduct.sellableUoms[0].inventoryMultiplier > 1}">
                <c:set var="tempValue" value="${Math.floor(variantProduct.nearestStoreStockLevel/variantProduct.sellableUoms[0].inventoryMultiplier)}" />
                <c:set var="variantProductnearestStoreStockLevel">
                    <fmt:formatNumber value="${tempValue}" type="number" maxFractionDigits="0" />
                </c:set>
            </c:if>
        </c:if>
        <input type="hidden" name="variantProductvarcnt" value="${variantCount}" />
        <input type="hidden" name="simpleVariant" value="${simpleVariant}" />
        <input type="hidden" name="variantProductvalue1inventory" value="${not empty variantProduct.sellableUoms[0].inventoryMultiplier}" />
        <input type="hidden" name="variantProductvalue1inventoryvalue" value="${variantProduct.sellableUoms[0].inventoryMultiplier}" />
        <input type="hidden" name="variantProductvalue2stock" value="${variantProductnearestStoreStockLevel}" />
        <input type="hidden" name="variantProductvalue3sellableuom" value="${not empty variantProduct.sellableUoms}" />
        <input type="hidden" class="atc-categoryLeve3" value="${fn:escapeXml(variantProduct.categories[0].name)}"/>
        <input type="hidden" name="variantProduct.nearestStoreStockLevelvalue" value="${variantProduct.nearestStoreStockLevel}" />
        
    	<input type="hidden" name="variantProduct.multidimensional" value="${variantProduct.multidimensional}" />
        <input type="hidden" name="variantProduct.variantCount" value="${variantProduct.variantCount}" />
        <input type="hidden" name="variantProduct.isRegulateditem" value="${variantProduct.isRegulateditem}" />
        <input type="hidden" name="variantProduct.isProductDiscontinued" value="${variantProduct.isProductDiscontinued}" />
        <input type="hidden" name="variantProduct.isStockAvailable" value="${variantProduct.isStockAvailable}" />
        <input type="hidden" name="onlyHubStoreAvailability" value="${onlyHubStoreAvailability}" />
        <input type="hidden" name="isBackorderAndShippable" value="${isBackorderAndShippable}" />
        <input type="hidden" name="variantProduct.nearestStore.storeId" value="${variantProduct.nearestStore.storeId}" />
        <input type="hidden" name="sessionStore.storeId" value="${sessionStore.storeId}" />
        <input type="hidden" name="isMixedCartEnabled" value="${isMixedCartEnabled}" />
        <input type="hidden" name="variantProduct.nearestStoreStockLevel" value="${variantProductnearestStoreStockLevel}" />
        <input type="hidden" name="variantProduct.hideList" value="${variantProduct.hideList}" />
        <input type="hidden" name="variantProduct.hideCSP" value="${variantProduct.hideCSP}" />
        <input type="hidden" name="isHomeOrNearbyStockAvailable" value="${isHomeOrNearbyStockAvailable}" />
          <%--<productItemVariant:productCardViewVariantStockData product="${variantProduct}" isProductSellable="${isProductSellable}" sessionStoreDisplayName="${sessionStoreDisplayName}" isBackorderAndShippable="${isBackorderAndShippable}" 
             isMyStoreProduct="${isMyStoreProduct}" onlyHubStoreAvailability="${onlyHubStoreAvailability}" isHomeOrNearbyStockAvailable="${isHomeOrNearbyStockAvailable}" hardscapeMoreOnWayMsg="${hardscapeMoreOnWayMsg}"/>--%>
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
                                                    <c:set var="stockMessageShown" value="13" />
													<c:set var="stockCountShown" value="${variantProductnearestStoreStockLevel}" />
                                                    <span id="stock13" class="stock-section-instock"
                                                        data-available-qty="${variantProductnearestStoreStockLevel}">
                                                        <span class="bg-color">${variantProductnearestStoreStockLevel}</span>
                                                        <spring:theme code="stock.section.in.stock" />
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="isNotInStock" value="true" />
                                                    <c:set var="stockMessageShown" value="14" />
													<c:set var="stockCountShown" value="${variantProductnearestStoreStockLevel}" />
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
                                        	<c:set var="stockMessageShown" value="15" />
											<c:set var="stockCountShown" value="0" />
                                            <span id="stock15" class="stock-section-backorder">
                                                <span class="bg-color">-</span>
                                                <spring:theme code="stock.section.backorder" />
                                            </span>
                                        </c:when>
                                        <c:when test="${variantProduct.nearestStore.storeId eq sessionStore.storeId}">
                                            <c:choose>
                                                <c:when test="${isMixedCartEnabled ne true}">
                                                	<c:set var="stockMessageShown" value="16" />
													<c:set var="stockCountShown" value="${variantProductnearestStoreStockLevel}" />
                                                    <span id="stock16" class="stock-section-instock"
                                                        data-available-qty="${variantProductnearestStoreStockLevel}">
                                                        <span class="bg-color">${variantProductnearestStoreStockLevel}</span>
                                                        <spring:theme code="stock.section.in.stock" />
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${variantProduct.hideList eq true && variantProduct.hideCSP eq true}">
                                                        	<c:set var="stockMessageShown" value="17" />
															<c:set var="stockCountShown" value="${variantProductnearestStoreStockLevel}" />
                                                            <span id="stock17" class="stock-section-instock"
                                                                data-available-qty="${variantProductnearestStoreStockLevel}">
                                                                <span class="bg-color">${variantProductnearestStoreStockLevel}</span>
                                                                <spring:theme code="stock.section.in.stock" />
                                                            </span>
                                                        </c:when>
                                                        <c:otherwise>
                                                        	<c:set var="stockMessageShown" value="18" />
															<c:set var="stockCountShown" value="${variantProductnearestStoreStockLevel}" />
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
                                                    <c:when test="${isMixedCartEnabled ne true}">
                                                    	<c:set var="stockMessageShown" value="19" />
														<c:set var="stockCountShown" value="${variantProductnearestStoreStockLevel}" />
														<c:set var="stockLocationData" value="${variantProduct.nearestStore.storeId}" />
                                                        <span id="stock19" class="stock-section-instock-nearby"
                                                            data-available-qty="${variantProductnearestStoreStockLevel}">
                                                            <span class="bg-color">${variantProductnearestStoreStockLevel}</span>
                                                            <span class="nearbylbl"><spring:theme code="stock.section.in.stock2" /></span>
                                                            <span class="branchIdCls"><a onclick="ACC.mystores.changebranchPLPPopupFn(${variantProduct.code},${productCode},true);">Branch #${variantProduct.nearestStore.storeId}</a></span>
                                                            <!--<a class="nearby" href=""><spring:theme code="stock.section.nearby" /></a>-->
                                                        </span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${variantProduct.hideList eq true && variantProduct.hideCSP eq true}">
                                                            	<c:set var="stockMessageShown" value="20" />
																<c:set var="stockCountShown" value="${variantProductnearestStoreStockLevel}" />
																<c:set var="stockLocationData" value="${variantProduct.nearestStore.storeId}" />
                                                                <span id="stock20" class="stock-section-instock-nearby"
                                                                    data-available-qty="${variantProductnearestStoreStockLevel}">
                                                                    <span class="bg-color">${variantProductnearestStoreStockLevel}</span>
                                                                    <span class="nearbylbl"><spring:theme code="stock.section.in.stock2" /></span>
                                                                    <span class="branchIdCls"><a onclick="ACC.mystores.changebranchPLPPopupFn(${variantProduct.code},${productCode},true);">Branch #${variantProduct.nearestStore.storeId}</a></span>
                                                                    <!--<a class="nearby" href=""><spring:theme code="stock.section.nearby" /></a>-->
                                                                </span>
                                                            </c:when>
                                                            <c:otherwise>
                                                            	<c:set var="stockMessageShown" value="21" />
																<c:set var="stockCountShown" value="${variantProductnearestStoreStockLevel}" />
																<c:set var="stockLocationData" value="${variantProduct.nearestStore.storeId}" />
                                                                <span id="stock21" class="stock-section-instock-nearby"
                                                                    data-available-qty="${variantProductnearestStoreStockLevel}">
                                                                    <span class="bg-color">${variantProductnearestStoreStockLevel}</span>
                                                                    <span class="nearbylbl"><spring:theme code="stock.section.in.stock2" /></span>
                                                                    <span class="branchIdCls"><a onclick="ACC.mystores.changebranchPLPPopupFn(${variantProduct.code},${productCode},true);">Branch #${variantProduct.nearestStore.storeId}</a></span>
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
                                        <c:when test="${isMixedCartEnabled ne true and variantProduct.isForceInStock eq true}">
                                        	<c:set var="stockMessageShown" value="22" />
											<c:set var="stockCountShown" value="0" />
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
                                        <c:when test="${isMixedCartEnabled ne true}">
                                            <c:choose>
                                                <c:when test="${hardscapeMoreOnWayMsg eq true}">
                                                	<c:set var="stockMessageShown" value="23" />
													<c:set var="stockCountShown" value="0" />
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
                                                	<c:set var="stockMessageShown" value="24" />
													<c:set var="stockCountShown" value="0" />
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
                                                	<c:set var="stockMessageShown" value="25" />
													<c:set var="stockCountShown" value="0" />
                                                    <span id="stock25" class="stock-section-backorder">
                                                        <span class="bg-color">-</span>
                                                        <spring:theme code="stock.section.backorder" />
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                	<c:set var="stockMessageShown" value="26" />
													<c:set var="stockCountShown" value="0" />
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
                                        	<c:set var="stockMessageShown" value="27" />
											<c:set var="stockCountShown" value="${variantProductnearestStoreStockLevel}" />
                                            <span id="stock27" class="stock-section-instock"
                                                data-available-qty="${variantProductnearestStoreStockLevel}">
                                                <span class="bg-color">${variantProductnearestStoreStockLevel}</span>
                                                <spring:theme code="stock.section.in.stock" />
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                        	<c:set var="stockMessageShown" value="28" />
											<c:set var="stockCountShown" value="${variantProductnearestStoreStockLevel}" />
											<c:set var="stockLocationData" value="${variantProduct.nearestStore.storeId}" />
                                            <span id="stock28" class="stock-section-instock-nearby"
                                                data-available-qty="${variantProductnearestStoreStockLevel}">
                                                <span class="bg-color">${variantProductnearestStoreStockLevel}</span>
                                                <span class="nearbylbl"><spring:theme code="stock.section.in.stock2" /></span>
                                                <span class="branchIdCls"><a onclick="ACC.mystores.changebranchPLPPopupFn(${variantProduct.code},${productCode},true);">Branch #${variantProduct.nearestStore.storeId}</a></span>
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
                                            <c:set var="stockMessageShown" value="29" />
											<c:set var="stockCountShown" value="0" />
                                            <div id="stock29" class="row col-md-12 stock-section-notavailable-banner">
                                                <span class="stock-section-notavailableonline col-xs-12 padding-md-0">
                                                    <div class="col-md-8 margin-left">
                                                        <spring:theme code="stock.section.not.available" /></br>
                                                        <span class="online-area">
                                                            <spring:theme code="stock.section.online.in.your.area" />
                                                        </span>
                                                    </div>
                                                    <div class="col-md-4 check-other-branch">
                                                        <a data-value="${variantProduct.code}" onclick="ACC.mystores.changebranchPLPPopupFn(${variantProduct.code},${productCode},false);">
                                                            <spring:theme code="stock.section.check.other.branches" />
                                                        </a>
                                                    </div>
                                                </span>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${isMixedCartEnabled ne true}">
                                                    <c:set var="hideSubmitFlag" value="true" />
                                                    <c:set var="stockMessageShown" value="30" />
													<c:set var="stockCountShown" value="0" />
                                                    <div id="stock30" class="row col-md-12 stock-section-notavailable-banner">
                                                        <span class="stock-section-notavailableonline col-xs-12 padding-md-0">
                                                            <div class="col-md-8 margin-left">
                                                                <spring:theme code="stock.section.not.available" /></br>
                                                                <span class="online-area">
                                                                    <spring:theme code="stock.section.online.in.your.area" />
                                                                </span>
                                                            </div>
                                                            <div class="col-md-4 check-other-branch">
                                                                <a data-value="${variantProduct.code}" onclick="ACC.mystores.changebranchPLPPopupFn(${variantProduct.code},${productCode},false);" >
                                                                    <spring:theme code="stock.section.check.other.branches" />
                                                                </a>
                                                            </div>
                                                        </span>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${hideList eq true && hideCSP eq true}">
                                                            <c:set var="hideSubmitFlag" value="true" />
                                                            <c:set var="stockMessageShown" value="31" />
															<c:set var="stockCountShown" value="0" />
                                                            <div id="stock31" class="row col-md-12 stock-section-notavailable-banner">
                                                                <span class="stock-section-notavailableonline col-xs-12 padding-md-0">
                                                                    <div class="col-md-8 margin-left">
                                                                        <spring:theme code="stock.section.not.available" /></br>
                                                                        <span class="online-area">
                                                                            <spring:theme code="stock.section.online.in.your.area" />
                                                                        </span>
                                                                    </div>
                                                                    <div class="col-md-4 check-other-branch">
                                                                        <a data-value="${variantProduct.code}" onclick="ACC.mystores.changebranchPLPPopupFn(${variantProduct.code},${productCode},false);">
                                                                            <spring:theme code="stock.section.check.other.branches" />
                                                                        </a>
                                                                    </div>
                                                                </span>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:set var="hideSubmitFlag" value="true" />
                                                            <c:set var="stockMessageShown" value="32" />
															<c:set var="stockCountShown" value="0" />
                                                            <div id="stock32" class="row col-md-12 stock-section-notavailable-banner">
                                                                <span class="stock-section-notavailableonline col-xs-12 padding-md-0">
                                                                    <div class="col-md-8 margin-left">
                                                                        <spring:theme code="stock.section.not.available" /></br>
                                                                        <span class="online-area">
                                                                            <spring:theme code="stock.section.online.in.your.area" />
                                                                        </span>
                                                                    </div>
                                                                    <div class="col-md-4 check-other-branch">
                                                                        <a data-value="${variantProduct.code}" onclick="ACC.mystores.changebranchPLPPopupFn(${variantProduct.code},${productCode},false);">
                                                                            <spring:theme code="stock.section.check.other.branches" />
                                                                        </a>
                                                                    </div>
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
    <input type="hidden" name="stockMessageShown" class="stockMessageShown_${variantProduct.code}" value="${stockMessageShown}" />
    <input type="hidden" name="stockCountShown" class="stockCountShown_${variantProduct.code}" value="${stockCountShown}" />
    <input type="hidden" name="stockLocationData" class="stockLocationData_${variantProduct.code}" value="${stockLocationData}" />
    <div class="plp-commonerror-section col-md-12 col-sm-12 col-xs-12 p-0-xs hidden">
		<common:plpwarning_icon /><span class="plp-commonerror"><spring:theme code="text.valid.quantity" /></span>
	</div>
    <div id="plp-commonshippingonlyqtyerror-${variantProduct.code}" class="plp-commonshippingonlyqtyerror hidden">
		<common:plpwarning_icon /><span class="plp-commonerror"><spring:theme code="text.valid.quantity" /></span>
	</div>
    <c:set var="isPriceAvailable" value="false" />
    <div class="col-xs-12 flex-center padding0 cardview-variant-pricing-section">
    		<div class="col-xs-5 p-l-0 p-r-10 variantprice">
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
                                                    <div class="variantPriceWrapper">
                                                        <h5 class="fontBold">
                                                            <spring:theme code="text.product.siteOnelistprice.PLP" />
                                                        </h5>
                                                        <product:productListerItemPricePLP product="${variantProduct}" />
                                                    </div>
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
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${empty variantProduct.customerPrice || variantProduct.customerPrice.value eq '' || variantProduct.customerPrice.value eq '0.0' || variantProduct.customerPrice.value == 0}">
                                                            <c:set var="callForPricingFlag" value="SET" />
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="variantPriceWrapper">
                                                                <c:set var="isYourPriceShown" value="true" />
                                                                <h5 class="fontBold">
                                                                    <spring:theme code="text.product.your.price.PLP" />
                                                                </h5>
                                                                <product:ProductCSPListerItemsPLP product="${variantProduct}" />
                                                                <c:set var="isPriceAvailable" value="true" />
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
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
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${empty variantProduct.customerPrice || variantProduct.customerPrice.value eq '' || variantProduct.customerPrice.value eq '0.0' || variantProduct.customerPrice.value == 0}">
                                                            <c:set var="callForPricingFlag" value="SET" />
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="variantPriceWrapper">
                                                                <c:set var="isYourPriceShown" value="true" />
                                                                <h5 class="fontBold">
                                                                    <spring:theme code="text.product.your.price.PLP" />
                                                                </h5>
                                                                <product:ProductCSPListerItemsPLP product="${variantProduct}" />
                                                                <c:set var="isPriceAvailable" value="true" />
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
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
        <div class="col-xs-7 padding0 variantprice1">
         	<c:if test="${isAnonymous}">	
                            <div class="${(((not empty variantProduct.price && variantProduct.price.value ne '0.0')|| not empty variantProduct.priceRange.minPrice)) and (hideList ne true) ? '':'login-center'} login-price-plp">
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
                            </div>
           	</c:if>
           	<c:if test="${!isAnonymous && isYourPriceShown eq true}">
           	     <div class="variantPriceWrapper-slash">
                     <div class="variantPriceWrapper">
                        <c:if test="${variantProduct.price.value gt variantProduct.customerPrice.value}">
                            <product:productListerItemPricePLP product="${variantProduct}" />
                        </c:if>
                      </div>   
                 </div>    
             </c:if>
        </div>
   </div>
 	<div class="col-md-12 col-sm-12 col-xs-12 carviewvariant-row">
 		<c:if test="${variantSimple eq false}">
 			<productItemVariant:productCardViewVariantDropdown productcode="${productCode}"/> 
 		</c:if>
 	</div>
 	<div class="col-md-12 col-sm-12 col-xs-12 carviewvariant-row padding0">
 		<div class="atc-qty-section">
    	<c:choose>
                    <c:when test="${(not empty quotesFeatureSwitch ? quotesFeatureSwitch : sessionScope.quotesFeatureSwitch) and isNotInStock and (!isAnonymous)}">
                        <div class="col-md-12 col-xs-12 m-t-5 m-t-5-xs padding0">
                            <button class="col-md-12 col-xs-12 btn btn-primary requestQuoteBtn"
                                data-product-description="${fn:escapeXml(variantProduct.productShortDesc)}"
                                onclick="ACC.savedlist.requestQuotePopupplp(this,'product-item','${variantProduct.code}')">
                                <spring:theme code="request.quote.popup.request.text" />
                            </button>
                        </div>
                    </c:when>
                    <c:when test="${isAnonymous and isNotInStock}">
                        <div class="col-md-12 p-l-0 p-r-0 m-t-5 m-t-5-xs">
                            <p class="loginToRequestAQuote">
                                <a href="<c:url value=" /login" />" class="signInOverlay">
                                <spring:theme code="plp.login.to.requestAQuote" />
                                </a>
                            </p>
                        </div>
                    </c:when>
                    <c:when test="${callForPricingFlag eq 'SET'}">
                        <div class="col-md-12 p-l-0 p-r-0">
                            <p class="callBranchForPrice">
                                <spring:theme code="text.product.callbranchforpricing" />
                            </p>
                        </div>
                    </c:when>
                    <c:when test="${nonTransferrable eq true}">
                        <div class="col-md-12 p-l-0 p-r-0">
                            <div class="bg-lightGrey cursor-pointer-plp">
                                <div onclick="ACC.mystores.changebranchPLPPopupFn(${variantProduct.code},${productCode},true);" class="changeBranchSection text-center">
                                    <spring:theme code="plp.changeBranch.toAddToCart" />
                                </div>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${(isAnonymous and (variantProduct.hideList eq true or empty variantProduct.price) and variantProduct.hideCSP ne true)}">
                                <div class="cardviewvariant-login_to_buy-section col-md-12 p-l-0">
                                   <c:if test="${!hideSubmitFlag}">
                                        <button type="submit" data-prod-code="${variantProduct.code}"
                                            class="btn btn-primary btn-block variant-login-to-buy_atc js-login-to-buy" onclick="ACC.productVariant.handleVariantAddToCart(event, this);">
                                        <spring:theme code="login.to.buy" />
                                        </button>
                                    </c:if>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="cardviewvariant-atc-wrapper-section col-md-12 padding0">
                                        <div class="cardviewvariant-qty-section col-md-6 col-sm-6 col-xs-6 p-l-0 p-r-5">
                                        <c:if test="${!hideSubmitFlag}">
                                        <c:set var="productQuantity_plp" value="1"/> 
										<c:if test="${variantProduct.orderQuantityInterval eq null || variantProduct.orderQuantityInterval eq '0' || variantProduct.orderQuantityInterval lt '0' || variantProduct.orderQuantityInterval eq ''}">
										    <c:set var="productQuantity_plp" value="1" />
										</c:if>
										<c:if test="${variantProduct.orderQuantityInterval ne null && variantProduct.orderQuantityInterval ne '0' && variantProduct.orderQuantityInterval gt '0' && variantProduct.orderQuantityInterval ne ''}">
										    <c:set var="productQuantity_plp" value="${variantProduct.orderQuantityInterval}" />
										</c:if>
										<c:if test="${variantProduct.minOrderQuantity ne null && variantProduct.minOrderQuantity ne '0' && variantProduct.minOrderQuantity ne '' && empty variantProduct.sellableUoms[0].parentInventoryUOMID}">
										    <c:set var="productQuantity_plp" value="${variantProduct.minOrderQuantity}" />
										</c:if>
									<ycommerce:testId code="listplpqtysection">
										<div class="qty-section_${variantProduct.code} listplp-qtysection">
											<div class="plp-qty-container flex-center">
												<button class="minusQty flex-center border-rad-left"
													type="button" id="minusQty_${variantProduct.code}"
													onclick="ACC.productcardvariant.cardvariantdecreaseQuantity(this)">
													<common:minusIcon iconColor="#5A5B5D" />
												</button>
												<input type="text" name="productPostPLPQty"
													id="productPLPPostQty_${variantProduct.code}"
													value="${productQuantity_plp}"
													onkeypress="return ACC.productcardvariant.cardvariantisNumberKey(event)"
													pattern="\d*" oninput="ACC.productcardvariant.cardvariantupdateQty(this)"
													onblur="ACC.productcardvariant.cardvariantsetDefaultValue(this);"
													class="form-control js-variant-qty ${productQuantity_plp > variantProduct.nearestStoreStockLevel && variantProduct.eeee ? 'ohhandqty' : '' }"
													size="1" maxlength="5" data-productcode="${variantProduct.code}"
													data-nurseryproduct="${variantProduct.isNurseryProduct}"
													data-maxqty="${variantProductnearestStoreStockLevel}"
													data-min-qty="${variantProduct.orderQuantityInterval}"
													data-eeeevalue="${variantProduct.eeee}"
													data-min-orderqty="${variantProduct.minOrderQuantity}"
													data-isbaseuomproduct="${variantProduct.sellableUoms[0].parentInventoryUOMID}" />
												<button class="plusQty flex-center border-rad-right"
													type="button" id="plusQty_${variantProduct.code}"
													onclick="ACC.productcardvariant.cardvariantincreaseQuantity(this)">
													<common:plusIcon iconColor="#5A5B5D" />
												</button>
											</div>
										</div>
									</ycommerce:testId>
									 </c:if>
                                        </div>
                                    	<div class="cardvariant-atc-wrapper-section col-md-6 col-sm-6 col-xs-6 p-l-5 p-r-0">
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
	                                                class="btn btn-primary variant-login-to-buy_atc js-atc-${variantProduct.code}"
	                                                data-available-qty="${variantStockLevel}" onclick="ACC.productcardvariant.cardvarianthandleVariantAddToCart(event, this);">
	                                                <spring:theme code="basket.add.to.basket" />
	                                            </button>
	                                        	</form:form>
                                   			 </c:if>
                                    	</div>
                                        <input type="hidden" name="getAQuoteFlagForB2BUser" class="getAQuoteFlagForB2BUser" value="${(not empty quotesFeatureSwitch ? quotesFeatureSwitch : sessionScope.quotesFeatureSwitch) and (!isAnonymous)}"/>
                                        <div class="getAQuoteSection col-md-6 col-sm-6 col-xs-6 p-l-5 p-r-0 hidden">
                                            <button class="col-md-12 col-xs-12 btn btn-primary requestQuoteBtn"
                                            data-product-description="${fn:escapeXml(variantProduct.productShortDesc)}"
                                            onclick="ACC.savedlist.requestQuotePopupplp(this,'product-item','${variantProduct.code}')">
                                                <spring:theme code="request.quote.popup.request.text" />
                                            </button>
                                        </div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
    </div>
 	</div>
 </div>
    
