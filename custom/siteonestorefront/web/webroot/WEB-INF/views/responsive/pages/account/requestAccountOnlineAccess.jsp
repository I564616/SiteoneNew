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
<c:url value="/request-account/onlineAccess" var="requestForm" />
<input type="hidden" class="currentBaseStoreId" value="${currentBaseStoreId eq 'siteoneCA' ? 'CA' : 'US'}"/>
<template:page pageTitle="${pageTitle}" hideHeaderLinks="true">
<div id="signinId" style="display: none">
<common:signinoverlaynew loginError="${loginError}" errorMessage="${errorMessage}" remainingAttempts="${remainingAttempts}" />
</div>
<script src="https://www.google.com/recaptcha/api.js"></script>
<c:url var="homePage" value="/" />
<!-- Banner -->
<div class="row flex-center banner-block">
  <div class="col-xs-12 margin-bot-10-xs text-center font-GeogrotesqueSemiBold f-s-26 f-s-22-xs-px"><spring:theme code="request.online.access" /></div>
</div>
<!-- ./Banner -->
<!-- Heading -->
<div class="row m-y-55 m-b-0-xs text-default text-center selfServe-form">
  <div class="col-md-6 col-md-offset-3 col-sm-12">
    <h1 class="font-Geogrotesque f-s-32 f-s-26-xs-px margin0 m-b-15"><spring:theme code="request.online.validation" /></h1>
    <p class="text-dark-gray f-s-18 f-s-16-xs-px p-x-20-xs margin0"><spring:theme code="request.online.info" /></p>
  </div>
</div>
<!-- ./Heading -->
<!-- Online Access Form -->
<form id="siteOneRequestOnlineAccessForm" class="selfServe-form" action="${requestForm}" method="POST" modelAttribute="siteOneRequestOnlineAccessForm">
  <div class="row m-b-60 m-t-0-xs">
    <div class="col-md-8 col-md-offset-2 col-sm-12 padding-xs-30 add-border-radius inner-container">
		<div class="form-group m-b-5">
            <label class="control-label pull-left font-14-xs font-Geogrotesque" for="accountNumber"><spring:theme code="request.online.number" /></label>
            <div class="cl"></div>
            <input id="accountNumber" name="accountNumber" class="details-input-field form-control" type="text" value="">
			<input type="radio" checked="checked" name="hasAccountNumber" value="yes" class="hidden" />
        </div>
		<span id="errorAccountNumber"></span>
		<div class="btn btn-blue btn-block m-b-5 bold transition-3s font-small-xs self-serve-help" onclick="ACC.accountdashboard.accountNumberHelp()"><spring:theme code="request.online.help" /></div>
		<div class="form-group margin-top-20 m-b-5">
            <label class="control-label pull-left font-14-xs font-Geogrotesque" for="emailAddress"><spring:theme code="request.online.email" /></label>
            <div class="cl"></div>
            <input id="selfServeEmail" name="emailAddress" class="details-input-field form-control" type="email" value="" autocomplete="off">
        </div>
		<span id="errorEmailAddress"></span>
		<div class="p-y-20 p-x-60 padding-xs-10 bg-light-grey add-border-radius text-center text-default f-s-13 font-small-xs"><p class="bold m-b-0"><spring:theme code="request.online.support" /></p>
		<spring:theme code="request.online.additional" />, <span class="show-xs"><spring:theme code="request.online.callus" />.</span><p class="m-b-0"><spring:theme code="request.online.available" /></p></div>
		<div class="line-divider marginTop40"></div>
		<div class="row">
			<div class="col-md-12 text-center">
				<div id="recaptcha-border" class="m-y-55 m-y-40-xs recaptcha-error-border">
					<div id="grecaptcha" class="g-recaptcha" data-callback="captcha_onclick" data-sitekey="${recaptchaPublicKey}"></div>
					<input type="hidden" name="recaptcha" id="recaptchaValidator" />
					<input type="hidden" id="recaptchaChallengeAnswered" value="${recaptchaChallengeAnswered}" />
					<div id="recaptcha-error" class="hidden" style="color: rgb(254, 3, 3); font-weight: normal;">
						<spring:theme code="recaptcha.error" />
					</div>
				</div>
			</div>
			<div class="col-md-4 col-md-offset-4 col-xs-12">
				<button type="submit" class="btn btn-primary btn-block transition-3s selfServe-formsubmit"><spring:theme code="request.online.continue" /></button>
			</div>
		</div>
    </div>
  </div>
</form>
<!-- ./Online Access Form -->
<!-- Verification Messages -->
<div class="row hidden selfServe-response-1 selfServe-response-2 selfServe-response-3">
  <div class="col-md-offset-3 col-md-6 col-xs-offset-1 col-xs-10 col-sm-11 text-center add-border-radius m-y-100 m-y-75-xs selfServe-respons">
    <!-- case 1 Success message form submit-->
    <div class="hidden selfServe-response-1">
      <svg xmlns="http://www.w3.org/2000/svg" width="68" height="68" viewBox="0 0 68 68">
        <circle cx="34" cy="34" r="34" fill="#f1f2f2"></circle>
        <path d="M8.412,83.2.363,75.155a1.238,1.238,0,0,1,0-1.751l1.751-1.751a1.238,1.238,0,0,1,1.751,0l5.422,5.422L20.9,65.461a1.238,1.238,0,0,1,1.751,0L24.4,67.212a1.238,1.238,0,0,1,0,1.751L10.163,83.2a1.238,1.238,0,0,1-1.751,0Z" transform="translate(20 -40.098)" fill="#78a22f"></path>
      </svg>
      <p class="m-y-15 text-default f-s-32 f-s-26-xs-px font-Geogrotesque"><spring:theme code="request.online.password" /></p>
    </div>
    <!-- case 2 - Failure messages form submit-->
    <div class="text-default hidden selfServe-response-2 selfServe-response-3">
      <svg xmlns="http://www.w3.org/2000/svg" width="68" height="68" viewBox="0 0 68 68">
        <circle cx="34" cy="34" r="34" fill="#f1f2f2"></circle>
        <path d="M23.813,21.094a3.906,3.906,0,1,1-3.906-3.906A3.911,3.911,0,0,1,23.813,21.094ZM16.452,1.23l.664,13.281a1.172,1.172,0,0,0,1.17,1.113h3.239a1.172,1.172,0,0,0,1.17-1.113L23.36,1.23A1.172,1.172,0,0,0,22.19,0H17.623A1.172,1.172,0,0,0,16.452,1.23Z" transform="translate(15 20)" fill="#78a22f"></path>
      </svg>
      <p class="m-y-15 f-s-32 f-s-26-xs-px font-Geogrotesque hidden selfServe-response-2"><spring:theme code="request.online.provided" /></p>
	    <p class="m-y-15 f-s-32 f-s-26-xs-px font-Geogrotesque hidden selfServe-response-3"><spring:theme code="request.online.associated" /></p>
      <p class="text-muted f-s-14 font-14-xs marginBottom30"><spring:theme code="request.online.steps.${currentBaseStoreId eq 'siteoneCA' ? 'ca' : 'com'}" /></p>
      <button class="btn btn-gray btn-block bold font-14-xs marginTop10 transition-3s hidden selfServe-response-2" onclick="ACC.accountdashboard.selfServeEventAnalytics('', 'Contact Customer Service', 'my account : error: no or invalid email', 'linkClicks'); window.location.href='${homePage}contactus'"><spring:theme code="request.online.contact" /></button>
      <button class="btn btn-gray btn-block bold font-14-xs marginTop10 transition-3s hidden selfServe-response-2" onclick="ACC.accountdashboard.selfServeEventAnalytics('', 'Contact your Home Branch', 'my account : error: no or invalid email', 'linkClicks'); window.location.href='${homePage}store-finder'"><spring:theme code="request.online.branch" /></button>
      <button class="btn btn-gray btn-block bold bold font-14-xs marginTop10 transition-3s hidden selfServe-response-2" onclick="ACC.accountdashboard.selfServeEventAnalytics('', 'Enter a Different email', 'my account : error: no or invalid email', 'linkClicks');ACC.accountdashboard.backSelfServe();"><spring:theme code="request.online.different" /></button>
      <button class="btn btn-gray btn-block bold font-14-xs marginTop10 marginBottom20 transition-3s hidden selfServe-response-2" onclick="ACC.accountdashboard.selfServeEventAnalytics('', 'Create a new site one account', 'my account : error: no or invalid email', 'linkClicks'); window.location.href='${homePage}request-account/form'"><spring:theme code="request.online.new" /></button>
      <button class="btn btn-primary btn-block bold font-14-xs marginTop10 transition-3s hidden selfServe-response-3 js-password-forgotten" data-link="<c:url value="/login/pw/request"/>" data-cbox-title="Send Reset Email"><spring:theme code="request.online.forgotpassword" /></button>
      <button class="btn btn-gray btn-block bold font-14-xs marginTop10 transition-3s hidden selfServe-response-3" onclick="ACC.accountdashboard.selfServeEventAnalytics('', 'Contact Customer Service', 'my account : error : existing account w/online access', 'linkClicks'); window.location.href='${homePage}contactus'"><spring:theme code="request.online.contact" /></button>
      <div class="flex m-t-20-xs m-t-60 onlineaccess-btn">
      <div><a class="btn btn-default transition-3s" href="${homePage}"><spring:theme code="request.online.back" /><span class="selfServe-home hidden">Home</span><span class="selfServe-siteone"><spring:theme code="request.online.${currentBaseStoreId eq 'siteoneCA' ? 'ca' : 'com'}" /></span></a></div>
      <div class="flex onlineaccess-txt">
      <p class="f-w-700 m-b-0 f-s-13 font-small-xs"><spring:theme code="request.online.call" /></p>
      <p class="m-b-0 f-s-13 font-small-xs"><spring:theme code="request.online.callus" /> (1-800-748-3663)</p>
      <p class="f-s-13 font-small-xs"><spring:theme code="request.online.available" /></p>
      </div>
      </div>
    </div>
	
  </div>
</div>
<!-- ./Verification Messages -->
</template:page>