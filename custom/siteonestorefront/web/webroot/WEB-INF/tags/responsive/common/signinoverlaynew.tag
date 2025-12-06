<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>

<%@ attribute name="loginError" required="false" type="java.lang.String" %>
<%@ attribute name="errorMessage" required="false" type="java.lang.String" %>
<%@ attribute name="remainingAttempts" required="false" type="java.lang.String" %>

<%@ attribute name="hideRightSide" required="false" type="java.lang.Boolean" %>
<%@ attribute name="guestCheckoutEnable" required="false" type="java.lang.Boolean" %>

<c:set var="leftSideWidth" value="12" />

<c:if test="${hideRightSide == null}">
	<c:set var="hideRightSide" value="${false}" />
	<c:set var="leftSideWidth" value="4" />
</c:if>

<c:if test="${guestCheckoutEnable == null}">
	<c:set var="guestCheckoutEnable" value="${false}" />
</c:if>

<div class="row m-l-0 m-r-0 signinpopupnew">
	<div class="col-md-${leftSideWidth} signinpopup-left">
		<c:url var="springsecurity" value="/j_spring_security_check" />
		<form:form id="loginForm" action="${springsecurity}" method="post">
			<c:if test="${hideRightSide eq true}">
				<input type="hidden" name="loginToBuy" value="true"/>
			</c:if>
			<p class="sigin-center-xs font-GeogrotesqueSemiBold f-s-26 m-t-15 m-b-5 text-default" role="heading" aria-level="3">
				<spring:theme code="login.title" />
			</p>
			<p class="bold text-default f-s-16 ${ loginError ? '' : 'marginBottom40' } hidden-xs">
				<spring:theme code="signinoverlay.heading1" />
			</p>
			<p class="bold font-size-14 text-default ${ loginError ? '' : 'marginBottom40' } visible-xs padding-xs-x-45 margin-xs-bottom-20 sigin-center-xs">
				<spring:theme code="signinoverlay.heading2" />
			</p>
			
			<c:if test="${loginError eq true}">
				<div class="alert alert-danger">
					<div>
						<spring:theme text="${errorMessage}" arguments="${remainingAttempts}" htmlEscape="false" />
					</div>
					<div class="cl"></div>
				</div>
			</c:if>
				
			<div class="form-group">
				<input class="form-control m-bottom-10" id="usernamePopup" name="j_username" type="text" value=""
					placeholder="<spring:theme code="signIn.username.email" />" onfocus="this.placeholder = ''"
					onblur="this.placeholder = '<spring:theme code="signIn.username.email" />'" autocomplete="off">
			</div>

		
			<div class="form-group">
				<input class="form-control m-bottom-10" id="j_password" name="j_password" type="password" value=""
					autocomplete="off" placeholder="<spring:theme code="signIn.username.pwrd" />"
					onfocus="this.placeholder = ''"
					onblur="this.placeholder='<spring:theme code="signIn.username.pwrd" />'">
			</div>

			<div class="forgot-password font-size-14">
				<a href="#" data-link="<c:url value="/login/pw/request"/>"
					class="js-password-forgotten bold font-size-14" data-cbox-title="Send Reset Email">
					<spring:theme code="header.forgot" />
				</a>
			</div>
			<div class="row margintop20 text-default font-size-14 hidden ">
				<div class="col-md-5 col-xs-12 m-t-15-xs">
					<span class="colored"> 
						<input type="checkbox"
						class="remember-checkbox" tabindex="3" value="remember"
						id="rememberPopup">
						<spring:theme code="header.rememberMe.guest.title" />
					</span>
				</div>
				<div class="col-md-7 hidden-xs font-size-14 text-grey italic-text text-align-right not-recom-text">
					<span class="block"><spring:theme code="signinoverlay.text1" /></span>
					<span><spring:theme code="signinoverlay.text2" /></span>
				</div>
			</div>
			<button type="submit" id="loginsubmitPopup" class="btn btn-primary signin-btn w-xs-100 m-t-50">
				<spring:theme code="header.signIn" />
			</button>
		</form:form>
	</div>
		<div class="col-md-8 p-r-0 p-l-30 signin-pad-xs-0 m-b-15-xs">
			<div class="top-section hidden-xs p-l-40 p-t-20 ${guestCheckoutEnable ? 'p-b-20' : 'height-50 p-b-30' }">
				<p class="f-s-26 m-t-15 m-b-5 font-GeogrotesqueSemiBold" role="heading" aria-level="3"><spring:theme code="header.customerRegistration" /></p>
				<div class="row">
					<div class="col-md-9 bold f-s-16 m-b-10">
						<span><spring:theme code="signinoverlay.text3" /></span>
						<span><spring:theme code="signinoverlay.text4" /></span>
					</div>
				</div>
				<div class="row font-size-14 bold">
					<div class="col-md-4">
						<ul>
							<li class="m-b-5"><spring:theme code="signinoverlay.text5" /></li>
							<li class="m-b-5"><spring:theme code="signinoverlay.text6" /></li>
							<c:if test="${guestCheckoutEnable eq false}">
								<li><spring:theme code="signinoverlay.text7" /></li>
							</c:if>
						</ul>
					</div>
					<div class="col-md-4 p-l-0">
						<ul>
							<c:if test="${guestCheckoutEnable eq true}">
								<li class="m-b-5"><spring:theme code="signinoverlay.text7" /></li>
							</c:if>
							<li class="m-b-5"><spring:theme code="signinoverlay.text8" /></li>
							<c:if test="${guestCheckoutEnable eq false}">
								<li class="m-b-5"><spring:theme code="signinoverlay.text9" /></li>
								<li class="m-b-5"><spring:theme code="signinoverlay.text24" /></li>
							</c:if>
						</ul>
					</div>
					<c:if test="${guestCheckoutEnable eq true}">
						<div class="col-md-4 p-l-0">
							<ul>
								<li class="m-b-5"><spring:theme code="signinoverlay.text9" /></li>
								<li class="m-b-5"><spring:theme code="signinoverlay.text24" /></li>
							</ul>
						</div>
					</c:if>
				</div>
			</div>
			<div class="bottom-section p-b-30 m-t-10-xs padding-xs-y-20 pad-lft-10 ${currentLanguage.isocode eq 'es' ? 'padding-md-10' : ''} pad-rgt-10 ${guestCheckoutEnable ? 'p-t-24' : 'p-t-40' }">
				<div class="row margin-left-30 m-xs-auto sigin-center-xs">
					<div class="col-md-6">
						<div class="row">
							<div class="col-md-2 vertical bold p-b-27 p-t-20 hidden-xs ${currentLanguage.isocode eq 'es' ? 'vertical-spanish' : ''}"><spring:theme code="signinoverlay.text10" /></div>
							<div class="col-md-10 pad-lft-20">
								<p class="font-GeogrotesqueSemiBold f-s-18 text-default m-t-10-xs"><spring:theme code="signinoverlay.text11" /></p>
								<p class="text-default font-size-14"><spring:theme code="signinoverlay.text12" /></p>
								<a href="<c:url value="/request-account/onlineAccess"/>" class="btn btn-default bold-text margin-xs-bottom-20 margin-xs-top-20 ${currentLanguage.isocode eq 'es' ? 'marginTop50' : ''}">
									<spring:theme code="signinoverlay.btn1" />
								</a>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="row">
							<div class="col-md-2 vertical bold p-t-3 p-b-20 hidden-xs ${currentLanguage.isocode eq 'es' ? 'vertical-spanish' : ''}"><spring:theme code="signinoverlay.text13" /></div>
							<div class="col-md-10 pad-lft-20 m-t-10-xs">
								<p class="font-GeogrotesqueSemiBold f-s-18 text-default margin-xs-top-20"><spring:theme code="signinoverlay.text15" /></p>
								<p class="text-default font-size-14"><spring:theme code="signinoverlay.text16" /></p>
								<a href="<c:url value="/request-account/form"/>" class="btn btn-default bold-text margin-xs-top-20 margin-bot-10-xs ${currentLanguage.isocode eq 'es' ? 'm-t-5' : ''}">
									<span class="hidden-xs"><spring:theme code="signinoverlay.btn2" /></span>
									<span class="visible-xs"><spring:theme code="header.requestAccount" /></span>
								</a>
							</div>
						</div>
					</div>
					<c:if test="${guestCheckoutEnable eq true}">
						<div class="col-md-12 p-b-20">
							<div class="row">
								<div class="hline marginTop20 marginBottom20 m-r-0-xs m-r-35"></div>
								<div class="col-md-6 p-l-5 signin-xs-view p-r-0">
									<p class="f-s-18 font-GeogrotesqueSemiBold text-default"><spring:theme code="header.customerRegistration.guest.line1" /></p>
									<p class="font-size-14 text-default"><spring:theme code="signinoverlay.text14" /></p>
								</div>
								<div class="col-md-6">
									<div class="col-md-10 col-md-offset-1 col-xs-12 p-l-0 p-r-0">
										<a href="<c:url value="/cart"/>" class="btn btn-primary m-t-25 m-t-20-xs bold-text arrow-guest guest-Signin-overlay-popup"><spring:theme code="header.customerRegistration.guest.line1" /></a>
									</div>
								</div>
							</div>
						</div>
					</c:if>
				</div>
			</div>
			<div class="hidden-md hidden-sm hidden-lg signin-xs-view">
				<p class="text-center font-GeogrotesqueSemiBold f-s-24 m-t-15 m-b-5 text-default" role="heading" aria-level="3"><spring:theme code="signinoverlay.text17" /></p>
				<div class="row margin-xs-x-0">
					<div class="col-xs-12 m-bottom-10">
						<div class="row signin-overlay-bg-lightGrey flex-center">
							<div class="col-xs-2 text-center p-l-0 p-r-0">
								<common:headerIcon iconName="pricing" iconFill="#77A12E" iconColor="#607f2b" width="18" height="30" viewBox="0 0 15 25" display="" />
							</div>
							<div class="col-xs-10 bg-light padding10">
								<span class="font-size-14 text-default "><spring:theme code="signinoverlay.text18" /></span>
								<p class="f-s-12 m-b-0 p-t-3"><spring:theme code="signinoverlay.text19" /></p>
							</div>
						</div>
					</div>
					<div class="col-xs-12 m-bottom-10">
						<div class="row signin-overlay-bg-lightGrey flex-center">
							<div class="col-xs-2 text-center p-l-0 p-r-0">
								<common:headerIcon iconName="carticon" iconFill="#EEEEEE" iconColor="#607f2b" width="30" height="30" viewBox="0 0 20 20" display="" />
							</div>
							<div class="col-xs-10 bg-light padding10">
								<span class="font-size-14 text-default"><spring:theme code="signinoverlay.text20" /></span>
								<p class="f-s-12 m-b-0 p-t-3"><spring:theme code="signinoverlay.text21" /></p>
							</div>
						</div>
					</div>
					<div class="col-xs-12 m-b-5">
						<div class="row signin-overlay-bg-lightGrey flex-center">
							<div class="col-xs-2 text-center p-l-0 p-r-0">
								<common:headerIcon iconName="mobile-app" iconFill="#77A12E" iconColor="#607f2b" width="21" height="30" viewBox="0 0 13 18" display="" />
							</div>
							<div class="col-xs-10 bg-light padding10">
								<span class="font-size-14 text-default"><spring:theme code="signinoverlay.text22" /></span>
								<p class="f-s-12 m-b-0 p-t-3"><spring:theme code="signinoverlay.text23" /></p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
</div>