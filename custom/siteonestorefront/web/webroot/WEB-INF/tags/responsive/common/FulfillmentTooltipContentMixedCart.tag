<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ attribute name="fulfillment" required="false" %>


<div class="text-left">
	
	<c:if test="${fulfillment eq 'pickUp'}">
	<div class="flex-center paddingBottom5">
	<div><common:transitDuration height="18" width="33"/></div>
	<div class="pad-lft-10"><spring:theme code="variant.tooltip.pickup.text1"/></div>
	</div>
	<div class="flex-center paddingBottom5">
	<div><common:mfIcon height="18" width="33"/></div>
	<div class="pad-lft-10"><spring:theme code="variant.tooltip.pickup.text2"/></div>
	</div>
	</c:if>
	
	
	
	<c:if test="${fulfillment eq 'delivery'}">
	<div class="flex-center paddingBottom5">
	<div><common:transitDuration height="18" width="33"/></div>
	<div class="pad-lft-10"><spring:theme code="variant.tooltip.delivery.text1"/></div>
	</div>
	<div class="flex-center paddingBottom5">
	<div><common:deliveryIcon height="18" width="33" iconColor="#78a22f" /></div>
	<div class="pad-lft-10"><spring:theme code="variant.tooltip.delivery.text2"/></div>
	</div>
	
	</c:if>
	
	
	<c:if test="${fulfillment eq 'parcel'}">
	<div class="flex-center paddingBottom5">
	<div><common:transitDuration height="18" width="33"/></div>
	<div class="pad-lft-10"><spring:theme code="variant.tooltip.shipping.text1"/></div>
	</div>
	<div class="flex-center paddingBottom5">
	<div><common:freeShippingIcon height="18" width="33"/></div>
	<div class="pad-lft-10"> <spring:theme code="variant.tooltip.freeshipping.text.mixedcart"/></div>
	</div>
	
	</c:if>

</div>