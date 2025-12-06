<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<c:if test="${entry.product.inStockImage && !entry.product.stockAvailableOnlyHubStore}">
       <div class="message-center atc-mesg-font ">
	        <c:choose>
	        	<c:when test="${entry.product.isStockInNearbyBranch}">
	        		 <span class="hidden-xs hidden-sm"><common:checkmarkIcon iconColor="#ef8700"/></span>
	        		 <span class="hidden-md hidden-lg"><common:checkmarkIcon width="25" height="25" iconColor="#ef8700"/></span>
	        	</c:when>
	        	<c:otherwise>
	        		 <span class="hidden-xs hidden-sm"><common:checkmarkIcon iconColor="#78a22f"/></span>
	        		 <span class="hidden-md hidden-lg"><common:checkmarkIcon width="25" height="25" iconColor="#78a22f"/></span>
	        	</c:otherwise>
	    
	     	</c:choose>
    		<p class=""><spring:theme text="${entry.product.storeStockAvailabilityMsg}" arguments="${contactNo}"/></p>
	</div>
</c:if>
<c:if test="${entry.product.notInStockImage && !entry.product.stockAvailableOnlyHubStore}">
	<div class="message-center atc-mesg-font">
	     <span class="hidden-xs hidden-sm"><common:exclamatoryIcon iconColor="#ef8700"/></span>
	     <span class="hidden-md hidden-lg"><common:exclamatoryIcon width="25" height="25" iconColor="#ef8700"/></span>
	     <p class=""><spring:theme text="${entry.product.storeStockAvailabilityMsg}" arguments="${contactNo}"/></p>
	</div>
</c:if>
<c:if test="${entry.product.outOfStockImage && !entry.product.stockAvailableOnlyHubStore}">
	<div class="message-center atc-mesg-font">
	     <span class="hidden-xs hidden-sm"><common:crossMarkIcon iconColor="#5a5b5d"/></span>
	     <span class="hidden-md hidden-lg"><common:crossMarkIcon width="25" height="25" iconColor="#5a5b5d"/></span>
	     <p class=""><spring:theme text="${entry.product.storeStockAvailabilityMsg}" arguments="${contactNo}"/></p>
	</div>
</c:if>
<c:if test="${entry.product.stockAvailableOnlyHubStore}">
	<div class="message-center atc-mesg-font">
		<span class="hidden-xs hidden-sm"><common:checkmarkIcon iconColor="#78a22f"/></span>
		<span class="hidden-md hidden-lg"><common:checkmarkIcon width="25" height="25" iconColor="#78a22f"/></span>
		<p class=""><spring:theme code="product.instock.shippingonly"/></p>
	</div>
</c:if>