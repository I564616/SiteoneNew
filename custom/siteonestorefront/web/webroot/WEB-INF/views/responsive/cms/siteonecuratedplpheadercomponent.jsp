<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/responsive/store" %>

<div class="pom-data hide">
	<h3 class="headline">${component.headline}</h3>
</div>
<div class="hidden-xs hidden-sm">
	<a href="${encodedUrl}" class ="banner-click"><img title="${media.altText}"
				alt="${media.altText}" class="img-responsive" src="${media.url}"></a>
</div>		
<div class="pom-data hide">
	<h3 class="headline">${component.headline}</h3>
</div>
<div class="hidden-md hidden-lg">
	<a href="${encodedUrl}" class ="banner-click"><img title="${media.altText}"
				alt="${media.altText}" class="img-responsive" src="${media.url}"></a>
</div>