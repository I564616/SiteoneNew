<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>


<template:page pageTitle="${pageTitle}">
<div>
		<cms:pageSlot position="NewsBanner" var="feature" element="div"
			class="search-grid-page-result-grid-slot">
			<cms:component component="${feature}" element="div" 
				class="search-grid-page-result-grid-component" />
		</cms:pageSlot>
		
		<cms:pageSlot position="NewsDetailsSlot" var="feature" element="div"
			class="search-grid-page-result-grid-slot">
			<cms:component component="${feature}" element="div"
				class="search-grid-page-result-grid-component" />
		</cms:pageSlot>
		
		<cms:pageSlot position="AboutSOLandSupply" var="feature" element="div"
			class="search-grid-page-result-grid-slot">
			<cms:component component="${feature}" element="div"
				class="search-grid-page-result-grid-component" />
		</cms:pageSlot>
		<div>
		<span class="pull-left" style="color:#6495ED;">&#8592;&nbsp;<a href="/news/${newsData.previousNews}"><spring:theme code="pressRoomDetailPage.previous" /></a></span>
 
		<span class="pull-right" style="color:#6495ED;"><a href="/news/${newsData.nextNews}"><spring:theme code="pressRoomDetailPage.next" /></a>&nbsp;&#8594;</span>
		</div>
	</div>

</template:page>
