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
<spring:url value="/" var="homelink" htmlEscape="false"/>
<c:choose>
		<c:when test="${isGuestUser eq true}">
			<c:set var="mixedcartShippingBoxGC" value="mixedcartShippingBoxGC"/>
		</c:when>
	
		<c:when test="${isGuestUser eq false}">
			<c:set var="mixedcartShippingBoxGC" value="marginTop35"/>
		</c:when>
</c:choose>

<div class="contact-data-box col-md-12">
<div class="title-bar order-confirmation-page-bottom row title-mobile-view">
		<div class="numberCircle-div"></div>
					<div class="col-xs-7 col-md-8 order-summary-title">
						<h1 class="order-confirmation-page-title">
						 <span class="pickup-title"><spring:theme code="shipping.title.text" /></span>
						</h1>
				</div>
					<div class="text-right col-xs-3 edit-shipping-information" style="display:none;">
					<span class="edit-link">
						<span id="mixedCartEditShipping" class="edit-btn-acco"><spring:theme code="mixcart.multi.edit"/></span>
					   </span>
					</div> 
					<div class="col-xs-3 text-right delivery-fee-shipping">
					<div id="shipping_fee" class="shipping_fee" style="display:none;">
					<span class="delivery-fee-title"><spring:theme code="flat.rate.text"/></span>
					<span class="headline3">
					<c:choose>
						<c:when test="${not empty flatRateShippingFee && flatRateShippingFee ne '0.0'}">
							&#36;${flatRateShippingFee}
						</c:when>
						<c:otherwise>
							 <spring:theme code="text.shipping.free"/>
						</c:otherwise>
					</c:choose>
					</span>
					</div>
					
					
					</div>
				</div>

				<div class="saved-shipping-details desk-mob-margin marginbottom30" style="display:none;">
					<div class="row">
						<div class="center-content delivery-card-content">
						<div class="row">
							<div class="col-md-6">
								<div class="bold black-title"><spring:theme code="mixcart.shipping.contact"/></div>
								<div class="margin-top-20">
								<span class="shipping-contact-details">
	   				</span>
								</div>
							
							</div>
						
						<div class="col-md-6">
								<div class="bold black-title"><spring:theme code="mixcart.shipping.to"/></div>
								<div class="margin-top-20">
								<div class="shipping-address"></div>
			     		<div class="shipping-region "></div>
			     		<div class="shipping-phone"></div>
								</div>
							
							</div>
						</div>
						</div>
					</div>
				</div>
				
				<div class="shipping-section hidden">
				<div class="cl marginTop35"></div>
				<div class="row">
				<div class="grey-border">
				</div>
				</div>
				</div>
				<div class="shipping-section marginTop35 hidden">
			       	<c:forEach items="${cartData.deliveryModeAndBranchOrderGroups}" var="groupData" varStatus="status">
			       		<c:if test="${groupData.deliveryMode.code eq 'free-standard-shipping'}">
     		   		<storepickup:pickupCartItems cartData="${cartData}" groupData="${groupData}" index="${status.index}" showHead="true" />
     		   		 </c:if>
			   </c:forEach>
					
					 
					 
				
				<c:if test="${isGuestUser eq false }">
				 <div class="row">
			   <div class="topBottom-border mixedcart-content-align">
	        <div class="row">
			   
			    <div class="col-xs-12 col-md-4">
					<p class="bold black-title"><spring:theme code="mixcart.shipping.contact"/></p>
					<span class="hidden-md hidden-lg pull-right direction-link">
						<a id="shippingChangeContact" class="mixCartChangeContact" href="#"><spring:theme code="choosePickupDeliveryMethodPage.change.contact" /></a>
					</span>
				</div>
			   
			   <div class="col-xs-6 col-md-5"><span class="shipping-contact-details">
	   				</span></div>
			   
			   <div class="col-md-3 hidden-xs hidden-sm text-right col-xs-6">
		           	 <div class="direction-link">
		           	 	 <a id="shippingChangeContact" class="mixCartChangeContact" href="#"><spring:theme code="choosePickupDeliveryMethodPage.change.contact" /></a>
		           	 </div>
		           	 </div>
			   </div>
			   </div>
			   </div>
				</c:if>
<!-- Shipping add adress -->
<div class="row">
			 <div id="shipping_content" class="topBottom-border mixedcart-content-align page-choosePickupDeliveryMethodPage">
		        
		        	<div class="row">  
						<div class="col-xs-12 hidden-md hidden-lg">
							<p class="marginBottom20"><span class="bold-text black-title"><spring:theme code="mixcart.shipping.to"/></span></p>
						</div> 
			   
	  <c:if test="${isGuestUser eq false }">
	  
		         <div class="col-xs-12 col-md-8 float-div">
		        	<label for="deliveryAddress" class="location-select-label"><strong> <spring:theme code="choosePickupDeliveryMethodPage.delivery.location" /></strong></label><br>
			        	<form:hidden path="shippingContactId" id="shippingContactId" value="${siteOneOrderTypeForm.shippingContactId}" />
			        	<form:hidden path="shippingAddressId" id="shippingAddressId" data-mode="shipping" value="${siteOneOrderTypeForm.addressId}" />
			        	<form:errors path="shippingAddressId"/>
			        	<select id="MixedcartShippingAddress" data-mode="shipping" placeholder="select">
			        		<option value="selectDefault"  disabled><spring:theme code="choosePickupDeliveryMethodPage.select.delivery.location" /></option>
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
				      	<span class="shippingError" id="errorShippingAddressRadio"></span>
			     		<div class="shipping-address marginTop10"></div>
			     		<div class="shipping-region "></div>
			     		<div class="shipping-phone"></div>
			     		<sec:authorize var="isAdmin" access="hasAnyRole('ROLE_B2BADMINGROUP')"/>
                       <c:if test="${isAdmin or enableAddDeliveryAddress}">
			     		<br/>
			     			<div class="newaddress-mixedcart btn btn-block btn-address" data-mode="shipping"><span class="glyphicon glyphicon-plus address-icon"></span> 
			     			<strong><spring:theme code="mixcart.shipping.location" /></strong></div>
			     		</c:if>
			     		
			     		<div id="addAddressSuccess" class="row"></div>
			     	</div>
			     	</c:if>
			     	
			     	
			     	
			     	<c:if test="${isGuestUser eq true }">
					<div class="col-xs-12 col-md-8 float-div">
						<form:form  id="SiteOneGCMainShippingForm" modelAttribute="siteOneOrderTypeForm" action="${homelink}checkout/multi/saveFulfilmentDetails" method="POST">
							    
							  <div id="mixedcart-shipping_Details">
							  <div>
							  <div class="row">
							      			<div class="col-xs-12 col-md-12">
								      			
													<div class="row">
													<div class="col-xs-12 col-sm-6">
													<span id="mixedcart-shipping_fname" ></span>
													<span id="mixedcart-shipping_lname" ></span>
													
													<div id="mixedcart-shipping_phone" ></div>
													
													<div id="mixedcart-shipping_email" ></div>
													</div>
													
													<div class="col-xs-12 col-sm-6">
													
													<div id="mixedcart-shipping_line1" ></div>
													<div id="mixedcart-shipping_line2" ></div>
												
													<span id="mixedcart-shipping_city" ></span>
													
													<span id="mixedcart-shipping_state" ></span>
													<span id="mixedcart-shipping_zipcode" ></span>
													</div>
													</div>
									
							      			</div>
							      			<div class="cl"></div>
							      			</div>
							      			</div>
							   </div>
							      		 	
							  <div id="mixedcart-shipment_div2" class="col-xs-12 no-padding-md">
								
								 <form:hidden path="addressId" id="addressId" value="${siteOneOrderTypeForm.addressId}" />
									<form:hidden path="contactId" value="${siteOneOrderTypeForm.contactId}" />
									<div class="col-xs-12 text-red marginBottom10 same-shipping-state-error florida-error-msg hidden"><spring:theme code="js.briteverify.shippingStates" /></div>
									<div class="col-md-12 padding0">
									<div class="lift-sec">
									<div class="col-md-12 message-center">
									<span class="colored"><input type="checkbox" name="isSameAsContactInfoShipping" class="mixedcart-shipping-check" checked/></span>
									<label><spring:theme code="parcel.shipping.checkbox.label" /></label>
										</div>
										<div class="cl"></div>
									</div>
									<div class="row">
									<div class="col-xs-12 mixedcart-shipping-Newaddress"></div>
							     	<div class="col-xs-12 shipping-Newregion"></div>
							     	
						        	<div class="col-xs-12" id="mixedcart-shippingaddressdata" >${addressData.line1}</div>
									<div class="col-xs-12" id="mixedcart-shippingaddressdat1" >${addressData.line2}</div>
								
									<div class="col-xs-12">
										<span id="mixedcart-shippingcitydata" >${addressData.town}</span>
										<span id="mixedcart-shippingstatedata" >${addressData.district}</span>
										<span id="mixedcart-shippingzipdata" >${addressData.postalCode}</span>
									</div>
									</div>
									</div>
									
							      <div class="cl"></div>
							   
							   
							  			   
									  </div>
									 
									  
									     	
								<div class="cl"></div>
								
									
								</form:form>  
								</div>
						</c:if>
			     	
			     	
			     	
			     	
			     	<div class="col-xs-12 col-md-4 icon-text-sec">
			     	<p class="hidden-xs hidden-sm"><span class="bold-text black-title"><spring:theme code="mixcart.shipping.to"/></span></p>
		        	 
		        
		        </div>
		        
		        
		        <div class="cl"></div>
		          <c:if test="${isGuestUser eq true}">
								<multiCheckout:contactInformationGuestShipping/>
			  	</c:if>
		        
		        
		        
		        
		        </div>
		        </div>
		        </div>
			    <div class="cl"></div>

<!-- Shipping instruction -->

				<div class="row ${mixedcartShippingBoxGC}">
			    <div class="topBottom-border mixedcart-content-align">
			        <div class="row">
			        	<div class="col-md-11 bold black-title ${(isMixedCartEnabled)? 'deliveryandship-instruction' : '' }">
			        	
			        					<c:choose>
											<c:when test="${!((cartData.orderingAccount.tradeClass eq homeOwnerCode) || (isGuestUser eq true)) eq true}">
					        			 		<spring:theme code="checkout.branch.msg" />
					        			 	</c:when>
				        			 	<c:otherwise>
				        			 		<spring:theme code="mixcart.shipping.instructions"/>
				        			 	</c:otherwise>
			        			 	</c:choose>
			        	</div>
			        	<div class="col-md-1 pull-right optional-message-wrapper-mixedcart">
			        	<div id="shipping-storeInstrn">
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
							<form:textarea path="shippingInstruction" id="specialinstruction" class="form-control ${(isMixedCartEnabled)? 'instruction-text-area' : '' }" rows="8" cols="40" maxlength="1000" placeholder="${textplaceholder}"/>
							<c:if test="${!isMixedCartEnabled}">
								<p class="small-text" ><span id="wordcountstaticmessage"><spring:theme code="choosePickupDeliveryMethodPage.text6" /></span></p>
							</c:if>
				        	<p class="small-text" ><span id="remainingwordcount" hidden></span></p>
				        	<span id="errorSpecialinstruction" ></span>
			        	</div>
			        </div>
	        	
			   <div class="col-md-5 col-xs-12 col-sm-6 margin30 btn-margin-bottom">
			   <div class="row">
      			<button type="button" class="btn btn-block btn-primary bold-text submit-ordertype-data submit-shipping-data"><spring:theme code="choosePickupDeliveryMethodPage.continue.to.payment" /></button>
      			</div>
		    </div>
		    </div>
			   </div>
				
			</div>	
				 
</div>				