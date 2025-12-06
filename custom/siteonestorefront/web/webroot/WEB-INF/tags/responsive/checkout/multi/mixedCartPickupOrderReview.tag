<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="multi-checkout"
	tagdir="/WEB-INF/tags/responsive/checkout/multi"%>
<%@ taglib prefix="multi-checkouts"
	tagdir="/WEB-INF/tags/responsive/checkout/multi"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order"%>
<%@ taglib prefix="multiCheckout"
	tagdir="/WEB-INF/tags/responsive/checkout/multi"%>
<%@ attribute name="groupData" required="false" type="de.hybris.platform.commercefacades.order.data.OrderEntryGroupData" %>

		<div class="row PickupInformation">
			<div class="col-xs-12">
				<div class="title-bar order-confirmation-page-bottom">
					<div class="col-xs-9 order-summary-title padding0">
						<div
							class="order-confirmation-page-title order-review-title-wrapper">
							<spring:theme code="orderSummaryPage.pickup.information.text" />
						</div>
					</div>
					<div class="col-xs-3 text-right padding0">
						<a class="add-edit-color add-edit-decoration"
							href="<c:url value="/checkout/multi/siteOne-checkout"/>"><spring:theme
								code="orderSummaryPage.edit" /></a>
					</div>
				</div>
				<div class="col-xs-12 no-padding-md">
					<div class="row">
						<div class="col-md-4">

							<div class="secondary-title first-child-title">
								<span class="bold-text"><spring:theme
										code="orderSummaryPage.pickup.contact" /></span>
							</div>
							<c:choose>
								<c:when test="${guestUsers eq 'guest'}">
									<div class="add-secondaryTitle">
										<div>${cartData.b2bCustomerData.firstName}&nbsp;${cartData.b2bCustomerData.lastName}</div>
										<c:choose>
											<c:when
												test="${not empty cartData.guestContactPerson.contactNumber}">
												<a class="tel-phone"
													href="tel:${cartData.guestContactPerson.contactNumber}">${cartData.guestContactPerson.contactNumber}</a>
												<br>
											</c:when>
											<c:otherwise>
												<spring:theme code="orderSummaryPage.na" />
												<br>
											</c:otherwise>
										</c:choose>
										${cartData.guestContactPerson.email} <br>
										<%-- ${cartData.contactPerson.email}<br> --%>
									</div>
								</c:when>
								<c:otherwise>
									<div class="add-secondaryTitle">
										${cartData.contactPerson.name}<br>
										<c:choose>
											<c:when
												test="${not empty cartData.contactPerson.contactNumber}">
												<a class="tel-phone"
													href="tel:${cartData.contactPerson.contactNumber}">${cartData.contactPerson.contactNumber}</a>
												<br>
											</c:when>
											<c:otherwise>
												<spring:theme code="orderSummaryPage.na" />
												<br>
											</c:otherwise>
										</c:choose>
										${cartData.contactPerson.email}<br>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="col-md-8 no-padding-xs">
							<div class="secondary-title">
								<spring:theme code="orderunconsignedentries.pickUpInstruction" />
							</div>
							<p class="">
								<c:choose>
									<c:when test="${not empty cartData.pickupInstruction}">
												${cartData.pickupInstruction}
											</c:when>
									<c:otherwise>
										<span class="marginTop10"><spring:theme
												code="orderSummaryPage.na" /></span>
									</c:otherwise>
								</c:choose>
							</p>

						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-12 marginTop40">
				<div class="title-bar order-confirmation-page-bottom">
					<div class="col-xs-9 order-summary-title padding0">
						<h1
							class="order-confirmation-page-title order-review-title-wrapper">
							<spring:theme code="orderSummaryPage.pickup.info" />
						</h1>
					</div>
					<div class="col-xs-3 text-right padding0">
						<a class="add-edit-color add-edit-decoration"
							href="<c:url value="/checkout/multi/siteOne-checkout"/>"><spring:theme
								code="orderSummaryPage.edit" /></a>
					</div>
				</div>
			</div>
			<div class="col-xs-12 no-padding-xs">
			<c:set var="counter" value="0"/>
			<c:forEach items="${cartData.deliveryModeAndBranchOrderGroups}" var="groupData" varStatus="status">	
 				<c:if test="${groupData.deliveryMode.code eq 'pickup'}">
			<c:set var="counter" value="${counter+1}"/>
				<div class="col-xs-12 add-border-radius add-border-grey marginBottom20">					
						<div class="order-confirmation-page-title order-review-title-wrapper pos-group-title">
							<spring:theme code="orderSummaryPage.pickup.from"/> #${counter}: 
							<span class="show-xs bold-text">
								${groupData.pointOfService.name}
							</span>
						</div>
						<div class="marginTop10 font-14-xs">
							
							${groupData.pointOfService.address.line1}<span class="hidden-xs hidden-sm">&nbsp;|&nbsp;</span>
							<span class="show-xs">${groupData.pointOfService.address.town},</span>
							<span class="show-xs">${groupData.pointOfService.address.region.isocodeShort}&nbsp;</span>
							<span class="show-xs">${groupData.pointOfService.address.postalCode}</span><span class="hidden-xs hidden-sm">&nbsp;|&nbsp;</span>
							<a
								class="tel-phone"
								href="tel:${entry.pointOfService.address.phone}">${entry.pointOfService.address.phone}</a>
						</div>					
					
					<div class="marginTop20 font-small-xs">
						<span class="black-title bold"><spring:theme
								code="orderSummaryPage.pickup.siteonemsg" arguments="${groupData.pointOfService.name}"/>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<multi-checkout:orderReviewCartEntriesMixedCart cartData="${cartData}" groupData="${groupData}" />	
						</div>
					</div>					
				</div>
				
			</c:if>
			</c:forEach>
			</div>

		</div>