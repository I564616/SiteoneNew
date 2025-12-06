<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="isOrderForm" required="false" type="java.lang.Boolean" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ attribute name="isloggedIn" required="false" type="java.lang.Boolean" %>
<%@ attribute name="listPrice" required="false" type="java.lang.Boolean" %>
<c:set var="entryFound" value="${false}" />
<c:forEach items="${product.sellableUoms}" var="sellableUom2">
	<c:if test="${entryFound eq false}">
		<c:set var="entryFound" value="${true}" />
		<c:set var="newuomDescription" value="${sellableUom2.inventoryUOMDesc}"/>
		<c:set var="newuomMeasure" value="${sellableUom2.measure}"/>
	</c:if>
	<c:if test="${product.hideUom eq true && sellableUom2.hideUOMOnline eq false}">
		<c:set var="hideuom2" value="true"/>
	</c:if>
</c:forEach>
<c:choose>
	<c:when test="${empty product.volumePrices}">
		<c:choose>
			<c:when test="${(not empty product.priceRange) and (not empty product.sellableUoms && hideUomSelect ne true)}">
				<span>
					<span>
						<format:price priceData="${!isAnonymous && listPrice? product.priceRange.minLSPPrice:product.priceRange.minPrice}"/>
					</span>
					<span>
					-
					</span>
					<span>
						<format:price priceData="${!isAnonymous && listPrice? product.priceRange.maxLSPPrice:product.priceRange.maxPrice}"/>
					</span>
				</span>
			</c:when>
			<c:when test="${(not empty product.priceRange) and (product.priceRange.minPrice.value ne product.priceRange.maxPrice.value) and ((empty product.baseProduct) or (not empty isOrderForm and isOrderForm))}">
				<span>
					<format:price priceData="${isloggedIn? product.priceRange.minLSPPrice:product.priceRange.minPrice}"/>
				</span>
				<span>
					-
				</span>
				<span>
					<format:price priceData="${isloggedIn? product.priceRange.maxLSPPrice:product.priceRange.maxPrice}"/>
				</span>
			</c:when>
			<c:otherwise>
				<p class="price">
					<c:choose>
						<c:when test="${not empty product.price}">
							<c:set var="entryFound" value="${false}" />
							<c:forEach items="${product.sellableUoms}" var="sellableUom">
								<c:if test="${entryFound eq false}">
									<c:set var="entryFound" value="${true}" />
									<c:set var="uomDescription" value="${sellableUom.inventoryUOMDesc}"/>
									<c:set var="uomMeasure" value="${sellableUom.measure}"/>
								</c:if>
							</c:forEach>
							<c:if test="${product.singleUom eq true}">
								<c:set var="singleUom" value="true"/>
								<c:set var="uomDescription" value="${product.singleUomDescription}"/>
								<c:set var="uomMeasure" value="${product.singleUomMeasure}"/>
							</c:if>
							<c:choose>
								<c:when test="${not empty uomDescription}">
									<c:choose>
										<c:when test="${hideuom2 eq true}">
											<format:fromPrice priceData="${product.price}"/> / ${newuomMeasure}
										</c:when>
										<c:otherwise>
											<format:fromPrice priceData="${product.price}"/> / ${uomMeasure}
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<format:fromPrice priceData="${product.price}"/>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:if test="${(not empty product.priceRange) && (empty product.baseProduct)}">
								<c:choose>
									<c:when test="${not empty uomDescription}">
										<format:fromPrice priceData="${product.priceRange.minPrice}"/> / ${uomMeasure}
									</c:when>
									<c:otherwise>
										<format:fromPrice priceData="${product.priceRange.minPrice}"/>
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:otherwise>
					</c:choose>
				</p>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<table class="volume__prices" cellpadding="0" cellspacing="0" border="0">
			<thead>
				<th class="volume__prices-quantity">
					<spring:theme code="product.volumePrices.column.qa"/>
				</th>
				<th class="volume__price-amount">
					<spring:theme code="product.volumePrices.column.price"/>
				</th>
			</thead>
			<tbody>
				<c:forEach var="volPrice" items="${product.volumePrices}">
					<tr>
						<td class="volume__price-quantity">
							<c:choose>
								<c:when test="${empty volPrice.maxQuantity}">
								${volPrice.minQuantity}+
								</c:when>
								<c:otherwise>
								${volPrice.minQuantity}-${volPrice.maxQuantity}
								</c:otherwise>
							</c:choose>
						</td>
						<td class="volume__price-amount text-right">${volPrice.formattedValue}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:otherwise>
</c:choose>