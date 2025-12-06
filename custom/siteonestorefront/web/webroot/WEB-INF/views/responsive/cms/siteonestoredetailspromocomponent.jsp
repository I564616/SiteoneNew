<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:url var="buttonUrl" value="${component.buttonURL}"/>
<c:choose>
      <c:when test="${not empty component.urlForLink}">
		<c:url value="${component.urlForLink}" var="encodedUrl" />
      </c:when>
      <c:otherwise>
		<c:url value="${component.urlLink}" var="encodedUrl" />
      </c:otherwise>
 </c:choose>
<c:choose>
<c:when test="${cmsPage.uid eq 'hardscapesLander'}">
<div class="col-md-12">
<div class="row">
<div class="stoneCenter_banner1">
	<div class="col-md-3"><div class="row"><a href="${buttonUrl}"><img src="${media.url}" class="img-responsive" alt="new location"/></a></div>
	</div>
	<div class="col-md-7 banner-content">
	<h2 class="headline">${component.headline}</h2>
	<p> ${component.promotionalText} </p>
	</div>
	<c:if test="${not empty component.buttonLabel}">
		<div class="col-md-2"><a href="${buttonUrl}" class="btn btn-primary btn-block stoneCenter-btn">${component.buttonLabel}</a></div>
	</c:if>
	<div class="cl"></div>
	</div>
</div>
</div>
</c:when>
<c:otherwise>
<div class="col-xs-12 col-sm-12 col-md-4 promo-wrapper">
	<a href="${encodedUrl}"> <img src="${media.url}" class="img-rounded" alt="Promotion Spot" height="auto" width="100%" /></a>
	<div style="position: absolute;top:50px;width:93%;" class="text-center">
	<div class="homepage-promo-heading2 promo-banner-txt">${component.promotionalText} </div>
	</div>
	<c:if test="${not empty component.buttonLabel}">
		<div class="learn-more-btn promo-like-btn">
		<a href="${buttonUrl}" class="btn btn-primary btn-block" onclick="">${component.buttonLabel}</a> 
		</div>
	</c:if>
</div>
</c:otherwise>
</c:choose>
	
<div class="cl visible-sm visible-xs"><br/></div>
	 
 <div class="cl"></div>

