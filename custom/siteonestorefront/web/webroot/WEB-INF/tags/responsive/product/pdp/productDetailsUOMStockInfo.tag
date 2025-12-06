<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="b2b-product" tagdir="/WEB-INF/tags/addons/siteoneacceleratoraddon/responsive/product"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ attribute name="hardscapeMoreOnWayMsg" type="java.lang.String"%>

<input type="hidden" value="${hardscapeMoreOnWayMsg}" id="hardscapeMoreOnWayMsg--PDUOMFullfillmentStockInfo"/>
<c:choose>
	<c:when test="${product.inStockImage}">
		<c:choose>
			<c:when test="${product.isStockInNearbyBranch}">
				<span><common:bigCheckmarkIcon iconColor="#ef8700" height="16" width="16" /></span>
			</c:when>
			<c:otherwise>
				<span><common:bigCheckmarkIcon iconColor="#78a22f" height="16" width="16" /></span>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="${product.notInStockImage || hardscapeMoreOnWayMsg}">
		<span><common:plpNewCheck height="16" width="16" /></span>
	</c:when>
	<c:when test="${product.outOfStockImage}">
		<span><common:bigCrossIcon height="16" width="16" /></span>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${product.isEligibleForBackorder eq true}">
		<c:set var="productAvailabilityStatus" value="back order" />
	</c:when>
	<c:otherwise>
		<c:set var="productAvailabilityStatus" value="regular" />
	</c:otherwise>
</c:choose>

<input type="hidden" name="productAvailabilityStatus" value="${productAvailabilityStatus}" />
<c:choose>
	<c:when test="${!product.stockAvailableOnlyHubStore}">
		<c:choose>
			<c:when test="${hardscapeMoreOnWayMsg}">
				<span class="pdp-store-detail js-pdp-store-detail stock-section-text">
					<spring:theme code="product.outofstock.invhit.forcestock" /> &nbsp;
					${sessionStore.name}
				</span>
			</c:when>
			<c:otherwise>
				<span class="pdp-store-detail js-pdp-store-detail stock-section-text">${product.storeStockAvailabilityMsg}</span>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<span class="pdp-store-detail js-pdp-store-detail stock-section-text">
			<spring:theme code="product.instock.shippingonly" />
		</span>
	</c:otherwise>
</c:choose>
</div>
