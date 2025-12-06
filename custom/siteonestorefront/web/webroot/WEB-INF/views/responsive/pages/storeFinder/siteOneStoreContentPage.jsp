<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/responsive/store" %>


<c:if test="${empty searchPageData.results}">

<template:page pageTitle="${pageTitle}">
		
		<cms:pageSlot position="TopContent" var="feature">
			<cms:component component="${feature}"  element="div" class="top-content-slot cms_disp-img_slot"  />
		</cms:pageSlot>

		<div id="storeFinder" class="row">
		
		<store:storeMap store="${store}"/>
			<cms:pageSlot position="SiteOneMiddleContentSlot" var="feature">
				<cms:component component="${feature}"  element="div"/>
			</cms:pageSlot>
			
			
			<cms:pageSlot position="SiteOneStoreDetailsPromoSlot" var="feature">
			
			<cms:component component="${feature}" />
		</cms:pageSlot>

		<cms:pageSlot position="storeTextContainer" var="feature">
			
			<cms:component component="${feature}" />
		</cms:pageSlot>
		
	
		<div style="clear:both"></div>
			
			
		</div>

</template:page>

</c:if>
