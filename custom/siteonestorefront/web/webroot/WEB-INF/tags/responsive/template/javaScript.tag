<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="cms" tagdir="/WEB-INF/tags/responsive/template/cms" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:url value="/" var="siteRootUrl"/>

<template:javaScriptVariables/>	
	<script src="https://siteone.usablenet.com/pt/start" type="text/javascript" async></script> 	
	<script type="text/javascript" src="/_ui/responsive/common/js/_main.js?v=<%=de.hybris.platform.util.Config.getParameter("build.builddate")%>"></script>
	<script type="text/javascript" src="/_ui/responsive/common/js/_addons.js?v=<%=de.hybris.platform.util.Config.getParameter("build.builddate")%>"></script>
	<script src="/_ui/addons/smarteditaddon/shared/common/js/webApplicationInjector.js?v=<%=de.hybris.platform.util.Config.getParameter("build.builddate")%>"></script>
	<script src="/_ui/addons/smarteditaddon/shared/common/js/reprocessPage.js?v=<%=de.hybris.platform.util.Config.getParameter("build.builddate")%>"></script>
	<script src="/_ui/addons/smarteditaddon/shared/common/js/adjustComponentRenderingToSE.js?v=<%=de.hybris.platform.util.Config.getParameter("build.builddate")%>"></script>
		

<c:if test="${isMonthlyFlyerPage}">
	<script type="text/javascript" src="/_ui/responsive/common/js/jquery.browser.min.js"></script>
	<script type="text/javascript" src="/_ui/responsive/common/js/jquery.ba-postmessage.min.js"></script>
</c:if>

<!--BEGIN QUALTRICS WEBSITE FEEDBACK SNIPPET-->
<c:if test="${not fn:contains(header['User-Agent'],'SiteOneEcomApp')}"> 

</c:if>
<!--END WEBSITE FEEDBACK SNIPPET-->

<cms:previewJS cmsPageRequestContextData="${cmsPageRequestContextData}" />
