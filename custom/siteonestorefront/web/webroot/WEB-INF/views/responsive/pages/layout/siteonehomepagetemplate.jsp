<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<template:page pageTitle="${pageTitle}">
    <div class="padding-zero home-mobile">
    <div class="white-bg">
    <div class="${globalContainerClasses}">
    <div class="hidden-sm hidden-xs desktop-carousal">
    	 		 <cms:pageSlot position="SectionS1" var="feature">
    	     		 <cms:component component="${feature}" />
    	  		</cms:pageSlot>
    	  		</div>
    	  		
    	  		 <div class="hidden-md hidden-lg  mobile-carousal">
    	  		   <cms:pageSlot position="SectionS2" var="feature">
    	     		 <cms:component component="${feature}"/>
    	  		</cms:pageSlot>    	  		
    	  		<cms:pageSlot position="Section2H" var="feature">
    	     		 <cms:component component="${feature}" />
    	  		</cms:pageSlot> 
    	  		</div>
    	  		</div>
    </div>
	    <div class="${globalContainerClasses}">
        	<div class="col-xs-12 col-md-12 padding-zero">
				<div class="row">
              <div class="${globalContainerClasses} hidden">
                    <div class="col-md-4 my-account-link">
                     <cms:pageSlot position="Section1A" var="feature">
                         <cms:component component="${feature}" />
                    </cms:pageSlot>
                    </div>
                </div>
               
                 	 
    	  		  	  		
    	  		<div class="cl"> </div>
    	  		<%-- <div>
    	  		<div class="homepage-reommended margin20">
    	  		<div class="featured-content">
    	  		<cms:pageSlot position="Section3H" var="feature">
    	     		 <cms:component component="${feature}" />
    	  		</cms:pageSlot>
    	  		<div class="cl"> </div>
    	  		</div>
    	  		 </div>
        	</div> --%>
    	</div>
    </div>
    <c:if test="${algonomyRecommendationEnabled}">
    <div class="cl"></div>
	<div class="mb-margin15">
		<div class="featured-content margin-top-20 marginBottom30" id="RecommendedProductSlotHomePageTop">
			
		</div>
	</div>
	</c:if>
    <div class="${globalContainerClasses} hidden">
        <div class="cl"></div>
        <div class="text-center margin20 home-mobile-heading"><h2 class="popular-categories"><spring:theme code="siteonehomepagetemplate.popular.categories" /></h2></div>
        <cms:pageSlot position="Section2" var="feature" element="div" class="wrapper-home-categories">
            <cms:component component="${feature}" element="div" class="yComponentWrapper wrap-home-category"/>
        </cms:pageSlot>
    </div>
   
     <div class="cl"></div>
    <div>
        <div class="col-xs-12 col-md-12 homepage-features-wrapper margin-top-20">
        <div class="row">
            <div class="col-xs-12 col-md-4 padding-zero">
                <cms:pageSlot position="Section4A" var="feature" element="div">
                    <cms:component component="${feature}" element="div" class="yComponentWrapper"/>
                </cms:pageSlot>
            </div>
            <div class="col-xs-12 col-md-4 padding-zero">
                <cms:pageSlot position="Section4B" var="feature" element="div" >
                    <cms:component component="${feature}" element="div" class="yComponentWrapper"/>
                </cms:pageSlot>
                 
            </div>
            <div class="col-xs-12 col-md-4 padding-zero">
                <cms:pageSlot position="Section4C" var="feature" element="div">
                    <cms:component component="${feature}" element="div" class="yComponentWrapper"/>
                </cms:pageSlot>
            </div>
            </div>
            
        </div>
    </div>
    <div class="cl"></div>
      <div class="row">
    <div class="siteone-banner-bg margin40">
     
    <span class="hidden-xs hidden-sm">
    	<cms:pageSlot position="Section4H4" var="feature">
		  		<cms:component component="${feature}" />
		</cms:pageSlot>
	</span>
	<span class="hidden-md hidden-lg">
		   	<cms:pageSlot position="Section4H5" var="feature">
		  		<cms:component component="${feature}" />
		   	</cms:pageSlot>
	</span>
   	</div>
   	 </div>
     <div class="promo-bannerAdd">	
    	  		<div class="hidden-sm hidden-xs">
    	 		 <cms:pageSlot position="Section5D" var="feature">
    	     		 <cms:component component="${feature}" />
    	  		</cms:pageSlot>
    	  		</div>
    	  		<div class="hidden-md hidden-lg">
    	  		   <cms:pageSlot position="Section5E" var="feature">
    	     		 <cms:component component="${feature}"/>
    	  		</cms:pageSlot>
    	  		</div>
    	  		 <div class="cl"></div>
    </div>
     <div class="cl"></div>
    <%-- <div  class="row no-margin">
     <div class="margin20">
     <div class="featured-content">
     <cms:pageSlot position="Section5H" var="feature">
  		<cms:component component="${feature}" />
   	</cms:pageSlot>
   	</div>
   	</div>
   	</div> --%>
    <div class="marginBottom30">
    <div  class="row mb-padding15">
    	<div class="col-xs-12 col-md-6 margin20 learn-more-container">
	 		 <cms:pageSlot position="Section3A" var="feature">
	     		 <cms:component component="${feature}" />
	  		</cms:pageSlot>
    	</div>
    	<div class="col-xs-12 col-md-6 margin20 learn-more-container">
	 		 <cms:pageSlot position="Section3B" var="feature">
	     		 <cms:component component="${feature}" />
	  		</cms:pageSlot>
    	</div>
    	</div>
    </div>	
  
    <c:if test="${algonomyRecommendationEnabled}">
    <div class="cl"></div>
	<div class="mb-margin15">
		<div class="featured-content margin-top-20 marginBottom30" id="RecommendedProductSlotHomePageBottom">
			
		</div>
	</div> 
	</c:if> 
<div class="cl"></div>
</div>
    <div class="row full-width-container no-gutter hidden">
        <cms:pageSlot position="Section5" var="feature" element="div">
            <div class="additinal-message homePage-customerComponent">
                <cms:component component="${feature}" element="div" class="yComponentWrapper"/>
            </div>
        </cms:pageSlot>
    </div>
    </div>
</template:page>

<script type="application/ld+json">
    {
      "@context" : "https://schema.org",
      "@type" : "WebSite",
      "name" : "SiteOne",
      "alternateName" : "SiteOne Landscape Supply, siteone.com",
      "url" : "https://www.siteone.com/",
      "potentialAction": {
        "@type": "SearchAction",
        "target": {
          "@type": "EntryPoint",
          "urlTemplate": "https://www.siteone.com/en/search/?searchtype=product&text={search_term_string}"
        },
        "query-input": "required name=search_term_string"
      }
    }
</script>
<script type='application/ld+json'> 
{
  "@context": "http://www.schema.org",
  "@type": "Organization",
  "url": "https://www.siteone.com/",
  "name": "SiteOne",
  "logo": "https://www.siteone.com/medias/sitelogo.svg?context=bWFzdGVyfGltYWdlc3w5MTI4fGltYWdlL3N2Zyt4bWx8aW1hZ2VzL2hkYy9oZDMvODc5NjY1MDE3NjU0Mi5zdmd8YzliMzhlNjY1NTgwNjMwNWQyNmEwNTQzN2RiYzZlZjliODdkNmVjMDMxYzBlMWY4NTA4OGU5NGI3ODYxOGEzZQ",
  "address": {
    "@type": "PostalAddress",
    "streetAddress": "300 Colonial Center Parkway, Suite 600",
    "addressLocality": "Roswell",
    "addressRegion": "Georgia",
    "postalCode": "30076",
    "addressCountry": "US"

  },
  "sameAs" : [ 
    "https://www.facebook.com/SiteOneLandscape/",
    "https://twitter.com/SiteOneSupply",
    "https:https://www.linkedin.com/company/10366520/",
    "https://www.youtube.com/channel/UC3D13yPhsL5XJHXgnJFDAmA"]
}
 </script>
 