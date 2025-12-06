<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
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



<c:set var="productQuantity_p" value="1"/> 
<c:if test="${product.orderQuantityInterval eq null || product.orderQuantityInterval eq '0' || product.orderQuantityInterval lt '0' || product.orderQuantityInterval eq ''}">
        <c:set var="productQuantity_p" value="1"/> 
 </c:if>
 <c:if test="${product.orderQuantityInterval ne null && product.orderQuantityInterval ne '0' && product.orderQuantityInterval gt '0' && product.orderQuantityInterval ne ''}">
        <c:set var="productQuantity_p" value="${product.orderQuantityInterval}"/> 
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
<input type="hidden" id="hardscapeisSellable-plp" name="hardscapeisSellable-plp" value="${product.isSellable}"/>
<input type="hidden" id="hardscape-outofstock" name="hardscape-outofstock" value="${!product.isStockAvailable}"/>
<c:if test="${product.isSellableInventoryHit eq true}">
	<c:set var="isMyStoreProduct" value="true" />
</c:if>
<c:if test="${product.askAnExpertEnable eq true}">
	<c:set var="showAskAnExpertButton" value="true" />
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

<div class="cl"></div>
<ycommerce:testId code="product_wholeProduct">
	<div class="row">
		<c:set var="isProductSellableclipcoupon" value="${product.nearestStore.storeId eq sessionStore.storeId}" />
		<c:if test="${product.couponEnabled eq true}">
             <div class="clipCouponplp"><common:clipCouponPLP></common:clipCouponPLP></div>
         </c:if>
		<div class="col-md-12 col-xs-5">
			<a class="thumb" href="${productUrl}" title="${fn:escapeXml(product.name)}">
				<product:productPrimaryImage product="${product}" format="product"/>
				<c:if test="${(!product.multidimensional || product.variantCount ge 1) && not empty product.productPromotion && (not empty product.price && product.price.value ne '0.0')}">
					<div class="on-sale-tag"><span class="glyphicon glyphicon-usd glyphicon-border"></span>On Sale</div></c:if>
				<c:if test="${product.isRegulateditem}">
					<div class="regulated-tag" style="bottom: 2px;" title="Restrictions on sale or use may apply to this item"><spring:theme code="productListerGridItem.regulated" /></div></c:if>
			</a>
		</div>
		<div class="col-md-12 col-xs-7">
			<span class="item-number-format"><c:out value="${product.itemNumber}" /></span>
			<ycommerce:testId code="product_productName">
				<div class="productName-wrapper">
					<input type="hidden" value="${product.code}" class="productCodeValue"/>
					<spring:url value="/p/showNearbyOverlay/?code=" var="nearbyOverlayUrl" htmlEscape="false"/>
					<a class="name" href="${productUrl}">
					<span>
					<c:choose>
						<c:when test="${fn:length(product.productShortDesc) > 85}">
							<c:out value="${fn:substring(product.productShortDesc, 0, 85)}..."/>
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
		<div class="col-xs-12 hidden-md hidden-lg">
			<div style="border-top: 1px solid #e0e0e0;"></div>
		</div>
	</div>
	<div class="row">
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
						<div class="col-xs-6 flex-half-width logged-in-your-price-section">
							<c:choose>
							<c:when test="${(product.variantCount ge 1 && (product.inventoryFlag ne true || (!(empty product.inventoryFlag)))) || (inventoryFlag ne true) }">
							<c:choose>
							<c:when test="${hideCSP ne true}">
							<product:productNonAnonymousCSP product="${product}" isMyStoreProduct="${isMyStoreProduct}" productCSPCuratedplp="Curatedplp"/>
							</c:when>
							<c:otherwise>
							<p class="callBranchForPrice price-position">
									<spring:theme code="text.product.callforpricing" />
								</c:otherwise>
								</c:choose>
								</c:when>
								<c:otherwise>
								<c:choose>
								<c:when test="${empty product.inventoryFlag && hideCSP ne true && hideList ne true}">
									<product:productNonAnonymousCSP product="${product}" isMyStoreProduct="${isMyStoreProduct}" productCSPCuratedplp="Curatedplp"/>
									<ycommerce:testId code="product_productPrice">
										<span id="promotionlabel_${loop}"></span>
									</ycommerce:testId>
								</c:when>
								<c:otherwise>
								<p class="callBranchForPrice price-position">
									<spring:theme code="text.product.callforpricing" />
								</c:otherwise>
								</c:choose>
							
								</c:otherwise>
								</c:choose>
						</div>

						<ycommerce:testId code="product_productPrice">
							<span id="promotionlabel_${loop}"></span>

							<div class="col-xs-6 flex-half-width text-align-right logged-in-retail-price-section"><!-- doneabc -->
								<c:if test="${(not empty product.price && product.price.value ne '0.0')|| not empty product.priceRange.minPrice}">
									<c:if test="${product.inventoryFlag ne true}">
										<c:choose><c:when test="${hideList ne true}">
										<c:if test="${product.price.value gt product.customerPrice.value}">
											<h5 class="fontBold ${product.multidimensional && product.variantCount ge 1 && isRetailBranchPrice && !isAnonymous ? 'hide' : ''}"><spring:theme code="text.product.siteOnelistprice"/></h5>
											</c:if>
										</c:when>
											<c:otherwise>
											</c:otherwise>
										</c:choose>
									</c:if>
								</c:if>
								<c:if test="${(not empty product.price && product.price.value ne '0.0') || not empty product.priceRange.minPrice}">
									<div class="productPriceWrapper">
										<h5 class="price-container">
								<span class="black-title">
									<span id="salePrice${loop}" class="discount sales-price"></span>
								</span>

											<span id="basePrice${loop}">
								 <c:choose>
								 	<c:when test="${product.inventoryFlag eq true && product.isSellableInventoryHit ne true} ">
								 	<p class="callBranchForPrice price-position"> <spring:theme code="text.product.callforpricing"/>

								    </c:when>
								    <c:otherwise>
								        <c:choose><c:when test="${hideList ne true}">
								        <c:if test="${product.price.value gt product.customerPrice.value}">
								        <product:productListerItemPrice	product="${product}" />
								        </c:if>
							             </c:when>
								         <c:otherwise>
								         </c:otherwise>
								          </c:choose>
								          </c:otherwise>
								          </c:choose>
							 </span>
										</h5>
									</div>
								</c:if>
								<div class="csperror display-none" id="cspError${loop}"></div>
							</div>
						</ycommerce:testId>
						<c:if test="${(!product.multidimensional || product.variantCount ge 1) && not empty product.productPromotion}">		
						<div class="product-promotion-wrapper col-xs-12">
							<input id="productPromotion${loop}" type="hidden" data-price="${product.price.value}" value="${product.productPromotion}">
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

			<c:if
					test="${!product.multidimensional || product.variantCount ge 1}">
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
										<a href="#" onclick="return false;"
										   id="customerSpecificPriceLink${product.code}"
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
										<div class="check_price" id="customerSpecificPriceSection${product.code}" style="display: none">
											<h5 class="fontBold"><spring:theme code="text.product.your.price" /></h5>
											<div class="cl"></div>
											<span id="cspSalePrice${loop}" class="discount sales-price"></span>
											<c:choose>
												<c:when test="${(not empty product.sellableUomsCount)}">
													<span id="customerSpecificPrice${product.code}"></span> / ${product.sellableUomDesc}
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
										<input type="hidden" value="${product.customerPrice.value}" class="csp${product.code}"/>
										<input type="hidden" value="${product.isRegulateditem}" class="isRegulateditemcsp${product.code}" />
										<input type="hidden" value="${isMyStoreProduct}" class="isMyStoreProduct${product.code}" />
										<input type="hidden" value="${sessionStore.isLicensed}" class="isLicensed${product.code}" />
										<input type="hidden" value="${isOrderingAccount}" class="isOrderingAccount${product.code}" />
										<input type="hidden" value="${product.isSellableInventoryHit}" class="isSellableInventoryHit${product.code}" />

										<div class="customerSpecificPrice-wrapper col-xs-12">
											<div class="check_price">
												<h5 class="fontBold"><spring:theme code="text.product.your.price" /></h5>
												<div class="cl"></div>
												<product:ProductCSPListerItemsCuratedPLP product="${product}"/>
												<div class="cl"></div>
												<span id="description${loop}"></span>
											</div>
										</div>
									</div>
								</c:when>
								<c:otherwise>
								<p class="callBranchForPrice price-position">
										<spring:theme code="text.product.callforpricing" />
									</c:otherwise>
									</c:choose>
									</c:otherwise>
									</c:choose>
									</c:when>
									<c:otherwise>
								<p class="callBranchForPrice price-position">
										<spring:theme code="text.product.callforpricing" />
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
									<div class="row plp-full-flex curated-flex ${hideHardscapeplp}">
										<ycommerce:testId code="product_productPrice">
											<span id="promotionlabel_${loop}"></span>
											<c:choose>
												<c:when test="${product.multidimensional && product.variantCount ge 1 && isRetailBranchPrice}">
												<c:if test="${!(!product.isStockAvailable && !(!isHomeOrNearbyStockAvailable && (product.isSellableInventoryHit eq true) && !onlyHubStoreAvailability))}">
													<c:if test="${hideList ne true}">
														<div class="col-xs-6 col-md-6">
															<div class="retailPrice-wrapper">
																<a href="#" onclick="return false;" id="retailPriceLink${product.code}" class="retailPriceLink"
																	data-index="${loop}" data-productcode="${product.code}"
																	data-is-sellable="${product.isSellableInventoryHit}"
																	data-is-regulateditem="${product.isRegulateditem}"
																	data-is-orderingaccount="${isOrderingAccount}" data-is-storeproduct="${isMyStoreProduct}"
																	data-is-productsellable="${isProductSellable}" data-is-licensed="${sessionStore.isLicensed}">
																	<spring:theme code="text.product.listing.retail.price" />
																</a>
																<input type="hidden" id="retailPriceValue${loop}" />
																<div id="retailPriceEnabled${product.code}" style="display: none">
																	<p class="callBranchForPrice price-position">
																		<spring:theme code="text.product.callforpricing" />
																	</p>
																</div>
																<div class="check_price" id="retailPriceSection${product.code}" style="display: none">
																	
																				<h5 class="fontBold">
																				<spring:theme code="text.product.siteOnelistprice" />
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
															<div class="csperror display-none" id="retailPriceError${loop}"></div>
														</div>
													</c:if>
												</c:if>
												</c:when>
												<c:otherwise>
													<div class="col-xs-6 col-md-6 retail-price-anonymous ${(((not empty product.price && product.price.value ne '0.0')|| not empty product.priceRange.minPrice)  and ((hideList ne true )||(hideList eq true and product.inventoryFlag eq true)))?'':'hide'}">
														<c:if test="${(not empty product.price && product.price.value ne '0.0')|| not empty product.priceRange.minPrice}">
															<c:if test="${product.inventoryFlag ne true}">
																<c:choose>
																	<c:when test="${hideList ne true}">
																		<h5 class="plp-price-total-title">
																			<spring:theme code="text.product.siteOnelistprice" />
																		</h5>
																	</c:when>
																	<c:otherwise>
																	</c:otherwise>
																</c:choose>
															</c:if>
														</c:if>
														<c:if test="${(not empty product.price && product.price.value ne '0.0') || not empty product.priceRange.minPrice}">
															<c:choose>
																<c:when test="${product.inventoryFlag eq true}">
																	<div class="productPriceWrapper">
																		<h5 class="price-container">
																			<span class="black-title">
																				<span id="salePrice${loop}" class="discount sales-price"></span>
																			</span>
																			<span id="basePrice${loop}">
																				<c:choose>
																					<c:when test="${hideList ne true}">
																						<c:choose>
																							<c:when test="${hideCSP ne true }">
																								<p class="callBranchForPrice price-position">
																									<spring:theme code="text.product.callforpricing" />
																							</c:when>
																							<c:otherwise>
																								<p class="callBranchForPrice price-position">
																									<spring:theme code="text.product.callforpricing" />
																							</c:otherwise>
																						</c:choose>
																					</c:when>
																					<c:otherwise>
																						<c:choose>
																							<c:when test="${hideCSP ne true }">
																								<p class="callBranchForPrice price-position">
																									<spring:theme code="text.product.callforpricing" />
																							</c:when>
																							<c:otherwise>
																								<p class="callBranchForPrice price-position">
																									<spring:theme code="text.product.callforpricing" />
																							</c:otherwise>
																						</c:choose>
																					</c:otherwise>
																				</c:choose>
																			</span>
																		</h5>
																	</div>
																</c:when>
																<c:otherwise>
																	<div class="productPriceWrapper">
																		<h5 class="price-container">
																			<span class="black-title">
																				<span id="salePrice${loop}" class="discount sales-price"></span>
																			</span>
																			<span id="basePrice${loop}">
																				<c:choose>
																					<c:when test="${hideList ne true}">
																						<product:productListerItemPrice product="${product}" />
																					</c:when>
																					<c:otherwise>
																					</c:otherwise>
																				</c:choose>
																			</span>
																		</h5>
																	</div>
																</c:otherwise>
															</c:choose>
											
											
														</c:if>
														<div class="csperror display-none" id="cspError${loop}"></div>
													</div>
												</c:otherwise>
											</c:choose>
										</ycommerce:testId>
										<div class="${(((not empty product.price && product.price.value ne '0.0')|| not empty product.priceRange.minPrice) || (isRetailBranchPrice eq true && !isRetailPriceforNotAvalProd)) and (hideList ne true) ?'col-xs-6 col-md-6':'col-xs-12 col-md-12 login-center'} login-price-plp">
											<c:if test="${product.inventoryFlag ne true }">
											<c:choose><c:when test="${hideCSP ne true}">
											<a href="<c:url value="/login"/>" class="logInToSeeYourPrice signInOverlay"><spring:theme code="text.product.logInToSeeYourPrice"/></a>
											</c:when>
											<c:otherwise>
											<p class="callBranchForPrice"> <spring:theme code="text.product.callforpricing"/>
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
		<div class="product-stock stock col-xs-12 col-sm-12 col-md-12">
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
								       <div class="flex-center plp-grey-msg">

											<common:checkmarkIcon width="25" height="25" iconColor="#78a22f"/>
 
												<c:choose>
													<c:when test="${isMixedCartEnabled ne true}">		 
													<span class=" pad-lft-10"><spring:theme code="product.instock.homestore.new"/>&nbsp;<span>${sessionStoreDisplayName}</span></span>
													</c:when>
													<c:otherwise>
													<span class=" pad-lft-10"><strong>${product.nearestStoreStockLevel}</strong>&nbsp;<spring:theme code="product.instock.homestore.new"/>&nbsp;<span>${sessionStoreDisplayName}</span></span>
													</c:otherwise>
												</c:choose>

								   </div>
									   <div class="cl"></div>
							   </span>
								</c:when>
								<c:otherwise>
		                      <span id="regulatoryMessage_${product.code}" class="hidden">
		                      
								
								<c:choose>
								<c:when  test="${isBackorderAndShippable}">
									<div class="flex-center  plp-grey-msg">								
										<common:exclamatoryIcon width="25" height="25" iconColor="#ed8606"/>
										<span class=" pad-lft-10"><spring:theme code="product.backorder.and.shippable" /></span>
									</div>								
									<div class="cl"></div>
								</c:when>
								<c:otherwise>
									<c:if test="${!onlyHubStoreAvailability}">
									<div class="flex-center  plp-grey-msg">
									

											<c:choose>
												<c:when test="${isMixedCartEnabled ne true}">
												<common:checkmarkIcon width="25" height="25" iconColor="#ef8700"/>
												<span class=" pad-lft-10"><spring:theme code="product.instock.nearbystore.new2"/>&nbsp;<spring:theme code="product.instock.nearbystore.new" arguments="${empty product.nearestStore.displayName ? product.nearestStore.name : product.nearestStore.displayName}"/></span>
												</c:when>
												<c:otherwise>
													<common:checkmarkIcon width="25" height="25" iconColor="#78a22f"/>
													<span class=" pad-lft-10"><strong>${product.nearestStoreStockLevel}</strong>&nbsp;<spring:theme code="product.instock.nearbystore.new2"/>&nbsp;<spring:theme code="product.instock.nearbystore.new" arguments="${empty product.nearestStore.displayName ? product.nearestStore.name : product.nearestStore.displayName}"/></span>
												</c:otherwise>
											</c:choose>

									</div>								
									<div class="cl"></div>
									</c:if>
								</c:otherwise>
								</c:choose>
								
							  </span>
								</c:otherwise>
							</c:choose>
						</c:if>

						<c:if test="${!product.isStockAvailable && (product.isSellableInventoryHit eq true)}">
							<span id="regulatoryMessage_${product.code}" class="hidden">
								<div class="flex-center  plp-grey-msg">
									<common:exclamatoryIcon width="25" height="25" iconColor="#ed8606"/>
                  
        				  <c:choose>
                          <c:when test="${isMixedCartEnabled ne true}">
                          <span class=" pad-lft-10"><spring:theme code="product.outofstock.invhit.new" /><span>${sessionStoreDisplayName}</span></span>
													</c:when>
                          <c:otherwise>
                          <span class=" pad-lft-10"><spring:theme code="product.outofstock.invhit.new" /><span>${empty product.nearestBackorderableStore.displayName ? product.nearestBackorderableStore.name : product.nearestBackorderableStore.displayName}</span></span>
                          </c:otherwise>
									</c:choose>

								</div>
								<div class="cl"></div>
						    </span>
						</c:if>



						<c:if test="${isMyStoreProduct}">
							<div id="regulatoryLicenceCheck_${product.code}">
								<div class="cl"></div>
								<div class="flex-center  plp-grey-msg">
									<common:exclamatoryIcon width="25" height="25" iconColor="#ed8606"/>
									<span class=" pad-lft-10"><spring:theme text="${regulatedMsgForExpiredLicense}" arguments="${contactNo}"/></span>
								</div>
							</div>
							<div class="cl"></div>
						</c:if>
					</c:if>
					<c:if test="${isMyStoreProduct && !isProductSellable && !onlyHubStoreAvailability}">
						<div class="cl"></div>
						<span id="regulatoryMessage_${product.code}">
						<div class="flex-center  plp-grey-msg">
							<common:exclamatoryIcon width="25" height="25" iconColor="#ed8606"/>

                       		<span class=" pad-lft-10"><spring:theme text="${regulatedItemNotApprovedMsg}" arguments="${contactNo}"/></span>
                       </div>
					</span>
						<div class="cl"></div>
					</c:if>
					<c:if test="${!isMyStoreProduct && isProductSellable && !onlyHubStoreAvailability}">
						<div class="cl"></div>
						<span id="regulatoryMessage_${product.code}">
			        <c:set var="isNotInStock" value="true"/>
						<div class="flex-center  plp-grey-msg cplp_splorder_height">
							<common:crossMarkIcon width="25" height="25" iconColor="#5a5b5d"/>
					    	<span class=" pad-lft-10"><spring:theme code="product.outofstock.noinventoryhit.regulated" /></span>
					    </div>
					</span>
						<div class="cl"></div>
					</c:if>
					<c:if test="${!isMyStoreProduct && !isProductSellable && !onlyHubStoreAvailability}">
						<div class="cl"></div>
						<span id="regulatoryMessage_${product.code}">
					    <c:set var="isNotInStock" value="true"/>
						<div class="flex-center  plp-grey-msg cplp_splorder_height">
							<common:crossMarkIcon width="25" height="25" iconColor="#5a5b5d"/>
						   <span class=" pad-lft-10"><spring:theme code="product.outofstock.noinventoryhit.regulated" /></span>
						</div>
					</span>
						<div class="cl"></div>
					</c:if>
					<c:if test="${isProductSellable && onlyHubStoreAvailability}">
						<div class="cl"></div>
						<span id="regulatoryMessage_${product.code}">
					    <c:set var="isNotInStock" value="true"/>
						<div class="flex-center  plp-grey-msg">
							<common:checkmarkIcon width="25" height="25" iconColor="#78a22f"/>
							<span class=" pad-lft-10"><spring:theme code="product.instock.shippingonly" /></span>
						</div>
					</span>
						<div class="cl"></div>
					</c:if>
				</c:if>




				<c:if test="${!product.isRegulateditem   && !product.isProductDiscontinued}">
					<c:choose>
						<c:when test="${product.isStockAvailable}">
							<c:choose>
								<c:when test="${onlyHubStoreAvailability}">
									<c:set var="isNotInStock" value="true"/>
									<div class="flex-center  plp-grey-msg">
										<common:checkmarkIcon width="25" height="25" iconColor="#78a22f"/>
										<span class=" pad-lft-10"><spring:theme code="product.instock.shippingonly" /></span>
									</div>
									<div class="cl"></div>
								</c:when>
								<c:when  test="${isBackorderAndShippable}">
									<div class="flex-center  plp-grey-msg">
										<common:exclamatoryIcon width="25" height="25" iconColor="#ed8606"/>
										<span class=" pad-lft-10"><spring:theme code="product.backorder.and.shippable" /></span>
									</div>
								</c:when>
								<c:when test="${product.nearestStore.storeId eq sessionStore.storeId}">
									<div class="flex-center  plp-grey-msg">
										<common:checkmarkIcon width="25" height="25" iconColor="#78a22f"/>

										<c:choose>
											<c:when test="${isMixedCartEnabled ne true}">
											<span class="pad-lft-10"><spring:theme code="product.instock.homestore.new" />&nbsp;<span>${sessionStoreDisplayName}</span></span>
											</c:when>
											<c:otherwise>
											<c:choose>
											<c:when test="${hideList eq true && hideCSP eq true}">
											<span class="pad-lft-10"><strong>${product.nearestStoreStockLevel}</strong>&nbsp;<spring:theme text="${instockMsg}" />&nbsp;<span>${sessionStoreDisplayName}</span></span>
											</c:when>
											<c:otherwise>
											<span class="pad-lft-10"><span class="display-block"><strong>${product.nearestStoreStockLevel}</strong>&nbsp;<spring:theme text="${instockMsg}" />&nbsp;<a  data-value="${product.code}" href="#" id="js-nearby-plp-overlay">${sessionStoreDisplayName}</a></span></span>
											</c:otherwise>
											</c:choose>
											</c:otherwise>
										</c:choose>

									</div>
									<div class="cl"></div>
								</c:when>
								<c:otherwise>
									<c:if test="${!onlyHubStoreAvailability && isHomeOrNearbyStockAvailable}">
										<div class="flex-center  plp-grey-msg">

											

  											<c:choose>
													<c:when test="${isMixedCartEnabled ne true}">
													<common:checkmarkIcon width="25" height="25" iconColor="#ef8700"/>
													<span class=" pad-lft-10"><spring:theme code="product.instock.nearbystore.new2"/>&nbsp;<spring:theme code="product.instock.nearbystore.new" arguments="${empty product.nearestStore.displayName ? product.nearestStore.name : product.nearestStore.displayName}"/></span>
													</c:when>
													<c:otherwise>
													<c:choose>
													<c:when test="${hideList eq true && hideCSP eq true}">
													<span class=" pad-lft-10"><strong>${product.nearestStoreStockLevel}</strong>&nbsp;<spring:theme code="product.instock.nearbystore.new2"/>&nbsp;<span><spring:theme code="product.instock.nearbystore.new" arguments="${empty product.nearestStore.displayName ? product.nearestStore.name : product.nearestStore.displayName}"/></span></span>

													</c:when>
													<c:otherwise>
													<common:checkmarkIcon width="25" height="25" iconColor="#78a22f"/>
													<span class=" pad-lft-10"><span class="display-block"><strong>${product.nearestStoreStockLevel}</strong>&nbsp;<spring:theme code="product.instock.nearbystore.new2"/>&nbsp;<a  data-value="${product.code}" href="#" id="js-nearby-plp-overlay"><spring:theme code="product.instock.nearbystore.new" arguments="${empty product.nearestStore.displayName ? product.nearestStore.name : product.nearestStore.displayName}"/></a></span></span>
													</c:otherwise>
												</c:choose>
												</c:otherwise>
												</c:choose>


										</div>
										<div class="cl"></div>
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${!isHomeOrNearbyStockAvailable && (product.isSellableInventoryHit eq true) && !onlyHubStoreAvailability}">
							<div class="flex-center  plp-grey-msg">
								<common:exclamatoryIcon width="25" height="25" iconColor="#ed8606"/>

												<c:choose>
                          <c:when test="${isMixedCartEnabled ne true}">
                            <span class=" pad-lft-10"><spring:theme code="product.outofstock.invhit.new" /><span >${sessionStoreDisplayName}</span></span>
													</c:when>
                          <c:otherwise>
                          <c:choose>
                          <c:when test="${hideList eq true && hideCSP eq true}">
													 <span class=" pad-lft-10"><spring:theme code="product.outofstock.invhit.new" /><span>${empty product.nearestBackorderableStore.displayName ? product.nearestBackorderableStore.name : product.nearestBackorderableStore.displayName}</span></span>
													</c:when>
													<c:otherwise>

													<span class=" pad-lft-10"><spring:theme code="product.outofstock.invhit.new" /><span><a  data-value="${product.code}" href="#" id="js-nearby-plp-overlay">${empty product.nearestBackorderableStore.displayName ? product.nearestBackorderableStore.name : product.nearestBackorderableStore.displayName}</a></span></span>

													</c:otherwise>
                          </c:choose>
                          </c:otherwise>
												</c:choose>

							</div>
							<div class="cl"></div>
						</c:when>


						<c:when test="${(product.isStockAvailable eq true)  && (product.variantCount ge 1)}">
							<c:choose>
								<c:when test="${product.nearestStore.storeId eq sessionStore.storeId}">
									<div class="flex-center  plp-grey-msg">

										<common:checkmarkIcon width="25" height="25" iconColor="#78a22f"/>

										<span class=" pad-lft-10"><spring:theme text="${instockMsg}" /><span class="display-block">${empty sessionStore.displayName ? sessionStore.name : sessionStore.displayName}</span></span>
									</div>
									<div class="cl"></div>
								</c:when>
								<c:otherwise>
									<div class="flex-center  plp-grey-msg">

										<common:checkmarkIcon width="25" height="25" iconColor="#ef8700"/>

										<span class=" pad-lft-10"><spring:theme text="${instockAtNearbyBranchMsg}" arguments="${empty product.nearestStore.displayName ? product.nearestStore.name : product.nearestStore.displayName}"/></span>
									</div>
									<div class="cl"></div>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<div class="cl"></div>
							<c:set var="isNotInStock" value="true"/>
							<div class="flex-center  plp-grey-msg cplp_splorder_height">
								<common:crossMarkIcon width="25" height="25" iconColor="#5a5b5d" />
								<span class=" pad-lft-10"> <c:choose>
										<c:when test="${product.isHardscapeProduct eq true}">
											<spring:theme text="${hardscapesOutOfStockNoInvHitMsg}"
												arguments="${contactNo}" />
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${isMixedCartEnabled ne true}">
															<spring:theme
																code="product.outofstock.noinventoryhit.new1" />															
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${hideList eq true && hideCSP eq true}">
															<spring:theme
																code="product.outofstock.noinventoryhit.new1" />
															<spring:theme
																code="product.outofstock.noinventoryhit.new" />
														</c:when>
														<c:otherwise>
															<spring:theme
																code="product.outofstock.noinventoryhit.new1" />
															<div>
																<a data-value="${product.code}" href="#"
																	id="js-nearby-plp-overlay"><spring:theme
																		code="product.outofstock.noinventoryhit.new" /></a>
															</div>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>

										</c:otherwise>
									</c:choose>
								</span>
							</div>
							<div class="cl"></div>
						</c:otherwise>
					</c:choose>
				</c:if>

			</c:if>
		</div>
		<c:set value="true" var="isBranchPickupAvailable"></c:set>
		<c:set value="true" var="isBranchDeliveryAvailable"></c:set>
		<c:set value="true" var="isBranchShippingAvailable"></c:set>
		<c:if test="${not empty sessionStore.pickupfullfillment && sessionStore.pickupfullfillment ne null}" >
			<c:set value="${sessionStore.pickupfullfillment}" var="isBranchPickupAvailable"></c:set>
		</c:if>
		<c:if test="${not empty sessionStore.deliveryfullfillment && sessionStore.deliveryfullfillment ne null}" >
			<c:set value="${sessionStore.deliveryfullfillment}" var="isBranchDeliveryAvailable"></c:set>
		</c:if>
		<c:if test="${not empty sessionStore.shippingfullfillment && sessionStore.shippingfullfillment ne null}" >
			<c:set value="${sessionStore.shippingfullfillment}" var="isBranchShippingAvailable"></c:set>
		</c:if>
		<input type="hidden" class="plp-branch-pickupAvailable" value="${isBranchPickupAvailable}"/>
		<input type="hidden" class="plp-branch-deliveryAvailable" value="${isBranchDeliveryAvailable}"/>
		<input type="hidden" class="plp-branch-shippingAvailable" value="${isBranchShippingAvailable}"/>
		<input type="hidden" class="plp-branch-segmentLevelShippingEnabled" value="${segmentLevelShippingEnabled}"/>
		<div class="col-xs-12 col-sm-12 col-md-12 ${(isNotInStock && ((product.variantCount eq 1) || (!product.multidimensional))) ? 'hide':''}">
			<div class="plp_icon_details no-margin flex-center justify-center row">
				<div class="col-xs-4 plp_icon_details__data">
					<common:pickUpIcon height="18" width="22" iconColor="${(isNotInStock || !isBranchPickupAvailable)? '#999999': '#000'}" />
				</div>
				<div class=" col-xs-4 plp_icon_details__data">
					<common:deliveryIcon height="18" width="37" iconColor="${(isNotInStock || !product.isDeliverable || !isBranchDeliveryAvailable)? '#999999': '#000'}" />
				</div>
				<div class="col-xs-4 plp_icon_details__data">
					<common:parcelIcon height="20" width="28" iconColor="${((isBranchDeliveryAvailable && (product.isShippable || onlyHubStoreAvailability)) && segmentLevelShippingEnabled) ? '#000': '#999999' }" />
				</div>
			</div>
		</div>
	</div>
	<c:if test="${onlyHubStoreAvailability}">
		<c:set var="isNotInStock" value="false"/>
	</c:if>
<c:choose>
<c:when test="${showAskAnExpertButton && !product.isStockAvailable && !(!isHomeOrNearbyStockAvailable && (product.isSellableInventoryHit eq true) && !onlyHubStoreAvailability)}">
			<div class="askAnExpert_wrapper row">
				<div class="col-md-12 pad-lft-15 padding-bottom-15 marginTop10 hardscape_overlay_Plp">
					<button class="btn btn-default btn-block ask-btn-wrap askexp-anlytcs"> Ask an Expert</button>
				</div>
			</div>
			<common:hardscapeOverlay/>

		</c:when>
		<c:otherwise>

	<c:choose>
		<c:when test="${isNotInStock && (!product.multidimensional)}">
			<div class="row">
				<div class="col-xs-12 col-sm-12 col-md-12 hide">
					<div class="flex-center justify-center grid-contact-branch bg-lightGrey">
						<div class="call-branch-icon-text"><spring:theme code="text.contact.your.branch"/></div>
						<div class="call-branch-icon-number">${contactNo}</div>
					</div>
				</div>
			</div>

		</c:when>
		<c:otherwise>

			<c:set var="product" value="${product}" scope="request"/>
			<c:set var="addToCartText" value="${addToCartText}" scope="request"/>
			<c:set var="addToCartUrl" value="${addToCartUrl}" scope="request"/>
			<c:set var="isGrid" value="true" scope="request"/>
			<div class="addtocart curated-analytics plp-ordermultiplesaddtocart" style="display:grid">
					<cms:component uid="ListAddToCartAction"/>
					<cms:component uid="ListOrderFormAction"/>
				<div class="actions-container-for-${component.uid} <c:if test="${ycommerce:checkIfPickupEnabledForStore() and product.availableForPickup}"> pickup-in-store-available</c:if>">
					<action:actions element="div" parentComponent="${component}"/>
				</div>
			</div>
			
			<c:if test="${productQuantity_p ne '1' and !(not product.multidimensional and (hideList eq true || empty product.price ) and hideCSP ne true)}">
            <div class="plp-ordermultiples plp-message1 ">
                <span class="plp-iconordermiltiples"><common:info-circle iconColor="#78a22f" width="13" height="13"/></span>
                <spring:theme code="text.minimum.value"/>&nbsp;${product.orderQuantityInterval}
            </div>
            
            <div class="plp-ordermultiples plp-message2 hidden">
                <span class="plp-iconordermiltiples"><common:info-circle iconColor="#EF8701" width="13" height="13"/></span>
                <spring:theme code="text.valid.quantity"/>: &nbsp;<span class="plp-ordermultiplesof20"><spring:theme code="text.plp.error.message1"/>&nbsp;${product.orderQuantityInterval}</span>
            </div>
            </c:if>
			<c:if test="${product.eeee eq true and !(not product.multidimensional and (hideList eq true || empty product.price ) and hideCSP ne true)}">
			<div class="plp-ordermultiples plp-message3 hidden">
                <span class="plp-iconordermiltiples"><common:info-circle iconColor="#EF8701" width="13" height="13"/></span>
				<spring:theme code="PLP.EEE.item.branch.on.hand.message1" arguments="${product.nearestStoreStockLevel}"/>
            </div>
			</c:if>
		</c:otherwise>
	</c:choose>
	</c:otherwise>
	</c:choose>
	<c:if test="${(!product.multidimensional || product.variantCount == 1)}">
		<div class="row compare-and-atl flex-center-md">
			<div class="col-xs-12 col-md-12 plp-wish-list-container wishlistAddProLink-wrapper curatedplp-list signInOverlay">
				<product:addToSaveList product="${product}" wishlist="${allWishlist}" wishlistName="${wishlistName}"/>
			</div>
		</div>
	</c:if>

</ycommerce:testId>
<common:nearbyOverlay/>
