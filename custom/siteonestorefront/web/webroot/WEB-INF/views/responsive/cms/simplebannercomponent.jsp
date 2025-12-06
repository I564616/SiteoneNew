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

<div class="banner__component banner">
	<c:choose>
		<c:when test="${empty encodedUrl || encodedUrl eq '#'}">
			<img title="${media.altText}" alt="${media.altText}"
				src="${media.url}">
		</c:when>
		<c:otherwise>
			<a href="${encodedUrl}"><img title="${media.altText}"
				alt="${media.altText}" src="${media.url}"></a>
		</c:otherwise>
	</c:choose>
</div>