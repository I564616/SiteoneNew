<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%> 
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>LinkToPay Payment Confirmation</title>
</head>
<body>

<div class="link-to-pay max-width">
		<div class="col-md-8 col-md-offset-2 p-b-60">
			<div class="row margin-container">
				<div class="review-text text-center">Success! Thank you for <br/>submitting your payment.</div>
				<div class="order-detail text-center">
					<div class="order-text">ORDER NUMBER</div>
					<div class="order-number p-b-54">${linkOrderNumber}</div>
					<div class="order-text">ORDER AMOUNT</div>
					<div class="order-number  p-b-54"><fmt:formatNumber value="${linkOrderAmount}" type="currency" currencySymbol="$" minFractionDigits="2" maxFractionDigits="2" /></div>
					<div class="order-text">DATE AND TIME</div>
					<div class="order-number" id="cDate"></div>
					<div class="order-number p-b-54 order-time" id="cTime"></div>
					<div class="web-printshare">
					<span class="printer print-linktopay hidden-xs hidden-sm"><common:print /></span>
					<span class="envelope share-email-linktopay"><common:envelope /></span>
					</div>

				</div>
			</div>
		</div>
	</div>
	
	<div class="share-by-mail hidden">
	<div class="row font-black">
	<input type="hidden" id="txtareemail" name="txtareemail" value="">
		<div class="col-md-12 col-xs-12 font-Geogrotesque-bold textTitle">Share by Email</div>
		<div class="col-md-12 col-xs-12 textDetail">Send a copy of your payment confirmation by entering an email address or multiple email addresses below.</div>
		<div class="col-md-12 col-xs-12 textDetail">To:</div>
		<div class="col-md-12 col-xs-12"><textarea class="form-control linkToPayTextarea" id="linktopay-share-email" type="text" placeholder="Enter email addresses separated by commas." rows="10"></textarea></div>
		<div class="col-md-12 col-xs-12"><button class="return-S1-home bg-green white linktopay-email">Send Email</button>
		</div>
	</div>
	</div>
</body>
</html>