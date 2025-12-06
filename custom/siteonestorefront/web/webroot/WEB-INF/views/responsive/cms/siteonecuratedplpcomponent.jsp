<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib prefix="pagination" tagdir="/WEB-INF/tags/responsive/nav/pagination" %>
<%@ taglib prefix="pag" tagdir="/WEB-INF/tags/responsive/nav/pagination" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>

<c:set var="allWishlist" value="${allWishlist}" scope="session" />
<c:set var="cmsPage" value="${cmsPage}" scope="session" />
<c:set var="quotesFeatureSwitch" value="${quotesFeatureSwitch}" scope="session" />
<c:set var="isMixedCartEnabled" value="${isMixedCartEnabled}" scope="session" />

<!-- Start filter and sort option in mobile -->
<div class="col-md-12 sortInHeading border-plp marginBottom20 hidden-lg hidden-md appliedFilter-section">	
	<c:if test="${null != paginationData.results}">
		<span class="mb-pagination">
			<nav:pagination top="true"  supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"  searchPageData="${paginationData}" searchUrl="${paginationData.currentQuery.url}"  numberPagesShown="${numberPagesShown}" />
		</span>
	</c:if>
</div>
<!-- End filter and sort option in mobile -->		
<div class="product__list--wrapper">
	<div id="productTab">
		<div class="product-grid-row paddingTop">
			<div class="col-xs-12 col-5th col-sm-6 mobile-refine-overlay white-bg filterAllSection">
			<div class="filters-slot">
			<div id="product-facet" class="product__facet js-product-facet">
				<div class="refinement-titleWrapper flex-center justify-between">
					<span class="glyphicon-leanChevron-left hidden-md hidden-lg"></span>
					<div class="filter-header">
						<a class="refinement-title">Filters</a>
						<div class="selFiltersCountSection">
							<span class="selFiltersCount">0</span>&nbsp;<span>Selected</span>
						</div>
					</div>
					<c:if test="${not empty paginationData.breadcrumbs}">
						<c:if test="${paginationData.freeTextSearch ne null}">
							<div class="remove-filter">
								<a href="${paginationData.currentQuery.url.split('\\?')[0]}?text=${paginationData.freeTextSearch}&viewtype=All"
									class="btn btn-default remove-filter-btn">
									<spring:theme code="productRefinementComponent.remove" />
								</a>
							</div>
						</c:if>
						<c:if test="${paginationData.freeTextSearch eq null}">
							<div class="remove-filter">
								<a href="${paginationData.currentQuery.url.split('\\?')[0]}&viewtype=All"
									class="btn btn-default remove-filter-btn">
									<spring:theme code="productRefinementComponent.remove" />
								</a>
							</div>
						</c:if>
					</c:if>
				</div> 
			
				<nav:facetNavRefinements contentSearchPageData="${contentSearchPageData}" pageData="${paginationData}" eventSearchPageData="${eventSearchPageData}"/> 
			</div>
			</div>
			<c:if test="${not empty marketingBanner.url}">
				<div class="col-xs-12 col-sm-12 col-md-12 promo-wrapper promo-wrapper-sec hidden-xs hidden-sm">
					<c:choose>
					<c:when test="${not empty marketingBannerLink}">
						<a href="<c:url value="/"/>${marketingBannerLink}"><img src="${marketingBanner.url}" class="" alt="${marketingBanner.altText}" height="auto" width="100%" /></a>
					</c:when>
					<c:otherwise>
						<img src="${marketingBanner.url}" class="" alt="${marketingBanner.altText}" height="auto" width="100%" />
					</c:otherwise>
					</c:choose>
				</div>
			</c:if>
			</div>
			<div class="col-sm-12 col-4-5th plp-gridlist-section">
				<div class="prod-list-section">
				<div class="resultsSection">
					<input type="hidden" id="plpproductCount" value="${paginationData.pagination.totalNumberOfResults}"/>
				
				<div class="resultsCtgy">
					<h1><span id="plpcategorynameAnalytics">${title}</span></h1>
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
							<button class="btn btn-primary add-multiple-to-cart-btn" disabled>Add (0) to Cart</button>
						</div>
					</c:if>
				</div>
				<div class="row no-margin p-a-15 flex-center display-block-xs display-block-sm appliedFilter-section ${not empty paginationData.breadcrumbs ? '':'hidden'}">
				<div class="col-md-3 sortInHeading js-plp-sort-mob m-b-10-xs hidden-md hidden-lg">
					<c:if test="${null != paginationData.results}">
						<span class="mb-pagination">
							<nav:pagination top="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${paginationData}" searchUrl="${paginationData.currentQuery.url}"  numberPagesShown="${numberPagesShown}"/>
						</span>
					</c:if>
				</div>
				<div class="col-md-12 p-r-0 ${not empty paginationData.breadcrumbs ? 'filter-wraper':''}">
					<nav:facetNavAppliedFilters contentSearchPageData="${contentSearchPageData}" pageData="${paginationData}" eventSearchPageData="${eventSearchPageData}"/>
				</div>
				</div>
				<div class="product__listing product__grid">
					<input type="hidden" class="plpviewtype" value="card">
    				<input type="hidden" class="isloggeduser" value="${!isAnonymous}">
					<c:forEach var="product" items="${productList}" varStatus="status">
						<div class="product-item print col-xs-12 col-sm-6 col-md-3" data-product-id="${product.code}" data-cardvariantcount="${product.variantCount}">
							<div class="plp-card product-item-box">
								<product:productListerGridItem product="${product}" loop="${status.index}" />
							</div>
						</div>
					</c:forEach>
				</div>
				<%-- Request Quote --%>
 				<common:requestQuotePopupPLPPDP />
				<div class="cplp-pagination marginTop35 hidden-xs hidden-sm">
					<nav:pagination top="false" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
						searchPageData="${paginationData}" searchUrl="${paginationData.currentQuery.url}"
						numberPagesShown="${numberPagesShown}" />
					<%-- <pagination:curatedplppagepagination searchUrl="${paginationData.currentQuery.url}"
						searchPageData="${paginationData}" numberPagesShown="${numberPagesShown}" themeMsgKey="${themeMsgKey}" /> --%>
				</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%-- Mobile pagination --%>
<div class="cl"></div>
<div class="plp-gridlist-section hidden-lg hidden-md">
	<div class="prod-list-section">
		<div class="cplp-pagination marginTop35">
			<div class="sort-refine-bar">
				<div class="pagination-wrap product-page-wrap">
					<nav:pagination top="false" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
									searchPageData="${paginationData}" searchUrl="${paginationData.currentQuery.url}"
									numberPagesShown="${numberPagesShown}" />
				</div>
			</div>
		</div>
	</div>
</div>
 <c:if test="${not empty marketingBanner.url}">
				<div class="col-xs-12 col-sm-12 col-md-4 promo-wrapper hidden-md hidden-lg ">
					<c:choose>
					<c:when test="${not empty marketingBannerLink}">
						<a href="<c:url value="/"/>${marketingBannerLink}"><img src="${marketingBanner.url}" class="img-rounded-cp" alt="${marketingBanner.altText}" height="auto" width="100%" /></a>
					</c:when>
					<c:otherwise>
						<img src="${marketingBanner.url}" class="img-rounded-cp" alt="${marketingBanner.altText}" height="auto" width="100%" />
					</c:otherwise>
					</c:choose>
				</div>
			</c:if>