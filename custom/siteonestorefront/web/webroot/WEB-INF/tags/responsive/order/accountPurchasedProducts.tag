<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ attribute name="accountpageId" required="true" type="String" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<c:set var="searchUrl" value="/my-account/buy-again/${unitId}?"/>
<c:set value="${buyItAgainSearchPageData.currentQuery.url}" var="buyItAgainQuery"/>
<c:set value='${buyItAgainQuery.replace("/search?", searchUrl)}' var="buyItAgainQueryUrl"/>
<c:set var="currentUrl" value="${pageContext.request.queryString}"/>
<c:set var="toolTipMsg" value="${currentBaseStoreId eq 'siteone' ? 'AvailableToOrder.msg.tooltip' : 'AvailableToOrder.msg.tooltip.ca'}" />
 <div class="p-t-25 p-t-0-xs buy-it-again-container">
<div class="product__list--wrapper">

    <div class="purchased-pagination mb-pagination orders-top-pagination"><nav:buyItAgainPagination top="true" hideRefineButton="false" supportShowPaged="${buyItAgain_isShowPageAllowed}" supportShowAll="${buyItAgain_isShowAllAllowed}"  searchPageData="${buyItAgainSearchPageData}" searchUrl="${buyItAgainQueryUrl}"  numberPagesShown="${buyItAgain_numberPagesShown}"/> </div>
    <c:choose>
	<c:when test="${not empty buyItAgainSearchPageData.results}">
    <div class="cl"></div>
<div class="bia-table row">
				<div class="col-xs-12 hidden-xs hidden-sm no-padding bia-table-header">
					<div class="title-bar">
						<div class="col-xs-3">
							<spring:theme code="basket.page.itemInfo"/>
						</div>
						<div class="col-xs-3">
							<spring:theme code="basket.page.availability"/>
						</div>
						<div class="col-xs-2">
							<spring:theme code="text.account.purchproduct.unitprice"/>
						</div>
						
						<div class="col-xs-2 hidden">
							<spring:theme code="text.account.purchproduct.purchasefrequency"/>
						</div>
						<div class="col-xs-2">
							<spring:theme code="text.account.purchproduct.lastdate"/>
						</div>
						<div class="col-xs-2 padding0">
							<spring:theme code="text.buy.it.again"/>
						</div>
						
					</div>
				</div>
				<c:forEach var="buyItAgainData" items="${buyItAgainSearchPageData.results}">
						<c:forEach var="product" items="${productList}">
						<c:set var="hardscapeMoreOnWayMsgBuyAgain" value="false"/>
						<c:if test="${product.isEligibleForBackorder eq true and product.inventoryCheck eq true and (fn:escapeXml(product.level2Category)== 'Manufactured Hardscape Products'  || fn:escapeXml(product.level2Category) == 'Productos de Paisajismo Manufacturados' || fn:escapeXml(product.level2Category) == 'Natural Stone' || fn:escapeXml(product.level2Category) == 'Piedra Natural'|| fn:escapeXml(product.level2Category) == 'Outdoor Living' || fn:escapeXml(product.level2Category) == 'Vida al Aire Libre')}">
							<c:set var="hardscapeMoreOnWayMsgBuyAgain" value="true"/>
						</c:if>
						<c:if test="${buyItAgainData.productCode eq product.code}">
						<div class="col-xs-12 bia-table-data">
						
						<c:if test="${not empty product.potentialPromotions}">
						<c:if test="${not empty product.couponCode}">
							<div class="buyagain-coupon-web hidden-xs hidden-sm">
								<common:buyagainclipcoupon></common:buyagainclipcoupon>
							</div>
						</c:if>
						</c:if>
						<div class="bia-table-row">
						<div class="js-bia-qty-error js-cart-qty-error marginBottom20 text-red hide" >
        							<img class="icon-red-exclamation cart-qty-alert" src="${themeResourcePath}/images/Exclamation-point.svg" alt""/><spring:theme code="text.product.qty.alert" arguments="${product.stock.stockLevel}" />
        					  </div>
	                        <div class="col-xs-12 col-md-3 col-sm-12 padding-LeftZero">
	                            <div class="hidden-md hidden-lg hidden-sm hidden-xs col-xs-6 col-sm-6 data-title"><spring:theme code="basket.page.itemInfo"/></div>
	                            <div class="col-md-4 col-sm-4 col-xs-4 padding-LeftZero">
	                            <c:url value="${product.url}" var="productUrl"/>
                                        <a class="list-img-sec" href="${productUrl}" title="${buyItAgainData.productDescription}"><c:set var="primaryImage" value="${buyItAgainData.image}" />
<c:choose>
<c:when test="${not empty primaryImage}">
<c:choose>
<c:when test="${not empty primaryImage.altText}">
<img src="${primaryImage.url}" alt="${fn:escapeXml(primaryImage.altText)}" title="${fn:escapeXml(primaryImage.altText)}"/>
</c:when>
<c:otherwise>
<img src="${primaryImage.url}" alt="${fn:escapeXml(buyItAgainData.productDescription)}" title="${fn:escapeXml(buyItAgainData.productDescription)}"/>
</c:otherwise>
</c:choose>
</c:when>
<c:otherwise>
<c:choose>
<c:when test="${not empty buyItAgainData.productDescription}">
  <theme:image code="img.missingProductImage.responsive.thumbnail" alt="${fn:escapeXml(buyItAgainData.productDescription)}" title="${fn:escapeXml(buyItAgainData.productDescription)}" />
</c:when>
<c:otherwise>
<theme:image code="img.missingProductImage.responsive.thumbnail" alt="${fn:escapeXml(buyItAgainData.productItemNumber)}" title="${fn:escapeXml(buyItAgainData.productItemNumber)}" />
</c:otherwise>
</c:choose>
</c:otherwise>
</c:choose></a>
	                               </div>
	                               <div class="col-md-8 col-xs-8">
	                               <a class="green-title row" href="${productUrl}" class="hide-for-mobile"><b>${fn:escapeXml(buyItAgainData.productDescription)}</b></a>
	                               <c:if test="${product.baseProduct != null}">
						                         <c:forEach items="${product.categories}" var="option">
							                                ${option.parentCategoryName}:${option.name}<br/>
						                         </c:forEach>
					                          </c:if>
											         <div class="row"><div class="margin20">${fn:escapeXml(buyItAgainData.productItemNumber)}</div></div>
									   <div class="row" style="display: none;">${fn:escapeXml(buyItAgainData.orderNumber)}</div>
											         
	                            </div>
	                        </div>
	                        
	                         
	                        
	                        
	                        <div class="col-xs-12 col-md-3 col-sm-12">
	                            <div class="hidden-md hidden-lg hidden-sm hidden-xs col-xs-6  col-sm-6 data-title">
	                            	<spring:theme code="basket.page.availability"/>
	                            </div>
	                            <input type="hidden" class="productcspCode" value="${product.code}">
	                              <input type="hidden" value="${product.price.value}" class="productRetailPrice${product.code}" />
	                              <input type="hidden" value="${product.customerPrice.value}" class="csp${product.code}"/>
	                              <input type="hidden" name="HardscapeMoreOnWayMsgBuyAgain" value="${hardscapeMoreOnWayMsgBuyAgain}">
	                                <c:if test="${not empty product.potentialPromotions}">
									<c:if test="${not empty product.couponCode}">
									<c:set var="clipcouponbuyagainMob" value="true"/>
										<div class="col-xs-4 buyagain-coupon hidden-lg hidden-md">
										<common:buyagainclipcouponmob></common:buyagainclipcouponmob>
										</div>
									</c:if>
									</c:if>

	                             <div class="col-md-12 col-sm-12 col-sm-6 col-xs-8 col-md-offset-0 ${clipcouponbuyagainMob? '':'col-xs-offset-4'} orderPage-stock padding0">
	                             <input type="hidden" class="product_stockCheck" value="${(!product.isSellable)}"/>
	                             <input type="hidden" class="BuyAgain_ForceInStock" value="${product.isForceInStock}"/>
	                             <input type="hidden" class="BuyAgain_level1Category" value="${product.level1Category}"/>
	                             <input type="hidden" class="BuyAgain_currentBaseStoreId" value="${currentBaseStoreId}"/>
	                             <input type="hidden" class="BuyAgain_productType" value="${product.productType}"/>
			     <div class="col-xs-12 padding-LeftZero product-notification-wrapper message-center">
						  	  <c:if test="${product.inStockImage}">
								<c:choose>					
									<c:when test="${product.isStockInNearbyBranch}">
										<common:checkmarkIcon iconColor="#ef8700"/>
									</c:when>
									<c:otherwise>
										<common:checkmarkIcon iconColor="#78a22f"/>
									</c:otherwise>	
								</c:choose>
								<span><p>${product.storeStockAvailabilityMsg}</p></span>
							  </c:if>
							  <c:if test="${product.notInStockImage or product.isEligibleForBackorder}">
								  <c:choose>
								  	<c:when test="${hardscapeMoreOnWayMsgBuyAgain}">
										<common:exclamatoryIcon iconColor="#ed8606"/><span><p><spring:theme code="product.outofstock.invhit.forcestock"/> ${sessionStore.name}</p></span>
									</c:when>
									<c:when test="${product.isForceInStock}">
									 	<span class="bold border-radius-3 f-s-12 m-r-5 p-x-5 p-t-6 p-b-1 bold bg-lightgreen message-center-availabletoorder"><common:plpNewCheck height="13" width="13"/></span><span class="f-s-14 text-green"><p>${product.storeStockAvailabilityMsg}</p></span><span class="buyAgain-info-tooltip p-t-3 p-l-5" rel="custom-tooltip"><span class="tooltip-content hide"><spring:theme code="${toolTipMsg}" /></span><common:headerIcon iconName="plpinfotooltip" width="13" height="13" viewBox="0 0 13 13" /></span>
								  	</c:when>
									<c:otherwise>
									 <common:exclamatoryIcon iconColor="#ed8606"/><span><p>${product.storeStockAvailabilityMsg}</p></span>
								  	</c:otherwise>
								  </c:choose>
							  </c:if>
							  <c:if test="${product.outOfStockImage}">
								 <common:crossMarkIcon iconColor="#5a5b5d"/><span><p>${product.storeStockAvailabilityMsg}</p></span>
							  </c:if>
					 </div>
								<c:if test="${product.productType eq 'Nursery'}">
				    				<div class="bold pad-lft-45"><spring:theme code="text.variantProduct.available"/> : ${product.stock.stockLevel}</div> 
								</c:if>
	                            </div>
	                            
	                            
	                            <c:if test="${product.isRegulatoryLicenseCheckFailed eq false}">
	                            	<div class="cl"></div>
									<div class="col-md-11 col-md-offset-1 f-s-12 marginTop20 col-xs-8 col-xs-offset-4 no-padding-xs"><span class="text-orange"><spring:theme code="restricted.license.msg.pdp" /></span> <span class="hidden-xs"><br/></span>
									<span class="js-info-tootip blueLink" rel="custom-tooltip"><span class="tooltip-content hide">
											 <span class=" f-s-12 bold"> <spring:theme code="restricted.license.msg.tooltip1" /> <br/>
											 <spring:theme code="restricted.license.msg.tooltip2" /></span>
										</span>
										<spring:theme code="show.details.msg" />
									</span>
									</div>
								</c:if>
	                            
	                        </div>
	                        <div class="col-xs-12 col-md-2 col-sm-12 padding-LeftZero border-top">
	                            <div class="hidden-md hidden-lg col-xs-6 col-sm-6 data-title">
	                            	<spring:theme code="text.account.purchproduct.unitprice"/>
	                            </div>
	                            <div class="col-md-12 col-sm-12 col-sm-6 col-xs-6 data-data data-margin">
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
												<c:forEach items="${product.sellableUoms}" var="sellableUom2">
													<c:if
														test="${product.hideUom eq true && sellableUom2.hideUOMOnline eq false}">
														<c:set var="hideuom2" value="true" />
														<c:set var="newuomDescription"
															value="${buyItAgainData.productUOMSHortDesc}" />
														<c:set var="uomMeasure" value="${buyItAgainData.productUOMSHortDesc}" />
													</c:if>
												</c:forEach>
												<c:set var="hideUomSelect" value="false"/>
								  <c:forEach items="${product.sellableUoms}" var="sellableUom1">
                                      <c:if test="${hideuom2 ne true}">
                                          <c:set var="hideUomSelect" value="true"/>
										  <c:set var="uomDescription" value="${buyItAgainData.productUOMSHortDesc}"/>
										  <c:set var="uomMeasure" value="${buyItAgainData.productUOMSHortDesc}"/>
                                      </c:if>
                                  </c:forEach>
								 <c:if test="${product.singleUom eq true}">
									 <c:set var="singleUom" value="true"/>
									 <c:set var="uomDescription" value="${product.singleUomDescription}"/>
									 <c:set var="uomMeasure" value="${product.singleUomMeasure}"/>
								 </c:if>
	                            <div class="product-details black-title col-md-12 col-sm-12 col-xs-12" id="uomPrice">
                      <ycommerce:testId
								code="productDetails_productNamePrice_label_${product.code}">
								<input type="hidden" id="listPrice" value="${product.price}"/>
								<input type="hidden" id="customerPrice" value="${product.customerPrice}"/>
							<c:choose>
								<c:when test="${hardscapeMoreOnWayMsgBuyAgain}">
									<p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/></p>
								</c:when>
								<c:when test="${product.customerPrice == null || product.customerPrice.formattedValue eq '$0.00'}">
								 <c:if test="${product.salePrice !=null}">
								 	<div class="col-md-2 col-sm-4 col-xs-4 actualPrice"><p class="price" style="color: red;">$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${product.salePrice}"/></p></div>
								 	<div class="col-md-2 col-sm-4 col-xs-4 prePrice"><del><product:productPricePanel product="${product}"/></del></div><br>
							        </c:if>
							       <c:if test="${product.salePrice == null}">
							       <c:choose>
							        <c:when test="${product.priceRange == null}">
							         <c:choose>
								            <c:when test="${hideList ne true}">
								                 <c:choose>
								                     <c:when test="${hideCSP ne true }">
								                     <c:choose>
								                            <c:when test="${product.inventoryFlag}">
								                              <p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/></p>
								                            </c:when>
								                	       	<c:otherwise>
								                	       	<c:if test="${product.price.value gt product.customerPrice.value}">
													         	<c:if test="${not empty product.sellableUoms && hideUomSelect eq true}">
													         	<c:if test="${not empty product.price}">
								               		       		<p class=""><spring:theme code="text.product.siteOnelistprice"/>
						                        	       		 <product:productPricePanel product="${product}" buyItAgainUOM="${buyItAgainData.productUOMSHortDesc}" isBuyItAgainUOM="true" />
						                        	       		 </c:if>
													       	</c:if>
													          <c:if test="${empty product.sellableUoms && hideUomSelect ne true}">
													          <c:if test="${not empty product.price}">
													       	<p class=""><spring:theme code="text.product.siteOnelistprice"/>
													       	<product:productPricePanel product="${product}"  buyItAgainUOM="${buyItAgainData.productUOMSHortDesc}" isBuyItAgainUOM="true"/>
													       	</c:if>
													         </c:if>
													         </c:if>
													         </c:otherwise>
													         </c:choose>
								                      </c:when>
								                           <c:otherwise>
								                           <c:choose>
								                           <c:when test="${product.inventoryFlag}">
								                              <p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
								                          
								                           </p>
								                           </c:when>
								                              <c:otherwise>
													 	       			<p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>											 	         
													 	        <c:if test="${not empty product.sellableUoms && hideUomSelect eq true}">
													 	        	<c:if test="${not empty product.price}">
													 	        		  <p class=""><spring:theme code="text.product.siteOnelistprice"/>
						                         	 	       		<product:productPricePanel product="${product}"  buyItAgainUOM="${buyItAgainData.productUOMSHortDesc}" isBuyItAgainUOM="true"/>
						                         	 	       		</c:if>
													           </c:if>
													 	          <c:if test="${empty product.sellableUoms && hideUomSelect ne true}">
													 	          	<c:if test="${not empty product.price}">
													 	       		 <p class=""><spring:theme code="text.product.siteOnelistprice"/>
													 	       	   <product:productPricePanel product="${product}"  buyItAgainUOM="${buyItAgainData.productUOMSHortDesc}" isBuyItAgainUOM="true"/>
													 	       	   </c:if>
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
								                     <p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/></p>
								                
								                 </c:when>
								                 <c:otherwise>
								                 </c:otherwise>
								                 </c:choose>
								                   </c:when>
								                   <c:otherwise>
								                   <p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/></p>
								                   </c:otherwise>
								                </c:choose>
								           </c:otherwise>
								          </c:choose>

							         </c:when>
							       <c:otherwise>
							       <c:if test="${!isAnonymous}">
							         <c:choose>
								            <c:when test="${hideList ne true}">
								                <c:choose><c:when test="${hideCSP ne true}">
								                  <c:choose>
								                     <c:when test="${product.inventoryFlag}">
														 <c:choose>
															 <c:when test="${product.variantType eq 'GenericVariantProduct' }">
																 <product:productPricePanel product="${product}"  buyItAgainUOM="${buyItAgainData.productUOMSHortDesc}" isBuyItAgainUOM="true"/>&nbsp;<spring:theme code="select.item.text"/>
															 </c:when>
															 <c:otherwise>
																	 <p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/></p>
															 </c:otherwise>
														 </c:choose>
													 </c:when>
								                     <c:otherwise>
								                     	<c:if test="${not empty product.price}">
								                  			<p class=""><spring:theme code="text.product.siteOnelistprice"/></p>
							                        		<div class="siteoneprice_pdp"><del> <product:productPricePanel product="${product}"  buyItAgainUOM="${buyItAgainData.productUOMSHortDesc}" isBuyItAgainUOM="true" isloggedIn="true" /></del></div><br>
							                        	</c:if>
														 <c:choose>
															 <c:when test="${product.variantType eq 'GenericVariantProduct' }">
																 <product:productPricePanel product="${product}"  buyItAgainUOM="${buyItAgainData.productUOMSHortDesc}" isBuyItAgainUOM="true"/>&nbsp;<spring:theme code="select.item.text"/>
															 </c:when>
															 <c:otherwise>
																 <p class="yourPrice"><spring:theme code="text.product.your.price"/><br>
																 <div class="price"><product:productPricePanel product="${product}"/> ${buyItAgainData.productUOMSHortDesc}</div></p>
															 </c:otherwise>
														 </c:choose>

								                     </c:otherwise>
								                     </c:choose>
								                           </c:when>
								                           <c:otherwise>
								                           	<c:choose>
								                           	<c:when test="${product.inventoryFlag}">
								                              <p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
								                             </p>
								                           </c:when>
								                           <c:otherwise>
								                               <p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
								                               <c:if test="${not empty product.price}">
								                			   <p class=""><spring:theme code="text.product.siteOnelistprice"/></p>
							                        		   <div class="siteoneprice_pdp"><del> <product:productPricePanel product="${product}" isloggedIn="true"  buyItAgainUOM="${buyItAgainData.productUOMSHortDesc}" isBuyItAgainUOM="true"/></del></div><br>
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
																<p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/></p>
															</c:when>
															<c:otherwise>
																<c:if test="${product.variantType eq 'GenericVariantProduct' }">
																	<product:productPricePanel product="${product}"  buyItAgainUOM="${buyItAgainData.productUOMSHortDesc}" isBuyItAgainUOM="true"/>&nbsp;<spring:theme code="select.item.text"/>
																</c:if>
															</c:otherwise>
														</c:choose>
													</c:when>
								                 	<c:otherwise>
														<c:choose>
															<c:when test="${product.variantType eq 'GenericVariantProduct' }">
																<product:productPricePanel product="${product}"  buyItAgainUOM="${buyItAgainData.productUOMSHortDesc}" isBuyItAgainUOM="true"/>&nbsp;<spring:theme code="select.item.text"/>
															</c:when>
															<c:otherwise>
																<p class="yourPrice"><spring:theme code="text.product.your.price"/><br>
																<div class="price"><product:productPricePanel product="${product}"/> ${buyItAgainData.productUOMSHortDesc}</div>
															</c:otherwise>
														</c:choose>
							                      	</c:otherwise>
							                     </c:choose>
								                 </c:when>
								                   <c:otherwise>
													   <c:choose>
														   <c:when test="${product.variantType eq 'GenericVariantProduct' }">
															  <%-- <product:productPricePanel product="${product}"/>&nbsp;<spring:theme code="select.item.text"/>--%>
						 									  <p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
														   </c:when>
														   <c:otherwise>
																<p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
														   </c:otherwise>
													   </c:choose>
													</c:otherwise>
								                </c:choose>
								           </c:otherwise>
								          </c:choose>
							        </c:if>
							         </c:otherwise>
							         </c:choose>
							          <br>
							          </c:if>
							     </c:when>
							<c:otherwise>
							<c:if test="${product.salePrice !=null}">
							     <div class="col-md-2 col-sm-4 col-xs-4 actualPrice"><p class="price" style="color: red;">$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${product.salePrice}"/></p></div>
											<div class="col-md-2 col-sm-4 col-xs-4 prePrice">
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
								                               <p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/></p>
								                            </c:when>
								                    <c:otherwise>
								                   <c:if test="${not empty product.price && (product.price.value gt product.customerPrice.value)}">
								                   <p class=""><spring:theme code="text.product.siteOnelistprice"/></p>
							         		 <del><product:productPricePanel product="${product}"  buyItAgainUOM="${buyItAgainData.productUOMSHortDesc}" isBuyItAgainUOM="true"/></del>
							         		 </c:if>
							       		<p class="yourPrice"><spring:theme code="text.product.your.price"/><br>
							    		 <div class="price"> <format:fromPrice priceData="${product.customerPrice}"/> / ${buyItAgainData.productUOMSHortDesc}</div> </p>
							    		 		</c:otherwise>
							    		 		</c:choose>
							    		     </c:when>
								                           <c:otherwise>
								                           <c:choose>
								                           <c:when test="${product.inventoryFlag}">
								                              <p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/></p>
								                            </c:when>
								                            <c:otherwise>
								                             <p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
								                   <c:if test="${not empty product.price}">
								                   <p class=""><spring:theme code="text.product.siteOnelistprice"/></p>
							         		 		<product:productPricePanel product="${product}"  buyItAgainUOM="${buyItAgainData.productUOMSHortDesc}" isBuyItAgainUOM="true"/>
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
								                           	 <p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/></p>
								                            </c:when>
								                 <c:otherwise>
							       			<p class="yourPrice"><spring:theme code="text.product.your.price"/><br>
							    		 <div class="price"> <format:fromPrice priceData="${product.customerPrice}"/> / ${buyItAgainData.productUOMSHortDesc}</div> </p>
							    		 </c:otherwise>
							    		 </c:choose>
						       		   </c:when>
								                   <c:otherwise>
								                    <p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
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
								                               <p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/></p>
								                    </c:when>
								                    <c:otherwise>
								                    <c:if test="${not empty product.price && (product.price.value gt product.customerPrice.value)}">
								                  <p class=""><spring:theme code="text.product.siteOnelistprice"/></p>
							               <del><product:productPricePanel product="${product}"  buyItAgainUOM="${buyItAgainData.productUOMSHortDesc}" isBuyItAgainUOM="true"/></del>
							               </c:if>	
							         	 <p class="yourPrice"><spring:theme code="text.product.your.price"/><br>
							          <div class="price"> <format:fromPrice priceData="${product.customerPrice}"/> / ${buyItAgainData.productUOMSHortDesc}<br> </div> </p>
							          	</c:otherwise>
							          	</c:choose>
							    		     </c:when>
								                           <c:otherwise>
								                           <c:choose>
								                           <c:when test="${product.inventoryFlag}">
								                              <p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/></p>
								                            </c:when>
								                            <c:otherwise>
								                            <p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
								                          <c:if test="${not empty product.price}">
								                          <p class=""><spring:theme code="text.product.siteOnelistprice"/></p>
							                           <del><product:productPricePanel product="${product}"  buyItAgainUOM="${buyItAgainData.productUOMSHortDesc}" isBuyItAgainUOM="true"/></del>
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
								                              <p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/></p>
								                 </c:when>
								                 <c:otherwise>
							         	           <p class="yourPrice"><spring:theme code="productDetailsPanel.yourPrice" /><br>
							                      <div class="price"> <format:fromPrice priceData="${product.customerPrice}"/><br></div> 
							                      </c:otherwise>
							                      </c:choose>
						       		              </c:when>
								                   <c:otherwise>
								                      <p class="callBranchForPrice"><spring:theme code="text.product.callBranchForPrice" arguments="${contactNo}"/>
								                   </c:otherwise>
								                </c:choose>
								           </c:otherwise>
								          </c:choose>
						        	 </c:otherwise>
							    </c:choose> 
							    </c:if>
						     </c:otherwise> </c:choose>
						</ycommerce:testId>
                    </div>

	                                     
	                            </div>
	                        </div>
	                        <div class="col-xs-12 col-md-2 col-sm-12 border-top hidden">
	                            <div class=" hidden-md hidden-lg col-sm-6 col-xs-6  data-title">
	                            	<spring:theme code="text.account.purchproduct.purchasefrequency"/>
	                            </div>
	                            <div class="col-md-12 col-sm-12  col-sm-6 col-xs-6 statusDisplay data-data data-margin status_col padding-LeftZero">
	                                Frequency
	                                               </div>
	                        </div>
	                         <div class="col-xs-12 col-md-2 col-sm-12 border-top padding-LeftZero">
	                            <div class="hidden-md hidden-lg col-xs-6  col-sm-6 data-title">
	                            	<spring:theme code="text.account.purchproduct.lastdate"/>
	                            </div>
	                            <div class="col-md-12 col-sm-12 col-sm-6 col-xs-6 data-data data-margin">
	                                   <fmt:formatDate value="${buyItAgainData.lastPurchasedDate}" pattern="MMMM dd, yyyy" /> 
	                            </div>
	                        </div>
	                        <div class="col-xs-12 col-md-2 col-sm-12 border-top padding-LeftZero">    
	                            <div class="hidden-md hidden-lg hidden-xs col-xs-6 col-sm-6 data-title">
	                            	<spring:theme code="text.buy.it.again"/>
	                            </div>
	                            <div class="col-md-12 col-sm-6  col-xs-12 data-data data-margin padding-rightZero">
		             				<div  class="qty-button-wrapper row">
        								<div class="qty-container row">
		             						<label for="bia-qty"><spring:theme code="product.product.details.future.qty"/></label>
		             						<input id="bia-qty" class="form-control bia-qty-txt-field" type="text" value="1" data-available-qty="${product.stock.stockLevel}" data-is-nursery="${product.productType eq 'Nursery'}">
    									</div>
    									<c:url value="/cart/add" var="addToCartUrl"/>
		             					<c:if test="${orderOnlinePermissions ne true}">
	                                           <c:set var="ATCOOId" value="orderOnlineATC" />
	                                               <cms:pageSlot position="OnlineOrder" var="feature">
					                                    <cms:component component="${feature}"/>
				                                   </cms:pageSlot>
                                         </c:if>
                                 <form:form method="post" id="addToCartForm" class="add_to_cart_form" action="${addToCartUrl}">
                                      	<c:set var="newuominventoryuomid" value=""/>
                                      	<c:set var="hideList" value="${product.hideList}"/>
                                        <c:set var="hideCSP" value="${product.hideCSP}"/>
                                        <c:set var="isStockAvailable" value="false" />
                                        	  <c:if test="${product.stock.stockLevel gt 0 or (not empty product.stock.inventoryHit and product.stock.inventoryHit >4) or product.isForceInStock}">
                                        		        <c:set var="isStockAvailable" value="true" />
                                        	 </c:if>
                                      	<c:forEach items="${product.sellableUoms}" var="sellableUom2">
                                      		<c:if test="${uomMeasure == sellableUom2.inventoryUOMDesc}" >
                                      			<c:set var="orderEntryInventoryUOMId" value="${sellableUom2.inventoryUOMID}" />
                                      		</c:if>
                                      		<c:if test="${product.hideUom eq true}">
                                      			<c:set var="hideuom2" value="true"/>
                                      			<c:set var="newuomDescription" value="${sellableUom2.inventoryUOMDesc}"/>
                                      			<c:set var="newuominventoryuomid" value="${sellableUom2.inventoryUOMID}"/>
                                      		</c:if>
                                      	</c:forEach>
                                      	<c:choose>
											<c:when test="${not empty orderEntryInventoryUOMId }">
							               		<input type="hidden" id="inventoryUomId" name="inventoryUomId" value="${orderEntryInventoryUOMId}">
							               </c:when>
							               <c:otherwise>
							                   <c:if test="${product.purchasable}">
		                                        	<c:if test="${not empty newuominventoryuomid}">
		                                        		<input type="hidden" id="inventoryUomId" name="inventoryUomId" value="${newuominventoryuomid}">
		                                        	</c:if>
		                                        </c:if>
							               </c:otherwise>
							             </c:choose>
                                      	<c:if test="${product.singleUom eq true}">
                                      		<c:set var="newuommeasure" value="${product.singleUomMeasure}"/>
                                      		<c:set var="newuominventoryuomid" value="${sellableUom2.inventoryUOMID}"/>
                                      	</c:if>
                                        <c:if test="${product.purchasable}">
                                        	<input type="hidden" maxlength="3" size="1" id="qty" name="qty" class="qty js-qty-selector-input qty-hidden-bia" value="1">
                                        </c:if>
                                        <input type="hidden" name="productCodePost" value="${product.code}"/>
                                        <input type="hidden" name="productNamePost" value="${fn:escapeXml(product.name)}"/>
                                      	<input type="hidden" name="storeId" value="${product.stock.fullfillmentStoreId}"/>
                                      	<input type="hidden" id="isSellable"name="isSellable" value="${(!product.isSellable)}"/>
                                        <c:if test="${empty showAddToCart ? true : showAddToCart}">
                                      	      <c:set var="buttonType">button</c:set>
                                      	      <c:if test="${product.variantType ne 'GenericVariantProduct' and product.price ne '' }">
                                      	      	<c:set var="buttonType">submit</c:set>
                                      	      </c:if>
                                      	       <div id="addToCartSection" class="col-md-12">
                                      	       <div class="row">
                                      	      <c:choose>
											  <c:when test="${hardscapeMoreOnWayMsgBuyAgain}">
													<div class="col-md-12 col-lg-12 col-xs-12 paddingRt product-detail-cartbtn bia-button-container">
														<div id="productSelect">
															<button type="submit" class="btn btn-primary pull-left btn-block buy-again-atc hardscapebtn" aria-disabled="true" disabled="disabled"> 
																<spring:theme code="basket.add.to.basket" />
															</button>
														</div>
													</div>
												</c:when>
                                      	      	<c:when test="${fn:contains(buttonType, 'button')}">
                                                        <c:choose>
                                                              <c:when test="${product.variantType eq 'GenericVariantProduct' && product.variantCount != 1}">
                                                                  <div class="variantButton col-md-12 col-lg-12 col-xs-12">
                                                                      <button type="${buttonType}" id="variantButton" class="btn btn-primary btn-block js-add-to-cart" >
                                                                          <spring:theme code="product.base.select.options"/>
                                                                      </button>
                                                                  </div>
                                                              </c:when>
                                                              <c:otherwise>
                                                              <ycommerce:testId code="addToCartButton">
                                                                      <button type="${(orderOnlinePermissions eq true)?buttonType:'button'}" id="${(orderOnlinePermissions eq true)?'addToCartButton':ATCOOId}" class="btn btn-primary btn-block pull-left js-add-to-cart  js-enable-btn buy-again-atc" disabled="disabled">
                                                                      <spring:theme code="basket.add.to.basket" />
                                                                      </button>
                                                              </ycommerce:testId>
                                                              </c:otherwise>
                                                        </c:choose>
                    
  	                	                        </c:when>
  	                	                        <c:otherwise>
  	                	                               <ycommerce:testId code="addToCartButton">
  	                	                             <div class="col-md-12 col-lg-12 col-xs-12 paddingRt product-detail-cartbtn bia-button-container">
  	                	                             <div id="productSelect">
  	                	                              <c:choose>
                                                         <c:when test="${product.sellableUomsCount == 0}">
                                                         		<c:choose>
  	                                                         	   <c:when test="${(!product.isSellable)}">
  	                                                                 <button type="submit" id="notSellable" class="btn btn-primary pull-left btn-block buy-again-atc"
  	                                                                         aria-disabled="true" disabled="disabled"> <spring:theme code="basket.add.to.basket" />
  	                                                                 </button>
  	                                                                 <c:if test="${!isOrderingAccount}">
  	                                                                      <c:set var="alertMsg" value="${orderingAccountMsg}"/>
  	                                                                 </c:if>
  	                                                               </c:when>
  	                                                               <c:otherwise>
  	                	                                               <c:choose>
  	                	                             	                 <c:when test="${(hideCSP eq true)}">
  	                                                                                   <button type="submit" id="showAddtoCart" class="btn btn-primary btn-block buy-again-atc"
  	                                                                                           disabled="disabled">
  	                                                                                           <spring:theme code="basket.add.to.basket" />
  	                                                                                   </button>
  	                                                                     </c:when>
  	                                                                     <c:otherwise>
  	                                                                         <button type="${(orderOnlinePermissions eq true)?'submit':'button'}" id="${(orderOnlinePermissions eq true)?'showAddtoCart':ATCOOId}" class="btn btn-primary btn-block js-enable-btn buy-again-atc"
  	                                                                                 disabled="disabled">
  	                                                                                  <spring:theme code="basket.add.to.basket" />
  	                                                                         </button>
  	                                                                     </c:otherwise>
  	                                                                   </c:choose>
  	                                                               </c:otherwise>
  	                                                            </c:choose>
                            	                         </c:when>
                            	                         <c:otherwise>
  	                			                                <c:choose>
  	                			                        	         <c:when test="${hideList ne true}">
  	                			                        	            <c:choose>
  	                			                        	                  <c:when test ="${hideCSP ne true}">
  	                			                        	                     <c:choose>
  	                			                        	                        <c:when test="${isStockAvailable && !product.outOfStockImage}">
  	                			                        	                            <button type="${(orderOnlinePermissions eq true)?'submit':'button'}" id="${(orderOnlinePermissions eq true)?'showAddtoCart':ATCOOId}" class="btn btn-primary btn-block js-enable-btn buy-again-atc"
  	                			                        				                   disabled="disabled">
  	                			                        			                      <spring:theme code="basket.add.to.basket" />
  	                			                        		                        </button>
  	                			                        	                        </c:when>
  	                			                        	                        <c:otherwise>
  	                			                        	                               <button type="submit" id="showAddtoCart" class="btn btn-primary btn-block buy-again-atc"
  	                                                                                       disabled="disabled">
  	                                                                                       <spring:theme code="basket.add.to.basket" />
  	                                                                                       </button>
  	                			                        	                        </c:otherwise>
  	                			                        	                    </c:choose>
  	                				                                          </c:when>
  	                				                                          <c:otherwise>
  	                				                                               <button type="submit" id="showAddtoCart" class="btn btn-primary btn-block buy-again-atc"
  	                                                                                disabled="disabled">
  	                                                                                <spring:theme code="basket.add.to.basket" />
  	                                                                               </button>
  	                				                                          </c:otherwise>
  	                				                                   </c:choose>
  	                				                               </c:when>
  	                				                           <c:otherwise>
  	                				                                  <c:choose>
  	                				                                    <c:when test="${hideCSP ne true}">
  	                				                                        <c:choose>
  	                				                                          <c:when test="${isStockAvailable && !product.outOfStockImage}">
  	                				                                           <button type="${(orderOnlinePermissions eq true)?'submit':'button'}" id="${(orderOnlinePermissions eq true)?'showAddtoCart':ATCOOId}"class="btn btn-primary btn-block js-enable-btn buy-again-atc"
  	                					   	                         	disabled="disabled">
  	                					   	                              <spring:theme code="basket.add.to.basket" />
  	                					                                     </button>
  	                				                                          </c:when>
  	                				                                          <c:otherwise>
  	                				                                               <button type="submit" id="showAddtoCart" class="btn btn-primary btn-block buy-again-atc"
  	                                                                               disabled="disabled">
  	                                                                               <spring:theme code="basket.add.to.basket" />
  	                                                                               </button>
  	                				                                          </c:otherwise>
  	                				                                          </c:choose>
  	                				                                    </c:when>
  	                				                                    <c:otherwise>
  	                				                                         <button type="submit" id="showAddtoCart" class="btn btn-primary btn-block buy-again-atc"
  	                                                                               disabled="disabled">
  	                                                                               <spring:theme code="basket.add.to.basket" />
  	                                                                       </button>
  	                				                                    </c:otherwise>
  	                				                                  </c:choose>
  	                			                      	        </c:otherwise>
  	                			                      	     </c:choose>
  	                				           <c:choose>
  	                            	                <c:when test="${(!product.isSellable)}">
  	                                                    <button type="submit" id="showAddtoCartUom" class="btn btn-primary pull-left btn-block buy-again-atc" style="display:none;margin-top:0px"
  	                                                            aria-disabled="true" disabled="disabled">
  	                                                           <spring:theme code="basket.add.to.basket" />
  	                                                    </button>
  	                                                </c:when>
  	                               	                <c:otherwise>
  	                                                   <button type="${(orderOnlinePermissions eq true)?'submit':'button'}" id="${(orderOnlinePermissions eq true)?'showAddtoCartUom':ATCOOId}" class="btn btn-primary btn-block js-enable-btn buy-again-atc" style="display:none;margin-top:0px"
  	                                                           disabled="disabled">
  	                                                           <spring:theme code="basket.add.to.basket" />
  	                                                   </button>
  	                                                </c:otherwise>
  	                                           </c:choose>
                            	        </c:otherwise>
                                   </c:choose>
                              </div>
                            </div>
                            </ycommerce:testId>
                        </c:otherwise>
                     </c:choose>
                  </div>
              </div>
          </c:if>
                        	
    </form:form>
		             					                          
		             			
		             				</div>
		             				<div>${alertMsg}</div>
		             				<div class="row">
		             					<div class="marginrow">
		             						<product:addToSaveList product="${product}" wishlist="${allWishlist}" wishlistName="${wishlistName}"/>
		             					</div>		
		             				</div>		             			
		             			</div>
	                        </div>
	                        <div class="cl"></div>             
	                       </div>
	                       </div>
	                       </c:if>
	                       </c:forEach>
	                       </c:forEach>
	                   
				
            </div>
<div class="account-orderhistory-pagination purchased-pagination bia-bottom-pagination">
 	<nav:buyItAgainPagination top="true" hideRefineButton="false" supportShowPaged="${buyItAgain_isShowPageAllowed}" supportShowAll="${buyItAgain_isShowAllAllowed}"  searchPageData="${buyItAgainSearchPageData}" searchUrl="${buyItAgainQueryUrl}"  numberPagesShown="${buyItAgain_numberPagesShown}"/>
   </div>
   </c:when>
 <c:otherwise>
  
   <div class="alert alert-danger cl"> <b><spring:theme code="purchasedProductPage.no.products" /> </b></div>
 </c:otherwise>
   </c:choose>
   
   </div>
   </div>
 
