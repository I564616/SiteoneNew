<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<div class="col-md-8 col-md-offset-1 video-component-wrapper">
<c:if test="${not empty component}">
<c:if test="${not empty component.media}">
<c:set var="mime" value="${component.media.mime }"/>
<c:set var="type" value ="${fn:split(mime, '/')[0]}" />
<c:choose>
<c:when test="${type eq 'image'}">
	<img src="${component.media.url}" height="auto" width="100%"/>
</c:when>
<c:otherwise>
<video width="100%" height="200" controls>
  <source src="${component.media.url}" type="${component.media.mime}">
</video>
</c:otherwise>
</c:choose>
</c:if>
</c:if>
</div>