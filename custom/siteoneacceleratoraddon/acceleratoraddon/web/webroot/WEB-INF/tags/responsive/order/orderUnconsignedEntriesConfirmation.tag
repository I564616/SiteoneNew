<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="order" required="true" type="de.hybris.platform.commercefacades.order.data.OrderData" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<spring:htmlEscape defaultHtmlEscape="true" />
<%-- Siteoneacceleratoraddon tag --%>
 <div class="cl"></div>
<br/>
<div class="row">
	<div class="col-md-2 marginBottom20">
		<div class="row">
			<div class="col-md-12 bold"><spring:theme code="orde.service.text" /></div>
			<div class="col-md-12">  ${order.pointOfService.address.town}, ${order.pointOfService.address.region.isocodeShort}&nbsp; #${order.pointOfService.storeId}
				<div class="cl"></div>
				<a class="tel-phone" href="tel:${order.pointOfService.address.phone}">${order.pointOfService.address.phone}</a>
			</div>
		</div>
	</div>


	<div class="col-md-10">
		<div class="row">
			<div  class="col-xs-6 col-sm-4 col-md-2 bold"><spring:theme code="text.account.orderHistory.orderNumber" /></div>
			<div  class="col-xs-6 col-sm-8 col-md-10 orderId">${order.code}</div>
		</div>
		<div class="row">
			<div  class="col-xs-6 col-sm-4 col-md-2 bold"><spring:theme code="text.account.orderHistory.orderDate" /></div>
			<div  class="col-xs-6 col-sm-8 col-md-10"><fmt:formatDate var="fmtDate" value="${order.created}" pattern="MM/dd/YYYY hh:mm:ss a z"/> ${fmtDate}</div>
		</div>
		<div class="row">
			<div  class="col-xs-6 col-sm-4 col-md-2 bold"><spring:theme code="orderUnconsignedEntries.poNumber" /></div>
			<div  class="col-xs-6 col-sm-8 col-md-10"><c:choose>
				<c:when test="${not empty order.purchaseOrderNumber}">
					${order.purchaseOrderNumber}<br>
				</c:when>
				<c:otherwise>NA</c:otherwise>
			</c:choose></div>
		</div>
	</div>
</div>
<c:set var="confirmationEmail" value=""/>
<c:set var="showRegistration" value="false"/>
<c:set var="isGuestUser" value="false"/>
<c:choose>
	<c:when test="${order.guestCustomer eq true }">
		<c:set var="confirmationEmail" value="${order.guestContactPerson.email}"/>
		<c:set var="showRegistration" value="true"/>
		<c:set var="isGuestUser" value="true"/>
	</c:when>
	<c:otherwise>
		<c:set var="confirmationEmail" value="${user.displayUid}"/>
	</c:otherwise>
</c:choose>

<div class="whatnext-section marginTop35">
	<!--orderUnconsignedEntriesConfirmation.tag  whatnext-section -->
	<div class="col-md-2 col-xs-12"><h2 class="headline3 text-center"><spring:theme code="what.next.text"/></h2></div>
	<div class="col-md-10 col-xs-12 content-sec">
		<div class="col-md-12">
			<div class="gray-bg">
				<div class="svg-icons"><common:contactemailIcon height="24" width="30" iconColor="#999999"/> </div>
				<span class="msg-text bold">
		 			<c:choose>
						<c:when test="${order.b2bCustomerData.needsOrderApproval eq true}">
							<spring:theme code="confirm.mail.text.needsOrderApproval.before" />&nbsp;<a href="mailto:${confirmationEmail}">${confirmationEmail}</a>&nbsp;<span class="hidden-lg hidden-md hidden-sm"></br></span><spring:theme code="confirm.mail.text.needsOrderApproval.after" />
						</c:when>
						<c:otherwise>
							<spring:theme code="confirm.mail.text"/>&nbsp;${confirmationEmail}
						</c:otherwise>
					</c:choose>
				</span>
			</div>
		</div>
		<div class="col-md-12"><div class="gray-bg">
			
			<c:choose>
				<c:when test="${order.isNationalShipping eq true}">
					<div class="svg-icons"><common:shippingIcon iconColor="#999999" width="30" height="26.25" /> </div> <span class="msg-text2 bold"><spring:theme code="orderconfirmation.msg10"/></span>
				</c:when>
				<c:otherwise>
					<div class="svg-icons"><common:trackingIcon iconColor="#999999"/></div> <span class="msg-text2 bold"><spring:theme code="orderconfirmation.msg1"/></span>
				</c:otherwise>
			</c:choose>
		</div></div>
		
		<c:set var="isRegulatoryLicenseCheckFailed" value="false"/>
		
		<c:forEach items="${order.unconsignedEntries}" var="entry" varStatus="loop">
			<c:if test="${entry.product.isRegulatoryLicenseCheckFailed eq false}">
				<c:set var="isRegulatoryLicenseCheckFailed" value="true"/>
			</c:if>
		</c:forEach>
		
		<c:if test="${isRegulatoryLicenseCheckFailed eq true}">
		<div class="col-md-12">
			<div class="gray-bg">
				<div class="svg-icons"><common:badgeIcon iconColor="#999999"/></div> <span class="msg-text2 bold"><spring:theme code="valid.license.msg"/></span>
			</div>
		</div>
		
		</c:if>
		
		
		
		<c:if test="${showRegistration eq 'true'}">
			<div class="col-md-12">
				<div class="gray-bg">
					<div class="col-md-3 no-padding-xs"><div class="padding10">
						<h3 class="headline3"><spring:theme code="orderconfirmation.msg2"/></h3>
						<p><spring:theme code="orderconfirmation.msg4"/></p>
					</div>
					</div>
					<div class="col-md-9 no-padding-xs">
						<div class="col-md-4 col-xs-12 no-padding-xs">
							<div class="white-bg">
								<div class="white-bg-icon"><common:lockIcon iconColor="#78a22f"/></div>
								<p><spring:theme code="orderconfirmation.msg5"/></p>
							</div>
						</div>
						<div class="col-md-4 col-xs-12 no-padding-xs">
							<div class="white-bg">
								<div class="white-bg-icon"><common:listIcon iconColor="#78a22f"/></div>
								<p><spring:theme code="orderconfirmation.msg6"/></p>
							</div>
						</div>
						<div class="col-md-4 col-xs-12 no-padding-xs">
							<div class="white-bg">
								<div class="white-bg-icon"><common:dollarcircleIcon iconColor="#78a22f"/></div>
								<p><spring:theme code="orderconfirmation.msg7"/></p>
							</div>
						</div>
						<div class="cl"></div>
						<div class="margin-top-20">
							<div class="col-md-12 no-padding-xs">
								<a href="<c:url value="/request-account/form"/>" class="btn btn-primary btn-block" onclick=""><spring:theme code="orderconfirmation.finish.registration"/></a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:if>
	</div>
	<div class="cl"></div>
</div>


<c:if test="${order.orderType eq 'PICKUP' || order.orderType eq 'FUTURE_PICKUP' }">
<div class="row marginTop35">
<div class="title-bar no-background-color col-md-12">
				<div class="col-xs-12 padding0 order-confirmation-page-title">
					<spring:theme code="orderunconsignedentries.branchPickUpDetails" />
				</div>
			</div>
			</div>
</c:if>
<c:if test="${order.orderType eq 'DELIVERY' || order.orderType eq 'STORE_DELIVERY' || order.orderType eq 'DIRECT_SHIP' }">
<div class="row marginTop35">
<div class="title-bar no-background-color col-md-12">
				<div class="col-xs-12 padding0 order-confirmation-page-title">
					<spring:theme code="orderUnconsignedentries.localDeliveryDetails" />
				</div>
			</div>
			</div>
</c:if>
<c:if test="${order.orderType eq 'PARCEL_SHIPPING'}">
<div class="row marginTop35">
 <div class="title-bar no-background-color col-md-12">
			   <div class="col-xs-12 padding0 order-confirmation-page-title">
				   <spring:theme code="orderunconsignedentries.parcelShipping" />
			   </div>
	   </div>
	   </div>
	   </c:if>
	   
	<div class="cl"></div>   
	   
<div class="border-grey white-bg">
<c:if test="${order.orderType eq 'PICKUP' || order.orderType eq 'FUTURE_PICKUP' }">
	<div class="PickupInformation">
		<!--orderUnconsignedEntriesConfirmation.tag  -->
		<div class="col-xs-12">
			
			<div class="col-xs-12">
				<div class="col-md-3 col-sm-12 padding-LeftZero">
					<div class="secondary-title">
						<b><spring:theme code="orderUnconsignedEntries.pickupLocation" /></b>
					</div>
					<div class="pickup-location-details">
						<p> <span class="bold">${order.pointOfService.name}</span></p>
						<p> <span class="bold">${order.pointOfService.title}</span></p>
						<p><span>${order.pointOfService.address.line1}</span></p>
						<p> <span>${order.pointOfService.address.town},${order.pointOfService.address.region.isocodeShort}&nbsp;${order.pointOfService.address.postalCode}</span></p>
						<p><span><a class="tel-phone" href="tel:${order.pointOfService.address.phone}">${order.pointOfService.address.phone}</a></span></p>
						<a id="getDirection" href="" data-url="${order.pointOfService.address.line1},${order.pointOfService.address.town},${order.pointOfService.address.region.isocodeShort},${order.pointOfService.address.postalCode}"><spring:theme code="choosePickupDeliveryMethodPage.get.direction" /></a></span>
					</div>
				</div>
				<div class="col-md-3 col-sm-12">
					<div class="secondary-title">
						<b><spring:theme code="orderUnconsignedEntries.pickUpContact" /></b>
					</div>
					<div>
						<c:choose>
							<c:when test="${order.guestCustomer eq true}">
								${order.b2bCustomerData.firstName}&nbsp;${order.b2bCustomerData.lastName}<br>
							   <c:choose>
								   <c:when test="${not empty order.guestContactPerson.contactNumber}">
									   m: <a class="tel-phone" href="tel:${order.guestContactPerson.contactNumber}">${order.guestContactPerson.contactNumber}</a><br>
								   </c:when>
								   <c:otherwise>
									   m: <spring:theme code="orderSummaryPage.na" /><br>
								   </c:otherwise>
							   </c:choose>
							   <a href="mailto:${order.guestContactPerson.email}">${order.guestContactPerson.email}</a> <br>
							</c:when>
							<c:otherwise>
								<c:set var = "contactPersonName" value = "${order.contactPerson.name}"/>
								<c:choose>
									<c:when test="${fn:startsWith(contactPersonName, 'storecontact')}">
										${order.storeUserName}</br>
									</c:when>
									<c:otherwise>
										${order.contactPerson.name}</br>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${not empty order.contactPerson.contactNumber}">
										m: <a class="tel-phone" href="tel:${order.contactPerson.contactNumber}">${order.contactPerson.contactNumber}</a><br>
									</c:when>
									<c:otherwise>
										m:NA<br>
									</c:otherwise>
								</c:choose>
								<c:set var = "contactPersonEmail" value = "${order.contactPerson.email}"/>
								<c:choose>
									<c:when test="${fn:startsWith(contactPersonEmail, 'storecontact')}">
										${order.storeContact}
									</c:when>
									<c:otherwise>
										<a href="mailto:${order.contactPerson.email}">${order.contactPerson.email}</a><br>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
						
					</div>
				</div>
				<div class="col-md-3 col-sm-12 hidden bg-danger">
					<div class="secondary-title">
						<b><spring:theme code="orderUnconsignedEntries.pickUpInfo" />:</b>
					</div>
					<div>
						<p class="message-header bold"><spring:theme code="orderUnconsignedEntries.date" />:</p>
						<fmt:formatDate var="fmtDate" value="${order.requestedDate}" pattern="MM/dd/YYYY hh:mm:ss a"/>
						<input type="hidden" id="requestedDateinUTC"  value="${fmtDate}"/>
						<span id="requestedDateInLocal"><fmt:formatDate value="${order.requestedDate}" pattern="MM/dd/YYYY" /></span>
						<BR>
						<c:choose>
							<c:when test="${order.requestedMeridian eq 'AM'}"><spring:theme code="orderSummaryPage.morning"/></c:when>
							<c:when test ="${order.requestedMeridian eq 'PM'}"><spring:theme code="orderSummaryPage.afternoon"/></c:when>
							<c:otherwise>Any Time</c:otherwise>
						</c:choose>
						<br>
					</div>
				</div>
				<c:if test = "${order.guestCustomer ne true and cmsPage.uid ne 'order'}">
					<div class="col-md-3 col-sm-12">
						<div class="secondary-title">
							<b><spring:theme code="orderUnconsignedEntries.accountNumber" /></b>
						</div>
						<div>
								${order.orderingAccount.displayId}<br>
						</div>

					</div>
				</c:if>
					<div class="col-xs-12 padding0 marginTopBVottom20">
						<c:choose>
							<c:when  test = "${cmsPage.uid eq 'orderConfirmationPage'}">
								<div class="col-xs-12 col-sm-4  padding-LeftZero col-md-3 bold"><spring:theme code="orderunconsignedentries.pickUpInstruction" />:</div>
							</c:when>
							<c:otherwise>
								<div class="col-xs-12 col-sm-4  col-md-3 bold"><spring:theme code="orderSummaryPage.msg.branch" />:</div>
							</c:otherwise>
						</c:choose>
						<p class="col-xs-6 col-sm-8 col-md-9">
							<c:choose>
								<c:when test="${not empty order.specialInstruction}">
									${order.specialInstruction}
								</c:when>
								<c:otherwise>NA</c:otherwise>
							</c:choose>
						</p>
					</div>
					<div class="cl"></div>
				    <div class="row marginTopBVottom30">
						<div class="col-md-12 info-mesg-wrapper"><spring:theme code="orderSummaryPage.pickup.front.message" />&nbsp;${order.pointOfService.name}&nbsp;<spring:theme code="orderSummaryPage.pickup.end.message" /></div>
					</div>

			</div>
		</div>
	</div>
</c:if>
<c:if test="${order.orderType eq 'DELIVERY' || order.orderType eq 'STORE_DELIVERY' || order.orderType eq 'DIRECT_SHIP' }">
	<div class="deliveryInformations">
		<div class="">
		<div class="deliveryInformations margin-top-20">
			<!--orderUnconsignedEntriesConfirmation.tag  -->
			<div class="col-xs-12">
				<div class="col-xs-12">
					<div class="col-md-3 col-sm-12 padding-LeftZero">
						<div class="secondary-title">
							<b><spring:theme code="orderUnconsignedEntries.DeliveryLocation" /></b>
						</div>
						<div class="delivery-location-details">
							<p>
								<span>${order.deliveryAddress.title}</span>
								<span>${order.deliveryAddress.firstName}</span>
								<span>${order.deliveryAddress.lastName}</span>
							</p>
							<p><span>${order.deliveryAddress.line1},</span></p>
							<c:if test="${not empty order.deliveryAddress.line2}">
								<p><span>${order.deliveryAddress.line2},</span></p>
							</c:if>
							<p>
								<span>${order.deliveryAddress.town},</span>
								<span>${order.deliveryAddress.region.isocodeShort}&nbsp;</span>
							</p>
							<p>
								<span>${order.deliveryAddress.postalCode}</span><br>
							</p>
							<c:choose>
								<c:when test="${order.guestCustomer eq true}">
									<c:choose>
									   <c:when test="${not empty order.guestContactPerson.contactNumber}">
										   m:<a class="tel-phone" href="tel:${order.guestContactPerson.contactNumber}">${order.guestContactPerson.contactNumber}</a><br>
									   </c:when>
									   <c:otherwise>
										   m:<spring:theme code="orderSummaryPage.na" /><br>
									   </c:otherwise>
								   </c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${not empty order.contactPerson.contactNumber}">
											<p>
											<span>
												m:<a class="tel-phone" href="tel:${order.contactPerson.contactNumber}">${order.contactPerson.contactNumber}</a>
											</span>
											</p>
										</c:when>
										<c:otherwise>
											m:<spring:theme code="orderSummaryPage.na" /><br>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<div class="col-md-3 col-sm-12">
						<div class="secondary-title">
							<b><spring:theme code="orderUnconsignedEntries.deliveryContact" /></b>
						</div>
						<div>
						 <c:choose>
							<c:when test="${order.guestCustomer eq true}">
								<div>${order.b2bCustomerData.firstName}&nbsp;${order.b2bCustomerData.lastName}</div>
								   <c:choose>
									   <c:when test="${not empty order.guestContactPerson.contactNumber}">
										   <a class="tel-phone" href="tel:${order.guestContactPerson.contactNumber}">${order.guestContactPerson.contactNumber}</a><br>
									   </c:when>
									   <c:otherwise>
										   <spring:theme code="orderSummaryPage.na" /><br>
									   </c:otherwise>
								   </c:choose>
								   ${order.guestContactPerson.email} <br>						
							</c:when>
							<c:otherwise>
								<c:set var = "contactPersonName" value = "${order.contactPerson.name}"/>
									<c:choose>
										<c:when test="${fn:startsWith(contactPersonName, 'storecontact')}">
											${order.storeUserName}</br>
										</c:when>
										<c:otherwise>
											${order.contactPerson.name}</br>
										</c:otherwise>
									</c:choose>
		
									<c:choose>
										<c:when test="${not empty order.contactPerson.contactNumber}">
											m: <a class="tel-phone" href="tel:${order.contactPerson.contactNumber}">${order.contactPerson.contactNumber}</a><br>
										</c:when>
										<c:otherwise>
											m:NA<br>
										</c:otherwise>
									</c:choose>
		
									<c:set var = "contactPersonEmail" value = "${order.contactPerson.email}"/>
									<c:choose>
										<c:when test="${fn:startsWith(contactPersonEmail, 'storecontact')}">
											${order.storeContact}<br>
										</c:when>
										<c:otherwise>
											<a href="mailto:${order.contactPerson.email}">${order.contactPerson.email}</a><br>
											<%-- ${order.contactPerson.email}<br> --%>
										</c:otherwise>
									</c:choose>
							</c:otherwise>
							</c:choose>

						</div>

					</div>
					<div class="col-md-3 col-sm-12 hidden bg-danger">
						<div class="secondary-title">
							<b><spring:theme code="orderUnconsignedEntries.DeliveryDateNTime" />:</b>
						</div>
						<div>
							<fmt:formatDate var="fmtDate" value="${order.requestedDate}" pattern="MM/dd/YYYY hh:mm:ss a"/>
							<input type="hidden" id="requestedDateinUTC"  value="${fmtDate}"/>
							<span id="requestedDateInLocal"><fmt:formatDate value="${order.requestedDate}" pattern="MM/dd/YYYY" /></span>
							<BR>

							<c:choose>
								<c:when test="${order.requestedMeridian eq 'AM'}">
									<spring:theme code="orderSummaryPage.morning" />
								</c:when>
								<c:when test ="${order.requestedMeridian eq 'PM'}">
									<spring:theme code="orderSummaryPage.afternoon" />
								</c:when>
								<c:otherwise>
									Any Time
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<c:if test = "${order.guestCustomer ne true and cmsPage.uid ne 'order'}">
						<div class="col-md-3 col-sm-12">
							<div class="secondary-title">
								<b><spring:theme code="orderUnconsignedEntries.accountNumber" /></b>
							</div>
							<div>
									${order.orderingAccount.displayId}<br>
							</div>

						</div>
					</c:if>
					<div class="col-md-12 col-sm-12 margin-top-20">
						<div class="row">
							<c:choose>
								<c:when  test = "${cmsPage.uid eq 'orderConfirmationPage'}">
									<div class="col-md-3 col-xs-12 padding-LeftZero"><p class="message-header additionalInfo bold">Delivery instructions:</p></div>
								</c:when>
								<c:otherwise>
									<div class="col-md-9 col-xs-12"><p class="message-header additionalInfo bold"><spring:theme code="orderSummaryPage.msg.branch" />:</p></div>
								</c:otherwise>
							</c:choose>
							<div class="col-md-9 col-xs-12">
								<p class="order-message">
									<c:choose>
										<c:when test="${not empty order.specialInstruction}">
											${order.specialInstruction}
										</c:when>
										<c:otherwise>NA</c:otherwise>
									</c:choose>
								</p>
							</div>
							<div class="cl"></div>
							<div class="row marginTopBVottom30">
							<div class="col-md-12 info-mesg-wrapper"><spring:theme code="orderSummaryPage.pickup.front.message"/>&nbsp;${order.pointOfService.name}&nbsp;<spring:theme code="orderSummaryPage.delivery.end.message"/></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="cl"></div>
		</div>
	   </div>
	</c:if>
<c:if test="${order.orderType eq 'PARCEL_SHIPPING'}">
	<div class="deliveryInformations">
	   <div class="">
	   <div class="deliveryInformations margin-top-20">
		   <!--orderUnconsignedEntriesConfirmation.tag  -->
		   <div class="col-xs-12">
			   <div class="col-xs-12">
				   <div class="col-md-3 col-sm-12 padding-LeftZero">
					   <div class="secondary-title">
						   <b><spring:theme code="orderSummaryPage.parcel.shipping.to.contact" /></b>
					   </div>
					   <div class="delivery-location-details">

						   <c:choose>
							   <c:when test="${order.guestCustomer eq true}">
								   <div>${order.b2bCustomerData.firstName}&nbsp;${order.b2bCustomerData.lastName}</div>
								   <c:choose>
									   <c:when test="${not empty order.guestContactPerson.contactNumber}">
										   <a class="tel-phone" href="tel:${order.guestContactPerson.contactNumber}">${order.guestContactPerson.contactNumber}</a><br>
									   </c:when>
									   <c:otherwise>
										   <spring:theme code="orderSummaryPage.na" /><br>
									   </c:otherwise>
								   </c:choose>
								   ${order.guestContactPerson.email} <br>
							   </c:when>
							   <c:otherwise>
								   ${order.contactPerson.name}<br>
								   <c:choose>
									   <c:when test="${not empty order.contactPerson.contactNumber}">
										   <a class="tel-phone" href="tel:${order.contactPerson.contactNumber}">${order.contactPerson.contactNumber}</a><br>
									   </c:when>
									   <c:otherwise>
										   <spring:theme code="orderSummaryPage.na" /><br>
									   </c:otherwise>
								   </c:choose>
								   ${order.contactPerson.email}<br>
								   <%--PARCEL_SHIPPING ${cartData.contactPerson.email}<br> --%>
							   </c:otherwise>
						   </c:choose>
					   </div>
				   </div>
				   <div class="col-md-3 col-sm-12">
				   	   <div class="secondary-title margin-zero">
						   &nbsp;
					   </div>
					   <div>  ${order.deliveryAddress.line1},<br>
						   <c:if test="${not empty order.deliveryAddress.line2}">
							   ${order.deliveryAddress.line2},<br>
						   </c:if>
							   ${order.deliveryAddress.town},&nbsp;
							   ${order.deliveryAddress.region.isocodeShort}&nbsp;
							   ${order.deliveryAddress.postalCode}<br>
					   </div>

				   </div>
				   <div class="col-md-3 col-sm-12">
					   <div></div>
				   </div>
				   <c:if test = "${order.guestCustomer ne true and cmsPage.uid ne 'order'}">
					   <div class="col-md-3 col-sm-12">
						   <div class="secondary-title">
							   <b><spring:theme code="orderUnconsignedEntries.accountNumber" /></b>
						   </div>
						   <div>
								   ${order.orderingAccount.displayId}<br>
						   </div>

					   </div>
				   </c:if>
				   <div class="col-md-12 col-sm-12 margin-top-20">
					   <div class="row">
					   	   <c:if test="${order.orderType eq 'DELIVERY'}">
						   <c:choose>
							   <c:when  test = "${cmsPage.uid eq 'orderConfirmationPage'}">
								   <div class="col-md-3 col-xs-12 padding-LeftZero"><p class="message-header additionalInfo bold">Delivery instructions:</p></div>
							   </c:when>
							   <c:otherwise>
								   <div class="col-md-9 col-xs-12"><p class="message-header additionalInfo bold"><spring:theme code="orderSummaryPage.msg.branch" />:</p></div>
							   </c:otherwise>
						   </c:choose>
						   <div class="col-md-9 col-xs-12">
							   <p class="order-message">
								   <c:choose>
									   <c:when test="${not empty order.specialInstruction}">
										   ${order.specialInstruction}
									   </c:when>
									   <c:otherwise>NA</c:otherwise>
								   </c:choose>
							   </p>
						   </div>
						   </c:if>
						   <div class="cl"></div>
						   <div class="row marginTopBVottom30"><div class="col-md-12 info-mesg-wrapper"><spring:theme code="orderSummaryPage.parcel.shipping.message" /></div></div>
					   </div>
				   </div>
			   </div>
		   </div>
	   </div>
	   </div>
	</div>
	   <!--------------------------------********************------------->
    </c:if>
   
	<div class="orderItemsDetails">
		<%-- <div class="col-xs-12">
           <div class="title-bar no-background-color margin20">
               <div class="col-xs-12 padding0 order-confirmation-page-title">
               <spring:theme code="checkout.multi.order.summary" />
               </div>

           </div> --%>

		<div class="col-xs-12">
			<div class="orderSummaryItems">
				<div class=" hidden-xs hidden-sm  hidden-md hidden-lg">
					<div class="col-xs-12 item__list--header sec-title-bar">
						<div class="col-md-7 item__image-title"><spring:theme code="basket.page.product.information"/></div>
						<div class="col-md-2 item__price-title"><spring:theme code="basket.page.price"/></div>
						<div class="col-md-1 item__quantity-title"><spring:theme code="basket.page.qty"/></div>
						<div class="col-md-2 item__total-title"><spring:theme code="basket.page.total"/></div>
					</div>
				</div>
			</div>
			<div class="item__list item__list__cart">
				<!-- Applied Promotion Vouchers -->
				<c:if test="${order.totalDiscounts.value > 0 and not empty order.appliedVouchers}">
					<div class="applied-promotion-wrapper" style="border-bottom: 1px solid #ccc;color: #ff0000;font-size: 14pt;">
						<c:set var="hideVoucher" value="hide" />
						<c:forEach items="${order.appliedVouchers}" var="voucher" varStatus="loop">
							<c:if test="${not empty showVoucherList}">
								<c:forEach items="${showVoucherList}" var="showVoucher" varStatus="loop">
									<c:if test="${voucher eq showVoucher}">
										<c:set var="hideVoucher" value="" />
									</c:if>
								</c:forEach>
							</c:if>
						</c:forEach>
						<c:if test="${hideVoucher ne 'hide'}">
							<span><spring:theme code="orderReviewCartEntries.promoCode" /></span>
						</c:if>
						<c:forEach items="${order.appliedVouchers}" var="voucher" varStatus="loop">
							<c:if test="${not empty showVoucherList}">
								<c:forEach items="${showVoucherList}" var="showVoucher" varStatus="loop">
									<c:if test="${voucher eq showVoucher}">
										<c:choose>
											<c:when test="${fn:length(order.appliedVouchers) gt 1 and !loop.last}">
												<span>${voucher},</span>
											</c:when>
											<c:when test="${fn:length(order.appliedVouchers) gt 1 and loop.last}">
												<span>${voucher} <spring:theme code="orderReviewCartEntries.applied" /></span>
											</c:when>
											<c:otherwise>
												<span>${voucher} <spring:theme code="orderReviewCartEntries.applied" /></span>
											</c:otherwise>
										</c:choose>
									</c:if>
								</c:forEach>
							</c:if>
						</c:forEach>
					</div>
				</c:if>

				<c:forEach items="${order.unconsignedEntries}" var="entry" varStatus="loop">
					<order:orderConfirmationDetails orderEntry="${entry}" order="${order}" itemIndex="${loop.index}"/>
				</c:forEach>
			</div>
		</div>
	</div>
 
 <div class="cl"></div>
    </div>
	<div id="signinId" style="display: none">  <common:signInoverlay/> </div> 
	<div class="cl"></div>