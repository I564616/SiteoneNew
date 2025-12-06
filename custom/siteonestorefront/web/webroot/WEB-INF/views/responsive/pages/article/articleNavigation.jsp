<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<p class="store-specialty-heading article-nav-heading"><b><spring:theme code="text.article.nav"/></b></p>
<br>
<c:if test="${searchPageData!=null && searchPageData.facets!=null}">
	<c:forEach items="${searchPageData.facets}" var="facetData">
		<c:if test="${facetData.code eq 'soArticleCategories'}">
			<c:forEach items="${facetData.values}" var="facetValuesData">
				<c:set var="cCode" value="${facetValuesData.code}"/>
			<p><a href="<c:url value="/articles"/>/${fn:toLowerCase(cCode)}">${facetValuesData.name}&nbsp;&#40;${facetValuesData.count}&#41;</a>
			</c:forEach>
		</c:if>	
	</c:forEach>
</c:if>
