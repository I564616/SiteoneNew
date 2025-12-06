<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

	      <div class="col-xs-12 col-sm-12 col-md-4 promo-wrapper">
	      <c:if test="${not empty l1categoryData.image.url}">
	         <c:choose>
	         <c:when test="${not empty l1categoryData.urlLink}">
		     <a href="${l1categoryData.urlLink}"> <img src="${l1categoryData.image.url}" class="img-rounded" alt="Promotion Spot" height="auto" width="100%" /></a>
			<div style="position: absolute;top:50px;width:93%;" class="text-center">
				<div class="homepage-promo-heading2 promo-banner-txt">${component.promotionalText} </div>
			</div>
			</c:when>
			<c:otherwise>
			    <a href="${urlLink}"> <img src="${l1categoryData.image.url}" class="img-rounded" alt="Promotion Spot" height="auto" width="100%" /></a>
			<div style="position: absolute;top:50px;width:93%;" class="text-center">
				<div class="homepage-promo-heading2 promo-banner-txt">${component.promotionalText} </div>
			</div>
			</c:otherwise>
			</c:choose>
			</c:if>
			<c:if test="${not empty component.buttonLabel}">
			 <div class="learn-more-btn promo-like-btn">
				<a href="${component.buttonURL}" class="btn btn-primary btn-block" onclick="">${component.buttonLabel}</a> 
			</div>
			</c:if>
	      </div>
	      <div class="cl visible-sm visible-xs"><br/></div>
