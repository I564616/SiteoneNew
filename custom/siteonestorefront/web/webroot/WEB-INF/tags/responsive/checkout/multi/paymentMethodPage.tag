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


<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>


	
		
		  
		
<div class="choose-payment-container col-md-12 col-xs-12 col-sm-12 contact-data-box">
     
				
				<div class="title-bar order-confirmation-page-bottom row">
		<div class="numberCircle-div"></div>
		<div class="title-bar order-confirmation-page-bottom">
					<div class="col-xs-8 order-summary-title-gc ">
						<h1 class="order-confirmation-page-title"><spring:theme code="checkout.multi.paymentMethod"/></h1>
					</div>
					
					
					
				</div>
				</div>
       

 	
 	

	<div id="paymentFailedGc" class="marginTop35 col-md-12">
		<spring:theme code="checkout.multi.paymentMethod.failed.gc" />
	</div>
	<button type="button"
		class="btn new-credit-card-guest hide col-md-8 col-xs-12">
		<spring:theme code="payment.pay.with.new.card" />
	</button>
	<div id="siteoneOnlinePaymentSubmit"
		class="col-md-12 col-sm-12 col-xs-12 padding0 ${isCCDisabledAtDC? ' hide ': ' '}">
		
		
		 <c:choose>
		 	<c:when test="${currentBaseStoreId eq 'siteoneCA'}">
		 		<div id="iframe_Popup_guest" class="guest-GP-container flex justify-center" style="height: 900px;">
		 		<div>
		 		<multiCheckout:paymentMethodGlobalPayment containerClass="col-md-12"/>
		 		</div>
		 		</div>
		 	</c:when>
		 	<c:otherwise>
 			<div id="iframe_Popup_guest" class="col-md-8" style="height: 900px;">
			<c:if test="${isMixedCartEnabled eq true}">
			<div class="row no-margin">
				<div class="col-md-5 marginTop20 no-padding-md">
					<c:choose>
						<c:when test="${cartData.orderingAccount.isPONumberRequired}">
							<spring:message code="paymentpo.required.text"
								var="poPlaceholder" />
						</c:when>
						<c:otherwise>
							<spring:theme code="optional.text" var="poPlaceholder" />
						</c:otherwise>
					</c:choose>
					<label class="bold"><spring:message code="paymentpo.label" /></label>
					<input type="text"
						class="js-payment-po-number payment-po-number ${cartData.orderingAccount.isPONumberRequired ? '':'js-optional-po-txt-box'}"
						placeholder="${poPlaceholder}" />
					<div class="payment-po-error-msg js-po-error-msg hidden">
						<common:exclamatoryIcon iconColor="#ed8606" width="20" height="20" />
						<spring:message code="payment.po.error" />
					</div>
					<button type="button"
						data-isporequired="${cartData.orderingAccount.isPONumberRequired}"
						class="btn btn-primary marginTop20 js-ponumber-continue ${cartData.orderingAccount.isPONumberRequired ? '':'hidden'}  full-width">Continue</button>
				</div>
			</div>
		</c:if>
			<br>
			<div class="myIframe-main"></div>
			<iframe id="myIframe" title="myIframe" class="Pop-up-myIframe"
				name="myIframe" style="width: 100%; height: 95%; margin-left: 0px;"
				onLoad="paymentContentLoad(this)"
				src="https://transport.merchantware.net/v4/TransportMobile.aspx?transportKey="></iframe>
		</div>
		</c:otherwise>
	 </c:choose>
	</div>
</div>




	

