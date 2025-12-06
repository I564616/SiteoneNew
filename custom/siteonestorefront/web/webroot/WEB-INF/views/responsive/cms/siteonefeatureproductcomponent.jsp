<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

 <c:if test="${not empty categoryProduct}">
 
 <c:if test="${pagePosition eq 'homePageTop'}">	
		<div class="feature-product-home-pdp">
		<div class="row"> 	
				<div class="col-xs-12 padding0 feature-carousel-products">
					<div class="category-tiles product__facet product_mob_heading js-product-facet">
						<div class="text-center margin20 black-title visible-xs"><c:out value="${recommendationTitle}" /></div>
					<%-- <h3 class="text-center margin20 black-title"><spring:theme code="text.featured.products"/></h3> --%>
						<div class="facet js-facet">
							<div class="facet__list js-facet-list">
								<div class="product-item col-md-2 hidden-xs category-tiles category-tile-item">
									<div class="product-item-box product_col_heading">
										<div class="product_name_heading"><c:out value="${recommendationTitle}" /></div>
									</div>
								</div>
								<c:set var="isrc" value="?isrc=featured"/>
								<c:forEach items="${categoryProduct}" var="product" varStatus="status">
										<div class="category-tile-item ${(status.index >3)? 'hidden-md hidden-lg' : ''}"><product:featureProductListerHT product="${product}" isrc="${isrc}" loop="${status.index}"/></div>
	
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
		</div>
		</div>
	</c:if>
	<c:if test="${pagePosition eq 'PDPTop'}">	
		<div class="feature-product-home-pdp">
		<div class="row"> 	
				<div class="col-xs-12 padding0 feature-carousel-products">
					<div class="category-tiles product__facet product_mob_heading js-product-facet">
						<div class="text-center margin20 black-title visible-xs"><c:out value="${recommendationTitle}" /></div>
					<%-- <h3 class="text-center margin20 black-title"><spring:theme code="text.featured.products"/></h3> --%>
						<div class="facet js-facet">
							<div class="facet__list js-facet-list ">
								<div class="product-item col-md-2 hidden-xs category-tiles category-tile-item">
									<div class="product-item-box product_col_heading">
										<div class="product_name_heading"><c:out value="${recommendationTitle}" /></div>
									</div>
								</div>
								<c:set var="isrc" value="?isrc=featured"/>
								<c:forEach items="${categoryProduct}" var="product" varStatus="status">
										<div class="category-tile-item ${status.index < 3? 'radio-plus-icon' : ''} ${(status.index >3)? ' hidden-md hidden-lg' : ''} ${(status.index < 1)? ' first-coupon-badge' : ''}">
											<span class="this-item ${(status.index < 1)? '' : 'hidden'}">This <br/>Item</span>
											<product:featureProductListerDT product="${product}" isrc="${isrc}" loop="${status.index}"/>
										</div>

								</c:forEach>
							</div>
						</div>
					</div>
				</div>
		</div>
		</div>
	</c:if>
	<c:if test="${pagePosition eq 'homePageBottom' || pagePosition eq 'PDPBottom' || pagePosition eq 'PGP' || pagePosition eq 'Featured Lighting Fixtures'}">
	 <div class="feature-carousel">
	 <div class="row"> 	
			<div class="col-xs-12 padding0 feature-carousel-products">
				<div class="category-tiles product__facet js-product-facet">
				<h3 class="text-center margin20 black-title"><c:out value="${recommendationTitle}" /></h3>
					<div class="facet js-facet" style="overflow:auto">
						<div class="facet__list js-facet-list">
						<input type="hidden" id="carouselProduct" value="${categoryProduct.size()}"/>
						<c:if test="${categoryProduct.size()%4==0}">
							<c:set var="noi" value="${categoryProduct.size()/4}"/>
						</c:if>
						<c:if test="${categoryProduct.size()%4!=0}">
						<c:set var="noi" value="${(categoryProduct.size()/4)+1}"/>
						</c:if>
	 						<c:set var="isrc" value="?isrc=featured"/>
							<c:forEach items="${categoryProduct}" var="product" varStatus="status">
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
						 <div class="carousel-indicators hidden-xs hidden-sm">
								<c:set var="prd" value="${categoryProduct.size()}"/>
								<c:forEach begin="1" end="${noi}" varStatus="loop" >
									<span data-target="#siteoneHomepageBanner" data-slide-to="${loop.index}" class="${loop.index == 1 ?'carouselA': ''}" ></span>
								</c:forEach>
							</div>
					</div>
				</div>
			</div>
	</div>
	</div>
	</c:if>
</c:if>

