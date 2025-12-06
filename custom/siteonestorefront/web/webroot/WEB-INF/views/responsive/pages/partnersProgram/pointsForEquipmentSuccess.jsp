<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<template:page pageTitle="${pageTitle}">

<h1 class="headline"><spring:theme code="pointsForEquipmentSuccess.request.submitted" /></h1>
<br/><br/><br/>
<p class="h3"><spring:theme code="text.partner.success"/></p>
<br/><br/><br/>
<a href="<c:url value="/"/>" class="btn btn-primary"><spring:theme code="cart.page.continue"/></a>

</template:page>