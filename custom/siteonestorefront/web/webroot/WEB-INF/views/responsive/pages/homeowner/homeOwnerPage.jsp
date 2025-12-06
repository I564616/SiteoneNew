<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>


<input type="hidden" class="currentBaseStoreId" value="${currentBaseStoreId eq 'siteone' ? 'US' : 'CA' }"/>
<template:page pageTitle="${pageTitle}">
<div class="col-md-6 col-sm-12 col-xs-12 row">
	<h1 class="headline"><spring:theme code="text.select.contractor"/></h1>
</div>
 <!--  Desktop View Starts--> 
<c:if test="${deviceData.desktopBrowser}">
	<cms:pageSlot position="SectionA" var="feature">
		<cms:component component="${feature}"/>
	</cms:pageSlot> 
	     
	<cms:pageSlot position="SectionB" var="feature">
		<cms:component component="${feature}"/>
	</cms:pageSlot> 
</c:if> 
<div class="row">
	<cms:pageSlot position="BodyContent" var="feature" element="div" class="col-md-12">
		<cms:component component="${feature}" element="div" class="" />
	</cms:pageSlot>
	
	<cms:pageSlot position="CustInfo" var="feature" element="div" class="">
		<cms:component component="${feature}" element="div" class="" />
	</cms:pageSlot>
	 
	<div class="col-sm-12 col-md-9"></div>
</div>
</template:page>   
