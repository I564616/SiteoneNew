<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:url var="springsecurity" value="/j_spring_security_check" />
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="multiCheckout"
	tagdir="/WEB-INF/tags/responsive/checkout/multi"%>

<%@ taglib prefix="multi-checkout"
	tagdir="/WEB-INF/tags/responsive/checkout/multi"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement"
	tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="address" tagdir="/WEB-INF/tags/responsive/address"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ attribute name="containerClass" required="false" type="java.lang.String" %>

<script src="https://js.globalpay.com/4.1.3/globalpayments.js"></script>
	
<input type="hidden" id="isGPInitialized" value="0"/>
<div id="siteoneGlobalPaymentSubmit" class="${containerClass} col-sm-12 col-xs-12">
	
		<!-- <h3>Payment Details</h3> -->
		<label for="GP-card-holder-name">Cardholder Name</label>
		<div id="GP-card-holder-name"></div>
		<label for="GP-card-number">Card Number</label>
		<div id="GP-card-number"></div>
		<label for="GP-card-expiration">Expiry Date</label>
		<div id="GP-card-expiration"></div>
		<label for="GP-card-cvv">CVV</label>
		<div id="GP-card-cvv"></div>
		<div class="GP-billingAddressForm">
			<div class="form-group">
				<label for="GP-b-streetAddress">Street Address</label>
				<input type="text" class="form-control" id="GP-b-streetAddress">
			</div>
			<div class="form-group">
				<label for="GP-b-zipCode">Postal Code</label>
				<input type="text" class="form-control" id="GP-b-zipCode">
			</div>
		</div>
		<div id="GP-submit-button" ></div>

</div>


<div class="gp-card-error hidden">
	<div class="flex justify-center red">
		<div><common:exclamation /></div>
		<div class="error">Error</div>
	</div>
	<div class="paymentfail-text red">Something went wrong. Try again.</div>
</div>

