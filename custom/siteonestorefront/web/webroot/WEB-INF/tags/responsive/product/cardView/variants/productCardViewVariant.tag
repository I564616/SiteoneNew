<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ taglib prefix="productItemVariant" tagdir="/WEB-INF/tags/responsive/product/cardView/variants" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<input type="hidden" name="baseVariantMapProdArrLength" value="${fn:length(baseVariantMap[product.code])}" />

<c:set var="variantshowhide" value="true" />
<c:set var="variantsimple" value="false" />
<c:choose>
    <c:when test="${product.variantCount eq 1}">
        <input type="hidden" name="baseVariantProd" value="${product.code}" />
        <c:set var="variantsimple" value="true" />
        <productItemVariant:productCardViewStockDataPrice productCode="${product.code}" variantProduct="${product}" variantShowHide="${variantshowhide}" variantSimple="${variantsimple}"  simpleVariant="${product.variantCount}" />
    </c:when>
    <c:otherwise>
        <c:set var="multivariant" value="${fn:length(baseVariantMap[product.code])}" />
        <c:forEach items="${baseVariantMap[product.code]}" var="variantProduct" varStatus="loop">
            <input type="hidden" name="variantProd" value="${variantProduct.code}" />
            <input type="hidden" name="variant-codes-cardDropdown-${product.code}" class="variant-codes-cardDropdown-${product.code}" value="${variantProduct.code}" />
            <productItemVariant:productCardViewStockDataPrice productCode="${product.code}" variantProduct="${variantProduct}" variantShowHide="${variantshowhide}"  variantSimple="${variantsimple}" simpleVariant="${multivariant}" />
            <c:set var="variantshowhide" value="false" />
        </c:forEach>
    </c:otherwise>
</c:choose>


		 


 