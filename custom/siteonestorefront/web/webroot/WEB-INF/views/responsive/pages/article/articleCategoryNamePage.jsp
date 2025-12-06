<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="article" tagdir="/WEB-INF/tags/responsive/article" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>


<c:forEach items="${searchPageData.contentCategoryData}" var="articleCategory">
<c:if test="${articleCategory!=null}">

<br aria-hidden="true">

	<h1 class="headline">${articleCategory.name}</h1>

	
</c:if>
</c:forEach>

