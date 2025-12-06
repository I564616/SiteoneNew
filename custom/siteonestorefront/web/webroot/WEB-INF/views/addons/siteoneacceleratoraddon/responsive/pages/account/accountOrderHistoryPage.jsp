<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="b2b-order" tagdir="/WEB-INF/tags/addons/siteoneacceleratoraddon/responsive/order" %>

<spring:htmlEscape defaultHtmlEscape="true" />

<c:set var="searchUrl" value="/my-account/orders/${unitId}?sort=${ycommerce:encodeUrl(searchPageData.pagination.sort)}"/>

<div class="col-sm-10 col-xs-9 row" style="width:100%;">
	<h1 class="headline"><spring:theme code="text.account.orderHistory" /></h1>
</div>

<b2b-order:orderListing searchUrl="${searchUrl}" messageKey="text.account.orderHistory.page"></b2b-order:orderListing>