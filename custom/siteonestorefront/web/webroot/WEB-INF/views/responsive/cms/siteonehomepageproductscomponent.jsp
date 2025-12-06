<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

 <c:if test="${not empty recommendedProducts}">
 	 <div class="feature-carousel">
 	 	<c:choose>
 		<c:when test="${title eq 'Recommended For You' }">
 			<c:set var="isrc" value="?isrc=recommended"/>
 			<c:set var="recommendedClass" value="col-md-9"/>
 			<c:set var="recommendedTitle">
	 			<div class="col-md-3 col-xs-12 col-sm-12 season-carousel-heading">
						<h1 class="headline text-center">${title}</h1>
				</div>
			</c:set>
			<c:set var="hideRecommended" value="hidden"/>
 		</c:when>
 		<c:otherwise>
 			<c:set var="isrc" value="?isrc=season"/>
 			<c:set var="recommendedClass" value="col-md-12"/>
 			<c:set var="recommendedTitle">
	 			
			</c:set>
			<c:set var="hideRecommended" value=""/>
 		</c:otherwise>
 	</c:choose>
 	  
	 <div class="row"> 
			${recommendedTitle}
			<div class="col-xs-12 ${recommendedClass} padding0 feature-carousel-products">
				<div class="category-tiles product__facet js-product-facet">
				<h3 class="text-center margin20 black-title ${hideRecommended}">${title}</h3>
					<div class="facet js-facet" style="overflow:auto">
						<div class="facet__list js-facet-list">
	<c:forEach items="${recommendedProducts}" var="product" varStatus="status">
	 		<div class="category-tile-item"><product:featureProductLister product="${product}" isrc="${isrc}" loop="${status.index}"/></div>
	</c:forEach>
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
</div>
</div>
</div>
</div>
</div>
</c:if>
