<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="entries" required="true" type="java.util.List" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="grid" tagdir="/WEB-INF/tags/responsive/grid" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ attribute name="loopIndex" required="true" type="java.lang.Integer"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ attribute name="fulfillmentType" required="false" type="java.lang.String"%>
<c:set var="unitpriceDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.unitprice.formattedDigits\")%>" />
<c:set var="isPickupDateRequired" value="false" />

<c:forEach items="${entries}" var="entry" varStatus="loop" begin='${loopIndex}'>
<c:set var="isChecked" value="${entry.bigBagInfo.isChecked ne null && not empty entry.bigBagInfo.isChecked ? entry.bigBagInfo.isChecked : false}" />
	<c:if test="${entry.product.homeStoreAvailableQty eq 0 or entry.quantity > entry.product.homeStoreAvailableQty}" > 
		<c:set var="isPickupDateRequired" value="true" />
	</c:if>
	<div class="col-xs-12 item__list--header orderDetailUom ${loop.index eq entries.size()-1?'order-review-last-item':''}" data-quantity="${entry.quantity}" data-homeStoreAvailableQty="${entry.product.homeStoreAvailableQty}" data-nearbyStoresAvailableQty="${entry.product.nearbyStoresAvailableQty}" data-hubStoresAvailableQty="${entry.product.hubStoresAvailableQty}" data-hubStoreNumber="${hubStoreId}"  data-isDeliverable="${entry.product.isDeliverable}" data-isShippable="${entry.product.isShippable}" data-stockAvailableOnlyHubStore="${entry.product.stockAvailableOnlyHubStore}" data-outOfStockImage="${entry.product.outOfStockImage}" data-isTransferrable="${entry.product.isTransferrable}" data-isStockInNearbyBranch="${entry.product.isStockInNearbyBranch}" data-pickupenabled="${(entry.product.outOfStockImage || entry.product.stockAvailableOnlyHubStore || (entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true))?false: true}" data-deliveryenabled="${(entry.product.isEligibleForDelivery && entry.product.isDeliverable && !entry.product.stockAvailableOnlyHubStore && (!(entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true)))?true: false}" data-shippingenabled="${((entry.product.isShippable || entry.product.stockAvailableOnlyHubStore) && (!(entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true)))?true: false}" data-index="${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" data-code="${entry.product.code}" data-loopIndex="${loopIndex}" data-uom="${entry.uomMeasure}" data-isEligibleForBackorder="${entry.product.isEligibleForBackorder}" data-isForceInStock="${entry.product.isForceInStock}" data-inStockImage="${entry.product.inStockImage}" data-productType="${entry.product.productType}" data-level1Category="${fn:escapeXml(entry.product.level1Category)}" data-level2Category="${fn:escapeXml(entry.product.level2Category)}" data-itemnumber="${entry.product.itemNumber}" data-islevel1Category="${fn:escapeXml(entry.product.level1Category) == 'Hardscapes & Outdoor Living'}" data-islevel2Category="${fn:escapeXml(entry.product.level2Category) == 'Natural Stone'}">
				<c:if test="${isMixedCartEnabled eq true}">	
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
				                       
				                    <div class=" marginBottom10 js-availablility-msg-mixed-cart ${showAvailableMessage}" 
				                    data-requestedqty="${requestedQty}"
				                    data-homestoreqty="${homeStoreQty}"
				                    data-nearbystoreqty="${nearbyStoresQty}"
				                    data-deliverystoreqty="${deliveryStoresQty}"
				                    data-shippingstoresqty="${shippingStoresQty}"
				                    data-isbackordarable="${entry.product.isEligibleForBackorder}"
				                    >
				                        <div class="flex-center order-cart-availability-backorder-msg " >
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
			
						<div class=" marginBottom10 ${entry.product.isEligibleForBackorder ? '':'hidden'}">
							<div class="flex-center font-small-xs order-cart-availability-backorder-msg">
								<common:exclamationCircle />
									<div class="pad-lft-10 black-title pad-rgt-10">
										<div class="bold delivery-threshold-text">
											<spring:theme code="cart.backorder.message" arguments="${not empty entry.product.nearestBackorderableStore ? entry.product.nearestBackorderableStore.name : entry.product.pickupHomeStoreInfo.storeName}" />
										</div>				
									</div>
							</div>
						</div>
				
				<!-- Nearest Backorderable at Iten Level Ends-->
            </c:if>
		    	 <c:if test="${entry.product.notInStockImage && isMixedCartEnabled ne true and (!(entry.product.isBackorderAndShippable && fulfillmentType eq 'PARCEL_SHIPPING'))}">
		    	 <div class=" marginBottom20 padding0">
				 <div class="flex-center order-review-available-backorder-mesg">
							<span class="flex-center"><common:exclamatoryIcon iconColor="#ef8700" width="24" height="24" /></span>
							<span class="pad-lft-10"><spring:theme code="checkout.multi.paymentMethod.text.notInStockImage" /></span>
				 </div>
				 </div>
		    	 
		    	 </c:if>
		    	<c:if test="${entry.product.isStockInNearbyBranch and isMixedCartEnabled ne true and fulfillmentType ne 'PARCEL_SHIPPING'}">
		    	<div class=" marginBottom20 padding0">
				<div class="flex-center order-review-available-nearby-mesg">
						<span class="flex-center"><common:exclamatoryIcon iconColor="#ed8606" width="24" height="24" /></span>
						<span class="pad-lft-10"><spring:theme code="checkout.multi.paymentMethod.text.isStockInNearbyBranch" /></span>
				</div>
				</div>
		    	
		    	 </c:if>
		    	 
		    	 <c:if test="${entry.product.isRegulatoryLicenseCheckFailed eq false}">
		    	 <div class="col-xs-12 marginBottom20 padding0">
                            <div class="flex-center  order-review-available-nearby-mesg">
                                <common:exclamatoryIcon iconColor="#ef8700" width="24" height="24" />
                                <span class="pad-lft-10"><spring:theme code="cart.restricted.msg"/></span>
                            </div>
                         </div>
		    	 
		    	 </c:if>
		    	 <div class="cl"></div>
		    	 <!-- RUPTraining Message -->
						<c:if test="${entry.product.isRUPTrainingSku eq true}">
						 <div class="col-xs-12 marginBottom20 padding0">
					                            <div class="flex-center order-review-available-nearby-mesg">
					                                <common:exclamatoryIcon iconColor="#ef8700" width="24" height="24" />
					                                <span class="pad-lft-10"><spring:theme code="training.certification.msg"/></span>
					                            </div>
					                         </div>
						</c:if>
						<div class="cl"></div>
				<!-- Refundable Message for Pallet and Tonnage-->
				
					<div class="bold col-xs-12 font-Arial font-size-14 text-default refundableMsg hidden">
							<spring:theme code="orderReview.refundableMsg"/>
					</div>	
	            <div class="col-md-6 col-xs-12 no-padding-xs item__image-wrapper p-r-0-imp">
		            <div class="col-xs-12 product-image-thumb">
		            	<div class="row">
			            <%-- product image --%>
			            <div class="col-md-3 col-xs-3">
				            <div class="item__image">
				                <a href="${entry.product.url}"><product:productPrimaryImage product="${entry.product}" format="product"/></a>
				            </div>
			            </div>
							<c:if test="${entry.product.singleUom eq true}">
								<c:set var="singleUom" value="true"/>
								<c:set var="uomDescription" value="${entry.product.singleUomDescription}"/>
								<c:set var="uomMeasure" value="${entry.product.singleUomMeasure}"/>
							</c:if>
			            <c:if test="${not empty entry.product.sellableUoms}">
						   <c:forEach items="${entry.product.sellableUoms}" var="sellableUom">
					 		<c:set var="uomDescription" value="${sellableUom.inventoryUOMDesc}"/>
			  			   </c:forEach>
	        		   </c:if>
	        		    <c:set var="uomMeasure" value="${entry.uomMeasure}"/>
			            <div class="col-md-9 col-xs-9 orderSummary_title p-r-30">
				            <%-- product name, code, promotions --%>
				            <div class="order-review-product-info">
				                <ycommerce:testId code="cart_product_name">
				                	<c:set var="productShortName" value="${fn:escapeXml(entry.product.name)}"></c:set>
				                	<c:set var="mobileProductShortName" value="${productShortName}"></c:set>
				                	<c:if test="${fn:length(mobileProductShortName) gt 45}">
					                	<c:set var="mobileProductShortName" value="${fn:substring(mobileProductShortName, 0, 42)}..."></c:set>
					                	<c:set var="mobileProductShortName" value="${mobileProductShortName}"></c:set>
				                	</c:if>
				                	<c:if test="${fn:length(productShortName) gt 70}">
					                	<c:set var="productShortName" value="${fn:substring(productShortName, 0, 67)}..."></c:set>
					                	<c:set var="productShortName" value="${productShortName}"></c:set>
				                	</c:if>
				                	<div class="font-14-xs show-for-mobile">
				                	${fn:escapeXml(entry.product.itemNumber)}
				                	 <c:if test="${entry.product.baseProduct != null}">
									<c:forEach items="${entry.product.categories}" var="option">
										<span class="pad-lft-15">${option.parentCategoryName}:${option.name}</span>
									</c:forEach>
									</c:if>
				                	</div>
				                	<div class="item__code hide-for-mobile">${fn:escapeXml(entry.product.itemNumber)} 
					                	<c:if test="${entry.product.baseProduct != null}">
										<c:forEach items="${entry.product.categories}" var="option">
											<span class="pad-lft-15">${option.parentCategoryName}:${option.name}</span>
										</c:forEach>
										</c:if>
									</div>
				                    <div class="item__name-wrapper">
				                    	<a href="${entry.product.url}" class="hide-for-mobile"><span class="item__name-black">${productShortName}</span></a>
				                    	<a href="${entry.product.url}" class="show-for-mobile"><span class="bold black-title">${fn:escapeXml(entry.product.name)}</span></a>
				                    </div>
				                </ycommerce:testId>
				               
				            	
				  				<c:if test="${ycommerce:doesPotentialPromotionExistForOrderEntry(cartData, entry.entryNumber)}">
				                 	<c:forEach items="${cartData.potentialProductPromotions}" var="promotion">
				                     	<c:set var="displayed" value="false"/>
				                      	<c:forEach items="${promotion.consumedEntries}" var="consumedEntry">
				                         	<c:if test="${not displayed && consumedEntry.orderEntryNumber == entry.entryNumber && not empty promotion.description}">
				                            	<c:set var="displayed" value="true"/>
				                               	<div>
				                                	<ycommerce:testId code="cart_potentialPromotion_label">
				                                     	${fn:escapeXml(promotion.description)}
				                                	</ycommerce:testId>
				            					</div>
				                            </c:if>
				                        </c:forEach>
				                    </c:forEach>
				                </c:if>
				                <c:if test="${ycommerce:doesAppliedPromotionExistForOrderEntry(cartData, entry.entryNumber)}">
				                    <c:forEach items="${cartData.appliedProductPromotions}" var="promotion">
				                        <c:set var="displayed" value="false"/>
				                        <c:forEach items="${promotion.consumedEntries}" var="consumedEntry">
				                            <c:if test="${not displayed && consumedEntry.orderEntryNumber == entry.entryNumber}">
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
				                <c:if test="${entry.product.configurable}">
				                    <div class="hidden-xs hidden-sm">
				                        <c:url value="/cart/${entry.entryNumber}/configuration/${entry.configurationInfos[0].configuratorType}" var="entryConfigUrl"/>
				                        <div class="item__configurations">
				                            <c:forEach var="config" items="${entry.configurationInfos}">
				                                <c:set var="style" value=""/>
				                                <c:if test="${config.status eq errorStatus}">
				                                    <c:set var="style" value="color:red"/>
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
				                                <a class="btn" href="${entryConfigUrl}"><spring:theme code="basket.page.change.configuration"/></a>
				                            </div>
				                        </c:if>
				                    </div>
				                </c:if>
				            </div>
			            </div>
			            <c:if test="${isChecked eq true}">
		            	<div class="hidden-sm hidden-xs col-md-9 col-md-offset-3 p-r-0"><div class="p-t-15">Big Bags</div></div>
		            	</c:if>
		            </div>
		         </div>
				</div>
				<c:set var="totalPrice" value="${ycommerce:cartEntryTotalPromotionPrice(entry.quantity, entry.totalPrice)}"/>
	            <div class="col-md-2 col-xs-4 item__price-wrapper p-x-0-imp">
	            	<!--  price -->
		        	<div class="item__price">
			            <c:if test="${entry.product.isRegulateditem   && !entry.product.isProductDiscontinued}">
			                <%-- <span class="visible-xs visible-sm col-xs-6 text-left"><b><spring:theme code="basket.page.itemPrice"/></b></span> --%>
			               	<%-- <format:price priceData="${entry.basePrice}" displayFreeForZero="false"/> --%>
			                <c:choose>
			                	<c:when test="${ycommerce:doesAppliedPromotionExistForOrderEntry(cartData, entry.entryNumber)}">
				                    <c:forEach items="${cartData.appliedProductPromotions}" var="promotion">
				                        <c:set var="displayed" value="false"/>
				                        <c:forEach items="${promotion.consumedEntries}" var="consumedEntry">
				                            <c:if test="${not displayed && consumedEntry.orderEntryNumber == entry.entryNumber}">
				                                <c:set var="displayed" value="true"/>
				                                <div class="col-md-12 col-xs-6">
				                                 <div class="row">
											         <c:choose>
														<c:when test="${ycommerce:isPromotionAppliedWithAdjustedPrice(totalPrice, entry.basePrice, consumedEntry.adjustedUnitPrice)}">
                                                         <c:choose>
										                  <c:when test="${not empty uomDescription }">
										                     <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/> / ${uomMeasure}
										                 </c:when>
										                 <c:otherwise>
													        <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/>
										                 </c:otherwise>
										                </c:choose>
											  	      </c:when>
														<c:otherwise>
														    <c:choose>
										                  <c:when test="${not empty uomDescription}">
										                     <del> <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/> / ${uomMeasure}</del>
														   <p class="discount-price">$<fmt:formatNumber pattern="${unitpriceDigits}" value="${totalPrice}"/> / ${uomMeasure}</p>
										                 </c:when>
										                 <c:otherwise>
													         <del> <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/></del>
														   <p class="discount-price">$<fmt:formatNumber pattern="${unitpriceDigits}" value="${totalPrice}"/></p>
										                 </c:otherwise>
										                   </c:choose>
														  </c:otherwise>
													</c:choose>
													 </div>
				                                </div>
				                            </c:if>
				                        </c:forEach>
				                    </c:forEach>
			                    </c:when>
			                    <c:otherwise>
			                       <c:choose>
						                 <c:when test="${not empty uomDescription }">
						                   <format:price priceData="${entry.basePrice}" displayFreeForZero="false" unitPrice="true"/> / ${uomMeasure}
						                </c:when>
						                <c:otherwise>
									      <format:price priceData="${entry.basePrice}" displayFreeForZero="false" unitPrice="true"/>
						                </c:otherwise>
					               </c:choose>

			                    </c:otherwise>
			                </c:choose>
			            </c:if>
			            <c:if test="${!entry.product.isRegulateditem   && !entry.product.isProductDiscontinued}">
			                	<%-- <span class="visible-xs visible-sm col-xs-6 text-left"><spring:theme code="basket.page.itemPrice"/></span> --%>
			                	<%-- <format:price priceData="${entry.basePrice}" displayFreeForZero="false"/> --%>
			                	<c:choose>
			                	<c:when test="${ycommerce:doesAppliedPromotionExistForOrderEntry(cartData, entry.entryNumber)}">
			                    <c:forEach items="${cartData.appliedProductPromotions}" var="promotion">
			                        <c:set var="displayed" value="false"/>
			                        <c:forEach items="${promotion.consumedEntries}" var="consumedEntry">
			                            <c:if test="${not displayed && consumedEntry.orderEntryNumber == entry.entryNumber}">
			                                <c:set var="displayed" value="true"/>
			                                <div>
										         <c:choose>
														<c:when test="${ycommerce:isPromotionAppliedWithAdjustedPrice(totalPrice, entry.basePrice, consumedEntry.adjustedUnitPrice)}">
                                                            <c:choose>
						                                       <c:when test="${not empty uomDescription }">
						                                           <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/> / ${uomMeasure}
						                                        </c:when>
						                                        <c:otherwise>
								   	                           <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/>
						                                        </c:otherwise>
					                                        </c:choose>
													    </c:when>
													<c:otherwise>
													      <c:choose>
										                  <c:when test="${not empty uomDescription}">
										                     <del> <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/> / ${uomMeasure}</del>
														   <p class="discount-price">$<fmt:formatNumber pattern="${unitpriceDigits}" value="${totalPrice}"/> / ${uomMeasure}</p>
										                 </c:when>
										                 <c:otherwise>
													         <del> <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/></del>
														   <p class="discount-price">$<fmt:formatNumber pattern="${unitpriceDigits}" value="${totalPrice}"/></p>
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
						                   <format:price priceData="${entry.basePrice}" displayFreeForZero="false" unitPrice="true"/> / ${uomMeasure}
						                </c:when>
						                <c:otherwise>
									      <format:price priceData="${entry.basePrice}" displayFreeForZero="false" unitPrice="true"/>
						                </c:otherwise>
					               </c:choose>
			                    
			                    </c:otherwise>
			                </c:choose>
			            </c:if>
			            <c:if test="${!entry.product.isRegulateditem  && entry.product.isProductDiscontinued}">
			               <%--  <span class="visible-xs visible-sm col-xs-6 text-left"><spring:theme code="basket.page.itemPrice"/></span> --%>
			                <%-- <format:price priceData="${entry.basePrice}" displayFreeForZero="false"/> --%>
			                <c:choose>
			                	<c:when test="${ycommerce:doesAppliedPromotionExistForOrderEntry(cartData, entry.entryNumber)}">
			                    <c:forEach items="${cartData.appliedProductPromotions}" var="promotion">
			                        <c:set var="displayed" value="false"/>
			                        <c:forEach items="${promotion.consumedEntries}" var="consumedEntry">
			                            <c:if test="${not displayed && consumedEntry.orderEntryNumber == entry.entryNumber}">
			                                <c:set var="displayed" value="true"/>
			                                <div>
										        <c:choose>
													<c:when test="${ycommerce:isPromotionAppliedWithAdjustedPrice(totalPrice, entry.basePrice, consumedEntry.adjustedUnitPrice)}">
                                                         <c:choose>
						                                       <c:when test="${not empty uomDescription}">
						                                           <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/> / ${uomMeasure}
						                                        </c:when>
						                                        <c:otherwise>
								   	                               <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/>
						                                        </c:otherwise>
					                                     </c:choose>
												   </c:when>
													<c:otherwise>
													    <c:choose>
										                  <c:when test="${not empty uomDescription}">
										                     <del> <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/> / ${uomMeasure}</del> 
														   <p class="discount-price">$<fmt:formatNumber pattern="${unitpriceDigits}" value="${totalPrice}"/> / ${uomMeasure}</p>
										                 </c:when>
										                 <c:otherwise>
													         <del> <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/></del> 
														   <p class="discount-price">$<fmt:formatNumber pattern="${unitpriceDigits}" value="${totalPrice}"/></p>
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
						                   <format:price priceData="${entry.basePrice}" displayFreeForZero="false" unitPrice="true"/> / ${uomMeasure}
						                </c:when>
						                <c:otherwise>
									      <format:price priceData="${entry.basePrice}" displayFreeForZero="false" unitPrice="true"/>
						                </c:otherwise>
					               </c:choose>
				                </c:otherwise>
			             	</c:choose>
			           	</c:if>
		        	</div>
		        	 <c:if test="${isChecked eq true}">
		        	<div class="item__price p-t-15 hidden-sm hidden-xs" style="margin-top: 64px;">
                        <span class="black-title b-price add_price atc-price-analytics">$${entry.bigBagInfo.unitPrice}</span>/ per bag
                    </div>
                    </c:if>
	            </div>
	            <div class="col-md-2 col-xs-4 item__quantity-wrapper p-x-0-imp">
	            	<!--    quantity -->
			        <div class="item__quantity">
				        <c:forEach items="${entry.product.baseOptions}" var="option">
				            <c:if test="${not empty option.selected and option.selected.url eq entry.product.url}">
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
				        <ycommerce:testId code="orderDetails_productQuantity_label"><span class="">

				            <label class="hidden-md hidden-lg col-xs-4 text-left padding0"><spring:theme code="text.account.savedCart.qty"/>:</label>
				             <label class="hidden-sm hidden-xs  text-left padding0"><spring:theme code="text.account.savedCart.qty"/>:</label>
				            <span class="qtyValue">
				                        ${fn:escapeXml(entry.quantity)}
				            </span></span>
				        </ycommerce:testId>
			    	</div>
			    	<c:if test="${isChecked eq true}">
	            	<div class="item__quantity p-t-15 hidden-sm hidden-xs" style="margin-top: 59px;">
                        <span class="">
                             <label class="hidden-sm hidden-xs  text-left padding0">QTY:</label>
                            <span class="qtyValue">${entry.bigBagInfo.numberOfBags}</span>
                        </span>
                    </div>
                    </c:if>
	            </div>
	            <div class="col-md-2 col-xs-4 item__total-wrapper no-padding-rgt-md p-l-0-imp">
	            <span class="">
	             	<!-- <div class="visible-xs visible-sm col-xs-6 text-left">Total</div> -->
	            	<c:if test="${!entry.product.isRegulateditem   && !entry.product.isProductDiscontinued}">
		            	<ycommerce:testId code="cart_totalProductPrice_label">
		                	<div class="item__total js-item-total">
		                    	<format:price priceData="${entry.totalPrice}" displayFreeForZero="true" />
		                	</div>
		            	</ycommerce:testId>
					</c:if>
					</span>
					<c:if test="${entry.product.isRegulateditem   && !entry.product.isProductDiscontinued}">
		            	<ycommerce:testId code="cart_totalProductPrice_label">
		                	<div class="item__total js-item-total">
		                    	<format:price priceData="${entry.totalPrice}" displayFreeForZero="true" />
		                	</div>
		            	</ycommerce:testId>
					</c:if>
					<c:if test="${!entry.product.isRegulateditem  && entry.product.isProductDiscontinued}">
						 <ycommerce:testId code="cart_totalProductPrice_label">
		                	<div class="item__total js-item-total">
		                    	<format:price priceData="${entry.totalPrice}" displayFreeForZero="true" />
		                	</div>
		            	</ycommerce:testId>
					</c:if>
					 <c:if test="${isChecked eq true}">
					<div class="item__total js-item-total p-t-15 hidden-sm hidden-xs" style="margin-top: 64px;">
                         <span class="black-title b-price add_price atc-price-analytics">$${entry.bigBagInfo.totalPrice}</span>
        			</div>
        			</c:if>
	            </div>
	             <div class="col-xs-12 hidden-md hidden-lg hidden-sm ${loop.index eq entries.size()-1?'':'order-review-xs-divider'}"></div>
	             <c:if test="${isChecked eq true}">
	             <div class="b-t-grey col-xs-8 hidden-lg hidden-md">Big Bags</div>
	             <div class="b-t-grey col-xs-4 hidden-lg hidden-md text-align-right">$${entry.bigBagInfo.totalPrice}</div>
	             </c:if>
	        </div>
        </c:forEach>
		<input type="hidden" class="isPickupDateRequired"  value="${isPickupDateRequired}"/>
