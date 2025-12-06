<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="productData" required="false" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="uomDesc" required="true" type="java.lang.String" %>
<%@ attribute name="uomMeasure" required="true" type="java.lang.String" %>
<%@ attribute name="uomid" required="true" type="java.lang.String" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<input type="hidden" value="${empty uomDesc ? uomMeasure : uomDesc}" class="uomvalue" />
<div class="slp-text-style text-uppercase">
	<spring:theme code="productDetailsPanel.unitOfMeasure"/>
</div>
<select onchange="ACC.recommendedlist.recListUomSelecion(this)" id="uom-options" class="js-uom-selector text-capitalize" <c:if test="${productData.variantCount > 0}">disabled</c:if>>
	<c:forEach items="${productData.sellableUoms}" var="sellableUom">
		<c:set var="inventoryMulvalue">&nbsp;(<fmt:formatNumber value="${sellableUom.inventoryMultiplier}" maxFractionDigits="0"/>)</c:set>
		<option class="text-capitalize" value="${sellableUom.inventoryUOMID}" data-uomdecs="${sellableUom.inventoryUOMDesc}" data-inventory="${sellableUom.inventoryUOMID}" data-inventoryMultiplier="${sellableUom.inventoryMultiplier}" ${uomid == sellableUom.inventoryUOMID? 'selected' : ''}>${fn:toLowerCase(sellableUom.inventoryUOMDesc)}${inventoryMulvalue}</option>
	</c:forEach>
</select>
<c:set var="uomMeasure" value="${empty uomMeasure? 'each' : uomMeasure}" />