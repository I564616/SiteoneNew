<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="pag" tagdir="/WEB-INF/tags/responsive/nav/pagination" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<c:set var="shipToAccount" value="${unitId}" />
<spring:url value="/" var="homelink" htmlEscape="false"/>
<c:if test="${not empty invoiceShiptos}">
	<c:set var="invShipRawData" value="${invoiceShiptos}" />
	<c:set var="shipToSplit" value="${fn:split(invoiceShiptos,' ')}" />
	<c:set var="shipToAccount" value="${shipToSplit[0]}" />
	<c:set var="shipToAccountRaw" value="${shipToSplit[0]}"/>
	<c:if test="${not fn:contains(shipToAccount,'_US')}">
		<c:set var="shipToAccount" value="${shipToSplit[0]}_US" />
	</c:if>
	<c:if test="${currentBaseStoreId eq 'siteoneCA'}">
		<c:if test="${not fn:contains(shipToAccount,'_CA')}">
			<c:set var="shipToAccount" value="${shipToSplit[0]}_CA" />
		</c:if>
	</c:if>
</c:if>
<c:forEach items="${listOfShipTos}" var="shipToacc" varStatus="loop">
	<c:if test="${loop.index == 0}">
		<c:set var="shipToSelected" value="${shipToacc}" />
		<c:set var="shipToSelectedTemp" value="${fn:split(shipToacc,' ')}"/>
		<c:set var="shipToSelectedRaw" value="${shipToSelectedTemp[0]}"/>
	</c:if>
</c:forEach>
<c:set var="shipToSelectedAccRaw" value="${shipToAccountRaw ne '' ? shipToAccountRaw : shipToSelectedRaw}" />
<template:page pageTitle="${pageTitle}">
<input type="hidden" id="shipToSelected_inv" name="invoiceShiptos" value="${not empty invoiceShiptos ? invoiceShiptos : 'All'}" />
<input type="hidden" id="pageSizeInvoice1" name="pagesize" value="25" />
    <script src="${commonResourcePath}/js/jquery-editable-select.min.js"></script>
    <link rel="stylesheet" type="text/css" media="all" href="${themeResourcePath}/css/jquery-editable-select.min.css" />
    <spring:htmlEscape defaultHtmlEscape="true" />
    <c:set var="esOrderHeader" value="" scope="application" />
    <c:if test="${currentLanguage.isocode eq 'es'}">
        <c:set var="esOrderHeader" value="es-title-bar" scope="application" />
    </c:if>
    <c:set var="currentDivisionId" value="_US" />
    <c:if test="${currentBaseStoreId eq 'siteoneCA'}">
        <c:set var="currentDivisionId" value="_CA" />
    </c:if>
    <input type="hidden" id="unitId" name="unitId" value="${fn:escapeXml(unitId)}">
    <c:set var="shipToAccount" value="${unitId}" />
    <c:if test="${not empty invoiceShiptos}">
        <c:set var="shipToAccount" value="${invoiceShiptos}" />
    </c:if>
    <!-- paybill -->
    <c:if test="${contPayBillOnline && acctPayBillOnline}">
        <div class="text-align-right m-b-15 hidden-md hidden-lg">
            <a href="${homelink}my-account/pay-account-online" class="bold btn btn-white-border transition-3s f-s-15  text-uppercase account-pay-account-online" target="redirectToBT">
                <span class="bg-primary display-ib flex-center justify-center">
                    <common:globalIcon iconName="paybill" iconFill="none" iconColor="#FFFFFF" width="18" height="21" viewBox="0 0 18 21" display="" />
                </span>
                <spring:theme code="text.account.payBillOnline.btn" />
            </a>
        </div>
    </c:if>
    <div class="orders-tabs-container f-s-18 f-s-12-xs-pt f-s-12-sm-pt font-Geogrotesque">
        <div class="row container-lg margin0 p-l-0-xs p-b-10-xs p-t-5-xs p-l-0-sm p-b-10-sm p-t-5-sm">
            <c:if test="${quotesFeatureSwitch eq true}">
                <a class="col-md-1 padding-20 p-10-xs font-small-xs p-10-sm orders-tab" href="${homelink}my-account/my-quotes">
                    <spring:theme code="myquotes.quotes" />
                </a>
            </c:if>
            <a class="col-md-1 padding-20 p-10-xs font-small-xs p-10-sm orders-tab" data-key="orderhistorypage" data-active="orderHistoryTab" href="${homelink}my-account/orders/${sessionShipTo.uid}">
                <spring:theme code="homepage.myOrder" />
            </a>
            <a class="col-md-1 padding-20 p-10-xs font-small-xs p-10-sm orders-tab" data-key="purchasedproducts" data-active="purchasedOrderTab" href="${homelink}my-account/buy-again/${sessionShipTo.uid}">
                <spring:theme code="text.account.buyagain" />
            </a>
            <a class="col-md-1 padding-20 p-10-xs font-small-xs p-10-sm orders-tab" data-key="invoicespage" data-active="invoicesTab" href="${homelink}my-account/invoices/${sessionShipTo.uid}">
                <spring:theme code="text.account.invoices" />
            </a>
        </div>
        <div class="tab-wrapper-border"></div>
    </div>
    <div id="invoice-datasearch" class="p-t-80 p-t-50-xs p-t-50-sm">
        <spring:htmlEscape defaultHtmlEscape="true" />
        <spring:url value="/my-account/invoice/" var="invoiceDetailsUrl" htmlEscape="false" />    
        <c:set var="searchUrl" value="/my-account/invoices/${unitId}?sort=${ycommerce:encodeUrl(searchPageData.pagination.sort)}" />
        <div class="invoiceHeadingBtm flex-center justify-between">
            <h1 class="headline f-s-28-imp  f-w-500">
                <spring:theme code="text.account.invoicelist.heading" />
                (${searchPageData.pagination.totalNumberOfResults}&nbsp;
                <spring:theme code="text.account.orderHistory.results" />)
            </h1>
            <div class="flex-center justify-between gap-10 hidden-sm hidden-xs">
            <c:if test="${searchPageData.pagination.totalNumberOfResults > 0 }">
                <div class="download_invoice">
                    <span class="linktext">
                        <a href="#" data-unitid="${unitId}" data-total-results="${searchPageData.pagination.totalNumberOfResults}"
                            class="downloadCSV no-text-decoration f-s-14" id="line">
                            <span class="glyphicon glyphicon-download-alt"></span>&nbsp;<span class="underline-text">
                                <spring:theme code="invoicelistingpage.details.download" />(<span class="f-s-12">.csv</span>)
                            </span>
                        </a>
                    </span>
                    <span class="pad-md-lft-15 linktext">
                        <a href="#" data-unitid="${unitId}" data-total-results="${searchPageData.pagination.totalNumberOfResults}"
                            class="downloadCSV no-text-decoration f-s-14" id="summary">
                            <span class="glyphicon glyphicon-download-alt"></span>&nbsp;<span class="underline-text">
                                <spring:theme code="invoicelistingpage.summary.download" />(<span class="f-s-12">.csv</span>)
                            </span>
                        </a>
                </div>
            </c:if>    
                <div class="text-center">
                    <a href="<c:url value=" /quickbooks" />" target="_blank" class="quickbook_btn f-w-700">
                    <spring:theme code="text.connect.toquickbook.btn" /></a>    
                </div>
                <c:if test="${contPayBillOnline && acctPayBillOnline}">
                    <div class="text-align-right hidden-xs hidden-sm">
                        <a href="${homelink}my-account/pay-account-online"
                            class="bold btn btn-white-border transition-3s f-s-15  text-uppercase account-pay-account-online"
                            target="redirectToBT">
                            <span class="bg-primary display-ib flex-center justify-center">
                                <common:globalIcon iconName="paybill" iconFill="none" iconColor="#FFFFFF" width="18"
                                    height="21" viewBox="0 0 18 21" display="" />
                            </span>
                            <spring:theme code="text.account.payBillOnline.btn" />
                        </a>
                    </div>
                </c:if>    
            </div>
            <!-- mobile -->    
            <div class="col-xs-2 f-s-14 hidden-lg hidden-md  flex justify-flex-end orderDetailsLinks-wrapper p-r-0 text-right">
                <button class="btn-link flex-center dropdown-toggle p-r-0-imp" type="button" id="dropdownMenu1"
                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                    <common:globalIcon iconName="circles3" iconFill="none" iconColor="#78A22F" width="23" height="5"
                        viewBox="0 0 23 5" display="" />
                </button>
                <ul class="bg-green border-radius-3 dropdown-menu width-200-px text-white" id="invoice-dropdown"
                    aria-labelledby="dropdownMenu1">
                    <li class="text-align-right">
                        <button type="button" class="b-0-xs bg-green margin0" data-dismiss="dropdown"
                            aria-label="Close"><span aria-hidden="true" class="white">x</span></button>
                    </li>
                    <c:if test="${searchPageData.pagination.totalNumberOfResults > 0 }">
                        <li>
                            <a href="#" data-unitid="${unitId}" data-total-results="${searchPageData.pagination.totalNumberOfResults}"
                                class="downloadCSV no-text-decoration p-l-0-imp" id="line">
                                <span class="text-white"><span class="glyphicon glyphicon-download-alt p-r-5"></span>&nbsp;
                                    <spring:theme code="invoicelistingpage.details.download" />(<span class="f-s-12">.csv</span>)
                                </span>
                            </a>
                        </li>
                        <li>
                            <a href="#" data-unitid="${unitId}" data-total-results="${searchPageData.pagination.totalNumberOfResults}"
                                class="downloadCSV no-text-decoration p-l-0-imp" id="summary">
                                <span class="text-white"><span class="glyphicon glyphicon-download-alt p-r-5"></span>&nbsp;
                                    <spring:theme code="invoicelistingpage.summary.download" />(<span class="f-s-12">.csv</span>)
                                </span>
                            </a>
                        </li>
                    </c:if>                    
                    <li>
                        <a href="<c:url value=" /quickbooks" />" target="_blank" class="no-text-decoration p-l-0-imp">
                        <span class="text-white"><span class="glyphicon glyphicon-new-window p-r-5"
                                aria-hidden="true"></span>&nbsp;
                            <spring:theme code="text.connect.toquickbook.btn" />
                        </span>
                        </a>
                    </li>
                </ul>
            </div>    
        </div>
<!-- buttons invvoice -->
    <form:form id="invoicesearchForm" method='GET' modelAttribute="invoicesearchForm">
        <input type="hidden" name="invoiceShiptos" id="invShipTo" value="${shipToAccount}" />
        	<div class="flex flex-dir-column-xs justify-between order-tab p-t-10-sm p-t-10-xs p-b-0-xs p-y-15 row-gap-10">
	        <div class="flex gap-10 left-tab">
	        <common:invoicelisting-searchby-tabs />
	        <div class="flex-1-xs">
	        <div class="bg-white btn btn-white full-width transition-3s hidden-md hidden-lg" onclick="ACC.myquotes.filterPopup('show', 500, '.invoice-filter-popup');ACC.global.popupHeightSet(this, '.invoice-filter-popup');">
	            <span class="f-s-16 f-w-700">Sort By</span>
	        </div>
	        </div>
	        <div class="btn btn-black-gray hidden">
                <span class="valign-m">
                    <common:headerIcon iconName="user" iconFill="none" iconColor="#77A12E" width="15" height="15" viewBox="0 0 23 24" />
                </span>
                <span class="f-s-16 f-w-400">Contact</span>
            </div>
            <div class="btn btn-black-gray hidden">
                <span class="valign-m">
                    <common:headerIcon iconName="order" iconFill="none" iconColor="#77A12E" width="15" height="15" viewBox="0 0 23 24" />
                </span>
                <span class="f-s-16 f-w-400">Order Status</span>
            </div>
            <div class="btn btn-black-gray hidden">
                <span class="valign-m">
                    <common:deliveryIcon iconColor="#77A12E" width="30" height="15" />
                </span>
                <span class="f-s-16 f-w-400">Fulfillment Type</span>
            </div>
		    </div>
		    <div class="col-xs-6 hidden-md hidden-lg hidden">
	        <button class="bg-white btn btn-white full-width transition-3s" onclick="ACC.myquotes.filterPopup('show', 500, '.invoice-filter-popup');ACC.global.popupHeightSet(this, '.invoice-filter-popup');">
	            <span class="f-s-16 f-w-700">Sort By</span>
	        </button>
	    	</div>      
        	<div class="right-tab">
	        <common:invoicelisting-right-tabs shipToSelectedAcc="${shipToSelectedAccRaw}" shipToradio="shipToAc" shipToradioMob="shipToAc-m" invShipRawData="${invShipRawData}"/>
	     	</div>
	     	</div>
            <div class="cl"></div>
            <input type="hidden" id="hidDateFrom" value="${dateFrom}">
            <input type="hidden" id="hidDateTo" value="${dateTo}">
            <input type="hidden" id="daysArgs" value="${daysArgs}">
            <input type="hidden" id="cmspageuid" value="${cmsPage.uid}">
            <input type="hidden" name="sort" id="sortinvoice" value="${sort}" />
            <input type="hidden" name="sortDirection" id="sort-direction" value="desc" />
            <input type="hidden" id="pagesize" name="pagesize" value="25">     	
    </form:form>    
<!--division for share functionality  -->
<product:invoiceListFilter searchPageData="${searchPageData}" />
<div class="account-section-content invoice_table">
    <div class="account-orderhistory">
    <c:if test="${false}">
        <div class="account-orderhistory-pagination paginationBtm invoiceTop-section invoiceTop-pagination invoice-pagination">
            <pag:invoiceListPagePagination top="true" msgKey="${messageKey}" showCurrentPageInfo="true" hideRefineButton="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" searchUrl="${searchUrl}" numberPagesShown="${numberPagesShown}" />
        </div>
        </c:if>
        <c:if test="${not empty searchPageData.results}">
            <div class="responsive-table m-t-0-xs-imp m-t-0-sm-imp">
                <div class="col-xs-12 col-sm-12 payonline-btn hidden hidden-md hidden-lg">
                    <div class="row">
                        <c:if test="${contPayBillOnline && acctPayBillOnline}">
                            <a href="<c:url value=" /my-account/pay-account-online" />" class="btn btn-primary btn-block" target="redirectToBT"><spring:theme code="text.account.payBillOnline.btn" /></a>
                        </c:if>
                    </div>
                </div>
                <div class="row margin0 p-y-20 bg-off-grey add-border-radius text-white bold-text f-s-12 text-uppercase flex-center hidden-sm hidden-xs">
                    <div class="col-md-3 padding0">
                        <button onclick="ACC.invoicepage.invoiceSortBy('InvoiceNumber')" class="bg-dark-grey btn btn-small btn-block f-s-12-imp text-white text-white-hover l-h-18 flex-center text-uppercase">
                            <span class="p-r-5"><spring:theme code="text.account.invoicelist.invoiceNumber" /> # / <spring:theme code="text.account.invoicelist.purchasedorderNumber" /></span>
                            <common:globalIcon iconName="sort" iconFill="none" iconColor="#fff" width="8" height="14" viewBox="0 0 8 12" display="sort-button" />
                        </button>
                    </div>
                    <div class="col-md-2 col-md-10pe padding0">
                        <button onclick="ACC.invoicepage.invoiceSortBy('InvoiceDate')" class="bg-dark-grey btn btn-small btn-block f-s-12-imp text-white text-white-hover l-h-18 flex-center text-uppercase">
                            <span class="p-r-5 bold"><spring:theme code="text.account.invoicelist.invoiceDate" /></span>
                            <common:globalIcon iconName="sort" iconFill="none" iconColor="#fff" width="8" height="14" viewBox="0 0 8 12" display="sort-button" />
                        </button>
                    </div>
                    <div class="col-md-2 col-md-10pe padding0">
                        <button onclick="ACC.invoicepage.invoiceSortBy('OrderNumber')" class="bg-dark-grey btn btn-small btn-block f-s-12-imp text-white text-white-hover l-h-18 flex-center text-uppercase">
                            <span class="p-r-5 bold"><spring:theme code="text.account.invoicelist.orderNumber" /></span>
                            <common:globalIcon iconName="sort" iconFill="none" iconColor="#fff" width="8" height="14" viewBox="0 0 8 12" display="sort-button" />
                        </button>
                    </div>
                    <div class="col-md-2 col-md-20pe p-r-0"><spring:theme code="text.account.invoicelist.shipTo" /></div>
                    <div class="col-md-2 padding0">
                        <button onclick="ACC.invoicepage.invoiceSortBy('InvoiceTotal')" class="bg-dark-grey btn btn-small btn-block f-s-12-imp text-white text-white-hover l-h-18 flex-center text-uppercase">
                            <span class="p-r-5 bold"><spring:theme code="text.account.invoicelist.invoicetotal" /></span>
                            <common:globalIcon iconName="sort" iconFill="none" iconColor="#fff" width="8" height="14" viewBox="0 0 8 12" display="sort-button" />
                        </button>
                    </div>
                    <div class="col-md-1 p-r-0 col-md-10pe"><spring:theme code="text.account.invoicelist.balance" /></div>
                    <div class="col-md-1 p-r-0 text-align-center"><spring:theme code="text.account.invoicelist.receipt" /></div>
                </div>
                <c:forEach items="${searchPageData.results}" var="invoice">
                    <c:set var="shipToAccountNumber" value="${ycommerce:getAccountNumberWithoutDivision(invoice.shipToAccountNumber)}" />
                    <div class="row margin0 p-y-20 m-t-10 m-t-15-xs m-t-15-sm no-padding-xs no-padding-sm flex-center justify-center flex-wrap-xs flex-wrap-sm add-border-radius border-grey text-default f-s-14 bg-white">
                        <div class="col-md-3 col-xs-12 col-sm-12 p-r-0 b-b-grey-xs b-b-grey-sm">
                            <div class="row">
                                <div class="col-md-12 col-xs-6 p-15-xs col-sm-6 p-15-sm">
                                    <span class="bold text-gray text-uppercase f-s-10 hidden-md hidden-lg"><spring:theme code="text.account.invoicelist.invoiceNumber" />&nbsp;<spring:theme code="text.account.invoicelist.number" /></span>
                                    <p class="m-b-0 f-w-n-xs f-w-n-sm">
                                        <ycommerce:testId code="orderHistoryItem_orderDetails_link">
                                            <a href="${invoiceDetailsUrl}${invoice.shipToAccountNumber}${currentDivisionId}/${ycommerce:encodeUrl(invoice.invoiceNumber)}?orderShipmentActualId=${invoice.orderShipmentActualId}" class="f-s-16 font-GeogrotesqueSemiBold f-s-14-xs-px no-text-decoration responsive-table-link text-green">${fn:escapeXml(invoice.invoiceNumber)}</a>
                                        </ycommerce:testId>
                                    </p>
                                </div>
                                <div class="col-md-12 col-xs-6 p-15-xs col-sm-6 p-15-sm b-l-grey-xs b-l-grey-sm">
                                    <span class="bold text-gray text-uppercase f-s-10 hidden-md hidden-lg"><spring:theme code="text.account.invoicelist.purchasedorderNumber" />&nbsp;<spring:theme code="text.account.invoicelist.number" /></span>
                                    <p class="m-b-0 f-w-n-xs f-w-n-sm">
                                        <c:choose>
                                            <c:when test="${not empty invoice.purchaseOrderNumber and invoice.purchaseOrderNumber ne ''}"><spring:theme code="${invoice.purchaseOrderNumber}" /></c:when>
                                            <c:otherwise><span class="hidden-md hidden-lg">-</span></c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-12 col-sm-12 p-15-xs p-15-sm b-b-grey-xs b-b-grey-sm hidden-md hidden-lg">
                            <span class="bold text-gray text-uppercase f-s-10"><spring:theme code="text.account.invoicelist.shipTo" /></span>
						    <p class="m-b-0 f-w-n-xs f-w-n-sm">
                                <c:choose>
                                    <c:when test="${not empty invoice.accountDisplay}">
                                        <spring:theme code="${invoice.accountDisplay}" />
                                    </c:when>
                                    <c:otherwise>
                                        <spring:theme code="${shipToAccountNumber}" />
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                        <div class="col-md-2 col-md-10pe col-xs-6 p-r-0 p-15-xs col-sm-6 p-15-sm b-b-grey-xs b-b-grey-sm b-r-grey-xs b-r-grey-sm">
                            <span class="bold text-gray text-uppercase f-s-10 hidden-md hidden-lg"><spring:theme code="text.account.invoicelist.invoiceDate" /></span>
						    <p class="m-b-0 f-w-n-xs f-w-n-sm">
                                <c:choose>
                                    <c:when test="${not empty invoice.invoiceDate and invoice.invoiceDate ne ''}"><fmt:formatDate value="${invoice.invoiceDate}" dateStyle="long" timeStyle="short" /></c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                        <div class="col-md-2 col-md-10pe col-xs-6 p-15-xs col-sm-6 p-15-sm p-r-0 b-b-grey-xs b-b-grey-sm">
                            <span class="bold text-gray text-uppercase f-s-10 hidden-md hidden-lg"><spring:theme code="text.account.invoicelist.orderNumber" /></span>
						    <p class="m-b-0 f-w-n-xs f-w-n-sm underline-text">
                                <c:choose>
                                    <c:when test="${not empty invoice.orderNumber and invoice.orderNumber ne ''}"><spring:theme code="${invoice.orderNumber}" /></c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                        <div class="col-md-2 col-md-20pe hidden-sm hidden-xs">
                            <p class="m-b-0 f-w-n-xs f-w-n-sm">
                                <c:choose>
                                    <c:when test="${not empty invoice.accountDisplay}">
                                        <spring:theme code="${invoice.accountDisplay}" />
                                    </c:when>
                                    <c:otherwise>
                                        <spring:theme code="${shipToAccountNumber}" />
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                        <div class="col-md-2 col-xs-6 p-r-0 p-15-xs col-sm-6 p-15-sm b-b-grey-xs b-b-grey-sm b-r-grey-xs b-r-grey-sm">
                            <span class="bold text-gray text-uppercase f-s-10 hidden-md hidden-lg"><spring:theme code="text.account.invoicelist.invoicetotal" /></span>
						    <p class="m-b-0 f-w-n-xs f-w-n-sm">
                                <c:choose>
                                    <c:when test="${not empty invoice.orderTotalPrice and invoice.orderTotalPrice ne '0.00'}">$${invoice.orderTotalPrice}</c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                        <div class="col-md-1 col-md-10pe col-xs-6 p-r-0 p-15-xs col-sm-6 p-15-sm b-b-grey-xs b-b-grey-sm">
                            <span class="bold text-gray text-uppercase f-s-10 hidden-md hidden-lg"><spring:theme code="text.account.invoicelist.balance" /></span>
						    <p class="m-b-0 f-w-n-xs f-w-n-sm">
                                <c:choose>
                                    <c:when test="${not empty invoice.amountDue and invoice.amountDue ne '0.00'}">
                                        <span class="text-default bold">$${invoice.amountDue}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-default bold">-</span>
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                        <div class="col-md-1 col-xs-6 p-15-xs col-sm-6 p-15-sm">
                            <span class="bold text-gray text-uppercase f-s-10 hidden-md hidden-lg"><spring:theme code="text.account.invoicelist.receipt" /></span>
                            <p class="m-b-0 f-w-n-xs f-w-n-sm text-align-center text-left-xs">
                                <c:if test="${not empty invoice.orderShipmentActualId}">
                                    <a href="<c:url value=" /my-account/invoicePDF" />/${invoice.invoiceNumber}/${invoice.orderShipmentActualId}" download="invoice.pdf" target="_blank">
                                        <span class="flex hidden-md hidden-lg">
                                            <common:globalIcon iconName="new-pdf-icon-mbl" iconColor="#414244" iconColorSecond="#77A12E" iconFill="none" width="16" height="18" viewBox="0 0 16 18" display="m-r-5" />
                                            <spring:theme code="invoicelistingpage.pdf" />
                                        </span>
                                        <span class="hidden-sm hidden-xs">
                                            <common:globalIcon iconName="new-pdf-icon" iconColor="#414244" iconColorSecond="#77A12E" iconFill="none" width="19" height="22" viewBox="0 0 19 22" />
                                        </span>
                                    </a>
                                </c:if>
                            </p>
                        </div>
                        <div class="col-xs-6 hidden-md hidden-lg"></div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>
    <div class="row hidden-xs"></div>
        <div class="account-orderhistory-pagination invoiceBottom-section invoice-pagination">
            <pag:invoiceListPagePagination top="false" msgKey="${messageKey}" showCurrentPageInfo="true"
                hideRefineButton="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
                searchPageData="${searchPageData}" searchUrl="${searchUrl}"
                numberPagesShown="${numberPagesShown}" />
        </div>
    </div>
        <c:if test="${empty searchPageData.results && noInvoiceFlag}">
            <div class="row">
                <div class="col-md-12">
                    <div class="account-section-content invoice-dropDown-disabled alert alert-danger">
                        <spring:theme code="invoicelistingpage.no.invoices" /> <a href='<c:url value="/contactus"/>'>
                            <spring:theme code="invoicelistingpage.contactus" />
                        </a>.
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${empty searchPageData.results && !noInvoiceFlag}">
            <div class="row">
                <div class="col-md-12">
                    <div class="account-section-content invoice-dropDown-disabled alert alert-danger">
                        <spring:theme code="text.account.invoicelist.noInvoices" />
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${contPayBillOnline && acctPayBillOnline}">
            <div class="product-disclaimer-text">
                <p class="disclaimerText">
                    <spring:theme code="text.account.dashboard.paybillonline.disclaimertext" />
                </p>
            </div>
        </c:if>
    </div>
</template:page>