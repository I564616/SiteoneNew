<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:choose>
      <c:when test="${not empty component.urlForLink}">
		<c:url value="${component.urlForLink}" var="encodedUrl" />
      </c:when>
      <c:otherwise>
		<c:url value="${component.urlLink}" var="encodedUrl" />
      </c:otherwise>
 </c:choose>

<div class="col-md-3 hidden-xs hidden-sm">
<div class="banner__component banner home-page-banner-ad row">
	<c:choose>
		<c:when test="${empty encodedUrl || encodedUrl eq '#'}">
	<div class="col-xs-6 col-md-12 col-sm-3 bannerimage padding-rightZero">
			 <img title="${media.altText}" class="img-responsive" alt="${media.altText}"
				src="${media.url}"> 
				
				</div>
		</c:when>
		<c:otherwise>
		<div class="col-xs-6 col-md-12 col-sm-3 bannerimage padding-rightZero">
			<a href="${encodedUrl}"><img title="${media.altText}"
				alt="${media.altText}" class="img-responsive" src="${media.url}"></a>
				</div>
		</c:otherwise>
	</c:choose>
</div>
</div>






 