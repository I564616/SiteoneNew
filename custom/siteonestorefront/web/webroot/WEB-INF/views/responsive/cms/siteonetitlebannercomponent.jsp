<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:choose>
<c:when test="${cmsPage.uid eq 'hardscapesLander'}">
<h2 class="headline2 green_border">${component.headline}</h2>
</c:when>
<c:otherwise>
<c:if test="${not empty component}">

<c:if test="${not empty component.media}">
 <c:if test="${cmsPage.uid ne 'faq'}">
	 <div class="row">
	 <div class="row">
 </c:if>
 <img title="${headline}" alt="${media.altText}" src="${component.media.url}" height="auto" width="100%;" class="margin20">
 <c:if test="${cmsPage.uid ne 'faq'}"> 
 </div>
 </div>
 </c:if>
<br/> 

</c:if>
 
<h1 class="headline page-heading banner-heading">${component.headline}</h1>
<c:if test="${not empty component.content }">
<div class="col-md-12 col-xs-12 col-sm-12 content-description-wrapper">
	${component.content}
</div>
</c:if>

</c:if>
<div class="cl"></div>
</c:otherwise>
</c:choose>

