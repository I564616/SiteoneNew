<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>

<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<c:if test="${orderOnlinePermissions ne true}">
	<c:set var="ATCOOId" value="orderOnlineATC" />
	<cms:pageSlot position="OnlineOrderPLP" var="feature">
		<cms:component component="${feature}"/>
	</cms:pageSlot>
	<cms:pageSlot position="OnlineOrderPLP2" var="feature">
		<cms:component component="${feature}"/>
	</cms:pageSlot>
</c:if>
 
    <c:set var="sellableUomListLen" value="${fn:length(product.sellableUoms)}" />
    <c:url value="/cart/add" var="addToCartUrl"/>
    <c:url value="${product.url}/configuratorPage/${configuratorType}" var="configureProductUrl"/>
    <input type="hidden" class="trackProductCode" name="trackProductCode" value="${product.code}">
    <input type="hidden" class="trackRetailPrice" name="trackRetailPrice" value="${product.price.value}">
    <input type="hidden" class="trackCSP" name="trackCSP" value="${product.customerPrice.value}">
	<c:set var="hideList" value="${product.hideList}"/>
	<c:set var="hideCSP" value="${product.hideCSP}"/>
	<c:set var="buttonID" value="listPageAddToCart_${product.code}" />
    <c:set var="buttonIDOO" value="orderOnlineATC_${product.code}" />

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
			
        <ycommerce:testId code="addToCartButton">
            
            <!-- <div class="PLPQuantitySelector col-md-6 col-xs-12"> -->
            <div class="row">
            <div class= "col-md-5 plp-qty-container flex-center col-xs-5 no-padding-rgt-xs no-padding-rgt-sm no-padding-rgt-md hide">
            <div class="qtyHeading"><spring:theme code="text.account.savedCart.qty" /></div>
            <input type="text" name="productPostPLPQty" id="productPLPPostQty_${product.code}" value="1" class="form-control js-qty-selector-input js-qty-updateOne js-plp-qty" size="1"  maxlength="5" data-nurseryproduct="${product.isNurseryProduct}" data-maxqty="${product.nearestStoreStockLevel}"/>
            </div>            
                    <c:choose>
                        <c:when test="${((product.isRegulateditem && addIsMyStoreProduct && !addIsProductSellable && !sessionStore.isLicensed) || (!addIsMyStoreProduct) || (empty product.price && empty product.priceRange.minPrice  && empty product.customerPrice) || (!(empty product.price) && product.price.value.toString() eq 0.0) || (!(empty product.customerPrice) )|| (!isOrderingAccount))}">

                        <div class="col-md-12 col-xs-12">
                            <button type="${(orderOnlinePermissions eq true)?'submit':'button'}" class="btn btn-primary btn-carousel_addtocart btn-block js-atc-${product.code}" ${(orderOnlinePermissions eq true)?"":"name=atclistoo"}
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
                            <button type="${(orderOnlinePermissions eq true)?'submit':'button'}" class="btn btn-primary btn-carousel_addtocart btn-block js-atc-${product.code}" ${(orderOnlinePermissions eq true)?"":"name=atclistoo"}
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
    <div class="qtyerror-plp hidden">
	    <img class="icon-red-exclamation cart-qty-alert" src="/_ui/responsive/theme-lambda/images/Exclamation-point.svg" alt=""/> 
	  <span>${product.nearestStoreStockLevel}</span> <spring:theme code="text.product.quantity.exceeded"/> 
    </div>
   <%--  <form:form id="configureForm${product.code}" action="${configureProductUrl}" method="get" class="configure_form">
        <c:if test="${product.configurable}">
            <c:choose>
                <c:when test="${(product.isRegulateditem && addIsMyStoreProduct && !addIsProductSellable) || (!addIsMyStoreProduct) || (empty product.price && empty product.priceRange.minPrice)}">
                    <button id="configureProduct" type="button" class="btn btn-primary btn-block"
                            disabled="disabled">
                        <spring:theme code="basket.configure.product"/>
                    </button>
                </c:when>
                <c:otherwise>
                    <button id="configureProduct" type="button" class="btn btn-primary btn-block js-enable-btn" disabled="disabled"
                            onclick="location.href='${configureProductUrl}'">
                        <spring:theme code="basket.configure.product"/>
                    </button>
                </c:otherwise>
            </c:choose>
        </c:if>
    </form:form> --%>
</c:if>
 <c:if test="${product.multidimensional && product.variantCount > 1}" >
	 <div class="atc-variant-button">
        <div class="orderProductFrom btn btn-block btn-default" name="sellableUomsCountValue">
            <!-- <button type="button" class="orderProductFrom btn btn-block btn-default" name="sellableUomsCountValue"> -->
                <c:url value="${product.url}" var="productUrl"/>
                <a href="${productUrl}" style="color: white; text-decoration: none;"><spring:theme code="featureProductList.selectFrom" /> ${product.variantCount} <spring:theme code="featureProductList.products" /></a>
            <!-- </button> -->
        </div>
    </div>              
</c:if>

</form:form>
</c:otherwise>
</c:choose>