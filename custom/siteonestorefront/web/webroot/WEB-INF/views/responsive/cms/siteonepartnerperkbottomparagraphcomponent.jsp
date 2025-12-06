<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/" var="homelink" htmlEscape="false"/>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<c:if test="${not empty component}">
<c:choose>
	<c:when test="${externalLink eq true}">
		<c:url value="_blank" var="target" />
	</c:when>
	<c:otherwise>
		<c:url value="_self" var="target" />
	</c:otherwise>
</c:choose>
<c:set var="title" value="${component.headline}"/>
<c:set var="content" value="${component.content}"/>

<c:choose>
<c:when test="${component.isExternalContent eq false}">
    <!-- Redeem Info -->
    <div class="row text-center text-default partner-section">
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
    </div>
    <!-- ./Redeem Info -->

    <!-- Redeem carousel -->
    <div class="row text-center text-default partner-section">
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
    </div>
    <!-- ./Redeem carousel -->

    <!-- Business Solutions Info -->
    <div class="row text-center text-default partner-section">
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
    </div>
    <!-- ./Business Solutions Info -->
    <!-- Business Solutions carousel -->
    <div class="row text-center text-default partner-section">
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
    </div>
    <div class="row text-center text-default partner-section">
    <!-- ./Business Solutions carousel -->
	<div class="col-md-12 col-xs-12 m-t-60 marginBottom10 f-s-12-xs-pt m-b-0-xs">
		<a href="${homelink}partnerprogramterms" target="_self"><spring:theme code="partners.program.condition" /></a>
	</div>
	</div>
</c:when>
<c:otherwise>
<c:when test="${empty component.media}">
	<section>
		<div class="col-md-12 col-xs-12 col-sm-12 store-article">
			<c:if test="${not empty title}">
				<div class="col-md-12 col-xs-12 col-sm-12 store-heading-wrapper">
					<p class="store-specialty-heading"><strong>${title}</strong></p>
				</div>
			</c:if>
			<c:if test="${not empty content}">
				<div class="col-md-12 col-xs-12 col-sm-12 store-content-wrapper">
					<p>${content}</p>
				</div>
			</c:if>
		</div>
	</section>
</c:when>
<c:otherwise>
<section>
		<div class="col-md-12 col-xs-12 col-sm-12 store-article">
			<c:if test="${not empty title}">
				<div class="col-md-12 col-xs-12 col-sm-12 store-heading-wrapper">
					<p class="store-specialty-heading"><strong>${title}</strong></p>
				</div>
			</c:if>
			<div class="col-md-12 col-xs-12 col-sm-12 store-content-wrapper">
				<c:if test="${not empty content}">
					<p>${content}</p>
				</c:if>
			</div>
		</div>
		<div class="col-md-4 col-xs-12 col-sm-12 margin20">
			<a href="${component.urlForLink}" target="${target}"><img title="${title}" alt="${component.media.altText}" src="${component.media.url}"  width="100%"/></a>
		</div>
	</section>
</c:otherwise>
</c:otherwise>
</c:choose>
</c:if>
<c:if test="${not empty content}">
<div class="cl"></div>
</c:if>

