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
<c:choose>
	<c:when test="${cartData.orderingAccount.isPONumberRequired}">
		<p class="bold-text text-default"><spring:theme code="PO.required.text" />:</p>
		<form:input type="text" onkeypress="ACC.checkout.poValueCheckonKeyPress(event)" oninput="ACC.checkout.poValueCheck(event)" path="PurchaseOrderNumber" class="form-control" id="poNumberReq" maxlength="30" />
			<span id="errorPONumberRequired"></span>
			<form:errors path="PurchaseOrderNumber"/>
			<div class="splitPurchaseOrderNumber hidden"></div>
	</c:when>
	<c:otherwise>
		<p class="bold-text text-default"><spring:theme code="choosePickupDeliveryMethodPage.po.num" /></p>
		<spring:message code="optional.text" var="Poplaceholder"/>
		<form:input type="text" onkeypress="ACC.checkout.poValueCheckonKeyPress(event)" oninput="ACC.checkout.poValueCheck(event)" class="form-control" path="PurchaseOrderNumber" maxlength="30" placeholder="${Poplaceholder}"/>
		<div class="splitPurchaseOrderNumber hidden"></div>
	</c:otherwise>
</c:choose> 
	