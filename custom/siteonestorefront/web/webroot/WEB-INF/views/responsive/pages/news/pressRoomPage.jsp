<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>



<template:page pageTitle="${pageTitle}">

	</div>	
 <!--  Desktop View Starts--> 
  <c:if test="${deviceData.desktopBrowser}">
   <cms:pageSlot position="SectionA" var="feature">
       		<cms:component component="${feature}"/>
        </cms:pageSlot> 
        
        <cms:pageSlot position="SectionB" var="feature">
       		<cms:component component="${feature}"/>
        </cms:pageSlot> 
</c:if> 

<!-- Desktop View Ends -->
	  
	<!--  Mobile View  Starts -->  
	<!-- Event List In Carousel -->
<c:if test="${deviceData.mobileBrowser}">
  <cms:pageSlot position="SectionD" var="feature">
       		<cms:component component="${feature}"/>
  </cms:pageSlot> 
        
</c:if>	
 
        
        <div class="row">
        <div class="col-md-12">
				<cms:pageSlot position="BodyContent" var="feature" element="div"
					class="search-grid-page-result-grid-slot">
					<cms:component component="${feature}" element="div"
						class="search-grid-page-result-grid-component" />
				</cms:pageSlot>
				
				<cms:pageSlot position="AllNewsEvents" var="feature" element="div"
					class="search-grid-page-result-grid-slot">
					<cms:component component="${feature}" element="div"
						class="search-grid-page-result-grid-component" />
				</cms:pageSlot>
				
				<%-- <cms:pageSlot position="AboutSOLandSupply" var="feature" element="div"
					class="search-grid-page-result-grid-slot">
					<cms:component component="${feature}" element="div"
						class="search-grid-page-result-grid-component" />
				</cms:pageSlot>
				
				<cms:pageSlot position="NewsDetailsSlot" var="feature" element="div"
					class="search-grid-page-result-grid-slot">
					<cms:component component="${feature}" element="div"
						class="search-grid-page-result-grid-component" />
				</cms:pageSlot> --%>
			</div>
		<div class="col-sm-12 col-md-9">
        
        <cms:pageSlot position="NewsBanner" var="feature" element="div" class="search-grid-page-result-grid-slot">
       		<cms:component component="${feature}" element="div" class="search-grid-page-result-grid-component"/>
        </cms:pageSlot>
       </div>
       </div>
        
     </template:page>   
