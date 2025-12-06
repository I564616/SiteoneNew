<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<spring:htmlEscape defaultHtmlEscape="true" />
<spring:url value="/my-account/masterOrder/" var="masterOrderDetailsUrl" htmlEscape="false"/>
<spring:url value="/my-account/orders" var="orderHistoryUrl" htmlEscape="false"/>
<input type="hidden" value="${orderData.invoiceCode}" id="invoiceCode"/>
<input type="hidden" value="${orderData.uid}" id="uid"/>
<section class="order-detail-header-print">
	<div class="row displayflex flex-center justify-center margin0 no-padding-hrz-xs order-detail-headline p-b-15-xs p-t-0-xs p-y-20 hidden-xs hidden-sm">
		<c:set var="orderNumber" value="" />
		<c:choose>
			<c:when test="${orderData.isHybrisOrder eq false}">
				<c:set var="orderNumber" value="${fn:escapeXml(orderData.code)}" />
			</c:when>
			<c:otherwise>
				<c:set var="orderNumber">
					<spring:theme code="accountOrderDetailsOverview.pending" />
				</c:set>
			</c:otherwise>
		</c:choose>
		<div class="col-md-8 col-xs-10 f-s-28 f-s-20-xs-px p-l-0 text-default font-Geogrotesque"><spring:theme code="checkout.orderConfirmation.orderNumberShort" arguments="${orderNumber}" /></div>
		<c:if test="${cmsPage.uid eq 'order'}">
			<div class="col-md-4 col-sm-12 col-xs-12 f-s-14 flex-center justify-flex-end orderDetailsLinks-wrapper p-r-0 p-y-10 text-right">
				<a href="" class="flex-center printOrderDetails">
					<span class="glyphicon glyphicon-print"></span>&nbsp;
					<span class="underline-text"><spring:theme code="account.overview.print.address" /></span>
				</a>
				<input type="hidden" value="${orderData.code}" id="orderCode" />
				<a href="#" class="flex-center" id="orderEmail">
					<span class="glyphicon glyphicon-envelope"></span>&nbsp;
					<span class="underline-text"><spring:theme code="account.overview.email.address" /></span>
				</a>
			</div>
		</c:if>
	</div>
<div style="display: none;" class="order-detail-headline-printview">
		<c:set var="orderNumber" value="" />
		<c:choose>
			<c:when test="${orderData.isHybrisOrder eq false}">
				<c:set var="orderNumber" value="${fn:escapeXml(orderData.code)}" />
			</c:when>
			<c:otherwise>
				<c:set var="orderNumber">
					<spring:theme code="accountOrderDetailsOverview.pending" />
				</c:set>
			</c:otherwise>
		</c:choose>
		<h1 class="headline">
			<span>
				<spring:theme code="checkout.orderConfirmation.orderNumberShort" arguments="${orderNumber}" />
			</span>
		</h1>
		<h1 style="display: none;" class="headline shipment-print">
			<span></span>
		</h1>
	</div>
	<c:if test="${orderData.isPartOfMasterHybrisOrder eq true}">
		<div class="col-md-4 col-xs-12 text-right">
			<spring:theme code="this.partof.text" />&nbsp; <a href="${masterOrderDetailsUrl}${unitId}/${ycommerce:encodeUrl(orderData.hybrisOrderNumber)}">Order# ${fn:escapeXml(orderData.hybrisOrderNumber)}</a>
			<div class="cl"></div>
			<a href="${masterOrderDetailsUrl}${unitId}/${ycommerce:encodeUrl(orderData.hybrisOrderNumber)}" class="btn btn-default pull-right marginTop10">
				<spring:theme code="view.entire.order.btn" />
			</a>
		</div>
	</c:if>
</section>