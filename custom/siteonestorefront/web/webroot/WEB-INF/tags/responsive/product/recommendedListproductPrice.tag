<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="uomDescription" required="true" type="java.lang.String" %>
<%@ attribute name="uomMeasure" required="true" type="java.lang.String" %>
<%@ attribute name="hideUomSelect" required="true" type="java.lang.Boolean" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<c:set var="totalpriceDigits" value="<%=de.hybris.platform.util.Config.getParameter(\"currency.totalprice.digits\")%>" />
<c:set var="hideList" value="${product.hideList}"/>
<c:set var="hideCSP" value="${product.hideCSP}"/>
<c:choose>
	<c:when test="${empty cookie['gls'] && sessionStore.storeId eq '172'}">
		<c:set var="contactNo" value="<%=de.hybris.platform.util.Config.getParameter(\"siteOne.Customer.Service.Number\")%>"/>		
	</c:when>
	<c:otherwise>
		<c:set var="contactNo" value="${sessionStore.address.phone}"/>
	</c:otherwise>
</c:choose>
<span class="f-s-10 bg-lightGrey hidden">
product.customerPrice.formattedValue:${product.customerPrice.formattedValue};
product.salePrice:${product.salePrice};
product.customerPrice:${product.customerPrice};
hideList:${hideList};
hideCSP:${hideCSP};
inventoryFlag:${product.inventoryFlag};
contactNo:${contactNo};
product.price.value:${product.price.value};
product.customerPrice.value:${product.customerPrice.value};
product.sellableUoms:${product.sellableUoms};
hideUomSelect:${hideUomSelect};
isAnonymous:${isAnonymous};
product.variantType:${product.variantType};
uomMeasure:${uomMeasure};
product.variantCount:${product.variantCount};
uomDescription:${uomDescription};
</span>
<input type="hidden" id="listPrice" value="${product.price}"/>
<input type="hidden" id="customerPrice" value="${product.customerPrice}"/>
<c:choose>
	<c:when test="${product.customerPrice == null || product.customerPrice.formattedValue eq '$0.00'}">
		<!-- when -->
		<c:if test="${product.salePrice !=null}">
			<!-- 1 -->
			<div class="col-md-2 col-sm-4 col-xs-4 actualPrice">
				<p class="p-l-15-xs slp-yourprice-style text-lowercase print-fs-14 print-fw-n" style="color: red;">$<fmt:formatNumber maxFractionDigits="${totalpriceDigits}" minFractionDigits="${totalpriceDigits}" value="${product.salePrice}"/>
				</p>
			</div>
			<div class="col-md-2 col-sm-4 col-xs-4 prePrice">
				<del>
					<product:recommendedPricePanel product="${product}"/>
				</del>
			</div>
		</c:if>
		<c:if test="${product.salePrice == null}">
			<!-- 2 if salePrice -->
			<c:choose>
				<c:when test="${product.priceRange == null}">
					<!-- 2 when priceRange -->
					<c:choose>
						<c:when test="${hideList ne true}">
							<!-- 2 when hideList -->
							<c:choose>
								<c:when test="${hideCSP ne true }">
									<!-- 2 when hideCSP -->
									<c:choose>
										<c:when test="${product.inventoryFlag}">
											<!-- 2 when inventoryFlag -->
											<p class="callBranchForPrice f-s-12 p-r-35 text-muted">
												<spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
											</p>
										</c:when>
										<c:otherwise>
											<!-- 2 otherwise inventoryFlag -->
											<c:if test="${not empty product.price && (product.price.value gt product.customerPrice.value)}">
												<!-- 3 if gt -->
												<c:if test="${not empty product.sellableUoms && hideUomSelect eq true}">
													<!-- 3 if sellableUoms -->
													<c:if test="${not empty product.price}">
														<!-- 3 if price -->
														<div class="floatrightprice">
															<p class="m-b-0 print-c-gray slp-text-style">
																<spring:theme code="text.product.siteOnelistprice"/>
															</p>
															<span class="print-c-gray print-f-none print-fs-14 f-w-600 print-fw-n slp-retailprice-style">
																<product:recommendedPricePanel product="${product}"/>
															</span>
														</div>
													</c:if>
												</c:if>
												<c:if test="${empty product.sellableUoms && hideUomSelect ne true}">
													<!-- 4 if sellableUoms -->
													<c:if test="${not empty product.price}">
														<!-- 4 price -->
														<div class="floatrightprice">
															<p class="m-b-0 print-c-gray slp-text-style">
																<spring:theme code="text.product.siteOnelistprice"/>
															</p>
															<span class="print-c-gray print-f-none print-fs-14 f-w-600 print-fw-n slp-retailprice-style">
																<product:recommendedPricePanel product="${product}"/>
															</span>
														</div>
													</c:if>
												</c:if>
											</c:if>
											<c:if test="${empty product.customerPrice}">
												<!-- 3 if !customerPrice -->
												<div class="floatleftprice flex-center-xs">
													<p class="text-uppercase slp-text-style print-c-gray m-b-5 m-b-0-xs print-hidden"><spring:theme code="text.product.siteOneCSP"/></p>
													<div class="p-l-15-xs slp-yourprice-style text-lowercase print-fs-14 print-fw-n">$<fmt:formatNumber maxFractionDigits="${totalpriceDigits}" minFractionDigits="${totalpriceDigits}" value="${not empty product.price ? product.price.value : 0}" /> / ${uomDescription}</div>
													<input type="hidden" maxFractionDigits="${totalpriceDigits}" class="js-customer-price" value="${not empty product.price ? product.price.value : 0}" />
												</div>
											</c:if>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${product.inventoryFlag}">
											<!-- 5 -->
											<p class="callBranchForPrice f-s-12 p-r-35 text-muted">
												<spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
											</p>
										</c:when>
										<c:otherwise>
											<!-- 6 -->
											<p class="callBranchForPrice">
												<spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
												<c:if test="${not empty product.sellableUoms && hideUomSelect eq true}">
													<c:if test="${not empty product.price}">
														<!-- 7 -->
														<div class="floatrightprice">
															<p class="m-b-0 print-c-gray slp-text-style">
																<spring:theme code="text.product.siteOnelistprice"/>
															</p>
															<span class="print-c-gray print-f-none print-fs-14 f-w-600 print-fw-n slp-retailprice-style">
																<product:recommendedPricePanel product="${product}"/>
															</span>
														</div>
													</c:if>
												</c:if>
												<c:if test="${empty product.sellableUoms && hideUomSelect ne true}">
													<c:if test="${not empty product.price}">
														<!-- 8 -->
														<div class="floatrightprice">
															<p class="m-b-0 print-c-gray slp-text-style">
																<spring:theme code="text.product.siteOnelistprice"/>
															</p>
															<span class="print-c-gray print-f-none print-fs-14 f-w-600 print-fw-n slp-retailprice-style">
																<product:recommendedPricePanel product="${product}"/>
															</span>
														</div>
													</c:if>
												</c:if>
											</p>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${hideCSP ne true}">
									<c:choose>
										<c:when test="${product.inventoryFlag}">
											<!-- 9 -->
											<p class="callBranchForPrice f-s-12 p-r-35 text-muted">
												<spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
											</p>
										</c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<!-- 10 -->
									<p class="callBranchForPrice f-s-12 p-r-35 text-muted">
										<spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
									</p>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<!-- 2 otherwise priceRange -->
					<c:if test="${!isAnonymous}">
						<c:choose>
							<c:when test="${hideList ne true}">
								<!-- 11 when hideList -->
								<c:choose>
									<c:when test="${hideCSP ne true}">
										<!-- 11 when hideCSP -->
										<c:choose>
											<c:when test="${product.inventoryFlag}">
												<!-- 11 when inventoryFlag -->
												<c:choose>
													<c:when test="${product.variantType eq 'GenericVariantProduct' }">
														<!-- 11 when variantType -->
														<product:recommendedPricePanel product="${product}"/>
														&nbsp;<spring:theme code="select.item.text"/>
													</c:when>
													<c:otherwise>
														<!-- 12 -->
														<p class="callBranchForPrice f-s-12 p-r-35 text-muted">
															<spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
														</p>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<c:if test="${not empty product.price}">
													<!-- 13 -->
													<div class="floatrightprice">
														<p class="m-b-0 print-c-gray slp-text-style">
															<spring:theme code="text.product.siteOnelistprice"/>
														</p>
														<div class="siteoneprice_pdp">
															<del class="print-c-gray print-f-none print-fs-14 f-w-600 print-fw-n slp-retailprice-style">
																<product:recommendedPricePanel product="${product}" isloggedIn="true" />
															</del>
														</div>
													</div>
												</c:if>
												<c:choose>
													<c:when test="${product.variantType eq 'GenericVariantProduct' }">
														<!-- 14 -->
														<product:recommendedPricePanel product="${product}"/>&nbsp;<spring:theme code="select.item.text"/>
													</c:when>
													<c:otherwise>
														<!-- 15 -->
														<p class="m-b-0 print-c-gray slp-text-style"><spring:theme code="text.product.your.price"/></p>
														<div class="p-l-15-xs slp-yourprice-style text-lowercase print-fs-14 print-fw-n">
															<product:recommendedPricePanel product="${product}"/> / ${uomMeasure}
														</div>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${product.inventoryFlag}">
												<!-- 16 -->
												<p class="callBranchForPrice f-s-12 p-r-35 text-muted">
													<spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
												</p>
											</c:when>
											<c:otherwise>
												<p class="callBranchForPrice">
													<spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
													<c:if test="${not empty product.price}">
														<!-- 17 -->
														<div class="floatrightprice">
															<p class="m-b-0 print-c-gray slp-text-style">
																<spring:theme code="text.product.siteOnelistprice"/>
															</p>
															<div class="siteoneprice_pdp">
																<del class="print-c-gray print-f-none print-fs-14 f-w-600 print-fw-n slp-retailprice-style">
																	<product:recommendedPricePanel product="${product}" isloggedIn="true" />
																</del>
															</div>
														</div>
													</c:if>
												</p>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${hideCSP ne true}">
										<c:choose>
											<c:when test="${product.inventoryFlag}">
												<c:choose>
													<c:when test="${product.variantCount==0}">
														<!-- 18 -->
														<p class="callBranchForPrice f-s-12 p-r-35 text-muted">
															<spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
														</p>
													</c:when>
													<c:otherwise>
														<c:if test="${product.variantType eq 'GenericVariantProduct' }">
															<!-- 19 -->
															<product:recommendedPricePanel product="${product}"/>
															&nbsp;<spring:theme code="select.item.text"/>
														</c:if>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${product.variantType eq 'GenericVariantProduct' }">
														<!--20 -->
														<product:recommendedPricePanel product="${product}"/>
														&nbsp;<spring:theme code="select.item.text"/>
													</c:when>
													<c:otherwise>
														<!-- 21 -->
														<p class="m-b-0 print-c-gray slp-text-style"><spring:theme code="text.product.your.price"/></p>
														<div class="p-l-15-xs slp-yourprice-style text-lowercase print-fs-14 print-fw-n">
															<product:recommendedPricePanel product="${product}"/> / ${uomMeasure}
														</div>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${product.variantType eq 'GenericVariantProduct' }">
												<!-- 22 -->
												<p class="callBranchForPrice f-s-12 p-r-35 text-muted">
													<spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
												</p>
											</c:when>
											<c:otherwise>
												<!-- 23 -->
												<p class="callBranchForPrice f-s-12 p-r-35 text-muted">
													<spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
												</p>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:otherwise>
			</c:choose>
		</c:if>
	</c:when>
	<c:otherwise>
		<!-- otherwise -->
		<c:if test="${product.salePrice !=null}">
			<!-- 24 -->
			<div class="col-md-2 col-sm-4 col-xs-4 actualPrice">
				<p class="p-l-15-xs slp-yourprice-style text-lowercase print-fs-14 print-fw-n" style="color: red;">
					$<fmt:formatNumber maxFractionDigits="${totalpriceDigits}" minFractionDigits="${totalpriceDigits}" value="${product.salePrice}"/>
				</p>
			</div>
			<div class="col-md-2 col-sm-4 col-xs-4 prePrice">
				<p class="p-l-15-xs slp-yourprice-style text-lowercase print-fs-14 print-fw-n">
					<del>
						<format:fromPrice priceData="${product.customerPrice}" />
					</del>
				</p>
			</div>
		</c:if>
		<c:if test="${product.salePrice ==null}">
			<c:choose>
				<c:when test="${not empty uomDescription }">
					<c:choose>
						<c:when test="${hideList ne true}">
							<c:choose>
								<c:when test="${hideCSP ne true}">
									<c:choose>
										<c:when test="${product.inventoryFlag}">
											<!-- 25 -->
											<p class="callBranchForPrice f-s-12 p-r-35 text-muted">
												<spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
											</p>
										</c:when>
										<c:otherwise>
											<c:if test="${not empty product.price && (product.price.value gt product.customerPrice.value)}">
												<!-- 26 -->
												<div class="floatrightprice hidden">
													<p class="m-b-0 print-c-gray slp-text-style">
														<spring:theme code="text.product.siteOnelistprice"/>
													</p>
													<del class="print-c-gray print-f-none print-fs-14 f-w-600 print-fw-n slp-retailprice-style">
														<product:recommendedPricePanel product="${product}"/>
													</del>
												</div>
											</c:if>
											<!-- 27 -->
											<p class="m-b-0 print-c-gray slp-text-style"><spring:theme code="text.product.your.price"/></p>
											<div class="p-l-15-xs slp-yourprice-style text-lowercase print-fs-14 print-fw-n">
												<format:fromPrice priceData="${product.customerPrice}"/> / ${uomMeasure}
											</div>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${product.inventoryFlag}">
											<!-- 28 -->
											<p class="callBranchForPrice f-s-12 p-r-35 text-muted">
												<spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
											</p>
										</c:when>
										<c:otherwise>
											<p class="callBranchForPrice">
												<spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
												<c:if test="${not empty product.price}">
													<!-- 29 -->
													<div class="floatrightprice">
														<p class="m-b-0 print-c-gray slp-text-style">
															<spring:theme code="text.product.siteOnelistprice"/>
														</p>
														<span class="print-c-gray print-f-none print-fs-14 f-w-600 print-fw-n slp-retailprice-style">
															<product:recommendedPricePanel product="${product}"/>
														</span>
													</div>
												</c:if>
											</p>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${hideCSP ne true}">
									<c:choose>
										<c:when test="${product.inventoryFlag}">
											<!-- 30 -->
											<p class="callBranchForPrice f-s-12 p-r-35 text-muted">
												<spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
											</p>
										</c:when>
										<c:otherwise>
											<!-- 31 -->
											<p class="m-b-0 print-c-gray slp-text-style"><spring:theme code="text.product.your.price"/></p>
											<div class="p-l-15-xs slp-yourprice-style text-lowercase print-fs-14 print-fw-n">
												<format:fromPrice priceData="${product.customerPrice}"/> / ${uomMeasure}
											</div>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<!-- 31 -->
									<p class="callBranchForPrice f-s-12 p-r-35 text-muted">
										<spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
									</p>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${hideList ne true}">
							<c:choose>
								<c:when test="${hideCSP ne true}">
									<c:choose>
										<c:when test="${product.inventoryFlag}">
											<!-- 32 -->
											<p class="callBranchForPrice f-s-12 p-r-35 text-muted">
												<spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
											</p>
										</c:when>
										<c:otherwise>
											<c:if test="${not empty product.price && (product.price.value gt product.customerPrice.value)}">
												<!-- 32 -->
												<div class="floatrightprice hidden">
													<p class="m-b-0 print-c-gray slp-text-style">
														<spring:theme code="text.product.siteOnelistprice"/>
													</p>
													<del class="print-c-gray print-f-none print-fs-14 f-w-600 print-fw-n slp-retailprice-style">
														<product:recommendedPricePanel product="${product}" />
													</del>
												</div>
											</c:if>
											<!-- 33 -->
											<p class="m-b-0 print-c-gray slp-text-style"><spring:theme code="text.product.your.price"/></p>
											<div class="p-l-15-xs slp-yourprice-style text-lowercase print-fs-14 print-fw-n">
												<format:fromPrice priceData="${product.customerPrice}"/> / ${uomMeasure}
											</div>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${product.inventoryFlag}">
											<!-- 34 -->
											<p class="callBranchForPrice f-s-12 p-r-35 text-muted">
												<spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
											</p>
										</c:when>
										<c:otherwise>
											<!-- 35 -->
											<p class="callBranchForPrice">
												<spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
												<c:if test="${not empty product.price}">
													<!-- 36 -->
													<div class="floatrightprice">
														<p class="m-b-0 print-c-gray slp-text-style">
															<spring:theme code="text.product.siteOnelistprice"/>
														</p>
														<del class="print-c-gray print-f-none print-fs-14 f-w-600 print-fw-n slp-retailprice-style">
															<product:recommendedPricePanel product="${product}" />
														</del>
													</div>
												</c:if>
											</p>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${hideCSP ne true}">
									<c:choose>
										<c:when test="${product.inventoryFlag}">
											<!-- 37 -->
											<p class="callBranchForPrice f-s-12 p-r-35 text-muted">
												<spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
											</p>
										</c:when>
										<c:otherwise>
											<!-- 38 -->
											<p class="m-b-0 print-c-gray slp-text-style"><spring:theme code="text.product.your.price"/></p>
											<div class="p-l-15-xs slp-yourprice-style text-lowercase print-fs-14 print-fw-n">
												<format:fromPrice priceData="${product.customerPrice}"/>
											</div>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<!-- 39 -->
									<p class="callBranchForPrice f-s-12 p-r-35 text-muted">
										<spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
									</p>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</c:if>
	</c:otherwise>
</c:choose>