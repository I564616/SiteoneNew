<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.CartData" %>
<%@ attribute name="showTax" required="false" type="java.lang.Boolean" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="quote" tagdir="/WEB-INF/tags/responsive/quote" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:forEach items="${cartData.entries}" var="entry" varStatus="loop">
	<c:if test="${entry.product.hideList eq true}">
		<c:set var="hideList" value="${entry.product.hideList}"/>
	</c:if>
</c:forEach>
<c:set var="isSplitMixedPickupBranchReview" value="false" />
	<c:if test="${not empty isSplitMixedCartEnabledBranch && isSplitMixedCartEnabledBranch ne null}" >
		<c:set var="isSplitMixedPickupBranchCheckout" value="${isSplitMixedCartEnabledBranch}" />
	</c:if>
	<c:if test="${not empty cartData.isShippingHubOnly && cartData.isShippingHubOnly ne null}" >
		<c:set var="isSplitCartShippingHubOnly" value="${cartData.isShippingHubOnly}"/>
	</c:if>
	<c:if test="${isSplitMixedPickupBranchCheckout && isSplitCartShippingHubOnly && (cartData.orderType eq 'PICKUP' || cartData.orderType eq 'DELIVERY')}" >
		<c:set var="isSplitMixedPickupBranchReview" value="true"/>
	</c:if>
	<c:set var="hasPickup" value="false" />
	<c:set var="hasDelivery" value="false" />
	<c:set var="hasShipping" value="false" />
	
 	<c:forEach items="${cartData.deliveryModeAndBranchOrderGroups}" var="groupData" varStatus="status">	
 	<c:if test="${groupData.deliveryMode.code eq 'pickup'}">	
 		<c:set var="hasPickup" value="true" />
 		
 	</c:if>
 	<c:if test="${groupData.deliveryMode.code eq 'standard-net'}">	
 		<c:set var="hasDelivery" value="true" />
 		
 	</c:if>
 	<c:if test="${groupData.deliveryMode.code eq 'free-standard-shipping'}">	
 		<c:set var="hasShipping" value="true" />
 		
 	</c:if>
 	
 </c:forEach>
<div class="col-xs-12 col-sm-12 col-md-12 padding0">
<c:if test="${cmsPage.uid eq 'orderSummaryPage'}">
<div class="secondary-title payment-box font-space-title">
						<b> <spring:theme code="order.order.totals"/> </b>
			</div>
			</c:if>
 <c:if test="${cartData.totalDiscounts.value > 0}">
 <div class="col-xs-12 marginBottom10  cartPage-totalWidth-page">
        <div class="col-xs-6 cart-totals-left mb-padding cartdiscount-price cartPage-totalWidth cartPage-totalWidth-page"><spring:theme code="basket.page.totals.discounts"/></div>
        <div class="col-xs-6 text-right cartTotalPrice-Mobile cartdiscount-price confirmation-subtotal-value text-right bold">
            <ycommerce:testId code="Order_Totals_Savings"><strong><format:price priceData="${cartData.totalDiscounts}" /></strong></ycommerce:testId>
        </div>
        </div>
    </c:if>
<c:choose>
	<c:when test="${cmsPage.uid eq 'orderSummaryPage'}">
	<div class="col-xs-12 marginBottom10 no-padding-rgt-md">
		<div class="js-cart-totals row cartPage-Totals"></div>
    	<div class="col-xs-6 cart-totals-left mb-padding cartPage-totalWidth"><spring:theme code="order.form.subtotal"/></div>
    	<div class="col-xs-6   text-right cartTotalPrice-Mobile confirmation-subtotal-value text-right bold"><ycommerce:testId code="Order_Totals_Subtotal"><strong><format:price priceData="${cartData.subTotal}" /></strong></ycommerce:testId></div>
	</div>
	</c:when>
	<c:otherwise>
	<div class="col-xs-12  marginBottom10 no-padding-rgt-md">
		<div class="js-cart-totals row cartPage-Totals"></div>
    	<div class="col-xs-6 cart-totals-left mb-padding black-title-cart cartPage-totalWidth"><spring:theme code="basket.page.totals.subtotal"/></div>
		<div class="col-xs-6   text-right cartTotalPrice-Mobile confirmation-subtotal-value text-right bold">
		<c:if test="${!isAnonymous || (isAnonymous && (hideList ne true))}">
			<ycommerce:testId code="Order_Totals_Subtotal"><strong><format:price priceData="${cartData.subTotal}" /></strong></ycommerce:testId>
		</c:if>
		</div></div>
	</c:otherwise>
</c:choose>

    <%--<c:if test="${not empty cartData.deliveryCost}">
    <div class="col-xs-12  marginBottom10 ">
        <div class="col-xs-6 cart-totals-left mb-padding cartPage-totalWidth"><spring:theme code="basket.page.totals.delivery"/></div>
        <div class="col-xs-6  text-right cartTotalPrice-Mobile confirmation-subtotal-value text-right bold"><strong><format:price priceData="${cartData.deliveryCost}" displayFreeForZero="TRUE" /></strong></div>
    </div>
     </c:if>--%>
<div class="col-xs-12 padding0 marginBottom10 hide">
				<div class="col-xs-6 padding0 confirmation-tax-title">Shipping</div>
				<div class="col-xs-6 padding0 confirmation-tax-value text-right bold"></div>
			</div>

<%--     <c:if test="${cartData.net && cartData.totalTax.value > 0 && showTax}"> --%>
	 <input type="hidden" class="has-Shipping" value="${hasShipping}"/>
     <input type="hidden" class="isMixedCart-Enabled" value="${isMixedCartEnabled}"/>
     <input type="hidden" class="isTampa-Branch" value="${cartData.isTampaBranch}"/>
     <input type="hidden" class="isLA-Branch" value="${cartData.isLABranch}"/>
     <input type="hidden" class="isShippingFee-Branch" value="${cartData.isShippingFeeBranch}"/>
     <c:if test="${cmsPage.uid eq 'orderSummaryPage'}">
            <c:if test="${((cartData.orderType eq 'DELIVERY' && isMixedCartEnabled ne true) && (not empty cartData.deliveryCost)) || (cartData.orderType eq 'DELIVERY' && cartData.orderingAccount.exemptDeliveryFee)}">
                <div class="col-xs-12  marginBottom10 no-padding-rgt-md">
                  <div class="col-xs-6 cart-totals-left mb-padding cartPage-totalWidth"><spring:theme code="order.form.delivery.cost" /></div>
                  <div class="col-xs-6   text-right cartTotalPrice-Mobile confirmation-subtotal-value text-right bold">
                  <c:choose>
                      <c:when test="${not empty cartData.deliveryCost.formattedValue}">
                      ${cartData.deliveryCost.formattedValue}
                      </c:when>
                      <c:otherwise>
                      $0.00
                      </c:otherwise>
                  </c:choose>
                   </div>
              </div>
              </c:if>
              <c:if test="${(hasDelivery && isMixedCartEnabled)  && (cartData.isTampaBranch || cartData.isLABranch || cartData.isShippingFeeBranch)}">
              <div class="col-xs-12  marginBottom10 no-padding-rgt-md">
                  <div class="col-xs-6 cart-totals-left mb-padding cartPage-totalWidth"><spring:theme code="order.form.delivery.cost" /></div>
                  <div class="col-xs-6   text-right cartTotalPrice-Mobile confirmation-subtotal-value text-right bold">
                  <c:choose>
                      <c:when test="${not empty cartData.deliveryFreight && cartData.deliveryFreight ne '0.00'}">
                  <strong>$<fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${cartData.deliveryFreight}"/> </strong>
                      </c:when>
                      <c:otherwise>
                      $0.00
                      </c:otherwise>
                  </c:choose>
                   </div>
              </div>
              </c:if>
     		<c:if test="${cartData.isNationalShipping eq false || cartData.isShippingFeeBranch || isSplitMixedPickupBranchReview}">
		      <c:if test="${((cartData.orderType eq 'PARCEL_SHIPPING' || isSplitMixedPickupBranchReview) && isMixedCartEnabled ne true) && (cartData.isTampaBranch || cartData.isLABranch || cartData.isShippingFeeBranch )}" >
		        <div class="col-xs-12  marginBottom10 no-padding-rgt-md">
                  <div class="col-xs-6 cart-totals-left mb-padding cartPage-totalWidth"><spring:theme code="order.form.shipping.cost" /></div>
                  <div class="col-xs-6   text-right cartTotalPrice-Mobile confirmation-subtotal-value text-right bold">
                  <input type="hidden" name="cartData_deliveryCost" value="${cartData.deliveryCost}"/>
                  <input  type="hidden"name="cartData_shippingFreight" value="${cartData.shippingFreight}"/>
                 <c:choose>
                    <c:when test="${isSplitMixedPickupBranchReview && cartData.orderType eq 'DELIVERY'}" > <%--  for splitcart and delivery--%>
                            <c:choose>
                                <c:when test="${empty cartData.shippingFreight || cartData.shippingFreight eq '0.0' || cartData.shippingFreight eq '0.00' }">
                                    <spring:theme code="text.shipping.free"/> <%--  for free--%>
                                </c:when> 
                                <c:otherwise>$${cartData.shippingFreight}</c:otherwise>  <%--  for value--%>
                            </c:choose>
                    </c:when> 
                        <c:otherwise>  <%--  for normalcart--%>
                            <c:choose>
                                <c:when test="${empty cartData.deliveryCost || cartData.deliveryCost.value eq '0.0' || cartData.deliveryCost.value eq '0.00' }">
                                    <spring:theme code="text.shipping.free"/>  <%--  for free--%>
                                </c:when> 
                                <c:otherwise>${cartData.deliveryCost.formattedValue}</c:otherwise>  <%--  for value--%>
                            </c:choose>
                    </c:otherwise>
                    </c:choose>
                  </div>
              </div>
              </c:if>
              <c:if test="${(hasShipping && isMixedCartEnabled) && (cartData.isTampaBranch || cartData.isLABranch || cartData.isShippingFeeBranch ) }">
                  <div class="col-xs-12  marginBottom10 no-padding-rgt-md">
                  <div class="col-xs-6 cart-totals-left mb-padding cartPage-totalWidth"><spring:theme code="order.form.shipping.cost" /></div>
                  <div class="col-xs-6   text-right cartTotalPrice-Mobile confirmation-subtotal-value text-right bold">
                  <c:choose>
                      <c:when test="${not empty cartData.shippingFreight && cartData.shippingFreight ne '0.00' && cartData.shippingFreight ne '0.0'}">
                                   $<fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${cartData.shippingFreight}"/> 
                      </c:when>
                      <c:otherwise>
						            <spring:theme code="text.shipping.free"/>
                      </c:otherwise>
                  </c:choose>
                  </div>
              </div>
              
              </c:if>
             </c:if>
             
              
              
          </c:if>
          <c:if test="${cmsPage.uid eq 'orderSummaryPage'}">
                <c:if test="${cartData.net && showTax}">
                <c:if test="${currentBaseStoreId eq 'siteoneCA' and not empty taxCA}">
                	<c:set var="unitpriceFormattedDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.unitprice.formattedDigits\")%>" />
                	<c:forEach items="${taxCA}" var="entry">
			            <div class="col-xs-12  marginBottom10 no-padding-rgt-md">
		                	<div class="col-xs-6 cart-totals-left mb-padding cartPage-totalWidth">${entry.key}</div>
		                  	<div class="col-xs-6 text-right cartTotalPrice-Mobile confirmation-subtotal-value text-right bold">
		                  		$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" minFractionDigits="2" maxFractionDigits="2"  value="${entry.value}" />
		                  	</div>
		              	</div>
			        </c:forEach>
                </c:if>
                <div class="col-xs-12  marginBottom10 no-padding-rgt-md">
                  <div class="col-xs-6 cart-totals-left mb-padding cartPage-totalWidth">
                    <spring:theme code="order.form.Sales" />
				  </div>
                  <div class="col-xs-6   text-right cartTotalPrice-Mobile confirmation-subtotal-value text-right bold"><format:price priceData="${cartData.totalTax}" /></div>
              </div>
              </c:if>
          </c:if>
          <c:if test="${not empty cartData.quoteData}">
              <quote:quoteDiscounts cartData="${cartData}"/>
          </c:if>

          <c:if test="${cartData.quoteDiscounts.value > 0}">
          <div class="col-xs-12 marginBottom10 hidden-xs hidden-sm hidden-md hidden-lg ">
              <div class="col-xs-6 cart-totals-left mb-padding discount cartPage-totalWidth"><spring:theme code="basket.page.quote.discounts" /></div>
              <div class="col-xs-6   text-right discount cartTotalPrice-Mobile cartPage-totalWidthTextAlign">
                  <ycommerce:testId code="Quote_Totals_Savings"><strong><format:price priceData="${cartData.quoteDiscounts}" /></strong></ycommerce:testId>
              </div></div>
          </c:if>
          <c:if test="${cmsPage.uid eq 'orderSummaryPage'}">
              <c:if test="${not empty cartData.totalDiscounts && cartData.totalDiscounts.value ne '0.0'}">
                <div class="col-xs-12  marginBottom10">
                      <div class="col-xs-7 confirmation-promo-title mb-padding"><spring:theme code="basket.validation.couponNotValid.promotion" /></div>
                      <div class="col-xs-5  confirmation-promo-value text-right bold no-padding-rgt-md">
                      <c:choose>
                      <c:when test="${not empty cartData.totalDiscounts && cartData.totalDiscounts.value>0}">
                          <ycommerce:testId code="orderTotal_discount_label">
                              <strong>-<format:price priceData="${cartData.totalDiscounts}" displayFreeForZero="false" /></strong>
                          </ycommerce:testId>
                       </c:when>
                       <c:otherwise>
                               $0.00
                       </c:otherwise>
                       </c:choose>
                      </div>
                  </div>
              </c:if>
         </c:if>

          <c:if test="${cmsPage.uid eq 'orderSummaryPage'}">
          <div class="col-xs-12  marginBottom10 no-padding-rgt-md">
                 <div class="cartTotalCost col-xs-6 cart-totals-left mb-padding grand-total black-title cartPage-totalWidth"><spring:theme code="basket.page.totals.total"/></div>
                 <div class="cartTotalCost col-xs-6  text-right grand-total cartTotalPrice-Mobile cartPage-totalWidthTextAlignPad">
                  <ycommerce:testId code="cart_totalPrice_label">
                      <c:choose>
                          <c:when test="${showTax}">
                              <strong><format:price priceData="${cartData.totalPriceWithTax}" /></strong>
                          </c:when>
                             <c:otherwise>
                              <strong><format:price priceData="${cartData.totalPrice}" /></strong>
                          </c:otherwise>
                      </c:choose>
                  </ycommerce:testId>
              </div>
          </div>
        <input type="hidden" class="review-showDeliveryFeeMessage" value="${cartData.showDeliveryFeeMessage}"/>
        <input type="hidden" class="review-isfreight" value="${empty cartData.freight}"/>
        <input type="hidden" class="review-freight" value="${cartData.freight}"/>
        <input type="hidden" class="review-isMixedCartEnabled" value="${isMixedCartEnabled}"/>
        <input type="hidden" class="review-isInDeliveryFeePilot" value="${cartData.isInDeliveryFeePilot}"/>
        <input type="hidden" class="review-exemptDeliveryFee" value="${cartData.orderingAccount.exemptDeliveryFee}"/>
    <c:if test="${((((empty cartData.freight && cartData.showDeliveryFeeMessage eq true) || (cartData.orderType eq 'DELIVERY' && empty cartData.deliveryCost && empty cartData.freight)) && (!cartData.orderingAccount.exemptDeliveryFee)) || (cartData.orderType eq 'DELIVERY' && currentBaseStoreId eq 'siteoneCA'))}">
		       	<div class="cl"></div>
		       		<div class="col-md-12 no-padding-lft-xs">
		       		<div class="delivery-fee-msg marginTop20">
				       	<div class="col-md-12">
					       	<div class="delivery-fee-box">
						       	<div class="bold"><spring:theme code="deliveryFeeTitle" /></div>
						       	<p>	<spring:theme code="deliveryFeeMSg" /></p>
					       	</div>
				       	</div>
		       	    </div>
		       	</div>
	       	</c:if>
          <c:if test="${not cartData.net}">
              <div class="cart-totals-taxess text-right">
                  <ycommerce:testId code="cart_taxes_label"><spring:theme code="basket.page.totals.grossTax" arguments="${cartData.totalTax.formattedValue}" argumentSeparator="!!!!"/></ycommerce:testId>
               </div>
          </c:if>


          <c:if test="${cartData.net && not showTax }">
              <div class="cart-totals-taxess text-right">
                  <ycommerce:testId code="cart_taxes_label"><spring:theme code="basket.page.totals.noNetTax"/></ycommerce:testId>
              </div>
          </c:if>
         </c:if>
      </div>
