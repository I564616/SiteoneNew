<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="pag" tagdir="/WEB-INF/tags/responsive/nav/pagination" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<fmt:setLocale value="en_US" scope="session"/>
<c:set var="unitpriceDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.totalprice.digits\")%>" />
<template:page pageTitle="${pageTitle}">
	<spring:url var="homelink" value="/" htmlEscape="false"/>
	<spring:url value="/my-account/masterOrder/" var="masterOrderDetailsUrl" htmlEscape="false"/>
	<c:set var="showInvoiceAndBill" value="${(isShipTo && AccountOverviewForShipTos) || (isMainAccount && AccountOverviewForParent) || (InvoicePermission) || (isAdmin && showManageEwallet) ? true : false}" />
	<c:set var="isAdminPermission" value="${(isShipTo && AccountOverviewForShipTos) || (isMainAccount && AccountOverviewForParent)? true : false }" />
	<c:set var="hasOrders" value="${orderData.size()}" />
	<script src="${commonResourcePath}/js/jquery-editable-select.min.js"></script>
	<c:set var="enrolled" value="false" />
	<c:set var="minthreshold" value="5000" />
	<c:set var="threshold" value="0" />
	<c:set var="tapoint" value="0" />
	<c:set var="tvapoint" value="0" />
	<c:set var="thrValue" value="0" />
	<c:set var="tapointFlag" value="false" />
	<c:set var="ispartnerProgramPermission" value="false" />
	<c:if test="${not empty partnerProgramPermission && partnerProgramPermission ne null}">
	<c:set var="ispartnerProgramPermission" value="${partnerProgramPermission}" />
	</c:if>
	<c:set var="PartnerProgramAdminEmail" value="false" />
	<c:if test="${not empty isPartnerProgramAdminEmail && isPartnerProgramAdminEmail ne null}">
	<c:set var="PartnerProgramAdminEmail" value="${isPartnerProgramAdminEmail}" />
	</c:if>
	<c:if test="${isPartnerProgramEnrolled eq true}">
		<c:set var="enrolled" value="true" />
		<c:choose>
			<c:when test="${not empty loyaltyPartnerPoints && loyaltyPartnerPoints ne null}">
				<c:set var="thrValue" value="${loyaltyPartnerPoints.minimumSpentThreshold}" />
				<c:set var="threshold" value="${thrValue*100/minthreshold}" />
				<c:set var="tapoint" value="${loyaltyPartnerPoints.totalAvailablePoints}" />
				<c:set var="tvapoint" value="${loyaltyPartnerPoints.value}" />			
			</c:when>
			<c:otherwise>
				<c:set var="tapointFlag" value="true" />
				<c:set var="tapoint" value="0" />
			</c:otherwise>
		</c:choose>
	</c:if>
	<!-- Dashboard Sales summary Wdiget -->
	<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${not empty ytdSales and ytdSales ne '' ? ytdSales : 0}" var="FmtYtdSales" />
	<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${not empty lastYtdSales and lastYtdSales ne '' ? lastYtdSales : 0}" var="FmtLastYtdSales" />
	<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${not empty twelveMonthsSales and twelveMonthsSales ne '' ? twelveMonthsSales : 0}" var="FmtTwelveMonthsSales" />
	<!-- Dashboard Credit summary Wdiget -->
	<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${not empty creditLimit and creditLimit ne '' ? creditLimit : 0}" var="FmtCreditLimit" />
	<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${not empty creditBalance and creditBalance ne '' ? creditBalance : 0}" var="FmtCreditBalance" />
	<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${not empty creditOTB and creditOTB ne '' ? creditOTB : 0}" var="FmtCreditOTB" />

	<input type="hidden" name="enrolled" value="${enrolled}" />
	<input type="hidden" name="threshold" value="${threshold}" />
	<input type="hidden" name="tapoint" value="${tapoint}" />
	<input type="hidden" name="tvapoint" value="${tvapoint}" />
	<input type="hidden" name="thrValue" value="${thrValue}" />
	<input type="hidden" name="partnerProgramPermission" value="${ispartnerProgramPermission}" />
	<input type="hidden" name="isPartnerProgramAdminEmail" value="${PartnerProgramAdminEmail}" />
	<link rel="stylesheet" type="text/css" media="all" href="${themeResourcePath}/css/jquery-editable-select.min.css" />
	<div class="row p-y-15 p-t-0-xs p-t-0-sm p-b-0-xs p-b-0-sm m-x-25-neg-xs m-x-25-neg-sm account-dashboard-content">
		<div class="col-xs-12 text-align-right p-r-25-xs p-r-25-sm hidden-md hidden-lg">
			<c:if test="${contPayBillOnline && acctPayBillOnline}">
				<a href="${homelink}my-account/pay-account-online" class="bold btn btn-white-border transition-3s f-s-12 text-uppercase account-pay-account-online" target="redirectToBT">
					<span class="bg-primary display-ib flex-center justify-center"><common:globalIcon iconName="paybill" iconFill="none" iconColor="#FFFFFF" width="18" height="21" viewBox="0 0 18 21" display="" /></span>
					<spring:theme code="text.account.payBillOnline.btn" />
				</a>
			</c:if>
		</div>
		<div class="col-md-10 col-xs-12 p-l-25-xs p-l-25-sm p-y-15-xs p-y-15-sm font-Geogrotesque">
			<p class="f-s-26 f-w-600 text-default m-b-0 f-s-18-xs-px f-s-18-sm-px"><spring:theme code="accountDashboardPage.hello" />, <ycommerce:testId code="header_LoggedUser">${user.firstName}<c:if test="${user.lastName ne ''}">&nbsp;${fn:toUpperCase(fn:substring(user.lastName, 0, 1))}.</c:if></ycommerce:testId></p>
			<p class="f-s-18 f-w-500 m-b-0 f-s-14-xs-px f-s-14-sm-px"><c:if test="${sessionScope.shipToCompanyName != null}">${sessionScope.shipToCompanyName} | </c:if>Account #<span class="current-customer-number">${unit.displayId}</span></p>
		</div>
		<div class="col-md-2 text-align-right hidden-xs hidden-sm">
			<c:if test="${contPayBillOnline && acctPayBillOnline}">
				<a href="${homelink}my-account/pay-account-online" class="bold btn btn-white-border transition-3s f-s-12 m-t-5 text-uppercase account-pay-account-online" target="redirectToBT">
					<span class="bg-primary display-ib flex-center justify-center"><common:globalIcon iconName="paybill" iconFill="none" iconColor="#FFFFFF" width="18" height="21" viewBox="0 0 18 21" display="" /></span>
					<spring:theme code="text.account.payBillOnline.btn" />
				</a>
			</c:if>
		</div>
		<div class="col-xs-3 col-sm-2 p-l-0 p-r-0 hidden">
			<button type="button" class="btn btn-primary btn-block flex-center justify-center transition-3s dashboard-menu-btn m-t-0-xs-imp m-t-0-sm-imp br-tr-0-imp br-br-0-imp" onclick="ACC.accountdashboard.mobMenuSlide(this, '.dashboard-account-menu', '.dashboard-menu-btn', 500)">
				<span class="m-r-10 ham-icon-set">
					<span class="bg-white ham-icon ham-icon-1 transition-3s"></span>
					<span class="bg-white ham-icon ham-icon-2 transition-3s"></span>
					<span class="bg-white ham-icon ham-icon-3 transition-3s"></span>
				</span>
			</button>
		</div>
		<div class="bg-gray col-sm-10 col-xs-9 f-s-18 font-Geogrotesque p-y-15 text-white hidden">
			Account Dashboard Menu
		</div>
	</div>
	<!-- desktop menu -->
	<div class="row margin0 account-dashboard-content">
		<div class="col-md-12 hidden-xs hidden-sm">
			<div class="btn-group flex-center justify-between row" role="group" aria-label="...">
				<div class="btn-group col-md-2${showInvoiceAndBill? '' :' col-md-20pe'} padding0" role="group">
					<button id="btnGroupAccount_1" type="button" class="btn btn-darker-gray btn-block dropdown-toggle flex-center justify-center transition-3s b-r-l-3-imp border-right account-dashboard-btn" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<spring:theme code="account.dashboard.orders" />
						<span class="glyphicon glyphicon-chevron-down f-s-6 m-l-5 hidden-xs hidden-sm"></span>
					</button>
					<ul class="dropdown-menu font-Geogrotesque f-s-16" aria-labelledby="btnGroupAccount_1">
						<li><a href="${homelink}my-account/orders/${unit.uid}" class="transition-3s account-history orders-link" data-key="orderhistorypage" data-active="orderHistoryTab"><spring:theme code="text.account.myorders" /></a></li>
						<li><a href="${homelink}my-account/buy-again/${unit.uid}?q=%3Alastpurchaseddate-desc%3AsoLastPurchasedDateFilter%3APast 60 Days" class="transition-3s selected-dropdown-value orders-link" data-key="purchasedproducts" data-active="purchasedOrderTab"><spring:theme code="text.account.buyagain" /></a></li>
						<li><a href="${homelink}quickOrder" class="transition-3s"><spring:theme code="header.quickOrder" /></a></li>
						<c:if test="${isAdmin && featureSwitch eq true}">
							<li>
								<a href="${homelink}my-account/approval-dashboard" class="transition-3s"><spring:theme code="header.approveOrders" /><c:if test="${pendingOrderCount > 0}"><span class="badge item-value-badge">${pendingOrderCount}</span></c:if></a>
							</li>
						</c:if>
						<c:if test="${quotesFeatureSwitch eq true}">
							<li><a href="${homelink}my-account/my-quotes" class="transition-3s"><spring:theme code="text.account.quote.myquotes" /></a></li>
						</c:if>
					</ul>
				</div>
				<div class="btn-group col-md-2${showInvoiceAndBill? '' :' col-md-20pe'} padding0" role="group">
					<button id="btnGroupAccount_2" type="button" class="btn btn-darker-gray btn-block dropdown-toggle flex-center justify-center transition-3s border-right account-dashboard-btn" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<spring:theme code="account.dashboard.locations" />
						<span class="glyphicon glyphicon-chevron-down f-s-6 m-l-5 hidden-xs hidden-sm"></span>
					</button>
					<ul class="dropdown-menu font-Geogrotesque f-s-16" aria-labelledby="btnGroupAccount_2">
						<li><a href="${homelink}my-account/my-stores" class="transition-3s"><spring:theme code="account.dashboard.locations.link1" /></a></li>
						<li><a href="${homelink}my-account/address-book/${unit.uid}" class="transition-3s"><spring:theme code="account.dashboard.locations.link2" /></a></li>
						<li><a href="${homelink}my-account/billing-address/${unit.uid}" class="transition-3s"><spring:theme code="account.dashboard.locations.link3" /></a></li>
					</ul>
				</div>
				<div class="btn-group col-md-2${showInvoiceAndBill? '' :' col-md-20pe'} padding0" role="group">
					<button id="btnGroupAccount_3" type="button" class="btn btn-darker-gray btn-block dropdown-toggle flex-center justify-center transition-3s border-right account-dashboard-btn" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<spring:theme code="account.dashboard.mylists" />
						<span class="glyphicon glyphicon-chevron-down f-s-6 m-l-5 hidden-xs hidden-sm"></span>
					</button>
					<ul class="dropdown-menu font-Geogrotesque f-s-16" aria-labelledby="btnGroupAccount_3">
						<li><a href="${homelink}savedList" class="transition-3s"><spring:theme code="account.dashboard.mylists.link1" /></a></li>
						<li><a href="${homelink}savedList/recommendedList" class="transition-3s"><spring:theme code="account.dashboard.mylists.link4" /></a></li>
						<li><a href="${homelink}assembly" class="transition-3s"><spring:theme code="account.dashboard.mylists.link2" /></a></li>
					</ul>
				</div>
				<div class="btn-group col-md-2 padding0${showInvoiceAndBill? '' :' hidden'}" role="group">
					<button id="btnGroupAccount_4" type="button" class="btn btn-darker-gray btn-block dropdown-toggle flex-center justify-center transition-3s border-right account-dashboard-btn" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<spring:theme code="account.dashboard.invoicingbilling" />
						<span class="glyphicon glyphicon-chevron-down f-s-6 m-l-5 hidden-xs hidden-sm"></span>
					</button>
					<ul class="dropdown-menu font-Geogrotesque f-s-16" aria-labelledby="btnGroupAccount_4">
						<c:if test="${isAdminPermission}">
							<li><a href="${homelink}my-account/account-overview?accountId=${unit.uid}" class="transition-3s account-invoice"><spring:theme code="account.dashboard.invoicingbilling.link1" /></a></li>
						</c:if>
						<c:if test="${InvoicePermission}">
							<li><a href="${homelink}my-account/invoices/${unit.uid}?invoiceShiptos=All" class="transition-3s account-invoice"><spring:theme code="account.dashboard.invoicingbilling.link2" /></a></li>
						</c:if>
						<c:if test="${isAdmin && showManageEwallet && currentBaseStoreId eq 'siteone'}">
							<li><a href="${homelink}my-account/ewallet/${unit.uid}" class="transition-3s account-invoice"><spring:theme code="text.company.ewallet.label" /></a></li>
						</c:if>
					</ul>
				</div>
				<div class="btn-group col-md-2${showInvoiceAndBill? '' :' col-md-20pe'} padding0" role="group">
					<button id="btnGroupAccount_5" type="button" class="btn btn-darker-gray btn-block dropdown-toggle flex-center justify-center transition-3s border-right account-dashboard-btn" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<spring:theme code="account.dashboard.mycompanyinfo" />
						<span class="glyphicon glyphicon-chevron-down f-s-6 m-l-5 hidden-xs hidden-sm"></span>
					</button>
					<ul class="dropdown-menu font-Geogrotesque f-s-16" aria-labelledby="btnGroupAccount_5">
						<li><a href="${homelink}my-account/account-information/${unit.uid}" class="transition-3s"><spring:theme code="account.dashboard.mycompanyinfo.link1" /></a></li>
						<!-- <li><a href="${homelink}my-account/all-ship-to/${unit.uid}" class="transition-3s removeSortbySession"><spring:theme code="account.dashboard.mycompanyinfo.link2" /></a></li> -->
						<!-- SE-30480 Redirect Ship-Tos dropdown to Ship To Overlay -->
						<li data-global-linkname="ship-to" class="shipToPopup" onclick="ACC.accountdashboard.showShipTos(this)"><a class="transition-3s removeSortbySession"><spring:theme code="account.dashboard.mycompanyinfo.link2" /></a></li>
						<c:if test="${isAdmin}">
							<li><a href="${homelink}my-company/organization-management/manage-users/${unit.uid}" class="transition-3s account-manage-user"><spring:theme code="account.dashboard.mycompanyinfo.link3" /></a></li>
						</c:if>
						<li><a href="${homelink}my-account/accountPartnerProgram" class="transition-3s"><spring:theme code="account.dashboard.mycompanyinfo.link4" /></a></li>
						<li><a href="${homelink}my-account/update-siteoneprofile" class="transition-3s"><spring:theme code="account.dashboard.mypersonalinfo.link1" /></a></li>
						<c:if test="${isPunchOutAccount ne true }">
							<li><a href="${homelink}my-account/update-password" class="transition-3s"><spring:theme code="account.dashboard.mypersonalinfo.link2" /></a></li>
						</c:if>
						<li><a href="${homelink}my-account/update-siteonepreference" class="transition-3s"><spring:theme code="account.dashboard.mypersonalinfo.link3" /></a></li>
					</ul>
				</div>
				<div class="btn-group col-md-2${showInvoiceAndBill? ' col-md-18pe' :' col-md-20pe'} padding0" role="group">
					<button id="btnGroupAccount_6" type="button" class="btn btn-darker-gray btn-block dropdown-toggle flex-center justify-center transition-3s b-r-r-3-imp account-dashboard-btn" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<spring:theme code="account.dashboard.mytools" />
						<span class="glyphicon glyphicon-chevron-down f-s-6 m-l-5 hidden-xs hidden-sm"></span>
					</button>
					<ul class="dropdown-menu font-Geogrotesque f-s-16" aria-labelledby="btnGroupAccount_6">
						<c:if test="${currentBaseStoreId ne 'siteoneCA'}"><li><a href="${homelink}my-account/downloadNurseryInventoryCSV" class="transition-3s"><spring:theme code="account.dashboard.mytools.link1" /></a></li></c:if>
						<c:if test="${isEnabledForAgroAI}">
							<c:set var="agroaischedulerurl" value="<%=de.hybris.platform.util.Config.getParameter(\"agro.ai.scheduler\")%>"/>
							<li><a href="${agroaischedulerurl}?code=${agroToken}" class="transition-3s hidden-xs hidden-sm"><spring:theme code="account.dashboard.mytools.link2" /></a></li>
						</c:if>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="flex m-y-10 hidden-xs hidden-sm col-gap-20">
		<common:accountdashboard-purchase-cart isAdminPermission="${isAdminPermission}" ytdSales="${FmtYtdSales}" lastYtdSales="${FmtLastYtdSales}" twelveMonthSales="${FmtTwelveMonthsSales}" />
		<common:accountdashboard-creditsummery-cart isAdminPermission="${isAdminPermission}" creditLimit="${FmtCreditLimit}" creditBalance="${FmtCreditBalance}" creditOTB="${FmtCreditOTB}" />
		<common:accountdashboard-partners-cart tvapoint="${tvapoint}" tapoint="${tapoint}" tapointFlag="${tapointFlag}" threshold="${threshold}" enrolled="${enrolled}" partnerProgramPermission="${ispartnerProgramPermission}" isPartnerProgramAdminEmail="${PartnerProgramAdminEmail}" minthreshold="${minthreshold}"/>
	</div>
	<!-- ./desktop menu -->
	<!-- mobile menu -->
	<div class="dashboard-account-menu hidden-md hidden-lg">
		<ul class="margin0 padding0 full-height scroll-bar-5 m1CategoryDashboard">
			<li class="m1Categories">
				<button onclick="ACC.accountdashboard.categories(this, 1, '.m2CategoryDashboard')" class="btn btn-block btn-darker-gray text-align-left m1Links child text-default f-w-500 f-s-18 b-t-grey b-b-grey transition-3s"><spring:theme code="account.dashboard.orders" /></button>
			</li>
			<li class="m1Categories">
				<button onclick="ACC.accountdashboard.categories(this, 2, '.m2CategoryDashboard')" class="btn btn-block btn-darker-gray text-align-left m1Links child text-default f-w-500 f-s-18 b-b-grey transition-3s"><spring:theme code="account.dashboard.locations" /></button>
			</li>
			<li class="m1Categories">
				<button onclick="ACC.accountdashboard.categories(this, 3, '.m2CategoryDashboard')" class="btn btn-block btn-darker-gray text-align-left m1Links child text-default f-w-500 f-s-18 b-b-grey transition-3s"><spring:theme code="account.dashboard.mylists" /></button>
			</li>
			<li class="m1Categories${showInvoiceAndBill? '' :' hidden'}">
				<button onclick="ACC.accountdashboard.categories(this, 4, '.m2CategoryDashboard')" class="btn btn-block btn-darker-gray text-align-left m1Links child text-default f-w-500 f-s-18 b-b-grey transition-3s"><spring:theme code="account.dashboard.invoicingbilling" /></button>
			</li>
			<li class="m1Categories">
				<button onclick="ACC.accountdashboard.categories(this, 5, '.m2CategoryDashboard')" class="btn btn-block btn-darker-gray text-align-left m1Links child text-default f-w-500 f-s-18 b-b-grey transition-3s"><spring:theme code="account.dashboard.mycompanyinfo" /></button>
			</li>
			<li class="m1Categories">
				<button onclick="ACC.accountdashboard.categories(this, 6, '.m2CategoryDashboard')" class="btn btn-block btn-darker-gray text-align-left m1Links child text-default f-w-500 f-s-18 b-b-grey transition-3s"><spring:theme code="account.dashboard.mytools" /></button>
			</li>
		</ul>
	</div>
	<ul class="margin0 padding0 m-t-60 font-Geogrotesque account-dashboard-content m2CategoryDashboard hidden-md hidden-lg"></ul>
	<!-- ./mobile menu -->
	<section class="bg-white border-grey border-radius-3 m-t-20 p-a-25 p-a-15-xs p-a-15-sm m-t-80-xs m-t-80-sm">
		<div class="row">
			<div class="col-xs-7 col-md-12">
				<p class="f-s-26 f-s-18-xs-px f-s-18-sm-px f-w-600 font-Geogrotesque text-gray-1"><spring:theme code="accountDashboardPage.recent.order" /></p>
			</div>
			<div class="col-xs-5 p-l-0 text-align-right hidden-md hidden-lg${hasOrders gt 0 ? '' : ' hidden'}">
				<a href="${homelink}my-account/orders/${unit.uid}" class="bold-text btn btn-white p-b-0-sm-imp p-b-0-xs-imp p-t-0-imp m-b-5-xs border-primary-imp transition-3s hidden-lg hidden-md orders-link account-history" data-key="orderhistorypage" data-active="orderHistoryTab"><span class="font-size-14"><spring:theme code="accountDashboardPage.viewall" /></span></a>
			</div>
		</div>
		<div class="row p-b-10 p-t-5 margin0 hidden-md hidden-lg">
			<div class="col-md-3 b-b-grey-3"> </div>
		</div>
		<!-- recent order header -->
		<div class="row add-border-radius bg-light-grey bold-text f-s-12 flex-center justify-center margin0 p-l-15 p-r-15 p-y-10 text-uppercase text-default hidden-xs hidden-sm${hasOrders gt 0 ? '' : ' hidden'}">
			<div class="col-md-3 col-md-20pe p-l-0"><spring:theme code="account.dashboard.recent.order" /></div>
			<div class="col-md-2 col-md-13pe padding0"><spring:theme code="account.dashboard.recent.contact" /></div>
			<div class="col-md-2 col-md-14pe text-align-center"><spring:theme code="account.dashboard.recent.date" /></div>
			<div class="col-md-3 col-md-13pe padding0 text-align-center"><spring:theme code="account.dashboard.recent.total" /></div>
			<div class="col-md-3 col-md-13pe p-r-0 text-align-center"><spring:theme code="account.dashboard.recent.status" /></div>
			<div class="col-md-1 col-md-7pe text-align-center"><spring:theme code="account.dashboard.recent.item" /></div>
			<div class="col-md-1 col-md-13pe padding0 text-align-center"><spring:theme code="account.dashboard.recent.fulfilment" /></div>
			<div class="col-md-1 col-md-7pe padding0 text-align-center"><spring:theme code="account.dashboard.recent.branch" /></div>
		</div>
		<!-- ./recent order header -->
		<!-- recent order table -->
		<c:choose>
			<c:when test="${hasOrders gt 0}">
				<c:forEach items="${orderData}" var="orderEntry" varStatus="index">
					<div class="row margin0 p-l-15 p-r-15 p-y-10 ${index.first? 'p-t-0-xs p-t-0-sm p-b-15-xs p-b-15-sm' : 'b-t-grey p-y-15-xs p-y-15-sm'} p-x-0-imp flex-center justify-center flex-wrap-xs flex-wrap-sm add-border-radius f-s-15 font-14-xs account-table bg-white">
						<div class="col-md-3 col-md-20pe col-xs-12 p-l-0-xs p-l-0-sm p-r-0-xs p-r-0-sm">
							<div class="row flex-center flex-align-start l-h-20">
								<div class="col-sm-3 col-xs-4 f-s-11 f-w-600 hidden-lg hidden-md p-r-0 m-y-5 m-b-0-xs m-t-0-xs text-gray text-uppercase">
									<spring:theme code="account.dashboard.recent.order" />
								</div>
								<div class="col-xs-8 col-sm-9 col-md-12">
									<c:forEach items="${orderEntry.consignments}" var="consignment" varStatus="num">
										<c:set var="shipmentNumber" value="${fn:escapeXml(orderEntry.shipmentNumber)}"/>
										<c:if test="${num.first}">
											<a href="${homelink}my-account/order/${unit.uid}/${fn:split(shipmentNumber,'-')[0]}-001?branchNo=${orderEntry.branchNumber}&shipmentCount=${orderEntry.consignments.size()}" class="bold-text m-b-0 text-green no-text-decoration">${fn:escapeXml(orderEntry.code)}</a>
										</c:if>
									</c:forEach>
									<p class="m-b-0 order-ponumber">${orderEntry.purchaseOrderNumber}</p>
								</div>
							</div>
						</div>
						<div class="col-md-2 col-md-13pe col-xs-12 padding0 text-capitalize">
							<div class="row flex-center flex-align-start l-h-20">
								<div class="col-sm-3 col-xs-4 f-s-11 f-w-600 hidden-lg hidden-md p-r-0 text-gray text-uppercase">
									<spring:theme code="account.dashboard.recent.contact" />
								</div>
								<div class="col-xs-8 col-sm-9 col-md-12">${orderEntry.placedBy}</div>
							</div>
						</div>
						<div class="col-md-2 col-md-14pe col-xs-12 text-align-center p-l-0-xs p-l-0-sm p-r-0-xs p-r-0-sm text-left-xs text-left-sm">
							<div class="row flex-center flex-align-start l-h-20">
								<div class="col-sm-3 col-xs-4 f-s-11 f-w-600 hidden-lg hidden-md p-r-0 text-gray text-uppercase">
									<spring:theme code="account.dashboard.recent.date" />
								</div>
								<div class="col-xs-8 col-sm-9 col-md-12">
									<fmt:formatDate value="${orderEntry.requestedDate}" dateStyle="long" timeStyle="short" />
								</div>
							</div>
						</div>
						<div class="col-md-3 col-md-13pe col-xs-12 padding0 text-align-center text-left-xs text-left-sm">
							<div class="row flex-center flex-align-start l-h-20">
								<div class="col-sm-3 col-xs-4 f-s-11 f-w-600 hidden-lg hidden-md p-r-0 text-gray text-uppercase">
									<spring:theme code="account.dashboard.recent.total" />
								</div>
								<div class="col-xs-8 col-sm-9 col-md-12">
									<c:choose>
										<c:when test="${orderEntry.net}">
											$${orderEntry.totalPriceWithTax.formattedValue}
										</c:when>
										<c:otherwise>
											$${orderEntry.totalPrice.formattedValue}
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
						<div class="col-md-3 col-md-13pe col-xs-12 text-align-center p-r-0 p-l-0-xs p-l-0-sm text-left-xs text-left-sm">
							<div class="row flex-center flex-align-start l-h-20">
								<div class="col-sm-3 col-xs-4 f-s-11 f-w-600 hidden-lg hidden-md p-r-0 text-gray text-uppercase">
									<spring:theme code="account.dashboard.recent.status" />
								</div>
								<div class="col-xs-8 col-sm-9 col-md-12 bold f-s-12-xs-px f-s-12-sm-px">
									<c:set var="recentOrderStatus" value="${fn:replace(orderEntry.statusDisplay, '_', ' ')}" />
									${fn:toUpperCase(fn:substring(recentOrderStatus, 0, 1))}${fn:toLowerCase(fn:substring(recentOrderStatus,
									1, -1))}
								</div>
							</div>
						</div>
						<div class="col-md-1 col-md-7pe col-xs-12 text-align-center p-l-0-xs p-l-0-sm p-r-0-xs p-r-0-sm text-left-xs text-left-sm">
							<div class="row flex-center flex-align-start l-h-20">
								<div class="col-sm-3 col-xs-4 f-s-11 f-w-600 hidden-lg hidden-md p-r-0 text-gray text-uppercase">
									<spring:theme code="account.dashboard.recent.item" />
								</div>
								<div class="col-xs-8 col-sm-9 col-md-12">
									${orderEntry.itemCount} Item${orderEntry.itemCount ne 1 ? 's' : ''}
								</div>
							</div>
						</div>
						<div class="col-md-1 col-md-13pe col-xs-12 padding0 text-align-center bold text-left-xs text-left-sm">
							<div class="row flex-center flex-align-start l-h-20">
								<div class="col-sm-3 col-xs-4 f-s-11 f-w-600 hidden-lg hidden-md p-r-0 text-gray text-uppercase">
									<spring:theme code="account.dashboard.recent.fulfilment" />
								</div>
								<div class="col-xs-8 col-sm-9 col-md-12 f-s-12-xs-px f-s-12-sm-px">
								<spring:theme code="text.account.order.type.display.${fn:escapeXml(orderEntry.orderType)}"/>
								</div>
							</div>
						</div>
						<div class="col-md-1 col-md-7pe col-xs-12 padding0 text-align-center text-left-xs text-left-sm">
							<div class="row flex-center flex-align-start l-h-20">
								<div class="col-sm-3 col-xs-4 f-s-11 f-w-600 hidden-lg hidden-md p-r-0 text-gray text-uppercase">
									<spring:theme code="account.dashboard.recent.branch" />
								</div>
								<div class="col-xs-8 col-sm-9 col-md-12">${orderEntry.branchNumber}</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<div class="row text-align-center">
					<div class="col-xs-12">
						<p class="f-s-20 f-s-16-xs-px f-s-16-sm-px f-w-600 text-default text-capitalize font-Geogrotesque m-t-25-xs"><spring:theme code="accountDashboardPage.no.order" /></p>
						<p class="m-b-0 font-size-14 text-gray-1"><spring:theme code="accountDashboardPage.no.order.desc1" /></p>
						<p class="m-b-20 font-size-14 text-gray-1"><spring:theme code="accountDashboardPage.no.order.desc2" /></p>
					</div>
					<div class="col-xs-12">
						<a class="btn btn-primary btn-small m-b-10" href="${homelink}"><spring:theme code="accountDashboardPage.no.order.btn" /></a>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		<!-- ./recent order -->
		<div class="row hidden-xs hidden-sm${hasOrders gt 0 ? '' : ' hidden'}">
			<div class="col-md-12">
				<a href="${homelink}my-account/orders/${unit.uid}" class="btn btn-white btn-block p-l-0 p-t-0-xs transition-3s bold-text text-green account-history orders-link" data-key="orderhistorypage" data-active="orderHistoryTab">
					<spring:theme code="accountDashboardPage.view.orders" />
				</a>
			</div>
		</div>
	</section>
	<!-- recommended section -->
	 <div class="row text-align-left p-t-20 p-t-15-xs p-t-15-sm margin0 dashboard-widget-recommended hidden">
		<div class="col-md-12 p-l-0 p-r-0 m-b-25-xs m-b-25-sm">
			<section class="bg-white border-grey border-radius-4 dashboard-widget-box">
				<p class="p-a-25 p-a-15-xs p-a-15-sm f-s-20-sm-px f-s-20-xs-px f-s-26 f-w-600 font-Geogrotesque l-h-26 m-b-0 m-r-40-xs p-r-50-xs m-r-40-sm p-r-50-sm text-gray-1"><spring:theme code="accountDashboardPage.addtoyour" /></p>
				<!-- Nav tabs -->
				 <div class="f-s-14-xs-px f-s-14-sm-px p-b-15 p-x-25 p-x-15-xs p-x-15-sm nav-parent">
					<ul class="border-radius-3 b-b-0 nav nav-tabs" role="tablist">
						<li role="recommendation" class="bg-light-grey b-r-l-3 p-a-5 p-r-0-xs-imp p-r-0-sm-imp active"><a href="#dash-tab-buyagain" aria-controls="dash-tab-buyagain" role="tab" data-toggle="tab" onclick="ACC.accountdashboard.getRecommendedData('BuyAgainPage', 'buyagain', false);"><spring:theme code="accountDashboardPage.buyagain" /></a></li>
						<li role="recommendation" class="bg-light-grey b-r-r-3 p-a-5"><a href="#dash-tab-recently" aria-controls="dash-tab-recently" role="tab" data-toggle="tab" onclick="ACC.accountdashboard.getRecommendedData('PersonalPage', 'recently', false);"><spring:theme code="accountDashboardPage.recently" /></a></li>
						<li role="recommendation" class="bg-light-grey b-r-r-3 p-a-5"><a href="#dash-tab-recommended" aria-controls="dash-tab-recommended" role="tab" data-toggle="tab" onclick="ACC.accountdashboard.getRecommendedData('RecommProductPage', 'recommended', false);"><spring:theme code="accountDashboardPage.recommendedp" /></a></li>
					</ul>
				 </div>
				<!-- tab Content -->
				<div class="float-none full-width tab-content">
					<div role="tabpanel" class="tab-pane fade in active" id="dash-tab-buyagain"><p>A</p></div>
					<div role="tabpanel" class="tab-pane fade in" id="dash-tab-recently"><p>B</p></div>
					<div role="tabpanel" class="tab-pane fade in" id="dash-tab-recommended"><p>C</p></div>
				</div>
			</section>
		</div>
	</div>
	<!-- ./recommended section -->
	<div class="row text-align-left p-y-20 p-t-15-xs p-t-15-sm margin0 js-document-dashboard-box dashboard-savedlist-box">
		<!-- My List Widget section -->
		<div class="col-md-6 col-xs-12 col-sm-12 p-l-0 p-r-10 p-r-0-xs p-r-0-sm m-b-25-xs m-b-25-sm">
			<section class="p-a-25 p-a-15-xs p-a-15-sm bg-white border-grey border-radius-4 dashboard-widget-box js-dashboard-widget">
				<p class="f-s-20-sm-px f-s-20-xs-px f-s-26 f-w-600 font-Geogrotesque l-h-26 m-b-15-sm m-b-15-xs m-b-25 m-r-40-xs p-r-50-xs m-r-40-sm p-r-50-sm text-gray-1"><spring:theme code="accountDashboardPage.lists" /></p>
				<ul class="b-b-0 nav nav-tabs f-s-14-xs-px f-s-14-sm-px" role="tablist">
					<li role="presentation" onclick="setTimeout(function(){ACC.global.elemHeightEqual('.js-dashboard-widget', 1024)}, 120);" class="bg-light-grey b-r-l-3 p-a-5 p-r-0-xs-imp p-r-0-sm-imp active"><a href="#dashboard-savedlist" aria-controls="dashboard-savedlist" role="tab" data-toggle="tab"><spring:theme code="accountDashboardPage.recent" /></a></li>
					<li role="presentation" onclick="setTimeout(function(){ACC.global.elemHeightEqual('.js-dashboard-widget', 1024)}, 120);" class="bg-light-grey b-r-r-3 p-a-5"><a href="#dashboard-recommended" aria-controls="dashboard-recommended" role="tab" data-toggle="tab"><spring:theme code="accountDashboardPage.recommended" /></a></li>
				</ul>
				<div class="float-none full-width tab-content">
					<div role="tabpanel" class="tab-pane active" id="dashboard-savedlist">
						<div class="list-group m-y-25 m-y-5-xs m-y-5-sm dashboard-savedlist-filled">
							<a href="${homelink}savedList" class="bold btn btn-border-green btn-small m-b-5-xs p-b-0-imp p-t-0-imp f-s-14-imp select-all text-green transition-3s"><spring:theme code="accountDashboardPage.viewall" /></a>
							<div class="bg-light-grey border-radius-3 f-s-12 f-w-600 l-h-12 margin0 p-x-10 p-y-15 row text-default text-uppercase hidden-xs hidden-sm">
								<div class="col-xs-8"><spring:theme code="accountDashboardPage.name" /></div>
								<div class="col-xs-4 text-align-center"><spring:theme code="accountDashboardPage.dateupdated" /></div>
							</div>
							<div class="text-capitalize list-dashboard-item-savedlist"></div>
						</div>
						<div class="dashboard-savedlist-empty b-b-grey-xs b-b-grey-sm p-x-15-xs p-x-15-sm hidden">
							<h6 class="font-Geogrotesque margin0 f-s-20 p-t-30 p-b-20 f-s-16-xs-px f-s-16-sm-px text-default"><spring:theme code="accountDashboardPage.norecent.heading" /></h6>
							<p class="f-s-14"><spring:theme code="accountDashboardPage.norecent.text" /></p>
							<p class="f-s-14 hidden"><spring:theme code="accountDashboardPage.norecent.text2" /></p>
						</div>
					</div>
					<div role="tabpanel" class="tab-pane" id="dashboard-recommended">
						<div class="list-group m-y-25 m-y-5-xs m-y-5-sm dashboard-recommended-filled">
							<a href="${homelink}savedList/recommendedList" class="bold btn btn-border-green btn-small m-b-5-xs p-b-0-imp p-t-0-imp f-s-14-imp select-all text-green transition-3s"><spring:theme code="accountDashboardPage.viewall" /></a>
							<div class="bg-light-grey border-radius-3 f-s-12 f-w-600 l-h-12 margin0 p-x-10 p-y-15 row text-default text-uppercase hidden-xs hidden-sm">
								<div class="col-xs-8"><spring:theme code="accountDashboardPage.name" /></div>
								<div class="col-xs-4 text-align-center"><spring:theme code="accountDashboardPage.dateupdated" /></div>
							</div>
							<div class="text-capitalize list-dashboard-item-recommended"></div>
						</div>
						<div class="dashboard-recommended-empty b-b-grey-xs b-b-grey-sm p-x-15-xs p-x-15-sm hidden">
							<h6 class="font-Geogrotesque margin0 f-s-20 p-t-30 p-b-20 f-s-16-xs-px f-s-16-sm-px text-default"><spring:theme code="accountDashboardPage.norecommended.heading" /></h6>
							<p class="f-s-14"><spring:theme code="accountDashboardPage.norecommended.text" /></p>
						</div>
					</div>
				</div>
				<c:set var="/savedList/createList" value="createList"/>
				<form:form action="${homelink}savedList/createList" method="post" modelAttribute="siteoneSavedListCreateForm" id="siteoneSavedListCreateForm" enctype="multipart/form-data" class="z-i-2">
					<div class="flex-center-xs flex-dir-column-xs flex-center-sm flex-dir-column-sm input-group">
						<input type="text" id="name" name="name" class="b-r-l-3-imp border-grey-imp form-control transition-3s dashboard-create-list-input" placeholder="<spring:theme code="accountDashboardPage.newlistname" />"  value="" maxlength="200" aria-describedby="basic-addon1">
						<div class="btn btn-primary input-group-addon m-t-10-xs p-y-15-xs display-ib-xs f-s-12-xs-px m-t-10-sm p-y-15-sm b-r-r-3-imp display-ib-sm transition-3s dashboard-create-list-btn" onclick="ACC.accountdashboard.dashboardListCreateForm()"><spring:theme code="accountDashboardPage.createnewlist" /> +</div>
					</div>
					<div class="text-red height-50 f-s-13 dashboard-create-list-error" style="display: none;"></div>
				</form:form>
			</section>
		</div>
		<!-- Quotes Widget section -->
		<c:if test="${quotesFeatureSwitch eq true}">
			<div class="col-md-6 col-xs-12 col-sm-12 p-l-10 p-r-0 p-l-0-xs p-l-0-sm">
				<section class="p-a-25 p-a-15-xs p-a-15-sm bg-white border-grey border-radius-4 dashboard-widget-box js-dashboard-widget">
					<p class="f-s-20-sm-px f-s-20-xs-px f-s-26 f-w-600 font-Geogrotesque l-h-26 m-b-15-sm m-b-15-xs m-b-25 m-r-40-xs p-r-50-xs m-r-40-sm p-r-50-sm text-gray-1"><spring:theme code="accountDashboardPage.quotes" /></p>
					<ul class="b-b-0 nav nav-tabs f-s-14-xs-px f-s-14-sm-px" role="tablist">
						<li role="presentation" onclick="ACC.accountdashboard.getQuotesData('open')" class="bg-light-grey b-r-l-3 p-a-5 p-r-0-xs-imp p-r-0-sm-imp active"><a href="#dashboard-open" aria-controls="dashboard-open" role="tab" data-toggle="tab"><spring:theme code="accountDashboardPage.open" /></a></li>
						<li role="presentation" onclick="ACC.accountdashboard.getQuotesData('full')" class="bg-light-grey b-r-r-3 p-a-5"><a href="#dashboard-full" aria-controls="dashboard-full" role="tab" data-toggle="tab"><spring:theme code="accountDashboardPage.approved" /></a></li>
						<li role="presentation" onclick="ACC.accountdashboard.getQuotesData('expired')" class="bg-light-grey b-r-r-3 p-a-5"><a href="#dashboard-expired" aria-controls="dashboard-expired" role="tab" data-toggle="tab"><spring:theme code="accountDashboardPage.expired" /></a></li>
					</ul>
					<div class="float-none full-width tab-content">
						<div role="tabpanel" class="tab-pane active" id="dashboard-open">
							<div class="list-group m-y-25 m-y-5-xs m-y-5-sm dashboard-open-filled">
								<a href="${homelink}my-account/my-quotes" class="bold btn btn-border-green btn-small m-b-5-xs p-b-0-imp p-t-0-imp f-s-14-imp select-all text-green transition-3s"><spring:theme code="accountDashboardPage.viewall" /></a>
								<div class="bg-light-grey border-radius-3 f-s-12 f-w-600 l-h-12 margin0 p-x-10 p-y-15 row text-default text-uppercase hidden-xs hidden-sm">
									<div class="col-xs-8"><spring:theme code="accountDashboardPage.idname" /></div>
									<div class="col-xs-4 p-l-10"><spring:theme code="accountDashboardPage.date.submitted" /></div>
								</div>
								<div class="text-capitalize list-dashboard-item-open"></div>
							</div>
							<div class="dashboard-open-empty m-y-15 m-y-5-xs m-y-5-sm p-x-15 hidden">
								<h6 class="font-Geogrotesque margin0 f-s-20 p-t-15 p-b-20 f-s-16-xs-px f-s-16-sm-px text-default"><spring:theme code="accountDashboardPage.noquotes.heading" /></h6>
								<p class="f-s-14"><spring:theme code="accountDashboardPage.noquotes.text" arguments="${currentBaseStoreId eq 'siteoneCA' ? 'ca' : 'com'}" /></p>
								<p class="f-s-14"><spring:theme code="accountDashboardPage.noquotes.text2" />&nbsp;<a href="${homelink}articles/article/Request-and-Approve-Quotes?icid=home-feature-promo"><spring:theme code="accountDashboardPage.noquotes.text3" /></a>&nbsp;<spring:theme code="accountDashboardPage.noquotes.text4" /></p>
								<p class="text-align-center m-b-0 m-t-60"><a href="${homelink}my-account/my-quotes" class="btn btn-primary"><spring:theme code="accountDashboardPage.noquotes.btn" /></a></p>
							</div>
							<common:accountDashboardQuotesLoader type="open"/>
						</div>
						<div role="tabpanel" class="tab-pane" id="dashboard-full">
							<div class="list-group m-y-25 m-y-5-xs m-y-5-sm dashboard-full-filled">
								<a href="${homelink}my-account/my-quotes" class="bold btn btn-border-green btn-small m-b-5-xs p-b-0-imp p-t-0-imp f-s-14-imp select-all text-green transition-3s"><spring:theme code="accountDashboardPage.viewall" /></a>
								<div class="bg-light-grey border-radius-3 f-s-12 f-w-600 l-h-12 margin0 p-x-10 p-y-15 row text-default text-uppercase hidden-xs hidden-sm">
									<div class="col-xs-8"><spring:theme code="accountDashboardPage.idname" /></div>
									<div class="col-xs-4 p-l-10"><spring:theme code="accountDashboardPage.date.approved" /></div>
								</div>
								<div class="text-capitalize list-dashboard-item-full"></div>
							</div>
							<div class="dashboard-full-empty m-y-15 m-y-5-xs m-y-5-sm p-x-15 hidden">
								<h6 class="font-Geogrotesque margin0 f-s-20 p-t-15 p-b-20 f-s-16-xs-px f-s-16-sm-px text-default"><spring:theme code="accountDashboardPage.noquotes.heading" /></h6>
								<p class="f-s-14"><spring:theme code="accountDashboardPage.noquotes.text" arguments="${currentBaseStoreId eq 'siteoneCA' ? 'ca' : 'com'}" /></p>
								<p class="f-s-14"><spring:theme code="accountDashboardPage.noquotes.text2" />&nbsp;<a href="${homelink}articles/article/Request-and-Approve-Quotes?icid=home-feature-promo"><spring:theme code="accountDashboardPage.noquotes.text3" /></a>&nbsp;<spring:theme code="accountDashboardPage.noquotes.text4" /></p>
								<p class="text-align-center m-b-0 m-t-60"><a href="${homelink}my-account/my-quotes" class="btn btn-primary"><spring:theme code="accountDashboardPage.noquotes.btn" /></a></p>
							</div>
							<common:accountDashboardQuotesLoader type="full"/>
						</div>
						<div role="tabpanel" class="tab-pane" id="dashboard-expired">
							<div class="list-group m-y-25 m-y-5-xs m-y-5-sm dashboard-expired-filled">
								<a href="${homelink}my-account/my-quotes" class="bold btn btn-border-green btn-small m-b-5-xs p-b-0-imp p-t-0-imp f-s-14-imp select-all text-green transition-3s"><spring:theme code="accountDashboardPage.viewall" /></a>
								<div class="bg-light-grey border-radius-3 f-s-12 f-w-600 l-h-12 margin0 p-x-10 p-y-15 row text-default text-uppercase hidden-xs hidden-sm">
									<div class="col-xs-8"><spring:theme code="accountDashboardPage.idname" /></div>
									<div class="col-xs-4 p-l-10"><spring:theme code="accountDashboardPage.date.expired" /></div>
								</div>
								<div class="text-capitalize list-dashboard-item-expired"></div>
							</div>
							<div class="dashboard-expired-empty m-y-15 m-y-5-xs m-y-5-sm p-x-15 hidden">
								<h6 class="font-Geogrotesque margin0 f-s-20 p-t-15 p-b-20 f-s-16-xs-px f-s-16-sm-px text-default"><spring:theme code="accountDashboardPage.noquotes.heading" /></h6>
								<p class="f-s-14"><spring:theme code="accountDashboardPage.noquotes.text" arguments="${currentBaseStoreId eq 'siteoneCA' ? 'ca' : 'com'}" /></p>
								<p class="f-s-14"><spring:theme code="accountDashboardPage.noquotes.text2" />&nbsp;<a href="${homelink}articles/article/Request-and-Approve-Quotes?icid=home-feature-promo"><spring:theme code="accountDashboardPage.noquotes.text3" /></a>&nbsp;<spring:theme code="accountDashboardPage.noquotes.text4" /></p>
								<p class="text-align-center m-b-0 m-t-60"><a href="${homelink}my-account/my-quotes" class="btn btn-primary"><spring:theme code="accountDashboardPage.noquotes.btn" /></a></p>
							</div>
							<common:accountDashboardQuotesLoader type="expired"/>
						</div>
					</div>
				</section>
			</div>
		</c:if>
	</div>
	<div class="flex m-y-10 hidden-md hidden-lg flex-dir-column-xs row-gap-10">
		<common:accountdashboard-purchase-cart isAdminPermission="${isAdminPermission}" ytdSales="${FmtYtdSales}" lastYtdSales="${FmtLastYtdSales}" twelveMonthSales="${FmtTwelveMonthsSales}" />
		<common:accountdashboard-creditsummery-cart isAdminPermission="${isAdminPermission}" creditLimit="${FmtCreditLimit}" creditBalance="${FmtCreditBalance}" creditOTB="${FmtCreditOTB}" />
		<common:accountdashboard-partners-cart tvapoint="${tvapoint}" tapoint="${tapoint}" tapointFlag="${tapointFlag}" threshold="${threshold}" enrolled="${enrolled}" partnerProgramPermission="${partnerProgramPermission}" isPartnerProgramAdminEmail="${isPartnerProgramAdminEmail}" minthreshold="${minthreshold}"/>
	</div>
	<div class="hidden">
		<p class="store-specialty-heading">
			<c:if test="${sessionScope.shipToCompanyName ne unit.name}">
				${unit.name}
			</c:if>
		</p>
		<c:if test="${user.roleName == 'Online Admin'}">
			<spring:theme code="account.dashboard.accountrole" />
			<spring:theme code="b2busergroup.b2badmingroup.text" />
		</c:if>
		<c:if test="${user.roleName == 'Team Member'}">
			<spring:theme code="account.dashboard.accountrole" />
			<spring:theme code="b2busergroup.b2bcustomergroup.name" />
		</c:if>
		<c:if test="${user.roleName == 'Account Owner'}">
			<spring:theme code="account.dashboard.accountrole" />
			<spring:theme code="b2busergroup.b2badmingroup.accountowner" />
		</c:if>
		<div class="italic-text marginTop10 js-show-shipto-msg hidden" data-has-child-units="${childUnits.size() gt 1}">
			<spring:theme code="account.shipto.select" arguments="${encodedContextPath}" />
		</div>

		<!-- paybillonline disclaimertext -->
		<c:if test="${contPayBillOnline && acctPayBillOnline}">
			<div class="product-disclaimer-text">
				<p class="disclaimerText">
					<spring:theme code="text.account.dashboard.paybillonline.disclaimertext" />
				</p>
			</div>
		</c:if>
		<!-- ./paybillonline disclaimertext -->
</div>
		<!-- Account-dashboard-promo -->
		<div class="row account-dashboard-promo">
			<div class="col-xs-12 col-md-4">
				<cms:pageSlot position="Promotion_SlotB" var="feature" element="div" class="dropdown">
					<cms:component component="${feature}" />
				</cms:pageSlot>
			</div>
			<div class="col-xs-12 col-md-4">
				<cms:pageSlot position="Promotion_SlotC" var="feature" element="div" class="dropdown">
					<cms:component component="${feature}" />
				</cms:pageSlot>
			</div>
			<div class="col-xs-12 col-md-4">
				<cms:pageSlot position="Promotion_SlotD" var="feature" element="div" class="dropdown">
					<cms:component component="${feature}" />
				</cms:pageSlot>
			</div>
		</div>
		<!-- ./Account-dashboard-promo -->
	
</template:page>
