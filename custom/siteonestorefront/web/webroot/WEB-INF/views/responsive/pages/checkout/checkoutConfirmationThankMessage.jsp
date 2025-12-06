<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>


<spring:htmlEscape defaultHtmlEscape="true" />

<c:set var="firstName" value=""/>
<c:set var="confirmationEmail" value=""/>
<c:set var="showRegistration" value="false"/>
<c:set var="isGuestUser" value="false"/>
<c:choose>
	<c:when test="${ orderData.guestCustomer eq true }">
		<c:set var="firstName" value="${orderData.b2bCustomerData.firstName}"/>
		<c:set var="confirmationEmail" value="${orderData.guestContactPerson.email}"/>
		<c:set var="isGuestUser" value="true"/>
	</c:when>
	<c:otherwise>
		<c:set var="firstName" value="${user.firstName}"/>
		<c:set var="confirmationEmail" value="${user.displayUid}"/>
	</c:otherwise>
</c:choose>


<div class="checkout-success col-md-8 print-row-12 print-p-t-10">
	  <input type="hidden" id="currentCartId" name="currentCartId" value="${user.currentCartId}">
	  <input type="hidden" id="anonymousCartId" name="anonymousCartId" value="${cartData.code}">
	 	<div class="headline print-f-w-medium print-f-s-28 print-l-h-28">${firstName},
			<c:choose>
				<c:when test="${orderData.b2bCustomerData.needsOrderApproval eq true}">
					<spring:theme code="checkout.orderConfirmation.submittedForApproval" />
				</c:when>
				<c:otherwise>
					<spring:theme code="checkout.orderConfirmation.thankYouForOrder" />
				</c:otherwise>
			</c:choose>
		</div>
		<c:choose>
			<c:when test="${orderData.b2bCustomerData.needsOrderApproval eq true}">
				<p class="thankyou-message-pickup"><spring:theme code="checkout.orderConfirmation.submittedForApproval.subheading" /></p>
			</c:when>
			<c:otherwise>
				<c:if test="${orderData.orderType eq 'DELIVERY'}">
					<!--checkoutConfirmationThankMessage.jsp delivery checkout-success -->
					<p class="thankyou-message-delivery print-text-darkgray print-f-s-14 print-f-w-bold print-m-b-0"><spring:theme code="checkout.orderConfirmation.thankYouForOrderMessage.delivery" />&nbsp;<a href="mailto:${confirmationEmail}" class="no-text-decoration print-text-mail">${confirmationEmail}</a><spring:theme code="checkout.orderConfirmation.thankYouForOrderMessage.delivery.end" /></p>
				</c:if>
				<c:if test="${orderData.orderType eq 'PICKUP'}">
					<!--checkoutConfirmationThankMessage.jsp pickup checkout-success -->
					<p class="thankyou-message-pickup print-text-darkgray print-f-s-14 print-f-w-bold"><spring:theme code="checkout.orderConfirmation.thankYouForOrderMessage.pickup.before" />&nbsp;<a href="mailto:${confirmationEmail}" class="no-text-decoration print-text-mail">${confirmationEmail}</a><spring:theme code="checkout.orderConfirmation.thankYouForOrderMessage.pickup" /></p>
				</c:if>
				<c:if test="${orderData.orderType eq 'PARCEL_SHIPPING'}">
					<!--checkoutConfirmationThankMessage.jsp pickup checkout-success -->
					<p class="thankyou-message-pickup print-text-darkgray print-f-s-14 print-f-w-bold"><spring:theme code="checkout.orderConfirmation.thankYouForOrderMessage.parcelshipping.before"/>&nbsp;<a href="mailto:${confirmationEmail}" class="no-text-decoration print-text-mail">${confirmationEmail}</a><spring:theme code="checkout.orderConfirmation.thankYouForOrderMessage.parcelshipping.end" /></p>
				</c:if>
			</c:otherwise>
		</c:choose>
		<c:if test="${isMixedCartEnabled eq true}">
			<div class="col-md-11 col-xs-12">
				<div class="row">
					<spring:theme code="checkout.orderConfirmation.thankYouForOrderMessage.mixedcart"/> <a href="mailto:${confirmationEmail}">${confirmationEmail}</a> 
					<spring:theme code="checkout.orderConfirmation.thankYouForOrderMessage2.mixedcart"/>
				</div>
			</div>
		</c:if>
	<order:giftCoupon giftCoupon="${giftCoupon}"/>
	<c:if test="${not empty guestRegisterForm}">
		<div class="checkout__new-account">
			<div class="checkout__new-account__headline"><spring:theme code="guest.register"/></div>
			<p><spring:theme code="order.confirmation.guest.register.description"/></p>
	
			<form:form method="post" modelAttribute="guestRegisterForm" class="checkout__new-account__form clearfix">
                <div class="col-sm-8 col-sm-push-2 col-md-6 col-md-push-3">
                    <form:hidden path="orderCode"/>
                    <form:hidden path="uid"/>

                    <div class="form-group">
                        <label for="email" class="control-label "><spring:theme code="register.email"/></label>
                        <input type="text" value="${fn:escapeXml(guestRegisterForm.uid)}" class="form-control" name="email" id="email" readonly>
                    </div>

                    <formElement:formPasswordBox idKey="password" labelKey="guest.pwd" path="pwd" inputCSS="password strength form-control" mandatory="true"/>
                    <formElement:formPasswordBox idKey="guest.checkPwd" labelKey="guest.checkPwd" path="checkPwd" inputCSS="password form-control" mandatory="true"/>


                    <div class="accountActions-bottom">
                        <ycommerce:testId code="guest_Register_button">
                            <button type="submit" class="btn btn-block btn-primary">
                                <spring:theme code="guest.register"/>
                            </button>
                        </ycommerce:testId>
                    </div>
                </div>
			</form:form>
		</div>
	</c:if>
</div>
<order:buttonsInConfirmationPage/>