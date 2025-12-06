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
					<spring:theme code="orderunconsignedentries.deliveryDetails.mixedcart.title" />
				</div>
			</div>
			</div>
	 		
	<div class="instruction-form col-md-12">

<div class="col-md-3 col-sm-12">
						<div class="message-header bold">
							 <spring:theme code="orderUnconsignedEntries.deliveryContact" /> 
						</div>
						<div>
						<c:choose>
								<c:when test="${order.guestCustomer eq true }">
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
							<c:set var = "contactPersonName" value = "${order.deliveryContactPerson.name}"/>
							<c:choose>
								<c:when test="${fn:startsWith(contactPersonName, 'storecontact')}">
									${order.storeUserName}</br>
								</c:when>
								<c:otherwise>
									${order.deliveryContactPerson.name}</br>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test="${not empty order.deliveryContactPerson.contactNumber}">
									m: <a class="tel-phone" href="tel:${order.deliveryContactPerson.contactNumber}">${order.deliveryContactPerson.contactNumber}</a><br>
								</c:when>
								<c:otherwise>
									m:NA<br>
								</c:otherwise>
							</c:choose>

							<c:set var = "contactPersonEmail" value = "${order.deliveryContactPerson.email}"/>
							<c:choose>
								<c:when test="${fn:startsWith(contactPersonEmail, 'storecontact')}">
									${order.storeContact}<br>
								</c:when>
								<c:otherwise>
									<a href="mailto:${order.contactPerson.email}">${order.deliveryContactPerson.email}</a><br>
									<%-- ${order.contactPerson.email}<br> --%>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
						</c:choose>
						</div>

					</div>
					<div class="hidden-lg hidden-md cl marginTop20"></div>
<div class="col-md-3 col-sm-12">
							<div class="bold message-header"><spring:theme code="orderUnconsignedEntries.DeliveryLocation" /></div>
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
								<c:when test="${not empty order.deliveryContactPerson.contactNumber}">
									<p>
									<span>
										m:<a class="tel-phone" href="tel:${order.deliveryContactPerson.contactNumber}">${order.deliveryContactPerson.contactNumber}</a>
									</span>
									</p>
								</c:when>
								<c:otherwise>
									m:NA<br>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<div class="hidden-lg hidden-md cl marginTop20"></div>
					<div class="col-md-6">
					<c:choose>
								<c:when  test = "${cmsPage.uid eq 'orderConfirmationPage'}">
									<div class="col-md-12 col-xs-12 no-padding-xs"><p class="message-header bold"><spring:theme code="orderUnconsignedEntries.DeliveryInstructions" /></p></div>
								</c:when>
								<c:otherwise>
									<div class="col-md-12 col-xs-12"><p class="message-header bold"><spring:theme code="orderSummaryPage.msg.branch" />:</p></div>
								</c:otherwise>
							</c:choose>
							<div class="col-md-12 col-xs-12 no-padding-xs">
								<p class="order-message">
									<c:choose>
										<c:when test="${not empty order.deliveryInstruction}">
											${order.deliveryInstruction}
										</c:when>
										<c:otherwise>NA</c:otherwise>
									</c:choose>
								</p>
							</div>
					
					
					
					</div>
					<div class="cl"></div>
					<div class="row marginTop35">
					<div class="title-bar no-background-color col-md-12">
				<div class="col-xs-12  pad-xs-lft-15 order-confirmation-page-title"><spring:theme code="orderunconsignedentries.deliveryDetails.mixedcart"/></div>
				</div>
		</div>
	</div>
	
	
	
	<div class="cl"></div>
<div id="delivery-box">
<c:forEach items="${order.deliveryModeAndBranchOrderGroups}" var="groupData" varStatus="loop">	
<c:if test="${groupData.deliveryMode.code eq 'standard-net'}" >
						
						 <div class="border-grey white-bg  marginbottomp30"> 			
	<div class="deliveryInformationss">
		<div class="">
		<div class="deliveryInformations margin-top-20">
			<!--orderUnconsignedEntriesConfirmation.tag  -->
			<div class="col-xs-12">
				<div>
					<div class="order-confirmation-page-title">
						 <spring:theme code="orderunconsignedentries.deliveryfrom.mixedcart"/>:&nbsp;
						<span class="branch-name">${groupData.pointOfService.name}</span> 
						  <span class="branch-name">${groupData.pointOfService.title}</span> 
						  </div>
					<div class="pickup-location-details marginTop10">
						  
						 <span>${groupData.pointOfService.address.line1}</span> |
						  <span>${groupData.pointOfService.address.town},${groupData.pointOfService.address.region.isocodeShort}&nbsp;${groupData.pointOfService.address.postalCode}</span>  |
						 <span><a class="tel-phone" href="tel:${groupData.pointOfService.address.phone}">${groupData.pointOfService.address.phone}</a></span> 
					</div>
					 
					<div class="col-md-12 col-sm-12">
						<div class="row">
							<div class="cl"></div>
							<div class="row">
							<div class="col-md-12 info-mesg-wrapper marginTopBVottom30">${groupData.pointOfService.name}&nbsp;<spring:theme code="orderSummaryPage.delivery.end.message.mixedcart"/></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="cl"></div>
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
		
	
 