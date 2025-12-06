<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/responsive/store" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<c:if test="${empty searchPageData.results}">

<template:page pageTitle="${pageTitle}">

		<cms:pageSlot position="TopContent" var="feature">
			<cms:component component="${feature}"  element="div" class="top-content-slot cms_disp-img_slot"  />
		</cms:pageSlot>
		
			<cms:pageSlot position="SiteOneCompareContentSlot" var="feature">
				<cms:component component="${feature}"  element="div"/>
			</cms:pageSlot>
			
			<cms:pageSlot position="PlaceholderContentSlot" var="feature">
				<cms:component component="${feature}"  element="div"/>
			</cms:pageSlot>
			<div class="margin20">&nbsp;</div>
<product:productDisclaimer/> 
<div class="cl"></div>
<br/><br/>


</template:page>
</c:if>