<%@ page trimDirectiveWhitespaces="true" contentType="application/json" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<c:set var="variantSize" value="${6}"/>
{
    "variants":"<spring:escapeBody htmlEscape="true" javaScriptEscape="true">   
        <c:forEach items="${sessionVariantOpts}" var="variantProduct" varStatus="loop" begin="${variantSize*1 + 1}" >      
            <product:productVariantDisplay variantProduct="${variantProduct}" loopIndex="${loop.index}" product="${productData}" parentVariantCategoryNameList="${parentVariantCategoryNameList}"/>
        </c:forEach>
    </spring:escapeBody>"
}