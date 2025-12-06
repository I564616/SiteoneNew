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

<div class="banner__component banner row no-gutter">
	<div class="col-md-12">
		<div class="container-lg container-fluid">
 			<div class="col-xs-12 col-md-7 floating-text">
			     ${content.content}
			    <a href="<c:url value="/contactus"/>" class="hidden-md hidden-lg"><spring:theme code="homePageCustomerEventsComponent.contact" /> &#8594;</a>
			</div>
 		</div>
		<img title="${media.altText}" alt="${media.altText}" src="${media.url}"/> 		
	</div>
</div>