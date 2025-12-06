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

<%-- setting locale to 'en_US' to format price with dot for decimal values and comma as grouping separator  
     reset to current locale in the end of page  --%>
<fmt:setLocale value="en_US" scope="session"/>
<c:set var="varShowStock" value="${(empty showStock) ? true : showStock}" />
<c:set var="unitpriceDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.unitprice.digits\")%>" />

<c:url value="${orderEntry.product.url}" var="productUrl"/>
<c:set var="entryStock" value="${orderEntry.product.stock.stockLevelStatus.code}"/>

<c:choose>
	<c:when test="${isMixedCartEnabled eq true}">
		<c:set var="productTitle" value="col-md-9 col-xs-6"/>
		<c:set var="paddingMobileZero" value="no-padding-xs no-padding-sm"/>
		<c:set var="LeftpaddingMobileZero" value="no-padding-lft-sm no-padding-lft-xs"/>
		<c:set var="priceWidth" value="col-xs-3"/>
		<c:set var="totalWidth" value="col-xs-3"/>
	</c:when>
	<c:otherwise>
			<c:set var="productTitle" value="col-md-9 col-xs-12" />
			 <c:set var="paddingMobileZero" value=""/>
			 <c:set var="LeftpaddingMobileZero" value=""/>
			 <c:set var="priceWidth" value="col-xs-12"/>
			 <c:set var="totalWidth" value="col-xs-6"/>
	</c:otherwise>
</c:choose>
	
<div class="col-xs-12 item__list--header print-p-b-15 print-p-t-15 ${paddingMobileZero}" data-quantity="${orderEntry.quantity}" data-homeStoreAvailableQty="${orderEntry.product.pickupHomeStoreInfo.onHandQuantity}" data-nearbyStoresAvailableQty="${orderEntry.product.pickupNearbyStoreInfo.onHandQuantity}" data-hubStoresAvailableQty="${orderEntry.product.hubStoresAvailableQty}">
<c:set var="isPickupDateRequired" value="false" />
<!-- Refundable Message for Pallet and Tonnage-->
<div class="bold col-xs-12 font-Arial font-size-14 text-default refundableMsg hidden">
							<spring:theme code="orderReview.refundableMsg"/>
					</div>	
<c:if test="${orderEntry.product.pickupHomeStoreInfo.onHandQuantity eq 0 or orderEntry.quantity > orderEntry.product.pickupHomeStoreInfo.onHandQuantity}" > 
		<c:set var="isPickupDateRequired" value="true" />
	</c:if>
	<input type="hidden" class="isPickupDateRequired"  value="${isPickupDateRequired}"/>
<c:if test="${isMixedCartEnabled eq true}">	
					<c:set var="requestedQty" value="${orderEntry.quantity}"/>
				    <c:set var="homeStoreQty" value="${orderEntry.product.pickupHomeStoreInfo.onHandQuantity}"/>
				    <c:set var="nearbyStoresQty" value="${orderEntry.product.pickupNearbyStoreInfo.onHandQuantity}"/>
				    <c:set var="deliveryStoresQty" value="${orderEntry.product.deliveryStoreInfo.onHandQuantity}"/>
				    <c:set var="shippingStoresQty" value="${orderEntry.product.shippingStoreInfo.onHandQuantity}"/>
				    <c:set var="hubStoresQty" value="${orderEntry.product.hubStoresAvailableQty}"/>
				    <c:set var="showAvailableMessage" value="hidden"/>
				  	<c:set var="onHandQty" value=""/>
				    <c:set var="remainingQty" value=""/>
				    <c:choose>
				    	<c:when test="${(orderEntry.deliveryMode.code eq 'pickup') and (orderEntry.deliveryPointOfService.storeId eq orderEntry.product.pickupHomeStoreInfo.storeId) and (requestedQty gt homeStoreQty) and (orderEntry.product.isEligibleForBackorder ne true)}">
				    		<c:set var="showAvailableMessage" value=""/>
				    		<c:set var="onHandQty" value="${homeStoreQty}"/>
				    		<c:set var="remainingQty" value="${requestedQty - homeStoreQty}"/>
				    	</c:when>
				    	<c:when test="${(orderEntry.deliveryMode.code eq 'pickup') and (orderEntry.deliveryPointOfService.storeId ne orderEntry.product.pickupHomeStoreInfo.storeId) and (requestedQty gt nearbyStoresQty) and (orderEntry.product.isEligibleForBackorder ne true)}">
				    		<c:set var="showAvailableMessage" value=""/>
				    		<c:set var="onHandQty" value="${nearbyStoresQty}"/>
				    		<c:set var="remainingQty" value="${requestedQty - nearbyStoresQty}"/>
				    	</c:when>
				    	<c:when test="${(orderEntry.deliveryMode.code eq 'standard-net') and (requestedQty gt deliveryStoresQty) and (orderEntry.product.isEligibleForBackorder ne true)}">
				    		<c:set var="showAvailableMessage" value=""/>
				    		<c:set var="onHandQty" value="${deliveryStoresQty}"/>
				    		<c:set var="remainingQty" value="${requestedQty - deliveryStoresQty}"/>
				    	</c:when>
				    	<c:when test="${(orderEntry.deliveryMode.code eq 'free-standard-shipping') and (requestedQty gt shippingStoresQty) and (orderEntry.product.isEligibleForBackorder ne true)}">
				    		<c:set var="showAvailableMessage" value=""/>
				    		<c:set var="onHandQty" value="${shippingStoresQty}"/>
				    		<c:set var="remainingQty" value="${requestedQty - shippingStoresQty}"/>
				    	</c:when>
				    </c:choose>
				   
				       <c:if test="${(orderEntry.product.productType ne 'Nursery') and (onHandQty gt 0)}">
				                       
				                    <div class=" marginBottom10 js-availablility-msg-mixed-cart ${showAvailableMessage}" 
				                    data-requestedqty="${requestedQty}"
				                    data-homestoreqty="${homeStoreQty}"
				                    data-nearbystoreqty="${nearbyStoresQty}"
				                    data-deliverystoreqty="${deliveryStoresQty}"
				                    data-shippingstoresqty="${shippingStoresQty}"
				                    data-isbackordarable="${orderEntry.product.isEligibleForBackorder}"
				                    >
				                        <div class="flex-center availability-backorder-msg" >
				                            <common:exclamationCircle />
				                            <div class="pad-lft-10 black-title bold pad-rgt-10">
					                            <spring:theme code="cart.more.quantity.than.available1"/>
					                            <span class="js-onhand-qty">${onHandQty}</span>
					                            <spring:theme code="cart.more.quantity.than.available2"/>
					                            <span class="js-remaining-qty">${remainingQty}</span>
					                            <spring:theme code="cart.more.quantity.than.available3"/>                                   
											</div>
											
					                        </div>
				                   		</div>                                     
			                
			        </c:if>
			        <!-- Nearest Backorderable at Iten Level -->
			
						<div class=" marginBottom10 ${orderEntry.product.isEligibleForBackorder ? '':'hidden'}">
							<div class="flex-center font-small-xs availability-backorder-msg">
								<common:exclamationCircle />
									<div class="pad-lft-10 black-title pad-rgt-10">
										<div class="bold delivery-threshold-text">
											<spring:theme code="cart.backorder.message" arguments="${not empty orderEntry.product.nearestBackorderableStore ? orderEntry.product.nearestBackorderableStore.name : orderEntry.product.pickupHomeStoreInfo.storeName}" />
										</div>				
									</div>
							</div>
						</div>
				
				<!-- Nearest Backorderable at Iten Level Ends-->
            </c:if>


	<c:if test="${orderEntry.product.isEligibleForBackorder}">
	   	 <div class=" marginBottom20 padding0 print-hidden">
		 	<div class="flex-center order-review-available-backorder-mesg">
				<span class="flex-center"><common:exclamatoryIcon iconColor="#ef8700" width="24" height="24" /></span>
				<span class="pad-lft-10"><spring:theme code="orderSummaryPage.backorder.message" /></span>
		 	</div>
		 </div>
   	 
   	 </c:if>
	 <c:if test="${orderEntry.product.isRUPTrainingSku eq true}">
	   	 <div class="col-xs-12 marginBottom20 hidden-print p-l-0 p-r-0">
            <div class="flex-center order-confirmation-rup-license">
                <common:exclamatoryIcon iconColor="#ef8700" width="24" height="24" />
                <span class="pad-lft-10"><spring:theme code="training.certification.msg"/></span>
            </div>
         </div>
   	 </c:if>
	 <c:if test="${orderEntry.product.isStockInNearbyBranch and isMixedCartEnabled ne true and order.orderType ne 'PARCEL_SHIPPING'}">
		    	<div class=" marginBottom20 padding0 print-hidden">
				<div class="flex-center order-review-available-nearby-mesg">
						<span class="flex-center"><common:exclamatoryIcon iconColor="#ed8606" width="24" height="24" /></span>
						<span class="pad-lft-10"><spring:theme code="checkout.multi.paymentMethod.text.isStockInNearbyBranch" /></span>
				</div>
				</div>
		    	
		    	 </c:if>
	<div class="col-md-6 col-xs-12 item__image-wrapper print-p-a-0 ${paddingMobileZero}">
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
	        <div class="col-md-3 col-xs-6 product-image-thumb print-hidden ${LeftpaddingMobileZero}">
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
            <div class="${productTitle} print-row-12 orderSummary_title">
	            <%-- product name, code, promotions --%>
	            <div class="print-d-f">
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
           				<div class="item__code print-m-a-0 print-row-3 print-f-w-bold print-word-br">${fn:escapeXml(orderEntry.product.itemNumber)}</div>
           				 	<div class="item__name-wrapper print-row-9 print-l-h-14 print-word-br">
	                    		<div class="hide-for-mobile"><span class="item__name">${productShortName}</span></div>
								<div class="show-for-mobile"><span class="item__name print-f-w-normal print-f-s-13">${mobileProductShortName}</span></div>
	                    	</div>
	                    </c:when>
	                    <c:otherwise>
	                    <div class="item__code print-m-a-0 print-row-3 print-f-w-bold print-word-br">${fn:escapeXml(orderEntry.product.itemNumber)}</div>
	                    <div class="item__name-wrapper print-row-9 print-l-h-14 print-word-br print-m-l-25">
	                    	<a href="${productUrl}" class="hide-for-mobile"><span class="item__name">${productShortName}</span></a>
	                    	<a href="${productUrl}" class="show-for-mobile"><span class="item__name print-f-s-13 print-f-w-normal">${mobileProductShortName}</span></a>
	                    </div>
	                    </c:otherwise>
	                    </c:choose>
	                </ycommerce:testId>
	                <c:if test="${orderEntry.product.baseProduct != null}">
						<c:forEach items="${orderEntry.product.categories}" var="option">
							${option.parentCategoryName}:${option.name}<br/>
						</c:forEach>
					</c:if>
	            	<div class="item__code hide-for-mobile hidden">${fn:escapeXml(orderEntry.product.itemNumber)}</div>
	            	<%-- availability --%>
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

    <div class="col-md-2 ${priceWidth} item__price-wrapper add-price-wrapper ${paddingMobileZero}">
    	<%-- price --%>
    	<input type="hidden" class="level2category" value="${not empty orderEntry.product.level2Category and orderEntry.product.level2Category ne null ? orderEntry.product.level2Category : "Empty"}" />
 		<div class="item__price orderDetailUom" data-uom="${uomMeasure}" data-level1category="${orderEntry.product.level1Category}" data-level2category="${orderEntry.product.level2Category}">
        	<%-- <span class="visible-xs visible-sm col-xs-6 text-left bold-text"><spring:theme code="basket.page.itemPrice"/></span> --%>
           	<div class="col-xs-6 col-md-12 ${paddingMobileZero}">
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
    <div class="col-md-2 col-xs-6 item__quantity-wrapper add-quantiy-wrapper">
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
		 <ycommerce:testId code="orderDetails_productQuantity_label"><span>
		    		      <label class="hidden-md hidden-lg print-hidden col-xs-4 text-left padding0"><spring:theme code="text.account.savedCart.qty"/>:</label>
				             <label class="hidden-sm hidden-xs  text-left padding0"><spring:theme code="text.account.savedCart.qty"/>:</label>
		     &nbsp;<span class="qtyValue print-m-l-18">
		         <c:choose>
		             <c:when test="${consignmentEntry ne null }">
		               ${fn:escapeXml(consignmentEntry.quantity)}
		             </c:when>
		             <c:otherwise>
			            <c:if test="${cmsPage.uid eq 'order'}">
			             <fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${orderEntry.quantityText}" var="quantityText" />
		             	 ${fn:escapeXml(quantityText)}
			             </c:if>
			             <c:if test="${cmsPage.uid eq 'orderConfirmationPage'}">
			             	${fn:escapeXml(orderEntry.quantity)}
			             </c:if>
		             	
		             </c:otherwise>
		         </c:choose>
		     </span></span>
		 </ycommerce:testId>
		</div>
     </div>
     <div class="col-md-2 ${totalWidth} item__total-wrapper add-totalwrapper ${paddingMobileZero}">
      	<!-- <div class="visible-xs visible-sm col-xs-6 text-left">Total</div> -->
     	<ycommerce:testId code="orderDetails_productTotalPrice_label">
         	<div class="item__total js-item-total">
             	<format:price priceData="${orderEntry.totalPrice}" displayFreeForZero="true" />
         	</div>
     	</ycommerce:testId>
     </div>
</div>
<fmt:setLocale value="${pageContext.response.locale}" scope="session"/>
