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
						<h1
							class="order-confirmation-page-title order-review-title-wrapper">
							<spring:theme code="orderSummaryPage.parcel.shipping.info" />
						</h1>
					</div>
					<div class="col-xs-3 text-right padding0">
						<a class="add-edit-decoration"
							href="<c:url value="/checkout/multi/siteOne-checkout"/>"><spring:theme
								code="orderSummaryPage.edit" /></a>
					</div>
				</div>
			</div>
			<div class="col-xs-12">
				<div class="col-xs-12 add-border-radius add-border-grey">
					
					<div class="col-md-3 col-sm-12">
						<div class="secondary-title first-child-title">
								<span class="bold-text"><spring:theme
										code="orderSummaryPage.shipping.contact" /></span>
						</div>						
						<div class="margin-top-20">
							<c:choose>
								<c:when test="${guestUsers eq 'guest'}">
									<div>${cartData.b2bCustomerData.firstName}&nbsp;${cartData.b2bCustomerData.lastName}</div>
									<c:choose>
										<c:when
											test="${not empty cartData.guestShippingContactPerson.contactNumber}">
											<a class="tel-phone"
												href="tel:${cartData.guestShippingContactPerson.contactNumber}">${cartData.guestShippingContactPerson.contactNumber}</a>
											<br>
										</c:when>
										<c:otherwise>
											<spring:theme code="orderSummaryPage.na" />
											<br>
										</c:otherwise>
									</c:choose>
					  			${cartData.guestShippingContactPerson.email} <br>
								</c:when>
								<c:otherwise>
						    		${cartData.shippingContactPerson.name}<br>
									<c:choose>
										<c:when
											test="${not empty cartData.shippingContactPerson.contactNumber}">
											<a class="tel-phone"
												href="tel:${cartData.shippingContactPerson.contactNumber}">${cartData.shippingContactPerson.contactNumber}</a>
											<br>
										</c:when>
										<c:otherwise>
											<spring:theme code="orderSummaryPage.na" />
											<br>
										</c:otherwise>
									</c:choose>
						  		${cartData.shippingContactPerson.email}<br>
								</c:otherwise>
							</c:choose>
						</div>

					</div>

					<div class="col-md-3 col-sm-12">
						<div class="secondary-title">
							<span class="bold-text"><spring:theme
									code="orderSummaryPage.parcel.shipping.to.contact" />:</span>
						</div>
						<div class="margin-top-20">
							${cartData.shippingAddress.line1},<br>
							<c:if test="${not empty cartData.shippingAddress.line2}"> 
							${cartData.shippingAddress.line2},<br>
							</c:if>
							${cartData.shippingAddress.town},&nbsp;
							${cartData.shippingAddress.region.isocodeShort}&nbsp;
							${cartData.shippingAddress.postalCode}<br>
						</div>
					</div>
					<div class="col-md-6">
							<div class="secondary-title">
								<spring:theme code="orderSummaryPage.shipping.shippingInstruction" />
							</div>
							<p class="">
								<c:choose>
									<c:when test="${not empty cartData.shippingInstruction}">
												${cartData.shippingInstruction}
											</c:when>
									<c:otherwise>
										<span class="marginTop10"><spring:theme
												code="orderSummaryPage.na" /></span>
									</c:otherwise>
								</c:choose>
							</p>

						</div>

					<div class="col-md-12 marginTop40"><span class="pick-up-mesg-wrapper">
						<spring:theme	code="orderSummaryPage.shipping.siteonemsg"/>
					</span>
					</div>
						<c:forEach items="${cartData.deliveryModeAndBranchOrderGroups}" var="groupData" varStatus="status">
		       				<c:if test="${groupData.deliveryMode.code eq 'free-standard-shipping'}">
								<div class="col-xs-12">
									<multi-checkout:orderReviewCartEntriesMixedCart cartData="${cartData}" groupData="${groupData}" />		
								</div>
							</c:if>
						</c:forEach>
					</div>
				</div>
		</div>