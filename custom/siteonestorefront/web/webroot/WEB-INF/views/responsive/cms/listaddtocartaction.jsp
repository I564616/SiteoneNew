<%@ page trimDirectiveWhitespaces="true" %>
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

<c:set var="productQuantity_plp" value="1"/> 
<c:if test="${product.orderQuantityInterval eq null || product.orderQuantityInterval eq '0' || product.orderQuantityInterval lt '0' || product.orderQuantityInterval eq ''}">
    <c:set var="productQuantity_plp" value="1" />
</c:if>
<c:if test="${product.orderQuantityInterval ne null && product.orderQuantityInterval ne '0' && product.orderQuantityInterval gt '0' && product.orderQuantityInterval ne ''}">
    <c:set var="productQuantity_plp" value="${product.orderQuantityInterval}" />
</c:if>
<c:if test="${product.minOrderQuantity ne null && product.minOrderQuantity ne '0' && product.minOrderQuantity ne '' && empty product.sellableUoms[0].parentInventoryUOMID}">
    <c:set var="productQuantity_plp" value="${product.minOrderQuantity}" />
</c:if>
 <input type="hidden" class="productQuantity_plp"  value="${productQuantity_plp}">
 <input type="hidden" class="inventoryCheck_${product.code}" value="${product.inventoryCheck}">
 <input type="hidden" class="inventoryCheck_Atc" value="${product.inventoryCheck}">
 <input type="hidden" class="isEligibleForBackorder_Atc" value="${product.isEligibleForBackorder}">
 <input type="hidden" class="isEligibleForBackorder_${product.code}" value="${product.isEligibleForBackorder}">
<c:set var="isStockNearByPLP" value="false" />
<c:if test="${product.nearestStoreStockLevel ne '' && product.nearestStoreStockLevel ne null && product.nearestStoreStockLevel gt '0'}">
    <c:set var="isStockNearByPLP" value="true" />
</c:if>
<input type="hidden" class="isStockNearByPLPCount"  value="${product.nearestStoreStockLevel}">
<input type="hidden" class="isStockNearByPLP"  value="${isStockNearByPLP}">
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
    <form:form id="addToCartForm${product.code}" action="${addToCartUrl}" method="post" class="add_to_cart_form">
		<product:listaddtocartparameters sellableUomListLen="${sellableUomListLen}" addIsMyStoreProduct="${addIsMyStoreProduct}" onlyHubStoreAvailability="${onlyHubStoreAvailability}" addIsProductSellable="${addIsProductSellable}" isSellableInventoryHit="${isSellableInventoryHit}"/>
	<c:if test="${(not product.multidimensional)}">
        <ycommerce:testId code="addToCartButton">
            <div class="row">
                <div class= "col-md-5 plp-qty-container flex-center col-xs-5 no-padding-rgt-xs no-padding-rgt-sm no-padding-rgt-md">
                    <div class="qtyHeading"><spring:theme code="text.account.savedCart.qty" /></div>
                    <input type="text" name="productPostPLPQty" id="productPLPPostQty_${product.code}" value="${productQuantity_plp}"
                        class="form-control js-qty-selector-input js-qty-updateOne js-plp-qty js-plp-uomqtyinterval ${productQuantity_plp > product.nearestStoreStockLevel && product.eeee ? 'ohhandqty' : '' }"
                        size="1" maxlength="5" data-nurseryproduct="${product.isNurseryProduct}"
                        data-maxqty="${product.nearestStoreStockLevel}" data-min-qty="${product.orderQuantityInterval}"
                        data-eeeevalue="${product.eeee}" data-min-orderqty="${product.minOrderQuantity}" data-isbaseuomproduct="${product.sellableUoms[0].parentInventoryUOMID}"/>
                </div>
                <div class="col-md-7 col-xs-7 pad-xs-lft-5 pad-sm-lft-5 pad-md-lft-5 ">
                    <c:set var="isPLPATCClass" value="js-enable-btn" />
                    <c:set var="isPLPAriaDisabled" value="false" /> 
                    <c:set var="isPLPOrderAccountingMsg" value="false" />        
                    <c:choose>
                        <c:when test="${((product.isRegulateditem && addIsMyStoreProduct && !addIsProductSellable && !sessionStore.isLicensed) || (!addIsMyStoreProduct) || (empty product.price && empty product.priceRange.minPrice  && empty product.customerPrice) || (!(empty product.price) && product.price.value.toString() eq 0.0) || (!(empty product.customerPrice) )|| (!isOrderingAccount))}">
                            <c:set var="isPLPATCClass" value="js-plp-uomqtyinterval" />
                            <c:set var="isPLPAriaDisabled" value="true" /> 
                            <c:if test="${!isOrderingAccount}">
                                <c:set var="isPLPOrderAccountingMsg" value="true" />
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${hideCSP eq true}">
                                <c:set var="isPLPATCClass" value="js-plp-uomqtyinterval" />
                                <c:set var="isPLPAriaDisabled" value="true" />
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${product.inventoryCheck eq true && product.isEligibleForBackorder eq true && isStockNearByPLP ne true}">
                        <c:set var="isPLPATCClass" value="${isPLPATCClass} inventoryCheck-and-isEligibleForBackorder" />
                    </c:if>
                    <button type="${(orderOnlinePermissions eq true)?'submit':'button'}" class="btn btn-primary btn-block ${isPLPATCClass} js-atc-${product.code}" ${(orderOnlinePermissions eq true)?"":"name=atclistoo"} id="${(orderOnlinePermissions eq true)?buttonID:buttonIDOO}" disabled="disabled" aria-disabled="${isPLPAriaDisabled}" data-inventoryCheck="${product.inventoryCheck}" data-isEligibleForBackorder="${product.isEligibleForBackorder}" data-sellableUomsCount="${product.sellableUomsCount}" data-isRegulateditem="${product.isRegulateditem}" data-price="${product.price}" data-minPrice="${product.priceRange.minPrice}" data-customerPrice="${product.customerPrice}" data-priceValue="${product.price.value}" data-code="${product.code}"  data-orderQuantityInterval="${product.orderQuantityInterval}" data-multidimensional="${product.multidimensional}" data-variantCount="${product.variantCount}" data-stores="${product.stores}" data-nearestStore="${product.nearestStore.storeId}" data-sellableUomListLen="${sellableUomListLen}"  data-addIsMyStoreProduct="${addIsMyStoreProduct}" data-addIsProductSellable="${addIsProductSellable}" data-isLicensed="${sessionStore.isLicensed}"  data-isOrderingAccount="${isOrderingAccount}" data-hideCSP="${hideCSP}" data-hideList="${hideList}" data-orderOnlinePermissions="${orderOnlinePermissions}">
                        <spring:theme code="basket.add.to.Basket"/>
                    </button>
                    <c:if test="${isPLPOrderAccountingMsg eq true}">
                        <span class="orderAccountingMsgPLP">${orderingAccountMsg}</span>
                    </c:if>
                </div>
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