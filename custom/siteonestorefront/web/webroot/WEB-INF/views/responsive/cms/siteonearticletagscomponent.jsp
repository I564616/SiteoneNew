<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="col-md-12 row">
<c:if test="${not empty component && not empty contentTags}">
<h3 class="store-specialty-heading"><strong><spring:theme code="articleDetailsPage.tags" />:</strong></h3> <br/>
<div class="col-md-6 row">
<c:choose>
<c:when test="${not fn:contains(header['User-Agent'],'SiteOneEcomApp')}"> 
	<c:forEach items="${contentTags}" var="tags">
	<a href="/search/?searchtype=product&text=${tags}" class="tag-sec">${tags}</a>
	</c:forEach>
</c:when>
<c:otherwise>
	<c:forEach items="${contentTags}" var="tags">
	<span class="tag-sec">${tags}</span>
	</c:forEach>
</c:otherwise>
</c:choose>

<br/><br/><br/><br/>
</div>

</c:if>
</div>