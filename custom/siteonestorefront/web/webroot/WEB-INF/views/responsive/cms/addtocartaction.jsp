<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>

<c:if test="${orderOnlinePermissions ne true}">
	<c:set var="ATCOOId" value="orderOnlineATC" />
	<cms:pageSlot position="OnlineOrder" var="feature">
					<cms:component component="${feature}"/>
				</cms:pageSlot>
</c:if>
<c:set var="addIsProductSellable" value="false" />
        

        <c:if test="${product.isRegulateditem}">
            <c:forEach items="${product.regulatoryStates}" var="regulatoryStates">
                <c:if test="${regulatoryStates eq sessionStore.address.region.isocodeShort}">
                    <c:set var="addIsProductSellable" value="true" />
                </c:if>
            </c:forEach>
         </c:if>
<c:url value="${url}" var="addToCartUrl"/>
<c:url value="${product.url}/configuratorPage/${configuratorType}" var="configureProductUrl"/>
<c:set var="hideList" value="${product.hideList}"/>
<c:set var="hideCSP" value="${product.hideCSP}"/>
<c:set var="isStockAvailable" value="false" />
	  <c:if test="${product.stock.stockLevel gt 0 or (not empty product.stock.inventoryHit and product.stock.inventoryHit > 0) or product.isForceInStock}">
		        <c:set var="isStockAvailable" value="true" />
	 </c:if>
<c:if test="${product.inventoryCheck eq true && product.isEligibleForBackorder eq true}">
		<c:set var="isPDPATCClass" value=" inventoryCheck-and-isEligibleForBackorder" />
</c:if>
<c:set var="regulatedAndNotSellableProduct" value="false" />
<c:if test="${not empty product.isRegulatedAndNotSellable && product.isRegulatedAndNotSellable ne null && product.isRegulatedAndNotSellable ne false}">
	<c:set var="regulatedAndNotSellableProduct" value="true" />
</c:if>

<product:addToCartTitle/>

<form:form method="post" id="configureForm" class="configure_form" action="${configureProductUrl}">
<c:if test="${product.purchasable}">
	<input type="hidden" maxlength="3" size="1" class="qty" name="qty" class="qty js-qty-selector-input" value="1"> 
</c:if>
<input type="hidden" name="productCodePost" value="${product.code}"/>
<input type="hidden" name="productNamePost" value="${fn:escapeXml(product.name)}"/>

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
<form:form method="post" id="addToCartForm" class="add_to_cart_form" action="${addToCartUrl}">
	<c:set var="newuominventoryuomid" value=""/>
	<input type="hidden" id="isCouponEnabled" name="isCouponEnabled" value="false">
	<input type="hidden" id="promoProductCode" name="promoProductCode" value="">
	<c:forEach items="${product.sellableUoms}" var="sellableUom2">
		<c:if test="${product.hideUom eq true}">
			<c:set var="hideuom2" value="true"/>
			<c:set var="newuomDescription" value="${sellableUom2.inventoryUOMDesc}"/>
			<c:set var="newuominventoryuomid" value="${sellableUom2.inventoryUOMID}"/>
		</c:if>
	</c:forEach>
	<c:if test="${product.singleUom eq true}">
		<c:set var="newuommeasure" value="${product.singleUomMeasure}"/>
		<c:set var="newuominventoryuomid" value="${sellableUom2.inventoryUOMID}"/>
	</c:if>
<c:if test="${product.purchasable}">
	<input type="hidden" maxlength="3" size="1" id="qty" name="qty" class="qty js-qty-selector-input" value="1">
	<input type="hidden" id="inventoryUomId" name="inventoryUomId" value="${newuominventoryuomid}">
	
	
</c:if>
<input type="hidden" name="productCodePost" value="${product.code}"/>
<input type="hidden" name="productNamePost" value="${fn:escapeXml(product.name)}"/>
<input type="hidden" name="storeId" value="${product.stock.fullfillmentStoreId}"/>
	<input type="hidden" class="trackProductCode" name="trackProductCode" value="${product.code}">
<input type="hidden" class="trackRetailPrice" name="trackRetailPrice" value="${product.price.value}">
<input type="hidden" class="trackCSP" name="trackCSP" value="${product.customerPrice.value}">
	<input type="hidden" id="isSellable"name="isSellable" value="${(!product.isSellable)}"/>
<c:if test="${empty showAddToCart ? true : showAddToCart}">
	<c:set var="buttonType">button</c:set>
	<c:if test="${product.variantType ne 'GenericVariantProduct' and product.price ne '' }">
		<c:set var="buttonType">submit</c:set>
	</c:if>
	 <div id="addToCartSection" class="col-md-12">
	 <div class="row">
	<c:choose>
		<c:when test="${fn:contains(buttonType, 'button')}">
		
                    <c:choose>
                        <c:when test="${product.variantType eq 'GenericVariantProduct' && product.variantCount != 1}">
                            <div class="variantButton col-md-12 col-lg-12 col-xs-12">
                                <button type="${buttonType}" id="variantButton" class="btn btn-primary btn-block js-add-to-cart" >
                                    <spring:theme code="product.base.select.options"/>
                                </button>
                            </div>
                             <%-- <a href="#" onclick="return false;"style="color:#999; cursor:not-allowed;" class="addToListPosition"><spring:theme code="text.product.listingOfProducts"/></a> --%>
                              <product:addToSaveList product="${product}" wishlist="${allWishlist}" wishlistName="${wishlistName}"/>
						 
                        </c:when>
                        <c:otherwise>
                        <ycommerce:testId code="addToCartButton">
                                <button type="${(orderOnlinePermissions eq true)?buttonType:'button'}" id="${(orderOnlinePermissions eq true)?'addToCartButton':ATCOOId}" class="btn btn-primary btn-block pull-left js-add-to-cart js-enable-btn js-atc-update ${isPDPATCClass}" disabled="disabled">
                                <spring:theme code="basket.add.to.basket" />
                                </button>
                                </ycommerce:testId>
                        </c:otherwise>
                    </c:choose>

			</c:when>
			<c:otherwise>
			  <ycommerce:testId code="addToCartButton">
			<div class="col-md-12 col-lg-12 col-xs-12 paddingRt padding-zero">
			<div id="productSelect">
			<c:choose>
				<c:when test="${(isAnonymous and (hideList eq true || empty product.price ) and hideCSP ne true) or (isAnonymous and isGuestCheckoutEnabled eq false)}">
				<!-- 1 -->
				<c:choose>
					<c:when test="${product.isRegulateditem and addIsProductSellable and sessionStore.isLicensed}">
						<c:set var="disableLoginToBuy" value=""/>
					</c:when>
					<c:when test="${not product.isRegulateditem}">
						<c:set var="disableLoginToBuy" value=""/>
					</c:when>
					<c:otherwise>
						<c:set var="disableLoginToBuy" value="disabled=disabled"/>
					</c:otherwise>
				</c:choose>
				<button type="submit" data-prod-code="${product.code}" class="btn btn-primary btn-block js-login-to-buy" ${disableLoginToBuy}><spring:theme code="pdp.new.login.to.buy" /></button>
			</c:when>
			
			<c:otherwise>
			<!-- 2 -->
			 <c:choose>
            	<c:when test="${product.sellableUomsCount == 0 || regulatedAndNotSellableProduct}">
					<!-- 3 -->
            		<c:choose>
		            	<c:when test="${(!product.isSellable)}">
							<!-- 4 -->
		                    <button type="submit" id="notSellable" class="btn btn-primary pull-left btn-block js-atc-update ${isPDPATCClass}"
		                            aria-disabled="true" disabled="disabled"> <spring:theme code="addToCartAction.addToCart" />
		                    </button>
		                    <c:if test="${!isOrderingAccount}">
		                         <span class="alert_msg">${orderingAccountMsg}</span>
		                    </c:if>
		                </c:when>
		                <c:otherwise>
							<!-- 5 -->
				    <c:choose>
				    <c:when test="${(hideCSP eq true)}">
						<!-- 6 -->
		                           <button type="submit" id="showAddtoCart" class="btn btn-primary btn-block js-atc-update ${isPDPATCClass}"
		                                   disabled="disabled">
		                                   <spring:theme code="basket.add.to.basket" />
		                           </button>
		             </c:when>
		                    <c:otherwise>
								<!-- 7 -->
		                    <button type="${(orderOnlinePermissions eq true)?'submit':'button'}" id="${(orderOnlinePermissions eq true)?'showAddtoCart':ATCOOId}" class="btn btn-primary btn-block js-enable-btn js-atc-${product.code} js-atc-update ${isPDPATCClass}"
		                            disabled="disabled">
		                             <spring:theme code="basket.add.to.basket" />
		                    </button>
		                    </c:otherwise></c:choose>
		                </c:otherwise>
		            </c:choose>
            	</c:when>
            	<c:otherwise>
					<!-- 8 -->
					<c:choose>
						    <c:when test="${hideList ne true}">
								<!-- 9 -->
						       <c:choose>
						         <c:when test ="${hideCSP ne true}">
									<!-- 10 -->
						            <c:choose>
						              <c:when test="${isStockAvailable or (product.isShippable and product.stockAvailableOnlyHubStore and product.isTransferrable)}">
										<!-- 11 -->
						                <button type="${(orderOnlinePermissions eq true)?'submit':'button'}" id="${(orderOnlinePermissions eq true)?'showAddtoCart':ATCOOId}" class="btn btn-primary btn-block js-enable-btn js-atc-${product.code} js-atc-update ${isPDPATCClass}"
									       disabled="disabled">
								          <spring:theme code="basket.add.to.basket" />
							            </button>
						              </c:when>
						              <c:otherwise>
										<!-- 12 -->
						                   <button type="submit" id="showAddtoCart" class="btn btn-primary btn-block js-atc-update"
		                                   disabled="disabled">
		                                   <spring:theme code="basket.add.to.basket" />
		                                   </button>
						              </c:otherwise>
						              </c:choose>
						         </c:when>
						         <c:otherwise>
									<!-- 13 -->
						              <button type="submit" id="showAddtoCart" class="btn btn-primary btn-block js-atc-update"
		                                   disabled="disabled">
		                                   <spring:theme code="basket.add.to.basket" />
		                           </button>
						         </c:otherwise>
						       </c:choose>
						    </c:when>
						    <c:otherwise>
						      <c:choose>
						        <c:when test="${hideCSP ne true}">
									<!-- 14 -->
						            <c:choose>
						              <c:when test="${isStockAvailable}">
										<!-- 15 -->
						               <button type="${(orderOnlinePermissions eq true)?'submit':'button'}" id="${(orderOnlinePermissions eq true)?'showAddtoCart':ATCOOId}"class="btn btn-primary btn-block js-enable-btn js-atc-${product.code} js-atc-update ${isPDPATCClass}"
									disabled="disabled">
								     <spring:theme code="basket.add.to.basket" />
							         </button>
						              </c:when>
						              <c:otherwise>
										<!-- 16 -->
						                   <button type="submit" id="showAddtoCart" class="btn btn-primary btn-block js-atc-update"
		                                   disabled="disabled">
		                                   <spring:theme code="basket.add.to.basket" />
		                                   </button>
						              </c:otherwise>
						              </c:choose>
						        </c:when>
						        <c:otherwise>
									<!-- 17 -->
						             <button type="submit" id="showAddtoCart" class="btn btn-primary btn-block js-atc-update"
		                                   disabled="disabled">
		                                   <spring:theme code="basket.add.to.basket" />
		                           </button>
						        </c:otherwise>
						      </c:choose>
						    </c:otherwise>
						  
					</c:choose>
                    <c:choose>
		            	<c:when test="${(!product.isSellable) || (hideCSP eq true)}">
							<!-- 18 -->
		                    <button type="submit" id="showAddtoCartUom" class="btn btn-primary pull-left btn-block js-atc-update" style="display:none;margin-top:0px"
		                            aria-disabled="true" disabled="disabled">
		                           <spring:theme code="basket.add.to.basket" />
		                    </button>
		                    <c:if test="${!isOrderingAccount}">
		                         <span class="alert_msg" id="orderingAccountMsg">${orderingAccountMsg}</span>
		                    </c:if>
		                </c:when>
		               	<c:otherwise>
							<!-- 19 -->
		                    <button type="${(orderOnlinePermissions eq true)?'submit':'button'}" id="${(orderOnlinePermissions eq true)?'showAddtoCartUom':ATCOOId}" class="btn btn-primary btn-block js-enable-btn js-atc-${product.code} js-atc-update" style="display:none;margin-top:0px"
		                            disabled="disabled">
		                            <spring:theme code="basket.add.to.basket" />
		                    </button>
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

<%-- <input type="hidden" name="wishlist" id="wishlist"
		value="${wishlistName}" />
<input type="hidden" name="productCode" value="${product.code}"	 />
            
				 <span class="pull-left margin20"><a id="wishlistAddLink" href="#"><spring:theme code="text.product.listingOfProducts"/></a></span> --%>
				 <%--  <product:addToSaveList product="${product}" wishlist="${allWishlist}" wishlistName="${wishlistName}"/>	 --%>
				<%-- <sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')">
				 <h5><b><a href="#"><spring:theme code="text.product.listingOfProducts"/></a></b></h5>
				 </sec:authorize>
				 <sec:authorize access="hasAnyRole('ROLE_ANONYMOUS')" >
						 <h5><a href="<c:url value='/login'/>">
							<b><spring:theme code="text.product.listingOfProducts"/></b>
						</a></h5>
				</sec:authorize> --%>
				</ycommerce:testId>
			</c:otherwise>
			
	</c:choose>
	</div>
	 </div>
</c:if>
</form:form>