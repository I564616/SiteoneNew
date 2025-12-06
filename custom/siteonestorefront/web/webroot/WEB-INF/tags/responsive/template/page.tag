<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true"%>
<%@ attribute name="pageTitle" required="false" rtexprvalue="true"%>
<%@ attribute name="pageCss" required="false" fragment="true"%>
<%@ attribute name="pageScripts" required="false" fragment="true"%>
<%@ attribute name="hideHeaderLinks" required="false"%>
<%@ attribute name="showCheckoutSteps" required="false"%>

<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/responsive/common/header"%>
<%@ taglib prefix="footer" tagdir="/WEB-INF/tags/responsive/common/footer"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart" %>
<%@ taglib prefix="multi-checkout" tagdir="/WEB-INF/tags/responsive/checkout/multi"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<template:master pageTitle="${pageTitle}">

	<jsp:attribute name="pageCss">
		<jsp:invoke fragment="pageCss" />
	</jsp:attribute>

	<jsp:attribute name="pageScripts">
		<jsp:invoke fragment="pageScripts" />
	</jsp:attribute>

	<jsp:body>
	    <c:if test = "${cmsPage.uid ne 'order' && cmsPage.uid ne 'choosePickupDeliveryMethodPage' && cmsPage.uid ne 'orderSummaryPage' && cmsPage.uid ne 'order-approval-details'}">    
		<div class="branding-mobile hidden-xs hidden-sm hidden-md hidden-lg">
			<div class="js-mobile-logo">
				<%--populated by JS acc.navigation--%>
			</div>
			<div class="site-logo-print">
				<common:headerIcon iconName="logoPrintMode" iconColor="#414244" iconColorSecond="#77A12E" iconFill="none" width="162" height="45" viewBox="0 0 162 45" />
			</div>
			<c:if test="${consignments.invoiceNumber ne null}">
					<div class="col-xs-6 col-md-3 padding-md-10 padding-sm-10 border-light-grey">
						<span class="bold"><spring:theme code="invoicedetailpage.invoice.number"/>:</span>
						<span class="show-xs pad-sm-lft-10 pad-md-lft-10">${consignments.invoiceNumber}</span>
					</div>
			</c:if>
		</div>
		</c:if>
		 <c:if test = "${cmsPage.uid eq 'order' &&  cmsPage.uid ne 'choosePickupDeliveryMethodPage' && cmsPage.uid ne 'orderSummaryPage' && cmsPage.uid ne 'order-approval-details'}">    
		<div class="branding-mobile hidden-xs hidden-sm hidden-md hidden-lg order-details-headerBgPrint">
			<div class="js-mobile-logo">
				<%--populated by JS acc.navigation--%>
			</div>
			<div class="order-detailsHeader-print">
				<div class="site-logo-print" style="margin: auto 0;">
					<span><common:globalIcon iconName="logoPrintMode" iconFill="none" width="162" height="45" viewBox="0 0 162 45"/></span>

				</div>
				<div class="order-invoice-print" style="margin: auto 0;"></div>
			</div>	
		</div>
		</c:if>
		<main data-currency-iso-code="${currentBaseStoreId eq 'siteoneCA' ? 'CAD' : 'USD'}" role="main">
			<spring:theme code="text.skipToContent" var="skipToContent" />
			<a href="#skip-to-content" class="skiptocontent" data-role="none">${skipToContent}</a>
			<spring:theme code="text.skipToNavigation" var="skipToNavigation" />
			<a href="#skiptonavigation" class="skiptonavigation" data-role="none">${skipToNavigation}</a>


			<c:if test="${not fn:contains(header['User-Agent'],'SiteOneEcomApp')}"> 
				<header:header hideHeaderLinks="${hideHeaderLinks}" />
			</c:if>
			<c:if test="${fn:contains(header['User-Agent'],'SiteOneEcomApp')}">
				<input type="hidden" id="User_Agent" value="SiteOneEcomApp" />
			</c:if>
			
			
			<a id="skip-to-content"></a>
			
			<div data-cmsPage-uid="${cmsPage.uid}" class="${(cmsPage.uid ne 'accountDashboardPage' && cmsPage.uid ne 'productDetails') ? 'p-t-20-xs p-t-30-sm ' : ''}main-container ${not product.multidimensional?'pdpredesign-container':'pdpredesign-container-variant'}">
			<c:set var="globalClasses" value="${globalContainerClasses}"/>
			<c:if test="${cmsPage.uid eq 'siteonehomepage'}">
				<c:set var="globalClasses" value=""/>
			</c:if>
			
			<div class="${globalClasses} no-class ${cmsPage.uid eq 'order' ? 'print-p-a-0 print-m-a-0' : ''}">
			<!-- <div class="container-lg container-fluid"> -->
				<c:if test="${showCheckoutSteps}">
					<multi-checkout:checkoutSteps checkoutSteps="${checkoutSteps}" progressBarId="${progressBarId}">
					</multi-checkout:checkoutSteps>
				</c:if>
				<common:globalMessages />
				<cart:cartRestoration />
				<jsp:doBody />
				
			</div>
			</div>
 			<c:if test="${not fn:contains(header['User-Agent'],'SiteOneEcomApp') && cmsPage.uid ne 'requestaccount' && cmsPage.uid ne 'requestAccountOnlineAccess'}"> 
				<footer:footer />
			</c:if>
		</main>

	</jsp:body>

</template:master>
