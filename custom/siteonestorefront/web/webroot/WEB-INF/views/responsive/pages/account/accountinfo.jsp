<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>



<template:page pageTitle="${pageTitle}">
<div class="accountIndo-wrapper">
	<h1 class="headline"><spring:theme code="account.information.title"/></h1>
		<br><br> 
	 <p class="black-title store-specialty-heading"><b>${companyInfo.name}</b></p> 
	 <div class="cl"></div>
 
	<div class="account-info-text"><spring:theme code="account.information.accountnumber"/>: ${companyInfo.displayId}</div>
	
	<spring:theme code="account.information.accounttypes"/>&nbsp;
	<c:forEach var="orderType" items="${companyInfo.orderType}" varStatus="loopStatus">	
	 ${orderType}
	  <c:if test="${!loopStatus.last}">,&nbsp;</c:if>
    </c:forEach>
    <br />
		<c:forEach items="${companyInfo.addresses}" var="address">
			<c:if test="${address.billingAddress}">
		    	<br/><p>${address.line1} <br> ${address.town},&nbsp;${address.country.name},&nbsp;${address.country.isocode}&nbsp;${address.postalCode} <br><a class="tel-phone" href="tel:${address.phone}">${address.phone}</a></p>
		    </c:if>
		</c:forEach>
	 <br>
	 <b><spring:theme code="account.information.admins"/>:&nbsp;</b>
	<c:forEach items="${admin}" var="admin" varStatus="loopStatus">	
		<c:set var="admintrim" value="${fn:trim(admin.name)}"/>
		<b>${admintrim}</b><c:if test="${!loopStatus.last}">, </c:if>
		
			   
	</c:forEach>
	<br>	
	<div style="padding:10px"></div>
<c:choose>
    <c:when test="${isAdmin}">
		<p><spring:theme code="accountinfo.contact.admin" />&nbsp;<a href="<c:url value="/contactus"/>"><spring:theme code="accountinfo.here" /></a></p>
	</c:when>
	<c:otherwise>

		<p><spring:theme code="accountinfo.contact.master.admin"/>&nbsp;<a href="<c:url value="/contactus"/>"><spring:theme code="accountinfo.contact" /></a>&nbsp;<spring:theme code="accountinfo.form" /></p>

		<!-- <p >To make changes to your account, please contact your<br>SiteOne administrator or <a href="<c:url value="/contactus"/>">contact SiteOne</a>.</p> -->

	</c:otherwise>
</c:choose>

</div> 
 
</template:page>