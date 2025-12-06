<%@ page trimDirectiveWhitespaces="true" contentType="application/json" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart" %>

<c:set var="productrecssellable" value="false"/>		
<c:set var="showUpdatequantity" value="false"/>
<c:choose>
	<c:when test="${isMixedCartEnabled eq true}">
		<c:set var="hideQTYnonmix" value="hidden"/>
		 <c:set var="showQTYnonmix" value=""/>
		 <c:set var="fullWidthContentBtn" value="col-md-12 col-xs-12 col-sm-12 marginTop10"/>
	</c:when>
	<c:otherwise>
		<c:set var="hideQTYnonmix" value="hidden"/>
		<c:set var="showQTYnonmix" value=""/>	
		<c:set var="fullWidthContentBtn" value="col-md-12 col-xs-12 col-sm-12 marginTop10"/>
	</c:otherwise>
</c:choose>
{"cartData": {
"total": "${cartData.totalPrice.value}",
"products": [
<c:forEach items="${cartData.entries}" var="cartEntry" varStatus="status">
	{
		"sku":		"${cartEntry.product.code}",
		"name": 	"<c:out value='${cartEntry.product.name}' />",
		"qty": 		"${cartEntry.quantity}",
		"price": 	"${cartEntry.basePrice.value}",
		"categories": [
		<c:forEach items="${cartEntry.product.categories}" var="category" varStatus="categoryStatus">
			"<c:out value='${category.name}' />"<c:if test="${not categoryStatus.last}">,</c:if>
		</c:forEach>
		]
	}<c:if test="${not status.last}">,</c:if>
</c:forEach>
]
},

"quickOrderErrorData": [
<c:forEach items="${quickOrderErrorData}" var="quickOrderEntry" varStatus="status">
	{
		"sku":		"${quickOrderEntry.productData.code}",
		"errorMsg": "<spring:theme code='${quickOrderEntry.errorMsg}' htmlEscape='true' />"
	}<c:if test="${not status.last}">,</c:if>
</c:forEach>
],

"cartAnalyticsData":{"cartCode" : "${cartCode}","productPostPrice":"${entry.basePrice.value}","productName":"<c:out value='${product.name}' />","productCategory":"<c:out value='${entry.product.level1Category}' escapeXml="false"/>","productSubCategory":"<c:out value='${entry.product.level2Category}' escapeXml="false"/>"}
,
"addToCartLayer":"<spring:escapeBody javaScriptEscape="true">
	<spring:theme code="text.addToCart" var="addToCartText"/>
	<c:url value="/cart" var="cartUrl"/>
	<c:url value="/cart/checkout" var="checkoutUrl"/>
	<ycommerce:testId code="addToCartPopup">
		<c:if test="${algonomyRecommendationEnabled}">	
		<c:if test="${not empty categoryProduct}">
			<c:forEach items="${categoryProduct}" var="product1" varStatus="status1">
					<c:if test="${product1.isStockAvailable eq true && (product1.hideList eq false || not empty product1.price || product1.hideCSP eq false || not empty product1.customerPrice)}">
				    	<c:set var="productrecssellable" value="true"/>
				    </c:if>
			</c:forEach>
		</c:if>
		</c:if>
		<c:choose>
		<c:when test="${productrecssellable ne true}">
				<c:set var="cartpopuperrormsg" value="cart_popup_error_msg-rec"/>
				<c:set var="cartpopuploginrec" value="cartpopuplogin-rec"/>
		</c:when>
		<c:otherwise>
				<c:set var="cartpopuperrormsg" value="cart_popup_error_msg"/>
				<c:set var="cartpopuploginrec" value=""/>
		</c:otherwise>
		</c:choose>
		
		<div id="addToCartLayer" class="row add-to-cart">
            <div class="${cartpopuperrormsg}">
                <c:choose>
	                <c:when test="${quickOrderErrorData ne null and not empty quickOrderErrorData}">
	                	<spring:theme code="${quickOrderErrorMsg}" arguments="${fn:length(quickOrderErrorData)}" htmlEscape="true" />
                    </c:when>
                    <c:when test="${multidErrorMsgs ne null and not empty multidErrorMsgs}">
                        <c:forEach items="${multidErrorMsgs}" var="multidErrorMsg">
                            <spring:theme code="${multidErrorMsg}" />
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <spring:theme code="${errorMsg}" />
                    </c:otherwise>
                </c:choose>
            </div>
           
          <input type="hidden" id="numberItemsInCart" value="${numberItemsInCart}">
          <c:if test="${showUpdatequantity ne true}">
            <h3 class="minicart-title"><span class="mixcart-qty ${showQTYnonmix}">${quantity}</span>
            <span class="${hideQTYnonmix}"> <spring:theme code="popup.cart.itemsAdded" />! </span>
            <span class="${showQTYnonmix}"> <spring:theme code="popup.cart.itemsAdded.mixedcart" /> </span>
            </h3>
		</c:if>
		 <c:if test="${showUpdatequantity}">
            <h3 class="minicart-title"> <span class="qty-title-atc"></span>&nbsp;<spring:theme code="popup.cart.itemsAdded.updateQuantity" /></h3>
		</c:if>
<c:if test="${showUpdatequantity ne true}">
            <c:choose>
                <c:when test="${modifications ne null}">
							
					 <b><spring:theme code="popup.cart.showing" arguments="${numberShowing},${numberShowing}"/></b>	
                    <div class="quick-order-border">
                    <c:forEach items="${modifications}" var="modification" varStatus="loop">
                    <c:choose>
                    	<c:when test="${loop.index < numberShowing}">
                    		 <c:set var="product" value="${modification.entry.product}" />
                       		 <c:set var="entry" value="${modification.entry}" />
                        	 <c:set var="quantity" value="${modification.quantityAdded}" />
                        	 <cart:popupCartItems entry="${entry}" product="${product}" quantity="${quantity}" recommendedproduct="${productrecssellable}"/>
                    	</c:when>
                    </c:choose>
                    </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                	<c:if test="${showUpdatequantity ne true}">
						<div class="qty productDetailCart-atc qty-mob-padding ${hideQTYnonmix}"><spring:theme code="popup.cart.quantity.added"/>&nbsp;${quantity}</div>
					</c:if>
                    <cart:popupCartItems entry="${entry}" product="${product}" quantity="${quantity}" recommendedproduct="${productrecssellable}"/>
                </c:otherwise>
            </c:choose>
      </c:if>      
            <c:if test="${not empty subTotal}">
            <div class="row margin20 sub-total-wrapper">
					    <div class="col-md-4 col-sm-4"><br></div>
					    <div class="col-md-6 col-sm-6"><b>Subtotal</b></div>
                    	<div class="col-md-2 col-sm-2 sub-total"><b>$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${subTotal}"/></b></div>
            </div>
            </c:if>
 
		</div>
		
		<div class="cl"></div>
		
		
		
		<c:if test="${algonomyRecommendationEnabled}">
		<c:if test="${productrecssellable eq true}">
			<div class="row product-recs-wrapper">
				<c:if test="${not empty categoryProduct}">
					<div class="productrecom-heading"><c:out value="${recommendationTitle}" /></div>
					<c:set var="isrc" value="?isrc=featuredpopup"/>
					<c:forEach items="${categoryProduct}" var="product" varStatus="status">
					<c:if test="${product.isStockAvailable eq true && (product.hideList eq false || not empty product.price || product.hideCSP eq false || not empty product.customerPrice)}">
								<div class="category-tile-item">
								<cart:popupcartProductRecs product="${product}" isrc="${isrc}"/>
								</div>
							</c:if>
					</c:forEach>	
				</c:if>
			</div>
		</c:if>
		</c:if>
		
			
			
			
	
		 <div class="cartpage-popup_btn">
			<div class="cartpage-popup-login row ${cartpopuploginrec}">
				<div class="${fullWidthContentBtn} add-cart-continue-btn">
		            <a href="" class="btn btn-primary btn-block bold-text js-mini-cart-close-button btn-popup-overlay-width">
		                <spring:theme code="cart.page.continue"/>
		            </a>
	            </div>
				<div class="${fullWidthContentBtn} add-cart-continue-btn">
	            <ycommerce:testId code="checkoutLinkInPopup">
	                <a onclick="ACC.colorbox.close();loading.start();" href="${cartUrl}" class="btn btn-default btn-block bold-text add-to-cart-button btn-popup-overlay-width">
		                <c:choose>
			            <c:when test="${isQuote}">
			                <spring:theme code="quote.view" />
		                </c:when>
			            <c:otherwise>
			                 <spring:theme code="cart.goToCartcheckout" />
			            </c:otherwise>
	                	</c:choose>
	                </a>
	            </ycommerce:testId>
	            </div>
 			</div>
         </div>
	
	</ycommerce:testId>
</spring:escapeBody>",
"isSellable": ${productrecssellable}
}



