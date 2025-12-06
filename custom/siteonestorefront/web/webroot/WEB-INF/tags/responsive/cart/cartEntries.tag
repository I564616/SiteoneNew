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
<%@ attribute name="cartPriceLoader" required="false" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart"%>
<fmt:setLocale value="en_US" scope="session"/>
<c:set var="errorStatus" value="<%= de.hybris.platform.catalog.enums.ProductInfoStatus.valueOf(\"ERROR\") %>" />
<c:set var="unitpriceDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.unitprice.digits\")%>" />
<c:set var="totalpriceDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.totalprice.digits\")%>" />
<c:set var="nontransferableFlag" value="false"/>
<c:set var="isSplitMixedPickupBranchFE" value="false" />
<c:if test="${not empty isSplitMixedCartEnabledBranch && isSplitMixedCartEnabledBranch ne null}" >
	<c:set value="${isSplitMixedCartEnabledBranch}" var="isSplitMixedPickupBranchFE"></c:set>
</c:if>
<c:set var="isSplitCart" value="${isSplitMixedPickupBranchFE}" />
<c:set var="isBulkBigBag" value="${not empty isBulkBigBagEnabled && isBulkBigBagEnabled ne null ? isBulkBigBagEnabled : false}" />
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
        		<c:if test="${store eq ((empty hubStoreNumber || !entry.product.isShippable)   ?  sessionStore.storeId : hubStoreNumber)}">
            		<c:set var="isMyStoreProduct" value="true" />
        		</c:if>
        </c:forEach>
        <c:if test="${entry.product.inStockImage}">
        		<c:set var="isMyStoreProduct" value="true" />
    		</c:if>
        <%--this confirms that item is available in home or a nearby store. --%>
        <c:if test="${(isPriceAvailable && hideList eq true) && (guestUsers ne 'guest')}">
			<c:set var="enableCheckout" value="true" />
		</c:if>
         <c:if test="${isMyStoreProduct eq false && (empty hubStoreNumber || ( !entry.product.inStockImage && !entry.product.isShippable ))}">
         
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
        <c:if test="${entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true}">
            <c:set var="enableCheckout" value="false"/>
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
        <%-- chevron for multi-d products --%>
        <c:choose>
       	<c:when test="${((entry.product.productType eq 'Nursery' and (entry.product.level1Category eq 'Nursery' || entry.product.level1Category eq 'Vivero')) and (entry.quantity gt entry.product.stock.stockLevel) and entry.product.isTransferrable ne true and entry.product.isEligibleForBackorder ne true) || ((entry.product.level1Category eq 'Materiales duros & Vida al Aire Libre' || entry.product.level1Category eq 'Hardscapes & Outdoor Living') && (entry.quantity gt entry.product.homeStoreAvailableQty) && entry.product.isTransferrable ne true && entry.product.isForceInStock ne true)}">
       		<c:set var="showErrorMsg" value=""/>
       		<c:set var="enableCheckout" value="false"/>
       		<c:set var="redBorder" value="border-red"/>
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
              <input type="hidden" name="isTransferrabletest" value="${entry.product.isTransferrable}"/>
                <input type="hidden" name="nurserytest" value="${((entry.product.productType eq 'Nursery') and (entry.quantity gt entry.product.stock.stockLevel) and (entry.product.isTransferrable ne true) )}"/>
            <input type="hidden" name="homestoreqtyhardscape" value="${entry.product.homeStoreAvailableQty}"/>
            <input type="hidden" name="nurseryqtymsg" value="${ entry.product.stock.stockLevel}"/>
            <div class="hidden-xs hidden-sm item__toggle" style="padding:0px;width: 0px;">
                <c:if test="${entry.product.multidimensional}">
                    <div class="js-show-editable-grid" data-index="${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" data-read-only-multid-grid="${not entry.updateable}">
                        <ycommerce:testId code="cart_product_updateQuantity">
                            <span class="glyphicon glyphicon-chevron-down"></span>
                        </ycommerce:testId>
                    </div>
                </c:if>
            </div>

            <%-- product image --%>
            

                <%-- product name, code, promotions --%>
                   <%--  <div class="col-md-6 col-xs-7 print-width print-item">
                        
                            <c:if test="${isPriceAvailable}">
							<c:choose>
							        <c:when test="${entry.isCustomerPrice}">
                                              <c:choose>
                                               <c:when test="${ycommerce:doesAppliedPromotionExistForOrderEntry(cartData, entry.entryNumber)}">
                                                </c:when>
                                               <c:otherwise>
                                                   <c:choose>
										               <c:when test="${not empty uomDescription}">
										                       <div class="visible-md visible-lg hidden-sm hidden-xs">
							         <c:choose>
								            <c:when test="${hideList ne true && hideCSP ne true}">
                                                <c:choose>
								            		<c:when test="${inventoryFlag eq true }">
								            			<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
								            		</c:when>
								            		<c:otherwise>
								                	 	<p class="siteOneListPricee"><spring:theme code="text.product.siteOnelistprice"/></p>
                                                		<del><span><b>$<fmt:formatNumber maxFractionDigits="${totalpriceDigits}" minFractionDigits="${totalpriceDigits}" value="${(not empty entry.uomPrice)?entry.uomPrice:entry.getListPrice()}"/></b><b> / ${uomMeasure}</b></span></del>
                                                        <div class="yourpriceGreen"> <p class="yourPrice"><spring:theme code="cartItems.yourPrice" /><br><p>
			                                            	<span><b><format:price priceData="${entry.basePrice}" displayFreeForZero="false" /> / ${uomMeasure}</b></span>
			                                            </div>
			                                        </c:otherwise>
			                                      </c:choose>
							                  </c:when>
								           <c:otherwise>
								               <c:choose>
								                 <c:when test="${hideCSP ne true}">
								                 <c:choose>
								            		<c:when test="${inventoryFlag eq true }">
								            			<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
								            		</c:when>
								            		<c:otherwise>
								                   <div class="yourpriceGreen"> <p class="yourPrice"><spring:theme code="cartItems.yourPrice" /><br><p>
			                                                <span><b><format:price priceData="${entry.basePrice}" displayFreeForZero="false" /> / ${uomMeasure}</b></span>
			                                                </div>
			                                         </c:otherwise>
			                                         </c:choose>
								                 </c:when>
								                   <c:otherwise>
								                   	<c:choose>
								            		<c:when test="${inventoryFlag eq true }">
								            			<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
								            		</c:when>
								            		<c:otherwise>
								            		</c:otherwise>
								            		</c:choose>
								                   </c:otherwise>
								                </c:choose>
								           </c:otherwise>
								          </c:choose>
			                                                </div>
										               </c:when>
										               <c:otherwise>
													         <div class="visible-md visible-lg hidden-sm hidden-xs">
							         <c:choose>
								            <c:when test="${hideList ne true && hideCSP ne true}">
								            	<c:choose>
								            		<c:when test="${inventoryFlag eq true }">
								            			<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
								            		</c:when>
								            		<c:otherwise>
								                	  <p class="siteOneListPricee"><spring:theme code="text.product.siteOnelistprice"/></p>
			                                                <del><span><b>$<fmt:formatNumber maxFractionDigits="${totalpriceDigits}" minFractionDigits="${totalpriceDigits}" value="${(not empty entry.uomPrice)?entry.uomPrice:entry.getListPrice()}"/> / ${uomMeasure}</b></span></del>
			                                                <div class="yourpriceGreen"> <p class="yourPrice"><spring:theme code="cartItems.yourPrice" /><br><p>
			                                                <span><b><format:price priceData="${entry.basePrice}" displayFreeForZero="false" /> / ${uomMeasure}</b></span>
			                                                </div>
			                                        </c:otherwise>
			                                    </c:choose>
			                               </c:when>
								           <c:otherwise>
								           	   <c:choose>
								                 <c:when test="${hideCSP ne true}">
								                 	<c:choose>
								            			<c:when test="${inventoryFlag eq true }">
								            				<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
								            			</c:when>
								            			<c:otherwise>
								                     		<div class="yourpriceGreen"> <p class="yourPrice"><spring:theme code="cartItems.yourPrice" /><br><p>
			                                                	<span><b><format:price priceData="${entry.basePrice}" displayFreeForZero="false" /> / ${uomMeasure}</b></span>
			                                                </div>
			                                            </c:otherwise>
			                                        </c:choose>
			                                     </c:when>
			                                     <c:otherwise>
			                                     	<c:choose>
			                                     	<c:when test="${inventoryFlag eq true }">
								            				<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
								            		</c:when>
								            		<c:otherwise>
								            		</c:otherwise>
								            		</c:choose>
			                                     </c:otherwise>
			                                     </c:choose>
								           </c:otherwise>
								          </c:choose>
			                                                </div>
										               </c:otherwise>
										            </c:choose>
                                                 </c:otherwise>
                                                </c:choose>
							           </c:when>
							      <c:otherwise>

							         </c:otherwise>
						      </c:choose>
		               </c:if>


                        

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
                                <c:if test="${ycommerce:doesAppliedPromotionExistForOrderEntry(cartData, entry.entryNumber)}">
                                    <c:forEach items="${cartData.appliedProductPromotions}" var="promotion">
                                        <c:set var="displayed" value="false" />
                                        <c:forEach items="${promotion.consumedEntries}" var="consumedEntry">
                                            <c:if test="${not displayed && consumedEntry.orderEntryNumber == entry.entryNumber}">
                                                <c:set var="displayed" value="true" />
                                                <div class="promo addPromotionDesc">
                                                    <ycommerce:testId code="cart_appliedPromotion_label">
                                                        ${fn:escapeXml(promotion.description)}
                                                    </ycommerce:testId>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                </c:if>
                            </c:if>

                                <c:if test="${entry.product.configurable}">
                                    <div class="hidden-xs hidden-sm">
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
                    </div> --%>




                    <%-- availablity--%>
                        
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

                        
                                                                                                     
    <c:if test="${!entry.product.isRegulateditem   && !entry.product.isProductDiscontinued}">
        <c:choose>
            <c:when test="${isMyStoreProduct}">
                <c:choose>
                    <c:when test="${isPriceAvailable}">
                        <c:choose>
                            <c:when test="${isStockAvailable}">
                                
                            </c:when>
                            <c:otherwise>
                            <c:if test="${entry.product.outOfStockImage}">
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
    <input type= "hidden" name="enableCheckoutNReg-test1" value="${enableCheckout}" />
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
                <c:if test="${!entry.product.isEligibleForBackorder}">
                    <c:set var="enableCheckout" value="false" />  
                </c:if>                
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
                            <c:if test="${entry.product.outOfStockImage}">
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
<input type= "hidden" name="enableCheckoutReg-test" value="${enableCheckout}" />
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
    <input type= "hidden" name="enableCheckoutNReg-test2" value="${enableCheckout}" />    
                                  
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
	<input type="hidden" id="productCode${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${entry.product.code}"/>
	<input type="hidden" id="productName${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${fn:escapeXml(entry.product.name)}"/>	
	<input type="hidden" id="productCategory${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${entry.product.level1Category}" />
	<input type="hidden" id="productSubCategory${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${entry.product.level2Category}" />
	<input type="hidden" id="productnearby${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${entry.product.isStockInNearbyBranch}" />
	<input type="hidden" id="productQty${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${entry.quantity}" />
	<input type="hidden" id="productprice${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${entry.basePrice.value}" />
	<input type="hidden" id="productstockStatus${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${fn:escapeXml(entry.product.storeStockAvailabilityMsg)}" />
	<input type="hidden" id="transferableFlag${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${entry.product.isTransferrable}" /> 
	<input type="hidden" id="isstockNearbyFlag${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${entry.product.isStockInNearbyBranch}"/>
	<input type="hidden" id="productBrand${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${entry.product.productBrandName}" />
	<input type="hidden" id="productLevel3Category${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${entry.product.categories[0].name}" />
	<!-- New Cart Page Starts -->
	
	
	<c:if test="${entry.product.stockAvailableOnlyHubStore}">
		<input type="hidden" class="isStockAvalilableOnlyHub" value="true"/>
	</c:if>
<!-- error messages -->
	<c:set var="guestRedBorder" value=""/>
	<c:set var="maxQtyRedBorder" value=""/>
	<div class="js-cart-qty-error  gc-error-msg ${showErrorMsg}" >
        		<spring:theme code="text.product.qty.alert" arguments="${isHardscapeQty ? entry.product.homeStoreAvailableQty : entry.product.stock.stockLevel}" />
     </div>	
     	<!--- Product level error message <c:if test="${!entry.product.isShippable}">	     
			<div class="gc-error-msg js-gc-error-msg hidden hidden-print">					
				<spring:theme code="text.cart.parcel.shipping.error"/>					
			</div>
		</c:if> --->
	
	<c:if test="${guestUsers eq 'guest' && hideList eq true}">
		<c:set var="guestRedBorder" value="border-color-orange "/>
		<div class="gc-error-msg">	         
           		<spring:theme code="text.cart.guest.error"/>
           	</div>	             
       	</c:if>
       	<!-- max quantity check -->
       	
    	<c:if test="${((!isAnonymous) or (guestUsers eq 'guest')) and (not empty entry.product.validationMessage)}">
			<c:set var="maxQtyRedBorder" value="maxQtyRed "/>		
				             
       	</c:if> 
       	<c:if test="${entry.product.validationMessage ne null}"> 
       		<div class="gc-error-msg js-max-qty-err-message hidden">	         
	           	 <!-- error message comes here -->
	           	 <spring:theme code="${entry.product.validationMessage}" />
	        </div>
	</c:if>
       	<!-- max quantity check end-->    
	
	<input type="hidden" id="enableCheckout" value="${enableCheckout}"/>			
<!-- error messages ends -->
			<c:if test="${entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true}">
				<c:set var="nontransferableFlag" value="true"/>
			</c:if>	
			
			<c:set var="nonsaleble" value="false"/>	
			
			<c:if test="${isProductSellable eq false}">
				<c:set var="nonsaleble" value="true"/>		
       		</c:if>
<c:set var="allFulfillmentEnabled" value="false"/>       		
<c:if test="${entry.product.isDeliverable && entry.product.isShippable && (!entry.product.stockAvailableOnlyHubStore)}">
	<c:set var="allFulfillmentEnabled" value="true"/>   
</c:if>
<c:if test="${!allFulfillmentEnabled && !entry.product.outOfStockImage && !(entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true)}">
<div class="hidden-print row hidden-md hidden-lg hidden${!entry.product.stockAvailableOnlyHubStore? ' national-shipping-nonship-element' : ' national-shipping-ship-element'}" data-analyticerror="${entry.product.stockAvailableOnlyHubStore? 'Available for shipping only' : 'Pick-Up/Delivery Only' }">
<c:if test="${entry.product.stockAvailableOnlyHubStore}">
	<div class="col-xs-offset-1 col-xs-11 padding0">
		<div class="font-size-14 font-small-xs bold flex-center national-shipping-alert ${isSplitCart ? 'hidden' : ''}">
			<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 49 49">
				<g fill="#ffffff" stroke="#ffffff" stroke-width="4">
					<circle cx="24.5" cy="24.5" r="24.5" stroke="none"></circle>
					<circle cx="24.5" cy="24.5" r="22.5" fill="none"></circle>
				</g>
				<path d="M22.887,18.595a3.444,3.444,0,1,1-3.444-3.444A3.447,3.447,0,0,1,22.887,18.595ZM16.4,1.085l.585,11.708a1.033,1.033,0,0,0,1.032.981h2.856a1.033,1.033,0,0,0,1.032-.981l.585-11.708A1.033,1.033,0,0,0,21.457,0H17.43A1.033,1.033,0,0,0,16.4,1.085Z" transform="translate(5.056 13.481)" fill="#ef8700"></path>
			</svg>
			<span class="pad-xs-lft-5">
				
				<spring:theme code="text.cart.problem.ship"/>
				
			</span>
		</div>
	</div>
</c:if>
</div>
</c:if>
<input class="cartUomId" value="${entry.uomId}" type="hidden"/> 
<input class="trasferable-flag" value="${entry.product.isTransferrable}" type="hidden"/> 
<input class="isstockNearby-flag" value="${entry.product.isStockInNearbyBranch}" type="hidden"/> 
<c:set var="requestedQty" value="${entry.quantity}"> </c:set>
<c:set var="homeStoreQty" value="${entry.product.homeStoreAvailableQty}"> </c:set>
<c:set var="nearbyStoresQty" value="${entry.product.nearbyStoresAvailableQty}"> </c:set>
<c:set var="hubStoresQty" value="${entry.product.hubStoresAvailableQty}"> </c:set>
<input name="uomMeasure" value="${uomMeasure}" type="hidden"/> 
<c:set var="hardscapeConsumablePriceVariationProduct" value="false"> </c:set>

<c:if test="${entry.product.level1Category == 'Hardscapes & Outdoor Living' || (entry.product.level1Category == 'Landscape Supply' && entry.product.level2Category == 'Consumables')}">
    <c:if test="${uomMeasure == 'Net Ton (2,000 LB).' || uomMeasure == 'Cubic Yard'}">
        <c:set var="hardscapeConsumablePriceVariationProduct" value="true"> </c:set>
    </c:if>
</c:if>
<c:if test="${entry.product.level1Category == 'Materiales duros & Vida al Aire Libre' || (entry.product.level1Category == 'Material de Jardinería' && entry.product.level2Category == 'Consumibles')}">
    <c:if test="${uomMeasure == 'Net Ton (2,000 LB).' || uomMeasure == 'Cubic Yard'}">
        <c:set var="hardscapeConsumablePriceVariationProduct" value="true"> </c:set>
    </c:if>
</c:if>

<input type="hidden" name="hardscapeConsumablePriceVariationProduct" value="${hardscapeConsumablePriceVariationProduct}"/>
<input type="hidden" name="landscape-es" value="${entry.product.level1Category == 'Material de Jardinería'}"/>
<input type="hidden" name="landscape-fn" value="${fn:escapeXml(entry.product.level1Category)== 'Material de Jardinería'}"/>
<c:set var="weighAndPay" value="${entry.product.weighAndPayEnabled ne null && not empty entry.product.weighAndPayEnabled ? entry.product.weighAndPayEnabled : false}" />
<c:set var="isSplitPickupOnly" value="false"> </c:set>
<c:if test="${entry.product.isDeliverable eq false && isSplitCart}">
    <c:set var="isSplitPickupOnly" value="true"> </c:set>
</c:if>
<div class="${isSplitPickupOnly ?' splitPickupOnlyBorder ':''} ${entry.product.isDeliverable eq false ?'orange-border mb-marginTop40':''} ${hardscapeConsumablePriceVariationProduct ?' hardscape-highlight-border ':''} row ${nontransferableFlag eq true ?'border-red-transferflag':''} cart-items-left-pane ${nonsaleble eq true ? 'border-color-orange':''} js-cart-items-row ${!entry.product.isShippable?'shippable ':''} ${maxQtyRedBorder} ${guestRedBorder} ${redBorder} js-cart-outofstock ${entry.product.outOfStockImage?'outofstock border-color-orange':''} print-p-l-0 print-p-r-0" data-cartentry="${entry.product.outOfStockImage?-1 : (entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true)? -2 : allFulfillmentEnabled? -3 : entry.entryNumber}" data-isDeliverable="${entry.product.isDeliverable}" data-isShippable="${entry.product.isShippable}" data-stockAvailableOnlyHubStore="${entry.product.stockAvailableOnlyHubStore}" data-nontransferableFlag="${nontransferableFlag}" data-nonsaleble="${nonsaleble}" data-outOfStockImage="${entry.product.outOfStockImage}" data-isTransferrable="${entry.product.isTransferrable}" data-isStockInNearbyBranch="${entry.product.isStockInNearbyBranch}" data-allFulfillmentEnabled="${allFulfillmentEnabled}" data-pickupenabled="${(entry.product.outOfStockImage || entry.product.stockAvailableOnlyHubStore || (entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true))?false: true}" data-deliveryenabled="${(entry.product.isEligibleForDelivery && entry.product.isDeliverable && !entry.product.stockAvailableOnlyHubStore && (!(entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true)))?true: false}" data-shippingenabled="${((entry.product.isShippable || entry.product.stockAvailableOnlyHubStore) && (!(entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true)))?true: false}" data-index="${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" data-code="${entry.product.code}" data-loopIndex="${loopIndex}" data-isPriceAvailable="${isPriceAvailable}" data-consumedEntry="${consumedEntry}" data-uomDescription="${uomDescription}" data-uomMeasure="${uomMeasure}" data-unitpriceDigits="${unitpriceDigits}" data-totalPrice="${totalPrice}" data-hideCSP="${hideCSP}" data-hideList="${hideList}" data-inventoryFlag="${inventoryFlag}" data-isAnonymous="${isAnonymous}" data-quantityBoxDisable="${quantityBoxDisable}" data-cartUpdateFormAction="${cartUpdateFormAction}" data-requestedQty="${requestedQty}" data-homeStoreQty="${homeStoreQty}" data-nearbyStoresQty="${nearbyStoresQty}" data-cartTotalPrice="${cartData.totalPrice}" data-isEligibleForBackorder="${entry.product.isEligibleForBackorder}" data-hubStoreNumber="${hubStoreNumber}" data-isForceInStock="${entry.product.isForceInStock}" data-inStockImage="${entry.product.inStockImage}" data-productType="${entry.product.productType}" data-level1Category="${fn:escapeXml(entry.product.level1Category)}" data-level2Category="${fn:escapeXml(entry.product.level2Category)}" data-itemnumber="${entry.product.itemNumber}" data-islevel1Category="${fn:escapeXml(entry.product.level1Category) == 'Hardscapes & Outdoor Living'}" data-islevel2Category="${fn:escapeXml(entry.product.level2Category) == 'Natural Stone'}">	
	<c:if test="${entry.product.isRegulatoryLicenseCheckFailed eq false}">
	 <div class="col-xs-12 marginBottom20 hidden-print">
                            <div class="flex-center cart-availability-nearby-msg hidden-print">
                                <common:exclamatoryIcon iconColor="#ef8700" width="24" height="24" />
                                <span class="pad-lft-10"><spring:theme code="cart.restricted.msg"/></span>
                            </div>
                         </div>
	</c:if>
	<div class="cl"></div>
	
	<!-- RUPTraining Message -->
	<c:if test="${entry.product.isRUPTrainingSku eq true}">
	 <div class="col-xs-12 marginBottom20 hidden-print">
                            <div class="flex-center cart-availability-nearby-msg">
                                <common:exclamatoryIcon iconColor="#ef8700" width="24" height="24" />
                                <span class="pad-lft-10"><spring:theme code="training.certification.msg"/></span>
                            </div>
                         </div>
	</c:if>
	<div class="cl"></div>
	
	<!-- Availability Messages -->
	
	<c:if test="${entry.product.isDeliverable eq false}">
	<div class="hidden-md hidden-lg font-GeogrotesqueSemiBold mobile-pickup-text hidden-print" data-analyticerror="Available for pick-up only"><spring:theme code="cart.pickuponly.text"/></div>
	</c:if>
    <c:if test="${entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true}">
	    <div class="col-xs-12 marginBottom20 p-5-xs margin-bot-10-xs hidden-print">
		     <div class="gc-error-msg transferflag-msg">
			       <spring:theme code="carttrasferable.msg2"/> 
		     </div>
	     </div>
     </c:if>
      <c:if test="${isProductSellable eq false}">
          <div class="flex-center gc-error-msg marginBottom20" id="cartRegulatoryMessage_${loop.index}">
              <span class="pad-lft-10"><spring:theme text="${cartRegulatoryItemNotSellableInStateMsg}" arguments="${sessionStore.address.phone}" htmlEscape="false" /></span>
         </div>
       </c:if>
        <c:set var="showExpectDelayHardscapes" value="false" />
        <c:if test="${((entry.product.level1Category eq 'Materiales duros & Vida al Aire Libre' || entry.product.level1Category eq 'Hardscapes & Outdoor Living') && (entry.quantity gt entry.product.homeStoreAvailableQty) && (entry.product.isForceInStock eq true))}">
            <c:set var="showExpectDelayHardscapes" value="true" />
        </c:if>
        <input type="hidden" class="showExpectDelayHardscapes" value="${showExpectDelayHardscapes}" />
        <c:set var="hideExpectDelayMessage" value="false" />
        <c:if test="${((entry.product.level1Category eq 'Materiales duros & Vida al Aire Libre' || entry.product.level1Category eq 'Hardscapes & Outdoor Living') && (entry.quantity gt entry.product.homeStoreAvailableQty) && (entry.product.isForceInStock eq false) && (entry.product.isTransferrable eq false))}">
            <c:set var="hideExpectDelayMessage" value="true" />
        </c:if>
        <input type="hidden" class="hideExpectDelayMessage" value="${hideExpectDelayMessage}" />
        <!-- Backorder Messages -->
        <c:if test="${entry.product.inStockImage and !entry.product.isForceInStock and !entry.product.isStockInNearbyBranch and entry.product.productType ne 'Nursery' and hideExpectDelayMessage eq 'false'}">
            <c:choose>
                <c:when test="${(requestedQty>homeStoreQty+nearbyStoresQty)}">
                    <!-- !isStockInNearbyBranch  and requestedQty>homeStoreQty+nearbyStoresQty -->
                    <div class="col-xs-12 marginBottom20 js-cart-availability-non-shippable hidden hidden-print" data-is-user-not-anonymous="${guestUsers eq 'guest'or !isAnonymous}" data-requested-qty="${requestedQty}" data-nearby-stores-qty="${homeStoreQty+nearbyStoresQty}">
                        <div class="flex-center cart-availability-backorder-msg">
                            <common:exclamatoryIcon iconColor="#ed8606" width="24" height="24" />
                            <span class="pad-lft-10">
                                <spring:theme code="Nearby.quantity.count.short.alert" arguments="${homeStoreQty};${nearbyStoresQty};${requestedQty - homeStoreQty - nearbyStoresQty}" htmlEscape="false" argumentSeparator=";" />
                            </span>
                        </div>
                    </div>
                </c:when>
                <c:when test="${homeStoreQty>0 and (requestedQty>homeStoreQty) and (requestedQty<=homeStoreQty+nearbyStoresQty)}">
                    <!-- !isStockInNearbyBranch  and requestedQty>homeStoreQty -->
                    <div class="col-xs-12 marginBottom20 hidden-print">
                        <div class="flex-center cart-availability-backorder-msg">
                            <common:exclamatoryIcon iconColor="#ed8606" width="24" height="24" />
                            <span class="pad-lft-10">
                                <spring:theme code="Nearby.quantity.count.exceed.alert" arguments="${homeStoreQty};${requestedQty - homeStoreQty}" htmlEscape="false" argumentSeparator=";" />
                            </span>
                        </div>
                    </div>
                </c:when>
            </c:choose>
        </c:if>
        <c:if test="${entry.product.isStockInNearbyBranch and !entry.product.isForceInStock and hideExpectDelayMessage eq 'false'}">
            <input type="hidden" class="remainingQty${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${requestedQty - homeStoreQty - nearbyStoresQty}" />
            <input type="hidden" class="analytics_backourderValue${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" value="${entry.product.isEligibleForBackorder}"/>
            <c:choose>
                <c:when test="${(requestedQty>homeStoreQty+nearbyStoresQty) and (entry.product.productType ne 'Nursery')}">
                    <!-- isStockInNearbyBranch  and requestedQty>homeStoreQty+nearbyStoresQty -->
                    <div class="col-xs-12 marginBottom20 hidden-print">
                        <div class="flex-center cart-availability-backorder-msg">
                            <common:exclamatoryIcon iconColor="#ed8606" width="24" height="24" />
                            <span class="pad-lft-10">
                                <spring:theme code="Nearby.quantity.count.short.alert" arguments="${homeStoreQty};${nearbyStoresQty};${requestedQty - homeStoreQty - nearbyStoresQty}" htmlEscape="false" argumentSeparator=";"/>
                            </span>
                        </div>
                    </div>
                </c:when>
                <c:when test="${homeStoreQty>0 and (requestedQty>homeStoreQty) and (requestedQty<=homeStoreQty+nearbyStoresQty) and (entry.product.productType ne 'Nursery')}">
                    <!-- isStockInNearbyBranch  and requestedQty>homeStoreQty -->
                    <div class="col-xs-12 marginBottom20 hidden-print">
                        <div class="flex-center cart-availability-backorder-msg">
                            <common:exclamatoryIcon iconColor="#ed8606" width="24" height="24" />
                            <span class="pad-lft-10">
                                <spring:theme code="Nearby.quantity.count.exceed.alert" arguments="${homeStoreQty};${requestedQty - homeStoreQty}" htmlEscape="false" argumentSeparator=";"/>
                            </span>
                        </div>
                    </div>
                </c:when>
                <c:when test="${empty hubStoreNumber and nontransferableFlag eq false}">
                    <div class="col-xs-12 marginBottom20 hidden-print">
                        <div class="flex-center cart-availability-nearby-msg">
                            <common:exclamatoryIcon iconColor="#ef8700" width="24" height="24" />
                            <span class="pad-lft-10">
                                <spring:theme code="cart.nearby.message"/>
                            </span>
                        </div>
                    </div>
                </c:when>
            </c:choose>
        </c:if>
        <c:if test="${showExpectDelayHardscapes eq 'true'}">
            <!-- isForceInStock and requestedQty>homeStoreQty and hardscape -->
            <div class="col-xs-12 marginBottom20 js-cart-availability-hardscape hidden-print" data-is-user-not-anonymous="${guestUsers eq 'guest'or !isAnonymous}" data-requested-qty="${requestedQty}">
                <div class="flex-center cart-availability-backorder-msg">
                    <common:exclamatoryIcon iconColor="#ed8606" width="24" height="24" />
                    <span class="pad-lft-10">
                        <spring:theme code="Forcestock.quantity.count.exceed.alert" arguments="${homeStoreQty};${requestedQty - homeStoreQty}" htmlEscape="false" argumentSeparator=";" />
                    </span>
                </div>
            </div>
        </c:if>
        <!-- ./Backorder Messages -->
        <c:if test="${entry.product.isEligibleForBackorder && (empty hubStoreNumber)}">
            <div class="col-xs-12 marginBottom20 hidden-print">
                <div class="flex-center cart-availability-backorder-msg">
                    <common:exclamatoryIcon iconColor="#ed8606" width="24" height="24" />
                    
                    <span class="pad-lft-10"><spring:theme code="cart.backorder.message" arguments="${not empty entry.product.nearestBackorderableStore ? entry.product.nearestBackorderableStore.name : sessionStore.displayName}"/></span>
                    
                </div>
            </div>
        <c:set var="quantityBoxDisable" value="false" />
        </c:if>
        
        <c:if test="${entry.product.outOfStockImage}">
            <div class="col-xs-12 marginBottom20 p-5-xs margin-bot-10-xs js-parcelShippingFulfillment-oos hidden-print ${empty hubStoreNumber?'hidden':''}">
             <div class="flex-center cart-availability-out-of-stock-msg">
                 <common:crossMarkIcon iconColor="#5a5b5d" width="24" height="24" />
                 <span class="pad-lft-10"><spring:theme code="cart.outofstock.message" arguments="${sessionStore.address.phone}"/></span>
             </div>
             </div>
        </c:if>

<!-- Availability Messages Ends -->
<div class="col-xs-12 p-5-xs print-p-l-0 print-p-r-0">
	<div class="row flex-md print-d-f print-a-i-c">
	<!-- Product Image Starts -->
		<div class="col-xs-3 col-md-2 no-padding-rgt-xs">
		
		<!-- Clip Coupon Start -->
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
              <!-- Clip Coupon End -->
			<div class="cart-img">
	            <a href="${productUrl}">
	                <product:productPrimaryImage product="${entry.product}" format="product" />
	            </a>
	        </div>
		</div>
	<!-- Product Image Ends -->
	<!-- Product Description Starts -->
		<div class="col-xs-9 col-md-3 cart-product-description-container">
			<div class="cart-item-number">${fn:escapeXml(entry.product.itemNumber)}</div>
			<ycommerce:testId code="cart_product_name">
                  <div><a href="${productUrl}" class="black-title bold no-text-decoration js-cart-linktracking cart-item-title">${fn:escapeXml(entry.product.name)}</a></div>                          
            </ycommerce:testId>
			
			<c:if test="${entry.product.baseProduct != null}">
                            <c:forEach items="${entry.product.categories}" var="option">
                                <div class="hidden-print">${option.parentCategoryName}:${option.name}</div>
                            </c:forEach>
            </c:if>
            
            <div class="hidden-xs hidden-sm hidden-print">
            <cart:cartEntriesFulfilment entry="${entry}"/>
            </div>
			
		</div>
	<!-- Product Description Ends -->
<c:set var="productQuantity_cart" value="1"/> 
<c:if test="${entry.product.orderQuantityInterval eq null || entry.product.orderQuantityInterval eq '0'  || entry.product.orderQuantityInterval gt '0' || entry.product.orderQuantityInterval eq ''}">
        <c:set var="productQuantity_cart" value="1"/> 
</c:if>
<c:if test="${entry.product.orderQuantityInterval ne null && entry.product.orderQuantityInterval ne '0' && entry.product.orderQuantityInterval gt '0' && entry.product.orderQuantityInterval ne ''}">
        <c:set var="productQuantity_cart" value="${entry.product.orderQuantityInterval}"/> 
</c:if>
	<!-- Product Price Starts -->
		<div class="col-xs-12 col-md-2 m-t-15-xs cart-price-container">
			<div class="row cart-price-row">
            <c:if test="${!isSplitCart}">
                <div class="col-xs-3 no-padding-rgt-xs  hidden-md hidden-lg hidden-print">
                    <cart:cartEntriesFulfilment entry="${entry}"/>
                </div>
            </c:if>
                <c:choose>
                    <c:when test="${cartPriceLoader}">
                        <ul class="margin0 padding0 text-center hidden-sm hidden-xs cart-items-left-loader" data-priceShowIndex="${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" data-priceShowCode="${entry.product.code}">
                            <li class="loader-circles"></li>
                            <li class="loader-circles delay-1s"></li>
                            <li class="loader-circles delay-2s"></li>
                        </ul>
                        <div class="hidden cart-items-left-price-show">
                            <div class="col-md-12 retail-your-price-section ${isSplitCart ? 'col-xs-6' : 'col-xs-5' }">
                                <p class="cart-price-text hidden-print"><spring:theme code="cartItems.yourPrice"/></p>
                                <span class="black-title b-price add_price"><span class="black-title b-price add_price atc-price-analytics"></span>/ ${uomMeasure}</span>
							</div>  
                        </div>
                        <!-- Mobile Only -->
                        <ul class="margin0 padding0 text-center hidden-lg hidden-md cart-items-left-loader" data-priceShowIndex="${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" data-priceShowCode="${entry.product.code}">
                            <li class="loader-circles"></li>
                            <li class="loader-circles delay-1s"></li>
                            <li class="loader-circles delay-2s"></li>
                        </ul>
                        <div class="text-right xs-cart-item-total no-padding-lft-xs ${isSplitCart ? 'col-xs-6' : 'col-xs-6'} hidden-lg hidden-md hidden cart-items-left-total-show">
                            <div class="js-item-total text-right no-margin f-s-18 text-default atc-price-analytics"></div>                                          
                        </div>
                    </c:when>
                    <c:otherwise>
                        <cart:cartEntriesPrice entry="${entry}" loopIndex="${loopIndex}" isPriceAvailable="${isPriceAvailable}" consumedEntry="${consumedEntry}" uomDescription="${uomDescription}" uomMeasure="${uomMeasure}" unitpriceDigits="${unitpriceDigits}" totalPrice="${totalPrice}" hideCSP="${hideCSP}" hideList="${hideList}" inventoryFlag="${inventoryFlag}" isAnonymous="${isAnonymous}" quantityBoxDisable="${quantityBoxDisable}" cartUpdateFormAction="${cartUpdateFormAction}" requestedQty="${requestedQty}" homeStoreQty="${homeStoreQty}" nearbyStoresQty="${nearbyStoresQty}" />
                    </c:otherwise>
                </c:choose>
                <c:if test="${entry.product.isEligibleForBackorder}">
                    <c:set var="quantityBoxDisable" value="false" />
                    <input type= "hidden" id="quantityBoxDisable_${entry.product.itemNumber}" value="${quantityBoxDisable}" />
                </c:if>
                <c:if test="${isSplitCart}">
                    <div class="col-xs-11 hidden-md hidden-lg hidden-print">
                    <cart:cartEntriesFulfilment entry="${entry}"/>
                    </div>
                </c:if>
                <div class="m-t-15-xs hidden-md hidden-lg cart-qty-container ${isSplitCart ? 'col-xs-12' : 'col-xs-9'} ${productQuantity_cart ne '1' ?'UOMqtyinterqtypart':''}">
                    <div class="row">			
                        <c:url value="/cart/update" var="cartUpdateFormAction" />
                        <form:form id="updateCartForm${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" action="${cartUpdateFormAction}" method="post" modelAttribute="updateQuantityForm${entry.entryNumber}" class="js-qty-form${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" data-cart='{"cartCode" : "${cartData.code}","productPostPrice":"${entry.basePrice.value}","productName":"${fn:escapeXml(entry.product.name)}"}'>
                            <input type="hidden" name="entryNumber" value="${entry.entryNumber}" />
                            <input type="hidden" name="productCode" value="${entry.product.code}" />
                            <input type="hidden" name="initialQuantity" value="${entry.quantity}" />
                            <input type="hidden" name="productName" value="${fn:escapeXml(entry.product.name)}" />
                            <input type="hidden" name="productCategory" value="${entry.product.level1Category}" />
                            <input type="hidden" name="productSubCategory" value="${entry.product.level2Category}" />
                            <input type="hidden" name="productnearbyItem" value="${entry.product.isStockInNearbyBranch}" />
                            <input type="hidden" name="productpriceItem" value="${entry.basePrice.value}" />
                            <input type="hidden" name="productstockStatusItem" value="${fn:escapeXml(entry.product.storeStockAvailabilityMsg)}" />
                            <input type="hidden" name="transferableFlagItem" value="${entry.product.isTransferrable}" />
                            <input type="hidden" name="isStockInNearbyBranchItem" value="${entry.product.isStockInNearbyBranch}" /> 
                            <input type="hidden" name="isstockNearbyFlagItem" value="${entry.product.isStockInNearbyBranch}"/>
                            <input type="hidden" name="remainingQtyItem" value="${requestedQty - homeStoreQty - nearbyStoresQty}" />
                            <input type="hidden" name="analytics_backourderValueItem" value="${entry.product.isEligibleForBackorder}"/>
                            <input type="hidden" name="uomintervalqtyCart" id="uomintervalqtyCart_${entry.product.code}" value="${productQuantity_cart}"/>
                            <input type="hidden" name="uommultiplierCart" id="uommultiplierCart_${entry.product.code}" value="${entry.uomMultiplier}"/>
                            <input type="hidden" name="minQtyCart" id="minQtyCart_${entry.product.code}" value="${entry.product.minOrderQuantity}"/>
                            <div class="cl"></div>
                            <div class="col-md-12">
                                    <div class="intervalQtyError_cart hidden print-hidden"> 
                                        <spring:theme code="text.valid.quantity"/>
                                    </div>
                            </div>
                            <input type="hidden" class="QtyintervalProductCode" value="${entry.product.code}" />
                            <div class="col-xs-12">
                                <ycommerce:testId code="cart_product_quantity">                                                 
                                    <button class="js-update-entry-quantity-btn minusQty cart-qty-btn hidden-print" type="button" ${quantityBoxDisable?'disabled':''} id="cartminusQty_${entry.product.itemNumber}">-</button>
                                    <c:choose>
                                        <c:when test="${hideList eq true && hideCSP eq true}">
                                            <form:input cssClass="cartQtyBox cart-qty-input form-control js-update-entry-quantity-input txtalign print-input js-qty-updateOne" disabled="${quantityBoxDisable}" type="text" maxlength="5" size="1" id="quantity_${entry.product.itemNumber}" path="quantity" value="0"/>
                                        </c:when>
                                        <c:otherwise>
                                            <form:input cssClass="cartQtyBox  cart-qty-input form-control js-update-entry-quantity-input txtalign print-input js-qty-updateOne" disabled="${quantityBoxDisable}" type="text" maxlength="5" size="1" id="quantity_${entry.product.itemNumber}" path="quantity" />
                                        </c:otherwise>
                                    </c:choose>
                                    <button class="js-update-entry-quantity-btn plusQty cart-qty-btn hidden-print" type="button" ${quantityBoxDisable?'disabled':''} id="cartplusQty_${entry.product.itemNumber}">+</button>
                                        <c:if test="${quantityBoxDisable}">
                                            <form:input type="hidden" path="quantity" value="0" />
                                        </c:if>
                                </ycommerce:testId>
                            </div>
                            <div class="row update_qty_cart hidden">Quantity Updated</div>
                             <c:if test="${productQuantity_cart ne '1' && entry.uomMultiplier <= 1}">
                                <div class="row intervalQtyInfo_cart print-hidden"> 
                                <span class="info-img_list"><common:info-circle iconColor="#78a22f"/></span><spring:theme code="text.minimum.value"/>&nbsp;${entry.product.orderQuantityInterval}
                                </div>
                            </c:if>
                            <c:if test="${entry.product.minOrderQuantity > '1' && entry.uomMultiplier <= 1}">
                                <div class="row intervalQtyInfo_cart print-hidden"> 
                                <span class="info-img_list"><common:info-circle iconColor="#78a22f"/></span><spring:theme code="text.minimum.info"/>&nbsp;${entry.product.minOrderQuantity}
                                </div>
                            </c:if>
                            <div class="col-xs-12 flex-center remove-list-container ${isSplitCart ? 'split-remove-list-container' :''}">
                                <ycommerce:testId code="cart_product_removeProduct">
                                    <div class="cart-padding js-remove-entry-button cart-remove-link print-hidden" id="removeEntry_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}">
                                        <a href="#"><spring:theme code="basket.page.remove" /></a>
                                    </div>
                                </ycommerce:testId>
                                <input type="hidden" class="moveTolistCart" name="moveTolistCart" value="true" />
                                <div class="cart-padding print-hidden" id="moveEntry_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}">
                                    <a class="wishlistAddProLink signInOverlay" id="moveEntry_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" data-role="${entry.product.itemNumber}|${entry.quantity}|${entry.product.code}" data-uomValue="${entry.uomId}"><spring:theme code="saveListDetailsPage.add.to.list" /></a>
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
                <!-- mobile only ends -->
            </div>
		</div>
		<!-- Product Price Ends -->
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
                <input type="hidden" name="productnearbyItem" value="${entry.product.isStockInNearbyBranch}" />
				<input type="hidden" name="productpriceItem" value="${entry.basePrice.value}" />
				<input type="hidden" name="productstockStatusItem" value="${fn:escapeXml(entry.product.storeStockAvailabilityMsg)}" />
				<input type="hidden" name="transferableFlagItem" value="${entry.product.isTransferrable}" />
				<input type="hidden" name="isStockInNearbyBranchItem" value="${entry.product.isStockInNearbyBranch}" /> 
				<input type="hidden" name="isstockNearbyFlagItem" value="${entry.product.isStockInNearbyBranch}"/>
			    <input type="hidden" name="remainingQtyItem" value="${requestedQty - homeStoreQty - nearbyStoresQty}" />
    			<input type="hidden" name="analytics_backourderValueItem" value="${entry.product.isEligibleForBackorder}"/>
                <input type="hidden" name="uomintervalqtyCart" id="uomintervalqtyCart_${entry.product.code}" value="${productQuantity_cart}"/>
                <input type="hidden" name="uommultiplierCart" id="uommultiplierCart_${entry.product.code}" value="${entry.uomMultiplier}"/>
                <input type="hidden" name="minQtyCart" id="minQtyCart_${entry.product.code}" value="${entry.product.minOrderQuantity}"/>
                <div class="cl"></div>
                <div class="col-md-12">
						<div class="intervalQtyError_cart hidden print-hidden"> 
							<spring:theme code="text.valid.quantity"/>
						</div>
                </div>
                <input type="hidden" class="QtyintervalProductCode" value="${entry.product.code}" />
                 <div class="col-xs-12">
                <ycommerce:testId code="cart_product_quantity">                                                 
										<button class="js-update-entry-quantity-btn minusQty cart-qty-btn hidden-print" type="button" id="cartminusQty_${entry.product.itemNumber}_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" ${quantityBoxDisable ? 'disabled' : '' }>-</button>
                                           <c:choose>
								            <c:when test="${hideList eq true && hideCSP eq true}">
								               <form:input cssClass="cartQtyBox cart-qty-input form-control js-update-entry-quantity-input txtalign print-input js-qty-updateOne" disabled="${quantityBoxDisable}" type="text" maxlength="5" size="1" id="quantity_${entry.product.itemNumber}_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" path="quantity" value="0"/>
							                  </c:when>
								           <c:otherwise>
								               <form:input cssClass="cartQtyBox  cart-qty-input form-control js-update-entry-quantity-input txtalign print-input js-qty-updateOne" disabled="${quantityBoxDisable}" type="text" maxlength="5" size="1" id="quantity_${entry.product.itemNumber}_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" path="quantity" />
                        					</c:otherwise>
								          </c:choose>
										<button class="js-update-entry-quantity-btn plusQty cart-qty-btn hidden-print" type="button" id="cartplusQty_${entry.product.itemNumber}_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" ${quantityBoxDisable ? 'disabled' : '' }>+</button>

                                            <c:if test="${quantityBoxDisable}">
                                                <form:input type="hidden" path="quantity" value="0" />
                                            </c:if>
                                        </ycommerce:testId>
                                        </div>
                                        <div class="row update_qty_cart hidden">Quantity Updated</div>
                                        <c:if test="${productQuantity_cart ne '1' && entry.uomMultiplier <= 1}">
                                            <div class="row intervalQtyInfo_cart print-hidden"> 
                                            <span class="info-img_list"><common:info-circle iconColor="#78a22f"/></span><spring:theme code="text.minimum.value"/>&nbsp;${entry.product.orderQuantityInterval}
                                            </div>
                                        </c:if>
                                        <c:if test="${entry.product.minOrderQuantity > '1' && entry.uomMultiplier <= 1}">
                                           <div class="row intervalQtyInfo_cart print-hidden"> 
                                           <span class="info-img_list"><common:info-circle iconColor="#78a22f"/></span><spring:theme code="text.minimum.info"/>&nbsp;${entry.product.minOrderQuantity}
                                           </div>
                                        </c:if>
                                        <div class="col-xs-12 flex-center remove-list-container">
                                        <ycommerce:testId code="cart_product_removeProduct">
                                            <div class="cart-padding js-remove-entry-button cart-remove-link print-hidden" id="removeEntry_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}">
                                                <a href="#">
                                                    <spring:theme code="basket.page.remove" />
                                                </a>
                                            </div>
                                        </ycommerce:testId>

                                        <input type="hidden" class="moveTolistCart" name="moveTolistCart" value="true" />
                                        <div class="cart-padding print-hidden" id="moveEntry_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}">
                                            <a class="wishlistAddProLink signInOverlay" id="moveEntry_${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" data-role="${entry.product.itemNumber}|${entry.quantity}|${entry.product.code}"  data-uomValue="${entry.uomId}"><spring:theme code="saveListDetailsPage.add.to.list" /></a>
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
		<!-- Product Qty Starts -->
                                                         
                       
		<!-- Total Starts -->
		<div class="col-xs-12 col-md-2 hidden-xs hidden-sm cart-total-left-pane">
            <c:choose>
                <c:when test="${cartPriceLoader}">
                    <ul class="margin0 padding0 text-center cart-items-left-loader" data-priceShowIndex="${(empty loopIndex) ? loop.index : (loopIndex + loop.index)}" data-priceShowCode="${entry.product.code}">
                        <li class="loader-circles"></li>
                        <li class="loader-circles delay-1s"></li>
                        <li class="loader-circles delay-2s"></li>
                    </ul>
                    <div class="js-item-total text-right no-margin h3 hidden cart-items-left-total-show">
                        <span class="black-title b-price add_price atc-price-analytics"></span>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="js-item-total text-right no-margin h3">
                        <c:if test="${isPriceAvailable}">
                            <ycommerce:testId code="cart_totalProductPrice_label">
                                <c:choose>
                                    <c:when test="${hideCSP eq true}">$0.00</c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${inventoryFlag eq true}">$0.00</c:when>
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
                            </ycommerce:testId>
                        </c:if>
                    </div>
                </c:otherwise>
            </c:choose>
		</div>
		<!-- Total Ends -->
			</div>
				
				 
				  <c:forEach items="${entry.product.potentialPromotions}" var="promotion" varStatus="loop">
					 <c:if test="${loop.last}"> 
					 
				 <c:choose>
				<c:when test="${not empty entry.product.couponCode}">
						<div class="cl"></div>
						<div class="promotion col-md-12 col-xs-12 print-hidden">
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
		
		<c:set var="isChecked" value="${entry.bigBagInfo.isChecked ne null && not empty entry.bigBagInfo.isChecked ? entry.bigBagInfo.isChecked : false}" />
		<c:if test="${isBulkBigBag eq true && weighAndPay eq true}">
		<div class="col-md-8 p-l-20 cart-bigbag hidden-xs">
		<span class="f-s-14 f-w-400"><spring:theme code="cart.bulkcheck.title" /></span>
			<div class="col-md-8 padding0 user-label f-s-16 f-w-500 m-t-10 font-Geogrotesque"> 		
			<label class="control-label" for="Big Bag">
						<input name="cartBigbag" class="green-check bigbag-check bigbagcheck-${entry.product.code}" ${isChecked eq true ? "checked":""} data-skuId="${entry.product.code}" data-quantity="${entry.quantity}" data-uom="${uomMeasure}" data-bigbagprice="${entry.bigBagInfo.unitPrice}"  type="checkbox">
							<div class="team-roleLabel bulk-label"><spring:theme code="cart.bulkcheck.text" /> (+$${entry.bigBagInfo.unitPrice} / per bag)
							<span class="p-l-10 p-t-2"><a href="javascript:void(0);" class="bigbag-selection-help"><common:info-circle-nofill-new width="20" height="20" /></a></span>
							</div>
			 </label>
		
			</div>
			<div class="col-md-offset-4"></div>
		</div>
		<div class="col-md-4 cart-bigbag bigbag-price-box hidden-xs">
			<c:if test="${isChecked eq true}">
			<div class="col-md-6 f-s-12 f-w-400 bigbag-price-label"><spring:theme code="cart.bulkcheck.bigBag.title" /> (${entry.bigBagInfo.numberOfBags}):</div>
			<div class="col-md-6 f-s-12 f-w-400 bigbag-price-label text-align-right">$${entry.bigBagInfo.totalPrice}</div>
			<div class="col-md-12 f-s-10 f-w-400 p-t-10 bigbag-price-text">
			<spring:theme code="cart.bulkcheck.bigBag.text" />
			</div>
			</c:if>
		</div>
		<!-- For Mobile -->
		<div class="col-xs-12 cart-bigbag hidden-md hidden-lg">
			<div class="row p-b-10-xs">
				<div class="col-xs-9 p-l-0-xs f-s-12-xs-px f-w-400"><spring:theme code="cart.bulkcheck.title" /></div>
				<c:if test="${isChecked eq true}">
				<div class="col-xs-3 f-s-10-xs-px p-l-0-xs p-r-0-xs f-w-400 text-align-right"><spring:theme code="cart.bulkcheck.bigBag.title" /> (${entry.bigBagInfo.numberOfBags})
				<span class="f-w-600 f-s-12-xs-px">$${entry.bigBagInfo.totalPrice}</span>
				</div>
				</c:if>
			</div>
			<div class="row m-b-20-xs">
				<div class="col-xs-10 p-l-0-xs f-w-500 f-s-12-xs-px flex-center bigbag-checkbox">
				<input name="cartBigbag" class="green-check bigbag-check mobbigbagcheck-${entry.product.code}" ${isChecked eq true ? "checked":""} data-skuId="${entry.product.code}" data-quantity="${entry.quantity}" data-uom="${uomMeasure}" data-bigbagprice="${entry.bigBagInfo.unitPrice}"  type="checkbox">
				<spring:theme code="cart.bulkcheck.text" /> (+${entry.bigBagInfo.unitPrice} / per bag)
				<span class="p-l-10"><a href="javascript:void(0);" class="bigbag-selection-help"><common:info-circle-nofill-new width="20" height="20" /></a></span>
				</div>
				<div class="col-xs-2 p-t-2">
				<c:if test="${isChecked eq true}">
				<a href="javascript:void(0);" class="bigbag-help"><common:help-icon /></a>
				</c:if>
				</div>
			</div>
		</div>
		<!-- For Mobile -->
		<div class="bigbag-help-mob hidden">
		<div class="row text-center bibag-help-title">
		<div class="col-xs-12 f-s-24 f-w-500 font-Geogrotesque"><spring:theme code="cart.bulkcheck.bigBag.help.title" /></div>
		</div>
		<div class="row text-center bibag-help-contnt p-t-10-xs p-b-10-xs">
		<div class="col-xs-12 f-w-400"><spring:theme code="cart.bulkcheck.bigBag.text" /></div>
		</div>
		<div class="row text-center">
		<div class="col-xs-12 p-l-0-xs p-r-0-xs"><button class="btn btn-primary btn-block bigbag-gotit">Got It</button>
		</div>	
		</div>
		</div>
		
		<!-- Help Overlay -->
		<div class="bigbag-help-web hidden">
		<div class="row text-center bibag-help-title">
		<div class="col-md-12 col-xs-12 f-s-24 f-w-600 font-Geogrotesque"><spring:theme code="cart.bigbag.help.title" /></div>
		<div class="col-md-12 col-xs-12 p-t-20 p-b-20 header-subtitle f-s-14 f-w-500 font-Geogrotesque p-r-0"><spring:theme code="cart.bigbag.help.text" /></div>
		</div>
		<div class="row m-b-20">
		<div class="col-md-12 col-xs-12 item-bg m-b-10">
		<div class="col-md-4 col-xs-4 p-t-10 p-b-10 p-l-0"><img class="overlay-bigbag" src="${commonResourcePath}/images/bigbag.png" alt=""></img></div>
		<div class="col-md-8 col-xs-8 p-t-10 p-b-10 p-r-0 p-r-0-xs"><span class="f-w-600 f-s-18 font-Geogrotesque item-cnt-title"><spring:theme code="cart.bigbag.help.row1.title" /></span><br/><span class="f-w-400 item-cnt-font"><spring:theme code="cart.bigbag.help.row1.text" /></span></div>
		</div>
		<div class="col-md-12 col-xs-12 item-bg">
		<div class="col-md-4 col-xs-4 p-t-10 p-b-10 p-l-0"><img class="overlay-truck" src="${commonResourcePath}/images/truck.jpg" alt=""></img></div>
		<div class="col-md-8 col-xs-8 p-t-10 p-b-10 p-r-0 p-r-0-xs"><span class="f-w-600 f-s-18 font-Geogrotesque item-cnt-title"><spring:theme code="cart.bigbag.help.row2.title" /></span><br/><span class="f-w-400 item-cnt-font"><spring:theme code="cart.bigbag.help.row2.text" /></span></div>
		</div>
		</div>
		<div class="row text-center">
		<div class="col-md-3 col-xs-3"></div>
		<div class="col-md-6 col-xs-6"><button class="btn btn-primary btn-block bigbag-gotit"><spring:theme code="cart.bigbag.help.button" /></button>
		<div class="col-md-3 col-xs-3"></div>
		</div>	
		</div>
		</div>
		</c:if>
	</div>
	
	
</c:forEach>
