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
<%@ attribute name="hardscapeMoreOnWayMsg" type="java.lang.String" %>
<div class="row multipleUomTable font-Arial m-r-0 ${hardscapeMoreOnWayMsg? ' hidden ': ''} hidden-xs hidden-sm">
    <div class="col-md-12 m-t-5">
        <div class="row multipleUomBorder">
            <div class="col-md-12">
                <div class="row multipleUomTitle">
                    <div class="col-md-3"><spring:theme code="pdp.new.uom.table.size" /></div>
                    <div class="col-md-3 width30 padding0"><spring:theme code="pdp.new.uom.table.availability" /></div>
                    <c:if test="${isAnonymous}">
                        <c:choose>
                            <c:when test="${product.inventoryFlag ne true}">
                                <c:choose>
                                    <c:when test="${product.hideList eq true}">
                                        <c:choose>
                                            <c:when test="${product.hideCSP eq true}">
                                                <div class="col-md-6 width45 text-align-right"><spring:theme code="multiple.uom.redesign.desktop.retail.price" /></div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="col-md-6 width45 text-align-right"><spring:theme code="multiple.uom.redesign.desktop.your.price" /></div>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="col-md-3"><spring:theme code="multiple.uom.redesign.desktop.your.price" /></div>
                                        <div class="col-md-3 width20 text-align-right"><spring:theme code="multiple.uom.redesign.desktop.retail.price" /></div>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <div class="col-md-6 width45 text-align-right"><spring:theme code="multiple.uom.redesign.desktop.your.price" /></div>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                    <c:if test="${!isAnonymous}">
                        <c:choose>
                            <c:when test="${product.inventoryFlag ne true}">
                                <c:choose>
                                    <c:when test="${product.hideList eq true}">
                                        <div class="col-md-6 text-align-right width45"><spring:theme code="multiple.uom.redesign.desktop.your.price" /></div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="col-md-3 width20"><spring:theme code="multiple.uom.redesign.desktop.retail.price" /></div>
                                        <div class="col-md-3 text-align-right"><spring:theme code="multiple.uom.redesign.desktop.your.price" /></div>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <div class="col-md-6 text-align-right width45"><spring:theme code="multiple.uom.redesign.desktop.your.price" /></div>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </div>
            </div>
            <div class="col-md-12 multipleUomContents">
                <c:if test="${isAnonymous}">
                    <c:set var="stockLevel" value="${product.stock.stockLevel}"/>
                    <c:forEach items="${product.sellableUoms}" var="sellableUom">
                        <c:set var="inventoryMul">
                            <fmt:formatNumber value="${sellableUom.inventoryMultiplier}" maxFractionDigits="0" />
                        </c:set>
                        <c:if test="${not empty sellableUom.inventoryMultiplier && sellableUom.inventoryMultiplier > 1}">
                            <c:set var="tempValue" value="${Math.floor(product.stock.stockLevel/sellableUom.inventoryMultiplier)}" />
                            <c:set var="stockLevel"><fmt:formatNumber value="${tempValue}" type="number" maxFractionDigits="0" /></c:set>
                        </c:if>
                        <c:set var="outOfStockCountFlag" value="${empty stockLevel || stockLevel le 0}" />
                        <div class="row multipleUomItem" data-unitPriceuom="${sellableUom.unitPrice.formattedValue}"
                            data-inventoryMul1="${inventoryMul}"
                            data-inventoryMultiplier="${sellableUom.inventoryMultiplier}"
                            data-inventoryUOMDesc="${sellableUom.inventoryUOMDesc}"
                            data-inventoryId="${sellableUom.inventoryUOMID}" data-productCode="${product.code}"
                            data-qty="${ACC.product.qty}" data-outOfStockFlag="${outOfStockCountFlag}" data-stockLevel="${stockLevel}">
                            <div class="col-md-3">
                                <label class="f-w-700 m-b-0 text-capitalize multipleUomItemText">
                                    <input type="radio" name="radio">
                                    <span class="checkmark"><common:uomSelectIcon /></span>
                                    ${sellableUom.inventoryUOMDesc}<span class="f-w-700">&nbsp;(${inventoryMul})</span>
                                    <input type="hidden" class="product-uomMeasure" value="${fn:escapeXml(sellableUom.inventoryUOMDesc)}" />
                                </label>
                            </div>
                            <div class="col-md-3 width30 padding0">
                                <c:choose>
                                    <c:when test="${!product.stockAvailableOnlyHubStore}">
                                        <c:choose>
                                            <c:when test="${hardscapeMoreOnWayMsg}">
                                                <span class="stock-section-moreontheway">
                                                    <span class="bg-color"><common:plpNewCheck height="13" width="13"/></span>
												    <spring:theme code="stock.section.more.on.the.way" />
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <c:choose>
                                                    <c:when test="${product.inStockImage}">
                                                        <c:choose>
                                                            <c:when test="${product.isStockInNearbyBranch}">
                                                                <c:choose>
                                                                    <c:when test="${outOfStockCountFlag}">
                                                                        <span class="stock-section-outofstock"><span class="bg-color">0</span>&nbsp;<spring:theme code="stock.section.out-of-stock" /></span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="stock-section-instock-nearby">
                                                                            <span class="bg-color">${stockLevel}</span>
                                                                            <span class="nearbylbl p-t-5"><spring:theme code="stock.section.in.stock2" /></span>&nbsp;
                                                                            <span class="branchIdCls p-t-5"><a class="pdp-store-link" href="javascript:void(0)">Branch #${product.stock.storeId}</a></span>
                                                                        </span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:choose>
                                                                    <c:when test="${outOfStockCountFlag}">
                                                                        <span class="stock-section-outofstock"><span class="bg-color">0</span>&nbsp;<spring:theme code="stock.section.out-of-stock" /></span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="stock-section-instock">
                                                                            <span class="bg-color">${stockLevel}</span> <spring:theme code="stock.section.in.stock" /></span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:when test="${product.outOfStockImage}">
                                                        <span class="stock-section-notavail">
                                                            <span class="bg-color">-</span> <spring:theme code="stock.section.not.available" />
                                                        </span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type="hidden" name="product.notInStockImage" value="${product.notInStockImage}" />
                                                        <c:if test="${product.notInStockImage || hardscapeMoreOnWayMsg}">
                                                            <c:choose>
                                                                <c:when test="${fn:contains(product.storeStockAvailabilityMsg, 'Available to Order') || fn:contains(product.storeStockAvailabilityMsg, 'Disponible para pedir')}">
                                                                    <span class="stock-section-moreontheway">
                                                                        <span class="bg-color"><common:plpNewCheck height="13" width="13"/></span> <spring:theme code="stock.section.more.on.the.way" />
                                                                    </span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:if test="${fn:contains(product.storeStockAvailabilityMsg, 'Available for Backorder') || fn:contains(product.storeStockAvailabilityMsg, 'Disponible para entrega pendiente de')}">
                                                                        <span class="stock-section-backorder">
												                            <span class="bg-color">-</span> <spring:theme code="stock.section.backorder" />
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
                                        <c:choose>
                                            <c:when test="${outOfStockCountFlag}">
                                                <span class="stock-section-outofstock"><span class="bg-color">0</span>&nbsp;
                                                    <spring:theme code="stock.section.out-of-stock" />
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="stock-section-instock">
                                                    <span class="bg-color">${stockLevel}</span> <spring:theme code="stock.section.in.stock" />&nbsp;
								                    <span class="shipping-only"><spring:theme code="stock.section.for.shipping.only" /></span>
                                                </span>
                                            </c:otherwise>          
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <c:choose>
                                <c:when test="${product.inventoryFlag ne true}">
                                    <c:choose>
                                        <c:when test="${product.hideList eq true}">
                                            <c:choose>
                                                <c:when test="${product.hideCSP eq true}">
                                                    <c:choose>
                                                        <c:when test="${product.outOfStockImage}">
                                                            <div class="col-md-6 text-align-right width45">
                                                                <a class="pdp-store-link check-other-branches" href="javascript:void(0)"><spring:theme code="pdp.new.text.check.other.branches" /></a>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="col-md-6 text-align-right width45 callBranch">
                                                                <spring:theme code="text.product.callforpricing" />
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="col-md-6 text-align-right width45">
                                                        <a href="<c:url value=" /login" />" class="logInToSeeYourPrice signInOverlay logintoseeyourpriceuomre">
                                                        <spring:theme code="text.product.logInToSeeYourPrice" /></a>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="col-md-3">
                                                <a href="<c:url value=" /login" />" class="logInToSeeYourPrice signInOverlay logintoseeyourpriceuomre">
                                                <spring:theme code="pdp.new.uom.table.loginToSee" /></a>
                                            </div>
                                            <div class="col-md-3 text-align-right width20">
                                                <div class="multipleUomItemUnitPrice f-w-700 text-default f-s-16">${sellableUom.price.formattedValue}</div>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <div class="col-md-6 text-align-right width45 callBranch">
                                        <spring:theme code="text.product.callforpricing" />
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${!isAnonymous}">
                    <c:set var="stockLevel" value="${product.stock.stockLevel}"/>
                    <c:forEach items="${product.sellableUoms}" var="sellableUom2">
                        <c:set var="inventoryMul">
                            <fmt:formatNumber value="${sellableUom2.inventoryMultiplier}" maxFractionDigits="0" />
                        </c:set>
                        <c:if test="${not empty sellableUom2.inventoryMultiplier && sellableUom2.inventoryMultiplier > 1}">
                            <c:set var="tempValue" value="${Math.floor(product.stock.stockLevel/sellableUom2.inventoryMultiplier)}" />
                            <c:set var="stockLevel"><fmt:formatNumber value="${tempValue}" type="number" maxFractionDigits="0" /></c:set>
                        </c:if>
                        <c:set var="outOfStockCountFlag" value="${empty stockLevel || stockLevel le 0}" />
                        <div class="row multipleUomItem" data-unitPriceuom="${sellableUom2.unitPrice.formattedValue}"
                            data-inventoryMul1="${inventoryMul}"
                            data-inventoryMultiplier="${sellableUom2.inventoryMultiplier}"
                            data-inventoryUOMDesc="${sellableUom2.inventoryUOMDesc}"
                            data-inventoryId="${sellableUom2.inventoryUOMID}" data-productCode="${product.code}"
                            data-qty="${ACC.product.qty}" data-outOfStockFlag="${outOfStockCountFlag}" data-stockLevel="${stockLevel}">
                            <div class="col-md-3">
                                <label class="f-w-700 m-b-0 text-capitalize multipleUomItemText">
                                    <input type="radio" name="radio">
                                    <span class="checkmark"><common:uomSelectIcon /></span>
                                    ${sellableUom2.inventoryUOMDesc}<span class="f-w-700">&nbsp;(${inventoryMul})</span>
                                    <input type="hidden" class="product-uomMeasure" value="${fn:escapeXml(sellableUom2.inventoryUOMDesc)}" />
                                </label>
                            </div>
                            <div class="col-md-3 width30 padding0">
                                <c:choose>
                                    <c:when test="${!product.stockAvailableOnlyHubStore}">
                                        <c:choose>
                                            <c:when test="${hardscapeMoreOnWayMsg}">
                                                <span class="stock-section-moreontheway">
                                                    <span class="bg-color"><common:plpNewCheck height="13" width="13"/></span>
												    <spring:theme code="stock.section.more.on.the.way" />
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <c:choose>
                                                    <c:when test="${product.inStockImage}">
                                                        <c:choose>
                                                            <c:when test="${product.isStockInNearbyBranch}">
                                                                <c:choose>
                                                                    <c:when test="${outOfStockCountFlag}">
                                                                        <span class="stock-section-outofstock"><span class="bg-color">0</span>&nbsp;<spring:theme code="stock.section.out-of-stock" /></span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="stock-section-instock-nearby">
                                                                            <span class="bg-color">${stockLevel}</span>
                                                                            <span class="nearbylbl p-t-5"><spring:theme code="stock.section.in.stock2" /></span>&nbsp;
                                                                            <span class="branchIdCls p-t-5"><a class="pdp-store-link" href="javascript:void(0)">Branch #${product.stock.storeId}</a></span>
                                                                        </span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:choose>
                                                                    <c:when test="${outOfStockCountFlag}">
                                                                        <span class="stock-section-outofstock"><span class="bg-color">0</span>&nbsp;<spring:theme code="stock.section.out-of-stock" /></span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="stock-section-instock">
                                                                            <span class="bg-color">${stockLevel}</span> <spring:theme code="stock.section.in.stock" /></span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:when test="${product.outOfStockImage}">
                                                        <span class="stock-section-notavail">
                                                            <span class="bg-color">-</span> <spring:theme code="stock.section.not.available" />
                                                        </span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type="hidden" name="product.notInStockImage" value="${product.notInStockImage}" />
                                                        <c:if test="${product.notInStockImage || hardscapeMoreOnWayMsg}">
                                                            <c:choose>
                                                                <c:when test="${fn:contains(product.storeStockAvailabilityMsg, 'Available to Order') || fn:contains(product.storeStockAvailabilityMsg, 'Disponible para pedir')}">
                                                                    <span class="stock-section-moreontheway">
                                                                        <span class="bg-color"><common:plpNewCheck height="13" width="13"/></span> <spring:theme code="stock.section.more.on.the.way" />
                                                                    </span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:if test="${fn:contains(product.storeStockAvailabilityMsg, 'Available for Backorder') || fn:contains(product.storeStockAvailabilityMsg, 'Disponible para entrega pendiente de')}">
                                                                        <span class="stock-section-backorder">
												                            <span class="bg-color">-</span> <spring:theme code="stock.section.backorder" />
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
                                        <c:choose>
                                            <c:when test="${outOfStockCountFlag}">
                                                <span class="stock-section-outofstock"><span class="bg-color">0</span>&nbsp;
                                                    <spring:theme code="stock.section.out-of-stock" />
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="stock-section-instock">
                                                    <span class="bg-color">${stockLevel}</span> <spring:theme code="stock.section.in.stock" />&nbsp;
								                    <span class="shipping-only"><spring:theme code="stock.section.for.shipping.only" /></span>
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <c:choose>
                                <c:when test="${product.inventoryFlag ne true}">
                                    <c:choose>
                                        <c:when test="${product.hideList eq true}">
                                            <c:choose>
                                                <c:when test="${product.hideCSP eq true}">
                                                    <c:choose>
                                                        <c:when test="${product.outOfStockImage}">
                                                            <div class="col-md-6 text-align-right width45"><a class="pdp-store-link check-other-branches" href="javascript:void(0)"><spring:theme code="pdp.new.text.check.other.branches" /></a></div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="col-md-6 text-align-right width45 callBranch"><spring:theme code="text.product.callforpricing" /></div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${(not empty sellableUom2.customerPrice && sellableUom2.customerPrice.value ne '0.0')}">
                                                            <div class="col-md-6 multipleUomItemYourPrice f-w-700 text-default f-s-16 text-align-right width45">${sellableUom2.customerPrice.formattedValue}</div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="col-md-6 text-align-right width45 callBranch"><spring:theme code="text.product.callforpricing" /></div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${product.hideCSP eq true}">
                                                    <div class="col-md-3 multipleUomItemUnitPrice f-w-400 text-gray f-s-16 width20">
                                                        <c:if test="${sellableUom2.customerPrice.value lt sellableUom2.price.value}">
                                                            ${sellableUom2.price.formattedValue}
                                                        </c:if>
                                                    </div>
                                                    <div class="col-md-3 text-align-right callBranch"><spring:theme code="text.product.callforpricing" /></div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="col-md-3 multipleUomItemUnitPrice f-w-400 text-gray f-s-16 width20">
                                                        <c:if test="${sellableUom2.customerPrice.value lt sellableUom2.price.value}">
                                                            ${sellableUom2.price.formattedValue}
                                                        </c:if>
                                                    </div>
                                                    <c:choose>
                                                        <c:when test="${(not empty sellableUom2.customerPrice && sellableUom2.customerPrice.value ne '0.0')}">
                                                            <div class="col-md-3 multipleUomItemYourPrice f-w-700 text-default f-s-16 text-align-right">
                                                                ${sellableUom2.customerPrice.formattedValue}
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="col-md-3 text-align-right callBranch"><spring:theme code="text.product.callforpricing" /></div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <div class="col-md-6 text-align-right width45 callBranch"><spring:theme code="text.product.callforpricing" /></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </div>
</div>