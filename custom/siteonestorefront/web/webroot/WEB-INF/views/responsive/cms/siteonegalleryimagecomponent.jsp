<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<c:url value="${urlLink}" var="encodedUrl" />
<div class="row">
	<div class="col-md-10 col-sm-12 col-xs-12"><h1 class="headline"><spring:theme code="hardScape.headline"/> </h1></div>
	<div class="col-md-2 hidden-xs hidden-sm"><img src="/_ui/responsive/theme-lambda/images/SiteOne_StoneCenter-Logo.png" alt="SiteOne Stone Center Logo" class="img-responsive"/></div>

</div>


<h2 class="headline2 green_border">${component.headline}</h2>
<div class="cl"></div>
<div class="banner__component banner row">
	
  <div class="col-md-12 col-sm-12 col-xs-12 promo-image-container">  
  <div class="row">
  
  <br>
  <c:forEach items="${component.imageList}" var="imageList" varStatus="status">
 
	<c:if test="${imageList.media ne null && imageList.media.url ne null}">
		<c:choose>
			<c:when test="${empty encodedUrl || encodedUrl eq '#'}">
				 <div class="col-md-4 col-sm-4 col-xs-12 gallery-box">
				 
						 <div class="gallery-details">
						 <a class="gallery"  href="${imageList.media.url}" title="${imageList.media.label}"> 
						  <img  alt="${imageList.media.altText}" src="${imageList.media.url}" class="img-rounded" alt="Promotion Spot" height="auto"/>
						  </a>
						  <c:if test="${imageList.media.galleryImages ne null }">
						  <div id="galleryImageLists" style="display: none">
						  <input type="hidden" id="galleryMedias" value="${imageList.media.galleryImages}"/>
						  <c:forEach items="${imageList.media.galleryImages}" var="galleryMedia" varStatus="statusloop">
						   <input type="hidden" class="gallerytitle" value="${galleryMedia.label}"/>
						  <c:set var="galleryCount" value="${fn:length(imageList.media.galleryImages)}"/>
						  <a class="gallery"  title="${galleryMedia.label}" data-count="${statusloop.count}" data-size="${galleryCount}" href="${galleryMedia.url}"><img class="" alt="${galleryMedia.altText}" src="${galleryMedia.url}" class="img-rounded" alt="Promotion Spot" height="auto"/></a>
							</c:forEach>
							</div>
							</c:if>
							<div class="cl"></div>
							<h3>${imageList.media.label}</h3>
							<div class="cl"></div>
						  </div>
						<div class="cl"></div>
					</div>
			</c:when>
			<c:otherwise>
			<div class="col-md-4 col-sm-4 col-xs-12 gallery-box">
					 <div class="product-item-box">
						 <div class="gallery-details">
						<img class="" title="${imageList.media.altText}" alt="${imageList.media.altText}" src="${imageList.media.url}" class="img-rounded" alt="Promotion Spot" height="auto"/>
						 <c:if test="${imageList.media.galleryImages ne null }">
						  <c:forEach items="${imageList.media.galleryImages}" var="galleryMedia" varStatus="status">
						  <a class="gallery"  href="${galleryMedia.url}"><img class="" title="${galleryMedia.altText}" alt="${galleryMedia.altText}" src="${galleryMedia.url}" class="img-rounded" alt="Promotion Spot" height="auto"/></a>
							</c:forEach><div class="cl"></div>
							</c:if>
							<h3>${imageList.media.label}</h3>
						  </div>
						<div class="cl"></div>
						</div>
					</div>
			</c:otherwise>
		</c:choose>
	</c:if>
	</c:forEach>
</div>
</div>
</div>
 