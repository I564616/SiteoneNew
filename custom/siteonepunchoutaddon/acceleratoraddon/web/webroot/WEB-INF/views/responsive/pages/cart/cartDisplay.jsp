<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="addoncart" tagdir="/WEB-INF/tags/addons/siteonepunchoutaddon/responsive/cart" %>

<spring:htmlEscape defaultHtmlEscape="true" />

<c:if test="${not empty cartData.entries}">
    <c:url value="/punchout/cxml/requisition" var="requisitionUrl"/>
    <c:url value="/punchout/cxml/cancel" var="cancelUrl"/>
    <c:url value="${continueUrl}" var="continueShoppingUrl" scope="session"/>
    
    <c:set var="showTax" value="false"/>
    <div class="js-cart-top-totals cart-top-totalsleft col-md-7 col-xs-12">
        <c:choose>
            <c:when test="${fn:length(cartData.entries) > 1}">
                <spring:theme code="basket.page.totals.total.items" arguments="${fn:length(cartData.entries)}" />
            </c:when>
            <c:otherwise>
                <spring:theme code="basket.page.totals.total.items.one" arguments="${fn:length(cartData.entries)}" />
            </c:otherwise>
        </c:choose>
    </div>
    <div class="cl"></div>
     <c:if test="${isInspectOperation ne true}"> 
	<div class="col-md-3 pull-left-ShoppingLink shopping-link">
		 
			<a href="${continueShoppingUrl}">&#8592; <spring:theme
					code="cart.page.continue" />
			</a>
		 
	</div>
	</c:if>
    <div class="cart__actions col-md-5 col-xs-12  col-sm-6 pull-right">
        <div class="row">
            <c:choose>
                <c:when test="${isInspectOperation}">
                    <div class="col-sm-5 col-md-3 col-sm-push-7 col-md-push-9">
                        <addoncart:returnButton url="${requisitionUrl}" />
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="col-sm-5 col-md-4 col-md-offset-1">
                        <addoncart:cancelButton url="${cancelUrl}" />
                    </div>
                    <div class="col-sm-7 col-md-7">
                        <addoncart:returnButton url="${requisitionUrl}" />
                    </div>
                </c:otherwise>
            </c:choose>
            </div>
            </div>
<div class="cl"></div>
	<c:choose>
		<c:when test="${isInspectOperation}">
			<addoncart:cartItems cartData="${cartData}"/>
		</c:when>
		<c:otherwise>
	    	<cart:cartItems cartData="${cartData}"/>
	    </c:otherwise>
    </c:choose>

</c:if>
<cart:ajaxCartTopTotalSection/>
