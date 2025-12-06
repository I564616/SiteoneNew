<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="product-classifications">

	<c:if test="${not empty mapConfigurableAttributes}">
		<c:forEach items="${mapConfigurableAttributes}" var="classification">
				<div>
						<c:forEach items="${classification.value}" var="feature"> 	
							<div class="col-md-6 col-xs-12 no-margin pdp-specification js-pdp-specification">
								<div class="col-md-4 col-xs-12  flex-center pdp-specification__header">${feature.name}</div>
								<div class="col-md-8 flex-center col-xs-12 pdp-specification__value">
									<p class="margin0">
										<c:forEach items="${feature.featureValues}" var="value" varStatus="status">
											${fn:escapeXml(value.value)}
											<c:choose>
												<c:when test="${feature.range}">
													${not status.last ? '-' : feature.featureUnit.symbol}
												</c:when>
												<c:otherwise>
													${feature.featureUnit.symbol}
													${not status.last ? '<br/>' : ''}
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</p>
								</div>
							</div>
						</c:forEach>
						<div class="col-md-6 col-xs-12 no-margin pdp-specification">
								<div class="col-md-4 col-xs-12 flex-center pdp-specification__header">
								<spring:theme code="productDetailsClassification.regulated" />
								</div>
								<div class="col-md-8 flex-center col-xs-12 pdp-specification__value">
						 <c:choose>
                         <c:when test="${product.isRegulateditem}">
                      <spring:theme code="productDetailsClassification.regulated.yes" />
                            </c:when>
                       <c:otherwise>
                     <spring:theme code="productDetailsClassification.regulated.no" />
                           </c:otherwise>
                       </c:choose>
								</div>
						</div>
						<div class="col-md-6 col-xs-12 no-margin pdp-specification">
						<c:if test="${product.productBrandName != null}">
								<div class="col-md-4 col-xs-12 flex-center pdp-specification__header">
								<spring:theme code="productDetailsClassification.brandName" />
								</div>
								<div class="col-md-8 flex-center col-xs-12 pdp-specification__value">
									<p class="margin0">${product.productBrandName}</p>
								</div>
						</c:if>
						</div>
				</div>
		</c:forEach>
	</c:if>
</div>

