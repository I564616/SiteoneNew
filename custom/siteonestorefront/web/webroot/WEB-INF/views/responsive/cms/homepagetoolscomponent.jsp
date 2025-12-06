<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="content" tagdir="/WEB-INF/tags/responsive/content"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
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
<div class="banner__component banner">
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
	<c:when test="${cmsPage.uid eq 'hardscapesLander'}">
		<a class="promo-banner-media" href='#' data-encoded-url="${encodedUrl}" data-youtube-url="${videoId}"
			title='Promotion banner Pop Up'> <img class="thumbnail-img" title="${media.altText}"
			alt="${media.altText}" src="${media.url}"/>
			<c:if test="${not empty videoId}">
				<button name="play" class="overlay-text-visible" style="visibility:hidden"></button>
			</c:if>
		</a>

		<h3 class="promo-heading">${title}</h3>
		<p class="mb-text">${description}

		<a href="${encodedUrl}">${buttonName}</a></p>
	</c:when>
	<c:otherwise>
		<div class="img-banner">


			<a class="promo-banner-media" href='#' data-encoded-url="${encodedUrl}" data-target-value="${target}"  data-youtube-url="${videoId}"
				title='Promotion banner Pop Up'> <img class="thumbnail-img" title="${media.altText}"
				alt="${media.altText}" src="${media.url}"/>
				<c:if test="${not empty videoId}">
					<button name="play" class="overlay-text-visible" style="visibility:hidden"></button>
				</c:if>
			</a>

			<h3 class="promo-heading trio-heading padding-md-30 font-metronic-headline ">${title}</h3>



			<div class="mb-text homepage-section-4abc trio-banner-bottom-text">
			<div class="row no-margin">
			<div class="col-md-8 col-xs-9 padding0 trio-banner-text">
					${description}
			</div>
			<div class="col-md-4 col-xs-3 text-right">
			<a href="${encodedUrl}" target="${target}" class="trio-bottom-text no-text-decoration"><span class="text-grey">|</span><span class="pad-lft-15 no-padding-sm no-padding-xs">
			<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 11.286 11">
			<defs><style>.arrow{fill:#78a22f;}</style></defs><path class="arrow" d="M4.8,38.387l.559-.559a.6.6,0,0,1,.854,0l4.9,4.894a.6.6,0,0,1,0,.854l-4.9,4.9a.6.6,0,0,1-.854,0L4.8,47.913a.605.605,0,0,1,.01-.864l3.035-2.892H.6a.6.6,0,0,1-.6-.6v-.806a.6.6,0,0,1,.6-.6H7.844L4.809,39.251A.6.6,0,0,1,4.8,38.387Z" transform="translate(0 -37.65)"/>
			</svg>
			</span>
			</a>

			</div>
			</div>

			</div>
		</div>
	</c:otherwise>
</c:choose>
</div>
<div class="marginTop35 hidden-md hidden-lg"></div>