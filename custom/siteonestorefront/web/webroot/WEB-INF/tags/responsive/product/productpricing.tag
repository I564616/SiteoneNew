<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="b2b-product" tagdir="/WEB-INF/tags/addons/siteoneacceleratoraddon/responsive/product"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ attribute name="isListPrice" required="true" type="java.lang.Boolean" %>
<%@ attribute name="reDesignPrice" required="true" type="java.lang.String" %>
<%@ attribute name="firstMulUOMRetailPrice" required="true" type="java.lang.String" %>
<c:set var="showUOMDropdown" value="false" />
<c:if test="${not empty product.sellableUoms && hideUomSelect ne true}">
	<c:set var="showUOMDropdown" value="true" />
</c:if>
<input type="hidden" id="firstMulUOMRetailPrice" value="${firstMulUOMRetailPrice}" />
<c:set var="hideList" value="${product.hideList}"/>
<c:set var="hideCSP" value="${product.hideCSP}"/>
<c:choose>
	<c:when test="${empty cookie['gls'] && sessionStore.storeId eq '172'}">
		<c:set var="contactNo" value="<%=de.hybris.platform.util.Config.getParameter(\"siteOne.Customer.Service.Numbesimple-your-price-title r\")%>"/>		
	</c:when>
	<c:otherwise>
		<c:set var="contactNo" value="${sessionStore.address.phone}"/>
	</c:otherwise>
</c:choose>
<div class="${(showUOMDropdown && isListPrice)?'row ':''} ${(showUOMDropdown && isListPrice && isAnonymous)?'flex':''} product-details black-title ${!showUOMDropdown && isListPrice?'col-md-10':'col-md-12'} col-sm-12 col-xs-12 padding0 3" id="uomPrice">
<ycommerce:testId code="productDetails_productNamePrice_label_${product.code}">

				<c:set var="hideUomSelect" value="false" />
				<c:forEach items="${product.sellableUoms}" var="sellableUom1">
					<c:if test="${product.hideUom eq true}">
						<c:set var="hideUomSelect" value="true" />
						<c:set var="newuomDescription"
							value="${sellableUom2.inventoryUOMDesc}" />
						<c:set var="uomMeasure" value="${sellableUom1.measure}" />
					</c:if>
				</c:forEach>
				<c:if test="${product.singleUom eq true}">
					<c:set var="singleUom" value="true" />
					<c:set var="uomDescription" value="${product.singleUomDescription}" />
					<c:set var="uomMeasure" value="${product.singleUomMeasure}" />
				</c:if>
				<c:if
					test="${not empty product.sellableUoms && hideUomSelect ne true}">


					<c:forEach items="${product.sellableUoms}" var="sellableUom">
						<c:set var="uomDescription"
							value="${sellableUom.inventoryUOMDesc}" />
						<c:set var="uomMeasure" value="${sellableUom.measure}" />

					</c:forEach>
				</c:if>
				<input type="hidden" class="product-uomMeasure" value="${fn:escapeXml(uomMeasure)}" />
				<input type="hidden" id="listPrice" value="${product.price}" />
				<input type="hidden" id="customerPrice"
					value="${product.customerPrice}" />
				<c:choose>
					<c:when
						test="${product.customerPrice == null || product.customerPrice.formattedValue eq '$0.00'}">
						<c:if test="${product.salePrice !=null}">
							<div class="col-md-2 col-sm-4 col-xs-4 actualPrice 4">
								<p class="price" style="color: red;">
									$
									<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"
										value="${product.salePrice}" />
								</p>
							</div>
							<div class="col-md-2 col-sm-4 col-xs-4 prePrice 5">
								<del>
									<product:productPricePanel product="${product}" />
								</del>
							</div>
							<br>
						</c:if>
						<c:if test="${product.salePrice ==null}">
							<c:choose>
								<c:when test="${product.priceRange == null}">
									<c:choose>
										<c:when test="${hideList ne true}">
											<c:choose>
												<c:when test="${hideCSP ne true }">
													<c:choose>
														<c:when test="${product.inventoryFlag}">
															<p class="callBranchForPrice 6">
																<spring:theme code="text.product.callBranchForPrice"
																	arguments="${contactNo}" />
															</p>
														</c:when>
														<c:otherwise>
															<c:if test="${not empty product.sellableUoms && hideUomSelect eq true}">
																<c:if test="${not empty product.price}">
																<div class="row m-t-10">
																	<div class="col-md-2 siteOneListPrice m-t-8 7 ">
																		<spring:theme code="text.product.siteOnelistprice" />
																	</div>
																		<c:if test="${isListPrice}">
																		<div class="col-md-10 ${(not empty product.bulkUOMPrice)?"uom-redsg-ftprice":""} 8">
																			<div class="${(not empty product.bulkUOMPrice)?"col-md-10 uom-redsg-ftprice ":"col-md-12 uom-redsg-ftprice"} col-sm-12 bulk-price">
																			<product:productPricePanel product="${product}" />
																			</div>
																			<div class="${(not empty product.bulkUOMPrice)?"col-md-2 ":"hidden"} col-sm-12 bulk-uom">
																			<c:if test="${not empty product.bulkUOMPrice}">
																				(${product.bulkUOMPrice}/${product.bulkUOMCode})
																			</c:if>
																			</div>
																		</div>
																		</c:if>
																		
																</div>
																</c:if>
																<a href="<c:url value="/login"/>"
																	class="logInToSeeYourPrice signInOverlay  ${not product.multidimensional?'pdpredesign-logintoseeyourprice':''} 9"><spring:theme
																		code="text.product.logInToSeeYourPrice" /></a>
															</c:if>
															<c:if test="${empty product.sellableUoms && hideUomSelect ne true}">
																<div class="row 10 m-t-8">
																<c:if test="${not empty product.price}">
																
																		<p class="col-md-2 siteOneListPrice pad-lft0 pdpredsg-retailprice 11">
																		<spring:theme code="text.product.siteOnelistprice" />
																		</p>
																		<c:if test="${isListPrice}">
																		<div class="col-md-10 pad-lft0 12 pad-rgt0">
																			<div class="${(not empty product.bulkUOMPrice)?"col-md-10 ":"col-md-12 uom-ftsize no-uom-padding pad-rgt0"} col-sm-12 bulk-price">
																			<product:productPricePanel product="${product}" />
																			</div>
																			<div class="${(not empty product.bulkUOMPrice)?"col-md-2 ":"hidden"} col-sm-12 bulk-uom">
																			<c:if test="${not empty product.bulkUOMPrice}">
																				(${product.bulkUOMPrice}/${product.bulkUOMCode})
																			</c:if>
																			</div>
																		</div>
																		</c:if>
																	
																</c:if>
																
																
																<a href="<c:url value="/login"/>"
																	class="logInToSeeYourPrice signInOverlay 1 ${not product.multidimensional?'pdpredesign-logintoseeyourprice':''} 13"><spring:theme
																		code="text.product.logInToSeeYourPrice" /></a>
																		</div>
																		
															</c:if>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${product.inventoryFlag}">
															<p class="callBranchForPrice 14">
																<spring:theme code="text.product.callBranchForPrice"
																	arguments="${contactNo}" />

															</p>
														</c:when>
														<c:otherwise>
														<div class="pricing-and-callbranch-container m-t-8 15">
															<p class="callBranchForPrice 16">
																<spring:theme code="text.product.callBranchForPrice"
																	arguments="${contactNo}" />
																<c:if
																	test="${not empty product.sellableUoms && hideUomSelect eq true}">
																	<c:if test="${not empty product.price}">
																		<p class="col-md-2 siteOneListPrice pad-lft0 pdpredsg-retailprice 17">
																			<spring:theme code="text.product.siteOnelistprice" /></p>
																	</c:if>
																		<div class="col-md-10 pad-lft0 18">
																			<div class="${(not empty product.bulkUOMPrice)?"col-md-10 ":"col-md-12 uom-ftsize no-uom-padding"} col-sm-12 bulk-price">
																			<product:productPricePanel product="${product}" />
																			</div>
																			<div class="${(not empty product.bulkUOMPrice)?"col-md-2 ":"hidden"} col-sm-12 bulk-uom">
																			<c:if test="${not empty product.bulkUOMPrice}">
																				(${product.bulkUOMPrice}/${product.bulkUOMCode})
																			</c:if>
																			</div>
																		</div>
																</c:if>
																<c:if
																	test="${empty product.sellableUoms && hideUomSelect ne true}">
																	<c:if test="${not empty product.price}">
																		<p class="col-md-2 siteOneListPrice pad-lft0 pdpredsg-retailprice 19">
																			<spring:theme code="text.product.siteOnelistprice" /></p>
																			<div class="col-md-10 pad-lft0 20">
																				<div class="${(not empty product.bulkUOMPrice)?"col-md-10 ":"col-md-12 uom-ftsize no-uom-padding"} col-sm-12 bulk-price">
																			<product:productPricePanel product="${product}" />
																			</div>
																				<div class="${(not empty product.bulkUOMPrice)?"col-md-2 ":"hidden"} col-sm-12 bulk-uom">
																				<c:if test="${not empty product.bulkUOMPrice}">
																					(${product.bulkUOMPrice}/${product.bulkUOMCode})
																				</c:if>
																				</div>
																			</div>
																	</c:if>
																</c:if>
																</div>
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
															<p class="callBranchForPrice 21">
																<spring:theme code="text.product.callBranchForPrice"
																	arguments="${contactNo}" />
															</p>

														</c:when>
														<c:otherwise>
															<%-- <p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/></p>
								                 	<br> --%>
															<a href="<c:url value="/login"/>"
																class="logInToSeeYourPrice signInOverlay 2 22 ${not product.multidimensional?'pdpredesign-logintoseeyourprice':''}"><spring:theme
																	code="text.product.logInToSeeYourPrice" /></a>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<p class="callBranchForPrice 23">
														<spring:theme code="text.product.callBranchForPrice"
															arguments="${contactNo}" />
													</p>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>

								</c:when>
								<c:otherwise>
									<c:if test="${!isAnonymous}">
										<c:choose>
											<c:when test="${hideList ne true}">
												<c:choose>
													<c:when test="${hideCSP ne true}">
														<c:choose>
															<c:when test="${product.inventoryFlag}">
																<c:choose>
																	<c:when
																		test="${product.variantType eq 'GenericVariantProduct'}">
																		<product:productPricePanel product="${product}" />
																		<div class="select-item-msg 24">
																			<spring:theme code="select.item.text" />
																		</div>
																	</c:when>
																	<c:otherwise>
																		<p class="callBranchForPrice 25">
																			<spring:theme code="text.product.callBranchForPrice"
																				arguments="${contactNo}" />
																		</p>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:if test="${not empty product.price}">
																	<p class="siteOneListPrice 26">
																		<spring:theme code="text.product.siteOnelistprice" />
																	</p>
																	<div class="siteoneprice_pdp 27">
																		<del>
																			<product:productPricePanel product="${product}"
																				isloggedIn="true" listPrice="true" />
																		</del>
																	</div>
																	<br>
																</c:if>
																<c:choose>
																	<c:when
																		test="${product.variantType eq 'GenericVariantProduct' || (not empty product.sellableUoms && hideUomSelect ne true)}">
																		<div class="row 28">
																					<div class="${(not empty product.bulkUOMPrice)?"col-md-10 ":"col-md-12"} col-sm-12 bulk-price uom-dropdown-text ${(showUOMDropdown && not product.multidimensional && !isAnonymous)? 'uom-dropdown-text-YPrice':''}">${reDesignPrice}</div>
																					<div class="${(not empty product.bulkUOMPrice)?"col-md-2 ":"hidden"} col-sm-12 bulk-uom">
																					<c:if test="${not empty product.bulkUOMPrice}">
																						(${product.bulkUOMPrice}/${product.bulkUOMCode})
																					</c:if>
																					</div>
																				</div>
																		<div class="select-item-msg 29">
																			<spring:theme code="select.item.text" />
																		</div>
																	</c:when>
																	<c:otherwise>
																		<div class="yourPrice 30"></div>


																			<div class="yourPriceValue large-size 31">
																				<div class="row">
																					<div class="${(not empty product.bulkUOMPrice)?"col-md-10 ":"col-md-12"} col-sm-12 bulk-price">
																			<product:productPricePanel product="${product}" />
																			</div>
																					<div class="${(not empty product.bulkUOMPrice)?"col-md-2 ":"hidden"} col-sm-12 bulk-uom">
																					<c:if test="${not empty product.bulkUOMPrice}">
																						(${product.bulkUOMPrice}/${product.bulkUOMCode})
																					</c:if>
																					</div>
																				</div>
																			</div>
																		
																	</c:otherwise>
																</c:choose>

															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${product.inventoryFlag}">
																<p class="callBranchForPrice 32">
																	<spring:theme code="text.product.callBranchForPrice"
																		arguments="${contactNo}" />
																</p>
															</c:when>
															<c:otherwise>
																<p class="callBranchForPrice 33">
																	<spring:theme code="text.product.callBranchForPrice"
																		arguments="${contactNo}" />
																	<c:if test="${not empty product.price}">
																		<p class="siteOneListPrice 34">
																			<spring:theme code="text.product.siteOnelistprice" />
																		</p>
																		<div class="siteoneprice_pdp 35">
																			<del>
																				<product:productPricePanel product="${product}"
																					isloggedIn="true" listPrice="true" />
																			</del>
																		</div>
																		<br>
																	</c:if>
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
																		<p class="callBranchForPrice 36">
																			<spring:theme code="text.product.callBranchForPrice"
																				arguments="${contactNo}" />
																		</p>
																	</c:when>
																	<c:otherwise>
																		<c:if
																			test="${product.variantType eq 'GenericVariantProduct' || (not empty product.sellableUoms && hideUomSelect ne true)}">
																			<div class="row 37">
																					<div class="${(not empty product.bulkUOMPrice)?"col-md-10 ":"col-md-12"} col-sm-12 bulk-price">
																			<product:productPricePanel product="${product}" />
																			</div>
																					<div class="${(not empty product.bulkUOMPrice)?"col-md-2 ":"hidden"} col-sm-12 bulk-uom">
																					<c:if test="${not empty product.bulkUOMPrice}">
																						(${product.bulkUOMPrice}/${product.bulkUOMCode})
																					</c:if>
																					</div>
																				</div>
																			<div class="select-item-msg 38">
																				<spring:theme code="select.item.text" />
																			</div>
																		</c:if>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when
																		test="${product.variantType eq 'GenericVariantProduct' || (not empty product.sellableUoms && hideUomSelect ne true)}">
																		<div class="row 39">
																					<div class="${(not empty product.bulkUOMPrice)?"col-md-10 ":"col-md-12"} col-sm-12 bulk-price">
																			<product:productPricePanel product="${product}" />
																			</div>
																					<div class="${(not empty product.bulkUOMPrice)?"col-md-2 ":"hidden"} col-sm-12 bulk-uom">
																					<c:if test="${not empty product.bulkUOMPrice}">
																						(${product.bulkUOMPrice}/${product.bulkUOMCode})
																					</c:if>
																					</div>
																				</div>
																		<div class="select-item-msg 40">
																			<spring:theme code="select.item.text" />
																		</div>
																	</c:when>
																	<c:otherwise>
																		<div class="yourPrice 41"></div>

																			<div class="yourPriceValue large-size 42">
																				<div class="row 43">
																					<div class="${(not empty product.bulkUOMPrice)?"col-md-10 ":"col-md-12"} col-sm-12 bulk-price">
																			<product:productPricePanel product="${product}" />${uomMeasure}
																			</div>
																					<div class="${(not empty product.bulkUOMPrice)?"col-md-2 ":"hidden"} col-sm-12 bulk-uom">
																					<c:if test="${not empty product.bulkUOMPrice}">
																						(${product.bulkUOMPrice}/${product.bulkUOMCode})
																					</c:if>
																					</div>
																				</div>
																			</div>
																		
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when
																test="${product.variantType eq 'GenericVariantProduct'}">
																<%-- <product:productPricePanel product="${product}"/>&nbsp;<spring:theme code="select.item.text"/>--%>
																<p class="callBranchForPrice 44">
																	<spring:theme code="text.product.callBranchForPrice"
																		arguments="${contactNo}" />
															</c:when>
															<c:otherwise>
																<p class="callBranchForPrice 45">
																	<spring:theme code="text.product.callBranchForPrice"
																		arguments="${contactNo}" />
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</c:if>
									<c:if test="${isAnonymous}">
										<c:choose>
											<c:when test="${hideList ne true}">
												<c:choose>
													<c:when test="${hideCSP ne true}">
														<c:choose>
															<c:when test="${product.inventoryFlag}">
																<p class="callBranchForPrice 46">
																	<spring:theme code="text.product.callBranchForPrice"
																		arguments="${contactNo}" />
																</p>
															</c:when>
															<c:otherwise>
																<c:if test="${not empty product.price}">
																	<p class="siteOneListPrice 47 ${showUOMDropdown?'col-md-2 pdpredesign-uomlabel':''}">
																		<spring:theme code="text.product.siteOnelistprice" /></p>
																	<div class="cl"></div>
																	<c:if test="${isListPrice}">
																	<div class="price_variant 48 ${showUOMDropdown?'col-md-10 pdpredesign-uomprice':''} ">
																		<div class="row 49">
																			<div class="${(not empty product.bulkUOMPrice)?"col-md-10 ":"col-md-12"} col-sm-12 bulk-price uom-dropdown-text">${reDesignPrice}</div>
																			
																			<div class="${(not empty product.bulkUOMPrice)?"col-md-2 ":"hidden"} col-sm-12 bulk-uom">
																			<c:if test="${not empty product.bulkUOMPrice}">
																				(${product.bulkUOMPrice}/${product.bulkUOMCode})
																			</c:if>
																			</div>
																		</div>
																	</div>
																	</c:if>
																</c:if>
																<a href="<c:url value="/login"/>"
																	class="logInToSeeYourPrice signInOverlay hide-for-mobile ${not product.multidimensional?'pdpredesign-logintoseeyourprice':''} 3 50"><spring:theme
																		code="text.product.logInToSeeYourPrice" /></a>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${product.inventoryFlag}">
																<p class="callBranchForPrice 51">
																	<spring:theme code="text.product.callBranchForPrice"
																		arguments="${contactNo}" />
																</p>
															</c:when>
															<c:otherwise>
																<c:if test="${not empty product.price}">
																	<p class="siteOneListPrice 52">
																		<spring:theme code="text.product.siteOnelistprice" /></p>
																		
																		<c:if test="${isListPrice}">
																	<div class="price_variant 53 ">
																		<div class="row 54">
																			<div class="${(not empty product.bulkUOMPrice)?"col-md-10 ":"col-md-12"} col-sm-12 bulk-price">
																			<product:productPricePanel product="${product}" />
																			</div>
																			<div class="${(not empty product.bulkUOMPrice)?"col-md-2 ":"hidden"} col-sm-12 bulk-uom">
																			<c:if test="${not empty product.bulkUOMPrice}">
																				(${product.bulkUOMPrice}/${product.bulkUOMCode})
																			</c:if>
																			</div>
																		</div>
																	</div>
																	</c:if>
																</c:if>
																<a href="<c:url value="/login"/>"
																	class="logInToSeeYourPrice signInOverlay ${not product.multidimensional?'pdpredesign-logintoseeyourprice':''} 4 55"><spring:theme
																		code="text.product.logInToSeeYourPrice" /></a>
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
																		<p class="callBranchForPrice 56">
																			<spring:theme code="text.product.callBranchForPrice"
																				arguments="${contactNo}" />
																		</p>
																	</c:when>
																	<c:otherwise>
																		<c:if
																			test="${product.variantType eq 'GenericVariantProduct' }">
																			<p class="callBranchForPrice 57">
																				<spring:theme code="text.product.callBranchForPrice"
																					arguments="${contactNo}" />
																			</p>
																		</c:if>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<a href="<c:url value="/login"/>"
																	class="logInToSeeYourPrice signInOverlay ${not product.multidimensional?'pdpredesign-logintoseeyourprice':''} 5 58"><spring:theme
																		code="text.product.logInToSeeYourPrice" /></a>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<p class="callBranchForPrice 59">
															<spring:theme code="text.product.callBranchForPrice"
																arguments="${contactNo}" />
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
						<c:if test="${product.salePrice !=null}">
							<div class="col-md-2 col-sm-4 col-xs-4 actualPrice 60">
								<p class="price" style="color: red;">
									$
									<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"
										value="${product.salePrice}" />
								</p>
							</div>
							<div class="col-md-2 col-sm-4 col-xs-4 prePrice 61">
								<p class="price">
									<del>
										<format:fromPrice priceData="${product.customerPrice}" />
									</del>
								</p>
							</div>
							<br>
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
															<p class="callBranchForPrice 62">
																<spring:theme code="text.product.callBranchForPrice"
																	arguments="${contactNo}" />
															</p>
														</c:when>
														<c:otherwise>
															<c:if test="${not empty product.price && (product.price.value gt product.customerPrice.value)}">
															<div class="${(not product.multidimensional && !isAnonymous)?'pdpredesign-retailprice m-t-15':''} 63 ${(showUOMDropdown && not product.multidimensional && !isAnonymous)?'mar-top-4-uom uom-redesign-rp-hide':''} ">
																<c:if test="${empty firstMulUOMRetailPrice}">
																<p class="siteOneListPrice 63">
																	<spring:theme code="text.product.siteOnelistprice" />
																</p>
																<del class="${(not product.multidimensional && !isAnonymous)?'p-l-15 mar-top-5':''} 64">
																	<product:productPricePanel product="${product}"
																		listPrice="true" />
																</del>
																</c:if>
																<c:if test="${not empty firstMulUOMRetailPrice}">
																<div class="pdpredesign-retailprice-uom">
																<div class="col-md-1 m-r-15">
																<p class="siteOneListPrice 63">
																	<spring:theme code="text.product.siteOnelistprice" />
																</p>
																</div>
																<del class="${(not product.multidimensional && !isAnonymous)? 'p-l-15 mar-top-5':''} 64 ${(showUOMDropdown && not product.multidimensional && !isAnonymous)? 'uom-dropdown-text1':''} ">
																${firstMulUOMRetailPrice}
																</del>
																</div>
																</c:if>
																</div>
																</c:if>
															
															<c:choose>
																<c:when
																	test="${not empty product.sellableUoms && hideUomSelect ne true}">
																	<div class="yourPrice 65"></div>

																		<div class="yourPriceValue large-size ${not product.multidimensional?'martop-padlft':''} 66 ${(showUOMDropdown && not product.multidimensional && !isAnonymous)?'p-l-30 uom-redsg-ftprice ':''}">
																		<div class="row">
																				<div class="${(not empty product.bulkUOMCustomerPrice)?"col-md-10 ":"col-md-12"} col-sm-12 bulk-price uom-dropdown-text">${reDesignPrice}</div>
																				<div class="${(not empty product.bulkUOMCustomerPrice)?"col-md-2 ":"hidden"} col-sm-12 bulk-uom">
																				<c:if test="${not empty product.bulkUOMCustomerPrice}">
																					(${product.bulkUOMCustomerPrice}/${product.bulkUOMCode})
																				</c:if>
																				</div>
																			</div>
																			<div class="select-item-msg hidden">
																				<spring:theme code="select.item.text" />
																			</div>
																		</div>
																	
																</c:when>
																<c:otherwise>
																	<div class="yourPrice 67"></div>

																		<div class="yourPriceValue large-size 68">
																			<div class="row 69">
																				<div class="${(not empty product.bulkUOMCustomerPrice)?"col-md-10 ":"col-md-12"} col-sm-12 bulk-price">
																			<format:fromPrice priceData="${product.customerPrice}" />
																				/ ${uomMeasure}
																			</div>
																				<div class="${(not empty product.bulkUOMCustomerPrice)?"col-md-2 ":"hidden"} col-sm-12 bulk-uom">
																				<c:if test="${not empty product.bulkUOMCustomerPrice}">
																					(${product.bulkUOMCustomerPrice}/${product.bulkUOMCode})
																				</c:if>
																				</div>
																			</div>
																		</div>
																	
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${product.inventoryFlag}">
															<p class="callBranchForPrice 70">
																<spring:theme code="text.product.callBranchForPrice"
																	arguments="${contactNo}" />
															</p>
														</c:when>
														<c:otherwise>
														<div class="pricing-and-callbranch-container 71">
															<p class="callBranchForPrice 72">
																<spring:theme code="text.product.callBranchForPrice"
																	arguments="${contactNo}" /></p>
																<c:if test="${not empty product.price}">
																	<p class="siteOneListPrice 73">
																		<spring:theme code="text.product.siteOnelistprice" /></p>
																		<div class="row 74">
																			<div class="${(not empty product.bulkUOMPrice)?"col-md-10 ":"col-md-12"} col-sm-12 bulk-price"><product:productPricePanel product="${product}"
																				listPrice="true" />
																			</div>
																			<div class="${(not empty product.bulkUOMPrice)?"col-md-2 ":"hidden"} col-sm-12 bulk-uom">
																			<c:if test="${not empty product.bulkUOMPrice}">
																				(${product.bulkUOMPrice}/${product.bulkUOMCode})
																			</c:if>
																			</div>
																		</div>
																	<div></div>
																</c:if>
																</div>
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
															<p class="callBranchForPrice 75">
																<spring:theme code="text.product.callBranchForPrice"
																	arguments="${contactNo}" />
															</p>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when
																	test="${not empty product.sellableUoms && hideUomSelect ne true}">
																	<div class="yourPrice 76"></div>

																		<div class="yourPriceValue large-size ${not product.multidimensional?'martop-padlft':''} 77 ${(showUOMDropdown && not product.multidimensional && !isAnonymous)?'p-l-30 uom-redsg-ftprice ':''}">
																			<div class="row 78">
																				<div class="${(not empty product.bulkUOMCustomerPrice)?"col-md-10 ":"col-md-12"} col-sm-12 bulk-price uom-dropdown-text">${reDesignPrice}</div>
																				<div class="${(not empty product.bulkUOMPrice)?"col-md-2 ":"hidden"} col-sm-12 bulk-uom">
																				<c:if test="${not empty product.bulkUOMPrice}">
																					(${product.bulkUOMPrice}/${product.bulkUOMCode})
																				</c:if>
																				</div>
																			</div>
																			<div class="select-item-msg hidden">
																				<spring:theme code="select.item.text" />
																			</div>
																		</div>
																	
																</c:when>
																<c:otherwise>
																	<div class="yourPrice 79"></div>

																		<div class="yourPriceValue large-size ${not product.multidimensional?'martop-padlft uom-ftsize':''} 80 ${(showUOMDropdown && not product.multidimensional && !isAnonymous)?'p-l-30':''}">
																			<div class="row 81">
																				<div class="${(not empty product.bulkUOMCustomerPrice)?"col-md-10 ":"col-md-12"} col-sm-12 bulk-price">
																				<format:fromPrice priceData="${product.customerPrice}" />
																				/ ${uomMeasure}
																				</div>
																				<div class="${(not empty product.bulkUOMCustomerPrice)?"col-md-2 ":"hidden"} col-sm-12 bulk-uom">
																				<c:if test="${not empty product.bulkUOMCustomerPrice}">
																					(${product.bulkUOMCustomerPrice}/${product.bulkUOMCode})
																				</c:if>
																				</div>
																			</div>
																		</div>
																	
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<p class="callBranchForPrice 82">
														<spring:theme code="text.product.callBranchForPrice"
															arguments="${contactNo}" />
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
															<p class="callBranchForPrice 83">
																<spring:theme code="text.product.callBranchForPrice"
																	arguments="${contactNo}" />
															</p>
														</c:when>
														<c:otherwise>
															<c:if test="${not empty product.price  && (product.price.value gt product.customerPrice.value)}">
																<div class="${(not product.multidimensional && !isAnonymous)?'pdpredesign-retailprice m-t-15':''} 84a ${(not empty product.bulkUOMCustomerPrice)? 'uom-redesign-sticky-mobile': ''}">
																	<p class="siteOneListPrice 84">
																		<spring:theme code="text.product.siteOnelistprice" />
																	</p>
																	<del class="${(not product.multidimensional && !isAnonymous)?'p-l-15 mar-top-5':''}">
																		<product:productPricePanel product="${product}"
																			listPrice="true" />
																	</del>
																</div>
															</c:if>
															<div class="yourPrice 85"></div>

																<div class="yourPriceValue large-size 86">
																	<div class="row 87 ${(not empty product.bulkUOMCustomerPrice)?"upm-redesign-sticky-mobile-yourprice":""}">
																		<div class="${(not empty product.bulkUOMCustomerPrice)?"col-md-10 uom-lft-30 uom-redesign-widthauto":"col-md-12 price-uom-redesign"} col-sm-12 bulk-price">
																		<format:fromPrice priceData="${product.customerPrice}" />
																		/ ${uomMeasure}<br>
																		</div>
																		<div class="${(not empty product.bulkUOMCustomerPrice)?"col-md-12 uom-redsg-bulkprice uom-redesign-sticky-bulkuom":"hidden"} col-sm-12 bulk-uom">
																		<c:if test="${not empty product.bulkUOMCustomerPrice}">
																			(${product.bulkUOMCustomerPrice}/${product.bulkUOMCode})
																		</c:if>
																		</div>
																	</div>
																</div>
															
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${product.inventoryFlag}">
															<p class="callBranchForPrice 88">
																<spring:theme code="text.product.callBranchForPrice"
																	arguments="${contactNo}" />
															</p>
														</c:when>
														<c:otherwise>
															<p class="callBranchForPrice 89">
																<spring:theme code="text.product.callBranchForPrice"
																	arguments="${contactNo}" />
																<c:if test="${not empty product.price}">
																	<div class="${(not product.multidimensional && !isAnonymous)?'pdpredesign-retailprice m-t-15':''} ">
																	<p class="siteOneListPrice 90">
																		<spring:theme code="text.product.siteOnelistprice" />
																	</p>
																	<del class="${(not product.multidimensional && !isAnonymous)?'p-l-15 mar-top-5':''}">
																		<product:productPricePanel product="${product}"
																			listPrice="true" />
																	</del>
																</div>
																</c:if>
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
															<p class="callBranchForPrice 91">
																<spring:theme code="text.product.callBranchForPrice"
																	arguments="${contactNo}" />
															</p>
														</c:when>
														<c:otherwise>
															<div class="yourPrice"></div>

																<div class="yourPriceValue large-size 92">
																	<div class="row 93 ${(not empty product.bulkUOMCustomerPrice)?"upm-redesign-sticky-mobile-yourprice":""}">
																		<div class="${(not empty product.bulkUOMCustomerPrice)?"col-md-12 price-hardscape-bulkuom ":"col-md-12 price-uom-redesign"} col-sm-12 bulk-price">
																		<format:fromPrice priceData="${product.customerPrice}" />
																		/ ${uomMeasure}<br>
																		</div>
																		<div class="${(not empty product.bulkUOMCustomerPrice)?"col-md-12 ":"hidden"} col-sm-12 uom-redsg-bulkprice-92 bulk-uom">
																		<c:if test="${not empty product.bulkUOMCustomerPrice}">
																			(${product.bulkUOMCustomerPrice}/${product.bulkUOMCode})
																		</c:if>
																		</div>
																	</div>
																</div>
															
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<p class="callBranchForPrice 94">
														<spring:theme code="text.product.callBranchForPrice"
															arguments="${contactNo}" />
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
			</ycommerce:testId>
			</div>