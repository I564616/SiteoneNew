<%@ page contentType="text/plain" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="env" value="<%=de.hybris.platform.util.Config.getParameter(\"storefront.seo.index.env\")%>"/>
<c:set var="requesturi" value="https://${header.host}"/>
<c:choose>
<c:when test="${env.equalsIgnoreCase('prod')}">

#Allow full access
User-agent: *
Disallow:/search

<c:choose>
<c:when test="${fn:contains(requesturi, 'ca')}">
Sitemap: https://www.siteone.ca/sitemap.xml
</c:when>
<c:otherwise>
Sitemap: https://www.siteone.com/sitemap.xml  
</c:otherwise>
</c:choose>

</c:when>
<c:otherwise>
# For all robots
User-agent: *

# Block access to specific groups of pages
Disallow: /
</c:otherwise>
</c:choose>