<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ attribute name="order" required="true" type="de.hybris.platform.commercefacades.order.data.AbstractOrderData" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<spring:htmlEscape defaultHtmlEscape="true" />
<spring:url value="/my-account/masterOrder/" var="masterOrderDetailsUrl" htmlEscape="false"/>
<spring:htmlEscape defaultHtmlEscape="true" />
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:choose>
	<c:when test="${cmsPage.uid eq 'order-approval-details'}">
		<c:set var="approvalOrderPaymentSection" value="col-md-12"/>
		<div class="cl"></div>
		<div class="row hidden print-visible print-m-t-15">
			<div class="col-sm-12 print-f-s-10 bold-text text-right print-text-uppercase">
				<span class="print-text-gray">list total</span>&nbsp;&nbsp;${fn:escapeXml(order.subTotal.formattedValue)}
			</div>
		</div>
	</c:when>
	<c:otherwise>
	<c:set var="approvalOrderPaymentSection" value=""/>
	</c:otherwise>
</c:choose>
<c:if test="${not empty order}">
    <div class="account-orderdetail m-t-20 print-m-t-15">
        <div class="col-xs-12 padding0 order-confirmation print-m-t-10">
			<c:if test="${cmsPage.uid eq 'order'}">
<div class="row margin0">
<div class="col-md-5 m-y-20 m-b-0-xs m-b-0-sm font-Geogrotesque text-default f-s-20 f-s-16-xs-px f-s-16-sm-px padding0 b-b-grey b-b-0-xs b-b-0-sm payment-detailHeader-print">
<spring:theme code="checkout.confirmation.payment.method" />
</div>
<div class="col-md-5 col-md-offset-2 m-y-20 font-Geogrotesque text-default f-s-20 padding0 b-b-grey payment-detailHeader-print hidden-xs hidden-sm">
<spring:theme code="orderconfirmation.checkout.multi.paymentMethod.text.paymentdetails" />
</div>
</div>
</c:if>
        <c:if test="${cmsPage.uid eq 'orderConfirmationPage'}">
            <div class="col-xs-12 margin20 padding0 payment-detailHeader-print print-hidden">
                <h1 class="order-confirmation-page-title print-p-a-10 print-left-0 print-bg-offgray print-f-s-16 print-f-w-bold print-text-uppercase">
                    <spring:theme code="orderconfirmation.checkout.multi.paymentMethod.text.paymentdetails" />
                </h1>
            </div>
        </c:if>	
<div class="print-visible print-no-break">
			<div class="row hidden print-margin print-visible">
				<div class="col-md-5 m-y-20 print-m-t-15 print-m-b-10 m-b-0-xs font-Geogrotesque text-default f-s-20 padding0 b-b-grey hidden-xs"><!--accountOrderDetailOrderTotals.tag -->
					<spring:theme code="checkout.confirmation.payment.method" />
				</div>
				<div class="col-md-5 print-row-6 print-m-t-15 print-m-b-10 col-md-offset-1 m-y-20 font-Geogrotesque text-default f-s-20 padding0 b-b-grey hidden-xs">
					<spring:theme code="orderconfirmation.checkout.multi.paymentMethod.text.paymentdetails" />
				</div>
			</div>
            <div class="row">
               
                <c:choose>
                <c:when test = "${cmsPage.uid eq 'order' or cmsPage.uid eq 'orderConfirmationPage'}"> 
                	<div class="col-xs-12 orderTotals-summaryWrapper">
                </c:when>
                <c:otherwise>
                	<div class="orderTotals-summaryWrapper ${approvalOrderPaymentSection}">
                </c:otherwise>
                </c:choose>
                
                <c:if test="${not empty order.appliedOrderPromotions}">
				    <div class="cartproline hidden-xs hidden-sm hidden-md hidden-lg">
				        <spring:theme code="basket.received.promotions" />
				        <ycommerce:testId code="cart_recievedPromotions_labels">
				            <c:forEach items="${order.appliedOrderPromotions}" var="promotion">
				                <div class="promotion">${promotion.description}</div>
				            </c:forEach>
				        </ycommerce:testId>
				    </div>
				</c:if>
				<c:if test="${cmsPage.uid eq 'order'}">
					<order:orderTotalsItem order="${order}" />
				</c:if>
				<c:if test="${cmsPage.uid eq 'orderConfirmationPage'}">
					<order:orderConfirmation order="${order}" />
				</c:if>
                    
                </div>
</div>              
                <c:if test = "${cmsPage.uid eq 'order'}"> 
	                <div class="col-md-12 disclaimerSection print-margin print-no-break" >
					    <div style="display:none" class="regulatedItems-disclaimertext-print print-m-t-10">
							<p>
								<spring:theme code="text.account.order.regulatedItems.disclaimertext"/>
							</p>
							<p style="color:#000000 !important; position:relative; top:-5px;" class="f-s-18 font-Geogrotesque order-details-trackInfo-print">
							</p>
						</div>
						
						<c:if test="${order.orderType eq 'PICKUP'}"> 
						     <p class="pickup-disclaimertext-print">
							     <spring:theme code="text.account.order.pickup.disclaimertext"/>
						     </p>
						</c:if>
						<c:if test="${order.orderType eq 'DELIVERY'}"> 
						     <p class="delivery-disclaimertext-print">
						     	<spring:theme code="text.account.order.delivery.notetocustomer"/>
						     </p>
						     <p class="delivery-disclaimertext-print">
						     	<spring:theme code="text.account.order.delivery.disclaimertext"/>
						     </p>
						</c:if>
						<p class="regulatedItems-disclaimertext">
							<spring:theme code="text.account.order.regulatedItems.disclaimertext"/>
						</p>
					</div>
				</c:if>
				
				 <div class="col-md-12 col-xs-12 marginTop35 text-right${approvalOrderPaymentSection==''? '' : ' print-hidden'}">  

<c:if test="${orderData.isPartOfMasterHybrisOrder eq true}">
<spring:theme code="this.partof.text"/>&nbsp; <a href="${masterOrderDetailsUrl}${unitId}/${ycommerce:encodeUrl(orderData.hybrisOrderNumber)}">Order# ${fn:escapeXml(orderData.hybrisOrderNumber)}</a>
<div class="cl"></div>
					<a href="${masterOrderDetailsUrl}${unitId}/${ycommerce:encodeUrl(orderData.hybrisOrderNumber)}" class="btn btn-default pull-right marginTop10">
<spring:theme code="view.entire.order.btn" />
</a>
</c:if>

</div>
             
    </div>
    </div>
    </div>
</c:if>