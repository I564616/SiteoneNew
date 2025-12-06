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
 
 <div class="row marginTop35">
<div class="title-bar no-background-color col-md-12">
				<div class="col-xs-12 padding0 order-confirmation-page-title">
					<spring:theme code="orderunconsignedentries.branchPickUpDetails.mixedcart" />
				</div>
			</div>
			</div>
			  <div class="cl"></div>
	 	
	<div class="instruction-form col-md-12">
<div class="col-md-3 col-sm-12">
					<div class="message-header bold">
						<b><spring:theme code="orderUnconsignedEntries.pickUpContact" /></b>
					</div>
					<div>
					<c:choose>
								<c:when test="${order.guestCustomer eq true }">
									<div class="add-secondaryTitle">
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
											 
									</div>
								</c:when>
								<c:otherwise>
						<c:set var = "contactPersonName" value = "${order.contactPerson.name}"/>
						<c:choose>
							<c:when test="${fn:startsWith(contactPersonName, 'storecontact')}">
								${order.storeUserName}<div class="cl"></div> 
							</c:when>
							<c:otherwise>
								${order.contactPerson.name}<div class="cl"></div> 
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
				<div class="hidden-md hidden-lg cl"><div class="marginTop20"></div></div>
				<div class="col-md-9">
						<c:choose>
							<c:when  test = "${cmsPage.uid eq 'orderConfirmationPage'}">
								<div class="col-xs-12 col-sm-12  no-padding-xs col-md-12 message-header bold"><spring:theme code="orderunconsignedentries.pickUpInstruction.mixedcart" />:</div>
							</c:when>
							<c:otherwise>
								<div class="col-xs-12 col-sm-12  col-md-12 bold"><spring:theme code="orderSummaryPage.msg.branch" />:</div>
							</c:otherwise>
						</c:choose>
						<p class="col-xs-12 col-sm-12 col-md-12 no-padding-xs">
							<c:choose>
								<c:when test="${not empty order.pickupInstruction}">
									${order.pickupInstruction}
								</c:when>
								<c:otherwise>NA</c:otherwise>
							</c:choose>
						</p>
					</div>
					<div class="cl"></div>
					
					<div class="marginTop35">
				<div class="col-xs-12  order-confirmation-page-title"><spring:theme code="orderunconsignedentries.pickupDetails.mixedcart"/></div>
				</div>
	</div>
			<div class="cl"></div>
			<div id="pickup-box">
		<c:forEach items="${order.deliveryModeAndBranchOrderGroups}" var="groupData" varStatus="loop">	
<c:if test="${groupData.deliveryMode.code eq 'pickup'}" >
						
						 <div class="border-grey white-bg marginTop35">		
	 			
	<div class="PickupInformation">
	<c:set var="counter" value="${counter+1}"/>
		<!--orderUnconsignedEntriesConfirmation.tag  -->
		<div class="col-xs-12">
				<div class="marginTop20">
					<div class="order-confirmation-page-title">
						 <spring:theme code="orderUnconsignedEntries.pickupLocation" />#${counter}:
						<span class="branch-name">${groupData.pointOfService.name}</span> 
						  <span class="branch-name">${groupData.pointOfService.title}</span> 
						  </div>
					 </div>
					<div class="pickup-location-details marginTop10">
						  
						 <span>${groupData.pointOfService.address.line1}</span> |
						  <span>${groupData.pointOfService.address.town},${order.pointOfService.address.region.isocodeShort}&nbsp;${groupData.pointOfService.address.postalCode}</span>  |
						 <span><a class="tel-phone" href="tel:${groupData.pointOfService.address.phone}">${groupData.pointOfService.address.phone}</a></span> 
					</div>
				
				
					
					<div class="cl"></div>
				    <div class="row">
						<div class="col-md-12 info-mesg-wrapper marginTopBVottom30"><spring:theme code="orderSummaryPage.pickup.front.message.mixedcart" />&nbsp;${groupData.pointOfService.name}&nbsp;<spring:theme code="orderSummaryPage.pickup.end.message.mixedcart" /></div>
					</div>

		</div>
	</div>
	
						<c:forEach items="${groupData.entries}" var="entry">
						<div class="product-section">
							<order:orderConfirmationDetails orderEntry="${entry}" order="${order}" itemIndex="${loop.index}"/>
				 			<div class="cl"></div>
						</div>
	<div class="cl"></div>
						</c:forEach>
				</div>

	</c:if>
</c:forEach>

 </div>

  
	
 
 
 