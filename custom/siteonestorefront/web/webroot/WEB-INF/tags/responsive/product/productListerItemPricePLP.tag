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
		<input type="hidden" name="product.sellableUomsCount" value="${product.sellableUomsCount}" />
		<input type="hidden" name="product.sellableUomDesc" value="${product.sellableUomDesc}" />
		<%-- if product is multidimensional with different prices, show range, else, show unique price --%>
		<c:if test="${not empty product.sellableUoms}">
	       <c:forEach items="${product.sellableUoms}" var="sellableUom" begin="0" end="0">
			<input type="hidden" name="sellableUom.inventoryUOMDesc" value="${sellableUom.inventoryUOMDesc}" />
			<input type="hidden" name="sellableUom.measure" value="${sellableUom.measure}" />
			<input type="hidden" name="sellableUom.code" value="${sellableUom.code}" />
		    <c:set var="uomDescription" value="${sellableUom.inventoryUOMDesc}"/>
		    <c:set var="uomMeasure" value="${sellableUom.measure}"/>
	      </c:forEach>
	   </c:if>
	   <c:forEach items="${product.sellableUoms}" var="sellableUom" begin="0" end="0">
		<c:set var="MulUomValue" value="${sellableUom.code}" />						
	</c:forEach>
		<c:choose>
			<c:when test="${product.multidimensional and (not empty product.priceRange.minPrice) and (not empty product.priceRange.maxPrice) and (product.priceRange.minPrice.value ne product.priceRange.maxPrice.value)}">
				<span id="pricing1" class="list-price-format"><format:price priceData="${product.priceRange.minPrice}"/> <c:if test="${product.sellableUomsCount eq null}"> - <format:price priceData="${product.priceRange.maxPrice}"/></c:if></span>
			</c:when>
			<c:when test="${product.multidimensional and (not empty product.priceRange.minPrice) and (not empty product.priceRange.maxPrice) and (product.priceRange.minPrice.value eq product.priceRange.maxPrice.value)}">
				<c:choose>
					<c:when test="${product.variantCount eq 1}">
						<input type="hidden" name="product.priceRange.minPrice.value" value="${product.priceRange.minPrice.value}" />
						<input type="hidden" name="product.priceRange.maxPrice.value" value="${product.priceRange.maxPrice.value}" />
						<span id="pricing2-1" class="list-price-format"><format:price priceData="${product.priceRange.minPrice}"/><c:if test="${sellableUomListLen==1}"><br><sup>${uomDescription}</sup></c:if></span>
					</c:when>
					<c:otherwise>
						<input type="hidden" name="product.priceRange.minPrice.value" value="${product.priceRange.minPrice.value}" />
						<input type="hidden" name="product.priceRange.maxPrice.value" value="${product.priceRange.maxPrice.value}" />
						<span id="pricing2-2" class="list-price-format"><format:price priceData="${product.priceRange.minPrice}"/></span>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
			<c:choose>
			<c:when test="${not empty product.sellableUomsCount}">
				<c:choose>
					<c:when test="${(product.priceRange.minLSPPrice.value ne product.priceRange.maxLSPPrice.value)}">
						<input type="hidden" name="product.price.value" value="${product.price.value}" />
						<span id="pricing3" class="list-price-format"><format:price priceData="${product.price}"/><br><sup>${uomDescription}</sup></span>
					</c:when>
			
					<c:when test="${not empty product.sellableUomDesc}">
						<input type="hidden" name="product.price.value" value="${product.price.value}" />
						<span id="pricing4" class="list-price-format"><format:price priceData="${product.price}"/></span><span class="black-title b-price add_price"><br><sup>${uomDescription}</sup></span>
					</c:when>
					
					<c:otherwise>
						<input type="hidden" name="product.price.value" value="${product.price.value}" />
						<span id="pricing5" class="list-price-format"><format:price priceData="${product.price}"/><c:if test="${sellableUomListLen==1}"><br><sup>${uomDescription}</sup></c:if></span>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:when test="${not empty product.sellableUomsCount and (cmsPage.uid eq 'compareProductPage')}">
			   <c:choose>
					<c:when test="${not empty uomMeasure}">
						<span id="pricing6" class="list-price-format"><format:price priceData="${product.price}"/></span><span class="black-title b-price add_price"><br><sup>${uomMeasure}</sup></span>
					</c:when>
					<c:otherwise>
						<span id="pricing7" class="list-price-format"><format:price priceData="${product.price}"/><c:if test="${sellableUomListLen==1}"><br><sup>${uomDescription}</sup></c:if></span>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
			<input type="hidden" name="product.price.value" value="${product.price.value}" />
			<span id="pricing8" class="list-price-format"><format:price priceData="${product.price}"/></span>
			
			</c:otherwise>
			</c:choose>
			</c:otherwise>
		</c:choose>

</ycommerce:testId>