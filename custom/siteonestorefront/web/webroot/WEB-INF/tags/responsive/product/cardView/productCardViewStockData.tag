<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="analytics" tagdir="/WEB-INF/tags/shared/analytics" %>
<%@ attribute name="isProductSellable" type="java.lang.String" %>
<%@ attribute name="sessionStoreDisplayName" type="java.lang.String" %>
<%@ attribute name="isBackorderAndShippable" type="java.lang.String" %>
<%@ attribute name="isMyStoreProduct" type="java.lang.String" %>
<%@ attribute name="onlyHubStoreAvailability" type="java.lang.String" %>
<%@ attribute name="isHomeOrNearbyStockAvailable" type="java.lang.String" %>
<%@ attribute name="hardscapeMoreOnWayMsg" type="java.lang.String" %>
 <c:set var="notPurchasable" value="false" />
<input type="hidden" class="isSellableForB2BUnitInputPDP" value="${product.isSellableForB2BUnit}" />
<input id="requestQuoteButtonDesc" type="hidden" value='${product.productShortDesc}' />
<input id="requestQuoteButtonItemnumber" type="hidden" value='${product.itemNumber}' />
<c:if test="${not empty product.isSellableForB2BUnit && product.isSellableForB2BUnit ne null && product.isSellableForB2BUnit eq false}">
	<c:set var="notPurchasable" value="true" />
</c:if>

<c:set var="nearestStoreStockLevel" value="${product.nearestStoreStockLevel}" />


<c:if test="${not empty product.sellableUoms}">
	<c:if test="${not empty product.sellableUoms[0].inventoryMultiplier && product.sellableUoms[0].inventoryMultiplier > 1}">
		<c:set var="tempValue" value="${Math.floor(product.nearestStoreStockLevel/product.sellableUoms[0].inventoryMultiplier)}" />
		<c:set var="nearestStoreStockLevel"><fmt:formatNumber value="${tempValue}" type="number" maxFractionDigits="0" /></c:set>
	</c:if>
</c:if>

<c:if test="${notPurchasable}">
		<div class="row">
			<div class="purchase-not-available col-md-12 col-sm-12 col-xs-12">			
				<div class="purchase-not-available-text text-align-center text-gray-1">
					Company purchase restrictions apply (Unavailable)
				</div>
			</div>
		</div>
	</c:if>

	<div class="${quotesFeatureSwitch ? 'quotesFeatureSwitchPLP' : ''}  ${notPurchasable? ' hide ': ' '}">
		<div>
			<c:set var="isNotInStock" value="false"/>
			<c:if test="${!product.multidimensional || product.variantCount ge 1 }">
				<c:if test="${product.isRegulateditem}">
					<c:if test="${isProductSellable}">
						<input type="hidden" id="isSellableInventoryHit_${product.code}" value="${product.isSellableInventoryHit}">
						<input type="hidden" id="isStockAvailable_${product.code}" value="${product.isStockAvailable}">
						<c:if test="${product.isStockAvailable}">
							<c:choose>
								<c:when test="${product.nearestStore.storeId eq sessionStore.storeId}">
    <span id="regulatoryMessage_${product.code}" class="hidden">
        <div>
            <c:choose>
                <c:when test="${isMixedCartEnabled ne true}">
                    <c:choose>
                        <c:when test="${nearestStoreStockLevel gt 0}">
                            <span id="stock1" class="stock-section-instock">
                                <span class="bg-color">${nearestStoreStockLevel}</span>
                                <spring:theme code="stock.section.in.stock" />
                            </span>
                            <span class="stock-section-outofstock hidden">
                                <span class="bg-color">0</span>
                                <spring:theme code="stock.section.out-of-stock" />
                            </span>
                        </c:when>
                        <c:otherwise>
                            <span id="stock1" class="stock-section-outofstock">
                                <span class="bg-color">0</span>
                                <spring:theme code="stock.section.out-of-stock" />
                            </span>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${nearestStoreStockLevel gt 0}">
                            <span id="stock2" class="stock-section-instock">
                                <span class="bg-color">${nearestStoreStockLevel}</span>
                                <spring:theme code="stock.section.in.stock" />
                            </span>
                            <span class="stock-section-outofstock hidden">
                                <span class="bg-color">0</span>
                                <spring:theme code="stock.section.out-of-stock" />
                            </span>
                        </c:when>
                        <c:otherwise>
                            <span id="stock2" class="stock-section-outofstock">
                                <span class="bg-color">0</span>
                                <spring:theme code="stock.section.out-of-stock" />
                            </span>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </div>
    </span>
</c:when>
								<c:otherwise>
									<span id="regulatoryMessage_${product.code}" class="hidden">
										<c:choose>
											<c:when test="${isBackorderAndShippable}">
												<div>
													<span id="stock3" class="stock-section-backorder">
														<span class="bg-color">-</span> <spring:theme code="stock.section.backorder" />
													</span>
												</div>
											</c:when>
											<c:otherwise>
    <c:if test="${!onlyHubStoreAvailability}">
        <div>
            <c:choose>
                <c:when test="${isMixedCartEnabled ne true}">
                    <c:choose>
                        <c:when test="${nearestStoreStockLevel gt 0}">
                            <span id="stock4" class="stock-section-instock-nearby">
                                <span class="bg-color">${nearestStoreStockLevel}</span>
                                <span class="nearbylbl"><spring:theme code="stock.section.in.stock2" /></span>
                                <span class="branchIdCls">
                                    <a onclick="ACC.mystores.changebranchPLPPopupFn(${product.code},${product.code},true);">
                                        Branch #${product.nearestStore.storeId}
                                    </a>
                                </span>
                            </span>
                            <span class="stock-section-outofstock hidden">
                                <span class="bg-color">0</span>
                                <spring:theme code="stock.section.out-of-stock" />
                            </span>
                        </c:when>
                        <c:otherwise>
                            <span id="stock4" class="stock-section-outofstock">
                                <span class="bg-color">0</span>
                                <spring:theme code="stock.section.out-of-stock" />
                            </span>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${nearestStoreStockLevel gt 0}">
                            <span id="stock5" class="stock-section-instock-nearby">
                                <span class="bg-color">${nearestStoreStockLevel}</span>
                                <span class="nearbylbl"><spring:theme code="stock.section.in.stock2" /></span>
                                <span class="branchIdCls">
                                    <a onclick="ACC.mystores.changebranchPLPPopupFn(${product.code},${product.code},true);">
                                        Branch #${product.nearestStore.storeId}
                                    </a>
                                </span>
                            </span>
                            <span class="stock-section-outofstock hidden">
                                <span class="bg-color">0</span>
                                <spring:theme code="stock.section.out-of-stock" />
                            </span>
                        </c:when>
                        <c:otherwise>
                            <span id="stock5" class="stock-section-outofstock">
                                <span class="bg-color">0</span>
                                <spring:theme code="stock.section.out-of-stock" />
                            </span>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>
</c:otherwise>										
</c:choose>
									
							</span>
							</c:otherwise>
						</c:choose>
						</c:if>
						<c:if test="${!product.isStockAvailable && (product.isSellableInventoryHit eq true)}">
							<span id="regulatoryMessage_${product.code}" class="hidden">
								<div>
        				  			<c:choose>
						  				<c:when test="${isMixedCartEnabled ne true and product.isForceInStock eq true}">
                          					<span id="stock6" class="stock-section-moreontheway">
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
											<span id="stock7" class="stock-section-backorder">
												<span class="bg-color">-</span> <spring:theme code="stock.section.backorder" />
											</span>
						 			 	</c:when>
                          				<c:otherwise>
                         					<span id="stock8" class="stock-section-backorder">
												<span class="bg-color">-</span> <spring:theme code="stock.section.backorder" />
											</span>
                          				</c:otherwise>
									</c:choose>
								</div>
						    </span>
						</c:if>
						<c:if test="${isMyStoreProduct}">
							<div id="regulatoryLicenceCheck_${product.code}">
								<div id="stock9" class="row col-md-12 stock-section-notavailable-banner">
									<span class="stock-section-notavailableonline col-xs-12 padding-md-0">
									<div class="col-md-8 col-xs-7 margin-left ">
										<spring:theme code="stock.section.not.available" /></br>
						 				<span class="online-area"><spring:theme code="stock.section.online.in.your.area" /></span>
									</div>
									<div class="col-md-4 col-xs-4 check-other-branch">
										<a data-value="${product.code}" onclick="ACC.mystores.changebranchPLPPopupFn(${product.code},${product.code},false);"><spring:theme code="stock.section.check.other.branches" /></a>
									</div>
									</span>
								</div>
							</div>
						</c:if>
					</c:if>
					<c:if test="${isMyStoreProduct && !isProductSellable && !onlyHubStoreAvailability}">
						<span id="regulatoryMessage_${product.code}">
							<div id="stock10" class="row col-md-12 stock-section-notavailable-banner">
									<span class="stock-section-notavailableonline col-xs-12 padding-md-0">
									<div class="col-md-8 col-xs-7 margin-left">
										<spring:theme code="stock.section.not.available" /></br>
						 				<span class="online-area"><spring:theme code="stock.section.online.in.your.area" /></span>
									</div>
									<div class="col-md-4 col-xs-4 check-other-branch">
										<a data-value="${product.code}" onclick="ACC.mystores.changebranchPLPPopupFn(${product.code},${product.code},false);"><spring:theme code="stock.section.check.other.branches" /></a>
									</div>
									</span>
							</div>
						</span>
					</c:if>
					<c:if test="${!isMyStoreProduct && !onlyHubStoreAvailability}">
						<span id="regulatoryMessage_${product.code}">
							<c:set var="isNotInStock" value="true" />
							<div id="stock11" class="row col-md-12 stock-section-notavailable-banner">
									<span class="stock-section-notavailableonline col-xs-12 padding-md-0">
									<div class="col-md-8 col-xs-7 margin-left">
										<spring:theme code="stock.section.not.available" /></br>
						 				<span class="online-area"><spring:theme code="stock.section.online.in.your.area" /></span>
									</div>
									<div class="col-md-4 col-xs-4 check-other-branch">
										<a data-value="${product.code}" onclick="ACC.mystores.changebranchPLPPopupFn(${product.code},${product.code},false);"><spring:theme code="stock.section.check.other.branches" /></a>
									</div>
									</span>
							</div>			
						</span>
					</c:if>
					<c:if test="${isProductSellable && onlyHubStoreAvailability}">
    <span id="regulatoryMessage_${product.code}">
        <c:set var="isNotInStock" value="true" />
        <c:choose>
            <c:when test="${nearestStoreStockLevel gt 0}">
                <span id="stock12" class="stock-section-instock">
                    <span class="bg-color">${nearestStoreStockLevel}</span> 
                    <spring:theme code="stock.section.in.stock" />&nbsp;
                    <span class="shipping-only">
                        <spring:theme code="stock.section.for.shipping.only" />
                        <input type="hidden" data-shippingonly="shippingonly" id="shipping_${product.code}" />
                    </span>
                </span>
                <span class="stock-section-outofstock hidden">
                    <span class="bg-color">0</span> 
                    <spring:theme code="stock.section.out-of-stock" />
                </span>
            </c:when>
            <c:otherwise>
                <span id="stock12" class="stock-section-outofstock">
                    <span class="bg-color">0</span> 
                    <spring:theme code="stock.section.out-of-stock" />
                </span>
            </c:otherwise>
        </c:choose>
    </span>
</c:if>
				</c:if>
				<c:if test="${!product.isRegulateditem && !product.isProductDiscontinued}">
					<c:choose>
						<c:when test="${product.isStockAvailable}">
							<c:choose>
    <c:when test="${onlyHubStoreAvailability}">
        <c:choose>
            <c:when test="${product.nearestStore.storeId eq sessionStore.storeId 
                           && (product.isStockAvailable eq true) 
                           && product.isShippable}">
                <c:set var="isNotInStock" value="false"/>
                <c:choose>
                    <c:when test="${nearestStoreStockLevel gt 0}">
                        <span id="stock13" class="stock-section-instock">
                            <span class="bg-color">${nearestStoreStockLevel}</span> 
                            <spring:theme code="stock.section.in.stock" />
                        </span>
                        <span class="stock-section-outofstock hidden">
                            <span class="bg-color">0</span> 
                            <spring:theme code="stock.section.out-of-stock" />
                        </span>
                    </c:when>
                    <c:otherwise>
                        <span id="stock13" class="stock-section-outofstock">
                            <span class="bg-color">0</span> 
                            <spring:theme code="stock.section.out-of-stock" />
                        </span>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <c:set var="isNotInStock" value="true"/>
                <c:choose>
                    <c:when test="${nearestStoreStockLevel gt 0}">
                        <span id="stock14" class="stock-section-instock">
                            <span class="bg-color">${nearestStoreStockLevel}</span> 
                            <spring:theme code="stock.section.in.stock" />&nbsp;
                            <span class="shipping-only">
                                <spring:theme code="stock.section.for.shipping.only" />
                                <input type="hidden" data-shippingonly="shippingonly" id="shipping_${product.code}" />
                            </span>
                        </span>
                        <span class="stock-section-outofstock hidden">
                            <span class="bg-color">0</span> 
                            <spring:theme code="stock.section.out-of-stock" />
                        </span>
                    </c:when>
                    <c:otherwise>
                        <span id="stock14" class="stock-section-outofstock">
                            <span class="bg-color">0</span> 
                            <spring:theme code="stock.section.out-of-stock" />
                        </span>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </c:when>
	<c:when  test="${isBackorderAndShippable}">
									<span id="stock15" class="stock-section-backorder">
										<span class="bg-color">-</span> <spring:theme code="stock.section.backorder" />
									</span>
								</c:when>
								<c:when test="${product.nearestStore.storeId eq sessionStore.storeId}">
    <c:choose>
        <c:when test="${isMixedCartEnabled ne true}">
            <c:choose>
                <c:when test="${nearestStoreStockLevel gt 0}">
                    <span id="stock16" class="stock-section-instock">
                        <span class="bg-color">${nearestStoreStockLevel}</span> 
                        <spring:theme code="stock.section.in.stock" />
                    </span>
                    <span class="stock-section-outofstock hidden">
                        <span class="bg-color">0</span> 
                        <spring:theme code="stock.section.out-of-stock" />
                    </span>
                </c:when>
                <c:otherwise>
                    <span id="stock16" class="stock-section-outofstock">
                        <span class="bg-color">0</span> 
                        <spring:theme code="stock.section.out-of-stock" />
                    </span>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${product.hideList eq true && product.hideCSP eq true}">
                    <c:choose>
                        <c:when test="${nearestStoreStockLevel gt 0}">
                            <span id="stock17" class="stock-section-instock">
                                <span class="bg-color">${nearestStoreStockLevel}</span> 
                                <spring:theme code="stock.section.in.stock" />
                            </span>
                            <span class="stock-section-outofstock hidden">
                                <span class="bg-color">0</span> 
                                <spring:theme code="stock.section.out-of-stock" />
                            </span>
                        </c:when>
                        <c:otherwise>
                            <span id="stock17" class="stock-section-outofstock">
                                <span class="bg-color">0</span> 
                                <spring:theme code="stock.section.out-of-stock" />
                            </span>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${nearestStoreStockLevel gt 0}">
                            <span id="stock18" class="stock-section-instock">
                                <span class="bg-color">${nearestStoreStockLevel}</span> 
                                <spring:theme code="stock.section.in.stock" />
                            </span>
                            <span class="stock-section-outofstock hidden">
                                <span class="bg-color">0</span> 
                                <spring:theme code="stock.section.out-of-stock" />
                            </span>
                        </c:when>
                        <c:otherwise>
                            <span id="stock18" class="stock-section-outofstock">
                                <span class="bg-color">0</span> 
                                <spring:theme code="stock.section.out-of-stock" />
                            </span>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>
</c:when>
								<c:otherwise>
    <c:if test="${!onlyHubStoreAvailability && isHomeOrNearbyStockAvailable}">
        <c:choose>
            <c:when test="${isMixedCartEnabled ne true}">
                <c:choose>
                    <c:when test="${nearestStoreStockLevel gt 0}">
                        <span id="stock19" class="stock-section-instock-nearby">
                            <span class="bg-color">${nearestStoreStockLevel}</span>
                            <span class="nearbylbl"><spring:theme code="stock.section.in.stock2" /></span>
                            <span class="branchIdCls">
                                <a onclick="ACC.mystores.changebranchPLPPopupFn(${product.code},${product.code},true);">
                                    Branch #${product.nearestStore.storeId}
                                </a>
                            </span> 
                        </span>
                        <span class="stock-section-outofstock hidden">
                            <span class="bg-color">0</span> 
                            <spring:theme code="stock.section.out-of-stock" />
                        </span>
                    </c:when>
                    <c:otherwise>
                        <span id="stock19" class="stock-section-outofstock">
                            <span class="bg-color">0</span> 
                            <spring:theme code="stock.section.out-of-stock" />
                        </span>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${product.hideList eq true && product.hideCSP eq true}">
                        <c:choose>
                            <c:when test="${nearestStoreStockLevel gt 0}">
                                <span id="stock20" class="stock-section-instock-nearby">
                                    <span class="bg-color">${nearestStoreStockLevel}</span>
                                    <span class="nearbylbl"><spring:theme code="stock.section.in.stock2" /></span>
                                    <span class="branchIdCls">
                                        <a onclick="ACC.mystores.changebranchPLPPopupFn(${product.code},${product.code},true);">
                                            Branch #${product.nearestStore.storeId}
                                        </a>
                                    </span>
                                </span>
                                <span class="stock-section-outofstock hidden">
                                    <span class="bg-color">0</span> 
                                    <spring:theme code="stock.section.out-of-stock" />
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span id="stock20" class="stock-section-outofstock">
                                    <span class="bg-color">0</span> 
                                    <spring:theme code="stock.section.out-of-stock" />
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${nearestStoreStockLevel gt 0}">
                                <span id="stock21" class="stock-section-instock-nearby">
                                    <span class="bg-color">${nearestStoreStockLevel}</span>
                                    <span class="nearbylbl"><spring:theme code="stock.section.in.stock2" /></span>
                                    <span class="branchIdCls">
                                        <a onclick="ACC.mystores.changebranchPLPPopupFn(${product.code},${product.code},true);">
                                            Branch #${product.nearestStore.storeId}
                                        </a>
                                    </span>
                                </span>
                                <span class="stock-section-outofstock hidden">
                                    <span class="bg-color">0</span> 
                                    <spring:theme code="stock.section.out-of-stock" />
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span id="stock21" class="stock-section-outofstock">
                                    <span class="bg-color">0</span> 
                                    <spring:theme code="stock.section.out-of-stock" />
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </c:if>
</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${!isHomeOrNearbyStockAvailable && (product.isSellableInventoryHit eq true) && !onlyHubStoreAvailability}">
								<c:choose>
									<c:when test="${isMixedCartEnabled ne true and product.isForceInStock eq true}">
                            			<span id="stock22" class="stock-section-moreontheway">
											<span class="bg-color"><common:plpNewCheck  height="13" width="13"/></span>
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
													<span id="stock23" class="stock-section-moreontheway">
														<span class="bg-color"><common:plpNewCheck  height="13" width="13"/></span>
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
														<span class="bg-color">-</span> <spring:theme code="stock.section.backorder" />
													</span>
												</c:otherwise>
										</c:choose>
									</c:when>
                          			<c:otherwise>
                          				<c:choose>
                          					<c:when test="${hideList eq true && hideCSP eq true}">
												<span id="stock25" class="stock-section-backorder">
													<span class="bg-color">-</span> <spring:theme code="stock.section.backorder" />
												</span>
											</c:when>
											<c:otherwise>
												<span id="stock26" class="stock-section-backorder">
													<span class="bg-color">-</span> <spring:theme code="stock.section.backorder" />
												</span>
											</c:otherwise>
                          				</c:choose>
                          			</c:otherwise>
								</c:choose>
						</c:when>
						<c:when test="${(product.isStockAvailable eq true) && (product.variantCount ge 1)}">
    <c:choose>
        <c:when test="${product.nearestStore.storeId eq sessionStore.storeId}">
            <c:choose>
                <c:when test="${nearestStoreStockLevel gt 0}">
                    <span id="stock27" class="stock-section-instock">
                        <span class="bg-color">${nearestStoreStockLevel}</span> 
                        <spring:theme code="stock.section.in.stock" />
                    </span>
                    <span class="stock-section-outofstock hidden">
                        <span class="bg-color">0</span> 
                        <spring:theme code="stock.section.out-of-stock" />
                    </span>
                </c:when>
                <c:otherwise>
                    <span id="stock27" class="stock-section-outofstock">
                        <span class="bg-color">0</span> 
                        <spring:theme code="stock.section.out-of-stock" />
                    </span>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${nearestStoreStockLevel gt 0}">
                    <span id="stock28" class="stock-section-instock-nearby">
                        <span class="bg-color">${nearestStoreStockLevel}</span>
                        <span class="nearbylbl"><spring:theme code="stock.section.in.stock2" /></span>
                        <span class="branchIdCls">
                            <a onclick="ACC.mystores.changebranchPLPPopupFn(${product.code},${product.code},true);">
                                Branch #${product.nearestStore.storeId}
                            </a>
                        </span>
                    </span>
                    <span class="stock-section-outofstock hidden">
                        <span class="bg-color">0</span> 
                        <spring:theme code="stock.section.out-of-stock" />
                    </span>
                </c:when>
                <c:otherwise>
                    <span id="stock28" class="stock-section-outofstock">
                        <span class="bg-color">0</span> 
                        <spring:theme code="stock.section.out-of-stock" />
                    </span>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>
</c:when>
						<c:otherwise>
							<c:set var="isNotInStock" value="true"/>
									<c:choose>
										<c:when test="${product.isHardscapeProduct eq true}">
											<div id="stock29" class="row col-md-12 stock-section-notavailable-banner">
												<span class="stock-section-notavailableonline col-xs-12 padding-md-0">
												<div class="col-md-8 col-xs-7 margin-left">
													<spring:theme code="stock.section.not.available" /></br>
													<span class="online-area"><spring:theme code="stock.section.online.in.your.area" /></span>
												</div>
												<div class="col-md-4 col-xs-4 check-other-branch">
													<a data-value="${product.code}" onclick="ACC.mystores.changebranchPLPPopupFn(${product.code},${product.code}),false;"><spring:theme code="stock.section.check.other.branches" /></a>
												</div>
												</span>
											</div>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${isMixedCartEnabled ne true}">
													<div id="stock30" class="row col-md-12 stock-section-notavailable-banner">
														<span class="stock-section-notavailableonline col-xs-12 padding-md-0">
														<div class="col-md-8 col-xs-7 margin-left">
															<spring:theme code="stock.section.not.available" /></br>
															<span class="online-area"><spring:theme code="stock.section.online.in.your.area" /></span>
														</div>
														<div class="col-md-4 col-xs-4 check-other-branch">
															<a data-value="${product.code}" onclick="ACC.mystores.changebranchPLPPopupFn(${product.code},${product.code},false);"><spring:theme code="stock.section.check.other.branches" /></a>
														</div>
														</span>
													</div>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${hideList eq true && hideCSP eq true}">
															<div id="stock31" class="row col-md-12 stock-section-notavailable-banner">
																<span class="stock-section-notavailableonline col-xs-12 padding-md-0">
																<div class="col-md-8 col-xs-7 margin-left">
																	<spring:theme code="stock.section.not.available" /></br>
																	<span class="online-area"><spring:theme code="stock.section.online.in.your.area" /></span>
																</div>
																<div class="col-md-4 col-xs-4 check-other-branch">
																	<a data-value="${product.code}" onclick="ACC.mystores.changebranchPLPPopupFn(${product.code},${product.code},false);"><spring:theme code="stock.section.check.other.branches" /></a>
																</div>
																</span>
															</div>
														</c:when>
														<c:otherwise>
															<div id="stock32" class="row col-md-12 stock-section-notavailable-banner">
																<span class="stock-section-notavailableonline col-xs-12 padding-md-0">
																<div class="col-md-8 col-xs-7 margin-left">
																	<spring:theme code="stock.section.not.available" /></br>
																	<span class="online-area"><spring:theme code="stock.section.online.in.your.area" /></span>
																</div>
																<div class="col-md-4 col-xs-4 check-other-branch">
																	<a data-value="${product.code}" onclick="ACC.mystores.changebranchPLPPopupFn(${product.code},${product.code},false);"><spring:theme code="stock.section.check.other.branches" /></a>
																</div>
																</span>
															</div>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>		
							<%--<input id="requestQuoteButtonDesc" type="hidden" value='${product.productShortDesc}'>
							<input id="requestQuoteButtonItemnumber" type="hidden" value='${product.itemNumber}'>
							<input id="quotesFeatureSwitch" type="hidden" value='${quotesFeatureSwitch}'>
							<input id="quotesFeatureSwitchvariant" type="hidden" value='${!product.multidimensional}'>
							<c:if test="${quotesFeatureSwitch && !product.multidimensional}">
							<div class="row">
								<div class=" col-md-12 col-xs-12 m-t-5 m-t-5-xs ">
									<button class="col-md-12 col-xs-12 btn btn-primary"
									data-product-description="${fn:escapeXml(product.productShortDesc)}"
									onclick="ACC.savedlist.requestQuotePopupplp(this,'product-item','${product.code}');ACC.adobelinktracking.requestQuotePLP(this,'','','${cmsPage.name}','true')"> 
										<spring:theme code="request.quote.popup.request.text" />
									</button>
								</div>
							</div>
							</c:if>--%>	
						</c:otherwise>
					</c:choose>
				</c:if>
			</c:if>
		</div>
	</div>

	<!--<span id="" class="stock-section-outofstock">
			<span class="bg-color">0</span> <spring:theme code="stock.section.out-of-stock" />
		</span>-->
