<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ attribute name="galleryImages" required="true" type="java.util.List" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

    
    <div class="row mobile-swap-columns gallery-popup-overlay">
    	<div class="col-md-4 col-xs-12 col-sm-4 mobile-order-bottom">
			<div class="h5 bold col-xs-12 popup-gallery-thumbnail padding0 hidden"><spring:theme code="popup.product.images" /></div>
    		<div class="row photo-thumbnails">    			
			</div>			
		</div>
		<div class="col-md-8 col-xs-12 col-sm-8 text-center margin40 mobile-order-top">
    		<a href="#" class="popup-big-image"> <img class="popup-enlarged-image" src=""/></a>
			<div class="text-center h5 popup-hover--message popup-hover-mesg-color hidden-xs hidden-sm">Hover on image to Zoom</div>
			<div class="text-center h5 popup-hover--message popup-hover-mesg-color hidden-md hidden-lg">Touch on image to Zoom</div>
			<div class="text-center h5 hidden"><a href="#" class="js-click-here-to-zoom ">Click here</a> to view zoomed image</div>
			<div class="pdp-video-responsive video-responsive hidden">
				<iframe  class="popup-video-iframe" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen="" frameborder="0"  src=""></iframe>
			</div>
		</div>	
	</div>