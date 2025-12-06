<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="order" required="true" type="de.hybris.platform.commercefacades.order.data.AbstractOrderData" %>
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
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%-- setting locale to 'en_US' to format price with dot for decimal values and comma as grouping separator  
     reset to current locale in the end of page  --%>
<fmt:setLocale value="en_US" scope="session"/>
<c:set var="unitpriceFormattedDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.unitprice.formattedDigits\")%>" />
<c:set var="unitpriceDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.unitprice.digits\")%>" />
<c:choose>
	<c:when test="${empty cookie['gls'] && sessionStore.storeId eq '172'}">
		<c:set var="contactNo" value="<%=de.hybris.platform.util.Config.getParameter(\"siteOne.Customer.Service.Number\")%>"/>		
	</c:when>
	<c:otherwise>
		<c:set var="contactNo" value="${sessionStore.address.phone}"/>
	</c:otherwise>
</c:choose>
<c:url value="${orderEntry.product.url}" var="productUrl"/>
<c:set var="entryStock" value="${orderEntry.product.stock.stockLevelStatus.code}"/>
<c:set var="isMyStoreProduct" value="false" />
    	<c:set var="hideList" value="${orderEntry.product.hideList}"/>
        <c:set var="hideCSP" value="${orderEntry.product.hideCSP}"/>
        <c:set var="inventoryFlag" value="${orderEntry.product.inventoryFlag}"/>
        <c:forEach items="${orderEntry.product.stores}" var="store">
        		<c:if test="${store eq sessionStore.storeId}">
            		<c:set var="isMyStoreProduct" value="true" />
        		</c:if>
    		</c:forEach>
            <c:if test="${orderEntry.product.singleUom eq true}">
                <c:set var="singleUom" value="true"/>
                <c:set var="uomDescription" value="${orderEntry.product.singleUomDescription}"/>
                <c:set var="uomMeasure" value="${orderEntry.product.singleUomMeasure}"/>
            </c:if>
    		<c:if test="${not empty orderEntry.product.sellableUoms}">
			   <c:forEach items="${orderEntry.product.sellableUoms}" var="sellableUom">
					 <c:set var="uomDescription" value="${sellableUom.inventoryUOMDesc}"/>
					 <c:set var="inventoryUOMIDParam" value="${sellableUom.inventoryUOMID}"/>
			   </c:forEach>
	        </c:if>
	         <c:set var="uomMeasure" value="${orderEntry.uomMeasure}"/>
    		<c:set var="isPriceAvailable" value="false" />
   			<c:if test="${orderEntry.basePrice.value gt 0.0}">
       			<c:set var="isPriceAvailable" value="true" />
   			</c:if>
    		<c:set var="isStockAvailable" value="false" />
		    <c:if test="${orderEntry.product.stock.stockLevel gt 0 or (not empty orderEntry.product.stock.inventoryHit and orderEntry.product.stock.inventoryHit > 4)}">
		        <c:set var="isStockAvailable" value="true" />
		    </c:if>
		    <c:set var="quantityBoxDisable" value="false" />
			<input type="hidden" id="regulated${loop.index}" value="${orderEntry.product.isRegulateditem}"/>
		    <c:if test="${orderEntry.product.isRegulateditem}">
		        <c:set var="isProductSellable" value="false" />
		        <c:forEach items="${orderEntry.product.regulatoryStates}" var="regulatoryStates">
		            <c:if test="${regulatoryStates eq sessionStore.address.region.isocodeShort}">
		                <c:set var="isProductSellable" value="true" />
		            </c:if>
		        </c:forEach>
		    </c:if>
			<input type="hidden" id="variantisForceinstock" value="${orderEntry.product.isForceInStock}"/>
			<c:choose>
				<c:when	test="${orderEntry.product.productType eq 'Nursery' and (orderEntry.product.inStockImage) and (orderEntry.quantity gt orderEntry.product.stock.stockLevel)}">
					<c:set var="showErrorMsg" value="hide" />
					<c:set var="disableAtc" value="" />
					
				</c:when>
				<c:otherwise>
					<c:set var="showErrorMsg" value="hide" />
					<c:set var="disableAtc" value="" />
				</c:otherwise>
			</c:choose>
<c:set var="uomMeasure" value="${orderEntry.uomMeasure}"/>

		                       
		                      <div class="col-xs-12 orderhistoryDetails-singleOrder sub-section singleorder-product-section">
		                      
		                      <div class="js-my-order-qty-error js-cart-qty-error marginBottom20 text-red ${showErrorMsg}" >
        							<img class="icon-red-exclamation cart-qty-alert" src="${themeResourcePath}/images/Exclamation-point.svg" alt""/><spring:theme code="text.product.qty.alert" arguments="${orderEntry.product.stock.stockLevel}" />
        					  </div>
        					  
		                      <div class="sub-section-part">
		                        <div class="col-xs-12 col-md-4 col-sm-12">
		                            <div class="hidden-sm hidden-md hidden-lg hidden-xs col-xs-12  data-title"><spring:theme code="basket.page.itemInfo"/></div>
		                            <div class="col-md-12 col-sm-12  col-xs-12 padding-LeftZero data-data data-margin">
		                            <div class="col-md-4 col-sm-4 col-xs-4 padding-LeftZero">
										<c:choose>
           			                             <c:when test="${orderEntry.product.availableStatus eq false}">
               	 			                         <product:productPrimaryImage product="${orderEntry.product}" format="product"/>
        			                             </c:when>
        			                            <c:otherwise>
		           			                           <a class="list-img-sec" href="${productUrl}" title="${orderEntry.product.name}"><product:productPrimaryImage product="${orderEntry.product}" format="product"/></a>
		        	                            </c:otherwise>
		                                </c:choose>
									</div>
									<c:set var="uomMeasure" value="${orderEntry.uomMeasure}"/>
									<div class="col-md-8  col-sm-6 col-xs-6 print-row-auto listproduct-info">
											
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
	                                             		<div class="data-title" ><b>${productShortName}</b></div>
	                                             </c:when>
	                                             <c:otherwise>
	                                             	<a class="green-title" href="${productUrl}" class="hide-for-mobile"><b>${productShortName}</b></a>
	                                             
	                                             </c:otherwise>
	                                             </c:choose>
	                                         </ycommerce:testId>
	                                          <c:if test="${orderEntry.product.baseProduct != null}">
						                         <c:forEach items="${orderEntry.product.categories}" var="option">
							                                ${option.parentCategoryName}:${option.name}<br/>
						                         </c:forEach>
					                          </c:if>
					                          	<div class="cl"></div>
											         <div  class="margin20">${fn:escapeXml(orderEntry.product.itemNumber)}</div>
									</div>
		                            </div>
		                        </div>
		                        
		                        <div class="col-xs-12 col-md-2 col-sm-12 orderPage-stock">
		                           
		                            <div class="col-md-12 col-sm-12  col-xs-12 orderPage-stock padding-LeftZero message-center">
		                              
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
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
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
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
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
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
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
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
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
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
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
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
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
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
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
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
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
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
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
                                                                        <c:if test="${orderEntry.product.notInStockImage or orderEntry.product.isEligibleForBackorder}">
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
		                        </div>
		                        
		                        
		                        <div class="col-xs-12 col-md-2 col-sm-12 border-top">
		                            <div class="hidden-md hidden-lg col-xs-6  data-title ">
		                            	 <spring:theme code="basket.page.price"/>
		                            </div>
		                            <div class="col-md-12 col-sm-6  col-xs-6 padding-LeftZero data-data data-margin">
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
												 	<!-- 1 -->
												    <format:price priceData="${orderEntry.basePrice}" displayFreeForZero="true" unitPrice="true"/> / ${uomMeasure}
												 </c:when>
												<c:otherwise>
													<!-- 2 -->
												   <format:price priceData="${orderEntry.basePrice}" displayFreeForZero="true" unitPrice="true"/>
												</c:otherwise>
												</c:choose>
											 </c:when>
											<c:otherwise>
											  <c:choose>
												 <c:when test="${not empty uomMeasure}">
												 	<!-- 3 -->
												     <del> <format:price priceData="${orderEntry.basePrice}" displayFreeForZero="true" unitPrice="true"/> / ${uomMeasure}</del>

											         <div class="hidden-md cl hidden-lg"></div>
                               <p class="discount-price">$<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${totalPrice}"/> / ${uomMeasure}</p>
												 </c:when>
												<c:otherwise>
													<!-- 4 -->
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
							 	<!-- 5 -->
								      <del><format:price priceData="${orderEntry.actualItemCost}" displayFreeForZero="true" unitPrice="true"/> / ${uomMeasure}</del>
								      <div class="hidden-md cl hidden-lg"></div>
							          <p class="discount-price"><format:price priceData="${orderEntry.basePrice}" displayFreeForZero="true" unitPrice="true"/> / ${uomMeasure}</p>
							 </c:when>
							 <c:otherwise>
							 	<!-- 6 -->
					              <del><format:price priceData="${orderEntry.actualItemCost}" displayFreeForZero="true" unitPrice="true"/></del> 
					              <div class="hidden-md cl hidden-lg"></div>
								  <p class="discount-price"><format:price priceData="${orderEntry.basePrice}" displayFreeForZero="true" unitPrice="true"/></p>
							 </c:otherwise>
					     </c:choose>
		            
		             </c:when>
		             <c:otherwise>
		                <c:choose>
						  <c:when test="${not empty uomMeasure}">
						  	<!-- 7 -->
						     <format:price priceData="${orderEntry.basePrice}" displayFreeForZero="false" unitPrice="true"/> / ${uomMeasure}
						  </c:when>
						 <c:otherwise>
						 	<!-- 8 -->
						    <format:price priceData="${orderEntry.basePrice}" displayFreeForZero="false" unitPrice="true"/>
						 </c:otherwise>
					   </c:choose>
		             </c:otherwise>
		             </c:choose>
                    </c:otherwise>
			</c:choose>
 		                            </div>
		                        </div>
		                        <div class="col-xs-12 col-md-1 col-sm-12 border-top padding-LeftZero">    
		                            <div class="hidden-md hidden-lg col-xs-6  data-title">
		                            	<spring:theme code="basket.page.quantity"/>
		                            </div>
		                            <div class="col-md-12 col-sm-6  col-xs-6 data-data data-margin qty-buyagain">
		                              ${fn:escapeXml(orderEntry.quantity)}
		                            </div>
		                        </div>
		                        
		                        
		                        <div class="col-xs-12 col-md-1 col-sm-12 border-top">   
		                            <div class="hidden-md hidden-lg col-xs-6 data-title">
		                            	<spring:theme code="basket.page.total"/>
		                           	</div>
		                            <div class="col-md-12 col-sm-6  col-xs-6 padding-LeftZero data-data data-margin">
									  $<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${orderEntry.totalPrice.value}" minFractionDigits="2"  maxFractionDigits="2" />
		                            </div>
		                        </div>
		                        
		                        
		                        <div class="col-xs-12 col-md-2 col-sm-12 padding-rightZero border-top">
		                            <div class="hidden-sm hidden-md hidden-lg hidden-xs col-xs-5 data-title padding-LeftZero black-title bold-text">
		                            	<spring:theme code="text.buy.it.again"/>
		                            </div>
		                            <div class="col-md-12 col-sm-12  col-xs-12 data-data padding0">
		                            <spring:theme code="text.addToCart" var="addToCartText"/>
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
                                         <c:url value="/cart/add" var="addToCartUrl"/>
                                 <form:form method="post" id="addToCartForm" class="add_to_cart_form padding-LeftZero" action="${addToCartUrl}">
                                      	<c:set var="newuominventoryuomid" value=""/>
                                      	<c:set var="product" value="${orderEntry.product}"/>
                                      	<c:set var="hideList" value="${product.hideList}"/>
                                        <c:set var="hideCSP" value="${product.hideCSP}"/>
                                        <c:set var="isStockAvailable" value="false" />
                                        	  <c:if test="${product.stock.stockLevel gt 0 or (not empty product.stock.inventoryHit and product.stock.inventoryHit > 4)}">
                                        		        <c:set var="isStockAvailable" value="true" />
                                        	 </c:if>
                             	 		<c:forEach items="${product.sellableUoms}" var="sellableUom2">
                                      		<c:if test="${uomMeasure == sellableUom2.inventoryUOMDesc}" >
                                      			<c:set var="orderEntryInventoryUOMId" value="${sellableUom2.inventoryUOMID}" />
                                      		</c:if>
                                      		<c:if test="${product.hideUom eq true}">
                                      			<c:set var="hideuom2" value="true"/>
                                      			<c:set var="newuomDescription" value="${sellableUom2.inventoryUOMDesc}"/>
                                      			<c:set var="newuominventoryuomid" value="${sellableUom2.inventoryUOMID}"/>
                                      		</c:if>
                                      	</c:forEach>
                                      	<c:choose>
											<c:when test="${not empty orderEntryInventoryUOMId }">
							               		<input type="hidden" id="inventoryUomId" name="inventoryUomId" value="${orderEntryInventoryUOMId}">
							               </c:when>
							               <c:otherwise>
							                   <c:if test="${product.purchasable}">
		                                        	<c:if test="${not empty newuominventoryuomid}">
		                                        		<input type="hidden" id="inventoryUomId" name="inventoryUomId" value="${newuominventoryuomid}">
		                                        	</c:if>
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
                                        <input type="hidden" name="productCodePost" value="${product.code}"/>
                                        <input type="hidden" name="productNamePost" value="${fn:escapeXml(product.name)}"/>
                                      	<input type="hidden" id="isSellable"name="isSellable" value="${(!product.isSellable)}"/>
                                      	<input type="hidden" name="storeId" value="${product.stock.fullfillmentStoreId}"/>
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
                                                                      <button type="${buttonType}" id="variantButton" class="btn btn-primary btn-block js-add-to-cart" ${disableAtc}>
                                                                          <spring:theme code="product.base.select.options"/>
                                                                      </button>
                                                                  </div>
                                                              </c:when>
                                                              <c:otherwise>
                                                              <ycommerce:testId code="addToCartButton">
                                                                      <button type="${(orderOnlinePermissions eq true)?buttonType:'button'}" id="${(orderOnlinePermissions eq true)?'addToCartButton':ATCOOId}" class="btn btn-primary btn-block pull-left js-add-to-cart  js-enable-btn" disabled="disabled" ${disableAtc}>
                                                                      <spring:theme code="basket.add.again" />
                                                                      </button>
                                                              </ycommerce:testId>
                                                              </c:otherwise>
                                                        </c:choose>
                    
  	                	                        </c:when>
  	                	                        <c:otherwise>
  	                	                               <ycommerce:testId code="addToCartButton">
  	                	                             <div class="col-md-12 col-lg-12 col-xs-12 paddingRt product-detail-cartbtn padding-LeftZero">
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
 </div>
 </div>

            
	                        <div id="requestordernotes" style="display:none;">
	                       <h3 class="headline2"><spring:theme code="text.add.request.order.notes" /></h3>
	                       <div class="request-productdetails">
		                       <div class="row">
			                       <div class="thumb col-md-4 col-xs-4 col-sm-4">
										<a class="img-thumbnail" href="">
											<img class="img-responsive" src="/_ui/responsive/theme-lambda/images/missing_product_EN_300x300.jpg" alt="" title="">
										</a>
									</div>
			                       <div class="col-md-8 col-xs-8 col-sm-8">
			                       <p class="req_productId"></p>
			                      <div class="data-title" ><b><span class="req_productName"></span></b></div>
			                       <div class="margin20"> 
					                	     <span class="req_productPrice"></span> / <span class="req_uom"></span>
					                   </div>
			                         <div><img class="inventory-message-icon" src="${commonResourcePath}/images/S1-out-of-stock-Xg.svg" alt=""/><span class="stock"><spring:theme code="text.order.notsoldonline" /></span></div>
			                       </div>
		                       </div>
	                       <div class="cl"></div>
	                       <p class="bold-text margin20"><spring:theme code="text.add.request.instructions" /></p>
	                       <textarea class="col-md-12 col-sm-12 col-xs-12 form-control textarea"   rows="5" cols="10" placeholder="Enter your request or instructions below"></textarea>
	                       <div class="cl"></div>
		                       <div class="col-md-4 col-sm-4 col-xs-6">
			                       <div class="row">
		                       			<button class="btn btn-primary btn-block marginTop35"><spring:theme code="text.add.request.note" /></button>
		                       		</div>
		                       	</div>		
	                       <div class="cl"></div>
	                       <hr/>
		                       <div class="col-md-4 col-sm-4 col-xs-6">
			                       <div class="row">
			                       		<button class="btn btn-default btn-block"><spring:theme code="text.add.request.cart" /></button>
			                       </div>
		                       </div>

	                       <div class="cl"></div>

	                       </div>
	                       </div>
<fmt:setLocale value="${pageContext.response.locale}" scope="session"/>    