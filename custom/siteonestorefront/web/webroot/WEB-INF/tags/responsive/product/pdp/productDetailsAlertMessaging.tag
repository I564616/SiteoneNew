<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ attribute name="type" type="java.lang.String" %>
<%@ attribute name="hardscapeMoreOnWayMsg" type="java.lang.String" %>
<c:set var="productOutOfStockImage" value="${not empty product.outOfStockImage and product.outOfStockImage ne null ? product.outOfStockImage : 'false'}" />
<c:set var="productIsEligibleForBackorder" value="${not empty product.isEligibleForBackorder and product.isEligibleForBackorder ne null ? product.isEligibleForBackorder : 'false'}" />
<c:set var="productIsForceInStock" value="${not empty product.isForceInStock and product.isForceInStock  ne null ? product.isForceInStock  : 'false'}" />
<c:set var="hidePriceAlertMessage" value="false" />
<c:if test="${(productOutOfStockImage eq true) || (productIsEligibleForBackorder eq true && productIsForceInStock eq false)}">
    <c:set var="hidePriceAlertMessage" value="true" />
</c:if>
<input type="hidden" name="productIsEligibleForBackorder" value="${productIsEligibleForBackorder}" />
<input type="hidden" name="productIsForceInStock" value="${productIsForceInStock}" />
<input type="hidden" name="productOutOfStockImage" value="${productOutOfStockImage}" />
<input type="hidden" name="hidePriceAlertMessage" value="${hidePriceAlertMessage}" />
<c:if test="${type eq 'mobile'}">
    <c:if test="${product.stockAvailExtendedMessage ne null && isMixedCartEnabled eq false && hardscapeMoreOnWayMsg eq false}">
        <div class="hidden-md hidden-lg col-xs-12 m-t-10 ${product.outOfStockImage ? 'hidden' : '' } no-right-padding p-l-0 availability-additional-message-multipleuom ${product.askAnExpertEnable ? 'hide':''}">
            <div class="availability-additional-message flex-center msg-p-r-l ${product.outOfStockImage ? 'grey-bg hidden': '' }">
                <div><common:exclamation-circle width="25" height="25" /></div>
                <div class="p-l-15">${product.stockAvailExtendedMessage}</div>
            </div>
        </div>
    </c:if>
    <c:if test="${product.stockAvailExtendedMessage ne null && isMixedCartEnabled eq true}">
        <div class="col-xs-12 col-sm-12 hidden-md hidden-lg m-t-10 ${product.askAnExpertEnable ? 'hide':''} simple-product-pdp simple-product-pdp__row padding0 mixedcart-message-availability">
            <div class="mixedcart-message-availability-text flex-center">
                <div>
                    <c:choose>
                        <c:when test="${product.inStockImage}">
                            <c:choose>
                                <c:when test="${product.isStockInNearbyBranch}">
                                    <common:bigCheckmarkIcon iconColor="#ef8700" height="25" width="25" />
                                </c:when>
                                <c:otherwise>
                                    <common:bigCheckmarkIcon iconColor="#78a22f" height="25" width="25" />
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:when test="${product.notInStockImage}">
                            <span class="bg-color">
                                <common:plpNewCheck height="25" width="25" />
                            </span>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${product.outOfStockImage}">
                                <common:bigCrossIcon height="25" width="25" />
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="p-l-15">${product.stockAvailExtendedMessage}</div>
            </div>
        </div>
    </c:if>
    <div class="col-xs-12 black-title flex-center ${hidePriceAlertMessage ? ' hide ' : ''} hidden hidden-md hidden-lg m-t-5 hardscape-pdp-stone-alert hardscape-price-alert"
        data-level1Category="${fn:escapeXml(product.level1Category)}"
        data-level2Category="${fn:escapeXml(product.level2Category)}" data-itemnumber="${product.itemNumber}"
        data-islevel1Category="${fn:escapeXml(product.level1Category) == 'Hardscapes & Outdoor Living'}"
        data-islevel2Category="${fn:escapeXml(product.level2Category) == 'Natural Stone'}">
        <div><common:exclamation-circle width="25" height="25" /></div>
        <div class="p-l-15 f-s-12 f-s-11-xs-px">
            <div>
                <span class="bold font-size-14 font-small-xs"><spring:theme code="productDetails.hardscape.stone.price" /></span>
                <span><spring:theme code="productDetails.hardscape.stone.weight" /></span>
            </div>
            <div><spring:theme code="productDetails.hardscape.stone.received" /></div>
        </div>
    </div>
    <c:if test="${((product.level1Category == 'Hardscapes & Outdoor Living' || product.level1Category == 'Materiales duros & Vida al Aire Libre') && (fn:escapeXml(product.level2Category) == 'Bulk Construction Aggregates' || fn:escapeXml(product.level2Category) == 'Bulk Decorative Aggregates'))}">
        <div class="col-xs-12 black-title flex-center hidden-md hidden-lg m-t-5 hardscape-pdp-stone-alert hardscape-note-alert"
            data-level1Category="${fn:escapeXml(product.level1Category)}"
            data-level2Category="${fn:escapeXml(product.level2Category)}">
            <div><common:exclamation-circle width="25" height="25" /></div>
            <div class="p-l-15 f-s-12 f-s-11-xs-px">
                <div>
                    <span class="bold font-size-14 font-small-xs"><spring:theme code="productDetails.hardscape.stone.note" /></span>
                    <span><spring:theme code="productDetails.hardscape.bulkAggregate.subject" /></span>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${fn:escapeXml(product.level2Category) == 'Natural Stone' || fn:escapeXml(product.level2Category) == 'Piedra Natural' || fn:escapeXml(product.level2Category) == 'Manufactured Hardscape Products' || fn:escapeXml(product.level2Category) == 'Productos de Paisajismo Manufacturados'}">
        <div class="col-xs-12 black-title flex-center hidden-md hidden-lg m-t-5 hardscape-pdp-stone-alert hardscape-note-alert"
            data-level1Category="${fn:escapeXml(product.level1Category)}"
            data-level2Category="${fn:escapeXml(product.level2Category)}">
            <div><common:exclamation-circle width="25" height="25" /></div>
            <div class="p-l-15 f-s-12 f-s-11-xs-px">
                <div>
                    <span class="bold font-size-14 font-small-xs"><spring:theme code="productDetails.hardscape.stone.note" /></span>
                    <span><spring:theme code="productDetails.hardscape.stone.subject" /></span>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${fn:escapeXml(product.level2Category) == 'Consumables'|| fn:escapeXml(product.level2Category) == 'Consumibles'}">
        <div class="col-xs-12 black-title flex-center hidden-md hidden-lg m-t-5 landscape-note-alert hidden"
            data-level1Category="${fn:escapeXml(product.level1Category)}"
            data-level2Category="${fn:escapeXml(product.level2Category)}">
            <div><common:exclamation-circle width="25" height="25" /></div>
            <div class="p-l-15 f-s-12 f-s-11-xs-px">
                <div>
                    <span class="bold font-size-14 font-small-xs"><spring:theme code="productDetails.hardscape.stone.note" /></span>
                    <span><spring:theme code="productDetails.landscape.subject" /></span>
                </div>
            </div>
        </div>
    </c:if>
</c:if>
<c:if test="${type eq 'desktop'}">
    <div class="row intervalQtyError pdp-commonerror-section flex-center m-r-0 m-t-10 hidden-sm hidden-xs hidden">
        <common:exclamation-circle width="25" height="25" />
        <div class="p-l-15">
            <span>
                <spring:theme code="text.valid.quantity" />
            </span>
        </div>
    </div>
    <div class="row availQtyError pdp-commonerror-section flex-center m-r-0 m-t-10 hidden-sm hidden-xs hidden">
        <common:exclamation-circle width="25" height="25" />
        <div class="p-l-15">
            <spring:theme code="text.available.quantity.error" arguments="${product.stock.stockLevel}" />
        </div>
    </div>
    <div class="row availQtyError2 pdp-commonerror-section flex-center m-r-0 m-t-10 hidden-sm hidden-xs hidden">
        <common:exclamation-circle width="25" height="25" />
        <div class="p-l-15">
            <spring:theme code="text.available.quantity.error2" arguments="${product.stock.stockLevel}" />
        </div>
    </div>
    <c:if test="${product.stockAvailExtendedMessage ne null && isMixedCartEnabled eq false && hardscapeMoreOnWayMsg eq false}">
        <div class="row hidden-xs hidden-sm m-r-0 m-t-10 ${product.outOfStockImage ? 'hidden' : '' } no-right-padding p-l-0 availability-additional-message-multipleuom ${product.askAnExpertEnable ? 'hide':''}">
            <div class="availability-additional-message flex-center msg-p-r-l ${product.outOfStockImage ? 'grey-bg hidden': '' }">
                <div><common:exclamation-circle width="25" height="25" /></div>
                <div class="p-l-15">${product.stockAvailExtendedMessage}</div>
            </div>
        </div>
    </c:if>
    <c:if test="${product.stockAvailExtendedMessage ne null && isMixedCartEnabled eq true}">
        <div class="row hidden-xs hidden-sm m-r-0 m-t-10 ${product.askAnExpertEnable ? 'hide':''}">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 simple-product-pdp simple-product-pdp__row padding0 mixedcart-message-availability">
                <div class="mixedcart-message-availability-text flex-center">
                    <div>
                        <c:choose>
                            <c:when test="${product.inStockImage}">
                                <c:choose>
                                    <c:when test="${product.isStockInNearbyBranch}">
                                        <common:bigCheckmarkIcon iconColor="#ef8700" height="25" width="25" />
                                    </c:when>
                                    <c:otherwise>
                                        <common:bigCheckmarkIcon iconColor="#78a22f" height="25" width="25" />
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:when test="${product.notInStockImage}">
                                <span class="bg-color">
                                    <common:plpNewCheck height="25" width="25" />
                                </span>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${product.outOfStockImage}">
                                    <common:bigCrossIcon height="25" width="25" />
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="p-l-15">${product.stockAvailExtendedMessage}</div>
                </div>
            </div>
        </div>
    </c:if>
    <div class="row black-title flex-center ${hidePriceAlertMessage ? ' hide ' : ''} hidden hidden-sm hidden-xs m-t-10 m-r-0 hardscape-pdp-stone-alert hardscape-price-alert"
        data-level1Category="${fn:escapeXml(product.level1Category)}"
        data-level2Category="${fn:escapeXml(product.level2Category)}" data-itemnumber="${product.itemNumber}"
        data-islevel1Category="${fn:escapeXml(product.level1Category) == 'Hardscapes & Outdoor Living'}"
        data-islevel2Category="${fn:escapeXml(product.level2Category) == 'Natural Stone'}">
        <common:exclamation-circle width="25" height="25" />
        <div class="p-l-15 f-s-12 f-s-11-xs-px">
            <div>
                <span class="bold font-size-14 font-small-xs"><spring:theme code="productDetails.hardscape.stone.price" /></span>
                <span><spring:theme code="productDetails.hardscape.stone.weight" /></span>
                <span><spring:theme code="productDetails.hardscape.stone.received" /></span>
            </div>
        </div>
    </div>
    <c:if test="${((product.level1Category == 'Hardscapes & Outdoor Living' || product.level1Category == 'Materiales duros & Vida al Aire Libre') && (fn:escapeXml(product.level2Category) == 'Bulk Construction Aggregates' || fn:escapeXml(product.level2Category) == 'Bulk Decorative Aggregates'))}">
        <div class="row">
            <div class="col-xs-12 hidden-sm hidden-xs p-l-0 m-b-5 m-t-10">
                <div class="black-title flex-center hardscape-pdp-stone-alert m-t-5"
                    data-level1Category="${fn:escapeXml(product.level1Category)}"
                    data-level2Category="${fn:escapeXml(product.level2Category)}">
                    <common:exclamation-circle width="25" height="25" />
                    <div class="p-l-15 f-s-12 f-s-11-xs-px">
                        <span class="bold font-size-14 font-small-xs"><spring:theme code="productDetails.hardscape.stone.note" /></span>
                        <span><spring:theme code="productDetails.hardscape.bulkAggregate.subject" /></span>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${fn:escapeXml(product.level2Category) == 'Natural Stone' || fn:escapeXml(product.level2Category) == 'Piedra Natural' || fn:escapeXml(product.level2Category) == 'Manufactured Hardscape Products' || fn:escapeXml(product.level2Category) == 'Productos de Paisajismo Manufacturados'}">
        <div class="row">
            <div class="col-xs-12 hidden-sm hidden-xs p-l-0 m-b-5 m-t-10">
                <div class="black-title flex-center hardscape-pdp-stone-alert hardscape-note-alert m-t-5"
                    data-level1Category="${fn:escapeXml(product.level1Category)}"
                    data-level2Category="${fn:escapeXml(product.level2Category)}">
                    <common:exclamation-circle width="25" height="25" />
                    <div class="p-l-15 f-s-12 f-s-11-xs-px">
                        <span class="bold font-size-14 font-small-xs"><spring:theme code="productDetails.hardscape.stone.note" /></span>
                        <span><spring:theme code="productDetails.hardscape.stone.subject" /></span>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${fn:escapeXml(product.level2Category) == 'Consumables'|| fn:escapeXml(product.level2Category) == 'Consumibles'}">
        <div class="row">
            <div class="col-xs-12 hidden-sm hidden-xs p-l-0 m-b-5 m-t-10">
                <div class="black-title flex-center landscape-note-alert hidden m-t-5"
                    data-level1Category="${fn:escapeXml(product.level1Category)}"
                    data-level2Category="${fn:escapeXml(product.level2Category)}">
                    <common:exclamation-circle width="25" height="25" />
                    <div class="p-l-15 f-s-12 f-s-11-xs-px">
                        <span class="bold font-size-14 font-small-xs"><spring:theme code="productDetails.hardscape.stone.note" /></span>
                        <span><spring:theme code="productDetails.landscape.subject" /></span>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</c:if>