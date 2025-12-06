<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<jsp:useBean id="date" class="java.util.Date" />
<fmt:formatDate var="currentDate" value="${date}" pattern="yyyy-mm-dd" />
<fmt:formatDate var="promoEndDate" value="${component.promotion.endDate}" pattern="yyyy-mm-dd"/>

<li class="hidden-xs hidden-sm">
<a href="#" class="promotionName"
	data-url="${component.promotion.image.url}"
	data-notes="${component.promotion.notes}"
	data-promo-code="${component.promotion.code}"
	data-end-date="${promoEndDate}"
	data-description="${component.promotion.description}"
	data-promo-title="${component.promotion.name}"
	data-current-date="${currentDate}"
	data-buttonlabel="${component.buttonLabel}">${component.promotion.name}
</a>
<input type="hidden" class="listOfCoupons" value='${component.promotion.rule.ruleParameters}' />
</li>
 

 
 <div class="carousel-image hidden-md hidden-lg">
<img src="${component.promotion.image.url}">
 
<a href="" class="mobilePromotionName" data-mobile-url="${component.promotion.image.url}" data-mobile-notes="${component.promotion.notes}" 
	data-mobile-promo-code="${component.promotion.code}" data-mobile-description="${component.promotion.description}" data-mobile-promo-title="${component.promotion.name}">${component.promotion.name}</a>
	<c:if test="${not empty component.promotion.image.url}">
		<button class="btn btn-primary promo-detail-btn"> ${component.buttonLabel}</button>
	</c:if>
<input type="hidden" class="listOfCoupons" value='${component.promotion.rule.ruleParameters}'/>
</div>
  

 