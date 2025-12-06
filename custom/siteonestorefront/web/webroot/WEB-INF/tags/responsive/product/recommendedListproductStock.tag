<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="productData" required="false" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="iconWidth" required="false" type="java.lang.Integer" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="stockCount" value="${productData.stock.stockLevel}" />
<c:set var="stockMsg">
	<spring:theme code="${productData.storeStockAvailabilityMsg}" />
</c:set>
<c:set var="stockStore" value="" />
<c:set var="stockColor" value="#fff" />
<c:set var="stockBackground" value="bg-white" />
<c:set var="hardscapeMoreOnWayMsgList" value="false"/>
<c:if test="${productData.isEligibleForBackorder eq true and productData.inventoryCheck eq true and (fn:escapeXml(productData.level2Category) eq 'Manufactured Hardscape Products' or fn:escapeXml(productData.level2Category) eq 'Productos de Paisajismo Manufacturados' or fn:escapeXml(productData.level2Category) eq 'Natural Stone' or fn:escapeXml(productData.level2Category) eq 'Piedra Natural' or fn:escapeXml(productData.level2Category) eq 'Outdoor Living' or fn:escapeXml(productData.level2Category) eq 'Vida al Aire Libre')}">
	<c:set var="hardscapeMoreOnWayMsgList" value="true"/>
</c:if>
<input type="hidden" name="hardscapemoreonwaylist" value="${hardscapeMoreOnWayMsgList}" />
<input type="hidden" name="listForceInStock" value="${productData.isForceInStock}" />
<input type="hidden" name="currentBaseStoreId-list" value="${currentBaseStoreId}" />
<c:choose>
	<c:when test="${productData.inStockImage}">
		<c:choose>
			<c:when test="${productData.isStockInNearbyBranch}">
				<c:set var="stockColor" value="#ef8700" />
				<c:set var="stockBackground" value="bg-orange-light" />
				<c:set var="stockStore" value="${productData.nearestStore.storeId}" />
			</c:when>
			<c:otherwise>
				<c:set var="stockColor" value="#78a22f" />
				<c:set var="stockBackground" value="bg-lightgreen" />
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="${productData.notInStockImage or productData.isEligibleForBackorder}">
		<c:choose>
			<c:when test="${hardscapeMoreOnWayMsgList}">
				<c:set var="stockMsg" value="product.outofstock.invhit.forcestock" />
				<c:set var="stockStore" value="${sessionStore.name}" />
				<c:set var="stockCount" value="!" />
				<c:set var="stockColor" value="#ef8700" />
				<c:set var="stockBackground" value="bg-orange-light" />
			</c:when>
			<c:when test="${productData.isForceInStock}">
				<c:set var="stockColor" value="#78a22f" />
				<c:set var="stockBackground" value="bg-lightgreen" />
				<c:set var="stockMsgColor" value="#78a22f" />
				<c:set var="stockMsg">
					<span class="listStockMessage">
						<spring:theme code="${stockMsg}"/>
					</span>
				</c:set>
				<c:set var="stockCount">
					<common:plpNewCheck height="13" width="13"/>
				</c:set>
				<c:set var="toolTipMsg" value="${currentBaseStoreId eq 'siteone' ? 'AvailableToOrder.msg.tooltip' : 'AvailableToOrder.msg.tooltip.ca'}" />
			</c:when>
			<c:otherwise>
				<c:set var="stockColor" value="#5a5b5d" />
				<c:set var="stockBackground" value="bg-light-gray" />
				<c:set var="stockCount" value="-" />
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<!-- productData.outOfStockImage -->
		<c:set var="stockColor" value="#5a5b5d" />
		<c:set var="stockBackground" value="hidden" />
	</c:otherwise>
</c:choose>
<span class="list-instock-section"  style="color:${stockMsgColor}"><span class="bold border-radius-3 f-s-12 m-r-5 p-x-5 ${productData.isForceInStock ? 'p-t-6 p-b-1' : 'p-y-3'} bold ${stockBackground}" style="color:${stockColor}">${stockCount}</span>${stockMsg}<span class="list-info-tooltip ${productData.isForceInStock ? '' : 'hide'}" rel="custom-tooltip"><span class="tooltip-content hide"><spring:theme code="${toolTipMsg}" /></span><common:headerIcon iconName="plpinfotooltip" width="13" height="13" viewBox="0 0 13 13" /></span>  ${stockStore}</span>
<span class="list-outofstock-section bold text-red" style="display:none;"><span class="border-radius-3 f-s-12 m-r-5 p-x-5 p-y-3 bold bg-orange-light">0</span><spring:theme code="product.grid.outOfStock"/></span><div class="list-warning_info_${productData.code} bg-orange-light f-s-12 flex-center l-h-14 list-warning-section p-l-20 p-y-3 m-t-5 m-t-10-xs m-t-10-sm position-relative print-hidden" style="display:none;">
	<b class="bg-orange f-s-14 l-h-24 p-a-5 text-white position-absolute l-h-10-xs l-h-10-sm">!</b><span><spring:theme code="text.plp.expect.delay.for.fullorder" /></span>
</div>