<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
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
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>

<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
	<input type="hidden" id="payment" value="" >
<c:set var="showCreditCard" value="${true}" />
<c:if test="${asmAgentId ne null and asmAgentId ne ''}">
	<c:set var="showCreditCard" value="${false}" />
</c:if>
<input type="hidden" id="showCreditCard" value="${showCreditCard}" >
<input type="hidden" id="hasAsmAgentId" value="${asmAgentId}" >

<c:set var="SplitMixedPickupBranchCheckout" value="false" />
<c:if test="${not empty isSplitMixedCartEnabledBranch && isSplitMixedCartEnabledBranch ne null}" >
	<c:set var="SplitMixedPickupBranchCheckout" value="${isSplitMixedCartEnabledBranch}" />
</c:if>
<c:if test="${not empty cartData.isShippingHubOnly && cartData.isShippingHubOnly ne null}" >
	<c:set var="isSplitCartShippingHubOnly" value="${cartData.isShippingHubOnly}"/>
</c:if>
<c:set var="isSplitCartCheckoutPickupPayment" value="false"/>
<c:if test="${SplitMixedPickupBranchCheckout && isSplitCartShippingHubOnly && (orderType eq 'PICKUP'  || orderType eq 'DELIVERY')}" >
	<c:set var="isSplitCartCheckoutPickupPayment" value="true"/>
</c:if>
<input class="isSplitCartCheckoutPickupPayment" type="hidden" value='${isSplitCartCheckoutPickupPayment}'>

<div class="choose-payment-container  col-md-12 col-xs-12 col-sm-12 padding contact-data-box padbottom14 ${isMixedCartEnabled ? 'mixedcart-payment-section' : ''}">
<div class="title-bar order-confirmation-page-bottom row">
	<div class="numberCircle-div"> </div>
		<div class="title-bar order-confirmation-page-bottom">
					<div class="col-xs-9 order-summary-title-gc">
						
						<h1 class="order-confirmation-page-title">
						<spring:theme code="checkout.multi.paymentMethod"/>
						</h1>
					</div>
					
				</div>
				</div>
	<div class="row">
      <div class="choosepaymentB2b_GC">
      <c:if test="${isMixedCartEnabled eq true}">
	  <div class="hidden-md hidden-lg mixedcart-po-line-divider"></div>
      <div class="row">
	  	<div class="hidden-xs hidden-sm mixedcart-po-line-divider"></div>
	      <div class="col-md-5 marginTop20 no-padding-md mixedcart-po-container">
	      	<c:choose>
	     		<c:when test="${cartData.orderingAccount.isPONumberRequired}">
	     		<spring:message code="paymentpo.required.text" var="poPlaceholder"/>
	     		</c:when>
	     		<c:otherwise>
	     		<spring:theme code="optional.text" var="poPlaceholder"/>
	     		</c:otherwise>
	     	</c:choose>
	     	<label class="bold PO-number"><spring:message code="paymentpo.label"/></label>
	     	<input type="text" class="js-payment-po-number payment-po-number ${cartData.orderingAccount.isPONumberRequired ? '':'js-optional-po-txt-box'}" placeholder="${poPlaceholder}"/>
	     	<div class="payment-po-error-msg js-po-error-msg hidden"><common:exclamatoryIcon iconColor="#ed8606" width="20" height="20" /><spring:message code="payment.po.error"/></div>
	     	<button type="button"  data-isporequired="${cartData.orderingAccount.isPONumberRequired}" class="btn btn-primary bold-text marginTop20 js-ponumber-continue ${cartData.orderingAccount.isPONumberRequired ? '':'hidden'}  full-width">Continue</button> 
	     	</div>
     	</div>
     	</c:if>
       	<div class="row js-mixed-cart-payment-section ${isMixedCartEnabled eq true and cartData.orderingAccount.isPONumberRequired ?'hidden':''}">
     <div class="col-xs-12">
 
     </div>
     <div class="payment-methods-container">
							 <div class="radio-wrapper ${isCCDisabledAtDC? 'border-none': ' '}">
							  
							 
							 
					<div id="div_SITEONE_ONLINE_ACCOUNT" class="paymentMethod"> 
								 	<input type="radio" id="SITEONE_ONLINE_ACCOUNT" name="radio-group" value="" >
											
								    	 <div class="pay_online_label col-sm-12 col-md-12 col-xs-9"><label for="SITEONE_ONLINE_ACCOUNT"><strong><spring:theme code="checkout.multi.option.account"/></strong>
								    	<input type="hidden" id="payment_method_radio" value="SITEONE_ONLINE_ACCOUNT">	
											
										</label>
										
								    	<p class="h6 payment-method-description col-sm-12 col-md-12 col-xs-12"><spring:theme code="checkout.multi.option.description.account"/></p>
								    	
								 	</div>
								 	 <div class="payOnlineAccount-border col-md-12 col-xs-12 col-sm-12"></div>
								 	 <div class="payOnlineCreditCard-border col-md-12 col-xs-12 col-sm-12"></div>
								 				 <div class="payOnlineAccount card-selected col-md-12 col-xs-12 col-sm-12" id="POA_terms" style="display: none;">

										<div class="col-xs-12 col-md-5 col-sm-12 border-top padding-LeftZero payment-info-code payment-openToBuy" >
	                            <div class="col-xs-6 col-md-6 col-sm-6 data-title black-title pay-account-text-error">
	                            	<b><spring:theme code="paymentmethodpage.poa.opentobuy" /></b> </div>
	                            <div class="col-md-6  col-sm-6 col-xs-6 confirmation-tax-value text-right payOnline-account pay-account-text-error ">

	                          									<fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${cartData.totalPriceWithTax.value}" var="orderTotal" />
								  <input type="hidden" id="orderTotal" value="${cartData.totalPriceWithTax.value}"/>
								   <%-- <fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${custInfoData.customerCreditInfo.openToBuy}" var="openToBuy" /> --%>
									<input type="hidden" id="openToBuy" value=""/>
									<input type="hidden" id="termsCode" value=""/>
									<input type="hidden" id="creditCode" value="${creditCode}"/>
									<input type="hidden" id="maxOpenToBuy" value=""/>
									
                 		<ycommerce:testId code="orderTotal_discount_label">
                        <strong><div class="openToBuyValue">$</div></strong>
                    </ycommerce:testId>
                </div>
	                        </div>
	                        <div class="col-xs-12 col-md-6 col-sm-12 border-top padding-LeftZero payment-info-code pay-online-account-terms"  >
	                            <div class="col-xs-7 col-md-6 col-sm-2 data-title black-title">
	                            	<b><spring:theme  code="paymentmethodpage.poa.paymentterms" /></b> </div>
	                            <div class="col-md-6  col-sm-2 col-xs-5 confirmation-tax-value text-right payOnline-account  ">
								<div class="termsCodeValue"></div></div>
	                        </div>
	                        <div class="payOnline-account-failure col-xs-12 col-md-12 col-sm-12"><p class="pay-on-account-text"> <spring:theme code="text.payOnlineAccount.payment.page.max.open"/></p></div>
	                        <div class="payOnline-account-failure col-xs-12 col-md-12 col-sm-12 hide"><p class="pay-on-account-text"> <spring:theme code="text.payOnlineAccount.payment.page"/>&nbsp;$${totalPrice.value}&nbsp;<spring:theme code="text.payOnlineAccount.payment.page.line"/>&nbsp;$${custInfoData.customerCreditInfo.openToBuy}&nbsp;.&nbsp;<spring:theme code="payment.final.payment.instruction.line"/><span class="contact-branch-payment">&nbsp;<spring:theme code="payment.final.payment.instruction.line.branch"/></span></p></div>
							<div class="payOnline-account-failure-term col-xs-12 col-md-12 col-sm-12"><p class="pay-on-account-text"> <spring:theme code="text.payOnlineAccount.payment.page.code.term"/></p></div>
	                        </div>

		      
		    </div>	
			<c:if test="${currentBaseStoreId eq 'siteoneCA'}">	
				<div class="c1"></div>
				<div id="div_PAY_AT_BRANCH" class="paymentMethod">
					<input type="radio" id="PAY_AT_BRANCH" name="radio-group" value="" checked>
					<div class="pay_online_label col-sm-12 col-md-12 col-xs-9"><label for="PAY_AT_BRANCH"><strong>
								<spring:theme code="checkout.multi.option.credit" />
							</strong>
							<input type="hidden" id="payment_method_radio" value="PAY_AT_BRANCH">
						</label>
						<p class="h6 payment-method-description col-sm-12 col-md-12 col-xs-12">
							<spring:theme code="checkout.multi.option.description" />
						</p>
					</div>
				</div>
			</c:if>
		    <c:if test="${showCreditCard}">
		    <div id="div_PAY_ONLINE_WITH_CREDIT_CARD" class="paymentMethod ${isCCDisabledAtDC? ' hide ': ' '}"> 
								 	<input type="radio" id="PAY_ONLINE_WITH_CREDIT_CARD" name="radio-group" value="" >
											
								    	 <div class="pay_online_label col-sm-12 col-md-12 col-xs-9"><label for="PAY_ONLINE_WITH_CREDIT_CARD"><strong><spring:theme code="checkout.multi.option.branch"/></strong>
								    	<input type="hidden" id="payment_method_radio" value="PAY_ONLINE_WITH_CREDIT_CARD" >	
										<img class="icon-master-cards icons-view hidden-xs" src="${commonResourcePath}/images/mastercard.png" alt=""></img>
											<img class="icon-cards icons-view hidden-xs " src="${commonResourcePath}/images/america.png" alt=""></img><img
												class="icon-discover icon-b2b icons-view hidden-xs" src="${commonResourcePath}/images/discover.png" alt=""></img><img
												class="icon-visa-card   icon-b2b-guest icons-view hidden-xs" src="${commonResourcePath}/images/visa.png" alt=""></img>
										</label>
										
								    	<p class="h6 payment-method-description col-sm-8 col-md-8 col-xs-12"><spring:theme code="checkout.multi.option.description.card"/></p>
								    	<div class="cl"></div>
								    	<div class="hidden-lg hidden-md hidden-sm card-label"><img class="icon-master-card icon-mobile-card icons-view" src="${commonResourcePath}/images/mastercard.png" alt=""></img>
											<img class="icon-cards icon-mobile-card icons-view" src="${commonResourcePath}/images/america.png" alt=""></img><img
												class="icon-discover icon-mobile-card icons-view" src="${commonResourcePath}/images/discover.png" alt=""></img><img
												class="icon-visa-card icon-mobile-card icons-view" src="${commonResourcePath}/images/visa.png" alt=""></img></div>
								 	</div>
								 	 <div class="payOnlineAccount-border col-md-12 col-xs-12 col-sm-12"></div>
								 	 <div class="payOnlineCreditCard-border col-md-12 col-xs-12 col-sm-12"></div>
								 	<div class=" card-selected col-md-12 col-xs-12 col-sm-12">
								 	<c:choose>
		 	<c:when test="${currentBaseStoreId eq 'siteoneCA'}">
		 		<div class="b2b-GP-container">
				<div>
		 		<multiCheckout:paymentMethodGlobalPayment containerClass="col-md-12"/>
		 		</div>
		 		</div>
		 	</c:when>
		 	<c:otherwise>
		 	
								 	<div class=" selected_new_ewalletCard col-md-6">
								 	<span class="bold selected_new_ewalletCard_title"></span>
								 	<select id="cardselectbox" class="cardselectedValue col-md-12 col-xs-12 col-sm-12" placeholder="Select a Saved Card">
										 	 
										 	 <option value=''><spring:theme
																code="payment.select.saved.card" /></option>
											  		
											</select></div>
											
										
										
											  
													
											<div class="pay-new-credit-card col-md-4 col-xs-12 selected_new_ewalletCard">
		    	<button type="button" class="btn new-credit-card col-md-12 col-xs-12"><spring:theme code="payment.pay.with.new.card"/></button>
		    </div>
		   
		   <multiCheckout:cayanTransportIframe/>
		   </c:otherwise>
	 </c:choose>
		     </div>
		     
		<div id="paymentFailedGc" class="col-md-12">Payment Failed, please try again.</div>
		  
		 <div  id="cayanFailure" class="col-md-12 pay-on-account-text " style="font-weight: bold;color: red;"></div>


		  
								 	</div>
								 	</c:if>
		    
		    
		  <c:if test="${currentBaseStoreId ne 'siteoneCA'}">
								 	 <div class="c1"></div>
								 <div id="div_PAY_AT_BRANCH" class="paymentMethod"> 
								 	<input type="radio" id="PAY_AT_BRANCH" name="radio-group" value="" ${!showCreditCard ? 'checked':''}>
											
								    	 <div class="pay_online_label col-sm-12 col-md-12 col-xs-9"><label for="PAY_AT_BRANCH"><strong><spring:theme code="checkout.multi.option.credit"/></strong>
								    	<input type="hidden" id="payment_method_radio" value="PAY_AT_BRANCH">	
											
										</label>
										
								    	<p class="h6 payment-method-description col-sm-12 col-md-12 col-xs-12"><spring:theme code="checkout.multi.option.description"/></p>
								    	
								 	</div>
								 	
		    </div>
		</c:if>
		     
		 		 	
								 	
								 	
								 	
								 	
								 	
								 	</div>
											
		    


		     
		

								
								 
								 
								 
        											<div class="descption-payment-details col-md-12 col-xs-12 col-sm-12"><span><i><spring:theme code="payment.final.payment.instruction"/></i></span></div>
<br />
 <input type="hidden" id="cayanFailure" value="">
 <div class="add-eroor-card-ewallet hide">
			<div class="row">
				<div class="col-md-12 manage-card-ewallet-add" style="margin-top: -29px;">
				<h4 class='payment-pop-up-title headline' style="font-size: 21pt;">
					<span class='headline-text'>${ewalletAddError}</span></h4>
					<p><spring:theme code="text.ewallet.add.error.messsage"/></p>
				</div>
			</div>
		</div>
  <input type="hidden" id="asmSession" value="">




					    <div id="siteoneOnlinePaymentSubmit" class="col-md-12 col-sm-12 col-xs-12 padding0">
					    <div>
									<input type="hidden" id="paymentMethodValue"/>
									<form action="<c:url value="/checkout/multi/order-summary/view"/>"
									method="get">   
											<input type="submit" id="btnShow" value="<spring:theme code="text.review.order.messsage"/>"  class="btn btn-primary  payment-to-branch col-md-3 col-xs-12 " />
										 
											
									</form>
									<c:if test="${showCreditCard eq true}">
									<input type="submit" id="btnShow" value="<spring:theme code="text.review.order.messsage"/>" class="btn btn-primary continue-payment-btn payment-to-online col-md-3 col-xs-12"  disabled="disabled"/>
									</c:if>
                  					<input type="submit" id="btnShow" value="<spring:theme code="text.review.order.messsage"/>" class="btn btn-primary continue-payment-poa-btn payment-to-acount col-md-3 col-xs-12"  disabled="disabled"/>
									</div>
									
					        </div>
					       </div>
					       </div>
					       <div class="cl"></div>
					       </div>
					 </div>	
				</div>	
