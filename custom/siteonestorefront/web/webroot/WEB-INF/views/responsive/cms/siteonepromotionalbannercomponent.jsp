<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%--  <div class="banner__component banner" style="position: relative;"> 
<div class="promo-sec" style="top:50px;">
    					<div class="homepage-promo-heading2 promo-banner-txt">${imageText}</div> 
    					
    				</div>
    			<div  class="promo-banner-btn"><a href="${encodedUrl}" class="btn btn-primary">${buttonName}</a></div> 
				<img title="${media.altText}" alt="${media.altText}" src="${media.url}" class="margin20"> --%>
				
	<c:if test="${not empty media.url && null !=media.url}">

				<div class="banner__component banner" style="position: relative;">
					<div class="banner__component banner" style="position: relative;">
						<div class="promo-sec" style="top:50px;">
							<div class="homepage-promo-heading2 promo-banner-txt">${imageText}</div> 
						</div>
						<div  class="promo-banner-btn"><a href="${encodedUrl}" class="btn btn-primary">${buttonName}</a></div> 
						<img title="${media.altText}" alt="${media.altText}" src="${media.url}" class="margin20">
					</div>
				</div>
	</c:if>				
				

			<!-- 	  </div>  -->
 