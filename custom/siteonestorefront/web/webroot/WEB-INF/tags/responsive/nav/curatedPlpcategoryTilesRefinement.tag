<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageData" required="true" type="de.hybris.platform.commerceservices.search.facetdata.FacetSearchPageData" %>
<%@ attribute name="contentSearchPageData" required="true" type="com.siteone.contentsearch.ContentSearchPageData" %>
<%@ attribute name="eventSearchPageData" required="true" type="com.siteone.facade.EventSearchPageData" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>


<c:forEach items="${pageData.facets}" var="facet">
    <nav:categoryTileRefinementInner facetData="${facet}"/> 
</c:forEach>

