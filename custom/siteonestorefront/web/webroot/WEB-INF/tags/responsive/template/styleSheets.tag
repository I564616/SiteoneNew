<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ taglib prefix="cms" tagdir="/WEB-INF/tags/responsive/template/cms" %>

	<%-- Theme CSS files --%>
	<link rel="stylesheet" type="text/css" media="all" href="${themeResourcePath}/css/style.css?v=<%=de.hybris.platform.util.Config.getParameter("build.builddate")%>"/>

	<link rel="preload" type="text/css" media="all" href="${themeResourcePath}/css/addons.css?v=<%=de.hybris.platform.util.Config.getParameter("build.builddate")%>" as="style" onload="this.onload=null;this.rel='stylesheet'"/>
	<noscript><link rel="stylesheet" type="text/css" media="all" href="${themeResourcePath}/css/addons.css?v=<%=de.hybris.platform.util.Config.getParameter("build.builddate")%>"/></noscript>

	<%-- 
	## Need to eventually add a print.css like below
	## <link rel="stylesheet" href="${themeResourcePath}/css/print.css?v=<%=de.hybris.platform.util.Config.getParameter("build.builddate")%>" type="text/css" media="print" /> 
	--%>

	<cms:previewCSS cmsPageRequestContextData="${cmsPageRequestContextData}" />
