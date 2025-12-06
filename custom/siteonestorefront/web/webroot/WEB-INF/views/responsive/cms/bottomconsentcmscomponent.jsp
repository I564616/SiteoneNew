<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<c:set var = "userAgent" value = "${fn:toLowerCase(header['User-Agent'])}"/>
<c:set var = "mobileFlag" value = "false"/>
<c:if test="${fn:contains(userAgent, 'iphone') || fn:contains(userAgent, 'android')}">
    <!-- If Mobile -->
    <c:set var = "mobileFlag" value = "true"/>
</c:if>
<c:if test="${consentForm ne 'Approved'}">
    <div id="cookieNotice" class="row addonbottompanel font-Geogrotesque consent-banner text-center-xs">
        <div class="col-md-12 p-r-10 p-b-10 p-l-10 text-default">
            <button onclick="ACC.global.updateConsent()" class="cosent-close"><span class="icon-close"></span></button>
            <h3 class="bold f-s-16-xs-px">${component.text}</h3>
            <p class="m-y-15 f-w-500 f-s-18 font-14-xs">${component.content}</p>
        </div>
        <div class="col-md-6 col-xs-8 col-xs-offset-2 p-b-10 hidden-md hidden-lg">
            <button onclick="ACC.global.updateConsent()" title="Accept" class='btn btn-primary btn-block'>Accept</button>
        </div>
        <div class="col-md-6 col-xs-8 col-xs-offset-2 col-md-offset-0 p-b-10">
            <a class='btn btn-default btn-block' target="_blank" href="${component.buttonURLPolicy}" type="submit" title="Privacy Policy">Privacy Policy</a>
        </div>
        <div class="col-md-6 p-b-10 hidden-xs hidden-sm">
            <button onclick="ACC.global.updateConsent()" title="Accept" class='btn btn-primary btn-block'>Accept</button>
        </div>
    </div>
</c:if>
