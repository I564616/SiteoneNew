<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="false" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="indexvalue" required="false" type="java.lang.String" %>
<%@ attribute name="uomMeasure" required="false" type="java.lang.String" %>
<%@ attribute name="uomid" required="false" type="java.lang.String" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="isStockAvailable" value="false" />
<c:if test="${product.stock.stockLevel gt 0 or (not empty product.stock.inventoryHit and product.stock.inventoryHit >4) or product.isForceInStock}">
	<c:set var="isStockAvailable" value="true" />
</c:if>
<div id="addToCartTitle" class="display-none">
	<div class="add-to-cart-header">
		<div class="headline">
			<span class="headline-text">
				<spring:theme code="basket.added.to.basket" />
			</span>
		</div>
	</div>
</div>
<c:url value="/cart/add" var="addToCartUrl"/>
<c:if test="${orderOnlinePermissions ne true}">
	<c:set var="ATCOOId" value="orderOnlineATC" />
	<cms:pageSlot position="OnlineOrder" var="feature">
		<cms:component component="${feature}"/>
	</cms:pageSlot>
</c:if>
<form:form method="post" id="addToCartForm" class="add_to_cart_form" action="${addToCartUrl}">
	<c:if test="${product.purchasable}">
		<input type="hidden" maxlength="3" size="1" id="qty" name="qty" value="1" class="qty js-qty-selector-input qty-hidden-bia"  />
	</c:if>
	<input type="hidden" id="inventoryUomId" name="inventoryUomId" value="${uomid}" />
	<input type="hidden" name="productCodePost" value="${product.code}" />
	<input type="hidden" name="productNamePost" value="${fn:escapeXml(product.name)}" />
	<input type="hidden" name="storeId" value="${product.stock.fullfillmentStoreId}" />
	<input type="hidden" id="isSellable" name="isSellable" value="${(!product.isSellable)}" />
	<input type="hidden" class="index-listproduct" name="index-listproduct" value="${indexvalue}" />
	<input type="hidden" id="requestQuoteButtonItemnumber" value="${product.itemNumber}" />
	<input type="hidden" id="requestQuoteButtonDesc" value="${fn:escapeXml(product.name)}" />
	<input type="hidden" class="quoteUom-CustomerPrice" value="${product.price.value}" />
	<input type="hidden" class="quoteUom-Price" value="${product.price.value}" />
	<input type="hidden" class="quoteUom-Measure" value="${not empty uomMeasure ? uomMeasure : 'Each'}" />
	<input type="hidden" class="quoteUom-code" value="${product.code}" />
	<c:if test="${empty showAddToCart ? true : showAddToCart}">
		<c:set var="buttonType">button</c:set>
		<c:if test="${product.variantType ne 'GenericVariantProduct' and product.price ne '' }">
			<c:set var="buttonType">submit</c:set>
		</c:if>
		<div id="addToCartSection" class="row">
			<c:choose>
				<c:when test="${fn:contains(buttonType, 'button')}">
					<c:choose>
						<c:when test="${product.variantType eq 'GenericVariantProduct' && product.variantCount != 1}">
							<!-- 1 -->
							<div class="list-button-row">
								<button type="${buttonType}" id="variantButton" class="btn btn-primary btn-block js-add-to-cart">
									<spring:theme code="product.base.select.options" />
								</button>
							</div>
						</c:when>
						<c:otherwise>
							<ycommerce:testId code="addToCartButton">
								<!-- 2 -->
								<button type="${(orderOnlinePermissions eq true)?buttonType:'button'}" id="${(orderOnlinePermissions eq true)?'addToCartButton':ATCOOId}" class="btn btn-primary btn-block pull-left js-add-to-cart  js-enable-btn buy-again-atc" disabled="disabled">
									<spring:theme code="basket.add.to.basket" />
								</button>
							</ycommerce:testId>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<ycommerce:testId code="addToCartButton">
						<div class="list-button-row">
							<div id="productSelect" class="margin15">
								<c:choose>
									<c:when test="${product.sellableUomsCount == 0}">
										<c:choose>
											<c:when test="${(!product.isSellable)}">
												<!-- 3 -->
												<!-- <button type="submit" id="notSellable" class="btn btn-primary pull-left btn-block buy-again-atc ${currentLanguage.isocode eq 'es' ? 'showAddtoCart-es' : ''}" >
													<spring:theme code="basket.add.to.basket" />
												</button> -->
													<button
														class="col-md-12 col-xs-12 btn btn-primary pull-left btn-block requestQuoteBtnPDP buy-again-atc ${currentLanguage.isocode eq 'es' ? 'showAddtoCart-es' : ''}"id="notSellable" data-product-description="${fn:escapeXml(product.name)}"
														onclick="ACC.savedlist.requestQuotePopupplp(this,'add_to_cart_form','${product.code}');ACC.adobelinktracking.requestQuotePLP(this,'','','${cmsPage.name}','true');event.preventDefault();">
														<spring:theme code="text.lists.requestQuote" />
													</button>
												<c:if test="${!isOrderingAccount}">
													<span class="alert_msg hidden">${orderingAccountMsg}</span>
												</c:if>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${product.hideCSP eq true}">
														<!-- 4 -->
														<button type="submit" id="showAddtoCart" class="btn btn-primary btn-block buy-again-atc ${currentLanguage.isocode eq 'es' ? 'showAddtoCart-es' : ''}" disabled="disabled">
															<spring:theme code="basket.add.to.basket" />
														</button>
													</c:when>
													<c:otherwise>
														<!-- 5 -->
														<button type="${(orderOnlinePermissions eq true)?'submit':'button'}" id="${(orderOnlinePermissions eq true)?'showAddtoCart':ATCOOId}" class="btn btn-primary btn-block js-enable-btn buy-again-atc ${currentLanguage.isocode eq 'es' ? 'showAddtoCart-es' : ''}" disabled="disabled">
															<spring:theme code="basket.add.to.basket" />
														</button>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${product.hideList ne true}">
												<c:choose>
													<c:when test="${product.hideCSP ne true}">
														<c:choose>
															<c:when test="${isStockAvailable && !product.outOfStockImage}">
																<!-- 6 -->
																<button type="${(orderOnlinePermissions eq true)?'submit':'button'}" id="${(orderOnlinePermissions eq true)?'showAddtoCart':ATCOOId}" class="btn btn-primary btn-block js-enable-btn buy-again-atc ${currentLanguage.isocode eq 'es' ? 'showAddtoCart-es' : ''}" disabled="disabled">
																	<spring:theme code="basket.add.to.basket" />
																</button>
															</c:when>
															<c:otherwise>
																<!-- 7 -->
																<!-- <button type="submit" id="showAddtoCart" class="btn btn-primary btn-block buy-again-atc ${currentLanguage.isocode eq 'es' ? 'showAddtoCart-es' : ''}" disabled="disabled">
																	<spring:theme code="basket.add.to.basket" />
																</button> -->
																<button id="showAddtoCart"
																	class="col-md-12 col-xs-12 btn btn-primary pull-left btn-block requestQuoteBtnPDP buy-again-atc ${currentLanguage.isocode eq 'es' ? 'showAddtoCart-es' : ''}"
																	data-product-description="${fn:escapeXml(product.name)}"
																	onclick="ACC.savedlist.requestQuotePopupplp(this,'add_to_cart_form','${product.code}');ACC.adobelinktracking.requestQuotePLP(this,'','','${cmsPage.name}','true');event.preventDefault();">
																	<spring:theme code="text.lists.requestQuote" />
																</button>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<!-- 8 -->
														<button type="submit" id="showAddtoCart" class="btn btn-primary btn-block buy-again-atc ${currentLanguage.isocode eq 'es' ? 'showAddtoCart-es' : ''}" disabled="disabled">
															<spring:theme code="basket.add.to.basket" />
														</button>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${product.hideCSP ne true}">
														<c:choose>
															<c:when test="${isStockAvailable && !product.outOfStockImage}">
																<!-- 9 -->
																<button type="${(orderOnlinePermissions eq true)?'submit':'button'}" id="${(orderOnlinePermissions eq true)?'showAddtoCart':ATCOOId}"class="btn btn-primary btn-block js-enable-btn buy-again-atc ${currentLanguage.isocode eq 'es' ? 'showAddtoCart-es' : ''}" disabled="disabled">
																	<spring:theme code="basket.add.to.basket" />
																</button>
															</c:when>
															<c:otherwise>
																<!-- 10 -->
																<button type="submit" id="showAddtoCart" class="btn btn-primary btn-block buy-again-atc ${currentLanguage.isocode eq 'es' ? 'showAddtoCart-es' : ''}" disabled="disabled">
																	<spring:theme code="basket.add.to.basket" />
																</button>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<!-- 11 -->
														<button type="submit" id="showAddtoCart" class="btn btn-primary btn-block buy-again-atc ${currentLanguage.isocode eq 'es' ? 'showAddtoCart-es' : ''}" disabled="disabled">
															<spring:theme code="basket.add.to.basket" />
														</button>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${(!product.isSellable)}">
												<!-- 12 -->
												<button type="submit" id="showAddtoCartUom" class="btn btn-primary pull-left btn-block buy-again-atc ${currentLanguage.isocode eq 'es' ? 'showAddtoCart-es' : ''}" style="display:none;margin-top:0px" aria-disabled="true" disabled="disabled">
													<spring:theme code="basket.add.to.basket" />
												</button>
												<c:if test="${!isOrderingAccount}">
													<span class="alert_msg hidden" id="orderingAccountMsg">${orderingAccountMsg}</span>
												</c:if>
											</c:when>
											<c:otherwise>
												<!-- 13 -->
												<button type="${(orderOnlinePermissions eq true)?'submit':'button'}" id="${(orderOnlinePermissions eq true)?'showAddtoCartUom':ATCOOId}" class="btn btn-primary btn-block js-enable-btn buy-again-atc" style="display:none;margin-top:0px" disabled="disabled">
													<spring:theme code="basket.add.to.basket" />
												</button>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')">
							<input type="hidden" name="isCurrentUser" id="isCurrentUser" value="true" />
						</sec:authorize>
					</ycommerce:testId>
				</c:otherwise>
			</c:choose>
		</div>
	</c:if>
</form:form>