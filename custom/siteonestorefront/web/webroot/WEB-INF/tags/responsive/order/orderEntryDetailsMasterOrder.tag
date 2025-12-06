<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="order" required="false" type="de.hybris.platform.commercefacades.order.data.AbstractOrderData" %>
<%@ attribute name="orderEntry" required="true" type="de.hybris.platform.commercefacades.order.data.OrderEntryData" %>
<%@ attribute name="consignmentEntry" required="false"
              type="de.hybris.platform.commercefacades.order.data.ConsignmentEntryData" %>
<%@ attribute name="itemIndex" required="true" type="java.lang.Integer" %>
<%@ attribute name="targetUrl" required="false" type="java.lang.String" %>
<%@ attribute name="showStock" required="false" type="java.lang.Boolean" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="grid" tagdir="/WEB-INF/tags/responsive/grid" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<input type="hidden" id="currentCartId" name="currentCartId" value="${user.currentCartId}">
<%-- setting locale to 'en_US' to format price with dot for decimal values and comma as grouping separator  
     reset to current locale in the end of page  --%>
<fmt:setLocale value="en_US" scope="session"/>
<c:set var="varShowStock" value="${(empty showStock) ? true : showStock}" />
<c:set var="unitpriceDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.unitprice.digits\")%>" />

<c:url value="${orderEntry.product.url}" var="productUrl"/>
<c:set var="entryStock" value="${orderEntry.product.stock.stockLevelStatus.code}"/>
<spring:url value="/" var="homelink" htmlEscape="false"/>
<c:set var="enableCheckout" value="true" />
<c:choose>
	<c:when
		test="${orderEntry.product.productType eq 'Nursery' and (orderEntry.quantity gt orderEntry.product.stock.stockLevel)}">
		<c:set var="showErrorMsg" value="hide" />
		<c:set var="disableAtc" value="" />
	</c:when>
	<c:otherwise>
		<c:set var="showErrorMsg" value="hide" />
	</c:otherwise>
</c:choose>
<div class="orderSummaryItems">
			<div class=" hidden-xs hidden-sm">
		        <div class="col-xs-12 sec-title-bar">
		            <div class="col-md-4 item__image-title"><spring:theme code="basket.page.product.information"/></div>
		            <div class="col-md-2 item__price-title"><spring:theme code="basket.page.price"/></div>
		            <div class="col-md-2 item__quantity-title"><spring:theme code="basket.page.qty"/></div>
		            <div class="col-md-2"><spring:theme code="basket.page.total"/></div>
		            <div class="col-md-2 "><spring:theme code="basket.add.again"/></div>
		        </div>
		    </div>
		</div>
<div class="col-xs-12 item__list--header padding0">
	<div class="js-order-details-qty-error js-cart-qty-error marginBottom20 text-red ${showErrorMsg}">
		<img class="icon-red-exclamation cart-qty-alert"
			src="${themeResourcePath}/images/Exclamation-point.svg" alt""/>
		<spring:theme code="text.product.qty.alert"
			arguments="${orderEntry.product.stock.stockLevel}" />
	</div>
	<div class="col-md-4 col-xs-12 item__image-wrapper padding0">
    	<%-- chevron for multi-d products --%>
        <div class="hidden-xs hidden-sm item__toggle">
            <c:if test="${orderEntry.product.multidimensional}" >
                <div class="js-show-editable-grid" data-index="${loop.index}" data-read-only-multid-grid="${not orderEntry.updateable}">
                    <ycommerce:testId code="cart_product_updateQuantity">
                        <span class="glyphicon glyphicon-chevron-down"></span>
                    </ycommerce:testId>
                </div>
            </c:if>
        </div>
        <div class="col-xs-12">
        <div class="row">
	        <%-- product image --%>
	        <div class="col-md-4 col-xs-6 product-image-thumb">
		        <c:choose>
           			 <c:when test="${orderEntry.product.availableStatus eq false}">
        				<div class="item__image">
               	 			<product:productPrimaryImage product="${orderEntry.product}" format="product"/>
            			</div>
        			</c:when>
        	
        			<c:otherwise>
		        		<div class="item__image">
		           			 <a href="${productUrl}"><product:productPrimaryImage product="${orderEntry.product}" format="product"/></a>
		        		</div>
		        	</c:otherwise>
		        </c:choose>
	        </div>
	        		    <c:set var="uomMeasure" value="${orderEntry.uomMeasure}"/>
            <div class="col-md-8 col-xs-6">
	            <%-- product name, code, promotions --%>
	            <div class="row">
	            <div>${fn:escapeXml(orderEntry.product.itemNumber)}</div>
	                <ycommerce:testId code="cart_product_name">
	                	<c:set var="productShortName" value="${fn:escapeXml(orderEntry.product.name)}"></c:set>
	                	<c:set var="mobileProductShortName" value="${productShortName}"></c:set>
	                	<c:if test="${fn:length(mobileProductShortName) gt 45}">
		                	<c:set var="mobileProductShortName" value="${fn:substring(mobileProductShortName, 0, 42)}..."></c:set>
		                	<c:set var="mobileProductShortName" value="${mobileProductShortName}"></c:set>
	                	</c:if>
	                	<c:if test="${fn:length(productShortName) gt 70}">
		                	<c:set var="productShortName" value="${fn:substring(productShortName, 0, 67)}..."></c:set>
		                	<c:set var="productShortName" value="${productShortName}"></c:set>
	                	</c:if>
	                    <c:choose>
           				 <c:when test="${orderEntry.product.availableStatus eq false}">
           				 	<div class="item__name-wrapper">
	                    		<div class="hide-for-mobile"><span class="item__name">${productShortName}</span></div>
	                    		<div class="show-for-mobile"><span class="item__name">${mobileProductShortName}</span></div>
	                    	</div>
	                    </c:when>
	                    <c:otherwise>
	                    <div class="item__name-wrapper">
	                    	<a href="${productUrl}" class="hide-for-mobile"><span class="item__name">${productShortName}</span></a>
	                    	<a href="${productUrl}" class="show-for-mobile"><span class="item__name">${mobileProductShortName}</span></a>
	                    </div>
	                    </c:otherwise>
	                    </c:choose>
	                </ycommerce:testId>
	                <c:if test="${orderEntry.product.baseProduct != null}">
						<c:forEach items="${orderEntry.product.categories}" var="option">
							${option.parentCategoryName}:${option.name}<br/>
						</c:forEach>
					</c:if>
	            	<div class="cl"><br/></div>
	            	<%-- availability --%>
	            	<div class="message-center">
	            	<c:choose>
	                                   <c:when test="${orderEntry.product.availableStatus eq false || orderEntry.product.isProductDiscontinued}">
		                               <c:if test="${orderEntry.product.availableStatus eq false}">
	                                           <img class="inventory-message-icon" src="${commonResourcePath}/images/S1-out-of-stock-Xg.svg" alt=""/><span class="stock"><spring:theme code="text.order.notsoldonline" /></span>
	                                  </c:if>
	                                  <c:if test="${orderEntry.product.isProductDiscontinued}">
	                                    <img class="inventory-message-icon" src="${commonResourcePath}/images/S1-out-of-stock-Xg.svg" alt=""/><span class="stock"><spring:theme code="text.order.productdiscontinued" /></span>
	                                  </c:if>
	                                  </c:when>
	                              <c:otherwise>                    
                                    <c:if test="${!orderEntry.product.isRegulateditem   && !orderEntry.product.isProductDiscontinued}">
                                        <c:choose>
                                            <c:when test="${isMyStoreProduct}">
                                                <c:choose>
                                                    <c:when test="${isPriceAvailable}">
                                                        <c:choose>
                                                            <c:when test="${isStockAvailable}">
                                                                <c:choose>
                                                                    <c:when test="${orderEntry.quantity > orderEntry.product.stock.stockLevel}">
                                                                        <c:if test="${orderEntry.product.inStockImage}">
                                                                        	<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
                                                                            <span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage}">
                                                                            <common:exclamatoryIcon iconColor="#ed8606"/><span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <common:crossMarkIcon iconColor="#5a5b5d"/><span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <c:if test="${orderEntry.product.inStockImage}">
                                                                            <c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage}">
                                                                            <common:exclamatoryIcon iconColor="#ed8606"/><span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <common:crossMarkIcon iconColor="#5a5b5d"/><span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                            	<c:set var="enableCheckout" value="false" />
                                                                        <c:if test="${orderEntry.product.inStockImage}">
                                                                            <c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage}">
                                                                            <common:exclamatoryIcon iconColor="#ed8606"/><span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <common:crossMarkIcon iconColor="#5a5b5d"/><span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="quantityBoxDisable" value="true" />
                                                        <input type= "hidden" id="quantityBoxDisable_${orderEntry.product.itemNumber}" value="true" />
                                                        <c:set var="enableCheckout" value="false" />
                                                        <spring:theme text="${cartRegularItemPriceNotAvailable}" arguments="${contactNo}" htmlEscape="false" />
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="quantityBoxDisable" value="true" />
                                                <input type= "hidden" id="quantityBoxDisable_${orderEntry.product.itemNumber}" value="true" />
                                                <c:set var="enableCheckout" value="false" />
                                                                      <c:if test="${orderEntry.product.inStockImage}">
                                                                            <c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage}">
                                                                            <common:exclamatoryIcon iconColor="#ed8606"/><span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <common:crossMarkIcon iconColor="#5a5b5d"/><span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>
                                    <c:if test="${orderEntry.product.isRegulateditem   && !orderEntry.product.isProductDiscontinued}">
                                    	<input type="hidden" value="${isProductSellable}" id="isProductSellable${loop.index}"/>
                                    	<input type="hidden" value="${sessionStore.isLicensed}" id="isLicensed${loop.index}"/>
                                    	<input type="hidden" value="${isMyStoreProduct}" id="isMyStoreProduct${loop.index}"/>
                                        <c:choose>
                                            <c:when test="${isMyStoreProduct and !isProductSellable}">
                                                <c:set var="quantityBoxDisable" value="true" />
                                                <input type= "hidden" id="quantityBoxDisable_${orderEntry.product.itemNumber}" value="true" />
                                                <c:set var="enableCheckout" value="false" />
                                                <div id="cartRegulatoryMessage_${loop.index}"><spring:theme text="${cartRegulatoryItemNotSellableInStateMsg}" arguments="${contactNo}" htmlEscape="false" /></div>
                                            </c:when>

                                            <c:when test="${!isMyStoreProduct and isProductSellable}">
                                                <c:set var="quantityBoxDisable" value="true" />
                                                <input type= "hidden" id="quantityBoxDisable_${orderEntry.product.itemNumber}" value="true" />
                                                <c:set var="enableCheckout" value="false" />
                                                                <c:if test="${orderEntry.product.inStockImage}">
                                                                            <c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage}">
                                                                            <common:exclamatoryIcon iconColor="#ed8606"/><span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <common:crossMarkIcon iconColor="#5a5b5d"/><span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                            </c:when>

                                            <c:when test="${(isMyStoreProduct && isProductSellable) || (isMyStoreProduct && !isProductSellable && sessionStore.isLicensed)}"> 
                                                <c:choose>
                                                    <c:when test="${isPriceAvailable}">
                                                    <div id="cartRegulatoryLicenceCheck_${loop.index}" class="hidden"> <spring:theme text="${cartRegulatoryItemLicenseExpiredMsg}" arguments="${contactNo}" htmlEscape="false" /></div>
                                                    <input type="hidden" value="${isStockAvailable}" id="isStockAvailable_${loop.index}">
                                                        <c:choose>
                                                             <c:when test="${isStockAvailable}">
                                                                <c:choose>
                                                                    <c:when test="${orderEntry.quantity > orderEntry.product.stock.stockLevel}">
                                                                        <c:if test="${orderEntry.product.inStockImage}">
                                                                            <c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			<span id="cartRegulatoryItemInSufficientStockMsg_${loop.index}"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage}">
                                                                            <common:exclamatoryIcon iconColor="#ed8606"/><span id="cartRegulatoryItemInSufficientStockMsg_${loop.index}"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <common:crossMarkIcon iconColor="#5a5b5d"/><span id="cartRegulatoryItemInSufficientStockMsg_${loop.index}"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                     </c:when>
                                                                    <c:otherwise>
                                                                    <c:choose>
                                                                     <c:when test="${hideCSP eq true}">
                                                                     <p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
                                                                          </c:when>
                                                                    <c:otherwise>
                                                                      <c:if test="${orderEntry.product.inStockImage}">
                                                                            <c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			<span id="cartRegulatoryItemInStockMsg_${loop.index}"><span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage}">
                                                                            <common:exclamatoryIcon iconColor="#ed8606"/><span id="cartRegulatoryItemInStockMsg_${loop.index}"><span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <common:crossMarkIcon iconColor="#5a5b5d"/><span id="cartRegulatoryItemInStockMsg_${loop.index}"><span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span></span>
                                                                        </c:if>
                                                                         </c:otherwise></c:choose>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                            
                                                            	<c:set var="enableCheckout" value="false" />
                                                                 <c:if test="${orderEntry.product.inStockImage}">
                                                                            <c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage}">
                                                                            <common:exclamatoryIcon iconColor="#ed8606"/><span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <common:crossMarkIcon iconColor="#5a5b5d"/><span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>

                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="quantityBoxDisable" value="true" />
                                                        <input type= "hidden" id="quantityBoxDisable_${orderEntry.product.itemNumber}" value="true" />
                                                        <c:set var="enableCheckout" value="false" />
                                                        <spring:theme text="${cartRegulatoryItemPriceNotAvailablekMsg}" arguments="${contactNo}" htmlEscape="false"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>

                                            <c:otherwise>
                                                <c:set var="quantityBoxDisable" value="true" />
                                                <input type= "hidden" id="quantityBoxDisable_${orderEntry.product.itemNumber}" value="true" />
                                                <c:set var="enableCheckout" value="false" />
                                                                             <c:if test="${orderEntry.product.inStockImage}">
	                                                                            <c:choose>				
																					<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																						<common:checkmarkIcon iconColor="#ef8700"/>
																					</c:when>
																					<c:otherwise>
																						<common:checkmarkIcon iconColor="#78a22f"/>
																					</c:otherwise>
																				</c:choose>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage}">
                                                                            <common:exclamatoryIcon iconColor="#ed8606"/><span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <common:crossMarkIcon iconColor="#5a5b5d"/><span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                              </c:if>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>

                                    <c:if test="${!orderEntry.product.isRegulateditem  && orderEntry.product.isProductDiscontinued}">
                                        <c:choose>
                                            <c:when test="${isMyStoreProduct}">
                                                <c:choose>
                                                    <c:when test="${isPriceAvailable}">
                                                        <c:choose>
                                                            <c:when test="${isStockAvailable}">
                                                                <c:choose>
                                                                    <c:when test="${(orderEntry.product.stock.stockLevel gt 0 or (not empty orderEntry.product.stock.inventoryHit and orderEntry.product.stock.inventoryHit > 4)) and orderEntry.quantity > orderEntry.product.stock.stockLevel}">
                                                                        <input type="hidden" id="orderEntry${loop.index}" value="${orderEntry.product.code}" data-quantity="${orderEntry.quantity}" />
                                                                        <span id="sufficient${loop.index}" class="collapse"><spring:theme text="${cartNLAItemSufficientStockMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        <span id="insufficient${loop.index}" class="collapse"><spring:theme text="${cartNLAItemInSufficientMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                    <c:choose>
                                                                     <c:when test="${hideCSP eq true}">
                                                                    <p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
                                                                          </c:when>
                                                                    <c:otherwise>
                                                                     <c:if test="${orderEntry.product.inStockImage}">
                                                                            <c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage}">
                                                                            <common:exclamatoryIcon iconColor="#ed8606"/><span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <common:crossMarkIcon iconColor="#5a5b5d"/><span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                    </c:otherwise></c:choose>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="hidden" id="entry${loop.index}" value="${orderEntry.product.code}" data-check-quantity="${orderEntry.quantity}"/>
                                                                <span id="inStock${loop.index}" class="collapse"><spring:theme text="${cartNLAItemInStockMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                <span id="outOfStock${loop.index}" class="collapse"><spring:theme text="${cartNLAItemOutOfStockMsg}" arguments="${contactNo}" htmlEscape="false"/></span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="quantityBoxDisable" value="true" />
                                                        <input type= "hidden" id="quantityBoxDisable_${orderEntry.product.itemNumber}" value="true" />
                                                        <c:set var="enableCheckout" value="false" />
                                                        <spring:theme text="${cartRegulatoryItemPriceNotAvailablekMsg}" arguments="${contactNo}" htmlEscape="false" />
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="quantityBoxDisable" value="true" />
                                                <input type= "hidden" id="quantityBoxDisable_${orderEntry.product.itemNumber}" value="true" />
                                                <c:set var="enableCheckout" value="false" />
                                                <input type="hidden" id="entry${loop.index}" value="${orderEntry.product.code}" data-nonstore="true" />
                                                <span id="notCarried${loop.index}" class="collapse"><spring:theme text="${cartNLAItemNotCarriedMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>
                                    </c:otherwise>
                                    </c:choose>
                                    </div>
			        <c:if test="${varShowStock}">
			        	<div class="item__stock">
			            	<ycommerce:testId code="orderDetail_productStock_label">
			            	</ycommerce:testId>
			        	</div>
			        </c:if>
			        <!-- Custom attribute for coupon description which is populated through order feed -->
	                 <c:if test="${not empty orderEntry.couponDescription}">
	                                <div class="addPromotionDesc">
	                                    <ycommerce:testId code="cart_appliedPromotion_label">
	                                        ${fn:escapeXml(orderEntry.couponDescription)}
	                                    </ycommerce:testId>
	                                </div>
	                 </c:if>
	                <c:if test="${not empty order.appliedProductPromotions}">
	                    <c:forEach items="${order.appliedProductPromotions}" var="promotion">
	                        <c:set var="displayed" value="false"/>
	                        <c:forEach items="${promotion.consumedEntries}" var="consumedEntry">
	                            <c:if test="${not displayed && consumedEntry.orderEntryNumber == orderEntry.entryNumber}">
	                                <c:set var="displayed" value="true"/>
	                                <div class="addPromotionDesc">
	                                    <ycommerce:testId code="cart_appliedPromotion_label">
	                                        ${fn:escapeXml(promotion.description)}
	                                    </ycommerce:testId>
	                                </div>
	                            </c:if>
	                        </c:forEach>
	                    </c:forEach>
	                </c:if>
	                <common:configurationInfos entry="${orderEntry}"/>
	            </div>
            </div>
		</div>
		</div>
	</div>
	
    <div class="col-md-2 col-xs-12 item__price-wrapper">
    	<%-- price --%>
 		<div class="item__price">
        	<span class="visible-xs visible-sm col-xs-6 text-left bold-text"><spring:theme code="basket.page.itemPrice"/></span>
           	<div class="col-xs-6 col-md-12">
           	<div class="row">
	           	<c:choose>
                	<c:when test="${ycommerce:isAppliedPromotionExistForOrderEntry(order, orderEntry.entryNumber)}">
	                    <c:forEach items="${order.appliedProductPromotions}" var="promotion">
	                        <c:set var="displayed" value="false"/>
	                        <c:forEach items="${promotion.consumedEntries}" var="consumedEntry">
	                            <c:if test="${not displayed && consumedEntry.orderEntryNumber == orderEntry.entryNumber}">
	                                <c:set var="displayed" value="true"/>
	                                <div><c:set var="totalPrice" value="${ycommerce:cartEntryTotalPromotionPrice(orderEntry.quantity, orderEntry.totalPrice)}"/>
								         <c:choose>
											 <c:when test="${ycommerce:isPromotionAppliedWithAdjustedPrice(totalPrice, orderEntry.basePrice, consumedEntry.adjustedUnitPrice)}">
												<c:choose>
												 <c:when test="${not empty uomMeasure}">
												    <format:price priceData="${orderEntry.basePrice}" displayFreeForZero="true" unitPrice="true"/> / ${uomMeasure}
												 </c:when>
												<c:otherwise>
												   <format:price priceData="${orderEntry.basePrice}" displayFreeForZero="true" unitPrice="true"/>
												</c:otherwise>
												</c:choose>
											 </c:when>
											<c:otherwise>
											  <c:choose>
												 <c:when test="${not empty uomMeasure}">
												     <del> <format:price priceData="${orderEntry.basePrice}" displayFreeForZero="true" unitPrice="true"/> / ${uomMeasure}</del>

											         <div class="hidden-md cl hidden-lg"></div>
                               <p class="discount-price">$<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${totalPrice}"/> / ${uomMeasure}</p>
												 </c:when>
												<c:otherwise>
												   <del> <format:price priceData="${orderEntry.basePrice}" displayFreeForZero="true" unitPrice="true"/></del>
                           <div class="hidden-md cl hidden-lg"></div>
											       <p class="discount-price">$<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${totalPrice}" /></p>
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
		             <c:when test="${not empty orderEntry.actualItemCost && not empty orderEntry.discountAmount &&  orderEntry.actualItemCost.value > 0  &&  orderEntry.discountAmount.value > 0 }">
		                 <c:choose>
							 <c:when test="${not empty uomMeasure}">
								      <del><format:price priceData="${orderEntry.actualItemCost}" displayFreeForZero="true" unitPrice="true"/> / ${uomMeasure}</del>
								      <div class="hidden-md cl hidden-lg"></div>
							          <p class="discount-price"><format:price priceData="${orderEntry.basePrice}" displayFreeForZero="true" unitPrice="true"/> / ${uomMeasure}</p>
							 </c:when>
							 <c:otherwise>
					              <del><format:price priceData="${orderEntry.actualItemCost}" displayFreeForZero="true" unitPrice="true"/></del> 
					              <div class="hidden-md cl hidden-lg"></div>
								  <p class="discount-price"><format:price priceData="${orderEntry.basePrice}" displayFreeForZero="true" unitPrice="true"/></p>
							 </c:otherwise>
					     </c:choose>
		            
		             </c:when>
		             <c:otherwise>
		                <c:choose>
						  <c:when test="${not empty uomMeasure}">
						     <format:price priceData="${orderEntry.basePrice}" displayFreeForZero="false" unitPrice="true"/> / ${uomMeasure}
						  </c:when>
						 <c:otherwise>
						    <format:price priceData="${orderEntry.basePrice}" displayFreeForZero="false" unitPrice="true"/>
						 </c:otherwise>
					   </c:choose>
		             </c:otherwise>
		             </c:choose>
                    </c:otherwise>
			</c:choose>
				</div>
           	</div>
 		</div>
    </div>
    <div class="col-md-2 col-xs-12 item__quantity-wrapper">
    	<!--    quantity -->
		<div class="item__quantity">
		 <c:forEach items="${orderEntry.product.baseOptions}" var="option">
		     <c:if test="${not empty option.selected and option.selected.url eq orderEntry.product.url}">
		         <c:forEach items="${option.selected.variantOptionQualifiers}" var="selectedOption">
		             <div>
		                 <ycommerce:testId code="orderDetail_variantOption_label">
		                     <span>${fn:escapeXml(selectedOption.name)}:</span>
		                     <span>${fn:escapeXml(selectedOption.value)}</span>
		                 </ycommerce:testId>
		             </div>
		             <c:set var="entryStock" value="${option.selected.stock.stockLevelStatus.code}"/>
		         </c:forEach>
		     </c:if>
		 </c:forEach>
		 <ycommerce:testId code="orderDetails_productQuantity_label">
		     <label class="visible-xs visible-sm col-xs-6 text-left"><spring:theme code="order.quantity"/></label>
		     <span class="qtyValue">
		         <c:choose>
		             <c:when test="${consignmentEntry ne null }">
		                 ${fn:escapeXml(consignmentEntry.quantity)}
		             </c:when>
		             <c:otherwise>
			            
			             ${fn:escapeXml(orderEntry.quantity)}
			              
		             	<%-- <fmt:parseNumber var="intValueOfQuantityText" value="${orderEntry.quantityText}" integerOnly="true"/>
		             	<fmt:formatNumber value="${intValueOfQuantityText}" pattern="#"></fmt:formatNumber> --%>
		             </c:otherwise>
		         </c:choose>
		     </span>
		 </ycommerce:testId>
		</div>
     </div>
     <div class="col-md-2 col-xs-12">
      	<div class="visible-xs visible-sm col-xs-6 text-left">Total</div>
     	<ycommerce:testId code="orderDetails_productTotalPrice_label">
         	<div class="item__totall col-xs-6 no-padding-xs js-item-total">
             	<format:price priceData="${orderEntry.totalPrice}" displayFreeForZero="true" />
         	</div>
     	</ycommerce:testId>
     </div>
     <div class="col-md-2 col-xs-12">
     <c:choose>
                            <c:when test="${orderEntry.product.availableStatus eq false || orderEntry.product.isProductDiscontinued}">
	                         <button type="submit" class="btn btn-primary pull-left btn-block"
  	                                                                         aria-disabled="true" disabled="disabled"> <spring:theme code="basket.add.again" />
  	                            </button>
	                          </c:when>

	                          <c:otherwise>
	                                     <c:if test="${orderOnlinePermissions ne true}">
	                                           <c:set var="ATCOOId" value="orderOnlineATC" />
	                                               <cms:pageSlot position="OnlineOrder" var="feature">
					                                    <cms:component component="${feature}"/>
				                                   </cms:pageSlot>
                                         </c:if>
                                 <form:form method="post" id="addToCartForm" class="add_to_cart_form" action="${homelink}cart/add">
                                      	<c:set var="newuominventoryuomid" value=""/>
                                      	<c:set var="product" value="${orderEntry.product}"/>
                                      	<c:set var="hideList" value="${product.hideList}"/>
                                        <c:set var="hideCSP" value="${product.hideCSP}"/>
                                        <c:set var="isStockAvailable" value="false" />
                                        	  <c:if test="${product.stock.stockLevel gt 0 or (not empty product.stock.inventoryHit and product.stock.inventoryHit >= 4)}">
                                        		        <c:set var="isStockAvailable" value="true" />
                                        	 </c:if>
                                      	<c:forEach items="${product.sellableUoms}" var="sellableUom2">
                                      		<c:if test="${uomMeasure == sellableUom2.inventoryUOMDesc }" >
                                      			<c:set var="orderEntryInventoryUOMId" value="${sellableUom2.inventoryUOMID}" />
                                      		</c:if>
                                      		<c:if test="${product.hideUom eq true}">
                                      			<c:set var="hideuom2" value="true"/>
                                      			<c:set var="newuomDescription" value="${sellableUom2.inventoryUOMDesc}"/>
                                      			<c:set var="newuominventoryuomid" value="${sellableUom2.inventoryUOMID}"/>
                                      		</c:if>
                                      	</c:forEach>
                                      	<c:if test="${not empty orderEntryInventoryUOMId }">
                                      		<input type="hidden" id="inventoryUomId" name="inventoryUomId" value="${orderEntryInventoryUOMId}">
                                      	</c:if>
                                      	<c:if test="${product.singleUom eq true}">
                                      		<c:set var="newuommeasure" value="${product.singleUomMeasure}"/>
                                      		<c:set var="newuominventoryuomid" value="${sellableUom2.inventoryUOMID}"/>
                                      	</c:if>
                                        <c:if test="${product.purchasable}">
                                        	<input type="hidden" maxlength="3" size="1" id="qty" name="qty" class="qty js-qty-selector-input" value="${orderEntry.quantity}">
                                        	<input type="hidden" id="inventoryUomId" name="inventoryUomId" value="${newuominventoryuomid}">
                                        </c:if>
                                        <input type="hidden" name="productCodePost" value="${product.code}"/>
                                        <input type="hidden" name="productNamePost" value="${fn:escapeXml(product.name)}"/>
                                       <input type="hidden" name="storeId" value="${product.stock.fullfillmentStoreId}"/>
                                      	<input type="hidden" id="isSellable"name="isSellable" value="${(!product.isSellable)}"/>
                                        <c:if test="${empty showAddToCart ? true : showAddToCart}">
                                      	      <c:set var="buttonType">button</c:set>
                                      	      <c:if test="${product.variantType ne 'GenericVariantProduct' and product.price ne '' }">
                                      	      	<c:set var="buttonType">submit</c:set>
                                      	      </c:if>
                                      	       <div id="addToCartSection" class="col-md-12">
                                      	       <div class="row">
                                      	      <c:choose>
                                      	      	<c:when test="${fn:contains(buttonType, 'button')}">
                                                        <c:choose>
                                                              <c:when test="${product.variantType eq 'GenericVariantProduct' && product.variantCount != 1}">
                                                                  <div class="variantButton col-md-12 col-lg-12 col-xs-12">
                                                                      <button type="${buttonType}" id="variantButton" class="btn btn-primary btn-block js-add-to-cart" >
                                                                          <spring:theme code="product.base.select.options"/>
                                                                      </button>
                                                                  </div>
                                                              </c:when>
                                                              <c:otherwise>
                                                              <ycommerce:testId code="addToCartButton">
                                                                      <button type="${(orderOnlinePermissions eq true)?buttonType:'button'}" id="${(orderOnlinePermissions eq true)?'addToCartButton':ATCOOId}" class="btn btn-primary btn-block pull-left js-add-to-cart  js-enable-btn" disabled="disabled">
                                                                      <spring:theme code="basket.add.again" />
                                                                      </button>
                                                              </ycommerce:testId>
                                                              </c:otherwise>
                                                        </c:choose>
                    
  	                	                        </c:when>
  	                	                        <c:otherwise>
  	                	                               <ycommerce:testId code="addToCartButton">
  	                	                             <div class=" product-detail-cartbtn">
  	                	                             <div id="productSelect">
  	                	                              <c:choose>
                                                         <c:when test="${product.sellableUomsCount == 0}">
                                                         		<c:choose>
  	                                                         	   <c:when test="${(!product.isSellable)}">
  	                                                                 <button type="submit" id="notSellable" class="btn btn-primary pull-left btn-block"
  	                                                                         aria-disabled="true" disabled="disabled"> <spring:theme code="basket.add.again" />
  	                                                                 </button>
  	                                                                 <c:if test="${!isOrderingAccount}">
  	                                                                      <span class="alert_msg">${orderingAccountMsg}</span>
  	                                                                 </c:if>
  	                                                               </c:when>
  	                                                               <c:otherwise>
  	                	                                               <c:choose>
  	                	                             	                 <c:when test="${(hideCSP eq true)}">
  	                                                                                   <button type="submit" id="showAddtoCart" class="btn btn-primary btn-block buy-again-atc"
  	                                                                                           disabled="disabled" ${disableAtc}>
  	                                                                                           <spring:theme code="basket.add.again" />
  	                                                                                   </button>
  	                                                                     </c:when>
  	                                                                     <c:otherwise>
  	                                                                         <button type="${(orderOnlinePermissions eq true)?'submit':'button'}" id="${(orderOnlinePermissions eq true)?'showAddtoCart':ATCOOId}" class="btn btn-primary btn-block js-enable-btn buy-again-atc"
  	                                                                                 disabled="disabled" ${disableAtc}>
  	                                                                                  <spring:theme code="basket.add.again" />
  	                                                                         </button>
  	                                                                     </c:otherwise>
  	                                                                   </c:choose>
  	                                                               </c:otherwise>
  	                                                            </c:choose>
                            	                         </c:when>
                            	                         <c:otherwise>
  	                			                            <c:choose>
  	                			                        	    <c:when test="${(product.hideUom)}">
  	                			                        	       <c:choose>
  	                			                        	         <c:when test="${hideList ne true}">
  	                			                        	            <c:choose>
  	                			                        	                  <c:when test ="${hideCSP ne true}">
  	                			                        	                     <c:choose>
  	                			                        	                        <c:when test="${isStockAvailable}">
  	                			                        	                            <button type="${(orderOnlinePermissions eq true)?'submit':'button'}" id="${(orderOnlinePermissions eq true)?'showAddtoCart':ATCOOId}" class="btn btn-primary btn-block js-enable-btn"
  	                			                        				                   disabled="disabled" ${disableAtc}>
  	                			                        			                      <spring:theme code="basket.add.again" />
  	                			                        		                        </button>
  	                			                        	                        </c:when>
  	                			                        	                        <c:otherwise>
  	                			                        	                               <button type="submit" id="showAddtoCart" class="btn btn-primary btn-block buy-again-atc"
  	                                                                                       disabled="disabled" ${disableAtc}>
  	                                                                                       <spring:theme code="basket.add.again" />
  	                                                                                       </button>
  	                			                        	                        </c:otherwise>
  	                			                        	                    </c:choose>
  	                				                                          </c:when>
  	                				                                          <c:otherwise>
  	                				                                               <button type="submit" id="showAddtoCart" class="btn btn-primary btn-block buy-again-atc"
  	                                                                                disabled="disabled" ${disableAtc}>
  	                                                                                <spring:theme code="basket.add.again" />
  	                                                                               </button>
  	                				                                          </c:otherwise>
  	                				                                   </c:choose>
  	                				                               </c:when>
  	                				                           <c:otherwise>
  	                				                                  <c:choose>
  	                				                                    <c:when test="${hideCSP ne true}">
  	                				                                        <c:choose>
  	                				                                          <c:when test="${isStockAvailable}">
  	                				                                           <button type="${(orderOnlinePermissions eq true)?'submit':'button'}" id="${(orderOnlinePermissions eq true)?'showAddtoCart':ATCOOId}"class="btn btn-primary btn-block js-enable-btn"
  	                					   	                         	disabled="disabled" ${disableAtc}>
  	                					   	                              <spring:theme code="basket.add.again" />
  	                					                                     </button>
  	                				                                          </c:when>
  	                				                                          <c:otherwise>
  	                				                                               <button type="submit" id="showAddtoCart" class="btn btn-primary btn-block buy-again-atc"
  	                                                                               disabled="disabled" ${disableAtc}>
  	                                                                               <spring:theme code="basket.add.again" />
  	                                                                               </button>
  	                				                                          </c:otherwise>
  	                				                                          </c:choose>
  	                				                                    </c:when>
  	                				                                    <c:otherwise>
  	                				                                         <button type="submit" id="showAddtoCart" class="btn btn-primary btn-block buy-again-atc"
  	                                                                               disabled="disabled" ${disableAtc}>
  	                                                                               <spring:theme code="basket.add.again" />
  	                                                                       </button>
  	                				                                    </c:otherwise>
  	                				                                  </c:choose>
  	                			                      	        </c:otherwise>
  	                			                      	     </c:choose>
  	                				                   </c:when>
  	                				                   <c:otherwise>
  	                				                   		<c:choose>
  	                            	                			<c:when test="${(!product.isSellable)}">
  	                                                   				 <button type="submit" id="showAddtoCartUom" class="btn btn-primary pull-left btn-block" 
  	                                                            	 		 aria-disabled="true" disabled="disabled" ${disableAtc}>
  	                                                           		 		 <spring:theme code="basket.add.again" />
  	                                                    			 </button></br>
  	                                                			</c:when>
  	                               	                			<c:otherwise>
  	                                                   				<button type="${(orderOnlinePermissions eq true)?'submit':'button'}" id="${(orderOnlinePermissions eq true)?'showAddtoCartUom':ATCOOId}" class="btn btn-primary btn-block js-enable-btn" 
  	                                                          				disabled="disabled" ${disableAtc}>
  	                                                           				<spring:theme code="basket.add.again" />
  	                                                   				</button>
  	                                                			</c:otherwise>
  	                                           				</c:choose>
  	                				                   </c:otherwise>
  	                				                 </c:choose>
                                               </c:otherwise>
                                   </c:choose>
                              </div>
                            </div>
                            </ycommerce:testId>
                        </c:otherwise>
                     </c:choose>
                  </div>
              </div>
          </c:if>
                        	
    </form:form>
	   </c:otherwise>
			
	</c:choose>
     </div>
</div>
<fmt:setLocale value="${pageContext.response.locale}" scope="session"/>