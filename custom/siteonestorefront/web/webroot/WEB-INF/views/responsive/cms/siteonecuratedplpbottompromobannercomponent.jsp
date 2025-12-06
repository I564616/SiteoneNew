<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/responsive/store" %>

<div class="col-md-12 hidden-xs hidden-sm">
	<div class="row">	
		<div class="stoneCenter_banner1">
			<div class="col-md-4 clr-gradient"><a href="${buttonUrl}" class="banner-click"><img src="${media.url}" title="${media.url}" class="img-responsive" alt="new location"/></a>
			</div>
		<div class="col-md-6 banner-content">
			<h3 class="headline">${component.headline}</h3>
			<div class="cl"></div>
		</div>
		<c:if test="${not empty component.buttonLabel}">
			<div class="col-md-2">
				<a href="${component.buttonURL}" class="btn btn-primary btn-block stoneCenter-btn curated-promo-btn" onclick="">${component.buttonLabel}</a> 
			</div>
		</c:if>
		</div>
	</div>
</div>

<div class="col-md-12 hidden-md hidden-lg">
<div class="stoneCenter_banner1">
	<div class="col-md-3 clr-mob-gradient"><div class="row"><a href="${buttonUrl}" class="banner-click"><img src="${media.url}" title="${media.url}" class="img-responsive" alt="new location"/></a></div>
	</div>
	<div class="col-md-7 banner-content">
	<h2 class="headline">${component.headline}</h2>
	</div>
	<c:if test="${not empty component.buttonLabel}">
		<div class="col-md-2"><a href="${buttonUrl}" class="btn btn-primary btn-block stoneCenter-btn curated-promo-btn">${component.buttonLabel}</a></div>
	</c:if>
	<div class="cl"></div>
	</div>
</div>


