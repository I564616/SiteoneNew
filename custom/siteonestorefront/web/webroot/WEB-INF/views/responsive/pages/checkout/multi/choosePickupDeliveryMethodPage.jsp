<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="multi-checkout" tagdir="/WEB-INF/tags/responsive/checkout/multi"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/responsive/store"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<template:page pageTitle="${pageTitle}" hideHeaderLinks="true"  showCheckoutSteps="true">
<spring:url value="/" var="homelink" htmlEscape="false"/>
<input type="hidden" id="currentCartId" name="currentCartId" value="${cartData.b2bCustomerData.currentCartId}">
<input type="hidden" id="recentCartIds" name="recentCartIds" value="${cartData.b2bCustomerData.recentCartIds}">
<input type="hidden" id="isPOValidated" name="isPOValidated" value="true" >
<input id="sessionStore" type="hidden" value="${sessionStore.storeId}"> 
<c:set var="ddcUrl" value="<%=de.hybris.platform.util.Config.getParameter(\"kount.ddc.url\")%>"/>
<c:set var="merchantId" value="<%=de.hybris.platform.util.Config.getParameter(\"payments.fraud.client.id\")%>"/>
<c:set var="subtotalLimit" value="${cartData.deliveryEligibilityThreshold}"/>
<input type="hidden" class="subtotalLimit" value="${subtotalLimit}"/>
<c:set var="homeOwnerCode" value="<%=de.hybris.platform.util.Config.getParameter(\"homeOwner.trade.class.code\")%>"/>
<input type="hidden" class="homeOwnerCode" value="${homeOwnerCode}"/>
<input type="hidden" class="trade-class" value="${cartData.orderingAccount.tradeClass}"/>
<input type="hidden" class="subTotal-class" value="${cartData.subTotal.value}"/>
<div class="global-alerts homeOwner-msg hidden">
<div class="alert alert-info alert-dismissable"><button class="close" type="button" data-dismiss="alert" aria-hidden="true">x</button><spring:theme code="delivery.enable.condition.message"/></div>
</div>

<div class="step-body kaxsdc" data-event="load">
	<br>
	<div class="errorPickupDelivery"></div>
	<div class="alert alert-box alert-dismissable alert-msg-section hidden" id="defaultStoreMessage">
	<store:storeFinderOverlay />
		
	</div>
	<div id="addAddressSuccess"></div>
	<div class="col-sm-8 col-md-4 col-xs-12 pick_delivery_summary ">
	<div class="row row pdp_whitebox col-md-12 panel-title ">
     <div class="col-xs-12 col-md-12 col-sm-12 border-top padding0 ">
	                            <div class="col-xs-8 col-md-8 col-sm-8 data-title black-title panel-title-order">
	                            	<span class="bold "><spring:theme code="checkout.multi.order.summary" /></span>
	                            </div>
	                            <div class="col-md-4 col-sm-4 col-sm-6 col-xs-4 data-data   panel-title-value">
	                                <span class="glyphicon glyphicon-chevron-down"></span> 
	                            </div>
	                        </div>
      
      <br>
      <br>
      <div id="collapseOne data-value-promo" class="collapsed">
      		<div class="col-xs-12 col-md-12 col-sm-12 border-top padding-LeftZero sub-total-summary">
	                            <div class="col-xs-8 col-md-9 col-sm-9 data-title black-title padding0">
	                            	<spring:theme code="order.form.subtotal" />
	                            </div>
	                            <div class="col-md-3  col-sm-3 col-xs-4 confirmation-tax-value text-right bold padding0">
	                                  <ycommerce:testId code="Order_Totals_Subtotal"><strong><format:price priceData="${cartData.subTotal}" /></strong></ycommerce:testId>
	                            </div>
	                        </div>
	                       
	                         <br>
      <br>
	                        <div class="col-xs-12 col-md-12 col-sm-12 border-top padding-LeftZero sale-tax-summary">
	                            <div class="col-xs-8 col-md-9 col-sm-9 data-title black-title padding0">
	                            	<spring:theme code="order.form.Sales" />
	                            </div>
	                          	
	                            <div class="col-md-3  col-sm-3 col-xs-4 confirmation-tax-value text-right bold padding0">
	                                 <strong> <format:price priceData="${cartData.totalTax}"/></strong>
	                            </div>
	                        
	                        </div>
	                         <br>
   
	                        
	                         <div class="col-xs-12 col-md-12 col-sm-12 border-top padding-LeftZero promo-summary ">
	                            <div class="col-xs-8 col-md-9 col-sm-9 data-title black-title padding0 ">
	                            	<spring:theme code="basket.validation.couponNotValid.promotion" />
	                            </div>
	                            <div class="col-md-3 col-sm-3 col-xs-4  confirmation-tax-value text-right bold padding0">
	                                 	<c:choose>
				<c:when test="${not empty cartData.totalDiscounts && cartData.totalDiscounts.value>0}">
					<ycommerce:testId code="orderTotal_discount_label">
                        <strong>-<format:price priceData="${cartData.totalDiscounts}" displayFreeForZero="false" /></strong>
                    </ycommerce:testId>
                 </c:when>
                 <c:otherwise>
                 		$0.00
                 </c:otherwise>
                 </c:choose>
	                            </div>  <br>
	                        </div><div class="cl"></div>
	                         <div class="col-xs-12 col-md-12 col-sm-12 border-top padding-LeftZero total-summary-order">
	                            <div class="col-xs-8 col-md-9 col-sm-9 data-title black-title padding0">
	                            	<strong><spring:theme code="text.quickOrder.page.total" /></strong>
	                            </div>
	                          	
	                            <div class="col-md-3 col-sm-3 col-xs-4 confirmation-tax-value text-right bold padding0">
	                                <strong>
	                                 <ycommerce:testId code="cart_totalPrice_label">
            	
                    	<format:price priceData="${cartData.totalPriceWithTax}" />
                	
        	</ycommerce:testId>
        	</strong>
	                            </div>
	                          </div>
	                     
      		</div>
      		   </div>
      		   <div class="summary-need-help pdp_whitebox col-md-12 col-xs-12 col-sm-12 collapsed">
      		<span class="help-text-center"><strong><spring:theme code="basket.page.needHelp" /></strong></span>
      		<br>
      		<span  class="help-text-center-number" style="font-size: 15px;"><spring:theme code="search.page.no.result" /></span><br>
      		<span class="link-help-order">or 
      		<a href="mailto:customersupport@siteone.com" ><spring:theme code="search.no.results.helpContactEmailId" /></a></span></div>
      		</div>	
      		<div class="choose-payment-container col-md-8 col-xs-12 col-sm-12 padding0">
      		
	<h1 class="pickupOrDelivery"><spring:theme code="choosePickupDeliveryMethodPage.pickup.delivery" /></h1>
	
	<br>
	<p><strong><spring:theme code="choosePickupDeliveryMethodPage.select" />:</strong></p></div>
	<input type="hidden" class="sessionAddressId" value="${pointOfService.address.id}">
	<input class="openingSchedule" type="hidden" value='${ycommerce:contructJSONForOpeningSchedule(pointOfService.openingHours)}'> 
	<input class="allCustomers" type="hidden" value='${ycommerce:contructJSONForCustomers(customers)}'>
	<input class="currentCustomer" type="hidden" value='${ycommerce:contructJSONForCustomer(currentCustomer)}'>
	<input class="deliveryAddresses" type="hidden" value='${ycommerce:contructJSONForaddresses(deliveryAddresses)}'>
	<input id="poNumberRequired" type="hidden" value="${cartData.orderingAccount.isPONumberRequired}">
	<input id="poRegex" type="hidden" value="${cartData.orderingAccount.poRegex}">
	
	<input type="hidden" value="${sessionStore.address.region.isocodeShort}"  id="california_location"/>
	<input type="hidden" value="${pointOfService.address.region.isocodeShort}"  id="pickup_location"/> <c:url value="/checkout/multi/order-type/submit" var="orderSubmit"/>
	
	<c:set var="timeLimit" value="<%=de.hybris.platform.util.Config.getParameter(\"timeLimit.morning.checkout\")%>"/>
	<input type="hidden" value="${timeLimit}" id="timeLimit"/>
	<c:set var="afternoonTimeLimit" value="<%=de.hybris.platform.util.Config.getParameter(\"timeLimit.afternoon.checkout\")%>"/>
	<input type="hidden" value="${afternoonTimeLimit}" id="afternoonTimeLimit"/>
	<form:form id="siteOneOrderTypeForm" modelAttribute="siteOneOrderTypeForm" action="${orderSubmit}" method="POST">
		<%-- <ycommerce:testId code="checkoutStepOne"> --%>
	    <div class="step-body-form col-sm-12 col-md-8 col-xs-12 padding0">
	        <div class="radiobuttons_deliveryselection">
	       		 <span class="delivery-mode">
		        	 <c:forEach items="${orderType}" var="siteOneOrderType">
		                <div class="radio-wrapper">
		                	<div class="colored-radio ">
		                		<form:radiobutton path="orderType" id="${siteOneOrderType.code}" value="${siteOneOrderType.code}"  />
		                	</div>
		                	<label for="${siteOneOrderType.name}">
		                		${siteOneOrderType.name}	
		                	</label>
		                </div>
		            </c:forEach>
		            <br><br><br>
		            <span id="errorOrderTypeRadio"></span>
		         	 <form:errors path="orderType" />
		         	 
			     </span>
	        </div>
	       
	        <c:if test="${!(empty backorderEligibleProducts) ||  !(empty nearbyProducts) }">
	        	<div class="checkout-product-error">
			       	<c:if test="${!(empty backorderEligibleProducts)}">
			        <div class="checkout-product-error-section">	        
			        <div class="message-center">
			        <common:exclamatoryIcon iconColor="#ed8606"/>
			        <p><spring:theme code="text.backorder.product.message"/></p>
			        </div>
			          <c:forEach var="item" items= "${backorderEligibleProducts}" >
			        	<div class="product-detail">
			        		<div class="col-md-1 col-sm-1 col-xs-2 img-thumb padding0">
			        		 
		                            <product:productPrimaryImage product="${item}" format="product" />
		                        
		                    </div>
		                    <div class="col-md-11 col-sm-1 col-xs-10 error-msg"> ${item.name} </div>
		                    <div class="cl"></div>
			        		</div>
			        		 </c:forEach>
			        	 <div class="cl"></div>
			        </div>
			        </c:if>	 
	        
	        		<div class="cl"></div> 
	        		                                	      
				    <c:if test="${!(empty nearbyProducts)}">                          	      
			        <div class="checkout-product-error-section">
			        <div class="message-center">
			        <common:checkmarkIcon iconColor="#ed8606"/>
			        <p><spring:theme code="text.nearby.product.message"/></p>
			        </div>
			        <c:forEach var="item" items= "${nearbyProducts}">
			        	<div class="product-detail">
			        		<div class="col-md-1 col-sm-1 col-xs-2 img-thumb padding0">
			        		<product:productPrimaryImage product="${item}" format="product" />
			        		</div>
			        		<div class="col-md-11 col-sm-1 col-xs-10 error-msg"> ${item.name}  </div>
			        	 <div class="cl"></div>
			        	</div>
			       	</c:forEach> 
			        <div class="cl"></div>
			        
			        </div>
			        </c:if>
	          </div>
		   
		    </c:if>
		    
		    
	        <div class="delivery-chargesMsg">
			     <spring:theme code="delivery.charges.apply" /><br>
			     </div>
			   <div class="icon-delivery-charges message-delivery choose-pickup-msgdelivery">
			      <spring:theme code="delivery.charges.notification" /><br>
			   </div>

	        
	         
	        
	        
	        
	        
	        <div class="pickupDelivery-mainContent">
	        <div class=" pickup-delivery-table">
	        <div class="col-sm-12 col-xs-12 pickup-delivery ">
		        <div id="pickup" class="pickup--content col-sm-6 padding0" >
		       		<form:hidden path="storeId" value="${sessionStore.storeId}" />
		         		<span class="pickup-store">
		         				<b> <spring:theme code="choosePickupDeliveryMethodPage.pickup.location" />:</b><br><br>
		         				<span class="pickup-store-detail">
		           			<strong>${pointOfService.name}</strong><br><strong><c:if test="${not empty pointOfService.title}">${pointOfService.title}<br></c:if></strong>${pointOfService.address.line1}<br>${pointOfService.address.town},&nbsp;${pointOfService.address.region.isocodeShort}&nbsp;${pointOfService.address.postalCode}</span><br><a class="tel-phone" href="tel:${pointOfService.address.phone}">${pointOfService.address.phone}</a><br><br>
		           			<a id="getDirection" href="" data-url="${pointOfService.address.line1},${pointOfService.address.line2},${pointOfService.address.town},${pointOfService.address.region.isocodeShort},${pointOfService.address.postalCode}"><spring:theme code="choosePickupDeliveryMethodPage.get.direction" /> &#8594;</a><br>
		           	 </span> 
		        </div>
		         <div id="pickup-delivery"  class="common-content-pickupDelivery col-sm-6 col-xs-12 padding0">
		        <form:hidden path="contactId" value="${siteOneOrderTypeForm.contactId}" />
	   				<!-- <div class="col-xs-12 col-sm-6 "> -->
	   				<div id="PickUpContact" class="pickup--content"  >
	   					<b> <spring:theme code="choosePickupDeliveryMethodPage.pickup.contact" />:</b><br><br>
	   					
	   				</div>
	   				<div id="deliveryContact" class="delivery--content" >
	   					<br><b> <spring:theme code="choosePickupDeliveryMethodPage.Delivery.contact" />:</b><br>
	   				</div>
	   				<span class="contact-details">
	   				
	   				</span>
	   				<br>
	   			<br>
	   				<br>
	   				<br>
	   				<span>
	   					<a id="deliveryChangeContact" class="delivery--content changeContact" href="#" ><spring:theme code="choosePickupDeliveryMethodPage.change.contact" />&#8594;</a>
	   					<a id="pickUpChangeContact" class="changeContact pickup--content" href="#" ><spring:theme code="choosePickupDeliveryMethodPage.change.contact" />&#8594;</a>
	   				</span>
	  			 <span id="addPhoneMessage" style="display: none;"><br><a class='contact-add-phoneNumber'  data-link='<c:url value='/checkout/multi/order-type/add-phone'/>' href='#' data-cbox-title='<spring:theme code='checkout.addphone.title'/> '><spring:theme code="choosePickupDeliveryMethodPage.add.phone.num" /></a><BR><br><spring:theme code="checkout.addphone.message"/></span>
	  		 		<br>
	  		 		<span id="errorPhoneNo"></span>
	  		        <input type="hidden" id="isContactRequirePhoneNumber"/>
                    <input type="hidden" id="isAddPhoneNumberUsed"/>	  	
                    				
	      			<span class="contactError" id="errorContact"></span>
	      			</div>
	      				<br>
	      			</div>
	      			</div>
	      			<br>
		        <div id="delivery" class="delivery--content row" >
		        	<div class="col-xs-12 col-sm-6 ">
			        	<label for="deliveryAddress"><b> <spring:theme code="choosePickupDeliveryMethodPage.delivery.location" />:</b></label><br>
			        	<form:hidden path="addressId" id="addressId" value="${siteOneOrderTypeForm.addressId}" />
			        	<form:errors path="addressId"/>
			        	<select id="deliveryAddress" placeholder="select">
			        		<option value="selectDefault"><spring:theme code="choosePickupDeliveryMethodPage.select.delivery.location" /></option>
				   		  	<c:forEach var="item" items= "${deliveryAddresses}">
				   		  		<c:choose>
				   		  			<c:when test="${not empty siteOneOrderTypeForm.addressId && siteOneOrderTypeForm.addressId != '' && siteOneOrderTypeForm.addressId == item.id}">
				   		  				<option value ="${item.id}" selected><spring:theme code="choosePickupDeliveryMethodPage.select.delivery.address" /> ${item.line1}</option>
				   		  			</c:when>
				   		  			<c:otherwise>
				   		  				<option value ="${item.id}"><spring:theme code="choosePickupDeliveryMethodPage.select.delivery.address" /> ${item.line1}</option>
				   		  			</c:otherwise>
				   		  		</c:choose>
				   		    </c:forEach>
				      	</select>
				      	<br>
				      	<span class="deliveryError" id="errorDeliveryAddressRadio"></span>
			     		<div class="delivery-address"></div>
			     		<div class="delivery-region hidden"></div>
			     		<div class="delivery-phone"></div>
			     		<sec:authorize access="hasAnyRole('ROLE_B2BADMINGROUP')">
			     		<br/>
			     			<div><a href="#" class="newaddress"><span class="glyphicon glyphicon-plus address-icon"></span> <spring:theme code="choosePickupDeliveryMethodPage.add.new.del.location" /></a></div>
			     		</sec:authorize>
			     	</div>
		        </div>
		        <br>
		        <div id="pickup-delivery"  class="common-content-pickupDelivery pickupDelivery_desktop">
		        	
	  				<div class="row">
		        		<div class="col-xs-12 col-sm-7 ">
			       			<div id="pickupinfo" class="pickup--content">
			       				<br><b><spring:theme code="choosePickupDeliveryMethodPage.pickup.info" />:</b>
			       				<div class="dateTimeHeader"><spring:theme code="choosePickupDeliveryMethodPage.request.pickup.date" /></div>
			       			</div>
			       			<div id="deliveryinfo" class="delivery--content" >
			       				<br><b><spring:theme code="choosePickupDeliveryMethodPage.delivery.info" />:</b>
			       				<div class="dateTimeHeader"><spring:theme code="choosePickupDeliveryMethodPage.request.delivery.date" /></div>
			       			</div>
			        		<div class="info-details ">
			        			<div class="date-time-pickupDelivery">
			        				<fmt:formatDate var="fmtDate" value="${siteOneOrderTypeForm.requestedDate}" pattern="MM/dd/YYYY hh:mm:ss a"/>
			        				<form:hidden path="requestedDate" id="requestedDate" value="${fmtDate}"/>
			        				<spring:message code="choosePickupDeliveryMethodPage.select.date" var="dataVar"/>
			        				<span class="input-dateWrapper">
			        					<form:input type="text" path="date" placeholder="${dataVar}" id="date" readonly="true"/>
			        				</span>
			        				
				        			 

		       		              	<span class="requested-time">
			        	              	<c:forEach items="${meridianTime}" var="siteOneMeridianTime">
			        	              	<div class="radio-wrapper">
			                	        	<div class="colored-radio"><form:radiobutton path="requestedMeridian" id="${siteOneMeridianTime.code}" value="${siteOneMeridianTime.code}"  />&nbsp;</div>
			                	        	<label for="${siteOneMeridianTime.code}">
			                	        	<c:choose>
			                	        		<c:when test="${siteOneMeridianTime.code eq 'AM'}">
			                	        			<spring:theme code="choosePickupDeliveryMethodPage.morning" />
			                	        		</c:when>
			                	        		<c:when test="${siteOneMeridianTime.code eq 'PM'}">
			                	        			<spring:theme code="choosePickupDeliveryMethodPage.afternoon" />
			                	        		</c:when>
			                	        		<c:otherwise>
			                	        			${siteOneMeridianTime.code}
			                	        		</c:otherwise>
			                	        	</c:choose>

			                	        	</label>
			                	        </div>
			                          	</c:forEach>
				                  	</span>
			        			</div>
			        			 	<form:errors path="requestedMeridian"/>
				                  	<div class="dateError" id="errorDate"></div>
			        				
				        			<div class="timeError" id="errorMeridianRadio"></div>
			        		</div>
		        		</div>
		        		<div class="col-xs-12 col-sm-5 hidden-xs pickup--content store-instructions">
		        			<br>
		      			 	<b><spring:theme code="choosePickupDeliveryMethodPage.pickup.inst" />:</b><br><br>
		      			 	
		      			 	<ul>
		      			 	<li><spring:theme code="pickup.instructions.point1" /></li><br>
							<li><spring:theme code="pickup.instructions.point2" /></li><br>
							<li><spring:theme code="pickup.instructions.point3" /></li><br>
							<li><spring:theme code="pickup.instructions.point4" /></li><br>
							<li><spring:theme code="pickup.instructions.point5" /></li>
							</ul>
							<br>
							<p class="pick-up-questions bold"><spring:theme code="choosePickupDeliveryMethodPage.text1" /></p>
							<p class="pick-up-questions bold"><spring:theme code="choosePickupDeliveryMethodPage.text2" /></p><br>
						</div>
						<div class="col-xs-12 col-sm-5 hidden-xs delivery--content store-instructions">
							<br>
		      			 	<b><spring:theme code="choosePickupDeliveryMethodPage.delivery.inst" />:</b><br><br>
							<spring:theme code="delivery.instrucions.point1" /><br>
							<br><br><spring:theme code="choosePickupDeliveryMethodPage.questions" /><br>
							<a href="<c:url value="/contactus"/>"><spring:theme code="choosePickupDeliveryMethodPage.contact.us" />&#8594;</a>
						</div>
						<div class="col-xs-12 col-sm-6 optional-message-wrapper">
								<div id="storeInstrn" class="common-content-pickupDelivery" >
			        			 	<br><label for="specialinstruction"><spring:theme code="choosePickupDeliveryMethodPage.msg.branch" />:</label>
			        			 	<a class=" pull-right glyphicon glyphicon-info-sign visible-xs visible-sm visible-md visible-lg" style="font-size:18pt;">
			        			 		<span>
			        			 			<div id="termsAndConditions-overlayText" >
									      		<p>
									      			<spring:theme code="choosePickupDeliveryMethodPage.text3" />
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
							<form:textarea path="specialInstruction" id="specialinstruction" rows="0" cols="40" maxlength="1000"/>
							<p class="small-text" ><span id="wordcountstaticmessage"><spring:theme code="choosePickupDeliveryMethodPage.text6" /></span></p>
				        	<p class="small-text" ><span id="remainingwordcount" hidden></span></p>
				        	<span id="errorSpecialinstruction" ></span>
			        	</div>
					</div>
		       	</div>
		   	</div>
		   	
		   	<div class="marginTopBVottom20 common-content-pickupDelivery col-md-6">
		   	<div class="row">
	      		  <c:choose>
		   	<c:when test="${cartData.orderingAccount.isPONumberRequired}">
			  <b>PO Number (Required):</b>
	      		<br>
	      		<form:input type="text" path="PurchaseOrderNumber" class="form-control" id="poNumberReq" maxlength="30"/>
	      		<br>
	      		<span id="errorPONumberRequired"></span>
	      		<form:errors path="PurchaseOrderNumber"/>
	      		</c:when>
	      		<c:otherwise>
	      		  <label for="PurchaseOrderNumber"><b><spring:theme code="choosePickupDeliveryMethodPage.po.num" /></b></label>
	      		<br>
	      		<form:input type="text" class="form-control" path="PurchaseOrderNumber" maxlength="30" />
	      		 </c:otherwise>
	      		</c:choose> 
      		</div> 
      		</div>
      		</div> 
      		<div class="cl"></div>
      		<div class="termsAndConditions-div common-content-pickupDelivery">
	      		<label>
	      		<input type="checkbox" name="termsAndConditions"  id="termsAndConditions">
	      		<span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
	      		<spring:theme code="choosePickupDeliveryMethodPage.text7" /> <a id="termsAndConditionsPopup" href="#" ><u><spring:theme code="choosePickupDeliveryMethodPage.text8" /></u></a>.
	      		<span class="termsCheckError" id="errorTermsAndConditionsCheckBox"></span>
	      		</label>
      		</div>
      		<div class="payment_cbtn ">
		    	<button type="button" class="btn btn-primary orderTypeFormSubmit col-md-2 col-xs-12 col-sm-2"><spring:theme code="choosePickupDeliveryMethodPage.continue.to.payment" /></button>
		    </div>
		    
		    <div class="col-xs-12 col-sm-5 hidden-sm hidden-md hidden-lg pickup--content store-instructions">
		        			<br>
		      			 	<br>
		      			 	<b><spring:theme code="choosePickupDeliveryMethodPage.pickup.inst" />:</b><br><br>
		      			 	<ul>
		      			 	<li><spring:theme code="pickup.instructions.point1" /></li><br>
							<li><spring:theme code="pickup.instructions.point2" /></li><br>
							<li><spring:theme code="pickup.instructions.point3" /></li><br>
							<li><spring:theme code="pickup.instructions.point4" /></li><br>
							<li><spring:theme code="pickup.instructions.point5" /></li>
							</ul>

							<br>
							<p class="pick-up-questions bold"><spring:theme code="choosePickupDeliveryMethodPage.text1" /></p>
							<p class="pick-up-questions bold"><spring:theme code="choosePickupDeliveryMethodPage.text2" /></p><br>
						</div>
						<div class="col-xs-12 col-sm-5  hidden-sm hidden-md hidden-lg delivery--content store-instructions">
							<br>
		      			 	<b><spring:theme code="choosePickupDeliveryMethodPage.delivery.inst" />:</b><br><br>
							<spring:theme code="delivery.instrucions.point1" /><br>
							<br><br><spring:theme code="choosePickupDeliveryMethodPage.questions" /><br>
							<a href="<c:url value="/contactus"/>"><spring:theme code="choosePickupDeliveryMethodPage.contact.us" />&#8594;</a>
						</div>
		   
		    <!-- <p class="checkoutError small-text gray-italic"></p> -->
		    <form:hidden id="kountSessionId" path="kountSessionId" value="" />
		</div>
		<%-- </ycommerce:testId> --%>
	</form:form>
	<sec:authorize access="hasAnyRole('ROLE_B2BADMINGROUP')">
	<div id="addresscheckout" style="display:none">
	<form:form  id="siteOneAddressForm" modelAttribute="siteOneAddressForm" action="${homelink}checkout/multi/order-type/add-address" method="POST">
			     	
			     		<div class="row">
			     		<div class="col-md-12"><span class="addAddressError" ></span></div>
			     		<div class="col-md-12">
			     		<form:hidden path="addressId" class="add_edit_delivery_address_id"/>
			     		<formElement:formInputBox idKey="checkoutAddress.companyName" labelKey="text.address.companyName"  path="companyName"  inputCSS="form-control" mandatory="true" />
						<span class="errorCompanyName"></span> </div>
						<div class="cl"></div>
				
			     		<div class="col-md-12">
			     		<formElement:formInputBox idKey="checkoutAddress.line1" labelKey="text.address.line1" path="line1" inputCSS="form-control" mandatory="true" />
						<span class="errorline1"></span></div>
			     		<div class="cl"></div>
			    	     		
			     		<div class="col-md-12">
			     		<formElement:formInputBox idKey="checkoutAddress.line2" labelKey="text.address.line2" path="line2" inputCSS="form-control" mandatory="false"/>
			     		</div>
			     		<div class="cl"></div>
			     		
			     		<div class="col-md-12">
			     		<formElement:formInputBox idKey="checkoutAddress.townCity" labelKey="address.townCity" path="townCity" inputCSS="form-control" mandatory="true" />
						<span class="errorTownCity"></span></div>
			     		<div class="cl"></div>

			     		<div class="col-md-12">
	  		 			<formElement:formSelectBox idKey="checkoutAddress.region" labelKey="address.state" path="regionIso" mandatory="true" skipBlank="false" skipBlankMessageKey="Select" items="${regions}" itemValue="${useShortRegionIso ? 'isocodeShort' : 'isocode'}" selectedValue="${addressForm.regionIso}" selectCSSClass="form-control"/>
						<span class="errorRegion"></span></div>
			     		<div class="cl"></div>

			     		<div class="col-md-12">
  						<formElement:formInputBox idKey="checkoutAddress.postcode" labelKey="address.zipcode" path="postcode" inputCSS="form-control" mandatory="true" />
						<span class="errorPostCode"></span></div>
			     		<div class="cl"></div>
			     					     		
			     		<div class="col-md-12">
			     		<formElement:formInputBox idKey="checkoutAddress.phone" labelKey="address.phone.name2" path="phone" inputCSS="form-control" mandatory="false"/>
			     		<span class="errorPhoneNumber"></span></div>
			     		<div class="cl"></div>
			     		<form:hidden id="checkoutAddress.countryIso" path="countryIso"  value="US"/>
			     		<form:hidden id="checkoutAddress.district" path="district"  />
			     		<div class="col-md-12 colored"><formElement:formCheckbox idKey="saveAddressInMyAddressBook"
							labelKey="checkout.summary.deliveryAddress.saveAddressInMyAddressBook.name2"
							path="saveInAddressBook" inputCSS="add-address-left-input"
							labelCSS="add-address-left-label" mandatory="true" /></div>
			     	
						<div class="col-md-12 colored"><formElement:formCheckbox idKey="defaultAddressInMyAddressBook"
							labelKey="checkout.summary.deliveryAddress.saveAddressInMyAddressBook.name3"
							path="defaultAddress" inputCSS="add-address-left-input"
							labelCSS="add-address-left-label" />
						</div>

			     		<div class="cl"></div>

			     		<div class="cl"></div>
			     		<div class="col-md-12"><button type="button" class="btn btn-primary addAddressFormSubmit"> <spring:theme code="choosePickupDeliveryMethodPage.add.location" /></button></div>
			     		</div>
			     				   
			     		
			     		
		     		

</form:form>
</div>
	 </sec:authorize>
	 </div>
	
<script type='text/javascript' src='${ddcUrl}${merchantId}'> </script>
	<script type='text/javascript'>
	$(document).ready(function () {
	if (!sessionStorage.MercSessId)
	{
        var client=new ka.ClientSDK();
        
     	// The auto load looks for an element with the 'kaxsdc' class and
        // data-event equal to a DOM event (load in this case). Data collection begins
        // when that event fires on that element--immediately in this example
        client.autoLoadEvents();
 
       // OPTIONAL
        client.setupCallback(
            {
                // fires when collection has finished - this example would not enable the 
                // login button until collection has completed
                'collect-end':
                    function(params) 
                    {
                		debugger
                		if (typeof(Storage) !== "undefined") 
                		{
                			sessionStorage.MercSessId = params.MercSessId;
                			$("#kountSessionId").val(sessionStorage.MercSessId);
              			}
                	}
            });
        // END OPTIONAL SECTION
        // The auto load looks for an element with the 'kaxsdc' class and
        // data-event equal to a DOM event (load in this case). Data collection begins
        // when that event fires on that element--immediately in this example
        client.autoLoadEvents();
	}
	$("#kountSessionId").val(sessionStorage.MercSessId);
    });
    </script> 
    
</template:page>
