<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:choose>
      <c:when test="${not empty component.urlForLink}">
		<c:url value="${component.urlForLink}" var="encodedUrl" />
      </c:when>
      <c:otherwise>
		<c:url value="${component.urlLink}" var="encodedUrl" />
      </c:otherwise>
 </c:choose>

<div class="banner__component banner">
<%-- <div style="padding: 20px;position: absolute;top: 10px;text-align: center;left:5%; width: 92%;">
<div class="homepage-banner-heading">
${component.title}
</div>
</div>  --%>
			 
			<c:choose>
<c:when test="${cmsPage.uid eq 'hardscapesLander'}">

<div class="col-md-6 hardscape-content">
<div class="row">
	<c:url var="buttonUrl" value="${encodedUrl}"/>
<div class="col-md-6 gallery_products">  <img src="${media.url}" class="img-rounded" alt="Promotion Spot" height="auto" width="100%"/> 
</div>
<div class="col-md-6">
<h3 class="headline3">${component.title}</h3>
<div>${component.promotionalText} </div>
</div>
</div>
<div class="cl"></div>

 
</div>

</c:when>
<c:otherwise>
<div class="row">
  <div class="col-md-12 col-xs-12 promo-image-container">  
	<c:choose>
		<c:when test="${empty encodedUrl || encodedUrl eq '#'}">
			<img title="${media.altText}" alt="${media.altText}"
				src="${media.url}" style="width:100%;">
					<div class="promo-dashboard-text">
					<c:if test="${component.promotionalText ne null}">
			<div class="homepage-banner-heading">${component.promotionalText}</div>
			</c:if>
			</div>
				<c:if test="${(component.buttonURL ne null) && (component.buttonLabel ne null)}">
			 <div  class="promo-dashboard-btn">
			<a href="${component.buttonURL}" class="btn btn-primary btn-block" onclick="">${component.buttonLabel}</a> 
			</div>
			</c:if>
		
		</c:when>
		<c:otherwise>
			<a href="${encodedUrl}"><img title="${media.altText}"
				alt="${media.altText}" src="${media.url}" style="width:100%;"></a>
					<div class="promo-dashboard-text">
					<c:if test="${component.promotionalText ne null}">
			<div class="homepage-banner-heading">${component.promotionalText} </div>
			</c:if>
				</div> 
				<c:if test="${(component.buttonURL ne null) && (component.buttonLabel ne null)}">
			 <div  class="promo-dashboard-btn">
			<a href="${component.buttonURL}" class="btn btn-primary btn-block" onclick="">${component.buttonLabel}</a> 
			</div>
			</c:if>
		
		</c:otherwise>
	</c:choose>
	</div>
</div>
	</c:otherwise>
	</c:choose>
</div>
<div class="cl hidden-md hidden-lg"><br/></div>
