<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="multi-checkout" tagdir="/WEB-INF/tags/responsive/checkout/multi"%>
<%@ taglib prefix="multi-checkouts" tagdir="/WEB-INF/tags/responsive/checkout/multi"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<spring:url value="/" var="homelink"/>
<spring:url value="/checkout/multi/order-summary/placeOrder" var="placeOrderUrl"/>
<spring:url value="/checkout/multi/order-summary/voucher/apply" var="applyVoucherAction"/>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<spring:url value="/checkout/multi/order-summary/voucher/remove" var="removeVoucherAction"/>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="multiCheckout" tagdir="/WEB-INF/tags/responsive/checkout/multi" %>

<input type="hidden" id="currentCartId" name="currentCartId" value="${cartData.b2bCustomerData.currentCartId}">
<input type="hidden" id="recentCartIds" name="recentCartIds" value="${cartData.b2bCustomerData.recentCartIds}">
<input type="hidden" id="anonymousCartId" name="anonymousCartId" value="${cartData.code}">
<input type="hidden" id="orderType" name="orderType" value="${cartData.orderType}">
<c:set var="subtotalLimit" value="${cartData.deliveryEligibilityThreshold}"/>
<input type="hidden" class="subtotalLimit" value="${subtotalLimit}"/>
<c:set var="homeOwnerCode" value="<%=de.hybris.platform.util.Config.getParameter(\"homeOwner.trade.class.code\")%>"/>
<input type="hidden" class="homeOwnerCode" value="${homeOwnerCode}"/>
<input type="hidden" class="trade-class" value="${cartData.orderingAccount.tradeClass}"/>
<input type="hidden" class="subTotal-class" value="${cartData.subTotal.value}"/>
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
	<input class="isSplitMixedPickupBranchReview" type="hidden" value='${isSplitMixedPickupBranchReview}'>

<div class="checkout-review">
	<c:if test="${cartData.orderType eq 'PICKUP'}"> 
		<div class="row PickupInformation">
			<div class="col-xs-12">
				<div class="title-bar order-confirmation-page-bottom ${isSplitMixedPickupBranchReview ? 'm-b-0-imp m-t-50-xs-imp' : '' }">
					<c:if test="${!isSplitMixedPickupBranchReview}">
					<div class="col-xs-9 order-summary-title padding0">
						<h1 class="order-confirmation-page-title order-review-title-wrapper"><spring:theme code="orderSummaryPage.pickup.info" /></h1>
					</div>
					</c:if>
					<c:if test="${isSplitMixedPickupBranchReview}">
						<div class="col-md-12 p-l-0 splitPickupOrder">
							<div class="bg-gray padding-20 shippingLabel text-align-center text-white add-border-radius"><spring:theme code="orderSummaryPage.parcel.splitpickup.info"/></div>
						</div>
					</c:if>
					<div class="col-xs-3 text-right padding0 ${isSplitMixedPickupBranchReview ? '' : 'order-exit-top'} ">
						<a class="add-edit-color add-edit-decoration" href="<c:url value="/checkout/multi/siteOne-checkout"/>"><spring:theme code="orderSummaryPage.edit" /></a>
					</div>
				</div>
			</div>
			<div class="col-xs-12 ">
			<div class="col-xs-12 add-border-radius add-border-grey">
				<div class="col-md-3 col-sm-12 pickup-location-wrapper">
					<div class="secondary-title ">
						<span class="bold-text"><spring:theme code="orderSummaryPage.pickup.location" /></span>
					</div>
					<div class="pickup-information-location">
						<p class="message-header">	
					    	<span class="bold">${cartData.pointOfService.name}</span><br>
							<span class="bold">${cartData.pointOfService.title}</span><br>
						</p>
						${cartData.pointOfService.address.line1}<br>
						${cartData.pointOfService.address.town},
						${cartData.pointOfService.address.region.isocodeShort}&nbsp;
						${cartData.pointOfService.address.postalCode}<br>
					<a class="tel-phone" href="tel:${cartData.pointOfService.address.phone}">${cartData.pointOfService.address.phone}</a><br><br>
					<a id="getDirection" href="" data-url="${cartData.pointOfService.address.line1},${cartData.pointOfService.address.town},${cartData.pointOfService.address.region.isocodeShort},${cartData.pointOfService.address.postalCode}"><spring:theme code="choosePickupDeliveryMethodPage.get.direction" /></a></span>

					</div>
				</div>
				<c:if test="${!isSplitMixedPickupBranchReview}">
				<div class="col-md-3 col-sm-12 pickup-contact-wrapper">
					<div class="secondary-title">
						<span class="bold-text"><spring:theme code="orderSummaryPage.pickup.contact" /></span>
					</div>
					<c:choose>
					<c:when test="${guestUsers eq 'guest'}">
					<div class="add-secondaryTitle">
						<div>${cartData.b2bCustomerData.firstName}&nbsp;${cartData.b2bCustomerData.lastName}</div>
						 <c:choose>  
				  			<c:when test="${not empty cartData.guestContactPerson.contactNumber}">
					    		 <a class="tel-phone" href="tel:${cartData.guestContactPerson.contactNumber}">${cartData.guestContactPerson.contactNumber}</a><br>
				 			 </c:when>
				 			 <c:otherwise>
				 				 <spring:theme code="orderSummaryPage.na" /><br>
				  			</c:otherwise>
				  		</c:choose>
				  		${cartData.guestContactPerson.email} <br>
						<%-- ${cartData.contactPerson.email}<br> --%>
					</div>
					</c:when>
					<c:otherwise>
					<div class="add-secondaryTitle">
						${cartData.contactPerson.name}<br>
						 <c:choose>  
				  			<c:when test="${not empty cartData.contactPerson.contactNumber}">
					    		 <a class="tel-phone" href="tel:${cartData.contactPerson.contactNumber}">${cartData.contactPerson.contactNumber}</a><br>
				 			 </c:when>
				 			 <c:otherwise>
				 				 <spring:theme code="orderSummaryPage.na" /><br>
				  			</c:otherwise>
				  		</c:choose>
				  		${cartData.contactPerson.email}<br>
						<%-- ${cartData.contactPerson.email}<br> --%>
					</div>
					</c:otherwise>
					</c:choose>
								    
				</div>
				</c:if>
				<div class="col-md-3 col-sm-12 pickup-info-wrapper isPickupDateRequired-date" style="display:none;">
					<div class="secondary-title">
						<span class="bold-text"><spring:theme code="orderSummaryPage.date.time" /></span>
					</div>
					<div class="pickup-information-details">
						<!--  <p class="message-header message-header"><spring:theme code="orderSummaryPage.date.time" />:</p>-->
							<fmt:formatDate var="fmtDate" value="${cartData.requestedDate}" pattern="MMM dd,YYYY hh:mm:ss a"/>
							<input type="hidden" id="requestedDateinUTC"  value="${fmtDate}"/>
							<span id="requestedDateInLocal"><fmt:formatDate value="${cartData.requestedDate}" pattern="MMM dd,YYYY" /></span>
							<BR>
							
							<c:if test="${cartData.requestedMeridian eq 'AM'}">
							<spring:theme code="orderSummaryPage.morning" />
							</c:if>
							<c:if test ="${cartData.requestedMeridian eq 'PM'}">
							<spring:theme code="orderSummaryPage.afternoon" />
							</c:if>
						<!-- <p class="message-header additionalInfo"><spring:theme code="orderSummaryPage.msg.branch" />:</p>
						<p class="order-message">
						<c:choose>
							<c:when test="${not empty cartData.specialInstruction}">
								${cartData.specialInstruction}
							</c:when>
							<c:otherwise><spring:theme code="orderSummaryPage.na" /></c:otherwise>
						</c:choose>
						</p>-->
					</div>
				</div>
				<!--  <div class="col-md-3 col-sm-12 pickup-account-wrapper">
					<div class="secondary-title">
						<span class="bold-text"><spring:theme code="orderSummaryPage.acc.num" />:</span>
					</div>
					<div>
						${cartData.orderingAccount.displayId}<br>
					</div>
					<%-- <br>
					<div class="account-shipto-message">
					  <spring:theme code="checkout.multi.order.message.account.shipto" />
					</div> --%>
				</div>-->
				
				<div class="col-xs-12 padding0 marginTopBVottom20 instruction-left">
				<div class="secondary-title col-xs-12 col-sm-4  col-md-3 bold"><spring:theme code="orderunconsignedentries.pickUpInstruction" /></div>
				<p class="col-xs-12 col-sm-8 col-md-9 special-instruct-wrapper marginTopBVottom20 no-margin-xs">
					<c:choose>
						<c:when test="${not empty cartData.specialInstruction}">
							${cartData.specialInstruction}
						</c:when>
						<c:otherwise><span><spring:theme code="orderSummaryPage.na" /></span></c:otherwise>
					</c:choose>
				</p>
			</div>
				<div class="col-md-12 pick-up-mesg-wrapper"><span class="pick-up-mesg"><spring:theme code="orderSummaryPage.pickup.message"/></span></div>
			<common:hardscapeAlert />
			<div class="row">
	 			<div class="col-xs-12">
				<c:if test="${!isSplitMixedPickupBranchReview}">
					<multi-checkout:siteOneCheckoutOrderSummary cartData="${cartData}" showDeliveryAddress="true" showTaxEstimate="true" showTax="true" fromPage="checkoutOrderSummary"/>
				</c:if>
				<c:if test="${isSplitMixedPickupBranchReview}">
				<multi-checkout:siteOneCheckoutOrderSummary cartData="${cartData}" showDeliveryAddress="true" showTaxEstimate="true" showTax="true" fromPage="checkoutOrderSummary" splitProductType="pickupAndDeliveryEntries"/>
				</c:if>
				</div>
		 	</div>
			</div>
			
		</div>
		
		</div>
 </c:if>	

 <c:if test="${cartData.orderType eq 'DELIVERY'}"> 
	<div class="row deliveryInformations">
		<div class="col-xs-12">
			<div class="title-bar order-confirmation-page-bottom ${isSplitMixedPickupBranchReview ? ' m-b-0-imp' : '' }">
				<div class="col-xs-9 order-summary-title padding0" >
				<c:if test="${!isSplitMixedPickupBranchReview}">
					<h1 class="order-confirmation-page-title order-review-title-wrapper"><spring:theme code="orderSummaryPage.delivery.info" /></h1>
				</c:if>
				<c:if test="${isSplitMixedPickupBranchReview}">
					<div class="bg-gray padding-20 shippingLabel text-align-center text-white add-border-radius text-uppercase"><spring:theme code="orderSummaryPage.delivery.details.info" /></div>
				</c:if>
				</div>
				<div class="col-xs-3 text-right padding0 order-exit-top">
					<a class="add-edit-decoration" href="<c:url value="/checkout/multi/siteOne-checkout"/>"><spring:theme code="orderSummaryPage.edit" /></a>
				</div>
			</div>
		</div>
		<div class="col-xs-12">
		<div class="col-xs-12 add-border-radius add-border-grey">
			<div class="col-md-3 col-sm-12 pickup-location-wrapper  ${isSplitMixedPickupBranchReview ? ' hidden' : '' }">
				<div class="secondary-title">
					<span class="bold-text"><spring:theme code="orderSummaryPage.delivery.contact" /></span>
				</div>
				<div>
					<c:choose>
					<c:when test="${guestUsers eq 'guest'}">
				
						<div>${cartData.b2bCustomerData.firstName}&nbsp;${cartData.b2bCustomerData.lastName}</div>
						 <c:choose>  
				  			<c:when test="${not empty cartData.guestContactPerson.contactNumber}">
					    		 <a class="tel-phone" href="tel:${cartData.guestContactPerson.contactNumber}">${cartData.guestContactPerson.contactNumber}</a><br>
				 			 </c:when>
				 			 <c:otherwise>
				 				 <spring:theme code="orderSummaryPage.na" /><br>
				  			</c:otherwise>
				  		</c:choose>
				  		${cartData.guestContactPerson.email} <br>
						
					
					</c:when>
					<c:otherwise>
				    ${cartData.contactPerson.name}<br>
				    <c:choose>  
				  			<c:when test="${not empty cartData.contactPerson.contactNumber}">
					    		 <a class="tel-phone" href="tel:${cartData.contactPerson.contactNumber}">${cartData.contactPerson.contactNumber}</a><br>
				 			 </c:when>
				 			 <c:otherwise>
				 				 <spring:theme code="orderSummaryPage.na" /><br>
				  			</c:otherwise>
				  		</c:choose>
				  		${cartData.contactPerson.email}<br>
				    <%-- ${cartData.contactPerson.email}<br> --%>
				    </c:otherwise>
				    </c:choose>
				</div>
							    
			</div>
			
			<div class="col-md-3 col-sm-12">
				<div class="secondary-title">
				<c:if test="${!isSplitMixedPickupBranchReview}">
				<span class="bold-text"><spring:theme code="orderSummaryPage.delivery.to" /></span>
				</div>
				</c:if>
				<c:if test="${isSplitMixedPickupBranchReview}">
				<span class="bold-text">Delivery Location</span>
				</div>
				</c:if>
				<div class="">
					${cartData.deliveryAddress.line1},<br>
					<c:if test="${not empty cartData.deliveryAddress.line2}"> 
						${cartData.deliveryAddress.line2},<br>
					</c:if>
					${cartData.deliveryAddress.town},&nbsp;
					${cartData.deliveryAddress.region.isocodeShort}&nbsp;
				    ${cartData.deliveryAddress.postalCode}<br>
				</div>
			</div>
			
			<div class="col-md-3 col-sm-12">
				<div class="secondary-title">
					<span class="bold-text"><spring:theme code="orderSummaryPage.delivery.date.time" /></span>
				</div>
				<div>
						<fmt:formatDate var="fmtDate" value="${cartData.requestedDate}" pattern="MM/dd/YYYY hh:mm:ss a"/>
						<input type="hidden" id="requestedDateinUTC"  value="${fmtDate}"/>
						<input type="hidden" name="meridiancode"  value="${cartData.requestedMeridian}"/>
						<span id="requestedDateInLocal"><fmt:formatDate value="${cartData.requestedDate}" pattern="MM/dd/YYYY" /></span>
						<BR>
						
						<c:if test="${cartData.requestedMeridian eq 'AM'}">
						<spring:theme code="orderSummaryPage.morning" />
						</c:if>
						<c:if test ="${cartData.requestedMeridian eq 'PM'}">
						<spring:theme code="orderSummaryPage.afternoon" />
						</c:if>
						<c:if test ="${cartData.requestedMeridian eq 'ANYTIME'}">
						  <spring:theme code="orderSummaryPage.allday" />
						</c:if>
				</div>
			</div>
			<c:if test="${guestUsers ne 'guest'}">
			<div class="col-md-3 col-sm-12 pickup-account-wrapper ${isSplitMixedPickupBranchReview ? ' hidden' : '' }">
				<div class="secondary-title">
					<span class="bold-text"><spring:theme code="orderSummaryPage.acc.num" /></span>
				</div>
				<div class="order-summary-account-number">
					${cartData.orderingAccount.displayId}<br>
				</div>
				<%-- <br>
				<div class="account-shipto-message">
					  <spring:theme code="checkout.multi.order.message.account.shipto" />
				</div> --%>
			</div>
			</c:if>
			<div class="col-xs-12 padding0 marginTopBVottom20 instruction-left">
				<div class="secondary-title col-xs-12 col-sm-4  col-md-3 bold"><spring:theme code="orderunconsignedentries.deliveryInstruction" /></div>
				<p class="col-xs-12 col-sm-8 col-md-9 special-instruct-wrapper marginTopBVottom20 no-margin-xs">
					<c:choose>
						<c:when test="${not empty cartData.specialInstruction}">
							${cartData.specialInstruction}
						</c:when>
						<c:otherwise><span><spring:theme code="orderSummaryPage.na" /></span></c:otherwise>
					</c:choose>
				</p>
			</div>
			<div class="col-md-12 pick-up-mesg-wrapper"><span class="pick-up-mesg"><spring:theme code="orderSummaryPage.pickup.front.message" />&nbsp;${cartData.pointOfService.name}&nbsp;<spring:theme code="orderSummaryPage.delivery.end.message" /></span></div>
			<common:hardscapeAlert />
			<div class="row ">
	 			<div class="col-xs-12">
				<c:if test="${!isSplitMixedPickupBranchReview}">
					<multi-checkout:siteOneCheckoutOrderSummary cartData="${cartData}" showDeliveryAddress="true" showTaxEstimate="true" showTax="true" fromPage="checkoutOrderSummary"/>
				</c:if>
				<c:if test="${isSplitMixedPickupBranchReview}">
					<multi-checkout:siteOneCheckoutOrderSummary cartData="${cartData}" showDeliveryAddress="true" showTaxEstimate="true" showTax="true" fromPage="checkoutOrderSummary" splitProductType="pickupAndDeliveryEntries"/>
				</c:if>
				</div>
		 	</div>
		</div>
		</div>
	</div>
</c:if>

 <c:if test="${cartData.orderType eq 'PARCEL_SHIPPING' || isSplitMixedPickupBranchReview}"> 
	<div class="row deliveryInformations">
		<div class="col-xs-12">
			<div class="title-bar order-confirmation-page-bottom ${isSplitMixedPickupBranchReview ? 'm-b-0-imp m-t-40-xs-imp' : '' }">
				<c:if test="${!isSplitMixedPickupBranchReview}">
					<div class="col-xs-9 order-summary-title padding0" >
						<h1 class="order-confirmation-page-title order-review-title-wrapper"><spring:theme code="orderSummaryPage.parcel.shipping.info" /></h1>
					</div>
				</c:if>
				<c:if test="${isSplitMixedPickupBranchReview}">
						<div class="col-md-12 p-l-0 splitPickupOrder">
							<div class="bg-gray padding-20 shippingLabel text-align-center text-white add-border-radius ${cartData.orderType eq 'DELIVERY' ? 'text-uppercase' : ''}"><spring:theme code="orderSummaryPage.parcel.shipping.info" /></div>
						</div>
				</c:if>
				<div class="col-xs-3 text-right padding0 ${isSplitMixedPickupBranchReview ? '' : 'order-exit-top'} ">
					<a class="add-edit-decoration" href="<c:url value="/checkout/multi/siteOne-checkout"/>"><spring:theme code="orderSummaryPage.edit" /></a>
				</div>
			</div>
		</div>
		<div class="col-xs-12">
		<div class="col-xs-12 add-border-radius add-border-grey">
			<div class="col-md-2 col-sm-12 pickup-location-wrapper">
				<div class="secondary-title">
				
					<span class="bold-text"><c:if test="${!isSplitMixedPickupBranchReview}"><spring:theme code="orderSummaryPage.parcel.shipping.to.contact" /></c:if>
					<c:if test="${isSplitMixedPickupBranchReview}"><spring:theme code="checkout.summary.shippingAddress" /> </c:if></span>
				</div>			    
			</div>
			<c:if test="${!isSplitMixedPickupBranchReview}">
			<div class="col-md-3 col-sm-12">
				<div class="margin-top-20">
					<c:choose>
						<c:when test="${guestUsers eq 'guest'}">
							<div>${cartData.b2bCustomerData.firstName}&nbsp;${cartData.b2bCustomerData.lastName}</div>
							 <c:choose>  
					  			<c:when test="${not empty cartData.guestContactPerson.contactNumber}">
						    		 <a class="tel-phone" href="tel:${cartData.guestContactPerson.contactNumber}">${cartData.guestContactPerson.contactNumber}</a><br>
					 			 </c:when>
					 			 <c:otherwise>
					 				 <spring:theme code="orderSummaryPage.na" /><br>
					  			</c:otherwise>
					  		</c:choose>
					  		${cartData.guestContactPerson.email} <br>
					   </c:when>
					   <c:otherwise>
						    ${cartData.contactPerson.name}<br>
						    <c:choose>  
						  			<c:when test="${not empty cartData.contactPerson.contactNumber}">
							    		 <a class="tel-phone" href="tel:${cartData.contactPerson.contactNumber}">${cartData.contactPerson.contactNumber}</a><br>
						 			 </c:when>
						 			 <c:otherwise>
						 				 <spring:theme code="orderSummaryPage.na" /><br>
						  			</c:otherwise>
						  		</c:choose>
						  		${cartData.contactPerson.email}<br>
						    <%--PARCEL_SHIPPING ${cartData.contactPerson.email}<br> --%>
					    </c:otherwise>
					    </c:choose>
				</div>
							    
			</div>
			</c:if>
			
			<div class="${isSplitMixedPickupBranchReview ? 'col-md-12' : 'col-md-3'} col-sm-12">
				
				<div class="${isSplitMixedPickupBranchReview ? 'p-l-5' : 'margin-top-20'}">
					<c:if test="${!isSplitMixedPickupBranchReview}">
						${cartData.deliveryAddress.line1},<br>
						<c:if test="${not empty cartData.deliveryAddress.line2}"> 
							${cartData.deliveryAddress.line2},<br>
						</c:if>
						${cartData.deliveryAddress.town},&nbsp;
						${cartData.deliveryAddress.region.isocodeShort}&nbsp;
						${cartData.deliveryAddress.postalCode}<br>
						
					
						
						<%-- m:${cartData.deliveryAddress.phone}<br> --%>
					</c:if>
					<c:if test="${isSplitMixedPickupBranchReview}">
						${cartData.shippingAddress.line1},<br>
						<c:if test="${not empty cartData.shippingAddress.line2}"> 
							${cartData.shippingAddress.line2},<br>
						</c:if>
						${cartData.shippingAddress.town},&nbsp;
						${cartData.shippingAddress.region.isocodeShort}&nbsp;
						${cartData.shippingAddress.postalCode}<br>
						
					</c:if>
				</div>
			</div>
			
			
			<div class="col-md-12 pick-up-mesg-wrapper marginTop40"><span class="pick-up-mesg"><spring:theme code="orderSummaryPage.parcel.shipping.message" /></span></div>
			<common:hardscapeAlert />
			
			<div class="row ">
	 			<div class="col-xs-12">
				<c:if test="${!isSplitMixedPickupBranchReview}">
					<multi-checkout:siteOneCheckoutOrderSummary cartData="${cartData}" showDeliveryAddress="true" showTaxEstimate="true" showTax="true" fromPage="checkoutOrderSummary"/>
				</c:if>
				<c:if test="${isSplitMixedPickupBranchReview}">
					<multi-checkout:siteOneCheckoutOrderSummary cartData="${cartData}" showDeliveryAddress="true" showTaxEstimate="true" showTax="true" fromPage="checkoutOrderSummary" splitProductType="shippingOnlyEntries"/>
				</c:if>
				</div>
		 	</div>
		</div>
		
		</div>
	</div>
	
</c:if>

<div class="row">
	<div class="col-xs-12">
		<multi-checkout:orderReviewPaymentinfo cartData="${cartData}" showDeliveryAddress="true" showTaxEstimate="true" showTax="true" fromPage="checkoutOrderSummary"/>
	</div>
</div>

 
  
 <div class="col-md-12 promoApply hidden-lg hidden-sm hidden-xs hidden-md">
	<div class="col-xs-12">
		<div class="title-bar">
			<div class="col-xs-12">
				<h2><spring:theme code="orderSummaryPage.promotion.code" /></h2>
			</div>
		</div>
	</div>
	<div class="col-xs-12 promotion-code-wrapper">
		<c:set var="containerClass">
	    	<c:choose>
	        	<c:when test="${not empty errorMsg}"><spring:theme code="orderSummaryPage.error" /></c:when>
	        	<c:when test="${not empty successMsg}"><spring:theme code="orderSummaryPage.success" /></c:when>
	        	<c:otherwise></c:otherwise>
	    	</c:choose>
		</c:set>
		<c:forEach items="${cartData.appliedProductPromotions}" var="promotion">
			<c:set var="promotionApplied" value="true" />
		</c:forEach>
			<div class="form-group js-voucher-respond ${containerClass}">
		    	<spring:theme code="text.checkoutvoucher.apply.input.placeholder" var="voucherInputPlaceholder"/>
		    	<label for="js-voucher-code-text"><spring:theme code="orderSummaryPage.have.prom.code" /></label>
		    	<input type="hidden" class="voucherDisplay" value="checkout" />
		 		<form:form id="applyVoucherForm_checkout" class="applyVoucherForm" action="${applyVoucherAction}" method="post" modelAttribute="voucherForm">
			       
		             		 <form:input cssClass="js-voucher-code cart-voucher__input form-control input-sm js-voucher-code-text" name="voucher-code"
			                    id="js-voucher-code-text" maxlength="100" placeholder="${voucherInputPlaceholder}"
			                    path="voucherCode" disabled="${disableUpdate}"/>            	   
					<c:if test="${not disableUpdate}">
				       
			 					<button type="button" id="js-voucher-apply-btn" class="btn btn-default btn-small cart-voucher__btn js-voucher-apply-btn">
				            		<spring:theme code="text.checkoutvoucher.apply.button.label"/>
				        		</button>					
					</c:if>
		    	</form:form>
			    <c:if test="${not empty errorMsg || not empty successMsg}">
				    <div class="js-voucher-validation-container help-block cart-voucher__help-block">
				        ${errorMsg}
				        ${successMsg}
				    </div>
			    </c:if>
		  	</div>
	</div>
</div>
<div class="cl"></div>

<div class="checkout-couponcode hidden-lg hidden-sm hidden-xs hidden-md">

 <ul id="js-applied-vouchers" class="selected_product_ids clearfix voucher-list row">
    <c:forEach items="${cartData.appliedVouchers}" var="voucher" varStatus="loop">
    <c:set var="hideVoucher" value="hide"/>
    <c:if test="${not empty showVoucherList}">
   		 <c:forEach items="${showVoucherList}" var="showVoucher">
    		<c:if test="${voucher eq showVoucher}">
    			<c:set var="hideVoucher" value=""/>
   		 	 </c:if>
    	</c:forEach>
    	</c:if>
        <li class="voucher-list__item ${hideVoucher}">
            <form:form id="removeVoucherForm${loop.index}_${voucherDisplay}" action="${removeVoucherAction}" method="post"
                       modelAttribute="voucherForm">
                <span class="js-release-voucher voucher-list__item-box" id="voucher-code-${voucher}">
                     ${voucher}
                     <form:input hidden="hidden"  id ="voucherCode_${voucherDisplay}" value="${voucher}" path="voucherCode"/>
                    <span class="glyphicon glyphicon-remove js-release-voucher-remove-btn voucher-list__item-remove"></span>
                </span>
            </form:form>
        </li>
        
    </c:forEach>
</ul> 
</div>
<div class="col-md-12 order-summary-call padding0">
<div class="col-md-8 hidden-xs hidden-sm paddingTopB10"><strong> <spring:theme code="checkout.multi.questions.order"/></strong> <spring:theme code="checkout.multi.questions.call"/><a href="mailto:customersupport@siteone.com"><spring:theme code="search.no.results.helpContactEmailId" /></a></div>
  <div class="row col-sm-12 col-md-4 padding0 palceOrder-button-summary">
	     	<form:form action="${placeOrderUrl}" id="placeOrderForm1" modelAttribute="placeOrderForm">
		        <button id="placeOrder" type="button" class="btn btn-primary btn-place-order btn-block button-payment-val ${cartData.b2bCustomerData.needsOrderApproval eq true ?'orderapproval_btn':''}">
		            <c:choose>
						<c:when test="${cartData.b2bCustomerData.needsOrderApproval eq true}">
							<spring:theme code="checkout.summary.submitForApproval" text="Place Order"/>
						</c:when>
						<c:otherwise>
		            		<spring:theme code="checkout.summary.placeOrder" text="Place Order"/>
						</c:otherwise>
					</c:choose>
		        </button>
	        </form:form> 
     	</div>
 <div class="col-md-8 hidden-md hidden-lg button-payment-bottom"><strong> <spring:theme code="checkout.multi.questions.order"/></strong><br> <spring:theme code="checkout.multi.questions.call"/><a href="mailto:customersupport@siteone.com"><spring:theme code="search.no.results.helpContactEmailId" /></a></div>
 </div>
   <br>
  <br>
    <div class="col-sm-12 col-lg-12 hidden-lg hidden-sm hidden-xs hidden-md">
        <br class="hidden-lg">
        <cms:pageSlot position="SideContent" var="feature" element="div" class="checkout-help">
            <cms:component component="${feature}"/>
        </cms:pageSlot>
    </div>
    <div class="col-md-12 padding0">
    <c:if test="${cartData.orderType eq 'PICKUP'}"> 
     	<p class="pickup-disclaimer-text col-md-12 padding0 hidden"><spring:theme code="text.account.order.pickup.disclaimertext"/></p>
     </c:if>
     <c:if test="${cartData.orderType eq 'DELIVERY'}">
     	<p class="delivery-note col-md-12 padding0 hidden"><spring:theme code="text.account.order.delivery.notetocustomer"/></p>
     	<p class="delivery-disclaimer-text col-md-12 padding0 hidden"><spring:theme code="text.account.order.delivery.disclaimertext"/></p>
     </c:if>
     <c:if test="${cartData.orderType eq 'DELIVERY' and not empty cartData.deliveryCost }"> <!-- or cartData.orderingAccount.exemptDeliveryFee -->
    <p class="delivery-disclaimer-text col-md-12 padding0">
		<spring:theme code="text.account.order.regulatedItems.disclaimerTextAdditionalDelivery"/>
	</p>
	</c:if>
	<c:if test="${cartData.orderType eq 'DELIVERY' or cartData.orderType eq 'PICKUP'}">
    <p class="text-default bold col-md-12 padding0">
		<spring:theme code="text.account.order.regulatedItems.disclaimertext"/>
	</p>
	</c:if>
		<p class="col-md-12 padding0 p-t-15 font-size-14 f-s-16-xs-px p-t-25-xs">
			<a href="${homelink}salesterms"><spring:theme code="text.account.order.termsofsale"/></a>
			<spring:theme code="text.account.order.includeimportantlegal"/>
			<spring:theme code="text.account.order.our"/>
			<a href="${homelink}termsandconditions"><spring:theme code="text.account.order.websiteterms"/></a>
			<spring:theme code="text.account.order.setforth"/>
			<spring:theme code="text.account.order.our"/>
			<a href="${homelink}privacypolicy"><spring:theme code="text.account.order.privacypolicy"/></a>
			<spring:theme code="text.account.order.includesimportantinformation"/>.
			<spring:theme code="text.account.order.byplacing"/>
			<spring:theme code="text.account.order.termsofsale"/>,
			<spring:theme code="text.account.order.websiteterms"/>
			<spring:theme code="text.account.order.and"/>
			<spring:theme code="text.account.order.privacypolicy"/>.
		</p>
	</div>
</div>

<div class="lodding-popup hidden">
  <div class="row">
	 <div id="floatBarsG" class="loader-margin">
		<div id="floatBarsG_1" class="floatBarsG"></div>
		<div id="floatBarsG_2" class="floatBarsG"></div>
		<div id="floatBarsG_3" class="floatBarsG"></div>
		<div id="floatBarsG_4" class="floatBarsG"></div>
		<div id="floatBarsG_5" class="floatBarsG"></div>
		<div id="floatBarsG_5" class="floatBarsG"></div>
		<div id="floatBarsG_6" class="floatBarsG"></div>
		<div id="floatBarsG_7" class="floatBarsG"></div>
		<div id="floatBarsG_8" class="floatBarsG"></div>
	</div>
 	<div class="col-sm-12 padding-bottom-15 text-center text-default bold error-title">
 		<spring:theme code="cart.loading.popup.text"/>
 	</div>
  </div>
</div>

<div class="order-error-popup hidden">
  <div class="row">
  	<div class="col-sm-12 padding-top-20 bold text-center text-default error-title">
  		<spring:theme code="cart.error.popup.title"/>
  	</div>
  	<div class="col-sm-12 padding-top-20 text-center text-default error-text">
  		<spring:theme code="cart.error.popup.text1"/> <span class="extra-bold">1-800-SITEONE</span>
  	</div>
  	<div class="col-sm-12 text-center text-default error-text">
  		<spring:theme code="cart.error.popup.text2"/>
  	</div>
  	<div class="col-sm-12 padding-bottom-15 text-center text-default error-text">
  		<spring:theme code="cart.error.popup.text3"/>
  	</div>
  	<div class="buttons">
	  	<div class="col-sm-6 goto-homepage-btn">
	 		<a href="<c:url value="/"/>" class="btn btn-default font-Geogrotesque-bold btn-lg btn-block" >
	      		<spring:theme code="cart.error.popup.btn.homepage"/>
	      	</a>
	    </div>
	    <div class="col-sm-6 goto-cart-btn">
	      	<a href="<c:url value="/cart"/>" class="btn btn-primary font-Geogrotesque-bold btn-lg btn-block" >
	      		<spring:theme code="basket.restoration.view.cart.btn"/>
	      	</a>
	    </div>
    </div>
  </div>
</div> 