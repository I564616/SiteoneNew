<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<div class="banner__component banner row">
 	<div class="col-md-12 col-sm-12 col-xs-12 promo-image-container">  
  	<div class="row">
 	<br>
	<c:forEach items="${component.toolsList}" var="item" varStatus="status">
 	<c:choose>
      <c:when test="${not empty item.urlForLink}">
		<c:url value="${item.urlForLink}" var="encodedUrl" />
      </c:when>
      <c:otherwise>
		<c:url value="${item.urlLink}" var="encodedUrl" />
      </c:otherwise>
 	</c:choose>
	<div class="col-md-4 col-sm-4 col-xs-12 gallery-box">			 
	<div class="gallery-details">
	<a href="${encodedUrl}"><img title="${item.media.altText}" alt="${item.media.altText}" src="${item.media.url}"></a> 
	<h3>${item.title}</h3>
	<p class="mb-text"><span class="stonecenter-storetext">${item.description}</span>
	<a href="${encodedUrl}">${item.buttonName}</a></p>
	</div>
	</div>
	</c:forEach>
	</div>
	</div>
</div>
 