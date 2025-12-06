<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="galleryImages" required="true" type="java.util.List" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>

<c:set var="hardscapeMoreOnWayMsg" value="false"/>
<c:if test="${!isAnonymous and product.isEligibleForBackorder eq true and product.inventoryCheck eq true and (fn:escapeXml(product.level2Category) == 'Manufactured Hardscape Products' || fn:escapeXml(product.level2Category) == 'Productos de Paisajismo Manufacturados' || fn:escapeXml(product.level2Category) == 'Natural Stone' || fn:escapeXml(product.level2Category) == 'Piedra Natural' || fn:escapeXml(product.level2Category) == 'Outdoor Living' || fn:escapeXml(product.level2Category) == 'Vida al Aire Libre')}">
	<c:set var="hardscapeMoreOnWayMsg" value="true"/>
</c:if>	

<div class="image-gallery js-gallery">
    <!-- <span class="image-gallery__zoom-icon glyphicon glyphicon-resize-full"></span> -->

    <c:choose>
        <c:when test="${galleryImages == null || galleryImages.size() == 0}">
            <div class="carousel image-gallery__image js-gallery-image">
                <div class="item">
                    <div class="image-inner-wrapper">
                        <spring:theme code="img.missingProductImage.responsive.product" text="/" var="imagePath"/>
                        <c:choose>
                            <c:when test="${originalContextPath ne null}">
                                <c:url value="${imagePath}" var="imageUrl" context="${originalContextPath}"/>
                            </c:when>
                            <c:otherwise>
                                <c:url value="${imagePath}" var="imageUrl" />
                            </c:otherwise>
                        </c:choose>
                        <img class="lazyOwl" data-src="${imageUrl}" alt="${fn:escapeXml(product.name)}" data-title="${fn:escapeXml(product.name)}"/>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>

            <div class="carousel image-gallery__image js-gallery-image">
                <c:forEach items="${galleryImages}" var="container" varStatus="varStatus">
	                <c:choose>
			    		<c:when test="${not fn:contains(container.youtube.altText, 'youtube')}">
			    			<c:set var="addTriangle" value=""/>
			    			<c:set var="hideTriangle" value="hidden"/>
                            			<c:set var="pdpVideoUrl" value=""/>
			    		</c:when>
			    		<c:otherwise>
			    			<c:set var="addTriangle" value="play"/>
			    			<c:set var="hideTriangle" value=""/>                            
                            			<c:set var="pdpVideoUrl" value="${container.youtube.altText}"/>
			    		</c:otherwise>
	    			</c:choose>
                    <div class="item">
                        <div class="image-inner-wrapper launch-image-popup">
                            <img class="lazyOwl disable-zoom" width="200px" height="200px" data-src="${container.pdpIcon.url}"
                                 data-zoom-image="${container.superZoom.url}" data-video-url="${pdpVideoUrl}" data-title="${container.thumbnail.altText}" data-has-video="false"
                                 loading="eager" fetchpriority="high"/>
                        	<button name="${addTriangle}" class="${hideTriangle} overlay-text-visible" style="visibility:hidden"></button>
                        </div>
                        
                    </div>
                </c:forEach>
            </div>
             <c:if test="${product.productType eq 'Nursery' && not empty product.photoCredit}"><div class="img-text-message pdp-img-variant-message"><spring:theme code="text.photo.credit.for.nursery.product" /><span class="p-l-5">${product.photoCredit}</span></div></c:if>
            
            <div class="hidden gallery-popup-container">
            <product:productGalleryPopup galleryImages="${galleryImages}"/>
            </div>
        </c:otherwise>
    </c:choose>
</div>
<c:if test="${product.isRegulateditem eq true}">
                        <div class="mob-regulatedItem hidden-lg hidden-md">
                        <common:redesignregutatedIcon></common:redesignregutatedIcon>
                        </div>
                        </c:if>

<div class="row no-margin hidden-sm hidden-lg hidden-md">
  <div class="col-xs-12 text-center pdp-slider-text launch-image-popup hidden">
    <span class="pdp-current-item"></span>
    <span><spring:theme code="text.pagination.of"/></span>
    <span class="pdp-total-item"></span>
    <span><spring:theme code="pdp.slider.text"/></span>
  </div>
  <c:if test="${(product.outOfStockImage || hardscapeMoreOnWayMsg) && quotesFeatureSwitch && !product.multidimensional}">
<div class="row hidden-md hidden-lg">
<input id="requestQuoteButtonDesc" type="hidden" value='${product.name}'>
<input id="requestQuoteButtonItemnumber" type="hidden" value='${product.itemNumber}'>
<input id="quotesFeatureSwitch" type="hidden" value='${quotesFeatureSwitch}'>
<input id="quotesFeatureSwitchvariant" type="hidden" value='${!product.multidimensional}'>
<input id="quotesFeatureSwitchoutofstock" type="hidden" value='${product.outOfStockImage}'>
<div class="col-md-12 col-xs-12 m-b-15">
	<button class="col-md-12 col-xs-12 btn btn-primary requestQuoteBtnPDP"  
    data-product-description="${fn:escapeXml(product.name)}"
    onclick="ACC.savedlist.requestQuotePopupplp(this,'product-item','${product.code}');ACC.adobelinktracking.requestQuotePLP(this,'','','${cmsPage.name}','true')"><spring:theme code="request.quote.popup.request.text" /></button>
</div>
</div>
</c:if>
  <div>
    <div class="flex-center justify-center paddingTopB10 pdpAddtoListPopupBtn removesignInlink signInOverlay" id="addToListMobile"><span class="m-r-5 pdp-popup-atl-btn"><spring:theme code="cartItems.addToList"/></span><common:filter-chevron-down iconColor="#4492B6" /></div>
  </div>
</div>