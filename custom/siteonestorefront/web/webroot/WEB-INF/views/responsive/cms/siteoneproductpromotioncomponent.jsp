<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<div class="promotion-sec">
<c:forEach var="promotion" items="${component.promotionList}" varStatus="loopStatus">
	<div class="col-md-3 col-xs-12 promotion-box">
		<div class="carousel-image text-center">
		<img alt="" src="${promotion.image.url}">
		</div>
		
		<div class="promotion-info">
			<p class="headline2">${promotion.discountValue}</p>
			<p class="bold-text promotion-notes">${promotion.notes}</p>
			<c:forEach var="products" items="${promotion.productsList}" varStatus="loopStatus" >
			<c:url value="/p/${products.code}" var="productUrl"/>
			<a class="promoproduct" href="${productUrl}" title="${products.code}">${products.itemNumber}</a>
			<c:if test="${!loopStatus.last}">| </c:if>	
			</c:forEach>
			<p>${promotion.maxValue}</p>
			
			<div class="cl"></div>
		</div> 
	
	<div class="cl"></div>
	</div>
</c:forEach>
<div class="cl"></div>
</div>