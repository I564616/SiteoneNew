	<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>



<template:page pageTitle="${pageTitle}">
<br/> 
<c:choose>
	<c:when test = "${isEmailAlreadyExists == 'true'}">
	<div class="cl"></div>
	<br> 
	<p><spring:theme code="requestaccount.message" /></p>
	<div class="cl"></div>
	<br>
	</c:when>
	<c:otherwise>
	<span class="hidden-xs hidden-sm"><br/></span>
	<h1 class="headline hidden-xs"><spring:theme code="requestaccountsuccess.acc.requested" /></h1>
	<h1 class="headline2 hidden-md hidden-lg hidden-sm"><spring:theme code="requestaccountsuccess.acc.requested" /></h1>
	<div class="cl"></div>
	<br> 
	<p><spring:theme code="requestaccountsuccess.thank.you" /></p>
	<div class="cl"></div>
	<br> 
	</c:otherwise>
</c:choose>
<p class="black-title"><b><a href="<c:url value="/"/>"><spring:theme code="requestaccountsuccess.return.homepage" /></a></b> </p>
<br>

		<div class="row">
		<div class="col-xs-12 col-md-12 account-dashboard-promo">
			 	<div class="row">
			 <div class="col-xs-12 col-md-6">
			 	<div class="row">
				<cms:pageSlot position="Promotion_SlotA" var="feature" element="div"
					class="dropdown">
					<cms:component component="${feature}" />
				</cms:pageSlot>
				 </div>
			 </div>
			<div class="col-xs-12 col-md-6">
			 	<div class="row">
				<cms:pageSlot position="Promotion_SlotB" var="feature" element="div"
					class="dropdown">
					<cms:component component="${feature}" />
				</cms:pageSlot>
				</div>
			 </div>
		</div>
		</div>
	 </div>


</template:page>