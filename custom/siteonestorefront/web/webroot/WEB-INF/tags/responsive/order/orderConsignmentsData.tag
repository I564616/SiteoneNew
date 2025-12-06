<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="pagination" tagdir="/WEB-INF/tags/responsive/nav/pagination" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<spring:url value="/my-account/order/" var="orderDetailsUrl" htmlEscape="false"/>
<%@ attribute name="consignments" required="false" type="de.hybris.platform.commercefacades.order.data.ConsignmentData" %>
<%@ attribute name="orderNumber" required="false" type="String" %>
<div class="order-shipment-data col-xs-12">
	<div class="col-xs-6 col-sm-5 col-md-2 pad-md-lft-30 no-padding-sm no-padding-xs shipment-number">
		<c:choose>
			<c:when test="${cmsPage.uid eq 'accountDashboardPage'}">
				<a href="${orderDetailsUrl}${unit.uid}/${ycommerce:encodeUrl(orderNumber)}" class="link-green">
					${fn:escapeXml(consignments.code)}
				</a>
			</c:when>
			<c:otherwise>
			<c:set var="shipToAccount" value="${unitId}"/>
			<c:if test="${not empty accountShiptos}">
			<c:set var="shipToSplit" value="${fn:split(accountShiptos,' ')}"/>
			<c:set var="shipToAccount" value="${shipToSplit[0]}_US"/>
			</c:if>
				<a href="${orderDetailsUrl}${shipToAccount}/${ycommerce:encodeUrl(orderNumber)}" class="link-green">
					${fn:escapeXml(consignments.code)}
				</a>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="col-md-3 col-sm-7 col-xs-7 no-padding-lg no-padding-md no-padding-sm hidden-xs hidden-sm xs-right">
		${consignments.invoiceNumber}
	</div>
	<div class="col-xs-6 col-sm-7 col-md-1 no-padding-xs no-padding-sm hidden-lg hidden-md shipment-total xs-right">
		<spring:theme code="order.form.currency" />
		<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${consignments.total}" />
	</div>
	<c:if test="${consignments.invoiceNumber ne null}">
		<div class="col-md-12 col-sm-7 col-xs-6 no-padding-lg no-padding-md no-padding-sm hidden-lg hidden-md bold pad-xs-bot-15">
			<spring:theme code="invoicedetailpage.invoice.number" />
		</div>
		<div class="col-md-12 col-sm-7 col-xs-6 no-padding-lg no-padding-md no-padding-sm hidden-lg hidden-md xs-right pad-xs-bot-15">
			${consignments.invoiceNumber}
		</div>
	</c:if>
	<div class="col-xs-6 col-sm-5 col-md-6 padding0">
		<c:set var="orderStatus" value="${fn:escapeXml(consignments.statusDisplay)}" />
		<c:set var="trackingUrl" value="${fn:escapeXml(consignments.trackingUrl)}" />
		<spring:theme code="text.account.track.order.status.display.${fn:escapeXml(orderStatus)}" />
		
		<a href="#" target="_blank" class="tms-pill ${consignments.code}_trackingUrl hidden-xs hidden-sm hidden"></a>
	</div>
	
		<div class="col-xs-6 no-padding-xs no-padding-sm xs-right hidden-md hidden-lg">
			<a href="${trackingUrl}" target="_blank" class="${consignments.code}_trackingUrl hidden tms-pill no-padding-xs no-padding-sm hidden-md hidden-lg">
				<spring:theme code="text.consignment.${orderStatus eq 'SHIPPED'? 'trackshiping' : 'trackdelivery'}" />
			</a>
		</div>
	
	<div class="col-xs-6 col-sm-6 col-md-1 hidden-xs hidden-sm no-padding-xs no-padding-sm">
		<spring:theme code="order.form.currency" />
		<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${consignments.total}" />
	</div>
</div>