<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="content" tagdir="/WEB-INF/tags/responsive/content"%>
<h1 class="headline"> ${promotionalText} </h1>
<c:choose>
      <c:when test="${not empty component.urlForLink}">
		<c:url value="${component.urlForLink}" var="encodedUrl" />
      </c:when>
      <c:otherwise>
		<c:url value="${component.urlLink}" var="encodedUrl" />
      </c:otherwise>
 </c:choose>
${promotionalText}
<br>
<a href="${buttonUrl}">${buttonName}</a>
<br>
${videoAttribute.url}
<br>
<a href="${encodedUrl}" >${imageText}</a>