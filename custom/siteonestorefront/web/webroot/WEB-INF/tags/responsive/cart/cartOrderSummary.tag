<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.CartData" %>
<%@ attribute name="showTax" required="false" type="java.lang.Boolean" %>
<%@ attribute name="cartPriceLoader" required="false" type="java.lang.Boolean" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="quote" tagdir="/WEB-INF/tags/responsive/quote" %>
<c:forEach items="${cartData.entries}" var="entry" varStatus="loop">
	<c:if test="${entry.product.hideList eq true}">
		<c:set var="hideList" value="${entry.product.hideList}"/>
	</c:if>
</c:forEach>
<div class="row cart-order-summary">
<div class="col-xs-12 no-padding-hrz-md font-Geogrotesque-bold cart-order-summary__title bold">
	 <spring:theme code="order.order.totals"/>
</div>

 <div class="col-xs-12 no-padding-hrz-md cart-order-summary__pricePanel">
 <div class="row">
 	 
		
    	<div class="col-xs-7 marginBottom10">
    		<spring:theme code="basket.page.totals.subtotal"/>
    	</div>
		<div class="col-xs-5 marginBottom10 text-align-right">
			<c:choose>
				<c:when test="${cartPriceLoader}">
					<ul class="margin0 padding0 text-center cart-total-loader">
						<li class="loader-circles"></li>
						<li class="loader-circles delay-1s"></li>
						<li class="loader-circles delay-2s"></li>
					</ul>
					<div class="hidden cart-total-loader-show">
						<span class="text-bold js-cart-subtotal black-title b-price add_price"></span>
					</div>
				</c:when>
				<c:otherwise>
					<c:if test="${(hideList ne true || !isAnonymous) && cartData.subTotal.value.toString() ne 0.0}">
						<ycommerce:testId code="Order_Totals_Subtotal"><strong><span class="js-cart-subtotal black-title b-price add_price"><format:price priceData="${cartData.subTotal}" /></span></strong></ycommerce:testId>
					</c:if>
				</c:otherwise>
			</c:choose>
		</div>
		
		 <c:if test="${cartData.totalDiscounts.value > 0}">
		        <div class="col-xs-7 marginBottom10 text-red"><spring:theme code="basket.page.totals.discounts"/></div>
		        <div class="col-xs-5 marginBottom10 text-right text-align-right">
		            <ycommerce:testId code="Order_Totals_Savings"><strong>-<span class="js-cart-total-discount"><format:price priceData="${cartData.totalDiscounts}" /></span></strong></ycommerce:testId>
		        </div>
		</c:if>
	  
	 	<c:if test="${cartData.quoteDiscounts.value > 0}">
	
			<div class="col-xs-7 marginBottom10 text-red"><spring:theme code="basket.page.quote.discounts" /></div>
			<div class="col-xs-5 marginBottom10 text-red text-align-right">
				<ycommerce:testId code="Quote_Totals_Savings"><strong>-<format:price priceData="${cartData.quoteDiscounts}" /></strong></ycommerce:testId>
			</div>
		</c:if>
	  
	  	<c:if test="${(cmsPage.uid ne 'cartPage') and (not empty cartData.deliveryCost)}"> 
		   
		        <div class="col-xs-7 marginBottom10"><spring:theme code="basket.page.totals.delivery"/></div>
		        <div class="col-xs-5 marginBottom10 text-align-right"><strong><format:price priceData="${cartData.deliveryCost}" displayFreeForZero="TRUE" /></strong></div>
		   
     	</c:if>
	  
</div>
        
 </div>
</div>

<%-- <c:if test="${not empty cartData.quoteData}">
		<quote:quoteDiscounts cartData="${cartData}"/>
	</c:if> --%>
