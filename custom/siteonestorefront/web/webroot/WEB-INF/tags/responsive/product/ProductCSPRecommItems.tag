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
	       <c:forEach items="${product.sellableUoms}" var="sellableUom" begin="0" end="0">
		    <c:set var="uomDescription" value="${sellableUom.inventoryUOMDesc}"/>
		    <c:set var="uomMeasure" value="${sellableUom.measure}"/>
	      </c:forEach>
	      <c:forEach items="${product.sellableUoms}" var="sellableUom2" begin="0" end="0">
			<c:if test="${product.hideUom eq true && sellableUom2.hideUOMOnline eq false}">
				<c:set var="hideuom2" value="true"/>
				<c:set var="newuomDescription" value="${sellableUom2.inventoryUOMDesc}"/>
				<c:set var="newuomMeasure" value="${sellableUom2.measure}"/>
			</c:if>
		</c:forEach>
	   </c:if>
		<c:if test="${product.singleUom eq true}">
			<c:set var="singleUom" value="true" />
			<c:set var="uomDescription" value="${product.singleUomDescription}" />
			<c:set var="uomMeasure" value="${product.singleUomMeasure}" />
		</c:if>
		<c:choose>
			<c:when test="${product.multidimensional and (product.priceRange.minPrice.value ne product.priceRange.maxPrice.value)}">
				<span class="csp-price-format"><format:cspprice priceData="${product.priceRange.minPrice}"/> <c:if test="${product.sellableUomsCount eq null}"> - <format:cspprice priceData="${product.priceRange.maxPrice}"/></c:if></span>
			</c:when>
			<c:when test="${product.multidimensional and (product.priceRange.minPrice.value eq product.priceRange.maxPrice.value)}">
				<span class="csp-price-format"><format:cspprice priceData="${product.priceRange.minPrice}"/></span>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${(product.priceRange.minPrice.value ne product.priceRange.maxPrice.value)}">
						<span class="csp-price-format"><format:cspprice priceData="${product.priceRange.minPrice}"/> - <format:cspprice priceData="${product.priceRange.maxPrice}"/></span>
			
					</c:when>
					<c:when test="${not empty uomDescription}">
									<c:choose>
										<c:when test="${hideuom2 eq true}">
											<span class="csp-price-format"><format:cspprice priceData="${product.customerPrice}"/></span><span class="black-title b-price"> / ${newuomMeasure}</span>
										</c:when>
										<c:otherwise>
											<span class="csp-price-format"><format:cspprice priceData="${product.customerPrice}"/></span><span class="black-title b-price"> / ${uomMeasure}</span>
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
