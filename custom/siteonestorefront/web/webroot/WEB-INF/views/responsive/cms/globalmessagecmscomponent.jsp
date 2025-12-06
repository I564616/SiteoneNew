<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<div class="container-global" style="padding:0px;">
<span class="hidden-xs hidden-sm">${component.content}</span>
<span class="hidden-md hidden-lg">${component.mobilecontent}</span>

<!-- mocking a sample video as of now -->
<c:choose>
<c:when test="${not empty component.url}">
	<c:url var="linkUrl" value="${component.url}"/>
		<a href="${linkUrl}" title="${component.text}">${component.text}</a>
</c:when>
<c:otherwise>
	<a href="${component.video.url}" id="playvideo" title="${component.text}" target="_blank">${component.text}</a>
</c:otherwise>
</c:choose>
</div>