<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:htmlEscape defaultHtmlEscape="true" />

<spring:url value="${continueUrl}" var="continueShoppingUrl" scope="session" htmlEscape="false"/>

<div class="row">
    <div class="pull-right col-xs-12 col-sm-6 col-md-5 col-lg-4">
        <div class="continue__shopping">
           <%--  <button class="btn btn-primary btn-block btn--continue-shopping js-continue-shopping-button" data-continue-shopping-url="${continueShoppingUrl}">
                <spring:theme code="checkout.orderConfirmation.continueShopping" />
            </button> --%>
        </div>
    </div>
</div>
<!-- <table class="billingInfo">
		<tr>
		<td colspan="3">
						<h3>Billing Information</h3>
						Your SiteOne branch will contact you to confirm the payment method.
		</tr>
 </table>  --> 
<%-- <div class="row col-xs-12">
<center><spring:theme code="text.account.order.disclaimertext"/></center>
</div> --%>