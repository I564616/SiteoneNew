<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:if test="${not empty component}">
<c:choose>
	<c:when test="${externalLink eq true}">
		<c:url value="_blank" var="target" />
	</c:when>
	<c:otherwise>
		<c:url value="_self" var="target" />
	</c:otherwise>
</c:choose>
<c:set var="title" value="${component.headline}"/>
<c:set var="content" value="${component.content}"/>
<c:choose>
<c:when test="${empty component.media}">
	<section>
		<div class="col-md-12 col-xs-12 col-sm-12 store-article">
			<c:if test="${not empty title}">
				<div class="col-md-12 col-xs-12 col-sm-12 store-heading-wrapper">
					<p class="store-specialty-heading"><strong>${title}</strong></p>
				</div>
			</c:if>
			<c:if test="${not empty content}">
				<div class="col-md-12 col-xs-12 col-sm-12 store-content-wrapper">
					<p>${content}</p>
				</div>
			</c:if>
		</div>
	</section>
</c:when>
<c:otherwise>
<section>
		<div class="col-md-12 col-xs-12 col-sm-12 store-article">
			<c:if test="${not empty title}">
				<div class="col-md-12 col-xs-12 col-sm-12 store-heading-wrapper">
					<p class="store-specialty-heading"><strong>${title}</strong></p>
				</div>
			</c:if>
			<div class="col-md-12 col-xs-12 col-sm-12 store-content-wrapper">
				<c:if test="${not empty content}">
					<p>${content}</p>
				</c:if>
			</div>
		</div>
		<div class="col-md-4 col-xs-12 col-sm-12 margin20">
			<a href="${component.urlForLink}" target="${target}"><img title="${title}" alt="${component.media.altText}" src="${component.media.url}"  width="100%"/></a>
		</div>
	</section>
</c:otherwise>
</c:choose>
</c:if>
<c:if test="${not empty content}">
<div class="cl"></div>
</c:if>

