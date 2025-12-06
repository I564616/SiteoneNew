<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<template:page pageTitle="${pageTitle}">
	<h1 class="headline">Inspiration Gallery</h1>
	<br>
	<hr/>
<div class="row">
	<cms:pageSlot position="Section3" var="feature" element="div"
		class="col-md-12">
		<cms:component component="${feature}" />
	</cms:pageSlot>
</div>


</template:page>

