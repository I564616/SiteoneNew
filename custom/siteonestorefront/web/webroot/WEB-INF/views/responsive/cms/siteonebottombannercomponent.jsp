<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<h2>${title}</h2>
<br>
<c:if test="${not empty buttonURL and empty videoId }">
	<c:url value="${buttonUrl}" var="encodedUrl" />
	<a href="${encodedUrl}">${buttonLabel}</a>
</c:if>
<c:if test="${not empty videoId and empty buttonURL }">
</c:if>
<a href="${encodedUrl}">${buttonLabel}</a>