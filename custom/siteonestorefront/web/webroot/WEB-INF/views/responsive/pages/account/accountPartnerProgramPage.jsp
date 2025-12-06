<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="content" tagdir="/WEB-INF/tags/responsive/content" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<template:page pageTitle="${pageTitle}">
	<c:set var = "userAgent" value = "${fn:toLowerCase(header['User-Agent'])}"/>
	<c:set var = "mobileFlag" value = "false"/>
	<c:if test="${fn:contains(userAgent, 'iphone') || fn:contains(userAgent, 'android')}">
    	<!-- If Mobile -->
    	<c:set var = "mobileFlag" value = "true"/>
	</c:if>
	<c:set var="enrolled" value="false" />
	<c:set var="minthreshold" value="5000" />
	<c:set var="threshold" value="0" />
	<c:set var="tapoint" value="0" />
	<c:set var="tvapoint" value="0" />
	<c:set var="apoint" value="0" />
	<c:set var="epoint" value="0" />
	<c:set var="ppoint" value="0" />
	<c:set var="ppointly" value="0" />
	<c:set var="thrValue" value="0" />
	<c:set var="tapointFlag" value="false" />
	<c:if test="${isPartnerProgramEnrolled eq true}">
		<c:set var="enrolled" value="true" />
		<c:choose>
		    <c:when test="${currentBaseStoreId eq 'siteoneCA'}">
		      <c:choose>
			<c:when test="${not empty rewardPoints && rewardPoints ne null}">
				<c:set var="threshold" value="${thrValue*100/minthreshold}" />
				<c:set var="tapoint" value="${rewardPoints.totalAvailablePoints}" />
				<c:set var="tvapoint" value="${totalValuePoints}" />
				<c:set var="apoint" value="${rewardPoints.availablePoints}" />
				<c:set var="epoint" value="${rewardPoints.expiringPoints}" />
				<c:set var="ppoint" value="${rewardPoints.pendingPoints}" />
				<c:set var="ppointly" value="${rewardPoints.lastYearPendingPoints}" />				
			</c:when>
			<c:otherwise>
				<c:set var="tapointFlag" value="true" />
				<c:set var="tapoint" value="Pending" />
			</c:otherwise>
			</c:choose>
		    </c:when>
		    <c:otherwise>
		    <c:choose>
			<c:when test="${not empty loyaltyPartnerPoints && loyaltyPartnerPoints ne null}">
				<c:set var="thrValue" value="${loyaltyPartnerPoints.minimumSpentThreshold}" />
				<c:set var="threshold" value="${thrValue*100/minthreshold}" />
				<c:set var="tapoint" value="${loyaltyPartnerPoints.totalAvailablePoints}" />
				<c:set var="tvapoint" value="${loyaltyPartnerPoints.value}" />
				<c:set var="apoint" value="${loyaltyPartnerPoints.availablePoints}" />
				<c:set var="epoint" value="${loyaltyPartnerPoints.expiringPoints}" />
				<c:set var="ppoint" value="${loyaltyPartnerPoints.pendingPoints}" />
				<c:set var="ppointly" value="${loyaltyPartnerPoints.lastYearPendingPoints}" />				
			</c:when>
			<c:otherwise>
				<c:set var="tapointFlag" value="true" />
				<c:set var="tapoint" value="Pending" />
			</c:otherwise>
			</c:choose>
			</c:otherwise>
			
		</c:choose>
	</c:if>
	<div class="row text-center text-default partner-section" data-isPartnerProgramEnrolled="${isPartnerProgramEnrolled}" data-pilot="${true}" data-enrolled="${enrolled}" data-threshold="${threshold}" data-loyaltyPartnerPoints="${loyaltyPartnerPoints}" data-isPartnerProgramAdminEmail="${isPartnerProgramAdminEmail}" data-isPartnersProgramRetail="${isPartnersProgramRetail}">
		<div class="col-md-12 flex-center no-padding-xs partner-banner partner-header">
			<img class="header-image partner-dashboard hidden-md hidden-lg" src="/_ui/responsive/theme-lambda/images/partner_background.jpg" alt="partners-program-banner" title="partners-program-banner" />
			<img class="header-image hidden-xs hidden-sm" src="/_ui/responsive/theme-lambda/images/partner_banner_point.jpg" alt="partners-program-banner" title="partners-program-banner" />
			<div class="row p-b-30 margin0 font-size-14 f-s-12-xs-pt font-Geogrotesque partner-overlay">
				<div class="col-md-offset-0 col-md-12 col-xs-offset-2 col-xs-8 m-t-15-xs"><img src="/_ui/responsive/theme-lambda/images/partner_logo.png" alt="partners-program" title="partners-program" /></div>
				<div class="col-md-3 col-xs-12 hidden-sm hidden-md hidden-lg pad-xs-bot-15 marginTop10 text-center">
					<div class="hidden">								
						<cms:pageSlot position="Promotion_SlotA" var="feature" element="div">
							<cms:component component="${feature}" />
							<c:set var="partnerMessage" value="${feature.content}" />										
						</cms:pageSlot>								
					</div>
					
					<content:contentPPDEnroll partnerMessage="${partnerMessage}" enrolled="${isPartnerProgramEnrolled}" ppemail="${isPartnerProgramAdminEmail}" retail="${isPartnersProgramRetail}" ppurl="${isPartnerProgramUrl}"/>
				
				</div>
				<div class="col-md-3 col-xs-12 m-t-20-xs text-left bold ${currentBaseStoreId eq 'siteoneCA' ? 'hidden' : ''}">
					<c:choose>
						<c:when test="${threshold >= 100}">
							<p class="text-green m-b-5"><fmt:formatNumber value="${minthreshold}" type="currency" currencySymbol="$" minFractionDigits="0" maxFractionDigits="0" />&nbsp;<spring:theme code="partners.point.of" />&nbsp;<fmt:formatNumber value="${minthreshold}" type="currency" currencySymbol="$" minFractionDigits="0" maxFractionDigits="0" /></p>
							<div class="marginBottom10 full-width bg-primary partner-threshold">
								<p class="pad-lft-15 p-t-10 f-s-18"><spring:theme code="partners.point.points" /></p>
								<img src="/_ui/responsive/theme-lambda/images/partner_lock.svg" width="53" alt="partners-unlock" />
							</div>
							<fmt:formatNumber value="${minthreshold}" type="currency" currencySymbol="$" minFractionDigits="0" maxFractionDigits="0" />&nbsp;<spring:theme code="partners.point.thresholdmet" />
						</c:when>
						<c:otherwise>
							<p class="text-green m-b-5"><fmt:formatNumber value="${thrValue}" type="currency" currencySymbol="$" minFractionDigits="0" maxFractionDigits="0" />&nbsp;<spring:theme code="partners.point.of" />&nbsp;<fmt:formatNumber value="${minthreshold}" type="currency" currencySymbol="$" minFractionDigits="0" maxFractionDigits="0" /></p>
							<div class="marginBottom10 full-width bg-white partner-threshold">
								<span class="bg-primary" style="width: ${threshold}%;"></span>
							</div>
							<spring:theme code="partners.point.progress" />&nbsp;<fmt:formatNumber value="${minthreshold}" type="currency" currencySymbol="$" minFractionDigits="0" maxFractionDigits="0" />&nbsp;<spring:theme code="partners.point.threshold" />
							<div class="info-tooltip js-info-tootip p-l-5" rel="custom-tooltip">
								<div class="tooltip-content hide">
									<p><spring:theme code="partners.point.make.minimum" /> <fmt:formatNumber value="${minthreshold}" type="currency" currencySymbol="$" minFractionDigits="0" maxFractionDigits="0" />&nbsp;<spring:theme code="partners.point.ineligible" /></p>
								</div>
								<img src="/_ui/responsive/theme-lambda/images/info_icon.svg" width="14" alt="partners-threshold" />
							</div>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="col-md-3 col-xs-6 marginTop20 text-left xs-center bold b-l-green b-0-xs">
					<p class="margin0 text-green f-s-40 f-s-24-xs-pt">
						<c:choose>
							<c:when test="${tapointFlag}">
								${tapoint}			
							</c:when>
							<c:otherwise>
								<fmt:formatNumber value="${tapoint}" type="number" minFractionDigits="0" maxFractionDigits="0" />
							</c:otherwise>
						</c:choose>
					</p>
					<spring:theme code="partners.point.total.available" />
					<div class="info-tooltip js-info-tootip p-l-5" rel="custom-tooltip">
						<div class="tooltip-content hide">
							<p><spring:theme code="partners.point.your.total" />.</p>
						</div>
						<img src="/_ui/responsive/theme-lambda/images/info_icon.svg" width="14" alt="TOTAL AVAILABLE POINTS" />
					</div>
				</div>
				<div class="col-md-3 col-xs-6 marginTop20 text-left xs-center bold b-l-green">
					<p class="margin0 text-green f-s-40 f-s-24-xs-pt"><fmt:formatNumber value="${tvapoint}" type="currency" currencySymbol="$" minFractionDigits="2" maxFractionDigits="2" /></p>
					<spring:theme code="partners.point.total.value" /><span class="hidden-xs"> <spring:theme code="partners.point.ofs" /> <spring:theme code="partners.point.available" /></span>
				</div>

				<div class="col-md-3 hidden-xs marginTop10 text-center ${currentBaseStoreId eq 'siteoneCA' ? 'col-md-offset-3' : ''}">
					<content:contentPPDEnroll partnerMessage="${partnerMessage}" enrolled="${isPartnerProgramEnrolled}" ppemail="${isPartnerProgramAdminEmail}" retail="${isPartnersProgramRetail}" ppurl="${isPartnerProgramUrl}"/>
				</div>
				
			</div>
		</div>
		<div class="col-md-12 padding-30 no-padding-hrz-xs m-xs-hrz--10 font-size-14 f-s-12-xs-pt bg-white text-left xs-center font-Geogrotesque">
			<div class="row">
				<div class="col-md-3 col-xs-6 b-l-green b-0-xs">
					<p class="margin0 text-dark-gray f-s-40 f-s-24-xs-pt"><fmt:formatNumber value="${apoint}" type="number" minFractionDigits="0" maxFractionDigits="0" /></p>
					<span class="bold"><spring:theme code="partners.point.available" /></span>
					<div class="info-tooltip js-info-tootip p-l-5" rel="custom-tooltip">
						<div class="tooltip-content hide">
							<p><spring:theme code="partners.point.earned" /></p>
						</div>
						<img src="/_ui/responsive/theme-lambda/images/info_icon.svg" width="14" alt="AVAILABLE POINTS" />
					</div>
				</div>
				<div class="col-md-3 col-xs-6 b-l-green">
					<p class="margin0 text-dark-gray f-s-40 f-s-24-xs-pt"><fmt:formatNumber value="${epoint}" type="number" minFractionDigits="0" maxFractionDigits="0" /></p>
					<span class="bold"><spring:theme code="partners.point.expiring" /></span>
					<div class="info-tooltip js-info-tootip p-l-5" rel="custom-tooltip">
						<div class="tooltip-content hide">
							<p><spring:theme code="partners.point.earnedly" />.</p>
						</div>
						<img src="/_ui/responsive/theme-lambda/images/info_icon.svg" width="14" alt="EXPIRING POINTS" />
					</div>
				</div>
				<div class="col-md-3 col-xs-6 m-t-30-xs b-l-green b-0-xs">
					<p class="margin0 text-dark-gray f-s-40 f-s-24-xs-pt"><fmt:formatNumber value="${ppoint}" type="number" minFractionDigits="0" maxFractionDigits="0" /></p>
					<span class="bold"><spring:theme code="partners.point.pendings" /></span>
					<div class="info-tooltip js-info-tootip p-l-5" rel="custom-tooltip">
						<div class="tooltip-content hide">
							<p><spring:theme code="partners.point.earnonce" /></p>
						</div>
						<img src="/_ui/responsive/theme-lambda/images/info_icon.svg" width="14" alt="PENDING POINTS" />
					</div>
				</div>
				<div class="col-md-3 col-xs-6 m-t-30-xs b-l-green">
					<p class="margin0 text-dark-gray f-s-40 f-s-24-xs-pt"><fmt:formatNumber value="${ppointly}" type="number" minFractionDigits="0" maxFractionDigits="0" /></p>
					<span class="bold"><spring:theme code="partners.point.pending" /> <span class="hidden-md hidden-lg">(<spring:theme code="partners.point.lastyear" />)</span><span class="hidden-xs"> <spring:theme code="partners.point.fromly" /></span></span>
					<div class="info-tooltip js-info-tootip p-l-5" rel="custom-tooltip">
						<div class="tooltip-content hide">
							<p><spring:theme code="partners.point.transactionsly" />.</p>
						</div>
						<img src="/_ui/responsive/theme-lambda/images/info_icon.svg" width="14" alt="PENDING POINTS FROM LAST YEAR" />
					</div>
				</div>
			</div>
		</div>
		<c:if test="${enrolled eq true and currentBaseStoreId ne 'siteoneCA'}">
			<div onmouseup="window.open('https://siteone.lawnline.marketing/','_blank','noreferrer','noopener')" class="partner-banner-lawnline ${mobileFlag == 'true'? 'banner-lawnline-bg-mob' : 'banner-lawnline-bg'} col-md-12 m-xs-hrz--10 text-left xs-center">
				<div class="row flex flex-dir-column-xs partner-point-info">
					<div class="col-md-6 col-xs-12 p-y-20 no-margin-xs text-white f-s-24 f-s-14-xs-pt point-banner-center ${mobileFlag == 'true'? 'point-info-full-width' : 'point-info-title-width'}">
						<div class="pad-md-lft-10">
						<p class="f-s-32 f-s-27-xs-px margin0 font-metronic-headline"><spring:theme code="partners.point.spent" /></p>
						<p class="point-info-title-content font-Geogrotesque f-s-24 f-s-17-xs-px ${mobileFlag == 'true'? 'banner-content-l-h-xs' : 'banner-title-l-h-md'}"><spring:theme code="partners.point.redemption" /></p>
						</div>
					</div>
					<div class="point-banner-center ${mobileFlag == 'true'? 'point-info-full-width' : 'point-logo-content-width'}"><img class="full-width point-banner-logo-maxwidth" src="/_ui/responsive/theme-lambda/images/partner_banner_logo.svg" alt="partners-program-Logo"></div>
					<div class="col-md-6 col-xs-12 p-20-xs padding-30 p-b-25-xs text-dark-gray f-s-18 f-s-14-xs-pt ${mobileFlag == 'true'? 'point-info-full-width' : 'point-info-title-width'}">
							<div class="col-md-15 ${mobileFlag == 'true'? 'banner-content-l-h-xs' : 'banner-content-l-h-md'}"><spring:theme code="partners.point.redeemable" /></div>
							<c:if test="${mobileFlag eq true}">
							<a class="hidden-md hidden-lg banner-learnmore-btn-mob btn-link bold f-s-18 text-green font-Geogrotesque m-t-5-xs" href="https://siteone.lawnline.marketing/" rel="noopener noreferrer" target="_blank">Learn More</a>
							</c:if>
					</div>
				</div>
				<c:if test="${mobileFlag ne true}">
				<a class="banner-learnmore-btn btn-link bold f-s-18 text-green font-Geogrotesque pad-rgt-10 m-t-5-xs" href="https://siteone.lawnline.marketing/" rel="noopener noreferrer" target="_blank">Learn More</a>
				</c:if>
			</div>
		</c:if>
		<c:if test="${currentBaseStoreId ne 'siteoneCA'}">
			<content:contentPartners/>
		</c:if>
	</div>
</template:page>
