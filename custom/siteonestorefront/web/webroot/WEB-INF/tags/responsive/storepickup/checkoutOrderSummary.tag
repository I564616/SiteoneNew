<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="cartData" type="de.hybris.platform.commercefacades.order.data.CartData" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="en_US" scope="session" />
<c:set var="isNationalShippingInCart" value="false" />

<c:set var="SplitMixedPickupBranchCheckout" value="false" />
<c:if test="${not empty isSplitMixedCartEnabledBranch && isSplitMixedCartEnabledBranch ne null}" >
	<c:set var="SplitMixedPickupBranchCheckout" value="${isSplitMixedCartEnabledBranch}" />
</c:if>
<c:if test="${not empty cartData.isShippingHubOnly && cartData.isShippingHubOnly ne null}" >
	<c:set var="isSplitCartShippingHubOnly" value="${cartData.isShippingHubOnly}"/>
</c:if>
<c:set var="isSplitCartCheckoutPickupFE" value="false"/>
<c:if test="${SplitMixedPickupBranchCheckout && isSplitCartShippingHubOnly && (orderType eq 'PICKUP' ||  orderType eq 'DELIVERY')}" >
	<c:set var="isSplitCartCheckoutPickupFE" value="true"/>
</c:if>
<input class="isSplitCartCheckoutPickupFE" type="hidden" value='${isSplitCartCheckoutPickupFE}'>

	<div class="row row pdp_whitebox col-md-12 panel-title ">
     <div class="col-xs-12 col-md-12 col-sm-12 border-top padding0 ">
     			<div class="row"> 
	                            <div class="col-xs-12 col-md-12 col-sm-12 data-title black-title">
	                            	<h3 class="headline3"><spring:theme code="checkout.multi.order.summary" /></h3>
	                            </div>
	                            <div class="col-md-4 col-sm-4 col-xs-2 data-data text-right  hidden-xs hidden-sm panel-title-value">
	                                <span class="glyphicon glyphicon-chevron-down"></span> 
	                            </div>
	                            </div>
	                        </div>
      
       <div class="cl"></div>
      <div id="collapseOne data-value-promo" class="collapsed margin-top-20">
      		<div class="col-xs-12 col-md-12 col-sm-12 border-top padding-LeftZero sub-total-summary">
								<div class="col-xs-12 col-md-12 col-sm-12 data-title padding-LeftZero font-small-md italic-text padding-rightzero p-l-0-xs-imp">
									<p><spring:theme code="cart.estimatedtax.message.text" /></p>
								</div>
	                            <div class="col-xs-8 col-md-8 col-sm-9 data-title black-title padding0">
	                            	<spring:theme code="order.form.subtotal" />
	                            </div>
	                            <div class="col-md-4  col-sm-3 col-xs-4 confirmation-tax-value text-right bold padding0">
 
	                                  <ycommerce:testId code="Order_Totals_Subtotal"><strong id="subtotalValue">${cartData.subTotal.formattedValue}</strong></ycommerce:testId>
 
	                            </div>
	                        </div>
							<c:if test="${orderType eq 'DELIVERY'}">
								<div class="col-xs-12 col-md-12 col-sm-12 border-top  marginBottom10 delivery-cost padding0 ${not empty cartData.freight || cartData.isInDeliveryFeePilot eq true || (cartData.isInDeliveryFeePilot ne true && cartData.orderingAccount.exemptDeliveryFee eq true)? '':'hidden'}">
									<div class="col-xs-8 col-md-8 col-sm-9 data-title black-title padding0">
										<spring:theme code="delivery.fee.text" />
									</div>
									<div class="col-md-4  col-sm-3 col-xs-4 confirmation-tax-value text-right bold padding0">
										<strong id="delivery_cost">&#36;0.00</strong>
									</div>
								</div>
							</c:if>
	                        <c:if test="${isMixedCartEnabled ne true}">
	                     	<c:if test="${(orderType eq 'PARCEL_SHIPPING' && (cartData.isTampaBranch || cartData.isLABranch || cartData.isShippingFeeBranch)) || isSplitCartCheckoutPickupFE}">
	                         <div class="col-xs-12 col-md-12 col-sm-12 border-top  marginBottom10 shipping-tax padding0">
	                            <div class="col-xs-8 col-md-8 col-sm-9 data-title black-title padding0">
	                            	<spring:theme code="shipping.cost.text"/>
	                            </div>
	                            <div class="col-md-4  col-sm-3 col-xs-4 confirmation-tax-value text-right bold padding0">
	                              <c:choose>
								  	<c:when test="${orderType eq 'DELIVERY' && isSplitCartCheckoutPickupFE}">
									<input class="shippingFrieght" type="hidden" value='${cartData.shippingFreight}'>
										 <c:choose>
										<c:when test="${empty cartData.shippingFreight || cartData.shippingFreight eq '0.00' || cartData.shippingFreight eq '0.0'}">
											<spring:theme code="text.shipping.free"/>
										</c:when>
										<c:otherwise>
											<strong id="shipping_cost">&#36;${cartData.shippingFreight}</strong>
										</c:otherwise>
									</c:choose>
									</c:when>
									<c:otherwise>
										 <c:choose>
										<c:when test="${not empty flatRateShippingFee && flatRateShippingFee ne '0.0'}">
											<strong id="shipping_cost">&#36;${flatRateShippingFee}</strong>
										</c:when>
										<c:otherwise>
											<spring:theme code="text.shipping.free"/>
										</c:otherwise>
									</c:choose>
									</c:otherwise>
								  </c:choose>
								 
								   
								   
	                            </div>

	                        </div>
	                        </c:if>
	                       
	                        
       </c:if>
              <c:if test="${isMixedCartEnabled eq true}">
	       <c:set var="hasDelivery" value="false" />
	       <c:set var="hasShipping" value="false" />
 	       <c:forEach items="${cartData.deliveryModeAndBranchOrderGroups}" var="groupData" varStatus="status">
 	       <c:if test="${groupData.deliveryMode.code eq 'standard-net'}">	
 	       	<c:set var="hasDelivery" value="true" />
 	       </c:if>
 	       <c:if test="${groupData.deliveryMode.code eq 'free-standard-shipping'}">	
 	       	<c:set var="hasShipping" value="true" />
 	       </c:if>
 	       </c:forEach>
	                        <c:if test="${hasDelivery && (cartData.isTampaBranch || cartData.isLABranch)}">
	                        <div class="col-xs-12 col-md-12 col-sm-12 border-top marginBottom20 delivery-cost padding0">
	                            <div class="col-xs-8 col-md-8 col-sm-9 data-title black-title padding0">
	                            	<spring:theme code="delivery.fee.text" />
	                            </div>
	                            <div class="col-md-4  col-sm-3 col-xs-4 confirmation-tax-value text-right bold padding0">
	                            <c:if test="${isGuestUser eq true}">
	                            <c:choose>
								<c:when test="${not empty cartData.deliveryFreight && cartData.deliveryFreight ne '0.00'}">
	                              <strong id="delivery_cost">
									  $<fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${cartData.deliveryFreight}"/> </strong>
	                              </c:when>
	                              <c:otherwise>
	                                 <strong id="delivery_cost">&#36;0.00 </strong> 
	                                 </c:otherwise>
	                                 </c:choose>
	                                 </c:if>
	                                 <c:if test="${isGuestUser eq false}">
		                            <c:choose>
										<c:when test="${not empty cartData.deliveryFreight && cartData.deliveryFreight ne '0.00'}">
			                              <strong id="delivery_cost">$<fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${cartData.deliveryFreight}"/> </strong>
			                             </c:when>
		                              <c:otherwise>
	                                 	<strong id="delivery_cost">&#36;0.00 </strong> 
	                                 </c:otherwise>
	                                 </c:choose>
	                                 </c:if>
	                            </div>
	                        </div>
	                        </c:if>
	                         <c:if test="${(hasShipping) && (cartData.isTampaBranch || cartData.isLABranch || cartData.isShippingFeeBranch)}">
	                         <div class="col-xs-12 col-md-12 col-sm-12 border-top marginBottom20 shipping-tax padding0">
	                            <div class="col-xs-8 col-md-8 col-sm-9 data-title black-title padding0">
	                            	<spring:theme code="shipping.cost.text"/>
	                            </div>
	                            <div class="col-md-4  col-sm-3 col-xs-4 confirmation-tax-value text-right bold padding0">
	                                <c:choose>
										<c:when test="${not empty flatRateShippingFee && flatRateShippingFee ne '0.0'}">
											<strong id="shipping_cost">&#36;${flatRateShippingFee}</strong>
										</c:when>
										<c:otherwise>
											<spring:theme code="text.shipping.free"/>
										</c:otherwise>
									</c:choose>
	                            </div>

	                        </div>
	                        </c:if>
       
       </c:if>
	                        <div class="col-xs-12 col-md-12 col-sm-12 border-top padding-LeftZero sale-tax-summary">
	                            <div class="col-xs-8 col-md-8 col-sm-9 data-title black-title padding0">
			                    	<spring:theme code="order.form.Sales" />
	                            </div>
	                          	
	                            <div class="col-md-4  col-sm-3 col-xs-4 confirmation-tax-value text-right bold padding0">
  
	                                 <strong id="taxValue">${cartData.totalTax.formattedValue}</strong>
 
	                            </div>
	                        
	                        </div>
							<c:if test="${not empty cartData.totalDiscounts && cartData.totalDiscounts.value ne '0.0'}">
								<div class="col-xs-12 col-md-12 col-sm-12 border-top padding-LeftZero promo-summary ">
									<div class="col-xs-8 col-md-8 col-sm-9 data-title black-title padding0 ">
										<spring:theme code="basket.validation.couponNotValid.promotion" />
									</div>
									<div class="col-md-4 col-sm-3 col-xs-4  confirmation-tax-value text-right bold padding0">
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
									</div>  <br>
								</div>
							</c:if>
							<div class="cl"></div>
	                         <div class="col-xs-12 col-md-12 col-sm-12 border-top padding-LeftZero total-summary-order">
	                            <div class="col-xs-8 col-md-8 col-sm-9 data-title black-title padding0">
	                            	<strong><spring:theme code="text.quickOrder.page.total" /></strong>
	                            </div>
	                          	
	                            <div class="col-md-4 col-sm-3 col-xs-4 confirmation-tax-value text-right bold padding0">
	                                <strong id="totalPriceWithTax">
	                                 <ycommerce:testId code="cart_totalPrice_label">
            	         <c:choose>
                                     <c:when test="${isGuestUser eq true}">
                                      		$<fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${guestTotalPriceWithTax}"/>                                 
                                     </c:when>
                                     <c:otherwise>                                                                        
 
 		$<fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${cartData.totalPriceWithTax.value}"/>
                	   </c:otherwise>
                	    </c:choose>
        	</ycommerce:testId>
        	</strong>
	                            </div>
	                          </div>
	                          
      		</div>
      		<input class="isInDeliveryFeePilot" type="hidden" value="${cartData.isInDeliveryFeePilot}"/>
			<input type="hidden" class="checkout-showDeliveryFeeMessage" value="${cartData.showDeliveryFeeMessage}"/>
			<input type="hidden" class="checkout-totalprice" value="${cartData.totalPriceWithTax.value}"/>
			<input type="hidden" class="checkout-isfreight" value="${empty cartData.freight}"/>
			<input type="hidden" class="checkout-freight" value="${cartData.freight}"/>
			<input type="hidden" class="checkout-isMixedCartEnabled" value="${isMixedCartEnabled}"/>
			<input type="hidden" class="checkout-isInDeliveryFeePilot" value="${cartData.isInDeliveryFeePilot}"/>
			<input type="hidden" class="checkout-exemptDeliveryFee" value="${cartData.orderingAccount.exemptDeliveryFee}"/>
      		<c:if test="${(((empty cartData.freight && cartData.showDeliveryFeeMessage eq true) || (cartData.orderType eq 'DELIVERY' && empty cartData.freight) || (cartData.orderType eq 'DELIVERY' && empty cartData.deliveryFreight && isSplitCartCheckoutPickupFE) ) && (!cartData.orderingAccount.exemptDeliveryFee)) || (cartData.orderType eq 'DELIVERY' && currentBaseStoreId eq 'siteoneCA')}">
		       	<div class="cl"></div>
		       		<div class="delivery-fee-msg ${(isMixedCartEnabled)? 'marginTop20' : '' }">
				       	<div class="col-md-12">
					       	<div class="delivery-fee-box row">
						       	<div class="bold"><spring:theme code="deliveryFeeTitle" /></div>
						       	<p>	<spring:theme code="deliveryFeeMSg" /></p>
					       	</div>
				       	</div>
		       	</div>
		       	
		       	
	       	</c:if>
	       		<c:if test="${cartData.isNationalShipping eq true}">
				<c:set var="isNationalShippingInCart" value="true" />
		       	<div class="cl"></div>
				<input type="hidden" id="checkout-shipping-fee-msg-show" value="${isNationalShippingInCart && !cartData.isShippingFeeBranch}"/>
		       	<div class="delivery-fee-msg ${(isMixedCartEnabled)? 'marginTop20' : '' }">
		       		<c:if test="${cartData.isShippingFeeBranch eq false}">
				       	<div class="col-md-12">
					       	<div class="delivery-fee-box checkout-shipping-fee-msg row">
						       	<div class="bold"><spring:theme code="shippingFeeTitle" /></div>
						       	<p>	<spring:theme code="shippingFeeMSg" /></p>
					       	</div>
				       	</div>
		       		</c:if>
		       	</div>
		       	</c:if>
				<input type="hidden" id="isNationalShippingInCart" name="isNationalShippingInCart" value="${isNationalShippingInCart}">
      		   </div>
      		   <div class="summary-need-help col-md-12 col-xs-12 col-sm-12 collapsed">
      		<div class="help-text-center"><strong><spring:theme code="basket.page.needHelp" /></strong></div>
      		
      		<div  class="help-text-center-number">
				<c:if test="${!isMixedCartEnabled}">
					<spring:theme code="cartTotalsDisplay.call" />&nbsp;
				</c:if>
      			<a class="tel-phone" style="text-decoration:none;" href="tel:(800)748-3663"><spring:theme code="search.page.no.result" /></a>
      		</div>
      		<div class="link-help-order"><spring:theme code="cartTotalsDisplay.or"/>&nbsp;  
      		<a class="${(isMixedCartEnabled)? 'help-mail' : '' }" href="mailto:customersupport@siteone.com" ><spring:theme code="search.no.results.helpContactEmailId" /></a></div></div>
      	 



