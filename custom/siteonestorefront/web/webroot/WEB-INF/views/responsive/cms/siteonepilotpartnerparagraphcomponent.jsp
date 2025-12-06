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

<spring:url value="/" var="homelink" htmlEscape="false"/>
<div class="row text-center text-default partner-section">
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
    </div>