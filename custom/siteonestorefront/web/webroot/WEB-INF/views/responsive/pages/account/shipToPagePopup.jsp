<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="org-common" tagdir="/WEB-INF/tags/addons/siteoneorgaddon/responsive/common" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<spring:htmlEscape defaultHtmlEscape="true" />
<input type="hidden" name="sortShipTo" id="sortShipTo" value="${sortShipToPopup}" />
<c:set var="searchPageData2" value="${{'results':[{'uid':'17058-3002','displayId':'17058-3002','modifiedTime':'Mon Dec 23 17:51:40 IST 2024','name':'LMI - Austin'},{'uid':'2060119-3004','displayId':'2060119-3004','modifiedTime':'Mon Dec 23 17:51:40 IST 2024','name':'J/Collin County Healthcare Parking/Medical Ex Fac'},{'uid':'17058-3002','displayId':'17058-3002','modifiedTime':'Mon Dec 23 17:51:40 IST 2024','name':'LMI - Austin'},{'uid':'2060119-3003','displayId':'2060119-3003','modifiedTime':'Mon Dec 23 17:51:40 IST 2024','name':'J/Carmax Fort Worth Store #7181'},{'uid':'2060119-3000','displayId':'2060119-3000','modifiedTime':'Mon Dec 23 17:51:40 IST 2024','name':'S/TAXABLE PURCHASES C&B Landscape Management'},{'uid':'17058-3002','displayId':'17058-3002','modifiedTime':'Mon Dec 23 17:51:40 IST 2024','name':'LMI - Austin'},{'uid':'2060119-3004','displayId':'2060119-3004','modifiedTime':'Mon Dec 23 17:51:40 IST 2024','name':'J/Collin County Healthcare Parking/Medical Ex Fac'},{'uid':'17058-3002','displayId':'17058-3002','modifiedTime':'Mon Dec 23 17:51:40 IST 2024','name':'LMI - Austin'},{'uid':'2060119-3003','displayId':'2060119-3003','modifiedTime':'Mon Dec 23 17:51:40 IST 2024','name':'J/Carmax Fort Worth Store #7181'},{'uid':'2060119-3000','displayId':'2060119-3000','modifiedTime':'Mon Dec 23 17:51:40 IST 2024','name':'S/TAXABLE PURCHASES C&B Landscape Management'}]}}" />
<c:set var="isShipToSelected" value="${empty selectedShipTo or selectedShipTo.uid eq parentUnitId ? false : true}" />
<c:set var="isSearchParam" value="${searchParam ? searchParam : '' }" />
<div class="row font-Geogrotesque b-b-grey shipto-mob-top hidden-md hidden-lg">
    <button class="col-xs-3 btn btn-link pointer flex-center flex-dir-column b-r-gray" onclick='$(".branchpopup_click").click();'>
        <common:headerIcon iconName="marker" iconFill="#FFFFFF" iconColor="#999" width="12" height="15" viewBox="0 0 15 16" display="m-b-5" />
        <span class="text-uppercase font-14-xs text-gray">Branch</span>
    </button>
    <button class="col-xs-3 btn btn-link pointer flex-center flex-dir-column b-r-gray active">
        <common:headerIcon iconName="shipping" iconFill="#FFFFFF" iconColor="#5a5b5d" width="30" height="16" viewBox="0 0 15 16" display="m-b-5 m-r-10" />
        <span class="text-uppercase font-14-xs text-dark-gray">Ship-To</span>
    </button>
</div>
<button class="btn btn-link pointer popup-close" onclick="ACC.accountdashboard.shipToSlide(this, '.js-document-shipto-box', 0, 500)">
    <span class="sr-only">Ship-To Close</span>
    <common:headerIcon iconName="close" iconFill="#FFFFFF" iconColor="#77A12E" width="15" height="16" viewBox="0 0 15 16" display="" />
</button>
<div class="row p-t-50-xs p-t-50-sm page-shipToPage">
    <div class="col-xs-12 m-b-25 p-t-10-xs p-t-10-sm">
        <p class="f-s-18 text-primary text-uppercase font-Geogrotesque js-shipto-current ${isShipToSelected eq true ? '' : 'hidden'}">Current Ship-to</p>
        <p class="f-s-18 text-primary text-uppercase font-Geogrotesque js-shipto-primary ${isShipToSelected eq true ? 'hidden' : ''}">Primary Account</p>
        <div class="bg-light-grey border-radius-10 l-h-18 p-l-15 p-y-20 p-r-25 shipto-box">
            <p class="m-b-0 f-s-16 bold text-gray-1">${empty selectedShipTo ? sessionShipTo.name : selectedShipTo.name}</p>
            <p class="m-b-0 f-s-12 text-dark-gray js-shipto-main-check">${empty selectedShipTo ? sessionShipTo.displayId : selectedShipTo.displayId}</p>
            <div class="shipto-box-check"><span class="glyphicon glyphicon-ok"></span></div>
        </div>
    </div>
    <div class="col-xs-12 m-b-25 js-shipto-current ${isShipToSelected eq true ? '' : 'hidden'}">
        <p class="f-s-18 text-uppercase font-Geogrotesque">Primary Account</p>
        <spring:url value="/my-account/ship-to/" var="viewShipToUrl" htmlEscape="false"></spring:url>
        <a class="p-x-15 p-y-20 bg-light-grey flex-center m-b-10 shipto-box ship-to-link js-shipto-main-data" href="${viewShipToUrl}">
            <div class="bg-white border-grayish m-r-10 ship-to-link-circle"></div>
            <input type="hidden" value="" class="ship-to-name" />
            <input type="hidden" value="" class="ship-to-display-id" />
            <input type="hidden" value="${parentUnitId}" class="ship-to-uid" />
            <div>
                <p class="m-b-0 f-s-16 text-gray-1 js-shipto-main-name"></p>
                <p class="m-b-0 f-s-12 text-dark-gray js-shipto-main-uid"></p>
            </div>
        </a>
    </div>
    <div class="col-xs-12 m-b-25 input-group-shipTo">
        <p class="f-s-18 text-default text-uppercase font-Geogrotesque">Search your Ship-Tos</p>
        <input type="text" id="search-ship-to-popup" placeholder="Enter name or number" name="searchParam" value="" onfocus="this.placeholder = ''" onblur="this.placeholder = '${shiptosearchPlaceholder}'" aria-label="productSearch" maxlength="100" placeholder="${shiptosearchPlaceholder}" class="form-control ship-to-search" />
        <span class="ship-tosearchbtn">
            <button class="btn btn-link shipToPopup ship-to-search-btn" type="button" id="shipToSearchBoxButton" onclick="ACC.accountdashboard.showShipTos(this)">
                <span class="sr-only">Ship-To Search</span>
                <common:headerIcon iconName="search" iconFill="#FFFFFF" iconColor="#77A12E" width="19" height="20" viewBox="0 0 19 20" display="" />
            </button>
        </span>
    </div>
    <div class="col-xs-12 m-b-25">
        <p class="f-s-18 text-default text-uppercase font-Geogrotesque">
            <c:choose>
                <c:when test="${not empty searchPageData.results}">
                    Select from Ship-Tos
                </c:when>
                <c:otherwise>
                    <spring:theme code="shipTo.not.linkedto.account" htmlEscape="false" />
                </c:otherwise>
            </c:choose>
        </p>
        <c:if test="${not empty searchPageData.results}">
            <div class="p-b-25 scroll-bar-5 js-document-shipto-scroll">
                <c:forEach items="${searchPageData.results}" var="result">
                    <spring:url value="/my-account/ship-to/${result.uid}" var="viewShipToDetailsUrl" htmlEscape="false"></spring:url>
                    <a class="p-x-15 p-y-20 bg-light-grey flex-center m-b-10 shipto-box ship-to-link" href="${viewShipToDetailsUrl}">
                        <div class="bg-white border-grayish m-r-10 ship-to-link-circle" ></div>
                        <input type="hidden" value="${fn:escapeXml(result.name)}" class="ship-to-name" />
                        <input type="hidden" value="${fn:escapeXml(result.displayId)}" class="ship-to-display-id" />
                        <input type="hidden" value="${fn:escapeXml(result.uid)}" class="ship-to-uid" />
                        <div class="">
                            <p class="m-b-0 f-s-16 text-gray-1 text-align-left">${fn:escapeXml(result.name)}</p>
                            <p class="m-b-0 f-s-12 text-dark-gray text-align-left">${fn:escapeXml(result.displayId)}</p>
                        </div>
                    </a>
                </c:forEach>
                <c:if test="${searchPageData.results.size() eq 10}">
                    <div class="text-align-center">
                        <button class="bold btn-gray p-y-3" onclick="ACC.accountdashboard.getMoreShipTos(this)">
                            More
                            <span class="glyphicon glyphicon-chevron-down f-s-7 p-l-5"></span>
                        </button>
                    </div>
                </c:if>
            </div>
        </c:if>
    </div>
</div>