<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="pagination" tagdir="/WEB-INF/tags/responsive/nav/pagination" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="quote" tagdir="/WEB-INF/tags/responsive/quote" %>
<%@ taglib prefix="b2b-order" tagdir="/WEB-INF/tags/addons/siteoneacceleratoraddon/responsive/order" %>
<template:page pageTitle="${pageTitle}">
   <spring:url value="/" var="homelink" htmlEscape="false" />
	<script src="${commonResourcePath}/js/jquery-editable-select.min.js"></script>
	<link rel="stylesheet" type="text/css" media="all" href="${themeResourcePath}/css/jquery-editable-select.min.css" />
<spring:htmlEscape defaultHtmlEscape="true" /> 
<c:set var="esOrderHeader" value="" scope="application"/>
  	<c:if test="${currentLanguage.isocode eq 'es'}">
  	<c:set var="esOrderHeader" value="es-title-bar" scope="application"/>
  	</c:if>
<input type="hidden" id="unitId" name="unitId" value="${fn:escapeXml(unitId)}">
<c:set var="searchUrl" value="/my-account/orders/${unitId}?sort=${ycommerce:encodeUrl(searchPageData.pagination.sort)}"/>
<h1 class="headline"><spring:theme code="account.dashboard.orders" /></h1>
<div class="orders-tabs-container f-s-18 f-s-12-xs-pt f-s-12-sm-pt font-Geogrotesque" data-quotesFeatureSwitch="${quotesFeatureSwitch}">
   <div class="row container-lg margin0 p-l-0-xs p-b-10-xs p-t-5-xs p-l-0-sm p-b-10-sm p-t-5-sm">
      <c:if test="${quotesFeatureSwitch eq true}">
         <a class="col-md-1 padding-20 p-10-xs p-10-sm font-small-xs orders-tab" href="${homelink}my-account/my-quotes"><spring:theme code="myquotes.quotes"/></a>
      </c:if>
      <a class="col-md-1 padding-20 p-10-xs p-10-sm font-small-xs orders-tab" data-key="orderhistorypage" data-active="orderHistoryTab" href="${homelink}my-account/orders/${sessionShipTo.uid}"><spring:theme code="homepage.myOrder" /></a>
      <a class="col-md-1 padding-20 p-10-xs p-10-sm font-small-xs orders-tab" data-key="purchasedproducts" data-active="purchasedOrderTab" href="${homelink}my-account/buy-again/${sessionShipTo.uid}"><spring:theme code="text.account.buyagain"/></a>
      <c:if test="${InvoicePermission}">
         <a class="col-md-1 padding-20 p-10-xs p-10-sm font-small-xs orders-tab" data-key="invoicespage" data-active="invoicesTab" href="${homelink}my-account/invoices/${sessionShipTo.uid}"><spring:theme code="text.account.invoices"/></a>
      </c:if>
   </div>
   <div class="tab-wrapper-border"></div>
</div>
   <div class="p-t-80 p-t-30-xs orders-tab-content">
	<c:choose>
      <c:when test = "${accountpageId eq 'orderhistorypage'}">
         <b2b-order:orderListing searchUrl="${searchUrl}" messageKey="text.account.orderHistory.page" accountpageId="${accountpageId}"></b2b-order:orderListing>
      </c:when>
      <c:otherwise>
         <order:accountPurchasedProducts accountpageId="${accountpageId}"></order:accountPurchasedProducts>
      </c:otherwise>
   </c:choose>
   </div>
</template:page>
