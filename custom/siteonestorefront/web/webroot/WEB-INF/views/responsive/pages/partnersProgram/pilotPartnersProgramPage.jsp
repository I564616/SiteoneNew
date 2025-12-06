<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="content" tagdir="/WEB-INF/tags/responsive/content" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<template:page pageTitle="${pageTitle}">
<spring:url value="/" var="homelink" htmlEscape="false"/>
<c:set var = "userAgent" value = "${fn:toLowerCase(header['User-Agent'])}"/>
<c:set var = "imgBanner" value = ""/>
<c:set var = "img2x" value = ""/>
<c:if test="${fn:contains(userAgent, 'iphone') || fn:contains(userAgent, 'android')}">
    <!-- If Mobile -->
    <c:set var = "imgBanner" value = "_mob"/>
    <c:set var = "img2x" value = "_mob"/>
</c:if>
<c:if test="${homelink eq '/es/'}">
    <!-- If Spanish -->
    <c:set var = "imgBanner" value = "${imgBanner}_es"/>
    <c:set var = "img2x" value = "${img2x}_es"/>
</c:if>
<!-- partner Program -->
<div class="row text-center text-default partner-section">
    <div class="col-md-12 flex-center no-padding-xs partner-banner partner-header">
        <img class="header-image" src="/_ui/responsive/theme-lambda/images/partner_banner${imgBanner}.jpg" alt="partners-program-banner" title="partners-program-banner" />
        <div class="row margin0 flex-center flex-dir-column-xs partner-overlay ">
            <a class="col-md-6 col-md-offset-2 col-xs-10 col-xs-offset-0 m-t-60" href="#becomeMember" style="height: 200px;"></a>
        </div>
    </div>
    <div class="col-md-10 col-md-offset-1 padding-30 marginBottom30 bg-white partner-flot-heading">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <h1 class="font-metronic-headline f-s-40 f-s-20-xs-pt"><spring:theme code="partners.program.heading" /></h1>
            </div>
            <div class="col-md-8 col-md-offset-2 marginBottom30 no-margin-xs">
                <P class="m-y-15 text-muted f-s-18 f-s-14-xs-pt"><spring:theme code="partners.program.heading.desc1" /> <span class="bold"> <spring:theme code="partners.program.heading.desc2" /></span> <spring:theme code="partners.program.heading.desc3" /></P>
            </div>
        </div>
    </div>
    <c:choose>
        <c:when test="${isAnonymous eq true}">
            <div id="becomeMember" class="col-md-5 col-md-offset-1 flex-center justify-center p-l-0 marginBottom30 no-padding-xs partner-banner">
                <img src="/_ui/responsive/theme-lambda/images/partner_background.jpg" alt="partners-program-background" />
                <div class="row margin0 partner-overlay">
                    <div class="col-md-8 col-md-offset-2">
                        <h2 class="font-metronic-headline f-s-20-xs-pt m-t-0"><spring:theme code="partners.program.haveAccess" /></h2>
                        <button class="btn btn-primary btn-block margin-top-20 signInOverlay enroll-tracking"><spring:theme code="partners.program.logIn" /></button>
                    </div>
                </div>
            </div>
            <div class="col-md-5 flex-center justify-center p-r-0 marginBottom30 no-padding-xs partner-banner">
                <img src="/_ui/responsive/theme-lambda/images/partner_background.jpg" alt="partners-program-background-2" />
                <div class="row margin0 partner-overlay">
                    <div class="col-md-8 col-md-offset-2">
                        <h2 class="font-metronic-headline f-s-20-xs-pt m-t-0"><spring:theme code="partners.program.noAccount" /></h2>
                        <button class="btn btn-primary btn-block margin-top-20" onclick="location.href='${homelink}request-account/form'"><spring:theme code="partners.program.createAccount" /></button>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${isPartnerProgramEnrolled eq true}">
                    <div id="becomeMember" class="col-md-10 col-md-offset-1 flex-center justify-center padding0 marginBottom30 no-padding-xs partner-banner partner-enroll-block">
                        <img src="/_ui/responsive/theme-lambda/images/partner_background.jpg" alt="partners-program-background-3" />
                        <div class="row margin0 partner-overlay">
                            <div class="col-md-12">
                                <h2 class="font-metronic-headline f-s-20-xs-pt margin0"><spring:theme code="partners.program.enrolled" /></h2>
                                <button class="btn btn-default btn-block margin-top-20 enroll-tracking" onclick="location.href='${homelink}my-account/accountPartnerProgram'"><spring:theme code="partners.program.details" /></button>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div id="becomeMember" class="col-md-10 col-md-offset-1 flex-center justify-center padding0 marginBottom30 no-padding-xs partner-banner partner-enroll-block">
                        <img src="/_ui/responsive/theme-lambda/images/partner_background.jpg" alt="partners-program-background-4" />
                        <div class="row margin0 partner-overlay">
                            <div class="col-md-12">
                                <h2 class="font-metronic-headline f-s-20-xs-pt margin0"><spring:theme code="partners.program.startEarning" /></h2>
                                <div class="cl"></div>
                                <c:choose>
                                        <c:when test="${isPartnersProgramRetail eq true}">
                                                <button class="btn btn-block margin-top-20 gray-disabled-btn js-info-tootip flex-center justify-center" rel="custom-tooltip">
                                                <common:exclamatoryIcon iconColor="#fff" width="20" height="20" />
                                                <span class="pad-lft-10"><spring:theme code="partners.program.enroll" /></span>
                                                  <span class="tooltip-content hide"><spring:theme code="hide.enroll.ppbtn" /></span>
                                           </button>
                                       </c:when>        
                                <c:otherwise>
                                <button class="btn btn-primary btn-block margin-top-20 enroll-tracking" onclick="ACC.partner.enrollModal('confirm','true')"><spring:theme code="partners.program.enroll" /></button>
                                   </c:otherwise>
                            </c:choose>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-10 col-md-offset-1 f-s-20 f-s-16-xs-pt marginBottom30 font-Geogrotesque">
                        <h4 class="bold"><spring:theme code="partners.program.retailAccount" /></h3>
                        <p><spring:theme code="partners.program.contact" /> <a class="show-xs p-l-5" href="phone:18007483663">1-800-7483-663</a> <spring:theme code="partners.program.noAccess2" /></p>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>
    <div class="col-md-10 col-md-offset-1 col-xs-12 col-xs-offset-0 flex-center m-t-25 m-b-60 m-b-50-xs m-t-20-xs partner-banner hidden">
        <img class="header-image" src="/_ui/responsive/theme-lambda/images/partner_b2x${img2x}.jpg" alt="partners-program-2x" title="partners-program-2x" />
    </div>
    <div class="col-md-10 col-md-offset-1 col-xs-12 col-xs-offset-0 m-b-60 m-b-35-xs text-align-center">
		<iframe class="border-light-grey marketing-videos" width="1080" height="607" src="https://www.youtube.com/embed/T_h8aeYMjGA" title="Partner Program Video" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" onload="marketingVideoAnalytics(this)" allowfullscreen></iframe>
    </div>
    <content:contentPartners/>
</div>
<!-- ./Partner Program -->
</template:page>