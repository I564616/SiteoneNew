<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="order" required="true" type="de.hybris.platform.commercefacades.order.data.OrderData" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>

<spring:htmlEscape defaultHtmlEscape="true" />

<div class="well well-quinary well-xs">
   <%--  <div class="well-headline">
        <spring:theme code="text.account.order.unconsignedEntry.status.pending" />
    </div>
    <div class="well-content">
        <div class="row">
            <div class="col-md-5 order-ship-to">
                <div class="label-order"><spring:theme code="text.account.order.shipto" /></div>
                <div class="value-order"><order:addressItem address="${order.deliveryAddress}"/></div>
            </div>
        </div>
    </div> --%>

   <%--  <div class="well-content">
        <div class="row">
            <div class="col-md-5 order-shipping-method">
                <div class="label-order"><spring:theme code="text.shippingMethod" /></div>
                <div class="value-order">${orderData.deliveryMode.name}<br>${orderData.deliveryMode.description}</div>
            </div>
        </div>
    </div> --%>
</div>
	 <c:if test="${order.orderType eq 'PICKUP'}"> 
     	<table class="PickupInformation">
	
		<tr>
			<td colspan="10"><h2><spring:theme code="orderUnconsignedEntries.pickUpInfo" /></h2></td>
			
		</tr>
	
	
				<tr>
					<td colspan="6">
						<h3><spring:theme code="orderUnconsignedEntries.pickupLocation" /></h3><br><br>
							
							 ${order.pointOfService.name}<br>
							 ${order.pointOfService.title}<br>
							 
							 ${order.pointOfService.address.line1}<br>
							 ${order.pointOfService.address.postalCode}<br>
							 ${order.pointOfService.address.phone}
							 
							 
					</td>
					<td colspan="7">
							<h3><spring:theme code="orderUnconsignedEntries.pickUpContact" /></h3><br><br>
							    ${order.contactPerson.name}<br>
							    m: ${order.contactPerson.contactNumber}<br>
							    ${order.contactPerson.email}<br>
					</td>
					<td colspan="8">
						<h3><spring:theme code="orderUnconsignedEntries.pickUpInfo" />:</h3><br><br>
						<h4><spring:theme code="orderUnconsignedEntries.date" />:</h4>
						<fmt:formatDate var="fmtDate" value="${order.requestedDate}" pattern="MM/dd/YYYY hh:mm:ss a"/>
						<input type="hidden" id="requestedDateinUTC"  value="${fmtDate}"/>
						<span id="requestedDateInLocal"></span>
						<BR>
						<h4><spring:theme code="orderUnconsignedEntries.crossRoads" /></h4>
						<h4><spring:theme code="orderunconsignedentries.pickUpInstruction" />:</h4>
					</td>
					
					<td colspan="9">
					<h3><spring:theme code="orderUnconsignedEntries.accountNumber" /></h3><br><br>
						${order.orderingAccount.uid}<br>
					</td>
				</tr>
		
	</table>
 </c:if>
 <c:if test="${order.orderType eq 'DELIVERY'}"> 
     <table class="deliveryInformations">
	
		<tr>
			<td colspan="10"><h2><spring:theme code="orderUnconsignedEntries.DeliveryInfo" /></h2>
			
			</td>
		</tr>
				<tr>
					<td colspan="6">
						<h3><spring:theme code="invoicedetailpage.del.loc" />:</h3><br><br>
						${order.deliveryAddress.title}&nbsp;
						${order.deliveryAddress.firstName}&nbsp;
			            ${order.deliveryAddress.lastName}<br>
						${order.deliveryAddress.line1}<br>
						${order.deliveryAddress.line2}<br>
						${order.deliveryAddress.town}<br>
						${order.deliveryAddress.region.name}
					     ${order.deliveryAddress.country.name}&nbsp;      
						${order.deliveryAddress.postalCode}<br>
						m:${order.deliveryAddress.phone}<br>
					</td>
					<td colspan="7">
							<h3><spring:theme code="orderUnconsignedEntries.deliveryContact" /></h3><br><br>
							    ${order.contactPerson.name}<br>
							    m: ${order.contactPerson.contactNumber}<br>
							    ${order.contactPerson.email}<br>
					</td>
					<td colspan="8">
						<h3><spring:theme code="orderUnconsignedEntries.DeliveryInfo" />:</h3><br><br>
						<h4><spring:theme code="orderUnconsignedEntries.date" /></h4>
						<fmt:formatDate var="fmtDate" value="${order.requestedDate}" pattern="MM/dd/YYYY hh:mm:ss a"/>
						<input type="hidden" id="requestedDateinUTC"  value="${fmtDate}"/>
						<span id="requestedDateInLocal"></span>
						<BR>
						<h4><spring:theme code="orderUnconsignedEntries.crossRoads" />:</h4>
						<h4><spring:theme code="orderunconsignedentries.pickUpInstruction" />:</h4>
					</td>
					
					<td colspan="9">
					<h3><spring:theme code="orderUnconsignedEntries.accountNumber" /></h3><br><br>
					      ${order.orderingAccount.uid}<br>
					</td>
				</tr>
		
	</table>
	</c:if>
<table class="deliveryPO">
	  <c:if test="${not empty order.purchaseOrderNumber}">
		<tr>
		
			<td colspan="6"><h2><spring:theme code="orderUnconsignedEntries.poNumber" /></h2></td>
		</tr>
				<tr>
					<td colspan="3">
						<h3><spring:theme code="orderUnconsignedEntries.poNumber1" /></h3>
							${order.purchaseOrderNumber}<br>
					</td>
				</tr>
				</c:if>
</table>
<ul class="item__list">
    <li class="hidden-xs hidden-sm">
        <ul class="item__list--header">
            <li class="item__toggle"></li>
            <li class="item__image"></li>
            <li class="item__info"><spring:theme code="basket.page.item"/></li>
            <li class="item__price"><spring:theme code="basket.page.price"/></li>
            <li class="item__quantity"><spring:theme code="basket.page.qty"/></li>
            <li class="item__total--column"><spring:theme code="basket.page.total"/></li>
        </ul>
    </li>
	<c:forEach items="${order.unconsignedEntries}" var="entry" varStatus="loop">
        <order:orderEntryDetails orderEntry="${entry}" order="${order}" itemIndex="${loop.index}"/>
	</c:forEach>
</ul>