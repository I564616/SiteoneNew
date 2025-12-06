<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="address" tagdir="/WEB-INF/tags/responsive/address"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="multiCheckout" 	tagdir="/WEB-INF/tags/responsive/checkout/multi"%>
<c:set var="homeOwnerCode" value="<%=de.hybris.platform.util.Config.getParameter(\"homeOwner.trade.class.code\")%>"/>
<c:choose>
		<c:when test="${isGuestUser eq true}">
			<c:set var="adjustWidthInstructionBox" value="col-md-12" />
			<c:set var="adjustMarginTop35" value="row marginTop35" />
			<c:set var="adjustMargin35Top0" value=""/>
			<c:set var="adjustDeliveryContinueBtn" value="col-md-5"/>
			<c:set var="mixedcartDeliveryBoxGC" value="mixedcartDeliveryBoxGC"/>
			<c:set var="margin35Top" value="marginTop35"/>
		</c:when>
	
		<c:when test="${isGuestUser eq false}">
			<c:set var="adjustWidthInstructionBox" value="col-md-8" />
			<c:set var="adjustMarginTop35" value="row"/>
			<c:set var="adjustMargin35Top0" value="marginTop35"/>
			<c:set var="adjustDeliveryContinueBtn" value="col-md-8"/>
			<c:set var="mixedcartDeliveryBoxGC" value=""/>
			<c:set var="Margin35Top" value=""/>
		</c:when>
</c:choose>
<div class="contact-data-box col-md-12">
<div class="title-bar order-confirmation-page-bottom row title-mobile-view">
		<div class="numberCircle-div"></div>
					<div class="col-xs-7 col-md-8 order-summary-title">
						<h1 class="order-confirmation-page-title">
						 <span class="pickup-title"><spring:theme code="branch.delivery.gc.title" /></span>
						</h1>
				</div>
					 
					<div class="text-right col-xs-3 edit-delivery-information" style="display:none;">
					<span class="edit-link">
						<span id="mixedCartEditDelivery" class="edit-btn-acco"><spring:theme code="mixcart.multi.edit"/></span>
					   </span>
					</div>
					 
					<div id="delivery_fee" class="col-xs-3  text-right pull-right delivery-fee">
					<c:if test="${cartData.isTampaBranch}" >
					<span class="delivery-fee-title"><spring:theme code="delivery.fee.text" /></span>
					<c:if test="${isGuestUser eq true}">
					  <c:choose>
						<c:when test="${not empty cartData.freight && cartData.freight ne '0.00'}">
                             <span id="deliveryfees" class="headline3">$<fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${cartData.freight}"/> </span>
                             </c:when>
                             <c:otherwise>
                                <span id="deliveryfees" class="headline3">&#36;0.00 </span> 
                                </c:otherwise>
	                    </c:choose>
	                   </c:if>
	                   
	                   <c:if test="${isGuestUser eq false}">
					  <c:choose>
						<c:when test="${not empty cartData.freight && cartData.freight ne '0.00'}">
                             <span id="deliveryfees" class="headline3">&#36;$<fmt:formatNumber pattern="#,##0" minFractionDigits="2" maxFractionDigits="2" value="${cartData.freight}"/> </span>
                             </c:when>
                             <c:otherwise>
                                <span id="deliveryfees" class="headline3">&#36;0.00 </span> 
                                </c:otherwise>
	                    </c:choose>
	                    </c:if>
	                   </c:if>
					</div>
				</div>
				
				<div class="saved-delivery-details desk-mob-margin marginbottom30" style="display:none;">
					<div class="row">
						<div class="center-content delivery-card-content">
						<div class="row">
							<div class="col-md-6">
								<div class="bold black-title"><spring:theme code="mixcart.delivery.contact" /></div>
								<div class="del-card-details">
								<span class="delivery-contact-details"></span>
								</div>
							
							</div>
						
						<div class="col-md-6 ">
								<div class="bold black-title mob-margin-top"><spring:theme code="mixcart.delivery.to" /></div>
								<div class="del-card-details">
								<div class="delivery-address"></div>
			     		<div class="delivery-region "></div>
			     		<div class="delivery-phone"></div>
								</div>
							
							</div>
						</div>
						</div>
					</div>
				</div>
				
				<div class="delivery-section hidden">
				
				<c:forEach items="${cartData.deliveryModeAndBranchOrderGroups}" var="groupData" varStatus="status">
		       		<c:if test="${groupData.deliveryMode.code eq 'standard-net'}">
		       		<div class="delivery-mixedcart-location row marginTop35">
		       		<div class="cl"></div>
	        <div class="topBottom-border mixedcart-content-align">
	        <div class="row">
		        <div>
		        
		       		<div id="pickup_Location_Div" class="col-md-3">
		       		
		       		<p class="bold-text black-title"><spring:theme code="mixcart.delivery.branch" /> <span class="address-number"></span></p>
					   <c:if test="${!isMixedCartEnabled}">
							<span class="hidden-md hidden-lg pull-right direction-link"><a id="getDirection" href="" data-url="${groupData.pointOfService.address.line1},${groupData.pointOfService.address.line2},${groupData.pointOfService.address.town},${groupData.pointOfService.address.region.isocodeShort},${groupData.pointOfService.address.postalCode}"><spring:theme code="choosePickupDeliveryMethodPage.get.direction" /></a></span>
						</c:if>
					   </div>
		       		<div class="col-md-6">
		         		<span class="pickup-store">
		         		<span class="pickup-store-detail">
		           			<strong>${groupData.pointOfService.name}</strong><br><strong><c:if test="${not empty groupData.pointOfService.title}">${groupData.pointOfService.title}<br></c:if></strong>${groupData.pointOfService.address.line1}<br>${groupData.pointOfService.address.town},&nbsp;${groupData.pointOfService.address.region.isocodeShort}&nbsp;${groupData.pointOfService.address.postalCode}</span><br><a class="tel-phone textDecorationNone" href="tel:${groupData.pointOfService.address.phone}">${groupData.pointOfService.address.phone}</a>
		           			</span> 
		           	 </div>
		           	 <div class="col-md-3 text-right">
						<c:if test="${!isMixedCartEnabled}">
							<div class="hidden-xs hidden-sm direction-link">
								<a id="getDirection" href="" data-url="${groupData.pointOfService.address.line1},${groupData.pointOfService.address.line2},${groupData.pointOfService.address.town},${groupData.pointOfService.address.region.isocodeShort},${groupData.pointOfService.address.postalCode}"><spring:theme code="choosePickupDeliveryMethodPage.get.direction" /></a>
							</div>
						</c:if>
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
	      			 
	      			<div class="${margin35Top}">
		        <c:if test="${groupData.deliveryMode.code eq 'standard-net'}">
     		   		<storepickup:pickupCartItems cartData="${cartData}" groupData="${groupData}" index="${status.index}" showHead="true" />
     		   		 </c:if>
     		   		 </div>
			   </c:forEach>
			   	<c:if test="${isGuestUser eq false }">
					<div class="row">
						<div class="topBottom-border mixedcart-content-align paddingBottom50">
							<div class="row">
						
								<div class="col-xs-12 col-md-4">
									<p class="bold black-title"><spring:theme code="mixcart.delivery.contact"/></p>
									<span class="hidden-md hidden-lg pull-right direction-link">
										<a id="deliveryChangeContact" class="mixCartChangeContact" href="#"><spring:theme code="choosePickupDeliveryMethodPage.change.contact"/></a>
									</span>
								</div>
							
								<div class="col-xs-6 col-md-5">
									<span class="delivery-contact-details"></span>
								</div>
							
								<div class="col-md-3 hidden-xs hidden-sm text-right col-xs-6">
									
									<a id="deliveryChangeContact" class="mixCartChangeContact" href="#"><spring:theme code="choosePickupDeliveryMethodPage.change.contact"/></a>
								</div>
							</div>
						</div>
					</div>
			   </c:if>
			   
				<div class="${adjustMarginTop35}">
					<div id="delivery_content1" class="topBottom-border mixedcart-content-align page-choosePickupDeliveryMethodPage">
						<div class="row">
							<div class="col-xs-12 hidden-md hidden-lg">
								<p class="marginBottom20"><span class="bold-text black-title"><spring:theme code="delivery.location.text"/></span></p>
							</div>
							<c:if test="${isGuestUser eq false }">   
								<div class="col-xs-12 col-md-8 float-div">
						
									<label for="MixedcartdeliveryAddress" class="location-select-label"><strong> <spring:theme code="choosePickupDeliveryMethodPage.delivery.location" /></strong></label><br>
									<form:hidden path="deliveryContactId" id="deliveryContactId" value="${siteOneOrderTypeForm.deliveryContactId}" />
									<form:hidden path="deliveryAddressId" id="deliveryAddressId" data-mode="standard-net"  value="${siteOneOrderTypeForm.addressId}" />
									<form:errors path="deliveryAddressId"/>
									<select id="MixedcartdeliveryAddress" data-mode="standard-net" placeholder="select" class="deliveryadress-selection">
										<option value="selectDefault" disabled><spring:theme code="choosePickupDeliveryMethodPage.select.delivery.location" /></option>
										<c:forEach var="item" items= "${deliveryAddresses}">
											<c:choose>
												<c:when test="${not empty siteOneOrderTypeForm.addressId && siteOneOrderTypeForm.addressId != '' && siteOneOrderTypeForm.addressId == item.id}">
													<option value ="${item.id}" selected><spring:theme code="choosePickupDeliveryMethodPage.select.delivery.address" /> ${item.line1}</option>
												</c:when>
												<c:otherwise>
													<option value ="${item.id}" selected><spring:theme code="choosePickupDeliveryMethodPage.select.delivery.address" /> ${item.line1}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									<br>
									<span class="deliveryError" id="errorDeliveryAddressRadio"></span>
									<div class="delivery-address"></div>
									<div class="delivery-region "></div>
									<div class="delivery-phone"></div>
									<sec:authorize var="isAdmin" access="hasAnyRole('ROLE_B2BADMINGROUP')"/>
									<c:if test="${isAdmin or enableAddDeliveryAddress}">
										<br/>
										<div class="newaddress-mixedcart btn btn-block btn-address" data-mode="delivery"><span class="glyphicon glyphicon-plus address-icon"></span> 
										<strong><spring:theme code="mixcart.delivery.location" /></strong></div>
									</c:if>
									<div id="addAddressSuccess" class="row"></div>
								</div>
							
							</c:if>
							
							<c:if test="${isGuestUser eq true }">
								<div class="col-xs-12 col-md-8 float-div">
									<div class="lift-sec">
										<div class="col-md-12 message-center">
											<span class="colored">
												<input id="mixedcart-isSameAsContactInfo" name="isSameAsContactInfoDelivery" type="checkbox" class="mixedcart-delivery-check" checked/>
											</span>
											<label><spring:theme code="parcel.shipping.checkbox.label" /></label>
										</div>
										<div class="cl"></div>
									</div>
									<div class="row">
										<div class="col-xs-12 delivery-Newaddress"></div>
										<div class="col-xs-12 delivery-Newregion"></div>
							
										<div class="col-xs-12" id="mixedcart-deliveryaddressdata" >${addressData.line1}</div>
										<div class="col-xs-12" id="mixedcart-deliveryaddressdat1" >${addressData.line2}</div>
										<div class="col-xs-12">
											<span id="mixedcart-deliverycitydata" >${addressData.town}</span>
											<span id="mixedcart-deliverystatedata" >${addressData.district}</span>
											<span id="mixedcart-deliveryzipdata" >${addressData.postalCode}</span>
										</div>
									</div>
								</div>
							</c:if>
							
							<div class="col-xs-12 col-md-4 icon-text-sec">
								<p class="hidden-xs hidden-sm marginBottom20">
									<span class="bold-text black-title"><spring:theme code="delivery.location.text"/></span>
								</p>
								<div class="checkout-info-icons">
									<div class="col-md-10 padLeftZero">
									<svg xmlns="http://www.w3.org/2000/svg" width="24" height="23.899" viewBox="0 0 24 23.899"><defs><style>.icon-7{fill:#78a22f;}</style></defs>
										<path class="icon-7" d="M408.138,321.446l-8.883-2.914-7.5,3.387V341.9l7.625-3.445,8.892,2.914,7.482-3.388V318Zm-7.691-.184,6.639,2.178v15.2l-6.639-2.176Zm-6.472,2.092,4.25-1.918v15.1l-4.25,1.92Zm19.556,13.194-4.222,1.912v-15.1l4.222-1.911Z" transform="translate(-391.752 -318)"/>
									</svg>
									<p><spring:theme code="delivery.msg1.text"/></p>	
								</div>
							</div>
						
						</div>
						<div class="cl"></div>
						<c:if test="${isGuestUser eq true}">
							<multiCheckout:contactInformationGuestDelivery/>
						</c:if>
					</div>
				</div>
		        </div>
			    <div class="cl"></div>
			    
			    
			    
			    
			    
			    
			    
			     <div class="row ${mixedcartDeliveryBoxGC}">
			    <div class="topBottom-border mixedcart-content-align">
	        <div class="row">
			    <c:if test="${isGuestUser eq false }">
			    <div class="col-xs-12 col-md-4 no-padding-xs icon-text-sec">
		        		<div class="col-md-12 marginBottom20 no-padding-md"><p class="bold-text black-title"><spring:theme code="choosePickupDeliveryMethodPage.delivery.info" /></p></div>
		        		<div class="hidden-xs hidden-sm margin-top-20">
			       				<storepickup:deliveryInfomationIcons/>
			       			</div>
		        		</div>
			    </c:if>
			  
						<div class="col-xs-12 ${adjustWidthInstructionBox}">
							<c:if test="${isGuestUser eq false }">
								<div class="lift-sec charges-sec1">
									<div class="col-md-8 col-xs-12 message-center">
										<span class="colored">
											<form:checkbox value="" path="expediteDelivery" id="expediteDelivery" class="form-control" />
										</span>
										<label> <spring:theme code="expedited.delivery.text"/></label>
									</div>
									<div class="col-md-5 col-xs-6 text-right">
										<em class="charges-text"><spring:theme code="charges.apply.text"/></em>
									</div>
									<div class="cl"></div>
								</div>
							</c:if>
							<div class="row">	
								<div class="${adjustMargin35Top0}">	        
									<div class="col-md-11 bold black-title ${(isMixedCartEnabled)? 'deliveryandship-instruction' : '' }">
										
			        					<c:choose>
											<c:when test="${!((cartData.orderingAccount.tradeClass eq homeOwnerCode) || (isGuestUser eq true)) eq true}">
					        			 		<spring:theme code="checkout.branch.msg" />
					        			 	</c:when>
				        			 	<c:otherwise>
				        			 		<spring:theme code="mixcart.delivery.instructions"/>
				        			 	</c:otherwise>
			        			 	</c:choose>
									</div>
									<div class="col-md-1 pull-right optional-message-wrapper-mixedcart">
										<div id="delivery-storeInstrn">
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
														<p> <spring:theme code="choosePickupDeliveryMethodPage.text4" /></p>
														<p> <spring:theme code="choosePickupDeliveryMethodPage.text5" /></p>
													</div>
												</span>
											</a>
											<br>
													
										</div>
									</div>
								</div> 
								<div class="col-md-12">
									<spring:message code="optional.text" var="textplaceholder"/>
									<form:textarea path="deliveryInstruction" id="specialinstruction" class="form-control ${(isMixedCartEnabled)? 'instruction-text-area' : '' }" rows="8" cols="40" maxlength="1000" placeholder="${textplaceholder}"/>
									<c:if test="${!isMixedCartEnabled}">
										<p class="small-text" ><span id="wordcountstaticmessage"><spring:theme code="choosePickupDeliveryMethodPage.text6" /></span></p>
									</c:if>
									<p class="small-text" ><span id="remainingwordcount" hidden></span></p>
									<span id="errorSpecialinstruction" ></span>
								</div>	
									
								<div class="${isGuestUser? 'col-md-5' : 'col-md-8' } col-xs-12 col-sm-6 margin-Top-20">
									<button type="button" class="btn btn-block btn-primary bold-text submit-delivery-data submit-ordertype-data"><spring:theme code="choosePickupDeliveryMethodPage.continue.to.payment" /></button>
								</div>
												
							</div>		        
								
						</div>
						<div class="${(isMixedCartEnabled)? 'col-xs-9 hidden-md hidden-lg delivery-charge-icon-xs' : 'hidden-xs hidden-sm hidden-md hidden-lg' }">
							<svg xmlns="http://www.w3.org/2000/svg" width="39.863" height="29.683" viewBox="0 0 39.863 29.683"><defs><style>.a{fill:#78a22f;}</style></defs><g transform="translate(-944.769 -522.266)"><path class="a" d="M956.54,545.693h0Z"/><path class="a" d="M984.082,538.738l-3.892-6.485a2.576,2.576,0,0,0-2.082-1.178h-5.989v-1.291a1.84,1.84,0,0,0-1.837-1.837h-6.537a9.974,9.974,0,1,0-14.513,12.61v6.427a1.842,1.842,0,0,0,1.844,1.836h1.418a4.182,4.182,0,0,0,8.094,0h12.688a4.182,4.182,0,0,0,8.094,0h1.419a1.819,1.819,0,0,0,1.843-1.793v-6.3A4.269,4.269,0,0,0,984.082,538.738Zm-37.313-6.491a7.982,7.982,0,1,1,7.981,7.982A7.99,7.99,0,0,1,946.769,532.247Zm9.771,17.592a2.073,2.073,0,0,1,0-4.146h0a2.073,2.073,0,0,1,0,4.146Zm13.472-3.127h-9.425a4.182,4.182,0,0,0-8.094,0h-1.147v-5.094a9.956,9.956,0,0,0,13.136-11.563h5.53Zm10.154-10.4,2.108,3.512a2.225,2.225,0,0,1,.218.634h-5.684c0-.736,0-2.893.005-4.166.265.01.674.012,1.277.014Zm-2.844,13.529a2.073,2.073,0,0,1,0-4.146h0a2.073,2.073,0,0,1,0,4.146Zm5.2-3.127h-1.152a4.182,4.182,0,0,0-8.1,0h-1.148V533.183H978.1a.549.549,0,0,1,.275.155l.518.864h-2.364a1.827,1.827,0,0,0-1.829,1.821v4.72a1.831,1.831,0,0,0,1.833,1.822h5.982Z"/><path class="a" d="M955.9,531.646l-1.7-.566a.925.925,0,0,1-.692-.95,1.28,1.28,0,0,1,2.5,0,.931.931,0,0,0,1.862,0,2.889,2.889,0,0,0-2.182-2.735v-.512a.931.931,0,0,0-1.862,0v.513a2.887,2.887,0,0,0-2.182,2.734,2.8,2.8,0,0,0,1.966,2.717l1.705.565a.927.927,0,0,1,.693.951,1.28,1.28,0,0,1-2.5,0,.931.931,0,0,0-1.862,0,2.89,2.89,0,0,0,2.182,2.736v.512a.931.931,0,0,0,1.862,0V537.1a2.89,2.89,0,0,0,2.182-2.736A2.8,2.8,0,0,0,955.9,531.646Z"/></g></svg>
							<p class="delivery-charge-text"><spring:theme code="delivery.msg3.text1"/></p>
			       		</div>
			 		</div>
				</div>
			   
			</div>   
</div>
</div>	
	