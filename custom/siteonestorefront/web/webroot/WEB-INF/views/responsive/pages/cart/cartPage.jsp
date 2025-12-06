<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<template:page pageTitle="${pageTitle}">
<c:set var="subtotalLimit" value="${cartData.deliveryEligibilityThreshold}"/>
<c:set var="deliveryEligibleTotal" value="${cartData.deliveryEligibleTotal}"/>
<input type="hidden" class="subtotalLimit" value="${subtotalLimit}"/>
<input type="hidden" class="deliveryEligibleTotal" value="${deliveryEligibleTotal}"/>
<input type="hidden" class="isGuestCheckoutEnabled" value="${isGuestCheckoutEnabled}"/>
<c:set var="freeShippingThreshold" value="<%=de.hybris.platform.util.Config.getParameter(\"shipping.fee.threshold\")%>"/>
<input type="hidden" class="freeShippingThreshold" value="${freeShippingThreshold}"/>
<c:set var="homeOwnerCode" value="<%=de.hybris.platform.util.Config.getParameter(\"homeOwner.trade.class.code\")%>"/>
<input type="hidden" class="homeOwnerCode" value="${homeOwnerCode}"/>
<input type="hidden" class="trade-class" value="${cartData.orderingAccount.tradeClass}"/>
<input type="hidden" class="subTotal-class" value="${cartData.subTotal.value}"/>
<input type="hidden" class="isDeliveryTampaBranch" value="${cartData.isDeliveryTampaBranch}"/>
<input type="hidden" class="isPickupaAllowedBranch" value="${cartData.isPickupaAllowedBranch}"/>
<input type="hidden" class="isTampaBranch" value="${cartData.isTampaBranch}"/>
<input type="hidden" class="isDeliveryLABranch" value="${cartData.isDeliveryLABranch}"/>
<input type="hidden" class="isLABranch" value="${cartData.isLABranch}"/>
<input type="hidden" value="${sessionStore.address.region.isocodeShort}"  id="california_location"/>
<input type="hidden" class="cartPageSize" value="${cartPageSize}"/>
<input type="hidden" class="cartPageCount" value="${cartPageCount}"/>
<c:set var="showingPriceLoader" value="false"/>
	<input id="sessionStore" type="hidden" value="${sessionStore.storeId}">
	<input type="hidden" class="isMixedCartEnabled" value="${isMixedCartEnabled}"/>
	<input type="hidden" id="cartItemsCountVar"
		value="${fn:length(cartData.entries)}" />
	<input type="hidden" class="cartDeliveryDisabled" value="${cartDeliveryDisabled}" />
	<c:set var="enableFullfilment" value="true"/>
	<c:set var="enableShippingMsg" value="false"/>
	<c:set var="enableShippingFullfilment" value="false"/>
	<c:set var="enableTopFullfilment" value="false"/>
	<c:set var="isShippingHubOnlyCart" value="false"/>
	<c:set value="false" var="isNationalShippingInCart"></c:set>
	<c:set value="true" var="isBranchPickupAvailable"></c:set>
	<c:set value="true" var="isBranchDeliveryAvailable"></c:set>
	<c:set value="true" var="isBranchShippingAvailable"></c:set>
	<c:set value="false" var="isDeliveryOnlyBranch"></c:set>
	<c:set var="nearbyContinue" value="true"/>
	<c:set var="nontransferable" value="false"/>
	<c:set var = "userAgent" value = "${fn:toLowerCase(header['User-Agent'])}"/>
	<c:set var = "mobileFlag" value = "false"/>
	<c:if test="${fn:contains(userAgent, 'iphone') || fn:contains(userAgent, 'android')}">
		<!-- If Mobile -->
		<c:set var = "mobileFlag" value = "true"/>
	</c:if>
<c:set var="isSplitMixedPickupBranchFE" value="false" />
 <c:set var="isQtyErrorMsg" value="false"/>
<c:if test="${not empty isSplitMixedCartEnabledBranch && isSplitMixedCartEnabledBranch ne null}" >
	<c:set value="${isSplitMixedCartEnabledBranch}" var="isSplitMixedPickupBranchFE"></c:set>
</c:if>
<c:set var="isSplitCart" value="${isSplitMixedPickupBranchFE}"/>
<input type="hidden" class="isSplitCart" value="${isSplitCart}"/>
<c:forEach items="${cartData.entries}" var="entry">
	<c:if test="${entry.product.stockAvailableOnlyHubStore}">
		<c:set var="isShippingHubOnlyCart" value="true"/>
	</c:if>
	<c:if test="${entry.product.inStockImage || entry.product.isEligibleForBackorder || entry.product.stockAvailableOnlyHubStore}">
		<c:set var="enableTopFullfilment" value="true"/>
	</c:if>
   <c:if test="${entry.product.outOfStockImage and isMixedCartEnabled ne true}">
     <c:set var="enableFullfilment" value="false"/>
   </c:if>
   <c:if test="${(entry.product.isStockInNearbyBranch and isMixedCartEnabled ne true and entry.product.isTransferrable ne true)||(entry.product.productType eq 'Nursery' and (entry.quantity gt entry.product.stock.stockLevel) and entry.product.isTransferrable ne true) || ((entry.product.level1Category eq 'Materiales duros & Vida al Aire Libre' || entry.product.level1Category eq 'Hardscapes & Outdoor Living') && (entry.quantity gt entry.product.homeStoreAvailableQty) && entry.product.isTransferrable ne true && entry.product.isForceInStock ne true)}">
     <c:set var="isQtyErrorMsg" value="true"/>
   </c:if> 
    <c:if test="${((entry.product.productType eq 'Nursery' and (entry.product.level1Category eq 'Nursery' || entry.product.level1Category eq 'Vivero')) and (entry.quantity gt entry.product.stock.stockLevel) and entry.product.isTransferrable ne true and entry.product.isEligibleForBackorder ne true) || ((entry.product.level1Category eq 'Materiales duros & Vida al Aire Libre' || entry.product.level1Category eq 'Hardscapes & Outdoor Living') && (entry.quantity gt entry.product.homeStoreAvailableQty) && entry.product.isTransferrable ne true && entry.product.isForceInStock ne true)}">
     <c:set var="enableFullfilment" value="false"/>
   </c:if> 
   <c:set var="dopickupEnabled" value="true" />	
	<c:set var="dodeliveryEnabled" value="false" />
	<c:set var="doshippingEnabled" value="false" />
	<c:set var="noFulfillmentavailable" value="false" />

	<c:if test="${(entry.product.outOfStockImage || entry.product.stockAvailableOnlyHubStore || (entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true))}">
		<c:set var="dopickupEnabled" value="false" />
	</c:if>

	<c:if test="${(entry.product.isEligibleForDelivery && entry.product.isDeliverable && !entry.product.stockAvailableOnlyHubStore && (!(entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true)))}">
		<c:set var="dodeliveryEnabled" value="true" />
	</c:if>

	<c:if test="${((entry.product.isShippable || entry.product.stockAvailableOnlyHubStore) && (!(entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true)))}">
		<c:set var="doshippingEnabled" value="true" />
	</c:if>

	<c:if test="${!dopickupEnabled && !dodeliveryEnabled && !doshippingEnabled}">
		<c:set var="noFulfillmentavailable" value="true" />
		<c:set var="enableFullfilment" value="false"/>
	</c:if>
	<input type="hidden" class="dopickupEnabled" value="${dopickupEnabled}"/>
	<input type="hidden" class="dodeliveryEnabled" value="${dodeliveryEnabled}"/>
	<input type="hidden" class="doshippingEnabled" value="${doshippingEnabled}"/>
	<input type="hidden" class="noFulfillmentavailable" value="${noFulfillmentavailable}"/>
   <c:if test="${!entry.product.isShippable}">
   <c:set var="enableShippingMsg" value="true"/>
   </c:if>
	<c:if test="${entry.product.isShippable && segmentLevelShippingEnabled}">
	<c:set var="enableShippingFullfilment" value="true"/>
	</c:if>	
	<c:if test="${guestUsers eq 'guest' && entry.product.hideList}">
		<c:set var="globalErrorMsgForHidelist" value="true"/>
	</c:if>
	
	<c:if test="${entry.product.isStockInNearbyBranch}">
		<c:set var="nearbyContinue" value="false"/>
	</c:if>
	 
	<c:if test="${entry.product.isTransferrable eq false and entry.product.isStockInNearbyBranch eq true}">
		<c:set var="nontransferable" value="true"/>
	</c:if>
	
	<c:if test="${entry.product.isRegulateditem}">
	<c:set var="isProductSellable" value="false" />
		<c:forEach items="${entry.product.regulatoryStates}" var="regulatoryStates">
			<c:if test="${regulatoryStates eq sessionStore.address.region.isocodeShort}">
				<c:set var="isProductSellable" value="true" />
			</c:if>
		</c:forEach>
	</c:if>
	
	
</c:forEach>
<c:set var="isSplitCartFulfilment" value="false"></c:set>
<c:if test="${isSplitCart && isShippingHubOnlyCart}" >
	<c:set var="isSplitCartFulfilment" value="true"></c:set>
</c:if>
<input type="hidden" class="isSplitCartFulfilment" value="${isSplitCartFulfilment}"/>
<input type="hidden" name="nursery-productType" value="${entry.product.productType}"/>
<input type="hidden" name="cart-entryQty" value="${entry.quantity}"/>
<input type="hidden" name="cart-enableFullfilment" value="${enableFullfilment}"/>
<input type="hidden" name="item-productStockLevel" value="${entry.product.stock.stockLevel}"/>
<input type="hidden" class="isQtyErrorMsg" value="${isQtyErrorMsg}"/>
<div class="col-xs-6 hidden printedTimeWrapper show-print">
	<h3 class="font-Geogrotesque-bold marginTop10 text-uppercase">
		<spring:theme code="cart.print.text1" />
	</h3>
</div>
<div class="show-print hr-line"></div>
<div class="font-size-14 hidden sub-heading1 show-print">
	<spring:theme code="cart.print.text2" />
</div>
<div class="f-s-16 font-Geogrotesque-bold hidden sub-heading2 text-uppercase show-print">
	<span><spring:theme code="cart.print.text3" /></span>
</div>
<div class="row hidden show-print paddingBottom5 no-margin print-table-heading p-t-15">
	<div class="col-xs-6 p-l-0"><strong><spring:theme code="saveListDetailsPage.print.product" /></strong></div>
	<div class="col-xs-3"><strong><spring:theme code="saveListDetailsPage.print.price" /></strong></div>
	<div class="col-xs-1 p-l-0 text-align-center"><strong><spring:theme code="saveListDetailsPage.print.quantity" /></strong></div>
	<div class="col-xs-2 text-align-right p-r-0"><strong><spring:theme code="saveListDetailsPage.print.totals" /></strong></div>
</div>


<c:if test="${not empty sessionStore.pickupfullfillment && sessionStore.pickupfullfillment ne null}" >
	<c:set value="${sessionStore.pickupfullfillment}" var="isBranchPickupAvailable"></c:set>
</c:if>
<c:if test="${not empty sessionStore.deliveryfullfillment && sessionStore.deliveryfullfillment ne null}" >
	<c:set value="${sessionStore.deliveryfullfillment}" var="isBranchDeliveryAvailable"></c:set>
</c:if>
<c:if test="${not empty sessionStore.shippingfullfillment && sessionStore.shippingfullfillment ne null}" >
	<c:set value="${sessionStore.shippingfullfillment}" var="isBranchShippingAvailable"></c:set>
</c:if>
<c:if test="${!cartData.isTampaBranch && !cartData.isLABranch && isShippingHubOnlyCart}" >
	<c:set value="true" var="isNationalShippingInCart"></c:set>
</c:if>
<input type="hidden" name="isSplitMixedPickupBranchFE" value="${isSplitMixedPickupBranchFE}"/>
<input type="hidden" class="branch-pickupAvailable" value="${isBranchPickupAvailable}"/>
<input type="hidden" class="branch-deliveryAvailable" value="${isBranchDeliveryAvailable}"/>
<input type="hidden" class="branch-shippingAvailable" value="${isBranchShippingAvailable}"/>
<c:if test="${!isBranchPickupAvailable && isBranchDeliveryAvailable && !isBranchShippingAvailable}" >
	<c:set value="true" var="isDeliveryOnlyBranch"></c:set>
</c:if>
<input type="hidden" class="isDeliveryOnlyBranch" value="${isDeliveryOnlyBranch}"/>
<input class="isNationalShippingInCart" type="hidden" value='${isNationalShippingInCart}'>
<input type="hidden" class="nearbyContinue" value="${nearbyContinue}"/>

<input type="hidden" class="isShippingHubOnly" value="${isShippingHubOnlyCart}"/>
<input type="hidden" class="enableFullfilment" value="${enableFullfilment}"/>
<input type="hidden" class="enableParcelShipping" value="${enableShippingFullfilment}"/>
	<c:if test="${not empty accErrorMsgs}">
		<c:forEach items="${accErrorMsgs}" var="msg">
			<c:if test="${(msg.code eq 'cart.checkout.disable.message')}">
				<input type="hidden" id="isCheckoutErrorMessagePresent" value="true">
			</c:if>
		</c:forEach>
	</c:if>
	<c:if test="${not empty accInfoMsgs}">
		<c:forEach items="${accInfoMsgs}" var="msg">
			<c:if test="${(msg.code eq 'checkout.multi.pos.invalid')}">
				<input type="hidden" id="isCheckoutErrorMessagePresent" value="true">
			</c:if>
		</c:forEach>
	</c:if>
	<c:if test="${not empty accErrorMsgs}">
		<c:forEach items="${accErrorMsgs}" var="msg">
			<c:if test="${(msg.code eq 'checkout.orderreview.pricechanges')}">
				<input type="hidden" id="isPriceChangeMessagePresent" value="true">
				<input type="hidden" id="cmspageid" value="${cmsPage.uid}">
			</c:if>
		</c:forEach>
	</c:if>
	

	
	<c:url value="/checkout/multi/siteOne-checkout" var="checkoutUrl" scope="session"/>
	<div class="nearby-continue" style="display:none;">
		<p><spring:theme code="nearby.text.message"/></p>
			<div class="margin20 text-center"><a href="${checkoutUrl}" class="btn btn-primary mb-text3 col-md-8 col-xs-12 float-none accept-delay"><spring:theme code="nearby.btn.accept"/></a></div>
			<div  class="text-center"><a class="btn btn-default col-md-8 col-xs-12 float-none mb-text3 closeColorBox dontaccept-delay"><spring:theme code="nearby.btn.donotaccept"/></a></div>
	</div>
	
	<c:if test="${not empty cartData.entries}">
		<sec:authorize access="hasAnyRole('ROLE_ANONYMOUS')">
			<c:if
				test="${null == cookie['j_username'] && cookie['j_username'].value == ''}">
				<div style="padding: 15px"></div>
				<div class="alert alert-info alert-dismissable">
					<button class="close" type="button" data-dismiss="alert"
						aria-hidden="true">x</button>
					<spring:theme code="cart.csp.message" />
				</div>
			</c:if>
		</sec:authorize>
	</c:if>

	<c:choose>
		<c:when
			test="${empty cookie['gls'] && sessionStore.isPreferredStore eq false && empty cookie['csc']}">


		</c:when>
		<c:otherwise>

			<input id="sessionStore" type="hidden"
				value="${sessionStore.storeId}">

			<c:if test="${not empty accErrorMsgs}">
				<c:forEach items="${accErrorMsgs}" var="msg">
					<c:if test="${(msg.code eq 'cart.checkout.disable.message')}">
						<input type="hidden" id="isCheckoutErrorMessagePresent"
							value="true">
					</c:if>
				</c:forEach>
			</c:if>
			<c:if test="${not empty accInfoMsgs}">
				<c:forEach items="${accInfoMsgs}" var="msg">
					<c:if test="${(msg.code eq 'checkout.multi.pos.invalid')}">
						<input type="hidden" id="isCheckoutErrorMessagePresent"
							value="true">
					</c:if>
				</c:forEach>
			</c:if>
			<c:if test="${not empty accErrorMsgs}">
				<c:forEach items="${accErrorMsgs}" var="msg">
					<c:if test="${(msg.code eq 'checkout.orderreview.pricechanges')}">
						<input type="hidden" id="isPriceChangeMessagePresent" value="true">
						<input type="hidden" id="cmspageid" value="${cmsPage.uid}">
					</c:if>
				</c:forEach>
			</c:if>
			
			<c:if test="${not empty cartData.entries}">
				<sec:authorize access="hasAnyRole('ROLE_ANONYMOUS')">
					<div class="hidden-print" style="padding: 15px"></div>
					<c:if test="${!guestUsers eq 'guest'}">
					<div class="alert alert-green alert-dismissable">
						<button class="close" type="button" data-dismiss="alert"
							aria-hidden="true">
							<common:crossIcon iconColor="#78a22f" />
						</button>
						<spring:theme code="cart.csp.message" />
					</div>
					</c:if>
				</sec:authorize>
			</c:if>

			<input type="hidden" id="listOfShipTos"
				value='${ycommerce:getJSONFromList(listOfShipTo)}' />


			<div class="cl"></div>
		<c:if test="${!isSplitCart}">
			<div class="row cart-page-container ${isSplitCart ? ' hidden ' : ''}" style="${(isNationalShippingInCart eq true || isBranchPickupAvailable eq false || isBranchDeliveryAvailable eq false || isBranchShippingAvailable eq false)? 'opacity:0;' : ''}">
				<div class="col-md-9 cart-left-container col-sm-12">
					<!-- Cart Page title -->
					<div class="row flex-center marginBottom20">
						<div class="col-md-6 flex-center col-xs-7 p-r-0 m-t-10-xs cart-print-heading hidden-print">
							<h1 class="headline">
								<spring:theme code="text.cart.headline" />
							</h1>
							<div class="cart-total-items">${cartData.entries.size() lt 10000?cartData.entries.size():"9999+"}</div>
						</div>
						<div class="hidden cart-address">
							<div class="col-xs-12 selectedBranchWrapper">
								<span class="showBranch"><spring:theme
											code="cartPage.selected.branch" />:</span> <span
									id="selectedBranch"></span>
								<div class="branchdesc">
									<p>
										<spring:theme code="cartPage.siteone" />
									<p>
									<p>
										<span class="line1">${sessionStore.address.line1}</span>&nbsp;<span
											class="line2">${sessionStore.address.line2}</span>
									</p>
									<p>
										<span class="town">${sessionStore.address.town},</span>&nbsp;<span
											class="region">${sessionStore.address.region.isocodeShort}</span>&nbsp;
										<span class="postalCode">${sessionStore.address.postalCode}</span>
									</p>
									<p>${sessionStore.address.phone}</p>

								</div>
							</div>
						</div>
						<div class="col-md-9 col-xs-6 flex-sm flex-dir-column-xs m-t-15-xs hidden-print" data-cartData="${cartData.entries}" data-guestUsers="${guestUsers}" data-isAnonymous="${isAnonymous}" data-mobileFlag="${mobileFlag}">
							<div class="row">
								<div class="col-md-4 col-xs-12 text-align-right hidden-print hidden-xs hidden-sm">
									<c:if test="${not empty cartData.entries && (guestUsers ne 'guest') && !isAnonymous && !mobileFlag}">
										<button class="btn btn-link p-t-0-imp bold no-text-decoration flex upload-csv" onclick="ACC.cart.csvCartOpenAndResponseHandle('openCart');ACC.adobelinktracking.cartCsvUpload('Upload CSV','','checkout: cart','checkout: cart: upload csv popup', 'checkout', 'checkout: cart: upload csv popup');">
											<span  class="glyphicon-position p-r-10"><common:globalIcon iconName="upload" iconFill="none" iconColor="#4492B6" width="18" height="17" viewBox="0 0 18 18"/></span>
											<spring:theme code="cartPage.shop.uploadcsv" />
										</button>
									</c:if>
								</div>
								<div class="col-md-4 col-xs-12 xs-right hidden-print">
									<c:if test="${not empty cartData.entries}">
										<a class="bold text-right no-text-decoration shareCart cart-email-link">
											<span class="glyphicon-position p-r-5 hidden-md hidden-lg"><common:envelopeIcon iconColor="#50A0C5" width="12" height="9"/></span>
											<span class="glyphicon-position p-r-5 hidden-xs hidden-sm"><common:envelopeIcon iconColor="#50A0C5" width="18" height="13.5"/></span>
											<spring:theme code="cartPage.email" />
										</a>
									</c:if>
									<input id="cartCode" value="${cartData.code}" hidden />
								</div>
								<div class="col-md-4 col-xs-12 xs-right hidden-print">
									<c:if test="${not empty cartData.entries}">
										<a class="bold text-right no-text-decoration font-small-xs pointer printOrderDetails">
											<span class="glyphicon p-r-5 glyphicon-print"></span>
											<spring:theme code="cart.print.text5" />
										</a>
									</c:if>
								</div>
							</div>
						</div>
					</div>
					<sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')" var="loggedIn">
						<c:if test="${not empty cartData.entries and cartData.entries.size() > cartPageSize}">
							<c:set var="showingPriceLoader" value="true"/>
						</c:if>
					</sec:authorize>
					<!-- Cart Page title Ends -->
					<c:if test="${not empty cartData.entries}">
						<div class="row hidden-print">
							<div class="col-md-4 paddingBottom5">
							<a href="#" id="remove-all-items-cart">
								<spring:theme code="text.cart.removeAll"/>
							</a>
							</div>
						</div>
					</c:if>
					<!-- Fulfillment Methods starts -->
					<input type="hidden" value="${cartData.orderType}" class="cart-orderType"/>
					<c:if test="${(loggedIn eq true) || (guestUsers eq 'guest')}">
						<div class="row margin-xs-hrz--25 ${isMixedCartEnabled?'hidden':''} hidden-print">
							<c:if test="${not empty cartData.entries}">
								<div class="col-md-12 col-xs-12 no-padding-xs">
									<div class="cart-top-fulfillment-method">
										<input type="hidden" class="changed_selectedFilfillment" value="${cartData.orderType}" />
										<div class="row">
											<div class="col-md-5 col-xs-12 cart-top-fulfillment-method__heading">
												<spring:theme code="text.cart.fulfilment.title" />
											</div>
											<div class="col-md-7 col-xs-12 cart-fulfilment-tile-wrapper" data-isLABranch="${cartData.isLABranch}" data-isTampaBranch="${cartData.isTampaBranch}" data-isAllShippable="${!enableShippingMsg}" data-isAnyShippable="${enableShippingFullfilment}">
												<div class="row  flex-md">
													<c:choose>
														<c:when test="${enableTopFullfilment ne false}">
															<div class="col-md-4 col-xs-12 cart-top-fulfillment-method__tile no-padding-rgt-md fullfillment-pickup branch-pickup ${(cartData.orderType eq 'PICKUP' or cartData.orderType eq '')?'fullfilment-select':'' } js-fullfilment" data-fullfillment="PICKUP">
																<div class="info-tooltip js-info-tootip"  rel="custom-tooltip">
																	<div class="tooltip-content hide">
																	<common:FulfillmentTooltipContent fulfillment="pickUp"/>
																	</div> 
																	<common:info-circle iconColor="#78a22f"/>
																</div>
																<div class="flex-center-xs flex-center-sm pad-xs-rgt-20">
																	<div class="hidden-xs">
																		<common:pickUpIcon height="24" width="30" iconColor="#000" />
																	</div>
																	<div class="hidden-md hidden-lg hidden-sm">
																		<common:pickUpIcon height="18" width="26" iconColor="#000" />
																	</div>
																	<div class="font-small-xs pad-xs-lft-10 pad-sm-lft-10 font-small-sm font-small-md">
																		<div class="bold inline-xs inline-sm"><spring:theme code="text.cart.branch.pickup"/></div>
																		<div class="pickUpAvail inline-xs inline-sm"><spring:theme code="text.cart.availableat.store" arguments="${sessionStore.name},${sessionStore.storeId}"/></div>
																		<div class="pickUpNotAvail inline-xs inline-sm hidden"><spring:theme code="text.cart.unavailable.for.order"/></div>
																	</div>
																</div>
															</div>
															<div class="col-md-4 col-xs-12 cart-top-fulfillment-method__tile local-delivery fullfillment-delivery  ${(cartData.orderType eq 'DELIVERY')?'fullfilment-select':'' } js-fullfilment" data-fullfillment="DELIVERY">
																<div class="info-tooltip js-info-tootip"  rel="custom-tooltip">
																	<div class="tooltip-content hide">
																		<common:FulfillmentTooltipContent fulfillment="delivery"/>
																	</div>
																	<span class="hidden-xs hidden-sm"><common:info-circle iconColor="#78a22f"/></span>
																	<span class="hidden-md hidden-lg pikuponly">
																			<c:choose>
																			<c:when test="${cartDeliveryDisabled eq 'true'}">
																			<common:info-circle iconColor="#ED8606"/>
																			</c:when>
																			<c:otherwise>
																			<common:info-circle iconColor="#78a22f"/>
																			</c:otherwise>
																			</c:choose>
																	</span>    
																</div>
																<div class="flex-center-xs flex-center-sm pad-xs-rgt-20">												
																	<div class="hidden-xs">
																		<common:deliveryIcon height="24" width="45" iconColor="#000" />
																	</div>
																	<div class="hidden-md hidden-lg hidden-sm">
																		<common:deliveryIcon height="18" width="26" iconColor="#000" />
																	</div>
																	<div class="font-small-xs pad-xs-lft-10 pad-sm-lft-10 font-small-sm font-small-md">
																		<div class="bold inline-xs inline-sm"><spring:theme code="variant.text.fulfillment.delivery"/></div>
																		<div class="deliveryAvail inline-xs inline-sm hidden"><spring:theme code="text.cart.available.for.order"/></div>
																		<div class="deliveryNotAvail inline-xs inline-sm hidden"><spring:theme code="text.cart.unavailable.for.order"/></div>
																	</div>
																</div>
															</div>
															<div class="col-md-4 col-xs-12 cart-top-fulfillment-method__tile parcel-shipping fullfillment-shipping ${(cartData.orderType eq 'PARCEL_SHIPPING')?'fullfilment-select':'' } js-fullfilment" data-fullfillment="PARCEL_SHIPPING">
																<div class="info-tooltip js-info-tootip${(!cartData.isLABranch && !cartData.isTampaBranch && enableShippingFullfilment)? ' hidden' : ''}" rel="custom-tooltip">
																	<div class="tooltip-content hide">
																		<common:FulfillmentTooltipContent fulfillment="parcel"/>
																	</div> 
																	<common:info-circle iconColor="#78a22f"/>
																</div>
																<div class="flex-center-xs flex-center-sm pad-xs-rgt-20">
																	<div class="hidden-xs">
																		<common:parcelIcon height="24" width="32" iconColor="#000" />
																		
																	</div>
																	<div class="hidden-md hidden-lg hidden-sm">
																		<common:parcelIcon height="18" width="26" iconColor="#000" />
																	</div>
																	<div class="font-small-xs pad-xs-lft-10 pad-sm-lft-10 font-small-sm font-small-md">
																			<div class="bold inline-xs inline-sm"><spring:theme code="text.cart.parcel.shipping"/></div>
																			<div class="parcelAvail inline-xs inline-sm hidden"><spring:theme code="text.cart.available.for.order"/></div>
																			<div class="parcelUnavailable inline-xs inline-sm hidden"><spring:theme code="text.cart.unavailable.for.order"/></div>
																	</div>												
																</div>										

															</div>
														</c:when>
														<c:otherwise>
															<div class="col-md-4 cart-top-fulfillment-method__tile disabled-fullfillment-method no-padding-rgt-md">
																	<div class="info-tooltip js-info-tootip"  rel="custom-tooltip">
																		<div class="tooltip-content hide">
																			<common:FulfillmentTooltipContent fulfillment="pickUp"/>
																		</div> 
																		<common:info-circle iconColor="#78a22f"/>
																	</div>
																<div class="flex-xs flex-sm pad-xs-rgt-20">
																	<div class="hidden-xs">
																		<common:pickUpIcon height="24" width="30" iconColor="#ccc" />
																	</div>
																	<div class="hidden-md hidden-lg hidden-sm">
																		<common:pickUpIcon height="18" width="26" iconColor="#ccc" />
																	</div>
																	<div class="font-small-xs pad-xs-lft-10 pad-sm-lft-10 font-small-sm font-small-md">
																		<div class="bold inline-xs inline-sm"><spring:theme code="text.cart.branch.pickup"/></div>
																		<div class="inline-xs inline-sm"><spring:theme code="text.cart.unavailable.at"/> ${sessionStore.name}</div>
																	</div>
																</div>
															</div>
															<div class="col-md-4 cart-top-fulfillment-method__tile disabled-fullfillment-method">
																<div class="info-tooltip js-info-tootip"  rel="custom-tooltip">
																		<div class="tooltip-content hide">
																			<common:FulfillmentTooltipContent fulfillment="delivery"/>
																		</div> 
																				<common:info-circle iconColor="#78a22f"/>
																</div>
																<div class="flex-xs flex-sm pad-xs-rgt-20">
																	<div class="hidden-xs">
																		<common:deliveryIcon height="24" width="45" iconColor="#ccc" />
																	</div>
																	<div class="hidden-md hidden-lg hidden-sm">
																		<common:deliveryIcon height="18" width="26" iconColor="#ccc" />
																	</div>
																	<div class="font-small-xs pad-xs-lft-10 pad-sm-lft-10 font-small-sm font-small-md">
																		<div class="bold inline-xs inline-sm"><spring:theme code="variant.text.fulfillment.delivery"/></div>
																		<div class="inline-xs inline-sm"><spring:theme code="text.cart.unavailable.for.order"/></div>
																	</div>
																</div>
															</div>
															<div class="col-md-4 cart-top-fulfillment-method__tile disabled-fullfillment-method">
																<div class="info-tooltip js-info-tootip"  rel="custom-tooltip">
																		<div class="tooltip-content hide">
																			<common:FulfillmentTooltipContent fulfillment="parcel"/>
																		</div> 
																		<common:info-circle iconColor="#78a22f"/>
																</div>
																<div class="flex-xs flex-sm pad-xs-rgt-20">
																	<div class="hidden-xs">
																		<common:parcelIcon height="24" width="32" iconColor="#ccc" />
																		
																	</div>
																	<div class="hidden-md hidden-lg hidden-sm">
																		<common:parcelIcon height="18" width="26" iconColor="#cc" />
																	</div>
																	<div class="font-small-xs pad-xs-lft-10 pad-sm-lft-10 font-small-sm font-small-md">
																		<div class="bold inline-xs inline-sm"><spring:theme code="text.cart.parcel.shipping"/></div>
																		<div class="inline-xs inline-sm"><spring:theme code="variant.text.fulfillment.unavailable"/></div>								
																	</div>
																</div>
															</div>
															
														</c:otherwise>
													</c:choose>
												</div>
											</div>
										</div>
									</div>
									<div class="cl"></div>
									<c:if test="${cartDeliveryDisabled eq 'true'}">
										<div class="black-title flex-center row">
											<div class="col-xs-12">
												<div class="flex-center pickupOnly-error-msg sm-center xs-center">
												<div class="hidden-sm hidden-xs"><common:exclamation-circle width="49" height="49"/></div>
												<div class="pad-lft-10">
												<div class="bold"><spring:theme code="basket.newerror.disabledelivery" /></div>
												<div class="col-xs-12 padding0 f-s-12 font-small-sm font-small-xs"><spring:theme code="basket.newerror2.disabledelivery" /></div>
												</div>
												</div>
											</div>
										</div>
									</c:if>
									<div class="black-title flex-center row m-t-5 m-b-10 hardscape-stone-alert-box hidden">
										<div class="col-xs-12">
											<div class="flex-center hardscape-stone-alert hardscape-highlight-background sm-center xs-center">
												<div class="m-t-5"><common:exclamation-circle width="30" height="30" /></div>
												<div class="p-l-15">
													<div class="font-size-14 text-align-left text-default">
														<span class="bold"><spring:theme code="productDetails.hardscape.stone.price" /></span>
														<span>&nbsp;<spring:theme code="productDetails.hardscape.stone.weight.cart" />&nbsp;<spring:theme code="productDetails.hardscape.stone.received.cart" /></span>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</c:if>
						</div>
					</c:if>
					<c:if test="${((loggedIn eq true) || (guestUsers eq 'guest')) and (isMixedCartEnabled ne true)}">
					    <c:if test="${not empty cartData.entries && enableTopFullfilment ne false}">
							<!-- Delivery error message -->
							<div class="cartHomeOwner-msg row hidden hidden-print">
								<div class="col-xs-12">
									<div class="flex-center font-small-xs cart-delivery-threshold-message marginBottom20">
									<common:deliveryIcon height="18" width="37" iconColor="#78a22f"/>
									<div class="pad-lft-10"><spring:theme code="cart.delivery.enable.condition.message" arguments="${(subtotalLimit) - (deliveryEligibleTotal)}"/></div></div>
								</div>
							</div>
                    		<!-- Shipping Threshold message -->
							<c:if test="${(cartData.freeShippingThreshold > cartData.subTotal.value) && (cartData.isTampaBranch || cartData.isLABranch || cartData.isShippingFeeBranch)}">
								<div class="shippingThreshold-msg row hidden hidden-print">
								<div class="col-xs-12">
										<div class="flex-center font-small-xs cart-delivery-threshold-message marginBottom20">
										<common:freeShippingIcon height="18" width="37" />
										<div class="pad-lft-10"><spring:theme code="cart.free.shipping.condition.message" arguments="${(cartData.freeShippingThreshold) - (cartData.subTotal.value)}"/></div></div>
										</div>
								</div>
                        	</c:if>
                        </c:if>
					</c:if> 
				
					<!-- Fulfillment Methods ends -->
					<!-- Error Messages -->
					<cart:cartValidation />
					<cart:cartPickupValidation />
						
					<!-- Pricing for one or more products is currently unavailable online -->
					<div class="gc-error-msg marginTop20  flex-center row hidden" id="disableCheckoutMessage">
						<div class="col-xs-12">
								<div class="flex-center gc-cart-global-error-msg">
											<span><common:exclamation-triangle iconColor="#bc0000"/></span>
								<c:set var="count" value="0" />
								<c:forEach var="entry" items="${cartData.entries}">
									<c:if test="${entry.product.hideCSP && count eq '0'}">
										<c:set var="count" value="1" />
										<c:set var="showhideCSPErrormsg" value="true" />
									</c:if>
								</c:forEach>
								<c:if test="${showhideCSPErrormsg eq 'true'}">
									<span class="pad-lft-10"><spring:theme code="cart.product.hideCSP.message" /></span>
								</c:if>
								<c:if test="${showhideCSPErrormsg ne 'true'}">
									<span class="pad-lft-10"><spring:theme code="cart.checkout.disable.message" /></span>
								</c:if>
								</div>
						</div>
					</div>
					<!-- Pricing for one or more products is currently unavailable online ends-->
					<div class="pricing-changed-error-message">
								<div id="pricechangemessage" class="gc-error-msg marginTop20 hidden  flex-center row">
									<div class="col-xs-12 edit-user-alert"> 
										<div class="flex-center gc-cart-global-error-msg">
											<span><common:exclamation-triangle iconColor="#bc0000"/></span>
											<span class="pad-lft-10"><spring:theme code="checkout.orderreview.pricechanges"/></span>
										</div>
									</div>
								</div>
								
					</div>
						
					<!-- There is a problem error message -->
					<c:if test="${(enableShippingMsg eq true) or (globalErrorMsgForHidelist eq true) or (nontransferable eq true) or (isProductSellable eq false) or (isNationalShippingInCart)}">
						<div class="gc-error-msg marginTop20 js-gc-problem-error flex-center row hidden-print  ${enableShippingMsg ? 'disable-parcel-checkout' : ''} ${nontransferable ? '' :(isProductSellable eq false ? '': isNationalShippingInCart ? '' : ' hidden')}" >
							<div class="col-xs-12">
								<div class="flex-center gc-cart-global-error-msg">
									<span><common:exclamation-triangle iconColor="#bc0000"/></span>
									<span class="pad-lft-10"><spring:theme code="text.cart.problem.msg"/></span>
								</div>
							</div>
						</div>
					</c:if>
					<div class="row marginTopBVottom20 hidden national-shipping-nonship-element hidden-print">
						<div class="col-xs-12">
							<div class="font-size-14 text-default font-small-xs padding-20 flex-center cart-alert-message">
								<common:exclamation-circle width="30" height="30"/>
								<span class="pad-lft-10"><span class="block bold"><spring:theme code="text.cart.problem.shipping.heading" /></span><spring:theme code="text.cart.problem.shipping.msg" /></span>
							</div>
						</div>
					</div>
					<c:if test="${isNationalShippingInCart}">
						<!-- <div class="row marginTopBVottom20 hidden national-shipping-nonship-element hidden-print">
							<div class="col-xs-12">
								<div class="font-size-14 font-small-xs padding-20 flex-center cart-alert-message">
									<common:exclamation-circle width="30" height="30"/>
									<span class="pad-lft-10"><span class="block bold"><spring:theme code="text.cart.problem.shipping.heading" /></span><spring:theme code="text.cart.problem.shipping.msg" /></span>
								</div>
							</div>
						</div> -->
						<div class="row marginTopBVottom20 hidden national-shipping-ship-element hidden-print">
							<div class="col-xs-12">
								<div class="font-size-14 text-default font-small-xs padding-20 flex-center cart-alert-message">
									<common:exclamation-circle width="30" height="30"/>
									<span class="pad-lft-10"><span class="block bold"><spring:theme code="text.cart.problem.ship.heading" /></span><spring:theme code="text.cart.problem.ship.msg" /></span>
								</div>
							</div>
						</div>
					</c:if>
					<!-- There is a problem error message -->
					<!-- Error Messages Ends -->
					<cms:pageSlot position="TopContent" var="feature">
						<cms:component component="${feature}" element="div"
							class="yComponentWrapper"  />
					</cms:pageSlot>
					<div class="hidden show-print print-footer text-uppercase text-right" >
						<div class="footer-item col-xs-3 cart-total-loader-show${showingPriceLoader eq true? ' hidden' : ''}">
							<span><spring:theme code="order.form.subtotal" /></span>
							<span class="js-cart-subtotal">${cartData.subTotal.formattedValue}</span>
						</div>
						<div class="footer-item col-xs-3${showingPriceLoader eq true? ' hidden' : ''}">
							<span><spring:theme code="basket.page.totals.netTax" /></span>
							<span>${cartData.totalTax.formattedValue}</span>
						</div>
						<c:set var="totalWithTax" value="${cartData.subTotal.value + cartData.totalTax.value}" />
						<div class="footer-item col-xs-3 cart-total-loader-show${showingPriceLoader eq true? ' hidden' : ''}">
							<span><spring:theme code="text.quickOrder.page.total" /></span>
							<span class="js-cart-total-with-tax">
								<span class="text-default-print">$</span><span>${totalWithTax}</span>
							</span>
						</div>
					</div>
					<div class="hidden show-print footer-thanks">
						<snap><spring:theme code="cart.print.text4" /></snap>
						<snap><spring:theme code="text.contactUs.storeHead" /></snap>
					</div>
				</div>
				<div class="col-md-3 hidden-print col-sm-12">
					<c:if test="${not empty cartData.entries}">
						<cms:pageSlot position="CenterRightContentSlot" var="feature">
							<cms:component component="${feature}" element="div"
								class="yComponentWrapper" />
						</cms:pageSlot>

					</c:if>
				</div>
				<c:if test="${algonomyRecommendationEnabled && not empty cartData.entries}">
						<div class="featured-content margin-top-20 product-recommend-margin hidden-print" id="RecommendedProductCartSlot">
		
						</div>
			   </c:if>
			</div>
		</c:if>
		<c:if test="${isSplitCart}">
			<div class="row split-cart-container cart-page-container">
				<div class="col-md-9 col-sm-12 split-cart-container-left">
					<!-- Split Cart Page title -->
					<div class="row flex-center marginBottom20">
						<div class="col-md-6 flex-center col-xs-7 p-r-0 m-t-10-xs m-t-10 cart-print-heading hidden-print">
							<h1 class="headline">
								Your Cart
							</h1>
							<div class="cart-total-items">${cartData.entries.size() lt 10000?cartData.entries.size():"9999+"}</div>
							<c:if test="${not empty cartData.entries && (guestUsers ne 'guest' || !isAnonymous)}">
								<div class="font-Arial m-l-20 text-default hidden-xs hidden-print split-shipping-count split-pickup-count-section hidden"><spring:theme code="text.account.order.type.display.PICKUP" /> (<span class="pickup_count">0</span><span>)</span> </div>
								<div class="font-Arial m-l-20 text-default hidden-xs hidden-print split-shipping-count split-delivery-count-section hidden"><spring:theme code="text.delivery.title" /> (<span class="delivery_count">0</span><span>)</span> </div>
								<ul class="m-b-0 m-t-0 p-l-10"><li class="font-Arial m-l-20 text-default hidden-xs hidden-print split-shipping-count hidden"><spring:theme code="text.account.order.type.display.PARCEL_SHIPPING" /> (<span class="shipping_count">0</span><span>)</span> </li>
								</ul>
							</c:if>	
						</div>
						<!-- Split Cart Page Email Upload Print -->
						<div class="col-md-6 col-xs-6 flex-sm flex-dir-column-xs m-t-15-xs hidden-print split-options-section" data-cartData="${cartData.entries}" data-guestUsers="${guestUsers}" data-isAnonymous="${isAnonymous}" data-mobileFlag="${mobileFlag}">
							<div class="row">
								<div class="col-md-4 col-xs-12 text-align-right hidden-print hidden-xs hidden-sm">
									<c:if test="${not empty cartData.entries && (guestUsers ne 'guest') && !isAnonymous && !mobileFlag}">
										<button class="btn btn-link p-t-0-imp bold no-text-decoration flex upload-csv" onclick="ACC.cart.csvCartOpenAndResponseHandle('openCart');ACC.adobelinktracking.cartCsvUpload('Upload CSV','','checkout: cart','checkout: cart: upload csv popup', 'checkout', 'checkout: cart: upload csv popup');">
											<span  class="glyphicon-position p-r-10"><common:globalIcon iconName="upload" iconFill="none" iconColor="#4492B6" width="18" height="17" viewBox="0 0 18 18"/></span>
											<spring:theme code="cartPage.shop.uploadcsv" />
										</button>
									</c:if>
								</div>
								<div class="col-md-4 col-xs-12 xs-right hidden-print">
									<c:if test="${not empty cartData.entries}">
										<a class="bold text-right no-text-decoration shareCart cart-email-link">
											<span class="glyphicon-position p-r-5 hidden-md hidden-lg"><common:envelopeIcon iconColor="#50A0C5" width="12" height="9"/></span>
											<span class="glyphicon-position p-r-5 hidden-xs hidden-sm"><common:envelopeIcon iconColor="#50A0C5" width="18" height="13.5"/></span>
											<spring:theme code="cartPage.email" />
										</a>
									</c:if>
									<input id="cartCode" value="${cartData.code}" hidden />
								</div>
								<div class="col-md-4 col-xs-12 xs-right hidden-print">
									<c:if test="${not empty cartData.entries}">
										<a class="bold text-right no-text-decoration font-small-xs pointer printOrderDetails">
											<span class="glyphicon p-r-5 glyphicon-print"></span>
											<spring:theme code="cart.print.text5" />
										</a>
									</c:if>
								</div>
							</div>
						</div>
					</div>
					<sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')" var="loggedIn">
						<c:if test="${not empty cartData.entries and cartData.entries.size() > cartPageSize}">
							<c:set var="showingPriceLoader" value="true"/>
						</c:if>
					</sec:authorize>
					<!-- Split Cart Page Remove Option -->
					<c:if test="${not empty cartData.entries}">
						<div class="row hidden-print">
							<div class="col-md-4 paddingBottom5 hidden-md hidden-lg">
								<c:if test="${not empty cartData.entries}">
									<div class="font-Arial p-l-0 text-default hidden-print split-shipping-count hidden">
										<span class="split-pickup-count-section"><spring:theme code="text.account.order.type.display.PICKUP" />
										(<span class="pickup_count">0</span><span>)</span></span>
										<span class="split-delivery-count-section"><spring:theme code="text.delivery.title" />
										(<span class="delivery_count">0</span><span>)</span></span>
										<span class="small-bullet">&#8226;</span><span class="font-Arial m-l-10 text-default">
										<spring:theme code="text.account.order.type.display.PARCEL_SHIPPING" /> (<span class="shipping_count">0</span><span>)</span></span></div>									
								</c:if>	
							</div>
							<div class="col-md-4 paddingBottom5">
								<a href="#" id="remove-all-items-cart">
									<spring:theme code="text.cart.removeAll"/>
								</a>
							</div>
						</div>
					</c:if>
					<!-- Split Cart Page Fulfilments options 1 -->
					<input type="hidden" value="${cartData.orderType}" class="cart-orderType"/>
					<c:if test="${(loggedIn eq true) || (guestUsers eq 'guest')}">
						<div class="row margin-xs-hrz--25 ${isMixedCartEnabled?'hidden':''} hidden-print split-cart-top-all">
							<c:if test="${not empty cartData.entries}">
								<div class="col-md-12 col-xs-12 no-padding-xs">
									<div class="cart-top-fulfillment-method">
										<input type="hidden" class="changed_selectedFilfillment" value="${cartData.orderType}" />
										<div class="row">
											<div class="col-md-5 col-xs-12 cart-top-fulfillment-method__heading splitCartHeader">
												<span class="splitPickupDeliveryHeader hidden">
													<span class="f-s-26 font-Geogrotesque text-default"><spring:theme code="text.cart.fulfilment.title.one" /></span>
													<p class="f-s-26 font-Geogrotesque text-default bold m-t-5">
														<span>${sessionStore.name}</span>
													</p>
													<p class="f-s-16 font-Arial m-b-0"><spring:theme code="text.cart.fulfilment.title" /></p>
												</span>
												<span class="splitShippingHeader hidden"><spring:theme code="text.cart.fulfilment.title" /></span>

											</div>
											<div class="col-md-7 col-xs-12 cart-fulfilment-tile-wrapper" data-isLABranch="${cartData.isLABranch}" data-isTampaBranch="${cartData.isTampaBranch}" data-isAllShippable="${!enableShippingMsg}" data-isAnyShippable="${enableShippingFullfilment}">
												<div class="row  flex-md">
													<c:choose>
														<c:when test="${enableTopFullfilment ne false}">
															<div class="col-md-4 col-xs-12 cart-top-fulfillment-method__tile no-padding-rgt-md fullfillment-pickup branch-pickup ${(cartData.orderType eq 'PICKUP' or cartData.orderType eq '')?'fullfilment-select':'' } js-fullfilment" data-fullfillment="PICKUP">
																<div class="info-tooltip js-info-tootip"  rel="custom-tooltip">
																	<div class="tooltip-content hide">
																		<common:FulfillmentTooltipContent fulfillment="pickUp"/>
																	</div> 
																	<common:info-circle iconColor="#78a22f"/>
																</div>
																<div class="flex-center-xs flex-center-sm pad-xs-rgt-20">
																	<div class="hidden-xs">
																		<common:pickUpIcon height="24" width="30" iconColor="#000" />
																	</div>
																	<div class="hidden-md hidden-lg hidden-sm">
																		<common:pickUpIcon height="18" width="26" iconColor="#000" />
																	</div>
																	<div class="font-small-xs pad-xs-lft-10 pad-sm-lft-10 font-small-sm font-small-md">
																		<div class="bold inline-xs inline-sm"><spring:theme code="text.cart.branch.pickup"/></div>
																		<div class="pickUpAvail inline-xs inline-sm"><spring:theme code="text.cart.availableat.store" arguments="${sessionStore.name},${sessionStore.storeId}"/></div>
																		<div class="pickUpNotAvail inline-xs inline-sm hidden"><spring:theme code="text.cart.unavailable.for.order"/></div>
																	</div>
																</div>
															</div>
															<div class="col-md-4 col-xs-12 cart-top-fulfillment-method__tile local-delivery fullfillment-delivery  ${(cartData.orderType eq 'DELIVERY')?'fullfilment-select':'' } js-fullfilment" data-fullfillment="DELIVERY">
																<div class="info-tooltip js-info-tootip"  rel="custom-tooltip">
																	<div class="tooltip-content hide">
																		<common:FulfillmentTooltipContent fulfillment="delivery"/>
																	</div>
																	<span class="hidden-xs hidden-sm"><common:info-circle iconColor="#78a22f"/></span>
																	<span class="hidden-md hidden-lg pikuponly">
																			<c:choose>
																			<c:when test="${cartDeliveryDisabled eq 'true'}">
																			<common:info-circle iconColor="#ED8606"/>
																			</c:when>
																			<c:otherwise>
																			<common:info-circle iconColor="#78a22f"/>
																			</c:otherwise>
																			</c:choose>
																	</span>    
																</div>
																<div class="flex-center-xs flex-center-sm pad-xs-rgt-20">												
																	<div class="hidden-xs">
																		<common:deliveryIcon height="24" width="45" iconColor="#000" />
																	</div>
																	<div class="hidden-md hidden-lg hidden-sm">
																		<common:deliveryIcon height="18" width="26" iconColor="#000" />
																	</div>
																	<div class="font-small-xs pad-xs-lft-10 pad-sm-lft-10 font-small-sm font-small-md">
																		<div class="bold inline-xs inline-sm"><spring:theme code="variant.text.fulfillment.delivery"/></div>
																		<div class="deliveryAvail inline-xs inline-sm hidden"><spring:theme code="text.cart.available.for.order"/></div>
																		<div class="deliveryNotAvail inline-xs inline-sm hidden"><spring:theme code="text.cart.unavailable.for.order"/></div>
																	</div>
																</div>
															</div>
															<div class="col-md-4 col-xs-12 cart-top-fulfillment-method__tile parcel-shipping fullfillment-shipping ${(cartData.orderType eq 'PARCEL_SHIPPING')?'fullfilment-select':'' } js-fullfilment" data-fullfillment="PARCEL_SHIPPING">
																<div class="info-tooltip js-info-tootip${(!cartData.isLABranch && !cartData.isTampaBranch && enableShippingFullfilment)? ' hidden' : ''}" rel="custom-tooltip">
																	<div class="tooltip-content hide">
																		<common:FulfillmentTooltipContent fulfillment="parcel"/>
																	</div> 
																	<common:info-circle iconColor="#78a22f"/>
																</div>
																<div class="flex-center-xs flex-center-sm pad-xs-rgt-20">
																	<div class="hidden-xs">
																		<common:parcelIcon height="24" width="32" iconColor="#000" />
																		
																	</div>
																	<div class="hidden-md hidden-lg hidden-sm">
																		<common:parcelIcon height="18" width="26" iconColor="#000" />
																	</div>
																	<div class="font-small-xs pad-xs-lft-10 pad-sm-lft-10 font-small-sm font-small-md">
																			<div class="bold inline-xs inline-sm"><spring:theme code="text.cart.parcel.shipping"/></div>
																			<div class="parcelAvail inline-xs inline-sm hidden"><spring:theme code="text.cart.available.for.order"/></div>
																			<div class="parcelUnavailable inline-xs inline-sm hidden"><spring:theme code="text.cart.unavailable.for.order"/></div>
																	</div>												
																</div>
															</div>
														</c:when>
														<c:otherwise>
															<div class="col-md-4 cart-top-fulfillment-method__tile disabled-fullfillment-method no-padding-rgt-md">
																<div class="info-tooltip js-info-tootip"  rel="custom-tooltip">
																	<div class="tooltip-content hide">
																		<common:FulfillmentTooltipContent fulfillment="pickUp"/>
																	</div> 
																	<common:info-circle iconColor="#78a22f"/>
																</div>
															<div class="flex-xs flex-sm pad-xs-rgt-20">
																<div class="hidden-xs">
																	<common:pickUpIcon height="24" width="30" iconColor="#ccc" />
																</div>
																<div class="hidden-md hidden-lg hidden-sm">
																	<common:pickUpIcon height="18" width="26" iconColor="#ccc" />
																</div>
																<div class="font-small-xs pad-xs-lft-10 pad-sm-lft-10 font-small-sm font-small-md">
																	<div class="bold inline-xs inline-sm"><spring:theme code="text.cart.branch.pickup"/></div>
																	<div class="inline-xs inline-sm"><spring:theme code="text.cart.unavailable.at"/> ${sessionStore.name}</div>
																</div>
															</div>
														</div>
														<div class="col-md-4 cart-top-fulfillment-method__tile disabled-fullfillment-method">
															<div class="info-tooltip js-info-tootip"  rel="custom-tooltip">
																	<div class="tooltip-content hide">
																		<common:FulfillmentTooltipContent fulfillment="delivery"/>
																	</div> 
																			<common:info-circle iconColor="#78a22f"/>
															</div>
															<div class="flex-xs flex-sm pad-xs-rgt-20">
																<div class="hidden-xs">
																	<common:deliveryIcon height="24" width="45" iconColor="#ccc" />
																</div>
																<div class="hidden-md hidden-lg hidden-sm">
																	<common:deliveryIcon height="18" width="26" iconColor="#ccc" />
																</div>
																<div class="font-small-xs pad-xs-lft-10 pad-sm-lft-10 font-small-sm font-small-md">
																	<div class="bold inline-xs inline-sm"><spring:theme code="variant.text.fulfillment.delivery"/></div>
																	<div class="inline-xs inline-sm"><spring:theme code="text.cart.unavailable.for.order"/></div>
																</div>
															</div>
														</div>
														<div class="col-md-4 cart-top-fulfillment-method__tile disabled-fullfillment-method">
															<div class="flex-xs flex-sm pad-xs-rgt-20">
																<div class="hidden-xs">
																	<common:parcelIcon height="24" width="32" iconColor="#ccc" />
																	
																</div>
																<div class="hidden-md hidden-lg hidden-sm">
																	<common:parcelIcon height="18" width="26" iconColor="#cc" />
																</div>
																<div class="font-small-xs pad-xs-lft-10 pad-sm-lft-10 font-small-sm font-small-md">
																	<div class="bold inline-xs inline-sm"><spring:theme code="text.cart.parcel.shipping"/></div>
																	<div class="inline-xs inline-sm"><spring:theme code="variant.text.fulfillment.unavailable"/></div>								
																</div>
															</div>
														</div>
														</c:otherwise>
													</c:choose>
												</div>
											</div>
										</div>
									</div>
								</div>
							</c:if>
						</div>
					</c:if>
					<!-- Split Cart Page Pickup only message -->
					<c:if test="${cartDeliveryDisabled eq 'true'}">
						<div class="black-title flex-center row">
							<div class="col-xs-12 m-t-10 m-b-10">
								<div class="flex-center pickupOnly-error-msg sm-center xs-center">
								<div class="hidden-sm hidden-xs"><common:exclamation-circle width="49" height="49"/></div>
								<div class="pad-lft-10">
								<div class="bold"><spring:theme code="basket.newerror.disabledelivery" /></div>
								<div class="col-xs-12 padding0 f-s-12 font-small-sm font-small-xs"><spring:theme code="basket.newerror2.split.disabledelivery" /></div>
								</div>
								</div>
							</div>
						</div>
					</c:if>
					<div class="black-title flex-center row m-t-5 m-b-10 hardscape-stone-alert-box hidden">
						<div class="col-xs-12">
							<div class="flex-center hardscape-stone-alert hardscape-highlight-background sm-center xs-center">
								<div class="m-t-5"><common:exclamation-circle width="30" height="30" /></div>
								<div class="p-l-15">
									<div class="font-size-14 text-align-left text-default">
										<span class="bold"><spring:theme code="productDetails.hardscape.stone.price" /></span>
										<span>&nbsp;<spring:theme code="productDetails.hardscape.stone.weight.cart" />&nbsp;<spring:theme code="productDetails.hardscape.stone.received.cart" /></span>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- Split Cart Page Delivery and Shipping Threshold Message-->
					<c:if test="${((loggedIn eq true) || (guestUsers eq 'guest')) and (isMixedCartEnabled ne true)}">
						<c:if test="${not empty cartData.entries && enableTopFullfilment ne false}">
							<!-- Delivery error message -->
							<div class="cartHomeOwner-msg row hidden hidden-print">
								<div class="col-xs-12">
									<div class="flex-center font-small-xs cart-delivery-threshold-message marginBottom20">
										<common:deliveryIcon height="18" width="37" iconColor="#78a22f"/>
										<div class="pad-lft-10"><spring:theme code="cart.delivery.enable.condition.message" arguments="${(subtotalLimit) - (deliveryEligibleTotal)}"/></div>
									</div>
								</div>
							</div>
							<!-- Shipping Threshold message -->
							 <input type="hidden" name="differenceAmount" value="${cartData.shippingThresholdCheckData.differenceAmountShipping}"/>
							 <input type="hidden" name="shippingAmount" value="${cartData.shippingThresholdCheckData.selectedItemTotalShipping}"/>
							
								<c:if test="${(cartData.freeShippingThreshold > cartData.shippingThresholdCheckData.selectedItemTotalShipping) && (cartData.isTampaBranch || cartData.isLABranch || cartData.isShippingFeeBranch)}">
									<div class="row split-nonshipping-threshold-free hidden hidden-print">
										<div class="col-xs-12">
											<div class="flex-center font-small-xs cart-delivery-threshold-message marginBottom20">
												<common:freeShippingIcon height="18" width="37" />
												<div class="pad-lft-10"><spring:theme code="cart.free.shipping.condition.message" arguments="${cartData.shippingThresholdCheckData.differenceAmountShipping - 0}"/></div>
											</div>
										</div>
									</div>
								</c:if>
								<c:if test="${(cartData.freeShippingThreshold > cartData.subTotal.value) && (cartData.isTampaBranch || cartData.isLABranch || cartData.isShippingFeeBranch)}">
									<div class="row split-shipping-threshold-free hidden hidden-print">
										<div class="col-xs-12">
											<div class="flex-center font-small-xs cart-delivery-threshold-message marginBottom20">
												<common:freeShippingIcon height="18" width="37" />
												<div class="pad-lft-10"><spring:theme code="cart.free.shipping.condition.message" arguments="${(cartData.freeShippingThreshold) - (cartData.subTotal.value)}"/></div>
											</div>
										</div>
									</div>
								</c:if>
						</c:if>
					</c:if>
					<!--Split Error Messages -->
					<cart:cartValidation />
					<cart:cartPickupValidation />
					<!-- Pricing for one or more products is currently unavailable online -->
					<div class="gc-error-msg marginTop20  flex-center row hidden" id="disableCheckoutMessage">
						<div class="col-xs-12">
							<div class="flex-center gc-cart-global-error-msg">
								<span><common:exclamation-triangle iconColor="#bc0000"/></span>
								<c:set var="count" value="0" />
								<c:forEach var="entry" items="${cartData.entries}">
									<c:if test="${entry.product.hideCSP && count eq '0'}">
										<c:set var="count" value="1" />
										<c:set var="showhideCSPErrormsg" value="true" />
									</c:if>
								</c:forEach>
								<c:if test="${showhideCSPErrormsg eq 'true'}">
									<span class="pad-lft-10"><spring:theme code="cart.product.hideCSP.message" /></span>
								</c:if>
								<c:if test="${showhideCSPErrormsg ne 'true'}">
									<span class="pad-lft-10"><spring:theme code="cart.checkout.disable.message" /></span>
								</c:if>
							</div>
						</div>
					</div>
					<!-- Pricing for one or more products is currently unavailable online ends-->
					<div class="pricing-changed-error-message">
						<div id="pricechangemessage" class="gc-error-msg marginTop20 hidden  flex-center row">
							<div class="col-xs-12 edit-user-alert"> 
								<div class="flex-center gc-cart-global-error-msg">
									<span><common:exclamation-triangle iconColor="#bc0000"/></span>
									<span class="pad-lft-10"><spring:theme code="checkout.orderreview.pricechanges"/></span>
								</div>
							</div>
						</div>
					</div>
					<!-- Split shipping error message -->
					<div class="col-xs-12 p-l-0 p-r-0 m-b-20 national-shipping-nonship-element hidden-print hidden">
						<div class="font-size-14 text-default font-small-xs padding-20 flex-center cart-alert-message">
							<common:exclamation-circle width="30" height="30"/>
							<span class="pad-lft-10"><span class="block bold"><spring:theme code="text.cart.problem.shipping.heading" /></span><spring:theme code="text.cart.problem.shipping.msg" /></span>
						</div>
					</div>
					<!-- Split There is a problem error message -->
					<c:if test="${(enableShippingMsg eq true) or (globalErrorMsgForHidelist eq true) or (nontransferable eq true) or (isProductSellable eq false) or (isNationalShippingInCart) or (isQtyErrorMsg)}">
						<div class="gc-error-msg marginTop20 js-gc-problem-error flex-center row hidden-print  ${enableShippingMsg ? 'disable-parcel-checkout' : ''} ${nontransferable ? '' :(isProductSellable eq false ? '': isNationalShippingInCart ? '' : ' hidden')}" >
							<div class="col-xs-12">
								<div class="flex-center gc-cart-global-error-msg">
									<span><common:exclamation-triangle iconColor="#bc0000"/></span>
									<span class="pad-lft-10"><spring:theme code="text.cart.problem.msg"/></span>
								</div>
							</div>
						</div>
					</c:if>
					<cms:pageSlot position="TopContent" var="feature">
						<cms:component component="${feature}" element="div" class="yComponentWrapper"  />
					</cms:pageSlot>
					<!-- Split/Mixed Cart Page Shippingonly options start -->
					<c:if test="${not empty cartData.entries &&((loggedIn eq true) || (guestUsers eq 'guest'))}">
						<div class="col-md-12 split-cart-shipping cart-top-fulfillment-method hidden print-hidden">
							<div class="col-md-5 col-xs-12 cart-top-fulfillment-method__heading">
								<span class="f-s-26 font-Geogrotesque text-default"><spring:theme code="text.split.cart.fulfilment.title.one" /></span>
								<p class="f-s-16 font-Arial m-b-0"><spring:theme code="text.split.cart.fulfilment.title.two" /></p>
							</div>
							<div class="col-md-7">
								<div class="row flex-md justify-flex-end p-r-15 p-r-0-xs">
									<div class="col-md-4 col-xs-12 cart-top-fulfillment-method__tile parcel-shipping-split fullfillment-shipping-split  fullfilment-select" data-fullfillment="PARCEL_SHIPPING">
										<div class="flex-center-xs flex-center-sm pad-xs-rgt-20">
											<div class="hidden-xs">
												<common:parcelIcon height="24" width="32" iconColor="#000" />
												
											</div>
											<div class="hidden-md hidden-lg hidden-sm">
												<common:parcelIcon height="18" width="26" iconColor="#000" />
											</div>
											<div class="font-small-xs pad-xs-lft-10 pad-sm-lft-10 font-small-sm font-small-md">
												<div class="bold inline-xs inline-sm"><spring:theme code="text.cart.parcel.shipping"/></div>
												<div class="parcelAvail-split inline-xs inline-sm "><spring:theme code="text.cart.available.for.order"/></div>	
											</div>												
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-12 split-cart-shipping-container hidden"></div>
					</c:if>
					<!-- Split/Mixed Cart Page Shippingonly options end -->
					<!-- Split footer print message -->
					<div class="hidden col-md-12 show-print print-footer text-uppercase text-right" >
						<div class="footer-item col-xs-3 cart-total-loader-show${showingPriceLoader eq true? ' hidden' : ''}">
							<span><spring:theme code="order.form.subtotal" /></span>
							<span class="js-cart-subtotal">${cartData.subTotal.formattedValue}</span>
						</div>
						<div class="footer-item col-xs-3${showingPriceLoader eq true? ' hidden' : ''}">
							<span><spring:theme code="basket.page.totals.netTax" /></span>
							<span>${cartData.totalTax.formattedValue}</span>
						</div>
						<c:set var="totalWithTax" value="${cartData.subTotal.value + cartData.totalTax.value}" />
						<div class="footer-item col-xs-3 cart-total-loader-show${showingPriceLoader eq true? ' hidden' : ''}">
							<span><spring:theme code="text.quickOrder.page.total" /></span>
							<span class="js-cart-total-with-tax">
								<span class="text-default-print">$</span><span>${totalWithTax}</span>
							</span>
						</div>
					</div>
					<div class="hidden col-md-12 show-print footer-thanks">
						<snap><spring:theme code="cart.print.text4" /></snap>
						<snap><spring:theme code="text.contactUs.storeHead" /></snap>
					</div>
				</div>
				<!-- Split Order Summary Section -->
				<div class="col-md-3 col-sm-12 hidden-print">
					<c:if test="${not empty cartData.entries}">
						<cms:pageSlot position="CenterRightContentSlot" var="feature">
							<cms:component component="${feature}" element="div"
										class="yComponentWrapper" />
						</cms:pageSlot>
					</c:if>
				</div>
				<!-- Split Recommendation Section -->
				<c:if test="${algonomyRecommendationEnabled && not empty cartData.entries}">
					<div class="featured-content margin-top-20 product-recommend-margin hidden-print" id="RecommendedProductCartSlot">
					</div>
				</c:if>
			</div>
		</c:if>

			<c:if test="${not empty cartData.entries}">
				<cms:pageSlot position="CenterLeftContentSlot" var="feature">
					<cms:component component="${feature}" element="div"
						class="yComponentWrapper " />
				</cms:pageSlot>
			</c:if>



			<div class="cl"></div>

			<c:if test="${empty cartData.entries}">
				<cms:pageSlot position="EmptyCartMiddleContent" var="feature">
					<div class="margin20 text-left empty-cart col-md-12 hidden-print">
						<div class="emptyCartContent row">
							<cms:component component="${feature}" element="div"
								class="yComponentWrapper content__empty" />
						</div>
					</div>
				</cms:pageSlot>
				<div class="row">
					<br /> <div class="col-md-2 col-sm-3 col-xs-12 hidden-print"><a href="<c:url value="/"/>"
						class="btn btn-block btn-primary"><spring:theme
							code="cartPage.shop.now" /></a></div>
							<c:if test="${!isAnonymous && (guestUsers ne 'guest') && !mobileFlag}"><div class="col-md-2 col-sm-3 col-xs-12 hidden-print hidden-xs"><button onclick="ACC.cart.csvCartOpenAndResponseHandle('openCart');ACC.adobelinktracking.cartCsvUpload('Upload CSV','','checkout: cart','checkout: cart: upload csv popup', 'checkout', 'checkout: cart: upload csv popup');"
						class="upload-csv btn btn-block btn-primary"><span  style="padding-right: 5px;"><common:globalIcon iconName="upload" iconFill="none" iconColor="#ffffff" width="16" height="15" viewBox="0 0 18 18"/></span><spring:theme
							code="cartPage.shop.uploadcsv" /></button></div></c:if>
				</div>
			</c:if>

			<div class="cl"></div>
			<br />
			<c:if test="${empty cartData.entries}">
				<div class="row hidden-print">
					<div class="categoryContent">
						<h2 class="categories-header emptyCartPage headline3 col-md-12">
							<spring:theme code="text.cart.categorycontentheading" />
						</h2>
						<div class="col-md-12 displayflex">
							<cms:pageSlot position="CategoryContent" var="feature">
								<cms:component component="${feature}" element="div"
									class="cartPopularCategories yComponentWrapper content__empty" />
							</cms:pageSlot>
						</div>
					</div>
				</div>
			</c:if>
			<div class="cl"></div>
		</c:otherwise>
	</c:choose>

	<div class="remove-all-items-modal hidden">
	  <div class="row">
	  	<div class="col-sm-12 padding-top-20 font-Geogrotesque-bold label text-center">
	  		<h3>
	  			<spring:theme code="text.cart.removeAllPopup.text1a"/>
				<span class="extra-bold"><spring:theme code="text.cart.removeAllPopup.text1b"/></span> 
	  			<span class="hidden-md hidden-lg"><br></span>
	  			<spring:theme code="text.cart.removeAllPopup.text1c"/>
	  		</h3>
	  	</div>
	  	<div class="col-sm-12 padding-20 bold text-center text-muted">
	  		<spring:theme code="text.cart.removeAllPopup.text2"/>
	  	</div>
	  	<div class="col-sm-6 confirm-btn">
	  		<spring:url value="/cart/removeAllEntries" var="cartRemoveAllEntries" htmlEscape="true"/>
	  		<form:form action="${cartRemoveAllEntries}" method="POST">
	  			<button type="submit" class="btn btn-primary btn-lg btn-block removeCart" >
		      		<spring:theme code="text.cart.removeAllPopup.confirm"/>
		      	</button>
	  		</form:form>
	    </div>
	    <div class="col-sm-6 remove-all-cart-cancel-btn">
	      	<button onClick="ACC.colorbox.close()" class="btn btn-default btn-lg btn-block" >
	      		<spring:theme code="text.cart.removeAllPopup.cancel"/>
	      	</button>
	    </div>
	  </div>
	  <c:if test="${!isAnonymous && (guestUsers ne 'guest') && !mobileFlag}">
	  	<common:cartListImportPopup/>
	  </c:if>
	 </div>
</template:page>
