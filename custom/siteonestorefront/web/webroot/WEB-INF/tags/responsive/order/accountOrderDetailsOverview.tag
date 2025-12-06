<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="order" required="true" type="de.hybris.platform.commercefacades.order.data.OrderData" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<fmt:setLocale value="en_US" scope="session"/>
<c:set var="unitpriceFormattedDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.unitprice.formattedDigits\")%>" />
<c:set var="unitpriceDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.unitprice.digits\")%>" />
<spring:htmlEscape defaultHtmlEscape="true" />
<div class="row print-hidden">
    <div class="col-sm-12 col-md-12 col-no-padding">
        <div class="row address-box-print">
            <div style="display:none" class="col-md-3 col-xs-12 item-wrapper hidden-lg hidden-md pickup-instu-col">
                <div class="item-group row">
                    <div class="col-md-12 col-xs-5 md-margin-bottom black-title"><span class="bold-text specialInstruction-title">PICK-UP INSTUCTIONS</span></div>
                    <div class="headline3 xs-customerName col-md-12 col-xs-7 xs-right pickUp-specialInstruction-print delivery-specialInstruction-print"></div>
                </div>
            </div>
            <div class="col-md-3 col-xs-12 item-wrapper poNumber-col">
                
                <div class="item-group row poNumber-label">
                 <ycommerce:testId code="orderDetail_overviewPurchaseOrderNumber_label">
                 <c:choose>
                    <c:when test="${not empty orderData.purchaseOrderNumber}">
                            <div class="col-xs-5 black-title"><span class="bold-text"><spring:theme code="text.account.order.orderDetails.purchaseOrderNumber"/></span></div>
                            <div class="xs-right col-xs-7">${orderData.purchaseOrderNumber}</div>
                    </c:when>
                    <c:otherwise>
                            <div class="col-xs-5 black-title"><span class="bold-text"><spring:theme code="text.account.order.orderDetails.purchaseOrderNumber"/></span></div>
                            <div class="xs-right col-xs-7">NA</div>
                    </c:otherwise>
                    </c:choose>
                 </ycommerce:testId>
                </div>
                <div class="item-group row order-dateplaced">
                    <ycommerce:testId code="orderDetail_overviewStatusDate_label">
                        <div class="col-xs-5 black-title"><span class="bold-text"><spring:theme code="text.account.orderHistory.datePlaced"/></span></div>
                        <div class="xs-right col-xs-7"><fmt:formatDate value="${order.created}" dateStyle="long" timeStyle="short"/></div>
                    </ycommerce:testId>
                </div>

                <div style="display:none" class="item-group row poNumber-label-print">
                 <ycommerce:testId code="orderDetail_overviewPurchaseOrderNumber_label">
                 <c:choose>
                    <c:when test="${not empty orderData.purchaseOrderNumber}">
                            <div class="col-md-12 col-xs-5 md-margin-bottom black-title"><span class="bold-text"><spring:theme code="text.account.order.orderDetails.purchaseOrderNumber"/></span></div>
                            <div class="headline3 xs-customerName col-md-12 col-xs-7 xs-right">${orderData.purchaseOrderNumber}</div>
                    </c:when>
                    <c:otherwise>
                            <div class="col-md-12 col-xs-5 md-margin-bottom black-title"><span class="bold-text"><spring:theme code="text.account.order.orderDetails.purchaseOrderNumber"/></span></div>
                            <div class="headline3 xs-customerName col-md-12 col-xs-7 xs-right">NA</div>
                    </c:otherwise>
                    </c:choose>
                 </ycommerce:testId>
                </div>
            </div>
            <div class="col-md-3 col-xs-12 item-wrapper placedby-col">
                
                
                <div class="item-group row">
                
                <c:set var = "b2bCustomerName" value = "${order.b2bCustomerData.firstName}"/>
                <c:choose>
	                <c:when test="false">
	                	<c:set var="custName" value="${order.storeUserName}"/>
	                </c:when>
	                 <c:otherwise>
	                 	<c:set var="custName"><spring:theme code="text.company.user.${fn:escapeXml(order.b2bCustomerData.titleCode)}.name" text=""/>${fn:escapeXml(order.b2bCustomerData.firstName)}&nbsp;${fn:escapeXml(order.b2bCustomerData.lastName)}</c:set>
	                 </c:otherwise>
                 </c:choose>
                 <div class="col-md-12 col-xs-5 md-margin-bottom black-title">
                 	<span class="bold-text"><spring:theme code="checkout.multi.summary.placedBy"/></span>	                         
                 </div>                         
               	
                   <div class="headline3 xs-customerName col-md-12 col-xs-7 xs-right">${custName}</div>
                            
                        
                        
                 </div>
            </div>
            <div class="col-md-3 col-xs-12 item-wrapper hidden-lg hidden-md date-placed-col">
                <div class="item-group row">
                    <ycommerce:testId code="orderDetail_overviewStatusDate_label">
                        <div class="col-md-12 col-xs-5 md-margin-bottom black-title"><span class="bold-text"><spring:theme code="text.account.orderHistory.datePlaced"/></span></div>
                        <div class="headline3 xs-customerName col-md-12 col-xs-7 xs-right"><fmt:formatDate value="${order.created}" dateStyle="long" timeStyle="short"/></div>
                    </ycommerce:testId>
                </div>
            </div>
            <div class="col-md-3 col-xs-12 item-wrapper status-col">
           
                    <ycommerce:testId code="orderDetail_overviewOrderStatus_label">
                         <div class="item-group row">
	                         <div class="col-md-12 col-xs-5 md-margin-bottom black-title">
	                         <span class="bold-text"><spring:theme code="text.account.orderHistory.orderStatus"/></span>
	                         <span class="hidden-xs hidden-sm"><order:trackShippingPill consignments="${orderData.consignments}"/></span>
	                         </div>
	                         <c:if test="${not empty orderData.statusDisplay}">
                        	
                            	<div class="xs-right headline3 col-md-12 col-xs-7"><spring:theme code="text.account.order.status.display.${fn:escapeXml(orderData.statusDisplay)}"/></div>
                            
                        </c:if>
                        <div class="col-xs-12 show-pill-xs hidden-lg hidden-md">
                        <order:trackShippingPill consignments="${orderData.consignments}"/>
                        </div>
                         </div>
                        
                    </ycommerce:testId>
                </div>
			
			
            <div class="col-md-3 col-xs-12 item-wrapper total-col">
                    <ycommerce:testId code="orderDetail_overviewOrderTotal_label">
                    	<div class="item-group row">
                        	<div class="col-md-12 col-xs-5 md-margin-bottom black-title"><span class="bold-text"><spring:theme code="text.account.order.total"/></span></div>
                        
               

		            <div class="xs-right headline3 col-md-12 col-xs-7">
						$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.totalPriceWithTax.value}" minFractionDigits="2"  maxFractionDigits="2" />
	                </div>
	                        

		        
                </div>  
                    </ycommerce:testId>
               
                
        </div>
        
    </div>

    <div class="col-sm-12 col-md-3 item-action">
        <c:set var="orderCode" value="${orderData.code}" scope="request"/>
        <action:actions element="div" parentComponent="${component}"/>
    </div>
</div>
</div>