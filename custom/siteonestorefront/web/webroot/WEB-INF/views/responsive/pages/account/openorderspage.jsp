<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="pagination" tagdir="/WEB-INF/tags/responsive/nav/pagination" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<template:page pageTitle="${pageTitle}">
<c:set var="searchUrl" value="/my-account/open-orders/${unitId}?sort=${ycommerce:encodeUrl(searchPageData.pagination.sort)}"/>

<div class="col-md-12 row"><h1 class="headline"><spring:theme code="text.account.openorders"/></h1></div>

<spring:htmlEscape defaultHtmlEscape="true"/>
<spring:url value="/my-account/order/" var="orderDetailsUrl" htmlEscape="false"/>
<!-- <div class="col-md-2 col-sm-2 col-xs-4 pull-right row">
<a href="" onclick="window.print()" class="btn btn-block btn-primary"> Print</a>
	 
 </div>  -->
 
 
<c:if test="${empty searchPageData.results}">
    <div class="row">
        <div class="col-md-6 col-md-push-3">
            <div class="account-section-content content-empty">
                <ycommerce:testId code="orderHistory_noOrders_label">
                    <spring:theme code="text.account.orderHistory.noOrders"/>
                </ycommerce:testId>
            </div>
        </div>
    </div>
</c:if>
<c:if test="${not empty searchPageData.results}">
    <div class="account-section-content">
        <div class="account-orderhistory">
            <div class="account-orderhistory-pagination top-section">
                <nav:pagination top="true" msgKey="filter" showCurrentPageInfo="true" hideRefineButton="true"
                                supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
                                searchPageData="${searchPageData}" searchUrl="${searchUrl}"
                                numberPagesShown="${numberPagesShown}"/>
            </div>
        <div class="margin20 marginBottom40">
	            <div class="orderhistoryDetails row">
					<div class="col-xs-12 hidden-xs orderhistoryDetails-header">
						<div class="title-bar">
							<div class="col-xs-2">
								<spring:theme code="text.account.orderHistory.orderNumber"/>
							</div>
							<div class="col-xs-2">
								<spring:theme code="text.account.orderHistory.purchaseOrderNumber"/>
							</div>
							<div class="col-xs-2">
								<spring:theme code="text.account.orderHistory.orderStatus"/>
							</div>
							<div class="col-xs-2">
								<spring:theme code="text.account.orderHistory.deliverytype"/>
							</div>
							<div class="col-xs-2">
								<spring:theme code="text.account.orderHistory.datePlaced"/>
							</div>
							<div class="col-xs-2">
								<spring:theme code="text.account.orderHistory.total"/>
							</div>
						</div>
					</div>
					<div class="col-xs-12 orderhistoryDetails-orders">
						<div class="hidden-md orderhistoryDetails-bottomBorder"></div>
							<c:forEach items="${searchPageData.results}" var="order">	
							<div class="col-xs-12 orderhistoryDetails-singleOrder">
		                        <div class="col-xs-12 col-md-2 col-sm-2">
		                            <div class="hidden-sm hidden-md hidden-lg col-xs-6  data-title"><spring:theme code="text.account.orderHistory.orderNumber"/></div>
		                            <div class="col-md-12 col-sm-12  col-xs-6 data-data data-margin">
		                                <ycommerce:testId code="orderHistoryItem_orderDetails_link">
		                                <c:choose>
		                                <c:when test="${order.isHybrisOrder eq false}">
		                                    <a href="${orderDetailsUrl}${unitId}/${ycommerce:encodeUrl(order.code)}" class="responsive-table-link">
		                                    	${fn:escapeXml(order.code)}
		                                    </a>
		                                 </c:when>
		                                 <c:otherwise>
		                                       <a href="${orderDetailsUrl}${unitId}/${ycommerce:encodeUrl(order.code)}" class="responsive-table-link">
		                                    	<spring:theme code="openorderspage.pending" />
		                                    </a>
		                                 </c:otherwise>
		                                 </c:choose>
		                                </ycommerce:testId>
		                            </div>
		                        </div>
		                        <div class="col-xs-12 col-md-2 col-sm-2">
		                            <div class="hidden-sm hidden-md hidden-lg col-xs-6  data-title">
		                            	<spring:theme code="text.account.orderHistory.purchaseOrderNumber"/>
		                            </div>
		                            <div class="col-md-12 col-sm-12  col-xs-6 data-data poNumberLength data-margin" title="${fn:escapeXml(order.purchaseOrderNumber)}">
		                                    ${fn:escapeXml(order.purchaseOrderNumber)}
		                            </div>
		                        </div>
		                        <div class="col-xs-12 col-md-2 col-sm-2">
		                            <div class="hidden-sm hidden-md hidden-lg col-xs-6  data-title ">
		                            	<spring:theme code="text.account.orderHistory.orderStatus"/>
		                            </div>
		                            <div class="col-md-12 col-sm-12  col-xs-6 statusDisplay data-data data-margin">
		                                <spring:theme code="text.account.order.status.display.${fn:escapeXml(order.statusDisplay)}"/>
		                            </div>
		                        </div>
		                        <div class="col-xs-12 col-md-2 col-sm-2">    
		                            <div class="hidden-sm hidden-md hidden-lg col-xs-6  data-title">
		                            	<spring:theme code="text.account.orderHistory.deliverytype"/>
		                            </div>
		                            <div class="col-md-12 col-sm-12  col-xs-6 data-data data-margin">
		                                <spring:theme code="text.account.order.type.display.${fn:escapeXml(order.orderType)}"/>
		                            </div>
		                        </div>
		                        <div class="col-xs-12 col-md-2 col-sm-2">   
		                            <div class="hidden-sm hidden-md hidden-lg col-xs-6 data-title">
		                            	<spring:theme code="text.account.orderHistory.datePlaced"/>
		                           	</div>
		                            <div class="col-md-12 col-sm-12  col-xs-6 data-data data-margin">
		                                <fmt:formatDate value="${order.placed}" dateStyle="long" timeStyle="short"/>
		                            </div>
		                        </div>
		                        <div class="col-xs-12 col-md-2 col-sm-2">
		                            <div class="hidden-sm hidden-md hidden-lg col-xs-6 data-title">
		                            	<spring:theme code="text.account.orderHistory.total"/>
		                            </div>
		                            <div class="col-md-12 col-sm-12  col-xs-6 data-data data-margin">
		                                <div>${fn:escapeXml(order.total.formattedValue)}</div>
		                            </div>
		                        </div>
		                       </div>
		                    </c:forEach>
					</div>
	            </div>
	            </div>
        </div>
        <div class="account-orderhistory-pagination bottom-section">
            <nav:pagination top="false" msgKey="filter" showCurrentPageInfo="true" hideRefineButton="true"
                            supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"
                            searchPageData="${searchPageData}" searchUrl="${searchUrl}"
                            numberPagesShown="${numberPagesShown}"/>
        </div>
    </div>
</c:if>

</template:page>