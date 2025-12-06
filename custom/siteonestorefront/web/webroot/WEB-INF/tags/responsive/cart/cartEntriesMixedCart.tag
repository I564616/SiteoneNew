<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="entries" required="true" type="java.util.List" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="grid" tagdir="/WEB-INF/tags/responsive/grid" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ attribute name="loopIndex" required="false" %>
<%@ attribute name="hubStoreNumber" required="false" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart"%>

<fmt:setLocale value="en_US" scope="session"/>
<c:set var="errorStatus" value="<%= de.hybris.platform.catalog.enums.ProductInfoStatus.valueOf(\"ERROR\") %>" />
<c:set var="unitpriceDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.unitprice.digits\")%>" />
<c:set var="totalpriceDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.totalprice.digits\")%>" />
<c:set var="isUserIdentified" value="${(isAnonymous ne true) || (guestUsers eq 'guest') ? true : false}"/>
 <c:forEach items="${entries}" var="entry" varStatus="loop">

 <c:if test="${!isAnonymous}">
 <input type="hidden" class="productcspCode" value="${entry.product.code}">
	   <input type="hidden" value="${entry.product.price.value}" class="productRetailPrice${entry.product.code}" />
	   <input type="hidden" value="${entry.basePrice.value}" class="csp${entry.product.code}"/>
	   </c:if>
 		<c:set var="enableCheckout" value="true" />
    	<c:set var="isMyStoreProduct" value="false" />
    	<c:set var="hideList" value="${entry.product.hideList}"/>
        <c:set var="hideCSP" value="${entry.product.hideCSP}"/>
        <c:set var="inventoryFlag" value="${entry.product.inventoryFlag}"/>
        <c:forEach items="${entry.product.stores}" var="store">
        		<c:if test="${store eq (empty hubStoreNumber ?  sessionStore.storeId : hubStoreNumber)}">
            		<c:set var="isMyStoreProduct" value="true" />
        		</c:if>
        </c:forEach>
        <c:if test="${(isPriceAvailable && hideList eq true) && (guestUsers ne 'guest')}">
			<c:set var="enableCheckout" value="true" />
		</c:if>
        <%--this confirms that item is available in home or a nearby store. --%>
         <c:if test="${isMyStoreProduct eq false && (empty hubStoreNumber)}">
             <c:set var="isMyStoreProduct" value="${entry.product.isStockInNearbyBranch}"/>
         </c:if>

             <c:if test="${entry.product.singleUom eq true}">
                 <c:set var="singleUom" value="true"/>
                 <c:set var="uomDescription" value="${entry.product.singleUomDescription}"/>
                 <c:set var="uomMeasure" value="${entry.product.singleUomMeasure}"/>
             </c:if>
             <c:if test="${not empty entry.product.sellableUoms}">
                <c:forEach items="${entry.product.sellableUoms}" var="sellableUom">
                      <c:set var="uomDescription" value="${sellableUom.inventoryUOMDesc}"/>
                      <c:set var="inventoryUOMIDParam" value="${sellableUom.inventoryUOMID}"/>
                </c:forEach>
             </c:if>
             <c:set var="uomMeasure" value="${entry.uomMeasure}"/>
             <c:set var="isPriceAvailable" value="false" />
                <c:if test="${entry.basePrice.value gt 0.0}">
                    <c:set var="isPriceAvailable" value="true" />
                </c:if>
             <c:set var="isStockAvailable" value="false" />
             <c:if test="${entry.product.stock.stockLevel gt 0 or (not empty entry.product.stock.inventoryHit and entry.product.stock.inventoryHit > 4)}">
                 <c:set var="isStockAvailable" value="true" />
             </c:if>
              <c:if test="${entry.basePrice.value.toString() eq 0.0}">
                 <c:set var="enableCheckout" value="false" />
             </c:if>
             <c:if test ="${hideCSP eq true}">
               <c:set var="enableCheckout" value="false" />
             </c:if>
             <!-- Inventory hit count check -->
             <%-- <c:if test="${entry.product.stock.stockLevel eq 0 && entry.product.stock.inventoryHit < 4}">
                 <c:set var="enableCheckout" value="false" />
             </c:if>

             <c:if test="${entry.product.stock.stockLevel eq 0 && entry.product.stock.inventoryHit eq null}">
                 <c:set var="enableCheckout" value="false" />
             </c:if> --%>


    		<c:set var="quantityBoxDisable" value="false" />
			<input type="hidden" id="regulated${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${entry.product.isRegulateditem}"/>
		    <c:if test="${entry.product.isRegulateditem}">
		        <c:set var="isProductSellable" value="false" />
		        <c:forEach items="${entry.product.regulatoryStates}" var="regulatoryStates">
		            <c:if test="${regulatoryStates eq sessionStore.address.region.isocodeShort}">
		                <c:set var="isProductSellable" value="true" />
		            </c:if>
		        </c:forEach>
		    </c:if>
		    <c:if test="${not empty entry.statusSummaryMap}">
		        <c:set var="errorCount" value="${entry.statusSummaryMap.get(errorStatus)}" />
		        <c:if test="${not empty errorCount && errorCount > 0}">
		            <div class="notification has-error">
		                <spring:theme code="basket.error.invalid.configuration" arguments="${errorCount}" />
		                <a href="<c:url value=" /cart/${entry.entryNumber}/configuration/${entry.configurationInfos[0].configuratorType} " />">
		                    <spring:theme code="basket.error.invalid.configuration.edit" />
		                </a>
		            </div>
		        </c:if>
   			</c:if>
    		<c:set var="showEditableGridClass" value="" />
    		<c:url value="${entry.product.url}" var="productUrl" />

    
        <%-- chevron for multi-d products --%>
       
        	
            <div class="hidden-xs hidden-sm item__toggle" style="padding:0px;width: 0px;">
                <c:if test="${entry.product.multidimensional}">
                    <div class="js-show-editable-grid" data-index="${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" data-read-only-multid-grid="${not entry.updateable}">
                        <ycommerce:testId code="cart_product_updateQuantity">
                            <span class="glyphicon glyphicon-chevron-down"></span>
                        </ycommerce:testId>
                    </div>
                </c:if>
            </div>
			
            
      <c:if test="${entry.basePrice.value.toString() ne 0.0}">
                                <c:if test="${ycommerce:doesPotentialPromotionExistForOrderEntry(cartData, entry.entryNumber)}">
                                    <c:forEach items="${cartData.potentialProductPromotions}" var="promotion">
                                        <c:set var="displayed" value="false" />
                                        <c:forEach items="${promotion.consumedEntries}" var="consumedEntry">
                                            <c:if test="${not displayed && consumedEntry.orderEntryNumber == entry.entryNumber && not empty promotion.description}">
                                                <c:set var="displayed" value="true" />

                                                <div class="promo">
                                                    <ycommerce:testId code="cart_potentialPromotion_label">
                                                        ${fn:escapeXml(promotion.description)}
                                                    </ycommerce:testId>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                </c:if>
                            </c:if>

                        
                                                                                                     
    <c:if test="${!entry.product.isRegulateditem   && !entry.product.isProductDiscontinued && isMixedCartEnabled ne true}">
        <c:choose>
            <c:when test="${isMyStoreProduct}">
                <c:choose>
                    <c:when test="${isPriceAvailable}">
                        <c:choose>
                            <c:when test="${isStockAvailable}">
                                
                            </c:when>
                            <c:otherwise>
                            <c:if test="${entry.product.outOfStockImage and (empty entry.defaultFulfillmentType)}">
                                <c:set var="enableCheckout" value="false" />
                             </c:if>       
                                        
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <c:set var="quantityBoxDisable" value="true" />
                        <input type= "hidden" id="quantityBoxDisable_${entry.product.itemNumber}" value="true" />
                        <c:set var="enableCheckout" value="false" />
                       
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <c:set var="quantityBoxDisable" value="true" />
                <input type= "hidden" id="quantityBoxDisable_${entry.product.itemNumber}" value="true" />
                <c:if test="${!entry.product.isEligibleForBackorder}">               
                <c:set var="enableCheckout" value="false" /> 
                </c:if>                           
                                       
                                        
            </c:otherwise>
        </c:choose>
    </c:if>
    <c:if test="${entry.product.isRegulateditem   && !entry.product.isProductDiscontinued}">
        <input type="hidden" value="${isProductSellable}" id="isProductSellable${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}"/>
        <input type="hidden" value="${sessionStore.isLicensed}" id="isLicensed${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}"/>
        <input type="hidden" value="${isMyStoreProduct}" id="isMyStoreProduct${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}"/>
        <c:choose>
            <c:when test="${isMyStoreProduct and !isProductSellable}">
                <c:set var="quantityBoxDisable" value="true" />
                <input type= "hidden" id="quantityBoxDisable_${entry.product.itemNumber}" value="true" />
                <c:set var="enableCheckout" value="false" />
            </c:when>

            <c:when test="${!isMyStoreProduct and isProductSellable}">
                <c:set var="quantityBoxDisable" value="true" />
                <input type= "hidden" id="quantityBoxDisable_${entry.product.itemNumber}" value="true" />
                <c:set var="enableCheckout" value="false" />
                                
                                        
                                        
            </c:when>

            <c:when test="${(isMyStoreProduct && isProductSellable) || (isMyStoreProduct && !isProductSellable && sessionStore.isLicensed)}"> 
                <c:choose>
                    <c:when test="${isPriceAvailable}">
                    <div id="cartRegulatoryLicenceCheck_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" class="hidden"> <spring:theme text="${cartRegulatoryItemLicenseExpiredMsg}" arguments="${contactNo}" htmlEscape="false" /></div>
                    <input type="hidden" value="${isStockAvailable}" id="isStockAvailable_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}">
                        <c:choose>
                             <c:when test="${isStockAvailable}">
                                
                            </c:when>
                            <c:otherwise>
                            <c:if test="${entry.product.outOfStockImage and (empty entry.defaultFulfillmentType)}">
                                <c:set var="enableCheckout" value="false" />
                             </c:if>    

                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <c:set var="quantityBoxDisable" value="true" />
                        <input type= "hidden" id="quantityBoxDisable_${entry.product.itemNumber}" value="true" />
                        <c:set var="enableCheckout" value="false" />
                        
                    </c:otherwise>
                </c:choose>
            </c:when>

            <c:otherwise>
                <c:set var="quantityBoxDisable" value="true" />
                <input type= "hidden" id="quantityBoxDisable_${entry.product.itemNumber}" value="true" />
                <c:set var="enableCheckout" value="false" />                                  
                                       
                                        
            </c:otherwise>
        </c:choose>
    </c:if>

    <c:if test="${!entry.product.isRegulateditem  && entry.product.isProductDiscontinued}">
        <c:choose>
            <c:when test="${isMyStoreProduct}">
                <c:choose>
                    <c:when test="${isPriceAvailable}">
                        <c:choose>
                            <c:when test="${isStockAvailable}">
                                <c:choose>
                                    <c:when test="${(entry.product.stock.stockLevel gt 0 or (not empty entry.product.stock.inventoryHit and entry.product.stock.inventoryHit > 4)) and entry.quantity > entry.product.stock.stockLevel}">
                                        <input type="hidden" id="entry${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${entry.product.code}" data-quantity="${entry.quantity}" />
                                     </c:when>
                                    <c:otherwise>
                                    
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <input type="hidden" id="entry${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${entry.product.code}" data-check-quantity="${entry.quantity}"/>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <c:set var="quantityBoxDisable" value="true" />
                        <input type= "hidden" id="quantityBoxDisable_${entry.product.itemNumber}" value="true" />
                        <c:set var="enableCheckout" value="false" />
                        
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <c:set var="quantityBoxDisable" value="true" />
                <input type= "hidden" id="quantityBoxDisable_${entry.product.itemNumber}" value="true" />
                <c:set var="enableCheckout" value="false" />
                <input type="hidden" id="entry${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${entry.product.code}" data-nonstore="true" />
            </c:otherwise>
        </c:choose>
    </c:if>
             
                                  
                                                <div class="col-xs-12 visible-xs visible-sm padding-zero cartPage-qtybox print-hidden">
                                                    
                                              
                                                    <c:if test="${entry.product.configurable}">
                                                        <div class="hidden-md hidden-lg">
                                                            <c:url value="/cart/${entry.entryNumber}/configuration/${entry.configurationInfos[0].configuratorType}" var="entryConfigUrl" />
                                                            <div class="item__configurations">
                                                                <c:forEach var="config" items="${entry.configurationInfos}">
                                                                    <c:set var="style" value="" />
                                                                    <c:if test="${config.status eq errorStatus}">
                                                                        <c:set var="style" value="color:red" />
                                                                    </c:if>
                                                                    <div class="item__configuration--entry" style="${style}">
                                                                        <div class="row">
                                                                            <div class="item__configuration--name col-sm-4">
                                                                                ${config.configurationLabel}
                                                                                <c:if test="${not empty config.configurationLabel}">:</c:if>
                                                                            </div>
                                                                            <div class="item__configuration--value col-sm-8">
                                                                                ${config.configurationValue}
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </c:forEach>
                                                            </div>
                                                            <c:if test="${not empty entry.configurationInfos}">
                                                                <div class="item__configurations--edit">
                                                                    <a class="btn" href="${entryConfigUrl}">
                                                                        <spring:theme code="basket.page.change.configuration" />
                                                                    </a>
                                                                </div>
                                                            </c:if>
                                                        </div>
                                                    </c:if>
                                                    <div class="cl"></div>
                                                </div>
  
<c:if test="${not empty cartData.quoteData}">
    <div class="item__list--comment">
        <div class="item__comment quote__comments">
            
                <c:choose>
                    <c:when test="${not entry.product.multidimensional}">
                        <c:set var="entryNumber" value="${entry.entryNumber}" />
                        <c:set var="entryComments" value="${entry.comments}" />
                    </c:when>
                    <c:otherwise>
                        <c:set var="entryNumber" value="${entry.entries.get(0).entryNumber}" />
                        <c:set var="entryComments" value="${entry.entries.get(0).comments}" />
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${not empty entryComments}">
                        <order:orderEntryComments entryComments="${entryComments}" entryNumber="${entryNumber}" />
                    </c:when>
                    <c:otherwise>
                        <div id="entryCommentListDiv_${fn:escapeXml(entryNumber)}" data-show-all-entry-comments="false"></div>
                    </c:otherwise>
                </c:choose>
                <c:if test="${entry.updateable}">
                    <div class="row">
                        <div class="col-sm-7 col-sm-offset-5">
                            <div id="entryCommentDiv_${fn:escapeXml(entryNumber)}" class="${not empty entryComments ?'collapse in':'collapse'}">
                                <textarea class="form-control js-quote-entry-comments" id="entryComment_${fn:escapeXml(entryNumber)}" placeholder="<spring:theme code=" quote.enter.comment "/>" data-entry-number="${fn:escapeXml(entryNumber)}" rows="3" maxlength="255"></textarea>
                            </div>
                        </div>
                    </div>
                </c:if>
            
        </div>
    </div>
</c:if>
    <div>
        <spring:url value="/cart/getProductVariantMatrix" var="targetUrl" />
        <grid:gridWrapper entry="${entry}" index="${loop.index}" styleClass="add-to-cart-order-form-wrap display-none" targetUrl="${targetUrl}" />
    </div>
	<input type="hidden" class="productCodeValue" id="cartEntry${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${entry.product.code}"/>
	<input type="hidden" id="productName${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${fn:escapeXml(entry.product.name)}"/>	
	<input type="hidden" id="productCategory${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${entry.product.level1Category}" />
	<input type="hidden" id="productSubCategory${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${entry.product.level2Category}" />
	<input type="hidden" id="productnearby${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${entry.product.isStockInNearbyBranch}" />
	<input type="hidden" id="productQty${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${entry.quantity}" />
	<input type="hidden" id="productprice${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${entry.basePrice.value}" />
	<input type="hidden" id="productstockStatus${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${entry.product.storeStockAvailabilityMsg}" />
	<!-- New Cart Page Starts -->
	
	
	<c:if test="${entry.product.stockAvailableOnlyHubStore}">
		<input type="hidden" class="isStockAvalilableOnlyHub" value="true"/>
	</c:if>
	<c:set var="dopickupEnabled" value="true" />	
	<c:set var="dodeliveryEnabled" value="false" />
	<c:set var="doshippingEnabled" value="false" />
	<c:set var="noFulfillmentavailable" value="false" />

	<c:if test="${(entry.product.outOfStockImage || entry.product.stockAvailableOnlyHubStore || (entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true))}">
		<c:set var="dopickupEnabled" value="false" />
	</c:if>

	<c:if test="${(entry.product.isEligibleForDelivery && entry.product.isDeliverable && !entry.product.stockAvailableOnlyHubStore && (!(entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true)))}">
		<c:set var="dodeliveryEnabled" value="true" />
	</c:if>

	<c:if test="${((entry.product.isShippable || entry.product.stockAvailableOnlyHubStore) && (!(entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true)))}">
		<c:set var="doshippingEnabled" value="true" />
	</c:if>

	<c:if test="${!dopickupEnabled && !dodeliveryEnabled && !doshippingEnabled}">
		<c:set var="noFulfillmentavailable" value="true" />
		<c:set var="enableCheckout" value="false" />
	</c:if>
	<input type="hidden" class="dopickupEnabled" value="${dopickupEnabled}"/>
	<input type="hidden" class="dodeliveryEnabled" value="${dodeliveryEnabled}"/>
	<input type="hidden" class="doshippingEnabled" value="${doshippingEnabled}"/>
	<input type="hidden" class="noFulfillmentavailable" value="${noFulfillmentavailable}"/>
<!-- error messages -->
	
	 <c:choose>
       	<c:when test="${((entry.product.productType eq 'Nursery' and (entry.product.level1Category eq 'Nursery' || entry.product.level1Category eq 'Vivero')) and (entry.quantity gt entry.product.stock.stockLevel) and entry.product.isTransferrable ne true and entry.product.isEligibleForBackorder ne true)||((entry.product.level1Category eq 'Materiales duros & Vida al Aire Libre' || entry.product.level1Category eq 'Hardscapes & Outdoor Living') && (entry.quantity gt entry.product.homeStoreAvailableQty) && entry.product.isTransferrable ne true && entry.product.isForceInStock ne true)}">
       		<c:set var="showErrorMsg" value=""/>
       		<c:set var="enableCheckout" value="false"/>
       		<c:set var="redBorder" value="border-red js-nursery-exceeded"/>
       	</c:when>
       	<c:when test="${entry.product.productType ne 'Nursery'}">
       		<c:set var="showErrorMsg" value="hide"/>
       		
       		<c:set var="redBorder" value=""/>
       	</c:when>
       	<c:otherwise>
       		<c:set var="showErrorMsg" value="hide"/>
			<c:set var="redBorder" value=""/>
       		
       	</c:otherwise>
       	</c:choose>
		 <c:set var="isHardscapeQty" value="false"/>
       	<c:if test="${(entry.product.level1Category eq 'Materiales duros & Vida al Aire Libre' || entry.product.level1Category eq 'Hardscapes & Outdoor Living')}">
            <c:set var="isHardscapeQty" value="true"/>
         </c:if>
        	<input type="hidden" name="isHardscapeQty" value="${isHardscapeQty}"/>
            <input type="hidden" name="homestoreqtyhardscape" value="${entry.product.homeStoreAvailableQty}"/>
            <input type="hidden" name="nurseryqtymsg" value="${ entry.product.stock.stockLevel}"/>
	<c:set var="guestRedBorder" value=""/>
	<c:set var="maxQtyRedBorder" value=""/>
	<div class="js-cart-qty-error  gc-error-msg ${showErrorMsg}" >
        		<spring:theme code="text.product.qty.alert" arguments="${isHardscapeQty ? entry.product.homeStoreAvailableQty : entry.product.stock.stockLevel}" />
     </div>	
     	<!---  Product level error message <c:if test="${!entry.product.isShippable}">	     
			<div class="gc-error-msg js-gc-error-msg hidden">					
				<spring:theme code="text.cart.parcel.shipping.error"/>					
			</div>
		</c:if> --->
	
	<c:if test="${guestUsers eq 'guest' && hideList eq true}">
		<c:set var="guestRedBorder" value="border-red "/>
		<div class="gc-error-msg">	         
           		<spring:theme code="text.cart.guest.error"/>
           	</div>	             
       	</c:if>
       	<!-- max quantity check -->
       	
    	<c:if test="${((!isAnonymous) or (guestUsers eq 'guest')) and not empty entry.maxShippingMessage}">
			<c:set var="maxQtyRedBorder" value="border-red"/>		
		</c:if>	
				             
       	<c:if test="${not empty entry.maxShippingMessage}">
       		<c:set var="enableCheckout" value="false" />
       	</c:if>
       	
       		<div class="gc-error-msg js-max-qty-err-message ${not empty entry.maxShippingMessage?'':'hidden'}">	         
	           	 <!-- error message comes here -->
	           	 ${entry.maxShippingMessage}
	        </div>
		
		<!-- max quantity check end-->    
	
		<!-- max quantity check end-->    
		<!--   Is Price Available -->
		<c:if test="${not isPriceAvailable && (isAnonymous && hideList ne true)}">
			 <c:set var="priceNotAvailable" value="border-red"/>
			<div class="gc-error-msg">	         
	           	 <spring:theme code="cart.priceunavailable.error.message"/>
	        </div>
			 
		</c:if>
		<!--   Is Price Available ends-->
				
<!-- error messages ends -->
				
<div class="row cart-items-left-pane js-cart-items-row ${!entry.product.isShippable?'shippable ':''} ${maxQtyRedBorder} ${guestRedBorder} ${redBorder} js-cart-outofstock ${entry.product.outOfStockImage and (empty entry.defaultFulfillmentType)?'outofstock border-red':''}">
	
	<input type="hidden" class="selectedFilfillment${(empty loopIndex) ? loop.index : (loopIndex + loop.index)} changed_selectedFilfillment" value="${entry.defaultFulfillmentType}" />
	<input type="hidden" class="selectedFilfillmentStore${(empty loopIndex) ? loop.index : (loopIndex + loop.index)} changed_selectedFilfillmentStore" value="" />
	
	<!-- Delivery Threshold Message at Item Level -->
	<c:if test="${(isUserIdentified eq true) and ((entry.defaultFulfillmentType eq 'delivery') and (cartData.deliveryThresholdCheckData.differenceAmount ne null and cartData.deliveryThresholdCheckData.differenceAmount gt 0))}">
		<c:set var="isDeliveryThresholdNotMet" value="true"/>
		<c:set var="enableCheckout" value="false" />
	</c:if>	
	<div data-entrynumber="${entry.entryNumber}" class="col-xs-12 marginBottom10 js-delivery-threshold-item-level ${entry.defaultFulfillmentType eq 'delivery' and isDeliveryThresholdNotMet eq true?'':'hidden' }">

			<div class="flex-center font-small-xs cart-delivery-threshold-message">
				<common:exclamationCircle />
				<div class="pad-lft-10">
					<div class="bold delivery-threshold-text ">
						<spring:theme code="cart.delivery.itemlevel.enable.condition.message1"/>&nbsp;
						$<span class="js-delivery-difference-amount">${cartData.deliveryThresholdCheckData.differenceAmount}</span>
						<spring:theme code="cart.delivery.itemlevel.enable.condition.message2"/>
					</div>
					
				</div>
			</div>
	</div>
<!-- Delivery Threshold Message at Item Level Ends -->
<!-- Shipping Threshold Message -->
			<div class="col-xs-12 marginBottom10 js-shipping-threshold ${(isUserIdentified eq true) and ((entry.defaultFulfillmentType eq 'shipping') and (cartData.shippingThresholdCheckData.differenceAmountShipping ne null and cartData.shippingThresholdCheckData.differenceAmountShipping gt 0)) ? '' : 'hidden'}">
             	<div class="flex-center font-small-xs cart-delivery-threshold-message shipping-threshold">
             	<common:parcelIcon height="18" width="30" iconColor="#000"/>
             	<div class="pad-lft-10 bold flex-1">
             		<spring:theme code="cart.free.shipping.condition.message1"/>
             		$<span class="js-shipping-difference-amount">${cartData.shippingThresholdCheckData.differenceAmountShipping}</span>
             		<spring:theme code="cart.free.shipping.condition.message2"/>
             	</div>
             	</div>
            </div>          
<!-- Shipping Threshold Message Ends -->
	<!-- Availability Messages -->
	<c:if test="${entry.basePrice.value.toString() ne 0.0}">
 <c:if test="${ycommerce:doesAppliedPromotionExistForOrderEntry(cartData, entry.entryNumber)}">
                                    <c:forEach items="${cartData.appliedProductPromotions}" var="promotion">
                                        <c:set var="displayed" value="false" />
                                        <c:forEach items="${promotion.consumedEntries}" var="consumedEntry">
                                            <c:if test="${not displayed && consumedEntry.orderEntryNumber == entry.entryNumber}">
                                                <c:set var="displayed" value="true" />
                                                <div class="promo addPromotionDesc">
                                                    <ycommerce:testId code="cart_appliedPromotion_label">
                                                       <div class="cart-promo-msg col-md-12" style="display:none;">${fn:escapeXml(promotion.description)}</div>
                                                    </ycommerce:testId>
                                                    <span class="coupon-applied">
															<svg xmlns="http://www.w3.org/2000/svg" width="80.019" height="14" viewBox="0 0 80.019 14">
																		<defs><style>.a{fill:#78a22f;}.b{fill:#fff;}</style></defs>
																		<g transform="translate(-681.093 -179.822)">
																		<g transform="translate(681.093 179.822)">
																		<path class="a" d="M761.094,184.364h.018v-3.646a.9.9,0,0,0-.892-.9H681.093v14h79.126a.9.9,0,0,0,.892-.9v-3.646h-.018a2.458,2.458,0,0,1,0-4.915Z" transform="translate(-681.093 -179.822)"/>
																		<g transform="translate(5.507 4.842)"><path class="b" d="M691.417,189.133l.826.262a1.852,1.852,0,0,1-.632,1.026,1.987,1.987,0,0,1-2.5-.239,2.194,2.194,0,0,1-.542-1.569,2.3,2.3,0,0,1,.545-1.637,1.865,1.865,0,0,1,1.43-.583,1.754,1.754,0,0,1,1.258.458,1.662,1.662,0,0,1,.432.777l-.844.2a.871.871,0,0,0-.889-.708.964.964,0,0,0-.761.337,1.657,1.657,0,0,0-.292,1.091,1.78,1.78,0,0,0,.287,1.14.937.937,0,0,0,.749.339.853.853,0,0,0,.584-.215A1.253,1.253,0,0,0,691.417,189.133Z" transform="translate(-688.567 -186.393)"/><path class="b" d="M695.635,188.6a2.68,2.68,0,0,1,.192-1.082,1.985,1.985,0,0,1,.393-.578,1.657,1.657,0,0,1,.546-.38,2.312,2.312,0,0,1,.909-.167,1.981,1.981,0,0,1,1.492.578,2.585,2.585,0,0,1,0,3.208,2.218,2.218,0,0,1-2.982,0A2.18,2.18,0,0,1,695.635,188.6Zm.878-.029a1.581,1.581,0,0,0,.331,1.087,1.139,1.139,0,0,0,1.677,0,1.618,1.618,0,0,0,.326-1.1,1.588,1.588,0,0,0-.318-1.082,1.178,1.178,0,0,0-1.694,0A1.6,1.6,0,0,0,696.513,188.572Z" transform="translate(-690.427 -186.393)"/><path class="b" d="M703.405,186.491h.852v2.285a4.7,4.7,0,0,0,.032.705.68.68,0,0,0,.261.416.908.908,0,0,0,.562.156.859.859,0,0,0,.547-.148.587.587,0,0,0,.222-.364,4.814,4.814,0,0,0,.038-.716v-2.334h.852v2.216a5.734,5.734,0,0,1-.069,1.074,1.187,1.187,0,0,1-.255.53,1.293,1.293,0,0,1-.5.344,2.156,2.156,0,0,1-.812.128,2.342,2.342,0,0,1-.917-.14,1.3,1.3,0,0,1-.494-.363,1.161,1.161,0,0,1-.239-.468,5.1,5.1,0,0,1-.083-1.071Z" transform="translate(-692.472 -186.419)"/><path class="b" d="M710.51,190.71v-4.219h1.368a4.989,4.989,0,0,1,1.013.063,1.105,1.105,0,0,1,.607.413,1.306,1.306,0,0,1,.245.822,1.372,1.372,0,0,1-.141.654,1.141,1.141,0,0,1-.358.416,1.224,1.224,0,0,1-.441.2,4.846,4.846,0,0,1-.884.06h-.556v1.592Zm.852-3.506v1.2h.466a2.2,2.2,0,0,0,.674-.066.55.55,0,0,0,.267-.207.563.563,0,0,0,.1-.328.545.545,0,0,0-.135-.38.594.594,0,0,0-.343-.187,4.1,4.1,0,0,0-.613-.029Z" transform="translate(-694.341 -186.419)"/><path class="b" d="M716.933,188.6a2.679,2.679,0,0,1,.193-1.082,1.955,1.955,0,0,1,.393-.578,1.646,1.646,0,0,1,.545-.38,2.314,2.314,0,0,1,.909-.167,1.984,1.984,0,0,1,1.493.578,2.586,2.586,0,0,1,0,3.208,2.218,2.218,0,0,1-2.982,0A2.177,2.177,0,0,1,716.933,188.6Zm.878-.029a1.585,1.585,0,0,0,.331,1.087,1.139,1.139,0,0,0,1.677,0,1.615,1.615,0,0,0,.326-1.1,1.589,1.589,0,0,0-.318-1.082,1.178,1.178,0,0,0-1.694,0A1.6,1.6,0,0,0,717.811,188.572Z" transform="translate(-696.032 -186.393)"/><path class="b" d="M724.723,190.71v-4.219h.829l1.726,2.818v-2.818h.792v4.219h-.855l-1.7-2.751v2.751Z" transform="translate(-698.082 -186.419)"/></g></g><g transform="translate(720.856 184.735)"><path class="b" d="M739.291,190.709h-.927l-.368-.958h-1.687l-.348.958h-.9l1.643-4.219h.9Zm-1.569-1.669-.581-1.566-.57,1.566Z" transform="translate(-735.057 -186.49)"/><path class="b" d="M743.217,190.709V186.49h1.367a4.964,4.964,0,0,1,1.013.063,1.109,1.109,0,0,1,.608.413,1.312,1.312,0,0,1,.245.822,1.374,1.374,0,0,1-.141.654,1.15,1.15,0,0,1-.358.416,1.244,1.244,0,0,1-.442.2,4.837,4.837,0,0,1-.883.06h-.556v1.592Zm.852-3.506v1.2h.466a2.206,2.206,0,0,0,.673-.066.556.556,0,0,0,.363-.536.548.548,0,0,0-.135-.38.589.589,0,0,0-.343-.186,4.074,4.074,0,0,0-.613-.029Z" transform="translate(-737.204 -186.49)"/><path class="b" d="M750.354,190.709V186.49h1.367a4.964,4.964,0,0,1,1.013.063,1.107,1.107,0,0,1,.608.413,1.312,1.312,0,0,1,.245.822,1.375,1.375,0,0,1-.141.654,1.151,1.151,0,0,1-.358.416,1.239,1.239,0,0,1-.442.2,4.834,4.834,0,0,1-.884.06h-.556v1.592Zm.852-3.506v1.2h.466a2.207,2.207,0,0,0,.673-.066.556.556,0,0,0,.363-.536.548.548,0,0,0-.135-.38.589.589,0,0,0-.343-.186,4.074,4.074,0,0,0-.613-.029Z" transform="translate(-739.083 -186.49)"/><path class="b" d="M757.522,190.722v-4.185h.852v3.473h2.118v.711Z" transform="translate(-740.969 -186.502)"/><path class="b" d="M764.143,190.709V186.49h.852v4.219Z" transform="translate(-742.711 -186.49)"/><path class="b" d="M768.2,190.709V186.49h3.129v.713h-2.277v.936h2.118v.711h-2.118V190h2.357v.711Z" transform="translate(-743.779 -186.49)"/><path class="b" d="M775.334,186.49h1.558a3.038,3.038,0,0,1,.8.08,1.417,1.417,0,0,1,.637.388,1.853,1.853,0,0,1,.4.684,3.116,3.116,0,0,1,.138,1,2.773,2.773,0,0,1-.13.9,1.855,1.855,0,0,1-.452.746,1.5,1.5,0,0,1-.6.337,2.568,2.568,0,0,1-.754.089h-1.6Zm.853.713V190h.636a2.347,2.347,0,0,0,.515-.04.787.787,0,0,0,.344-.176.9.9,0,0,0,.223-.407,2.758,2.758,0,0,0,.086-.773,2.525,2.525,0,0,0-.086-.752.972.972,0,0,0-.242-.408.805.805,0,0,0-.394-.2,3.885,3.885,0,0,0-.7-.041Z" transform="translate(-745.656 -186.49)"/></g></g>
															</svg>
													</span>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                </c:if>
                            </c:if>
    <c:set var="requestedQty" value="${entry.quantity}"/>
    <c:set var="homeStoreQty" value="${entry.product.pickupHomeStoreInfo.onHandQuantity}"/>
    <c:set var="nearbyStoresQty" value="${entry.product.pickupNearbyStoreInfo.onHandQuantity}"/>
    <c:set var="deliveryStoresQty" value="${entry.product.deliveryStoreInfo.onHandQuantity}"/>
    <c:set var="shippingStoresQty" value="${entry.product.shippingStoreInfo.onHandQuantity}"/>
    <c:set var="hubStoresQty" value="${entry.product.hubStoresAvailableQty}"/>
    <c:set var="showAvailableMessage" value="hidden"/>
  	<c:set var="onHandQty" value=""/>
    <c:set var="remainingQty" value=""/>
    <c:choose>
    	<c:when test="${(entry.defaultFulfillmentType eq 'pickuphome') and (requestedQty gt homeStoreQty) and (entry.product.isEligibleForBackorder ne true)}">
    		<c:set var="showAvailableMessage" value=""/>
    		<c:set var="onHandQty" value="${homeStoreQty}"/>
    		<c:set var="remainingQty" value="${requestedQty - homeStoreQty}"/>
    	</c:when>
    	<c:when test="${(entry.defaultFulfillmentType eq 'pickupnearby') and (requestedQty gt nearbyStoresQty) and (entry.product.isEligibleForBackorder ne true)}">
    		<c:set var="showAvailableMessage" value=""/>
    		<c:set var="onHandQty" value="${nearbyStoresQty}"/>
    		<c:set var="remainingQty" value="${requestedQty - nearbyStoresQty}"/>
    	</c:when>
    	<c:when test="${(entry.defaultFulfillmentType eq 'delivery') and (requestedQty gt deliveryStoresQty) and (entry.product.isEligibleForBackorder ne true)}">
    		<c:set var="showAvailableMessage" value=""/>
    		<c:set var="onHandQty" value="${deliveryStoresQty}"/>
    		<c:set var="remainingQty" value="${requestedQty - deliveryStoresQty}"/>
    	</c:when>
    	<c:when test="${(entry.defaultFulfillmentType eq 'shipping') and (requestedQty gt shippingStoresQty) and (entry.product.isEligibleForBackorder ne true)}">
    		<c:set var="showAvailableMessage" value=""/>
    		<c:set var="onHandQty" value="${shippingStoresQty}"/>
    		<c:set var="remainingQty" value="${requestedQty - shippingStoresQty}"/>
    	</c:when>
    </c:choose>
   
       <c:if test="${(entry.product.productType ne 'Nursery') and (guestUsers eq 'guest'or !isAnonymous) and (onHandQty gt 0)}">
                       
                    <div class="col-xs-12 marginBottom10 js-availablility-msg-mixed-cart ${showAvailableMessage}" 
                    data-requestedqty="${requestedQty}"
                    data-homestoreqty="${homeStoreQty}"
                    data-nearbystoreqty="${nearbyStoresQty}"
                    data-deliverystoreqty="${deliveryStoresQty}"
                    data-shippingstoresqty="${shippingStoresQty}"
                    data-isbackordarable="${entry.product.isEligibleForBackorder}"
                    >
                    <input type="hidden" class="remainingQty${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${remainingQty}" />
                        <div class="flex-center cart-availability-backorder-msg" >
                            <common:exclamationCircle />
                            <div class="pad-lft-10 bold pad-rgt-10">
	                            <spring:theme code="cart.more.quantity.than.available1"/>
	                            <span class="js-onhand-qty">${onHandQty}</span>
	                            <spring:theme code="cart.more.quantity.than.available2"/>
	                            <span class="js-remaining-qty">${remainingQty}</span>
	                            <spring:theme code="cart.more.quantity.than.available3"/>                                   
							</div>
							<div id="js-nearby-cartentry-overlay" data-prodcode="${entry.product.code}" data-category="${entry.product.level1Category}" data-subcategory="${entry.product.level2Category}" data-prodname="${fn:escapeXml(entry.product.name)}" class="flex-1 text-right">
								<input type="hidden" name="entryNumber" value="${entry.entryNumber}" />
								<input type="hidden" name="inventoryUOMID" value="${inventoryUOMIDParam}" />
								<a href="#" class="no-text-decoration bold pdp-store-link"><spring:theme code="productDetailsPanel.changeBranch" /></a>
							</div>
	                        </div>
                   		</div>                                     
                
            </c:if> 
                  
        <!-- Nearest Backorderable at Iten Level -->
			<input type="hidden" class="analytics_backourderValue${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${entry.product.isEligibleForBackorder}"/>
			<div class="col-xs-12 marginBottom10 ${entry.product.isEligibleForBackorder ? '':'hidden'}">
			<div class="flex-center font-small-xs cart-delivery-threshold-message">
				<common:exclamationCircle />
					<div class="pad-lft-10 pad-rgt-10 cart-threshold-message">
						<div class="bold delivery-threshold-text">
							<spring:theme code="cart.backorder.message" arguments="${not empty entry.product.nearestBackorderableStore ? entry.product.nearestBackorderableStore.name : entry.product.pickupHomeStoreInfo.storeName}" />
						</div>				
						<div id="js-nearby-cartentry-overlay" data-prodcode="${entry.product.code}"  data-category="${entry.product.level1Category}" data-subcategory="${entry.product.level2Category}" data-prodname="${fn:escapeXml(entry.product.name)}" class="full-width text-right">
							<input type="hidden" name="entryNumber" value="${entry.entryNumber}" />
							<input type="hidden" name="inventoryUOMID" value="${inventoryUOMIDParam}" />
							<a href="#" class="no-text-decoration bold pdp-store-link"><spring:theme code="productDetailsPanel.changeBranch" /></a>
						</div>
						<c:set var="quantityBoxDisable" value="false" />
						
					</div>
					</div>
					</div>
				
		<!-- Nearest Backorderable at Iten Level Ends-->
		
         
        
        <c:if test="${entry.product.outOfStockImage and (empty entry.defaultFulfillmentType)}">
            <div class="col-xs-12 marginBottom10 js-parcelShippingFulfillment-oos">
             <div class="flex-center cart-availability-out-of-stock-msg">
                 <common:crossMarkIcon iconColor="#5a5b5d" width="24" height="24" />
                 <span class="pad-lft-10"><spring:theme code="cart.outofstock.message" arguments="${sessionStore.address.phone}"/></span>
             </div>
             </div>
        </c:if>

<!-- Availability Messages Ends -->
<div class="col-xs-12">
	<div class="row">
	<!-- Product Image starts MD-->
		<div class="col-xs-4 col-md-2 no-padding-rgt-xs hidden-xs hidden-sm">
			<div class="cart-img">
	            <a href="${productUrl}">
	                <product:productPrimaryImage product="${entry.product}" format="product" />
	            </a>
	        </div>
		</div>
	<!-- Product Image Ends MD-->
	<div class="col-xs-12 col-md-10">
			<div class="row flex-md">
		
	<!-- Product Description Starts -->
		<div class="col-xs-12 col-md-7 cart-product-description-container cart-product-description-container--mixedcart">
			<div class="cart-item-number">
			${fn:escapeXml(entry.product.itemNumber)} 
			<span class="bold pad-lft-25">
				<c:if test="${entry.product.baseProduct != null}">
                    <c:forEach items="${entry.product.categories}" var="option">
                        ${option.parentCategoryName}:${option.name}
                    </c:forEach>
           		</c:if>
			</span>
			</div>
			
			<ycommerce:testId code="cart_product_name">
                  <div><a href="${productUrl}" class="black-title bold no-text-decoration">${fn:escapeXml(entry.product.name)}</a></div>                          
            </ycommerce:testId>
			
			
            
           
			
		</div>
	<!-- Product Description Ends -->
	<!-- Mobile only Product Image and Qty  Starts -->
	<div class="row marginTopBVottom20 mob-img-qty-container hidden-md hidden-lg">
		<!-- Mobile only Product Image Starts -->
			<div class="col-xs-6 col-md-2 hidden-md hidden-lg">
				<div class="cart-img">
		            <a href="${productUrl}">
		                <product:productPrimaryImage product="${entry.product}" format="product" />
		            </a>
		        </div>
			</div>
		<!-- Mobile only Product Image Ends -->
		<!-- Mobile Only cart Qty and Price-->
			<c:if test="${entry.product.isEligibleForBackorder}">
   	   			<c:set var="quantityBoxDisable" value="false" />
   	   			<input type= "hidden" id="quantityBoxDisable_${entry.product.itemNumber}" value="${quantityBoxDisable}" />
   			</c:if>
        	<div class="col-xs-6 hidden-md  hidden-lg cart-qty-container ">
				<div class="col-xs-12 col-md-2 cart-total-left-pane no-padding-rgt-xs">
		
         	<c:if test="${isPriceAvailable}">


			<ycommerce:testId code="cart_totalProductPrice_label">
			
			
			
			<div class="js-item-total black-title b-price add_price text-right no-margin h3 ">
			
			
			
			<c:choose>
			
			<c:when test="${hideCSP eq true}">
			
			$0.00
			
			</c:when>
			
			<c:otherwise>
			
			<c:choose>
			
			<c:when test="${inventoryFlag eq true}">
			
			$0.00
			
			</c:when>
			
			<c:otherwise>
			
			<c:choose>
			
			<c:when test="${!isAnonymous || (isAnonymous && hideList ne true)}">
			
			<format:price priceData="${entry.totalPrice}" displayFreeForZero="true" />
			
			</c:when>
			
			</c:choose>
			
			</c:otherwise>
			
			</c:choose>
			
			</c:otherwise>
			
			</c:choose>
			

			</div>
			
			</ycommerce:testId>
			
			</c:if>
			<!-- Product Unit Price Starts -->
		<div class="col-xs-12 col-md-12 no-padding-rgt-md text-right cart-price-container">
			<div class="row cart-price-row">
			          
			<div class="flex-end-xs flex-end-sm">
			<div class="col-md-12 retail-your-price-section retail-your-price-section--mixedcart">
			<c:if test="${isPriceAvailable}">
                                    <c:choose>
	                                     <c:when test="${entry.isCustomerPrice}">
	                                        
	                                            <c:choose>
	                                                <c:when test="${ycommerce:doesAppliedPromotionExistForOrderEntry(cartData, entry.entryNumber)}">
	                                                    <c:forEach items="${cartData.appliedProductPromotions}" var="promotion">
	                                                        <c:set var="displayed" value="false" />
	                                                        <c:forEach items="${promotion.consumedEntries}" var="consumedEntry">
	                                                            <c:if test="${not displayed && consumedEntry.orderEntryNumber == entry.entryNumber}">
	                                                                <c:set var="displayed" value="true" />
	                                                               
	                                                                 <div>
	                                                                 	<c:set var="totalPrice" value="${ycommerce:cartEntryTotalPromotionPrice(entry.quantity, entry.totalPrice)}"/>  
	                                                                    <c:choose>
	                                                                        <c:when test="${ycommerce:isPromotionAppliedWithAdjustedPrice(totalPrice, entry.basePrice, consumedEntry.adjustedUnitPrice)}">
	                                                                             <c:choose>
	                                                                                 <c:when test="${not empty uomDescription}">
	                                                                                    <span class="black-title  b-price  add_price">  <span class="js-cart-unit-price"><format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/></span> / ${uomMeasure} </span>
	                                                                                 </c:when>
	                                                                                 <c:otherwise>
	                                                                                      <span class="js-cart-unit-price"> <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/>   </span>
	                                                                                 </c:otherwise>
	                                                                             </c:choose>
	                                                                            
	                                                                        </c:when>
	                                                                        <c:otherwise>
	                                                                            <c:choose>
	                                                                                 <c:when test="${not empty uomDescription}">
	                                                                                      <p class="discountPrice-cartPage discount-price "> <span class="js-cart-unit-discount-price">$<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${totalPrice}" /></span> / ${uomMeasure}</p>
	                                                                                      <del><span class="black-title b-price add_price">  <span class="js-cart-unit-price"> <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/></span> / ${uomMeasure}</span></del> 
	                                                                                 </c:when>
	                                                                                 <c:otherwise>
	                                                                                      <p class="discountPrice-cartPage  discount-price"> <span class="js-cart-unit-discount-price">$<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${totalPrice}" /></span></p>
	                                                                                      <del> <span class="js-cart-unit-price"> <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/></span> </del>
	                                                                                 </c:otherwise>
	                                                                             </c:choose>
	                                                                        </c:otherwise>
	                                                                    </c:choose>
	                                                                </div>
	                                                            </c:if>
	                                                        </c:forEach>
	                                                    </c:forEach>
	                                                </c:when>
	                                                <c:otherwise>
	                                                    <c:choose>
	                                                         <c:when test="${not empty uomDescription}">
													
																<div class="">
																	<c:choose>
																		<c:when test="${hideCSP eq true}">
																			<c:if test="${hideList ne true}">
																					<p class="cart-price-text"><spring:theme code="text.cart.siteOnelistprice"/></p>
							                                                 		<span class="black-title  b-price add_price"> <span class="js-cart-unit-price">$$<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${entry.getListPrice()}" /></span> / ${uomMeasure}</span>
							                                                 	</c:if>
																					<p class="callBranchForPrice js-cart-unit-price"><spring:theme code="text.product.callforpricing"/>
																				
																		</c:when>
																		<c:otherwise>
																			<c:choose>
				                                                                    <c:when test="${inventoryFlag eq true}">
				                                                                    	<p class="callBranchForPrice js-cart-unit-price"><spring:theme code="text.product.callforpricing"/>
				                                                                    </c:when>
				                                                                    <c:otherwise>
				                                                                    	<c:if test="${entry.basePrice ne null}">
				                                                                    	<p class="cart-price-text"><spring:theme code="cartItems.yourPrice" /></p>
				                                                                    	</c:if>
																						<span class="black-title b-price  add_price"> <span class="js-cart-unit-price"><format:price
																						priceData="${entry.basePrice}"
																						displayFreeForZero="false" unitPrice="true"/></span> / ${uomMeasure}</span>
																					</c:otherwise>
																			</c:choose>
																		</c:otherwise>
																  </c:choose>
				
																</div>
															</c:when>
	                                                         <c:otherwise>
	                                                            
	                                                            
	                                                            
	                                                   <div class="">          
	                                                                <c:choose>
	                                                                     <c:when test="${hideCSP eq true}">
	                                                                        <p class="callBranchForPrice js-cart-unit-price"><spring:theme code="text.product.callforpricing"/>
	                                                                          </c:when>
	                                                                    <c:otherwise>
	                                                                    <c:if test="${entry.basePrice ne null}">
	                                                                    	<p class="cart-price-text"><spring:theme code="cartItems.yourPrice" /></p>
	                                                                    </c:if>
	                                                                	<span class="js-cart-unit-price"><format:price priceData="${entry.basePrice}" displayFreeForZero="false" unitPrice="true"/></span>
	                                                                    </c:otherwise></c:choose></div>
	                                                               
	                                                         </c:otherwise>
	                                                    </c:choose>	                                                
	                                                </c:otherwise>	
	                                            </c:choose>	                                           
	                                            </c:when>
	                                            
	                                            <c:otherwise>
		                                            
		                                            <c:choose>
		                                                <c:when test="${ycommerce:doesAppliedPromotionExistForOrderEntry(cartData, entry.entryNumber)}">
		                                                    <c:forEach items="${cartData.appliedProductPromotions}" var="promotion">
		                                                        <c:set var="displayed" value="false" />
		                                                        <c:forEach items="${promotion.consumedEntries}" var="consumedEntry">
		                                                            <c:if test="${not displayed && consumedEntry.orderEntryNumber == entry.entryNumber}">
		                                                                <c:set var="displayed" value="true" />
		                                                                
		                                                                 <div class=""><c:set var="totalPrice" value="${ycommerce:cartEntryTotalPromotionPrice(entry.quantity, entry.totalPrice)}"/>  
		                                                                    <c:choose>
		                                                                        <c:when test="${ycommerce:isPromotionAppliedWithAdjustedPrice(totalPrice, entry.basePrice, consumedEntry.adjustedUnitPrice)}">
		                                                                             <c:choose>
																			               <c:when test="${not empty uomDescription}">
																			                   <span class="black-title   b-price add_price">  <span class="js-cart-unit-price"><format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true" /></span> / ${uomMeasure}</span>
																			               </c:when>
																			               <c:otherwise>
																			                   <span class="js-cart-unit-price"><format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true" /></span>
																			               </c:otherwise>
																			             </c:choose>
		                                                                        </c:when>
		                                                                        <c:otherwise>
		                                                                             <c:choose>
		                                                                                <c:when test="${not empty uomDescription}">
		                                                                                       <p class="discountPrice-cartPage  discount-price"><span class="js-cart-unit-discount-price">$<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${totalPrice}" /></span> / ${uomMeasure}</p>
		                                                                        <del> <span class="black-title  b-price add_price"><span class="js-cart-unit-price"><format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/></span> / ${uomMeasure}</span></del>
		                                                                                </c:when>
		                                                                                <c:otherwise>
		                                                                                       <p class="discountPrice-cartPage  discount-price"><span class="js-cart-unit-discount-price">$<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${totalPrice}" /></span></p>
		                                                                                       <del><format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/></del>
		                                                                                </c:otherwise>
		                                                                              </c:choose>
		                                                                        
		                                                                        </c:otherwise>
		                                                                    </c:choose>
		                                                                </div>
		                                                            </c:if>
		                                                        </c:forEach>
		                                                    </c:forEach>
		                                                </c:when>
		                                                <c:otherwise>
		                                                    <c:choose>
													               <c:when test="${not empty uomDescription}">
		                                                             <c:choose>
		                                                                     <c:when test="${hideList ne true && hideCSP ne true}">
		                                                                     	<c:choose>
										            							<c:when test="${inventoryFlag eq true }">
										            									<p class="callBranchForPrice js-cart-unit-price"><spring:theme code="text.product.callforpricing"/>
										            							</c:when>
										            							<c:otherwise>
		                                                                         	<p class="cart-price-text"><spring:theme code="text.cart.siteOnelistprice"/></p>
		                                                              			 	<span class="black-title  b-price add_price"> <span class="js-cart-unit-price"><format:price priceData="${entry.basePrice}" displayFreeForZero="false" unitPrice="true"/></span> / ${uomMeasure} </span>
		                                                              			</c:otherwise>
		                                                              			</c:choose>
		                                                                      </c:when>
		                                                                    <c:otherwise>
		                                                                		<c:choose>
		                                                                			<c:when test="${hideCSP ne true}">
		                                                                				<c:choose>
		                                                                					<c:when test="${inventoryFlag eq true}">
		                                                                						<p class="callBranchForPrice js-cart-unit-price"><spring:theme code="text.product.callforpricing"/>
		                                                                					</c:when>
		                                                                					<c:otherwise>
		                                                                					<sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')" var="loggedIn">
		                                                                						<c:if test="${product.customerPrice ne null}">
		                                                                							<p class="cart-price-text"><spring:theme code="cartItems.yourPrice" /></p>
		                                                                						</c:if>
									                      										<div class="black-title font-Geogrotesque-bold  b-price add_price"><span class="js-cart-unit-price"> <format:fromPrice priceData="${product.customerPrice}"/></span><br></div> </p>
		                                                                					</sec:authorize>
		                                                                					</c:otherwise>
		                                                                				</c:choose>
		                                                                			</c:when>
		                                                                			<c:otherwise>
		                                                                				<c:choose>
		                                                                					<c:when test="${hideList eq true }">
		                                                                						<p class="callBranchForPrice js-cart-unit-price"><spring:theme code="text.product.callforpricing"/>
		                                                                					</c:when>
		                                                                					<c:otherwise>
		                                                                						<c:choose>
		                                                                							<c:when test="${inventoryFlag eq true}">
		                                                                								<p class="callBranchForPrice js-cart-unit-price"><spring:theme code="text.product.callforpricing"/>
		                                                                							</c:when>
		                                                                							<c:otherwise>
		                                                                						 		<p class="cart-price-text"><spring:theme code="text.cart.siteOnelistprice"/></p>
		                                                             							 		<span class="black-title  b-price add_price"><span class="js-cart-unit-price">$${entry.getListPrice()} </span>/ ${uomMeasure}</span>
		                                                             							 		<p class="callBranchForPrice js-cart-unit-price"><spring:theme code="text.product.callforpricing"/>
		                                                                							</c:otherwise>
		                                                                						</c:choose>
		                                                                					</c:otherwise>
		                                                                				</c:choose>
		                                                                			</c:otherwise>
		                                                                		</c:choose>
													                  </c:otherwise>
													                  </c:choose>   
		                                                                
		                                                                
													               </c:when>
													               <c:otherwise>
		                                                                 <c:choose>
		                                                                     <c:when test="${hideList ne true && hideCSP ne true}">
		                                                                     	<c:choose>
										            							<c:when test="${inventoryFlag eq true }">
										            									<p class="callBranchForPrice js-cart-unit-price"><spring:theme code="text.product.callforpricing"/>
										            							</c:when>
										            							<c:otherwise>
		                                                                         	<p class="cart-price-text"><spring:theme code="text.cart.siteOnelistprice"/></p>
		                                                              			 	<span class="black-title b-price  add_price"> <span class="js-cart-unit-price"><format:price priceData="${entry.basePrice}" displayFreeForZero="false" unitPrice="true"/></span> / ${uomMeasure} </span>
		                                                              			</c:otherwise>
		                                                              			</c:choose>
		                                                                      </c:when>
		                                                                    <c:otherwise>
		                                                                		<c:choose>
		                                                                			<c:when test="${hideCSP ne true}">
		                                                                				<c:choose>
		                                                                					<c:when test="${inventoryFlag eq true}">
		                                                                						<p class="callBranchForPrice js-cart-unit-price"><spring:theme code="text.product.callforpricing"/>
		                                                                					</c:when>
		                                                                					<c:otherwise>
		                                                                					<sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')" var="loggedIn">
		                                                                						 <c:if test="${product.customerPrice ne null}">
		                                                                						 	<p class="cart-price-text"><spring:theme code="cartItems.yourPrice" /></p>
		                                                                						 </c:if>
									                      										 <div class="black-title font-Geogrotesque-bold  b-price add_price"><span class="js-cart-unit-price"> <format:fromPrice priceData="${product.customerPrice}"/></span><br></div> </p>
		                                                                					</sec:authorize>
		                                                                					</c:otherwise>
		                                                                				</c:choose>
		                                                                			</c:when>
		                                                                			<c:otherwise>
		                                                                				<c:choose>
		                                                                					<c:when test="${hideList eq true }">
		                                                                						<p class="callBranchForPrice js-cart-unit-price"><spring:theme code="text.product.callforpricing"/>
		                                                                					</c:when>
		                                                                					<c:otherwise>
		                                                                						<c:choose>
		                                                                							<c:when test="${inventoryFlag eq true}">
		                                                                								<p class="callBranchForPrice "><spring:theme code="text.product.callforpricing"/>
		                                                                							</c:when>
		                                                                							<c:otherwise>
		                                                                						 		<p class="cart-price-text"><spring:theme code="text.cart.siteOnelistprice"/></p>
		                                                             							 		<span class="black-title b-price  add_price"><span class="js-cart-unit-price">$${entry.getListPrice()}</span> / ${uomMeasure}</span>
		                                                             							 	</c:otherwise>
		                                                                						</c:choose>
		                                                                					</c:otherwise>
		                                                                				</c:choose>
		                                                                			</c:otherwise>
		                                                                		</c:choose>
													                  </c:otherwise>
													                  </c:choose>
													               </c:otherwise>
													             </c:choose>
		                                                
		                                                </c:otherwise>
		
		                                            </c:choose>
	                                            
	                                            
	                                            </c:otherwise>
                                            </c:choose>
                                    </c:if>
                                    
                                    </div>
                                    
                                    
             						</div>
             						
             						
                                    </div>
		</div>
		<!-- Product Unit Price Ends mobile -->
		</div>		
					<c:url value="/cart/update" var="cartUpdateFormAction" />
		            <form:form id="updateCartForm${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" action="${cartUpdateFormAction}" method="post" modelAttribute="updateQuantityForm${entry.entryNumber}" class="js-qty-form${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" data-cart='{"cartCode" : "${cartData.code}","productPostPrice":"${entry.basePrice.value}","productName":"${fn:escapeXml(entry.product.name)}"}'>
		                <input type="hidden" name="entryNumber" value="${entry.entryNumber}" />
		                <input type="hidden" name="productCode" value="${entry.product.code}" />
		                <input type="hidden" name="initialQuantity" value="${entry.quantity}" />
		                <input type="hidden" name="productName" value="${fn:escapeXml(entry.product.name)}" />
		                <input type="hidden" name="productCategory" value="${entry.product.level1Category}" />
		                <input type="hidden" name="productSubCategory" value="${entry.product.level2Category}" />
		                 <div class="row">
		                 <div class="col-xs-12 no-padding-lft-xs">
		                <ycommerce:testId code="cart_product_quantity">                                                 
									<button class="js-update-entry-quantity-btn minusQty cart-qty-btn" type="button" ${quantityBoxDisable?'disabled':''} id="cartminusQty_${entry.product.itemNumber}_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}"><common:minus-green /></button>
                                          <c:choose>
							            <c:when test="${hideList eq true && hideCSP eq true}">
							               <form:input cssClass="cartQtyBox cart-qty-input cart-qty-input--mixedcart form-control js-update-entry-quantity-input txtalign print-input js-qty-updateOne" disabled="${quantityBoxDisable}" type="text" maxlength="5" size="1" id="quantity_${entry.product.itemNumber}" path="quantity" value="0"/>
						                  </c:when>
							           <c:otherwise>
							               <form:input cssClass="cartQtyBox  cart-qty-input cart-qty-input--mixedcart form-control js-update-entry-quantity-input txtalign print-input js-qty-updateOne" disabled="${quantityBoxDisable}" type="text" maxlength="5" size="1" id="quantity_${entry.product.itemNumber}" path="quantity" />
                       					</c:otherwise>
							          </c:choose>
									<button class="js-update-entry-quantity-btn plusQty cart-qty-btn" type="button" ${quantityBoxDisable?'disabled':''} id="cartplusQty_${entry.product.itemNumber}_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}"><common:plus-green /></button>
                                           <c:if test="${quantityBoxDisable}">
                                               <form:input type="hidden" path="quantity" value="0" />
                                           </c:if>
                                       </ycommerce:testId>
                                       </div>
                                       <div class="col-xs-12 flex-center remove-list-container remove-list-container--mixedcart no-padding-lft-xs">
                                       <ycommerce:testId code="cart_product_removeProduct">
                                           <div class="cart-padding js-remove-entry-button cart-remove-link cart-remove-link--mixedcart print-hidden" id="removeEntry_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}">
                                               <a href="#">
                                                   <spring:theme code="basket.page.remove" />
                                               </a>
                                           </div>
                                       </ycommerce:testId>

		                                        <input type="hidden" class="moveTolistCart" name="moveTolistCart" value="true" />
		                                        <div class="cart-padding print-hidden" id="moveEntry_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}">
		                                            <a class="wishlistAddProLink signInOverlay" id="moveEntry_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" data-role="${entry.product.code}|${entry.quantity}"><spring:theme code="saveListDetailsPage.moveto.list" /></a>
		                                        </div>
		                                        </div>
		                                        </div>
		
		                                    </form:form>
		                                
						
					<c:if test="${entry.product.multidimensional}">
						<ycommerce:testId code="cart_product_updateQuantity">
							<span class="glyphicon glyphicon-chevron-right"></span>
						</ycommerce:testId>
					</c:if>
		         
    						</div>
         <!-- mobile only cart qty ends -->
	</div>
	<!-- Mobile only Product Image and Qty Ends -->
		<!-- Product Qty Starts -->
		<c:if test="${entry.product.isEligibleForBackorder}">
    	   	<c:set var="quantityBoxDisable" value="false" />
    	   	<input type= "hidden" id="quantityBoxDisable_${entry.product.itemNumber}" value="${quantityBoxDisable}" />
    	</c:if>
		<div class="col-xs-9 hidden-xs hidden-sm col-md-3 cart-qty-container">
			<div class="row">			
			<c:url value="/cart/update" var="cartUpdateFormAction" />
            <form:form id="updateCartForm${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" action="${cartUpdateFormAction}" method="post" modelAttribute="updateQuantityForm${entry.entryNumber}" class="js-qty-form${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" data-cart='{"cartCode" : "${cartData.code}","productPostPrice":"${entry.basePrice.value}","productName":"${fn:escapeXml(entry.product.name)}"}'>
                <input type="hidden" name="entryNumber" value="${entry.entryNumber}" />
                <input type="hidden" name="productCode" value="${entry.product.code}" />
                <input type="hidden" name="initialQuantity" value="${entry.quantity}" />
                <input type="hidden" name="productName" value="${fn:escapeXml(entry.product.name)}" />
                <input type="hidden" name="productCategory" value="${entry.product.level1Category}" />
                <input type="hidden" name="productSubCategory" value="${entry.product.level2Category}" />
                 <div class="col-xs-12 no-padding-lft-xs">
                <ycommerce:testId code="cart_product_quantity">                                                 
										<button class="js-update-entry-quantity-btn minusQty cart-qty-btn" type="button" id="cartminusQty_${entry.product.itemNumber}_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" ${quantityBoxDisable ? 'disabled' : '' }><common:minus-green /></button>
                                           <c:choose>
								            <c:when test="${hideList eq true && hideCSP eq true}">
								               <form:input cssClass="cartQtyBox cart-qty-input cart-qty-input--mixedcart form-control js-update-entry-quantity-input txtalign print-input js-qty-updateOne" disabled="${quantityBoxDisable}" type="text" maxlength="5" size="1" id="quantity_${entry.product.itemNumber}_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" path="quantity" value="0"/>
							                  </c:when>
								           <c:otherwise>
								               <form:input cssClass="cartQtyBox  cart-qty-input cart-qty-input--mixedcart form-control js-update-entry-quantity-input txtalign print-input js-qty-updateOne" disabled="${quantityBoxDisable}" type="text" maxlength="5" size="1" id="quantity_${entry.product.itemNumber}_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" path="quantity" />
                        					</c:otherwise>
								          </c:choose>
										<button class="js-update-entry-quantity-btn plusQty cart-qty-btn" type="button" id="cartplusQty_${entry.product.itemNumber}_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" ${quantityBoxDisable ? 'disabled' : '' }><common:plus-green /></button>

                                            <c:if test="${quantityBoxDisable}">
                                                <form:input type="hidden" path="quantity" value="0" />
                                            </c:if>
                                        </ycommerce:testId>
                                        </div>
                                        <div class="col-xs-12 flex-center remove-list-container remove-list-container--mixedcart no-padding-lft-xs">
                                        <ycommerce:testId code="cart_product_removeProduct">
                                            <div class="cart-padding js-remove-entry-button cart-remove-link cart-remove-link--mixedcart print-hidden" id="removeEntry_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}">
                                                <a href="#">
                                                    <spring:theme code="basket.page.remove" />
                                                </a>
                                            </div>
                                        </ycommerce:testId>

                                        <input type="hidden" class="moveTolistCart" name="moveTolistCart" value="true" />
                                        <div class="cart-padding print-hidden" id="moveEntry_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}">
                                            <a class="wishlistAddProLink signInOverlay" id="moveEntry_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" data-role="${entry.product.itemNumber}|${entry.quantity}|${entry.product.code}"><spring:theme code="saveListDetailsPage.moveto.list" /></a>
                                        </div>
                                        </div>

                                    </form:form>
                                
				
			<c:if test="${entry.product.multidimensional}">
				<ycommerce:testId code="cart_product_updateQuantity">
					<span class="glyphicon glyphicon-chevron-right"></span>
				</ycommerce:testId>
			</c:if>
         </div>
         </div>
		<!-- Product Qty ends -->
                                                         
                       
		<!-- Total Starts -->
		<div class="col-xs-12 col-md-2 hidden-xs hidden-sm cart-total-left-pane">
		
         <c:if test="${isPriceAvailable}">


			<ycommerce:testId code="cart_totalProductPrice_label">
			
			
			
			<div class="js-item-total black-title b-price add_price text-right no-margin h3 ">
			
			
			
			<c:choose>
			
			<c:when test="${hideCSP eq true}">
			
			$0.00
			
			</c:when>
			
			<c:otherwise>
			
			<c:choose>
			
			<c:when test="${inventoryFlag eq true}">
			
			$0.00
			
			</c:when>
			
			<c:otherwise>
			
			<c:choose>
			
			<c:when test="${!isAnonymous || (isAnonymous && hideList ne true)}">
			
			<format:price priceData="${entry.totalPrice}" displayFreeForZero="true" />
			
			</c:when>
			
			</c:choose>
			
			</c:otherwise>
			
			</c:choose>
			
			</c:otherwise>
			
			</c:choose>
			

			</div>
			
			</ycommerce:testId>
			
			</c:if>
			<!-- Product Unit Price Starts -->
		<div class="col-xs-12 col-md-12 no-padding-rgt-md text-right cart-price-container">
			<div class="row cart-price-row">
			          
			<div class="flex-end-xs flex-end-sm">
			<div class="col-md-12 retail-your-price-section retail-your-price-section--mixedcart">
			<c:if test="${isPriceAvailable}">
                                    <c:choose>
	                                     <c:when test="${entry.isCustomerPrice}">
	                                        
	                                            <c:choose>
	                                                <c:when test="${ycommerce:doesAppliedPromotionExistForOrderEntry(cartData, entry.entryNumber)}">
	                                                    <c:forEach items="${cartData.appliedProductPromotions}" var="promotion">
	                                                        <c:set var="displayed" value="false" />
	                                                        <c:forEach items="${promotion.consumedEntries}" var="consumedEntry">
	                                                            <c:if test="${not displayed && consumedEntry.orderEntryNumber == entry.entryNumber}">
	                                                                <c:set var="displayed" value="true" />
	                                                               
	                                                                 <div>
	                                                                 	<c:set var="totalPrice" value="${ycommerce:cartEntryTotalPromotionPrice(entry.quantity, entry.totalPrice)}"/>  
	                                                                    <c:choose>
	                                                                        <c:when test="${ycommerce:isPromotionAppliedWithAdjustedPrice(totalPrice, entry.basePrice, consumedEntry.adjustedUnitPrice)}">
	                                                                             <c:choose>
	                                                                                 <c:when test="${not empty uomDescription}">
	                                                                                    <span class="black-title  b-price  add_price">  <span class="js-cart-unit-price"><format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/></span> / ${uomMeasure} </span>
	                                                                                 </c:when>
	                                                                                 <c:otherwise>
	                                                                                      <span class="js-cart-unit-price"> <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/>   </span>
	                                                                                 </c:otherwise>
	                                                                             </c:choose>
	                                                                            
	                                                                        </c:when>
	                                                                        <c:otherwise>
	                                                                            <c:choose>
	                                                                                 <c:when test="${not empty uomDescription}">
	                                                                                      <p class="discountPrice-cartPage discount-price "> <span class="js-cart-unit-discount-price">$<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${totalPrice}" /></span> / ${uomMeasure}</p>
	                                                                                      <del><span class="black-title b-price add_price">  <span class="js-cart-unit-price"> <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/></span> / ${uomMeasure}</span></del> 
	                                                                                 </c:when>
	                                                                                 <c:otherwise>
	                                                                                      <p class="discountPrice-cartPage  discount-price"> <span class="js-cart-unit-discount-price">$<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${totalPrice}" /></span></p>
	                                                                                      <del> <span class="js-cart-unit-price"> <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/></span> </del>
	                                                                                 </c:otherwise>
	                                                                             </c:choose>
	                                                                        </c:otherwise>
	                                                                    </c:choose>
	                                                                </div>
	                                                            </c:if>
	                                                        </c:forEach>
	                                                    </c:forEach>
	                                                </c:when>
	                                                <c:otherwise>
	                                                    <c:choose>
	                                                         <c:when test="${not empty uomDescription}">
													
																<div class="">
																	<c:choose>
																		<c:when test="${hideCSP eq true}">
																			<c:if test="${hideList ne true}">
																					<p class="cart-price-text"><spring:theme code="text.cart.siteOnelistprice"/></p>
							                                                 		<span class="black-title  b-price add_price"> <span class="js-cart-unit-price">$$<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${entry.getListPrice()}" /></span> / ${uomMeasure}</span>
							                                                 	</c:if>
																					<p class="callBranchForPrice js-cart-unit-price"><spring:theme code="text.product.callforpricing"/>
																				
																		</c:when>
																		<c:otherwise>
																			<c:choose>
				                                                                    <c:when test="${inventoryFlag eq true}">
				                                                                    	<p class="callBranchForPrice js-cart-unit-price"><spring:theme code="text.product.callforpricing"/>
				                                                                    </c:when>
				                                                                    <c:otherwise>
				                                                                    	<c:if test="${entry.basePrice ne null}">
				                                                                    	<p class="cart-price-text"><spring:theme code="cartItems.yourPrice" /></p>
				                                                                    	</c:if>
																						<span class="black-title b-price  add_price"> <span class="js-cart-unit-price"><format:price
																						priceData="${entry.basePrice}"
																						displayFreeForZero="false" unitPrice="true"/></span> / ${uomMeasure}</span>
																					</c:otherwise>
																			</c:choose>
																		</c:otherwise>
																  </c:choose>
				
																</div>
															</c:when>
	                                                         <c:otherwise>
	                                                            
	                                                            
	                                                            
	                                                   <div class="">          
	                                                                <c:choose>
	                                                                     <c:when test="${hideCSP eq true}">
	                                                                        <p class="callBranchForPrice js-cart-unit-price"><spring:theme code="text.product.callforpricing"/>
	                                                                          </c:when>
	                                                                    <c:otherwise>
	                                                                    <c:if test="${entry.basePrice ne null}">
	                                                                    	<p class="cart-price-text"><spring:theme code="cartItems.yourPrice" /></p>
	                                                                    </c:if>
	                                                                	<span class="js-cart-unit-price"><format:price priceData="${entry.basePrice}" displayFreeForZero="false" unitPrice="true"/></span>
	                                                                    </c:otherwise></c:choose></div>
	                                                               
	                                                         </c:otherwise>
	                                                    </c:choose>	                                                
	                                                </c:otherwise>	
	                                            </c:choose>	                                           
	                                            </c:when>
	                                            
	                                            <c:otherwise>
		                                            
		                                            <c:choose>
		                                                <c:when test="${ycommerce:doesAppliedPromotionExistForOrderEntry(cartData, entry.entryNumber)}">
		                                                    <c:forEach items="${cartData.appliedProductPromotions}" var="promotion">
		                                                        <c:set var="displayed" value="false" />
		                                                        <c:forEach items="${promotion.consumedEntries}" var="consumedEntry">
		                                                            <c:if test="${not displayed && consumedEntry.orderEntryNumber == entry.entryNumber}">
		                                                                <c:set var="displayed" value="true" />
		                                                                
		                                                                 <div class=""><c:set var="totalPrice" value="${ycommerce:cartEntryTotalPromotionPrice(entry.quantity, entry.totalPrice)}"/>  
		                                                                    <c:choose>
		                                                                        <c:when test="${ycommerce:isPromotionAppliedWithAdjustedPrice(totalPrice, entry.basePrice, consumedEntry.adjustedUnitPrice)}">
		                                                                             <c:choose>
																			               <c:when test="${not empty uomDescription}">
																			                   <span class="black-title   b-price add_price">  <span class="js-cart-unit-price"><format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true" /></span> / ${uomMeasure}</span>
																			               </c:when>
																			               <c:otherwise>
																			                   <span class="js-cart-unit-price"><format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true" /></span>
																			               </c:otherwise>
																			             </c:choose>
		                                                                        </c:when>
		                                                                        <c:otherwise>
		                                                                             <c:choose>
		                                                                                <c:when test="${not empty uomDescription}">
		                                                                                       <p class="discountPrice-cartPage  discount-price"><span class="js-cart-unit-discount-price">$<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${totalPrice}" /></span> / ${uomMeasure}</p>
		                                                                        <del> <span class="black-title  b-price add_price"><span class="js-cart-unit-price"><format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/></span> / ${uomMeasure}</span></del>
		                                                                                </c:when>
		                                                                                <c:otherwise>
		                                                                                       <p class="discountPrice-cartPage  discount-price"><span class="js-cart-unit-discount-price">$<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${totalPrice}" /></span></p>
		                                                                                       <del><format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/></del>
		                                                                                </c:otherwise>
		                                                                              </c:choose>
		                                                                        
		                                                                        </c:otherwise>
		                                                                    </c:choose>
		                                                                </div>
		                                                            </c:if>
		                                                        </c:forEach>
		                                                    </c:forEach>
		                                                </c:when>
		                                                <c:otherwise>
		                                                    <c:choose>
													               <c:when test="${not empty uomDescription}">
		                                                             <c:choose>
		                                                                     <c:when test="${hideList ne true && hideCSP ne true}">
		                                                                     	<c:choose>
										            							<c:when test="${inventoryFlag eq true }">
										            									<p class="callBranchForPrice js-cart-unit-price"><spring:theme code="text.product.callforpricing"/>
										            							</c:when>
										            							<c:otherwise>
		                                                                         	<p class="cart-price-text"><spring:theme code="text.cart.siteOnelistprice"/></p>
		                                                              			 	<span class="black-title  b-price add_price"> <span class="js-cart-unit-price"><format:price priceData="${entry.basePrice}" displayFreeForZero="false" unitPrice="true"/></span> / ${uomMeasure} </span>
		                                                              			</c:otherwise>
		                                                              			</c:choose>
		                                                                      </c:when>
		                                                                    <c:otherwise>
		                                                                		<c:choose>
		                                                                			<c:when test="${hideCSP ne true}">
		                                                                				<c:choose>
		                                                                					<c:when test="${inventoryFlag eq true}">
		                                                                						<p class="callBranchForPrice js-cart-unit-price"><spring:theme code="text.product.callforpricing"/>
		                                                                					</c:when>
		                                                                					<c:otherwise>
		                                                                					<sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')" var="loggedIn">
		                                                                						<c:if test="${product.customerPrice ne null}">
		                                                                							<p class="cart-price-text"><spring:theme code="cartItems.yourPrice" /></p>
		                                                                						</c:if>
									                      										<div class="black-title font-Geogrotesque-bold  b-price add_price"><span class="js-cart-unit-price"> <format:fromPrice priceData="${product.customerPrice}"/></span><br></div> </p>
		                                                                					</sec:authorize>
		                                                                					</c:otherwise>
		                                                                				</c:choose>
		                                                                			</c:when>
		                                                                			<c:otherwise>
		                                                                				<c:choose>
		                                                                					<c:when test="${hideList eq true }">
		                                                                						<p class="callBranchForPrice js-cart-unit-price"><spring:theme code="text.product.callforpricing"/>
		                                                                					</c:when>
		                                                                					<c:otherwise>
		                                                                						<c:choose>
		                                                                							<c:when test="${inventoryFlag eq true}">
		                                                                								<p class="callBranchForPrice js-cart-unit-price"><spring:theme code="text.product.callforpricing"/>
		                                                                							</c:when>
		                                                                							<c:otherwise>
		                                                                						 		<p class="cart-price-text"><spring:theme code="text.cart.siteOnelistprice"/></p>
		                                                             							 		<span class="black-title  b-price add_price"><span class="js-cart-unit-price">$${entry.getListPrice()} </span>/ ${uomMeasure}</span>
		                                                             							 		<p class="callBranchForPrice js-cart-unit-price"><spring:theme code="text.product.callforpricing"/>
		                                                                							</c:otherwise>
		                                                                						</c:choose>
		                                                                					</c:otherwise>
		                                                                				</c:choose>
		                                                                			</c:otherwise>
		                                                                		</c:choose>
													                  </c:otherwise>
													                  </c:choose>   
		                                                                
		                                                                
													               </c:when>
													               <c:otherwise>
		                                                                 <c:choose>
		                                                                     <c:when test="${hideList ne true && hideCSP ne true}">
		                                                                     	<c:choose>
										            							<c:when test="${inventoryFlag eq true }">
										            									<p class="callBranchForPrice js-cart-unit-price"><spring:theme code="text.product.callforpricing"/>
										            							</c:when>
										            							<c:otherwise>
		                                                                         	<p class="cart-price-text"><spring:theme code="text.cart.siteOnelistprice"/></p>
		                                                              			 	<span class="black-title b-price  add_price"> <span class="js-cart-unit-price"><format:price priceData="${entry.basePrice}" displayFreeForZero="false" unitPrice="true"/></span> / ${uomMeasure} </span>
		                                                              			</c:otherwise>
		                                                              			</c:choose>
		                                                                      </c:when>
		                                                                    <c:otherwise>
		                                                                		<c:choose>
		                                                                			<c:when test="${hideCSP ne true}">
		                                                                				<c:choose>
		                                                                					<c:when test="${inventoryFlag eq true}">
		                                                                						<p class="callBranchForPrice js-cart-unit-price"><spring:theme code="text.product.callforpricing"/>
		                                                                					</c:when>
		                                                                					<c:otherwise>
		                                                                					<sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')" var="loggedIn">
		                                                                						 <c:if test="${product.customerPrice ne null}">
		                                                                						 	<p class="cart-price-text"><spring:theme code="cartItems.yourPrice" /></p>
		                                                                						 </c:if>
									                      										 <div class="black-title font-Geogrotesque-bold  b-price add_price"><span class="js-cart-unit-price"> <format:fromPrice priceData="${product.customerPrice}"/></span><br></div> </p>
		                                                                					</sec:authorize>
		                                                                					</c:otherwise>
		                                                                				</c:choose>
		                                                                			</c:when>
		                                                                			<c:otherwise>
		                                                                				<c:choose>
		                                                                					<c:when test="${hideList eq true }">
		                                                                						<p class="callBranchForPrice js-cart-unit-price"><spring:theme code="text.product.callforpricing"/>
		                                                                					</c:when>
		                                                                					<c:otherwise>
		                                                                						<c:choose>
		                                                                							<c:when test="${inventoryFlag eq true}">
		                                                                								<p class="callBranchForPrice "><spring:theme code="text.product.callforpricing"/>
		                                                                							</c:when>
		                                                                							<c:otherwise>
		                                                                						 		<p class="cart-price-text"><spring:theme code="text.cart.siteOnelistprice"/></p>
		                                                             							 		<span class="black-title b-price  add_price"><span class="js-cart-unit-price">$${entry.getListPrice()}</span> / ${uomMeasure}</span>
		                                                             							 	</c:otherwise>
		                                                                						</c:choose>
		                                                                					</c:otherwise>
		                                                                				</c:choose>
		                                                                			</c:otherwise>
		                                                                		</c:choose>
													                  </c:otherwise>
													                  </c:choose>
													               </c:otherwise>
													             </c:choose>
		                                                
		                                                </c:otherwise>
		
		                                            </c:choose>
	                                            
	                                            
	                                            </c:otherwise>
                                            </c:choose>
                                    </c:if>
                                    
                                    
                                    
                                    </div>
                                    <!-- Mobile Only Total starts-->
                                    
             						</div>
             						<!-- Mobile Only Total ends -->
             						
                                    </div>
		</div>
		<!-- Product Unit Price Ends -->
		</div>
		<!-- Total Ends -->
			</div>
          	<cart:cartEntriesFulfilmentMixedCart entry="${entry}" isUserIdentified="${isUserIdentified}" isDeliveryThresholdNotMet="${isDeliveryThresholdNotMet}"/>
    </div>
  	</div>
	
	
			
				
				 
				  <c:forEach items="${entry.product.potentialPromotions}" var="promotion" varStatus="loop">
					 <c:if test="${loop.last}"> 
					 
				 <c:choose>
				<c:when test="${not empty entry.product.couponCode}">
						<div class="cl"></div>
						<div class="promotion col-md-12 col-xs-12">
						<div class="flex-sec">
						<div class="col-md-2 paddingTB10 col-xs-5 padding0 dashed-border colored message-center">
	                    <input type="hidden" id="promoProductCode" name="promoProductCode" value="${entry.product.code}">
						<span class="col-xs-5 col-md-3"><input type="checkbox" id="clipCoupon" name="clipCoupon" value="${entry.product.couponCode}"></span>
						<span class="green-title col-xs-9 padding0"><spring:theme code="text.coupon.title" /></span>
				</div>
						<div class="triangle-top"></div>
						<div class="triangle-bottom"></div>
						<div class="col-md-10 paddingTB10 col-xs-7 message-center">
							<div class="col-md-11 col-xs-8 wordbreakmobile">${promotion.description}</div>	
						 <a class="promotooltip pull-right"><spring:theme code="text.details.title" /> 
  						<span class="tooltiptext">${entry.product.promoDetails}</span>
							</a>
							</div>
							</div>
							<div class="cl"></div>
						</div>					
				</c:when>
				<c:otherwise>
				<c:if test="${entry.product.isPromoDescriptionEnabled}">
					<div class="promotion"> <div class="paddingTB10">${promotion.description}</div></div>
					</c:if>
				</c:otherwise>
			</c:choose>
			</c:if>
					</c:forEach>
				 
		</div>
	</div>
	
	<input type="hidden" id="enableCheckout" value="${enableCheckout}"/>
</c:forEach>
<common:nearbyOverlay/>
