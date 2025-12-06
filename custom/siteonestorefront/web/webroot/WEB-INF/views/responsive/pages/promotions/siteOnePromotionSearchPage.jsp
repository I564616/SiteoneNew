<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="content" tagdir="/WEB-INF/tags/responsive/content" %>
<%@ taglib prefix="promotion" tagdir="/WEB-INF/tags/responsive/promotion" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>


<template:page pageTitle="${pageTitle}">
<div class="product__list--wrapper">
<div>
	    
		<c:if test="${null != promotions_searchPageData.results}">
		 		<div style="position:relative;">
		 		<h1 class="promotionsHeading headline promoStatCap">${fn:toLowerCase(promotionData.displayName)}</h1>
		 	<br><img src="${promotionData.productBanner.url}" style="width:116%;height:200px;position: relative;left: -8%;" />
		 	<div class="promotion-landing-banner" style="margin-top:50px;">
		 	<div class="bannerContent homepage-banner-heading">${fn:toUpperCase(promotionData.code)} </div>
		 	<div class="bannerHeading homepage-banner-heading" style="font-size:16px;margin-top:20px;">${promotionData.code} </div>
		<%--  	${promotionData.description} --%>
		 	</div>
		 	</div>
		 	<p class="promotionsDetails margin20 promoParaFont"><spring:theme code="siteOnePromotionSearchPage.text.1" />
</p>
		 	
		 	<nav:pagination top="true"  supportShowPaged="${promotions_isShowPageAllowed}" supportShowAll="${promotions_isShowAllAllowed}"  searchPageData="${promotions_searchPageData}" searchUrl="${promotions_searchPageData.currentQuery.url}"  numberPagesShown="${promotions_numberPagesShown}" promotions="true"/> 
			<div id="promotionTab">
			<ul class="product__listing product__grid">
            <c:forEach items="${promotions_searchPageData.results}" var="product" varStatus="status">
			<div class="product-item col-md-3 col-sm-4 col-xs-6">
			<div class="product-item-box">
			<product:productListerGridItem product="${product}" loop="${status.index}"/>
				</div>
			    </div>
			 </c:forEach>
			</ul>
			 <nav:pagination top="false"  supportShowPaged="${promotions_isShowPageAllowed}" supportShowAll="${promotions_isShowAllAllowed}"  searchPageData="${promotions_searchPageData}" searchUrl="${promotions_searchPageData.currentQuery.url}"  numberPagesShown="${promotions_numberPagesShown}" promotions="true" footer="true"/> 
	   </div>
		<div>
		${promotionData.notes}
		</div>
		</c:if>
	</div>
	</div>
</template:page>


		