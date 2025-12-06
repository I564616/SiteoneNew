<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/responsive/store" %>

<div class="col-xs-12 col-md-6 margin20 learn-more-container hidden-xs hidden-sm">
	<div class="banner__component banner bottom-banner img-banner">
	<img title="${media.altText}" class="thumbnail-img bottom-banner-img" alt="${media.altText}"
					src="${media.url}">
	<div class="col-xs-12 no-padding-md">
		<h3 class="promo-heading no-margin-top">${component.headline}</h3>
		</div>
			<div class="col-xs-12">
				<c:if test="${not empty component.buttonLabel}">
					<div class="learn-more-btn curated-promo-btn">
						<a href="${component.buttonURL}" class="btn btn-primary btn-block" onclick="">${component.buttonLabel}</a>
					</div>
					</c:if>
		</div>
	</div>
</div>
<div class="col-xs-12 col-md-6 margin20 learn-more-container hidden-md hidden-lg">
	<div class="banner__component banner bottom-banner img-banner">
	<img title="${media.altText}" class="thumbnail-img bottom-banner-img" alt="${media.altText}"
					src="${media.url}">
	<div class="col-xs-12 no-padding-md">
		<h3 class="promo-heading no-margin-top">${component.headline}</h3>
		</div>
			<div class="col-xs-12">
				<c:if test="${not empty component.buttonLabel}">
					<div class="learn-more-btn curated-promo-btn">
						<a href="${component.buttonURL}" class="btn btn-primary btn-block" onclick="">${component.buttonLabel}</a>
					</div>
					</c:if>
		</div>
	</div>
</div>
