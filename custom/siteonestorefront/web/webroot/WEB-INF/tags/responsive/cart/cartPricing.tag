<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="grid" tagdir="/WEB-INF/tags/responsive/grid" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
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
	                                                               
	                                                                 <div>
	                                                                 	<c:set var="totalPrice" value="${ycommerce:cartEntryTotalPromotionPrice(entry.quantity, entry.totalPrice)}"/>  
	                                                                    <c:choose>
	                                                                        <c:when test="${ycommerce:isPromotionAppliedWithAdjustedPrice(totalPrice, entry.basePrice, consumedEntry.adjustedUnitPrice)}">
	                                                                             <c:choose>
	                                                                                 <c:when test="${not empty uomDescription}">
	                                                                                    <span class="black-title font-Geogrotesque-bold b-price add_price">  <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/> / ${uomMeasure} </span>
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
	                                                                                      <del><span class="black-title font-Geogrotesque-bold b-price add_price"> <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/> / ${uomMeasure}</span></del> 
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
													<div class="visible-xs visible-sm hidden-lg hidden-md">
													 <c:choose>
														<c:when test="${hideCSP ne true && hideList ne true}">
														<c:choose>
									            			<c:when test="${inventoryFlag eq true }">
									            				<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
									            			</c:when>
									            			<c:otherwise>
																<p class="cart-price-text">
																	<spring:theme code="text.cart.siteOnelistprice" />
																</p>
																<del>
																	<span class="black-title font-Geogrotesque-bold b-price add_price">$<c:choose>
																			<c:when test="${not empty entry.uomPrice}">
																				<fmt:formatNumber maxFractionDigits="${unitpriceDigits}"
																				minFractionDigits="${unitpriceDigits}" value="${entry.uomPrice}" />
																			</c:when>
																			<c:otherwise>
																				${entry.getListPrice()}
																			</c:otherwise>
																		</c:choose>	 /
																		${uomMeasure}
																	</span>
																</del>
															</c:otherwise>
														</c:choose>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${hideCSP ne true}">
																<c:choose>
									            					<c:when test="${inventoryFlag eq true }">
									            						<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
									            					</c:when>
									            					<c:otherwise>
																		<div class="yourpriceGreen">
																		<p class="yourPrice">
																			<spring:theme code="cartItems.yourPrice" /><br>
																		<p>
																		<span><b><format:price priceData="${entry.basePrice}"
																			displayFreeForZero="false"  unitPrice="true"/> / ${uomMeasure}</b></span>
																		</div>
																	</c:otherwise>
																</c:choose>
																</c:when>
																<c:otherwise>
																	<c:choose>
									            					<c:when test="${inventoryFlag eq true }">
									            						<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
									            					</c:when>
									            					<c:otherwise>
									            					</c:otherwise>
									            					</c:choose>
									            				</c:otherwise>
									            			</c:choose>
									            		</c:otherwise>
									            	</c:choose>
									            </div>
													<div class="visible-md visible-lg hidden-sm hidden-xs">
														<c:choose>
															<c:when test="${hideCSP eq true}">
																<c:if test="${hideList ne true}">
																		<p class="cart-price-text"><spring:theme code="text.cart.siteOnelistprice"/></p>
				                                                 		<span class="black-title font-Geogrotesque-bold b-price add_price">$$<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${entry.getListPrice()}" /> / ${uomMeasure}</span>
				                                                 	</c:if>
																		<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
																	
															</c:when>
															<c:otherwise>
																<c:choose>
	                                                                    <c:when test="${inventoryFlag eq true}">
	                                                                    	<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
	                                                                    </c:when>
	                                                                    <c:otherwise>
																			<span class="black-title font-Geogrotesque-bold b-price add_price"><format:price
																			priceData="${entry.basePrice}"
																			displayFreeForZero="false" unitPrice="true"/> / ${uomMeasure}</span>
																		</c:otherwise>
																</c:choose>
															</c:otherwise>
													  </c:choose>
	
													</div>
												</c:when>
	                                                         <c:otherwise>
	                                                            <div class="visible-xs visible-sm hidden-lg hidden-md">
	                                                            <c:choose>
									            <c:when test="${hideList ne true}">
									                <c:choose>
									                	<c:when test="${hideCSP ne true}">
									                		<c:choose>
									            				<c:when test="${inventoryFlag eq true }">
									            					<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
									            				</c:when>
									            				<c:otherwise>
									                  				<p class="cart-price-text"><spring:theme code="text.cart.siteOnelistprice"/></p>
	                                                             	<del><span class="black-title font-Geogrotesque-bold b-price add_price"><b>$$<fmt:formatNumber maxFractionDigits="${unitpriceDigits}" minFractionDigits="${unitpriceDigits}" value="${entry.getListPrice()}" /></b> / ${uomMeasure}</span></del>
	                                                          		<div class="yourpriceGreen"> <p class="yourPrice"><spring:theme code="cartItems.yourPrice" /><br><p>
	                                                           			<span><b><format:price priceData="${entry.basePrice}" displayFreeForZero="false" unitPrice="true"/> / ${uomMeasure}</b></span>
	                                                            	</div> 
	                                                            </c:otherwise>
	                                                       </c:choose>
								    		    		</c:when>
									                           <c:otherwise>
									                         		<c:choose>
									                    				<c:when test="${inventoryFlag eq true }">
									                    					<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
									                    				</c:when>
									                    				<c:otherwise>
									                    					<p class="cart-price-text"><spring:theme code="text.cart.siteOnelistprice"/></p>
	                                                             			<span class="black-title font-Geogrotesque-bold b-price add_price">$${entry.getListPrice()} / ${uomMeasure}</span>
	                                                            		</c:otherwise>
									                    			</c:choose>       
								                        	  </c:otherwise>
									                 </c:choose>
								                  </c:when>
									           <c:otherwise>
									               <c:choose>
									                 <c:when test="${hideCSP eq true}">
								         	           <p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
							       		              </c:when>
									                   <c:otherwise>
									                   <c:choose>
									            		<c:when test="${inventoryFlag eq true }">
									            			<p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
									            		</c:when>
									            		<c:otherwise>
									                    <p class="yourPrice"><spring:theme code="cartItems.yourPrice" /><br>
								                      <div class="black-title font-Geogrotesque-bold b-price add_price"> <format:fromPrice priceData="${product.customerPrice}"/><br></div> </p>
								                      </c:otherwise>
								                      </c:choose>
									                       </c:otherwise>
									                </c:choose>
									           </c:otherwise>
									          </c:choose>
	                                                            </div>
	                                                            
	                                                            
	                                                   <div class="visible-md visible-lg hidden-sm hidden-xs">          
	                                                                <c:choose>
	                                                                     <c:when test="${hideCSP eq true}">
	                                                                        <p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
	                                                                          </c:when>
	                                                                    <c:otherwise>
	                                                                <format:price priceData="${entry.basePrice}" displayFreeForZero="false" unitPrice="true"/>
	                                                                    </c:otherwise></c:choose></div>
	                                                               
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
																			                   <span class="black-title font-Geogrotesque-bold b-price add_price"> <format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true" /> / ${uomMeasure}</span>
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
		                                                                        <del> <span class="black-title font-Geogrotesque-bold b-price add_price"><format:price priceData="${entry.basePrice}" displayFreeForZero="true" unitPrice="true"/> / ${uomMeasure}</span></del>
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
		                                                                         	<p class="cart-price-text"><spring:theme code="text.cart.siteOnelistprice"/></p>
		                                                              			 	<span class="black-title font-Geogrotesque-bold b-price add_price"> <format:price priceData="${entry.basePrice}" displayFreeForZero="false" unitPrice="true"/> / ${uomMeasure} </span>
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
		                                                                						<p class="yourPrice"><spring:theme code="cartItems.yourPrice" /><br>
									                      										<div class="black-title font-Geogrotesque-bold b-price add_price"> <format:fromPrice priceData="${product.customerPrice}"/><br></div> </p>
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
		                                                                						 		<p class="cart-price-text"><spring:theme code="text.cart.siteOnelistprice"/></p>
		                                                             							 		<span class="black-title font-Geogrotesque-bold b-price add_price">$${entry.getListPrice()} / ${uomMeasure}</span>
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
		                                                                         	<p class="cart-price-text"><spring:theme code="text.cart.siteOnelistprice"/></p>
		                                                              			 	<span class="black-title font-Geogrotesque-bold b-price add_price"> <format:price priceData="${entry.basePrice}" displayFreeForZero="false" unitPrice="true"/> / ${uomMeasure} </span>
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
		                                                                						 <p class="yourPrice"><spring:theme code="cartItems.yourPrice" /><br>
									                      										 <div class="black-title font-Geogrotesque-bold b-price add_price"> <format:fromPrice priceData="${product.customerPrice}"/><br></div> </p>
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
		                                                                						 		<p class="cart-price-text"><spring:theme code="text.cart.siteOnelistprice"/></p>
		                                                             							 		<span class="black-title font-Geogrotesque-bold b-price add_price">$${entry.getListPrice()} / ${uomMeasure}</span>
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