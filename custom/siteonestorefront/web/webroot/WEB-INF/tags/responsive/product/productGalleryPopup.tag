<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ attribute name="galleryImages" required="true" type="java.util.List" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
    <div class="pdpgalleryheading">${product.name}</div>
    <div class="row mobile-swap-columns gallery-popup-overlay p-l-40 p-t-20">
		<input id="galleryImageCount" type="hidden" value='${fn:length(galleryImages)}'>  
		<c:set var="galleryVideoCount" value="0"/> 	
		<c:set var="galleryImagesCount" value="0"/> 		
		<c:forEach items="${galleryImages}" var="container"  varStatus="status">   			
			<c:if test = "${fn:contains(container.youtube.altText, 'youtube')}">
				<c:set var="videosCount" value="true"/>
				<c:set var="galleryVideoCount" value="${galleryVideoCount+1}"/>
			</c:if>
			<c:if test = "${not fn:contains(container.youtube.altText,'youtube')}">
				<c:set var="galleryImagesCount" value="${galleryImagesCount+1}"/>
			</c:if>
		</c:forEach>
		<input id="videosCount" type="hidden" value='true'>
		<input id="galleryImagesCount" type="hidden" value='${galleryImagesCount}'> 
		<input id="galleryVideoCount" type="hidden" value='${galleryVideoCount}'> 
    	<div class="col-md-4 col-xs-12 col-sm-4 mobile-order-bottom imageVideoWrapper no-padding-xs">
			<div class="h5 bold col-xs-12 popup-gallery-thumbnail p-l-0 hidden"><spring:theme code="popup.product.images" /></div>
    		<div class="photo-thumbnails">
				<c:choose>
					<c:when test="${galleryImagesCount le 20}">
						<c:forEach items="${galleryImages}" var="container" varStatus="status">
						<c:if test = "${not fn:contains(container.youtube.altText,'youtube')}">
							<div class="col-md-2 marginBottom10 m-r-5 m-r-10-xs padding0">
								<a href="#" class="popup-thumbnails popup-thumbnails_${status.index}"> 
								<img class="popup-product-thumb ${status.index == 0 ?'selected':''}" src="${container.thumbnail.url}" data-large-image="${container.pdpIcon.url}"
										data-zoom-image="${container.superZoom.url}"  alt="${container.thumbnail.altText}" data-title="${container.thumbnail.altText}" data-index="${status.index}" onclick="ACC.imagegallery.carouselGalleryPdp(this,'${status.index}','images')"/></a>
							</div>
						</c:if>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:set var="galleryImagesCountSection" value="1"/>
						<c:forEach items="${galleryImages}" var="container" varStatus="status">
						<c:if test = "${(not fn:contains(container.youtube.altText,'youtube')) and galleryImagesCountSection le 20}">
							<c:set var="galleryImagesCountSection" value="${galleryImagesCountSection+1}"/>
							<div class="col-md-2 marginBottom10 m-r-5 m-r-10-xs padding0">
								<a href="#" class="popup-thumbnails popup-thumbnails_${status.index}"> 
								<img class="popup-product-thumb ${status.index == 0 ?'selected':''}" src="${container.thumbnail.url}" data-large-image="${container.pdpIcon.url}"
										data-zoom-image="${container.superZoom.url}"  alt="${container.thumbnail.altText}" data-title="${container.thumbnail.altText}" data-index="${status.index}" onclick="ACC.imagegallery.carouselGalleryPdp(this,'${status.index}','images')"/></a>
							</div>
						</c:if>
						</c:forEach>
						<input id="galleryImagesCountSection" type="hidden" value='${galleryImagesCountSection}'> 
					</c:otherwise>
				</c:choose>
				
			</div>
			<c:if test="${videosCount == 'true'}">
			<div class="h5 bold col-xs-12 popup-video-thumbnail p-l-0 hidden"><spring:theme code="popup.product.videos" /></div>
			</c:if>
			<div class="video-thumbnails">
				<c:choose>
					<c:when test="${galleryVideoCount le 10}">
						<c:forEach items="${galleryImages}" var="container"  varStatus="status">   			
						<c:if test = "${fn:contains(container.youtube.altText, 'youtube')}">
							<div class="col-md-2 marginBottom10 m-r-5 m-r-10-xs padding0">
								<a href="#" class="popup-thumbnails popup-thumbnails_${status.index}">
								<img class="popup-product-thumb video-thumb"src="${container.thumbnail.url}" data-youtube-url="${container.youtube.altText}" data-index="${status.index}" onclick="ACC.imagegallery.carouselGalleryPdp(this,'${status.index}','videos')" />
								<button name="play" class="overlay-text-visible" onclick="ACC.imagegallery.carouselGalleryPdp(this,'${status.index}','videos')" style="visibility:hidden"></button>
								</a>
							</div>
						</c:if>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:set var="galleryVideoCountSection" value="1"/>
						<c:forEach items="${galleryImages}" var="container"  varStatus="status">   			
						<c:if test = "${fn:contains(container.youtube.altText, 'youtube') and galleryVideoCountSection le 10}">
							<c:set var="galleryVideoCountSection" value="${galleryVideoCountSection+1}"/>
							<div class="col-md-2 marginBottom10 m-r-5 m-r-10-xs padding0">
								<a href="#" class="popup-thumbnails popup-thumbnails_${status.index}">
								<img class="popup-product-thumb video-thumb"src="${container.thumbnail.url}" data-youtube-url="${container.youtube.altText}" data-index="${status.index}" onclick="ACC.imagegallery.carouselGalleryPdp(this,'${status.index}','videos')" />
								<button name="play" class="overlay-text-visible" onclick="ACC.imagegallery.carouselGalleryPdp(this,'${status.index}','videos')" style="visibility:hidden"></button>
								</a>
							</div>
						</c:if>
						</c:forEach>
						<input id="galleryVideoCountSection" type="hidden" value='${galleryVideoCountSection}'> 
					</c:otherwise>
				</c:choose>		
			</div>
		</div>
		<div class="col-md-8 col-xs-12 col-sm-8 text-center margin30 mobile-order-top mainGalleryWrapper">
			<div class="row">
			<div class="col-md-2 pdp-gallery-prev hidden-xs hidden-sm" onclick="ACC.imagegallery.caroselGalleryprevnext(this,'prev')" ><common:pdpGalleryPrev></common:pdpGalleryPrev></div>
			<div class="col-md-8 col-md-offset-2">
			<input id="inputdataprev" type="hidden" value='0'>
    		<a href="#" class="popup-big-image"> <img class="popup-enlarged-image" src="" data-index="0"/></a>
			<div class="text-center h5 popup-hover--message popup-hover-mesg-color text-italic hidden-xs hidden-sm">Hover on image to Zoom</div>
			<div class="text-center h5 popup-hover--message popup-hover-mesg-color text-italic hidden-md hidden-lg">Touch on image to Zoom</div>
			<c:if test="${product.productType eq 'Nursery' && not empty product.photoCredit}"><div class="img-text-message-popup"><spring:theme code="text.photo.credit.for.nursery.product" /><span class="p-l-5">${product.photoCredit}</span></div></c:if>
			<div class="text-center h5 hidden"><a href="#" class="js-click-here-to-zoom ">Click here</a> to view zoomed image</div>
			<div class="pdp-video-responsive video-responsive hidden">
				<iframe  class="popup-video-iframe" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen="" frameborder="0"  src=""></iframe>
			</div>
			</div>
			<div class="col-md-2 pdp-gallery-next hidden-xs hidden-sm" onclick="ACC.imagegallery.caroselGalleryprevnext(this,'next')" ><common:pdpGalleryNext></common:pdpGalleryNext></div>
			</div>
		</div>	
	</div>