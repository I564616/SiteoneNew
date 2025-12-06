<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%> 
<template:page pageTitle="${pageTitle}">
<script src="https://www.google.com/recaptcha/api.js"></script>

 <div class="col-md-6 col-sm-6 col-xs-12  row">
		<h1 class="headline"><spring:theme code="pointsForEquipmentPage.points.for.equip" /></h1>
		<p class="textPartnerForm"> <spring:theme code="text.partner.form"/></p>
</div>

<div class="cl"></div>
 <div class="col-md-4 col-sm-6 col-xs-12  row">
 <br/><br/>
<p class="store-specialty-heading"><b><spring:theme code="pointsForEquipmentPage.dealer.info" /></b></p>
<br/><c:url value="/pointsForEquipment/form" var="pointsFormSubmit"/>
<form:form action="${pointsFormSubmit}" method="POST" modelAttribute="siteOnePointsForEquipmentForm">
<div class="form-group">
<form:label path="dealerContactName"><spring:theme code="pointsForEquipmentPage.contact.name" /></form:label><br/>
<form:input idKey="dealerContactName" path="dealerContactName" class="form-control"/>
<span id="dealerContactNameError"></span>
 </div>
 
 <div class="form-group">
<form:label path="dealerName"><spring:theme code="pointsForEquipmentPage.dealer.name" /></form:label>
<br/>
<form:input idKey="dealerName" path="dealerName" class="form-control"/>
<div class="cl"></div>
<span id="dealerNameError"></span>
</div>


<div class="form-group">
<form:label path="dealerAddressLine1"><spring:theme code="pointsForEquipmentPage.address.line.1" /></form:label>
<br/>
<form:input idKey="dealerAddressLine1" path="dealerAddressLine1" class="form-control"/>
<div class="cl"></div>
<span id="dealerAddressLine1Error"></span>
</div>


<div class="form-group">
<form:label path="dealerAddressLine2"><spring:theme code="pointsForEquipmentPage.address.line.2" /></form:label>
<br/>
<form:input idKey="dealerAddressLine2" path="dealerAddressLine2" class="form-control"/>
<div class="cl"></div>
<span id="dealerAddressLine2Error"></span>
</div>

<div class="form-group">
<form:label path="dealerCity"><spring:theme code="pointsForEquipmentPage.city" /></form:label>
<br/>
<form:input idKey="dealerCity" path="dealerCity" class="form-control"/>
<div class="cl"></div>
<span id="dealerCityError"></span>
</div>

<div class="form-group select-grey">
<form:label path="dealerState"><spring:theme code="pointsForEquipmentPage.state" /></form:label>
<br/>
<form:select idKey="dealerState" path="dealerState" class="form-control">
<form:option value=""><spring:theme code="pointsForEquipmentPage.select" /></form:option>
 <form:options items="${states}"></form:options>
</form:select>
</div>

<div class="form-group">
<form:label path="dealerZipCode"><spring:theme code="pointsForEquipmentPage.zip.code" /></form:label>
<br/>
<form:input idKey="dealerZipCode" path="dealerZipCode" class="form-control"/>
<div class="cl"></div>
<span id="dealerZipCodeError"></span>
</div>


<br/><br/>
<p class="store-specialty-heading"><b><spring:theme code="pointsForEquipmentPage.customer.info" /></b></p>
<div class="form-group">
<br/>
<form:label path="customerContactName"><spring:theme code="pointsForEquipmentPage.contact.name" /></form:label>
<br/>
<form:input idKey="customerContactName" path="customerContactName" class="form-control"/>
<div class="cl"></div>
<span id="customerContactNameError"></span>
</div>

<div class="form-group">
<form:label path="companyName"><spring:theme code="pointsForEquipmentPage.company.name" /></form:label>
<br/>
<form:input idKey="companyName" path="companyName" class="form-control"/>
<div class="cl"></div>
<span id="companyNameError"></span>
</div>


<div class="form-group">
<form:label path="jdlAccountNumber"><spring:theme code="pointsForEquipmentPage.account.no" /></form:label>
<br/>
<form:input idKey="jdlAccountNumber" path="jdlAccountNumber" class="form-control"/>
<div class="cl"></div>
<span id="jdlAccountNumberError"></span>
</div>


<div class="form-group">
<form:label path="customerAddressLine1"><spring:theme code="pointsForEquipmentPage.address.line.1" /></form:label>
<br/>
<form:input idKey="customerAddressLine1" path="customerAddressLine1" class="form-control"/>
<div class="cl"></div>
<span id="customerAddressLine1Error"></span>
</div>


<div class="form-group">
<form:label path="customerAddressLine2"><spring:theme code="pointsForEquipmentPage.address.line.2" /></form:label>
<br/>
<form:input idKey="customerAddressLine2" path="customerAddressLine2" class="form-control"/>
<div class="cl"></div>
<span id="customerAddressLine2Error"></span>
</div>



<div class="form-group">
<form:label path="customerCity"><spring:theme code="pointsForEquipmentPage.city" /></form:label>
<br/>
<form:input idKey="customerCity" path="customerCity" class="form-control"/>
<div class="cl"></div>
<span id="customerCityError"></span>
</div>


<div class="form-group select-grey">
<form:label path="customerState"><spring:theme code="pointsForEquipmentPage.state" /></form:label>
<br/>
<form:select idKey="customerState" path="customerState" class="form-control">
<form:option value=""><spring:theme code="pointsForEquipmentPage.select" /></form:option>
 <form:options items="${states}"></form:options>
</form:select>
<br/>
<form:label path="customerZipCode"><spring:theme code="pointsForEquipmentPage.zip.code" /></form:label>
<br/>
<form:input idKey="customerZipCode" path="customerZipCode" class="form-control"/>
<div class="cl"></div>
<span id="customerZipCodeError"></span>
</div>


<div class="form-group">
<form:label path="emailAddress"><spring:theme code="pointsForEquipmentPage.email" /></form:label>
<br/>
<form:input idKey="emailAddress" path="emailAddress" class="form-control"/>
<div class="cl"></div>
<span id="emailAddressError"></span>
</div>


<div class="form-group">
<form:label path="phoneNumber"><spring:theme code="pointsForEquipmentPage.ph.no" /></form:label>
<br/>
<form:input idKey="phoneNumber" path="phoneNumber" class="form-control"/>
<div class="cl"></div>
<span id="phoneNumberError"></span>
</div>

<div class="form-group">
<form:label path="faxNumber"><spring:theme code="pointsForEquipmentPage.fax.no" /></form:label>
<br/>
<form:input idKey="faxNumber" path="faxNumber" class="form-control"/>
<div class="cl"></div>
<span id="faxNumberError"></span>
</div>


<br/><br/>
<p class="store-specialty-heading"><b><spring:theme code="pointsForEquipmentPage.product.info" /></b></p>

<div id="product_info_1" class="product_info">
<div class="form-group">
<br/>
<form:label path="dateOfPurProduct1"><spring:theme code="pointsForEquipmentPage.date.of.purchase" /></form:label>
<br/>
<form:input idKey="dateOfPurProduct1" path="dateOfPurProduct1" class="dateOfPurProduct form-control"/>
<div class="cl"></div>
<span id="dateOfPurProductError1"></span>
</div>
<div class="form-group">
<form:label path="itemDescProduct1"><spring:theme code="pointsForEquipmentPage.product.desc" /></form:label>
<br/>
<form:input idKey="itemDescProduct1" path="itemDescProduct1" class="itemDescProduct form-control"/>
<div class="cl"></div>
<span id="itemDescProductError1"></span>
</div>


<div class="form-group">
<form:label path="serialNumberProduct1"><spring:theme code="pointsForEquipmentPage.serial.no" /></form:label>
<br/>
<form:input idKey="serialNumberProduct1" path="serialNumberProduct1" class="serialNumberProduct form-control"/>
<div class="cl"></div>
<span id="serialNumberProductError1"></span>
</div>



<div class="form-group">
<form:label path="invoiceCostProduct1"><spring:theme code="pointsForEquipmentPage.invoice.cost" /></form:label>
<br/>
<form:input idKey="invoiceCostProduct1" path="invoiceCostProduct1" class="invoiceCostProduct form-control"/>
<div class="cl"></div>
<span id="invoiceCostProductError1"></span>
</div>
</div>

<div id="product_info_2" class="product_info hidden">
<hr/>
<div class="form-group">
<form:label path="dateOfPurProduct2"><spring:theme code="pointsForEquipmentPage.date.of.purchase" /></form:label>
<br/>
<form:input idKey="dateOfPurProduct2" path="dateOfPurProduct2" class="dateOfPurProduct form-control"/>
<div class="cl"></div>
<span id="dateOfPurProductError2"></span>
</div>
<div class="form-group">
<form:label path="itemDescProduct2"><spring:theme code="pointsForEquipmentPage.product.desc" /></form:label>
<br/>
<form:input idKey="itemDescProduct2" path="itemDescProduct2" class="itemDescProduct form-control"/>
<div class="cl"></div>
<span id="itemDescProductError2"></span>
</div>

<div class="form-group">
<form:label path="serialNumberProduct2"><spring:theme code="pointsForEquipmentPage.serial.no" /></form:label>
<br/>
<form:input idKey="serialNumberProduct2" path="serialNumberProduct2" class="serialNumberProduct form-control" />
<div class="cl"></div>
<span id="serialNumberProductError2"></span>
</div>

<div class="form-group">
<form:label path="invoiceCostProduct2"><spring:theme code="pointsForEquipmentPage.invoice.cost" /></form:label>
<br/>
<form:input idKey="invoiceCostProduct2" labelKey="Invoice Cost" path="invoiceCostProduct2" class="invoiceCostProduct form-control"/>
<div class="cl"></div>
<span id="invoiceCostProductError2"></span>
</div>
</div>

<div id="product_info_3" class="product_info hidden">
<hr/>
<div class="form-group">
<form:label path="dateOfPurProduct3"><spring:theme code="pointsForEquipmentPage.date.of.purchase" /></form:label>
<br/>
<form:input idKey="dateOfPurProduct3" path="dateOfPurProduct3" class="dateOfPurProduct form-control"/>
<div class="cl"></div>
<span id="dateOfPurProductError3"></span>
</div>

<div class="form-group">
<form:label path="itemDescProduct3"><spring:theme code="pointsForEquipmentPage.product.desc" /></form:label>
<br/>
<form:input idKey="itemDescProduct3" path="itemDescProduct3" class="itemDescProduct form-control"/>
<div class="cl"></div>
<span id="itemDescProductError3"></span>
</div>



<div class="form-group">
<form:label path="serialNumberProduct3"><spring:theme code="pointsForEquipmentPage.serial.no" /></form:label>
<br/>
<form:input idKey="serialNumberProduct3" path="serialNumberProduct3" class="serialNumberProduct form-control"/>
<div class="cl"></div>
<span id="serialNumberProductError3"></span>
</div>


<div class="form-group">
<form:label path="invoiceCostProduct3"><spring:theme code="pointsForEquipmentPage.invoice.cost" /></form:label>
<br/>
<form:input idKey="invoiceCostProduct3" labelKey="Invoice Cost" path="invoiceCostProduct3" class="invoiceCostProduct form-control"/>
<div class="cl"></div>
<span id="invoiceCostProductError3"></span>
</div>
</div>

<div id="product_info_4" class="product_info hidden">
<hr/>
<form:label path="dateOfPurProduct4"><spring:theme code="pointsForEquipmentPage.date.of.purchase" /></form:label>
 <div class="form-group">
<form:input idKey="dateOfPurProduct4" path="dateOfPurProduct4" class="dateOfPurProduct form-control"/>
<div class="cl"></div>
<span id="dateOfPurProductError4"></span>
</div>

<div class="form-group">
<form:label path="itemDescProduct4"><spring:theme code="pointsForEquipmentPage.product.desc" /></form:label>
<br/>
<form:input idKey="itemDescProduct4" path="itemDescProduct4" class="itemDescProduct form-control" />
<div class="cl"></div>
<span id="itemDescProductError4"></span>
</div>


<div class="form-group">
<form:label path="serialNumberProduct4"><spring:theme code="pointsForEquipmentPage.serial.no" /></form:label>
<br/>
<form:input idKey="serialNumberProduct4" path="serialNumberProduct4" class="serialNumberProduct"/>
<div class="cl"></div>
<span id="serialNumberProductError4"></span>
</div>

<div class="form-group">
<form:label path="invoiceCostProduct4"><spring:theme code="pointsForEquipmentPage.invoice.cost" /></form:label>
<br/>
<form:input idKey="invoiceCostProduct4" labelKey="Invoice Cost" path="invoiceCostProduct4" class="invoiceCostProduct form-control"/>
<br/>
<span id="invoiceCostProductError4"></span>
</div>
</div>


<div id="product_info_5" class="product_info hidden">
<hr/>
<div class="form-group">
<form:label path="dateOfPurProduct5"><spring:theme code="pointsForEquipmentPage.date.of.purchase" /></form:label>
<br/>
<form:input idKey="dateOfPurProduct5" path="dateOfPurProduct5" class="dateOfPurProduct form-control"/>
<div class="cl"></div>
<span id="dateOfPurProductError5"></span>
</div>


<div class="form-group">
<form:label path="itemDescProduct5"><spring:theme code="pointsForEquipmentPage.product.desc" /></form:label>
<br/>
<form:input idKey="itemDescProduct5" path="itemDescProduct5" class="itemDescProduct form-control" />
<div class="cl"></div>
<span id="itemDescProductError5"></span>
</div>


<div class="form-group">
<form:label path="serialNumberProduct5"><spring:theme code="pointsForEquipmentPage.serial.no" /></form:label>
<br/>
<form:input idKey="serialNumberProduct5" path="serialNumberProduct5" class="serialNumberProduct form-control"/>
<div class="cl"></div>
<span id="serialNumberProductError5"></span>
</div>

<div class="form-group">
<form:label path="invoiceCostProduct5"><spring:theme code="pointsForEquipmentPage.invoice.cost" /></form:label>
<br/>
<form:input idKey="invoiceCostProduct5" path="invoiceCostProduct5" class="invoiceCostProduct form-control"/>
<div class="cl"></div>
<span id="invoiceCostProductError5"></span>
</div>

</div>
<div class="cl"></div>
<a href="#" class="product_view"><spring:theme code="pointsForEquipmentPage.add.another.product" />&#8594;</a>
<br/><br/>
<div class="cl"></div>
<div id="recaptcha-border" class="recaptcha-error-border">
        <div id="grecaptcha" class="g-recaptcha" data-callback="captcha_onclick" data-sitekey="${recaptchaPublicKey}"></div>
	     <input type="hidden" name="recaptcha" id="recaptchaValidator" />
	     <input type="hidden" id="recaptchaChallengeAnswered" value="${recaptchaChallengeAnswered}" />
	   
	     <div id="recaptcha-error" class="hidden" style="color: rgb(254, 3, 3); font-weight: normal;">
	      <spring:theme code="recaptcha.error"/> 
	     </div>
	      </div>

<div class="margin20">
<div class="col-md-6 col-sm-6 xol-xs-12 row">
<button type="button" class="btn btn-primary btn-block pointsForEquipment">
						<spring:theme code="pointsForEquipmentPage.submit" />
					</button>
					</div>
					</div>
</form:form>

</div>
<div class="cl"></div>
<br/><br/><br/>
<div class="col-md-6 col-sm-6 col-xs-12  row">
<p class="textPartnerForm"><spring:theme code="text.partner.info"/></p>
</div>
<div class="cl"></div>
</template:page>