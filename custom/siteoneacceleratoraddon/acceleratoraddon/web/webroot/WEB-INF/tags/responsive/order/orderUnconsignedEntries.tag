<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="order" required="true" type="de.hybris.platform.commercefacades.order.data.OrderData" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<fmt:setLocale value="en_US" scope="session" />
<c:set var="unitpriceFormattedDigits" value="<%=de.hybris.platform.util.Config.getParameter(\" currency.unitprice.formattedDigits\")%>" />
<c:set var="unitpriceDigits" value="<%=de.hybris.platform.util.Config.getParameter(\" currency.unitprice.digits\")%>" />
<spring:htmlEscape defaultHtmlEscape="true" />
<c:set var="hasDeliveryFlag" value="false" />
<c:set var="hasInvoiceNumber" value="false" />
<c:set var="hasTrackingUrl" value="false" />
<c:set var="hasStatusDisplay" value="delivery" />
<spring:url value="/" var="homelink" htmlEscape="false" />
<div class="row m-b-15 displayflex flex-center hidden-lg hidden-md justify-center margin0">
	<c:set var="orderNumber" value="" />
	<c:choose>
		<c:when test="${orderData.isHybrisOrder eq false}">
			<c:set var="orderNumber" value="${fn:escapeXml(orderData.code)}" />
		</c:when>
		<c:otherwise>
			<c:set var="orderNumber">
				<spring:theme code="accountOrderDetailsOverview.pending" />
			</c:set>
		</c:otherwise>
	</c:choose>
	<div class="col-xs-10 f-s-28 f-s-20-xs-px p-l-0 text-default font-Geogrotesque print-hidden"><spring:theme code="checkout.orderConfirmation.orderNumberShort" arguments="${orderNumber}" /></div>
	<c:if test="${cmsPage.uid eq 'order'}">
		<div class="col-xs-2 f-s-14 flex-center justify-flex-end orderDetailsLinks-wrapper p-r-0 text-right">
			<button class="btn-link flex-center dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
				<common:globalIcon iconName="circles3" iconFill="none" iconColor="#78A22F" width="23" height="5" viewBox="0 0 23 5" display="" />
			</button>
			<ul class="bg-green dropdown-menu" aria-labelledby="dropdownMenu1">
				<li class="text-align-right">
					<button type="button" class="b-0-xs bg-green margin0" data-dismiss="dropdown" aria-label="Close"><span aria-hidden="true">x</span></button>
				</li>
				<li>
					<button class="btn btn-block bg-green transition-3s f-s-14-imp printOrderDetails">
						<span class="glyphicon glyphicon-print"></span>&nbsp;
						<span class="">Print</span>
					</button>
				</li>
				<li>
					<button class="btn btn-block bg-green transition-3s f-s-14-imp" id="orderEmail">
						<span class="glyphicon glyphicon-envelope"></span>&nbsp;
						<span class="">Email</span>
					</button>
				</li>
			</ul>
		</div>
	</c:if>
</div>
<div class="row margin0 bg-lightGrey border-grey text-default f-s-15 print-f-s-15 print-bg-offgray print-b-grey-2">
	<c:if test="${order.orderType eq 'PICKUP' || order.orderType eq 'FUTURE_PICKUP' }">
		<!-- invoiceNumber -->
		<c:set var="hasDeliveryFlag" value="pickup" />
		<c:if test="${order.invoiceNumber ne null}">
			<div class="col-xs-12 col-md-12 padding-md-10 padding-xs-10 padding-sm-10 order-detail-invoice hidden">
				<span class="bold order-invoice-label">
					<spring:theme code="orderUnconsignedEntries.pickupLocation" />
				</span>
				<span class="pad-sm-lft-10 pad-md-lft-10 order-invoice-number">${order.invoiceNumber}</span>
			</div>
			</c:if>
		<!-- ./invoiceNumber -->
		<!-- order-details-track -->
		<div class="col-md-3 col-sm-12 margin20 orderTrack hidden">
			<c:if test="${order.status eq 'INVOICED' }">
				<a href="#" class="order-details-track-btn"><spring:theme code="invoiced" /></a>
			</c:if>
		</div>
		<!-- ./order-details-track -->
		<div class="col-md-3 padding-30 no-padding-xs no-padding-sm b-r-grey order-height-adjust-grey">
			<div class="row margin0 p-a-15-xs p-20-sm border-light-grey print-boxshadow-none order-accordion-parent order-accordion-open disabled" onclick="ACC.global.openCloseAccordion(this,'open', 1, 'order-accordion')" data-acconum="1">
				<div class="col-xs-10 p-l-0 bold text-gray text-uppercase f-s-10 font-Geogrotesque print-text-gray"><spring:theme code="orderUnconsignedEntries.pickupLocation"/></div>
				<div class="col-xs-2 p-r-0 text-right hidden-md hidden-lg print-hidden"> 
					<span class="glyphicon glyphicon-minus green-title"></span> 
				</div>
				<div class="col-xs-12 padding0 m-t-10 font-size-14 text-default order-accordion-data-1">
					<p class="m-b-0">${order.pointOfService.name}</p>
					<p class="m-b-0">${order.pointOfService.title}</p>
					<p class="m-b-0">${order.pointOfService.address.line1}</p>
					<p class="m-b-0">${order.pointOfService.address.town}, ${order.pointOfService.address.region.isocodeShort}&nbsp;${order.pointOfService.address.postalCode}</p>
					<p class="m-b-0">${order.pointOfService.address.phone}</p>
				</div>
			</div>
		</div>
		<div class="col-md-3 padding-30 no-padding-xs no-padding-sm b-r-grey b-l-0-xs b-l-0-sm order-height-adjust-grey">
			<div class="row margin0 p-a-15-xs p-20-sm border-light-grey print-boxshadow-none b-t-0 order-accordion-parent disabled" onclick="ACC.global.openCloseAccordion(this,'close', 2, 'order-accordion')" data-acconum="2">
				<div class="col-xs-10 p-l-0 bold text-gray text-uppercase f-s-10 font-Geogrotesque print-text-gray"><spring:theme code="orderUnconsignedEntries.pickUpContact"/></div>
				<div class="col-xs-2 p-r-0 text-right hidden-md hidden-lg print-hidden"> 
					<span class="glyphicon glyphicon-plus green-title"></span> 
				</div>
				<div class="col-xs-12 padding0 m-t-10 font-size-14 text-default order-accordion-data-2">
					<p class="m-b-0 ">
						<c:set var="contactPersonName" value="${order.contactPerson.name}" />
						<c:choose>
							<c:when test="${fn:startsWith(contactPersonName, 'storecontact')}">${order.storeUserName}</c:when>
							<c:otherwise>${order.contactPerson.name}</c:otherwise>
						</c:choose>
					</p>
					<p class="m-b-0">
						<c:choose>
							<c:when test="${not empty order.contactPerson.contactNumber}">m: ${order.contactPerson.contactNumber}</c:when>
							<c:otherwise>m: NA</c:otherwise>
						</c:choose>
					</p>
					<p class="m-b-0" style="word-wrap: break-word;">
						<c:set var="contactPersonEmail" value="${order.contactPerson.email}" />
						<c:choose>
							<c:when test="${fn:startsWith(contactPersonEmail, 'storecontact')}">${order.storeContact}</c:when>
							<c:otherwise>${order.contactPerson.email}</c:otherwise>
						</c:choose>
					</p>
				</div>
			</div>
		</div>
		<div class="col-md-3 padding-30 no-padding-xs no-padding-sm b-l-grey b-r-grey b-l-0-xs b-l-0-sm order-height-adjust-grey">
			<div class="row margin0 p-a-15-xs p-20-sm border-light-grey print-boxshadow-none b-t-0 order-accordion-parent disabled" onclick="ACC.global.openCloseAccordion(this,'close', 3, 'order-accordion')" data-acconum="3">
				<div class="col-xs-10 p-l-0 bold text-gray text-uppercase f-s-10 font-Geogrotesque print-text-gray p-x-0-imp"><spring:theme code="orderUnconsignedEntries.pickUpInfo"/></div>
				<div class="col-xs-2 p-r-0 text-right hidden-md hidden-lg print-hidden"> 
					<span class="glyphicon glyphicon-plus green-title"></span> 
				</div>
				<div class="col-xs-12 padding0 m-t-10 font-size-14 text-default order-accordion-data-3">
					<p class="m-b-0 print-f-w-bold"><spring:theme code="orderUnconsignedEntries.date" />:</p>
					<p class="m-b-0">
						<fmt:formatDate var="fmtDate" value="${order.requestedDate}" pattern="MM/dd/YYYY hh:mm:ss a" />
						<input type="hidden" id="requestedDateinUTC" value="${fmtDate}" />
						<fmt:formatDate value="${order.requestedDate}" pattern="MM/dd/YYYY" />
					</p>
					<p class="m-b-0">				
						<c:choose>
							<c:when test="${order.requestedMeridian eq 'AM'}"><spring:theme code="orderSummaryPage.morning" /></c:when>
							<c:when test="${order.requestedMeridian eq 'PM'}"><spring:theme code="orderSummaryPage.afternoon" /></c:when>
							<c:otherwise>Any Time</c:otherwise>
						</c:choose>
					</p>
				</div>
			</div>
		</div>
		<div class="col-md-3 padding-20 hidden-xs hidden-sm b-l-0-xs b-l-0-sm order-height-adjust-grey print-visible"><span class="bold text-gray text-uppercase f-s-10 print-text-gray"><spring:theme code="orderUnconsignedEntries.msgForBranch"/></span>
			<p class="m-b-0 font-size-14">
				<c:choose>
					<c:when test="${not empty order.specialInstruction}">${order.specialInstruction}</c:when>
					<c:otherwise>NA</c:otherwise>
				</c:choose>
			</p>
		</div>
	</c:if>
	<c:if test="${!isSplitMixedPickupBranchOrder}">
		<c:if test="${order.orderType eq 'DELIVERY' || order.orderType eq 'STORE_DELIVERY' || order.orderType eq 'DIRECT_SHIP' || order.orderType eq 'MULTIPLE_DELIVERIES' || order.orderType eq 'PARCEL_SHIPPING'}">
			<c:set var="totalConsignments" value="${fn:length(orderData.consignments)}" />
			<c:choose>
				<c:when test="${not empty orderData.consignments}">
					<c:if test="${orderData.isServicedByDC ne true}">
						<c:set var="hasDeliveryFlag" value="consignments" />
						<input type="hidden" id="orderservicedbyInsideif" value="${order.isServicedByDC}" />
					</c:if>
					<c:forEach items="${orderData.consignments}" var="consignments" varStatus="loop">
						<!-- Tracking Url -->
						<c:if test="${consignments.trackingUrl ne null}">
							<c:set var="hasTrackingUrl" value="${fn:escapeXml(consignments.trackingUrl)}" />
							<c:if test="${consignments.statusDisplay eq 'SHIPPED'}">
								<c:set var="hasStatusDisplay" value="shiping" />
							</c:if>
						</c:if>
						<!-- ./Tracking Url -->
						<!-- shipment Info -->
						<c:if test="${consignments.invoiceNumber ne null}">
							<c:set var="hasInvoiceNumber" value="${consignments.invoiceNumber}" />
						</c:if>
						<!-- ./shipment Info -->
						<c:choose>
							<c:when test="${(consignments.deliveryMode eq 'Direct ship' or consignments.deliveryMode eq 'Store Delivery') and ( consignments.status eq 'SHIPPED' or consignments.status eq 'IN_TRANSIT' or consignments.status eq 'INVOICED' or consignments.status eq 'PROCESSING' or consignments.status eq 'SUBMITTED' or consignments.status eq 'CANCELLED')}">
								<c:set var="shipDeliverTo" value="text.order.shipto" />
								<c:set var="shipDeliverContact" value="text.order.contact" />
								<c:set var="shipDeliverInfo" value="text.order.shippinginfo" />
								<c:set var="shipDeliverInstruction" value="text.order.shipinfo" />
							</c:when>
								<c:when test="${(consignments.deliveryMode eq 'Direct ship' or consignments.deliveryMode eq 'Store Delivery') and (consignments.status eq 'COMPLETED' or consignments.status eq 'SCHEDULED_FOR_DELIVERY') }">
								<c:set var="shipDeliverTo" value="text.order.deliverto" />
								<c:set var="shipDeliverContact" value="text.order.contact" />
								<c:set var="shipDeliverInfo" value="text.order.deliverinfo" />
								<c:set var="shipDeliverInstruction" value="orderUnconsignedEntries.msgForBranch" />
							</c:when>
							<c:when test="${consignments.status eq 'READY_FOR_PICKUP' or (consignments.deliveryMode eq 'Future Pick-up' or consignments.deliveryMode eq 'Pick-up')}">
								<c:set var="shipDeliverTo" value="orderUnconsignedEntries.pickupLocation" />
								<c:set var="shipDeliverContact" value="text.order.contact" />
								<c:set var="shipDeliverInfo" value="orderUnconsignedEntries.pickUpInfo" />
								<c:set var="shipDeliverInstruction" value="orderUnconsignedEntries.msgForBranch" />
							</c:when>
							<c:otherwise>
							<c:set var="shipDeliverTo" value="orderUnconsignedEntries.DeliveryLocation" />
								<c:set var="shipDeliverContact" value="text.order.contact" />
								<c:set var="shipDeliverInfo" value="text.order.deliverinfo" />
								<c:set var="shipDeliverInstruction" value="orderUnconsignedEntries.msgForBranch" />
							</c:otherwise>
						</c:choose>
						<div class="col-md-3 padding-30 no-padding-xs no-padding-sm order-height-adjust-grey print-b-r-grey-2">
							<div class="row print-bg-offgray margin0 p-a-15-xs p-20-sm border-light-grey print-boxshadow-none order-accordion-parent order-accordion-open disabled" onclick="ACC.global.openCloseAccordion(this,'open', 1, 'order-accordion')" data-acconum="1">
								<div class="col-xs-10 p-l-0 bold text-gray print-text-gray text-uppercase f-s-10 font-Geogrotesque"><spring:theme code="${shipDeliverTo}"/></div>
								<div class="col-xs-2 p-r-0 text-right hidden-md hidden-lg print-hidden"> 
									<span class="glyphicon glyphicon-minus green-title"></span> 
								</div>
								<div class="col-xs-12 padding0 m-t-10 font-size-14 print-f-s-15 order-accordion-data-1">
									<p class="m-b-0">
										${consignments.consignmentAddress.title}
										${consignments.consignmentAddress.firstName}
										${consignments.consignmentAddress.lastName}
										${consignments.consignmentAddress.line1},
									</p>
									<c:if test="${not empty consignments.consignmentAddress.line2}">
										<p class="m-b-0">${consignments.consignmentAddress.line2},</p>
									</c:if>
									<p class="m-b-0">
										${consignments.consignmentAddress.town},
										${consignments.consignmentAddress.region.isocodeShort}&nbsp;
										${consignments.consignmentAddress.postalCode}
									</p>
									<p class="m-b-0">
										<c:choose>
											<c:when test="${not empty order.contactPerson.contactNumber}">
												m: ${order.contactPerson.contactNumber}
											</c:when>
											<c:otherwise> m:NA</c:otherwise>
										</c:choose>
									</p>
								</div>
							</div>
						</div>
						<div class="col-md-3 padding-30 no-padding-xs no-padding-sm b-l-grey b-l-0-xs b-l-0-sm order-height-adjust-grey print-b-r-grey-2">
							<div class="row print-bg-offgray margin0 p-a-15-xs p-20-sm print-boxshadow-none border-light-grey b-t-0 order-accordion-parent disabled" onclick="ACC.global.openCloseAccordion(this,'close', 2, 'order-accordion')" data-acconum="2">
								<div class="col-xs-10 p-l-0 bold text-gray print-text-gray text-uppercase f-s-10 font-Geogrotesque"><spring:theme code="${shipDeliverContact}"/></div>
								<div class="col-xs-2 p-r-0 text-right hidden-md hidden-lg print-hidden"> 
									<span class="glyphicon glyphicon-plus green-title"></span> 
								</div>
								<div class="col-xs-12 padding0 m-t-10 font-size-14 print-f-s-15 text-default order-accordion-data-2">
									<p class="m-b-0">
										<c:set var="contactPersonName" value="${order.contactPerson.name}" />
										<c:choose>
											<c:when test="${fn:startsWith(contactPersonName, 'storecontact')}">${order.storeUserName}</c:when>
											<c:otherwise>${order.contactPerson.name}</c:otherwise>
										</c:choose>
									</p>
									<p class="m-b-0">
										<c:choose>
											<c:when test="${not empty order.contactPerson.contactNumber}">m: ${order.contactPerson.contactNumber}</c:when>
											<c:otherwise>m: NA</c:otherwise>
										</c:choose>
									</p>
									<p class="m-b-0" style="word-wrap: break-word;">
										<c:set var="contactPersonEmail" value="${order.contactPerson.email}" />
										<c:choose>
											<c:when test="${fn:startsWith(contactPersonEmail, 'storecontact')}">${order.storeContact}</c:when>
											<c:otherwise>${order.contactPerson.email}</c:otherwise>
										</c:choose>
									</p>
								</div>
							</div>
						</div>
						<div class="col-md-3 padding-30 no-padding-xs no-padding-sm b-l-grey b-l-0-xs b-l-0-sm order-height-adjust-grey print-b-l-grey print-b-r-grey-2">
							<div class="row print-bg-offgray margin0 p-a-15-xs print-boxshadow-none p-20-sm border-light-grey b-t-0 order-accordion-parent disabled" onclick="ACC.global.openCloseAccordion(this,'close', 3, 'order-accordion')" data-acconum="3">
								<div class="col-xs-10 p-x-0-imp bold text-gray print-text-gray text-uppercase f-s-10 font-Geogrotesque"><spring:theme code="${shipDeliverInfo}"/></div>
								<div class="col-xs-2 p-r-0 text-right hidden-md hidden-lg print-hidden"> 
									<span class="glyphicon glyphicon-plus green-title"></span> 
								</div>
								<div class="col-xs-12 padding0 m-t-10 font-size-14 print-f-s-15 text-default order-accordion-data-3">
									<p class="m-b-0 print-f-w-bold">
										<spring:theme code="orderUnconsignedEntries.date" />:
									</p>
									<p class="m-b-0">
										<fmt:formatDate var="fmtDate" value="${consignments.requestedDate}" pattern="MM/dd/YYYY hh:mm:ss a" />
										<input type="hidden" id="requestedDateinUTC" value="${fmtDate}" />
										<span id="requestedDateInLocal"><fmt:formatDate value="${consignments.requestedDate}" pattern="MM/dd/YYYY" /></span>
									</p>
									<p class="m-b-0">
										<c:choose>
											<c:when test="${consignments.requestedMeridian eq 'AM'}"><spring:theme code="orderSummaryPage.morning" /></c:when>
											<c:when test="${consignments.requestedMeridian eq 'PM'}"><spring:theme code="orderSummaryPage.afternoon" /></c:when>
											<c:otherwise>Any Time</c:otherwise>
										</c:choose>
									</p>
								</div>
							</div>
						</div>
						<div class="col-md-3 p-20-sm print-bg-offgray padding-30 b-l-grey print-b-l-grey b-l-0-xs b-l-0-sm order-height-adjust-grey disabled order-accordion-parent"><span class="bold text-gray print-text-gray text-uppercase f-s-10"><spring:theme code="${shipDeliverInstruction}"/></span>
							<p class="m-b-0">
								<c:choose>
									<c:when test="${not empty consignments.specialInstructions}">${consignments.specialInstructions}</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${not empty order.specialInstruction}">${order.specialInstruction}</c:when>
											<c:otherwise>NA</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</p>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<div class="col-md-3 padding-30 order-height-adjust-grey"><span class="bold text-gray text-uppercase f-s-10"><spring:theme code="orderUnconsignedEntries.DeliveryLocation"/></span>
						<p class="m-b-0">
							${order.deliveryAddress.title}
							${order.deliveryAddress.firstName}
							${order.deliveryAddress.lastName}
						</p>
						<p class="m-b-0">${order.deliveryAddress.line1},</p>
						<c:if test="${not empty order.deliveryAddress.line2}"><p class="m-b-0">${order.deliveryAddress.line2},</p></c:if>
						<p class="m-b-0">
							${order.deliveryAddress.town},
							${order.deliveryAddress.region.isocodeShort}&nbsp;
						</p>
						<p class="m-b-0">${order.deliveryAddress.postalCode}</p>
						<p class="m-b-0">
							<c:choose>
								<c:when test="${not empty order.contactPerson.contactNumber}">m: ${order.contactPerson.contactNumber}</c:when>
								<c:otherwise>m: NA</c:otherwise>
							</c:choose>
						</p>
					</div>
					<div class="col-md-3 padding-30 order-height-adjust-grey"><span class="bold text-gray text-uppercase f-s-10"><spring:theme code="orderUnconsignedEntries.deliveryContact"/></span>
						<p class="m-b-0">
							<c:set var="contactPersonName" value="${order.contactPerson.name}" />
							<c:choose>
								<c:when test="${fn:startsWith(contactPersonName, 'storecontact')}">${order.storeUserName}</c:when>
								<c:otherwise>${order.contactPerson.name}</c:otherwise>
							</c:choose>
						</p>
						<p class="m-b-0">
							<c:choose>
								<c:when test="${not empty order.contactPerson.contactNumber}">m: ${order.contactPerson.contactNumber}</c:when>
								<c:otherwise>m: NA</c:otherwise>
							</c:choose>
							<c:set var="contactPersonEmail" value="${order.contactPerson.email}" />
							<c:choose>
								<c:when test="${fn:startsWith(contactPersonEmail, 'storecontact')}">${order.storeContact}<</c:when>
								<c:otherwise>${order.contactPerson.email}</c:otherwise>
							</c:choose>
						</p>
					</div>
					<div class="col-md-3 padding-30 order-height-adjust-grey"><span class="bold text-gray text-uppercase f-s-10"><spring:theme code="orderUnconsignedEntries.DeliveryInfo"/></span>
						<p class="m-b-0"><spring:theme code="orderUnconsignedEntries.date" />:</p>
						<p class="m-b-0">
							<fmt:formatDate var="fmtDate" value="${order.requestedDate}" pattern="MM/dd/YYYY hh:mm:ss a" />
							<input type="hidden" id="requestedDateinUTC" value="${fmtDate}" />
							<fmt:formatDate value="${order.requestedDate}" pattern="MM/dd/YYYY" />
						</p>
						<p class="m-b-0">
							<c:choose>
								<c:when test="${order.requestedMeridian eq 'AM'}"><spring:theme code="orderSummaryPage.morning" /></c:when>
								<c:when test="${order.requestedMeridian eq 'PM'}"><spring:theme code="orderSummaryPage.afternoon" /></c:when>
								<c:otherwise>Any Time</c:otherwise>
							</c:choose>
						</p>
					</div>
					<div class="col-md-3 padding-30 order-height-adjust-grey"><span class="bold text-gray text-uppercase f-s-10"><spring:theme code="orderUnconsignedEntries.msgForBranch"/></span>
						<p class="m-b-0">
							<c:choose>
								<c:when test="${not empty order.specialInstruction}">${order.specialInstruction}</c:when>
								<c:otherwise>NA</c:otherwise>
							</c:choose>
						</p>
					</div>
				</c:otherwise>
			</c:choose>
		</c:if>
	</c:if>
</div>
<div class="row margin0 no-margin-xs border-grey b-t-0 text-default f-s-15 font-14-xs print-hidden">
	<div class="col-md-3 col-xs-6 padding-30 p-t-15 p-15-xs b-b-grey-xs order-height-adjust-white">
		<span class="bold print-text-gray text-uppercase f-s-10">
			<spring:theme code="text.account.order.orderDetails.purchaseOrderNumber" />
		</span>
		<p class="m-b-0 l-h-20">${not empty orderData.purchaseOrderNumber && orderData.purchaseOrderNumber ne null ? orderData.purchaseOrderNumber : 'NA'}</p>
	</div>
	<ycommerce:testId code="orderDetail_overviewOrderStatus_label">
		<div class="col-md-2 col-xs-6 padding-30 p-t-15 p-15-xs b-l-grey b-b-grey-xs order-height-adjust-white hidden-md hidden-lg">
			<span class="bold print-text-gray text-uppercase f-s-10">
				<spring:theme code="text.account.orderHistory.orderStatus" />
			</span>
			<c:if test="${not empty orderData.statusDisplay}">
				<p class="m-b-0 l-h-20"><spring:theme code="text.account.order.status.display.${fn:escapeXml(orderData.statusDisplay)}" /></p>
			</c:if>
		</div>
	</ycommerce:testId>
	<div class="col-md-3 col-xs-12 padding-30 p-t-15 p-15-xs b-l-grey b-l-0-xs b-b-grey-xs order-height-adjust-white">
		<span class="bold print-text-gray text-uppercase f-s-10">
			<spring:theme code="checkout.multi.summary.placedBy" />
		</span>
		<c:set var="b2bCustomerName" value="${order.b2bCustomerData.firstName}" />
		<c:choose>
			<c:when test="false">
				<c:set var="custName" value="${order.storeUserName}" />
			</c:when>
			<c:otherwise>
				<c:set var="custName">
					<spring:theme code="text.company.user.${fn:escapeXml(order.b2bCustomerData.titleCode)}.name" text="" />${fn:escapeXml(order.b2bCustomerData.firstName)}&nbsp;${fn:escapeXml(order.b2bCustomerData.lastName)}
				</c:set>
			</c:otherwise>
		</c:choose>
		<p class="m-b-0 l-h-20">${not empty custName && custName ne null ? custName : '-' }</p>
	</div>
	<div class="col-md-2 col-xs-6 padding-30 p-t-15 p-15-xs b-l-grey b-l-0-xs order-height-adjust-white">
		<span class="bold print-text-gray text-uppercase f-s-10">
			<spring:theme code="text.account.orderHistory.datePlaced" />
		</span>
		<ycommerce:testId code="orderDetail_overviewStatusDate_label">
			<p class="m-b-0 l-h-20"><fmt:formatDate value="${order.created}" dateStyle="long" timeStyle="short" /></p>
		</ycommerce:testId>
	</div>
	<ycommerce:testId code="orderDetail_overviewOrderStatus_label">
		<div class="col-md-2 col-xs-6 padding-30 p-t-15 p-15-xs b-l-grey order-height-adjust-white hidden-xs hidden-sm">
			<span class="bold print-text-gray text-uppercase f-s-10">
				<spring:theme code="text.account.orderHistory.orderStatus" />
			</span>
			<c:if test="${not empty orderData.statusDisplay}">
				<p class="m-b-0 l-h-20"><spring:theme code="text.account.order.status.display.${fn:escapeXml(orderData.statusDisplay)}" /></p>
			</c:if>
		</div>
		<div class="col-md-2 col-xs-6 b-l-grey p-15-xs padding-30 p-t-15 order-height-adjust-white">
			<span class="bold print-text-gray text-uppercase f-s-10">
				<spring:theme code="text.account.order.total" />
			</span>
			<p class="m-b-0 l-h-20 bold">
				$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.totalPriceWithTax.value}" minFractionDigits="2" maxFractionDigits="2" />
			</p>
		</div>
	</ycommerce:testId>
	<div class="col-xs-12 b-t-grey-xs p-15-xs padding-30 p-t-15 order-height-adjust-white hidden">
		<span class="bold text-gray text-uppercase f-s-10">
			<spring:theme code="orderUnconsignedEntries.msgForBranch"/>
		</span>
		<p class="m-b-0 l-h-20">
			<c:choose>
				<c:when test="${not empty order.specialInstruction}">${order.specialInstruction}</c:when>
				<c:otherwise>NA</c:otherwise>
			</c:choose>
		</p>
	</div>
</div>
<div class="row margin0 no-margin-xs border-grey b-t-0 text-default print-f-s-13 font-14-xs hidden print-visible print-m-b-30">
	<div class="col-md-3 col-xs-6 padding-15 print-p-a-15 p-15-xs order-height-adjust-white">
		<span class="bold print-text-gray text-uppercase f-s-10">
			<spring:theme code="text.account.order.orderDetails.purchaseOrderNumber" />
		</span>
		<p class="m-b-0 l-h-20">${not empty orderData.purchaseOrderNumber && orderData.purchaseOrderNumber ne null ? orderData.purchaseOrderNumber : 'NA'}</p>
	</div>
	<div class="col-md-3 col-xs-12 padding-15 print-p-a-15 p-15-xs b-l-grey b-l-0-xs order-height-adjust-white">
		<span class="bold print-text-gray text-uppercase f-s-10">
			<spring:theme code="checkout.multi.summary.placedBy" />
		</span>
		<c:set var="b2bCustomerName" value="${order.b2bCustomerData.firstName}" />
		<c:choose>
			<c:when test="false">
				<c:set var="custName" value="${order.storeUserName}" />
			</c:when>
			<c:otherwise>
				<c:set var="custName">
					<spring:theme code="text.company.user.${fn:escapeXml(order.b2bCustomerData.titleCode)}.name" text="" />${fn:escapeXml(order.b2bCustomerData.firstName)}&nbsp;${fn:escapeXml(order.b2bCustomerData.lastName)}
				</c:set>
			</c:otherwise>
		</c:choose>
		<p class="m-b-0 l-h-20">${not empty custName && custName ne null ? custName : '-' }</p>
	</div>
	<div class="col-md-2 col-xs-6 padding-15 print-p-a-15 p-15-xs b-l-grey b-l-0-xs order-height-adjust-white">
		<span class="bold print-text-gray text-uppercase f-s-10">
			<spring:theme code="text.account.orderHistory.datePlaced" />
		</span>
		<ycommerce:testId code="orderDetail_overviewStatusDate_label">
			<p class="m-b-0 l-h-20"><fmt:formatDate value="${order.created}" dateStyle="long" timeStyle="short" /></p>
		</ycommerce:testId>
	</div>
		<ycommerce:testId code="orderDetail_overviewOrderStatus_label">
		<div class="col-md-2 col-xs-6 padding-15 print-p-a-15 p-15-xs b-l-grey order-height-adjust-white hidden-md hidden-lg">
			<span class="bold print-text-gray text-uppercase f-s-10">
				<spring:theme code="text.account.orderHistory.orderStatus" />
			</span>
			<c:if test="${not empty orderData.statusDisplay}">
				<p class="m-b-0 l-h-20"><spring:theme code="text.account.order.status.display.${fn:escapeXml(orderData.statusDisplay)}" /></p>
			</c:if>
		</div>
	</ycommerce:testId>
	<ycommerce:testId code="orderDetail_overviewOrderStatus_label">
		<div class="col-md-2 col-xs-6 padding-15 print-p-a-15 p-15-xs b-l-grey order-height-adjust-white hidden-xs hidden-sm">
			<span class="bold print-text-gray text-uppercase f-s-10">
				<spring:theme code="text.account.orderHistory.orderStatus" />
			</span>
			<c:if test="${not empty orderData.statusDisplay}">
				<p class="m-b-0 l-h-20"><spring:theme code="text.account.order.status.display.${fn:escapeXml(orderData.statusDisplay)}" /></p>
			</c:if>
		</div>
		<div class="col-md-2 col-xs-6 b-l-grey p-15-xs padding-15 print-p-a-15 order-height-adjust-white">
			<span class="bold print-text-gray text-uppercase f-s-10">
				<spring:theme code="text.account.order.total" />
			</span>
			<p class="m-b-0 l-h-20 bold print-f-s-15">
				$<fmt:formatNumber pattern="${unitpriceFormattedDigits}" value="${order.totalPriceWithTax.value}" minFractionDigits="2" maxFractionDigits="2" />
			</p>
		</div>
	</ycommerce:testId>
	<div class="col-xs-12 b-t-grey-xs p-15-xs padding-30 p-20-sm order-height-adjust-white hidden">
		<span class="bold text-gray text-uppercase f-s-10">
			<spring:theme code="orderUnconsignedEntries.msgForBranch"/>
		</span>
		<p class="m-b-0 l-h-20">
			<c:choose>
				<c:when test="${not empty order.specialInstruction}">${order.specialInstruction}</c:when>
				<c:otherwise>NA</c:otherwise>
			</c:choose>
		</p>
	</div>
</div>
<!-- Delivery Icon -->
<c:if test="${hasDeliveryFlag eq 'consignments'}">
	<div class="row m-t-20 p-a-15 print-hidden">
		<div class="col-xs-12 bg-white border-radius-3 box-shadow-20 f-s-18 f-s-12-xs-px f-s-12-sm-px flex-center p-l-70 p-y-30 p-y-15-xs p-y-15-sm p-x-10-xs p-x-10-sm">
			<img src="/_ui/responsive/theme-lambda/images/SiteOne-Delivery-Icon.png" class="display-ib width-75-px" />
			<div class="display-ib m-l-30">
				<p class="bold text-default m-b-0 deliveryCharges-cardType-print"><spring:theme code="delivery.charges.notification.cardType" /></p>
				<p class="m-b-0 deliveryCharges-notificationInfo-print"><spring:theme code="delivery.charges.notification.info" /></p>
			</div>
		</div>
	</div>
</c:if>
<!-- ./Delivery Icon -->
<div class="row address-box-print print-hidden">
	<div style="display:none" class="col-md-3 col-xs-12 item-wrapper hidden-lg hidden-md pickup-instu-col">
		<div class="item-group row">
			<div class="col-md-12 col-xs-5 md-margin-bottom black-title"><span class="bold-text specialInstruction-title">PICK-UP INSTUCTIONS</span></div>
			<div class="headline3 xs-customerName col-md-12 col-xs-7 xs-right pickUp-specialInstruction-print delivery-specialInstruction-print"></div>
		</div>
	</div>
</div>
<div class="row hidden">
	<div class="col-sm-12 col-md-3 item-action">
		<c:set var="orderCode" value="${orderData.code}" scope="request" />
		<action:actions element="div" parentComponent="${component}" />
	</div>
</div>
<div class="row m-y-20 m-y-10-xs m-y-10-sm print-hidden">
	<div class="col-md-3 f-s-22 font-Geogrotesque orderItemsDetails-headline-print text-default hidden-xs hidden-sm">
		<spring:theme code="checkout.multi.order.summary" />
	</div>
	<div class="col-md-9 text-right">
		<a href="${hasTrackingUrl}" class="bg-white bold btn btn-border-green display-block-xs display-block-sm btn-small f-s-14-imp m-r-10 m-r-0-xs-imp m-r-0-sm-imp m-b-5-xs m-b-5-sm ${hasTrackingUrl ne 'false' ? '' : 'hidden'}"><spring:theme code="text.consignment.track${hasStatusDisplay}" /></a>
		<a href="${homelink}my-account/invoice/${unitId}/${hasInvoiceNumber}?orderShipmentActualId=${orderData}orderShipmentActualId" class="bg-white bold btn btn-border-green display-block-xs display-block-sm btn-small f-s-14-imp m-r-10 m-r-0-xs-imp m-r-0-sm-imp m-b-1-xs m-b-5-sm ${hasInvoiceNumber ne false ? '' : 'hidden'}">View Invoice ${hasInvoiceNumber}</a>
		<button class="bg-green bold text-white btn btn-small b-r-l-3-imp b-r-r-3-imp f-s-14-imp hidden-xs hidden-sm" onclick ="ACC.order.addOrderToListBtn()"><spring:theme code="cartItems.addOrderList"/></button>
		<button class="bg-green bold text-white btn btn-small btn-block b-r-l-3-imp b-r-r-3-imp f-s-14-imp hidden-md hidden-lg print-hidden" onclick ="ACC.order.addOrderToListBtn()"><spring:theme code="cartItems.addOrderList"/></button>
	</div>
	<div class="col-md-3 f-s-22 font-Geogrotesque orderItemsDetails-headline-print text-default m-t-15 hidden-md hidden-lg">
		<spring:theme code="checkout.multi.order.summary" />
	</div>
</div>
<div class="row orderItemsDetails">
	<div class="col-xs-12 bg-white add-border-radius border-grey md-order-detail-padding order-detail-print">
		<div class="text-uppercase orderSummaryItems">
			<div class="hidden-xs hidden-sm">
				<div class="col-xs-12 item__list--header sec-title-bar print-hidden">
					<div class="col-md-4 item__image-title">
						<spring:theme code="basket.page.product.information" />
					</div>
					<div style="display:none" class="col-md-2 item__available-title iteamDetails-available-print">Availability</div>
					<div class="col-md-2 item__price-title">
						<spring:theme code="basket.page.price" />
					</div>
					<div class="col-md-2 item__quantity-title">
						<spring:theme code="basket.page.qty" />
					</div>
					<div class="col-md-2 item__total-title order-details-total">
						<spring:theme code="basket.page.total" />
					</div>
					<div class="col-md-2 text-center iteamDetails-addAgain-print">
						<spring:theme code="basket.add.again" />
					</div>
				</div>
				
				<div class="col-xs-12 item__list--header sec-title-bar hidden print-visible">
					<div class="col-md-2 item__title">
						<spring:theme code="text.lists.recommendedDetails.item" /> #
					</div>
					<div class="col-md-3 item__description-title">
						<spring:theme code="text.quote.description.label" />
					</div>
					<div class="col-md-3 item__available-title iteamDetails-available-print">Availability</div>
					<div class="col-md-2 item__price-title">
						<spring:theme code="basket.page.price" />
					</div>
					<div class="col-md-1 item__quantity-title">
						<spring:theme code="text.account.order.qty" />
					</div>
					<div class="col-md-1 item__total-title order-details-total">
						<spring:theme code="basket.page.total" />
					</div>
				</div>
			</div>
		</div>
		<div class="item__list item__list__cart">
			<!-- Applied Promotion Vouchers -->
			<c:if test="${order.totalDiscounts.value > 0 and not empty order.appliedVouchers}">
				<div class="applied-promotion-wrapper" style="border-bottom: 1px solid #ccc;color: red;font-size: 14pt;">
					Promotion code
					<c:forEach items="${order.appliedVouchers}" var="voucher" varStatus="loop">
						<c:choose>
							<c:when test="${fn:length(order.appliedVouchers) gt 1 and !loop.last}">${voucher},</c:when>
							<c:when test="${fn:length(order.appliedVouchers) gt 1 and loop.last}">${voucher} applied</c:when>
							<c:otherwise>${voucher} applied</c:otherwise>
						</c:choose>
					</c:forEach>
				</div>
			</c:if>
			<c:forEach items="${order.unconsignedEntries}" var="entry" varStatus="loop">
				<order:orderEntryDetails orderEntry="${entry}" order="${order}" itemIndex="${loop.index}" fromPage="order" />
			</c:forEach>
		</div>
	</div>
</div>
<!-- orderlistpoup -->
<div class="hidden" id="listpopup">
		<div class="pdplistpopupnew">
			<div class=pdplistpopup1>
				<div class='pdp-atlpopupnew-list p-b-20'><spring:theme code="cartItems.addOrderList"/></div>
				<div class="border-grey margin-bot-10-md m-b-10-xs ${not empty allWishlist?'':'hidden'}">
					<div class="popupsavedlistoptions">
						<div class="popupoptionlistitem">
							<c:forEach var="wishlists" items="${allWishlist}">
								<button type="button" class="list-group-item Ordlistoption font-size-14 f-w-400 wish-item text-default m-b-10-xs  hidden-xs" onclick="ACC.order.moveItemsToList(${wishlists.code})">${wishlists.name}</button>
							<button type="button"class="list-group-item Ordlistoptionmobile hidden-md hidden-lg hidden-sm"  onclick="ACC.order.moveItemsToList(${wishlists.code})">${wishlists.name}</button>
							</c:forEach>
						</div>
					</div>	
				</div>
				<div class='input-group margin-bot-10-md m-b-10-xs'>
					<input class='form-control createnewlistinput' id="pdppopupinput" placeholder='<spring:theme code="addToSavedList.createList" />'/>
					<div class='input-group-btn'>
						<button class="bg-primary createnewlistbtn" value="createNewListLinkProduct" data-productcode="${product.code}">+</button>
					</div>
				</div>
				<div class="pdp-newlist-popup-error pdp-newlist-popup-error-text"><spring:theme code="saved.list.duplicate"/></div>
				<p class="pdp-emptynewlist-popup-error pdp-emptynewlist-popup-error-text"><spring:theme code="saved.list.empty"/></p>
				<div class="row listpopupbtn">
					<div class="col-md-6 col-xs-5">
						<button class="btn-default listpopupclosebtn"><spring:theme code="text.cart.removeAllPopup.cancel"/></button>
					</div>
					<div class="col-md-6 col-xs-7 mob-list-save-button">
						<button class="btn-primary" onclick="ACC.order.saveContinueList()"><spring:theme code="pdp.addtolist.popup.save.and.continue"/></button>
					</div>
				</div>
			</div>
        </div>
	</div>
</div>