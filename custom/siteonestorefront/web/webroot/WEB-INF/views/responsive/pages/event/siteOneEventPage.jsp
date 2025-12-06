<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>



<template:page pageTitle="${pageTitle}">

<script>
$(document).ready(function(){
	
	if(window.location.href.indexOf("/events?q=event") > -1) {
		$('html, body').animate({
	        scrollTop: $('#event-results-wrapper').offset().top
	    }, 'slow');
    }
});

</script>


		<h1 class="headline"><spring:theme code="siteOneEventPage.events" /></h1>

		<div class="col-md-5"> 
		<div class="row">
		<cms:pageSlot position="SectionA" var="feature">
				<cms:component component="${feature}" />
		 </cms:pageSlot> 
		 </div>
	</div>
	<div class="cl"></div>
	 
	<div class="col-md-5 event-data-content"> 
	    <div class="row">
	     <cms:pageSlot position="SectionB" var="feature" >
				<cms:component component="${feature}" />
		 </cms:pageSlot>
		 </div>
		 </div>

	<div class="cl"></div>
<div class="item-sec-border"></div>
<div id="event-results-wrapper"> 
        <div class="row">
        <div class="col-xs-3 mobile-refine-overlay event-facet-container">
        <cms:pageSlot position="ProductLeftRefinements" var="feature" element="div" class="search-grid-page-left-refinements-slot">
				<cms:component component="${feature}" element="div" class="search-grid-page-left-refinements-component"/>
		</cms:pageSlot>
		</div>
		<div class="col-sm-12 col-md-9 col-xs-12">
        <cms:pageSlot position="SectionC" var="feature" element="div" class="search-grid-page-result-grid-slot">
       		<cms:component component="${feature}" element="div" class="search-grid-page-result-grid-component"/>
        </cms:pageSlot>
       </div>
       </div>
        </div>
     </template:page>   
