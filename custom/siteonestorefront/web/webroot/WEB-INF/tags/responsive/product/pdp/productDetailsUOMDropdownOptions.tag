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
<c:set var="stockLevel" value="${product.stock.stockLevel}" />
<c:if test="${isAnonymous}">
    <div class="col-md-7 ${product.isRegulateditem eq true ? 'custom-regulated-dropdown-top':'custom-dropdown-top'} custom-dropdown-option font-Geogrotesque-bold uom-dropdown-option" style="display:none;">
        <c:forEach items="${product.sellableUoms}" var="sellableUom">
            <c:set var="inventoryMul">&nbsp;[<fmt:formatNumber value="${sellableUom.inventoryMultiplier}" maxFractionDigits="0" />]</c:set>
            <c:if test="${not empty sellableUom.inventoryMultiplier && sellableUom.inventoryMultiplier > 1}">
                <c:set var="tempValue" value="${Math.floor(product.stock.stockLevel/sellableUom.inventoryMultiplier)}" />
                <c:set var="stockLevel">
                    <fmt:formatNumber value="${tempValue}" type="number" maxFractionDigits="0" />
                </c:set>
            </c:if>
            <div class="transition-3s custom-dropdown-label f-s-22 flex justify-between"
                onclick="ACC.global.dropDownSelection(this,'uom-dropdown')"
                data-uom-dropdown="${sellableUom.price.formattedValue}&nbsp;/&nbsp;${sellableUom.inventoryUOMDesc}${inventoryMul}"
                data-uom-dropdown-bulk="${product.bulkUOMPrice}/${product.bulkUOMCode}" data-code="${product.code}"
                data-value="${sellableUom.inventoryUOMID}" data-inventory="${sellableUom.inventoryMultiplier}" data-qty="${ACC.product.qty}"
                data-stock="${sellableUom.stockQuantity}" data-outOfStockFlag="${empty stockLevel || stockLevel le 0}"
                data-uom="${sellableUom1.inventoryUOMDesc}">
                <c:choose>
                    <c:when test="${not empty sellableUom.price.formattedValue}">
                        <span>${sellableUom.price.formattedValue}&nbsp;/&nbsp;${sellableUom.inventoryUOMDesc}${inventoryMul}</span>
                    </c:when>
                    <c:otherwise>
                        <span>${sellableUom.inventoryUOMDesc}${inventoryMul}</span>
                    </c:otherwise>
                </c:choose>
                <div class="stockInfoSection hidden">
                    <c:choose>
                        <c:when test="${!product.stockAvailableOnlyHubStore}">
                            <c:choose>
                                <c:when test="${hardscapeMoreOnWayMsg}">
                                    <span class="stock-section-moreontheway">
                                        <spring:theme code="stock.section.more.on.the.way" />
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${product.inStockImage}">
                                            <c:choose>
                                                <c:when test="${product.isStockInNearbyBranch}">
                                                    <c:choose>
                                                        <c:when test="${empty stockLevel || stockLevel le 0}">
                                                            <span class="stock-section-outofstock">0&nbsp;
                                                                <spring:theme code="stock.section.in.stock" />
                                                            </span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="stock-section-instock-nearby">
                                                                <span>${stockLevel}</span>
                                                                <span class="nearbylbl">
                                                                    <spring:theme code="pdp.new.uom.mobile.stock.section.nearby" />
                                                                </span>
                                                            </span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${empty stockLevel || stockLevel le 0}">
                                                            <span class="stock-section-outofstock">0&nbsp;
                                                                <spring:theme code="stock.section.in.stock" />
                                                            </span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="stock-section-instock">
                                                                <span>${stockLevel}</span>
                                                                <spring:theme code="stock.section.in.stock" />
                                                            </span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:when test="${product.outOfStockImage}">
                                            <span class="stock-section-notavail">
                                                <spring:theme code="stock.section.not.available" />
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${product.notInStockImage || hardscapeMoreOnWayMsg}">
                                                <c:choose>
                                                    <c:when test="${fn:contains(product.storeStockAvailabilityMsg, 'Available to Order')}">
                                                        <span class="stock-section-moreontheway">
                                                            <spring:theme code="stock.section.more.on.the.way" />
                                                        </span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:if test="${fn:contains(product.storeStockAvailabilityMsg, 'Available for Backorder')}">
                                                            <span class="stock-section-backorder">
                                                                <spring:theme code="stock.section.backorder" />
                                                            </span>
                                                        </c:if>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <span>
                                <span>${stockLevel}</span>
                                <spring:theme code="stock.section.in.stock" />&nbsp;
                                <span class="shipping-only">
                                    <spring:theme code="stock.section.for.shipping.only" />
                                </span>
                            </span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:forEach>
    </div>
</c:if>
<c:if test="${!isAnonymous}">
    <div class="col-md-9 custom-dropdown-m-l-uom ${product.isRegulateditem eq true ? 'custom-regulated-dropdown-top':'custom-dropdown-top'} custom-dropdown-option font-Geogrotesque-bold uom-dropdown-option" style="display:none;">
        <c:forEach items="${product.sellableUoms}" var="sellableUom1">
            <c:set var="inventoryMul">&nbsp;[<fmt:formatNumber value="${sellableUom1.inventoryMultiplier}" maxFractionDigits="0" />]</c:set>
            <c:if test="${not empty sellableUom1.inventoryMultiplier && sellableUom1.inventoryMultiplier > 1}">
                <c:set var="tempValue" value="${Math.floor(product.stock.stockLevel/sellableUom1.inventoryMultiplier)}" />
                <c:set var="stockLevel">
                    <fmt:formatNumber value="${tempValue}" type="number" maxFractionDigits="0" />
                </c:set>
            </c:if>
            <div class="transition-3s custom-dropdown-label f-s-22 flex justify-between"
                onclick="ACC.global.dropDownSelection(this,'uom-dropdown')" data-code="${product.code}"
                data-uom-dropdown="${sellableUom1.customerPrice.formattedValue}&nbsp;/&nbsp;${sellableUom1.inventoryUOMDesc}${inventoryMul}"
                data-uom="${sellableUom1.inventoryUOMDesc}" data-qty="${ACC.product.qty}"
                data-yourprice="${sellableUom1.customerPrice.formattedValue}"
                data-retail-price="${sellableUom1.price.formattedValue}"
                data-uom-dropdown-bulk="${product.bulkUOMPrice}/${product.bulkUOMCode}"
                data-value="${sellableUom1.inventoryUOMID}" data-inventory="${sellableUom1.inventoryMultiplier}"
                data-stock="${sellableUom1.stockQuantity}" data-outOfStockFlag="${empty stockLevel || stockLevel le 0}">
                <c:choose>
                    <c:when test="${not empty sellableUom1.customerPrice.formattedValue}">
                        <span>${sellableUom1.customerPrice.formattedValue}&nbsp;/&nbsp;${sellableUom1.inventoryUOMDesc}${inventoryMul}</span>
                    </c:when>
                    <c:otherwise>
                        <span>${sellableUom1.inventoryUOMDesc}${inventoryMul}</span>
                    </c:otherwise>
                </c:choose>
                <div class="stockInfoSection hidden">
                    <c:choose>
                        <c:when test="${!product.stockAvailableOnlyHubStore}">
                            <c:choose>
                                <c:when test="${hardscapeMoreOnWayMsg}">
                                    <span class="stock-section-moreontheway">
                                        <spring:theme code="stock.section.more.on.the.way" />
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${product.inStockImage}">
                                            <c:choose>
                                                <c:when test="${product.isStockInNearbyBranch}">
                                                    <c:choose>
                                                        <c:when test="${empty stockLevel || stockLevel le 0}">
                                                            <span class="stock-section-outofstock">0&nbsp;
                                                                <spring:theme code="stock.section.in.stock" />
                                                            </span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="stock-section-instock-nearby">
                                                                <span>${stockLevel}</span>
                                                                <span class="nearbylbl">
                                                                    <spring:theme code="pdp.new.uom.mobile.stock.section.nearby" />
                                                                </span>
                                                            </span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${empty stockLevel || stockLevel le 0}">
                                                            <span class="stock-section-outofstock">0&nbsp;
                                                                <spring:theme code="stock.section.in.stock" />
                                                            </span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="stock-section-instock">
                                                                <span>${stockLevel}</span>
                                                                <spring:theme code="stock.section.in.stock" />
                                                            </span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:when test="${product.outOfStockImage}">
                                            <span class="stock-section-notavail-mob">
                                                <spring:theme code="request.quote.popup.request.text" />
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${product.notInStockImage || hardscapeMoreOnWayMsg}">
                                                <c:choose>
                                                    <c:when test="${fn:contains(product.storeStockAvailabilityMsg, 'Available to Order')}">
                                                        <span class="stock-section-moreontheway">
                                                            <spring:theme code="stock.section.more.on.the.way" />
                                                        </span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:if test="${fn:contains(product.storeStockAvailabilityMsg, 'Available for Backorder')}">
                                                            <span class="stock-section-backorder">
                                                                <spring:theme code="stock.section.backorder" />
                                                            </span>
                                                        </c:if>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <span>
                                <span>${stockLevel}</span>
                                <spring:theme code="stock.section.in.stock" />&nbsp;
                                <span class="shipping-only">
                                    <spring:theme code="stock.section.for.shipping.only" />
                                </span>
                            </span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:forEach>
    </div>
</c:if>