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

	<div class="row deliveryInformations">
		<div class="col-xs-12">
			<div class="title-bar order-confirmation-page-bottom">
				<div class="col-xs-9 order-summary-title padding0">
					<div
						class="order-confirmation-page-title order-review-title-wrapper">
						<spring:theme code="orderSummaryPage.delivery.information.text" />
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
					<div class="col-md-3 no-padding-xs">
						<div class="secondary-title first-child-title">
							<span class="bold-text"><spring:theme
									code="orderSummaryPage.delivery.contact" /></span>
						</div>
						<div>
							<c:choose>
								<c:when test="${guestUsers eq 'guest'}">

									<div>${cartData.b2bCustomerData.firstName}&nbsp;${cartData.b2bCustomerData.lastName}</div>
									<c:choose>
										<c:when
											test="${not empty cartData.guestDeliveryContactPerson.contactNumber}">
											<a class="tel-phone"
												href="tel:${cartData.guestDeliveryContactPerson.contactNumber}">${cartData.guestDeliveryContactPerson.contactNumber}</a>
											<br>
										</c:when>
										<c:otherwise>
											<spring:theme code="orderSummaryPage.na" />
											<br>
										</c:otherwise>
									</c:choose>
				  						${cartData.guestDeliveryContactPerson.email} <br>
								</c:when>
								<c:otherwise>
				    			${cartData.deliveryContactPerson.name}<br>
									<c:choose>
										<c:when
											test="${not empty cartData.deliveryContactPerson.contactNumber}">
											<a class="tel-phone"
												href="tel:${cartData.deliveryContactPerson.contactNumber}">${cartData.deliveryContactPerson.contactNumber}</a>
											<br>
										</c:when>
										<c:otherwise>
											<spring:theme code="orderSummaryPage.na" />
											<br>
										</c:otherwise>
									</c:choose>
				  					${cartData.deliveryContactPerson.email}<br>
									<%-- ${cartData.contactPerson.email}<br> --%>
								</c:otherwise>
							</c:choose>
						</div>

					</div>
					<div class="col-md-3 no-padding-xs">
						<div class="secondary-title">
							<span class="bold-text">
								<spring:theme code="orderSummaryPage.delivery.to" />
							</span>
						</div>
					<div class="">
						${cartData.deliveryAddress.line1},<br>
						<c:if test="${not empty cartData.deliveryAddress.line2}"> 
						${cartData.deliveryAddress.line2},<br>
						</c:if>
						${cartData.deliveryAddress.town},&nbsp;
						${cartData.deliveryAddress.region.isocodeShort}&nbsp;
						${cartData.deliveryAddress.postalCode}<br>
					</div>
					</div>
					<div class="col-md-2  no-padding-xs">
						<c:if test="${guestUsers ne 'guest'}">
					
						<div class="secondary-title">
							<span class="bold-text"><spring:theme
									code="orderSummaryPage.acc.num" /></span>
						</div>
						<div class="order-summary-account-number">
							${cartData.orderingAccount.displayId}<br>
						</div>
						
						</c:if>
					</div>
					<div class="col-md-4 no-padding-xs">
						<div class="secondary-title">
							<spring:theme code="orderunconsignedentries.deliveryInstruction" />
						</div>
						<p class="marginTop10">
							<c:choose>
								<c:when test="${not empty cartData.deliveryInstruction}">
									${cartData.deliveryInstruction}
								</c:when>
								<c:otherwise>
									<span class="marginTop10"> <spring:theme
											code="orderSummaryPage.na" />
									</span>
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
						<spring:theme code="orderSummaryPage.delivery.info" />
					</h1>
				</div>
				<div class="col-xs-3 text-right padding0">
					<a class="add-edit-decoration"
						href="<c:url value="/checkout/multi/siteOne-checkout"/>"><spring:theme
							code="orderSummaryPage.edit" /></a>
				</div>
			</div>
		</div>
		<div class="col-xs-12 no-padding-xs">
		<c:forEach items="${cartData.deliveryModeAndBranchOrderGroups}" var="groupData" varStatus="status">
		       		<c:if test="${groupData.deliveryMode.code eq 'standard-net'}">
			<div class="col-xs-12 add-border-radius add-border-grey marginBottom20">
				<div class="order-confirmation-page-title order-review-title-wrapper pos-group-title">
							
							<spring:theme code="orderSummaryPage.delivery.from"/> 
							<span class="show-xs bold-text">
								${groupData.pointOfService.name} 
							</span>
				</div>			
				<div class="marginTop20 font-small-xs">
					<span class="black-title bold"><spring:theme
								code="orderSummaryPage.delivery.siteonemsg" arguments="${groupData.pointOfService.name}"/></span>
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
