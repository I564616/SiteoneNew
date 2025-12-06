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
	   </c:if>
	   <c:forEach items="${product.sellableUoms}" var="sellableUom" begin="0" end="0">
		<c:set var="MulUomValue" value="${sellableUom.code}" />						
	</c:forEach>
		<c:choose>
			<c:when test="${product.multidimensional and (not empty product.priceRange.minPrice) and (not empty product.priceRange.maxPrice) and (product.priceRange.minPrice.value ne product.priceRange.maxPrice.value)}">
				<span id="csppricing1" class="csp-price-format"><format:cspprice priceData="${product.priceRange.minPrice}"/> <c:if test="${product.sellableUomsCount eq null}"> - <format:cspprice priceData="${product.priceRange.maxPrice}"/></span></c:if>
			</c:when>
			<c:when test="${product.multidimensional and (not empty product.priceRange.minPrice) and (not empty product.priceRange.maxPrice) and (product.priceRange.minPrice.value eq product.priceRange.maxPrice.value)}">
				<c:choose>
					<c:when test="${product.variantCount eq 1}">
						<input type="hidden" name="product.priceRange.minPrice.value" value="${product.priceRange.minPrice.value}" />
						<input type="hidden" name="product.priceRange.maxPrice.value" value="${product.priceRange.maxPrice.value}" />
						<span id="csppricing2-1" class="csp-price-format"><format:cspprice priceData="${product.priceRange.minPrice}"/><c:if test="${sellableUomListLen==1}"><br><sup>${uomDescription}</sup></c:if></span>
					</c:when>
					<c:otherwise>
						<input type="hidden" name="product.priceRange.minPrice.value" value="${product.priceRange.minPrice.value}" />
						<input type="hidden" name="product.priceRange.maxPrice.value" value="${product.priceRange.maxPrice.value}" />
						<span id="csppricing2-2" class="csp-price-format"><format:cspprice priceData="${product.priceRange.minPrice}"/></span>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
			<c:choose>
			<c:when test="${not empty product.sellableUomsCount}">
				<c:choose>
					<c:when test="${(not empty product.priceRange.minPrice) and (not empty product.priceRange.maxPrice) and (product.priceRange.minPrice.value ne product.priceRange.maxPrice.value)}">
						<span id="csppricing3" class="csp-price-format"><format:cspprice priceData="${product.customerPrice}"/><span class="check_price"><br><sup>${uomDescription}</sup></span>
					</span>
					</c:when>
					<c:when test="${not empty product.sellableUomDesc}">
						<span id="csppricing4" class="csp-price-format"><format:cspprice priceData="${product.customerPrice}"/></span><span class="black-title b-price add_price"><br><sup>${uomDescription}</sup></span>
					</c:when>
					<c:otherwise>
						<span id="csppricing5" class="csp-price-format"><format:cspprice priceData="${product.customerPrice}"/>
							<c:choose>
								<c:when test="${sellableUomListLen==1}">
									<span id="csppricing5-1" class="check_price"><br><sup>${uomDescription}</sup></span>
								</c:when>
								<c:otherwise>
									<c:if test="${not empty product.sellableUoms}">
										<span id="csppricing5-2" class="check_price"><br><sup>${uomDescription}</sup></span>
									</c:if>
								</c:otherwise>
							</c:choose>
						</span>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:when test="${not empty product.sellableUomsCount and (cmsPage.uid eq 'compareProductPage')}">
			   <c:choose>
					<c:when test="${not empty uomMeasure}">
						<span id="csppricing6" class="csp-price-format"><format:cspprice priceData="${product.customerPrice}"/></span><span class="black-title b-price add_price"><br><sup>${uomMeasure}</sup></span>
					</c:when>
					<c:otherwise>
						<span id="csppricing7" class="csp-price-format"><format:cspprice priceData="${product.customerPrice}"/><c:if test="${sellableUomListLen==1}"><span class="check_price"><br><sup>${uomDescription}</sup></span></c:if></span>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
			<span id="csppricing8" class="csp-price-format"><format:cspprice priceData="${product.customerPrice}"/></span>
			
			</c:otherwise>
			</c:choose>
			</c:otherwise>
		</c:choose>

</ycommerce:testId>