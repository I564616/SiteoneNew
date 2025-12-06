<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="order" required="true" type="de.hybris.platform.commercefacades.order.data.AbstractOrderData" %>
<%@ attribute name="orderEntry" required="true" type="de.hybris.platform.commercefacades.order.data.OrderEntryData" %>
<%@ attribute name="consignmentEntry" required="false"
              type="de.hybris.platform.commercefacades.order.data.ConsignmentEntryData" %>
<%@ attribute name="itemIndex" required="true" type="java.lang.Integer" %>
<%@ attribute name="targetUrl" required="false" type="java.lang.String" %>
<%@ attribute name="fromPage" required="false" type="java.lang.String" %>
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
<spring:url value="/" var="homelink" htmlEscape="false"/>
<c:set var="varShowStock" value="${(empty showStock) ? true : showStock}" />
<c:set var="unitpriceFormattedDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.unitprice.formattedDigits\")%>" />
<c:set var="unitpriceDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.unitprice.digits\")%>" />

<c:url value="${orderEntry.product.url}" var="productUrl"/>
<c:set var="entryStock" value="${orderEntry.product.stock.stockLevelStatus.code}"/>

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
<c:choose>
	<c:when test="${cmsPage.uid eq 'order-approval-details'}">
		<c:set var="imgthubwidth" value="col-md-3" />
		<c:set var="productlink" value="col-md-9 marginTop20" />
		<c:set var="imgsection" value="col-md-6"/>
		<c:set var="mobilePrice" value="hidden"/>
		<c:set var="mobileQTY" value="pull-left marginrgt5 hidden-md hidden-lg"/>
		<c:set var="halfWidth" value="col-xs-12"/>
	</c:when>
	<c:otherwise>
		<c:set var="imgthubwidth" value="col-md-4" />
		<c:set var="productlink" value="col-md-8" />
		<c:set var="imgsection" value="col-md-4"/>
		<c:set var="mobilePrice" value="visible-xs visible-sm col-xs-6 text-left bold-text"/>
		<c:set var="mobileQTY" value="visible-xs visible-sm col-xs-6 text-left"/>
		<c:set var="halfWidth" value="col-xs-12"/>
	</c:otherwise>
</c:choose>
<c:set var="doesNursery" value="false"/>
<c:if test="${orderEntry.product.productType eq 'Nursery'}" >
	<c:set var="doesNursery" value="true"/>
</c:if>
<div class="col-xs-12 item__list--header print-p-b-15 p-t-0-xs-imp p-t-0-sm-imp m-b-20-xs m-b-20-sm print-no-break">
	<div class="js-order-details-qty-error js-cart-qty-error marginBottom20 text-red ${showErrorMsg}">
		<img class="icon-red-exclamation cart-qty-alert"
			src="${themeResourcePath}/images/Exclamation-point.svg" alt""/>
		<spring:theme code="text.product.qty.alert"
			arguments="${orderEntry.product.stock.stockLevel}" />
	</div>
	<div class="${imgsection} col-xs-12 item__image-wrapper p-b-0-xs-imp p-b-0-sm-imp">
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
	        <div class="${imgthubwidth} col-xs-6 hidden-xs hidden-sm">
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
            <div class="${productlink} block col-xs-12 no-padding-xs no-padding-sm print-hidden">
	            <%-- product name, code, promotions --%>
				<c:choose>
					<c:when test="${cmsPage.uid eq 'order-approval-details'}">
						<div class="f-s-12-xs-px print-f-s-13">
					</c:when>
					<c:otherwise>
						<div class="row f-s-12-xs-px print-f-s-13">
					</c:otherwise>
				</c:choose>
				<div class="col-xs-4 p-l-0 bold visible-xs visible-sm text-uppercase">ITEM #:</div>
	            <div class="col-xs-8 col-md-12 text-default-xs text-default-sm print-hidden">${fn:escapeXml(orderEntry.product.itemNumber)}</div>
	            <div class="col-xs-3 text-default-xs text-default-sm hidden print-visible">${fn:escapeXml(orderEntry.product.itemNumber)}</div>

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
						<div class="col-xs-4 p-l-0 bold visible-xs visible-sm text-uppercase">DESC:</div>
	                    <div class="col-xs-8 col-md-12 text-default-xs text-default-sm item__name-wrapper p-b-0-xs-imp p-b-0-sm-imp print-hidden">
	                    	<a href="${productUrl}" class="hide-for-mobile print-visible print-word-br"><span class="item__name">${productShortName}</span></a>
	                    	<span class="visible-xs visible-sm">${mobileProductShortName}</span>
	                    </div>
						<div class="col-xs-4 text-default-xs text-default-sm item__name-wrapper p-b-0-xs-imp p-b-0-sm-imp hidden print-visible">
	                    	<a href="${productUrl}" class="hide-for-mobile print-visible print-word-br"><span class="item__name">${productShortName}</span></a>
	                    	<span class="visible-xs visible-sm">${mobileProductShortName}</span>
	                    </div>
	                    </c:otherwise>
	                    </c:choose>
	                </ycommerce:testId>
	                <c:if test="${orderEntry.product.baseProduct != null}">
						<c:forEach items="${orderEntry.product.categories}" var="option">
							${option.parentCategoryName}:${option.name}<br/>
						</c:forEach>
					</c:if>
	            	
	            	<%-- availability --%>
	            	<c:if test="${cmsPage.uid ne 'order-approval-details'}">
	            	<div class="cl hidden-xs hidden-sm"><br/></div>
					<div class="col-xs-4 p-l-0 bold visible-xs visible-sm text-uppercase">Availability</div>
	            	<div class="col-md-12 col-xs-8 p-b-0-sm-imp p-b-0-xs-imp text-default-sm text-default-xs flex-center print-hidden">
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
																			<span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
                                                                        	<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
                                                                            <span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"><common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"><common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <c:if test="${orderEntry.product.inStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"><common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"><common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                            	<c:set var="enableCheckout" value="false" />
                                                                        <c:if test="${orderEntry.product.inStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"><common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                           <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"> <common:crossMarkIcon iconColor="#5a5b5d"/></span>
																		   <span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/>
																			</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span id="cartRegulatoryItemInSufficientStockMsg_${loop.index}"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span id="cartRegulatoryItemInSufficientStockMsg_${loop.index}"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span id="cartRegulatoryItemInSufficientStockMsg_${loop.index}"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                     </c:when>
                                                                    <c:otherwise>
                                                                    <c:choose>
                                                                     <c:when test="${hideCSP eq true}">
                                                                     <p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
                                                                          </c:when>
                                                                    <c:otherwise>
                                                                      <c:if test="${orderEntry.product.inStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span id="cartRegulatoryItemInStockMsg_${loop.index}"></span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span id="cartRegulatoryItemInStockMsg_${loop.index}"><span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span id="cartRegulatoryItemInStockMsg_${loop.index}"><span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span></span>
                                                                        </c:if>
                                                                         </c:otherwise></c:choose>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                            
                                                            	<c:set var="enableCheckout" value="false" />
                                                                 <c:if test="${orderEntry.product.inStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
	                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																				<c:choose>				
																					<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																						<common:checkmarkIcon iconColor="#ef8700"/>
																					</c:when>
																					<c:otherwise>
																						<common:checkmarkIcon iconColor="#78a22f"/>
																					</c:otherwise>
																				</c:choose>
																				</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
					<div class="p-b-0-sm-imp p-b-0-xs-imp text-default-sm text-default-xs flex-center hidden print-visible">
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
																			<span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
                                                                        	<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
                                                                            <span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"><common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"><common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <c:if test="${orderEntry.product.inStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"><common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"><common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                            	<c:set var="enableCheckout" value="false" />
                                                                        <c:if test="${orderEntry.product.inStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"><common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                           <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"> <common:crossMarkIcon iconColor="#5a5b5d"/></span>
																		   <span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/>
																			</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span id="cartRegulatoryItemInSufficientStockMsg_${loop.index}"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span id="cartRegulatoryItemInSufficientStockMsg_${loop.index}"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span id="cartRegulatoryItemInSufficientStockMsg_${loop.index}"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                     </c:when>
                                                                    <c:otherwise>
                                                                    <c:choose>
                                                                     <c:when test="${hideCSP eq true}">
                                                                     <p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
                                                                          </c:when>
                                                                    <c:otherwise>
                                                                      <c:if test="${orderEntry.product.inStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span id="cartRegulatoryItemInStockMsg_${loop.index}"></span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span id="cartRegulatoryItemInStockMsg_${loop.index}"><span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span id="cartRegulatoryItemInStockMsg_${loop.index}"><span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span></span>
                                                                        </c:if>
                                                                         </c:otherwise></c:choose>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                            
                                                            	<c:set var="enableCheckout" value="false" />
                                                                 <c:if test="${orderEntry.product.inStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
	                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																				<c:choose>				
																					<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																						<common:checkmarkIcon iconColor="#ef8700"/>
																					</c:when>
																					<c:otherwise>
																						<common:checkmarkIcon iconColor="#78a22f"/>
																					</c:otherwise>
																				</c:choose>
																				</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
	                </c:if>
	            </div>
            </div>
			<div class="block col-xs-12 no-padding-xs no-padding-sm hidden print-visible">
	            <%-- product name, code, promotions --%>
	            <div class="row f-s-12-xs-px print-f-s-13">
				<div class="col-xs-4 p-l-0 bold visible-xs visible-sm text-uppercase">ITEM #:</div>
	            <div class="col-xs-8 col-md-12 text-default-xs text-default-sm print-hidden">${fn:escapeXml(orderEntry.product.itemNumber)}</div>
	            <div class="col-xs-3 text-default-xs text-default-sm hidden print-visible bold" style="word-break: break-word;">${fn:escapeXml(orderEntry.product.itemNumber)}</div>

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
						<div class="col-xs-4 p-l-0 bold visible-xs visible-sm text-uppercase">DESC:</div>
	                    <div class="col-xs-8 col-md-12 text-default-xs text-default-sm item__name-wrapper p-b-0-xs-imp p-b-0-sm-imp print-hidden">
	                    	<a href="${productUrl}" class="hide-for-mobile print-visible print-word-br"><span class="item__name">${productShortName}</span></a>
	                    	<span class="visible-xs visible-sm">${mobileProductShortName}</span>
	                    </div>
						<div class="col-xs-4 text-default-xs text-default-sm item__name-wrapper p-b-0-xs-imp p-b-0-sm-imp hidden print-visible">
	                    	<a href="${productUrl}" class="hide-for-mobile print-visible print-word-br"><span class="item__name">${productShortName}</span></a>
	                    	<span class="visible-xs visible-sm">${mobileProductShortName}</span>
	                    </div>
	                    </c:otherwise>
	                    </c:choose>
	                </ycommerce:testId>
	                <c:if test="${orderEntry.product.baseProduct != null}">
						<c:forEach items="${orderEntry.product.categories}" var="option">
							${option.parentCategoryName}:${option.name}<br/>
						</c:forEach>
					</c:if>
	            	
	            	<%-- availability --%>
	            	<c:if test="${cmsPage.uid ne 'order-approval-details'}">
	            	<div class="cl hidden-xs hidden-sm"><br/></div>
					<div class="col-xs-4 p-l-0 bold visible-xs visible-sm text-uppercase">Availability</div>
	            	<div class="col-md-12 col-xs-8 p-b-0-sm-imp p-b-0-xs-imp text-default-sm text-default-xs flex-center print-hidden">
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
																			<span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
                                                                        	<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
                                                                            <span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"><common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"><common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <c:if test="${orderEntry.product.inStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"><common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"><common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                            	<c:set var="enableCheckout" value="false" />
                                                                        <c:if test="${orderEntry.product.inStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"><common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                           <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"> <common:crossMarkIcon iconColor="#5a5b5d"/></span>
																		   <span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/>
																			</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span id="cartRegulatoryItemInSufficientStockMsg_${loop.index}"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span id="cartRegulatoryItemInSufficientStockMsg_${loop.index}"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span id="cartRegulatoryItemInSufficientStockMsg_${loop.index}"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                     </c:when>
                                                                    <c:otherwise>
                                                                    <c:choose>
                                                                     <c:when test="${hideCSP eq true}">
                                                                     <p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
                                                                          </c:when>
                                                                    <c:otherwise>
                                                                      <c:if test="${orderEntry.product.inStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span id="cartRegulatoryItemInStockMsg_${loop.index}"></span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span id="cartRegulatoryItemInStockMsg_${loop.index}"><span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span id="cartRegulatoryItemInStockMsg_${loop.index}"><span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span></span>
                                                                        </c:if>
                                                                         </c:otherwise></c:choose>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                            
                                                            	<c:set var="enableCheckout" value="false" />
                                                                 <c:if test="${orderEntry.product.inStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
	                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																				<c:choose>				
																					<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																						<common:checkmarkIcon iconColor="#ef8700"/>
																					</c:when>
																					<c:otherwise>
																						<common:checkmarkIcon iconColor="#78a22f"/>
																					</c:otherwise>
																				</c:choose>
																				</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
					<div class="p-b-0-sm-imp p-b-0-xs-imp text-default-sm text-default-xs flex-center hidden print-visible">
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
																			<span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
                                                                        	<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
                                                                            <span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"><common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"><common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <c:if test="${orderEntry.product.inStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"><common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"><common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                            	<c:set var="enableCheckout" value="false" />
                                                                        <c:if test="${orderEntry.product.inStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"><common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                           <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10"> <common:crossMarkIcon iconColor="#5a5b5d"/></span>
																		   <span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/>
																			</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span id="cartRegulatoryItemInSufficientStockMsg_${loop.index}"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span id="cartRegulatoryItemInSufficientStockMsg_${loop.index}"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span id="cartRegulatoryItemInSufficientStockMsg_${loop.index}"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                     </c:when>
                                                                    <c:otherwise>
                                                                    <c:choose>
                                                                     <c:when test="${hideCSP eq true}">
                                                                     <p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
                                                                          </c:when>
                                                                    <c:otherwise>
                                                                      <c:if test="${orderEntry.product.inStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span id="cartRegulatoryItemInStockMsg_${loop.index}"></span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span id="cartRegulatoryItemInStockMsg_${loop.index}"><span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span id="cartRegulatoryItemInStockMsg_${loop.index}"><span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span></span>
                                                                        </c:if>
                                                                         </c:otherwise></c:choose>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                            
                                                            	<c:set var="enableCheckout" value="false" />
                                                                 <c:if test="${orderEntry.product.inStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
	                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																				<c:choose>				
																					<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																						<common:checkmarkIcon iconColor="#ef8700"/>
																					</c:when>
																					<c:otherwise>
																						<common:checkmarkIcon iconColor="#78a22f"/>
																					</c:otherwise>
																				</c:choose>
																				</span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<c:choose>				
																				<c:when test="${orderEntry.product.isStockInNearbyBranch}">
																					<common:checkmarkIcon iconColor="#ef8700"/>
																				</c:when>
																				<c:otherwise>
																					<common:checkmarkIcon iconColor="#78a22f"/>
																				</c:otherwise>
																			</c:choose>
																			</span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:exclamatoryIcon iconColor="#ed8606"/></span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
                                                                        </c:if>
                                                                        <c:if test="${orderEntry.product.outOfStockImage}">
                                                                            <span class="hidden-xs hidden-sm icon-hide-mobile m-r-10">
																			<common:crossMarkIcon iconColor="#5a5b5d"/></span>
																			<span class="stock"><spring:theme text="${orderEntry.product.storeStockAvailabilityMsg}" arguments="${contactNo}" htmlEscape="false" /></span>
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
	                </c:if>
	            </div>
            </div>
		</div>
		</div>
	</div>
	
    <div class="col-md-2 col-xs-12 item__price-wrapper ${fromPage eq 'order' ? 'p-t-30 no-padding-xs p-b-0-xs-imp p-t-0-xs-imp no-padding-sm p-b-0-sm-imp p-t-0-sm-imp' : ''}">
    	<%-- price --%>
 		<div class="f-s-12-xs-px f-s-12-sm-px item__price print-m-t-15">
			<div class="col-xs-4 p-l-0 bold visible-xs visible-sm text-uppercase ${cmsPage.uid eq 'order-approval-details' ? 'text-dark-gray':''}">Unit <spring:theme code="basket.page.itemPrice"/></div>
           	<div class="col-xs-8 col-md-12 text-default-xs text-default-sm print-f-s-13">
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
												     <del> <format:price priceData="${orderEntry.basePrice}" displayFreeForZero="true" unitPrice="true"/> / ${uomMeasure} </del>

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
    <div class="col-md-2 print-m-t-15 ${halfWidth} item__quantity-wrapper ${fromPage eq 'order' ? 'p-t-30 no-padding-xs p-b-0-xs-imp p-t-0-xs-imp no-padding-sm p-b-0-sm-imp p-t-0-sm-imp f-s-12-xs-px f-s-12-sm-px' : ''}">
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
		     <div class="col-xs-4 p-l-0 bold visible-xs visible-sm text-uppercase ${cmsPage.uid eq 'order-approval-details' ? 'text-dark-gray':''}">
		      <c:choose>
		     <c:when test="${cmsPage.uid eq 'order-approval-details'}">
		      <spring:theme code="text.account.savedCart.qty"/>:
		     </c:when>
		      <c:otherwise>QTY</c:otherwise>
		     </c:choose>
		    </div>
		     
		     <span class="col-xs-8 col-md-12 text-default-xs text-default-sm qtyValue print-f-s-13">
		         <c:choose>
		             <c:when test="${consignmentEntry ne null }">
		                 ${fn:escapeXml(consignmentEntry.quantity)}
		             </c:when>
		             <c:otherwise>
			            <c:if test="${cmsPage.uid eq 'order'}">
			             ${fn:escapeXml(orderEntry.quantity)}
			             </c:if>
			             <c:if test="${cmsPage.uid eq 'orderConfirmationPage' or cmsPage.uid eq 'order-approval-details'}">
			             	${fn:escapeXml(orderEntry.quantity)}
			             </c:if>
		             </c:otherwise>
		         </c:choose>
		     </span>
		 </ycommerce:testId>
		</div>
     </div>
     <div class="col-md-2 print-m-t-15 ${halfWidth} item__total-wrapper ${fromPage eq 'order' ? 'p-t-30 no-padding-xs p-b-0-xs-imp p-t-0-xs-imp no-padding-sm p-b-0-sm-imp p-t-0-sm-imp f-s-12-xs-px f-s-12-sm-px' : ''}">
      	<div class="col-xs-4 bold visible-xs visible-sm text-uppercase ${cmsPage.uid eq 'order-approval-details' ? 'text-dark-gray':'p-l-0'}">Total</div>
     	<ycommerce:testId code="orderDetails_productTotalPrice_label">
         	<div class="col-xs-8 col-md-12 text-default-xs text-default-sm js-item-total bold print-f-s-13 ${cmsPage.uid eq 'order-approval-details' ? 'p-l-20-xs':'p-l-0'}">
				<fmt:setLocale value="en_US" scope="session"/>
				$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${orderEntry.totalPrice.value}" minFractionDigits="2"  maxFractionDigits="2" />
         	</div>
     	</ycommerce:testId>
     </div>
      <c:if test="${cmsPage.uid ne 'order-approval-details'}">
     <div class="col-md-2 col-xs-7 col-xs-offset-4 col-md-offset-0 orderSummary-buyagain-print ${fromPage eq 'order' ? 'p-t-10' : ''}">
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
                                      	<c:set var="newuominventoryuomid" value="" />
                                        <c:set var="product" value="${orderEntry.product}" />
                                        <c:set var="hideList" value="${false}" />
                                        <c:set var="hideCSP" value="${false}" />
                                        <c:set var="stockLevel" value="${false}" />
                                        <c:set var="inStockImage" value="${false}" />
                                        <c:set var="isStockInNearbyBranch" value="${false}" />
                                        <c:set var="notInStockImage" value="${false}" />
                                        <c:set var="outOfStockImage" value="${false}" />
                                        <c:set var="isStockAvailable" value="${false}" />

										<c:set var="doesEligibleforBackorder" value="${false}" />
										<c:set var="doesForceinStock" value="${false}" />
										<c:set var="doesTransferrable" value="${false}" />
										<c:set var="doesInventoryCheck" value="${false}" />
										<c:set var="doesHomeStoreAvail" value="${false}" />
										<c:set var="toEnableBuyagain" value="${false}" />
										<c:set var="doesNurseryBuyagian" value="${false}" />

                                        <c:if test="${not empty product.hideList && product.hideList ne null}">
                                            <c:set var="hideList" value="${product.hideList}" />
                                        </c:if>
                                        <c:if test="${not empty product.hideCSP && product.hideCSP ne null}">
                                            <c:set var="hideCSP" value="${product.hideCSP}" />
                                        </c:if>
                                        <c:if test="${not empty product.stock.stockLevel && product.stock.stockLevel ne null}">
                                            <c:set var="stockLevel" value="${product.stock.stockLevel}" />
                                        </c:if>
                                        <c:if test="${not empty product.stock.isHomeStoreStockAvailable && product.stock.isHomeStoreStockAvailable ne null}">
                                            <c:set var="doesHomeStoreAvail" value="${product.stock.isHomeStoreStockAvailable}" />
                                        </c:if>
                                        <c:if test="${not empty product.inStockImage && product.inStockImage ne null}">
                                            <c:set var="inStockImage" value="${product.inStockImage}" />
                                        </c:if>
                                        <c:if test="${not empty product.isStockInNearbyBranch && product.isStockInNearbyBranch ne null}">
                                            <c:set var="isStockInNearbyBranch" value="${product.isStockInNearbyBranch}" />
                                        </c:if>
                                        <c:if test="${not empty product.notInStockImage && product.notInStockImage ne null}">
                                            <c:set var="notInStockImage" value="${product.notInStockImage}" />
                                        </c:if>
                                        <c:if test="${not empty product.outOfStockImage && product.outOfStockImage ne null}">
                                            <c:set var="outOfStockImage" value="${product.outOfStockImage}" />
                                        </c:if>
                                        	  <c:if test="${product.stock.stockLevel gt 0 or (not empty product.stock.inventoryHit and product.stock.inventoryHit >= 4)}">
                                        		        <c:set var="isStockAvailable" value="true" />
                                        	 </c:if>

											 
										<c:if test="${not empty product.isEligibleForBackorder &&  product.isEligibleForBackorder ne null}">
                                            <c:set var="doesEligibleforBackorder" value="${product.isEligibleForBackorder}" />
                                        </c:if>
										<c:if test="${not empty product.isForceInStock &&  product.isForceInStock ne null}">
                                            <c:set var="doesForceinStock" value="${product.isForceInStock}" />
                                        </c:if>
										<c:if test="${not empty product.isTransferrable &&  product.isTransferrable ne null}">
                                            <c:set var="doesTransferrable" value="${product.isTransferrable}" />
                                        </c:if>
										<c:if test="${not empty product.inventoryCheck &&  product.inventoryCheck ne null}">
                                            <c:set var="doesInventoryCheck" value="${product.inventoryCheck}" />
                                        </c:if>
										<c:if test="${doesForceinStock||(doesEligibleforBackorder && !doesInventoryCheck) || (isStockInNearbyBranch && doesTransferrable)}">
                                        	<c:set var="toEnableBuyagain" value="true" />
                                        </c:if>
										<c:if test="${doesNursery &&  toEnableBuyagain}">
                                            <c:set var="doesNurseryBuyagian" value="true" />
                                        </c:if>
                                        <input type="hidden" id="hideList${product.code}" name="hideList" value="${hideList}">
                                        <input type="hidden" id="hideCSP${product.code}" name="hideCSP" value="${hideCSP}">
                                        <input type="hidden" id="stockLevel${product.code}" name="stockLevel" value="${stockLevel}">
                                        <input type="hidden" id="inStockImage${product.code}" name="inStockImage" value="${inStockImage}">
                                        <input type="hidden" id="isStockInNearbyBranch${product.code}" name="isStockInNearbyBranch" value="${isStockInNearbyBranch}">
                                        <input type="hidden" id="notInStockImage${product.code}" name="notInStockImage" value="${notInStockImage}">
                                        <input type="hidden" id="outOfStockImage${product.code}" name="outOfStockImage" value="${outOfStockImage}">
                                        <input type="hidden" id="isStockAvailable${product.code}" name="isStockAvailable" value="${isStockAvailable}">
										
										<input type="hidden" id="doesHomeStoreAvail${product.code}" value="${doesHomeStoreAvail}">
										<input type="hidden" class="doesForceinStock" value="${doesForceinStock}">
										<input type="hidden" class="doesEligibleforBackorder" value="${doesEligibleforBackorder}">
										<input type="hidden" class="doesTransferrable" value="${doesTransferrable}">
										<input type="hidden" class="doesInventoryCheck" value="${doesInventoryCheck}">
										<input type="hidden" id="toEnableBuyagain${product.code}" value="${toEnableBuyagain}">
										<input type="hidden" class="doesNurseryBuyagian${product.code}" value="${doesNurseryBuyagian}">

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
										<c:choose>
											<c:when test="${not empty orderEntryInventoryUOMId}">
												<input type="hidden" id="inventoryUomId" name="inventoryUomId" value="${orderEntryInventoryUOMId}">
											</c:when>
											<c:otherwise>
												<c:if test="${product.purchasable}">
													<input type="hidden" id="inventoryUomId" name="inventoryUomId" value="${newuominventoryuomid}">
												</c:if>
											</c:otherwise>
										</c:choose>
                                      	<c:if test="${product.singleUom eq true}">
                                      		<c:set var="newuommeasure" value="${product.singleUomMeasure}"/>
                                      		<c:set var="newuominventoryuomid" value="${sellableUom2.inventoryUOMID}"/>
                                      	</c:if>
                                        <c:if test="${product.purchasable}">
                                        	<input type="hidden" maxlength="3" size="1" id="qty" name="qty" class="qty js-qty-selector-input" value="${orderEntry.quantity}">
                                        </c:if>
                                        <input type="hidden" class="product-code" name="productCodePost" value="${product.code}"/>
                                        <input type="hidden" class="produc-name" name="productNamePost" value="${fn:escapeXml(product.name)}"/>
                                       <input type="hidden" name="storeId" value="${product.stock.fullfillmentStoreId}"/>
                                      	<input type="hidden" id="isSellable"name="isSellable" value="${(!product.isSellable)}"/>
                                        <c:if test="${empty showAddToCart ? true : showAddToCart}">
                                      	      <c:set var="buttonType">button</c:set>
                                      	      <c:if test="${product.variantType ne 'GenericVariantProduct' and product.price ne '' }">
                                      	      	<c:set var="buttonType">submit</c:set>
                                      	      </c:if>
                                      	       <div id="addToCartSection" class="col-md-12 print-hidden">
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
     </c:if>
</div>
<fmt:setLocale value="${pageContext.response.locale}" scope="session"/>