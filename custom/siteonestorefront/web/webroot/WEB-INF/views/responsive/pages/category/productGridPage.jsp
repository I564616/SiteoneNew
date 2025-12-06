<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<template:page pageTitle="${pageTitle}">

	<cms:pageSlot position="Section1" var="feature" element="div" class="product-grid-section1-slot">
		<cms:component component="${feature}" element="div" class="yComponentWrapper map product-grid-section1-component"/>
	</cms:pageSlot>
  	<input type="hidden" id="currentCartId" name="currentCartId" value="${user.currentCartId}">
  	<input type="hidden" value="${method}" class="referrermethod"/>
	<input type="hidden" value="${methodMetaData}" class="referrermethoddata"/>
	<input type="hidden" id="categoryCodePR" value="${categoryCode}">
	<input type="hidden" id="ViewType" value="${viewType}">
	<!-- <div class="plpFilter">
		<nav:categoryTilesRefinement contentSearchPageData="${contentSearchPageData}" pageData="${searchPageData}" eventSearchPageData="${eventSearchPageData}" />
	</div> -->
	<div class="product-grid-row">
		<div class="col-xs-12 col-5th col-sm-6 mobile-refine-overlay white-bg filterAllSection">
			<div class="filters-slot">
				<cms:pageSlot position="ProductLeftRefinements" var="feature" element="div" class="product-grid-left-refinements-slot">
					<cms:component component="${feature}" element="div" class="yComponentWrapper product-grid-left-refinements-component"/>
				</cms:pageSlot>
			</div>
			<div class="panel-body">
				<cms:pageSlot position="ProductPromotionSpot" var="feature" element="div">
					<cms:component component="${feature}" element="div"/>
				</cms:pageSlot>
			</div>
			 <c:if test="${not empty categoryData.marketingBanner.url}">
				<div class="col-xs-12 col-sm-12 col-md-4 promo-wrapper promo-wrapper-sec hidden-xs hidden-sm">
					<c:choose>
					<c:when test="${not empty categoryData.marketingBannerLink}">
						<a href="${categoryData.marketingBannerLink}"><img src="${categoryData.marketingBanner.url}" class="img-rounded" alt="${categoryData.marketingBanner.altText}" height="auto" width="100%" /></a>
					</c:when>
					<c:otherwise>
						<img src="${categoryData.marketingBanner.url}" class="img-rounded" alt="${categoryData.marketingBanner.altText}" height="auto" width="100%" />
					</c:otherwise>
					</c:choose>
				</div>
			</c:if>
		</div>
		<div class="col-sm-12 col-4-5th plp-gridlist-section">
			<div class="resultsSection">
				<input type="hidden" id="plpproductCount" value="${searchPageData.pagination.totalNumberOfResults}"/>
				
				<div class="resultsCtgy">
					<h1><span id="plpcategorynameAnalytics"></span></h1>
					<span id="plpcategoryName"></span>
					<!--<span><spring:theme code="results.for" /></span> 
					<span class="headline plpcategoryHeadline">
						<span id="plpcategoryName">${categoryData.getName()}</span>
					</span>-->
					<div id="plpproductcountviewDesktop">0</div>
				</div>
				<c:if test="${!isAnonymous}">
					<div class="multiple-selection-container hidden-sm hidden-xs m-r-15">
						<button class="btn add-multiple-to-list-btn pdpAddtoListCommonBtn" disabled>Add (0) to List</button>
						<span class="common-atc-tooltip">
							<button class="btn btn-primary add-multiple-to-cart-btn" disabled>Add (0) to Cart</button>
							<span class="common-atc-tooltipmsg remove"><spring:theme code="common.atc.error.tooltip" /></span>
						</span>
					</div>
				</c:if>
			</div>
			<div class="row no-margin p-a-15 flex-center display-block-xs display-block-sm appliedFilter-section">
				<div class="col-md-3 sortInHeading js-plp-sort-mob m-b-10-xs hidden-md hidden-lg">
					<c:if test="${null != searchPageData.results}">
						<span class="mb-pagination">
							<nav:pagination top="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}"  numberPagesShown="${numberPagesShown}"/>
						</span>
					</c:if>
				</div>
				<div class="col-md-8 p-r-0 ${not empty searchPageData.breadcrumbs ? 'filter-wraper':''}">
					<nav:facetNavAppliedFilters contentSearchPageData="${contentSearchPageData}" pageData="${searchPageData}" eventSearchPageData="${eventSearchPageData}"/>
				</div>
				<div class="col-md-2 p-r-10 toggle-plp-header">
					<div class="flex-center justify-flex-end  plp-toggle-view hidden-sm hidden-xs">
						<div class="flex-center card-toggle ${viewType == 'card' ? 'active' : ''}" onclick="ACC.global.plpToggleCard()">
                       <common:cardViewIcon iconColor="white" />
                    </div>
                     <div class="flex-center list-toggle ${viewType == 'list' ? 'active' : ''}" onclick="ACC.global.plpToggleList()">
                        <common:listViewIcon iconColor="#CCC"/>
                   </div> 
					</div>
				</div>
				<div class="col-md-2 sortInHeading js-plp-sort-web p-l-0 hidden-xs hidden-sm">
					<c:if test="${null != searchPageData.results}">
						<span class="mb-pagination">
							<nav:pagination top="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}"  numberPagesShown="${numberPagesShown}"/>
						</span>
					</c:if>
				</div>
			</div>
			<cms:pageSlot position="ProductGridSlot" var="feature" element="div" class="product-grid-right-result-slot">
				<cms:component component="${feature}" element="div" class="product__list--wrapper yComponentWrapper product-grid-right-result-component prod-list-section"/>
			</cms:pageSlot>
		</div> 
	</div>
	<div class="mobile-promo panel-body hidden-lg hidden-md">
		<cms:pageSlot position="ProductPromotionSpot" var="feature" element="div">
			<cms:component component="${feature}" element="div"/>
		</cms:pageSlot>
	</div>
    <c:if test="${algonomyRecommendationEnabled}">
	<div class="row">	
	<div class="featured-content margin-top-20" id="RecommendedProductSlotCategory">
		
	</div>
	</div>
	</c:if>

	 <c:if test="${not empty categoryData.marketingBanner.url}">
			<div class="col-xs-12 col-sm-12 col-md-4 promo-wrapper hidden-md hidden-lg ">
			<c:choose>
			<c:when test="${not empty categoryData.marketingBannerLink}">
				<a href="${categoryData.marketingBannerLink}"><img src="${categoryData.marketingBanner.url}" class="img-rounded" alt="${categoryData.marketingBanner.altText}" height="auto" width="100%" /></a>
			</c:when>
			<c:otherwise>
				<img src="${categoryData.marketingBanner.url}" class="img-rounded" alt="${categoryData.marketingBanner.altText}" height="auto" width="100%" />
			</c:otherwise>
			</c:choose>
			</div>
		</c:if>

	<c:if test="${not empty categoryData.seoEditableText}">
		<div class="bottom-conent-main-content">${categoryData.seoEditableText}</div>
	</c:if>
	<product:productDisclaimer/>
	<storepickup:pickupStorePopup />

	<div class="col-sm-8 compareBox hidden-xs hidden-sm">
		<div class="container-lg container-fluid">
			<div class="col-md-4 compareChange" style="margin-top: 20px;">
				<h4 class="headline3"><spring:theme code="productGridPage.select" />.</h4>
			</div>
			<div class="col-md-7 comparePadding">
				<div class="col-md-3 black-title" style ="margin-top:30px;">
					<span class="bold-text"><spring:theme code="productGridPage.items.selected" /> :</span>
				</div>
				<span class="product__compare__checkbox col-md-6"></span> <span class="col-md-3 btnpadding">
					<button class="btn btn-primary btn-block" id="btn_Compare"><spring:theme code="productGridPage.compare" /></button>
					
				</span>
			</div>
			<div class="col-md-1"  style="margin-top:30px;"><div class="row"><a id="removeAllCompare" style="cursor:pointer; color:#000;"><spring:theme code="productGridPage.clear.all" /></a></div></div>
		</div>
	</div>
</template:page>