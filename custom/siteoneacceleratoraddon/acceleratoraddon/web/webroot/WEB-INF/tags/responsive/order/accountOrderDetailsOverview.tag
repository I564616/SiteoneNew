<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="order" required="true" type="de.hybris.platform.commercefacades.order.data.OrderData" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>

<spring:htmlEscape defaultHtmlEscape="true" />
<div class="row">
    <div class="col-sm-12 col-md-12 col-no-padding">
        <div class="row">
            <div class="col-sm-4 col-xs-12 item-wrapper">
                <div class="item-group">
                    <ycommerce:testId code="orderDetail_overviewOrderID_label">
                        <span class="item-label"><b><spring:theme code="text.account.orderHistory.orderNumber"/></b></span>
                        <span class="item-value">${fn:escapeXml(orderData.code)}</span>
                    </ycommerce:testId>
                </div>
                 <div class="item-group">
                 <ycommerce:testId code="orderDetail_overviewPurchaseOrderNumber_label">
                 <c:choose>
                    <c:when test="${orderData.paymentType.code=='ACCOUNT' and not empty orderData.purchaseOrderNumber}">
                            <span class="item-label"><b><spring:theme code="text.account.order.orderDetails.purchaseOrderNumber"/></b></span>
                            <span class="item-value">${fn:escapeXml(orderData.purchaseOrderNumber)}</span>
                    </c:when>
                    <c:otherwise>
                            <span class="item-label"><b><spring:theme code="text.account.order.orderDetails.purchaseOrderNumber"/></span></b>
                            <span class="item-value">NA</span>
                    </c:otherwise>
                    </c:choose>
                 </ycommerce:testId>
                </div>
                <div class="item-group">
                    <ycommerce:testId code="orderDetail_overviewOrderStatus_label">
                        <span class="item-label"><b><spring:theme code="text.account.orderHistory.orderStatus"/></span></b>
                        <c:if test="${not empty orderData.statusDisplay}">
                            <span class="item-value"><spring:theme code="text.account.order.status.display.${fn:escapeXml(orderData.statusDisplay)}"/></span>
                        </c:if>
                    </ycommerce:testId>
                </div>
            </div>
            <div class="col-sm-4 col-xs-12  item-wrapper">
                <div class="item-group">
                    <ycommerce:testId code="orderDetail_overviewStatusDate_label">
                        <span class="item-label"><b><spring:theme code="text.account.orderHistory.datePlaced"/></span></b>
                        <span class="item-value"><fmt:formatDate value="${order.created}" dateStyle="medium" timeStyle="short"/></span>
                    </ycommerce:testId>
                </div>
                <div class="item-group">
                    <ycommerce:testId code="orderDetail_overviewPlacedBy_label">
                        <span class="item-label"><b><spring:theme code="checkout.multi.summary.orderPlacedBy"/></span></b>
                        <span class="item-value"><spring:theme code="text.company.user.${fn:escapeXml(order.b2bCustomerData.titleCode)}.name" text=""/>&nbsp;${fn:escapeXml(order.b2bCustomerData.firstName)}&nbsp;${fn:escapeXml(order.b2bCustomerData.lastName)}</span>
                    </ycommerce:testId>
                </div>
               <%--  <div class="item-group">
                    <c:if test="${orderData.paymentType.code=='ACCOUNT'}">
                        <ycommerce:testId code="orderDetail_overviewParentBusinessUnit_label">
                            <span class="item-label"><spring:theme code="text.account.order.orderDetails.ParentBusinessUnit"/></span>
                            <span class="item-value">${fn:escapeXml(orderData.costCenter.unit.name)}</span>
                        </ycommerce:testId>
                    </c:if>
                </div> --%>
            </div>
            <div class="col-sm-4 col-xs-12  item-wrapper">
                <%-- <div class="item-group">
                    <c:if test="${orderData.paymentType.code=='ACCOUNT'}">
                        <ycommerce:testId code="orderDetail_overviewCostCenter_label">
                            <span class="item-label"><spring:theme code="text.account.order.orderDetails.CostCenter"/></span>
                            <span class="item-value">${fn:escapeXml(orderData.costCenter.name)}</span>
                        </ycommerce:testId>
                    </c:if>
                </div> --%>
                <div class="item-group">
                    <ycommerce:testId code="orderDetail_overviewOrderTotal_label">
                        <span class="item-label"><b><spring:theme code="text.account.order.total"/></span></b>
                        <span class="item-value"><format:price priceData="${order.totalPrice}"/></span>
                    </ycommerce:testId>
                </div>
            </div>
        </div>
    </div>

    <div class="col-sm-12 col-md-3 item-action">
        <c:set var="orderCode" value="${orderData.code}" scope="request"/>
        <action:actions element="div" parentComponent="${component}"/>
    </div>
</div>

