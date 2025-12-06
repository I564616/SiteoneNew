
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ attribute name="sellableUomListLen" required="false" type="java.lang.String" %>
<%@ attribute name="addIsMyStoreProduct" required="false" type="java.lang.Boolean" %>
<%@ attribute name="onlyHubStoreAvailability" required="false" type="java.lang.Boolean" %>
<%@ attribute name="addIsProductSellable" required="false" type="java.lang.Boolean" %>
<%@ attribute name="isSellableInventoryHit" required="false" type="java.lang.Boolean" %>

<c:if test="${(not product.multidimensional || product.variantCount == 1)}">
    <c:if test="${sellableUomListLen == 1}">
        <input type="hidden" id="inventoryUomId" name="inventoryUomId"
            value="${product.sellableUoms[0].inventoryUOMID}">
    </c:if>
    <ycommerce:testId code="addToCartButton">
        <input type="hidden" name="productCodePost" value="${product.code}" />
        <input type="hidden" name="productNamePost" value="${fn:escapeXml(product.productShortDesc)}" />
        <input type="hidden" name="productPostPrice" value="${product.price.value}" />
        <input type="hidden" name="storeId" value="${product.nearestStore.storeId}"/>
        <input type="hidden" value="${product.isRegulateditem}" id="regulated${product.code}" />

        <c:if test="${product.isRegulateditem}">
            
            <input type="hidden" value="${addIsProductSellable}" id="isProductSellable${product.code}" />
            <input type="hidden" value="${sessionStore.isLicensed}" id="isLicensed${product.code}" />
            <input type="hidden" value="${addIsMyStoreProduct}" id="isMyStoreProduct${product.code}" />
            <input type="hidden" id="isRup${product.code}" />
        </c:if>
        
    </ycommerce:testId>
</c:if>