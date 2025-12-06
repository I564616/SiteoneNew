<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="b2b-order" tagdir="/WEB-INF/tags/addons/siteoneacceleratoraddon/responsive/order"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order"%>
<div class="row hidden margin30 p-b-20 border-bottom-greeen-5 print-visible">
	<div class="col-sm-6 p-l-0">
		<div class="site-logo-print">
			<img alt="SiteOne Logo" src="${themeResourcePath}/images/SiteOneLogo_printMode.jpg" width="250px">
		</div>
	</div>
	<div class="col-sm-6 p-r-0 text-right">
		<p class="f-s-20 text-uppercase font-Geogrotesque-bold">order for approval</p>
		<p class="print-f-s-9 text-capitalize">ordered: <fmt:formatDate value="${orderData.requestedDate}" pattern="MMM dd,YYYY" /></p>
		<p class="print-f-s-9">
			<c:set var="orderStatus" value="${orderApprovalData.b2bOrderData.status}" />
			<c:choose>
				<c:when test="${orderApprovalData.b2bOrderData.status eq 'REJECTED'}">
					<span class="indicator m-r-15 rejected-indicator"></span>
				</c:when>
				<c:when test="${orderApprovalData.b2bOrderData.status eq 'PENDING_APPROVAL'}">
					<span class="indicator m-r-15 pending-indicator"></span>
				</c:when>
				<c:otherwise>
					<span class="indicator m-r-15 approved-indicator"></span>
					<c:set var="orderStatus" value="APPROVED" />
				</c:otherwise>
			</c:choose>
			<span class="print-f-s-9 text-capitalize orderapproval-status">${fn:replace(orderStatus, '_', ' ')}</span>
		</p>
	</div>
</div>
<c:choose>
	<c:when test="${orderApprovalData.b2bOrderData.status ne 'PENDING_APPROVAL'}">
		<c:set var="fullWidthInfo" value="col-md-12 no-border"/>
		<c:set var="hidebtnSec" value="hidden" />
	</c:when>
	<c:otherwise>
		<c:set var="fullWidthInfo" value="col-md-9"/>
		<c:set var="hidebtnSec" value=""/>
	</c:otherwise>
</c:choose>
<div class="border-grey white-bg add-border-radius print-border-0">
	<div class="col-md-12 padding0">
		<div class="flex-center">
			<div class="${fullWidthInfo} col-sm-12 col-xs-12 padding-30 borderRight print-border-0 print-padding">
				<div class="col-md-3 padding0">
					<div class="black-title bold font-size-14 print-text-gray print-text-uppercase print-f-s-10">
						<spring:theme code="text.account.orderHistory.mobile.page.sort.byOrderNumber" />
					</div>
					<div class="headline3 orderapproval-number print-f-s-14 print-f-w-bold">${orderApprovalData.b2bOrderData.code}</div>

				</div>
				<div class="col-md-4 no-padding-xs no-padding-sm m-y-20-xs print-m-a-0">
					<div class="black-title bold font-size-14 print-text-gray print-text-uppercase print-f-s-10">
						<spring:theme code="checkout.multi.summary.placedBy" />
					</div>
					<div class="headline3 text-capitalize print-f-s-14 print-f-w-bold">${orderApprovalData.b2bOrderData.b2bCustomerData.name}</div>
				</div>
				<div class="col-md-3 hidden print-visible">
					<div class="black-title bold font-size-14 print-text-gray print-text-uppercase print-f-s-10">
						<spring:theme code="paymentpo.label" />
					</div>
					<div class="headline3 orderapproval-amount print-f-s-14 print-f-w-bold js-po-value" data-value="${orderApprovalData.b2bOrderData.purchaseOrderNumber}">
						<c:choose>
							<c:when test="${not empty orderApprovalData.b2bOrderData.purchaseOrderNumber}">
								${orderApprovalData.b2bOrderData.purchaseOrderNumber}
							</c:when>
							<c:otherwise>NA</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="col-md-2 no-padding-xs no-padding-sm print-text-right">
					<div class="black-title bold font-size-14 print-text-gray print-text-uppercase print-f-s-10">
						<spring:theme code="text.account.invoicelist.amount" />
					</div>
					<div class="headline3 orderapproval-amount print-f-s-14 print-f-w-bold">${orderApprovalData.b2bOrderData.totalPriceWithTax.formattedValue}</div>

				</div>
				<div class="cl"></div>
				<div class="col-md-12 padding0 marginTop20 borderTop padding-top-20 m-y-20-xs print-hidden">
					<div class="col-md-3 padding0">
						<span class="black-title bold font-size-14 m-r-15"> <spring:theme
								code="text.account.orderHistory.orderDate" /></span>
						<div class="hidden-md hidden-lg m-t-10"></div>
						<span><fmt:formatDate value="${orderData.requestedDate}"
								pattern="MMM dd,YYYY" /></span>


					</div>
					<div class="col-md-4 no-padding-xs no-padding-sm m-y-20-xs js-po-container">
						<input id="poNumberRequired" type="hidden" value="${orderApprovalData.b2bOrderData.orderingAccount.isPONumberRequired}">
						<div class="js-po-err-msg-1 hidden">Invalid PO Number</div>
						<div class="js-po-err-msg-2 hidden">PO Number required</div>
						<span class="black-title bold font-size-14 m-r-15"> <spring:theme
								code="paymentpo.label" />
						</span>
						<div class="hidden-md hidden-lg m-t-10"></div>
						<div class="inline-md po-info">
							<span class="js-po-value" data-value="${orderApprovalData.b2bOrderData.purchaseOrderNumber}">
								<c:choose>
									<c:when test="${not empty orderApprovalData.b2bOrderData.purchaseOrderNumber}">
										${orderApprovalData.b2bOrderData.purchaseOrderNumber}
									</c:when>
									<c:otherwise>NA</c:otherwise>
								</c:choose>
							</span>
							<c:if test="${orderApprovalData.b2bOrderData.status eq 'PENDING_APPROVAL'}">
								<a class="font-size-14 f-s-14-xs-pt m-l-20 pointer" onclick="ACC.accountdashboard.openApprovalPOUpdateContainer()"><spring:theme code="text.account.addressBook.edit" /></a>
							</c:if>
						</div>
						<div class="po-update-container flex-column hidden">
							<div class="flex-end-xs">
								<input class="form-control inline-md po-input"type="text" value="" placeholder="Enter PO" onkeyup="ACC.accountdashboard.onchangePOUpdate()" >
								<a class="font-size-14 f-s-14-xs-pt m-l-20 m-r-5 pointer" onclick="ACC.accountdashboard.saveApprovalPOUpdate()">
									<spring:theme code="estimate.save" />
								</a>
								<span class="font-size-14 f-s-12-xs-pt">or</span>
								<a class="font-size-14 f-s-14-xs-pt m-l-5 pointer js-po-cancel hidden" onclick="ACC.accountdashboard.cancelApprovalPOUpdate()">
									<spring:theme code="estimate.cancel" />	
								</a>
								<a class="font-size-14 f-s-14-xs-pt m-l-5 pointer js-po-delete hidden" onclick="ACC.accountdashboard.deleteApprovalPOUpdate()">
									<spring:theme code="estimate.delete" />
								</a>
							</div>
							<div class="m-t-5 hidden text-red js-po-err-msg"></div>
						</div>
					</div>
					<div
						class="col-md-3 no-padding-xs no-padding-sm flex-center m-y-20-xs">
						<c:set var="orderStatus" value="${orderApprovalData.b2bOrderData.status}" />
						<c:choose>
							<c:when test="${orderApprovalData.b2bOrderData.status eq 'REJECTED'}">
								<span class="indicator m-r-15 rejected-indicator"></span>
							</c:when>
							<c:when test="${orderApprovalData.b2bOrderData.status eq 'PENDING_APPROVAL'}">
								<span class="indicator m-r-15 pending-indicator"></span>
							</c:when>
							<c:otherwise>
								<span class="indicator m-r-15 approved-indicator"></span>
								<c:set var="orderStatus" value="APPROVED" />
							</c:otherwise>
						</c:choose>
						<span class="orderapproval-status">${fn:replace(orderStatus, '_', ' ')}</span>
					</div>
				</div>
			</div>

			<div class="col-md-3 padding-30 hidden-xs hidden-sm ${hidebtnSec}">


				<b2b-order:approveOrderAction />
				<div class="marginTop10">
					<b2b-order:rejectOrderAction />
				</div>
			</div>

			<div class="cl"></div>
		</div>
	</div>
	<div class="cl"></div>
</div>

<div class="col-md-12 hidden-md hidden-lg row print-hidden">
<div class="marginTop35 row">
				<b2b-order:approveOrderAction />
				<div class="marginTop10">
					<b2b-order:rejectOrderAction />
				</div>
				</div>
			</div>
			
<div class="col-xs-12 marginTopBVottom30  margin30 padding0">
	<div class="title-bar order-confirmation-page-bottom flex-center print-p-a-10 print-left-0 print-bg-offgray">
		<div class="col-xs-10 order-summary-title padding0">
			<h1 class="order-confirmation-page-title order-review-title-wrapper print-m-a-0 print-f-s-16 print-f-w-bold print-text-uppercase">
				<spring:theme code="text.account.order.title.details" />
			</h1>
		</div>
		<div class="col-md-2 col-xs-2 hidden-xs text-right text-green bold">
		<div class="row">
			<span class="col-xs-12 text-right pointer" onclick="window.print()">
				<span class="glyphicon glyphicon-print marginrgt5"></span><spring:theme code="assemblyDetailsPage.print" />
			</span>
			<span class="col-xs-4 padding0 hidden">
				<span class="glyphicon glyphicon-envelope marginrgt5"></span><spring:theme code="assemblyDetailsPage.email" />
			</span>
			</div>
		</div>
	</div>
</div>


<div class="col-xs-12 page-orderSummaryPage print-f-s-11">
	<div class="row">
		<div class="col-xs-12 add-border-radius add-border-grey white-bg print-border-0 print-p-a-0">
			<!-- PICKUP Section  -->
			<c:if test="${orderData.orderType eq 'PICKUP' || order.orderType eq 'FUTURE_PICKUP' }">
				<div class="row PickupInformation">
					<div class="col-xs-12 padding0">
						<c:if test="${orderData.invoiceNumber ne null}">
							<div class="col-xs-12 col-md-12 padding-md-10  padding-xs-10  padding-sm-10">
								<span class="bold"><spring:theme code="invoicedetailpage.invoice.number" />:</span> <span class="pad-sm-lft-10 pad-md-lft-10">${orderData.invoiceNumber}</span>
							</div>
						</c:if>
						<div class="col-md-3 col-sm-12 col-print-4">
							<div class="secondary-title print-m-t-0 print-m-b-10 bold-text print-text-gray print-text-uppercase print-f-s-10">
								<spring:theme code="orderUnconsignedEntries.pickupLocation" />
							</div>
							<div class="pickup-location-details">
								<p class="margin0">
									<span class="bold">${orderData.pointOfService.name}</span>
								</p>
								<p class="margin0">
									<span class="bold">${orderData.pointOfService.title}</span>
								</p>
								<p class="margin0">
									<span>${orderData.pointOfService.address.line1}</span>
								</p>
								<p class="margin0">
									<span>${orderData.pointOfService.address.town},
										${orderData.pointOfService.address.region.isocodeShort}&nbsp;
										${orderData.pointOfService.address.postalCode}</span>
								</p>
								<p class="margin0">
									<span><a class="tel-phone"
										href="tel:${orderData.pointOfService.address.phone}">${orderData.pointOfService.address.phone}</a></span>
								</p>
								<div class="margin20 print-m-a-0">
									<a id="getDirection" href=""
										data-url="${orderData.pointOfService.address.line1},${orderData.pointOfService.address.town},${orderData.pointOfService.address.region.isocodeShort},${orderData.pointOfService.address.postalCode}"><spring:theme
											code="choosePickupDeliveryMethodPage.get.direction" /></a></span>
								</div>
							</div>
						</div>
						<div class="col-md-3 col-sm-12 col-print-5">
							<div class="secondary-title print-m-t-0 print-m-b-10 bold-text print-text-gray print-text-uppercase print-f-s-10">
								<spring:theme code="orderUnconsignedEntries.pickUpContact" />
							</div>
							<div>
								<c:set var="contactPersonName"
									value="${orderData.contactPerson.name}" />
								<c:choose>
									<c:when
										test="${fn:startsWith(contactPersonName, 'storecontact')}">
				    ${orderData.storeUserName}</br>
									</c:when>
									<c:otherwise>
				  ${orderData.contactPerson.name}</br>
									</c:otherwise>
								</c:choose>

								<c:choose>
									<c:when test="${not empty order.contactPerson.contactNumber}">
					    m: <a class="tel-phone"
											href="tel:${orderData.contactPerson.contactNumber}">${orderData.contactPerson.contactNumber}</a>
										<br>
									</c:when>
									<c:otherwise>
				 		 m:NA<br>
									</c:otherwise>
								</c:choose>


								<c:set var="contactPersonEmail"
									value="${orderData.contactPerson.email}" />
								<c:choose>
									<c:when
										test="${fn:startsWith(contactPersonEmail, 'storecontact')}">
						${orderData.storeContact}
					 </c:when>
									<c:otherwise>
										<a href="mailto:${orderData.contactPerson.email}">${orderData.contactPerson.email}</a>
										<br>
									</c:otherwise>
								</c:choose>

							</div>

						</div>
						<div class="col-md-3 col-sm-12 col-print-3 pickup-info-wrapper">
							<div class="secondary-title print-m-t-0 print-m-b-10 bold-text print-text-gray print-text-uppercase print-f-s-10">
								<spring:theme code="orderSummaryPage.date.time" />
							</div>
							<div class="pickup-information-details">
								<!--  <p class="message-header message-header"><spring:theme code="orderSummaryPage.date.time" />:</p>-->
								<fmt:formatDate var="fmtDate" value="${orderData.requestedDate}"
									pattern="MMM dd,YYYY hh:mm:ss a" />
								<input type="hidden" id="requestedDateinUTC" value="${fmtDate}" />
								<span id="requestedDateInLocal"><fmt:formatDate
										value="${orderData.requestedDate}" pattern="MMM dd,YYYY" /></span> <BR>

								<c:if test="${orderData.requestedMeridian eq 'AM'}">
									<spring:theme code="orderSummaryPage.morning" />
								</c:if>
								<c:if test="${orderData.requestedMeridian eq 'PM'}">
									<spring:theme code="orderSummaryPage.afternoon" />
								</c:if>

							</div>
						</div>

						<div class="col-xs-12 margin20">
							<span class="order-message">
								<span class="message-header additionalInfo bold print-text-gray print-text-uppercase print-f-s-10"><spring:theme code="orderunconsignedentries.pickUpInstruction" />:</span>
								<span class="print-d-b">
									<c:choose>
										<c:when test="${not empty order.specialInstruction}">
										${orderData.specialInstruction}
										</c:when>
										<c:otherwise>NA</c:otherwise>
									</c:choose>
								</span>
							</span>
						</div>
					</div>
				</div>
				<div class="marginrow">
					<div class="col-md-12 pick-up-mesg-wrapper marginbottomp30 print-bg-offgreen print-f-s-11">
						<span class="pick-up-mesg"><spring:theme
								code="orderSummaryPage.pickup.front.message" />&nbsp;${orderData.pointOfService.name}&nbsp;<spring:theme
								code="orderSummaryPage.pickup.end.message" /></span>
					</div>
				</div>
			</c:if>
			<!-- ./PICKUP Section  -->
			<!-- DELIVERY Section  -->
			<c:if test="${orderData.orderType eq 'DELIVERY' || orderData.orderType eq 'STORE_DELIVERY' || orderData.orderType eq 'DIRECT_SHIP'  || orderData.orderType eq 'MULTIPLE_DELIVERIES' }">
				<div class="row">
					<div class="col-md-3 col-sm-12 col-print-4">
						<div class="secondary-title print-m-t-0 print-m-b-10 bold-text print-text-gray print-text-uppercase print-f-s-10">
							<spring:theme code="orderUnconsignedEntries.DeliveryLocation" />
						</div>
						<div class="delivery-location-details">
							<p class="margin0">
								<span>${orderData.deliveryAddress.title}</span> <span>${orderData.deliveryAddress.firstName}</span>
								<span>${orderData.deliveryAddress.lastName}</span>
							</p>
							<p class="margin0">
								<span>${orderData.deliveryAddress.line1},</span>
							</p>
							<c:if test="${not empty orderData.deliveryAddress.line2}">
								<p class="margin0">
									<span>${orderData.deliveryAddress.line2},</span>
								</p>
							</c:if>
							<p class="margin0">
								<span>${orderData.deliveryAddress.town},</span> <span>${orderData.deliveryAddress.region.isocodeShort}&nbsp;</span>
							</p>
							<p class="margin0">
								<span>${orderData.deliveryAddress.postalCode}</span><br>
							</p>
							<c:choose>
								<c:when
									test="${not empty orderData.contactPerson.contactNumber}">
									<p class="margin0">
										<span> m:<a class="tel-phone"
											href="tel:${orderData.contactPerson.contactNumber}">${orderData.contactPerson.contactNumber}</a>
										</span>
									</p>
								</c:when>
								<c:otherwise>
				 		 m:NA<br>
								</c:otherwise>
							</c:choose>
							<div class="margin20 print-m-a-0">
								<a id="getDirection" href=""
									data-url="${orderData.pointOfService.address.line1},${orderData.pointOfService.address.town},${orderData.pointOfService.address.region.isocodeShort},${orderData.pointOfService.address.postalCode}">
									<spring:theme code="choosePickupDeliveryMethodPage.get.direction" /></a>
							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-12 col-print-5">
						<div class="secondary-title print-m-t-0 print-m-b-10 bold-text print-text-gray print-text-uppercase print-f-s-10">
							<spring:theme code="orderUnconsignedEntries.deliveryContact" />
						</div>
						<div>
							<c:set var="contactPersonName"
								value="${orderData.contactPerson.name}" />
							<c:choose>
								<c:when
									test="${fn:startsWith(contactPersonName, 'storecontact')}">
				    ${orderData.storeUserName}<div class="cl"></div>
								</c:when>
								<c:otherwise>
				  ${orderData.contactPerson.name}<div class="cl"></div>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when
									test="${not empty orderData.contactPerson.contactNumber}">
					    m: <a class="tel-phone"
										href="tel:${orderData.contactPerson.contactNumber}">${orderData.contactPerson.contactNumber}</a>
									<div class="cl"></div>
								</c:when>
								<c:otherwise>
				 		 m:NA<div class="cl"></div>
								</c:otherwise>
							</c:choose>

							<c:set var="contactPersonEmail"
								value="${orderData.contactPerson.email}" />
							<c:choose>
								<c:when
									test="${fn:startsWith(contactPersonEmail, 'storecontact')}">
					    ${orderData.storeContact}<div class="cl"></div>
								</c:when>
								<c:otherwise>
									<a href="mailto:${orderData.contactPerson.email}">${orderData.contactPerson.email}</a>
									<div class="cl"></div>
									<%-- ${order.contactPerson.email}<br> --%>
								</c:otherwise>
							</c:choose>

						</div>

					</div>
					<div class="col-md-3 col-sm-12 col-print-3">
						<div class="secondary-title print-m-t-0 print-m-b-10 bold-text print-text-gray print-text-uppercase print-f-s-10">
							<spring:theme code="text.account.order.type.display.DELIVERY" />&nbsp;<spring:theme code="orderUnconsignedEntries.date" />
						</div>
						<div>
							<fmt:formatDate var="fmtDate" value="${orderData.requestedDate}"
								pattern="MM/dd/YYYY hh:mm:ss" />
							<input type="hidden" id="requestedDateinUTC" value="${fmtDate}" />
							<span id="requestedDateInLocal"><fmt:formatDate
									value="${orderData.requestedDate}" pattern="MM/dd/YYYY" /></span> <BR>

							<c:choose>
								<c:when test="${orderData.requestedMeridian eq 'AM'}">
									<spring:theme code="orderSummaryPage.morning" />
								</c:when>
								<c:when test="${orderData.requestedMeridian eq 'PM'}">
									<spring:theme code="orderSummaryPage.afternoon" />
								</c:when>
								<c:otherwise>
								Any Time
							</c:otherwise>
							</c:choose>
						</div>
					</div>

					<div class="col-xs-12 margin20 padding0">
						<div class="col-md-3">
							<p class="message-header additionalInfo bold print-text-gray print-text-uppercase print-f-s-10">
								<spring:theme code="orderunconsignedentries.deliveryInstruction" />
								:
							</p>
						</div>
						<div class="col-md-9 col-print-12">

							<p class="order-message">
								<c:choose>
									<c:when test="${not empty orderData.specialInstruction}">
							${orderData.specialInstruction}
						</c:when>
									<c:otherwise>NA</c:otherwise>
								</c:choose>
							</p>
						</div>
					</div>
				</div>
				<div class="marginrow">
						<div class="col-md-12 pick-up-mesg-wrapper marginbottomp30 print-bg-offgreen print-f-s-11">
							<span class="pick-up-mesg"><spring:theme
									code="orderSummaryPage.pickup.front.message" />&nbsp;${orderData.pointOfService.name}&nbsp;<spring:theme
									code="orderSummaryPage.delivery.end.message" /></span>
						</div>
					</div>
			</c:if>
			<!-- ./DELIVERY Section  -->
			<!-- SHIPPING Section  -->
			<c:if test="${orderData.orderType eq 'PARCEL_SHIPPING'}">

				<div class="row">

					<div class="col-md-3 col-sm-12 col-print-4">
						<div class="secondary-title print-m-t-0 print-m-b-0 bold-text print-text-gray print-text-uppercase print-f-s-10">
							<spring:theme code="orderSummaryPage.parcel.shipping.to.contact" />:
						</div>
						<div class="margin-top-20 print-m-t-10">

							${orderData.contactPerson.name}<br>
							<c:choose>
								<c:when
									test="${not empty orderData.contactPerson.contactNumber}">
									<a class="tel-phone"
										href="tel:${orderData.contactPerson.contactNumber}">${orderData.contactPerson.contactNumber}</a>
									<br>
								</c:when>
								<c:otherwise>
									<spring:theme code="orderSummaryPage.na" />
									<br>
								</c:otherwise>
							</c:choose>
							${orderData.contactPerson.email}<br>

						</div>
					</div>

					<div class="col-md-3 col-sm-12 col-print-4">
						<div class="secondary-title margin-zero print-m-t-0 print-m-b-10 bold-text print-text-gray print-text-uppercase print-f-s-10">&nbsp;</div>
						<div class="margin-top-20">
							${orderData.deliveryAddress.line1},<br>
							<c:if test="${not empty orderData.deliveryAddress.line2}"> 
						${orderData.deliveryAddress.line2},<br>
							</c:if>
							${orderData.deliveryAddress.town},&nbsp;
							${orderData.deliveryAddress.region.isocodeShort}&nbsp;
							${orderData.deliveryAddress.postalCode}<br>



							<%-- m:${cartData.deliveryAddress.phone}<br> --%>
						</div>
					</div>
				</div>
				<div class="marginrow">
					<div
						class="col-md-12 pick-up-mesg-wrapper marginTop40 marginbottomp30 print-bg-offgreen print-f-s-11">
						<span class="pick-up-mesg"><spring:theme
								code="orderSummaryPage.parcel.shipping.message" /></span>
					</div>
				</div>
			</c:if>
			<div class="cl"></div>
			<div class="row hidden print-visible print-m-b-5 bold-text print-text-uppercase print-f-s-10">
				<div class="col-sm-6 print-text-gray">Product</div>
				<div class="col-sm-2 print-text-gray">price</div>
				<div class="col-sm-2 print-text-gray text-center">quantity</div>
				<div class="col-sm-2 print-text-gray text-align-right">totals</div>
			</div>
			<div class="row print-m-a-0 print-border-top-1-gray print-border-bottom-1-gray orderapprovalItemdata">
				<c:forEach items="${orderData.unconsignedEntries}" var="entry" varStatus="loop">
					<order:orderEntryDetails orderEntry="${entry}" order="${order}" itemIndex="${loop.index}" />
				</c:forEach>
			</div>
			<div class="cl"></div>
		</div>
	</div>
</div>