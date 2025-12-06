<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:url value="/cart" var="cartUrl" />

<c:set var="textPos" value="left" scope="request"/>
<c:if test="${not showModifications}">
	<c:set var="textPos" value="center" scope="request"/>
</c:if>

<c:if test="${not empty restorationErrorMsg}">
	<div class="alert negative">
		<spring:theme code="basket.restoration.${restorationErrorMsg}" />
	</div>
</c:if>
<c:if
	test="${not empty restorationData and not empty restorationData.modifications}">
	<c:if test="${cmsPage.uid == 'choosePickupDeliveryMethodPage'}">
		<div id="pricechangemessage"><div class="alert alert-box alert-dismissable edit-user-alert"> <div class="alert-msg-text"><spring:theme code="checkout.orderreview.pricechanges"/></div></div></div>
	 </c:if>	
	 	<div class="cart-restoration-bar hidden-print">
			<div class="text-${textPos}">
				<spring:theme code="basket.restoration"/>
				<c:choose>
					<c:when test="${not showModifications}">
						<div class="row">
							<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 col-xs-offset-3 col-sm-offset-4 col-md-offset-4 col-lg-offset-5">
								<%-- <br />
								<button class="btn btn-default btn-block cartRestoration" data-cart-url="${cartUrl}"><spring:theme text="View Cart" code="basket.restoration.view.cart.btn"/></button> --%>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<%-- <br /><br />
						<spring:theme code="basket.restoration.items.msg"/>
						<br /> --%>
						<c:forEach items="${restorationData.modifications}"
							var="modification">
							
							<c:url value="${modification.entry.product.url}" var="entryUrl" />
							<c:choose>
								<c:when
									test="${modification.deliveryModeChanged and not empty modification.entry}">
									<div>
										<spring:theme code="basket.restoration.delivery.changed"
										arguments="${fn:escapeXml(modification.entry.product.name)},${entryUrl},${modification.entry.quantity},${modification.quantityAdded}" />
									</div>
								</c:when>
								<c:when
									test="${modification.deliveryModeChanged and empty modification.entry}">
									<div>
										<spring:theme code="basket.restoration.delivery.changed"
										arguments="${fn:escapeXml(modification.entry.product.name)},${entryUrl},${modification.quantity},${modification.quantityAdded}" />
									</div>
								</c:when>
								<c:when test="${not modification.deliveryModeChanged and not empty modification.entry}">
								<div>
									<spring:theme
										code="basket.restoration.${modification.statusCode}"
										arguments="${fn:escapeXml(modification.entry.product.name)},${entryUrl},${modification.entry.quantity},${modification.quantityAdded}" />
								</div>
								</c:when>
								<c:when test="${not modification.deliveryModeChanged and empty modification.entry}">
								<div>
									<spring:theme
										code="basket.restoration.${modification.statusCode}"
										arguments="${fn:escapeXml(modification.entry.product.name)},${entryUrl},${modification.quantity},${modification.quantityAdded}" />
								</div>
								</c:when>
							</c:choose>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
</c:if>
