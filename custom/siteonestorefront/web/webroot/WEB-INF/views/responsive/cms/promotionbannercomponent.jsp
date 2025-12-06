<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="content" tagdir="/WEB-INF/tags/responsive/content"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<c:choose>
      <c:when test="${not empty component.urlForLink}">
		<c:url value="${component.urlForLink}" var="encodedUrl" />
      </c:when>
      <c:otherwise>
		<c:url value="${component.urlLink}" var="encodedUrl" />
      </c:otherwise>
 </c:choose>
<c:choose>
	<c:when test="${external eq true}">
		<c:url value="_blank" var="target" />
	</c:when>
	<c:otherwise>
		<c:url value="_self" var="target" />
	</c:otherwise>
</c:choose>
<div class="banner__component banner bottom-banner img-banner">
<c:set var="videoUrl" value="<%=de.hybris.platform.util.Config.getParameter(\"component.video.url\")%>"/>
 <c:choose>
  <c:when test="${fn:contains(videoId, 'autoplay')}">
   <c:set var="videoIdUrl" value="${videoId}&${videoUrl}"/>
   </c:when>
   <c:otherwise>
     <c:set var="videoIdUrl" value="${videoId}?${videoUrl}"/>
   </c:otherwise>
   </c:choose>

<c:choose>
<c:when test="${empty videoId}">
	<a  href="${encodedUrl}" data-youtube-url="${videoIdUrl}" target="${target}"
		title='Promotion banner Pop Up' > <img class="thumbnail-img bottom-banner-img" title="${media.altText}"
		alt="${media.altText}" src="${media.url}"/></a>
</c:when>
<c:otherwise>
	   <div class="video-container bottom-video-container">
			<content:youtubeMedia videoUrl="${videoIdUrl}"/>
	 </div>
</c:otherwise>
</c:choose>
<div class="col-xs-12 no-padding-md">
<h3 class="promo-heading no-margin-top">${title}</h3>
	<p class="mb-text"><span>${imageText}</span>
		<a href="${encodedUrl}" class="btn btn-primary" target="${target}" rel="noopener">${buttonName}</a>
	</p>
</div>
</div>

