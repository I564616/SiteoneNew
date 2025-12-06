<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="article" tagdir="/WEB-INF/tags/responsive/article" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>


<c:if test="${searchPageData!=null && searchPageData.contentCategoryData!=null}">
	<c:forEach items="${searchPageData.contentCategoryData}" var="contentCategory">
		<h3 class="store-specialty-heading marginBottom20">
			<strong>${contentCategory.name}&nbsp;&#40;${fn:length(contentCategory.contentDataList)}&#41;</strong></h3>
		<!-- <a href="<c:url value="/articles"/>/${contentCategory.category}"><spring:theme code="articlelist.view"/>
			&#8594;</a> -->
		<div class="product__listing marginTop20 product__grids articlerow row">
			<c:forEach items="${contentCategory.contentDataList}" var="contentData" begin="0" end="5">
				<article:articleLister article="${contentData}" category="${contentCategory.category}"/>
			</c:forEach>
			<c:if test="${contentCategory.productCount > 6}">
				<div class="pull-right">
					<a href="<c:url value="/articles"/>/${fn:toLowerCase(contentCategory.category)}"><spring:theme code="articlelist.show.more"/>
						&#8594;</a>
				</div>
			</c:if>
		</div>
	</c:forEach>
</c:if>