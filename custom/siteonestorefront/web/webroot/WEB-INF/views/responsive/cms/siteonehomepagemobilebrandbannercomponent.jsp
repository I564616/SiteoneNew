<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

 
				<div class="col-md-3 top-promo-banner-slot desktop-brandBanner hidden-xs hidden-sm padding-rightZero">
					<c:forEach var="brandBanner" items="${bannerList}">
					<c:choose>
				      <c:when test="${not empty brandBanner.urlForLink}">
						<c:url value="${brandBanner.urlForLink}" var="encodedUrl" />
				      </c:when>
				      <c:otherwise>
						<c:url value="${brandBanner.urlLink}" var="encodedUrl" />
				      </c:otherwise>
					 </c:choose>
							<div class="col-xs-12 col-sm-12 img-wrapper padding-rightZero">
							<a href="${encodedUrl}"><img title="${brandBanner.media.altText}" href="${encodedUrl}" alt="${brandBanner.media.altText}" class="img-responsive" src="${brandBanner.media.url}"></a>
							</div>
					</c:forEach>
			 
				</div>





<div class="product-carousel col-xs-12 col-sm-12 mobile-brandBanner hidden-md hidden-lg">
<div class="row">
	<div class="category-tiles product__facet js-product-facet">
			<div class="facet js-facet" style="overflow:auto">
				<div class="facet__list js-facet-list">
					<c:forEach var="brandBanner" items="${bannerList}">
					<c:choose>
				      <c:when test="${not empty brandBanner.urlForLink}">
						<c:url value="${brandBanner.urlForLink}" var="encodedUrl" />
				      </c:when>
				      <c:otherwise>
						<c:url value="${brandBanner.urlLink}" var="encodedUrl" />
				      </c:otherwise>
					 </c:choose>
						<div class="category-tile-item">
						<div class="row">
							<div class="col-xs-12 col-sm-12">
								<a href="${encodedUrl}"><img title="${brandBanner.media.altText}" alt="${brandBanner.media.altText}" class="img-responsive" src="${brandBanner.media.url}"></a>
							</div>
							</div>
						</div>
					</c:forEach>
				</div>
		</div>
	</div>
	</div>
</div>
