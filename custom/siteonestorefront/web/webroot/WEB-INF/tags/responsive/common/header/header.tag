<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="hideHeaderLinks" required="false"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/desktop/formElement"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/responsive/store"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>                         
<%@ taglib prefix="footer" tagdir="/WEB-INF/tags/responsive/common/footer"%>
<!-- /WEB-INF/tags/responsive/common/footer/ -->
<cms:pageSlot position="TopHeaderSlot" var="component" element="div" class="container">
	<cms:component component="${component}" />
</cms:pageSlot>
<input type="hidden" id="unitUid" name="unitUid" value="${sessionShipTo.uid}">
<input type="hidden" id="emailaddres" name="emailaddres" value="${user.displayUid}">
<input type="hidden" id="trackBranch" name="trackBranch" value="${sessionStore.storeId}">	   
<input type="hidden" value="${isMixedCartEnabled}" class="checkmixedcart"/>   
<input type="hidden" id="loginErrorMsg" name="loginErrorMsg" value=" ${errorMessage}">
<spring:url value="/" var="homelink" htmlEscape="false"/>
<c:set var = "userAgent" value = "${fn:toLowerCase(header['User-Agent'])}"/>
<c:set var = "mobileFlag" value = "false"/>
<c:set var = "loggedIn" value = "true"/>
<c:if test="${fn:contains(userAgent, 'ipad') || fn:contains(userAgent, 'iphone') || fn:contains(userAgent, 'android')}">
<!-- If Mobile -->
<c:set var = "mobileFlag" value = "true"/>
</c:if>
<sec:authorize access="hasAnyRole('ROLE_ANONYMOUS')">
<c:set var = "loggedIn" value = "false"/>
</sec:authorize>
<c:set var="simpleHeader" value="false" />
<c:if test="${cmsPage.uid eq 'requestAccountOnlineAccess' || cmsPage.uid eq 'requestaccount'|| cmsPage.uid eq 'choosePickupDeliveryMethodPage' || cmsPage.uid eq 'siteOneCheckoutPage' || cmsPage.uid eq 'orderSummaryPage' || cmsPage.uid eq 'multiStepCheckoutSummaryPage' || cmsPage.uid eq 'orderConfirmationPage' || cmsPage.uid eq 'linktopayverification' || cmsPage.uid eq 'linkConfirmationPage' || cmsPage.uid eq 'linktopaypayment'}">
    <c:set var="simpleHeader" value="true" />
</c:if>
<input type="hidden" id="globalMobileFlag" name="globalMobileFlag" value="${mobileFlag}">
<c:if test="${isCreditCodeValid eq true}">
	<div id="logo-svg" class="hidden">
		<common:headerIcon iconName="logo" iconFill="none" iconColor="#78A22F" iconColorSecond="#414244" width="201" height="48" viewBox="0 0 201 48" />
	</div>
	<div id="creditCodeError" class="hidden">
		<div class="row">
			<div class="col-sm-12">
				<p class="m-t-15 m-b-25 f-s-26 f-s-16-xs-px m-t-30-xs font-Geogrotesque text-default"><spring:theme code="header.account.unable.heading" /></p>
				<p class="m-b-25 f-s-20 font-14-xs"><spring:theme code="header.account.unable.message" /></p>
			</div>
			<div class="col-sm-12 text-center">
				<button class="btn btn-default bold m-r-15" onclick="$('.logout').trigger('click');ACC.colorbox.close();"><spring:theme code="header.account.unable.signout" /></button>
				<c:if test="${isPayBillEnabled}">
					<a href="${homelink}my-account/pay-account-online" class="btn btn-primary bold" target="redirectToBT">
						<spring:theme code="text.account.payBillOnline.btn" />
					</a>
				</c:if>
			</div>
		</div>
	</div>
</c:if>
<!-- localizedBranch : ${localizedBranch} -->
<c:if test="${not empty localizedBranch}">
	<input type="hidden" id="localized-town" value="${localizedBranch.address.town}">
	<input type="hidden" id="localized-isocode" value="${localizedBranch.address.region.isocodeShort}">
	<input type="hidden" id="localized-store" value="${localizedBranch.storeId}">
	<div id="localizedBranch" class="hidden">
		<div class="row">
			<div class="col-sm-12">
				<p class="m-t-15 m-b-25 f-s-26 f-s-20-xs-px m-t-30-xs m-b-10-xs font-Geogrotesque text-default">Update Shopping Branch Location?</p>
				<p class="m-b-25 f-s-18 font-14-xs m-b-10-xs">${localizedBranch.address.town}, ${localizedBranch.address.region.isocodeShort} #${localizedBranch.storeId} is the default branch for that account
				<br>Would you like to switch to that location?</p>
				<button class="bold-text btn btn-link underline-text m-b-25 m-b-5-xs" onclick="ACC.colorbox.close();setTimeout(function(){$('.js-select-location').trigger('click');},500);">Select Different Branch</button>
			</div>
			<div class="col-xs-12 hidden-lg hidden-md">
				<form id="command" action="${homelink}store-finder/make-my-store/${localizedBranch.storeId}" method="get">
					<input type="submit" value="Confirm" class="btn btn-primary btn-block bold marginBottom20 makeMyBranch"> 
				</form>
			</div>
			<div class="col-sm-5 col-sm-offset-1 col-xs-12 col-xs-offset-0 text-right">
				<button class="btn btn-block btn-default bold" onclick="ACC.colorbox.close();">Keep Current Branch</button>
			</div>
			<div class="col-sm-5 text-left hidden-xs">
				<form id="command" action="${homelink}store-finder/make-my-store/${localizedBranch.storeId}" method="get">
					<input type="submit" value="Confirm" class="btn btn-primary btn-block bold marginBottom20 makeMyBranch"> 
				</form>
			</div>
		</div>
	</div>
</c:if>
<c:set var="hasShipTo" value="${listOfShipTo.size()}" />
<input type="hidden" id="cartCountId" name="cartCountId" value="0">
<input type="hidden" class="loginstatus" value="${loginFlagModel}"/>
<input type="hidden" class="cartdataguest" value="${fn:length(cartData.entries)}"/>
<c:set var="logoWidth" value="col-xs-7 col-sm-8 col-md-2 width-auto global-logo-section" />
<c:if test="${cmsPage.uid eq 'requestAccountOnlineAccess' || cmsPage.uid eq 'requestaccount' || cmsPage.uid eq 'siteOneCheckoutPage' || cmsPage.uid eq 'choosePickupDeliveryMethodPage' || cmsPage.uid eq 'orderSummaryPage' || cmsPage.uid eq 'multiStepCheckoutSummaryPage' || cmsPage.uid eq 'orderConfirmationPage' || cmsPage.uid eq 'linktopayverification' || cmsPage.uid eq 'linkConfirmationPage' || cmsPage.uid eq 'linktopaypayment'}">
	<c:set var="logoWidth" value="col-sm-10 col-md-10" />
</c:if>
<c:choose>
	<c:when test="${empty fullPagePath}">
		<input type="hidden" class="siteonepagename" value="${fn:escapeXml(cmsPage.fullPagePath)}"/>
		<input type="hidden" class="siteonepathingchannel" value="${fn:escapeXml(cmsPage.pathingChannel)}"/> 
	</c:when>
	<c:otherwise>
		<input type="hidden" class="siteonepagename" value="${fn:escapeXml(fullPagePath)}"/>
		<input type="hidden" class="siteonepathingchannel" value="${fn:escapeXml(pathingChannel)}"/>
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${cmsPage.uid eq 'linkConfirmationPage' || cmsPage.uid eq 'linktopayverification'}">
		<c:set var="logoWidth" value="col-sm-8 col-md-10 col-xs-6 linktopayLogo" />
		<c:set var="backtoLink" value="col-xs-6" />
	</c:when>
	<c:otherwise>
		<c:set var="backtoLink" value="col-sm-2 col-md-2 mobile-checkout " />
	</c:otherwise>
</c:choose>
<header class="js-mainHeader" role="combobox, grid, listbox, menu, menubar, radiogroup, tablist, tree, treegrid">
    <div class="bg-black ${!simpleHeader ? 'animated-5s animateInDown ' : ''}linktracking-header">
        <!-- Top Hat header starts -->
        <c:if test="${!simpleHeader}">
            <div class="p-y-10 bg-dark-green text-white header-top-hat">
                <div class="container-lg padding0">
                    <div class="row margin0 flex-center">
                        <div class="col-md-3 hidden-sm hidden-xs ${loggedIn && hasShipTo gt 1 ? 'col-md-22pe ' : ''} flex-center p-t-3 top-hat-col-1">
                            <c:if test="${empty hideHeaderLinks && cmsPage.uid ne 'linktopayverification' && cmsPage.uid ne 'linkConfirmationPage' && cmsPage.uid ne 'linktopaypayment'}">
                                <a data-global-linkname="enable accessibility" href="#" class="text-capitalize text-white text-white-hover UsableNetAssistive" onclick="return enableUsableNetAssistive()">
                                    <spring:theme code="header.accessibility" />
                                </a>
                                <a data-global-linkname="exit accessibility mode" href="#" class="text-capitalize text-white text-white-hover UsableNetAssistive disableUsableNetAssistive hidden">Exit Accessibility Mode</a><span class="p-x-10">|</span>
                            </c:if>
                            <c:if test="${empty hideHeaderLinks && cmsPage.uid ne 'linktopayverification' && cmsPage.uid ne 'linkConfirmationPage' && cmsPage.uid ne 'linktopaypayment'}">
                                <span class="lang-dropdown">
                                    <footer:languageSelector currentLanguage="${currentLanguage}" languages="${languages}" />
                                </span>
                            </c:if>
                        </div>
                        <store:storeHeader loggedIn="${loggedIn}" hasShipTo="${hasShipTo}" />
                        <div class="col-xs-4 hidden-md hidden-lg flex-center justify-flex-end-sm justify-flex-end-xs p-t-3 p-t-0-xs p-t-0-sm top-hat-col-1">
                            <c:if test="${empty hideHeaderLinks && cmsPage.uid ne 'linktopayverification' && cmsPage.uid ne 'linkConfirmationPage' && cmsPage.uid ne 'linktopaypayment'}">
                                <a data-global-linkname="enable accessibility" href="#" class="text-capitalize text-white text-white-hover UsableNetAssistive" onclick="return enableUsableNetAssistive()">
                                    <common:headerIcon iconName="accessibility" iconColor="#ffffff" iconColorSecond="#ffffff" iconFill="none" width="19" height="18" viewBox="0 0 19 18" display="" />
                                </a>
                                <a data-global-linkname="exit accessibility mode" href="#" class="text-capitalize text-white text-white-hover UsableNetAssistive disableUsableNetAssistive hidden">Exit Accessibility Mode</a><span class="bg-white display-ib accessibility-line"></span>
                            </c:if>
                            <c:if test="${empty hideHeaderLinks && cmsPage.uid ne 'linktopayverification' && cmsPage.uid ne 'linkConfirmationPage' && cmsPage.uid ne 'linktopaypayment'}">
                                <span class="lang-dropdown">
                                    <footer:languageSelector currentLanguage="${currentLanguage}" languages="${languages}" />
                                </span>
                            </c:if>
                        </div>
                        <c:if test="${loggedIn}">
                            <div data-global-linkname="ship-to" class="col-md-4 col-md-30pe hidden-sm hidden-xs p-l-20 b-l-grey flex-center shipToPopup top-hat-col-3 ${hasShipTo gt 1 ? '' : 'hidden' }" onclick="ACC.accountdashboard.showShipTos(this)">
                                <common:headerIcon iconName="shipping" iconFill="#f90" iconColor="#fff" width="22" height="16" viewBox="0 0 22 16" display="m-r-5" />
                                <span class="pointer bold p-r-5 visible-md-lg-ib"><spring:theme code="global.header.shipto" /></span>
                                <span title="${sessionShipTo.name}" class="pointer shipto-header">${sessionShipTo.name}</span>
                                <span class="glyphicon glyphicon-chevron-down f-s-7 p-l-5 headerShow"></span>
                            </div>
                            <div class="${hasShipTo gt 1 ? 'col-md-20pe' : '' } col-md-3 hidden-sm hidden-xs text-align-right top-hat-col-4">
                                <ycommerce:testId code="header_LoggedUser">
                                    <c:set var="firstName" value="${fn:replace(user.firstName, '%20', ' ')}" />
                                    <c:set var="lastName" value="${fn:replace(user.lastName, '%20', ' ')}" />
                                    <span class="visible-md-lg-ib"><spring:theme code="global.header.welcome" /></span>
                                    <span id="flname" class="headerShow">${firstName}</span>
                                    <span class="visible-md-lg-ib">${lastName}</span>
                                </ycommerce:testId>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </c:if>
        <!-- ./ Top Hat header -->
        <!-- Top Right header starts -->
        <div class="col-md-12 hidden header-menu-mob">
            <div class="nav__Topright hidden-xs hidden-sm">
                <ul class="top-rightMenu">
                    <li>
                        <c:if test="${empty hideHeaderLinks && cmsPage.uid ne 'linktopayverification' && cmsPage.uid ne 'linkConfirmationPage' && cmsPage.uid ne 'linktopaypayment'}">
                            <a href="${homelink}store-finder" class="notransform hidden-xs hidden-sm"><spring:theme code="header.findStore" /></a>
                        </c:if>
                    </li>
                    <li class="header-dropdown">
                        <c:if test="${empty hideHeaderLinks && cmsPage.uid ne 'linktopayverification' && cmsPage.uid ne 'linkConfirmationPage' && cmsPage.uid ne 'linktopaypayment'}">
                            <a href="${homelink}Partners" class="dropdown-toggle notransform hidden-xs hidden-sm" role="button" aria-haspopup="true" aria-expanded="false">
                                <spring:theme code="text.partner.program" />
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="${homelink}my-account/accountPartnerProgram" class="f-s-13 font-Geogrotesque removesignInlink signInOverlay">
                                    <spring:theme code="header.seeMyPoints" /></a>
                                </li>
                                <li><a href="${homelink}Partners" class="f-s-13 font-Geogrotesque">
                                    <spring:theme code="header.programOverview" /></a>
                                </li>
                                <li><a href="${homelink}PartnerPerks" class="f-s-13 font-Geogrotesque">
                                    <spring:theme code="header.programPerks" /></a>
                                </li>
                            </ul>
                        </c:if>
                    </li>
                    <li class="header-dropdown">
                        <c:if test="${empty hideHeaderLinks && cmsPage.uid ne 'linktopayverification' && cmsPage.uid ne 'linkConfirmationPage' && cmsPage.uid ne 'linktopaypayment'}">
                            <a href="${homelink}my-account/account-dashboard" class="dropdown-toggle notransform hidden-xs hidden-sm removesignInlink signInOverlay" role="button" aria-haspopup="true" aria-expanded="false" data-quotesFeatureSwitch="${quotesFeatureSwitch}">
                                <spring:theme code="header.myAccount" />
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="${homelink}my-account/account-dashboard" class="f-s-13 font-Geogrotesque removesignInlink signInOverlay">
                                        <spring:theme code="header.myAccountDashboard" />
                                    </a>
                                </li>
                                <li>
                                    <a href="${homelink}my-account/buy-again/${sessionShipTo.uid}" class="f-s-13 font-Geogrotesque removesignInlink signInOverlay js-setactivetab" data-active="purchasedOrderTab" data-key="purchasedproducts">
                                        <spring:theme code="text.account.buyagain" />
                                    </a>
                                </li>
                                <li>
                                    <a href="${homelink}my-account/orders/${sessionShipTo.uid}" class="f-s-13 font-Geogrotesque removesignInlink signInOverlay js-setactivetab" data-active="orderHistoryTab" data-key="orderhistorypage">
                                        <spring:theme code="text.account.myorders" />
                                    </a>
                                </li>
                                <c:if test="${quotesFeatureSwitch eq true}">
                                    <li>
                                        <a onclick="ACC.adobelinktracking.quotesView('','Quotes','pageName')" href="${homelink}my-account/my-quotes" class="f-s-13 font-Geogrotesque removesignInlink signInOverlay js-setactivetab">
                                            <spring:theme code="myquotes.quotes" />
                                        </a>
                                    </li>
                                </c:if>
                            </ul>
                        </c:if>
                    </li>
                    <li>
                        <c:if test="${empty hideHeaderLinks && cmsPage.uid ne 'linktopayverification' && cmsPage.uid ne 'linkConfirmationPage' && cmsPage.uid ne 'linktopaypayment'}">
                            <c:if test="${contPayBillOnline && acctPayBillOnline}">
                                <a href="/my-account/pay-account-online" class="notransform hidden-xs hidden-sm" target="redirectToBT">
                                    <spring:theme code="header.payBill" />
                                </a>
                            </c:if>
                        </c:if>
                    </li>
                    <li>
                        <c:if test="${empty hideHeaderLinks && cmsPage.uid ne 'linktopayverification' && cmsPage.uid ne 'linkConfirmationPage' && cmsPage.uid ne 'linktopaypayment'}">
                            <ycommerce:testId code="">
                                <a href="${homelink}savedList" class="removesignInlink signInOverlay">
                                    <span><spring:theme code="header.lists" /></span>
                                </a>
                            </ycommerce:testId>
                        </c:if>
                    </li>
                    <li>
                        <c:if test="${empty hideHeaderLinks && cmsPage.uid ne 'linktopayverification' && cmsPage.uid ne 'linkConfirmationPage' && cmsPage.uid ne 'linktopaypayment'}">
                            <a href="${homelink}events" class="notransform hidden-xs hidden-sm">
                                <spring:theme code="header.events" />
                            </a>
                        </c:if>
                    </li>
                    <li>
                        <c:if test="${empty hideHeaderLinks && cmsPage.uid ne 'linktopayverification' && cmsPage.uid ne 'linkConfirmationPage' && cmsPage.uid ne 'linktopaypayment'}">
                            <a href="https://careers.siteone.com" class="notransform hidden-xs hidden-sm" target="_blank">
                                <spring:theme code="header.careers" />
                            </a>
                        </c:if>
                    </li>
                    <li>
                        <c:if test="${empty hideHeaderLinks && cmsPage.uid ne 'linktopayverification' && cmsPage.uid ne 'linkConfirmationPage' && cmsPage.uid ne 'linktopaypayment'}">
                            <ycommerce:testId code="">
                                <a href="${homelink}contactus">
                                    <span><spring:theme code="header.help" /></span>
                                </a>
                            </ycommerce:testId>
                        </c:if>
                    </li>
                </ul>
            </div>
        </div>
        <!-- Top Right header Ends -->
        <nav id="header-store-links" aria-label="headerLinks" class="container-lg navigation navigation--top">
            <div class="row flex-center display-block-xs display-block-sm p-y-15-xs p-y-15-sm ${!simpleHeader? 'margin0' : ''}">
                <c:if test="${!simpleHeader}">
                    <div data-global-linkname="categories" class="col-md-1 col-lg-1 m-l-15 hidden-xs hidden-sm pointer transition-3s flex-center justify-center js-document-global-btn width-auto global-category-section" onclick="ACC.global.categorySlide(this, '.new-vertical-categories', '.global-category-section', 500)">
                        <span class="m-r-10 ham-icon-set">
                            <span class="bg-white ham-icon ham-icon-1 transition-3s"></span>
                            <span class="bg-white ham-icon ham-icon-2 transition-3s"></span>
                            <span class="bg-white ham-icon ham-icon-3 transition-3s"></span>
                        </span>
                        <span class="text-capitalize f-s-18 font-Geogrotesque text-white hidden-xs hidden-sm"><spring:theme code="verticalBarComponent.Categories" /></span>
                    </div>
                    <div class="col-xs-2 col-sm-2 p-t-10 pointer transition-3s hidden-md hidden-lg js-document-global-btn global-menu-btn" onclick="ACC.global.mobMenuSlide(this, '.global-menu-section', '.global-menu-btn', 500)">
                        <div class="flex-center">
                            <span class="m-r-10 m-r-0-xs-imp m-r-0-sm-imp ham-icon-set">
                                <span class="bg-white ham-icon ham-icon-1 transition-3s"></span>
                                <span class="bg-white ham-icon ham-icon-2 transition-3s"></span>
                                <span class="bg-white ham-icon ham-icon-3 transition-3s"></span>
                            </span>
                            <span class="text-capitalize f-s-18 font-Geogrotesque text-white hidden-xs hidden-sm"><spring:theme code="verticalBarComponent.Categories" /></span>
                        </div>
                    </div>
                </c:if>
                 <div class="${logoWidth} p-l-35 p-l-0-xs p-l-0-sm text-align-center">
                    <div class="nav__left js-site-logo l-h-10 ${simpleHeader ? 'text-align-left' : ''}">
                        <a data-global-linkname="siteone logo" href="${homelink}">
                            <span class="sr-only">SiteOne</span>
                            <common:headerIcon iconName="logoTM" iconFill="none" iconColor="#78A22F" iconColorSecond="#fff" width="162" height="46" display="hidden-xs hidden-sm" viewBox="0 0 162 46" />
                            <common:headerIcon iconName="logoTM" iconFill="none" iconColor="#78A22F" iconColorSecond="#fff" width="125" height="35" display="hidden-md hidden-lg" viewBox="0 0 162 46" />
                        </a>
                    </div>
                </div>
                <c:if test="${cmsPage.uid eq 'choosePickupDeliveryMethodPage' || cmsPage.uid eq 'siteOneCheckoutPage' || cmsPage.uid eq 'orderSummaryPage' || cmsPage.uid eq 'multiStepCheckoutSummaryPage'}">
                    <div class="col-sm-2 col-md-2 col-lg-2 mobile-checkout">
                        <a href="${homelink}cart" class=" btn header-return-cart-back" id="checkout-cart-link">
                        <spring:theme code="header.returnToCart" /></a>
                    </div>
                </c:if>
                <c:if test="${cmsPage.uid eq 'orderConfirmationPage' || cmsPage.uid eq 'linkConfirmationPage' || cmsPage.uid eq 'linktopaypayment'}">
                    <div class="col-sm-2 col-md-2 col-lg-2 mobile-checkout p-r-40-xs ${backtoLink}">
                        <a href="${homelink}" class="btn header-return-cart-back">
                            <spring:theme code="checkout.multi.navigate.home.page${currentBaseStoreId eq 'siteoneCA' ? '.ca' : ''}" />
                        </a>
                    </div>
                </c:if>
                <c:if test="${cmsPage.uid eq 'linktopayverification'}">
                    <div class="col-sm-4 col-md-2 col-lg-1 mobile-checkout ${backtoLink} linktopayheader no-padding-rgt-xs">
                        <a href="${homelink}" class="btn header-return-cart-back ">
                            <spring:theme code="checkout.multi.navigate.home.page" />
                        </a>
                    </div>
                </c:if>
                <c:if test="${cmsPage.uid eq 'requestAccountOnlineAccess' || cmsPage.uid eq 'requestaccount'}">
                    <div class="col-sm-2 col-md-2 col-lg-1 mobile-checkout">
                        <a href="${homelink}" class="back-to-siteone-link">
                            <span id="multiNavigateHomePage1">
                                <spring:theme code="${currentBaseStoreId eq 'siteone' ? 'requestaccount.back.to.siteone.com' : 'requestaccount.back.to.siteone.ca' }" />
                            </span>
                            <span id="multiNavigateHomePage2" class="hidden">
                                <spring:theme code="${currentBaseStoreId eq 'siteone' ? 'requestaccount.shop.siteone.com' : 'requestaccount.shop.siteone.ca' }" />
                            </span>
                        </a>
                    </div>
                </c:if>
                <c:if test="${!simpleHeader}">
                    <div class="col-xs-3 col-sm-2 p-l-0 text-align-right hidden-md hidden-lg">
                        <button title="Cart" data-global-linkname="view cart" type="button" class="btn pos-relative-xs pos-relative-sm global-cart-btn" onclick="loading.start(); window.location.href='${homelink}cart';">
                            <common:headerIcon iconName="carticon-mob" iconColor="#ffffff" iconColorSecond="#ffffff" iconFill="none" width="16" height="16" viewBox="0 0 16 16" display="global-carticon-mob" />
                            <c:if test="${empty hideHeaderLinks}">
                                <cms:pageSlot position="MiniCart" var="cart" element="span" class="componentContainer text-align-right">
                                    <cms:component component="${cart}" element="span" />
                                </cms:pageSlot>
                            </c:if>
                        </button>
                    </div>
                    <div class="col-xs-12 col-sm-12 col-md-3 col-lg-5 p-l-20 p-r-35 p-r-15-sm p-r-15-xs m-l-0-xs m-t-15-xs m-t-15-sm global-search-section">
                        <nav id="header-product-links" aria-label="productNavigation" class="navigation navigation--middle js-navigation--middle">
                            <c:choose>
                                <c:when test="${cmsPage.uid eq 'choosePickupDeliveryMethodPage' || cmsPage.uid eq 'siteOneCheckoutPage' || cmsPage.uid eq 'orderSummaryPage' || cmsPage.uid eq 'multiStepCheckoutSummaryPage' || cmsPage.uid eq 'orderConfirmationPage' || cmsPage.uid eq 'linktopayverification' || cmsPage.uid eq 'linkConfirmationPage' || cmsPage.uid eq 'linktopaypayment'}">
                                    <div class="nav__left col-xs-12 col-md-12 hidden-sm">
                                </c:when>
                                <c:otherwise>
                                    <div class="nav__left col-xs-12 col-md-12 padding0">
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${cmsPage.uid ne 'choosePickupDeliveryMethodPage' && cmsPage.uid ne 'siteOneCheckoutPage' && cmsPage.uid ne 'orderSummaryPage' && cmsPage.uid ne 'multiStepCheckoutSummaryPage' && cmsPage.uid ne 'orderConfirmationPage' && cmsPage.uid ne 'linktopayverification' && cmsPage.uid ne 'linkConfirmationPage' && cmsPage.uid ne 'linktopaypayment'}">
                                <div class="site-search ">
                                    <cms:pageSlot position="SearchBox" var="component">
                                        <cms:component component="${component}" element="div" />
                                    </cms:pageSlot>
                                    <span class="errorMessage text-white m-l-15-xs" hidden>
                                        <spring:theme code="search.emptySearchBox.errorMessage" />
                                    </span>
                                </div>
                            </c:if>
                            <!-- opening tags are in c choose conditions, don't delete below warnings -->
                        </nav>
                        <div class="nav__right hidden-xs hidden-sm">
                            <span class="js-mobile-links hidden-md hidden-lg">
                                <a href="#" class="notransform changebranchpopup-mobile">
                                    <spring:theme code="headerstoreoverlay.changeBranch" />
                                </a>
                            </span>
                        </div>
                        <div class="nav__right hidden-xs hidden-sm">
                            <span class="js-mobile-links">
                                <a href="${homelink}contactus" class="notransform hidden-md hidden-lg"><spring:theme code="header.help" /></a>
                            </span>
                        </div>
                    </div>
                    <sec:authorize access="hasAnyRole('ROLE_ANONYMOUS')">
                        <div class="col-sm-12 col-xs-12 hidden-lg hidden-md p-l-20-sm p-t-20-sm p-t-5-sm p-t-5-xs">
                            <button class="btn btn-primary btn-block m-b-0-xs-imp m-b-0-sm-imp signIn-btn signInOverlay${cmsPage.uid eq 'cartPage' && not empty cartData.entries? 'Checkout_guest' : ''}">
                                <spring:theme code="header.link.signin" />
                            </button>
                        </div>
                    </sec:authorize>
                    <div class="after-overlay"></div>
                    <div class="col-xs-5 col-sm-5 col-md-5 col-lg-4 text-align-right padding0 width-auto global-menu-section">
                        <div class="row flex-center hidden-md hidden-lg">
                            <div class="col-xs-9 col-sm-10 font-Geogrotesque p-l-15 p-l-30 p-t-10 text-align-left">
                                <c:if test="${loggedIn}">
                                    <ycommerce:testId code="header_LoggedUser">
                                        <c:set var="firstName" value="${fn:replace(user.firstName, '%20', ' ')}" />
                                        <c:set var="lastName" value="${fn:replace(user.lastName, '%20', ' ')}" />
                                        <p class="f-s-18 m-b-0 text-default">${firstName}&nbsp;${lastName}</p>
                                    </ycommerce:testId>
                                    <p title="${sessionShipTo.name}" class="f-s-16 f-w-500 l-h-12 m-b-15 text-dark-gray">${sessionShipTo.name}</p>
                                </c:if>
                            </div>
                            <div class="col-xs-3 col-sm-2 p-t-5">
                                <button class="btn btn-link js-document-global-btn" onclick="ACC.global.mobMenuSlide(this, '.global-menu-section', '.global-menu-btn', 500)"><common:headerIcon iconName="close" iconFill="none" iconColor="#77A12E" iconColorSecond="#cccccc" width="16" height="16" display="" viewBox="0 0 16 16" /></button>
                            </div>
                        </div>
                        <div class="btn-group" role="group" aria-label="...">
                            <button title="<spring:theme code="verticalBarComponent.Categories" />" data-global-linkname="categories" type="button" class="btn btn-dark-gray flex-center justify-flex-center-xs justify-flex-center-sm transition-3s f-s-15-xs-px f-s-15-sm-px hidden-md hidden-lg js-document-global-btn global-categories-btn" onclick="ACC.global.toggleOffElems(this, '.global-categories-box' , 'active' , '.global-popup-box')">
                                <spring:theme code="verticalBarComponent.Categories" />
                            </button>
                            <button title="<spring:theme code="global.header.resources" />" data-global-linkname="resources" type="button" class="btn btn-dark-gray flex-center justify-flex-center-xs justify-flex-center-sm transition-3s js-document-global-btn global-resources-btn" onclick="ACC.global.toggleOffElems(this, '.global-resources-box' , 'active' , '.global-popup-box')">
                                <common:headerIcon iconName="wrench" iconFill="none" iconColor="#f1f6f9" iconColorSecond="#cccccc" width="15" height="16" display="m-r-5 hidden-xs hidden-sm" viewBox="0 0 15 16" />
                                <span class="m-r-5 font-size-14 f-s-15-xs-px f-s-15-sm-px"><spring:theme code="global.header.resources" /></span>
                                <span class="glyphicon glyphicon-chevron-down f-s-6 hidden-xs hidden-sm"></span>
                            </button>
                            <button title="<spring:theme code="header.lists" />" data-global-linkname="lists" type="button" class="btn btn-dark-gray flex-center justify-flex-center-xs justify-flex-center-sm transition-3s visible-sm visible-xs visible-md-lg-flex js-document-global-btn global-savedlist-btn ${loggedIn? '' : 'signInOverlay'}" onclick="ACC.global.toggleOffElems(this, '.global-savedlist-box' , 'active' , '.global-popup-box')">
                                <common:headerIcon iconName="list" iconFill="none" iconColor="#f1f6f9" iconColorSecond="#cccccc" width="16" height="16" display="m-r-5 hidden-xs hidden-sm" viewBox="0 0 24 24" />
                                <span class="m-r-5 font-size-14 f-s-15-xs-px f-s-15-sm-px"><spring:theme code="header.lists" /></span>
                                <span class="glyphicon glyphicon-chevron-down f-s-6 hidden-xs hidden-sm"></span>
                            </button>
                            <button title="<spring:theme code="text.account.account" />" data-global-linkname="my account" type="button" class="btn btn-dark-gray flex-center justify-flex-center-xs justify-flex-center-sm transition-3s js-document-global-btn global-myaccount-btn signIn-btn ${loggedIn? '' : 'signInOverlay'}" onclick="ACC.global.toggleOffElems(this, '.global-myaccount-box' , 'active' , '.global-popup-box')">
                                <common:headerIcon iconName="user" iconFill="none" iconColor="#f1f6f9" iconColorSecond="#cccccc" width="16" height="16" display="m-r-5 hidden-xs hidden-sm" viewBox="0 0 24 25" />
                                <span class="m-r-5 font-size-14 f-s-15-xs-px f-s-15-sm-px">
                                    <c:if test="${loggedIn}">
                                        <spring:theme code="text.account.account" />
                                    </c:if>
                                    <c:if test="${!loggedIn}">
                                        <spring:theme code="login.login" />
                                    </c:if>
                                </span>
                                <span class="glyphicon glyphicon-chevron-down f-s-6 hidden-xs hidden-sm"></span>
                                <span class="${homelink}">${loggedIn || homelink == '/es/' ? '' : '&nbsp;'}</span>
                            </button>
                            <button title="Cart" data-global-linkname="view cart" type="button" class="btn btn-primary flex-center transition-3s hidden-xs hidden-sm global-cart-btn" onclick="loading.start(); window.location.href='${homelink}cart';">
                                <common:headerIcon iconName="carticon" iconColor="#ffffff" iconColorSecond="#ffffff" iconFill="none" width="17" height="17" viewBox="0 0 20 20" display="m-r-5" />
                                    <c:if test="${empty hideHeaderLinks}">
                                    <cms:pageSlot position="MiniCart" var="cart" element="span" class="componentContainer">
                                        <cms:component component="${cart}" element="span" />
                                    </cms:pageSlot>
                                </c:if>
                            </button>
                            <div class="row text-align-left p-y-20 p-t-0-xs-imp p-t-0-sm-imp f-s-15 font-Geogrotesque js-document-global-box global-popup-box global-resources-box">
                                <div class="col-md-12 p-l-0-xs p-r-0-xs p-l-0-sm p-r-0-sm">
                                    <div class="list-group margin0">
                                        <a data-global-linkname="resources: project calculators" href="${homelink}projectcalculators" class="list-group-item flex-center transition-3s"><div class="l-h-20 icon-box m-r-10"><common:headerIcon iconName="calculator" iconFill="none" iconColor="#77a12e" iconColorSecond="#77a12e" width="16" height="22" display="valign-m" viewBox="0 0 17 23" /></div><spring:theme code="global.header.calculator" /></a>
                                        <a data-global-linkname="resources: learn" href="${homelink}articles" class="list-group-item flex-center transition-3s"><div class="l-h-20 icon-box m-r-10"><common:headerIcon iconName="learn" iconFill="none" iconColor="#77a12e" iconColorSecond="#77a12e" width="22" height="16" display="valign-m" viewBox="0 0 24 18" /></div><spring:theme code="global.header.learn" /></a> 
                                        <a data-global-linkname="resources: promotions" href="${homelink}Promotions" class="list-group-item flex-center transition-3s"><div class="l-h-20 icon-box m-r-10"><common:headerIcon iconName="promotions" iconFill="none" iconColor="#77a12e" iconColorSecond="#77a12e" width="22" height="14" display="valign-m" viewBox="0 0 25 16" /></div><spring:theme code="global.header.promotion" /></a>
                                        <a data-global-linkname="resources: catalogs" href="${homelink}catalogs" class="list-group-item flex-center transition-3s"><div class="l-h-20 icon-box m-r-10"><common:headerIcon iconName="catalogs" iconFill="none" iconColor="#78A22F" iconColorSecond="#78A22F" width="22" height="16" display="valign-m" viewBox="0 0 22 15" /></div><spring:theme code="global.header.catalogs" /></a>
                                        <a data-global-linkname="resources: partners program" href="${homelink}Partners" class="list-group-item flex-center transition-3s ${currentBaseStoreId eq 'siteoneCA' ? 'hidden' : ''}"><div class="l-h-20 icon-box m-r-10"> <common:headerIcon iconName="partnersprogram" iconFill="none" iconColor="#77A12E" iconColorSecond="#cccccc" width="22" height="17" display="valign-m" viewBox="0 0 28 23" /></div><spring:theme code="global.header.partnersprogram" /></a>
                                        <a data-global-linkname="resources: events" href="${homelink}events" class="list-group-item flex-center transition-3s"><div class="l-h-20 icon-box m-r-10"><common:headerIcon iconName="events" iconFill="none" iconColor="#77a12e" iconColorSecond="#77a12e" width="21" height="22" display="valign-m" viewBox="0 0 22 23" /></div><spring:theme code="global.header.events" /></a>
                                        <a data-global-linkname="resources: credit resources" href="${homelink}openlineofcredit" class="list-group-item flex-center transition-3s"><div class="l-h-20 icon-box m-r-10"><common:headerIcon iconName="credit-resources" iconFill="none" iconColor="#77a12e" iconColorSecond="#77a12e" width="22" height="17" display="valign-m" viewBox="0 0 23 18" /></div><spring:theme code="global.header.creditresources" /></a>
                                        <a data-global-linkname="resources: turf maintenance" href="${homelink}turfplans" class="list-group-item flex-center transition-3s"><div class="l-h-20 icon-box m-r-10"><common:headerIcon iconName="turf-maintenance" iconFill="none" iconColor="#77a12e" iconColorSecond="#77a12e" width="20" height="20" display="valign-m" viewBox="0 0 20 20" /></div><spring:theme code="global.header.turfmaintenance" /></a>
                                        <a data-global-linkname="resources: safety sheet" href="${homelink}sdssearch/results" class="list-group-item flex-center transition-3s"><div class="l-h-20 icon-box m-r-10"><common:headerIcon iconName="safety-sheet" iconFill="none" iconColor="#77a12e" iconColorSecond="#77a12e" width="20" height="22" display="valign-m" viewBox="0 0 21 23" /></div><spring:theme code="global.header.safetysheet" /></a>
                                        <a data-global-linkname="resources: spreader sprayer" href="${homelink}spreadersprayer" class="list-group-item flex-center transition-3s"><div class="l-h-20 icon-box m-r-10"><common:headerIcon iconName="spreader-sprayer" iconFill="none" iconColor="#77a12e" iconColorSecond="#77a12e" width="22" height="22" display="valign-m" viewBox="0 0 22 22" /></div><spring:theme code="global.header.spreadersprayer" /></a>
                                        <a data-global-linkname="resources: mobile app" href="${homelink}mobile" class="list-group-item flex-center transition-3s ${currentBaseStoreId eq 'siteoneCA' ? 'hidden' : ''}"><div class="l-h-20 icon-box m-r-10"><common:headerIcon iconName="mobile-app" iconFill="none" iconColor="#77a12e" iconColorSecond="#77a12e" width="15" height="22" display="valign-m" viewBox="0 0 16 23" /></div><spring:theme code="global.header.mobileapp" /></a>
                                        <a data-global-linkname="resources: business management" href="${homelink}bms" class="list-group-item flex-center transition-3s"><div class="l-h-20 icon-box m-r-10"><common:headerIcon iconName="business-management" iconFill="none" iconColor="#78A22F" iconColorSecond="#78A22F" width="18" height="22" display="valign-m" viewBox="0 0 18 22" /></div><spring:theme code="global.header.businessmanagement" /></a>
                                    </div>
                                </div>
                            </div>
                            <sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')">
                            <div class="row text-align-left p-y-20 p-t-15-xs p-t-15-sm js-document-global-box global-popup-box global-savedlist-box">
                                <div class="col-xs-12 col-sm-12 col-md-6 p-l-0 p-r-5">
                                    <div class="list-group m-b-0 global-savedlist-filled">
                                        <h6 class="b-b-grey-sm b-b-grey-xs f-s-17 font-GeogrotesqueSemiBold list-group-item-heading m-b-0 padding-bottom-15 p-l-20 p-l-15-xs p-l-15-sm p-r-15-xs p-r-15-sm text-default"><span class="hidden-sm hidden-xs"><spring:theme code="global.header.mostrecent" /></span><span class="bold display-ib hidden-lg hidden-md p-t-5">Recent Lists</span><a data-global-linkname="lists: most recent: see all" href="${homelink}savedList" class="btn btn-primary btn-small hidden-lg hidden-md m-b-5-xs p-b-0-xs-imp p-t-0-imp p-b-0-sm-imp pull-right select-all transition-3s"><span class="bold font-size-14"><spring:theme code="global.header.seeall" /></span></a></h6>
                                        <div class="text-capitalize list-group-item-savedlist"></div>
                                        <a data-global-linkname="lists: most recent: see all" href="${homelink}savedList" class="list-group-item transition-3s select-all hidden-sm hidden-xs"><spring:theme code="global.header.seeall" /></a>
                                    </div>
                                    <div class="global-savedlist-empty b-b-grey-xs b-b-grey-sm p-l-20 p-x-15-xs p-x-15-sm hidden">
                                        <h6 class="font-GeogrotesqueSemiBold margin0 padding-bottom-15 f-s-17 text-default"><spring:theme code="global.header.mostrecent" /></h6>
                                        <p class="f-s-14">Save your favorite items to easily reorder, request quotes, and create customer facing estimates.</p>
                                    </div>
                                    <div class="global-all-empty p-l-20 p-x-15-xs p-x-15-sm hidden">
                                        <h6 class="font-GeogrotesqueSemiBold margin0 padding-bottom-15 f-s-22 text-default">Business is Easier with Lists.</h6>
                                        <p class="f-s-14 f-s-15-xs-px f-s-15-sm-px text-gray-1 m-b-35">It looks like you don't have any lists yet. Save your favorite items to easily reorder, request quotes, and create customer facing estimates.</p>
                                        <h6 class="font-GeogrotesqueSemiBold margin0 padding-bottom-15 f-s-22 text-default">Utilize Recommended Lists</h6>
                                        <p class="f-s-14 f-s-15-xs-px f-s-15-sm-px text-gray-1">Recommended Lists are personalized shopping lists which are automatically updated with your recent buys. Copy them to a list or add items directly to your cart.</p>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-6 p-l-0 p-r-5 p-t-10-xs p-t-10-sm p-r-0-sm">
                                    <div class="list-group m-b-0 global-recommended-filled">
                                        <h6 class="b-b-grey-sm b-b-grey-xs f-s-17 font-GeogrotesqueSemiBold list-group-item-heading m-b-0 padding-bottom-15 p-l-20 p-l-15-xs p-l-15-sm p-r-15-xs p-r-15-sm f-s-17-xs-px f-s-17-sm-px text-default"><span class="hidden-sm hidden-xs"><spring:theme code="global.header.recommended" /></span><span class="bold display-ib hidden-lg hidden-md p-t-5"><spring:theme code="global.header.recommended" /></span><a data-global-linkname="lists: recommended: see all" href="${homelink}savedList/recommendedList" class="btn btn-primary btn-small hidden-lg hidden-md m-b-5-xs p-b-0-xs-imp p-t-0-imp p-b-0-sm-imp pull-right select-all transition-3s"><span class="bold font-size-14"><spring:theme code="global.header.seeall" /></span></a></h6>
                                        <div class="text-capitalize list-group-item-recommended"></div>
                                        <a data-global-linkname="lists: recommended: see all" href="${homelink}savedList/recommendedList" class="list-group-item transition-3s select-all hidden-sm hidden-xs"><spring:theme code="global.header.seeall" /></a>
                                    </div>
                                    <div class="global-recommended-empty b-b-grey-xs b-b-grey-sm p-l-20 p-x-15-xs p-x-15-sm hidden">
                                        <h6 class="font-GeogrotesqueSemiBold margin0 padding-bottom-15 f-s-17 text-default"><spring:theme code="global.header.recommended" /></h6>
                                        <p class="f-s-14">Start shopping to enable recommended lists.</p>
                                    </div>
                                </div>
                                <form id="siteoneSavedListHeaderCreateForm" action="${homelink}savedList/createList" method="post" enctype="multipart/form-data" data-gtm-form-interact-id="0" class="bg-white b-t-grey col-md-12 col-sm-12 col-xs-12 z-i-2 p-t-20 p-x-20 p-t-15-xs p-x-15-xs p-t-15-sm p-x-15-sm">
                                    <div class="flex-center-xs flex-dir-column-xs flex-center-sm flex-dir-column-sm input-group">
                                        <input type="text" id="name" name="name" class="form-control transition-3s listName headerListName" placeholder="<spring:theme code="global.header.createanew" />"  value="" maxlength="200" aria-describedby="basic-addon1">
                                        <span data-global-linkname="lists: create new" class="btn btn-primary create-list-button-wrapper input-group-addon m-t-10-xs p-y-15-xs  display-ib-xs f-s-12-xs-px m-t-10-sm p-y-15-sm display-ib-sm transition-3s" id="createList">+ <spring:theme code="global.header.createnew" /></span>
                                    </div>
                                    <div id="nameError" class="create-list-error bg-danger"></div>
                                    <input type="hidden" name="CSRFToken" value="${CSRFToken.token}">
                                </form>
                            </div>
                            <div class="row text-align-left js-document-global-box global-popup-box global-myaccount-box">
                                <div class="col-md-6 col-xs-12 col-sm-12 p-y-20 b-b-grey-xs b-b-grey-sm p-t-0-xs p-b-5-xs bg-light-offgrey-xs bg-light-offgrey-sm">
                                    <div class="row">
                                        <div class="col-md-12 col-xs-12 b-b-grey hidden-xs hidden-sm">
                                            <div class="list-group">
                                                <h5 class="f-s-16 f-w-500 font-Geogrotesque list-group-item-heading p-l-5 text-default">${user.firstName}&nbsp; ${user.lastName}</h5>
                                                <div class="f-s-13 l-h-16 list-group-item">
                                                    <span class="font-Geogrotesque text-dark-gray">${sessionShipTo.name}</span>
                                                    <ycommerce:testId code="header_signOut">
                                                        <a data-global-linkname="my account: sign out" class="display-block m-b-10 p-t-10 f-s-12 logout" href="#"><spring:theme code="global.header.signout" /></a>
                                                    </ycommerce:testId>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-12 col-xs-12 p-b-20 p-x-10-xs p-x-10-sm s p-b-0-sm p-b-0-xs p-t-10">
                                            <div class="list-group">
                                                <div class="row list-group-item flex-center-xs flex-center-sm bg-light-offgrey-xs bg-light-offgrey-sm">
                                                    <div class="col-xs-8 col-md-12 p-l-0">
                                                        <p class="f-s-10 text-gray bold text-uppercase m-b-0-xs m-b-0-sm"><spring:theme code="global.header.yourshopping" /></p>
                                                        <p class="f-s-13 f-s-16-sm-px f-s-16-xs-px f-w-500 font-Geogrotesque m-b-0 text-default">${sessionStore.name}</p>
                                                    </div>
                                                    <div class="col-xs-4 p-r-0 text-align-right">
                                                        <button class="btn btn-primary btn-small hidden-lg hidden-md m-b-5-xs p-b-0-xs-imp p-t-0-imp m-b-0-xs-imp m-b-0-sm-imp p-b-0-sm-imp pull-right branchpopup_click transition-3s"><span class="bold font-size-14">Change</span></button>
                                                    </div>
                                                </div>
                                                <div class="list-group-item mob-store-details-block bg-light-offgrey-xs bg-light-offgrey-sm">
                                                    <p class="f-s-12 l-h-16 text-dark-gray">
                                                        ${sessionStore.address.line1}
                                                        ${sessionStore.address.line2}</br>
                                                        ${sessionStore.address.town},&nbsp;${sessionStore.address.region.isocodeShort}&nbsp;${sessionStore.address.postalCode}
                                                    </p>
                                                    <a class="display-block f-s-12 m-b-5" href="phone:${sessionStore.address.phone}">${sessionStore.address.phone}</a>
                                                    <p class="f-s-12 l-h-16 text-dark-gray">${sessionStore.storeStatus}</p>
                                                    <a data-global-linkname="my account: store details" class="p-b-10 p-b-0-xs p-b-0-sm f-s-12 display-block" href='${homelink}store/${sessionStore.storeId}'><spring:theme code="global.header.storeDetails" /></a>
                                                    <a data-global-linkname="my account: change branch" class="display-block f-s-12 hidden-xs hidden-sm" href='javascript:$("#js-mobile-flyoutlink").trigger("click")'><spring:theme code="global.header.changeStore" /></a>
                                                </div>
                                                <div class="list-group-item p-t-0-xs-imp p-t-0-sm-imp bg-light-offgrey-xs bg-light-offgrey-sm hidden-md hidden-lg">
                                                    <button class="btn btn-link btn-small flex-center padding-zero mob-store-details-btn-more" onclick="ACC.global.toggleGlobalElems(this, '.mob-store-details-block, .mob-store-details-btn-less')"><span class="f-s-14 text-blue bold">More</span><span class="glyphicon glyphicon-chevron-down text-blue p-l-5 f-s-10"></span></button>
                                                    <button class="btn btn-link btn-small flex-center padding-zero mob-store-details-btn-less" onclick="ACC.global.toggleGlobalElems(this, '.mob-store-details-block','.mob-store-details-btn-more')" style="display:none;"><span class="f-s-14 text-blue bold">Less</span><span class="glyphicon glyphicon-chevron-up text-blue p-l-5 f-s-10"></span></button>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-xs-12 p-x-10 m-t-5 hidden-md hidden-lg">
                                            <div class="list-group p-t-5 b-t-grey">
                                                <div class="row list-group-item flex-center-xs flex-center-sm bg-light-offgrey-xs bg-light-offgrey-sm">
                                                    <div class="col-xs-8 p-l-0">
                                                        <p class="f-s-10 text-gray bold text-uppercase m-b-0-xs m-b-0-sm"><spring:theme code="global.header.shipto" /></p>
                                                        <p class="f-s-13 f-s-16-xs-px f-s-16-sm-px m-b-0 font-Geogrotesque bold">${sessionShipTo.name}</p>
                                                    </div>
                                                    <div class="col-xs-4 p-r-0 text-align-right ${hasShipTo gt 1 ? '' : 'hidden'}">
                                                        <button class="btn btn-primary btn-small m-b-5-xs p-b-0-xs-imp p-t-0-imp pull-right shipToPopup transition-3s m-b-0-xs-imp m-b-0-sm-imp p-b-0-sm-imp" onclick="ACC.accountdashboard.showShipTos(this)"><span class="bold font-size-14">Change</span></button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6 col-xs-12 col-sm-12 p-t-5 b-l-grey b-l-0-xs b-l-0-sm p-l-0-xs p-r-0-xs p-l-0-sm p-r-0-sm">
                                    <div class="list-group f-s-15 font-Geogrotesque account-list-group">
                                        <a data-global-linkname="my account: dashboard" href="${homelink}my-account/account-dashboard" class="list-group-item flex-center transition-3s"><div class="l-h-20 icon-box m-r-10"><common:headerIcon iconName="user" iconFill="none" iconColor="#77A12E" iconColorSecond="#cccccc" width="21" height="22" display="valign-m" viewBox="0 0 24 25" /></div><spring:theme code="global.header.user" /></a>
                                        <c:if test="${contPayBillOnline && acctPayBillOnline}">
                                            <a data-global-linkname="my account: pay bill" href="${homelink}my-account/pay-account-online" class="list-group-item flex-center transition-3s account-invoice" target="redirectToBT"><div class="l-h-20 icon-box m-r-10"><common:headerIcon iconName="paybill" iconFill="none" iconColor="#77A12E" iconColorSecond="#cccccc" width="23" height="17" display="valign-m" viewBox="0 0 28 21" /></div><spring:theme code="global.header.paybill" /></a>
                                        </c:if>
                                        <a data-global-linkname="my account: orders" href="${homelink}my-account/orders/${sessionShipTo.uid}" data-key="orderhistorypage" data-active="orderHistoryTab" class="list-group-item flex-center transition-3s account-pageid"><div class="l-h-20 icon-box m-r-10"><common:headerIcon iconName="order" iconFill="none" iconColor="#77A12E" iconColorSecond="#cccccc" width="20" height="22" display="valign-m" viewBox="0 0 23 24" /></div><spring:theme code="global.header.order" /></a>
                                        <c:if test="${InvoicePermission}">
                                            <a data-global-linkname="my account: invoices" href="${homelink}my-account/invoices/${sessionShipTo.uid}" data-key="invoicespage" data-active="invoicesTab" class="list-group-item flex-center transition-3s account-pageid"><div class="l-h-20 icon-box m-r-10"><common:headerIcon iconName="invoices" iconFill="none" iconColor="#77A12E" iconColorSecond="#cccccc" width="18" height="22" display="valign-m" viewBox="0 0 22 26" /></div><spring:theme code="global.header.invoices" /></a>
                                        </c:if>
                                        <a data-global-linkname="my account: buy again" href="${homelink}my-account/buy-again/${sessionShipTo.uid}" data-key="purchasedproducts" data-active="purchasedOrderTab" class="list-group-item flex-center transition-3s account-pageid"><div class="l-h-20 icon-box m-r-10"><common:headerIcon iconName="buyagain" iconFill="none" iconColor="#77A12E" iconColorSecond="#cccccc" width="22" height="18" display="valign-m" viewBox="0 0 29 24" /></div><spring:theme code="global.header.buyagain" /></a>
                                        <!-- isProjectServicesEnabled: ${isProjectServicesEnabled} -->
                                        <!-- isProjectServicesEnabledForB2BUnit: ${isProjectServicesEnabledForB2BUnit} -->
                                        <c:choose>
                                            <c:when test="${not empty isProjectServicesEnabled && isProjectServicesEnabled ne null && not empty isProjectServicesEnabledForB2BUnit && isProjectServicesEnabledForB2BUnit ne null && isProjectServicesEnabled eq true && isProjectServicesEnabledForB2BUnit eq true}">
                                                <a data-global-linkname="my account: project services" href="${homelink}projectservices/dashboard" class="list-group-item flex-center transition-3s"><div class="l-h-20 icon-box m-r-10"><common:headerIcon iconName="services" iconFill="none" iconColor="#77A12E" iconColorSecond="#cccccc" width="22" height="22" display="valign-m" viewBox="0 0 26 26" /></div><spring:theme code="global.header.services" /></a>
                                            </c:when>
                                            <c:when test="${not empty projectServicesUrl && projectServicesUrl ne null && projectServicesUrl != ''}">
                                                <a data-global-linkname="my account: project services" href="${projectServicesUrl}" target="_blank" class="list-group-item flex-center transition-3s"><div class="l-h-20 icon-box m-r-10"><common:headerIcon iconName="services" iconFill="none" iconColor="#77A12E" iconColorSecond="#cccccc" width="22" height="22" display="valign-m" viewBox="0 0 26 26" /></div><spring:theme code="global.header.services" /></a>
                                            </c:when>
                                            <c:otherwise>
                                                <!-- project Services otherwise -->
                                            </c:otherwise>
                                        </c:choose>
                                        <c:if test="${quotesFeatureSwitch eq true}">
                                            <a data-global-linkname="my account: quotes" href="${homelink}my-account/my-quotes" class="list-group-item flex-center transition-3s"><div class="l-h-20 icon-box m-r-10"><common:headerIcon iconName="quotes" iconFill="none" iconColor="#77A12E" iconColorSecond="#cccccc" width="18" height="22" display="valign-m" viewBox="0 0 20 24" /></div><spring:theme code="global.header.quotes" /></a>
                                        </c:if>
                                        <a data-global-linkname="my account: partners program" href="${homelink}my-account/accountPartnerProgram" class="list-group-item flex-center transition-3s"><div class="l-h-20 icon-box m-r-10"><common:headerIcon iconName="partnersprogram" iconFill="none" iconColor="#77A12E" iconColorSecond="#cccccc" width="22" height="17" display="valign-m" viewBox="0 0 28 23" /></div><spring:theme code="global.header.partnersprogram" /></a>
                                        <a data-global-linkname="my account: lists" href="${homelink}savedList" class="list-group-item flex-center transition-3s"><div class="l-h-20 icon-box m-r-10"><common:headerIcon iconName="list" iconFill="none" iconColor="#77A12E" iconColorSecond="#cccccc" width="22" height="22" display="valign-m" viewBox="0 0 24 24" /></div><spring:theme code="global.header.list" /></a>
                                        <a data-global-linkname="my account: estimates" href="${homelink}estimate" class="list-group-item flex-center transition-3s"><div class="l-h-20 icon-box m-r-10"><common:headerIcon iconName="estimate" iconFill="none" iconColor="#77A12E" iconColorSecond="#cccccc" width="22" height="19" display="valign-m" viewBox="0 0 28 25" /></div><spring:theme code="global.header.estimate" /></a>
                                        <ycommerce:testId code="header_signOut">
                                            <a data-global-linkname="my account: sgin out" href="#" class="list-group-item flex-center transition-3s hidden-md hidden-lg logout"><div class="l-h-20 icon-box m-r-10"><common:headerIcon iconName="signout" iconFill="none" iconColor="#77A12E" iconColorSecond="#cccccc" width="22" height="19" display="valign-m" viewBox="0 0 26 23" /></div><spring:theme code="global.header.signout" /></a>
                                        </ycommerce:testId>
                                    </div>
                                </div>
                            </div>
                            </sec:authorize>
                        </div>
                    </div>
                    <div class="hidden">
                        <c:if test="${empty hideHeaderLinks}">
                            <c:if test="${uiExperienceOverride}">
                                <li class="backToMobileLink">
                                    <a href="${homelink}_s/ui-experience?level=">
                                        <spring:theme code="text.backToMobileStore" />
                                    </a>
                                </li>
                            </c:if>
                            <sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')">
                                <c:set var="maxNumberChars" value="25" />
                                <c:if test="${fn:length(user.firstName) gt maxNumberChars}">
                                    <c:set target="${user}" property="firstName" value="${fn:substring(user.firstName, 0, maxNumberChars)}..." />
                                </c:if>
                                <input type="hidden" id="userfname" value="${user.firstName}">
                                <input type="hidden" id="isOrderingAccount" value="${isOrderingAccount}" />
                                <input type="hidden" id="sessionShipToId" value="${sessionShipTo.uid}" />
                            </sec:authorize>
                            <sec:authorize access="hasAnyRole('ROLE_ANONYMOUS')">
                                <ycommerce:testId code="header_Login_link">
                                    <div id="signinId" style="display: none">
                                        <common:signinoverlaynew loginError="${loginError}" errorMessage="${errorMessage}" remainingAttempts="${remainingAttempts}" />
                                    </div>
                                    <div id="signinId-hidecsp" style="display: none">
                                        <common:signinoverlaynew loginError="${loginError}" errorMessage="${errorMessage}" remainingAttempts="${remainingAttempts}" />
                                    </div>
                                    <div id="signinId-checkout" style="display: none">
                                        <common:signinoverlaynew loginError="${loginError}" errorMessage="${errorMessage}" remainingAttempts="${remainingAttempts}" guestCheckoutEnable="${isGuestCheckoutEnabled}" />
                                    </div>
                                </ycommerce:testId>
                            </sec:authorize>
                            <cms:pageSlot position="MiniCart" var="cart" element="div" class="componentContainer">
                                <cms:component component="${cart}" element="div" />
                            </cms:pageSlot>
                        </c:if>
                    </div>
                </c:if>
            </div>
        </nav>
        <!-- a hook for the my account links in desktop/wide desktop -->
        <div class="hidden-xs hidden-sm js-secondaryNavAccount collapse" id="accNavComponentDesktopOne">
            <ul class="nav__links"></ul>
        </div>
        <div class="hidden-xs hidden-sm js-secondaryNavCompany collapse" id="accNavComponentDesktopTwo">
            <ul class="nav__links js-nav__links"></ul>
        </div>
    </div>
    <div class="shipto-overlay overlay-gray z-i-1003"></div>
    <div class="categories-overlay overlay-gray z-i-1000" onclick="ACC.global.categorySlide(this, '.new-vertical-categories', '.global-category-section', 500)"></div>
    <nav class="new-vertical-categories global-categories-l3 global-categories-box js-document-global-box font-Geogrotesque">
        <ul class="L3CategoryHeader margin0 padding0 p-y-10 p-t-0-xs p-t-0-sm scroll-bar-5"></ul>
    </nav>
    <nav class="new-vertical-categories global-categories-l2 global-categories-box js-document-global-box font-Geogrotesque">
        <ul class="L2CategoryHeader margin0 padding0 p-y-10 p-t-0-xs p-t-0-sm scroll-bar-5"></ul>
    </nav>
    <nav class="js-document-shipto-box ship-to-box p-a-15 p-t-25"></nav>
	    <c:if test="${!simpleHeader}">
		<c:forEach items="${sessionNearbyStores}" var="nearbyStoreValue" varStatus="loop" step="1" begin="1">
	    <c:if test="${nearbyStoreValue.isNearbyStoreSelected}">
	         <c:set var="nearbyStoresToggle" value="${loop.first?'' : nearbyStoresToggle}${nearbyStoreValue.storeId}${loop.last?'' :','}" />
	    </c:if>
		</c:forEach>
		<input type="hidden" id="nearbyStoresToggle" value="${nearbyStoresToggle}"/>
		
		<input type="hidden" id="hardscapeFeatureSwitch" value="${hardscapeFeatureSwitch}"/>
	
        <nav class="new-vertical-categories nav-categories-ajx global-categories-box js-document-global-box font-Geogrotesque"></nav>
        <script id="navL1Cat" type="template">
		<li class="L1Categories">
		<button data-global-linkname="categories: {{name}}" onclick="ACC.global.categories(this, '{{code}}', '.L2CategoryHeader')" data-code="{{code}}" class="btn btn-block text-align-left L1CatLinks text-default f-w-500 f-s-18 b-b-grey transition-3s" data-href="{{url}}">{{name}}</button>
		</li>
		<li class="L1Categories-data hidden">{{L2Cat}}</li>
		</script>
		<script id="navL2Cat" type="template">
		<span class="L2CatLinks" data-{{L1code}}="{{code}}" data-href="{{url}}" data-children="{{havechildren}}">{{name}}</span>
		</script>
		<script id="navL3Cat" type="template">
		<span class="L3CatLinks" data-{{L2code}}="{{code}}" data-href="{{url}}">{{name}}</span>
		</script>
		<script id="navL2Images" type="template">
		<span class="L2Images" data-{{L2code}}="{{url}}" data-href="{{imageUrl}}" data-children="{{havechildren}}">{{name}}</span>
		</script>
    </c:if>
    <div class="cl"></div>
    <c:if test="${cmsPage.uid ne 'linktopayverification' && cmsPage.uid ne 'linkConfirmationPage' && cmsPage.uid ne 'linktopaypayment' && cmsPage.uid ne 'productGrid' && cmsPage.uid ne 'searchGrid' && cmsPage.uid ne 'search'}">
        <cms:pageSlot position="GlobalMessage" var="component" element="div" class="container-global bar-slot wlcme-text container-lg">
            <cms:component component="${component}" />
        </cms:pageSlot>
    </c:if>
    <a id="skiptonavigation"></a>
    <nav:topNavigation />
    <script>
        $(window).resize(function () {
            if ($('.search-box-zoominwidth').length && document.body.offsetWidth > 1000 && document.body.offsetWidth < 1100) {
                $('.search-box-zoominwidth').css('width', '385');
            }
        });
    </script>
</header>
<cms:pageSlot position="BottomHeaderSlot" var="component" element="div" class="container-fluid">
	<cms:component component="${component}" />
</cms:pageSlot>
<input type="hidden" id="pageId" value="${cmsPage.uid}" />
<c:choose>
	<c:when test="${empty cookie['gls'] && sessionStore.storeId eq '172'}">
		<c:set var="contactNo" value="<%=de.hybris.platform.util.Config.getParameter(\"siteOne.Customer.Service.Number\")%>"/>
	</c:when>
	<c:otherwise>
		<c:set var="contactNo" value="${sessionStore.address.phone}"/>
	</c:otherwise>
</c:choose>
<input type="hidden" id="sessionStorePhoneNumber" value="${contactNo}" />
<input type="hidden" id="cplpnumberitemcart" value="${numberOfItemsInCart}"/>
<input type="hidden" id="cplp-sessionstore" value="${sessionStore.storeId}">
<input type="hidden" id="cplp-storeid">
<c:set var="cplp-storeid" value=""/>
<div class="col-md-12 branch-selection" >
	<div class="row  branchselection-popup" >
		<div class="col-md-12 flex-center marginBottom20">
			<div><common:cplpexclamatoryIcon></common:cplpexclamatoryIcon></div>
			<div class="icon-cplp-text"><spring:theme code="curatedplp.cart.branch.message"/></div>
		</div>
		<div class="col-md-12 col-xs-12 cplp-branch-btn">
			<div class="col-md-6 col-xs-12 pad-xs-bot-15">
				<button class="col-md-6 col-xs-12 btn btn-primary btn-block "  onclick="ACC.product.cplpredirectUrlBranchlink()">
				<spring:theme code="curatedplp.branch.updated"/>
				</button>
			</div>
			<div class="col-md-6 col-xs-12 pad-xs-bot-15">
				<button class="col-md-6  col-xs-12 cplp-btn-popup cplp-homebranchnotupdated"><spring:theme code="curatedplp.no.branch.updated"/></button>
			</div>
		</div>
	</div>
</div>
