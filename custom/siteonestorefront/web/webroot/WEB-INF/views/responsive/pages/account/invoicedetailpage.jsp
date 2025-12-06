<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>


<spring:url value="/" var="homelink" htmlEscape="false"/>

<template:page pageTitle="${pageTitle}">
	<!-- Invoice top section starts here  -->
	<section class="invoice-detail-header">
		<!-- Paybill for Mobile -->
		<c:if test="${contPayBillOnline && acctPayBillOnline}">
			<div class="text-align-right m-b-15 hidden-md hidden-lg">
				<a href="${homelink}my-account/pay-account-online"
					class="bold btn btn-white-border transition-3s f-s-15  text-uppercase account-pay-account-online"
					target="redirectToBT">
					<span class="bg-primary display-ib flex-center justify-center">
						<common:globalIcon iconName="paybill" iconFill="none" iconColor="#FFFFFF" width="18" height="21"
							viewBox="0 0 18 21" display="" />
					</span>
					<spring:theme code="text.account.payBillOnline.btn" />
				</a>
			</div>
		</c:if>

		<div class="row displayflex flex-center justify-center margin0 no-padding-hrz-xs order-detail-headline p-b-15-xs p-t-0-xs p-y-20 hidden-xs hidden-sm">
			<div class="col-md-6 col-xs-10 f-s-28 f-s-20-xs-px p-l-0 text-default font-Geogrotesque">
				<spring:theme code="text.account.invoicedetails.heading" />
			</div>
			<div class="col-md-6 col-sm-12 col-xs-12 f-s-14 flex-center justify-flex-end invoiceDetailsLinks-wrapper p-r-0 p-y-10 text-right hidden-xs hidden-sm">
				<c:if test="${not empty invoiceData.orderShipmentActualId}">
					<a href="${homelink}my-account/invoicePDF/${invoiceData.invoiceNumber}/${invoiceData.orderShipmentActualId}"
						download="invoice.pdf" target="_blank" class="flex-center">
						<span class="glyphicon glyphicon-print"></span>&nbsp;
						<span class="underline-text">
							<spring:theme code="invoicedetailpage.print.details" />
						</span>
					</a>
				</c:if>
				<a href="#" class="flex-center" id="invoiceEmail">
					<span class="glyphicon glyphicon-envelope"></span>&nbsp;
					<span class="underline-text">
						<spring:theme code="invoicedetailpage.email.details" />
					</span>
				</a>
				<c:if test="${contPayBillOnline && acctPayBillOnline}">
					<a href="${homelink}my-account/pay-account-online"
						class="bold btn btn-white-border transition-3s f-s-15  text-uppercase account-pay-account-online"
						target="redirectToBT">
						<span class="bg-primary display-ib flex-center justify-center">
							<common:globalIcon iconName="paybill" iconFill="none" iconColor="#FFFFFF" width="18"
								height="21" viewBox="0 0 18 21" display="" />
						</span>
						<spring:theme code="text.account.payBillOnline.btn" />
					</a>
				</c:if>
			</div>
		</div>
	</section>
	<div class="row displayflex margin0 no-padding-hrz-xs order-detail-headline p-b-15-xs p-t-0-xs p-y-20 flex-center hidden-md hidden-lg">
		<div class="col-md-6 col-xs-10 f-s-28 f-s-20-xs-px p-l-0 text-default font-Geogrotesque">
			<spring:theme code="text.account.invoicedetails.heading" />
		</div>
		<div class="col-xs-2 f-s-14 hidden-lg hidden-md  flex justify-flex-end invoiceDetailsLinks-wrapper p-r-0 text-right">
			<button class="btn-link flex-center dropdown-toggle p-r-0-imp" type="button" id="dropdownMenu1"
				data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
				<common:globalIcon iconName="circles3" iconFill="none" iconColor="#78A22F" width="23" height="5"
					viewBox="0 0 23 5" display="" />
			</button>
			<ul class="bg-green border-radius-3 dropdown-menu padding-zero width-110-px left-auto text-white "
				id="invoice-dropdown" aria-labelledby="dropdownMenu1">
				<li class="text-align-right p-t-10 p-r-10">
					<svg xmlns="http://www.w3.org/2000/svg" width="10" height="10" viewBox="0 0 10 10" fill="none">
						<path
							d="M6.89545 5L9.73835 2.1571C10.0872 1.80824 10.0872 1.24261 9.73835 0.893466L9.10653 0.261648C8.75767 -0.0872159 8.19205 -0.0872159 7.8429 0.261648L5 3.10455L2.1571 0.261648C1.80824 -0.0872159 1.24261 -0.0872159 0.893466 0.261648L0.261648 0.893466C-0.0872159 1.24233 -0.0872159 1.80795 0.261648 2.1571L3.10455 5L0.261648 7.8429C-0.0872159 8.19176 -0.0872159 8.75739 0.261648 9.10653L0.893466 9.73835C1.24233 10.0872 1.80824 10.0872 2.1571 9.73835L5 6.89545L7.8429 9.73835C8.19176 10.0872 8.75767 10.0872 9.10653 9.73835L9.73835 9.10653C10.0872 8.75767 10.0872 8.19205 9.73835 7.8429L6.89545 5Z"
							fill="white" />
					</svg>
				</li>
				<li>
					<button class="btn white p-t-0-imp bg-green transition-3s f-s-14-imp">
						<c:if test="${not empty invoiceData.orderShipmentActualId}">
							<a href="${homelink}my-account/invoicePDF/${invoiceData.invoiceNumber}/${invoiceData.orderShipmentActualId}"
								download="invoice.pdf" target="_blank" class="flex-center m-l-0-imp m-b-0-imp white">
								<span class="glyphicon glyphicon-print"></span>&nbsp;
								<span>
									<spring:theme code="invoicedetailpage.print" />
								</span>
							</a>
						</c:if>
					</button>
				</li>
				<li>
					<button class="btn white p-t-0-imp bg-green transition-3s f-s-14-imp" id="orderEmail">
						<span class="glyphicon glyphicon-envelope"></span>&nbsp;
						<span class="">
							<spring:theme code="invoicedetailpage.mail" />
						</span>
					</button>
				</li>
			</ul>
		</div>
	</div>
	<c:choose>
		<c:when test="${invoiceData.deliveryMode eq 'Customer Pick up' or invoiceData.deliveryMode eq 'pickup'}">
			<div class="row margin0 bg-lightGrey border-grey text-default f-s-15 print-f-s-15 print-bg-offgray print-b-grey-2">
				<div class="col-md-3 padding-30 no-padding-xs no-padding-sm b-r-grey order-height-adjust-grey a">
					<div class="row margin0 border-semi-light-grey p-a-15-xs p-20-sm print-boxshadow-none order-accordion-parent order-accordion-open disabled"
						onclick="ACC.global.openCloseAccordion(this,'open', 1, 'order-accordion')" data-acconum="1">
						<div class="col-xs-10 p-l-0 bold text-gray text-uppercase f-s-10 f-s-16-xs-px Geogrotesque-medium-xs print-text-gray">
							<spring:theme code="invoicedetailpage.pickup.loc" />
						</div>
						<div class="col-xs-2 p-r-0 text-right hidden-md hidden-lg print-hidden">
							<span class="glyphicon glyphicon-minus green-title"></span>
						</div>
						<div class="col-xs-12 padding0 m-t-5 font-size-14 text-default order-accordion-data-1">
							<p class="m-b-0">${invoiceData.storeName}</p>
							<c:choose>
								<c:when test="${not empty invoiceData.storeTitle}">
									<p class="m-b-0">${invoiceData.storeTitle}</p>
								</c:when>
								<c:otherwise>
									<p class="m-b-0">
										<spring:theme code="invoicedetailpage.na" />
									</p>
								</c:otherwise>
							</c:choose>
							<p class="m-b-0">${invoiceData.branchAddress.line1}</p>
							<p class="m-b-0">${invoiceData.address.town},
								${invoiceData.address.region.isocodeShort}&nbsp;${invoiceData.address.postalCode}</p>
							<p class="m-b-0">${invoiceData.branchAddress.phone}</p>
						</div>
					</div>
				</div>
				<div class="col-md-4 padding-30 no-padding-xs no-padding-sm b-r-grey b-l-0-xs b-l-0-sm order-height-adjust-grey b">
					<div class="row margin0 border-semi-light-grey p-a-15-xs p-20-sm print-boxshadow-none b-t-0 order-accordion-parent disabled"
						onclick="ACC.global.openCloseAccordion(this,'close', 2, 'order-accordion')" data-acconum="2">
						<div
							class="col-xs-10 p-l-0 bold text-gray text-uppercase f-s-10 f-s-16-xs-px Geogrotesque-medium-xs print-text-gray">
							<spring:theme code="invoicedetailpage.pickup.contact" />
						</div>
						<div class="col-xs-2 p-r-0 text-right hidden-md hidden-lg print-hidden">
							<span class="glyphicon glyphicon-plus green-title"></span>
						</div>
						<div class="col-xs-12 padding0 m-t-5 font-size-14 text-default order-accordion-data-2">
							<p class="m-b-0 ">
								<c:choose>
									<c:when test="${not empty invoiceData.contact_firstName}">
										${invoiceData.contact_firstName}
									</c:when>
									<c:otherwise>name:
										<spring:theme code="invoicedetailpage.na" /><br>
									</c:otherwise>
								</c:choose>
							</p>
							<p class="m-b-0">
								<c:choose>
									<c:when test="${not empty invoiceData.contact_phone}">
										${invoiceData.contact_phone}
									</c:when>
									<c:otherwise>m:
										<spring:theme code="invoicedetailpage.na" /><br>
									</c:otherwise>
								</c:choose>
							</p>
							<p class="m-b-0" style="word-wrap: break-word;">
								<c:choose>
									<c:when test="${not empty invoiceData.contact_emailId}">
										${invoiceData.contact_emailId}
									</c:when>
									<c:otherwise>e:
										<spring:theme code="invoicedetailpage.na" /><br>
									</c:otherwise>
								</c:choose>
							</p>
						</div>
					</div>
				</div>
				<div class="col-md-2 padding-30 no-padding-xs no-padding-sm b-r-grey b-l-0-xs b-l-0-sm order-height-adjust-grey c">
					<div class="row margin0 border-semi-light-grey p-a-15-xs p-20-sm print-boxshadow-none b-t-0 order-accordion-parent disabled"
						onclick="ACC.global.openCloseAccordion(this,'close', 3, 'order-accordion')" data-acconum="3">
						<div
							class="col-xs-10 p-l-0 bold text-gray text-uppercase f-s-10 f-s-16-xs-px Geogrotesque-medium-xs print-text-gray p-x-0-imp">
							<spring:theme code="invoicedetailpage.pickup.info" />
						</div>
						<div class="col-xs-2 p-r-0 text-right hidden-md hidden-lg print-hidden">
							<span class="glyphicon glyphicon-plus green-title"></span>
						</div>
						<div class="col-xs-12 padding0 m-t-5 font-size-14 text-default order-accordion-data-3">
							<p class="m-b-0 print-f-w-bold">
								<spring:theme code="invoicedetailpage.date.time" />:
							</p>
							<p class="m-b-0">
								<c:choose>
									<c:when test="${not empty invoiceData.pickupOrDeliveryDateTime}">
										<fmt:formatDate var="fmtDate" value="${invoiceData.pickupOrDeliveryDateTime}"
											pattern="MM/dd/YYYY" />
										<input type="hidden" id="requestedDateinUTC" value="${fmtDate}" />
										${fmtDate}
									</c:when>
									<c:otherwise>
										<spring:theme code="invoicedetailpage.na" />
									</c:otherwise>
								</c:choose>
							</p>
						</div>
					</div>
				</div>
				<div class="col-md-3 padding-20 hidden-xs hidden-sm b-l-0-xs b-l-0-sm order-height-adjust-grey print-visible d">
					<span class="bold text-gray text-uppercase f-s-10 f-s-16-xs-px Geogrotesque-medium-xs print-text-gray">
						<spring:theme code="invoicedetailpage.msg" />
					</span>
					<p class="m-b-0 font-size-14">
						<c:choose>
							<c:when test="${not empty invoiceData.instructions}">${invoiceData.instructions}</c:when>
							<c:otherwise>
								<spring:theme code="invoicedetailpage.na" />
							</c:otherwise>
						</c:choose>
					</p>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="row margin0 bg-lightGrey border-grey text-default f-s-15 print-f-s-15 print-bg-offgray print-b-grey-2">
				<div class="col-md-3 padding-30 no-padding-xs no-padding-sm b-r-grey order-height-adjust-grey a">
					<div class="row margin0 border-semi-light-grey p-a-15-xs p-20-sm print-boxshadow-none order-accordion-parent order-accordion-open disabled"
						onclick="ACC.global.openCloseAccordion(this,'open', 1, 'order-accordion')" data-acconum="1">
						<div class="col-xs-10 p-l-0 bold text-gray text-uppercase f-s-10 f-s-16-xs-px Geogrotesque-medium-xs print-text-gray">
							<spring:theme code="invoicedetailpage.del.loc" />
						</div>
						<div class="col-xs-2 p-r-0 text-right hidden-md hidden-lg print-hidden">
							<span class="glyphicon glyphicon-minus green-title"></span>
						</div>
						<div class="col-xs-12 padding0 m-t-5 font-size-14 text-default order-accordion-data-1">
							<c:choose>
								<c:when test="${not empty invoiceData.address}">
									<p class="m-b-0">${invoiceData.address.town},
										${invoiceData.address.region.isocodeShort}
										#${invoiceData.branchNumber }</p>
									<p class="m-b-0">${invoiceData.address.companyName}</p>
									<p class="m-b-0">${invoiceData.address.line1}</p>
									<p class="m-b-0">
										${invoiceData.address.town},&nbsp;${invoiceData.address.region.isocodeShort}&nbsp;${invoiceData.address.postalCode}
									</p>
								</c:when>
								<c:otherwise>
									<spring:theme code="invoicedetailpage.na" /><br>
								</c:otherwise>
							</c:choose>
							<p class="m-b-0">
								<c:choose>
									<c:when test="${not empty invoiceData.address.phone}">
										${invoiceData.address.phone}
									</c:when>
									<c:otherwise>
										<spring:theme code="invoicedetailpage.na" />
									</c:otherwise>
								</c:choose>
							</p>
						</div>
					</div>
				</div>
				<div class="col-md-4 padding-30 no-padding-xs no-padding-sm b-r-grey b-l-0-xs b-l-0-sm order-height-adjust-grey b">
					<div class="row margin0 border-semi-light-grey p-a-15-xs p-20-sm print-boxshadow-none b-t-0 order-accordion-parent disabled"
						onclick="ACC.global.openCloseAccordion(this,'close', 2, 'order-accordion')" data-acconum="2">
						<div
							class="col-xs-10 p-l-0 bold text-gray text-uppercase f-s-10 f-s-16-xs-px Geogrotesque-medium-xs print-text-gray">
							<spring:theme code="invoicedetailpage.del.contact" />
						</div>
						<div class="col-xs-2 p-r-0 text-right hidden-md hidden-lg print-hidden">
							<span class="glyphicon glyphicon-plus green-title"></span>
						</div>
						<div class="col-xs-12 padding0 m-t-5 font-size-14 text-default order-accordion-data-2">
							<p class="m-b-0 ">
								<c:choose>
									<c:when test="${not empty invoiceData.contact_firstName}">
										${invoiceData.contact_firstName}
									</c:when>
									<c:otherwise>name:
										<spring:theme code="invoicedetailpage.na" /><br>
									</c:otherwise>
								</c:choose>
							</p>
							<p class="m-b-0">
								<c:choose>
									<c:when test="${not empty invoiceData.contact_phone}">
										${invoiceData.contact_phone}
									</c:when>
									<c:otherwise>m:
										<spring:theme code="invoicedetailpage.na" /><br>
									</c:otherwise>
								</c:choose>
							</p>
							<p class="m-b-0" style="word-wrap: break-word;">
								<c:choose>
									<c:when test="${not empty invoiceData.contact_emailId}">
										${invoiceData.contact_emailId}
									</c:when>
									<c:otherwise>e:
										<spring:theme code="invoicedetailpage.na" /><br>
									</c:otherwise>
								</c:choose>
							</p>
						</div>
					</div>
				</div>
				<div class="col-md-2 padding-30 no-padding-xs no-padding-sm b-r-grey b-l-0-xs b-l-0-sm order-height-adjust-grey c">
					<div class="row margin0 border-semi-light-grey p-a-15-xs p-20-sm print-boxshadow-none b-t-0 order-accordion-parent disabled"
						onclick="ACC.global.openCloseAccordion(this,'close', 3, 'order-accordion')" data-acconum="3">
						<div class="col-xs-10 p-l-0 bold text-gray text-uppercase f-s-10 f-s-16-xs-px Geogrotesque-medium-xs print-text-gray p-x-0-imp">
							<spring:theme code="invoicedetailpage.del.info" />
						</div>
						<div class="col-xs-2 p-r-0 text-right hidden-md hidden-lg print-hidden">
							<span class="glyphicon glyphicon-plus green-title"></span>
						</div>
						<div class="col-xs-12 padding0 m-t-5 font-size-14 text-default order-accordion-data-3">
							<p class="m-b-0 print-f-w-bold">
								<spring:theme code="invoicedetailpage.date.time" />:
							</p>
							<p class="m-b-0">
								<c:choose>
									<c:when test="${not empty invoiceData.pickupOrDeliveryDateTime}">
										<fmt:formatDate var="fmtDate" value="${invoiceData.pickupOrDeliveryDateTime}"
											pattern="MM/dd/YYYY" />
										<input type="hidden" id="requestedDateinUTC" value="${fmtDate}" />
										${fmtDate}
									</c:when>
									<c:otherwise>
										<spring:theme code="invoicedetailpage.na" />
									</c:otherwise>
								</c:choose>
							</p>
						</div>
					</div>
				</div>
				<div class="col-md-3 padding-20 hidden-xs hidden-sm b-l-0-xs b-l-0-sm order-height-adjust-grey print-visible d">
					<span class="bold text-gray text-uppercase f-s-10 f-s-16-xs-px Geogrotesque-medium-xs print-text-gray">
						<spring:theme code="invoicedetailpage.msg" />
					</span>
					<p class="m-t-5 m-b-0 font-size-14">
						<c:choose>
							<c:when test="${not empty invoiceData.instructions}">${invoiceData.instructions}
							</c:when>
							<c:otherwise>
								<spring:theme code="invoicedetailpage.na" />
							</c:otherwise>
						</c:choose>
					</p>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
	<div class="row margin0 bg-white border-grey b-t-0 text-default f-s-15 font-14-xs print-hidden">
		<div class="col-md-2 col-xs-6 padding-30 p-t-15 p-15-xs b-b-grey-xs order-height-adjust-white">
			<span class="bold print-text-gray text-uppercase text-gray f-s-10">
				<spring:theme code="invoicedetailpage.invoice.number" />
			</span>
			<p class="m-b-0 l-h-20">${not empty invoiceData.invoiceNumber && invoiceData.invoiceNumber ne null ?
				invoiceData.invoiceNumber : 'NA'}</p>
		</div>
		<div class="col-md-2 col-xs-6 padding-30 p-t-15 p-15-xs b-b-grey-xs b-l-grey order-height-adjust-white hidden-md hidden-lg">
			<span class="bold print-text-gray text-uppercase text-gray f-s-10">
				<spring:theme code="invoicedetailpage.invStatus" />
			</span>
			<p class="m-b-0 l-h-20">
				<c:choose>
					<c:when test="${not empty invoiceData.status}">${fn:toLowerCase(invoiceData.status)}</c:when>
					<c:otherwise>NA</c:otherwise>
				</c:choose>
			</p>
		</div>
		<div class="col-md-3 col-xs-12 padding-30 p-t-15 p-15-xs b-l-grey b-l-0-xs b-b-grey-xs order-height-adjust-white">
			<span class="bold print-text-gray text-gray text-uppercase f-s-10">
				<spring:theme code="invoicedetailpage.placedBy" />
			</span>
			<p class="m-b-0 l-h-20">
				<c:choose>
					<c:when test="${not empty invoiceData.userName}">
						${invoiceData.userName}&nbsp;
					</c:when>
					<c:when test="${not empty invoiceData.userName && empty invoiceData.user}">
						<spring:theme code="invoicedetailpage.na" />
					</c:when>
				</c:choose>
			</p>
			<p class="m-b-0 l-h-20">
				<c:choose>
					<c:when test="${not empty invoiceData.user}">
						${invoiceData.user}&nbsp;
					</c:when>
					<c:otherwise>
						<spring:theme code="invoicedetailpage.na" />
					</c:otherwise>
				</c:choose>
			</p>
		</div>
		<div class="col-md-3 col-xs-6 padding-30 p-t-15 p-15-xs b-l-grey b-b-grey-xs b-l-0-xs order-height-adjust-white">
			<span class="bold print-text-gray text-uppercase text-gray f-s-10">
				<spring:theme code="invoicedetailpage.datePlaced" />
			</span>
			<p class="m-b-0 l-h-20">
				<c:choose>
					<c:when test="${not empty invoiceData.invoiceDate}">
						<fmt:formatDate var="fmtDate" value="${invoiceData.invoiceDate}" pattern="MMMM dd, YYYY"
							dateStyle="long" />
						${fmtDate}
					</c:when>
					<c:otherwise>
						<spring:theme code="invoicedetailpage.na" />
					</c:otherwise>
				</c:choose>
			</p>
		</div>
		<ycommerce:testId code="orderDetail_overviewOrderStatus_label">
			<div class="col-md-2 col-xs-6 padding-30 p-t-15 p-15-xs b-l-grey order-height-adjust-white hidden-xs hidden-sm">
				<span class="bold print-text-gray text-uppercase text-gray f-s-10">
					<spring:theme code="invoicedetailpage.invStatus" />
				</span>
				<p class="m-b-0 l-h-20">
					<c:choose>
						<c:when test="${not empty invoiceData.status}">${fn:toLowerCase(invoiceData.status)}</c:when>
						<c:otherwise>NA</c:otherwise>
					</c:choose>
				</p>
			</div>
			<div class="col-md-2 col-xs-6 padding-30 p-t-15 p-15-xs b-l-grey b-b-grey-xs order-height-adjust-white">
				<span class="bold print-text-gray text-uppercase text-gray f-s-10">
					<spring:theme code="invoicedetailpage.purchaseOrderNumber" />
				</span>
				<p class="m-b-0 l-h-20">${not empty invoiceData.purchaseOrderNumber && invoiceData.purchaseOrderNumber
					ne	null ? invoiceData.purchaseOrderNumber : 'NA'}</p>
			</div>
			<div class="col-md-3 col-xs-12 p-15-xs padding-30 hidden-md hidden-lg b-l-0-xs b-l-0-sm order-height-adjust-grey print-visible d">
				<span class="bold text-gray text-uppercase f-s-10 print-text-gray">
					<spring:theme code="invoicedetailpage.msg" />
				</span>
				<p class="m-b-0 font-size-14">
					<c:choose>
						<c:when test="${not empty invoiceData.instructions}">${invoiceData.instructions}</c:when>
						<c:otherwise>
							<spring:theme code="invoicedetailpage.na" />
						</c:otherwise>
					</c:choose>
				</p>
			</div>
		</ycommerce:testId>
	</div>
	<!-- Invoice top section ends here  -->
	<input type="hidden" value="${invoiceData.invoiceNumber}" id="invoiceNumber" />
	<input type="hidden" value="${invoiceData.accountNumber}" id="accountNumber" />

	<!-- Invoice Payment section starts here  -->
	<section class="invoice-detail-payment">
		<div class="row margin0">
			<!-- Payment Details section starts here -->
			<div class="col-md-6 m-y-30 f-s-20 p-l-35 p-r-50 p-l-0-xs p-l-0-sm p-r-0-xs p-r-0-sm">
				<p class="b-b-grey p-b-10 font-Geogrotesque text-default hidden-xs hidden-sm">
					<spring:theme code="orderconfirmation.checkout.multi.paymentMethod.text.paymentdetails" />
				</p>
				<p class="font-Geogrotesque text-default f-s-16 hidden-md hidden-lg">
					<spring:theme code="orderconfirmation.checkout.multi.paymentMethod.text.paymentdetails" />
				</p>
				<div class="hidden-xs hidden-sm">
					<!-- Payment Details - Subtotal -->
					<div class="row margin0 f-s-15 p-t-5">
						<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray">
							<spring:theme code="basket.page.totals.subtotal" />
						</div>
						<div class="col-xs-8">
							<ycommerce:testId code="Order_Totals_Subtotal">
								<c:choose>
									<c:when test="${not empty invoiceData.subTotal}">
										<span class="black-title">$${invoiceData.subTotal}</span>
									</c:when>
									<c:otherwise>
										<span class="black-title">$0.00</span>
									</c:otherwise>
								</c:choose>
							</ycommerce:testId>
						</div>
					</div>
					<!-- Payment Details - Freight -->
					<c:if test="${not empty invoiceData.freight && invoiceData.freight != '0'}">
						<div class="row margin0 f-s-15 p-t-5">
							<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray">
								<spring:theme code="invoice.page.freight" />
							</div>
							<div class="col-xs-8">
								<ycommerce:testId code="Order_Totals_Frieght">
									<c:choose>
										<c:when test="${not empty invoiceData.freight}">
											<span class="black-title">$${invoiceData.freight}</span>
										</c:when>
										<c:otherwise>
											<span class="black-title">$0.00</span>
										</c:otherwise>
									</c:choose>
								</ycommerce:testId>
							</div>
						</div>
					</c:if>
					<!-- Payment Details - Tax -->
					<c:if test="${not empty invoiceData.totalTax}">
						<div class="row margin0 f-s-15 p-t-5">
							<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray">
								<spring:theme code="basket.page.totals.netTax" />
							</div>
							<div class="col-xs-8">
								<c:choose>
									<c:when test="${not empty invoiceData.totalTax}">
										<span class="black-title">$${invoiceData.totalTax}</span>
									</c:when>
									<c:otherwise>
										<span class="black-title">$0.00</span>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</c:if>
					<!-- Payment Details - Order Total -->
					<div class="row margin0 f-s-15 p-t-5">
						<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray">
							<spring:theme code="basket.page.totals.total" />
						</div>
						<div class="col-xs-8">
							<ycommerce:testId code="cart_totalPrice_label">
								<c:choose>
									<c:when test="${not empty invoiceData.orderTotalPrice}">
										<span class="black-title bold-text">$${invoiceData.orderTotalPrice}</span>
									</c:when>
									<c:otherwise>
										<span class="black-title bold-text">$0.00</span>
									</c:otherwise>
								</c:choose>
							</ycommerce:testId>
						</div>
					</div>
					<!-- Payment Details - Total Payment -->
					<div class="row margin0 f-s-15 p-t-5">
						<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray">
							<spring:theme code="invoice.page.total.payment" />
						</div>
						<div class="col-xs-8">
							<ycommerce:testId code="invoice-totalPayment_label">
								<c:choose>
									<c:when test="${not empty invoiceData.totalPayment}">
										<span class="black-title">$${invoiceData.totalPayment}</span>
									</c:when>
									<c:otherwise>
										<span class="black-title">$0.00</span>
									</c:otherwise>
								</c:choose>
							</ycommerce:testId>
						</div>
					</div>
					<!-- Payment Details - Amount Due -->
					<div class="row margin0 f-s-15 p-t-5">
						<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray">
							<spring:theme code="invoice.page.amount.due" />
						</div>
						<div class="col-xs-8">
							<ycommerce:testId code="invoice-amountDue_label">
								<c:choose>
									<c:when test="${not empty invoiceData.amountDue}">
										<span class="black-title">$${invoiceData.amountDue}</span>
									</c:when>
									<c:otherwise>
										<span class="black-title">$0.00</span>
									</c:otherwise>
								</c:choose>
							</ycommerce:testId>
						</div>
					</div>
				</div>
				<!-- mobile -->
				<div class="hidden-md hidden-lg bg-light-dark-xs bg-light-dark-sm p-l-15 p-t-15 p-b-15 text-uppercase">
					<!-- Payment Details - Subtotal -->
					<div class="row margin0 f-s-11 p-t-5">
						<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray p-r-0-xs">
							<spring:theme code="basket.page.totals.subtotal" />:
						</div>
						<div class="col-xs-8">
							<ycommerce:testId code="Order_Totals_Subtotal">
								<c:choose>
									<c:when test="${not empty invoiceData.subTotal}">
										<span class="black-title">$${invoiceData.subTotal}</span>
									</c:when>
									<c:otherwise>
										<span class="black-title">$0.00</span>
									</c:otherwise>
								</c:choose>
							</ycommerce:testId>
						</div>
					</div>
					<!-- Payment Details - Freight -->
					<c:if test="${not empty invoiceData.freight && invoiceData.freight != '0'}">
						<div class="row margin0 f-s-11 p-t-5">
							<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray">
								<spring:theme code="invoice.page.freight" />:
							</div>
							<div class="col-xs-8">
								<ycommerce:testId code="Order_Totals_Frieght">
									<c:choose>
										<c:when test="${not empty invoiceData.freight}">
											<span class="black-title">$${invoiceData.freight}</span>
										</c:when>
										<c:otherwise>
											<span class="black-title">$0.00</span>
										</c:otherwise>
									</c:choose>
								</ycommerce:testId>
							</div>
						</div>
					</c:if>
					<!-- Payment Details - Tax -->
					<c:if test="${not empty invoiceData.totalTax}">
						<div class="row margin0 f-s-11 p-t-5">
							<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray">
								<spring:theme code="basket.page.totals.netTax" />:
							</div>
							<div class="col-xs-8">
								<c:choose>
									<c:when test="${not empty invoiceData.totalTax}">
										<span class="black-title">$${invoiceData.totalTax}</span>
									</c:when>
									<c:otherwise>
										<span class="black-title">$0.00</span>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</c:if>
					<!-- Payment Details - Order Total -->
					<div class="row margin0 f-s-11 p-t-5">
						<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray">
							<spring:theme code="basket.page.totals.total" />:
						</div>
						<div class="col-xs-8">
							<ycommerce:testId code="cart_totalPrice_label">
								<c:choose>
									<c:when test="${not empty invoiceData.orderTotalPrice}">
										<span class="black-title bold-text">$${invoiceData.orderTotalPrice}</span>
									</c:when>
									<c:otherwise>
										<span class="black-title bold-text">$0.00</span>
									</c:otherwise>
								</c:choose>
							</ycommerce:testId>
						</div>
					</div>
					<!-- Payment Details - Total Payment -->
					<div class="row margin0 f-s-11 p-t-5">
						<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray p-r-0-xs p-r-0-sm">
							<spring:theme code="invoice.page.total.payment" />:
						</div>
						<div class="col-xs-8">
							<ycommerce:testId code="invoice-totalPayment_label">
								<c:choose>
									<c:when test="${not empty invoiceData.totalPayment}">
										<span class="black-title">$${invoiceData.totalPayment}</span>
									</c:when>
									<c:otherwise>
										<span class="black-title">$0.00</span>
									</c:otherwise>
								</c:choose>
							</ycommerce:testId>
						</div>
					</div>
					<!-- Payment Details - Amount Due -->
					<div class="row margin0 f-s-12 p-t-5">
						<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray">
							<spring:theme code="invoice.page.amount.due" />:
						</div>
						<div class="col-xs-8">
							<ycommerce:testId code="invoice-amountDue_label">
								<c:choose>
									<c:when test="${not empty invoiceData.amountDue}">
										<span class="black-title">$${invoiceData.amountDue}</span>
									</c:when>
									<c:otherwise>
										<span class="black-title">$0.00</span>
									</c:otherwise>
								</c:choose>
							</ycommerce:testId>
						</div>
					</div>
				</div>
			</div>
			<!-- Payment Method section starts here -->
			<div class="col-md-6 m-y-30 text-default f-s-20 p-l-0 p-r-0 hidden-xs hidden-sm">
				<p class="b-b-grey p-b-10 font-Geogrotesque hidden-xs hidden-sm">
					<spring:theme code="checkout.confirmation.payment.method" />
				</p>				
				<c:choose>
					<c:when test="${paymentType eq '3'}">
						<!-- Payment Method - Credit Card -->
						<div class="row margin0 f-s-15 p-t-5">
							<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray">
								<spring:theme code="checkout.multi.paymentType" />
							</div>
							<div class="col-xs-8">
								<spring:theme code="invoicedetailpage.creditcard" />
							</div>
						</div>
						<div class="row margin0 f-s-15 p-t-5">
							<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray">
								<spring:theme code="invoicedetailpage.cardType" />
							</div>
							<div class="col-xs-8">
								Visa
							</div>
						</div>
						<div class="row margin0 f-s-15 p-t-5">
							<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray">
								<spring:theme code="invoicedetailpage.cardNumber" />
							</div>
							<div class="col-xs-8">
								${cardNumber}
							</div>
						</div>
						<div class="row margin0 f-s-15 p-t-5">
							<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray">
								<spring:theme code="invoicedetailpage.amount" />
							</div>
							<div class="col-xs-8">
								<ycommerce:testId code="cart_totalPrice_label">
									<c:choose>
										<c:when test="${not empty invoiceData.orderTotalPrice}">
											<span class="black-title bold-text">$${invoiceData.orderTotalPrice}</span>
										</c:when>
										<c:otherwise>
											<span class="black-title bold-text">$0.00</span>
										</c:otherwise>
									</c:choose>
								</ycommerce:testId>
							</div>
						</div>
					</c:when>
					<c:when test="${paymentType eq '1'}">
						<!-- Payment Method - Siteone Online Account -->
						<div class="row margin0 f-s-15 p-t-5">
							<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray">
								<spring:theme code="checkout.multi.paymentType" />
							</div>
							<div class="col-xs-8">
								<spring:theme code="invoicedetailpage.payonaccount" />
							</div>
						</div>
					</c:when>
					<c:when test="${paymentType eq '2'}">
						<!-- Payment Method - Pay at branch -->
						<div class="row margin0 f-s-15 p-t-5">
							<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray">
								<spring:theme code="checkout.multi.paymentType" />
							</div>
							<div class="col-xs-8">
								<spring:theme code="invoicedetailpage.payatbranch" />
							</div>
						</div>
						<div class="row margin0 f-s-15 p-t-5">
							<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray">
								<spring:theme code="invoicedetailpage.branch" />
							</div>
							<div class="col-xs-8">
								${invoiceData.address.town} &nbsp;${invoiceData.storeId}
							</div>
						</div>
						<div class="row margin0 f-s-15 p-t-5">
							<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray">
								<spring:theme code="invoicedetailpage.amount" />
							</div>
							<div class="col-xs-8">
								<ycommerce:testId code="cart_totalPrice_label">
									<span class="black-title">$${invoiceData.orderTotalPrice}</span>
								</ycommerce:testId>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<!-- Payment Method - Pay at branch -->
						<div class="row margin0 f-s-15 p-t-5">
							<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray">
								<spring:theme code="checkout.multi.paymentType" />
							</div>
							<div class="col-xs-8">
								<spring:theme code="invoicedetailpage.payatbranch" />
							</div>
						</div>
						<div class="row margin0 f-s-15 p-t-5">
							<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray">
								<spring:theme code="invoicedetailpage.branch" />
							</div>
							<div class="col-xs-8">
								${invoiceData.address.town} &nbsp;${invoiceData.storeId}
							</div>
						</div>
						<div class="row margin0 f-s-15 p-t-5">
							<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray">
								<spring:theme code="invoicedetailpage.amount" />
							</div>
							<div class="col-xs-8">
								<ycommerce:testId code="cart_totalPrice_label">
									<span class="black-title">$${invoiceData.orderTotalPrice}</span>
								</ycommerce:testId>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			<!-- mobile -->
			<div class="col-md-6 m-y-30 text-default f-s-20 p-l-0 p-r-0 ">
				<p class=" hidden-md hidden-lg f-s-16  font-Geogrotesque">
					<spring:theme code="checkout.confirmation.payment.method" />
				</p>
				<div class="hidden-md hidden-lg bg-light-dark-xs bg-light-dark-sm p-l-15 p-t-15 p-b-15">
					<c:choose>
						<c:when test="${paymentType eq '3'}">
							<!-- Payment Method - Credit Card -->
							<div class="row margin0 f-s-11 p-t-5">
								<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray text-uppercase">
									<spring:theme code="checkout.multi.paymentType" />:
								</div>
								<div class="col-xs-8">
									<spring:theme code="invoicedetailpage.creditcard" />
								</div>
							</div>
							<div class="row margin0 f-s-11 p-t-5">
								<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray text-uppercase">
									<spring:theme code="invoicedetailpage.cardType" />:
								</div>
								<div class="col-xs-8">
									Visa
								</div>
							</div>
							<div class="row margin0 f-s-11 p-t-5">
								<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray text-uppercase">
									<spring:theme code="invoicedetailpage.cardNumber" />:
								</div>
								<div class="col-xs-8">
									${cardNumber}
								</div>
							</div>
							<div class="row margin0 f-s-11 p-t-5">
								<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray text-uppercase">
									<spring:theme code="invoicedetailpage.amount" />:
								</div>
								<div class="col-xs-8">
									<ycommerce:testId code="cart_totalPrice_label">
										<c:choose>
											<c:when test="${not empty invoiceData.orderTotalPrice}">
												<span
													class="black-title bold-text">$${invoiceData.orderTotalPrice}</span>
											</c:when>
											<c:otherwise>
												<span class="black-title bold-text">$0.00</span>
											</c:otherwise>
										</c:choose>
									</ycommerce:testId>
								</div>
							</div>
						</c:when>
						<c:when test="${paymentType eq '1'}">
							<!-- Payment Method - Siteone Online Account -->
							<div class="row margin0 f-s-11 p-t-5">
								<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray text-uppercase">
									<spring:theme code="checkout.multi.paymentType" />:
								</div>
								<div class="col-xs-8">
									<spring:theme code="invoicedetailpage.payonaccount" />
								</div>
							</div>
						</c:when>
						<c:when test="${paymentType eq '2'}">
							<!-- Payment Method - Pay at branch -->
							<div class="row margin0 f-s-11 p-t-5">
								<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray text-uppercase">
									<spring:theme code="checkout.multi.paymentType" />:
								</div>
								<div class="col-xs-8">
									<spring:theme code="invoicedetailpage.payatbranch" />
								</div>
							</div>
							<div class="row margin0 f-s-11 p-t-5">
								<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray text-uppercase">
									<spring:theme code="invoicedetailpage.branch" />:
								</div>
								<div class="col-xs-8">
									${invoiceData.address.town} &nbsp;${invoiceData.storeId}
								</div>
							</div>
							<div class="row margin0 f-s-11 p-t-5">
								<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray text-uppercase">
									<spring:theme code="invoicedetailpage.amount" />:
								</div>
								<div class="col-xs-8">
									<ycommerce:testId code="cart_totalPrice_label">
										<span class="black-title">$${invoiceData.orderTotalPrice}</span>
									</ycommerce:testId>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<!-- Payment Method - Pay at branch -->
							<div class="row margin0 f-s-11 p-t-5">
								<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray text-uppercase">
									<spring:theme code="checkout.multi.paymentType" />:
								</div>
								<div class="col-xs-8">
									<spring:theme code="invoicedetailpage.payatbranch" />
								</div>
							</div>
							<div class="row margin0 f-s-11 p-t-5">
								<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray text-uppercase">
									<spring:theme code="invoicedetailpage.branch" />:
								</div>
								<div class="col-xs-8">
									${invoiceData.address.town} &nbsp;${invoiceData.storeId}
								</div>
							</div>
							<div class="row margin0 f-s-11 p-t-5">
								<div class="col-xs-4 p-l-0 f-w-700 text-dark-gray text-uppercase">
									<spring:theme code="invoicedetailpage.amount" />:
								</div>
								<div class="col-xs-8">
									<ycommerce:testId code="cart_totalPrice_label">
										<span class="black-title">$${invoiceData.orderTotalPrice}</span>
									</ycommerce:testId>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</section>
	<!-- Invoice Payment section ends here  -->
	<!-- Order details section Starts here -->

	<div class="row orderItemsDetails m-l-0 m-r-0 m-r-00 m-t-15 hidden-xs hidden-sm">
		<div class="print-row-12 col-xs-12 f-s-20 p-l-30 font-Geogrotesque f-w-500 title-bar pointer accordion-toggle orderAccordion border-lighter-grey bg-white-imp m-t-0-imp"
			onclick="ACC.global.openCloseAccordion(this,'close',1,'invoiceAccordion', 'chevron-down,chevron-up')">
			<c:set var="listLength" value="${fn:length(invoiceData.invoiceEntryList)}" />
			<spring:theme code="invoicedetailpage.order" />&nbsp;<span class="text-dark-gray">(${listLength})</span>
			<common:globalIcon iconName="down-arrow" iconFill="none" iconColor="#77A12E" width="25" height="9"
				viewBox="0 0 15 9" />
		</div>
	</div>
	<div class="row orderItemsDetails m-l-0 m-r-0 m-r-00 hidden-md hidden-lg">
		<div class="print-row-12 col-xs-12 f-s-16 p-l-0 text-default m-b-10  m-t-15 font-Geogrotesque f-w-500  pointer accordion-toggle orderAccordion  m-t-0-imp "
			onclick="ACC.global.openCloseAccordion(this,'close',1,'invoiceAccordion', 'chevron-down,chevron-up')">
			<c:set var="listLength" value="${fn:length(invoiceData.invoiceEntryList)}" />
			<spring:theme code="invoicedetailpage.order" />&nbsp;<span class="text-dark-gray">(${listLength})</span>
			<common:globalIcon iconName="down-arrow" iconFill="none" iconColor="#77A12E" width="25" height="9"
				viewBox="0 0 15 9" />
		</div>
	</div>
	<section class="invoiceAccordion-data-1 margin0 bg-white m-t-neg-3 border-lighter-grey md-order-detail-padding order-detail-print p-b-0-imp m-b-50 p-t-0-xs-imp row" style="display: none;">
		<div class="print-row-12 col-md-12 col-xs-12 padding0 productInformationDetails">
			<div class="hidden-xs hidden-sm">
				<div class="row text-uppercase">
					<div class="item__list--header sec-title-bar m-r-30 m-l-30">
						<div class="col-md-5 col-md-offset-1 p-l-0 f-s-12 item__image-title">
							<spring:theme code="basket.page.itemInfo" />
						</div>
						<div class="col-md-2 f-s-12 item__price-title">
							<spring:theme code="basket.page.price" />
						</div>
						<div class="col-md-2  f-s-12 text-left-imp item__quantity-title invoice-detail-qty">
							<spring:theme code="basket.page.qty" />
						</div>
						<div class="col-md-2 f-s-12 text-left-imp item__total-title">
							<spring:theme code="basket.page.total" />
						</div>
					</div>
				</div>
			</div>
			<c:forEach items="${invoiceData.invoiceEntryList}" var="entry" varStatus="loop">
				<div class="col-xs-12 item__list--header  invoice-border p-y-20">
					<div class="col-md-6 col-xs-12 item__image-wrapper">
						<div class="hidden-xs hidden-sm item__toggle">
							<c:if test="${orderEntry.product.multidimensional}">
								<div class="js-show-multiD-grid-in-order" data-index="${itemIndex}">
									<ycommerce:testId code="cart_product_updateQuantity">
										<span class="glyphicon glyphicon-chevron-down"></span>
									</ycommerce:testId>
								</div>
							</c:if>
						</div>
						<div class="row col-xs-12 p-l-0-xs p-l-0-sm">
							<!-- If product has a URL image is clickable takes to PDP page (Image with Link) -->
							<c:if test="${not empty entry.productUrl}">
								<div class="item__image col-md-2 col-xs-6 hidden-xs hidden-sm">
									<ycommerce:testId code="orderDetail_productThumbnail_link">
										<a class="imgBorder f-s-12" href="${fn:escapeXml(entry.productUrl)}">
											<c:set value="thumbnail" var="format" />
											<c:set value="${ycommerce:getImageForTypeAndFormat(entry.images, 'thumbnail')}" var="primaryImage" />
											<c:choose>
												<c:when test="${not empty primaryImage}">
													<c:choose>
														<c:when test="${not empty primaryImage.altText}">
															<img src="${primaryImage.url}" alt="${fn:escapeXml(primaryImage.altText)}"
																title="${fn:escapeXml(primaryImage.altText)}" />
														</c:when>
														<c:otherwise>
															<img src="${primaryImage.url}"
																alt="${fn:escapeXml(entry.description)}"
																title="${fn:escapeXml(entry.description)}" />
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<theme:image code="img.missingProductImage.responsive.${format}"
														alt="${fn:escapeXml(entry.description)}"
														title="${fn:escapeXml(entry.description)}" />
												</c:otherwise>
											</c:choose>
										</a>
									</ycommerce:testId>
								</div>
							</c:if>
							<!-- If No URl,fallback missing image -->
							<c:if test="${empty entry.productUrl}">
								<div class="item__image col-md-2 col-xs-6 hidden-xs hidden-sm">
									<ycommerce:testId code="orderDetail_productThumbnail_link">
										<c:set value="thumbnail" var="format" />
										<c:set value="${ycommerce:getImageForTypeAndFormat(entry.images, 'thumbnail')}"
											var="primaryImage" />
										<c:choose>
											<c:when test="${not empty primaryImage}">
												<c:choose>
													<c:when test="${not empty primaryImage.altText}">
														<img src="${primaryImage.url}"
															alt="${fn:escapeXml(primaryImage.altText)}"
															title="${fn:escapeXml(primaryImage.altText)}" />
													</c:when>
													<c:otherwise>
														<img src="${primaryImage.url}"
															alt="${fn:escapeXml(entry.description)}"
															title="${fn:escapeXml(entry.description)}" />
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<theme:image code="img.missingProductImage.responsive.${format}"
													alt="${fn:escapeXml(entry.description)}"
													title="${fn:escapeXml(entry.description)}" />
											</c:otherwise>
										</c:choose>
									</ycommerce:testId>
								</div>
							</c:if>
							<!-- Product name / product code -->
							<div class=" col-md-7 col-xs-12">
								<div class="row">
									<div class="item__name-wrapper item__name  p-l-10 hidden-xs hidden-sm">
										<ycommerce:testId code="orderDetails_productName_link">
											<c:if test="${not empty entry.productUrl}">
												<a href="${fn:escapeXml(entry.productUrl)}"
													class="text-invoice-green f-s-15">
													${fn:escapeXml(entry.description)}
												</a>
											</c:if>
											<c:if test="${empty entry.productUrl}">
												${fn:escapeXml(entry.description)}
											</c:if>
										</ycommerce:testId>
									</div>
									<div class="item__code">
										<div class="col-xs-4 p-l-0 f-s-12 visible-xs visible-sm p-r-0-xs text-uppercase">
											<spring:theme code="basket.page.title" /> #:
										</div>
										<div class="col-xs-8 f-s-12 p-l-10 p-l-15-xs p-l-15-sm">
											<ycommerce:testId code="orderDetails_productCode">
												${fn:escapeXml(entry.productItemNumber)}
											</ycommerce:testId>
										</div>
									</div>
									<div class="col-xs-4 p-l-0 f-s-12 visible-xs visible-sm p-t-10 text-uppercase">
										<spring:theme code="invoicedetailpage.desc" />
									</div>
									<span class="visible-xs visible-sm text-default col-xs-8 f-s-12 p-r-0 p-t-10">${entry.description}</span>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-2 col-xs-12 p-t-10-xs p-t-10-sm item__price-wrapper">
						<!--  price -->
						<div class="row item__price">
							<span class="visible-xs visible-sm col-xs-4 f-s-12 text-uppercase text-left">
								<spring:theme code="basket.page.itemPrice" />
							</span>
							<span class="black-title col-md-12 col-xs-8 p-l-10-xs p-l-10-sm f-s-12-xs-px">$${entry.basePrice}
								<c:if test="${not empty entry.unit}"> / ${entry.unit} </c:if>
							</span>
							<c:if test="${entry.actualItemCost > entry.basePrice}">
								<span class="visible-xs visible-sm text-uppercase col-xs-4 f-s-12 p-r-0-xs p-r-0-sm p-t-10-xs p-t-10-sm text-left">
									<spring:theme code="basket.page.itemRetailPrice" />
								</span>
								<span class="col-md-12 col-xs-8 p-l-10-xs p-l-10-sm p-t-10-xs p-t-10-sm  f-s-12-xs-px">
									<del>$${entry.actualItemCost}
										<c:if test="${not empty entry.unit }"> / ${entry.unit} </c:if>
									</del>
								</span>
							</c:if>
						</div>
					</div>
					<div class="col-md-2 col-xs-12 p-t-10-xs p-t-10-sm text-left-imp item__quantity-wrapper">
						<!--    quantity -->
						<div class="row item__quantity text-left-imp">
							<ycommerce:testId code="orderDetails_productQuantity_label">
								<span class="visible-xs visible-sm col-xs-4 f-s-12 text-uppercase text-left">
									<spring:theme code="text.account.order.qty" />
								</span>
								<span class="black-title col-md-12 col-xs-8 p-l-10-xs p-l-10-sm f-s-12-xs-px">
									<fmt:formatNumber minFractionDigits="2" maxFractionDigits="2"
										value="${entry.quantity}" var="quantityText" />
									${fn:escapeXml(quantityText)}
								</span>
							</ycommerce:testId>
						</div>
					</div>
					<!-- Total -->
					<div class="col-md-2 col-xs-12 text-left-imp p-t-10-xs p-t-10-sm item__total-wrapper">
						<div class="row">
							<div class="visible-xs p-l-0-sm visible-sm col-xs-4 f-s-12 text-uppercase text-left">Total
							</div>
							<ycommerce:testId code="orderDetails_productTotalPrice_label">
								<span class="black-title col-md-12 col-xs-8 p-l-10-xs f-s-12-xs-px p-l-0-sm p-l-5">$${fn:escapeXml(entry.extPrice)}</span>
							</ycommerce:testId>
						</div>
					</div>
				</div>
			</c:forEach>
	</section>	
	<!-- Order details section Ends here -->
	<div class="col-md-12 col-xs-12 marginTop35 text-right">
	</div>
	<!-- disclaimer text section Ends here -->
	<c:if test="${contPayBillOnline && acctPayBillOnline}">
		<div class="product-disclaimer-text">
			<p class="disclaimerText">
				<spring:theme code="text.account.dashboard.paybillonline.disclaimertext" />
			</p>
		</div>
	</c:if>
	<!-- disclaimer text section Ends here -->
</template:page>	