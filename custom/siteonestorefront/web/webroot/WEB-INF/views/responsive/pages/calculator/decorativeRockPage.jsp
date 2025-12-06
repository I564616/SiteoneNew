<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<template:page pageTitle="${pageTitle}">
<spring:url value="/" var="homelink" htmlEscape="false"/>
<div id="materialsCalculators" class=" hidden-xs hidden-sm materialsCalculators">
<div class="col-md-12 col-xs-12 flex padding0 matCalpart1">
	<img alt="SiteOne Logo"
		src="/_ui/responsive/theme-lambda/images/DecorativeRock_img.png">
	<div class="row overlay-bcontent col-md-offset-1 m-t-50">
		<div class="">
			<common:calculatoricon />
			<span class="matcalpart-text">How much should I get?</span>
		</div>
		<h1 class="matcal-heading">Decorative Rock</h1>
		<div class="col-md-4 padding0 matcal-desc">This calculator will help you figure out how much product you need to complete your project.</div>
	</div>
</div>
<div class="cl"></div>
<template:projectCaclulator type="decorativeRockCalc" title="Decorative Rock Fill Calculator" />
</div>
<common:projectCalculatorMob type="decorativeRockCalc"></common:projectCalculatorMob>

</template:page>