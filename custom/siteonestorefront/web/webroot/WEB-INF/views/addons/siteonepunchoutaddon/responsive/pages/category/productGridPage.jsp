<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<template:page pageTitle="${pageTitle}">

	<cms:pageSlot position="Section1" var="feature" element="div" class="product-grid-section1-slot">
		<cms:component component="${feature}" element="div" class="yComponentWrapper map product-grid-section1-component"/>
	</cms:pageSlot>
	<br/>
	<div class="plpFilter">
	<!-- <p class="text-right test hidden-xs hidden-sm"><a href="javascript:void(0)" onclick="window.print()"><span class="glyphicon glyphicon-print"></span>Print</a></p> -->
	<c:choose>
		<c:when test="${searchPageData.pagination.totalNumberOfResults > 1}">
			<div class="visible-xs visible-sm product-results-count"><fmt:formatNumber type = "number"  groupingUsed="true" value = "${searchPageData.pagination.totalNumberOfResults}"/> results.</div>
		</c:when>
		<c:otherwise>
			<div class="visible-xs visible-sm product-results-count"><fmt:formatNumber type = "number"  groupingUsed="true" value = "${searchPageData.pagination.totalNumberOfResults}"/> product found.</div>
		</c:otherwise>
	</c:choose>
	<c:set var="results" value="${searchPageData.pagination.totalNumberOfResults}"/>
	
	</div>
	<c:choose>
		<c:when test="${searchPageData.pagination.totalNumberOfResults > 1}">
			<div class="hidden-xs hidden-sm"><fmt:formatNumber type = "number"  groupingUsed="true" value = "${searchPageData.pagination.totalNumberOfResults}"/> results.</div>
		</c:when>
		<c:otherwise>
			<div class="hidden-xs hidden-sm"><fmt:formatNumber type = "number"  groupingUsed="true" value = "${searchPageData.pagination.totalNumberOfResults}"/> product found.</div>
		</c:otherwise>
	</c:choose>
	<c:if test="${searchPageData.pagination.totalNumberOfResults > 1}">
           <div class="filterspan">Use the filters to narrow your results.</div>         
    </c:if>
	<br/>
	<div class="row product-grid-row">
		<div class="col-xs-12 col-md-3 col-sm-6 mobile-refine-overlay marginpos">
			<cms:pageSlot position="ProductLeftRefinements" var="feature" element="div" class="product-grid-left-refinements-slot">
				<cms:component component="${feature}" element="div" class="yComponentWrapper product-grid-left-refinements-component"/>
			</cms:pageSlot>
			<div class="panel-body">
			<cms:pageSlot position="ProductPromotionSpot" var="feature" element="div">
				<cms:component component="${feature}" element="div"/>
			</cms:pageSlot>
			</div>
		</div>
		<div class="col-sm-12 col-md-9">
			<cms:pageSlot position="ProductGridSlot" var="feature" element="div" class="product-grid-right-result-slot">
				<cms:component component="${feature}" element="div" class="product__list--wrapper yComponentWrapper product-grid-right-result-component"/>
			</cms:pageSlot>
		</div>
		 
	</div>
	<div class="mobile-promo panel-body hidden-lg hidden-md">
		<cms:pageSlot position="ProductPromotionSpot" var="feature" element="div">
			<cms:component component="${feature}" element="div"/>
		</cms:pageSlot>
	</div>
	
	<product:productDisclaimer/>
	<storepickup:pickupStorePopup />

	<div class="col-sm-8 compareBox hidden-xs hidden-sm">
		<div class="container-lg container-fluid">
			<div class="col-md-4 compareChange" style="margin-top: 10px; padding-left: 84px;">
				<h4 class="headline3">Select up to 3 items to compare.</h4>
			</div>
			<div class="col-md-7 comparePadding">
				<div class="col-md-3 black-title" style ="margin-top: 10px;">
					<span class="bold-text">Items selected :</span>
				</div>
				<span class="product__compare__checkbox"></span> <span class="col-md-3 btnpadding">
					<button class="btn btn-primary btn-block" id="btn_Compare" style="margin-top: 3px; width:195px;">Compare</button>
					
				</span>
			</div>
			<div class="col-md-1"  style="margin-top:30px;"><a id="removeAllCompare" style="cursor:pointer; color:#000;">Clear All</a></div>
		</div>
	</div>
</template:page>