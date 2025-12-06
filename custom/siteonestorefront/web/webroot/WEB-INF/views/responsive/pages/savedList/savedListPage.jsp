<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/responsive/store" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>


<template:page pageTitle="${pageTitle}">
<c:forEach items="${breadcrumbs}" var="breadcrumb">
	<c:set var="searchTextFromBreadcrumb" value="${breadcrumb.name}"/>  
</c:forEach>
	
			<cms:pageSlot position="SavedListContentSlot" var="feature">
				<cms:component component="${feature}"  element="div"/>
			</cms:pageSlot>

</template:page>