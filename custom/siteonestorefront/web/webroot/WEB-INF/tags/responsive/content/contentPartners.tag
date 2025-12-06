<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<spring:url value="/" var="homelink" htmlEscape="false"/>
<div class="col-md-4 hidden-xs marginTop35 marginBottom40">
    <img src="/_ui/responsive/theme-lambda/images/partnerGroup1.jpg" alt="partners-program-Group1" />
</div>
<div class="col-md-4 col-xs-6 marginBottom40 marginTop35 m-y-20-xs no-padding-xs partner-banner-left">
    <img src="/_ui/responsive/theme-lambda/images/partnerGroup2.jpg" alt="partners-program-Group2" />
</div>
<div class="col-md-4 col-xs-6 marginTop35 marginBottom40 no-padding-xs partner-banner-right">
    <img src="/_ui/responsive/theme-lambda/images/partnerGroup3.jpg" alt="partners-program-Group3" />
</div>
<div class="cl"></div>
<div class="col-md-4 col-md-offset-1 marginTopBVottom30 partner-end">
    <h2 class="font-metronic-headline f-s-40 f-s-20-xs-pt"><spring:theme code="partners.program.earning" /></h2>
    <P class="marginTopBVottom20 text-muted f-s-18 f-s-14-xs-pt"><spring:theme code="partners.program.earning.desc" /></P>
    <a class="bold" href="${homelink}PartnerPerks"><spring:theme code="partners.program.earning.link" /></a>
</div>
<div class="col-md-4 col-md-offset-2 marginTopBVottom30 m-y-50-xs">
    <h2 class="font-metronic-headline f-s-40 f-s-20-xs-pt"><spring:theme code="partners.program.redeeming" /></h2>
    <P class="marginTopBVottom20 text-muted f-s-18 f-s-14-xs-pt"><spring:theme code="partners.program.redeeming.desc" /></P>
    <a class="bold" href="${homelink}PartnerPerks"><spring:theme code="partners.program.redeeming.link" /></a>
</div>
<div class="col-md-10 col-md-offset-1 flex-center justify-center padding0 marginTop40 marginBottom40 partner-banner partner-bussines">
    <img src="/_ui/responsive/theme-lambda/images/partner_banner_bussines.jpg" alt="partners-program-bussines" />
    <div class="row margin0 partner-overlay">
        <div class="col-md-10 col-md-offset-1 text-white">
            <h2 class="font-metronic-headline f-s-40 f-s-22-xs-pt m-t-0"><spring:theme code="partners.program.business" /></h2>
            <P class="marginTopBVottom20 f-s-18 f-s-12-xs-pt"><spring:theme code="partners.program.business.desc" /></P>
            <button class="btn btn-overlay text-white" onclick="location.href='${homelink}PartnerPerks'"><spring:theme code="partners.program.more" /></button>
        </div>
    </div>	
</div>
<div class="col-xs-8 col-xs-offset-2 hidden-md hidden-lg margin-bot-10-xs">
    <img src="/_ui/responsive/theme-lambda/images/partner_marketing.png" alt="partners-program-marketing" />
</div>
<div class="col-md-6 col-md-offset-1 marginTop40 marginBottom40 padding-top-30 text-left xs-center">
    <h2 class="font-metronic-headline f-s-40 f-s-20-xs-pt marginTop40 padding-top-30"><spring:theme code="partners.program.marketing" /></h2>
    <P class="marginTopBVottom20 f-s-18 f-s-14-xs-pt text-muted"><spring:theme code="partners.program.marketing.desc" /></P>
    <button class="btn btn-primary hidden-xs" onclick="location.href='${homelink}toolkit'"><spring:theme code="partners.program.more" /></button>
    <button class="btn btn-default hidden-md hidden-lg" onclick="location.href='${homelink}toolkit'"><spring:theme code="partners.program.more" /></button>
</div>
<div class="col-md-4 hidden-xs marginTop40 marginBottom40 p-r-0">
    <img src="/_ui/responsive/theme-lambda/images/partner_marketing.png" alt="partners-program-marketing-2" />
</div>
<div class="col-md-12 marginTop40 marginBottom40 f-s-12-xs-pt m-b-0-xs">
    <a href="${homelink}partnerprogramterms" target="_self"><spring:theme code="partners.program.condition" /></a>
</div>