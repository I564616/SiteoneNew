<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>

<spring:theme code="text.addToCart" var="addToCartText"/>
<spring:theme code="text.popupCartTitle" var="popupCartTitleText"/>
<c:url value="/cart" var="cartUrl"/>
<c:url value="/cart/checkout" var="checkoutUrl"/>

<c:choose>
	<c:when test="${not empty cartData.quoteData}">
		<c:set var="miniCartProceed" value="quote.view"/>
	</c:when>
	<c:otherwise>
		<c:set var="miniCartProceed" value="cart.checkout"/>
	</c:otherwise>
</c:choose>
			

<div class="mini-cart js-mini-cart">

	<ycommerce:testId code="mini-cart-popup">
		<div class="mini-cart-body">
			<c:choose>
				<c:when test="${numberShowing > 0 }">
					<h3 class="minicart-title"><spring:theme code="basket.added.to.basket" /></h3>
						<div class="legend itemCountWrapper">
							<spring:theme code="popup.cart.showing" arguments="${numberShowing},${numberItemsInCart}"/>
							<%-- <c:if test="${numberItemsInCart > numberShowing}">
								<a href="${cartUrl}"><spring:theme code="popup.cart.showall"/></a>
							</c:if> --%>
						</div>
<div class="mini-cart-scroll">
						<ol class="mini-cart-list">
							<c:forEach items="${entries}" var="entry" end="${numberShowing - 1}">
								<c:url value="${entry.product.url}" var="entryProductUrl"/>
								<li class="mini-cart-item">
								<div class="row mini-cart-row">
									<div class="thumb col-md-3">
										<a href="${entryProductUrl}">
											<product:productPrimaryImage product="${entry.product}" format="cartIcon"/>
										</a>
									</div>
									<div class="cart-detail col-md-5 col-xs-5">
										<a class="name" href="${entryProductUrl}">${fn:escapeXml(entry.product.name)}</a>
										<div class="qty"><spring:theme code="popup.cart.quantity"/>: ${entry.quantity}</div>
										<div class="itemNumber"> ${entry.product.itemNumber}</div>
										<%-- <c:if test="${entry.product.stock.stockLevel gt 0}">
											<div class="stock"> In stock</div>
										</c:if> --%>
										
								<c:if test="${entry.product.baseProduct != null}">
					                   <c:forEach items="${entry.product.categories}" var="option">
						                 ${option.parentCategoryName}:${option.name}<br/>
					                  </c:forEach>
				                    </c:if>
										
										<c:forEach items="${entry.product.baseOptions}" var="baseOptions">
											<c:forEach items="${baseOptions.selected.variantOptionQualifiers}" var="baseOptionQualifier">
												<c:if test="${baseOptionQualifier.qualifier eq 'style' and not empty baseOptionQualifier.image.url}">
													<div class="itemColor">
														<span class="label"><spring:theme code="product.variants.colour"/></span>
														<img src="${baseOptionQualifier.image.url}" alt="${baseOptionQualifier.value}" title="${baseOptionQualifier.value}"/>
													</div>
												</c:if>
												<c:if test="${baseOptionQualifier.qualifier eq 'size'}">
													<div class="itemSize">
														<span class="label"><spring:theme code="product.variants.size"/></span>
															${baseOptionQualifier.value}
													</div>
												</c:if>
											</c:forEach>
										</c:forEach>
										<c:if test="${not empty entry.deliveryPointOfService.name}">
											<div class="itemPickup"><span class="itemPickupLabel"><spring:theme code="popup.cart.pickup"/></span>&nbsp;${entry.deliveryPointOfService.name}</div>
										</c:if>
									
									
                                </div>
                                <div class="cart-detail col-md-3 col-xs-5">
                                <c:choose>
                						<c:when test="${ycommerce:doesAppliedPromotionExistForOrderEntry(cartData, entry.entryNumber)}">
                    						<c:forEach items="${cartData.appliedProductPromotions}" var="promotion">
                       						 <c:set var="displayed" value="false"/>
                        					   <c:forEach items="${promotion.consumedEntries}" var="consumedEntry">
                           						 <c:if test="${not displayed && consumedEntry.orderEntryNumber == entry.entryNumber}">
                               						 <c:set var="displayed" value="true"/>
                                	<div>
									<c:choose>
											<c:when test="${consumedEntry.adjustedUnitPrice == null || (consumedEntry.adjustedUnitPrice != null && 
													consumedEntry.adjustedUnitPrice.toString() == entry.basePrice.value.toString())}"> 
										        <format:price priceData="${entry.basePrice}"
																			   displayFreeForZero="true"/>
										    </c:when>
										<c:otherwise>
										<div class="mini-cart-newprice b-price"> $<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${entry.totalPrice.value/entry.quantity}"/></div>
										   <del> <format:price priceData="${entry.basePrice}"
																			   displayFreeForZero="true"/></del>
									     </c:otherwise>
									</c:choose>
                                    </div>
                                              </c:if>
                                            </c:forEach>
                                         </c:forEach>
                                     </c:when>
                                     <c:otherwise>
                                       <c:set var="zero" value="0.0"/>
                                     <c:if test="${entry.basePrice.value.toString() != zero}">
                                        <format:price priceData="${entry.basePrice}" displayFreeForZero="false"/>
                                     </c:if>
                                    </c:otherwise>
                                </c:choose>
                                </div>
                                </div>
								</li>
								
							</c:forEach>
						</ol>
						<div class="cl"></div>
					</div>
						
						<div class="cl"></div>
						 <div class="margin20">
						  <div class="">
						  
						  <div class="col-sm-6 continue-shopping-wrapper">
						<a href="" class="btn btn-primary  btn-block js-mini-cart-close-button">
							<spring:theme text="Continue Shopping" code="cart.page.continue"/>
						</a>
						</div>
						  <div class="col-sm-6 checkout-wrapper">
						<a href="${cartUrl}" class="btn btn-default btn-block mini-cart-checkout-button">
							<spring:theme code="${miniCartProceed }" />
						</a>
						</div>
						
						</div>
						</div>
				</c:when>

				<c:otherwise>
					<h3 class="minicart-title"><spring:theme code="text.minicart.empty.message" /></h3>
					<div class="margin20">
						  <div class="">
						
					 <div class="col-sm-6">
					<a href="" class="btn btn-primary btn-block js-mini-cart-close-button">
						<spring:theme code="cart.page.continue"/>
					</a>
					</div>
					
					  <div class="col-sm-6">
					<a href="${cartUrl}" class="btn btn-default btn-block mini-cart-checkout-button">
							<spring:theme code="${miniCartProceed }" />
					</a>
					</div>
					</div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</ycommerce:testId>
</div>


