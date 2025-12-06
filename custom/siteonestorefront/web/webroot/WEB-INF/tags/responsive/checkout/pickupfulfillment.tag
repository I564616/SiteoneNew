<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="multiCheckout" 	tagdir="/WEB-INF/tags/responsive/checkout/multi"%>
<c:set var="homeOwnerCode" value="<%=de.hybris.platform.util.Config.getParameter(\"homeOwner.trade.class.code\")%>"/>
<c:choose>
		<c:when test="${isGuestUser eq true}">
			<c:set var="margin35Top" value="marginTop35"/>
		</c:when>
	
		<c:when test="${isGuestUser eq false}">
			<c:set var="Margin35Top" value=""/>
		</c:when>
</c:choose>
<div class="contact-data-box col-md-12">

<div class="title-bar order-confirmation-page-bottom row title-mobile-view">
		<div class="numberCircle-div"> </div>
					<div class="col-xs-7 col-md-8 order-summary-title">
						<h1 class="order-confirmation-page-title">
						 <span class="pickup-title"><spring:theme code="mixcart.pickup.heading" /></span>
						</h1>
				</div>
					 
					<div class="text-right col-xs-3 edit-pickup-information" style="display:none;">
					<span class="edit-link">
						<span id="mixedCartEditPickup" class="edit-btn-acco"><spring:theme code="mixcart.multi.edit"/></span>
					   </span>
					</div>
				</div>
				
				<div class="pickup-fulfillment-branch-details saved-pickup-details" style="display:none;">
				<div class="margin20">
					<c:set var="pickupLocation" value="" />
					<c:set var="pickupLocationSize" value="0" />
					<c:forEach items="${cartData.deliveryModeAndBranchOrderGroups}" var="groupDataItem" >
						<c:if test="${groupDataItem.deliveryMode.code eq 'pickup'}">
							<c:set var="pickupLocationSize" value="${pickupLocationSize + 1}" />
						</c:if>
					</c:forEach>
					<c:forEach items="${cartData.deliveryModeAndBranchOrderGroups}" var="groupData" varStatus="status">
						<c:if test="${groupData.deliveryMode.code eq 'pickup'}">
						  	<c:set var="pickupLocation" value="${groupData.pointOfService.name}" />
						  	<div class="fulfillmentinfo-capsule-block margin-bot-10-md"><spring:theme code="choosePickupDeliveryMethodPage.pickup.location" />
								<c:if test="${pickupLocationSize gt 1}">
									<span class="address-number">
										<span class="numberorder"> #${status.count}</span>
									</span>: <b>${pickupLocation}</b></div> 
								</c:if>
								<c:if test="${pickupLocationSize eq 1}">
									: <b>${pickupLocation}</b></div> 
								</c:if>
						</c:if>
					</c:forEach>
				</div>
				</div>
				
				
				<div class="pickup-section hidden">
				<form:hidden path="contactId" value="${siteOneOrderTypeForm.contactId}" />
		       <c:forEach items="${cartData.deliveryModeAndBranchOrderGroups}" var="groupData" varStatus="status">
		       <c:if test="${groupData.deliveryMode.code eq 'pickup'}">
		       		
		       		<div class="pickup-mixedcart-location row desk-mob-margin">
		       		<div class="cl"></div>
	        <div class="topBottom-border mixedcart-content-align">
	        <div class="row">
		        <div id="pickup" class="pickup--content">
		       		<div id="pickup_Location_Div" class="col-md-3 col-xs-12">
						<p class="bold-text black-title"> 
							<spring:theme code="choosePickupDeliveryMethodPage.pickup.location" /> <span class="address-number"></span>
						</p>
						<span class="hidden-md hidden-lg pull-right direction-link">
							<a id="getDirection" href="" data-url="${groupData.pointOfService.address.line1},${groupData.pointOfService.address.line2},${groupData.pointOfService.address.town},${groupData.pointOfService.address.region.isocodeShort},${groupData.pointOfService.address.postalCode}"><spring:theme code="choosePickupDeliveryMethodPage.get.direction" /></a>
						</span>
		       		</div>
		       		<div class="col-md-5 col-xs-9">
		         		<span class="pickup-store">
							<span class="pickup-store-detail">
								<strong>${groupData.pointOfService.name}</strong>
								<br><strong><c:if test="${not empty groupData.pointOfService.title}">${groupData.pointOfService.title}<br></c:if></strong>${groupData.pointOfService.address.line1}<br>${groupData.pointOfService.address.town},&nbsp;${groupData.pointOfService.address.region.isocodeShort}&nbsp;${groupData.pointOfService.address.postalCode}
							</span>
							<br>
							<a class="tel-phone textDecorationNone" href="tel:${groupData.pointOfService.address.phone}">${groupData.pointOfService.address.phone}</a>
		           		</span> 
		           	</div>
					<div class="col-md-4 text-right hidden-xs col-xs-6">
						<div class="direction-link">
							<a id="getDirection" href="" data-url="${groupData.pointOfService.address.line1},${groupData.pointOfService.address.line2},${groupData.pointOfService.address.town},${groupData.pointOfService.address.region.isocodeShort},${groupData.pointOfService.address.postalCode}"><spring:theme code="choosePickupDeliveryMethodPage.get.direction" /></a>
						</div>
					</div>
		           	 
		           	 <div id="pickup_Details" class="col-md-6 col-xs-12 bold-text">
		           	 <span id="requestedDateData"></span> 	  <span id="requestedMeridianData"></span>
		           	 </div>
		        </div>
		         
		         <div class="cl"></div>
		         </div>
	      			</div>
	      			</div>
	      			</c:if>
	      			<div class="${Margin35Top}">
			        	<c:if test="${groupData.deliveryMode.code eq 'pickup'}">
	     		   		<storepickup:pickupCartItems cartData="${cartData}" groupData="${groupData}" index="${status.index}" showHead="true" />
	     		   		 </c:if>
     		   		 </div>
			   </c:forEach>
			  
			  
			  
			   <div class="marginTop20 row">
			    <div class="topBottom-border mixedcart-content-align">
			        <div class="row">
			        	<div class="col-md-11 bold black-title">
			        				<c:choose>
											<c:when test="${!((cartData.orderingAccount.tradeClass eq homeOwnerCode) || (isGuestUser eq true)) eq true}">
					        			 		<spring:theme code="checkout.branch.msg" />
					        			 	</c:when>
				        			 	<c:otherwise>
				        			 		<spring:theme code="mixcart.pickup.instruction" />
				        			 	</c:otherwise>
			        			 	</c:choose>
			        	
			        	</div>
			        	<div class="col-md-1 pull-right optional-message-wrapper-mixedcart">
			        	<div id="pickup-storeInstrn">
			        			 	<a class=" pull-right checkout-instruction-icon">
			        			 	<svg xmlns="http://www.w3.org/2000/svg" width="13.653" height="13.653" viewBox="0 0 13.653 13.653">
									  <g id="info_icon" transform="translate(0.5 0.5)">
									    <path id="info-circle" d="M14.327,8a6.327,6.327,0,1,0,6.327,6.327A6.328,6.328,0,0,0,14.327,8Z" transform="translate(-8 -8)" fill="none" stroke="#78a22f" stroke-width="1"/>
									    <path id="info-circle-2" data-name="info-circle" d="M14.327,10.806a1.071,1.071,0,1,1-1.071,1.071A1.071,1.071,0,0,1,14.327,10.806Zm1.429,6.48a.306.306,0,0,1-.306.306H13.2a.306.306,0,0,1-.306-.306v-.612a.306.306,0,0,1,.306-.306h.306V14.735H13.2a.306.306,0,0,1-.306-.306v-.612a.306.306,0,0,1,.306-.306h1.633a.306.306,0,0,1,.306.306v2.551h.306a.306.306,0,0,1,.306.306Z" transform="translate(-8 -8)" fill="#78a22f"/>
									  </g>
									</svg>
			        			 		<span>
			        			 		<div id="termsAndConditions-overlayText">
			        			 		
									      		<p>
									      		<c:choose>
														<c:when test="${!((cartData.orderingAccount.tradeClass eq homeOwnerCode) || (isGuestUser eq true)) eq true}">
								        			 		<spring:theme code="choosePickupDeliveryMethodPage.checkout1" />
								        			 	</c:when>
								        			 	<c:otherwise>
								        			 		<spring:theme code="choosePickupDeliveryMethodPage.text3" />
								        			 	</c:otherwise>
						        			 	</c:choose>
									      			
									      		</p>
									      		<p>
									      			 <spring:theme code="choosePickupDeliveryMethodPage.text4" />
									      		</p>
									      		<p>
									      			<spring:theme code="choosePickupDeliveryMethodPage.text5" />
									      		</p>
								      		</div>
			        			 		</span>
			        			 	</a>
			        			 	<br>
			        			 	
			        			</div>
			        	</div>
			        	 
			        	<div class="col-md-12">
			        		<spring:message code="optional.text" var="textplaceholder"/>
							<form:textarea path="pickupInstruction" id="specialinstruction" class="form-control ${(isMixedCartEnabled)? 'instruction-text-area' : '' }" rows="8" cols="40" maxlength="1000" placeholder="${textplaceholder}"/>
							<c:if test="${!isMixedCartEnabled}">
								<p class="small-text" ><span id="wordcountstaticmessage"><spring:theme code="choosePickupDeliveryMethodPage.text6" /></span></p>
							</c:if>
				        	<p class="small-text" ><span id="remainingwordcount" hidden></span></p>
				        	<span id="errorSpecialinstruction" ></span>
			        	</div>
			        </div>
	        	
			   <div class="col-md-5 col-xs-12 col-sm-6 margin30 btn-margin-bottom">
			   <div class="row">
			   <span class="pickupError" id="errorPickupAddressRadio"></span>
      			<button type="button" class="btn btn-block btn-primary bold-text submit-pickup-data submit-ordertype-data"><spring:theme code="choosePickupDeliveryMethodPage.continue.to.payment" /></button>
      			</div>
		    </div>
		    </div>
			   </div>
			  </div> 
			  
			  
			  
			  
			  
			  
			  
 </div>