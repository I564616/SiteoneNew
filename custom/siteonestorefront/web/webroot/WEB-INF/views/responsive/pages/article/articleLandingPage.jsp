<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>


<template:page pageTitle="${pageTitle}">

	
	<div class="container__full">
		<div class="row">
		<cms:pageSlot position="Section1" var="feature" element="div" >
			<cms:component component="${feature}"/>
		</cms:pageSlot>
		
		<cms:pageSlot position="HeroBannerSlot" var="feature" element="div" class="article-hero-banner">
			<cms:component component="${feature}"/>
		</cms:pageSlot>
		
		<cms:pageSlot position="PromotionBannerSlot" var="feature" element="div" class="article-promo-banner">
			<cms:component component="${feature}"/>
		</cms:pageSlot>
		
		<div class="row">
			<cms:pageSlot position="ArticleNavigationSlot" var="feature" element="div" class="col-md-3">
					<cms:component component="${feature}"/>
			</cms:pageSlot>
	
			<cms:pageSlot position="ArticleContentSlot" var="feature" element="div" class="col-md-9">
					<cms:component component="${feature}"/>
			</cms:pageSlot>
		</div>
		
		<cms:pageSlot position="Section3" var="feature" element="div">
			<cms:component component="${feature}"/>
		</cms:pageSlot>
		
		<cms:pageSlot position="FeaturedArticleSlot" var="feature" element="div">
			<cms:component component="${feature}"/>
		</cms:pageSlot>
	
		</div>
	</div>
	
</template:page>