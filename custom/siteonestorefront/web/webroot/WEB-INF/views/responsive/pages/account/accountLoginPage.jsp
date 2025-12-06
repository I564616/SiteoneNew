<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>

<template:page pageTitle="${pageTitle}">
	<div class="row">
		<div class="col-md-4 col-sm-12 col-xs-12 login-customerSec">
			<cms:pageSlot position="LeftContentSlot" var="feature" element="div"
				class="login-left-content-slot">
				<cms:component component="${feature}" element="div"
					class="login-left-content-component" />
			</cms:pageSlot>
		</div>
		
		<div class="col-md-6 col-sm-12 col-xs-12 registration_sec">
		
			<cms:pageSlot position="RightContentSlot" var="feature" element="div"
				class="login-right-content-slot">
				<cms:component component="${feature}" element="div"
					class="login-right-content-component" />
			</cms:pageSlot>
			<div class="cl"></div>
		</div>

	</div>
</template:page>