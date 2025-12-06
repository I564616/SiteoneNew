<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.CartData" %>
<%@ attribute name="showDeliveryAddress" required="true" type="java.lang.Boolean" %>
<%@ attribute name="showTax" required="false" type="java.lang.Boolean" %>
<%@ attribute name="showTaxEstimate" required="false" type="java.lang.Boolean" %>
<%@ attribute name="fromPage" required="false" type="java.lang.String" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="multi-checkout" tagdir="/WEB-INF/tags/responsive/checkout/multi" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>

<%@ attribute name="splitProductType" required="false" type="java.lang.String" %>
<spring:url value="/checkout/multi/summary/placeOrder" var="placeOrderUrl"/>


<ul class="item__list">
        <multi-checkout:orderReviewCartEntries cartData="${cartData}" splitProductType="${splitProductType}"/>
</ul>

<c:if test="${not empty cartData.appliedOrderPromotions}">
    <div class="cartproline hidden-xs hidden-sm hidden-md hidden-lg">
        <spring:theme code="basket.received.promotions" />
        <ycommerce:testId code="cart_recievedPromotions_labels">
            <c:forEach items="${cartData.appliedOrderPromotions}" var="promotion">
                <div class="promotion">${promotion.description}</div>
            </c:forEach>
        </ycommerce:testId>
    </div>
</c:if>

<%-- <multi-checkout:orderTotals cartData="${cartData}" showTaxEstimate="${showTaxEstimate}" showTax="${showTax}" /> --%>
