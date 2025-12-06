<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>

<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>


<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<ycommerce:testId code="searchPage_price_label_${product.code}">
		<c:set var="sellableUomListLen" value="${fn:length(product.sellableUoms)}" />
		<%-- if product is multidimensional with different prices, show range, else, show unique price --%>
		<c:if test="${not empty product.sellableUoms}">
	       <c:forEach items="${product.sellableUoms}" var="sellableUom">
		    <c:set var="uomDescription" value="${sellableUom.inventoryUOMDesc}"/>
		    <c:set var="uomMeasure" value="${sellableUom.measure}"/>
	      </c:forEach>
	   </c:if>
		<c:choose>
			<c:when test="${product.multidimensional and (product.priceRange.minPrice.value ne product.priceRange.maxPrice.value)}">
				<span class="csp-price-format"><format:cspprice priceData="${product.priceRange.minPrice}"/> <c:if test="${product.sellableUomsCount eq null}"> - <format:cspprice priceData="${product.priceRange.maxPrice}"/></span></c:if>
			</c:when>
			<c:when test="${product.multidimensional and (product.priceRange.minPrice.value eq product.priceRange.maxPrice.value)}">
				<span class="csp-price-format"><format:cspprice priceData="${product.priceRange.minPrice}"/></span>
			</c:when>
			<c:otherwise>
			<c:choose>
			<c:when test="${not empty product.sellableUomsCount}">
				<c:choose>
					<c:when test="${(product.priceRange.minPrice.value ne product.priceRange.maxPrice.value)}">
						<span class="csp-price-format"><format:cspprice priceData="${product.priceRange.minPrice}"/> - <format:cspprice priceData="${product.priceRange.maxPrice}"/></span>
			
					</c:when>
					<c:when test="${not empty product.sellableUomDesc}">
						<span class="csp-price-format"><format:cspprice priceData="${product.customerPrice}"/></span><span class="black-title b-price add_price"> / ${product.sellableUomDesc}</span>
					</c:when>
					<c:otherwise>
						<span class="csp-price-format"><format:cspprice priceData="${product.customerPrice}"/><c:if test="${sellableUomListLen==1}"><span class="check_price 5"> / ${uomDescription}</span></c:if></span>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:when test="${not empty product.sellableUomsCount and (cmsPage.uid eq 'compareProductPage')}">
			   <c:choose>
					<c:when test="${not empty uomMeasure}">
						<span class="csp-price-format"><format:cspprice priceData="${product.customerPrice}"/></span><span class="black-title b-price add_price"> / ${uomMeasure}</span>
					</c:when>
					<c:otherwise>
						<span class="csp-price-format"><format:cspprice priceData="${product.customerPrice}"/><c:if test="${sellableUomListLen==1}"><span class="check_price 6"> / ${uomDescription}</span></c:if></span>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
			<span class="csp-price-format"><format:cspprice priceData="${product.customerPrice}"/></span>
			
			</c:otherwise>
			</c:choose>
			</c:otherwise>
		</c:choose>

</ycommerce:testId>