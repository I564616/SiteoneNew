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

<div class="flex-center paddingBottom5">
<div>
<c:if test="${fulfillment eq 'pickUp'}">
<common:pickUpIcon height="18" width="33" iconColor="#78a22f" />
</c:if>
<c:if test="${fulfillment eq 'delivery'}">
<common:deliveryIcon height="18" width="33" iconColor="#78a22f" />
</c:if>
<c:if test="${fulfillment eq 'parcel'}">
<common:parcelIcon height="18" width="33" iconColor="#78a22f" />
</c:if>
</div>
<div class="pad-lft-10">
<c:if test="${fulfillment eq 'pickUp'}">
<spring:theme code="variant.tooltip.pickup.text"/>
</c:if>
<c:if test="${fulfillment eq 'delivery'}">
<spring:theme code="variant.tooltip.delivery.text"/>
</c:if>
<c:if test="${fulfillment eq 'parcel'}">
<spring:theme code="variant.tooltip.parcel.text"/>
</c:if>
</div>
</div>
<div class="flex-center paddingBottom5">
<div>
<common:mfIcon height="18" width="33"/>
</div>
<div class="pad-lft-10">
<spring:theme code="variant.tooltip.businessdays.text"/>
</div>
</div>
<div class="flex-center paddingBottom5">
<div>
<common:transitDuration height="18" width="33"/>
</div>
<div class="pad-lft-10">
<c:if test="${fulfillment eq 'pickUp'}">
<spring:theme code="variant.tooltip.transitDuration.pickup.text"/>
</c:if>
<c:if test="${fulfillment eq 'delivery'}">
<spring:theme code="variant.tooltip.transitDuration.delivery.text"/>
</c:if>
<c:if test="${fulfillment eq 'parcel'}">
<spring:theme code="variant.tooltip.transitDuration.parcel.text"/>
</c:if>
</div>
</div>
<c:if test="${fulfillment eq 'pickUp' or fulfillment eq 'delivery'}">
<div class="flex-center paddingBottom5">
<div>
<c:if test="${fulfillment eq 'pickUp'}">
<common:pickUpIcon height="18" width="33" iconColor="#78a22f" />
</c:if>
<c:if test="${fulfillment eq 'delivery'}">
<common:deliveryIcon height="18" width="33" iconColor="#78a22f" />
</c:if>
</div>
<div class="pad-lft-10 paddingBottom5">
<c:if test="${fulfillment eq 'pickUp'}">
<spring:theme code="variant.tooltip.pickupfee.text"/>
</c:if>
<c:if test="${fulfillment eq 'delivery'}">
<spring:theme code="variant.tooltip.deliveryfee.text"/>
</c:if>
</div>
</div>
</c:if>
</div>