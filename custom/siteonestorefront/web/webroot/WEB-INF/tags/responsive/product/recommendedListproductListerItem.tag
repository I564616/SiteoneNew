<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="indexvalue" required="false" type="java.lang.String" %>
<%@ attribute name="productData" required="false" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<spring:url var="homelink" value="/" htmlEscape="false"/>
<c:set var="showingCoupon" value="false" />
<c:set var="iconWidth" value="23" />
<c:set var = "userAgent" value = "${fn:toLowerCase(header['User-Agent'])}"/>
<c:set var="skuNumber" value="${not empty productData.code && productData.code ne null ? productData.code : '9090C'}" />
<c:set var="itemNumber" value="${not empty productData.itemNumber && productData.itemNumber ne null ? productData.itemNumber : '9090I'}" />
<c:set var="itemName" value="${not empty productData.name && productData.name ne null ? fn:escapeXml(productData.name) : '9090N'}" />
<c:set var="itemRPrice" value="${not empty productData.price.value && productData.price.value ne null ? productData.price.value : '9091'}" />
<c:set var="itemYPrice" value="${not empty productData.customerPrice.value && productData.customerPrice.value ne null ? productData.customerPrice.value : '9090'}" />
<c:set var="itemCount" value="${not empty productData.purchasedCount && productData.purchasedCount ne null ? productData.purchasedCount : 9090}" />
<c:set var="orderCount" value="${9092}" />
<c:set var="productCount" value="${9093}" />
<input type="hidden" id="prodCode${indexvalue}" name="code" value="${skuNumber}" class="productcode" />
<input type="hidden" id="prodQty_${skuNumber}" name="qty" value="1" class="	ValueId" />
<input type="hidden" id="prodItemNumber${indexvalue}" name="code" value="${itemNumber}" class="productitemnumber" />
<input type="hidden" id="prodQtyItem_${itemNumber}" name="qty" value="1" class="productqtyItem" />
<input type="hidden" class="whishlistcode" value="8796519105213" />
<input type="hidden" class="inventryuom" value="54345" />
<input type="hidden" value="${skuNumber}" class="productID_assemblyList" />
<input type="hidden" name="selectedProducts" class="selectedProducts" value="" />
<input type="hidden" id="variantisForceinstock" value="false" />
<c:set var="productQuantity_list" value="1"/> 
<c:if test="${productData.orderQuantityInterval ne null && productData.orderQuantityInterval ne '0' && productData.orderQuantityInterval ne ''}">
	<c:set var="productQuantity_list" value="intervalQtyInfo_list"/> 
</c:if>
<c:if test="${productData.minOrderQuantity ne null && productData.minOrderQuantity ne '0' && productData.minOrderQuantity ne ''}">
	<c:set var="productQuantity_list" value="minOrderQtyInfo_list" />
</c:if>
<!-- Check : List Details -->
<div class="colored padding-md-10 no-padding-xs width-100-px-xs list-check list-Checkbox hidden-print">
	<input type="hidden" class="productSelectionCode" value="${skuNumber}">
	<input  aria-label="Checkbox"  id="selectedProdType${indexvalue}" class ="select_product_checkbox" type="checkbox" value="SelectedProduct" data-role="${indexvalue}" data-listnum="${itemName}" data-listdes="${itemName}" data-listqty="1" data-listpri="$${itemRPrice}">
</div>
<c:if test="${productData.isRegulatoryLicenseCheckFailed eq false}">
	<div class="list-rup-msg m-b-${not empty productData.potentialPromotions && not empty productData.couponCode? '30' : '10'}-xs p-x-15-xs">
		<div class="flex-center print-p-b-0 order-review-available-backorder-mesg">
			<common:exclamatoryIcon iconColor="#ef8700" width="24" height="24" />
			<span class="pad-lft-10 f-s-12 f-s-11-xs-px"><spring:theme code="list.restricted.msg"/></span>
		</div>
	</div>
</c:if>
<!-- Image : List Details -->
<div class="col-md-1 col-xs-2 p-r-0 print-p-l-0 list-details-image-wrapper">
	<c:if test="${not empty productData.potentialPromotions && not empty productData.couponCode}">
		<c:set var="showingCoupon" value="true" />
		<div class="list-clipcouponbadge">
			<common:buyagainclipcoupon></common:buyagainclipcoupon>
		</div>
	</c:if>
	<div class="js-my-order-qty-error js-cart-qty-error marginBottom20 text-red hide">
		<img class="icon-red-exclamation cart-qty-alert" src="/_ui/responsive/theme-lambda/images/Exclamation-point.svg" alt""="">
		Only 9,999,990 of these are available at your selected branch
	</div>
	<a class="f-s-10 text-lowercase list-img-sec" href="${homelink}p/${skuNumber}" title="${itemName}">
		<product:recommendedListproductImage productData="${productData}" />
	</a>
</div>
<!-- Title : List Details -->
<div class="col-md-2 col-md-20pe col-xs-10 col-print-5 print-p-l-15 p-l-25 p-l-20-xs listproduct-info">
	<input type="hidden" value="[]" class="listpotentialPromotions" />
	<input type="hidden" value="" class="listcouponCode" />
	<input type="hidden" value="${skuNumber}" class="productcspCode" />
	<input type="hidden" value="${itemRPrice}" class="productRetailPrice${skuNumber}" />
	<input type="hidden" value="" class="csp${skuNumber}" />
	<div class="squ-pd-id bold-text ${showingCoupon? 'p-t-15-xs' : ''}">
		<c:out value="${itemNumber}" />
	</div>
	<ycommerce:testId code="searchPage_productName_link_${skuNumber}">
		<a class="green-title" href="${homelink}p/${skuNumber}" title="${itemName}"><span class="bold-text">${itemName}</span></a>
	</ycommerce:testId>
	<!-- Stock : List Details : Mobile | Print -->
	<div class="p-t-10-xs collist-check-saved f-s-14 hidden-lg hidden-md print-d-f print-p-t-5">
		<product:recommendedListproductStock productData="${productData}" iconWidth="${iconWidth}" />
	</div>
	<div class="message-center marginTop10 f-s-12 hidden-md hidden-lg hidden">Purchased ${productCount} time${productCount > 1 ? 's' : ''} in ${orderCount} order${orderCount > 1 ? 's' : ''}.</div>
	<div class="message-center marginTop10 f-s-12 hidden-md hidden-lg">Purchased ${itemCount} time${itemCount > 1 ? 's' : ''} in the last 12 months.</div>
</div>
<!-- Stock : List Details : Desktop -->
<div class="col-md-2 col-md-offset-0 col-xs-10 col-xs-offset-2 hidden-xs hidden-sm collist-check-saved message-center print-hidden">
	<div class="row">
		<div class="col-md-12 f-s-14"><product:recommendedListproductStock productData="${productData}" iconWidth="${iconWidth}" /></div>
		<div class="col-md-12 marginTop10 f-s-12 hidden-xs hidden-sm hidden">Purchased ${productCount} time${productCount > 1 ? 's' : ''} in ${orderCount} order${orderCount > 1 ? 's' : ''}.</div>
		<div class="col-md-12 marginTop10 f-s-12 hidden-xs hidden-sm">Purchased ${itemCount} time${itemCount > 1 ? 's' : ''} in the last 12 months.</div>
	</div>
</div>
<c:set var="hideUomSelect" value="false"/>
<c:if test="${productData.hideUom eq true}">
	<c:set var="hideUomSelect" value="true"/>
</c:if>
<c:set var="uomDescription" value="EACH"/>
<c:set var="uomid" value="12345"/>
<c:if test="${productData.singleUom eq true}">
	<c:set var="singleUom" value="true"/>
	<c:set var="uomDescription" value="${productData.singleUomDescription}"/>
	<c:set var="uomMeasure" value="${productData.singleUomMeasure}"/>
	<c:set var="uomid" value="12345"/>
	<c:set var="inventoryMultiplier" value="${productData.inventoryMultiplier}"/>
</c:if>
<c:if test="${not empty productData.sellableUoms}">
	<c:forEach items="${productData.sellableUoms}" var="sellableUom" varStatus="index">
		<c:if test="${sellableUom.inventoryUOMDesc eq productData.multiUOMDesc}">
			<c:set var="uomid" value="${sellableUom.inventoryUOMID}"/>
			<c:set var="uomDescription" value="${sellableUom.inventoryUOMDesc}"/>
			<c:set var="uomMeasure" value="${sellableUom.measure}"/>
			<c:set var="inventoryMultiplier" value="${sellableUom.inventoryMultiplier}"/>
		</c:if>
		<c:if test="${productData.hideUom eq true && sellableUom.hideUOMOnline eq false}">
			<c:set var="hideUomSelect" value="true" />
		</c:if>
	</c:forEach>
</c:if>
<!-- UOM Desktop : List Details -->
<div class="col-md-2 col-md-13pe col-xs-12 hidden-xs hidden-sm print-hidden">
	<c:if test="${not empty productData.sellableUoms && hideUomSelect ne true}">
		<product:recommendedListproductUom productData="${productData}" uomDesc="${uomDesc}" uomMeasure="${uomMeasure}" uomid="${uomid}" />
	</c:if>
</div>
<c:set var="uomMeasure" value="${empty uomMeasure? 'each' : uomMeasure}" />
<!-- Price : List Details -->
<div class="col-md-2 col-xs-12 col-print-3 print-text-center print-p-r-0 ${showingCoupon?'list-clipcoupon-price':'flex-center-md'} mob-border-top m-t-15-xs p-l-0-xs p-t-15-xs p-b-10-xs print-m-0 print-b-0 print-row-price" data-siteOneCSPList="${siteOneCSPList}" data-code="${skuNumber}" data-hideList="${productData.hideList}" data-hideCSP="${productData.hideCSP}" data-inventoryFlag="${productData.inventoryFlag}" data-price="${itemRPrice}" data-customerPrice="${productData.customerPrice.value}" data-sellableUoms="${productData.sellableUoms}" data-hideUomSelect="${hideUomSelect}" data-uomDesc="${uomDesc}" data-variantCount="${productData.variantCount}" data-inventoryUOMDesc="${sellableUom.inventoryUOMDesc}" data-measure="${sellableUom.measure}" data-inventoryMultiplier="${sellableUom.inventoryMultiplier}" data-uomid="${uomid}" data-inventoryUOMID="${sellableUom.inventoryUOMID}" data-uom='${empty uomDesc ? uomMeasure : uomDesc}'>
	<div class="flex-center-xs padlr print-fw-n">
		<product:recommendedListproductPrice product="${productData}" uomDescription="${uomDescription}" uomMeasure="${uomMeasure}" hideUomSelect="${hideUomSelect}" />
	</div>
</div>
<!-- UOM Mobile : List Details -->
<c:if test="${not empty productData.sellableUoms && hideUomSelect ne true}">
	<div class="col-xs-9 p-b-15-xs p-r-5-xs hidden-md hidden-lg print-hidden">
		<product:recommendedListproductUom productData="${productData}" uomDesc="${uomDesc}" uomMeasure="${uomMeasure}" uomid="${uomid}" />
	</div>
	<c:set var="uomMeasure" value="${empty uomMeasure? 'each' : uomMeasure}" />
</c:if>
<!-- Quantity : List Details -->
<div class="col-md-2 col-md-20pe col-xs-12 col-print-1 p-l-25-xs print-b-0 print-price-quantity-total">
	<div class="row qty-height-inrow">
		<div class="col-md-5 col-xs-4 p-l-5 print-p-0 ${showingCoupon?'list-clipcoupon-price':' '} print-m-0 print-p-0 print-row-quantity">
			<div class="intervalQtyError_list hidden print-hidden">
				<spring:theme code="text.valid.quantity" />
			</div>
			<div class="border-grey add-border-radius flex text-align-center justify-center list-item-qty">
				<input type="hidden" id="productCodeQtyChange" value="${skuNumber}" />
				<input type="hidden" id="listCodeQtyChange" value="${code}" />
				<input type="hidden" value="${productData.outOfStockImage}" class="outofstockStatus" />
				<div class="bold f-s-10 text-default bg-white b-l-grey qtyHeading hidden-print">QTY</div>
				<button class="bg-white text-green js-add-entry-quantity-list-btn plusQty hidden-print" onclick="ACC.savedlist.quantityHandler('plus','#quantity_${indexvalue}','recList')">+</button>
				<input type="text" min="1" max="99999" name="qty" value="1" class="form-control js-update-entry-quantity-input ${productQuantity_list ne '1' ? 'text-white' : ''}" id="quantity_${indexvalue}" maxlength="5" data-role="prodQty_${skuNumber}" data-available-qty="${productData.stock.stockLevel}" data-is-nursery="${productData.productType eq 'Nursery'}" data-min-qty="${productData.orderQuantityInterval}" data-min-orderqty="${productData.minOrderQuantity}" data-inventoryMultiplier="${inventoryMultiplier}" autocomplete="off" onkeyup="ACC.recommendedlist.listDetailTotalCalculate(this)" />
				<button class="bg-white text-green js-add-entry-quantity-list-btn minusQty minusQtyBtn hidden-print" onclick="ACC.savedlist.quantityHandler('minus','#quantity_${indexvalue}','recList')">-</button>
				<input type="hidden" value="${productData.orderQuantityInterval}" class="orderQtyInterval" />
			</div>
			<c:if test="${quantityFlag eq true }">
				<span id="quantityFlag">
					<spring:theme code="listDetails.invalidQuantity.message" />
				</span>
			</c:if>
			<div id="update_qty_list" class="col-xs-12 col-md-12 update_qty_list padding0 hidden-print"></div>
		</div>
		<div class="col-md-7 col-xs-6 hidden-print pad-lft-10 quantityInputSection">
			<product:recommendedListproductATC product="${productData}" indexvalue="${indexvalue}" uomid="${uomid}" />
			<input type="hidden" id="listSize" value="9" />
		</div>
		<c:if test="${productQuantity_list ne '1'}">
			<div class="col-md-12 col-xs-12 m-l-10-xs m-t-5 p-l-0 f-s-12 flex-center text-default hidden-print ${productQuantity_list}">
				<common:info-circle iconColor="#78a22f" width="16" height="16" className="m-r-5" />
				<c:choose>
					<c:when test="${productQuantity_list == 'minOrderQtyInfo_list'}">
						<spring:theme code="text.minimum.info" />&nbsp;${productData.minOrderQuantity}
					</c:when>
					<c:otherwise>
						<spring:theme code="text.minimum.value" />&nbsp;${productData.orderQuantityInterval}
					</c:otherwise>
				</c:choose>
			</div>
		</c:if>
	</div>
</div>
<!-- Total : List Details -->
<div class="col-md-1 col-md-10pe col-xs-12 print-p-r-0 m-t-15-xs mob-border-top pad-lft-10 p-l-15-xs p-y-10-xs print-b-0 print-p-0 print-row-total">
	<div class="text-uppercase tot-amt-list-text hidden-print"><spring:theme code="saveListDetailsPage.total" /></div>
	<div class="p-l-15-xs bold tot-amt-list">-</div>
</div>