<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="address" tagdir="/WEB-INF/tags/responsive/address"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="multiCheckout" 	tagdir="/WEB-INF/tags/responsive/checkout/multi"%>

<c:set var="SplitMixedPickupBranchCheckout" value="false" />
<c:if test="${not empty isSplitMixedCartEnabledBranch && isSplitMixedCartEnabledBranch ne null}" >
	<c:set var="SplitMixedPickupBranchCheckout" value="${isSplitMixedCartEnabledBranch}" />
</c:if>
<c:if test="${not empty cartData.isShippingHubOnly && cartData.isShippingHubOnly ne null}" >
	<c:set var="isSplitCartShippingHubOnly" value="${cartData.isShippingHubOnly}"/>
</c:if>
<c:set var="isSplitCartCheckoutPickup" value="false"/>
<c:if test="${SplitMixedPickupBranchCheckout && isSplitCartShippingHubOnly && (orderType eq 'PICKUP' || orderType eq 'DELIVERY')}" >
	<c:set var="isSplitCartCheckoutPickup" value="true"/>
</c:if>
<!-- Shipping Guest User Block -->
			<c:if test="${isGuestUser eq true }">
				<form:hidden path="addressId" id="addressId" value="${siteOneOrderTypeForm.addressId}" />
				<form:hidden path="contactId" value="${siteOneOrderTypeForm.contactId}" />
				<div class="row topBottom-border">
					<div  class="col-xs-12 text-red marginBottom10 same-shipping-state-error florida-error-msg hidden">
						<spring:theme code="js.briteverify.shippingStates" />
					</div>
					<div class="col-md-4 marginBottom20 no-padding-xs">
						<strong><spring:theme code="checkout.pickup.items.to.be.shipped" /></strong>
					</div>
					<div class="col-md-8 padding0">
						<div class="row lift-sec margin0">
							<div class="col-md-12 message-center">
								<span class="colored"><input type="checkbox" name="isSameAsContactInfo" class="shipmentcheck" checked /></span>
		                        <label><spring:theme code="parcel.shipping.checkbox.label" /></label>
							</div>
						</div>
						<div class="row marginTop10">
							<div class="col-xs-12 shipping-Newaddress"></div>
							<div class="col-xs-12 shipping-Newregion"></div>
							<div class="col-xs-12" id="shippingaddressdata">${addressData.line1}</div>
							<div class="col-xs-12" id="shippingaddressdat1">${addressData.line2}</div>
							<span class="col-xs-12" id="shippingcitydata">${addressData.town}</span>
							<span class="col-xs-12" id="shippingstatedata">${addressData.district}</span>
							<span class="col-xs-12" id="shippingzipdata">${addressData.postalCode}</span>
						</div>
					</div>
				</div>
			</c:if>
			<!-- ./Shipping Guest User Block -->
			<!-- Shipping B2B User Block -->
			<c:if test="${isGuestUser eq false }">
				<div class="row ${isSplitCartCheckoutPickup ? 'splitShippingSection':'topBottom-border'}">
					<c:if test="${!isSplitCartCheckoutPickup}">
		            <div class="col-md-4 marginBottom20 padding0">
		                <strong><spring:theme code="checkout.pickup.items.to.be.shipped" /></strong>
		            </div>
					</c:if>
					<c:if test="${isSplitCartCheckoutPickup}">
					<div class="bold col-md-4 f-s-16 font-arial marginBottom20 padding0 text-gray-1 hidden-xs">
						<spring:theme code="checkout.summary.shippingAddress" />:
						<form:hidden path="addressId" id="shippingAddressId" value="${siteOneOrderTypeForm.addressId}" />
					</div>
					</c:if>
		            <div class="col-xs-12 col-md-8 padding0 float-div page-choosePickupDeliveryMethodPage">
		               <c:if test="${orderType ne 'DELIVERY'}">
					    <label for="deliveryAddress"><strong> <spring:theme code="choosePickupDeliveryMethodPage.shipping.location" />:
		                </strong></label><br>
						</c:if>
		                <form:hidden path="contactId" value="${siteOneOrderTypeForm.contactId}" />
		                <form:hidden path="addressId" id="addressId" value="${siteOneOrderTypeForm.addressId}" />
		                <form:errors path="addressId" />
						<c:if test="${orderType ne 'DELIVERY'}">
							<select id="${isSplitCartCheckoutPickup ?'splitDeliveryAddress':'deliveryAddress'}" placeholder="select">
								<option value="selectDefault"><spring:theme code="choosePickupDeliveryMethodPage.select.delivery.location" /></option>
								<c:forEach var="item" items="${deliveryAddresses}" varStatus="status">
								<c:set var="nickname" value=""/>
								<c:if test="${not empty item.projectName}">
									<c:set var="nickname" value="${item.projectName}:"/>
								</c:if>
									<c:choose>
										<c:when test="${not empty siteOneOrderTypeForm.addressId && siteOneOrderTypeForm.addressId != '' && siteOneOrderTypeForm.addressId == item.id}">
											<option value="${item.id}" ${status.index == 0 ? 'selected':''}>
												${nickname}&nbsp;${item.line1},&nbsp;${item.region.isocodeShort}&nbsp;${item.postalCode}
											</option>
										</c:when>
										<c:otherwise>
											<option value="${item.id}" ${status.index == 0 ? 'selected':''}>
											${nickname}&nbsp;${item.line1},&nbsp;${item.region.isocodeShort}&nbsp;${item.postalCode}
											</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select> <br> <span class="deliveryError" id="errorDeliveryAddressRadio"></span>
						</c:if>
		                <div class="delivery-address ${orderType eq 'DELIVERY' ? ' p-t-0-imp p-b-100 p-b-30-xs split-b-b-grey-xs' : ''}"></div>
		                <div class="delivery-region hidden"></div>
		                <div class="delivery-phone hidden"></div>
						<sec:authorize var="isAdmin" access="hasAnyRole('ROLE_B2BADMINGROUP')"/>
						<c:if test="${orderType ne 'DELIVERY'}">
							<c:if test="${isAdmin or enableAddDeliveryAddress}">
								<div class="btn btn-block btn-address bold marginTop10 newaddress"><span class="glyphicon glyphicon-plus marginrgt5 address-icon"></span><spring:theme code="choosePickupDeliveryMethodPage.add.new.ship.location" /></div>
							</c:if>
						</c:if>
					<c:if test="${isSplitCartCheckoutPickup}">
						<c:if test="${orderType eq 'PICKUP'}">
							<div class="payment_cbtn col-md-5 col-xs-12 col-sm-6 no-padding-md margintop30">
								<button type="button" class="btn btn-block btn-primary bold-text splitShippingPickupSubmit" onclick="ACC.checkout.continueCheckoutProcess()"><spring:theme code="choosePickupDeliveryMethodPage.continue.to.payment" /></button>
							</div>
						</c:if>
					</c:if>
		            </div>
				</div>
				
			</c:if>
			<!-- ./Shipping B2B User Block -->
	