<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<c:if test="${cmsPage.uid ne 'order' and cmsPage.uid ne 'order-approval-details'}">
	<div class="row col-md-4 col-xs-12 col-sm-12">
		<div class="orderDetailsLinks-wrapper">
			<a href="#" id="orderEmail"><span class="glyphicon glyphicon-envelope"></span> <u>
					<spring:theme code="account.overview.email.address" />
				</u></a>
			<a href="" class="printOrderDetails"><span class="glyphicon glyphicon-print"></span>&nbsp;<u>
					<spring:theme code="account.overview.print.address" />
				</u></a>
			<input type="hidden" value="${orderData.code}" id="orderCode" />
		</div>
	</div>
</c:if> 
<div class="cl"></div>
<div style="display: none;" class="pickupInformation-clone-print"></div>
<div style="display: none;" class="deliveryInformations-clone-print"></div>
<c:if test="${cmsPage.uid ne 'order' and cmsPage.uid ne 'orderSummaryPage' && cmsPage.uid ne 'orderConfirmationPage' && cmsPage.uid ne 'order-approval-details'}">
	<div class="address-box">
		<ycommerce:testId code="orderDetail_overview_section">
			<order:accountOrderDetailsOverview order="${orderData}" />
		</ycommerce:testId>
	</div>
</c:if>
<c:if test="${not empty orderData.placedBy}">
	<div class="alert alert-info order-placedby">
		<c:choose>
			<c:when test="${not empty agent}">
				<spring:theme code="text.account.order.placedBy" arguments="${orderData.placedBy}" />
			</c:when>
			<c:otherwise>
				<spring:theme code="text.account.order.placedByText" />
			</c:otherwise>
		</c:choose>
	</div>
</c:if>