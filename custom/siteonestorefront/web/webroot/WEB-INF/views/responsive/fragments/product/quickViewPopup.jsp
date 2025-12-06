<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>

<c:set var="qtyMinus" value="1" />
<c:url value="${product.url}" var="productUrl" />

<div class="quick-view-popup">
	<div class="col-xs-9 col-xs-push-1 col-sm-6 col-sm-push-0 col-md-4 col-lg-5  image-popup">
		<%-- <product:productImagePanel galleryImages="${galleryImages}" product="${product}"/> --%>
		<product:productPrimaryImage product="${product}" format="product" />
	</div>
	<div class="clearfix hidden-sm hidden-md hidden-lg"></div>
	
	<div class="product-details">
		<div class="name">
			<c:out value="${product.productBrandName}" />
		</div>
		<div class="name">
			<a href="${productUrl}"><c:out value="${product.name}" /></a>
		</div>
		<div class="stock-wrapper clearfix">
		    <product:productNotifications product="${product}"/><br/><br/>
		   
        </div>
        <div class="price">
			 <c:if test="${product.salePrice !=null && product.price != null}">
			 	  <div class="col-md-2 col-sm-4 col-xs-4 prePrice"><del><format:fromPrice priceData="${product.price}" /></del></div><br>
			 	  <div class="col-md-2 col-sm-4 col-xs-4 actualPrice"><p class="price" style="color: red;"> $<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${product.salePrice}"/></p></div><br>
		     </c:if>
		     <c:if test="${product.salePrice ==null}">
		           <format:fromPrice priceData="${product.price}" />
		     </c:if>
		</div>
	</div>
	 <a href="${productUrl}">View Full Details</a>
</div>
	