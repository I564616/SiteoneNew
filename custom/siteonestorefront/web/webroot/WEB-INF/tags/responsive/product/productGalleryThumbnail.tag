<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ attribute name="galleryImages" required="true" type="java.util.List" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<c:set var="totalGalleryImage" value="${fn:length(galleryImages)}"/>
<input id="totalGalleryImage" type="hidden" value='${fn:length(galleryImages)}'>
<div class="gallery-thumbnail-wrapper col-md-2 ${not product.multidimensional?'p-l-0':'p-r-0'} hidden-sm hidden-xs ${totalGalleryImage lt 6 ? 'thumbnail-m-t-5':''}">
	<div class="up-arrow-icon arrow-icon disabled m-b-5 ${totalGalleryImage lt 6 ? 'hidden':''}" onclick="ACC.imagegallery.thumnailGalleryImagePDP('up')">
	<common:topArrowIcon></common:topArrowIcon>
	</div>
	<input type="hidden" id="nextBtnValue" value="6">
	<input type="hidden" id="startIndex" value="1">
	<div class="gallery-thumbnail">
		<c:forEach items="${galleryImages}" var="container" varStatus="loop">
   	<c:set var="remaining" value="${total - 5}"/>

    	<c:choose>
    	<c:when test="${(loop.index eq 4) and (remaining ne 0) }">
    		<c:set var="addOverlay" value=""/>
    		<c:set var="addClass" value="overlay-text"/>
    		<c:set var="isExceeded" value=""/>
    		<c:set var="hideMedia" value=""/>
    	</c:when>
    	<c:otherwise>
    		<c:set var="addOverlay" value=""/>
    		<c:set var="addClass" value="hidden "/>
    		<c:set var="isExceeded" value=""/>
    		<c:set var="hideMedia" value=""/>
    	</c:otherwise>
    	</c:choose>
    	<c:choose>
    		<c:when test="${not fn:contains(container.youtube.altText, 'youtube')}">
    			<c:set var="addTriangle" value=""/>
    			<c:set var="hideTriangle" value="hidden"/>
    		</c:when>
    		<c:otherwise>
    			<c:set var="addTriangle" value="play"/>
    			<c:set var="hideTriangle" value=""/>
    		</c:otherwise>
    	</c:choose>
    	<c:if test="${loop.index eq 0}">
    	<c:set var="firstBigImage" value="${container.pdpIcon.url}"/>
    	<c:set var="firstZoomImage" value="${container.superZoom.url}"/>
    	<c:set var="firstVideoImage" value="${not fn:contains(container.youtube.altText, 'youtube')?'':container.youtube.altText}"/>
    	    	</c:if>
		<div id="galleyImageItem_${loop.index+1}" class="galleyImageItem ${(loop.index+1) > 5 ? 'hidden':''}" onclick="ACC.imagegallery.mainImageGallery('${loop.index+1}')">
			<a class="item" data-first-src="${firstBigImage}" data-first-zoom-image="${firstZoomImage}" data-first-video-url="${firstVideoImage}"> 
				<img class="thumbnailImages pdp-thumbnail" src="${container.thumbnail.url}" alt="${container.thumbnail.altText}" title="${container.thumbnail.altText}">
				<button name="${addTriangle}" class="${hideTriangle} ${hideMedia} overlay-text-visible" style="visibility:hidden"></button>
			</a>  
			
		</div>
		</c:forEach>
	</div>
	<div class="down-arrow-icon arrow-icon ${totalGalleryImage lt 6 ? 'hidden':''}" onclick="ACC.imagegallery.thumnailGalleryImagePDP('down')" >
	<common:bottonArrowIcon></common:bottonArrowIcon>	
	</div>
</div>


