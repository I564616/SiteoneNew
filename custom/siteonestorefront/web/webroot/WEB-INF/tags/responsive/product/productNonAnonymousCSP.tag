<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ attribute name="isMyStoreProduct" type="java.lang.String" %>
<%@ attribute name="productCSPCuratedplp" type="java.lang.String" %>
<%@ attribute name="displayFlag" type="java.lang.Boolean" %>
<c:choose>
	<c:when test="${product.variantCount ge 1}">
		<div class="customerSpecificPrice-wrapper">
			<a href="#" onclick="return false;" id="customerSpecificPriceLink${product.code}"
				class="customerSpecificPriceLink" data-index="${loop}" data-productcode="${product.code}"
				data-is-sellable="${product.isSellableInventoryHit}" data-is-regulateditem="${product.isRegulateditem}"
				data-is-orderingaccount="${isOrderingAccount}" data-is-storeproduct="${isMyStoreProduct}"
				data-is-productsellable="${isProductSellable}" data-is-licensed="${sessionStore.isLicensed}">
				<spring:theme code="text.product.listing.Price" />
			</a> <input type="hidden" id="customerSpecificPriceValue${loop}" />
			<!-- <c:if test="${displayFlag eq true}">
				<div id="callForPriceEnabled${product.code}" style="display: none">
					<p class="callBranchForPrice">
						<spring:theme code="text.product.callbranch" />
					</p>
				</div>
			</c:if> -->
			<div class="check_price" id="customerSpecificPriceSection${product.code}" style="display: none">
				<h5 class="fontBold">
					<spring:theme code="text.product.your.price.PLP" />
				</h5>
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
			<c:when test="${(not empty product.customerPrice && product.customerPrice.value ne '0.0')}">
				<div id="mycspDiv${product.code}">
					<input type="hidden" value="${product.code}" class="productcspCode" />
					<input type="hidden" value="${product.price.value}" class="productRetailPrice${product.code}" />
					<input type="hidden" value="${product.isStockAvailable}" class="isStockAvailablecsp${product.code}" />
					<input type="hidden" value="${product.customerPrice.value}" class="csp${product.code}" />
					<input type="hidden" value="${product.isRegulateditem}" class="isRegulateditemcsp${product.code}" />
					<input type="hidden" value="${isMyStoreProduct}" class="isMyStoreProduct${product.code}" />
					<input type="hidden" value="${sessionStore.isLicensed}" class="isLicensed${product.code}" /> 
					<input type="hidden" value="${isOrderingAccount}" class="isOrderingAccount${product.code}" /> 
					<input type="hidden" value="${product.isSellableInventoryHit}" class="isSellableInventoryHit${product.code}" />
					<div class="customerSpecificPrice-wrapper">
						<div class="check_price">
							<h5 class="fontBold">
								<spring:theme code="text.product.your.price.PLP" />
							</h5>
							<div class="cl"></div>
							<c:choose>
								<c:when test="${productCSPCuratedplp == 'Curatedplp'}">
									<product:ProductCSPListerItemsCuratedPLP product="${product}" />
								</c:when>
								<c:otherwise>
									<product:ProductCSPListerItemsPLP product="${product}" />
								</c:otherwise>
							</c:choose>
							<div class="cl"></div>
							<span id="description${loop}"></span>
						</div>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<!-- <c:if test="${displayFlag eq true}">
					<p class="callBranchForPrice">
						<spring:theme code="text.product.callbranch" />
					</p>
				</c:if> -->
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>
