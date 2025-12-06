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

<div class="banner__component banner" style="position: relative;">
	<c:choose>
		
		<c:when test="${not empty buttonName && buttonName ne ''}">
			<c:choose>
				<c:when test="${empty encodedUrl || encodedUrl eq '#'}">
					<div class="homepage-promo">
    					<div class="homepage-promo-heading">${imageText}</div> 
    					<div class="promo-btn"><a href="${encodedUrl}" class="btn btn-primary">${buttonName}</a></div>
    				</div>
					<img title="${media.altText}" alt="${media.altText}" src="${media.url}">
				</c:when>
				<c:otherwise>
					<div class="homepage-promo">
    					<div class="homepage-promo-heading">${imageText}</div> 
    					<div class="promo-btn"><a href="${encodedUrl}" class="btn btn-primary">${buttonName}</a></div>
    				</div>
				<img title="${media.altText}" alt="${media.altText}" src="${media.url}">
				</c:otherwise>
			</c:choose>
		</c:when>
	
		<c:otherwise>
			<c:choose>
				<c:when test="${empty encodedUrl || encodedUrl eq '#'}">
					<img title="${media.altText}" alt="${media.altText}" src="${media.url}">
				</c:when>
				<c:otherwise>
					<a href="${encodedUrl}">
						<img title="${media.altText}" alt="${media.altText}" src="${media.url}">
					</a>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
		
	</c:choose>
</div>