<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>

<c:forEach items="${breadcrumbs}" var="breadcrumb">
		<c:set var="searchText" value="${breadcrumb.name}"/>  
</c:forEach>

<c:choose>
	<c:when test="${not empty searchPageData.freeTextSearch}">
		<c:set var="searchTerm" value="${searchPageData.freeTextSearch}"/>
	</c:when>
	<c:otherwise>
		<c:set var="searchTerm" value="${param.text}"/>
	</c:otherwise>
</c:choose>

<template:page pageTitle="${pageTitle}"> 
	<!-- <nav:categoryTilesRefinement contentSearchPageData="${contentSearchPageData}" pageData="${searchPageData}" eventSearchPageData="${eventSearchPageData}" />  -->
	
	<input type="hidden" id="ViewType" value="${viewType}">
	<div class="row product-search-row">
		<div class="col-xs-3 col-5th mobile-refine-overlay white-bg filterAllSection">
			<div class="filters-slot">
				<cms:pageSlot position="ProductLeftRefinements" var="feature" element="div" class="search-grid-page-left-refinements-slot">
					<cms:component component="${feature}" element="div" class="search-grid-page-left-refinements-component"/>
				</cms:pageSlot>
			</div>
		</div>
		<div class="col-sm-12 col-4-5th plp-gridlist-section">
			<div class="resultsSection col-xs-12">
				<c:if test="${not empty searchTerm}">
					<c:if test="${fn:startsWith(searchTerm, 'skuId=')}">
						<c:set var="searchTerm" value="${fn:replace(searchTerm, 'skuId=', '')}" />
					</c:if>
						<fmt:formatNumber type="number" groupingUsed="true" value="${searchPageData.pagination.totalNumberOfResults}" var="results" />
						<c:set var="resultsCount" value="${fn:replace(results, '.', ',')}" />
						<div class="resultsCtgy">
							<h1><span><spring:theme code="results.for" /></span>
							<span class="highlightedSearchText">${searchTerm}</span>&nbsp;</h1>
							<!-- <span class="cityInSearchText">
								<spring:theme code="breadcrum.near.text" />&nbsp;${sessionStore.address.town}
							</span> -->
							<div>${resultsCount}</div>
						</div>
						<c:if test="${null != searchPageData.results && searchPageData.pagination.totalNumberOfResults le 20 && failed_word == null}">
							<nav:searchSpellingSuggestion spellingSuggestion="${searchPageData.spellingSuggestion}" />
						</c:if>
						<c:if test="${not empty failed_word}">
							<c:if test="${null != searchPageData.results && searchPageData.pagination.totalNumberOfResults gt 0}">
								<spring:theme code="search.suggested.term.message.part1" /> <span
									class="highlightedSearchText">${failed_word}</span>
								<spring:theme code="search.suggested.term.message.part2" /> <span
									class="highlightedSearchText">${suggested_word}</span>
								<spring:theme code="search.suggested.term.message.part3" />
							</c:if>
						</c:if>
						<c:if test="${!isAnonymous}">
							<div class="multiple-selection-container hidden-sm hidden-xs m-r-15">
								<button class="btn add-multiple-to-list-btn pdpAddtoListCommonBtn" disabled>Add (0) to List</button>
								<span class="common-atc-tooltip">
									<button class="btn btn-primary add-multiple-to-cart-btn" disabled>Add (0) to Cart</button>
									<span class="common-atc-tooltipmsg remove"><spring:theme code="common.atc.error.tooltip" /></span>
								</span>
							</div>
						</c:if>
				</c:if>
			</div>
			<div class="row no-margin p-a-15 flex-center display-block-xs display-block-sm appliedFilter-section">
				<div class="col-md-3 sortInHeading js-plp-sort-mob m-b-10-xs hidden-md hidden-lg">
					<c:if test="${null != searchPageData.results}">
						<span class="mb-pagination">
							<nav:pagination top="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"  searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}"  numberPagesShown="${numberPagesShown}"/>
						</span>
					</c:if>
				</div>
				<div class="col-md-8 p-r-0 ${not empty searchPageData.breadcrumbs ? 'filter-wraper':''}">
					<nav:facetNavAppliedFilters contentSearchPageData="${contentSearchPageData}" pageData="${searchPageData}" eventSearchPageData="${eventSearchPageData}"/>
				</div>
				<div class="col-md-2 p-r-10 toggle-plp-header">
					<div class="flex-center justify-flex-end plp-toggle-view hidden-sm hidden-xs">
						<div class="flex-center card-toggle ${viewType == 'card' ? 'active' : ''}" onclick="ACC.global.plpToggleCard()">
                       <common:cardViewIcon iconColor="white" />
                   </div>
             <div class="flex-center list-toggle ${viewType == 'list' ? 'active' : ''}" onclick="ACC.global.plpToggleList()">
                <common:listViewIcon iconColor="#CCC"/>
             </div> 
					</div>
				</div>	
				<div class="col-md-2 sortInHeading js-plp-sort-web p-l-0 m-b-10-xs hidden-xs hidden-sm">
					<c:if test="${null != searchPageData.results}">
						<span class="mb-pagination">
							<nav:pagination top="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"  searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}"  numberPagesShown="${numberPagesShown}"/>
						</span>
					</c:if>
				</div>
			</div>
			<cms:pageSlot position="SearchResultsGridSlot" var="feature" element="div" class="search-grid-page-result-grid-slot">
				<cms:component component="${feature}" element="div" class="search-grid-page-result-grid-component prod-list-section"/>
			</cms:pageSlot>
		</div>
	</div>
	<c:if test="${algonomyRecommendationEnabled}">
		<div class="cl"></div>
		<div class="mb-margin15">
		<div class="featured-content margin-top-20 marginBottom30" id="RecommendedProductSlotCategory">

		</div>
		</div>
	</c:if>
	<div class="cl"></div>
	<product:productDisclaimer/>
	<storepickup:pickupStorePopup />
	
</template:page>