<%@ page trimDirectiveWhitespaces="true"%>
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

<body>
	<template:page pageTitle="${pageTitle}" hideHeaderLinks="false">
	
		<div class="">
			<multiCheckout:checkoutSteps checkoutSteps="${checkoutSteps}"
				progressBarId="${progressBarId}">
				<jsp:body>  
				
	<div class="col-sm-8 col-md-4 col-xs-12 pick_delivery_summary ">
	<div class="row row pdp_whitebox col-md-12 panel-title ">
     <div class="col-xs-12 col-md-12 col-sm-12 border-top padding0">
	                            <div class="col-xs-8 col-md-8 col-sm-8 data-title black-title panel-title-order">
	                            	<span class="bold "><spring:theme code="checkout.multi.order.summary" /></span>
	                            </div>
	                            <div class="col-md-4 col-sm-4 col-sm-6 col-xs-4 data-data   panel-title-value">
	                                <span class="glyphicon glyphicon-chevron-down"></span> 
	                            </div>
	                        </div>
      
      <br>
      <br>
      <div id="collapseOne data-value-promo" class="collapsed">
      		<div class="col-xs-12 col-md-12 col-sm-12 border-top padding-LeftZero sub-total-summary">
	                            <div class="col-xs-8 col-md-9 col-sm-9 data-title black-title padding0">
	                            	<spring:theme code="order.form.subtotal" />
	                            </div>
	                            <div class="col-md-3  col-sm-3 col-xs-4 confirmation-tax-value text-right bold padding0">
	                                  <ycommerce:testId code="Order_Totals_Subtotal"><strong><format:price priceData="${cartData.subTotal}" /></strong></ycommerce:testId>
	                            </div>
	                        </div>
	                       
	                         <br>
      <br>
	                        <div class="col-xs-12 col-md-12 col-sm-12 border-top padding-LeftZero sale-tax-summary">
	                            <div class="col-xs-8 col-md-9 col-sm-9 data-title black-title padding0">
	                            	<spring:theme code="order.form.Sales" />
	                            </div>
	                          	
	                            <div class="col-md-3  col-sm-3 col-xs-4 confirmation-tax-value text-right bold padding0">
	                                 <strong> <format:price priceData="${cartData.totalTax}"/></strong>
	                            </div>
	                        
	                        </div>
	                         <br>
   
	                        
	                         <div class="col-xs-12 col-md-12 col-sm-12 border-top padding-LeftZero promo-summary ">
	                            <div class="col-xs-8 col-md-9 col-sm-9 data-title black-title padding0 ">
	                            	<spring:theme code="basket.validation.couponNotValid.promotion" />
	                            </div>
	                            <div class="col-md-3 col-sm-3 col-xs-4  confirmation-tax-value text-right bold padding0">
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
	                        </div><div class="cl"></div>
	                         <div class="col-xs-12 col-md-12 col-sm-12 border-top padding-LeftZero total-summary-order">
	                            <div class="col-xs-8 col-md-9 col-sm-9 data-title black-title padding0">
	                            	<strong><spring:theme code="text.quickOrder.page.total" /></strong>
	                            </div>
	                          	
	                            <div class="col-md-3 col-sm-3 col-xs-4 confirmation-tax-value text-right bold padding0">
	                                <strong>
	                                 <ycommerce:testId code="cart_totalPrice_label">
            	
                    	<format:price priceData="${cartData.totalPriceWithTax}" />
                	
        	</ycommerce:testId>
        	</strong>
	                            </div>
	                          </div>
	                     

      		</div>
      		   </div>
      		   <div class="summary-need-help pdp_whitebox col-md-12 col-xs-12 col-sm-12 collapsed">
      		<span class="help-text-center"><strong><spring:theme code="basket.page.needHelp" /></strong></span>
      		<br>
      		<span  class="help-text-center-number" style="font-size: 15px;"><spring:theme code="search.page.no.result" /></span><br>
      		<span class="link-help-order">or 
      		<a href="mailto:customersupport@siteone.com" ><spring:theme code="search.no.results.helpContactEmailId" /></a></span></div>
      		</div>	
<div class="choose-payment-container col-md-8 col-xs-12 col-sm-12 padding0">
        <div class="h1  choose-payment-header">
          <spring:theme code="checkout.multi.title" />
         
        </div>
        <a class="choose-payment-header-link " href="<c:url value="/checkout/multi/order-type/choose"/>"><spring:theme code="checkout.multi.title.link" /></a>	
       
      
        				<div
							class="payment-methods-container col-sm-12 col-md-12 col-xs-12">
							 <div class="radio-wrapper">
								 <c:forEach items="${siteOnePaymentMethod}"
									var="siteOnePayment">
								 	<div id="div_${siteOnePayment.code}" class="paymentMethod"> 
								 	<c:choose>
								 	<c:when test="${checked eq siteOnePayment.code}">
								 	<c:url var="check" value="checked"/>
								 	</c:when>
								 	<c:otherwise>
								 	<c:url var="check" value=""/>
								 	</c:otherwise>
								 	</c:choose>
								    	<input type="radio" id="${siteOnePayment.code}"
											name="radio-group" value="${siteOnePayment.code}" ${check}>
											
								    	 <div class="pay_online_label col-sm-12 col-md-12 col-xs-9"><label for="${siteOnePayment.code}"><strong>${siteOnePayment.name}</strong>
								    	<input type="hidden" id="payment_method_radio" value="${checked}" >	
											<img class="icon-master-cards icons-view hidden-xs" src="${commonResourcePath}/images/mastercard.png" alt=""></img>
											<img class="icon-cards icons-view hidden-xs " src="${commonResourcePath}/images/america.png" alt=""></img><img
												class="icon-discover icons-view hidden-xs" src="${commonResourcePath}/images/discover.png" alt=""></img><img
												class="icon-visa-card icons-view hidden-xs" src="${commonResourcePath}/images/visa.png" alt=""></img>
										</label>
										
								    	<p class="h6 payment-method-description col-sm-12 col-md-12 col-xs-12">${siteOnePayment.description}</p>
								    	<span class="hidden-lg hidden-md hidden-sm card-label"><img class="icon-master-card icon-mobile-card icons-view" src="${commonResourcePath}/images/mastercard.png" alt=""></img>
											<img class="icon-cards icon-mobile-card icons-view" src="${commonResourcePath}/images/america.png" alt=""></img><img
												class="icon-discover icon-mobile-card icons-view" src="${commonResourcePath}/images/discover.png" alt=""></img><img
												class="icon-visa-card icon-mobile-card icons-view" src="${commonResourcePath}/images/visa.png" alt=""></img></span>
								 	</div>
								 	 <div class="payOnlineAccount-border col-md-12 col-xs-12 col-sm-12"></div>
								 	 <div class="payOnlineCreditCard-border col-md-12 col-xs-12 col-sm-12"></div>
								 	<div class=" card-selected col-md-12 col-xs-12 col-sm-12">
								 	<c:if test="${payWithEwallet}">
								 	<div class=" selected_new_ewalletCard col-md-6">
								 	<span class="bold selected_new_ewalletCard_title"><spring:theme code="payment.select.saved.card"/></span>
								 	<select id="cardselectbox" class="cardselectedValue col-md-12 col-xs-12 col-sm-12" placeholder="Select a Saved Card">
										 	 
										 	 <option value=''><spring:theme
																code="checkout.multi.iframe.pleaseselect.option" /></option>
											  		 <c:forEach items="${eWallet}" var="shipTo">
        												<option class="ewallet_drop_down-card"
																	value="${shipTo.valutToken}">${shipTo.nickName}</option>
    												</c:forEach>
    												<c:if test="${not empty eWallet}">
													<input type="hidden" id="ewalletAvailable"
																value="ewalletAvailable"> </c:if>
													
    												
											</select></div>
											</c:if>
											<c:if test="${payWithCC}">
											  
													
											<div class="pay-new-credit-card col-md-4 col-xs-12 selected_new_ewalletCard">
		    	<button type="button" class="btn new-credit-card col-md-12 col-xs-12"><spring:theme code="payment.pay.with.new.card"/></button>
		    </div>
		    </c:if>
		    <c:if test="${!payWithEwallet && !payWithCC }">
		    <div class="descption-payment-details col-md-12 col-xs-12 col-sm-12"> <spring:theme code="payment.option.bol.uavailable"/></div>
		    </c:if>


		    </div>

		      <div class="payOnlineAccount card-selected col-md-12 col-xs-12 col-sm-12" id="POA_terms" style="display: none;">

										<div class="col-xs-12 col-md-5 col-sm-12 border-top padding-LeftZero payment-info-code payment-openToBuy" >
	                            <div class="col-xs-8 col-md-9 col-sm-9 data-title black-title pay-account-text-error">
	                            	<b><spring:theme code="paymentmethodpage.poa.opentobuy" /></b> </div>
	                            <div class="col-md-3  col-sm-3 col-xs-4 confirmation-tax-value text-right payOnline-account pay-account-text-error ">

	                          									<fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${totalPrice.value}" var="orderTotal" />
								  <input type="hidden" id="orderTotal" value="${totalPrice.value}"/>
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
	                            <div class="col-xs-7 col-md-5 col-sm-2 data-title black-title">
	                            	<b><spring:theme  code="paymentmethodpage.poa.paymentterms" /></b> </div>
	                            <div class="col-md-7  col-sm-2 col-xs-5 confirmation-tax-value text-right payOnline-account  ">
								<div class="termsCodeValue"></div></div>
	                        </div>
	                        <div class="payOnline-account-failure col-xs-12 col-md-12 col-sm-12"><p class="pay-on-account-text"> <spring:theme code="text.payOnlineAccount.payment.page.max.open"/></p></div>
	                        <div class="payOnline-account-failure col-xs-12 col-md-12 col-sm-12 hide"><p class="pay-on-account-text"> <spring:theme code="text.payOnlineAccount.payment.page"/>&nbsp;$${totalPrice.value}&nbsp;<spring:theme code="text.payOnlineAccount.payment.page.line"/>&nbsp;$${custInfoData.customerCreditInfo.openToBuy}&nbsp;.&nbsp;<spring:theme code="payment.final.payment.instruction.line"/><span class="contact-branch-payment">&nbsp;<spring:theme code="payment.final.payment.instruction.line.branch"/></span></p></div>
							<div class="payOnline-account-failure-term col-xs-12 col-md-12 col-sm-12"><p class="pay-on-account-text"> <spring:theme code="text.payOnlineAccount.payment.page.code.term"/></p></div>
	                        </div>
		    </div>

								 </c:forEach>
								 
							
							</div> 
							</div>    
							<div class="descption-payment-details col-md-12 col-xs-12 col-sm-12"><span><i><spring:theme code="payment.final.payment.instruction"/></i></span></div>
<br />
 <input type="hidden" id="cayanFailure" value="${ewalletAddError}">
 <div class="add-eroor-card-ewallet hide">
			<div class="row">
				<div class="col-md-12 manage-card-ewallet-add" style="margin-top: -29px;">
				<h4 class='payment-pop-up-title headline' style="font-size: 21pt;">
					<span class='headline-text'>${ewalletAddError}</span></h4>
					<p><spring:theme code="text.ewallet.add.error.messsage"/></p>
				</div>
			</div>
		</div>
  <input type="hidden" id="asmSession" value="${asm}">




					    <div id="siteoneOnlinePaymentSubmit" class="col-md-12 col-sm-12 col-xs-12 padding0">
					    <div>
									<input type="hidden" id="paymentMethodValue"/>
									<form action="<c:url value="/checkout/multi/order-summary/view"/>"
									method="get">   
											<input type="submit" id="btnShow" value="<spring:theme code="text.review.order.messsage"/>"  class="btn btn-primary  payment-to-branch col-md-3 col-xs-12 " />
										 
											
									</form>
									<input type="submit" id="btnShow" value="<spring:theme code="text.review.order.messsage"/>" class="btn btn-primary continue-payment-btn payment-to-online col-md-3 col-xs-12"  disabled="disabled"/>
                  					<input type="submit" id="btnShow" value="<spring:theme code="text.review.order.messsage"/>" class="btn btn-primary continue-payment-poa-btn payment-to-acount col-md-3 col-xs-12"  disabled="disabled"/>

									</div>
									<div id="iframe_Popup" class="hide">

									<br><div class="myIframe-main">
									<form:form target="myIframe">
								
								<c:if test="${isAdmin }">
								<div  class="col-md-12 col-sm-12 col-xs-12 padding0 Payment_content">
									<div id="Payment_name" class="col-md-12 col-sm-12 col-xs-12" ><span class="bold">
										<spring:theme code="checkout.multi.iframe.nickName" /></span><br>
						<input type="text" name="nickName"  disabled="disabled"
											value="${nickName}" class="card_holder_nickname col-md-12 col-sm-12 col-xs-12" placeholder="<spring:theme code="payment.text.enter.card.nickname" />" />
						</div>
										
										<span class="colored">
										<div id="savecard" > 
											<input type="checkbox" id="savecard_check" class="col-md-12 "value="${saveCard}"
												style="width: 18px; height: 18px; margin-left:0px;" />
												<spring:theme
												code="checkout.multi.iframe.saveCard" />
										</div>
										</span>
										</div>
										</c:if>
										
									<c:if test="${isAdmin ne true}">
									<br>
										<div id="Payment_team_member">
										<div class="team_member"><spring:theme code="checkout.multi.iframe.message.member" /> </div>
										</div>
										</c:if>
									</form:form>
									<br>
    							
    								
								</div>
									<iframe id="myIframe" title="myIframe" scrolling="no" class="Pop-up-myIframe" name="myIframe" onLoad="paymentContentLoad(this)" src="https://transport.merchantware.net/v4/TransportMobile.aspx?transportKey="></iframe>
										
									</div>
					        </div>
					       
					    </div>				
					</jsp:body>
			</multiCheckout:checkoutSteps>
		</div>
	</template:page>

	
</body>
