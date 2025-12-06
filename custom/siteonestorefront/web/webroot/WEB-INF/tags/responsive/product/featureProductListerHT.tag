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
				<c:if test="${!product.isRegulateditem   && !product.isProductDiscontinued}">
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
<div class="product-item col-xs-12 col-md-12">
<c:if test="${product.couponEnabled eq true}">
      <div class="couponBadgeRecomm"><common:clipCouponPLP></common:clipCouponPLP></div>
</c:if>
<div class="product-item-box unset_border produc-recomm-item">	
<analytics:GA4HiddenFields product="${product}"/>
<input type="hidden" id="clickTrackingURL" name="clickTrackingURL" value="${(product.clickTrackingURL)}"/>
	<ycommerce:testId code="product_wholeProduct">
		<a class="thumb recomm-analytics js-click-tracking" href="${productUrl}${fn:escapeXml(isrc)}" title="${fn:escapeXml(product.productShortDesc)}">
			<product:productPrimaryImage product="${product}" format="product"/>
		</a>
		<div class="details">

			<ycommerce:testId code="product_productName">
			<div class="four-line-text">
					<a class="green-title black-text recomm-analytics js-click-tracking" title="${fn:escapeXml(product.productShortDesc)}" href="${productUrl}${fn:escapeXml(isrc)}">
					<c:choose>
						<c:when test="${fn:length(product.productShortDesc) > 85}">
							<c:out value="${fn:substring(product.productShortDesc, 0, 85)}..."/>
						</c:when>
						<c:otherwise>
							<c:out value="${product.productShortDesc}" />
						</c:otherwise>
					</c:choose>
				</a>
			</div>
				
		
			</ycommerce:testId>
	
		
		<div class="customerSpecificPrice-wrapper homepage-csp">
			<c:if test="${!product.multidimensional || product.variantCount ge 1}">
		<sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')">
		<div class="${strikeListPrice ? 'strike-list-price ' : ''}">
			<ycommerce:testId code="product_productPrice">
				<span id="promotionlabel_${loop}"></span>
				<div class="price">
					<c:if test="${(not empty product.price && product.price.value ne '0.0')|| not empty product.priceRange.minPrice}">
						<c:if test="${product.inventoryFlag ne true}">
							<c:choose><c:when test="${hideList ne true}">
							<c:if test="${product.price.value gt product.customerPrice.value}">
								 <!--  <div class="fontBold list-text"><spring:theme code="text.product.siteOnelist.price"/></div>-->
								 </c:if>
							</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:if>
					<c:if test="${(not empty product.price && product.price.value ne '0.0') || not empty product.priceRange.minPrice}">
						<div class="productPriceWrapper">
							<div class="price-container">
								<span>
									<span id="salePrice${loop}" class="discount sales-price"></span>
								</span>

								<span id="basePrice${loop}">
								 <c:choose>
								 	<c:when test="${product.inventoryFlag eq true}">
								 	<p class="callBranchForPrice price-position"> <spring:theme code="text.product.callforpricing"/>
						    			
								    </c:when>
								    <c:otherwise>
								        <c:choose>
									        <c:when test="${hideList ne true}">
									        <c:if test="${product.price.value gt product.customerPrice.value}">
									        <!--<product:productRecommItemPrice	product="${product}" />-->
									        </c:if>
								             </c:when>
									         <c:otherwise>
									         </c:otherwise>
								          </c:choose>
								          </c:otherwise>
								          </c:choose>
							 </span>
							</div>
						</div>
					</c:if>
					<div class="csperror display-none" id="cspError${loop}"></div>
				</div>
			</ycommerce:testId>
			
			<div class="cl"></div>

			
			<c:if test="${product.inventoryFlag ne true }">
				<c:choose>
					<c:when test="${hideCSP ne true}">
						<c:choose>
							<c:when test="${product.variantCount ge 1}">
								<div class="customerSpecificPrice-wrapper col-md-12">
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
										<spring:theme code="text.product.your.price" />
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
											<input type="hidden" value="${product.customerPrice.value}" class="csp${product.code}"/>
											<input type="hidden" value="${product.isRegulateditem}" class="isRegulateditemcsp${product.code}" />
											<input type="hidden" value="${isMyStoreProduct}" class="isMyStoreProduct${product.code}" />
											<input type="hidden" value="${sessionStore.isLicensed}" class="isLicensed${product.code}" />
											<input type="hidden" value="${isOrderingAccount}" class="isOrderingAccount${product.code}" />
											<input type="hidden" value="${product.isSellableInventoryHit}" class="isSellableInventoryHit${product.code}" />

											<div class="customerSpecificPrice-wrapper col-md-12">
												<div class="check_price">
													<spring:theme code="text.product.your.price" />
													<div class="cl"></div>
													<product:ProductCSPRecommItems product="${product}" />
													<div class="cl"></div>
													<span id="description${loop}"></span>
												</div>
											</div>
										</div>
									</c:when>
									<c:otherwise>
										<p class="callBranchForPrice price-position">
										<!--<spring:theme code="text.product.callforpricing" />-->
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<p class="callBranchForPrice price-position">
						<!--<spring:theme code="text.product.callforpricing" />-->
					</c:otherwise>
				</c:choose>
			</c:if>
			</div>
		</sec:authorize>
	</c:if>

	<c:if test="${!product.multidimensional || product.variantCount ge 1}">
		<sec:authorize access="hasAnyRole('ROLE_ANONYMOUS')">
			<c:choose>
				<c:when test="${null != cookie['j_username'] && cookie['j_username'].value != ''}"> <%--softlogin new code to show price for session timeout--%>
					<c:if test="${product.inventoryFlag ne true }">
						<c:choose>
							<c:when test="${hideCSP ne true}">
								<c:choose>
									<c:when test="${product.variantCount ge 1}">
										<div class="customerSpecificPrice-wrapper col-md-12">
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
												<spring:theme code="text.product.your.price" />
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
													<input type="hidden" value="${product.customerPrice.value}" class="csp${product.code}"/>
													<input type="hidden" value="${product.isRegulateditem}" class="isRegulateditemcsp${product.code}" />
													<input type="hidden" value="${isMyStoreProduct}" class="isMyStoreProduct${product.code}" />
													<input type="hidden" value="${sessionStore.isLicensed}" class="isLicensed${product.code}" />
													<input type="hidden" value="${isOrderingAccount}" class="isOrderingAccount${product.code}" />
													<input type="hidden" value="${product.isSellableInventoryHit}" class="isSellableInventoryHit${product.code}" />

													<div class="customerSpecificPrice-wrapper col-md-12">
														<div class="check_price">
															<spring:theme code="text.product.your.price" />
															<div class="cl"></div>
															<product:ProductCSPRecommItems product="${product}" />
															<div class="cl"></div>
															<span id="description${loop}"></span>
														</div>
													</div>
												</div>
											</c:when>
											<c:otherwise>
												<p class="callBranchForPrice price-position">
												<!--<spring:theme code="text.product.callforpricing" />-->
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<p class="callBranchForPrice price-position">
								<!--<spring:theme code="text.product.callforpricing" />-->
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:when>
				<c:otherwise> <%--softlogin new code anonymous--%>

					<ycommerce:testId code="product_productPrice">
						<span id="promotionlabel_${loop}"></span>
						<div class="price">
						<c:if test="${(not empty product.price && product.price.value ne '0.0')|| not empty product.priceRange.minPrice}">
							<c:if test="${product.inventoryFlag ne true}">
								<c:choose>
									<c:when test="${hideList ne true}">
									<div class="fontBold list-text"><spring:theme code="text.product.siteOnelist.price"/></div>
								</c:when>
								<c:otherwise>
								</c:otherwise>
								</c:choose>
							</c:if>
						</c:if>
						<c:if test="${(not empty product.price && product.price.value ne '0.0') || not empty product.priceRange.minPrice}">
							<div class="productPriceWrapper">
							<div class="price-container">
							<span>
								<span id="salePrice${loop}" class="discount sales-price"></span>
								</span>
							<span id="basePrice${loop}">
							<c:choose>
								<c:when test="${product.inventoryFlag eq true}">
									<c:choose>
										<c:when test="${hideList ne true}">
											<c:choose>
												<c:when test="${hideCSP ne true }">
													<p class="callBranchForPrice price-position"> <spring:theme code="text.product.callforpricing"/>
												</c:when>
												<c:otherwise>
													<p class="callBranchForPrice price-position"> <spring:theme code="text.product.callforpricing"/>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${hideCSP ne true }">
													<p class="callBranchForPrice price-position"> <spring:theme code="text.product.callforpricing"/>
												</c:when>
												<c:otherwise>
													<p class="callBranchForPrice price-position"> <spring:theme code="text.product.callforpricing"/>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose><c:when test="${hideList ne true}">
										<product:productRecommItemPrice	product="${product}" />
									</c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>

							</span>
							</div>
							</div>
						</c:if>
						<div class="csperror display-none" id="cspError${loop}"></div>
						</div>
					</ycommerce:testId>
					
					<div class="row">
						<div class="col-md-12 login-price-plp carousel-login-btn-wrapper">
							<c:if test="${product.inventoryFlag ne true }">
							<c:choose><c:when test="${hideCSP ne true}">
							<a href="<c:url value="/login"/>" class="logInToSeeYourPrice signInOverlay carousel-login-btn"><spring:theme code="text.product.logInToSeeYourPricetext"/></a>
							</c:when>
							<c:otherwise>
							<p class="callBranchForPrice"> <spring:theme code="text.product.callforpricing"/>
								</c:otherwise>
								</c:choose>
								</c:if>

						</div>
					</div>
				</c:otherwise> <%--//anonymous no price--%>
			</c:choose><%--/anonymous no price--%>
		</sec:authorize>
	</c:if>
	</div> 
	<div class="col-xs-12 col-md-12 hidden-sm">
	<!-- AddToCart scenario -->
	<c:choose>
		<c:when test="${isNotInStock && (!product.multidimensional)}">
			<div class="row">
				<div class="col-xs-12 col-sm-12 col-md-12 ">
					<div class="flex-center justify-center grid-contact-branch bg-lightGrey">
						<div class="call-branch-icon-text"><spring:theme code="text.contact.your.branch"/></div>
						<div class="call-branch-icon-number hide">${contactNo}</div>
					</div>
				</div>
			</div>

		</c:when>
		<c:otherwise>
		<c:set var="product" value="${product}" scope="request"/>
			<c:set var="addToCartText" value="${addToCartText}" scope="request"/>
			<c:set var="addToCartUrl" value="${addToCartUrl}" scope="request"/>
			<c:set var="isGrid" value="true" scope="request"/>
	 <c:url value="/cart/add" var="addToCartUrl"/>
    <c:url value="${product.url}/configuratorPage/${configuratorType}" var="configureProductUrl"/>
    <input type="hidden" class="trackProductCode" name="trackProductCode" value="${product.code}">
    <input type="hidden" class="trackRetailPrice" name="trackRetailPrice" value="${product.price.value}">
    <input type="hidden" class="trackCSP" name="trackCSP" value="${product.customerPrice.value}">
	<c:set var="hideList" value="${product.hideList}"/>
	<c:set var="hideCSP" value="${product.hideCSP}"/>
	<c:set var="buttonID" value="listPageAddToCart_${product.code}" />
    <c:set var="buttonIDOO" value="orderOnlineATC_${product.code}" />
    <c:set var="sellableUomListLen" value="${fn:length(product.sellableUoms)}" />
	  <c:if test="${(not product.multidimensional || product.variantCount == 1)}">
	    <c:set var="addIsMyStoreProduct" value="false" />
	        <c:set var="onlyHubStoreAvailability" value="false" />
	        <c:forEach items="${product.stores}" var="store">
	            <c:if test="${store eq product.nearestStore.storeId}">
	                <c:set var="addIsMyStoreProduct" value="true" />
	            </c:if>
	        </c:forEach>
	        <c:if test="${product.isSellableInventoryHit eq true}">
	            <c:set var="addIsMyStoreProduct" value="true" />
	        </c:if>
	        <c:forEach items="${product.stores}" var="store">
	            <c:if
	                test="${addIsMyStoreProduct eq false && (product.isShippable && !empty sessionStore.hubStores && store eq sessionStore.hubStores[0])}">
	                <c:set var="addIsMyStoreProduct" value="true" />
	                <c:set var="onlyHubStoreAvailability" value="true" />
	            </c:if>
	        </c:forEach>
	
	        <c:set var="addIsProductSellable" value="false" />
	        <c:if test="${product.isRegulateditem}">
	            <c:forEach items="${product.regulatoryStates}" var="regulatoryStates">
	                <c:if test="${regulatoryStates eq sessionStore.address.region.isocodeShort}">
	                    <c:set var="addIsProductSellable" value="true" />
	                </c:if>
	            </c:forEach>
	            </c:if>
	            <c:set var="isSellableInventoryHit" value="false" />
	        	<c:if test="${product.isSellableInventoryHit eq true}">
	            	<c:set var="isSellableInventoryHit" value="true" />
	        	</c:if>
      </c:if>
	<c:choose>
			<c:when test="${(isAnonymous and not product.multidimensional and (hideList eq true || empty product.price ) and hideCSP ne true) or (isAnonymous and isGuestCheckoutEnabled eq false)}">
				<button type="submit" data-prod-code="${product.code}" class="btn btn-primary btn-block js-login-to-buy"><spring:theme code="login.to.buy" /></button>
				<product:listaddtocartparameters sellableUomListLen="${sellableUomListLen}" addIsMyStoreProduct="${addIsMyStoreProduct}" onlyHubStoreAvailability="${onlyHubStoreAvailability}" addIsProductSellable="${addIsProductSellable}" isSellableInventoryHit="${isSellableInventoryHit}"/>
			</c:when>
			
			<c:otherwise>
			
	<form:form id="addToCartForm${product.code}" action="${addToCartUrl}" onsubmit="clickTracking(this)" method="post" class="add_to_cart_form">
		<input type="hidden" id="clickTrackingURL" name="clickTrackingURL" value="${(product.clickTrackingURL)}"/>
		<product:listaddtocartparameters sellableUomListLen="${sellableUomListLen}" addIsMyStoreProduct="${addIsMyStoreProduct}" onlyHubStoreAvailability="${onlyHubStoreAvailability}" addIsProductSellable="${addIsProductSellable}" isSellableInventoryHit="${isSellableInventoryHit}"/>
	<c:if test="${(not product.multidimensional || product.variantCount == 1)}">
		<input type="hidden" name="productPostPLPQty" id="productPLPPostQty_${product.code}" value="1">	
        <ycommerce:testId code="addToCartButton">
            
            <!-- <div class="PLPQuantitySelector col-md-6 col-xs-12"> -->
            <div class="row">
                      
                    <c:choose>
                        <c:when test="${((product.isRegulateditem && addIsMyStoreProduct && !addIsProductSellable && !sessionStore.isLicensed) || (!addIsMyStoreProduct) || (empty product.price && empty product.priceRange.minPrice  && empty product.customerPrice) || (!(empty product.price) && product.price.value.toString() eq 0.0) || (!(empty product.customerPrice) )|| (!isOrderingAccount))}">

                        <div class="col-md-12 col-xs-12">
                            <button type="${(orderOnlinePermissions eq true)?'submit':'button'}" class="btn btn-primary btn-block btn-carousel_addtocart js-atc-${product.code}" ${(orderOnlinePermissions eq true)?"":"name=atclistoo"}
                                    id="${(orderOnlinePermissions eq true)?buttonID:buttonIDOO}" aria-disabled="true" disabled="disabled">
                                <spring:theme code="basket.add.to.Basket"/>

                            </button>
                            <c:if test="${!isOrderingAccount}">
                                        <span class="orderAccountingMsgPLP">${orderingAccountMsg}</span>
                            </c:if>
                        </div>
                        </c:when>
                        <c:otherwise>
                          
                        <c:choose><c:when test= "${(hideCSP eq true)}">
                        <div class="col-md-12 col-xs-12">
                            <button type="${(orderOnlinePermissions eq true)?'submit':'button'}" class="btn btn-primary btn-block btn-carousel_addtocart js-atc-${product.code}" ${(orderOnlinePermissions eq true)?"":"name=atclistoo"}
                                id="${(orderOnlinePermissions eq true)?buttonID:buttonIDOO}" aria-disabled="true" disabled="disabled">
                                    <spring:theme code="basket.add.to.Basket"/>
                            </button>
                        </div>
                        </c:when><c:otherwise>
            
            
                   <div class="col-md-12 col-xs-12">
                        <button type="${(orderOnlinePermissions eq true)?'submit':'button'}" class="btn btn-primary btn-block btn-carousel_addtocart js-enable-btn js-atc-${product.code}" ${(orderOnlinePermissions eq true)?"":"name=atclistoo"}
                            id="${(orderOnlinePermissions eq true)?buttonID:buttonIDOO}" disabled="disabled">
                            <spring:theme code="basket.add.to.Basket"/>
                        </button>
                    </div>
                    </c:otherwise></c:choose>
                   
                    
                </c:otherwise>
            </c:choose>
           
           </div>
        </ycommerce:testId>   
</c:if>
 <c:if test="${product.multidimensional && product.variantCount > 1}" >
	 <div class="atc-variant-button">
        <div class="orderProductFrom btn btn-block btn-default" name="sellableUomsCountValue">
            <!-- <button type="button" class="orderProductFrom btn btn-block btn-default" name="sellableUomsCountValue"> -->
                <c:url value="${product.url}" var="productUrl"/>
                <a href="${productUrl}" class="recomm-analytics recomm-multivar" style="color: white; text-decoration: none;"><spring:theme code="featureProductList.selectFrom" /> ${product.variantCount} <spring:theme code="featureProductList.products" /></a>
            <!-- </button> -->
        </div>
    </div>              
</c:if>

</form:form>
</c:otherwise>
</c:choose>
</c:otherwise>
</c:choose>
	</div>
</div>		
	</ycommerce:testId>
</div>
</div>