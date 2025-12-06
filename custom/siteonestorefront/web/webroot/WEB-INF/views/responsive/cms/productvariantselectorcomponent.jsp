<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<product:productVariantSelector product="${product}"/>
<c:if test="${product.multidimensional}">
	 <a href="${product.baseProduct}"><spring:theme code="productVariantSelectorComponent.reset" /></a>
</c:if>