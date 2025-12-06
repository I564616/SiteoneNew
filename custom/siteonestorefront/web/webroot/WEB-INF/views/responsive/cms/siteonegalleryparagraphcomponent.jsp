<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="col-xs-12 col-sm-12 col-md-4 promo-wrapper hidden-md hidden-lg">
		
			<c:url var="buttonUrl" value="${component.buttonURL}"/>
			<a href="${buttonUrl}" class="banner-click"> <img src="${component.image.url}" title="${buttonUrl}" class="img-rounded" alt="Promotion Spot" height="auto" width="100%" /></a>
			<div class="cl"></div>
			<h2 class="headline2">${component.headline}</h2>
			<div class="cl"></div>
			<div class="promo-banner-txt">${component.promotionalText} </div>
			<c:if test="${not empty component.buttonLabel}">
			<a href="${buttonUrl}" class="curatedplp-promo" onclick="">${component.buttonLabel}</a>
		 </c:if>
	<div class="cl visible-sm visible-xs"><br/></div>
</div>	
<div class="col-xs-12 col-sm-12 col-md-4 promo-wrapper hidden-xs hidden-sm">
		
			<c:url var="buttonUrl" value="${component.buttonURL}"/>
			<a href="${buttonUrl}" class="banner-click"> <img src="${component.image.url}" title="${buttonUrl}" class="img-rounded" alt="Promotion Spot" height="auto" width="100%" /></a>
			<div class="cl"></div>
			<h2 class="headline2">${component.headline}</h2>
			<div class="cl"></div>
			<div class="promo-banner-txt">${component.promotionalText} </div>
			<c:if test="${not empty component.buttonLabel}">
			<a href="${buttonUrl}" class="curatedplp-promo" onclick="">${component.buttonLabel}</a>
		 </c:if>
	<div class="cl visible-sm visible-xs"><br/></div>
</div>	 
 

