<%@ page trimDirectiveWhitespaces="true" contentType="application/json" %>
<%@ taglib prefix="productItem" tagdir="/WEB-INF/tags/responsive/product/cardView" %>
<%@ taglib prefix="listviewComponent" tagdir="/WEB-INF/tags/responsive/product/listView" %>
<%@ taglib prefix="listviewVariantComponent" tagdir="/WEB-INF/tags/responsive/product/listView/variants" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

{
    "variants":"<spring:escapeBody htmlEscape="true" javaScriptEscape="true">
<c:forEach var="entry" items="${baseVariantMap}">
    <c:forEach var="variantProduct" items="${entry.value}">
        <listviewVariantComponent:productListViewVariantRow productCode="${entry.key}" variantProduct="${variantProduct}" />
    </c:forEach>
</c:forEach>
</spring:escapeBody>"
}