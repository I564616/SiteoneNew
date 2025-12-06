<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<template:page pageTitle="${pageTitle}">
	
		<cms:pageSlot position="Section3" var="feature" element="div" class="col-md-12" >
			<div class="row content-section-container"><cms:component component="${feature}"/></div>
		</cms:pageSlot>
		
		<div class="cl"></div>
	
		<cms:pageSlot position="Section2A" var="feature" element="div" class="col-md-3 leftNavContent hidden-xs hidden-sm">
				<div class="row"><cms:component component="${feature}"/>	</div>
		</cms:pageSlot>
		
		<cms:pageSlot position="Section2B" var="feature" element="div" class="col-md-8 col-md-offset-1 mb-margin30">
				<div class="col-md-12 content-section-container"><cms:component component="${feature}"/></div>
		</cms:pageSlot>
		
		<div class="cl"></div>
		<cms:pageSlot position="Section7A" var="feature" element="div" class="col-md-8">
				<div class="row content-section-container"><cms:component component="${feature}"/></div>
		</cms:pageSlot>
		
		<cms:pageSlot position="Section7B" var="feature" element="div" class="col-md-3">
				<cms:component component="${feature}"/>
		</cms:pageSlot>
	   <c:choose>
        <c:when test="${cmsPage.uid eq 'hardscapesLander' || cmsPage.uid eq 'stonecenterLander' || cmsPage.uid eq 'promotionSale' || cmsPage.uid eq 'partnerPerksPage'|| cmsPage.uid eq  'pilotPartnersProgramPage' }">
		<cms:pageSlot position="Section1" var="feature" element="div" class="col-md-12 pageSectionA">
			<cms:component component="${feature}"/>
		</cms:pageSlot>
		<cms:pageSlot position="Section4" var="feature" element="div" class="col-md-12">
			<cms:component component="${feature}"/>
		</cms:pageSlot>
		 <div class="cl"></div>
   <div class="${globalContainerClasses}">
   	<div class="rowmargin">
        <div class="col-xs-12 col-md-12 padding0">
        <div class="row">
            <div class="col-xs-12 col-md-4">
                <cms:pageSlot position="Section4A" var="feature" element="div">
                    <cms:component component="${feature}" element="div" class="yComponentWrapper"/>
                </cms:pageSlot>
            </div>
            <div class="col-xs-12 col-md-4">
                <cms:pageSlot position="Section4B" var="feature" element="div" >
                    <cms:component component="${feature}" element="div" class="yComponentWrapper"/>
                </cms:pageSlot>
                 
            </div>
            <div class="col-xs-12 col-md-4">
                <cms:pageSlot position="Section4C" var="feature" element="div">
                    <cms:component component="${feature}" element="div" class="yComponentWrapper"/>
                </cms:pageSlot>
            </div>
            </div>
        </div>
       </div> 
    </div>
		</c:when>
		<c:otherwise>
		<cms:pageSlot position="Section1" var="feature" element="div" class="col-md-6 pageSectionA">
			<cms:component component="${feature}"/>
		</cms:pageSlot>
		
		<cms:pageSlot position="Section4" var="feature" element="div" class="col-md-6">
			<cms:component component="${feature}"/>
		</cms:pageSlot>
		</c:otherwise>
		</c:choose>
		
		
		
		<div class="cl"></div>
		<div class="row">
		<div class="row stoneCenter-content">
		<cms:pageSlot position="Section2A" var="feature" element="div" class="col-md-3 mobileBannerContainer hidden-md hidden-lg">
				<div class="col-xs-12 col-sm-12"><cms:component component="${feature}"/>
				<div class="cl"></div>
					</div>
					<div class="cl"></div>
		</cms:pageSlot>
		
		<cms:pageSlot position="Section5" var="feature" element="div" class="col-md-12 bottom-banner-container">
			 <cms:component component="${feature}"/>
		</cms:pageSlot>
	</div>
		</div>
	


</template:page>
