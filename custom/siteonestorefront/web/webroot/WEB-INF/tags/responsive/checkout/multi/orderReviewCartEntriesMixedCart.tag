<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="groupData" required="true" type="de.hybris.platform.commercefacades.order.data.OrderEntryGroupData" %>
<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.CartData" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="grid" tagdir="/WEB-INF/tags/responsive/grid" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="multi-checkout" tagdir="/WEB-INF/tags/responsive/checkout/multi"%>

<jsp:useBean id="list" class="java.util.ArrayList"/>
<jsp:useBean id="list1" class="java.util.ArrayList"/>
<jsp:useBean id="list2" class="java.util.ArrayList"/>

<%-- setting locale to 'en_US' to format price with dot for decimal values and comma as grouping separator  
     reset to current locale in the end of page  --%>
<fmt:setLocale value="en_US" scope="session"/>
<spring:htmlEscape defaultHtmlEscape="true" />
<c:set var="unitpriceDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.unitprice.digits\")%>" />
<c:set var="errorStatus" value="<%= de.hybris.platform.catalog.enums.ProductInfoStatus.valueOf(\"ERROR\") %>" />

<div class="col-xs-12  add-border-radius padding0">
<div class="orderSummaryItems">
	<div class=" hidden-xs hidden-sm hidden-md hidden-lg">
        <div class="col-xs-12 item__list--header sec-title-bar">
            <div class="col-md-6 item__image-title"><spring:theme code="basket.page.product.information"/></div>
            <div class="col-md-2 item__price-title"><spring:theme code="basket.page.price"/></div>
            <div class="col-md-2 item__quantity-title"><spring:theme code="basket.page.qty"/></div>
            <div class="col-md-2 item__total-title"><spring:theme code="basket.page.total"/></div>
        </div>
    </div>
    <div class="item__list item__list__cart">
    <c:if test="${cartData.totalDiscounts.value > 0 and not empty cartData.appliedVouchers}">
    <div class="cl"></div>
	 <div class="applied-promotion-wrapper hide">
	 <c:set var="hideVoucher" value="hide" />
	 <c:forEach items="${cartData.appliedVouchers}" var="voucher" varStatus="loop">
    <c:if test="${not empty showVoucherList}">
   		 <c:forEach items="${showVoucherList}" var="showVoucher" varStatus="loop">
    		<c:if test="${voucher eq showVoucher}">
    			<c:set var="hideVoucher" value="" />
   		 	 </c:if>
    	</c:forEach>
    	</c:if>
        </c:forEach>
        <c:if test="${hideVoucher ne 'hide'}">
		<span><spring:theme code="orderReviewCartEntries.promoCode" /></span>
		</c:if>
		<c:forEach items="${cartData.appliedVouchers}" var="voucher" varStatus="loop">
		
		<c:if test="${not empty showVoucherList}">
		<c:forEach items="${showVoucherList}" var="showVoucher" varStatus="loop">
<c:if test="${voucher eq showVoucher}">
<c:choose>
				<c:when test="${fn:length(cartData.appliedVouchers) gt 1 and !loop.last}">
		   			<span>${voucher},</span>
				</c:when>
				<c:when test="${fn:length(cartData.appliedVouchers) gt 1 and loop.last}">
		   			<span>${voucher} <spring:theme code="orderReviewCartEntries.applied" /></span>
				</c:when>
				<c:otherwise>
					<span>${voucher} <spring:theme code="orderReviewCartEntries.applied" /></span>
				</c:otherwise>
		   </c:choose>
</c:if>
</c:forEach>
</c:if>
			
        </c:forEach>
	</div> 
</c:if> 
     	<c:forEach items="${groupData.entries}" var="entry1" varStatus="loop">
		<c:choose>
		<c:when test="${entry1.product.inStockImage}">
		<c:choose>
			<c:when test="${entry1.product.isStockInNearbyBranch}">
				<c:set var="stockNearbyEnteries" value="${list1.add(entry1)}"/>
			</c:when>
			<c:otherwise>
				<c:set var="stockEnteries" value="${list.add(entry1)}"/>
			</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<c:set var="backorderEnteries" value="${list2.add(entry1)}"/>
		</c:otherwise>
		</c:choose>
		</c:forEach>

<c:if test="${not empty list}">
	<multi-checkout:orderReviewCartEntriesSort entries="${list}" loopIndex="0"/>
</c:if>
<c:if test="${not empty list1}">
	<multi-checkout:orderReviewCartEntriesSort entries="${list1}"  loopIndex="0"/> 
</c:if>
<c:if test="${not empty list2}">
	<multi-checkout:orderReviewCartEntriesSort entries="${list2}"  loopIndex="0"/> 
</c:if>
</div>
</div>
</div>

<%-- <ul class="item__list item__list__cart">
	<c:if test="${cartData.totalDiscounts.value > 0}">
		<div>
			<c:forEach items="${cartData.appliedVouchers}" var="voucher" varStatus="loop">
			Discount Code ${voucher} applied<br>
			</c:forEach>
		</div>
	</c:if>
    <c:forEach items="${cartData.entries}" var="entry" varStatus="loop">
	    <li class="item__list--item">
				 total
	    </li>
   	</c:forEach>
</ul> --%>
 
<fmt:setLocale value="${pageContext.response.locale}" scope="session"/>