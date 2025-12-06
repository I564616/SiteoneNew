<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ attribute name="entry" required="true" type="de.hybris.platform.commercefacades.order.data.OrderEntryData" %>
<%@ attribute name="loopIndex" required="false" %>
<%@ attribute name="isPriceAvailable" required="false" %>
<%@ attribute name="consumedEntry" required="false" %>
<%@ attribute name="uomDescription" required="false" %>
<%@ attribute name="uomMeasure" required="false" %>
<%@ attribute name="unitpriceDigits" required="false" %>
<%@ attribute name="totalPrice" required="false" %>
<%@ attribute name="hideCSP" required="false" %>
<%@ attribute name="hideList" required="false" %>
<%@ attribute name="inventoryFlag" required="false" %>
<%@ attribute name="isAnonymous" required="false" %>
<%@ attribute name="quantityBoxDisable" required="false" %>
<%@ attribute name="cartUpdateFormAction" required="false" %>
<%@ attribute name="requestedQty" required="false" %>
<%@ attribute name="homeStoreQty" required="false" %>
<%@ attribute name="nearbyStoresQty" required="false" %>
<!-- entry: ${entry}~~<br>
loopIndex: ${loopIndex}~~<br>
isPriceAvailable: ${isPriceAvailable}~~<br>
cartData: ${cartData}~~<br>
promotion: ${promotion}~~<br>
consumedEntry: ${consumedEntry}~~<br>
uomDescription: ${uomDescription}~~<br>
uomMeasure: ${uomMeasure}~~<br>
unitpriceDigits: ${unitpriceDigits}~~<br>
totalPrice: ${totalPrice}~~<br>
hideCSP: ${hideCSP}~~<br>
hideList: ${hideList}~~<br>
inventoryFlag: ${inventoryFlag}~~<br>
product: ${product}~~<br>
isAnonymous: ${isAnonymous}~~<br>
quantityBoxDisable: ${quantityBoxDisable}~~<br>
cartUpdateFormAction: ${cartUpdateFormAction}~~<br>
requestedQty: ${requestedQty}~~<br>
homeStoreQty: ${homeStoreQty}~~<br>
nearbyStoresQty: ${nearbyStoresQty}~~<br> -->
<div class="col-md-12 retail-your-price-section col-xs-5">
	<c:if test="${isPriceAvailable}">
		<c:choose>
			<c:when test="${entry.isCustomerPrice}">
				<c:choose>
					<c:when test="${ycommerce:doesAppliedPromotionExistForOrderEntry(cartData, entry.entryNumber)}">
						<c:forEach items="${cartData.appliedProductPromotions}" var="promotion">
							<c:set var="displayed" value="false" />
							<c:forEach items="${promotion.consumedEntries}" var="consumedEntry">
								<c:if test="${not displayed && consumedEntry.orderEntryNumber == entry.entryNumber}">
									<c:set var="displayed" value="true" />
									<c:set var="totalPrice" value="${ycommerce:cartEntryTotalPromotionPrice(entry.quantity, entry.totalPrice)}"/>
									<div>
										<c:choose>
											<c:when test="${ycommerce:isPromotionAppliedWithAdjustedPrice(totalPrice, entry.basePrice, consumedEntry.adjustedUnitPrice)}">
												<c:choose>
													<c:when test="${not empty uomDescription}">
														<span class="black-title  b-price add_price">  <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/> / ${uomMeasure} </span>
													</c:when>
													<c:otherwise>
														<format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/>   
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${not empty uomDescription}">
														<p class="discountPrice-cartPage discount-price">$<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${totalPrice}" /> / ${uomMeasure}</p>
														<del><span class="black-title b-price add_price"> <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/> / ${uomMeasure}</span></del> 
													</c:when>
													<c:otherwise>
														<p class="discountPrice-cartPage discount-price">$<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${totalPrice}" /></p>
														<del> <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/> </del>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</div>
								</c:if>
							</c:forEach>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${not empty uomDescription}">
								<div>
									<c:choose>
										<c:when test="${hideCSP eq true}">
											<c:if test="${hideList ne true}">
												<p class="cart-price-text hidden-print"><spring:theme code="text.cart.siteOnelistprice"/></p>
												<span class="black-title  b-price add_price">$$<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${entry.getListPrice()}" /> / ${uomMeasure}</span>
											</c:if>
											<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${inventoryFlag eq true}">
													<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
												</c:when>
												<c:otherwise>
													<c:if test="${entry.basePrice ne null}">
														<p class="cart-price-text hidden-print"><spring:theme code="cartItems.yourPrice" /></p>
													</c:if>
													<span class="black-title b-price add_price"><format:price
													priceData="${entry.basePrice}"
													displayFreeForZero="false" unitPrice="true"/> / ${uomMeasure}</span>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</div>
							</c:when>
							<c:otherwise>
								<div class="">          
									<c:choose>
										<c:when test="${hideCSP eq true}">
											<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
										</c:when>
										<c:otherwise>
										<c:if test="${entry.basePrice ne null}">
											<p class="cart-price-text hidden-print"><spring:theme code="cartItems.yourPrice" /></p>
										</c:if>
										<format:price priceData="${entry.basePrice}" displayFreeForZero="false" unitPrice="true"/>
										</c:otherwise>
									</c:choose>
								</div>
							</c:otherwise>
						</c:choose>	                                                
					</c:otherwise>	
				</c:choose>	                                           
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${ycommerce:doesAppliedPromotionExistForOrderEntry(cartData, entry.entryNumber)}">
						<c:forEach items="${cartData.appliedProductPromotions}" var="promotion">
							<c:set var="displayed" value="false" />
							<c:forEach items="${promotion.consumedEntries}" var="consumedEntry">
								<c:if test="${not displayed && consumedEntry.orderEntryNumber == entry.entryNumber}">
									<c:set var="displayed" value="true" />
									<div class=""><c:set var="totalPrice" value="${ycommerce:cartEntryTotalPromotionPrice(entry.quantity, entry.totalPrice)}"/>  
										<c:choose>
											<c:when test="${ycommerce:isPromotionAppliedWithAdjustedPrice(totalPrice, entry.basePrice, consumedEntry.adjustedUnitPrice)}">
												<c:choose>
													<c:when test="${not empty uomDescription}">
														<span class="black-title  b-price add_price"> <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true" /> / ${uomMeasure}</span>
													</c:when>
													<c:otherwise>
														<format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true" />
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${not empty uomDescription}">
														<p class="discountPrice-cartPage discount-price">$<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${totalPrice}" /> / ${uomMeasure}</p>
														<del> <span class="black-title  b-price add_price"><format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/> / ${uomMeasure}</span></del>
													</c:when>
													<c:otherwise>
														<p class="discountPrice-cartPage discount-price">$<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${totalPrice}" /></p>
														<del> <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/></del>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</div>
								</c:if>
							</c:forEach>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${not empty uomDescription}">
								<c:choose>
									<c:when test="${hideList ne true && hideCSP ne true}">
										<c:choose>
											<c:when test="${inventoryFlag eq true }">
												<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
											</c:when>
											<c:otherwise>
												<p class="cart-price-text hidden-print"><spring:theme code="text.cart.siteOnelistprice"/></p>
												<span class="black-title  b-price add_price"> <format:price priceData="${entry.basePrice}" displayFreeForZero="false" unitPrice="true"/> / ${uomMeasure} </span>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${hideCSP ne true}">
												<c:choose>
													<c:when test="${inventoryFlag eq true}">
														<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
													</c:when>
													<c:otherwise>
														<sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')" var="loggedIn">
															<c:if test="${product.customerPrice ne null}">
																<p class="cart-price-text hidden-print"><spring:theme code="cartItems.yourPrice" /></p>
															</c:if>
															<div class="black-title font-Geogrotesque-bold b-price add_price"> <format:fromPrice priceData="${product.customerPrice}"/><br></div> </p>
														</sec:authorize>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${hideList eq true }">
														<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${inventoryFlag eq true}">
																<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
															</c:when>
															<c:otherwise>
																<p class="cart-price-text hidden-print"><spring:theme code="text.cart.siteOnelistprice"/></p>
																<span class="black-title  b-price add_price">$${entry.getListPrice()} / ${uomMeasure}</span>
																<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${hideList ne true && hideCSP ne true}">
										<c:choose>
											<c:when test="${inventoryFlag eq true }">
												<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
											</c:when>
											<c:otherwise>
												<p class="cart-price-text hidden-print"><spring:theme code="text.cart.siteOnelistprice"/></p>
												<span class="black-title b-price add_price"> <format:price priceData="${entry.basePrice}" displayFreeForZero="false" unitPrice="true"/> / ${uomMeasure} </span>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${hideCSP ne true}">
												<c:choose>
													<c:when test="${inventoryFlag eq true}">
														<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
													</c:when>
													<c:otherwise>
														<sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')" var="loggedIn">
															<c:if test="${product.customerPrice ne null}">
															<p class="cart-price-text hidden-print"><spring:theme code="cartItems.yourPrice" /></p>
															</c:if>
															<div class="black-title font-Geogrotesque-bold b-price add_price"> <format:fromPrice priceData="${product.customerPrice}"/><br></div> </p>
														</sec:authorize>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${hideList eq true }">
														<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${inventoryFlag eq true}">
																<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
															</c:when>
															<c:otherwise>
																<p class="cart-price-text hidden-print"><spring:theme code="text.cart.siteOnelistprice"/></p>
																<span class="black-title b-price add_price">$${entry.getListPrice()} / ${uomMeasure}</span>
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</c:if>
</div>
<!-- Mobile Only -->
<div class="text-right xs-cart-item-total no-padding-lft-xs col-xs-6 hidden-lg  hidden-md p-r-0">                
	<c:if test="${isPriceAvailable}">
		<ycommerce:testId code="cart_totalProductPrice_label">
			<div class="js-item-total text-right  no-margin h3 ">
				<c:choose>
					<c:when test="${hideCSP eq true}">$0.00</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${inventoryFlag eq true}">$0.00</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${!isAnonymous || (isAnonymous && hideList ne true)}">
										<format:price priceData="${entry.totalPrice}" displayFreeForZero="true" />
									</c:when>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>		
			</div>
		</ycommerce:testId>
	</c:if>                                           
</div>