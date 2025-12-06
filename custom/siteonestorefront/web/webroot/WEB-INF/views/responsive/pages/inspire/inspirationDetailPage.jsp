<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>

<template:page pageTitle="${pageTitle}">
	<h3><spring:theme code="inspirationDetailPage.gallery" /></h3>
	<br>
	
	<c:if test="${inspirationData!=null }">
		<div class="col-md-12">
			<img src="${inspirationData.inspirationMedia.url}" width="100%" style="border:1px solid black"/>
		</div>

		<div class="col-md-12">
			<div class="col-md-6">
				<h1>${inspirationData.title}</h1>
				<br> ${inspirationData.snippetOfTheStory}
			</div>
			<br />
			<br />
			
			<div class="col-md-2">
			</div>
			
			<div class="col-md-4">
				<b>Location:</b>${inspirationData.location} <br /> <b><spring:theme code="inspirationDetailPage.designedby" />:</b>${inspirationData.designedBy}
			</div>

		</div>
	</c:if>
</template:page>
