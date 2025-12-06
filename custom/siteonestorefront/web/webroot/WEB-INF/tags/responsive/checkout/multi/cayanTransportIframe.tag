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


<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>


<div id="iframe_Popup">

									<br><div class="myIframe-main" class="col-md-8" >
									<form:form target="myIframe">
								
								<c:if test="${isAdmin }">
								<div  class="col-md-12 col-sm-12 col-xs-12  Payment_content padding0">
									<div id="Payment_name" class="col-md-12 col-sm-12 col-xs-12 m-t-30-xs" ><span class="bold">
										<spring:theme code="checkout.multi.iframe.nickName" /></span><br>
										<div class="col-md-6 col-sm-7 col-xs-12 no-padding-lft-md no-padding-lft-xs">
						<input type="text" name="nickName"  disabled="disabled"
											value="${nickName}" class="card_holder_nickname form-control" placeholder="<spring:theme code="payment.text.enter.card.nickname" />" />
						
										</div>
										<span class="colored col-md-5 col-xs-12 savedB2b save-card-bg no-padding-lft-xs">
										<div id="savecard" > 
											<input type="checkbox" id="savecard_check" class="col-md-12 "value="${saveCard}"
												style="width: 18px; height: 18px; margin-left:10px;" />
												<spring:theme
												code="checkout.multi.iframe.saveCard" />
										</div>
										</span>
										</div>
										</div>
										</c:if>
										
									<c:if test="${isAdmin ne true}">
									<br>
										<div id="Payment_team_member">
										<div class="team_member col-md-12 col-xs-12"><spring:theme code="checkout.multi.iframe.message.member" /> </div>
										</div>
										</c:if>
									</form:form>
									<br>
    							
    								
								</div>
									<iframe id="myIframe" title="myIframe"  class="Pop-up-myIframe col-md-12" name="myIframe" onload="paymentContentLoadB2B(this)" src="https://transport.merchantware.net/v4/TransportMobile.aspx?transportKey="></iframe>
										
									</div>