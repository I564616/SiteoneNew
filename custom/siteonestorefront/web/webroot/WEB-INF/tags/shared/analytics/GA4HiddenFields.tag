<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>

<input type="hidden" id="ga4-productCode" value="${product.code}"/>
<input type="hidden" id="ga4-productName" value="${fn:escapeXml(product.productShortDesc)}"/>
<input type="hidden" id="ga4-brandName" value="${fn:escapeXml(product.productBrandName)}"/>
<input type="hidden" id="ga4-categoryLeve1" value="${fn:escapeXml(product.categories[0].name)}"/>
<input type="hidden" id="ga4-categoryLeve2" value="${fn:escapeXml(product.categories[1].name)}"/>
<input type="hidden" id="ga4-categoryLeve3" value="${fn:escapeXml(product.categories[2].name)}"/>
<input type="hidden" id="ga4-multidimensional" value="${product.multidimensional}"/>
