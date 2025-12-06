<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>

<template:page pageTitle="${pageTitle}">
<!-- begin excentos integration code -->
<script 
	src="${excentosUrl}"> 
</script> 
<!-- end excentos integration code // start content area where Product Guide is displayed -->
<div id="xc_application">
	<div id="xc_application_pane"></div>  
	<div id="xc_loader"></div>
</div>
<!-- end excentos content area -->
	
</template:page>