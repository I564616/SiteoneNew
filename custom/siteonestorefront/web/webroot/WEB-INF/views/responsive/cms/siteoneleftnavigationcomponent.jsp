<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
 
	<div class="cl"></div>
	<div class="faq-links">
		<div class="col-md-12">
			<div class="row">
				<c:if test="${not empty component}">
				<c:url var="linkUrl" value="${component.linkUrl}"/>
					<a href="${linkUrl}" class="content_left_nav"
						id="${component.linkName}">${component.linkName}</a>
				</c:if>
			</div>
		</div>
		<div class="cl"></div>
	</div>
 