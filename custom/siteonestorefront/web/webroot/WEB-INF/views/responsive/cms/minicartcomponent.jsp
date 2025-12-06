<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:url value="/cart/miniCart/${totalDisplay}" var="refreshMiniCartUrl"/>
<c:url value="/cart/rollover/${component.uid}" var="rolloverPopupUrl"/>
<c:url value="/cart" var="cartUrl"/>
<div class="nav-cart">
	<span onclick="loading.start(); window.location.href='${cartUrl}'"
		class="mini-cart-link"
		data-mini-cart-refresh-url="${refreshMiniCartUrl}" 
	    data-mini-cart-items-text="<spring:theme code="basket.items"/>" >
		<c:choose>
			<c:when  test = "${cmsPage.uid eq 'choosePickupDeliveryMethodPage' || cmsPage.uid eq 'orderSummaryPage' || cmsPage.uid eq 'multiStepCheckoutSummaryPage'}">
				<span class="icon-cart">${totalItems lt 10000 ? totalItems : "9999+"}</span>
			</c:when>
			<c:otherwise>
				<div class="mini-cart-icon">
					<span class="mini-cart-count js-mini-cart-count"><span class="nav-items-total font-Geogrotesque font-size-14 f-s-16-xs-px f-s-16-sm-px">${totalItems lt 10000 ? totalItems : "9999+"}</span></span>
				</div>
			</c:otherwise>
		</c:choose>
	</span>
</div>
<div class="mini-cart-container js-mini-cart-container"></div>