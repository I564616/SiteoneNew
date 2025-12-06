<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="format" required="true" type="java.lang.String" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<c:set value="${ycommerce:productImage(product, format)}" var="primaryImage"/>
<input type="hidden" value="${primaryImage.url}" id="checkbranch-imgurl-${product.code}" />
<input type="hidden" value="${fn:escapeXml(product.productShortDesc)}" id="checkbranch-productname-${product.code}" />
<input type="hidden" value="${product.itemNumber}" id="checkbranch-itemnumber-${product.code}" />
<c:choose>
	<c:when test="${not empty primaryImage}">
		<c:choose>
			<c:when test="${not empty primaryImage.altText}">
<img src="${primaryImage.url}" alt="${fn:escapeXml(primaryImage.altText)}" title="${fn:escapeXml(primaryImage.altText)}"/>
			</c:when>
			<c:otherwise>
<img src="${primaryImage.url}" alt="${fn:escapeXml(product.productShortDesc)}" title="${fn:escapeXml(product.productShortDesc)}"/>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${not empty product.productShortDesc}">
				<theme:image code="img.missingProductImage.responsive.${format}" alt="${fn:escapeXml(product.productShortDesc)}" title="${fn:escapeXml(product.productShortDesc)}" />
			</c:when>
			<c:otherwise>
				<theme:image code="img.missingProductImage.responsive.${format}" alt="${fn:escapeXml(product.name)}" title="${fn:escapeXml(product.name)}" />
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>