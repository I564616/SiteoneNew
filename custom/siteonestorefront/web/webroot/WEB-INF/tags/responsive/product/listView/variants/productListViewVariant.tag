<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ taglib prefix="listviewVariantComponent" tagdir="/WEB-INF/tags/responsive/product/listView/variants" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<input type="hidden" name="baseVariantMapProdArrLength" value="${fn:length(baseVariantMap[product.code])}" />

<c:choose>
    <c:when test="${product.variantCount eq 1}">
        <input type="hidden" name="baseVariantProd" value="${product.code}" />
        <listviewVariantComponent:productListViewVariantRow productCode="${product.code}" variantProduct="${product}" />
    </c:when>
    <c:otherwise>
        <c:forEach items="${baseVariantMap[product.code]}" var="variantProduct" varStatus="loop">
            <input type="hidden" name="variantProd" value="${variantProduct.code}" />
            <listviewVariantComponent:productListViewVariantRow productCode="${product.code}" variantProduct="${variantProduct}" />
        </c:forEach>
    </c:otherwise>
</c:choose>

