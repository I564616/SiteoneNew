<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageData" required="true" type="de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData" %>
<%@ attribute name="contentSearchPageData" required="true" type="com.siteone.contentsearch.ContentSearchPageData" %>
<%@ attribute name="eventSearchPageData" required="true" type="com.siteone.facade.EventSearchPageData" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:if test="${not empty pageData.breadcrumbs}">
	<div class="facet js-facet active appliedFilters">
		<div class="facet__values js-facet-values">
			<ul class="facet__list">
				<c:forEach items="${pageData.breadcrumbs}" var="breadcrumb">
					<c:if test="${breadcrumb.facetCode ne 'isSellable'}">
						<li>
							<c:url value="${breadcrumb.removeQuery.url}" var="removeQueryUrl"/>
							<c:choose>
								<c:when test="${breadcrumb.facetCode eq 'soavailableInStores'}">
									<spring:theme code="search.nav.applied.inStockAtMyBranch"/> &nbsp;<a href="${removeQueryUrl.split('%3AisSellable%3Atrue')[0]}${removeQueryUrl.split('%3AisSellable%3Atrue')[1]}&viewtype=All" ><span class="glyphicon glyphicon-remove"></span></a>								
								</c:when>
								<c:otherwise>
									<div class="applied-filter flex-center"><a href="${removeQueryUrl}&viewtype=All" ><span class="glyphicon glyphicon-remove"></span></a>&nbsp;${breadcrumb.facetValueName}</div>
								</c:otherwise>
							</c:choose>
						</li>
					</c:if>
				</c:forEach>
				<li>
					<c:choose>
						<c:when test="${cmsPage.uid eq 'searchGrid' || cmsPage.uid eq 'search' || cmsPage.uid eq 'productGrid'}">			
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
						</c:when>
						<c:otherwise>
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
						</c:otherwise>
					</c:choose>

				</li>
			</ul>
			<!--<c:if test="${pageData.freeTextSearch ne null}">
			<div class="margin20 hide-for-mobile">
	 		<a href="${pageData.currentQuery.url.split('\\?')[0]}?text=${pageData.freeTextSearch}" class="btn btn-default">Remove All Filters</a>
	 		</div>
	 		</c:if>
	 	<c:if test="${pageData.freeTextSearch eq null}">
	 	<div class="margin20 hide-for-mobile">
	 	<a href="${pageData.currentQuery.url.split('\\?')[0]}" class="btn btn-default">Remove All Filters</a>
	 	</div>
	 	</c:if>-->
		</div>
	</div>
</c:if>

<c:if test="${not empty contentSearchPageData.breadcrumbs}">
	<div class="facet js-facet">

	<div class="facet__name js-facet-name hidden">
		<span class="glyphicon facet__arrow"></span>
		<spring:theme code="search.nav.applied.facets"/>
	</div>
		<div class="facet__values js-facet-values">
			<ul class="facet__list">
				<c:forEach items="${contentSearchPageData.breadcrumbs}" var="breadcrumb">
					<c:if test="${breadcrumb.facetCode ne 'isSellable'}">
						<li>
							<c:url value="${breadcrumb.removeQuery.url}" var="removeQueryUrl"/>
							${breadcrumb.facetValueName}&nbsp;<a href="${removeQueryUrl}&searchtype=content" ><span class="glyphicon glyphicon-remove"></span></a>
						</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
	</div>
</c:if>
	
<c:if test="${not empty eventSearchPageData.breadcrumbs}">
	<div class="facet js-facet">

	<div class="facet__name js-facet-name hidden">
		<span class="glyphicon facet__arrow"></span>
		<spring:theme code="search.nav.applied.facets"/>
	</div>
		<div class="facet__values js-facet-values">
			<ul class="facet__list">
				<c:forEach items="${eventSearchPageData.breadcrumbs}" var="breadcrumb">
				<c:if test="${breadcrumb.facetCode ne 'soeventgroup'}">
					<li>
						<c:url value="${breadcrumb.removeQuery.url}" var="removeQueryUrl"/>
						<c:set value='${removeQueryUrl.replace("/search?", "/events?")}' var="removeQueryUrl"/>
						${breadcrumb.facetValueName}&nbsp;<a href="${removeQueryUrl}" ><span class="glyphicon glyphicon-remove"></span></a>
					</li>
					</c:if>
				</c:forEach>
			</ul>
			 <c:set var="eventFaceturl" value="${pageContext.request.queryString}"/>
			 <c:if test = "${fn:length(eventFaceturl) gt 50}"> 
			<div class="margin20">
		 		<a href="<c:url value="/events"/>" class="btn btn-default"><spring:theme code="productRefinementComponent.remove"/></a>
		 	</div>
		 	 </c:if> 
		 	
		</div>
	</div>
</c:if>