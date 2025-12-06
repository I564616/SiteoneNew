<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="breadcrumbs" required="true" type="java.util.List"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url value="/" var="homeUrl" />

<ol class="breadcrumb">
<c:set var="length" value="${fn:length(breadcrumbs)}"/>
<c:set var="pageId" value="${cmsPage.uid}"/>
<li><a href="/"><spring:theme code="text.link.home.label" /></a></li>
<c:choose>
	<c:when test="${length > 1}">
		<c:forEach items="${breadcrumbs}" var="breadcrumb" varStatus="status">
			<c:url value="${breadcrumb.url}" var="breadcrumbUrl" />
			<c:if test="${breadcrumb.name ne ''}">
				<c:choose>
					<c:when test="${breadcrumb.url eq '#'}">
						<li class="breadcrumb-name">
							${fn:escapeXml(breadcrumb.name)}
						</li>
					</c:when>
					<c:otherwise>
					<c:set var="attributeFreeTextSearch">
						<c:catch var="exception">${searchPageData.freeTextSearch}</c:catch>
					</c:set>
					<c:choose>
						<c:when test="${(empty exception && (empty searchPageData.freeTextSearch && pageId == 'searchGrid')) || pageId == 'linkConfirmationPage'}">
							<div class="emptyBreadcrumb"></div>
				    	</c:when>
						<c:otherwise>
						     <li class="breadcrumb-name">
							<a class="linktracking-breadcrumb" href="${breadcrumbUrl}"<c:if test="${pageId == 'order'}">class="selected-dropdown-value"</c:if>>${fn:escapeXml(breadcrumb.name)}</a>
						</li>
						</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</c:if>
		</c:forEach>
	</c:when>
	
	<c:otherwise>
		<c:forEach items="${breadcrumbs}" var="breadcrumb" varStatus="status">
		<c:if test="${fn:escapeXml(breadcrumb.name) != 'Sign in' && breadcrumb.name ne ''}">
			<c:url value="${breadcrumb.url}" var="breadcrumbUrl" />
			<c:choose>
				<c:when test="${breadcrumb.url eq '#'}">
					<li class="breadcrumb-name">
						${fn:escapeXml(breadcrumb.name)}
					</li>
				</c:when>
				<c:otherwise>
					<li class="breadcrumbChange breadcrumb-name">
						<a href="${fn:escapeXml(breadcrumbUrl)}">${fn:escapeXml(breadcrumb.name)}</a>
					</li>
				</c:otherwise>
			</c:choose>
		</c:if>
		</c:forEach>
	</c:otherwise>

</c:choose>

</ol>
<c:if test="${cmsPage.uid eq 'productGrid'}">
	<!-- <div id="plpproductcountviewMobile" class="hidden-lg hidden-md plpfilterbycategoryMobile">${searchPageData.pagination.totalNumberOfResults}&nbsp;Results</div> -->
	<!--<h1 class="sr-only"><span id="plpcategorynameAnalytics"></span></h1>-->
</c:if>

<c:forEach items="${breadcrumbs}" var="breadcrumb" varStatus="loop">
			<input type="hidden" id="breadcrumbName${loop.index}" value="${fn:escapeXml(breadcrumb.name)}"/>
			<input type="hidden" id="breadcrumbUrl${loop.index}" value="${fn:escapeXml(breadcrumb.url)}"/>
</c:forEach>
 <script type="application/ld+json">
{
  "@context": "http://schema.org",
  "@type": "BreadcrumbList",
"itemListElement": [
 <c:forEach items="${breadcrumbs}" var="breadcrumb" varStatus="status">
<c:choose>
  <c:when test="${(fn:escapeXml(breadcrumb.name) eq 'All Products') && (cmsPage.uid eq 'productGrid')}">
      {
      "@type": "ListItem",
      "position": ${status.index},
      "item": {
        "@id": "${breadcrumbs[1].url}",
        "name": "${breadcrumb.name}"
      }

  </c:when>
  <c:otherwise>
<c:choose>
<c:when test="${(fn:escapeXml(breadcrumb.name) eq 'All Products') && (cmsPage.uid eq 'productDetails')}">
			 {
      "@type": "ListItem",
      "position": ${status.index},
      "item": {
        "@id": "${request.contextPath}${product.url}",
        "name": "${breadcrumb.name}"
      }
</c:when>
<c:otherwise>
  	 {
      "@type": "ListItem",
      "position": ${status.index},
      "item": {
        "@id": "${breadcrumb.url}",
        "name": "${breadcrumb.name}"
      }
</c:otherwise>
</c:choose>
  </c:otherwise>
</c:choose>
   
    }<c:if test="${!status.last}">,</c:if>
</c:forEach>
]
}
</script> 