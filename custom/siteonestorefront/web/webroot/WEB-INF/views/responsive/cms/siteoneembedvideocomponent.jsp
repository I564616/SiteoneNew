<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:if test="${not empty component}">
<c:set var="title" value="${component.headline}"/>
<c:set var="url" value="${component.externalurl}"/>
<c:choose>
<c:when test="${not empty url}">
	<section>
		<div class="col-md-12 col-xs-12 col-sm-12 store-article">
			<c:if test="${not empty title}">
				<div class="col-md-12 col-xs-12 col-sm-12 store-heading-wrapper">
					<p class="store-specialty-heading"><b>${title}</b></p>
				</div>
			</c:if>
			<c:if test="${not empty url}">
				<div class="col-md-12 col-xs-12 col-sm-12 store-content-wrapper">					
					<iframe width="560" height="315" src="${url}" 
					frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>
				</div>
			</c:if>
		</div>
	</section>
</c:when>
<c:otherwise>
</c:otherwise>
</c:choose>
</c:if>

