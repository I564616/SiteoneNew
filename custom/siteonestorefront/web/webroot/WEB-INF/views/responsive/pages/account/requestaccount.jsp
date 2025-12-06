<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%> 
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<c:url value="/request-account/form" var="requestForm" />
<input type="hidden" class="currentBaseStoreId" value="${currentBaseStoreId eq 'siteoneCA' ? 'CA' : 'US'}"/>
<c:url var="homePage" value="/" />
<template:page pageTitle="${pageTitle}" hideHeaderLinks="true">
<script src="https://www.google.com/recaptcha/api.js"></script>
<script type="text/javascript">
	$(function () {
		var UUID = uuidv4();
		$(".application-uuid").val(UUID);
	});
	var strings = new Array();
	strings['js.request.contryearsInBusiness.empty'] = "<spring:message code='js.request.contryearsInBusiness.empty' javaScriptEscape='true' />";
	strings['js.request.contryearsInBusiness.invalid'] = "<spring:message code='js.request.contryearsInBusiness.invalid' javaScriptEscape='true' />";
	strings['js.request.contrempcount.empty'] = "<spring:message code='js.request.contrempcount.empty' javaScriptEscape='true' />";
	strings['js.request.primarybusiness.empty'] = "<spring:message code='js.request.primarybusiness.empty' javaScriptEscape='true' />";
	strings['js.request.landscapingindustry.empty'] = "<spring:message code='js.request.landscapingindustry.empty' javaScriptEscape='true' />";
</script>
<!-- Banner -->
<div class="row flex-center banner-block">
	<div class="col-lg-offset-1 col-lg-2 col-md-offset-1 col-md-3 col-xs-8 font-Geogrotesque f-s-26 banner-text-heading">
		<span class="contratorSec">
			<spring:theme code="requestaccount.create.contractor.account" />
		</span>
		<span class="homeownerSec">
			<spring:theme code="requestaccount.create.siteone.account" />
		</span>
	</div>
	<div class="col-lg-offset-1 col-lg-4 col-md-offset-0 col-md-5 col-xs-offset-2 col-xs-8 margin-bot-10-xs text-center hidden banner-icon-wrapper">
		<div class="row flex-center">
			<div class="col-md-4 icons-holder">
				<span class="business-fill-icon">
					<common:bannerIcon width="40" height="40" value="1" color="#ffffff" textcolor="#78a22f"></common:bannerIcon>
				</span>
				<span class="business-success-icon hidden">
					<common:checkmark-bg-white></common:checkmark-bg-white>
				</span>
				<span class="horizontal-line" style="width: calc(100% - 40px);"></span>
			</div>
			<div class="col-md-4 icons-holder">
				<span class="branch-icon">
					<common:bannerIcon width="40" height="40" value="2" color="transparent" textcolor="#ffffff"></common:bannerIcon>
				</span>
				<span class="branch-fill-icon hidden">
					<common:bannerIcon width="40" height="40" value="2" color="#ffffff" textcolor="#78a22f"></common:bannerIcon>
				</span>
				<span class="branch-success-icon hidden">
					<common:checkmark-bg-white></common:checkmark-bg-white>
				</span>
				<span class="horizontal-line" style="width: calc(100% - 40px);"></span>
			</div>
			<div class="col-md-4">
				<span class="confirmation-icon">
					<common:bannerIcon width="40" height="40" value="3" color="transparent" textcolor="#ffffff"></common:bannerIcon>
				</span>
				<span class="confirmation-fill-icon hidden">
					<common:bannerIcon width="40" height="40" value="3" color="#ffffff" textcolor="#78a22f"></common:bannerIcon>
				</span>
			</div>
		</div>
		<div class="row flex-center hidden-xs">
			<div class="col-md-4 contratorSec">
				<spring:theme code="requestaccount.buisness.information.icon" />
			</div>
			<div class="col-md-4 homeownerSec">
				<spring:theme code="requestaccount.personal.information.icon" />
			</div>
			<div class="col-md-4">
				<spring:theme code="requestaccount.homebranch" />
			</div>
			<div class="col-md-4">
				<spring:theme code="requestaccount.confirmation.icon" />
			</div>
		</div>
	</div>
</div>
<!-- ./Banner -->

<!-- Heading -->
<div class="row font-Geogrotesque f-s-32 marginTopBVottom30 text-default text-center business-details-headline ">
	<div class="col-md-4 col-md-offset-4 col-xs-12 col-sm-12 businessDetails">
		<spring:theme code="requestaccount.contractor.headline.message" />
	</div>
	<div class="col-md-4 col-md-offset-4 col-xs-12 col-sm-12 retailDetails">
		<spring:theme code="requestaccount.homeowner.headline.message" />
	</div>
	<div class="col-md-4 col-md-offset-4 col-xs-12 col-sm-12 hidden branchDetails">
		<spring:theme code="requestaccount.select.your.branch" />
	</div>
</div>
<!-- ./Heading -->

<!-- Customer Form -->
<form:form id="siteOneRequestAccountForm" action="${requestForm}" method="POST" modelAttribute="siteOneRequestAccountForm">
	<input type="hidden" class="application-uuid" name="uuid" value="" />
	<input type="hidden" id="storeid" name="storeNumber" value="" />
	<input type="hidden" name="ueType" value="RealTime Bot" />
	<!-- Account Select -->
	<div class="row" id="accountSelect">
		<div class="col-md-offset-0 col-md-12 col-sm-offset-1 col-sm-10 col-xs-offset-2 col-xs-8 text-center marginBottom40">
			<h1 class="font-Geogrotesque text-default f-s-40 margin0 m-b-15 business-details-headline">
				<spring:theme code="requestaccount.create.siteone.account" />
			</h1>
			<p class="text-muted bold-text f-s-18 margin0 reqacc-option-message">
				<spring:theme code="requestaccount.create.siteone.account.message1" />
			</p>
		</div>
		<div class="col-md-3 col-md-offset-3 col-sm-5 col-sm-offset-1 col-xs-12 col-xs-offset-0">
			<label for="typeOfCustomer1" id="chk-btn" class="flex-center bg-white font-Geogrotesque text-center m-b-15 transition-3s chk-btn" onclick="ACC.accountdashboard.selfServeEventAnalytics('', 'Contractor', 'my account : online access', 'linkClicks')">
				<div class="hidden-xs hidden-sm m-b-15">
					<common:contractorIcon width="107" height="107"></common:contractorIcon>
				</div>
				<div class="hidden-lg hidden-md m-r-15">
					<common:contractorIcon width="75" height="75"></common:contractorIcon>
				</div>
				<p class="margin0 f-s-26 business-details-headline">
					<spring:theme code="requestaccount.contractor" />
				</p>
			</label>
			<form:radiobutton name="typeOfCustomer" path="typeOfCustomer" value="Contractor" idKey="typeOfCustomer1" class="hide-input" />
		</div>
		<div class="col-md-3 col-sm-5 col-xs-12">
			<label for="typeOfCustomer2" id="chk-btn" class="flex-center bg-white font-Geogrotesque text-center transition-3s chk-btn" onclick="ACC.accountdashboard.selfServeEventAnalytics('', 'Homeowner/Retail', 'my account : online access', 'linkClicks')">
				<div class="hidden-xs hidden-sm m-b-15">
					<common:homeownerIcon width="107" height="107"></common:homeownerIcon>
				</div>
				<div class="hidden-lg hidden-md m-r-15">
					<common:homeownerIcon width="75" height="75"></common:homeownerIcon>
				</div>
				<p class="margin0 f-s-26 business-details-headline">
					<spring:theme code="requestaccount.ownwer.customer" />
				</p>
			</label>
			<form:radiobutton name="typeOfCustomer" path="typeOfCustomer" value="Homeowner / Retail Customer" idKey="typeOfCustomer2" class="hide-input" />
		</div>
		<div class="col-md-12 col-xs-12 m-y-55 m-y-20-xs text-center">
			<p class="font-GeogrotesqueSemiBold f-s-26 f-s-18-xs-px text-default"><spring:theme code="request.online.already" /></p>
			<p class="btn-link f-s-16 font-small-xs text-blue bold pointer" onclick="ACC.accountdashboard.selfServeEventAnalytics('', 'Request Online Access for Existing Account', 'my account : online access', 'linkClicks'); window.location.href='${homePage}request-account/onlineAccess'"><spring:theme code="request.online.existing" /></p>
		</div>
	</div>
	<!-- ./Account Select -->
	
	<!-- Request Account -->
	<div class="row marginTop40 requestaccount-form RTA_wrapper">
		<div class="col-md-8 col-md-offset-2 col-xs-12 col-sm-12 inner-container">
			<div class="row m-b-15 contratorSec">
				<div class="col-md-12 col-xs-12 col-sm-12">
					<div class="bd-heading-container">
						<common:leaf_icon></common:leaf_icon>
						<span class="bd-heading">
							<spring:theme code="requestaccount.businesssdetails" />
						</span>
					</div>
				</div>
			</div>
			<div class="row m-b-15 homeownerSec">
				<div class="col-md-12 col-xs-12 col-sm-12">
					<div class="bd-heading-container">
						<common:personal_details_icon></common:personal_details_icon>
						<span class="bd-heading">
							<spring:theme code="requestaccount.personaldetails" />
						</span>
					</div>
				</div>
			</div>
			<!-- C-Company Name, C-Phone or  H-First Name and H-LastName -->
			<div class="row">
				<!-- company name or firstName -->
				<div class="col-md-6 col-xs-12">
					<div class="contratorSec">
						<formElement:formInputBox idKey="companyName" labelKey="requestaccount.company.name" path="companyName" mandatory="true" inputCSS="details-input-field" />
						<div><span id="errorCompanyName"></span></div>
						
						<div class="visible-md visible-lg"></div>
					</div>
					<div class="homeownerSec">
						<formElement:formInputBox disabled="false" idKey="" labelKey="requestaccount.first.name" path="firstName" mandatory="true" inputCSS="details-input-field firstName" />
						<div><span class="errorFirstName"></span></div>
						
						<div class="visible-md visible-lg"></div>
					</div>
				</div>
				<!-- company phone or lastname -->
				<div class="col-md-6 col-xs-12">
					<div class="homeownerSec">
						<formElement:formInputBox disabled="false" idKey="" labelKey="requestaccount.last.name" path="lastName" mandatory="true" inputCSS="details-input-field lastName" />
						<div><span class="errorLastName"></span></div>
						
						<div class="visible-md visible-lg"></div>
					</div>
					<div class="contratorSec">
						<formElement:formInputBox disabled="false" idKey="" labelKey="requestaccount.company.phone" path="phoneNumber" mandatory="true" inputCSS="details-input-field companyphone" />
						<div><span class="errorPhoneNumber"></span></div>
						
						<div class="visible-md visible-lg"></div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6 col-xs-12 homeownerSec">
					<formElement:formInputBox disabled="false" idKey="phoneNumber" labelKey="requestaccount.phone" path="phoneNumber" mandatory="true" inputCSS="details-input-field homephone" />
					<div><span id="errorPhoneNumber"></span></div>
					
					<div class="visible-md visible-lg"></div>
				</div>
				<div class="col-md-6 col-xs-12">
					<div class="homeownerSec">
						<formElement:formInputBox disabled="false" idKey="" labelKey="requestaccount.email" path="emailAddress" mandatory="true" inputCSS="details-input-field emailaddress" autocomplete="off" />
						<div><span class="errorEmailAddress"></span></div>
						
						<div class="visible-md visible-lg"></div>
					</div>
				</div>
			</div>
			<!-- Address input box -->
			<div class="row">
				<div class="col-md-6 col-xs-12">
					<div class="homeownerSec">
						<label for="addressLine1">
							<spring:theme code="requestaccount.address.line1" />
						</label>
					</div>
					<div class="contratorSec">
						<label for="addressLine1">
							<spring:theme code="requestaccount.company.address.line1" />
						</label>
					</div>
					<formElement:formInputBox idKey="addressLine1" labelKey="" path="addressLine1" mandatory="true" inputCSS="details-input-field" />
					<div><span id="errorAddressLine1"></span></div>
					<div class="visible-md visible-lg"></div>
					
				</div>
				<div class="col-md-6 col-xs-12">
					<div class="homeownerSec">
						<label for="addressLine2">
							<spring:theme code="requestaccount.address.line2" />
						</label>
					</div>
					<div class="contratorSec">
						<label for="addressLine2">
							<spring:theme code="requestaccount.company.address.line2" />
						</label>
					</div>
					<formElement:formInputBox idKey="addressLine2" labelKey="" path="addressLine2" mandatory="true" inputCSS="details-input-field" />
					<div class="visible-md visible-lg"></div>
					
				</div>
			</div>
			<!-- city state zipcode -->
			<div class="row">
				<div class="col-md-6 col-xs-12 col-sm-12">
					<div class="homeownerSec">
						<label for="city">
							<spring:theme code="requestaccount.city" />
						</label>
					</div>
					<div class="contratorSec">
						<label for="city">
							<spring:theme code="requestaccount.company.city" />
						</label>
					</div>
					<formElement:formInputBox idKey="city" labelKey="" path="city" mandatory="true" inputCSS="details-input-field" />
					<div><span id="errorCity"></span></div>
					<div class="visible-md visible-lg"></div>
					
				</div>
				<div class="col-md-3 col-xs-6 col-sm-6">
					<div class="homeownerSec state-align">
						<label for="state">
							<spring:theme code="${currentBaseStoreId eq 'siteone' ? 'requestaccount.state' : 'homeOwnerComponent.province' }" />
						</label>
					</div>
					<div class="contratorSec state-align">
						<label for="state">
							<spring:theme code="${currentBaseStoreId eq 'siteone' ? 'requestaccount.company.state' : 'requestaccount.company.province' }" />
						</label>
					</div>
					<formElement:customSelectBox idKey="state" labelCSS="select-label" labelKey="" selectCSSClass="details-input-field reqacc-dropdown" path="state" mandatory="true" skipBlank="false" skipBlankMessageKey="request.form.select.empty" items="${states}" />
					<div><span id="errorState"></span></div>
					<div class="visible-md visible-lg"></div>
					
				</div>
				<div class="col-md-3 col-xs-6 col-sm-6 ${currentLanguage.isocode eq 'es' ? 'no-padding-rgt-md' : ''}">
					<div class="homeownerSec">
						<label for="zipcode">
							<spring:theme code="${currentBaseStoreId eq 'siteone' ? 'requestaccount.zipcode' : 'text.postcode.homeowner' }" />
						</label>
					</div>
					<div class="contratorSec">
						<label for="zipcode">
							<spring:theme code="${currentBaseStoreId eq 'siteone' ? 'requestaccount.company.zipcode' : 'requestaccount.company.postalcode' }" />
						</label>
					</div>
					<formElement:formInputBox idKey="zipcode" labelKey="" path="zipcode" mandatory="true" inputCSS="details-input-field" maxlength="10" />
					<div><span id="errorZipcode"></span></div>
					<div class="visible-md visible-lg"></div>
					
				</div>
			</div>
			<!-- homeowner languagePreference -->
			<div class="row homeownerSec marginBottom10">
				<div class="col-md-6 col-xs-12 col-sm-12">
					<label for="yourPreferredLanguage">
						<spring:theme code="requestaccount.preferredLang" />
					</label>
					<div class="row">
						<div class="col-md-6 col-xs-6">
							<div class="label-language">
								<span class="radio-button-circle">
									<form:radiobutton name="homelanguagePreference" path="languagePreference" value="English" id="homelanguagePreference1"/>
								</span>&nbsp;<label for="homelanguagePreference1">English</label>
							</div>
						</div>
						<div class="col-md-6 col-xs-6">
							<div class="label-language">
								<span class="radio-button-circle">
									<form:radiobutton name="homelanguagePreference" path="languagePreference" value="Espanol" id="homelanguagePreference2" />
								</span> &nbsp;<label for="homelanguagePreference2">Espa&#241;ol</label>
							</div>
						</div>
						
					</div>
					
					<div class="visible-md visible-lg"></div>
				</div>
			</div>
			<!-- Primary business -->
			<div class="row contratorSec">
				<div class="form-group ">
					
					<div class="business-dropdown">
						<div class="col-md-6 col-xs-12 col-sm-12">
							<spring:message code="requestaccount.select" var="businessSelect" />
							<div class="business-dropdown-primary">
								<label>
									<spring:theme code="requestaccount.primary" />
								</label>
							</div>
						</div>
						<div class="col-md-6 col-xs-12 col-sm-12">
							<label>
								<spring:theme code="requestaccount.servicetypes" />
							</label>
							<form:select id="contrPrimaryBusiness" path="contrPrimaryBusiness" cssClass="form-control customSelect">
								<optgroup label="${businessSelect}">
								</optgroup>
								<c:forEach var="associatedMap" items="${primaryBusinessList}">
									<c:set var="associatedVal" value="${associatedMap.key}" />
									<c:set var="primaryBusiness" value="${fn:split(associatedVal, '|')}" />
									<optgroup value="${primaryBusiness[0]}" label="${primaryBusiness[1]}">
										<c:choose>
											<c:when test="${fn:length(associatedMap.value)==0}">
												<form:option value="${primaryBusiness[0]}|${primaryBusiness[1]}">
													<c:out value="${primaryBusiness[1]}" />
												</form:option>
											</c:when>
											<c:otherwise>
												<form:option value="Select">
													<spring:theme code="requestaccount.select" />
												</form:option>
												<c:forEach var="associatedItem" items="${associatedMap.value}">
													<c:if test="${associatedItem.code eq associatedItem.description}">
														<form:option value='${primaryBusiness[0]}/${associatedItem.code}|${primaryBusiness[1]}/${associatedItem.description}'>
															<c:out value="${associatedItem.description}" />
														</form:option>
													</c:if>
													<c:if test="${associatedItem.code ne null}">
														<form:option value='${primaryBusiness[0]}/${associatedItem.code}|${primaryBusiness[1]}/${associatedItem.description}'>
															<c:out value="${associatedItem.description}" />
														</form:option>
													</c:if>
												</c:forEach>
											</c:otherwise>
										</c:choose>
									</optgroup>
								</c:forEach>
							</form:select>
							<div><span id="errorContrPrimaryBusiness"></span></div>
						</div>
					</div>
					
				</div>
				<div class="visible-md visible-lg"></div>
				
			</div>
			<!-- years and employees -->
			<div class="row contratorSec marginTop10">
				<div class="col-md-3 col-xs-6 col-sm-6">
					<div class="businessyears">
						<formElement:formInputBox idKey="contrYearsInBusiness" labelKey="requestaccount.contrYearsInBusiness" path="contrYearsInBusiness" mandatory="true" inputCSS="details-input-field" maxlength="6" autocomplete="off" />
						
						<span id="errorContrYearsInBusiness"></span>
					</div>
				</div>
				<div class="col-md-3 col-xs-6 col-sm-6">
					<formElement:formInputBox idKey="contrEmpCount" labelKey="requestaccount.no.of.employees" path="contrEmpCount" mandatory="true" inputCSS="details-input-field" maxlength="5" autocomplete="off" />
					<span id="errorContrEmpCount"></span>
				</div>
				<!-- Landscaping Industry -->
				<div class="contratorSec">
				<div class="business-dropdown">
				<div class="col-md-6 col-xs-12 col-sm-12">
							<label>
								<spring:theme code="requestaccount.landscaping" />
							</label>
							<select id="landscapingIndustry" name="landscapingIndustry">
							<option value="select">Select</option>
							<option value="true">Yes</option>
							<option value="false">No</option>
							</select>
							<div><span id="errorlandscapingIndustry"></span></div>
						</div>
						</div>
						</div>
			</div>
			<!-- Account Number section -->
			<div class="row marginTopBVottom20 hidden">
				<div class="col-md-12 col-xs-12 col-sm-12">
					<div class="bd-heading-container">
						<common:account_number_icon></common:account_number_icon>
						<span class="bd-heading">
							<spring:theme code="requestaccount.account.number.heading" />
						</span>
					</div>
				</div>
			</div>
			<div class="visible-md visible-lg"></div>
			<div class="row hidden">
				<div class="col-md-6">
					<div class="form-group">
						<span class="radio-button-circle">
							<form:radiobutton name="hasAccountNumber" path="hasAccountNumber" value="no" checked="checked" />
						</span>
						<label for="hasAccountNumber2">
							<spring:theme code="requestaccount.no.acc.no" />
						</label>
						<div class="visible-md visible-lg"></div>
						
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<span class="radio-button-circle">
							<form:radiobutton name="hasAccountNumber" path="hasAccountNumber" value="yes" />
						</span>
						<span><label for="hasAccountNumber1"><spring:theme code="requestaccount.acc.no" /></label></span>
						<div class="visible-md visible-lg"></div>
						
					</div>
				</div>
				<div class="pull-left col-md-12 col-sm-11 col-xs-12">
					<span class="questionCircle_icon" onclick="ACC.accountdashboard.accountNumberHelp()">
						<svg xmlns="http://www.w3.org/2000/svg" width="15.039" height="15.039" viewBox="0 0 15.039 15.039">
  							<path id="question-circle" d="M23.039,15.52A7.52,7.52,0,1,1,15.52,8,7.519,7.519,0,0,1,23.039,15.52Zm-7.318-5.033a3.927,3.927,0,0,0-3.534,1.933.364.364,0,0,0,.082.493l1.052.8a.364.364,0,0,0,.505-.064c.542-.687.913-1.085,1.737-1.085.619,0,1.386.4,1.386,1,0,.454-.375.687-.986,1.03-.713.4-1.657.9-1.657,2.142v.121a.364.364,0,0,0,.364.364h1.7a.364.364,0,0,0,.364-.364v-.04c0-.863,2.522-.9,2.522-3.234C19.255,11.82,17.43,10.486,15.721,10.486Zm-.2,7.52A1.395,1.395,0,1,0,16.914,19.4,1.4,1.4,0,0,0,15.52,18.006Z" transform="translate(-8 -8)" fill="#50A0C5"></path>
						</svg>
					</span>
					<formElement:formInputBox idKey="accountNumber" labelKey="requestaccount.account.number" path="accountNumber" mandatory="true" inputCSS="details-input-field" />
					<div><span id="errorAccountNumber"></span></div>
					<div id="info" class="account-req" style="display:none;width: 100%;"><img width="100%" height="100%" src="/_ui/responsive/theme-lambda/images/tool-tip-image.png" alt="store-speciality-filter"/>
					</div>
				</div>
			</div>
			
			<div class="row contratorSec">
				<div class="col-md-12 col-xs-12 col-sm-12">
					<div class="bd-heading-container">
						<common:personal_details_icon></common:personal_details_icon>
						<span class="bd-heading"><spring:theme code="requestaccount.personaldetails" /></span>
					</div>
				</div>
			</div>
			<div class="contratorSec"></div>
			<!-- C-first name and c-lastname -->
			<div class="contratorSec">
				<div class="row">
					<div class="col-md-6">
						<formElement:formInputBox disabled="false" idKey="firstName" labelKey="requestaccount.first.name" path="firstName" mandatory="true" inputCSS="details-input-field" />
						<div><span id="errorFirstName"></span></div>
						<div class="visible-md visible-lg"></div>
						
					</div>
					<div class="col-md-6">
						<formElement:formInputBox disabled="false" idKey="lastName" labelKey="requestaccount.last.name" path="lastName" mandatory="true" inputCSS="details-input-field" />
						<div><span id="errorLastName"></span></div>
					</div>
				</div>
			</div>
			<div class="contratorSec bd-heading-container">
				
				<span class="radio-button-circle">
					<form:radiobutton name="isAccountOwner" path="isAccountOwner" value="no" style="margin-top: 0px;" />
				</span>
				<label class="accnt-text-owner"><spring:theme code="requestaccount.no.acc.owner" /></label>
			</div>
			
			<div class="contratorSec"></div>
			<!-- C-email and languages -->
			<div class="row contratorSec">
				<div class="col-md-6 col-xs-12 col-sm-12">
					<formElement:formInputBox disabled="false" idKey="emailaddress" labelKey="requestaccount.email" path="emailAddress" mandatory="true" inputCSS="details-input-field" autocomplete="off" />
					<div><span id="errorEmailAddress"></span></div>
					
					<div class="visible-md visible-lg"></div>
				</div>
				<div class="col-md-6 col-xs-12 col-sm-12">
					<label for="yourPreferredLanguage"><spring:theme code="requestaccount.preferredLang" /></label>
					<div class="row">
						<div class="col-md-6 col-xs-6">
							<div class="label-language">
								<span class="radio-button-circle">
									<form:radiobutton name="languagePreference" path="languagePreference" value="English" id="languagePreference1" />
								</span>&nbsp;<label for="languagePreference1">English</label>
							</div>
						</div>
						<div class="col-md-6 col-xs-6">
							<div class="label-language">
								<span class="radio-button-circle">
									<form:radiobutton name="languagePreference" path="languagePreference" value="Espanol" id="languagePreference2" />
								</span> &nbsp;<label for="languagePreference2">Espa&#241;ol</label>
							</div>
						</div>
						
					</div>
					
					<div class="contratorSec"></div>
				</div>
				<div class="col-md-12 col-xs-12 col-sm-12 pad-top14">
					<div class="form-group">
						 <span class="colored"> 
            				<input type="checkbox" class="enroll-partners-program-checkbox" data-toggle="switch" checked="checked" value="true" name="enrollInPartnersProgram"/>
            			</span>
            			<span class="font-Geogrotesque-bold text-default f-s-12">
	            			<spring:theme code="requestaccount.enroll.text1" /> <a href="<c:url value="/Partners"/>" target="_blank" class="enroll-partners-program-link"><spring:theme code="requestaccount.enroll.text2" /></a> <spring:theme code="requestaccount.enroll.text3" />
            			</span>
            			<span class="font-Geogrotesque f-s-12 text-default underline-text">
	            			<spring:theme code="requestaccount.enroll.text4" />
            			</span>
      				</div>
      			</div>
			</div>
			<div class="col-md-12 col-xs-12 col-sm-12 term-text-margin">
				<span class="font-Geogrotesque f-s-10 text-default term-text-font-weight">
					<a href="${homePage}salesterms"><spring:theme code="text.account.order.termsofsale"/></a>
					<spring:theme code="text.account.order.includeimportantlegal"/>
					<spring:theme code="text.account.order.our"/>&nbsp;
					<a href="${homePage}termsandconditions"><spring:theme code="text.account.order.websiteterms"/></a>
					<spring:theme code="text.account.order.setforth"/>&nbsp;
					<spring:theme code="text.account.order.our"/>&nbsp;
					<a href="${homePage}privacypolicy"><spring:theme code="text.account.order.privacypolicy"/></a>&nbsp;
					<spring:theme code="text.account.order.includesimportantinformation"/>.
					<spring:theme code="text.account.order.bycompleting"/>&nbsp;
					<spring:theme code="text.account.order.termsofsale"/>,
					<spring:theme code="text.account.order.websiteterms"/>&nbsp;
					<spring:theme code="text.account.order.and"/>&nbsp;
					<spring:theme code="text.account.order.privacypolicy"/>.
				</span>
			</div>
			<div class="line-divider margin-top-90 hidden-sm hidden-xs"></div>
			<div class="row">
				<div class="col-md-8 col-md-offset-4 marginTop40">
					<div id="recaptcha-border" class="recaptcha-error-border">
						<div id="grecaptcha" class="g-recaptcha" data-callback="captcha_onclick" data-sitekey="${recaptchaPublicKey}"></div>
						<input type="hidden" name="recaptcha" id="recaptchaValidator" />
						<input type="hidden" id="recaptchaChallengeAnswered" value="${recaptchaChallengeAnswered}" />
						<div id="recaptcha-error" class="hidden" style="color: rgb(254, 3, 3); font-weight: normal;">
							<spring:theme code="recaptcha.error" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Request Account -->
	
	<!-- next and Submit Button -->
	<div class="row">
		<div class="hidden btn-footr">
			<div class="col-xs-12 hidden branch-selected-text">
				<p class="bold-text text-center text-green f-s-14"><span class="branch-result-branch"></span> <spring:theme code="requestaccount.selected.home.branch" /></p>
			</div>
			<div class="col-md-2 col-md-offset-4 col-sm-3 col-sm-offset-3 col-xs-6 no-padding-lft-xs next_goback_wrapper">
				<div class="btn btn-block bold-text btn-gray transition-3s gb-footer-btn">
					<spring:theme code="requestaccount.goback" />
				</div>
				<div class="btn btn-block btn-gray bold-text transition-3s branch-selection-back hidden" onclick="ACC.accountdashboard.backCreateCustomer()">
					<spring:theme code="requestaccount.goback" />
				</div>
			</div>
			<div class="col-md-2 col-sm-3 col-xs-6 no-padding-xs next_goback_wrapper">
				<button type="submit" id="realtime_submit" class="btn btn-primary btn-block bold-text transition-3s next-footer-btn">
					<spring:theme code="requestaccount.next" />
				</button>
				<div class="btn btn-block btn-primary bold-text transition-3s branch-selection-next hidden" onclick="ACC.accountdashboard.nextCreateCustomer(this)">
					<spring:theme code="requestaccount.next" />
				</div>
			</div>
			<div class="col-md-offset-4 col-md-4 col-xs-offset-1 col-xs-10">
				<!-- shop siteone.com	 -->
				<a href="${homePage}" class="btn btn-primary btn-block hidden shop_siteone_wrap"><spring:theme code="${currentBaseStoreId eq 'siteone' ? 'requestaccount.shop.siteone.com' : 'requestaccount.shop.siteone.ca' }" /></a>
				<!-- back to siteone.com -->
				<a href="${homePage}" class="btn btn-primary btn-block hidden back-to-siteone-wrap"><spring:theme code="requestaccount.back.to.siteone.com" /></a>
			</div>
		</div>
	</div>
</form:form>
<!-- ./Customer Form -->

<!-- Home Branch -->
<div class="row hidden branch-wrapper">
    <div class="col-md-8 col-md-offset-2 col-xs-12 col-sm-12s inner-container">
        <div class="row margin0 m-b-15">
        	<p class="text-default font-size-14 bold-text marginTopBVottom20 font-Geogrotesque hidden-xs"><spring:theme code="requestaccount.filter.title"/></p>
        	<p class="text-default font-size-14 bold-text marginTopBVottom20 font-Geogrotesque text-center visible-xs m-t-0"><spring:theme code="requestaccount.filter.title.mobile"/></p>
          <div class="col-md-2 col-xs-6 p-l-0">
                <div class="form-group">
                    <label class="font-size-14 font-Geogrotesque" for="branchzip"><spring:theme code="contactUsForm.zipcode"/> </label>
                    <input id="branch-finder-zip" name="branchzip" class="form-control form-control font-size-14 input-number" placeholder="ZIP" type="text" value="07004">
                </div>
            </div>
            <div class="col-md-4 col-xs-6 no-padding-xs">
                <div class="form-group">
                    <label class="font-size-14 font-Geogrotesque" for="branchmiles"><spring:theme code="storeDetails.table.distance" /></label>
                    <select id="branch-finder-miles" name="branchmiles" class="details-input-field reqacc-dropdown font-size-14" title="Search within">
                        <option value="100" selected><spring:theme code="tag.store.miles.100" /></option>
                        <option value="50"><spring:theme code="tag.store.miles.50"/></option>
                        <option value="20"><spring:theme code="tag.store.miles.20"/></option>
                    </select>
                </div>
            </div>
            <div class="col-md-4 col-xs-12 no-padding-xs margin-bot-10-xs">
                <label class="font-size-14 font-Geogrotesque hidden-xs"><spring:theme code="storeDetailsContent.branch.special" /></label>
                <div class="flex-center specialties-dropdown details-input-field reqacc-dropdown" onclick="ACC.accountdashboard.showSpecialtiesOption()">
                    <img src="/_ui/responsive/theme-lambda/images/branchspecialty_StoreFilter.png" alt="store-speciality-filter" class="pull-left" height="24" />
                    <div class="specialties-filter font-size-14 hidden-xs"><spring:theme code="search.nav.refine.button" /></div>
                    <div class="specialties-filter font-size-14 visible-xs"><spring:theme code="requestaccount.filter.text.mobile" /></div>
                </div>
                <div class="specialties-option" style="width:calc(100% - 30px);">
                    <label for="Agronomics" class="specialty-name flex-center">
                        <span>
							<img  alt="Agronomics" title="<spring:theme code="storeDetailsContent.agronomics"/>" src="/_ui/responsive/theme-lambda/images/branchspecialty_Agronomics.png"/>
							<spring:theme code="storeDetailsContent.agronomics" />
						</span>
                        <span class="colored"><input id="Agronomics" class="form-control choose-specialty" type="checkbox" /></span>
                    </label>
                    <label for="Delivery" class="specialty-name flex-center">
                        <span>
							<img alt="Delivery" title="<spring:theme code="storeDetailsContent.delivery"/>" src="/_ui/responsive/theme-lambda/images/branchspecialty_Delivery.png"/>
							<spring:theme code="storeDetailsContent.delivery" />
						</span>
                        <span class="colored"><input id="Delivery" class="form-control choose-specialty" type="checkbox" /></span>
                    </label>
                    <label for="Hardscapes" class="specialty-name flex-center">
                        <span>
							<img alt="Hardscapes" title="<spring:theme code="storeDetailsContent.hardscape.tier"/>" src="/_ui/responsive/theme-lambda/images/branchspecialty_Hardscapes.png"/>
							<spring:theme code="storeDetailsContent.hardscape.tier" />
						</span>
                        <span class="colored"><input id="Hardscapes" class="form-control choose-specialty" type="checkbox" /></span>
                    </label>
                    <label for="Irrigation" class="specialty-name flex-center">
                        <span>
							<img alt="Irrigation" title="<spring:theme code="storeDetailsContent.irrigation"/>" src="/_ui/responsive/theme-lambda/images/branchspecialty_Irrigation.png"/>
							<spring:theme code="storeDetailsContent.irrigation" />
						</span>
                        <span class="colored"><input id="Irrigation" class="form-control choose-specialty" type="checkbox" /></span>
                    </label>
                    <label for="Pesticide" class="specialty-name flex-center">
                        <span>
							<img alt="Pesticide" title="<spring:theme code="storeDetailsContent.pestmanagement"/>" src="/_ui/responsive/theme-lambda/images/branchspecialty_Pesticide.png"/>
							<spring:theme code="storeDetailsContent.pestmanagement" />
						</span>
                        <span class="colored"><input id="Pesticide" class="form-control choose-specialty" type="checkbox" /></span>
                    </label>
                    <label for="Nursery" class="specialty-name flex-center">
                        <span>
							<img alt="Nursery" title="<spring:theme code="storeDetailsContent.nursery"/>" src="/_ui/responsive/theme-lambda/images/branchspecialty_Nursery.png"/>
							<spring:theme code="storeDetailsContent.nursery" />
						</span>
                        <span class="colored"><input id="Nursery" class="form-control choose-specialty" type="checkbox" /></span>
                    </label>
                    <label for="Lighting" class="specialty-name flex-center">
                        <span>
							<img  alt="Lighting" title="<spring:theme code="storeDetailsContent.outdoor"/>" src="/_ui/responsive/theme-lambda/images/branchspecialty_Lighting.png"/>
							<spring:theme code="storeDetailsContent.outdoor" />
						</span>
                        <span class="colored"><input id="Lighting" class="form-control choose-specialty" type="checkbox" /></span>
                    </label>
                    <div class="cl"></div>
                </div>
            </div>
            <div class="col-md-2 col-xs-12 p-r-0 no-padding-xs">
            	<div class="form-group no-margin-xs">
                    <label class="text-white hidden-xs">.</label>
                    <button class="btn btn-block btn-primary" onclick="ACC.accountdashboard.updateStores(this)"><spring:theme code="estimate.update" /></button>
                </div>
            </div>
			<div class="cl"></div>
			<div class="col-md-12 p-l-0">
                <span id="errorInZipcode"></span>
            </div>
            <div class="col-md-12 padding0">
                <hr/>
            </div>
			<div class="col-md-12 padding0">
                <div class="row specialties"></div>
            </div>
			<div class="cl"></div>
            <div class="col-md-12 xs-center m-t-20-xs m-b-5 font-Geogrotesque font-size-14 text-default padding0 marginTop10">
            	<p class="bold-text m-b-0 hidden branch-result-found"><span class="branch-result-number marginrgt5">XX</span> <spring:theme code="requestaccount.branches.meet.home.branch"/></p>
            	<p class="m-b-0 flex-center flex-dir-column-xs margin-bot-10-xs hidden branch-result-not-found">
            		<span class="m-r-15 margin-bot-10-xs"><common:exclamatoryIcon iconColor="#ed8606" width="34" height="34" /></span>
            		<span class=""><span class="bold-text text-orange marginrgt5"><spring:theme code="request.nobranches.within" /><span class="branch-result-miles p-l-5">xxx</span> <spring:theme code="miles.of.text"/><span class="branch-result-zipcode p-l-5">XXXXX</span> 
            		<spring:theme code="have.selected.text" /></span><spring:theme code="request.radius.textmsg"/></span>
            	</p>
            	<div class="text-center marginTop10 hidden branch-not-found">
            		<span class="branch-not-found-icon"><common:exclamatoryIcon iconColor="#ed8606" width="34" height="34" /></span>
            		<h2 class="text-orange bold-text f-s-38 h1"><spring:theme code="oh.Notext"/></h2>
            		<h3 class="text-orange bold-text"><spring:theme code="nobranch.msg"/> <span class="branch-result-miles p-l-5">xx</span> <spring:theme code="miles.of.text"/> <span class="branch-result-zipcode p-l-5">XXXXX</span>.</h3>
            		<p class="f-s-16"><spring:theme code="request.suggestbranch.text"/></p>
            	</div>
            </div>
        </div>
		<div class="branch-results">
		</div>
		<div class="row text-center">
			<div class="col-md-12">
				<button class="btn btn-block bold-text hidden branch-show-more" onclick="ACC.accountdashboard.showMoreBranches(this)"><spring:theme code="storeDetailsContent.showMoreBranches" /></button>
			</div>
		</div>
    </div>
</div>
<!-- ./Home Branch -->
<!-- Verification Messages -->
<div class="row">
	<div class="col-md-offset-3 col-md-6 col-xs-offset-1 col-xs-10 col-sm-11 text-center marginBottom40 hidden verification-container">
		<!-- Spinner loading message after contractor form submit-->
		<div class="hidden verification-container-step1">
			<div class="spinner"></div>
			<p class="marginTop40 text-default f-s-32 font-Geogrotesque"><spring:theme code="requestaccount.spinner.loading.message1" /></p>
			<p class="text-muted"><em><spring:theme code="requestaccount.spinner.loading.message2" /></em></p>
		</div>
		<!-- case 1 Success message(great news) after contractor form submit-->
		<div class="hidden verification-container-step2">
			<common:checkmarkIcon_rta width="68" height="68"></common:checkmarkIcon_rta>
			<p class="m-y-15 text-default f-s-32 font-Geogrotesque"><spring:theme code="requestaccount.contractor.success.message1" /></p>
			<p class="text-muted"><em><spring:theme code="requestaccount.contractor.success.message2" /></em></p>
		</div>
		<!-- case 2 - Failure message(please allow 1-2days) after contractor form submit-->
		<div class="text-default hidden verification-container-step3">
			<common:checkmarkIcon_rta width="68" height="68"></common:checkmarkIcon_rta>
			<p class="m-y-15 f-s-32 font-Geogrotesque"><spring:theme code="requestaccount.failure.message1" /></p>
			<p class="f-s-32 font-Geogrotesque"><spring:theme code="requestaccount.failure.message2" /></p>
		</div>
		<!-- case 3 - Existing mail id message(checek your mail) -->
		<div class="text-default hidden verification-container-step4">
			<common:checkmarkIcon_rta width="68" height="68"></common:checkmarkIcon_rta>
			<p class="m-y-15 f-s-32 font-Geogrotesque"><spring:theme code="requestaccount.existing.emailid.message1" /></p>
			<p class="f-s-32 font-Geogrotesque"><spring:theme code="requestaccount.existing.emailid.message2" /></p>
		</div>
	</div>
</div>
<!-- ./Verification Messages -->

<!-- Confirmation Messages -->
<div class="marginBottom40 hidden verification-container-step5">
	<div class="row text-center">
		<div class="col-md-offset-3 col-md-6 col-xs-offset-2 col-xs-8 col-sm-10 marginTopBVottom30">
			<h2 class="font-Geogrotesque text-default f-s-40 business-details-headline h1"><spring:theme code="requestaccount.confirmation.message.heading1" /></h2>
			<p class="text-muted bold-text f-s-18 margin0 reqacc-option-message"><spring:theme code="requestaccount.confirmation.message.heading2" /></p>
		</div>
		<div class="${currentBaseStoreId eq 'siteone' ? 'col-md-offset-3' : 'col-md-offset-4' } col-md-2 col-xs-offset-2 col-xs-8 col-sm-4">
			<div class="padding10 shop-btn">
				<div class="accountactive_icon_wrapper">
					<common:activeaccount1_icon width="85" height="85"></common:activeaccount1_icon>
				</div>
				<p><spring:theme code="${currentBaseStoreId eq 'siteone' ? 'requestaccount.confirmation.message1' : 'requestaccount.confirmation.message1.ca' }" /></p>
			</div>
		</div>
		<div class="col-md-offset-0 col-md-2 col-xs-offset-2 col-xs-8 col-sm-4 ${currentBaseStoreId eq 'siteone' ? '' : 'hidden' }">
			<div class="padding10 shop-btn">
				<div class="accountactive_icon_wrapper">
					<common:activeaccount2_Icon width="85" height="85"></common:activeaccount2_Icon>
				</div>
				<p><spring:theme code="requestaccount.confirmation.message2" /></p>
			</div>
		</div>
		<div class="col-md-offset-0 col-md-2 col-xs-offset-2 col-xs-8 col-sm-4">
			<div class="padding10 shop-btn">
				<div class="accountactive_icon_wrapper">
					<common:homeownerIcon width="85" height="85"></common:homeownerIcon>
				</div>
				<p><spring:theme code="requestaccount.confirmation.message3" /></p>
			</div>
		</div>
	</div>
</div>
<!-- ./Confirmation Messages -->
</template:page>
