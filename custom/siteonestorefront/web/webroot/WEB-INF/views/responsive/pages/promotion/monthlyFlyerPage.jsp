<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<template:page pageTitle="${pageTitle}">

<h2><spring:theme code="monthlyFlyerPage.monthly.flyer" /></h2>

<iframe class="catalog_iframe" webkitallowfullscreen="" mozallowfullscreen="" allowfullscreen="" id="catalog" title="Flyers" frameborder="0" allowtransparency="true" scrolling="no" width="100%" src="https://www.siteonecatalog.com" height="1192px" style="min-height: 600px; overflow-y: hidden;">
</iframe>

</template:page>