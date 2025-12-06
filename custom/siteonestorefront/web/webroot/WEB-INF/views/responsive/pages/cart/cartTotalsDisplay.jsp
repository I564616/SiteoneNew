<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<c:set value="true" var="isNationalShippingInCart"></c:set>
<c:set var="isShippingHubOnlyCart" value="false"/>
<c:url value="/checkout/multi/siteOne-checkout" var="checkoutUrl" scope="session"/>
<c:set var="enableCheckoutIfOrderingAccount" value="true"/>
<c:set var="showPriceLoader" value="${not empty cartPageSize and cartPageSize ne null? cartPageSize : 50}" />
<c:set var="cartPriceLoader" value="${(!isAnonymous && cartData.entries.size() > showPriceLoader)? true: false}" />
<input type="hidden" class="siteoneCARegion" value="${currentBaseStoreId eq 'siteoneCA'}"/>
		<div class="cart-price-promo-container${cartPriceLoader?' cart-loader-button-overlay':''}">
	       <cart:cartOrderSummary cartData="${cartData}" cartPriceLoader="${cartPriceLoader}" showTax="true"/>
	       <%-- <cart:ajaxCartTotals/> --%>
	       <cart:cartPromotion cartData="${cartData}" voucherDisplay="desktop"/>
	       <c:forEach items="${cartData.entries}" var="entry" varStatus="loop">
				<c:if test="${entry.product.hideList eq true}">
					<c:set var="hideList" value="${entry.product.hideList}"/>
				</c:if>
				<c:if test="${entry.defaultFulfillmentType eq 'delivery'}">
					<c:set var="isDeliverySelected" value="true"/>
				</c:if>
				<c:if test="${!cartData.isTampaBranch && !cartData.isLABranch && entry.product.stockAvailableOnlyHubStore}">
					<c:set value="true" var="isNationalShippingInCart"></c:set>
				</c:if>
	       </c:forEach>
	       <div class="tax-promo-text"><spring:theme code="cart.text.tax.promocode.calculation" /></div>
	       <div class="row">
    			<div class="col-xs-7 bold marginBottom10"><spring:theme code="cart.text.estimatedtax" /></div>

	       	<c:if test="${(hideList ne true || !isAnonymous) && cartData.totalPrice.value.toString() ne 0.0}">
				<c:choose>
					<c:when test="${cartPriceLoader}">
						<ul class="margin0 padding0 text-center cart-total-loader">
							<li class="loader-circles"></li>
							<li class="loader-circles delay-1s"></li>
							<li class="loader-circles delay-2s"></li>
						</ul>
						<div class="col-xs-5 bold marginBottom10 text-align-right cart-totals hidden cart-total-loader-show">
							<span class="js-cart-total-with-tax black-title b-price add_price"></span>
						</div>
					</c:when>
					<c:otherwise>
						<div class="col-xs-5 bold marginBottom10 text-align-right cart-totals">
							<span class="js-cart-total-with-tax black-title b-price add_price"><format:price priceData="${cartData.totalPrice}" /></span>
						</div>
					</c:otherwise>
				</c:choose>
	       	</c:if>
	       	<input type="hidden" class="showDeliveryFeeMessage" value="${cartData.showDeliveryFeeMessage}"/>
			<input type="hidden" class="cart-isfreight" value="${empty cartData.freight}"/>
			<input type="hidden" class="cart-freight" value="${cartData.freight}"/>
			<input type="hidden" class="cart-isMixedCartEnabled" value="${isMixedCartEnabled}"/>
			<input type="hidden" class="cart-isInDeliveryFeePilot" value="${cartData.isInDeliveryFeePilot}"/>
			<input type="hidden" class="cart-exemptDeliveryFee" value="${cartData.orderingAccount.exemptDeliveryFee}"/>
		       	<div class="cl"></div>
		       		<div class="delivery-fee-msg ${(cartData.showDeliveryFeeMessage eq true && isMixedCartEnabled eq true) ?'':'hidden' }">
				       	<div class="col-md-12">
					       	<div class="delivery-fee-box">
						       	<div class="bold"><spring:theme code="deliveryFeeTitle" /></div>
						       	<p>	<spring:theme code="deliveryFeeMSg" /></p>
					       	</div>
				       	</div>
		       	</div>
			
	       	<c:if test="${(((empty cartData.freight && !isMixedCartEnabled && (!(cartData.isInDeliveryFeePilot))) && (!cartData.orderingAccount.exemptDeliveryFee)) || (currentBaseStoreId eq 'siteoneCA'))}">
		       	<div class="cl"></div>
		       		<div class="delivery-fee-msg-nonmixcart hidden">
				       	<div class="col-md-12">
					       	<div class="delivery-fee-box">
						       	<div class="bold"><spring:theme code="deliveryFeeTitle" /></div>
						       	<p>	<spring:theme code="deliveryFeeMSg" /></p>
					       	</div>
				       	</div>
		       	</div>
	       	 </c:if>
			 <c:if test="${isNationalShippingInCart && !cartData.isShippingFeeBranch}">
		       	<div class="cl"></div>
				   <input type="hidden" id="cart-shipping-fee-msg-show" value="${isNationalShippingInCart && !cartData.isShippingFeeBranch}"/>
				<div class="col-md-12 national-shipping-element national-shipping-msg cart-shipping-fee-msg hidden">
					<p class="padding-20 marginBottom20 font-size-14"><span class="block bold m-b-5"><spring:theme code="shipping.cost.text" /></span><spring:theme code="shipping.cost.msg" /></p>
				</div>
	       	 </c:if>
	       <div class="col-xs-12"> 
	        <c:choose>
	            <c:when test="${orderOnlinePermissions eq true}"> 
	            
	            <ycommerce:testId code="checkoutButton">
	                <c:choose>
                    		<c:when test="${guestUsers eq 'guest'}">                    		
                    			<a href="${checkoutUrl}" class="btn btn-primary btn-block cart-checkout-btn js-continue-guestcheckout-button bold-text"><spring:theme code="checkout.checkout.guest"/></a>
                    			<div class="text-center">
                    				<div class="text-lowercase text-italic"><spring:theme code="pickup.find.search.or"/></div>
                    				<a href="${checkoutUrl}" class="js-continue-checkout-button font-small-xs font-small-sm font-small-md"><spring:theme code="text.sign.in.link"/></a>
                    			</div>
                        	</c:when>
                       		<c:otherwise> 
                       			<c:if test="${isPunchOutAccount ne true }">
	                       			<button class="btn btn-primary btn-block js-continue-checkout-button cart-checkout-btn bold-text" data-checkout-url="${checkoutUrl}">                          
	                           	     	 <c:choose>
	                    					<c:when test="${!isAnonymous}">
	                    						<spring:theme code="checkout.checkout.text"/>   
	                    					</c:when>
	                    					<c:otherwise>                           	     	
	                           	     			<spring:theme code="checkout.checkout"/> 
	                           	     		</c:otherwise>
	                           	     	</c:choose> 
	                           		</button>  
	                           	</c:if>                       
                        	</c:otherwise>
			</c:choose>
	            </ycommerce:testId>
	            </c:when>
	            <c:otherwise>
	               <cms:pageSlot position="OnlineOrderCO" var="feature">
								<cms:component component="${feature}"/>
					</cms:pageSlot>
			    		<button class="btn btn-primary btn-block cart-checkout-btn  js-continue-checkout-buttonoo" type="button" id="orderOnlineATC" >
					 <c:choose>
                       				<c:when test="${guestUsers eq 'guest'}">	               
	                    				<spring:theme code="checkout.checkout.guest"/>	              
	                			</c:when>
	                			<c:otherwise>	                    
	                    				<c:choose>
	                    					<c:when test="${!isAnonymous}">
	                    						<spring:theme code="checkout.checkout.text"/>   
	                    					</c:when>
	                    					<c:otherwise>                           	     	
	                           	     			<spring:theme code="checkout.checkout"/> 
	                           	     		</c:otherwise>
                           	     		</c:choose>
	                		       </c:otherwise>
	                		</c:choose>
				       </button>
	             </c:otherwise> 
             </c:choose>
             </div>
             
				<div class="transferable-flag-msg col-md-12 gc-error-msg text-center marginTop30 hidden">
				<spring:theme code="carttrasferable.msg1"/>
				</div>
			
             <c:if test="${!isOrderingAccount}">
            	   <c:set var="enableCheckoutIfOrderingAccount" value="false"/>
            	   <div class="cl"></div>
            	   <br/>
		           <div class="col-md-8 text-left col-md-offset-2" style="color:red;">${orderingAccountMsg}</div> 
		   	 </c:if>
		   	 
		   	 	<div class="col-xs-12 js-delivery-minimum-msg text-red bold-text font-size-14 marginTop10  ${(cartData.deliveryThresholdCheckData.differenceAmount ne null and cartData.deliveryThresholdCheckData.differenceAmount gt 0 and cartData.deliveryThresholdCheckData.differenceAmount lt cartData.deliveryEligibilityThreshold)?'':'hidden'  }"><spring:theme code="cart.delivery.minimums" /></div>
		   	 
             <input type="hidden" id="enableCheckoutIfOrderingAccount" value="${enableCheckoutIfOrderingAccount}"/>
	       </div>
       </div>
	   <div class="need-help">  
	       <div class="need-help__title"><spring:theme code="cartTotalsDisplay.help" /></div>
	       <div class="need-help__contact">
	       		<spring:theme code="cartTotalsDisplay.call" />&nbsp;
	       		<a class="tel-phone" style="text-decoration:none;" href="tel:(800)748-3663">1-800-SITEONE (1-800-748-3663)</a>
	       </div>
	     	<div class="need-help__mailto">
	     		<span class="need-help__or"><spring:theme code="cartTotalsDisplay.or" /></span> <a class="bold no-text-decoration" href="mailto:${siteoneSupportEmail}">${siteoneSupportEmail}</a>
	     	</div>
     	</div>    
     
   
    


 
