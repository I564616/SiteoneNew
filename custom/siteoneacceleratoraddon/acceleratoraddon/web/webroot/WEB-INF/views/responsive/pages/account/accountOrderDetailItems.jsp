<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="b2b-order" tagdir="/WEB-INF/tags/addons/siteoneacceleratoraddon/responsive/order" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<spring:url value="/" var="homelink" htmlEscape="false" />
<c:set var="currentShipmentIndex" value="${param.index != null ? param.index : 1}" />
<c:if test="${orderData.shipmentCount > 1}">
    <div class="shipment-details-secs flex justify-between flex-center m-b-20">
        <div class="left-sec width-100-pe-xs">
            <div class="shipment-nav flex-center justify-center border-radius-3 p-x-10 p-y-5 print-hidden">
                <span class="f-s-16 f-w-700 font-Arial text-dark-gray">Shipments:</span>
                <ul class="nav nav-pills p-l-10" id="shipmentTabs">
                    <c:forEach begin="1" end="${orderData.shipmentCount}" var="i">
                        <li role="presentation"
                            class="shipment-tab p-x-3 <c:if test='${i == currentShipmentIndex}'>active</c:if> f-w-700 border-radius-4 text-dark-gray transition-3s"
                            data-index="${i-1}">
                            <c:choose>
                                <c:when test="${i == currentShipmentIndex}">
                                    <p class="bg-green text-white margin0 f-s-16 p-x-15 p-y-5 border-radius-4">${i}</p>
                                </c:when>
                                <c:otherwise>
                                    <a class="text-dark-gray margin0 p-y-5 f-s-16" href="${homelink}${orderData.shipmentCodes[i-1]}?branchNo=${orderData.pointOfService.storeId}&shipmentCount=${orderData.shipmentCount}&index=${i}">${i}</a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="right-sec hidden-xs">
            <span class="f-w-500 f-s-20 text-dark-gray" id="shipmentInfo">
                You're viewing shipment ${currentShipmentIndex} of ${orderData.shipmentCount}
                for order #${fn:escapeXml(orderData.code)}
            </span>
        </div>
    </div>
</c:if>
<div class="account-orderdetail account-consignment">
    <ycommerce:testId code="orderDetail_itemList_section">   
        <c:if test="${not empty orderData.unconsignedEntries and  cmsPage.uid eq 'order'}">
            <b2b-order:orderUnconsignedEntries order="${orderData}"/>
        </c:if>
        <c:if test="${not empty orderData.unconsignedEntries and cmsPage.uid eq 'orderConfirmationPage' && isMixedCartEnabled eq false}">
           <b2b-order:orderUnconsignedEntriesConfirmation order="${orderData}"/>
        </c:if>
        <c:if test="${not empty orderData.unconsignedEntries and cmsPage.uid eq 'orderConfirmationPage' && isMixedCartEnabled eq true}">
           <b2b-order:orderUnconsignedEntriesConfirmationMixedCart order="${orderData}"/>
        </c:if>
    </ycommerce:testId>
</div>