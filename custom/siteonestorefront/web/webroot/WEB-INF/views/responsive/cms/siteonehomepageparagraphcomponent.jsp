<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:url value="${buttonUrl}" var="encodedUrl" />
<a href="${encodedUrl}">${buttonLabel}</a>