<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/" var="homelink" htmlEscape="false"/>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<template:page pageTitle="${pageTitle}">
<!-- partner Program -->
<div class="row text-center text-default partner-section">
    <!-- Partners Program Header -->
    <div class="col-md-12 col-xs-12 flex-center m-b-60 no-padding-xs partner-banner partner-header">
        <img class="header-image perks-elem" src="/_ui/responsive/theme-lambda/images/partner_banner_point.jpg" alt="partners-program-banner" title="partners-program-banner" />
        <div class="row margin0 flex-center flex-dir-column-xs font-metronic-headline f-s-24 f-s-18-xs-px partner-overlay">
            <div class="col-md-3 col-md-offset-1 m-l-10p hidden-sm hidden-xs"><spring:theme code="partners.perks.banner1" /></div>
            <div class="col-md-3 col-xs-9 col-xs-offset-0 m-t-15-xs"><img src="/_ui/responsive/theme-lambda/images/partner_logo.png" alt="partners-program-info" /></div>
			<div class="col-md-4 col-xs-9 col-xs-offset-0 m-t-15-xs hidden-sm hidden-xs"><spring:theme code="partners.perks.banner2" /></div>
			<div class="col-xs-9 col-xs-offset-0 hidden-md hidden-lg"><spring:theme code="partners.perks.banner1" /></div>
        </div>
    </div>
    <!-- ./Partners Program Header -->
	<div class="col-xs-12 hidden-md hidden-lg m-b-50-xs"></div>
	<div class="col-md-4 hidden-xs marginTop35 m-b-60 m-b-50-xs">
		<img src="/_ui/responsive/theme-lambda/images/partnerPerks1.jpg" alt="partners-program-Group1" />
	</div>
	<div class="col-md-4 col-xs-6 m-b-60 m-b-50-xs m-y-20-xs no-padding-xs partner-banner-left">
		<img src="/_ui/responsive/theme-lambda/images/partnerPerks2.jpg" alt="partners-program-Group2" />
	</div>
	<div class="col-md-4 col-xs-6 no-margin-xs marginTop35 m-b-60 m-b-50-xs no-padding-xs partner-banner-right">
		<img src="/_ui/responsive/theme-lambda/images/partnerPerks3.jpg" alt="partners-program-Group3" />
	</div>
    <!-- Partnership Info -->
    <div class="col-md-10 col-md-offset-1 col-xs-12 padding-30 marginBottom10 m-b-0-xs partner-flot-heading">
        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <h1 class="font-metronic-headline f-s-40 f-s-20-xs-px"><spring:theme code="partners.perks.heading1" /></h1>
            </div>
            <div class="col-md-8 col-md-offset-2 no-margin-xs">
                <P class="m-y-15 text-muted f-s-18 font-14-xs"><spring:theme code="partners.perks.subheading1" /></P>
            </div>
        </div>
    </div>
    <!-- ./Partnership Info -->

    <!-- Login/Create Account blocks -->
    <c:choose>
        <c:when test="${isAnonymous eq true}">
            <div class="col-md-5 col-md-offset-1 col-xs-12 flex-center justify-center p-l-0 m-b-60 m-b-20-xs no-padding-xs partner-banner">
                <img src="/_ui/responsive/theme-lambda/images/partner_background.jpg" alt="partners-program-background" />
                <div class="row margin0 partner-overlay">
                    <div class="col-md-8 col-md-offset-2">
                        <h2 class="font-metronic-headline f-s-20-xs-px m-t-0"><spring:theme code="partners.program.haveAccess" /></h2>
                        <button class="btn btn-primary btn-block margin-top-20 signInOverlay enroll-tracking"><spring:theme code="partners.program.logIn" /></button>
                    </div>
                </div>
            </div>
            <div class="col-md-5 col-xs-12 flex-center justify-center p-r-0 m-b-60 m-b-30-xs no-padding-xs partner-banner">
                <img src="/_ui/responsive/theme-lambda/images/partner_background.jpg" alt="partners-program-background-2" />
                <div class="row margin0 partner-overlay">
                    <div class="col-md-8 col-md-offset-2">
                        <h2 class="font-metronic-headline f-s-20-xs-px m-t-0"><spring:theme code="partners.program.noAccount" /></h2>
                        <button class="btn btn-primary btn-block margin-top-20" onclick="location.href='${homelink}request-account/form'"><spring:theme code="partners.program.createAccount" /></button>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${isPartnerProgramEnrolled eq true}">
                    <div class="col-md-10 col-md-offset-1 col-xs-12 flex-center justify-center padding0 m-b-60 m-b-30-xs no-padding-xs partner-banner partner-enroll-block">
                        <img src="/_ui/responsive/theme-lambda/images/partner_background.jpg" alt="partners-program-background-3" />
                        <div class="row margin0 partner-overlay">
                            <div class="col-md-12">
                                <h2 class="font-metronic-headline f-s-20-xs-px margin0"><spring:theme code="partners.program.enrolled" /></h2>
                                <button class="btn btn-default btn-block margin-top-20 enroll-tracking" onclick="location.href='${homelink}my-account/accountPartnerProgram'"><spring:theme code="partners.program.details" /></button>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="col-md-10 col-md-offset-1 col-xs-12 flex-center justify-center padding0 m-b-60 m-b-30-xs no-padding-xs partner-banner partner-enroll-block">
                        <img src="/_ui/responsive/theme-lambda/images/partner_background.jpg" alt="partners-program-background-4" />
                        <div class="row margin0 partner-overlay">
                            <div class="col-md-12">
                                <h2 class="font-metronic-headline f-s-20-xs-px margin0"><spring:theme code="partners.program.startEarning" /></h2>
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
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>
    <!-- ./Login/Create Account blocks -->

    <!-- Redeem Info -->
    <div class="col-md-10 col-md-offset-1 col-xs-12 padding-30 marginBottom10 m-b-0-xs partner-flot-heading">
        <div class="row">
            <div class="col-md-8 col-md-offset-2 no-padding-xs">
                <h2 class="font-metronic-headline f-s-40 f-s-20-xs-px m-b-0"><spring:theme code="partners.perks.heading2" /></h2>
            </div>
            <div class="col-md-8 col-md-offset-2 no-margin-xs">
                <P class="m-y-15 m-b-0-xs text-muted f-s-18 font-14-xs"><spring:theme code="partners.perks.subheading2" /></P>
            </div>
        </div>
    </div>
    <!-- ./Redeem Info -->

    <!-- Redeem carousel -->
    <div class="col-md-12 col-xs-12 marginBottom40 m-b-30-xs">
      <div class="row combinedpartner flex flex-dir-column-xs">
        <div class="col-md-6 bg-combinedpartner white">
          <div class="m-t-50 f-s-34 f-s-20-xs-px font-MetronicSlabProBold text-center">
            <p class="m-b-0"><spring:theme code="partners.perks.heading4" /> </p>
          </div>
          <p class="f-s-26 f-s-16-xs-px font-Geogrotesque text-center"><spring:theme code="partners.perks.subheading4" /></p>
          <div class="green-line"></div>
          <div class="m-l-80 f-s-18 font-14-xs m-l-20-xs">
            <div class="flex-center marginTop30">
              <span class="badge badge-primary font-GeogrotesqueSemiBold f-s-16">1</span>
              <span class="pad-lft-10 xs-left"><spring:theme code="partners.perks.step1" /></span>
            </div class="m-l-80">
            <div class="flex-center marginTopBVottom25">
              <span class="badge badge-primary font-GeogrotesqueSemiBold f-s-16">2</span>
              <span class="pad-lft-10"><spring:theme code="partners.perks.step2" /></span>
            </div>
            <div class="flex-center">
              <span class="badge badge-primary font-GeogrotesqueSemiBold f-s-16">3</span>
              <span class="pad-lft-10"><spring:theme code="partners.perks.step3" /></span>
            </div>
            <div class="flex-center marginTopBVottom25">
              <span class="badge badge-primary font-GeogrotesqueSemiBold f-s-16">4</span>
              <span class="pad-lft-10"><spring:theme code="partners.perks.step4" /></span>
            </div>
            <div class="flex-center marginBottom20">
              <span class="badge badge-primary font-GeogrotesqueSemiBold f-s-16">5</span>
              <span class="pad-lft-10"><spring:theme code="partners.perks.step5" /></span>
            </div>
          </div>
          <div class="green-line"></div>
          <div class="font-size-14 text-center marginTop20 m-b-55 font-small-xs m-b-40-xs m-x-50 m-xs-hrz-auto">
            <span><spring:theme code="partners.perks.subheading6" /></span>
          </div>
        </div>
        <div class="col-md-6 bg-grayish p-b-75-xs">
          <div class="m-t-50 f-s-34 f-s-20-xs-px font-MetronicSlabProBold text-center text-default">
            <p class="m-b-0"><spring:theme code="partners.perks.heading5" /></p>
          </div>
          <p class="f-s-26 f-s-16-xs-px font-Geogrotesque text-center text-default m-b-0"><spring:theme code="partners.perks.subheading5" /></p>
          <div id="slider" class="carousel slide partnerperks-carousel m-x-50 m-t-5 m-xs-hrz-auto" data-ride="carousel">
            <!-- Indicators -->
            <ol class="carousel-indicators">
              <li onclick="ACC.global.functionalCarouselIndicators(this)" data-target="#slider" data-slide-to="1" class="active" id="1"></li>
              <li onclick="ACC.global.functionalCarouselIndicators(this)" data-target="#slider" data-slide-to="2" id="2"></li>
              <li onclick="ACC.global.functionalCarouselIndicators(this)" data-target="#slider" data-slide-to="3" id="5"></li>
              <li onclick="ACC.global.functionalCarouselIndicators(this)" data-target="#slider" data-slide-to="4" id="4"></li>
              <li onclick="ACC.global.functionalCarouselIndicators(this)" data-target="#slider" data-slide-to="5" id="5"></li>
              <li onclick="ACC.global.functionalCarouselIndicators(this)" data-target="#slider" data-slide-to="6" id="6"></li>
              <li onclick="ACC.global.functionalCarouselIndicators(this)" data-target="#slider" data-slide-to="7" id="7"></li>
            </ol>
            <!-- Wrapper for slides -->
            <div class="carousel-inner" role="listbox">
              <c:forEach var="i" begin="1" end="7">
                <common:partnercarouselcard card="${i}" />
              </c:forEach>
            </div>
            <!-- Controls -->
            <a class="left carousel-control" href="#slider" role="button" data-slide="prev">
              <span class="sr-only"><spring:theme code="partners.perks.prev" /></span>
            </a>
            <a class="right carousel-control" href="#slider" role="button" data-slide="next">
              <span class="sr-only"><spring:theme code="partners.perks.next" /></span>
            </a>
          </div>
        </div>
      </div>
    </div>
    <!-- ./Redeem carousel -->

    <!-- Business Solutions Info -->
	  <div class="col-md-10 col-md-offset-1 col-xs-12 padding-30 m-b-15 m-b-0-xs p-b-0-xs partner-flot-heading">
      <div class="row">
        <div class="col-md-8 col-md-offset-2 marginTop20">
          <h2 class="font-metronic-headline f-s-40 f-s-20-xs-px m-b-0"><spring:theme code="partners.perks.heading3" /></h2>
        </div>
        <div class="col-md-8 col-md-offset-2 no-margin-xs">
          <P class="m-y-15 m-b-0-xs text-muted f-s-18 font-14-xs"><spring:theme code="partners.perks.subheading3" /></P>
        </div>
      </div>
    </div>
    <!-- ./Business Solutions Info -->
    <!-- Business Solutions carousel -->
    <div class="col-xs-12 marginBottom30 category-tiles product__facet">
        <div class="facet js-facet p-b-30" style="overflow:auto">
            <div id="partnerCarousel" class="facet__list">
                <c:set var="totalCards" value="5" />
                <input type="hidden" id="carouselProduct" value="${totalCards}">
                <c:forEach var="i" begin="1" end="${totalCards}">
                  <common:partnerSliderCard card="${i}" />
                </c:forEach>
                <div class="category-tile-prev pointer">
                  <img width="37" src="/_ui/responsive/theme-lambda/images/category-tile-prev.svg" alt="prev">
                </div>
                <div class="category-tile-next pointer">
                  <img width="37" src="/_ui/responsive/theme-lambda/images/category-tile-next.svg" alt="next">
                </div>
            </div>
            <div class="carousel-indicators"></div>
        </div>
    </div>
    <!-- ./Business Solutions carousel -->
	<div class="col-md-12 col-xs-12 m-t-60 marginBottom10 f-s-12-xs-pt m-b-0-xs">
		<a href="${homelink}partnerprogramterms" target="_self"><spring:theme code="partners.program.condition" /></a>
	</div>
</div>
<!-- ./Partner Program -->
</template:page>