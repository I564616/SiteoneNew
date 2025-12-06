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

{
"recommendedProductsLayer":"<spring:escapeBody javaScriptEscape="true">
<c:if test="${algonomyRecommendationEnabled}">	
	<c:if test="${not empty recommendedProducts}">
		<div class="feature-carousel">
			<div class="row"> 	
				<div class="col-xs-12 padding0 feature-carousel-products">
					<div class="category-tiles product__facet js-product-facet">
					<h2 class="text-center margin20 black-title h3"><c:out value="${recommendationTitle}" /></h2>
						<div class="facet js-facet" style="overflow:auto">
							<div id="recommendedProductBanner" class="facet__list js-facet-list">
							<input type="hidden" id="carouselProduct" value="${recommendedProducts.size()}"/>
							<c:if test="${recommendedProducts.size()%3==0}">
								<c:set var="noi" value="${recommendedProducts.size()/3}"/>
							</c:if>
							<c:if test="${recommendedProducts.size()%3!=0}">
							<c:set var="noi" value="${(recommendedProducts.size()/3)+1}"/>
							</c:if>
		 						<c:set var="isrc" value="?isrc=featured"/>
								<c:forEach items="${recommendedProducts}" var="product" varStatus="status"><div class="category-tile-item"><product:featureProductLister product="${product}" isrc="${isrc}" loop="${status.index}"/></div></c:forEach>
								 <div class="category-tile-prev active">
								     <svg width="6" height="9.29" viewBox="0 0 6 9.29" class="bi bi-chevron-left" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
								         <path d="M4.154,5.8.2,1.846A.694.694,0,0,1,.2.861L.861.2a.694.694,0,0,1,.985,0L4.645,3,7.445.2a.694.694,0,0,1,.985,0l.656.656a.694.694,0,0,1,0,.985L5.136,5.8A.691.691,0,0,1,4.154,5.8Z" transform="translate(6) rotate(90)"/>
								     </svg>
								 </div>
								 <div class="category-tile-next active">
								     <svg width="6" height="9.29" viewBox="0 0 6 9.29" class="bi bi-chevron-right" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
								         <path d="M4.154.2.2,4.154a.694.694,0,0,0,0,.985L.861,5.8a.694.694,0,0,0,.985,0L4.645,3l2.8,2.8a.694.694,0,0,0,.985,0l.656-.656a.694.694,0,0,0,0-.985L5.136.2A.691.691,0,0,0,4.154.2Z" transform="translate(6) rotate(90)"/>
								     </svg>
								 </div>
		
							</div>
							 <div class="carousel-indicators hidden-xs hidden-sm">
							 </div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:if>
</c:if>
</spring:escapeBody>"
}