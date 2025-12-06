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
<div class="col-md-12">
<div class="row">
<div class="cplp_stoneCenter_banner1">
	<div class="col-md-3"><div class="row"><a href="${buttonUrl}" class="banner-click"><img src="${media.url}" title="${buttonUrl}" class="img-responsive" alt="new location"/></a></div>
	</div>
	<div class="col-md-7 banner-content">
	<h2 class="headline">${component.headline}</h2>
	<p> ${component.promotionalText} </p>
	</div>
	<c:if test="${not empty component.buttonLabel}">
		<div class="col-md-2"><a href="${buttonUrl}" class="btn btn-primary btn-block stoneCenter-btn curated-promo-btn">${component.buttonLabel}</a></div>
	</c:if>
	<div class="cl"></div>
	</div>
</div>
</div>