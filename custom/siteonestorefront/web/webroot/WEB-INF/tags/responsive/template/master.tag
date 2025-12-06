<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="false" rtexprvalue="true" %>
<%@ attribute name="metaDescription" required="false" %>
<%@ attribute name="metaKeywords" required="false" %>
<%@ attribute name="pageCss" required="false" fragment="true" %>
<%@ attribute name="pageScripts" required="false" fragment="true" %>

<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="analytics" tagdir="/WEB-INF/tags/shared/analytics" %>
<%@ taglib prefix="addonScripts" tagdir="/WEB-INF/tags/responsive/common/header" %>
<%@ taglib prefix="generatedVariables" tagdir="/WEB-INF/tags/shared/variables" %>
<%@ taglib prefix="debug" tagdir="/WEB-INF/tags/shared/debug" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="htmlmeta" uri="http://hybris.com/tld/htmlmeta"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

 <c:set var="popupcountry" value="<%=de.hybris.platform.util.Config.getParameter(\"popupcountry\")%>"/>
<c:set var="briteVerifyEnable" value="<%=de.hybris.platform.util.Config.getParameter(\"briteverify.enable\")%>"/> 
<c:set var="briteVerifyTimeout" value="<%=de.hybris.platform.util.Config.getParameter(\"briteverify.timeout\")%>"/>
<c:set var="globalContainerClasses" value="container-lg container-fluid" scope="application"/>


<!DOCTYPE html>
<html lang="${currentLanguage.isocode}">
<head>
	<title>${not empty pageTitle ? pageTitle : not empty cmsPage.title ? cmsPage.title : 'Accelerator Title'}</title>
	<%-- Meta Content --%>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
	<meta name="com.silverpop.brandeddomains" content="www.pages02.net,info.siteone.com,siteone.com,web.qa.siteone.com,www.johndeerelandscapes.com,www.siteone.com,sio-d-www.ms.ycs.io,sio-q-www.ms.ycs.io,sio-q2-www.ms.ycs.io,sio-s-www.ms.ycs.io,test.siteone.com" />
	<meta name="format-detection" content="telephone=no">
	<meta name="facebook-domain-verification" content="n4x4uwnap8jc0vgji0hephpw4c7i3g" />
	
	<%-- Additional meta tags --%>
	<c:choose>
		<c:when test="${cmsPage.uid eq 'productDetails'}">
		<meta name="keywords">
		<meta name="description" content="${fn:escapeXml(product.name)} ${' '} ${fn:escapeXml(product.productLongDesc)}">
		<meta name="robots" content="${metaRobots}">
		</c:when>
		<c:otherwise>
			<htmlmeta:meta items="${metatags}"/>
		</c:otherwise>
	</c:choose>
	
	
	<%-- Homepage Meta Data --%>
	<c:if test="${cmsPage.uid eq 'siteonehomepage'}">
				<meta property="og:url" content="https://www.siteone.com/en/">
				<meta property="og:type" content="website">
				<meta property="og:site_name" content="SiteOne Landscape Supply">
				<meta property="og:locale" content="en_US">
				<meta property="og:title" content="Landscape Supply, Irrigation & Agronomic Maintenance: SiteOne">
				<meta property="og:description" content="SiteOne is the green industry's No. 1 destination for landscape supplies, irrigation tools and agronomic maintenance. Learn about our Partners Program.">
				<meta property="og:image" content="/_ui/responsive/theme-lambda/images/siteone-ogimage.png">
	</c:if>
	<%-- PDP OG Meta Tag --%>
	<c:if test="${cmsPage.uid eq 'productDetails'}">
				<meta property="og:url" content="https://${header.host}${requestScope['jakarta.servlet.forward.request_uri']}">
				<meta property="og:type" content="product">
				<meta property="og:site_name" content="SiteOne Landscape Supply">
				<meta property="og:locale" content="${currentLanguage.isocode eq 'en' ? 'en_US' : 'es_US'}">
				<meta property="og:title" content="${fn:escapeXml(product.name)}">
				<meta property="og:description" content="${fn:escapeXml(product.name)} ${' '} ${fn:escapeXml(product.productLongDesc)}">
				<meta property="og:image" content="https://www.siteone.com${!galleryImages.isEmpty() ? galleryImages.get(0).thumbnail.url : ''}">
	</c:if>
	

	<c:if test="${cmsPage.uid eq 'siteonestoredetailsPage'}">
		<meta name="description"
			content="<spring:theme code="storeFinder.meta.desc1"/> ${store.storeId}# ${store.address.town}, ${store.address.region.isocodeShort}. <spring:theme code="storeFinder.meta.desc2"/>">
	</c:if>
	 
	<%-- Favourite Icon --%>
	<spring:theme code="img.favIcon" text="/" var="favIconPath"/>
	<link rel="shortcut icon" type="image/x-icon" media="all" href="${(originalContextPath eq '/')?'':originalContextPath}${favIconPath}" />
	<c:set value="https://${header.host}${requestScope['jakarta.servlet.forward.request_uri']}" var="requesturi"/>
	<c:set var="requesturihost" value="https://${header.host}"/>
	<c:choose>
		<c:when test="${fn:contains(requesturi, 'en')}">
		<c:set var = "requesturien" value = "${requesturi}" />
		<c:set var = "requesturies" value = "${fn:replace(requesturi,'/en/','/es/')}" />
		</c:when>
		<c:when test="${fn:contains(requesturi, 'es')}">
		<c:set var = "requesturies" value = "${requesturi}" />
		<c:set var = "requesturien" value = "${fn:replace(requesturi,'/es/','/en/')}" />
		</c:when>
    </c:choose>
	<c:choose>
		<c:when test="${requestScope['jakarta.servlet.forward.request_uri'].endsWith('/en')}">
		<c:set var = "requesturien" value = "${requesturi}" />
		<c:set var = "requesturies" value = "${fn:replace(requesturi,'/en','/es')}" />
		</c:when>
		<c:when test="${requestScope['jakarta.servlet.forward.request_uri'].endsWith('/es')}">
		<c:set var = "requesturies" value = "${requesturi}" />
		<c:set var = "requesturien" value = "${fn:replace(requesturi,'/es','/en')}" />
		</c:when>
	</c:choose>
	<c:choose>
		<c:when test="${not fn:contains(requesturihost, 'siteone.ca')}">
			<link rel="alternate" href="${requesturien}" hreflang="en" />
			<link rel="alternate" href="${requesturies}" hreflang="es" />
			<link rel="alternate" href="${fn:replace(requesturi, 'en/', '')}" hreflang="x-default" />
		</c:when>
	</c:choose>
    
    <c:set var="hasPreviousPage" value="${searchPageData.pagination.currentPage > 0}"/>
	<c:set var="hasNextPage" value="${(searchPageData.pagination.currentPage + 1) < searchPageData.pagination.numberOfPages}"/>
	<c:set var="currentUrl" value="${pageContext.request.queryString}"/>

	<c:if test="${!fn:contains(requestScope['jakarta.servlet.forward.request_uri'], '/search')}">
		<c:set var="requeststoreuri" value="https://${header.host}${requestScope['jakarta.servlet.forward.request_uri']}" />
		
	    <c:choose>
			<c:when test="${cmsPage.uid eq 'siteonestoredetailsPage'}">
				<c:choose>
					<c:when test="${requeststoreuri.endsWith('/')}">
						<link rel="canonical" href="${requeststoreuri.substring(0,requeststoreuri.length() - 1)}"/>
					</c:when>
					<c:otherwise>
		    			<link rel="canonical" href="https://${header.host}${requestScope['jakarta.servlet.forward.request_uri']}"/>
		    		</c:otherwise>	
				</c:choose>	
			</c:when>
		    <c:when test="${searchPageData.pagination.currentPage == null}">
		    	<c:choose>
		    		<c:when test="${requestScope['jakarta.servlet.forward.request_uri'].endsWith('/')}">
		    			<link rel="canonical" href="https://${header.host}${requestScope['jakarta.servlet.forward.request_uri']}"/>
		    		</c:when>
		    		<c:otherwise>
		    			<link rel="canonical" href="https://${header.host}${requestScope['jakarta.servlet.forward.request_uri']}"/>
		    		</c:otherwise>
		    	</c:choose>
			</c:when>
			<c:otherwise>
				<c:if test="${searchPageData.pagination.currentPage == 0}">
					<link rel="canonical" href="https://${header.host}${requestScope['jakarta.servlet.forward.request_uri']}"/>
				</c:if>
			</c:otherwise>
		</c:choose>
		
	    <c:if test="${hasPreviousPage}">
	    	<c:set value="${request.getParameter('page') - 1}" var="pageNumber"/>
	        <link rel="prev" href="https://${header.host}${requestScope['jakarta.servlet.forward.request_uri']}?q=%3Arelevance&page=${pageNumber}"/>
	   	</c:if>
	    
	    <c:if test="${hasNextPage}">
	    	<c:set value="${request.getParameter('page') + 1}" var="pageNumber"/>
	    	<link rel="next" href="https://${header.host}${requestScope['jakarta.servlet.forward.request_uri']}?q=%3Arelevance&page=${pageNumber}"/>
	    </c:if>
    
    </c:if>
	<c:if test="${cmsPage.uid eq 'requestaccount'}">
		<link rel="preload" as="image" href="https://${header.host}/_ui/responsive/theme-lambda/images/Banner.jpg" fetchpriority="high">
	</c:if>

	<%-- CSS Files Are Loaded First as they can be downloaded in parallel --%>
	<template:styleSheets/>

	<%-- Inject any additional CSS required by the page --%>
	<jsp:invoke fragment="pageCss"/>
	<%--<script type="text/javascript" src="${commonResourcePath}/js/jquery-2.1.1.min.js"></script>--%>
	<script type="text/javascript" src="${commonResourcePath}/js/jquery-3.5.1.min.js"></script> 
	<script src="https://www.sc.pages02.net/lp/static/js/iMAWebCookie.js?1fc719b-1047cb0959a-f528764d624db129b32c21fbca0cb8d6&h=www.pages02.net" type="text/javascript"></script> 
	<analytics:analytics/>
	<generatedVariables:generatedVariables/>
	<analytics:GTMGoogleAnalytics/>
	<analytics:GA4Analytics/>
</head>

<body class="${pageBodyCssClasses} ${cmsPageRequestContextData.liveEdit ? ' yCmsLiveEdit' : ''} language-${currentLanguage.isocode}">
<c:if test="${cmsPage.uid eq 'cmsitem_00123008'}">
	<script src="https://media.algorecs.com/rrserver/js/2.1/p13n.js"></script>
	<% String sessionId = session.getId(); %>
	<script charset="utf-8" type="text/javascript">
	 var R3_COMMON = new r3_common();
	 var rr_env = 'integration'; // It should be 'recs' in the production environment
	 window.R3_COMMON.setApiKey('66a4070c4c745c4d');
	 window.R3_COMMON.apiClientKey = 'eb1c6fc2c566e272';
	 window.R3_COMMON.setSessionId('<%= sessionId %>');
	 window.R3_COMMON.setBaseUrl('https://'+rr_env+'.algorecs.com/rrserver/');
	 window.R3_COMMON.setUserId('${user.guid}'); // if no user ID is available, please leave this blank
	
	 // Call multiple times to display more than one placement.
	 window.R3_COMMON.addPlacementType('generic_page.Promo1');
	
	 // Create the object for the generic page
	 var R3_GENERIC = new r3_generic();
	 // Dynamic Experiences Definition
	 window.R3_COMMON.placements = window.R3_COMMON.placementType;
	 window.R3_COMMON.pref = document.referrer;
	 var script = document.createElement('script');
	 script.src = 'https://cdn.algorecs.com/dashboard/applications/clientjs/2.1/client.js';
	 document.getElementsByTagName('head')[0].appendChild(script);
	 // call flush onload to render the placements
	 rr_flush_onload();
	 r3();
 	</script>
 	<script charset="utf-8" type="text/javascript">
	 RR.jsonCallback = function(){
	 // Place your rendering logic here. Actual code varies depending on your website implementation.
	 console.dir(RR.data.JSON.placements);
	 };
	 </script>
	
	</c:if>
<input type="hidden" class="pagename" value="${cmsPage.name}"/>
<c:choose>
<c:when test="${cmsPage.uid ne 'productDetails'}">
<input type="hidden" class="headingname" value="${categoryData.name}"/>
</c:when>
<c:otherwise>
<input type="hidden" class="headingname" value="${cmsPage.title}"/>
</c:otherwise>
</c:choose>

	<%-- Inject the page body here --%>
	<jsp:doBody/>


	<form name="accessiblityForm">
		<input type="hidden" id="accesibility_refreshScreenReaderBufferField" name="accesibility_refreshScreenReaderBufferField" value=""/>
	</form>
	
		<input id="isAnonymous" type="hidden" value="${isAnonymous}">
		
		<input type="hidden" id="briteVerifyUrl" value="${briteVerifyUrl}"/>
	    <input type="hidden" id="briteVerifyApiKey" value="${briteVerifyApiKey}"/>
	    <input type="hidden" id="parentUnitId" value="${parentUnitId}"/>
	    <input type="hidden" id="popupcountry" value="${popupcountry}"/>
	     <input type="hidden" id="hasSignedUp" value="${hasSignedUp}"/> 
	     <input type="hidden" id="briteVerifyEnable" value="${briteVerifyEnable}"/>
	      <input type="hidden" id="briteVerifyTimeout" value="${briteVerifyTimeout}"/>
	                                 
	<div id="ariaStatusMsg" class="skip" role="status" aria-relevant="text" aria-live="polite"></div>

	<%-- Load JavaScript required by the site --%>
	<template:javaScript/>
	<%-- Inject any additional JavaScript required by the page --%>
	<jsp:invoke fragment="pageScripts"/>

	<%-- Inject CMS Components from addons using the placeholder slot--%>
	<addonScripts:addonScripts/>
	<script>
    if (typeof(document.getElementById("flname")) != 'undefined' && document.getElementById("flname") != null)
    {
    let abc = document.getElementById("flname").innerHTML;
    document.getElementById("flname").innerHTML = abc.replace(/%20/g, " ");
    }
    </script>
    <noscript>
        <iframe src="https://www.googletagmanager.com/ns.html?id=${googleAnalyticsKey}"
        height="0" width="0" style="display:none;visibility:hidden"></iframe>
    </noscript>
</body>

<debug:debugFooter/>

</html>
