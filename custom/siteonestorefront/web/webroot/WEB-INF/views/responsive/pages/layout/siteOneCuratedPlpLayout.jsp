<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>

<template:page pageTitle="${pageTitle}">
	
		<div class=" row top-nav hidden-xs hidden-sm">
			<cms:pageSlot position="Section1" var="feature" element="div" class="col-md-12 header-com hidden-xs hidden-sm" >
			<div class="nav-bar1">
			<cms:component component="${feature}"/>	
			</div>
			</cms:pageSlot>
		</div>
		<div class="cl"></div>
		
		<div class=" row top-nav mobile-navbar  hidden-md hidden-lg">
			<cms:pageSlot position="Section1A" var="feature" element="div" class="col-md-12 header-com hidden-md hidden-lg" >
				<div class="nav-bar1">
					<cms:component component="${feature}"/>	
				</div> 
			</cms:pageSlot>	
		</div>
		<div class="cl"></div>
		
		<div class="container-lg container-fluid">
	    <cms:pageSlot position="Section2" var="feature" element="div" class="col-md-12 padding0" >
			<div class="row content-section-container"><cms:component component="${feature}"/></div>
		</cms:pageSlot>
		</div>
		<div class="cl"></div>
		<div class="col-sm-12 col-md-12">
			<cms:pageSlot position="Section2N" var="feature" element="div" class="product-grid-right-result-slot">
				<div class="row"><cms:component component="${feature}" element="div" class="product__list--wrapper yComponentWrapper product-grid-right-result-component"/></div>
			</cms:pageSlot>
		</div>
		<div class="cl"></div>
		<div class="col-sm-12 col-md-12">
			<cms:pageSlot position="Section2A" var="feature" element="div" class="product-grid-right-result-slot">
				<div class="row"><cms:component component="${feature}" element="div" class="product__list--wrapper yComponentWrapper product-grid-right-result-component"/></div>
			</cms:pageSlot>
		</div>
		<div class="cl"></div>
		<cms:pageSlot position="Section2B" var="feature" element="div" class="col-md-12">
				<div class="row content-section-container"><cms:component component="${feature}"/></div>
		</cms:pageSlot>
		
		<div class="cl"></div>
		
		<cms:pageSlot position="Section3" var="feature" element="div" class="col-md-12 no-padding-md marginbottomp30 hidden-xs hidden-sm">
			<cms:component component="${feature}"/>
		</cms:pageSlot>
		<div class="cl"></div>
		<cms:pageSlot position="Section3A" var="feature" element="div" class="col-md-12 no-padding-md marginbottomp30 hidden-md hidden-lg">
			<cms:component component="${feature}"/>
		</cms:pageSlot>
		 <div class="cl"></div>
		 
		
		<cms:pageSlot position="Section4" var="feature" element="div" class="col-md-12 no-padding-md marginbottomp30 hidden-xs hidden-sm">
			<cms:component component="${feature}"/>
		</cms:pageSlot>
		<div class="cl"></div>
		<cms:pageSlot position="Section4A" var="feature" element="div" class="col-md-12 no-padding-md marginbottomp30 hidden-md hidden-lg">
			<cms:component component="${feature}"/>
		</cms:pageSlot>
		
		
		<div class="cl"></div>
		<cms:pageSlot position="Section5" var="feature" element="div" class="col-md-12 marginbottomp30 btm-promo hidden-xs hidden-sm" >
			<div class="row content-section-container"><cms:component component="${feature}"/></div>
		</cms:pageSlot>
		<cms:pageSlot position="Section5A" var="feature" element="div" class="col-md-12 btm-mob-promo hidden-md hidden-lg" >
			<div class="row content-section-container"><cms:component component="${feature}"/></div>
		</cms:pageSlot>


</template:page>
