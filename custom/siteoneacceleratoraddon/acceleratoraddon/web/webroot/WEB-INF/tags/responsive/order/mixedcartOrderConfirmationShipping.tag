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
					<spring:theme code="orderunconsignedentries.ShippingDetails.mixedcart.title" />
				</div>
			</div>
			</div>
	  		 <div class="border-grey white-bg  marginbottomp30"> 
	<div>
	   <div class="deliveryInformations margin-top-20">
		   <!--orderUnconsignedEntriesConfirmation.tag  -->
			   <div class="col-xs-12">
				   <div class="col-md-3 col-sm-12 no-padding-xs">
					   <div class="message-header bold">
						  <spring:theme code="orderSummaryPage.parcel.shipping.to.contact.mixedcart"/>
					   </div>
					   <div class="delivery-location-details">

						   <c:choose>
							   <c:when test="${not empty order.guestShippingContactPerson && empty order.shippingContactPerson}">
								   <div>${order.guestShippingContactPerson.name}</div>
								   <c:choose>
									   <c:when test="${not empty order.guestShippingContactPerson.contactNumber}">
										   <a class="tel-phone" href="tel:${order.guestShippingContactPerson.contactNumber}">${order.guestShippingContactPerson.contactNumber}</a><br>
									   </c:when>
									   <c:otherwise>
										   <spring:theme code="orderSummaryPage.na" /><br>
									   </c:otherwise>
								   </c:choose>
								   ${order.guestShippingContactPerson.email} <br>
							   </c:when>
							   <c:otherwise>
								   ${order.shippingContactPerson.name}<br>
								   <c:choose>
									   <c:when test="${not empty order.shippingContactPerson.contactNumber}">
										   <a class="tel-phone" href="tel:${order.shippingContactPerson.contactNumber}">${order.shippingContactPerson.contactNumber}</a><br>
									   </c:when>
									   <c:otherwise>
										   <spring:theme code="orderSummaryPage.na" /><br>
									   </c:otherwise>
								   </c:choose>
								   ${order.shippingContactPerson.email}<br>
								   <%--PARCEL_SHIPPING ${cartData.contactPerson.email}<br> --%>
							   </c:otherwise>
						   </c:choose>
					   </div>
				   </div>
				   <div class="hidden-lg hidden-md cl marginTop20"></div>
				   <div class="col-md-3 col-sm-12 no-padding-xs">
				   <div class="message-header bold">
						   <spring:theme code="orderSummaryPage.parcel.shipping.to.contact"/>
					   </div>
					   <div>  ${order.shippingAddress.line1},<br>
						   <c:if test="${not empty order.shippingAddress.line2}">
							   ${order.shippingAddress.line2},<br>
						   </c:if>
							   ${order.shippingAddress.town},&nbsp;
							   ${order.shippingAddress.region.isocodeShort}&nbsp;
							   ${order.shippingAddress.postalCode}<br>
					   </div>

				   </div>
				   <div class="hidden-lg hidden-md cl marginTop20"></div>
				    
				    
				   <div class="col-md-6 col-sm-12">
					   <div class="row">
						   <c:choose>
							   <c:when  test = "${cmsPage.uid eq 'orderConfirmationPage'}">
								   <div class="col-md-12 col-xs-12 no-padding-xs"><p class="message-header bold">
								  <spring:theme code="orderunconsignedentries.shippingUpInstruction.mixedcart"/>
								   </p></div>
							   </c:when>
							   <c:otherwise>
								   <div class="col-md-12 col-xs-12 no-padding-xs"><p class="message-header additionalInfo bold"><spring:theme code="orderSummaryPage.msg.branch" />:</p></div>
							   </c:otherwise>
						   </c:choose>
						   <div class="col-md-12 col-xs-12 no-padding-xs">
							   <p class="order-message">
								   <c:choose>
									   <c:when test="${not empty order.shippingInstruction}">
										   ${order.shippingInstruction}
									   </c:when>
									   <c:otherwise>NA</c:otherwise>
								   </c:choose>
							   </p>
						   </div>
						   
					   </div>
				   </div>
				   <div class="cl"></div>
						   <div class="marginTopBVottom30">
						   <div class="col-md-12 info-mesg-wrapper-shipping"><spring:theme code="orderSummaryPage.parcel.shipping.message.mixedcart" /></div>
						   <div class="cl"></div>
						   </div>
			   </div>
		    
	   </div>
	 
	    <div class="cl"></div>
	</div>
	<div class="cl"></div>
	<div id="shipping-box">
		<c:forEach items="${order.deliveryModeAndBranchOrderGroups}" var="groupData" varStatus="loop">	
					<c:if test="${groupData.deliveryMode.code eq 'free-standard-shipping'}" >
						<c:forEach items="${groupData.entries}" var="entry">
							<div class="product-section">
										<order:orderConfirmationDetails orderEntry="${entry}" order="${order}" itemIndex="${loop.index}"/>
										<div class="cl"></div>
							</div>
						</c:forEach>
					</c:if>
				</c:forEach>
				</div>
<div class="cl"></div>
 </div>
 
