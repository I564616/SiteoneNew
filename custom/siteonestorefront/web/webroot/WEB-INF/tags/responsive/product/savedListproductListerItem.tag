
<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="totalPrice" required="true" type="de.hybris.platform.commercefacades.product.data.PriceData" %>
<%@ attribute name="hidePrice" required="true" type="java.lang.Boolean" %>
<%@ attribute name="quantity" required="true" type="java.lang.Integer" %>
<%@ attribute name="code" required="false" type="java.lang.Long" %>
<%@ attribute name="comment" required="false" type="java.lang.String" %>
<%@ attribute name="uomid" required="false" type="java.lang.Integer" %>
<%@ attribute name="indexvalue" required="false" type="java.lang.String" %>
<%@ attribute name="uomDesc" required="false" type="java.lang.String" %>
<%@ attribute name="isModified" required="false" type="java.lang.Boolean" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<c:set var="showingCoupon" value="false" />
<c:set var="iconWidth" value="24" />
<c:set var = "userAgent" value = "${fn:toLowerCase(header['User-Agent'])}"/>
<%-- setting locale to 'en_US' to format price with dot for decimal values and comma as grouping separator  
     reset to current locale in the end of page  --%>
<fmt:setLocale value="en_US" scope="session"/>
<c:set var="totalpriceDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.totalprice.digits\")%>" />
<c:set var="productQuantity_list" value="1"/> 
<c:if test="${product.orderQuantityInterval eq null || product.orderQuantityInterval eq '0' || product.orderQuantityInterval eq ''}">
        <c:set var="productQuantity_list" value="1"/> 
 </c:if>
 <c:if test="${product.orderQuantityInterval ne null && product.orderQuantityInterval ne '0' && product.orderQuantityInterval ne ''}">
        <c:set var="productQuantity_list" value="${product.orderQuantityInterval}"/> 
 </c:if>
<c:if test="${product.minOrderQuantity ne null && product.minOrderQuantity ne '0' && product.minOrderQuantity ne ''}">
	<c:set var="productQuantity_list" value="${product.minOrderQuantity}" />
</c:if>
<input type="hidden" value="${product.code}" class="productID_assemblyList"/> 
<input type="hidden" name="selectedProducts" class="selectedProducts" value="${selectedProducts}" />
<c:choose>
	<c:when test="${empty cookie['gls'] && empty cookie['csc']}">
		<c:set var="contactNo" value="<%=de.hybris.platform.util.Config.getParameter(\"siteOne.Customer.Service.Number\")%>"/>		
	</c:when>
	<c:otherwise>
		<c:set var="contactNo" value="${sessionStore.address.phone}"/>
	</c:otherwise>
</c:choose>
<spring:theme code="text.addToCart" var="addToCartText"/>
<c:url value="${product.url}" var="productUrl"/>
<input type="hidden" id="variantisForceinstock" value="${product.isForceInStock}"/>
<input type="hidden" id="modifiedStatus" value="${isModified}"/>
<c:set value="${not empty product.potentialPromotions}" var="hasPromotion"/>
<c:choose>
	<c:when	test="${product.productType eq 'Nursery' and (quantity gt product.stock.stockLevel) and (product.inStockImage)}">
		<c:set var="showErrorMsg" value="hide" />
		<c:set var="disableAtc" value="" />
		<c:set var="redBorder" value=""/>	
	</c:when>
	<c:otherwise>
		<c:set var="showErrorMsg" value="hide" />
		<c:set var="disableAtc" value="" />
		<c:set var="redBorder" value=""/>
	</c:otherwise>
</c:choose>
<c:set var="hardscapeMoreOnWayMsgList" value="false"/>
<c:if test="${product.isEligibleForBackorder eq true and product.inventoryCheck eq true and(fn:escapeXml(product.level2Category) == 'Manufactured Hardscape Products' || fn:escapeXml(product.level2Category) == 'Productos de Paisajismo Manufacturados' || fn:escapeXml(product.level2Category) == 'Natural Stone' || fn:escapeXml(product.level2Category) == 'Piedra Natural' || fn:escapeXml(product.level2Category) == 'Outdoor Living' || fn:escapeXml(product.level2Category) == 'Vida al Aire Libre')}">
	<c:set var="hardscapeMoreOnWayMsgList" value="true"/>
</c:if>
<input type="hidden" name="hardscapemoreonwaylist" value="${hardscapeMoreOnWayMsgList}" />
<input type="hidden" name="productl2" value="${product.level2Category}" />

<ycommerce:testId code="test_searchPage_wholeProduct">
	<!-- Check : List Details -->
	<div class="colored padding-md-10 no-padding-xs width-100-px-xs list-check list-Checkbox hidden-print">
		<input type="hidden" class="productSelectionCode" value="${product.code}">
		<input  aria-label="Checkbox"  id="selectedProdType${indexvalue}" class ="select_product_checkbox"  type="checkbox"   value="SelectedProduct" data-role="${indexvalue}" data-listcod="${product.code}" data-listnum="${product.itemNumber}" data-listdes="${fn:escapeXml(product.name)}" data-listqty="${quantity}" data-listpri="${totalPrice.formattedValue}">
	</div>	
	<c:if test="${product.isRegulatoryLicenseCheckFailed eq false}">
		<div class="list-rup-msg m-b-${not empty product.potentialPromotions && not empty product.couponCode? '30' : '10'}-xs p-x-15-xs">
			<div class="flex-center print-p-b-0 order-review-available-backorder-mesg">
				<common:exclamatoryIcon iconColor="#ef8700" width="24" height="24" />
				<span class="pad-lft-10 f-s-12 f-s-11-xs-px"><spring:theme code="list.restricted.msg"/></span>
			</div>
		</div>
	</c:if>
	<!-- Image : List Details -->
	<div class="col-md-1 col-xs-2 p-r-0 print-p-l-0 list-details-image-wrapper">
		<c:if test="${not empty product.potentialPromotions && not empty product.couponCode}">
			<c:set var="showingCoupon" value="true" />
			<div class="list-clipcouponbadge">
				<common:buyagainclipcoupon></common:buyagainclipcoupon>
			</div>
		</c:if>
		<a class="f-s-10 text-lowercase list-img-sec" href="${productUrl}" title="${fn:escapeXml(product.name)}">
			<product:productPrimaryImage product="${product}" format="product"/>
		</a>
	</div>
	<!-- Title : List Details -->
	<div class="col-md-2 col-md-18pe col-xs-10 col-print-5 print-p-l-15 p-l-25 p-l-20-xs listproduct-info">
		<input type="hidden" class="listpotentialPromotions" value="${product.potentialPromotions}">
		<input type="hidden" class="listcouponCode" value="${product.couponCode}">
		<div class="squ-pd-id bold-text ${showingCoupon? 'p-t-15-xs' : ''}">
			<c:out value="${product.itemNumber}" />
		</div>
		<input type="hidden" class="productcspCode" value="${product.code}">
		<input type="hidden" value="${product.price.value}" class="productRetailPrice${product.code}" />
		<input type="hidden" value="${product.customerPrice.value}" class="csp${product.code}"/>
		<ycommerce:testId code="searchPage_productName_link_${product.code}">
			<a class="green-title" href="${productUrl}"><span class="bold-text">${fn:escapeXml(product.name)}</span></a>
		</ycommerce:testId>
		<c:if test="${not empty product.summary}"> 
			<div class="product__listing-description">${fn:escapeXml(product.summary)}</div>
		</c:if>
		<!-- Stock : List Details : Mobile | Print -->
		<div class="p-t-10-xs collist-check-saved f-s-14 hidden-lg hidden-md print-d-f print-p-t-5">
			<product:recommendedListproductStock productData="${product}" />
		</div>
	</div>
	<!-- Stock : List Details : Desktop -->
	<div class="col-md-2 col-md-15pe col-md-offset-0 col-xs-10 col-xs-offset-2 p-r-0 p-l-0 hidden-xs hidden-sm collist-check-saved f-s-14 print-hidden">
		<product:recommendedListproductStock productData="${product}" />
	</div>
	<c:set var="hideUomSelect" value="false"/>			  	
	<c:if test="${product.hideUom eq true}">
		<c:set var="hideUomSelect" value="true"/>
	</c:if>
	<c:if test="${product.singleUom eq true}">
		<c:set var="singleUom" value="true"/>
		<c:set var="uomDescription" value="${product.singleUomDescription}"/>
		<c:set var="uomMeasure" value="${product.singleUomMeasure}"/>
		<c:set var="inventoryMultiplier" value="${product.inventoryMultiplier}"/>
	</c:if>
	<c:if test="${not empty product.sellableUoms}">				                    
		<c:forEach items="${product.sellableUoms}" var="sellableUom">
			<c:if test="${sellableUom.inventoryUOMID eq uomid}">
				<c:set var="uomDescription" value="${sellableUom.inventoryUOMDesc}"/>
				<c:set var="uomMeasure" value="${sellableUom.measure}"/>
				<c:set var="inventoryMultiplier" value="${sellableUom.inventoryMultiplier}"/>         
			</c:if>
		</c:forEach>			                       
	</c:if>
	
	<!-- UOM Desktop : List Details -->
	<div class="col-md-2 col-md-13pe col-xs-12 hidden-xs hidden-sm print-hidden">
		<c:if test="${not empty product.sellableUoms && hideUomSelect ne true}">
			<input type="hidden" value="${empty uomDesc ? uomMeasure : uomDesc}" class="uomvalue"/>
			<div class="slp-text-style text-uppercase  ${hardscapeMoreOnWayMsgList ? ' hide ': ''}">
				<spring:theme code="productDetailsPanel.unitOfMeasure"/>
			</div>
			<select onchange="ACC.savedlist.listUomSelecion(this)" id="uom-options" class="js-uom-selector text-capitalize  ${hardscapeMoreOnWayMsgList ? ' hide ': ''}" <c:if test="${product.variantCount > 0}">disabled</c:if>>
				<c:forEach items="${product.sellableUoms}" var="sellableUom">
					<c:set var="inventoryMulvalue">&nbsp;(<fmt:formatNumber value="${sellableUom.inventoryMultiplier}" maxFractionDigits="0"/>)</c:set>
					<c:if test="${uomid == sellableUom.inventoryUOMID}">
						<c:set var="uomMeasure" value="${sellableUom.measure}" />
						<option class="text-capitalize" value="${sellableUom.inventoryUOMID}" data-uomdecs="${sellableUom.inventoryUOMDesc}" data-inventory="${sellableUom.inventoryUOMID}" data-inventoryMultiplier="${sellableUom.inventoryMultiplier}" selected>${fn:toLowerCase(sellableUom.inventoryUOMDesc)}${inventoryMulvalue}</option>
					</c:if>
					<c:if test="${uomid != sellableUom.inventoryUOMID}">
						<option class="text-capitalize" value="${sellableUom.inventoryUOMID}" data-uomdecs="${sellableUom.inventoryUOMDesc}" data-inventory="${sellableUom.inventoryUOMID}" data-inventoryMultiplier="${sellableUom.inventoryMultiplier}">${fn:toLowerCase(sellableUom.inventoryUOMDesc)}${inventoryMulvalue}</option>
					</c:if>
				</c:forEach>
			</select>
		</c:if>
	</div>
	<c:set var="uomMeasure" value="${empty uomMeasure? 'each' : uomMeasure}" />
	<!-- Price : List Details -->
	<div class="col-md-2 col-md-15pe col-xs-12 p-r-0 col-print-3 print-text-center print-p-r-0 ${showingCoupon?'list-clipcoupon-price':'flex-center-md'} mob-border-top m-t-15-xs p-l-0-xs p-t-15-xs p-b-10-xs print-m-0 print-b-0 print-row-price" data-siteOneCSPList="${siteOneCSPList}" data-code="${product.code}" data-hideList="${product.hideList}" data-hideCSP="${product.hideCSP}" data-inventoryFlag="${product.inventoryFlag}" data-price="${product.price.value}" data-customerPrice="${product.customerPrice.value}" data-sellableUoms="${product.sellableUoms}" data-hideUomSelect="${hideUomSelect}" data-uomDesc="${uomDesc}" data-variantCount="${product.variantCount}" data-inventoryUOMDesc="${sellableUom.inventoryUOMDesc}" data-measure="${sellableUom.measure}" data-inventoryMultiplier="${sellableUom.inventoryMultiplier}" data-uomid="${uomid}" data-inventoryUOMID="${sellableUom.inventoryUOMID}" data-uom='${empty uomDesc ? uomMeasure : uomDesc}'>
		<div class="padlr print-fw-n">
		<c:choose>					
			<c:when test="${hardscapeMoreOnWayMsgList}">
				<p class="callBranchForPrice black-title m-b-5">
					<spring:theme code="text.product.callforpricing"/>
				</p>
			</c:when>
			<c:otherwise>
				<c:if test="${not empty siteOneCSPList}">
					<c:forEach items="${siteOneCSPList}" var="customerPrice" varStatus="index">
						<c:if test="${product.code eq customerPrice.getCode()}">
							<c:choose>
								<c:when test="${product.hideList ne true}">
									<c:choose>
										<c:when test="${product.hideCSP ne true}">
											<c:choose>
												<c:when test="${product.inventoryFlag}">
													<!-- 1 -->
													<p class="callBranchForPrice black-title m-b-5">
														<spring:theme code="text.product.callforpricing"/>
													</p>
												</c:when>
												<c:otherwise>
													<!-- 2 -->
													<div class="floatleftprice flex-center-xs">
														<div class="text-uppercase slp-text-style print-c-gray print-hidden">
															<spring:theme code="text.product.siteOneCSP"/>
														</div>
														<div class="p-l-15-xs slp-yourprice-style text-lowercase print-fs-14 print-fw-n">
															$<fmt:formatNumber maxFractionDigits="${totalpriceDigits}" minFractionDigits="${totalpriceDigits}" value="${customerPrice.getPrice()}"/> / ${empty uomDesc ? uomMeasure : uomDesc}
														</div>
														<input type="hidden" class="js-customer-price" value="${customerPrice.getPrice()}" />
													</div>
													<c:if test="${product.price.value ge product.customerPrice.value}">
														<!-- 3 -->
													</c:if>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${product.inventoryFlag}">
													<!-- 4 -->
													<p class="callBranchForPrice black-title m-b-5">
														<spring:theme code="text.product.callforpricing"/>
													</p>
												</c:when>
												<c:otherwise>
													<!-- 5 -->
													<p class="callBranchForPrice black-title m-b-5">
														<spring:theme code="text.product.callforpricing"/>
													</p>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${product.hideCSP ne true}">
											<c:choose>
												<c:when test="${product.inventoryFlag}">
													<!-- 6 -->
													<p class="callBranchForPrice black-title m-b-5">
														<spring:theme code="text.product.callforpricing"/>
													</p>
												</c:when>
												<c:otherwise>
													<!-- 7 -->
													<div class="floatleftprice flex-center-xs">
														<p class="slp-text-style print-c-gray m-b-5 m-b-0-xs">
															<span class="text-uppercase print-hidden"><spring:theme code="text.product.siteOneCSP"/></span>
															<div class="p-l-15-xs slp-yourprice-style text-lowercase print-fs-14 print-fw-n">
																$<fmt:formatNumber maxFractionDigits="${totalpriceDigits}" minFractionDigits="${totalpriceDigits}" value="${customerPrice.getPrice()}"/> / ${uomMeasure}
															</div>
														</p>
														<input type="hidden" class="js-customer-price" value="${customerPrice.getPrice()}" />
													</div>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<!-- 8 -->
											<p class="callBranchForPrice black-title m-b-5">
												<spring:theme code="text.product.callforpricing"/>
											</p>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:forEach>
				</c:if>
				<c:if test="${empty siteOneCSPList}">
					<!-- 9 -->
					<div class="floatleftprice flex-center-xs">
						<p class="text-uppercase slp-text-style print-c-gray m-b-5 m-b-0-xs print-hidden"><spring:theme code="text.product.siteOneCSP"/></p>
						<div class="p-l-15-xs slp-yourprice-style text-lowercase print-fs-14 print-fw-n">
							$<fmt:formatNumber maxFractionDigits="${totalpriceDigits}" minFractionDigits="${totalpriceDigits}" value="${not empty product.price.value?product.price.value : 0}"/> / ${uomDescription}
						</div>
						<input type="hidden" class="js-customer-price" value="${product.price.value}" />
					</div>
				</c:if>
			</c:otherwise>
		</c:choose>
		</div>
	</div>
	<!-- UOM Mobile : List Details -->
	<c:if test="${not empty product.sellableUoms && hideUomSelect ne true}">
		<div class="col-xs-10 p-b-15-xs hidden-md hidden-lg print-hidden">
			<input type="hidden" value="${empty uomDesc ? uomMeasure : uomDesc}" class="uomvalue"/>
			<div class="slp-text-style text-uppercase ${hardscapeMoreOnWayMsgList ? ' hide ': ''}">
				<spring:theme code="productDetailsPanel.unitOfMeasure"/>
			</div>
			<select onchange="ACC.savedlist.listUomSelecion(this)" id="uom-options" class="js-uom-selector text-capitalize  ${hardscapeMoreOnWayMsgList ? ' hide ': ''}" <c:if test="${product.variantCount > 0}">disabled</c:if>>
				<c:forEach items="${product.sellableUoms}" var="sellableUom">
					<c:set var="inventoryMulvalue">&nbsp;(<fmt:formatNumber value="${sellableUom.inventoryMultiplier}" maxFractionDigits="0"/>)</c:set>
					<c:if test="${uomid == sellableUom.inventoryUOMID}">
						<c:set var="uomMeasure" value="${sellableUom.measure}" />
						<option class="text-capitalize" value="${sellableUom.inventoryUOMID}" data-uomdecs="${sellableUom.inventoryUOMDesc}" data-inventory="${sellableUom.inventoryUOMID}" data-inventoryMultiplier="${sellableUom.inventoryMultiplier}" selected>${fn:toLowerCase(sellableUom.inventoryUOMDesc)}${inventoryMulvalue}</option>
					</c:if>
					<c:if test="${uomid != sellableUom.inventoryUOMID}">
						<option class="text-capitalize" value="${sellableUom.inventoryUOMID}" data-uomdecs="${sellableUom.inventoryUOMDesc}" data-inventory="${sellableUom.inventoryUOMID}" data-inventoryMultiplier="${sellableUom.inventoryMultiplier}">${fn:toLowerCase(sellableUom.inventoryUOMDesc)}${inventoryMulvalue}</option>
					</c:if>
				</c:forEach>
			</select>
		</div>
		<c:set var="uomMeasure" value="${empty uomMeasure? 'each' : uomMeasure}" />
	</c:if>
	<!-- Quantity : List Details -->	
	<div class="col-md-2 col-md-20pe col-xs-12 p-l-25 col-print-1 print-b-0 print-price-quantity-total">
		<div class="row qty-height-inrow">
			<div class="col-md-5 col-xs-4 p-l-5-xs print-p-0 no-padding-rgt-xs ${showingCoupon?'list-clipcoupon-price':' '} print-m-0 print-p-0 print-row-quantity">
				<div class="intervalQtyError_list hidden print-hidden">
					<spring:theme code="text.valid.quantity" />
				</div>
				<div class="border-grey add-border-radius flex text-align-center justify-center list-item-qty">
					<input type="hidden" id="productCodeQtyChange" value="${product.code}" />
					<input type="hidden" id="listCodeQtyChange" value="${code}" />
					<input type="hidden" value="${product.outOfStockImage}" class="outofstockStatus" />
					<div class="bold f-s-10 text-default bg-white b-l-grey qtyHeading hidden-print">QTY</div>
					<c:choose>
						<c:when test="${isModified}">
							<button class="js-update-entry-quantity-list-btn minusQty minusQtyBtn hidden-print" type="button">-</button>
							<input type="text" name="qty" value="${quantity}" 
								class="print-fw-b print-p-0 form-control js-update-entry-quantity-input js-list-add-to-cart  qtyId js-qty-updateOne quantity_updated ${redBorder}"
								id="quantity_${indexvalue}" maxlength="5" data-role="prodQty_${product.code}"
								data-available-qty="${product.stock.stockLevel}" data-is-nursery="${product.productType eq 'Nursery'}"
								data-min-qty="${product.orderQuantityInterval}" data-inventoryMultiplier="${inventoryMultiplier}" data-min-orderqty="${product.minOrderQuantity}" autocomplete="off" oninput="ACC.savedlist.listQtyUpdate(this)" />
							<button class="js-update-entry-quantity-list-btn plusQty hidden-print" type="button">+</button>
						</c:when>
						<c:otherwise>
							<button class="js-update-entry-quantity-list-btn minusQty minusQtyBtn hidden-print" type="button" disabled>-</button>
							<input type="text" name="qty" value="${quantity}" disabled="disabled"
								class="print-fw-b print-p-0 form-control js-update-entry-quantity-input js-list-add-to-cart  qtyId js-qty-updateOne quantity_updated ${redBorder}"
								id="quantity_${indexvalue}" maxlength="5" data-role="prodQty_${product.code}"
								data-available-qty="${product.stock.stockLevel}" data-is-nursery="${product.productType eq 'Nursery'}"
								data-min-qty="${product.orderQuantityInterval}" data-inventoryMultiplier="${inventoryMultiplier}" data-min-orderqty="${product.minOrderQuantity}" autocomplete="off" oninput="ACC.savedlist.listQtyUpdate(this)" />
							<button class="js-update-entry-quantity-list-btn plusQty hidden-print" type="button" disabled>+</button>
						</c:otherwise>
					</c:choose>
					<input type="hidden" value="${product.orderQuantityInterval}" class="orderQtyInterval" />
				</div>
				<c:if test="${quantityFlag eq true }">
					<span id="quantityFlag">
						<spring:theme code="listDetails.invalidQuantity.message" />
					</span>
				</c:if>
				<div id="update_qty_list" class="col-xs-12 col-md-12 update_qty_list padding0 hidden-print"></div>
			</div>
			<div class="col-md-7 col-xs-6 pad-lft-10 hidden-print quantityInputSection">
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
					<cms:pageSlot position="OnlineOrderSL" var="feature">
						<cms:component component="${feature}"/>
					</cms:pageSlot>
				</c:if>
				<form:form method="post" id="addToCartForm" class="add_to_cart_form" action="${addToCartUrl}">
					<c:if test="${product.purchasable}">
						<input type="hidden" id="quantity" name="qty" value="${quantity}">
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
								<c:when test="${hardscapeMoreOnWayMsgList}">
									<div class="list-button-row">
										<div id="productSelect" class="margin15">
											<button type="submit" class="btn btn-primary pull-left btn-block hardscapebtn" aria-disabled="true" disabled="disabled">
											<spring:theme code="basket.add.to.basket" />
											</button>
										</div>
									</div>
								</c:when>
								<c:when test="${fn:contains(buttonType, 'button')}">
									<c:choose>
										<c:when test="${product.variantType eq 'GenericVariantProduct' && product.variantCount != 1}">
											<!-- 1 -->
											<div class="variantButton col-md-6 col-lg-7 col-xs-12">
												<button type="${buttonType}" id="variantButton" class="btn btn-primary btn-block js-add-to-cart">
													<spring:theme code="product.base.select.options" />
												</button>
											</div>
										</c:when>
										<c:otherwise>
											<ycommerce:testId code="addToCartButton">
												<!-- 2 -->
												<button type="${(orderOnlinePermissions eq true)?buttonType:'button'}" id="${(orderOnlinePermissions eq true)?'addToCartButton':ATCOOId}" class="btn btn-primary btn-block pull-left js-add-to-cart  js-enable-btn" disabled="disabled">
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
																<!-- <button type="submit" id="notSellable" class="btn btn-primary pull-left btn-block" aria-disabled="true" disabled="disabled">									<spring:theme code="basket.add.to.basket" />			</button> -->
																
																<button class="col-md-12 col-xs-12 btn btn-primary pull-left btn-block requestQuoteBtnPDP" id="notSellable"
																	data-product-description="${fn:escapeXml(product.name)}"
																	onclick="ACC.savedlist.requestQuotePopupplp(this,'add_to_cart_form','${product.code}');ACC.adobelinktracking.requestQuotePLP(this,'','','${cmsPage.name}','true');event.preventDefault();">
																	<spring:theme code="text.lists.requestQuote" />
																</button>
																	
																	<c:if test="${!isOrderingAccount}">
																		<span class="alert_msg">${orderingAccountMsg}</span>
																	</c:if>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${product.hideCSP eq true}">
																		<!-- 4 -->
																		<button type="submit" id="hideCSP" class="btn btn-primary btn-block" disabled="disabled" ${disableAtc}>
																			<spring:theme code="basket.add.to.basket" />
																		</button>
																	</c:when>
																	<c:otherwise>
																		<!-- 5 -->
																		<button type="${(orderOnlinePermissions eq true)?'submit':'button'}"id="${(orderOnlinePermissions eq true)?'showAddtoCart':ATCOOId}" class="btn btn-primary btn-block js-enable-btn"
																			disabled="disabled" ${disableAtc}>
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
																				<button type="${(orderOnlinePermissions eq true)?'submit':'button'}" id="${(orderOnlinePermissions eq true)?'showAddtoCart':ATCOOId}" class="btn btn-primary btn-block js-enable-btn f-w-700" disabled="disabled" ${disableAtc}>
																					<spring:theme code="basket.add.to.basket" />
																				</button>
																			</c:when>
																			<c:otherwise>
																				<!-- 7 -->
																				<!-- <button type="submit" id="showAddtoCart" class="btn btn-primary btn-block" disabled="disabled" ${disableAtc}>
																					<spring:theme code="basket.add.to.basket" />
																				</button> -->
																				<button class="col-md-12 col-xs-12 btn btn-primary  btn-block requestQuoteBtnPDP" id="showAddtoCart" ${disableAtc}
																					data-product-description="${fn:escapeXml(product.name)}"
																					onclick="ACC.savedlist.requestQuotePopupplp(this,'product-item','${product.code}');ACC.adobelinktracking.requestQuotePLP(this,'','','${cmsPage.name}','true');event.preventDefault();">
																					<spring:theme code="text.lists.requestQuote" />
																				</button>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<!-- 8 -->
																		<button type="submit" id="showAddtoCart" class="btn btn-primary btn-block" disabled="disabled" ${disableAtc}>
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
																				<button type="${(orderOnlinePermissions eq true)?'submit':'button'}" id="${(orderOnlinePermissions eq true)?'showAddtoCart':ATCOOId}" class="btn btn-primary btn-block js-enable-btn" disabled="disabled" ${disableAtc}>
																					<spring:theme code="basket.add.to.basket" />
																				</button>
																			</c:when>
																			<c:otherwise>
																				<!-- 10 -->
																				<button class=" btn btn-primary  btn-block requestQuoteBtnPDP" id="showAddtoCart" ${disableAtc}
																					data-product-description="${fn:escapeXml(product.name)}"
																					onclick="ACC.savedlist.requestQuotePopupplp(this,'product-item','${product.code}');ACC.adobelinktracking.requestQuotePLP(this,'','','${cmsPage.name}','true');event.preventDefault();">
																					<spring:theme code="text.lists.requestQuote" />
																				</button>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<!-- 11 -->
																		<!-- <button type="submit" id="showAddtoCart" class="btn btn-primary btn-block" disabled="disabled" ${disableAtc}>
																			<spring:theme code="basket.add.to.basket" />
																		</button> -->
																		<button class=" btn btn-primary  btn-block requestQuoteBtnPDP" id="showAddtoCart" ${disableAtc}								data-product-description="${fn:escapeXml(product.name)}"
																					onclick="ACC.savedlist.requestQuotePopupplp(this,'add_to_cart_form','${product.code}');ACC.adobelinktracking.requestQuotePLP(this,'','','${cmsPage.name}','true');event.preventDefault();">
																					<spring:theme code="text.lists.requestQuote" />
																				</button>
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose>
														<c:choose>
															<c:when test="${(!product.isSellable)}">
																<!-- 12 -->
																<button id="showAddtoCartUom" class="btn btn-primary pull-left btn-block"
																	style="display:none;margin-top:0px" data-product-description="${fn:escapeXml(product.name)}"
																	onclick="ACC.savedlist.requestQuotePopupplp(this,'add_to_cart_form','${product.code}');ACC.adobelinktracking.requestQuotePLP(this,'','','${cmsPage.name}','true');event.preventDefault();">
																	<spring:theme code="text.lists.requestQuote" />
																</button>
																<c:if test="${!isOrderingAccount}">
																	<span class="alert_msg" id="orderingAccountMsg">${orderingAccountMsg}</span>
																</c:if>
															</c:when>
															<c:otherwise>
																<!-- 13 -->
																<button type="${(orderOnlinePermissions eq true)?'submit':'button'}" id="${(orderOnlinePermissions eq true)?'showAddtoCartUom':ATCOOId}" class="btn btn-primary btn-block js-enable-btn" style="display:none;margin-top:0px" disabled="disabled" ${disableAtc}>
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
				<input type="hidden" id="listSize" value="${fn:length(savedLists)}" />
			</div>
			<c:if test="${productQuantity_list ne '1' && empty product.minOrderQuantity}">
				<div class="col-md-12 col-xs-12 m-l-10-xs m-t-5 p-l-0 f-s-12 flex-center text-default hidden-print intervalQtyInfo_list">
					<common:info-circle iconColor="#78a22f" width="16" height="16" className="m-r-5" />
					<spring:theme code="text.minimum.value" />&nbsp;${product.orderQuantityInterval}
				</div>
			</c:if>
			<c:if test="${product.minOrderQuantity ne null && product.minOrderQuantity ne '0' && product.minOrderQuantity ne ''}">
				<div class="col-md-12 col-xs-12 m-l-10-xs m-t-5 p-l-0 f-s-12 flex-center text-default hidden-print minOrderQtyInfo_list">
					<common:info-circle iconColor="#78a22f" width="16" height="16" className="m-r-5" />
					<spring:theme code="text.minimum.info" />&nbsp;${product.minOrderQuantity}
				</div>
			</c:if>
		</div>
	</div>
	<!-- Total : List Details -->
	<div class="col-md-12pe col-md-2 col-xs-12 print-p-r-0 m-t-15-xs mob-border-top p-l-15-xs print-b-0 print-p-0 print-row-total tot-list-wraper">
		<div class="text-uppercase tot-amt-list-text m-b-5 hidden-print"><spring:theme code="saveListDetailsPage.total" /></div>
		<div class="p-l-15-xs bold tot-amt-list">
			${hidePrice ? '$0.00' : totalPrice.formattedValue}
		</div>
	</div>
	<!-- Remove : List Details -->
	<div class="list-detail-delete hidden-print">
		<input type="hidden" id="listSize" value="${fn:length(savedLists)}" /> 
		<input type="hidden" name="addproductCode" id="addproductCode" value="${product.itemNumber}" />
		<input type="hidden" name="savedListName" id="savedListName" value="${savedListData.code}" /> 
		<input type="hidden" name="qty" id="qty" value="${quantity}" />
		<input type="hidden" name="product_code" id="product_code" value="${product.code}" /> 
		<c:choose>
			<c:when test="${isModified}">
				<button class="btn btn-green-link deleteProFromList list-delete-style"><common:globalIcon iconName="close" iconFill="none" iconColor="#78a22f" width="11" height="11" viewBox="0 0 11 11" display="" /></button>
			</c:when>
			<c:otherwise>
				<button class="btn btn-green-link deleteProFromList list-delete-style" disabled><common:globalIcon iconName="close" iconFill="none" iconColor="#78a22f" width="11" height="11" viewBox="0 0 11 11" display="" /></button>
			</c:otherwise>
		</c:choose>
	</div>
</ycommerce:testId>
<fmt:setLocale value="${pageContext.response.locale}" scope="session"/>