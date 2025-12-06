<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageData" required="true" type="de.hybris.platform.commerceservices.search.facetdata.FacetSearchPageData" %>
<%@ attribute name="contentSearchPageData" required="true" type="com.siteone.contentsearch.ContentSearchPageData" %>
<%@ attribute name="eventSearchPageData" required="true" type="com.siteone.facade.EventSearchPageData" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>


	<c:set var="isTopFacet" value="false" />
	<c:set var="isMoreWays" value="false" />

	<c:forEach items="${pageData.facets}" var="facet">
		<c:choose>
			<c:when test="${facet.code eq 'soproductBrandNameFacet' || facet.code eq 'price' || facet.code eq 'socategory' || facet.code eq 'sopriorityBrandNameFacet'}">
				<c:set var="isTopFacet" value="true" />
			</c:when>
			<c:when test = "${fn:containsIgnoreCase(facet.code, 'soisShippable')}">
       			<c:set var="isMoreWays" value="false" />
     	 </c:when>
			<c:when test="${facet.code ne 'soproductBrandNameFacet' || facet.code ne 'price' || facet.code ne 'socategory' || facet.code ne 'sopriorityBrandNameFacet'}">
				<c:set var="isMoreWays" value="true" />
			</c:when>
		</c:choose>	
	</c:forEach>

	<div id="productFacets">
		<input type="hidden" id="facetTotalCount" value="${fn:length(pageData.facets)}">
		<input type="hidden" id="facetLimit" value="${facetLimit}">
		<input type="hidden" id="totalFacetSize" value="${totalFacetsSize}">
		<c:set var="facetCount" value="${fn:length(pageData.facets)}"/>
		<c:if test="${not empty pageData.facets}">
			<div class="facet-border"></div>
		</c:if>
		<c:set var="currentUrl" value="${pageContext.request.queryString}"/>
	
		<!-- <div class="main-facet-nav-filters"> -->
			<nav:facetNavRefinementStoresFacet userLocation="${userLocation}" pageData="${pageData}" pageUid="${cmsPage.uid}" cmsPage="${isCMSPage}" toggle="${enableToggleFilter}"/>
			
			<c:if test="${isTopFacet eq true}">
				<c:forEach items="${pageData.facets}" var="facet">
					<nav:facetNavRefinementTopFacet facetData="${facet}"/> 
				</c:forEach>
			</c:if>
		<!-- </div> -->
		<c:if test="${isMoreWays eq true}">
			<c:forEach items="${pageData.facets}" var="facet">
				<nav:facetNavRefinementFacet facetData="${facet}"/>
			</c:forEach>
			<%-- <div class="more-filters-btn-section hidden hidden-sm hidden-xs">
				<button class="btn btn-primary btn-block more-filters-btn"><spring:theme code="more.filters"/></button>
			</div> --%>
		</c:if>
	</div>
	<c:if test="${totalFacetsSize gt facetLimit}">
		<div class="show-more-filter-section">
			<button id="showMoreFilters" data-offset="${facetLimit}" onClick="ACC.facets.showMoreFacet(this)"><spring:theme code="review.show.more" /></button>
		</div>
	</c:if>

<c:if test="${not empty eventSearchPageData.facets}">
	<div id="eventFacets">
			<span><spring:theme code="tag.facetNavRefinements.filter.by" /></span>
			<div class="cl"></div>
			<br/>
			
			<div style="border-bottom: #999 solid 1px;"></div>
			<c:forEach items="${eventSearchPageData.facets}" var="facet">
				<nav:facetNavRefinementFacet facetData="${facet}" type="event"/>
			</c:forEach>
	</div>
</c:if>
<div class="row">
	<c:set var="promoCounter" value="0"/>
	<c:forEach items="${contentSearchPageData.results}" var="content" varStatus="status">

	<c:if test="${content.contentType eq 'PROMOTION_PAGE' and promoCounter lt 2}">
	<c:set var="promoCounter" value="${promoCounter+1}"/>
		<div class="col-xs-12 col-md-12">
			<a href="${content.url}" class="content-link"> 
				
					<c:choose>
						<c:when test="${null != content.previewImage}"> 
							<img class="img-responsive" src="${content.previewImage.url}"  alt="${content.title}" title="${content.title}"/>
							
						</c:when>
						<c:otherwise>
							<img class="img-responsive"  src="/_ui/responsive/theme-lambda/images/missing_product_EN_300x300.jpg" alt="${content.title}" title="${content.title}" />
			
						</c:otherwise>
					</c:choose>	

				</a>			
			</div>
			</c:if>
</c:forEach>

</div>
