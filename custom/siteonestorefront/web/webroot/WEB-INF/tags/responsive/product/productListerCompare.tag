<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ attribute name="loop" type="java.lang.String" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>
<c:choose>
	<c:when test="${empty cookie['gls'] && sessionStore.storeId eq '172'}">
		<c:set var="contactNo" value="<%=de.hybris.platform.util.Config.getParameter(\"siteOne.Customer.Service.Number\")%>"/>		
	</c:when>
	<c:otherwise>
		<c:set var="contactNo" value="${sessionStore.address.phone}"/>
	</c:otherwise>
</c:choose>
	<div class="product-item col-xs-6 col-sm-4">
	<div class="product-item-box">
	<a class="remove_compare_product pull-right">X</a>
<spring:theme code="text.addToCart" var="addToCartText"/>
<spring:url value="/" var="homelink" htmlEscape="false"/>
<c:url value="${product.url}" var="productUrl"/>
<c:set var="hideList" value="${product.hideList}"/>
<c:set var="hideCSP" value="${product.hideCSP}"/>
<c:set value="${not empty product.potentialPromotions}" var="hasPromotion"/>
<c:set var="sellableUomListLen" value="${fn:length(product.sellableUoms)}" />
	<c:if test="${(!product.multidimensional || product.variantCount == 1) && product.isRegulateditem}">
	    <c:set var="isProductSellable" value="false" />
		<c:forEach items="${product.regulatoryStates}" var="regulatoryStates">
			<c:if test="${regulatoryStates eq sessionStore.address.region.isocodeShort}">
				<c:set var="isProductSellable" value="true" />
			</c:if>
		</c:forEach>
	</c:if>
		<c:if test="${not empty product.sellableUoms}">
	<c:forEach items="${product.sellableUoms}" var="sellableUom">
		<c:set var="uomDescription" value="${sellableUom.inventoryUOMDesc}"/>
		<c:set var="uomMeasure" value="${sellableUom.measure}"/>
	 </c:forEach>
	</c:if>
	<div class="cl"></div>
	<ycommerce:testId code="product_wholeProduct">
		<a class="thumb" href="${productUrl}" title="${fn:escapeXml(product.name)}">
			<product:productPrimaryImage product="${product}" format="product"/>
			<c:if test="${(!product.multidimensional || product.variantCount == 1) && not empty product.productPromotion && not empty product.price}">
			<div class="on-sale-tag" style="bottom: 30px;">On Sale</div></c:if>
			<c:if test="${product.isRegulateditem}">
			<div class="regulated-tag" style="bottom: 2px;" title="Restrictions on sale or use may apply to this item"><spring:theme code="productlisterCompare.regulated" /></div></c:if>
		</a>
		<div class="details">
		<input type="hidden" value="${product.code}" id="productCode"/>
		<div class="product-stock stock">
		<c:out value="${product.itemNumber}" />
			<c:set var="isMyStoreProduct" value="false" />
			<c:forEach items="${product.stores}" var="store">
				<c:if test="${store eq sessionStore.storeId}">
					<c:set var="isMyStoreProduct" value="true" />
				</c:if>
			</c:forEach>
					
			<c:if test="${!product.multidimensional || product.variantCount == 1}">		
				<c:if test="${product.isRegulateditem}">
					<c:if test="${isMyStoreProduct && !isProductSellable}"><div class="cl"></div><spring:theme text="${regulatedItemNotApprovedMsg}" arguments="${contactNo}"/> <div class="cl"></div></c:if>
					<c:if test="${!isMyStoreProduct && isProductSellable}"><div class="cl"></div><spring:theme text="${regulatedItemApprovedMsg}" arguments="${contactNo}"/><div class="cl"></div></c:if>
					<c:if test="${isMyStoreProduct && isProductSellable}">
						<c:choose>
							<c:when test="${product.isSellable}">
								<spring:theme text="${instockMsg}" arguments="${contactNo}"/><div class="cl"></div>
							</c:when>
							<c:otherwise>
								<div class="cl"></div><spring:theme text="${callStoreForAvailabilityMsg}" arguments="${contactNo}"/><div class="cl"></div>
							</c:otherwise>	
						</c:choose>
					</c:if>
			   	</c:if>	   	
			   	<c:if test="${!product.isRegulateditem  && product.isProductDiscontinued}">
			   		<c:choose>
			   			<c:when test="${isMyStoreProduct && product.isSellable && product.isSellableInventoryHit}">
							<div class="cl"></div><spring:theme text="${callStoreForAvailabilityMsg}" arguments="${contactNo}"/><div class="cl"></div>
						</c:when>
						<c:when test="${isMyStoreProduct && product.isSellable}">
							<spring:theme text="${instockMsg}" arguments="${contactNo}"/><div class="cl"></div>
						</c:when>
						<c:otherwise>
							<div class="cl"></div><spring:theme text="${callStoreForAvailabilityMsg}" arguments="${contactNo}"/><div class="cl"></div>
						</c:otherwise>	
					</c:choose>
			   	</c:if>		   	
			   	<c:if test="${!product.isRegulateditem   && !product.isProductDiscontinued}">
			   		<c:choose>
			   		
						<c:when test="${isMyStoreProduct && product.isSellable}">
							<spring:theme text="${instockMsg}" arguments="${contactNo}"/><div class="cl"></div>
						</c:when>
						<c:otherwise>
							<div class="cl"></div><spring:theme text="${callStoreForAvailabilityMsg}" arguments="${contactNo}"/><div class="cl"></div>
						</c:otherwise>	
					</c:choose>
			   	</c:if>
			</c:if>
		</div>
			
			
<div class="cl"></div>
			<ycommerce:testId code="searchPage_productName_link_${product.code}">
			<a class="green-title" href="${productUrl}">${fn:escapeXml(product.name)}</a>  
			<div class="cl"></div>
			</ycommerce:testId>
			<c:if test="${not empty product.potentialPromotions}">
				<div class="promo">
					<c:forEach items="${product.potentialPromotions}" var="promotion">
						${promotion.description}
					</c:forEach>
				</div>
			</c:if>
			
			<ycommerce:testId code="product_productPrice">
			<span id="promotionlabel_${loop}">
			</span>
			  <div class="price">
					<c:if test="${empty product.price && empty product.priceRange.minPrice}">
					 	<h5><spring:theme text="${priceUnavailableMsg}" arguments="${contactNo}"/></h5>
					</c:if> 
					<c:if test="${not empty product.price || not empty product.priceRange.minPrice}">
						 <c:choose>
                                <c:when test="${hideList eq true}">
                                </c:when>
                                <c:otherwise>
                                <c:if test="${(product.price.value gt product.customerPrice.value && !isAnonymous) || isAnonymous}">
                              <h5><spring:theme code="text.product.siteOnelistprice"/></h5>
						<div class="productPriceWrapper">
							<h5 class="price-container">
								<span class="black-title">
									<span id="salePrice${loop}" class="discount sales-price"></span>
								</span>
								<span id="basePrice${loop}">
									<product:productListerItemPrice product="${product}" />
								</span>
								<div class="csperror display-none" id="cspError${loop}"></div>
							</h5>
						</div>
								</c:if>
                                </c:otherwise>
                                </c:choose>
					</c:if>
			 	</div>
					 
			</ycommerce:testId>
			<div class="product-promotion-wrapper">
				<c:if test="${(!product.multidimensional || product.variantCount == 1) && not empty product.productPromotion && not empty product.price}">
					<input id="productPromotion${loop}" type="hidden" data-price="${product.price.formattedValue}" value="${product.productPromotion}">
				</c:if>
			</div>
			
			<div class="variant-option-wrapper">
				<c:forEach var="variantOption" items="${product.variantOptions}">
					<c:forEach items="${variantOption.variantOptionQualifiers}" var="variantOptionQualifier">
						<c:if test="${variantOptionQualifier.qualifier eq 'rollupProperty'}">
		                    <c:set var="rollupProperty" value="${variantOptionQualifier.value}"/>
		                </c:if>
						<c:if test="${variantOptionQualifier.qualifier eq 'thumbnail'}">
		                    <c:set var="imageUrl" value="${variantOptionQualifier.value}"/>
		                </c:if>
		                <c:if test="${variantOptionQualifier.qualifier eq rollupProperty}">
		                    <c:set var="variantName" value="${variantOptionQualifier.value}"/>
		                </c:if>
					</c:forEach>
					<img style="width: 32px; height: 32px;" src="${imageUrl}" title="${variantName}" alt="${variantName}"/>
				</c:forEach>
			</div>
			<c:if test="${!product.multidimensional || product.variantCount == 1}">
						<sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')">
							<c:if test="${not empty product.price || not empty product.priceRange.minPrice}">
							<c:choose>
                                <c:when test="${hideCSP eq true}">
                                </c:when>
                                <c:otherwise>
                               <c:choose>
								<c:when
									test="${(not empty product.customerPrice && product.customerPrice.value ne '0.0')}">
									<div id="mycspDiv${product.code}">
									<input type="hidden" value="${product.code}" class="productcspCode" />
									<input type="hidden" value="${product.isStockAvailable}" class="isStockAvailablecsp${product.code}" />
									<input type="hidden" value="${product.customerPrice.value}" class="csp${product.code}"/>
									<input type="hidden" value="${product.isRegulateditem}" class="isRegulateditemcsp${product.code}" />
									<input type="hidden" value="${isMyStoreProduct}" class="isMyStoreProduct${product.code}" />
									<input type="hidden" value="${sessionStore.isLicensed}" class="isLicensed${product.code}" />
									<input type="hidden" value="${isOrderingAccount}" class="isOrderingAccount${product.code}" />
									<input type="hidden" value="${product.isSellableInventoryHit}" class="isSellableInventoryHit${product.code}" />
									
									<div class="customerSpecificPrice-wrapper col-md-12 row">
										<div class="check_price">
											<spring:theme code="text.product.your.price" />
											<div class="cl"></div>
											<product:ProductCSPListerItems product="${product}" />
											<div class="cl"></div>
											<span id="description${loop}"></span>
										</div>
									</div>
									</div>
								</c:when>
								<c:otherwise>
									<p class="callBranchForPrice price-position">
										<spring:theme code="text.product.callforpricing" />
								</c:otherwise>
							</c:choose></c:otherwise></c:choose>
							</c:if>
						</sec:authorize>
						<sec:authorize access="hasAnyRole('ROLE_ANONYMOUS')">
						     <c:if test="${hideCSP ne true}">
								           <a href="<c:url value="/login"/>" class="logInToSeeYourPrice signInOverlay"><spring:theme code="text.product.logInToSeeYourPrice"/></a>
							             </c:if>
						
						</sec:authorize>
				</c:if>
				
		</div>

		<c:set var="product" value="${product}" scope="request"/>
		<c:set var="CartText" value="${addToCartText}" scope="request"/>
		<c:set var="isGrid" value="true" scope="request"/>
		<div class="addtocart" style="display:grid">
			<c:if test="${cmsPage.uid eq 'siteOnePromotionSearchPage'}">
				<cms:component uid="ListAddToCartAction"/>
				<cms:component uid="ListOrderFormAction"/>
			</c:if>
			<div class="actions-container-for-${component.uid} <c:if test="${ycommerce:checkIfPickupEnabledForStore() and product.availableForPickup}"> pickup-in-store-available</c:if>">
				<action:actions element="div" parentComponent="${component}"/>
			</div>
	
		</div>
							
	</ycommerce:testId>
	

<c:url value="${product.url}/configuratorPage/${configuratorType}" var="configureProductUrl"/>

<product:addToCartTitle/>

<form:form method="post" id="configureForm" class="configure_form" action="${configureProductUrl}">
<c:if test="${product.purchasable}">
	<input type="hidden" maxlength="3" size="1" id="qty" name="qty" class="qty js-qty-selector-input" value="1"> 
</c:if>
<input type="hidden" name="productCodePost" value="${product.code}"/>
<input type="hidden" name="productNamePost" value="${product.name}"/>
<input type="hidden" name="storeId" value="${product.stock.fullfillmentStoreId}"/>
<c:if test="${empty showAddToCart ? true : showAddToCart}">
	<c:set var="buttonType">button</c:set>
	<c:if test="${product.purchasable and product.stock.stockLevelStatus.code ne 'outOfStock' }">
		<c:set var="buttonType">submit</c:set>
	</c:if>
	<c:choose>
		<c:when test="${fn:contains(buttonType, 'button')}">
			<c:if test="${product.configurable}">
				<button id="configureProduct" type="button" class="btn btn-primary btn-block js-enable-btn outOfStock" disabled="disabled">
					<spring:theme code="basket.configure.product"/>
				</button>
			</c:if>
		</c:when>
		<c:otherwise>
            <c:if test="${product.configurable}">
                <button id="configureProduct" type="${buttonType}" class="btn btn-primary btn-block js-enable-btn" disabled="disabled"
                        name="configure">
                    <spring:theme code="basket.configure.product"/>
                </button>
            </c:if>
		</c:otherwise>
	</c:choose>
</c:if>
</form:form>
<form:form method="post" id="addToCartForm" class="add_to_cart_form" action="${homelink}cart/add">
<c:set var="newuominventoryuomid" value=""/>
	<input type="hidden" id="isCouponEnabled" name="isCouponEnabled" value="false">
	<input type="hidden" id="promoProductCode" name="promoProductCode" value="">
	<c:forEach items="${product.sellableUoms}" var="sellableUom2">
		<c:if test="${product.hideUom eq true}">
			<c:set var="newuominventoryuomid" value="${sellableUom2.inventoryUOMID}"/>
		</c:if>
	</c:forEach>
	<c:if test="${product.singleUom eq true}">
		<c:set var="newuominventoryuomid" value="${sellableUom2.inventoryUOMID}"/>
	</c:if>
<c:if test="${product.purchasable}">
	<input type="hidden" maxlength="3" size="1" id="qty" name="qty" class="qty js-qty-selector-input" value="1">
	<input type="hidden" id="inventoryUomId" name="inventoryUomId" value="${newuominventoryuomid}"> 
</c:if>
<input type="hidden" name="productCodePost" value="${product.code}"/>
<input type="hidden" name="productNamePost" value="${product.name}"/>

<input type="hidden" id="isSellable"name="isSellable" value="${(!product.isSellable)}"/>
<c:if test="${empty showAddToCart ? true : showAddToCart}">
	<c:set var="buttonType">button</c:set>
	<c:if test="${product.variantType ne 'GenericVariantProduct' and product.price ne '' }">
		<c:set var="buttonType">submit</c:set>
	</c:if>
	 <div id="addToCartSection" class="row">
	<c:choose>
		<c:when test="${fn:contains(buttonType, 'button')}">
		
                    <c:choose>
                        <c:when test="${product.variantType eq 'GenericVariantProduct' && product.variantCount != 1}">
                            <div class="variantButton col-md-6 col-lg-7 col-xs-12">
                                <button type="${buttonType}" id="variantButton" class="btn btn-primary btn-block js-add-to-cart" >
                                    <spring:theme code="product.base.select.options"/>
                                </button>
                            </div>
                            <a href="#" class="addToListPosition" onclick="return false;"><spring:theme code="text.product.listingOfProducts"/></a>
                        </c:when>
                        <c:otherwise>
                        <ycommerce:testId code="addToCartButton">
                                <button type="${buttonType}" id="addToCartButton" class="btn btn-primary btn-block pull-left js-add-to-cart  js-enable-btn" disabled="disabled">
                                <spring:theme code="basket.add.to.basket" />
                                </button>
                                </ycommerce:testId>
                        </c:otherwise>
                    </c:choose>
                     
			</c:when>
			<c:otherwise>
			  <ycommerce:testId code="addToCartButton">
			<div class="col-md-12 col-lg-12 col-xs-12">
			<div id="productSelect">
			 <c:choose>
            	<c:when test="${product.sellableUomsCount == 0 || sellableUomListLen==1}"> 
            		<c:choose>
		            	<c:when test="${(!product.isSellable)}"> 
		                    <button type="submit" id="notSellable" class="btn btn-primary pull-left btn-block"
		                            aria-disabled="true" disabled="disabled"> <spring:theme code="basket.add.to.Basket"/>
		                    </button>
		                    <c:if test="${!isOrderingAccount}">
		                         <span>${orderingAccountMsg}</span> 
		                    </c:if>
		                </c:when>
		                <c:otherwise>
		                    <c:choose>
							        
								            <c:when test="${hideList ne true}">
								            <c:if test="${hideCSP eq true}">
		                                        <p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
		                                       </c:if>
								               <button type="submit" id="showAddtoCart" class="btn btn-primary btn-block js-enable-btn" disabled="disabled"><spring:theme code="basket.add.to.Basket"/>
		                                        </button>
							                  </c:when>
								           <c:otherwise>
								               <c:choose>
								                 <c:when test="${hideCSP ne true}">
								                 <button type="submit" id="showAddtoCart" class="btn btn-primary btn-block js-enable-btn" disabled="disabled"><spring:theme code="basket.add.to.Basket"/>
		                                         </button>
						       		              </c:when>
								                   <c:otherwise>
								                    <p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
								                   <button type="submit" id="showAddtoCart" class="btn btn-primary btn-block" disabled="disabled"><spring:theme code="basket.add.to.Basket"/>
		                                        </button>  
								                   </c:otherwise>
								                </c:choose>
								           </c:otherwise>
								          </c:choose>
		                </c:otherwise>
		            </c:choose>
            	</c:when>
            	<c:otherwise>
            		<button type="button" id="sellableUoms" class="btn btn-primary btn-block" style="display:none;">
                       <spring:theme code="product.base.select.options"/>
                    </button>
                    <c:choose>
		            	<c:when test="${(!product.isSellable)}"> 
		                    <button type="submit" id="showAddtoCartUom" class="btn btn-primary pull-left btn-block" style="display:none;margin-top:0px"
		                            aria-disabled="true" disabled="disabled"> <spring:theme code="basket.add.to.Basket"/>
		                    </button>
		                    <c:if test="${!isOrderingAccount}">
		                         <span>${orderingAccountMsg}</span> 
		                    </c:if>
		                </c:when>
		                <c:otherwise>
		                    <c:choose>
								            <c:when test="${hideList ne true}">
								               <button type="submit" id="showAddtoCartUom" class="btn btn-primary btn-block js-enable-btn" style="display:none;margin-top:0px" disabled="disabled"><spring:theme code="basket.add.to.Basket"/>
		                                       </button>
		                                         <c:if test="${hideCSP eq true}">
		                                        <p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
		                                       </c:if>
							                  </c:when>
								           <c:otherwise>
								               <c:choose>
								                 <c:when test="${hideCSP ne true}">
								                 <button type="submit" id="showAddtoCartUom" class="btn btn-primary btn-block js-enable-btn" style="display:none;margin-top:0px" disabled="disabled"><spring:theme code="basket.add.to.Basket"/>
		                                       </button>
						       		              </c:when>
								                   <c:otherwise>
								                    <p class="callBranchForPrice"><spring:theme code="text.product.callforpricing"/>
								                   <button type="submit" id="showAddtoCartUom" class="btn btn-primary btn-block" style="display:none;margin-top:0px" disabled="disabled"><spring:theme code="productlisterCompare.addToCart" />
		                                       </button>  
								                   </c:otherwise>
								                </c:choose>
								           </c:otherwise>
								          </c:choose>
		                </c:otherwise>
		            </c:choose>
            	</c:otherwise>
            </c:choose>
            </div>
            </div>
            <sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')">
	<input type="hidden" name="isCurrentUser" id="isCurrentUser"
		value="true" />
</sec:authorize>

				</ycommerce:testId>
			</c:otherwise>
			
	</c:choose>
	 </div>
</c:if>
</form:form>
	<div class="wishlistAddProLink-wrapper signInOverlay">
			
		
<c:if test="${(!product.multidimensional || product.variantCount == 1) && (sellableUomListLen==1 || !(product.sellableUomsCount>=1))}">
				  <product:addToSaveList product="${product}" wishlist="${allWishlist}" wishlistName="${wishlistName}"/>	
				  </c:if>
</div>
</div>
</div>