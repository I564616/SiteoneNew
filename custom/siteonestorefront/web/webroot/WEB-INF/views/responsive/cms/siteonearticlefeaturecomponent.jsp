<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<div class="col-md-12 row">
<c:if test="${not empty component}">
<div class="col-md-8">
<c:if test="${not empty component.name}">
<h2 class="headline2"><spring:theme code="text.article.featureHeading"/></h2>
</c:if>
<c:if test="${not empty component.media}">
<c:set var="mime" value="${component.media.mime }"/>
<c:set var="type" value ="${fn:split(mime, '/')[0]}" />
<c:choose>
<c:when test="${type eq 'image'}">
	<img src="${component.media.url}" width="100%"/>
</c:when>
<c:otherwise>
<video width="500" height="200" controls>
  <source src="${component.media.url}" type="${component.media.mime}">
</video>
</c:otherwise>
</c:choose>
</c:if>
</div>
<br/>
<div class="col-md-4">
<br/>
<h3 class="green-title">${component.headline}</h3>
<br/>
<p>${component.content}</p>

<div class="row col-md-7 col-xs-12 col-sm-12 article-learn-more-btn">
<a href="<c:url value="/articles/${fn:toLowerCase(cmsPage.uid)}/"/>${component.urlLink}" class="btn btn-primary col-md-12 col-xs-12 col-sm-12"><spring:theme code="siteonearticlefeaturecomponent.learnMore" /></a>

<div class="cl"></div>
</div>
</div>
</c:if>
</div>