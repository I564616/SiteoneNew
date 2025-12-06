<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="breadcrumb"	tagdir="/WEB-INF/tags/responsive/nav/breadcrumb"%>
<c:choose>
      <c:when test="${not empty component.urlForLink}">
		<c:url value="${component.urlForLink}" var="encodedUrl" />
      </c:when>
      <c:otherwise>
		<c:url value="${component.urlLink}" var="encodedUrl" />
      </c:otherwise>
 </c:choose>


<div class="row banner__component banner category-banner-component">
<div class="col-md-6 col-sm-8 col-xs-12 banner-text-content <c:if test='${media ne null && media.url ne null}'>text-over-image</c:if>">
		<div class="cl margin20"></div>
		<div class="banner-breadcrumb ">
			<div class="container-lg container-fluid">
				<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}" />
			</div>
		</div>	
		<h1 class="headline category-headline">${categoryData.name}</h1>	
		<div class="category-description-wrapper">${categoryData.description}</div>
	</div>
	<c:if test="${media ne null && media.url ne null}">
		<c:choose>
			<c:when test="${empty encodedUrl || encodedUrl eq '#'}">
				<img class="hidden-xs" title="${media.altText}" alt="${media.altText}"
					src="${media.url}">
			</c:when>
			<c:otherwise>
				<a href="${encodedUrl}" class="hidden-xs"><img title="${media.altText}"
					alt="${media.altText}" src="${media.url}"></a>
			</c:otherwise>
		</c:choose>
	</c:if>
</div>
