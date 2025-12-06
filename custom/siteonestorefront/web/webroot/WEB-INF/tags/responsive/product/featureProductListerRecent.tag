<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="isrc" type="java.lang.String"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ attribute name="loop" type="java.lang.String" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="analytics" tagdir="/WEB-INF/tags/shared/analytics" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<c:url value="${product.url}" var="productUrl"/>
<c:set value="${not empty product.potentialPromotions}" var="hasPromotion"/>
<c:set var="hideList" value="${product.hideList}"/>
<c:set var="hideCSP" value="${product.hideCSP}"/>
<c:set var="strikeListPrice" value="false"/>
<c:set var="sessionStoreDisplayName" value="${not empty sessionNearbyStores[0].displayName ? sessionNearbyStores[0].displayName : sessionNearbyStores[0].name }"/>
<c:set var="sellableUomListLen" value="${fn:length(product.sellableUoms)}"/>
<c:set var="onlyHubStoreAvailability" value="false"/>
<c:set var="isBackorderAndShippable" value="false"/>
<c:set var="isHomeOrNearbyStockAvailable" value="false"/>
<c:set var="isMyStoreProduct" value="false"/>
<c:set var="showAskAnExpertButton" value="false"/>
<c:forEach items="${product.stores}" var="store">
    <c:if test="${store eq product.nearestStore.storeId}">
        <c:set var="isMyStoreProduct" value="true"/>
        <c:set var="isHomeOrNearbyStockAvailable" value="true"/>
    </c:if>
</c:forEach>
<input type="hidden" id="hardscapeisSellable-plp" name="hardscapeisSellable-plp" value="${product.isSellable}"/>
<input type="hidden" id="hardscape-outofstock" name="hardscape-outofstock" value="${!product.isStockAvailable}"/>
<c:if test="${product.isSellableInventoryHit eq true}">
    <c:set var="isMyStoreProduct" value="true"/>
</c:if>
<c:if test="${product.askAnExpertEnable eq true}">
    <c:set var="showAskAnExpertButton" value="true"/>
</c:if>
<c:choose>
    <c:when test="${showAskAnExpertButton && !product.isStockAvailable && !(!isHomeOrNearbyStockAvailable && (product.isSellableInventoryHit eq true) && !onlyHubStoreAvailability)}">
        <c:set var="hideHardscapeplp" value="hide"/>
    </c:when>
    <c:otherwise>
        <c:set var="hideHardscapeplp" value=""/>
    </c:otherwise>
</c:choose>
<c:forEach items="${product.stores}" var="store">
    <c:if test="${isMyStoreProduct eq false && (product.isShippable && !empty sessionStore.hubStores && store eq sessionStore.hubStores[0])}">
        <c:set var="isMyStoreProduct" value="true"/>
        <c:set var="onlyHubStoreAvailability" value="true"/>
    </c:if>
    <c:if test="${product.isSellableInventoryHit eq true && !isHomeOrNearbyStockAvailable && (product.isShippable && !empty sessionStore.hubStores && store eq sessionStore.hubStores[0])}">
        <c:set var="isBackorderAndShippable" value="true"/>
    </c:if>
</c:forEach>
<c:set var="inventoryFlag" value="${product.inventoryFlag}"/>
<c:if test="${empty product.inventoryFlag}">
    <c:choose>
        <c:when test="${isHomeOrNearbyStockAvailable eq true}">
            <c:set var="inventoryFlag" value="false"/>
        </c:when>
        <c:otherwise>
            <c:set var="inventoryFlag" value="true"/>
        </c:otherwise>
    </c:choose>
</c:if>
<c:if test="${(not empty product.customerPrice && product.customerPrice.value ne '0.0') && ((not empty product.price && product.price.value ne '0.0') || not empty product.priceRange.minPrice)}">
    <c:set var="strikeListPrice" value="true"/>
</c:if>
<c:choose>
    <c:when test="${empty cookie['gls'] && sessionStore.storeId eq '172'}">
        <c:set var="contactNo" value="<%=de.hybris.platform.util.Config.getParameter(\" siteOne.Customer.Service.Number\")%>"/>
    </c:when>
    <c:otherwise>
        <c:set var="contactNo" value="${sessionStore.address.phone}"/>
    </c:otherwise>
</c:choose>
<c:if test="${(!product.multidimensional || product.variantCount ge 1) && product.isRegulateditem}">
    <c:set var="isProductSellable" value="false"/>
    <c:forEach items="${product.regulatoryStates}" var="regulatoryStates">
        <c:if test="${regulatoryStates eq sessionStore.address.region.isocodeShort}">
            <c:set var="isProductSellable" value="true"/>
        </c:if>
    </c:forEach>
</c:if>
<c:set var="isNotInStock" value="false"/>
<c:if test="${!product.multidimensional || product.variantCount ge 1 }">
    <c:if test="${product.isRegulateditem}">
        <c:if test="${!isMyStoreProduct && !onlyHubStoreAvailability}">
            <c:set var="isNotInStock" value="true"/>
        </c:if>
        <c:if test="${isProductSellable && onlyHubStoreAvailability}">
            <c:set var="isNotInStock" value="true"/>
        </c:if>
    </c:if>
    <c:if test="${!product.isRegulateditem && !product.isProductDiscontinued}">
        <c:choose>
            <c:when test="${product.isStockAvailable}">
                <c:choose>
                    <c:when test="${onlyHubStoreAvailability}">
                        <c:set var="isNotInStock" value="true"/>
                    </c:when>
                </c:choose>
            </c:when>
            <c:otherwise>
                <c:set var="isNotInStock" value="true"/>
            </c:otherwise>
        </c:choose>
    </c:if>
</c:if>
<c:if test="${onlyHubStoreAvailability}">
    <c:set var="isNotInStock" value="false"/>
</c:if>
<!-- product section -->
<c:if test="${product.couponEnabled eq true}">
    <!-- product clipCouponPLP -->
    <span class="couponBadgeRecomm hidden">
        <common:clipCouponPLP></common:clipCouponPLP>
    </span>
    <!-- ./product clipCouponPLP -->
</c:if>
<ycommerce:testId code="product_wholeProduct">
    <!-- product productPrimaryImage -->
    <a class="thumb" href="${productUrl}${fn:escapeXml(isrc)}" title="${fn:escapeXml(product.productShortDesc)}">
        <product:productPrimaryImage product="${product}" format="product"/>
    </a>
    <!-- ./product productPrimaryImage -->
     <ycommerce:testId code="product_productName">
        <!-- product Title -->
        <a class="l-h-18 p-b-5 text-default account-widget-atc-desc" title="${fn:escapeXml(product.productShortDesc)}" href="${productUrl}${fn:escapeXml(isrc)}">
            <c:choose>
                <c:when test="${fn:length(product.productShortDesc) > 35}">
                    <c:out value="${fn:substring(product.productShortDesc, 0, 35)}..."/>
                </c:when>
                <c:otherwise>
                    <c:out value="${product.productShortDesc}"/>
                </c:otherwise>
            </c:choose>
        </a>
        <!-- ./product Title -->
     </ycommerce:testId>
    <!-- product product_productPrice -->
     <p class="margin0 f-s-18 f-w-600 l-h-18 p-t-10">
        <c:if test="${!product.multidimensional || product.variantCount ge 1}">
            <c:if test="${product.inventoryFlag ne true }">
                <c:choose>
                    <c:when test="${hideCSP ne true}">
                        <c:choose>
                            <c:when test="${(not empty product.customerPrice && product.customerPrice.value ne '0.0')}">
                                <input type="hidden" value="${product.code}" class="productcspCode"/>
                                <input type="hidden" value="${product.isStockAvailable}" class="isStockAvailablecsp${product.code}"/>
                                <input type="hidden" value="${product.customerPrice.value}" class="csp${product.code}"/>
                                <input type="hidden" value="${product.isRegulateditem}" class="isRegulateditemcsp${product.code}"/>
                                <input type="hidden" value="${isMyStoreProduct}" class="isMyStoreProduct${product.code}"/>
                                <input type="hidden" value="${sessionStore.isLicensed}" class="isLicensed${product.code}"/>
                                <input type="hidden" value="${isOrderingAccount}" class="isOrderingAccount${product.code}"/>
                                <input type="hidden" value="${product.isSellableInventoryHit}" class="isSellableInventoryHit${product.code}"/>
                                <format:accountPrice priceData="${product.customerPrice}"/>
                                <!-- price 1 -->
                            </c:when>
                            <c:otherwise>
                                <spring:theme code="text.product.callforpricing"/>
                                <!-- otherwise 1 -->
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <spring:theme code="text.product.callforpricing"/>
                        <!-- otherwise 2 -->
                    </c:otherwise>
                </c:choose>
            </c:if>
        </c:if>
    </p>
    <!-- ./product product_productPrice -->
    <!-- product sellableUoms -->
    <c:set var="uomDescription" value="true"/>
    <c:set var="uomMeasure" value="true"/>
    <c:set var="newuomDescription" value="true"/>
    <c:set var="newuomMeasure" value="true"/>
    <c:set var="singleUom" value="true"/>
    <c:if test="${not empty product.sellableUoms}">
        <c:forEach items="${product.sellableUoms}" var="sellableUom">
            <c:set var="uomDescription" value="${sellableUom.inventoryUOMDesc}"/>
            <c:set var="uomMeasure" value="${sellableUom.measure}"/>
        </c:forEach>
        <c:forEach items="${product.sellableUoms}" var="sellableUom2">
            <c:if test="${product.hideUom eq true && sellableUom2.hideUOMOnline eq false}">
                <c:set var="singleUom" value="false"/>
                <c:set var="newuomDescription" value="${sellableUom2.inventoryUOMDesc}"/>
                <c:set var="newuomMeasure" value="${sellableUom2.measure}"/>
            </c:if>
        </c:forEach>
    </c:if>
    <c:if test="${product.singleUom eq true}">
        <c:set var="singleUom" value="true"/>
        <c:set var="uomDescription" value="${product.singleUomDescription}"/>
        <c:set var="uomMeasure" value="${product.singleUomMeasure}"/>
    </c:if>
    <p class="margin0 f-s-12 l-h-12" data-sellableUoms="${product.sellableUoms}" data-psingleUom="${product.singleUom}" data-singleUom="${singleUom}" data-uomMeasure="${uomMeasure}" data-uomDescription="${uomDescription}">
        <c:if test="${not empty uomDescription}">
            <c:choose>
                <c:when test="${singleUom eq false}">${newuomMeasure}</c:when>
                <c:otherwise>${uomMeasure}</c:otherwise>
            </c:choose>
        </c:if>
    </p>
    <!-- ./product sellableUoms -->
    <!-- product ATC -->
    <c:choose>
        <c:when test="${isNotInStock && (!product.multidimensional)}">
            <button class="btn btn-primary btn-small account-widget-atc-button transition-3s"><span><spring:theme code="text.contact.your.branch"/> </span>+</button>
        </c:when>
        <c:otherwise>
            <c:set var="product" value="${product}" scope="request"/>
            <c:set var="addToCartUrl" value="${addToCartUrl}" scope="request"/>
            <c:url value="/cart/add" var="addToCartUrl"/>
            <input type="hidden" class="trackProductCode" name="trackProductCode" value="${product.code}">
            <input type="hidden" class="trackRetailPrice" name="trackRetailPrice" value="${product.price.value}">
            <input type="hidden" class="trackCSP" name="trackCSP" value="${product.customerPrice.value}">
            <c:set var="buttonID" value="listPageAddToCart_${product.code}"/>
            <c:set var="buttonIDOO" value="orderOnlineATC_${product.code}"/>
            <c:if test="${(not product.multidimensional || product.variantCount == 1)}">
                <c:set var="addIsMyStoreProduct" value="false"/>
                <c:set var="onlyHubStoreAvailability" value="false"/>
                <c:forEach items="${product.stores}" var="store">
                    <c:if test="${store eq product.nearestStore.storeId}">
                        <c:set var="addIsMyStoreProduct" value="true"/>
                    </c:if>
                </c:forEach>
                <c:if test="${product.isSellableInventoryHit eq true}">
                    <c:set var="addIsMyStoreProduct" value="true"/>
                </c:if>
                <c:forEach items="${product.stores}" var="store">
                    <c:if test="${addIsMyStoreProduct eq false && (product.isShippable && !empty sessionStore.hubStores && store eq sessionStore.hubStores[0])}">
                        <c:set var="addIsMyStoreProduct" value="true"/>
                        <c:set var="onlyHubStoreAvailability" value="true"/>
                    </c:if>
                </c:forEach>
                <c:set var="addIsProductSellable" value="false"/>
                <c:if test="${product.isRegulateditem}">
                    <c:forEach items="${product.regulatoryStates}" var="regulatoryStates">
                        <c:if test="${regulatoryStates eq sessionStore.address.region.isocodeShort}">
                            <c:set var="addIsProductSellable" value="true"/>
                        </c:if>
                    </c:forEach>
                </c:if>
                <c:set var="isSellableInventoryHit" value="false"/>
                <c:if test="${product.isSellableInventoryHit eq true}">
                    <c:set var="isSellableInventoryHit" value="true"/>
                </c:if>
            </c:if>
            <form:form id="addToCartForm${product.code}" action="${addToCartUrl}" onsubmit="clickTracking(this)" method="post" class="add_to_cart_form">
                <input type="hidden" id="clickTrackingURL" name="clickTrackingURL" value="${(product.clickTrackingURL)}"/>
                <product:listaddtocartparameters sellableUomListLen="${sellableUomListLen}" addIsMyStoreProduct="${addIsMyStoreProduct}" onlyHubStoreAvailability="${onlyHubStoreAvailability}" addIsProductSellable="${addIsProductSellable}" isSellableInventoryHit="${isSellableInventoryHit}"/>
                <c:if test="${(not product.multidimensional || product.variantCount == 1)}">
                    <!-- if 1 -->
                    <input type="hidden" name="productPostPLPQty" id="productPLPPostQty_${product.code}" value="1">
                    <ycommerce:testId code="addToCartButton">
                        <c:choose>
                            <c:when test="${((product.isRegulateditem && addIsMyStoreProduct && !addIsProductSellable && !sessionStore.isLicensed) || (!addIsMyStoreProduct) || (empty product.price && empty product.priceRange.minPrice && empty product.customerPrice) || (!(empty product.price) && product.price.value.toString() eq 0.0) || (!(empty product.customerPrice) )|| (!isOrderingAccount))}">
                                <button type="${(orderOnlinePermissions eq true)?'submit':'button'}" class="btn btn-primary btn-small transition-3s account-widget-atc-button js-atc-${product.code}" ${(orderOnlinePermissions eq true)?"":"name=atclistoo"} id="${(orderOnlinePermissions eq true)?buttonID:buttonIDOO}" aria-disabled="true" disabled="disabled">
                                    <span><spring:theme code="basket.add.to.Basket"/> </span><b class="f-s-16 l-h-15"> +</b></button>
                                <c:if test="${!isOrderingAccount}">
                                    <span class="orderAccountingMsgPLP">${orderingAccountMsg}</span>
                                </c:if>
                                <!-- when 1 -->
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${(hideCSP eq true)}">
                                        <button type="${(orderOnlinePermissions eq true)?'submit':'button'}" class="btn btn-primary btn-small transition-3s account-widget-atc-button js-atc-${product.code}" ${(orderOnlinePermissions eq true)?"":"name=atclistoo"} id="${(orderOnlinePermissions eq true)?buttonID:buttonIDOO}" aria-disabled="true" disabled="disabled">
                                            <span><spring:theme code="basket.add.to.Basket"/> </span><b class="f-s-16 l-h-15"> +</b></button>
                                        <!-- when 2 -->
                                    </c:when>
                                    <c:otherwise>
                                        <button type="${(orderOnlinePermissions eq true)?'submit':'button'}" class="btn btn-primary btn-small transition-3s account-widget-atc-button js-enable-btn js-atc-${product.code}" ${(orderOnlinePermissions eq true)?"":"name=atclistoo"} id="${(orderOnlinePermissions eq true)?buttonID:buttonIDOO}" disabled="disabled">
                                            <span><spring:theme code="basket.add.to.Basket"/> </span><b class="f-s-16 l-h-15"> +</b></button>
                                        <!-- otherwise 1 -->
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </ycommerce:testId>
                </c:if>
                <c:if test="${product.multidimensional && product.variantCount > 1}">
                    <c:url value="${product.url}" var="productUrl"/>
                    <a href="${productUrl}" class="btn btn-primary btn-small transition-3s account-widget-atc-button" style="text-decoration: none;">
                        <span><spring:theme code="featureProductList.selectFrom"/> ${product.variantCount}
                        <spring:theme code="featureProductList.products"/> </span><b class="f-s-16 l-h-15"> +</b></a>
                    <!-- if 2 -->
                </c:if>
            </form:form>
        </c:otherwise>
    </c:choose>
    <!-- ./product ATC -->
</ycommerce:testId>
<!-- ./product section -->