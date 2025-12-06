<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="breadcrumb"
	tagdir="/WEB-INF/tags/responsive/nav/breadcrumb"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>

<c:if test="${fn:length(breadcrumbs) > 0 && cmsPage.uid eq 'productGrid'}">
	<div class="breadcrumb-section results">
		<div class="container-lg container-fluid p-l-30">
			<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}" />
		</div>
	</div>
</c:if>

<c:if test="${fn:length(breadcrumbs) > 0 && cmsPage.uid ne 'productGrid'}">
	<div class="breadcrumb-section">
	<div class="container-lg container-fluid">
		<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}" />
		</div>
	</div>
</c:if>
