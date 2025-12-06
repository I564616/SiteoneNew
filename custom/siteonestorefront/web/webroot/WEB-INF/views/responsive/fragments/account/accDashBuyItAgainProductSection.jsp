<%@ page trimDirectiveWhitespaces="true" contentType="application/json" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
{ "recommendedProductsLayer":"<spring:escapeBody javaScriptEscape="true">
	<c:if test="${algonomyRecommendationEnabled}">
		<c:if test="${not empty buyAgainProductsrr1 and buyAgainProductsrr1.size() gt 1}">
			<div class="p-a-25 p-a-15-xs p-a-15-sm owl-theme dashboard-carousel-buyagain">
				<c:forEach items="${buyAgainProductsrr1}" var="product" varStatus="status">
					<div class="item text-align-center font-Geogrotesque p-x-10">
						<c:set var="isrc" value="?isrc=featured"/>
						<product:featureProductListerRecent product="${product}" isrc="${isrc}" loop="${status.index}"/>
					</div>
				</c:forEach>
			</div>
		</c:if>
	</c:if>
</spring:escapeBody>" }