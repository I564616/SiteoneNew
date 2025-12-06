<%@ page contentType="text/plain" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<?xml version="1.0" encoding="UTF-8"?>
<sitemapindex xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
	<c:forEach items="${siteMapData}" var="siteData">
		<sitemap>
		    <loc>${siteData.key}</loc>
			<lastmod>${siteData.value}</lastmod>
		</sitemap>
	</c:forEach>
</sitemapindex>

