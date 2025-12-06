<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart" %>
 <%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>

<c:set var="autoClose" value="auto-close"/>

<div class="nearby-overlay scroll-bar" id="nearbyOverlay"></div>


<div id="nearbyOverlayNodata" style="display:none;">
<div class='col-md-12 padding0 product-info'>
	<div class='flex-center'>
		<div class='col-md-1 col-sm-2 col-xs-4 padding0'><img class='img-responsive' src=''></div>
		<div class='col-md-4 col-sm-4 col-xs-6'><span class='product-title'></span></div>
	</div>
</div>
<div class='cl'></div>

<div class="no-data-msg flex-center">
	<common:exclamatoryIcon width="25" height="25" iconColor="#ed8606"/> <p><spring:theme code="mixcart.nearby.overlay"/>.</p>
</div>
<div class="col-md-12 text-center col-md col-xs-12 add-cart-continue-btn marginTop35 ${autoClose}">
		            <a href="" class="btn btn-primary js-mini-cart-close-button btn-popup-overlay-width">
		                <spring:theme code="cart.page.continue"/>
		            </a>
	            </div>
 </div>