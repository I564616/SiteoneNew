<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:forEach items="${breadcrumbs}" var="breadcrumb">
			<c:set var="searchText" value="${breadcrumb.name}"/>  
</c:forEach>

<template:page pageTitle="${pageTitle}">
	<div class="results">
  <c:set var = "string1" value = "${searchPageData.pagination.totalNumberOfResults}"/>
      <c:set var = "resultsCount" value = "${fn:replace(string1, '.', ',')}" />
        <h1 class="headline">${resultsCount}&nbsp;<c:out value="${searchText}"/></h1>
         
        <span><spring:theme code="searchGridPage.use.filters" />.</span>
    </div>
	<div class="row">
		<div class="col-xs-3 mobile-refine-overlay">
			<cms:pageSlot position="ProductLeftRefinements" var="feature" element="div" class="search-list-page-left-refinements-slot">
				<cms:component component="${feature}" element="div" class="search-list-page-left-refinements-component"/>
			</cms:pageSlot>
		</div>
		<div class="col-sm-12 col-md-9">
			<cms:pageSlot position="SearchResultsListSlot" var="feature" element="div" class="search-list-page-right-result-list-slot">
				<cms:component component="${feature}" element="div" class="search-list-page-right-result-list-component"/>
			</cms:pageSlot>
        </div>
	</div>
	<product:productDisclaimer/>
	<storepickup:pickupStorePopup />
	
</template:page>
 