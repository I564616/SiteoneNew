<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<template:page pageTitle="${pageTitle}">
<h1 class="category-landing-page-h1">${categoryData.name}</h1>
<input type="hidden" value="${method}" class="referrermethod"/>
<input type="hidden" value="${methodMetaData}" class="referrermethoddata"/>

<div class="category-banner hidden-xs hidden-sm desktop-carousal">
<cms:pageSlot position="SectionS1" var="feature">
<cms:component component="${feature}"/>
</cms:pageSlot>
</div>
        <div class="category-banner mobile-carousal hidden-md hidden-lg">
<cms:pageSlot position="SectionS2" var="feature">
<cms:component component="${feature}"/>
</cms:pageSlot>
</div>
<div class="category-banner hidden-md hidden-lg">
<cms:pageSlot position="SectionS4" var="feature">
<cms:component component="${feature}"/>
</cms:pageSlot>
</div>
		
<div class="cl"></div>
<div class="hidden-md hidden-lg">
<div class="marginTop35"></div>
</div>
<div class="row">
<div class="col-xs-12">
<div class="row categoryRow">
<input type="hidden" id="categoryCodePR" value="${categoryCode}">
<c:if test="${null != categoryData}">
<c:forEach items="${categoryData.subCategories}" var="subcategory" >
<c:if test="${subcategory.productCount != 0}">
						<div class="col-xs-6 col-md-3 col-sm-3 category-box-sec">
<c:url var="subcaturl" value="${subcategory.url}"/>
<a href="${subcaturl}">
							<span class="category-box">
<c:choose>
<c:when test="${null != subcategory.image}">
<img title="${subcategory.image.altText}" alt="${subcategory.image.altText}" src="${subcategory.image.url}">
</c:when>  
<c:otherwise>
<img class="lazyOwl" title="${subcategory.name}" alt="${subcategory.name}" src="/_ui/responsive/theme-lambda/images/missing_product_EN_300x300.jpg" style="display: inline;">
</c:otherwise>
</c:choose>
							<span class="category-text">${subcategory.name}</span>
							<span class="category-text-count">(<fmt:formatNumber type = "number"  groupingUsed="true"  value ="${subcategory.productCount}" />)</span>
							</span>
						</a>
</div>
</c:if>
</c:forEach>
</c:if>
</div>
</div>
</div>
<div class="cl"></div>
<div class="col-sm-12 col-md-12">
	<cms:pageSlot position="Section2N" var="feature" element="div" class="product-grid-right-result-slot">
		<div class="row"><cms:component component="${feature}" element="div" class="product__list--wrapper yComponentWrapper product-grid-right-result-component"/></div>
	</cms:pageSlot>
</div>
<c:if test="${algonomyRecommendationEnabled}">
<div class="cl"></div>
<div class="mb-margin15">
<div class="featured-content margin-top-20 marginBottom30" id="RecommendedProductSlotCategory">

</div>
</div>
</c:if>
<div class="cl"></div>
<div class="marginTop35"></div>
	<div class="hidden-sm hidden-xs">
    	 <cms:pageSlot position="SectionC" var="feature">
    	 	 <cms:component component="${feature}" />
    	 </cms:pageSlot>
    </div>
    <div class="hidden-md hidden-lg">
    	 <cms:pageSlot position="SectionCMobile" var="feature">
    	 	 <cms:component component="${feature}"/>
    	 </cms:pageSlot>
    </div>

<div class="category-store-specialty-heading clp-trio-banner">
<div class="row">
	<div class="col-xs-12 col-md-12 margin40 homepage-features-wrapper">
        <div class="row">
            <div class="col-xs-12 col-md-4 padding-zero">
<cms:pageSlot position="SectionD1" var="feature">
<cms:component component="${feature}"/>
</cms:pageSlot>
</div>
<div class="col-xs-12 col-md-4 padding-zero">
<cms:pageSlot position="SectionD2" var="feature">
<cms:component component="${feature}"/>
</cms:pageSlot>
</div>
<div class="col-xs-12 col-md-4 padding-zero">
<cms:pageSlot position="SectionD3" var="feature">
<cms:component component="${feature}"/>
</cms:pageSlot>
</div>
</div>
</div>
</div>
</div>
<div class="cl"></div>
<div class="row">
<div class="mb-margin15">
<div class="category-banner bottom-category-article-banner">
<cms:pageSlot position="SectionE" var="feature">
<cms:component component="${feature}"/>
</cms:pageSlot>
</div>
</div>
</div>
<product:productDisclaimer/>
   
</template:page>
