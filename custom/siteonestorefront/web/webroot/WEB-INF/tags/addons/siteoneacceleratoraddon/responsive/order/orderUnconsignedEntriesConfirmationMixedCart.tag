<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="order" required="true" type="de.hybris.platform.commercefacades.order.data.OrderData" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<spring:htmlEscape defaultHtmlEscape="true" />
<%@ taglib prefix="b2b-order" tagdir="/WEB-INF/tags/addons/siteoneacceleratoraddon/responsive/order" %>

 <c:set var="hasPickup" value="false" />
	<c:set var="hasDelivery" value="false" />
	<c:set var="hasShipping" value="false" />
<div class="cl"></div>
<c:forEach items="${order.deliveryModeAndBranchOrderGroups}" var="groupData" varStatus="status">	
 	<c:if test="${groupData.deliveryMode.code eq 'pickup'}">	
 		<c:set var="hasPickup" value="true" />
 		<c:set var="pickupEntries" value="${groupData.entries}"/>
 	</c:if>
 	<c:if test="${groupData.deliveryMode.code eq 'standard-net'}">	
 		<c:set var="hasDelivery" value="true" />
 		<c:set var="deliveryEntries" value="${groupData.entries}"/>
 	</c:if>
 	<c:if test="${groupData.deliveryMode.code eq 'free-standard-shipping'}">	
 		<c:set var="hasShipping" value="true" />
 		<c:set var="shippingEntries" value="${groupData.entries}"/>
 	</c:if>

 </c:forEach>
 
 
 
 
 <div class="mixedcart-orderconfirmation">


	<div class="marginTop20">
		<div class="row">
			<div  class="col-xs-6 col-sm-4 col-md-2 bold"><spring:theme code="text.account.orderHistory.orderNumber" /></div>
			<div  class="col-xs-6 col-sm-8 col-md-10 orderId">${order.code}</div>
		</div>
		<div class="row">
			<div  class="col-xs-6 col-sm-4 col-md-2 bold"><spring:theme code="text.account.orderHistory.orderDate" /></div>
			<div  class="col-xs-6 col-sm-8 col-md-10"><fmt:formatDate var="fmtDate" value="${order.created}" pattern="MM/dd/YYYY hh:mm:ss a z"/> ${fmtDate}</div>
		</div>
		<div class="row">
			<div  class="col-xs-6 col-sm-4 col-md-2 bold"><spring:theme code="orderUnconsignedEntries.poNumber" /></div>
			<div  class="col-xs-6 col-sm-8 col-md-10"><c:choose>
				<c:when test="${not empty order.purchaseOrderNumber}">
					${order.purchaseOrderNumber}<br>
				</c:when>
				<c:otherwise>NA</c:otherwise>
			</c:choose></div>
		</div>
	</div>
 
<c:set var="confirmationEmail" value=""/>
<c:set var="showRegistration" value="false"/>
<c:set var="isGuestUser" value="false"/>
<c:choose>
	<c:when test="${order.guestCustomer eq true }">
		<c:set var="confirmationEmail" value="${order.guestContactPerson.email}"/>
		<c:set var="showRegistration" value="true"/>
		<c:set var="isGuestUser" value="true"/>
	</c:when>
	<c:otherwise>
		<c:set var="confirmationEmail" value="${user.displayUid}"/>
	</c:otherwise>
</c:choose>
	
<div class="whatnext-section marginTop35">
	<!--orderUnconsignedEntriesConfirmation.tag  whatnext-section -->
	<div class="col-md-2 col-xs-12"><h2 class="headline3 text-center"><spring:theme code="what.next.text"/></h2></div>
	<div class="col-md-10 col-xs-12 content-sec">
		<div class="col-md-12 no-padding-xs"><div class="gray-bg">
		<div class="checkout-icon"><common:contactemailIcon height="24" width="30" iconColor="#78a22f"/> </div>
		<span class="bold"><spring:theme code="confirm.mail.text"/>&nbsp;${confirmationEmail}
		</span></div></div>
		<!-- Pickup -->
		<c:if test="${hasPickup}">
		<div class="col-md-12 no-padding-xs"><div class="gray-bg">
		<div class="checkout-icon"><common:pickUpIcon height="24" width="30"  iconColor="#78a22f"/> </div>
		<span class="bold"><spring:theme code="orderunconsignedentries.pickupDetails.msg.mixedcart"/></span></div>
		<div class="cl"></div>
		 <div class="gray-bg fulfillment-info-at-checkout">
		 
		 <c:forEach items="${order.deliveryModeAndBranchOrderGroups}" var="groupData" varStatus="loop">	
					<c:if test="${groupData.deliveryMode.code eq 'pickup'}" >
					<c:set var="pickupcounter" value="${pickupcounter+1}"/>
		 		<a href="#pickup-box">
		 		<div class="fulfillment-branch-details">
		 		<spring:theme code="orderUnconsignedEntries.pickupLocation" /> #${pickupcounter}: <span class="bold"> ${groupData.pointOfService.name}</span>
		 		</div> 
		 		</a>
		 		</c:if>
			</c:forEach>	 
		</div> 
		</div>
		</c:if>

		<!-- Delivery -->
		<c:if test="${hasDelivery}">
		<div class="col-md-12 no-padding-xs"><div class="gray-bg">
		<div class="checkout-icon"><common:deliveryIcon height="22" width="40"  iconColor="#78a22f"/></div> <span class="bold"><spring:theme code="orderunconsignedentries.deliveryDetails.msg.mixedcart"/></span></div>
		<div class="cl"></div>
		 <div class="gray-bg fulfillment-info-at-checkout">
		 		<c:forEach items="${order.deliveryModeAndBranchOrderGroups}" var="groupData" varStatus="loop">	
					<c:if test="${groupData.deliveryMode.code eq 'standard-net'}" >
					
				 		<a href="#delivery-box">
				 		<div class="fulfillment-branch-details">
				 		<spring:theme code="orderunconsignedentries.deliveryfrom.mixedcart" />:  <span class="bold">${groupData.pointOfService.name}</span>
				 		</div> 
				 		</a>
		 		</c:if>
			</c:forEach>
		</div> 
		</div>
		</c:if>
		<c:if test="${hasShipping}">
		<!-- Shipping -->
		<div class="col-md-12 no-padding-xs"><div class="gray-bg">
		<div class="checkout-icon"><common:trackingshipping height="32" width="32" iconColor="#78a22f"/> </div><span class="bold"><spring:theme code="orderunconsignedentries.shippingDetails.msg.mixedcart"/></span></div>
		<div class="cl"></div>
		 <div class="gray-bg fulfillment-info-at-checkout">
		 		<a href="#shipping-box"><div class="fulfillment-branch-details bold"><spring:theme code="orderunconsignedentries.viewshippingItem.mixedcart"/></div> </a>
		</div> 
		</div>
		</c:if>
		
		
		<c:if test="${showRegistration eq 'true'}">
			<div class="col-md-12 no-padding-xs">
				<div class="gray-bg">
					<div class="col-md-3 no-padding-xs"><div class="padding10">
						<h3 class="headline3"><spring:theme code="orderconfirmation.msg2"/></h3>
						<p><spring:theme code="orderconfirmation.msg4"/></p>
					</div>
					</div>
					<div class="col-md-9 no-padding-xs">
						<div class="col-md-4 col-xs-12 no-padding-xs">
							<div class="white-bg">
								<div class="white-bg-icon"><common:lockIcon iconColor="#78a22f"/></div>
								<p><spring:theme code="orderconfirmation.msg5"/></p>
							</div>
						</div>
						<div class="col-md-4 col-xs-12 no-padding-xs">
							<div class="white-bg">
								<div class="white-bg-icon"><common:listIcon iconColor="#78a22f"/></div>
								<p><spring:theme code="orderconfirmation.msg6"/></p>
							</div>
						</div>
						<div class="col-md-4 col-xs-12 no-padding-xs">
							<div class="white-bg">
								<div class="white-bg-icon"><common:dollarcircleIcon iconColor="#78a22f"/></div>
								<p><spring:theme code="orderconfirmation.msg7"/></p>
							</div>
						</div>
						<div class="cl"></div>
						<div class="margin-top-20">
							<div class="col-md-12 no-padding-xs">
								<a href="<c:url value="/request-account/form"/>" class="btn btn-primary btn-block" onclick=""><spring:theme code="orderconfirmation.finish.registration"/></a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:if>
	</div>
	<div class="cl"></div>
</div>
 
<div class="">
	 <c:if test="${hasPickup}">
	  <b2b-order:mixedcartOrderConfirmationPickup order="${orderData}"/>
	</c:if>
	<c:if test="${hasDelivery}">
	 <b2b-order:mixedcartOrderConfirmationDelivery order="${orderData}"/>
	</c:if>
	<c:if test="${hasShipping}">
		 <b2b-order:mixedcartOrderConfirmationShipping order="${orderData}"/>
	</c:if>
  
	 </div>
	 
	 
	 
	 
	 
 <div class="orderItemsDetails">
		 

		<div class="col-xs-12 no-padding-xs">
			<div class="orderSummaryItems">
				<div class=" hidden-xs hidden-sm  hidden-md hidden-lg">
					<div class="col-xs-12 item__list--header sec-title-bar">
						<div class="col-md-7 item__image-title"><spring:theme code="basket.page.product.information"/></div>
						<div class="col-md-2 item__price-title"><spring:theme code="basket.page.price"/></div>
						<div class="col-md-1 item__quantity-title"><spring:theme code="basket.page.qty"/></div>
						<div class="col-md-2 item__total-title"><spring:theme code="basket.page.total"/></div>
					</div>
				</div>
			</div>
			<div class="item__list item__list__cartt">
				<!-- Applied Promotion Vouchers -->
				<c:if test="${order.totalDiscounts.value > 0 and not empty order.appliedVouchers}">
					<div class="applied-promotion-wrapper" style="border-bottom: 1px solid #ccc;color: #ff0000;font-size: 14pt;">
						<c:set var="hideVoucher" value="hide" />
						<c:forEach items="${order.appliedVouchers}" var="voucher" varStatus="loop">
							<c:if test="${not empty showVoucherList}">
								<c:forEach items="${showVoucherList}" var="showVoucher" varStatus="loop">
									<c:if test="${voucher eq showVoucher}">
										<c:set var="hideVoucher" value="" />
									</c:if>
								</c:forEach>
							</c:if>
						</c:forEach>
						<c:if test="${hideVoucher ne 'hide'}">
							<span><spring:theme code="orderReviewCartEntries.promoCode" /></span>
						</c:if>
						<c:forEach items="${order.appliedVouchers}" var="voucher" varStatus="loop">
							<c:if test="${not empty showVoucherList}">
								<c:forEach items="${showVoucherList}" var="showVoucher" varStatus="loop">
									<c:if test="${voucher eq showVoucher}">
										<c:choose>
											<c:when test="${fn:length(order.appliedVouchers) gt 1 and !loop.last}">
												<span>${voucher},</span>
											</c:when>
											<c:when test="${fn:length(order.appliedVouchers) gt 1 and loop.last}">
												<span>${voucher} <spring:theme code="orderReviewCartEntries.applied" /></span>
											</c:when>
											<c:otherwise>
												<span>${voucher} <spring:theme code="orderReviewCartEntries.applied" /></span>
											</c:otherwise>
										</c:choose>
									</c:if>
								</c:forEach>
							</c:if>
						</c:forEach>
					</div>
				</c:if>
				
			</div>
		</div>
	</div>
 
 
 
 
</div>
<div id="signinId" style="display: none">  <common:signInoverlay/> </div> 
	<div class="cl"></div>