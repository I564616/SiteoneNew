<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="product-facet" class="product__facet js-product-facet">
	<c:if test="${cmsPage.uid eq 'searchGrid' || cmsPage.uid eq 'search' || cmsPage.uid eq 'productGrid'}">
		<div class="refinement-titleWrapper flex-center justify-between">
			<span class="glyphicon-leanChevron-left hidden-md hidden-lg"></span>
			<div class="filter-header">
				<a class="refinement-title">Filters</a>
				<div class="selFiltersCountSection">
					<span class="selFiltersCount">0</span>&nbsp;<span>Selected</span>
				</div>
			</div>
			<c:if test="${not empty searchPageData.breadcrumbs}">
				<c:if test="${searchPageData.freeTextSearch ne null}">
					<div class="remove-filter">
						<a href="${searchPageData.currentQuery.url.split('\\?')[0]}?text=${searchPageData.freeTextSearch}&viewtype=All"
							class="btn btn-default remove-filter-btn">
							<spring:theme code="productRefinementComponent.remove" />
						</a>
					</div>
				</c:if>
				<c:if test="${searchPageData.freeTextSearch eq null}">
					<div class="remove-filter">
						<a href="${searchPageData.currentQuery.url.split('\\?')[0]}&viewtype=All"
							class="btn btn-default remove-filter-btn">
							<spring:theme code="productRefinementComponent.remove" />
						</a>
					</div>
				</c:if>
			</c:if>
		</div> 
	</c:if>
    <nav:facetNavRefinements contentSearchPageData="${contentSearchPageData}" pageData="${searchPageData}" eventSearchPageData="${eventSearchPageData}"/> 
</div>