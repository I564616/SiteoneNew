<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="b2b-order" tagdir="/WEB-INF/tags/addons/siteoneacceleratoraddon/responsive/order"%>
<c:choose>
	<c:when test="${cmsPage.uid eq 'order-approval-details'}">
		<div class="col-md-4 padding0">
			<div class="row flex-dir-column-reverse-xs">
				<div class="col-md-6 col-xs-12 padding0 margintop20-md"><b2b-order:rejectOrderAction /></div>
				<div class="col-md-6 col-xs-12 padding0 margintop20"><b2b-order:approveOrderAction /></div>
			</div>
		</div>
	</c:when>
	<c:otherwise>


		<div class="col-md-4">
			<div class="row">
				<div class="col-md-6 col-xs-12 hidden-sm hidden-xs no-padding-xs no-padding-sm margintop20 hide-when-print">
					<a href="" class="btn btn-default btn-block printOrderDetails">
						<spring:theme code="checkout.multi.view.my.orders.print" />
					</a>
				</div>

				<c:choose>
					<c:when test="${orderData.guestCustomer eq true}">
						<div class="col-md-6 col-xs-12  no-padding-xs no-padding-sm margintop20 hidden-sm hidden-xs hide-when-print">
							<a href="<c:url value=" /request-account/form" />" class="btn btn-primary btn-block"
							onclick="">
							<spring:theme code="guest.register" /></a>
						</div>
					</c:when>
					<c:otherwise>
						<div class="col-md-6 col-xs-12  hidden-sm hidden-xs no-padding-xs no-padding-sm margintop20 hide-when-print">
							<c:url var="accountorders" value="/my-account/orders" />
							<a href="${accountorders}/${sessionShipTo.uid}" data-key="orderhistorypage"
								data-active="orderHistoryTab" class=" btn btn-primary btn-block orders-tab orders-link">
								<spring:theme code="checkout.multi.view.my.orders" />
							</a>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</c:otherwise>
</c:choose>