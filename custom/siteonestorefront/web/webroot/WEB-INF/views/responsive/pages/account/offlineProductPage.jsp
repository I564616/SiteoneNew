<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>

<template:page pageTitle="${pageTitle}">

	<div class="row">
		<div class="col-xs-12 col-md-12" >
			<cms:pageSlot position="BodyContent" var="comp" element="div">
				<cms:component component="${comp}" element="div"/>
			</cms:pageSlot>
		</div>
	</div>
</template:page>
