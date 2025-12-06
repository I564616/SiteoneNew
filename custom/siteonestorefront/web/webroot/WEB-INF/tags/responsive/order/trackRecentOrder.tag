
<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="pagination" tagdir="/WEB-INF/tags/responsive/nav/pagination" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>

<spring:url value="/my-account/order/" var="orderDetailsUrl" htmlEscape="false"/>

 
<c:if test="${not empty ordersInTransit}">
<div class="heading2 col-xs-12 black-title  marginTop35 hidden">
<spring:theme code="text.orders.in.transit"/></div>
<div class="track-order-table col-xs-12  margin20 hidden">

	<div class="track-order-table__header row hidden-xs hidden-sm">
		<div
			class="track-order-table__heading col-md-2 col-sm-2 track-order-table__heading--item">
			<spring:theme code="text.account.orderHistory.mobile.page.sort.byOrderNumber" />
		</div>
		<div
			class="track-order-table__heading col-md-2 col-sm-2 track-order-table__heading--item">
			<spring:theme code="text.account.orderHistory.page.sort.byInvoiceNumber" />
		</div>
		<div
			class="track-order-table__heading col-md-1 col-sm-2 track-order-table__heading--item no-padding-md">
			<spring:theme code="text.account.orderHistory.page.sort.byPONumber" />
		</div>
		<div
			class="track-order-table__heading col-md-3 col-sm-2 track-order-table__heading--item">
			<spring:theme code="text.tms.order.status" />
		</div>
		<div
			class="track-order-table__heading col-md-2 col-sm-2 track-order-table__heading--item">
			<spring:theme code="text.tms.fulfilment.method" />
		</div>
		<div
			class="track-order-table__heading col-md-1 col-sm-2 track-order-table__heading--item no-padding-md">
			<spring:theme code="text.account.orderHistory.datePlaced" />
		</div>
		<div
			class="track-order-table__heading col-md-1 col-sm-1 track-order-table__heading--item track-order-table__heading--total">
			<spring:theme code="text.account.quote.total" />
		</div>

	</div>


	<c:forEach items="${ordersInTransit}" var="transitOrders" varStatus="loop"> 
		
		<div class="track-order-table__values row ">
			
			<div
				class="track-order-table__data col-md-2 col-sm-12 col-xs-12 track-order-table__heading--item">
				<div class="track-order-table__data--mobileHeader">
										
					<div
						class="col-md-12 col-sm-12 col-xs-12 no-padding-lg no-padding-md no-padding-sm track-order-table__data--ordernumber">
						<span class="show-tracking-details md-product-details hidden-xs hidden-sm" data-order="${transitOrders.code}"  data-state="expand"><span class='icon-plus-circle'></span></span>
						 <a href="${orderDetailsUrl}${unit.uid}/${ycommerce:encodeUrl(transitOrders.code)}" class="link-green">
	                                    	${fn:escapeXml(transitOrders.code)}
	                                    </a></div> 
				</div>
				
			</div>
			<div
				class="track-order-table__data col-md-2 col-sm-12 col-xs-12 track-order-table__heading--item">
				<div class="track-order-table__data--mobileHeader">
					<div class="hidden-md hidden-lg col-sm-5 col-xs-5 bold">
						<spring:theme code="text.account.orderHistory.page.sort.byInvoiceNumber" />
					</div>
					<div
						class="col-md-12 col-sm-7  col-xs-7 no-padding-lg no-padding-md no-padding-sm xs-right">
							${transitOrders.invoiceNumber}
						</div>
				</div>
			</div>
			<div
				class="track-order-table__data col-md-1 col-sm-12 col-xs-12 track-order-table__heading--item no-padding-md">
				<div class="track-order-table__data--mobileHeader">
					<div class="hidden-md hidden-lg col-sm-5 col-xs-5 bold">
						<spring:theme code="text.account.orderHistory.page.sort.byPONumber" />
					</div>
					<div
						class="col-md-12 col-sm-7  col-xs-7 no-padding-lg no-padding-md no-padding-sm xs-right">

						${transitOrders.purchaseOrderNumber}</div>
				</div>
			</div>
			<div
				class="track-order-table__data col-md-3 col-sm-12 col-xs-12 track-order-table__heading--item">
				<div class="track-order-table__data--mobileHeader">
					<div class="hidden-md hidden-lg col-sm-5 col-xs-5 bold">
						<spring:theme code="text.tms.order.status" />
					</div>
					<div
						class="col-md-12 col-sm-7  col-xs-7 no-padding-lg no-padding-md no-padding-sm xs-right">
						<c:set var="orderStatus" value="${transitOrders.status}"/>
						<spring:theme code="text.account.track.order.status.display.${fn:escapeXml(orderStatus)}"/>
						<span class="hidden-xs hidden-sm"><order:trackShippingPill consignments="${transitOrders.consignments}"/></span>
	                		</div>
                   			<div class="col-xs-12 show-pill-xs hidden-lg hidden-md">
               					<order:trackShippingPill consignments="${transitOrders.consignments}"/>
               		</div>
				</div>
				<div class="hidden-lg hidden-md acc-dashboard-mobile-tms">
				<c:if test="${not empty transitOrders.consignments and transitOrders.consignments.size() gt 1}">
					<c:forEach items="${transitOrders.consignments}" var="consignments" varStatus="loop">
					 	<order:orderConsignmentsData consignments="${consignments}" orderNumber="${transitOrders.code}" />
					</c:forEach>
				</c:if>
				</div>
			</div>
			<div
				class="track-order-table__data col-md-2 col-sm-12 col-xs-12 track-order-table__heading--item">
				<div class="track-order-table__data--mobileHeader">
					<div class="hidden-md hidden-lg no-padding-rgt-xs col-sm-5 col-xs-5 bold">
						<spring:theme code="text.tms.fulfilment.method" />
					</div>
					<div
						class="col-md-12 col-sm-7  col-xs-7 no-padding-lg no-padding-md no-padding-sm xs-right">
						<spring:theme code="text.account.track.order.type.display.${fn:escapeXml(transitOrders.orderType)}"/>
					</div>
				</div>
			</div>
			<div
				class="track-order-table__data col-md-1 col-sm-12 col-xs-12 track-order-table__heading--item no-padding-md">
				<div class="track-order-table__data--mobileHeader">
					<div class="hidden-md hidden-lg col-sm-5 col-xs-5 bold">
						<spring:theme code="text.account.orderHistory.datePlaced" />
					</div>
					<div
						class="col-md-12 col-sm-7  col-xs-7 no-padding-lg no-padding-md no-padding-sm xs-right">

						<fmt:formatDate value="${transitOrders.created}" dateStyle="long" timeStyle="short" pattern="MMM dd,yyyy"/></div>
				</div>
			</div>
			<div
				class="track-order-table__data col-md-1 col-sm-12 col-xs-12 track-order-table__heading--item track-order-table__data--total">
				<div class="track-order-table__data--mobileHeader">
					<div class="hidden-md hidden-lg col-sm-5 col-xs-5 bold">
						<spring:theme code="text.account.quote.total" />
					</div>
					<div
						class="col-md-12 col-sm-7  col-xs-7 no-padding-lg no-padding-md no-padding-sm xs-right">

						<div>${fn:escapeXml(transitOrders.totalPriceWithTax.formattedValue)}</div></div>
				</div>
				
			</div>
			<a href="#" class="col-xs-12 pad-xs-lft-30 no-text-decoration bold xs-product-details show-tracking-details hidden-md hidden-lg" data-order="${transitOrders.code}"  data-state="expand">
				<span class='icon-plus-circle'></span> <spring:theme code="text.account.order.title.details"/>
			</a>
			
			<div class="cl"></div>
			
			<div class="hidden-xs hidden-sm">
			<c:if test="${not empty transitOrders.consignments and transitOrders.consignments.size() gt 1}">
			<div class="transit-bottom-margin margin20"></div>
			<c:forEach items="${transitOrders.consignments}" var="consignments" varStatus="loop">
			 
			<order:orderConsignmentsData consignments="${consignments}" orderNumber="${transitOrders.code}" />
			</c:forEach>
			</c:if>
			</div>
			<div class="cl"></div>
			<div class="transit-product-details hidden">
			<div class="transit-product-details__header col-xs-12 hidden-xs hidden-sm">
		                       <div class="title-bar">
			                       <div class="col-md-4"><spring:theme code="basket.page.itemInfo"/></div>
			                       <div class="col-md-2"><spring:theme code="basket.page.availability"/></div>
			                       <div class="col-md-2"><spring:theme code="basket.page.price"/></div>
			                       <div class="col-md-1"><spring:theme code="basket.page.quantity"/></div>
			                       <div class="col-md-1"><spring:theme code="basket.page.total"/></div>
			                       <div class="col-md-2"></div>
		                       </div>
		                       </div>
 
		            <c:if test="${transitOrders.isPartOfMasterHybrisOrder eq true}">
              	<div class="col-md-12 col-xs-12 no-padding-md">
	                  <div class="no-padding-rgt-xs no-padding-md marginTop10 col-md-2 col-sm-12 col-md-offset-10">
	                  
	                  	<a href="${masterOrderDetailsUrl}${transitOrders.orderingAccount.uid}/${ycommerce:encodeUrl(transitOrders.hybrisOrderNumber)}" class="btn btn-default pull-right view-entire-order"><spring:theme code="view.entire.order.btn" /></a>
	                   
	                  </div>
                  </div>
               </c:if>           
			<div class="col-xs-12 text-center">
				<a href="#" class="close-product no-text-decoration bold"><spring:theme code="text.tms.close.orderdetails"/></a>			
			</div>
		     </div>
		</div>

	</c:forEach> 
	</div>
	</c:if>
