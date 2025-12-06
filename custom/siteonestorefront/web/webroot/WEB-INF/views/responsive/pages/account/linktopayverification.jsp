<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%> 
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<c:set var="ddcUrl" value="<%=de.hybris.platform.util.Config.getParameter(\"kount.ddc.url\")%>"/>
<c:set var="merchantId" value="<%=de.hybris.platform.util.Config.getParameter(\"payments.fraud.client.id\")%>"/>
<input type="hidden" id="kountSessionIdLink" name="kountSessionIdLink" value="">
<input type="hidden" id="orderNumberLink" name="orderNumberLink" value="${orderNumber}">
<input type="hidden" id="orderAmountLink" name="orderAmountLink" value="${orderAmount}">

<template:page pageTitle="${pageTitle}" hideHeaderLinks="true">


<c:if test="${tokenValid}">
<div class="step-body kaxsdc token-valid" style="opacity: 0;" data-event="load">
		<div class="link-to-pay max-width">
		<div class="col-md-8 col-md-offset-2 p-b-60">
			<div class="row margin-container">
				<div class="review-text text-center">Please review the amount below and click the button to continue.</div>
				<div class="order-detail text-center">
					<div class="order-text">ORDER NUMBER</div>
					<div class="order-number p-b-54">${orderNumber}</div>
					<div class="order-text">ORDER AMOUNT</div>
					<div class="order-number"><fmt:formatNumber value="${orderAmount}" type="currency" currencySymbol="$" minFractionDigits="2" maxFractionDigits="2" /></div>
				</div>
				<div class="col-md-12 text-center p-t-26"><button class="pay-now" id="linkToPayNow" onclick="ACC.global.linkToPayClick()">Pay Now</button></div>
			</div>
		</div>
	</div>
	</div>
</c:if>

<c:if test="${!tokenValid}">
    <div class="link-to-pay max-width">
		<div class="col-md-8 col-md-offset-2 p-b-60">
			<div class="row">
			<div class="col-md-12 p-t-260 link-to-pay-error">
				<div class="row">
					<div class="col-md-1 text-center linktopayexclaim"> <common:exclamation /> </div>
					<div class="col-md-11 text-center token-expired-text p-l-0 red">Your payment token has expired. Please visit your SiteOne branch to make a payment.</div>
				</div>
			</div>
			<div class="col-md-12 text-center p-t-60">
			<c:url var="homePage" value="/" />
			<a href="${homePage}">
			<button class="return-to-home-error bg-green white font-16">Return to SiteOne Home</button>
			</a>
			</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${tokenValid}">
	<div class="link-to-pay max-width cayan-iframe">
		<div class="col-md-8 col-md-offset-2 p-b-60">
			<div class="row margin-container cayan-iframe">
				<div class="review-text text-center">Your Order Total: <fmt:formatNumber value="${orderAmount}" type="currency" currencySymbol="$" minFractionDigits="2" maxFractionDigits="2" /></div>
				<div class="order-detail text-center linktopay-iframe"></div>
			</div>
		</div>
	</div>
</c:if>

<div class="payment-not-process text-center fix-hw payment-error hidden">
		<div class="flex justify-center red">
			<div><common:exclamation /></div>
			<div class="error">Error</div></div>
		<div class="paymentfail-text red">The payment cannot be processed at this time.</div>
		<div class="visit-branch brown">Please visit your SiteOne branch to make a payment.</div>
		<c:url var="homePageUrl" value="/" />
		<a href="${homePageUrl}">
		<button class="return-S1-home bg-green white">Return to SiteOne Home</button>
		</a>
	</div>
	
<div class="link-to-pay max-width cayan-payment-failed">
		<div class="col-md-8 col-md-offset-2 p-b-60">
			<div class="col-md-12 p-t-260 link-to-pay-error">
				<div class="row">
					<div class="col-md-1 text-center"> <common:exclamation /> </div>
					<div class="col-md-11 text-center token-expired-text p-l-0 red">Sorry, we were not able to process your payment for Order #<span class="order-id">${orderNumber}</span>.</div>
					<div class="col-md-8 col-md-offset-2 try-again-text text-center brown">Please try again or reach out to your branch for help.</div>
					<div class="col-md-2 col-md-offset-5 m-t-10 try-again-btn text-center hidden"><button class="btn btn-default" onclick="location.reload();">Try Agian</button></div>
				</div>
			</div>
			
		</div>
	</div>	
	
<script type='text/javascript' src='${ddcUrl}${merchantId}'> </script>
	<script type='text/javascript'>
	$(document).ready(function () {
	if (!sessionStorage.MercSessId)
	{
        var client=new ka.ClientSDK();
        
     	// The auto load looks for an element with the 'kaxsdc' class and
        // data-event equal to a DOM event (load in this case). Data collection begins
        // when that event fires on that element--immediately in this example
        client.autoLoadEvents();
 
       // OPTIONAL
        client.setupCallback(
            {
                // fires when collection has finished - this example would not enable the 
                // login button until collection has completed
                'collect-end':
                    function(params) 
                    {
                		debugger
                		if (typeof(Storage) !== "undefined") 
                		{
                			sessionStorage.MercSessId = params.MercSessId;
                			$("#kountSessionIdLink").val(sessionStorage.MercSessId);
              			}
                	}
            });
        // END OPTIONAL SECTION
        // The auto load looks for an element with the 'kaxsdc' class and
        // data-event equal to a DOM event (load in this case). Data collection begins
        // when that event fires on that element--immediately in this example
        client.autoLoadEvents();
	}
	$("#kountSessionIdLink").val(sessionStorage.MercSessId);
    });
    </script> 

</template:page>