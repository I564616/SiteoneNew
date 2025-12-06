<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>


<c:if test="${not empty component}">
	<c:if test="${not empty component.media}">
		<c:set var="mime" value="${component.media.mime }" />
		<c:set var="type" value="${fn:split(mime, '/')[0]}" />
		<c:choose>
			<c:when test="${type eq 'image'}">
				<div class="banner__component banner" style="position: relative;">
					<div class="article-promo"
						style="position: absolute; left: 0px;">
						<div class="article-promo-heading">${imageText}</div>
						<c:if test="${not empty buttonName }">
							<div class="promo-btn">
								<a href="${encodedUrl}" class="btn btn-primary">${buttonName}</a>
							</div>
						</c:if>
					</div>
				</div>
				<img src="${component.media.url}" alt="${component.media.altText}" height="auto" width="100%" />
			</c:when>
			<c:otherwise>
				<video width="500" height="auto" controls>
					<source src="${component.media.url}" type="${component.media.mime}">
				</video>
			</c:otherwise>
		</c:choose>
	</c:if>
</c:if>
<div class="cl"></div>
<br />
<br />