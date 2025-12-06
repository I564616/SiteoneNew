<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>


<c:if test="${not empty component}">
	<c:if test="${not empty component.heroImage}">	
	<div class="cl"></div>
				<div class="banner__component banner" style="position: relative;">
					<div class="article-promo"
						style="position: absolute; left: 0px;">
						<div class="article-promo-heading">${component.heroImageText}</div>
					</div>
				</div>
				<div class="article-promo"
						left: 0px;"><img src="${component.heroImage.url}" width="100%" alt="${component.heroImage.altText}"/></div>
					
	</c:if>
</c:if>
<div class="cl"></div>
<br />
<br />